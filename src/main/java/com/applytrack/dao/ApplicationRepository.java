package com.applytrack.dao;

import com.applytrack.model.Application;
import java.sql.SQLException;
import java.util.List;

/**
 * Repository interface for Application persistence.
 * Group Project 3 refactoring: separates the contract from the MySQL implementation.
 */
public interface ApplicationRepository {
    int createApplication(Application app) throws SQLException;
    List<Application> findByUserId(int userId) throws SQLException;
    Application findById(int id) throws SQLException;
    boolean updateApplication(Application app) throws SQLException;
    boolean deleteApplication(int id, int userId) throws SQLException;
    int countByStatus(int userId, String status) throws SQLException;
    int countTotal(int userId) throws SQLException;
}
