package com.applytrack.servlet;

import com.applytrack.model.DashboardData;
import com.applytrack.service.DashboardService;

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

    private final DashboardService dashService = new DashboardService();

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
            req.setAttribute("counts", dashboardData.getCounts());
            req.setAttribute("applications", dashboardData.getApplications());
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
