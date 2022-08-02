package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Plateforme;

public class PlateformeDAO {
	public static List<Plateforme> getAllPlateforme() throws SQLException {
		return getPlateforme(Optional.empty(), Optional.empty());
	}

	public static Plateforme getPlateformebyPlateformeId(Integer PlateformeId) throws SQLException {
		Optional<String> whereClause = Optional.ofNullable("where Plateforme.Plateforme_Id = ?");
		return getPlateforme(whereClause, Optional.ofNullable(PlateformeId))
				.stream()
				.findAny()
				.get();
	}

	private static List<Plateforme> getPlateforme(Optional<String> whereClause, Optional<Integer> id)
			throws SQLException {
		List<Plateforme> PlateformeList = new ArrayList<>();

		String q = "Select Plateforme_Id, plateforme_nom, plateforme_description "
				+ "FROM Plateforme ";

		if (whereClause.isPresent()) {
			q += whereClause.get();
		}

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			if (id.isPresent()) {
				p.setInt(1, id.get());
			}
			// execute the query, and get a java resultset
			try (ResultSet rs = p.executeQuery()) {

				// iterate through the java resultset
				while (rs.next()) {
					Plateforme Plateforme = new Plateforme();

					Plateforme.setPlateformeId(rs.getInt("Plateforme_Id"));
					Plateforme.setNom(rs.getString("plateforme_nom"));
					Plateforme.setDescription(rs.getString("Plateforme_description"));

					PlateformeList.add(Plateforme);
				}
			}
		}

		return PlateformeList;
	}

	public static void insertPlateforme(Plateforme Plateforme) throws SQLException {
		String q = "insert Plateforme values(null,?,?)";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q, Statement.RETURN_GENERATED_KEYS)) {

			p.setString(1, Plateforme.getNom());
			p.setString(2, Plateforme.getDescription());

			int affectedRows = p.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating Plateforme failed, no rows affected.");
			}

			try (ResultSet generatedKeys = p.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					Plateforme.setPlateformeId(generatedKeys.getInt(1));
				} else {
					throw new SQLException("Creating Plateforme failed, no ID obtained.");
				}
			}
		}
	}

	public void deletePlateformeById(int PlateformeId) throws SQLException {
		String q = "Delete from Plateforme where Plateforme_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setInt(1, PlateformeId);
			p.execute();
		}
	}

	public static void updatePlateforme(Plateforme Plateforme) throws SQLException {
		String q = "update Plateforme set plateforme_nom = ?,"
				+ "plateforme_description=? "
				+ "where Plateforme_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setString(1, Plateforme.getNom());
			p.setString(2, Plateforme.getDescription());
			p.setInt(3, Plateforme.getPlateformeId());

			p.execute();
		}
	}

	public static List<Plateforme> getJeuxPlateformes(int jeuxId) throws SQLException {
		List<Plateforme> PlateformeList = new ArrayList<>();
		String q = "select p.Plateforme_Id, p.plateforme_nom, p.plateforme_description, "
				+ "(select count(*) from jeuxplateforme jp where jp.jeux_id=? and p.plateforme_id=jp.plateforme_id) as checked "
				+ "from plateforme p";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setInt(1, jeuxId);
			try (ResultSet rs = p.executeQuery()) {

				// iterate through the java resultset
				while (rs.next()) {
					Plateforme Plateforme = new Plateforme();

					Plateforme.setPlateformeId(rs.getInt("Plateforme_Id"));
					Plateforme.setNom(rs.getString("plateforme_nom"));
					Plateforme.setDescription(rs.getString("Plateforme_description"));
					Plateforme.setChecked(rs.getBoolean("checked"));

					PlateformeList.add(Plateforme);
				}
			}
		}

		return PlateformeList;
	}
}
