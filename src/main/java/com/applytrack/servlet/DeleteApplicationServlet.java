package com.applytrack.servlet;

import com.applytrack.dao.ApplicationRepository;
import com.applytrack.dao.DaoFactory;
import com.applytrack.model.Application;
import com.applytrack.observer.ApplicationEvent;
import com.applytrack.observer.ApplicationEventPublisher;
import com.applytrack.observer.ApplicationEventType;

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

	/**
	 * 
	 */
	private static final long serialVersionUID = -3788879140633554855L;
	private final ApplicationRepository appRepository = DaoFactory.getInstance().getApplicationRepository();

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

            // Observer pattern:
            // Fetch the application before deleting so the event can include useful details.
            // If deletion succeeds, publish a DELETED event to all registered observers.
            Application app = appRepository.findById(appId);
            
            boolean deleted = appRepository.deleteApplication(appId, userId);
            if (deleted && app != null) {
                ApplicationEventPublisher.getInstance().notifyObservers(
                        new ApplicationEvent(
                                ApplicationEventType.DELETED,
                                userId,
                                appId,
                                app.getCompanyName(),
                                app.getStatus()
                        )
                );

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