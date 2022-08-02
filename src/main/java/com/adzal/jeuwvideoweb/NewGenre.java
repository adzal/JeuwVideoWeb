package com.adzal.jeuwvideoweb;

import dataaccess.GenreDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Genre;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "NewGenre", value = "/NewGenre")
public class NewGenre extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = "";
        String genreId = request.getParameter("genreId");
        Genre genre = null;

        if (genreId == null) {
            genre = new Genre();
        } else {
            try {
                genre = GenreDAO.getGenreById(Integer.parseInt(genreId));
            } catch (Exception e) {
                message = "oops";
            }
        }

        request.setAttribute("message", message);
        HttpSession session = request.getSession();
        // Put genre in the request for the next page
        session.setAttribute("genre", genre);
        getServletContext().getRequestDispatcher("/WEB-INF/newgenre.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String message = "";
        Genre genre = (Genre) session.getAttribute("genre");
        genre.setTitre(request.getParameter("titre"));
        genre.setDescription(request.getParameter("description"));

        try {
            if (genre.getGenreId() > 0) {
                // already exists so do an update
                GenreDAO.updateGenre(genre);
                message = "Genre updated";
            } else {
                GenreDAO.insertGenre(genre);
                message = "Genre created";
            }
        } catch (SQLException e) {
            message = "Enter a new title.";
        }

        request.setAttribute("message", message);
        session.setAttribute("genre", genre);
        getServletContext().getRequestDispatcher("/WEB-INF/newgenre.jsp").forward(request, response);
    }
}
