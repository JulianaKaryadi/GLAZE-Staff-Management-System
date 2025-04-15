<%-- 
    Document   : viewReports
    Created on : Jan 4, 2025, 7:43:26 AM
    Author     : LENOVO
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.entities.Users"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="com.interfaces.ReportInterface"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>GLAZE Staff Management System - Task Reports</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="css/viewReports.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    </head>
    <body>
        <h1>GLAZE Task Reports</h1>
        <a href="managerDashboard.jsp">Back to Dashboard</a>

        <!-- Task Statistics -->
        <div class="report-container">
            <h2>Task Statistics</h2>
            <div class="table-responsive">
                <table>
                    <tr>
                        <th>Total Tasks</th>
                        <td>${totalTasks}</td>
                    </tr>
                    <tr>
                        <th>Not Started Tasks</th>
                        <td>${notStartedTasks}</td>
                    </tr>
                    <tr>
                        <th>In Progress Tasks</th>
                        <td>${inProgressTasks}</td>
                    </tr>
                    <tr>
                        <th>Completed Tasks</th>
                        <td>${completedTasks}</td>
                    </tr>
                    <tr>
                        <th>Average Completion Time (Hours and Minutes)</th>
                        <td>${String.format("%.2f", avgCompletionTime)}</td>
                    </tr>
                </table>
            </div>
        </div>
        
        <!-- Top Performing Staff -->
        <div class="report-container">
            <h2>Top Performing Staff</h2>
            <div class="table-responsive">
                <table>
                    <thead>
                        <tr>
                            <th>Staff Name</th>
                            <th>Completed Tasks</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<Users> topStaff = (List<Users>) request.getAttribute("topStaff");
                            ReportInterface reportBean = (ReportInterface) request.getAttribute("reportBean");
                            for (Users staff : topStaff) {
                        %>
                        <tr>
                            <td><%= staff.getFullName() %></td>
                            <td><%= reportBean.countTasksByUser(staff) %></td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
        
        <!-- Generate Detailed Report -->
        <div class="report-container">
            <h2>Generate Detailed Report</h2>
            <form action="ReportServlet" method="POST">
                <input type="submit" value="Download Detailed Report">
            </form>
        </div>
    </body>
</html>