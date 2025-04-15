<%-- 
    Document: manageStaff 
    Created on: Jan 4, 2025, 7:19:30 AM 
    Author: LENOVO 
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GLAZE Staff Management System - Manage Staff</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="css/manageStaff.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    </head>
    <body>
        <h1>GLAZE Manage Staff</h1>
        <a href="${pageContext.request.contextPath}/managerDashboard.jsp">Back to Dashboard</a>

        <!-- Add Staff Form -->
        <div>
            <h2>Add New Staff</h2>
            <form action="${pageContext.request.contextPath}/ManageStaffServlet" method="POST">
                <input type="hidden" name="action" value="add">
                <table>
                    <tr>
                        <td>Username:</td>
                        <td><input type="text" name="username" required></td>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td><input type="password" name="password" required></td>
                    </tr>
                    <tr>
                        <td>Full Name:</td>
                        <td><input type="text" name="fullName" required></td>
                    </tr>
                    <tr>
                        <td>Email:</td>
                        <td><input type="email" name="email" required></td>
                    </tr>
                    <tr>
                        <td>Role:</td>
                        <td>
                            <select name="role" required>
                                <option value="STAFF">Staff</option>
                                <option value="MANAGER">Manager</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" value="Add Staff">
                        </td>
                    </tr>
                </table>
            </form>
        </div>

        <!-- User List and Management -->
        <div>
            <h2>Staff List</h2>
            <table border="1">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Full Name</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${not empty userList}">
                        <c:forEach var="user" items="${userList}">
                            <tr>
                                <td>${user.userId}</td>
                                <td>${user.username}</td>
                                <td>${user.fullName}</td>
                                <td>${user.email}</td>
                                <td>${user.role}</td>
                                <td>
                                    <button onclick="showEditForm(${user.userId}, '${user.username}', '${user.fullName}', '${user.email}', '${user.role}')">Edit</button>
                                    <form action="${pageContext.request.contextPath}/ManageStaffServlet" method="POST" style="display: inline;">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="userId" value="${user.userId}">
                                        <input type="submit" value="Delete" onclick="return confirm('Are you sure?')">
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty userList}">
                        <tr>
                            <td colspan="6">No users found.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>

        <!-- Hidden Edit Form -->
        <div id="editForm" style="display: none;">
            <h2>Edit User</h2>
            <form action="${pageContext.request.contextPath}/ManageStaffServlet" method="POST">
                <input type="hidden" name="action" value="edit">
                <input type="hidden" name="userId" id="editUserId">
                <table>
                    <tr>
                        <td>Username:</td>
                        <td><input type="text" name="username" id="editUsername" required></td>
                    </tr>
                    <tr>
                        <td>Full Name:</td>
                        <td><input type="text" name="fullName" id="editFullName" required></td>
                    </tr>
                    <tr>
                        <td>Email:</td>
                        <td><input type="email" name="email" id="editEmail" required></td>
                    </tr>
                    <tr>
                        <td>Role:</td>
                        <td>
                            <select name="role" id="editRole" required>
                                <option value="STAFF">Staff</option>
                                <option value="MANAGER">Manager</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>New Password:</td>
                        <td><input type="password" name="password" placeholder="Leave blank to keep current"></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" value="Update">
                            <button type="button" onclick="hideEditForm()">Cancel</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>

        <script>
            function showEditForm(userId, username, fullName, email, role) {
                document.getElementById('editForm').style.display = 'block';
                document.getElementById('editUserId').value = userId;
                document.getElementById('editUsername').value = username;
                document.getElementById('editFullName').value = fullName;
                document.getElementById('editEmail').value = email;
                document.getElementById('editRole').value = role;
            }

            function hideEditForm() {
                document.getElementById('editForm').style.display = 'none';
            }
        </script>
    </body>
</html>
