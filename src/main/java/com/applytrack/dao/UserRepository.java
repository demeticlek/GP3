package com.applytrack.dao;

import com.applytrack.model.User;
import java.sql.SQLException;

/**
 * Repository interface for User persistence.
 * Provides a stable abstraction above JDBC for authentication services.
 */
public interface UserRepository {
    int createUser(User user) throws SQLException;
    User findByEmail(String email) throws SQLException;
    User findById(int id) throws SQLException;
}
