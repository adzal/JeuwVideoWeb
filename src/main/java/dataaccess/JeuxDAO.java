package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Jeux;

public class JeuxDAO {
	public static List<Jeux> getAllJeux() throws SQLException {
		return getJeux(Optional.empty(), Optional.empty(), Optional.empty());
	}

	public static Jeux getJeuxbyJeuxId(Integer jeuxId) throws SQLException {
		Optional<String> whereClause = Optional.ofNullable("where jeux.Jeux_Id = ?");
		return getJeux(whereClause, Optional.ofNullable(jeuxId), Optional.empty())
				.stream()
				.findAny()
				.get();
	}

	public static List<Jeux> getJeuxByGenreId(int genreId) throws SQLException {
		Optional<String> whereClause = Optional.ofNullable("where jeux.Genre_Id = ?");
		return getJeux(whereClause, Optional.ofNullable(genreId), Optional.empty());
	}

	public static List<Jeux> getJeuxByPlateformeId(int PlateformeId) throws SQLException {
		Optional<String> whereClause = Optional.ofNullable("where jeuxplateforme.Plateforme_Id = ?");
		return getJeux(whereClause, Optional.empty(), Optional.ofNullable(PlateformeId));
	}

	public static List<Jeux> getJeuxByGenreAndPlateforme(int genreId, int PlateformeId) throws SQLException {
		Optional<String> whereClause = Optional.ofNullable("where jeux.Genre_Id = ? "
				+ "and jeuxplateforme.Plateforme_Id = ?");
		return getJeux(whereClause, Optional.ofNullable(genreId), Optional.ofNullable(PlateformeId));
	}

	private static List<Jeux> getJeux(
			Optional<String> whereClause,
			Optional<Integer> id,
			Optional<Integer> plateformeId) throws SQLException {
		List<Jeux> jeuxList = new ArrayList<>();

		String q = "SELECT jeux.Jeux_Id,Jeux_Titre,Jeux_Description,Jeux_Prix,Jeux_DateSortie,"
				+ "Jeux_PaysOrigine,Jeux_Connexion,Jeux_Mode,jeux.Genre_Id, "
				+ "genre.genre_description,"
				+ "plateforme.plateforme_nom, plateforme.Plateforme_Id "
				+ "FROM jeux "
				+ "inner join genre on genre.genre_id = jeux.genre_id "
				+ "inner join jeuxplateforme on jeux.Jeux_Id = jeuxplateforme.Jeux_Id "
				+ "inner join plateforme on plateforme.Plateforme_Id = jeuxplateforme.Plateforme_Id ";

		if (whereClause.isPresent()) {
			q += whereClause.get();
		}

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			if (id.isPresent()) {
				p.setInt(1, id.get());
				if (plateformeId.isPresent()) {
					p.setInt(2, plateformeId.get());
				}
			} else {
				if (plateformeId.isPresent()) {
					p.setInt(1, plateformeId.get());
				}
			}
			// execute the query, and get a java resultset
			try (ResultSet rs = p.executeQuery()) {

				// iterate through the java resultset
				while (rs.next()) {
					Jeux jeux = new Jeux();

					jeux.setJeuxId(rs.getInt("jeux_Id"));
					jeux.setTitre(rs.getString("jeux_titre"));
					jeux.setDescription(rs.getString("jeux_description"));
					jeux.setPrix(rs.getDouble("jeux_prix"));
					jeux.setDateSortie(rs.getDate("jeux_datesortie"));
					jeux.setPaysOrigine(rs.getString("jeux_paysorigine"));
					jeux.setConnexion(rs.getString("jeux_connexion"));
					jeux.setMode(rs.getString("jeux_mode"));
					jeux.setGenreId(rs.getInt("genre_id"));
					jeux.setGenreDescription(rs.getString("genre_description"));
					jeux.setPlateformeNom(rs.getString("plateforme_nom"));
					jeux.setPlateformeId(rs.getInt("plateforme_id"));

					jeuxList.add(jeux);
				}
			}
		}

		return jeuxList;
	}

	public void updateTitreById(int jeuxId, String newTitre) throws SQLException {
		String q = "update jeux set jeux_titre = ? where jeux_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setString(1, newTitre);
			p.setInt(2, jeuxId);

			p.execute();
		}
	}

	public void insertJeux(Jeux jeux) throws SQLException {
		String q = "insert jeux values(null,?,?,?,?,?,?,?,?)";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q, Statement.RETURN_GENERATED_KEYS)) {
			p.setString(1, jeux.getTitre());
			p.setString(2, jeux.getDescription());
			p.setDouble(3, jeux.getPrix());
			p.setDate(4, jeux.getDateSortie());
			p.setString(5, jeux.getPaysOrigine());
			p.setString(6, jeux.getConnexion());
			p.setString(7, jeux.getJeuxMode());
			p.setInt(8, jeux.getGenreId());
			int affectedRows = p.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating Jeux failed, no rows affected.");
			}

			try (ResultSet generatedKeys = p.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					jeux.setJeuxId(generatedKeys.getInt(1));
				} else {
					throw new SQLException("Creating Jeux failed, no ID obtained.");
				}
			}
		}
	}

	public void deleteJeuxById(int jeuxId) throws SQLException {
		String q = "Delete from jeux where jeux_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setInt(1, jeuxId);
			p.execute();
		}
	}

	public void updateJeux(Jeux jeux) throws SQLException {
		String q = "update jeux set jeux_titre = ?,"
				+ "jeux_description=?,"
				+ "jeux_paysorigine=?,"
				+ "jeux_connexion=?,"
				+ "jeux_mode=?, "
				+ "jeux_prix=?, "
				+ "genre_id=? "
				+ "where jeux_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setString(1, jeux.getTitre());
			p.setString(2, jeux.getDescription());
			p.setString(3, jeux.getPaysOrigine());
			p.setString(4, jeux.getConnexion());
			p.setString(5, jeux.getJeuxMode());
			p.setDouble(6, jeux.getPrix());
			p.setInt(7, jeux.getGenreId());
			p.setInt(8, jeux.getJeuxId());

			p.execute();
		}
	}

	public void clearPlateformes(int jeuxId) throws SQLException {
		String q = "Delete from jeuxplateforme where jeux_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setInt(1, jeuxId);
			p.execute();
		}
	}

	public void UpdatePlateforms(int jeuxId, String[] plateformes) throws SQLException {
		for (String string : plateformes) {
			String q = "insert jeuxplateforme(jeux_id, plateforme_id) values(?,?)";

			try (Connection connection = ConnectionFactory.getInstance().getConnection();
					PreparedStatement p = connection.prepareStatement(q)) {
				p.setInt(1, jeuxId);
				int plateforme_id = Integer.parseInt(string);
				p.setInt(2, plateforme_id);
				p.execute();
			}
		}
	}
}
