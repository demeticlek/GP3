package com.applytrack.service;

import com.applytrack.dao.DaoFactory;
import com.applytrack.dao.UserRepository;
import com.applytrack.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

/**
 * Business logic layer for user authentication.
 * Handles registration validation, password hashing, and login verification.
 * 
 * Maps to PRD: PR0001 (registration), PR0002 (sign-in).
 * Maps to class diagram M0002: AuthService class.
 * Maps to sequence diagram M0003: Sign-up and verification flow.
 * 
 * Uses BCrypt for secure password hashing — passwords are never stored in plain text.
 */
public class AuthService {

    private final UserRepository userDAO = DaoFactory.getInstance().getUserRepository();

    /**
     * Registers a new student account.
     * Validates input, checks for duplicate email, hashes password with BCrypt.
     *
     * @return the created User object with generated ID
     * @throws IllegalArgumentException if validation fails
     */
    public User register(String email, String password, String fullName,
                         String program) throws SQLException {

        // Input validation
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required.");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters.");
        }
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name is required.");
        }

        // Check for duplicate email [PR0001]
        if (userDAO.findByEmail(email.trim()) != null) {
            throw new IllegalArgumentException("An account with this email already exists.");
        }

        // Hash password with BCrypt — never store plain text
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Create and persist user
        User user = new User(email.trim(), hashedPassword, fullName.trim(),
                             "STUDENT", program != null ? program.trim() : "");

        int id = userDAO.createUser(user);
        user.setId(id);

        return user;
    }

    /**
     * Authenticates a user by email and password.
     * Uses BCrypt.checkpw() to verify against the stored hash.
     *
     * @return the authenticated User, or null if credentials are invalid
     */
    public User login(String email, String password) throws SQLException {
        if (email == null || password == null) {
            return null;
        }

        User user = userDAO.findByEmail(email.trim());
        if (user == null) {
            return null;
        }

        // Verify password against stored BCrypt hash
        if (BCrypt.checkpw(password, user.getPasswordHash())) {
            return user;
        }

        return null;
    }
}
