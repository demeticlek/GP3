package com.applytrack.dao;

import com.applytrack.model.User;

import java.sql.*;

/**
 * Data Access Object for the users table.
 * Handles all JDBC operations for User entities.
 * Supports PR0001 (registration), PR0002 (login), PR0004 (profile).
 * 
 * DAO pattern as recommended in course LinkedIn resources
 * and live session slides (slide 4: "The service between servlet 
 * and Database: It is the DAO").
 */
public class UserDAO implements UserRepository {

    /**
     * Inserts a new user into the database.
     * Uses PreparedStatement to prevent SQL injection.
     * @return the generated user ID, or -1 on failure
     */
    public int createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (email, password_hash, full_name, role, program) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getProgram());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        return -1;
    }

    /**
     * Finds a user by email address.
     * Used for login authentication and duplicate email check.
     * @return the User object or null if not found
     */
    public User findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    /**
     * Finds a user by their primary key ID.
     */
    public User findById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    /**
     * Maps a ResultSet row to a User object.
     * Centralizes the mapping logic to avoid code duplication.
     */
    private User mapRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setFullName(rs.getString("full_name"));
        user.setRole(rs.getString("role"));
        user.setProgram(rs.getString("program"));
        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return user;
    }
}