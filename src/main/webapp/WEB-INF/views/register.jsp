<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - ApplyTrack</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<nav class="navbar">
    <a href="dashboard" class="brand">&#128188; ApplyTrack</a>
</nav>

<div class="auth-wrapper">
    <div class="card">
        <h1>Create Account</h1>
        <p class="subtitle">Join ApplyTrack to organize your job search</p>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <form method="POST" action="register">
            <div class="form-group">
                <label for="fullName">Full Name</label>
                <input type="text" id="fullName" name="fullName" required
                       placeholder="e.g. Jane Doe"
                       value="${fullNameValue}">
            </div>
            <div class="form-group">
                <label for="email">College Email</label>
                <input type="email" id="email" name="email" required
                       placeholder="you@algonquincollege.com"
                       value="${emailValue}">
            </div>
            <div class="form-group">
                <label for="program">Program</label>
                <input type="text" id="program" name="program"
                       placeholder="e.g. Computer Programming"
                       value="${programValue}">
            </div>
            <div class="form-group">
                <label for="password">Password (min 6 characters)</label>
                <input type="password" id="password" name="password" required
                       minlength="6">
            </div>
            <button type="submit" class="btn btn-primary btn-full">Sign Up</button>
        </form>
    </div>
    <div class="auth-footer">
        Already have an account? <a href="login">Sign in</a>
    </div>
</div>

<footer class="footer">
    <p>© <%= java.time.Year.now() %> ApplyTrack — CST8288 Group Project</p>
</footer>

</body>
</html>