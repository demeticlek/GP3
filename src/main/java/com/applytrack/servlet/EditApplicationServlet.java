package com.applytrack.servlet;

import com.applytrack.dao.ApplicationRepository;
import com.applytrack.dao.DaoFactory;
import com.applytrack.model.Application;
import com.applytrack.model.ApplicationBuilder;
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
 * Handles editing an application record. Uses Repository abstraction and Builder
 * pattern to keep servlet code focused on HTTP/session workflow.
 */
public class EditApplicationServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8752366895646924984L;
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
            Application app = appRepository.findById(appId);

            if (app == null || app.getUserId() != userId) {
                resp.sendRedirect("dashboard?error=Application+not+found.");
                return;
            }

            req.setAttribute("app", app);
            req.getRequestDispatcher("/WEB-INF/views/edit-application.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendRedirect("dashboard?error=Invalid+application+ID.");
        } catch (Exception e) {
            resp.sendRedirect("dashboard?error=Error+loading+application.");
        }
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

        try {
            int appId = Integer.parseInt(req.getParameter("id"));
            Application existing = appRepository.findById(appId);

            if (existing == null || existing.getUserId() != userId) {
                resp.sendRedirect("dashboard?error=Application+not+found.");
                return;
            }

            String companyName = req.getParameter("companyName");
            String positionTitle = req.getParameter("positionTitle");

            if (companyName == null || companyName.trim().isEmpty()
                    || positionTitle == null || positionTitle.trim().isEmpty()) {
                req.setAttribute("error", "Company name and position title are required.");
                req.setAttribute("app", existing);
                req.getRequestDispatcher("/WEB-INF/views/edit-application.jsp").forward(req, resp);
                return;
            }

            // Builder pattern:
            // Rebuild the Application object using ApplicationBuilder instead of manually
            // setting each field individually. This improves readability and maintainability
            // when handling objects with many configurable properties.
            Application updated = new ApplicationBuilder()
                    .id(appId)
                    .userId(userId)
                    .companyName(companyName)
                    .positionTitle(positionTitle)
                    .status(req.getParameter("status"))
                    .applicationDate(req.getParameter("applicationDate"))
                    .jobUrl(req.getParameter("jobUrl"))
                    .notes(req.getParameter("notes"))
                    .build();

            
            boolean saved = appRepository.updateApplication(updated);

            if (saved) {
                // Observer pattern:
                // If the update succeeds, publish an UPDATED event.
                // This decouples post-update behavior from the servlet and makes it easier
                // to add future features such as notifications or audit logging.
                ApplicationEventPublisher.getInstance().notifyObservers(
                        new ApplicationEvent(
                                ApplicationEventType.UPDATED,
                                userId,
                                appId,
                                updated.getCompanyName(),
                                updated.getStatus()
                        )
                );

                resp.sendRedirect("dashboard?msg=Application+updated+successfully!");
            } else {
                resp.sendRedirect("dashboard?error=Application+not+found.");
            }

        } catch (Exception e) {
            resp.sendRedirect("dashboard?error=Error+updating+application.");
        }
    }
}
