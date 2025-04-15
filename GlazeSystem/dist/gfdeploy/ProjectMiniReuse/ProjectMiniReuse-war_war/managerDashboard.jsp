<%-- 
    Document   : managerDashboard
    Created on : Jan 4, 2025, 7:15:04 AM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>GLAZE Staff Management System - Manager Dashboard</title>
        <link rel="stylesheet" type="text/css" href="css/managerDashboard.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    </head>
    <body>
        <h1>Welcome, <%= session.getAttribute("fullName") %></h1>
        <div>
            <h2>GLAZE Manager Dashboard</h2>
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/ManageStaffServlet">
                        <i class="fas fa-users"></i> Manage Staff</a></li>
                    <li><a href="${pageContext.request.contextPath}/ManageTasksServlet">
                        <i class="fas fa-tasks"></i> Manage Tasks</a></li>
                    <li><a href="${pageContext.request.contextPath}/ManageShiftsServlet">
                        <i class="fas fa-calendar"></i> Manage Shifts</a></li>
                    <li><a href="${pageContext.request.contextPath}/ReportServlet">
                        <i class="fas fa-chart-bar"></i> View Reports</a></li>
                    <li><a href="LogoutServlet">
                        <i class="fas fa-sign-out-alt"></i> Logout</a></li>
                </ul>
            </nav>
        </div>
    </body>
</html>