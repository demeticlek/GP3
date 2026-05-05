package com.applytrack.servlet;

import com.applytrack.dao.ApplicationRepository;
import com.applytrack.dao.DaoFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Handles deleting an application record (PR0005 — delete).
 * GET /delete-application?id=N -> deletes the record, redirects to dashboard.
 *
 * Security: only the owning user can delete their own applications.
 */
public class DeleteApplicationServlet extends HttpServlet {

    private final ApplicationRepository appDAO = DaoFactory.getInstance().getApplicationRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect("login");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        try {
            int appId = Integer.parseInt(req.getParameter("id"));
            boolean deleted = appDAO.deleteApplication(appId, userId);

            if (deleted) {
                resp.sendRedirect("dashboard?msg=Application+deleted.");
            } else {
                resp.sendRedirect("dashboard?error=Application+not+found.");
            }

        } catch (NumberFormatException e) {
            resp.sendRedirect("dashboard?error=Invalid+application+ID.");
        } catch (Exception e) {
            resp.sendRedirect("dashboard?error=Error+deleting+application.");
        }
    }
}