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
 * Handles creation of application records. Uses Builder pattern to construct the
 * domain object and Repository abstraction for persistence.
 */
public class ApplicationServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2245991086425304931L;
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
        	// Builder pattern:
        	// ApplicationBuilder is used to construct Application objects with multiple
        	// required and optional fields in a clean and readable way.
        	// This avoids large constructor parameter lists and reduces repetitive setter calls.
            Application app = new ApplicationBuilder()
                    .userId(userId)
                    .companyName(companyName)
                    .positionTitle(positionTitle)
                    .status(req.getParameter("status"))
                    .applicationDate(req.getParameter("applicationDate"))
                    .jobUrl(req.getParameter("jobUrl"))
                    .notes(req.getParameter("notes"))
                    .build();

            int appId = appRepository.createApplication(app);

            // Observer pattern:
            // After the application is saved, publish a CREATED event.
            // This keeps the servlet focused on request handling while allowing
            // separate observer classes to respond to application changes.
            ApplicationEventPublisher.getInstance().notifyObservers(
                    new ApplicationEvent(
                            ApplicationEventType.CREATED,
                            userId,
                            appId,
                            app.getCompanyName(),
                            app.getStatus()
                    )
            );

            resp.sendRedirect("dashboard?msg=Application+added+successfully!");

        } catch (Exception e) {
            req.setAttribute("error", "Error saving application: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/application-form.jsp").forward(req, resp);
        }
    }
}
