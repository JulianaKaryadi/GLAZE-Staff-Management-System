<%-- 
    Document   : staffDashboard
    Created on : Jan 4, 2025, 7:15:51 AM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.entities.Tasks"%>
<%@page import="com.entities.TaskNotes"%>
<%@page import="java.util.List"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="com.interfaces.TaskInterface"%>
<%@page import="com.interfaces.TaskNoteInterface"%>
<%@page import="com.interfaces.UserInterface"%>
<%-- Add security check --%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>GLAZE Staff Dashboard</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="css/staffDashboard.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link href='https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.css' rel='stylesheet' />
        <script src='https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.js'></script>
    </head>
    <body>
        <h1>Welcome, <%= session.getAttribute("fullName") %></h1>
        <a href="LogoutServlet" class="logout-btn">Logout</a>
        
        <div class="list-container">
            <h2>My Tasks</h2>
            <div class="table-responsive">
                <table>
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th>Description</th>
                            <th>Due Date</th>
                            <th>Priority</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<Tasks> myTasks = (List<Tasks>) request.getAttribute("myTasks");
                            for (Tasks task : myTasks) {
                        %>
                        <tr>
                            <td><%= task.getTitle() %></td>
                            <td><%= task.getDescription() %></td>
                            <td><%= task.getDueDate() %></td>
                            <td class="priority-<%= task.getPriority().toLowerCase() %>">
                                <%= task.getPriority() %>
                            </td>
                            <td class="status-<%= task.getStatus().toLowerCase() %>">
                                <%= task.getStatus() %>
                            </td>
                            <td class="actions">
                                <button onclick="showUpdateForm(<%= task.getTaskId() %>, '<%= task.getStatus() %>')" 
                                        class="action-btn update-btn">
                                    <i class="fas fa-sync-alt"></i>
                                </button>
                                <button onclick="showNoteForm(<%= task.getTaskId() %>)" 
                                        class="action-btn note-btn">
                                    <i class="fas fa-sticky-note"></i>
                                </button>
                                <button onclick="viewNotes(<%= task.getTaskId() %>)" 
                                        class="action-btn view-notes-btn">
                                    <i class="fas fa-eye"></i>
                                </button>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
                    
        <!-- Shift Schedule Section -->
        <div class="list-container">
            <h2>Shifts Schedule</h2>
            <div id="calendar"></div>
        </div>

        <!-- Update Status Modal -->
        <div id="updateStatusModal" class="modal">
            <div class="modal-content">
                <h3>Update Task Status</h3>
                <form action="StaffTaskServlet" method="POST">
                    <input type="hidden" name="action" value="updateStatus">
                    <input type="hidden" name="taskId" id="updateTaskId">
                    <table>
                        <tr>
                            <td>Status:</td>
                            <td>
                                <select name="status" id="statusSelect" required>
                                    <option value="NOT_STARTED">Not Started</option>
                                    <option value="IN_PROGRESS">In Progress</option>
                                    <option value="COMPLETED">Completed</option>
                                </select>
                            </td>
                        </tr>
                    </table>
                    <div class="modal-buttons">
                        <input type="submit" value="Update">
                        <button type="button" onclick="hideUpdateForm()">Cancel</button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Add Note Modal -->
        <div id="noteModal" class="modal">
            <div class="modal-content">
                <h3>Add Note</h3>
                <form action="StaffTaskServlet" method="POST">
                    <input type="hidden" name="action" value="addNote">
                    <input type="hidden" name="taskId" id="noteTaskId">
                    <table>
                        <tr>
                            <td>Note:</td>
                            <td><textarea name="noteContent" rows="4" cols="50" required></textarea></td>
                        </tr>
                    </table>
                    <div class="modal-buttons">
                        <input type="submit" value="Add Note">
                        <button type="button" onclick="hideNoteForm()">Cancel</button>
                    </div>
                </form>
            </div>
        </div>

        <!-- View Notes Modal -->
        <div id="notesViewModal" class="modal">
            <div class="modal-content">
                <h3>Task Notes</h3>
                <div id="notesList"></div>
                <div class="modal-buttons">
                    <button onclick="hideNotes()">Close</button>
                </div>
            </div>
        </div>

        <script>
            // Get the modal elements
            const updateStatusModal = document.getElementById('updateStatusModal');
            const noteModal = document.getElementById('noteModal');
            const notesViewModal = document.getElementById('notesViewModal');

            // Function to show the update status form modal
            function showUpdateForm(taskId, currentStatus) {
                document.getElementById('updateTaskId').value = taskId;
                document.getElementById('statusSelect').value = currentStatus;
                updateStatusModal.style.display = 'block';
            }
            
            // Function to hide the update status form modal
            function hideUpdateForm() {
                updateStatusModal.style.display = 'none';
            }
            
            // Function to show the add note form modal
            function showNoteForm(taskId) {
                document.getElementById('noteTaskId').value = taskId;
                noteModal.style.display = 'block';
            }
            
            // Function to hide the add note form modal
            function hideNoteForm() {
                noteModal.style.display = 'none';
            }
            
            // Function to view notes in a modal
            async function viewNotes(taskId) {
                try {
                    const response = await fetch('StaffTaskServlet?action=getNotes&taskId=' + taskId);
                    const notes = await response.text();
                    document.getElementById('notesList').innerHTML = notes;
                    notesViewModal.style.display = 'block';
                } catch (error) {
                    console.error('Error:', error);
                }
            }
            
            // Function to hide the view notes modal
            function hideNotes() {
                notesViewModal.style.display = 'none';
            }

            // Close modals when clicking outside the modal content
            window.onclick = function(event) {
                if (event.target == updateStatusModal) {
                    updateStatusModal.style.display = 'none';
                } else if (event.target == noteModal) {
                    noteModal.style.display = 'none';
                } else if (event.target == notesViewModal) {
                    notesViewModal.style.display = 'none';
                }
            }

            // Calendar Initialization
            document.addEventListener('DOMContentLoaded', function() {
                var calendarEl = document.getElementById('calendar');
                var calendar = new FullCalendar.Calendar(calendarEl, {
                    initialView: 'timeGridWeek',
                    slotMinTime: '06:00:00',
                    slotMaxTime: '23:00:00',
                    headerToolbar: {
                        left: 'prev,next today',
                        center: 'title',
                        right: 'timeGridWeek,timeGridDay'
                    },
                    navLinks: true,
                    nowIndicator: true,
                    events: [
                        <c:forEach items="${shiftList}" var="shift" varStatus="loop">
                        {
                            title: '${shift.userId.fullName} (${shift.shiftType})',
                            start: '<fmt:formatDate value="${shift.shiftDate}" pattern="yyyy-MM-dd"/>T<fmt:formatDate value="${shift.startTime}" pattern="HH:mm:ss"/>',
                            end: '<fmt:formatDate value="${shift.shiftDate}" pattern="yyyy-MM-dd"/>T<fmt:formatDate value="${shift.endTime}" pattern="HH:mm:ss"/>',
                            backgroundColor: getShiftColor('${shift.shiftType}'),
                            display: '${shift.userId.userId == sessionScope.userId ? "auto" : "background"}'
                        }<c:if test="${!loop.last}">,</c:if>
                        </c:forEach>
                    ]
                });
                calendar.render();
            });

            function getShiftColor(shiftType) {
                switch(shiftType) {
                    case 'MORNING':
                        return '#4CAF50';  // Green
                    case 'AFTERNOON':
                        return '#2196F3';  // Blue
                    case 'EVENING':
                        return '#FFC107';  // Amber
                    case 'NIGHT':
                        return '#9C27B0';  // Purple
                    default:
                        return '#757575';  // Grey
                }
            };
        </script>
    </body>
</html>
