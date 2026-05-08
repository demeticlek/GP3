<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Application - ApplyTrack</title>
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
    <div class="card">
        <h2>Add New Application</h2>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <form method="POST" action="application">
            <div class="form-row">
                <div class="form-group">
                    <label for="companyName">Company Name *</label>
                    <input type="text" id="companyName" name="companyName" required
                           placeholder="e.g. Shopify">
                </div>
                <div class="form-group">
                    <label for="positionTitle">Position Title *</label>
                    <input type="text" id="positionTitle" name="positionTitle" required
                           placeholder="e.g. Junior Developer">
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="status">Status</label>
                    <select id="status" name="status">
                        <option value="Saved">Saved</option>
                        <option value="Applied">Applied</option>
                        <option value="Interviewing">Interviewing</option>
                        <option value="Offer">Offer</option>
                        <option value="Rejected">Rejected</option>
                        <option value="Closed">Closed</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="applicationDate">Date</label>
                    <input type="date" id="applicationDate" name="applicationDate">
                </div>
            </div>

            <div class="form-group">
                <label for="jobUrl">Job URL</label>
                <input type="url" id="jobUrl" name="jobUrl"
                       placeholder="https://...">
            </div>

            <div class="form-group">
                <label for="notes">Notes</label>
                <textarea id="notes" name="notes"
                          placeholder="Any notes about this application..."></textarea>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Save Application</button>
                <a href="dashboard" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</div>

<footer class="footer">
    <p>© <%= java.time.Year.now() %> ApplyTrack — CST8288 Group Project</p>
</footer>

</body>
</html>