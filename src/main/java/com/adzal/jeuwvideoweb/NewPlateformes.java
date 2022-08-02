package com.adzal.jeuwvideoweb;

import dataaccess.PlateformeDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Plateforme;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "NewPlateformes", value = "/NewPlateformes")
public class NewPlateformes extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Plateforme plateforme = null;
        String message = "";

        String plateformeId = request.getParameter("plateformeId");
        if (plateformeId == null) {
            // Create an empty plateforme so that the page is empty
            plateforme = new Plateforme();
        } else {
            // Get the plateforme from the DAO
            try {
                plateforme = PlateformeDAO.getPlateformebyPlateformeId(Integer.parseInt(plateformeId));
            } catch (Exception e) {
                message = "oops";
            }
        }
        HttpSession session = request.getSession();
        // Put plateforme in the session for the next page
        session.setAttribute("plateforme", plateforme);
        request.setAttribute("message", message);
        getServletContext().getRequestDispatcher("/WEB-INF/newplateforme.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = "";
        HttpSession session = request.getSession();
        Plateforme plateforme = (Plateforme) session.getAttribute("plateforme");
        plateforme.setNom(request.getParameter("nom"));
        plateforme.setDescription(request.getParameter("description"));

        try {
            if (plateforme.getPlateformeId() > 0) {
                // We already have a plateforme_Id so do an update
                PlateformeDAO.updatePlateforme(plateforme);
                message = "plateforme updated";
            } else {
                PlateformeDAO.insertPlateforme(plateforme);
                message = "plateforme created";
            }
        } catch (SQLException e) {
            message = "Enter a new plateforme nom.";
        }

        request.setAttribute("message", message);
        session.setAttribute("plateforme", plateforme);
        getServletContext().getRequestDispatcher("/WEB-INF/newplateforme.jsp").forward(request, response);
    }
}
