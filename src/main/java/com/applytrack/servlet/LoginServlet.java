package com.applytrack.servlet;

import com.applytrack.model.User;
import com.applytrack.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Handles user login (PR0002).
 * GET  /login -> displays the login JSP page
 * POST /login -> authenticates user, creates session, redirects to dashboard
 *
 * Follows MVC pattern: Servlet = Controller, JSP = View, AuthService = Model.
 * Sequence diagram reference: M0003 (Sign-up and Verification flow).
 */
public class LoginServlet extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // If already logged in, redirect to dashboard
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            resp.sendRedirect("dashboard");
            return;
        }

        // Forward to login JSP
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            User user = authService.login(email, password);

            if (user != null) {
                // Create session and store user info [PR0002]
                HttpSession session = req.getSession(true);
                session.setAttribute("user", user);
                session.setAttribute("userId", user.getId());
                session.setAttribute("userName", user.getFullName());
                resp.sendRedirect("dashboard");
            } else {
                req.setAttribute("error", "Invalid email or password.");
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            req.setAttribute("error", "An unexpected error occurred.");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}