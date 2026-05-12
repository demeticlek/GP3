<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - ApplyTrack</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<nav class="navbar">
    <a href="dashboard" class="brand">&#128188; ApplyTrack</a>
    <div class="nav-right">
        <span class="nav-user">${sessionScope.userName}</span>
        <a href="logout" class="nav-link">Logout</a>
    </div>
</nav>

<div class="container">

    <!-- Success / Error messages -->
    <c:if test="${not empty param.msg}">
        <div class="alert alert-success">${param.msg}</div>
    </c:if>
    <c:if test="${not empty param.error}">
        <div class="alert alert-error">${param.error}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>

    <!-- Stats Bar [PR0009] -->
    <div class="stats-bar">
        <div class="stat-card">
            <div class="stat-number">${counts['total']}</div>
            <div class="stat-label">Total</div>
        </div>
        <div class="stat-card">
            <div class="stat-number">${counts['Applied']}</div>
            <div class="stat-label">Applied</div>
        </div>
        <div class="stat-card">
            <div class="stat-number">${counts['Interviewing']}</div>
            <div class="stat-label">Interviewing</div>
        </div>
        <div class="stat-card">
            <div class="stat-number">${counts['Offer']}</div>
            <div class="stat-label">Offers</div>
        </div>
        <div class="stat-card">
            <div class="stat-number">${counts['Rejected']}</div>
            <div class="stat-label">Rejected</div>
        </div>
    </div>

    <!-- Application List [PR0005] -->
    <div class="card">
    <div class="card-header">
        <h2>My Applications</h2>
        <a href="application" class="btn btn-primary btn-sm">+ Add Application</a>
    </div>

    <!-- Feature improvement:
         Status filter lets users narrow the dashboard application list
         without changing the underlying dashboard statistics. -->
    <form method="get" action="dashboard" class="filter-form">
        <label for="status">Filter by status:</label>

        <select name="status" id="status" onchange="this.form.submit()">
            <option value="All" ${selectedStatus == 'All' ? 'selected' : ''}>All</option>
            <option value="Saved" ${selectedStatus == 'Saved' ? 'selected' : ''}>Saved</option>
            <option value="Applied" ${selectedStatus == 'Applied' ? 'selected' : ''}>Applied</option>
            <option value="Interviewing" ${selectedStatus == 'Interviewing' ? 'selected' : ''}>Interviewing</option>
            <option value="Offer" ${selectedStatus == 'Offer' ? 'selected' : ''}>Offer</option>
            <option value="Rejected" ${selectedStatus == 'Rejected' ? 'selected' : ''}>Rejected</option>
            <option value="Closed" ${selectedStatus == 'Closed' ? 'selected' : ''}>Closed</option>
        </select>
    </form>

    <c:choose>
            <c:when test="${empty applications}">
                <p class="empty-msg">No applications yet. Click <strong>+ Add Application</strong> to get started!</p>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>Company</th>
                            <th>Position</th>
                            <th>Status</th>
                            <th>Date</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="app" items="${applications}">
                            <tr>
                                <td>${app.companyName}</td>
                                <td>${app.positionTitle}</td>
                                <td>
                                    <span class="status-badge status-${app.status.toLowerCase()}">
                                        ${app.status}
                                    </span>
                                </td>
                                <td>${app.applicationDate}</td>
                                <td class="actions">
                                    <a href="edit-application?id=${app.id}" class="btn btn-secondary btn-sm">Edit</a>
                                    <a href="delete-application?id=${app.id}" class="btn btn-danger btn-sm"
                                       onclick="return confirm('Delete this application?');">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>

</div>

<footer class="footer">
    <p>© <%= java.time.Year.now() %> ApplyTrack — CST8288 Group Project</p>
</footer>

</body>
</html>