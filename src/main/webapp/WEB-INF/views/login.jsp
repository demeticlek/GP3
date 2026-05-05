<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign In - ApplyTrack</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<nav class="navbar">
    <a href="dashboard" class="brand">&#128188; ApplyTrack</a>
</nav>

<div class="auth-wrapper">
    <div class="card">
        <h1>Sign In</h1>
        <p class="subtitle">Welcome back to ApplyTrack</p>

        <!-- Success message after registration -->
        <c:if test="${param.registered == 'true'}">
            <div class="alert alert-success">
                Account created successfully! Please sign in.
            </div>
        </c:if>

        <!-- Error message -->
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <form method="POST" action="login">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" required
                       placeholder="you@algonquincollege.com">
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
            </div>
            <button type="submit" class="btn btn-primary btn-full">Sign In</button>
        </form>
    </div>
    <div class="auth-footer">
        Don't have an account? <a href="register">Create one</a>
    </div>
</div>

<footer class="footer">
    <p>&copy; 2025 ApplyTrack &mdash; CST8288 Group Project</p>
</footer>

</body>
</html>