package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Genre;

public class GenreDAO {
	public static List<Genre> getAllGenres() throws SQLException {
		List<Genre> GenreList = new ArrayList<>();

		String q = "SELECT Genre_Id, genre_titre, genre_description "
				+ "FROM Genre";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {

			// execute the query, and get a java resultset
			try (ResultSet rs = p.executeQuery()) {

				// iterate through the java resultset
				while (rs.next()) {
					Genre Genre = new Genre();

					Genre.setGenreId(rs.getInt("Genre_Id"));
					Genre.setTitre(rs.getString("Genre_titre"));
					Genre.setDescription(rs.getString("Genre_description"));

					GenreList.add(Genre);
				}
			}
		}

		return GenreList;
	}

	public static void updateGenre(Genre genre) throws SQLException {
		String q = "update Genre "
				+ "set Genre_titre = ?,"
				+ "genre_description=? "
				+ "where Genre_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setString(1, genre.getTitre());
			p.setString(2, genre.getDescription());
			p.setInt(3, genre.getGenreId());

			p.execute();
		}
	}

	public static void insertGenre(Genre Genre) throws SQLException {
		String q = "insert Genre values(null,?,?)";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q, Statement.RETURN_GENERATED_KEYS)) {
			p.setString(1, Genre.getTitre());
			p.setString(2, Genre.getDescription());
			int affectedRows = p.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating Genre failed, no rows affected.");
			}

			try (ResultSet generatedKeys = p.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					Genre.setGenreId(generatedKeys.getInt(1));
				} else {
					throw new SQLException("Creating Genre failed, no ID obtained.");
				}
			}
		}
	}

	public void deleteGenreById(int GenreId) throws SQLException {
		String q = "Delete from Genre where Genre_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setInt(1, GenreId);
			p.execute();
		}
	}

	public static Genre getGenreById(int genreId) throws SQLException {
		String q = "SELECT Genre_Id, genre_titre, genre_description "
				+ "FROM Genre where Genre_Id=?";
		Genre genre = new Genre();

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setInt(1, genreId);
			
			// execute the query, and get a java resultset
			try (ResultSet rs = p.executeQuery()) {

				// iterate through the java resultset
				while (rs.next()) {

					genre.setGenreId(rs.getInt("Genre_Id"));
					genre.setTitre(rs.getString("Genre_titre"));
					genre.setDescription(rs.getString("Genre_description"));
					break;
				}
			}
		}

		return genre;
	}
}
