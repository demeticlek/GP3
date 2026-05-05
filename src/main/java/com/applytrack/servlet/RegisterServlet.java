package com.applytrack.servlet;

import com.applytrack.model.User;
import com.applytrack.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles user registration (PR0001).
 * GET  /register -> displays the registration JSP page
 * POST /register -> validates input, creates account, redirects to login
 *
 * Follows MVC pattern: Servlet = Controller, JSP = View, AuthService = Model.
 * Sequence diagram reference: M0003 (Sign-up and Verification flow).
 */
public class RegisterServlet extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String fullName = req.getParameter("fullName");
        String program = req.getParameter("program");

        try {
            User user = authService.register(email, password, fullName, program);
            // Registration successful — redirect to login with success message
            resp.sendRedirect("login?registered=true");

        } catch (IllegalArgumentException e) {
            // Validation error — show form again with error message
            req.setAttribute("error", e.getMessage());
            req.setAttribute("emailValue", email);
            req.setAttribute("fullNameValue", fullName);
            req.setAttribute("programValue", program);
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Error: " + e.getClass().getName() + " - " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        }
    }
}