package com.applytrack.servlet;

import com.applytrack.dao.ApplicationRepository;
import com.applytrack.dao.DaoFactory;
import com.applytrack.model.Application;
import com.applytrack.model.ApplicationBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Handles creation of application records. Uses Builder pattern to construct the
 * domain object and Repository abstraction for persistence.
 */
public class ApplicationServlet extends HttpServlet {

    private final ApplicationRepository appRepository = DaoFactory.getInstance().getApplicationRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect("login");
            return;
        }

        req.getRequestDispatcher("/WEB-INF/views/application-form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect("login");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String companyName = req.getParameter("companyName");
        String positionTitle = req.getParameter("positionTitle");

        if (companyName == null || companyName.trim().isEmpty()
                || positionTitle == null || positionTitle.trim().isEmpty()) {
            req.setAttribute("error", "Company name and position title are required.");
            req.getRequestDispatcher("/WEB-INF/views/application-form.jsp").forward(req, resp);
            return;
        }

        try {
            Application app = new ApplicationBuilder()
                    .userId(userId)
                    .companyName(companyName)
                    .positionTitle(positionTitle)
                    .status(req.getParameter("status"))
                    .applicationDate(req.getParameter("applicationDate"))
                    .jobUrl(req.getParameter("jobUrl"))
                    .notes(req.getParameter("notes"))
                    .build();

            appRepository.createApplication(app);
            resp.sendRedirect("dashboard?msg=Application+added+successfully!");

        } catch (Exception e) {
            req.setAttribute("error", "Error saving application: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/application-form.jsp").forward(req, resp);
        }
    }
}
