/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.web;

import com.entities.Shifts;
import com.entities.TaskNotes;
import com.entities.Tasks;
import com.entities.Users;
import com.exception.TaskException;
import com.exception.UserException;
import com.interfaces.ShiftInterface;
import com.interfaces.TaskInterface;
import com.interfaces.TaskNoteInterface;
import com.interfaces.UserInterface;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author LENOVO
 */
public class StaffTaskServlet extends HttpServlet {
    
    @EJB
    private TaskInterface taskBean;
    
    @EJB
    private TaskNoteInterface noteBean;
    
    @EJB
    private UserInterface userBean;
    
    @EJB
    private ShiftInterface shiftBean; 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("getNotes".equals(action)) {
            Integer taskId = Integer.valueOf(request.getParameter("taskId"));
            Tasks task = taskBean.getTaskById(taskId);
            List<TaskNotes> notes = noteBean.getNotesForTask(task);
            StringBuilder html = new StringBuilder();
            if (!notes.isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                html.append("<ul>");
                for (TaskNotes note : notes) {
                    html.append("<li>")
                            .append("<strong>").append(dateFormat.format(note.getCreatedAt())).append("</strong><br>")
                            .append(note.getNoteContent())
                            .append("</li>");
                }
                html.append("</ul>");
            } else {
                html.append("<p>No notes available.</p>");
            }
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(html.toString());
        } else if (action == null) {
            try {
                Integer userId = (Integer) request.getSession().getAttribute("userId");
                Users user = userBean.getUserById(userId);

                // Get tasks
                List<Tasks> myTasks = taskBean.getTasksByAssignedUser(user);
                request.setAttribute("myTasks", myTasks);

                // Get shifts for a date range
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.WEEK_OF_YEAR, -1); 
                Date startDate = cal.getTime();

                cal.add(Calendar.WEEK_OF_YEAR, 2);  // Two weeks forward
                Date endDate = cal.getTime();

                List<Shifts> shiftList = shiftBean.getShiftsByDateRange(startDate, endDate);
                request.setAttribute("shiftList", shiftList);

                request.getRequestDispatcher("staffDashboard.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            switch(action) {
                case "updateStatus":
                    updateTaskStatus(request);
                    break;
                case "addNote":
                    addTaskNote(request);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid action: " + action);
            }
            
            response.sendRedirect(request.getContextPath() + "/StaffTaskServlet");
            
        } catch (TaskException | UserException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (IOException | IllegalArgumentException e) {
            response.sendRedirect(request.getContextPath() + "/StaffTaskServlet");
        }
    }
    
    private void updateTaskStatus(HttpServletRequest request) throws TaskException {
        Integer taskId = Integer.valueOf(request.getParameter("taskId"));
        String newStatus = request.getParameter("status");
        
        Tasks task = taskBean.getTaskById(taskId);
        if (task != null) {
            task.setStatus(newStatus);
            task.setUpdatedAt(new Date());
            
            if ("COMPLETED".equals(newStatus)) {
                task.setCompletedAt(new Date());
            }
            
            taskBean.updateTask(task);
        } else {
            throw new TaskException("Task not found");
        }
    }
    
    private void addTaskNote(HttpServletRequest request) throws TaskException, UserException {
        TaskNotes note = new TaskNotes();
        
        Integer taskId = Integer.valueOf(request.getParameter("taskId"));
        Tasks task = taskBean.getTaskById(taskId);
        if (task == null) {
            throw new TaskException("Task not found");
        }
        note.setTaskId(task);
        
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        Users user = userBean.getUserById(userId);
        if (user == null) {
            throw new UserException("User not found");
        }
        note.setUserId(user);
        
        note.setNoteContent(request.getParameter("noteContent"));
        note.setCreatedAt(new Date());
        
        noteBean.createTaskNote(note);
    }
}
