<%-- 
    Document   : manageTasks
    Created on : Jan 4, 2025
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.entities.Tasks"%>
<%@page import="com.entities.Users"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>GLAZE Staff Management System - Manage Tasks</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="css/manageTasks.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    </head>
    <body>
        <h1>GLAZE Manage Staff Tasks</h1>
        <a href="managerDashboard.jsp">Back to Dashboard</a>

        <!-- Add Task Form -->
        <div class="form-container">
            <h2>Add New Task</h2>
            <form action="${pageContext.request.contextPath}/ManageTasksServlet" method="POST">
                <input type="hidden" name="action" value="add">
                <table>
                    <tr>
                        <td>Title:</td>
                        <td><input type="text" name="title" required></td>
                    </tr>
                    <tr>
                        <td>Description:</td>
                        <td><textarea name="description" required></textarea></td>
                    </tr>
                    <tr>
                        <td>Assign To:</td>
                        <td>
                            <select name="assignedToId" required>
                                <%
                                    List<Users> staffList = (List<Users>) request.getAttribute("staffList");
                                    if (staffList != null && !staffList.isEmpty()) {
                                        for (Users staff : staffList) {
                                %>
                                    <option value="<%= staff.getUserId() %>"><%= staff.getFullName() %></option>
                                <%
                                        }
                                    } else {
                                %>
                                    <option value="">No staff available</option>
                                <%
                                    }
                                %>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Priority:</td>
                        <td>
                            <select name="priority" required>
                                <option value="HIGH">High</option>
                                <option value="MEDIUM">Medium</option>
                                <option value="LOW">Low</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Due Date:</td>
                        <td><input type="date" name="dueDate" required></td>
                    </tr>
                    <tr>
                        <td colspan="2" class="button-cell">
                            <input type="submit" value="Add Task">
                        </td>
                    </tr>
                </table>
            </form>
        </div>

        <!-- Task List -->
        <div class="list-container">
            <h2>Task List</h2>
            <div class="table-responsive">
                <table border="1">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Title</th>
                            <th>Assigned To</th>
                            <th>Priority</th>
                            <th>Due Date</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<Tasks> taskList = (List<Tasks>) request.getAttribute("taskList");
                            if(taskList != null) {
                                for (Tasks task : taskList) {
                        %>
                        <tr>
                            <td><%= task.getTaskId() %></td>
                            <td><%= task.getTitle() %></td>
                            <td><%= task.getAssignedToId().getFullName() %></td>
                            <td class="priority-<%= task.getPriority().toLowerCase() %>">
                                <%= task.getPriority() %>
                            </td>
                            <td><%= task.getDueDate() %></td>
                            <td class="status-<%= task.getStatus().toLowerCase() %>">
                                <%= task.getStatus() %>
                            </td>
                            <td class="actions">
                                <button onclick="showTaskDetails('<%= task.getTaskId() %>')" 
                                        class="view-btn">
                                    <i class="fas fa-eye"></i> View
                                </button>
                                <button onclick="showEditForm(
                                    '<%= task.getTaskId() %>',
                                    '<%= task.getTitle() %>',
                                    '<%= task.getDescription() %>',
                                    '<%= task.getAssignedToId().getUserId() %>',
                                    '<%= task.getPriority() %>',
                                    '<%= task.getDueDate() %>')" 
                                    class="edit-btn">
                                    <i class="fas fa-edit"></i> Edit
                                </button>
                                <form action="${pageContext.request.contextPath}/ManageTasksServlet" 
                                      method="POST" style="display: inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="taskId" value="<%= task.getTaskId() %>">
                                    <button type="submit" class="delete-btn" 
                                            onclick="return confirm('Are you sure you want to delete this task?')">
                                        <i class="fas fa-trash"></i> Delete
                                    </button>
                                </form>
                            </td>
                        </tr>
                        <% 
                                }
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Task Details Modal -->
        <div id="modal-overlay" class="modal-overlay" style="display: none;"></div>
        <div id="taskDetailsModal" class="modal">
            <div class="modal-content">
                <h2>Task Details</h2>
                <div id="taskDetails"></div>
                <button onclick="hideTaskDetails()" class="close-btn">
                    <i class="fas fa-times"></i> Close
                </button>
            </div>
        </div>

        <!-- Edit Task Modal -->
        <div id="editForm" class="modal">
            <div class="modal-content">
                <h2>Edit Task</h2>
                <form action="${pageContext.request.contextPath}/ManageTasksServlet" method="POST">
                    <input type="hidden" name="action" value="edit">
                    <input type="hidden" name="taskId" id="editTaskId">
                    <table>
                        <tr>
                            <td>Title:</td>
                            <td><input type="text" name="title" id="editTitle" required></td>
                        </tr>
                        <tr>
                            <td>Description:</td>
                            <td>
                                <textarea name="description" id="editDescription" required></textarea>
                            </td>
                        </tr>
                        <tr>
                            <td>Assign To:</td>
                            <td>
                                <select name="assignedToId" id="editAssignedToId" required>
                                    <% if(staffList != null) {
                                        for (Users staff : staffList) { %>
                                        <option value="<%= staff.getUserId() %>">
                                            <%= staff.getFullName() %>
                                        </option>
                                    <% }
                                    } %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>Priority:</td>
                            <td>
                                <select name="priority" id="editPriority" required>
                                    <option value="HIGH">High</option>
                                    <option value="MEDIUM">Medium</option>
                                    <option value="LOW">Low</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>Due Date:</td>
                            <td><input type="date" name="dueDate" id="editDueDate" required></td>
                        </tr>
                        <tr>
                            <td colspan="2" class="button-cell">
                                <input type="submit" value="Update Task">
                                <button type="button" onclick="hideEditForm()" class="cancel-btn">
                                    Cancel
                                </button>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>

        <script>
            function showTaskDetails(taskId) {
                document.getElementById('modal-overlay').style.display = 'block';
                document.getElementById('taskDetailsModal').style.display = 'block';
                fetch('${pageContext.request.contextPath}/ManageTasksServlet?action=getDetails&taskId=' + taskId)
                    .then(response => response.text())
                    .then(data => {
                        document.getElementById('taskDetails').innerHTML = data;
                    })
                    .catch(error => console.error('Error:', error));
            }

            function hideTaskDetails() {
                document.getElementById('modal-overlay').style.display = 'none';
                document.getElementById('taskDetailsModal').style.display = 'none';
            }

            function showEditForm(taskId, title, description, assignedToId, priority, dueDate) {
                document.getElementById('modal-overlay').style.display = 'block';
                document.getElementById('editForm').style.display = 'block';
                document.getElementById('editTaskId').value = taskId;
                document.getElementById('editTitle').value = title;
                document.getElementById('editDescription').value = description;
                document.getElementById('editAssignedToId').value = assignedToId;
                document.getElementById('editPriority').value = priority;
                document.getElementById('editDueDate').value = dueDate;
            }

            function hideEditForm() {
                document.getElementById('modal-overlay').style.display = 'none';
                document.getElementById('editForm').style.display = 'none';
            }

            // Close modals when clicking outside
            document.getElementById('modal-overlay').addEventListener('click', function() {
                hideTaskDetails();
                hideEditForm();
            });
        </script>
    </body>
</html>