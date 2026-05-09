package com.applytrack.servlet;

import com.applytrack.model.DashboardData;
import com.applytrack.service.DashboardService;
import com.applytrack.dao.ApplicationRepository;
import com.applytrack.dao.DaoFactory;
import com.applytrack.model.Application;

import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Main dashboard servlet. Servlet acts as MVC controller while DashboardService
 * coordinates business logic and multi-threaded dashboard loading.
 */
public class DashboardServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1009702226741203430L;
	private final DashboardService dashService = new DashboardService();
    private final ApplicationRepository appRepository =
            DaoFactory.getInstance().getApplicationRepository();

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
        	DashboardData dashboardData = dashService.getDashboardData(userId);

        	String selectedStatus = req.getParameter("status");
        	List<Application> applications;

        	if (selectedStatus != null && !selectedStatus.trim().isEmpty()
        	        && !"All".equalsIgnoreCase(selectedStatus)) {
        	    applications = appRepository.findByUserIdAndStatus(userId, selectedStatus.trim());
        	} else {
        	    applications = dashboardData.getApplications();
        	    selectedStatus = "All";
        	}

        	req.setAttribute("counts", dashboardData.getCounts());
        	req.setAttribute("applications", applications);
        	req.setAttribute("selectedStatus", selectedStatus);

        	req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("error", "Error loading dashboard: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {
        dashService.shutdown();
        super.destroy();
    }
}
