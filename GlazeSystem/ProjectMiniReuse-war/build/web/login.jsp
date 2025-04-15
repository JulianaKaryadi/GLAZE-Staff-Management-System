<%-- 
    Document   : login
    Created on : Jan 4, 2025, 7:12:09 AM
    Author     : LENOVO
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>GLAZE Staff Management System - Login</title>
        <link rel="stylesheet" type="text/css" href="css/login.css">
        <!-- Add Font Awesome for icons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    </head>
    <body>
        <!-- Decorative elements -->
        <div class="decoration"></div>
        <div class="decoration"></div>
        
        <div class="login-container">
            <h1>GLAZE Staff Management System</h1>
            
            <%-- Display error message if present --%>
            <% if (request.getAttribute("error") != null) { %>
                <p class="error-message">
                    <i class="fas fa-exclamation-circle"></i>
                    <%= request.getAttribute("error") %>
                </p>
            <% } %>
            
            <form action="LoginServlet" method="POST" class="login-form">
                <div class="form-group">
                    <label for="username">Username</label>
                    <div class="input-group">
                        <i class="fas fa-user"></i>
                        <input type="text" id="username" name="username" required 
                               placeholder="Enter your username">
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="password">Password</label>
                    <div class="input-group">
                        <i class="fas fa-lock"></i>
                        <input type="password" id="password" name="password" required 
                               placeholder="Enter your password">
                        <i class="fas fa-eye-slash password-toggle" onclick="togglePassword()"></i>
                    </div>
                </div>
                
                <div class="form-group">
                    <button type="submit" class="login-btn">
                        <span>Login</span>
                        <i class="fas fa-sign-in-alt"></i>
                    </button>
                </div>
            </form>
        </div>

        <!-- Add JavaScript for password toggle functionality -->
        <script>
            function togglePassword() {
                const passwordInput = document.getElementById('password');
                const toggleIcon = document.querySelector('.password-toggle');
                
                if (passwordInput.type === 'password') {
                    passwordInput.type = 'text';
                    toggleIcon.classList.remove('fa-eye-slash');
                    toggleIcon.classList.add('fa-eye');
                } else {
                    passwordInput.type = 'password';
                    toggleIcon.classList.remove('fa-eye');
                    toggleIcon.classList.add('fa-eye-slash');
                }
            }
        </script>
    </body>
</html>