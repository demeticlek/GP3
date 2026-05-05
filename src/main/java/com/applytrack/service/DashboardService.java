package com.applytrack.service;

import com.applytrack.dao.ApplicationRepository;
import com.applytrack.dao.DaoFactory;
import com.applytrack.model.Application;
import com.applytrack.model.DashboardData;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Business service for dashboard metrics and application list.
 * Group Project 3 refactoring: uses CompletableFuture and ExecutorService so
 * dashboard count queries and list retrieval can run concurrently.
 */
public class DashboardService {

    private final ApplicationRepository appRepository;
    private final ExecutorService dashboardExecutor;

    public DashboardService() {
        this.appRepository = DaoFactory.getInstance().getApplicationRepository();
        this.dashboardExecutor = Executors.newFixedThreadPool(2);
    }

    /**
     * Returns dashboard data using two concurrent tasks: status counts and application list.
     */
    public DashboardData getDashboardData(int userId) throws SQLException {
        CompletableFuture<Map<String, Integer>> countsFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return getStatusCounts(userId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, dashboardExecutor);

        CompletableFuture<List<Application>> applicationsFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return appRepository.findByUserId(userId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, dashboardExecutor);

        try {
            return new DashboardData(countsFuture.get(), applicationsFuture.get());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SQLException("Dashboard loading was interrupted.", e);
        } catch (ExecutionException e) {
            throw new SQLException("Dashboard loading failed.", e);
        }
    }

    public Map<String, Integer> getStatusCounts(int userId) throws SQLException {
        Map<String, Integer> counts = new HashMap<>();

        counts.put("total", appRepository.countTotal(userId));
        counts.put(Application.STATUS_SAVED, appRepository.countByStatus(userId, Application.STATUS_SAVED));
        counts.put(Application.STATUS_APPLIED, appRepository.countByStatus(userId, Application.STATUS_APPLIED));
        counts.put(Application.STATUS_INTERVIEWING, appRepository.countByStatus(userId, Application.STATUS_INTERVIEWING));
        counts.put(Application.STATUS_OFFER, appRepository.countByStatus(userId, Application.STATUS_OFFER));
        counts.put(Application.STATUS_REJECTED, appRepository.countByStatus(userId, Application.STATUS_REJECTED));
        counts.put(Application.STATUS_CLOSED, appRepository.countByStatus(userId, Application.STATUS_CLOSED));

        return counts;
    }

    public void shutdown() {
        dashboardExecutor.shutdown();
    }
}
