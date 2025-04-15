/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.web;

import com.entities.Users;
import com.interfaces.ReportInterface;
import java.io.IOException;
import java.io.PrintWriter;
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
public class ReportServlet extends HttpServlet {
    
    @EJB
    private ReportInterface reportBean;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/csv");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String fileName = "TaskReport_" + dateFormat.format(new Date()) + ".csv";
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        
        try (PrintWriter writer = response.getWriter()) {
            writer.println("Report Generated on: " + new Date());
            writer.println();
            
            writer.println("Task Statistics");
            writer.println("Total Tasks," + reportBean.countTotalTasks());
            writer.println("Not Started Tasks," + reportBean.countTasksByStatus("NOT_STARTED"));
            writer.println("In Progress Tasks," + reportBean.countTasksByStatus("IN_PROGRESS"));
            writer.println("Completed Tasks," + reportBean.countTasksByStatus("COMPLETED"));
            writer.println("Average Completion Time," + String.format("%.0fh %.0fm", reportBean.getAverageTaskCompletionTime(), (reportBean.getAverageTaskCompletionTime() % 1) * 60));
            writer.println();
            
            writer.println("Top Performing Staff");
            writer.println("Staff Name,Completed Tasks");
            List<Users> topStaff = reportBean.getTopPerformingUsers();
            for (Users staff : topStaff) {
                writer.println(staff.getFullName() + "," + reportBean.countTasksByUser(staff));
            }
        } catch (Exception e) {
            response.sendRedirect("viewReports.jsp");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long totalTasks = reportBean.countTotalTasks();
            long notStartedTasks = reportBean.countTasksByStatus("NOT_STARTED");
            long inProgressTasks = reportBean.countTasksByStatus("IN_PROGRESS");
            long completedTasks = reportBean.countTasksByStatus("COMPLETED");
            double avgCompletionTime = reportBean.getAverageTaskCompletionTime();
            List<Users> topStaff = reportBean.getTopPerformingUsers();

            request.setAttribute("totalTasks", totalTasks);
            request.setAttribute("notStartedTasks", notStartedTasks);
            request.setAttribute("inProgressTasks", inProgressTasks);
            request.setAttribute("completedTasks", completedTasks);
            request.setAttribute("avgCompletionTime", avgCompletionTime);
            request.setAttribute("topStaff", topStaff);
            request.setAttribute("reportBean", reportBean);

            request.getRequestDispatcher("viewReports.jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            response.sendRedirect("managerDashboard.jsp");
        }
    }
}