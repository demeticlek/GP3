package com.applytrack.dao;

import com.applytrack.model.Application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the applications table.
 * Provides full CRUD operations for job application records.
 * Supports PR0005 (create/edit/archive/delete), PR0006 (status changes),
 * PR0009 (dashboard metrics via count queries).
 * 
 * DAO pattern separates database logic from business logic,
 * as required by the three-tier architecture (CLR 6).
 */
public class ApplicationDAO implements ApplicationRepository {

    /**
     * Creates a new application record.
     * @return the generated application ID, or -1 on failure
     */
    public int createApplication(Application app) throws SQLException {
        String sql = "INSERT INTO applications (user_id, company_name, position_title, "
                   + "status, application_date, job_url, notes) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, app.getUserId());
            ps.setString(2, app.getCompanyName());
            ps.setString(3, app.getPositionTitle());
            ps.setString(4, app.getStatus());
            ps.setDate(5, Date.valueOf(app.getApplicationDate()));
            ps.setString(6, app.getJobUrl());
            ps.setString(7, app.getNotes());
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
     * Retrieves all applications for a specific user, newest first.
     */
    public List<Application> findByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM applications WHERE user_id = ? ORDER BY updated_at DESC";
        List<Application> list = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }
    
    /**
     * Retrieves applications for a specific user filtered by status.
     * Used by the dashboard status filter feature added in Part 3 of the Project
     */
    @Override
    public List<Application> findByUserIdAndStatus(int userId, String status) throws SQLException {
        String sql = "SELECT * FROM applications WHERE user_id = ? AND status = ? ORDER BY updated_at DESC";
        List<Application> list = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, status);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }

        return list;
    }

    /**
     * Retrieves a single application by ID.
     */
    public Application findById(int id) throws SQLException {
        String sql = "SELECT * FROM applications WHERE id = ?";

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
     * Updates an existing application record.
     */
    public boolean updateApplication(Application app) throws SQLException {
        String sql = "UPDATE applications SET company_name = ?, position_title = ?, "
                   + "status = ?, application_date = ?, job_url = ?, notes = ? "
                   + "WHERE id = ? AND user_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, app.getCompanyName());
            ps.setString(2, app.getPositionTitle());
            ps.setString(3, app.getStatus());
            ps.setDate(4, Date.valueOf(app.getApplicationDate()));
            ps.setString(5, app.getJobUrl());
            ps.setString(6, app.getNotes());
            ps.setInt(7, app.getId());
            ps.setInt(8, app.getUserId());

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Deletes an application record. Only the owning user can delete.
     */
    public boolean deleteApplication(int id, int userId) throws SQLException {
        String sql = "DELETE FROM applications WHERE id = ? AND user_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Counts applications by status for a user (used by dashboard PR0009).
     */
    public int countByStatus(int userId, String status) throws SQLException {
        String sql = "SELECT COUNT(*) FROM applications WHERE user_id = ? AND status = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, status);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    /**
     * Counts total applications for a user.
     */
    public int countTotal(int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM applications WHERE user_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    /**
     * Maps a ResultSet row to an Application object.
     */
    private Application mapRow(ResultSet rs) throws SQLException {
        Application app = new Application();
        app.setId(rs.getInt("id"));
        app.setUserId(rs.getInt("user_id"));
        app.setCompanyName(rs.getString("company_name"));
        app.setPositionTitle(rs.getString("position_title"));
        app.setStatus(rs.getString("status"));
        app.setApplicationDate(rs.getDate("application_date").toLocalDate());
        app.setJobUrl(rs.getString("job_url"));
        app.setNotes(rs.getString("notes"));
        app.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        app.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return app;
    }
}