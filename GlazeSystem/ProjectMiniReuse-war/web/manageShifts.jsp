<%-- 
    Document   : manageShifts
    Created on : Jan 21, 2025, 7:44:13 PM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>GLAZE Staff Management System - Manage Shifts</title>
    <link rel="stylesheet" type="text/css" href="css/manageShifts.css">
    <link href='https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.css' rel='stylesheet' />
</head>
<body>
    <div class="container mt-4">
        <h1>GLAZE Staff Shifts</h1>
        <a href="${pageContext.request.contextPath}/managerDashboard.jsp">Back to Dashboard</a>
        
        <!-- Add Shift Form -->
        <div class="card mb-4">
            <div class="card-header">
                <h5 class="card-title">Add New Shift</h5>
            </div>
            <div class="card-body">
                <form action="ManageShiftsServlet" method="POST">
                    <input type="hidden" name="action" value="add">
                    
                    <div class="row mb-3">
                        <div class="col-md-4">
                            <label class="form-label">Staff Member</label>
                            <select name="userId" class="form-select" required>
                                <c:forEach items="${staffList}" var="staff">
                                    <option value="${staff.userId}">${staff.fullName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <div class="col-md-4">
                            <label class="form-label">Shift Type</label>
                            <select name="shiftType" class="form-select" required>
                                <option value="MORNING">Morning</option>
                                <option value="AFTERNOON">Afternoon</option>
                                <option value="EVENING">Evening</option>
                                <option value="NIGHT">Night</option>
                            </select>
                        </div>
                    </div>
                    
                    <div class="row mb-3">
                        <div class="col-md-4">
                            <label class="form-label">Date</label>
                            <input type="date" name="shiftDate" class="form-control" required>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Start Time</label>
                            <input type="time" name="startTime" class="form-control" required>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">End Time</label>
                            <input type="time" name="endTime" class="form-control" required>
                        </div>
                    </div>
                    
                    <button type="submit" class="btn btn-primary">Add Shift</button>
                </form>
            </div>
        </div>
        
        <!-- Calendar View -->
        <div class="card">
            <div class="card-header">
                <h5 class="card-title">Shift Schedule</h5>
            </div>
            <div class="card-body">
                <div id="calendar"></div>
            </div>
        </div>
    </div>

    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src='https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.js'></script>
    <script>
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
                selectable: true,
                nowIndicator: true,
                events: [
                    <c:forEach items="${shiftList}" var="shift" varStatus="loop">
                    {
                        title: '${shift.userId.fullName} (${shift.shiftType})',
                        start: '<fmt:formatDate value="${shift.shiftDate}" pattern="yyyy-MM-dd"/>T<fmt:formatDate value="${shift.startTime}" pattern="HH:mm:ss"/>',
                        end: '<fmt:formatDate value="${shift.shiftDate}" pattern="yyyy-MM-dd"/>T<fmt:formatDate value="${shift.endTime}" pattern="HH:mm:ss"/>',
                        id: '${shift.shiftId}',
                        backgroundColor: getShiftColor('${shift.shiftType}')
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
        }
    </script>
</body>
</html>
