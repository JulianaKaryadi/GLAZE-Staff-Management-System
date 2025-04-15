/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.web;

import com.entities.TaskNotes;
import com.entities.Tasks;
import com.entities.Users;
import com.exception.TaskException;
import com.interfaces.TaskInterface;
import com.interfaces.TaskNoteInterface;
import com.interfaces.UserInterface;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
public class ManageTasksServlet extends HttpServlet {

    @EJB
    private TaskInterface taskBean;

    @EJB
    private UserInterface userBean;

    @EJB
    private TaskNoteInterface taskNoteBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("getDetails".equals(action)) {
                handleGetTaskDetails(request, response);
            } else {
                handleTaskList(request, response);
            }
        } catch (TaskException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error: " + e.getMessage());
        }
    }

    private void handleGetTaskDetails(HttpServletRequest request, HttpServletResponse response) throws TaskException, IOException {
        Integer taskId = Integer.valueOf(request.getParameter("taskId"));
        Tasks task = taskBean.getTaskById(taskId);
        List<TaskNotes> notes = taskNoteBean.getNotesForTask(task);

        StringBuilder html = new StringBuilder();
        html.append("<h3>").append(task.getTitle()).append("</h3>");
        html.append("<p><strong>Description:</strong> ").append(task.getDescription()).append("</p>");
        html.append("<p><strong>Status:</strong> ").append(task.getStatus()).append("</p>");
        html.append("<p><strong>Priority:</strong> ").append(task.getPriority()).append("</p>");
        html.append("<p><strong>Assigned To:</strong> ").append(task.getAssignedToId().getFullName()).append("</p>");
        html.append("<p><strong>Due Date:</strong> ").append(new SimpleDateFormat("yyyy-MM-dd").format(task.getDueDate())).append("</p>");

        html.append("<h4>Task Notes:</h4>");
        if (!notes.isEmpty()) {
            html.append("<ul>");
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (TaskNotes note : notes) {
                html.append("<li>")
                    .append("<strong>").append(note.getUserId().getFullName()).append("</strong> ")
                    .append("(").append(dateTimeFormat.format(note.getCreatedAt())).append("): ")
                    .append(note.getNoteContent())
                    .append("</li>");
            }
            html.append("</ul>");
        } else {
            html.append("<p>No notes available.</p>");
        }

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(html.toString());
    }

    private void handleTaskList(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        try {
            Integer userId = (Integer) request.getSession().getAttribute("userId");
            if (userId == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            Users creator = userBean.getUserById(userId);
            List<Tasks> taskList = taskBean.getTasksByCreator(creator);

            List<Users> staffList = userBean.getUsersByRole("STAFF");
            System.out.println("Number of staff found: " + (staffList != null ? staffList.size() : 0));

            request.setAttribute("taskList", taskList);
            request.setAttribute("staffList", staffList);

            request.getRequestDispatcher("/manageTasks.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("Error in handleTaskList: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Error handling task list", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        System.out.println("Received action: " + action);
        
        try {
            switch (action) {
                case "add":
                    System.out.println("Starting add task..."); // Debug log
                    addTask(request);
                    System.out.println("Task added successfully"); // Debug log
                    break;
                case "edit":
                    editTask(request);
                    break;
                case "delete":
                    deleteTask(request);
                    break;
            }
            response.sendRedirect(request.getContextPath() + "/ManageTasksServlet");
        } catch (TaskException e) {
            System.out.println("Error in task operation: " + e.getMessage()); // Debug log
            e.printStackTrace(); // This will print the full error stack trace
            response.sendRedirect(request.getContextPath() + "/ManageTasksServlet");
        }
    }

    private void addTask(HttpServletRequest request) throws TaskException {
        try {
            System.out.println("Adding new task with parameters:");
            System.out.println("Title: " + request.getParameter("title"));
            System.out.println("Description: " + request.getParameter("description"));
            System.out.println("AssignedToId: " + request.getParameter("assignedToId"));
            System.out.println("Priority: " + request.getParameter("priority"));
            System.out.println("DueDate: " + request.getParameter("dueDate"));

            Integer creatorId = (Integer) request.getSession().getAttribute("userId");
            System.out.println("Creator ID from session: " + creatorId);

            Tasks newTask = new Tasks();
            newTask.setTitle(request.getParameter("title"));
            newTask.setDescription(request.getParameter("description"));

            Integer assignedToId = Integer.valueOf(request.getParameter("assignedToId"));
            Users assignedUser = userBean.getUserById(assignedToId);
            if (assignedUser == null) {
                throw new TaskException("Assigned user not found");
            }
            newTask.setAssignedToId(assignedUser);

            Users creator = userBean.getUserById(creatorId);
            if (creator == null) {
                throw new TaskException("Creator not found");
            }
            newTask.setCreatorId(creator);

            newTask.setPriority(request.getParameter("priority").toUpperCase());
            newTask.setStatus("NOT_STARTED"); // Default status 

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dueDate = dateFormat.parse(request.getParameter("dueDate"));
            newTask.setDueDate(dueDate);

            Date now = new Date();
            newTask.setCreatedAt(now);
            newTask.setUpdatedAt(now);

            Tasks createdTask = taskBean.createTask(newTask);
            System.out.println("Task created with ID: " + createdTask.getTaskId());

        } catch (Exception e) {
            System.err.println("Error creating task: " + e.getMessage());
            e.printStackTrace();
            throw new TaskException("Error while adding the task: " + e.getMessage());
        }
    }

    private void editTask(HttpServletRequest request) throws TaskException {
        try {
            Integer taskId = Integer.valueOf(request.getParameter("taskId"));
            Tasks task = taskBean.getTaskById(taskId);

            if (task != null) {
                task.setTitle(request.getParameter("title"));
                task.setDescription(request.getParameter("description"));

                Integer assignedToId = Integer.valueOf(request.getParameter("assignedToId"));
                Users assignedUser = userBean.getUserById(assignedToId);
                task.setAssignedToId(assignedUser);

                task.setPriority(request.getParameter("priority"));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dueDate = dateFormat.parse(request.getParameter("dueDate"));
                task.setDueDate(dueDate);

                task.setUpdatedAt(new Date());

                taskBean.updateTask(task);
            }
        } catch (Exception e) {
            throw new TaskException("Error while editing the task", e);
        }
    }

    private void deleteTask(HttpServletRequest request) throws TaskException {
        try {
            Integer taskId = Integer.valueOf(request.getParameter("taskId"));
            taskBean.deleteTask(taskId);
        } catch (Exception e) {
            throw new TaskException("Error while deleting the task", e);
        }
    }
}
