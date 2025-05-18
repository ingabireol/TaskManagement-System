<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Task Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .register-container {
            max-width: 500px;
            margin: 50px auto;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 5px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        .register-title {
            text-align: center;
            margin-bottom: 20px;
            color: #343a40;
        }
        .form-group {
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="register-container">
        <h2 class="register-title">Register</h2>

        <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger" role="alert">
            <%= request.getAttribute("error") %>
        </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/auth/register" method="post">
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" class="form-control <%= request.getAttribute("usernameError") != null ? "is-invalid" : "" %>"
                       id="username" name="username" value="${username}" required>
                <% if (request.getAttribute("usernameError") != null) { %>
                <div class="invalid-feedback">
                    <%= request.getAttribute("usernameError") %>
                </div>
                <% } %>
            </div>

            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" class="form-control <%= request.getAttribute("emailError") != null ? "is-invalid" : "" %>"
                       id="email" name="email" value="${email}" required>
                <% if (request.getAttribute("emailError") != null) { %>
                <div class="invalid-feedback">
                    <%= request.getAttribute("emailError") %>
                </div>
                <% } %>
            </div>

            <div class="form-group">
                <label for="fullName">Full Name</label>
                <input type="text" class="form-control <%= request.getAttribute("fullNameError") != null ? "is-invalid" : "" %>"
                       id="fullName" name="fullName" value="${fullName}" required>
                <% if (request.getAttribute("fullNameError") != null) { %>
                <div class="invalid-feedback">
                    <%= request.getAttribute("fullNameError") %>
                </div>
                <% } %>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control <%= request.getAttribute("passwordError") != null ? "is-invalid" : "" %>"
                       id="password" name="password" required>
                <% if (request.getAttribute("passwordError") != null) { %>
                <div class="invalid-feedback">
                    <%= request.getAttribute("passwordError") %>
                </div>
                <% } %>
            </div>

            <div class="form-group">
                <label for="confirmPassword">Confirm Password</label>
                <input type="password" class="form-control <%= request.getAttribute("confirmPasswordError") != null ? "is-invalid" : "" %>"
                       id="confirmPassword" name="confirmPassword" required>
                <% if (request.getAttribute("confirmPasswordError") != null) { %>
                <div class="invalid-feedback">
                    <%= request.getAttribute("confirmPasswordError") %>
                </div>
                <% } %>
            </div>

            <div class="d-grid gap-2">
                <button type="submit" class="btn btn-primary">Register</button>
            </div>
        </form>

        <hr>
        <div class="text-center">
            <p>Already have an account? <a href="${pageContext.request.contextPath}/auth/login">Login</a></p>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>