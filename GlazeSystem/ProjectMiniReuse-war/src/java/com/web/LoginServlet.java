/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.web;

import com.entities.Users;
import com.exception.AuthenticationException;
import com.interfaces.UserInterface;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author LENOVO
 */
public class LoginServlet extends HttpServlet {
    
    @EJB
    private UserInterface userBean;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("LoginServlet doPost called");
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        System.out.println("Login attempt - Username: " + username); 
        
        try {
            if (userBean.authenticateUser(username, password)) {
                Users user = userBean.getUserByUsername(username);
                HttpSession session = request.getSession();
                
                System.out.println("User authenticated successfully"); 
                System.out.println("User role: " + user.getRole()); 
                
                // Store user information in session
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("username", user.getUsername());
                session.setAttribute("role", user.getRole());
                session.setAttribute("fullName", user.getFullName());
                
                // Redirect based on role
                if ("MANAGER".equals(user.getRole())) {
                    System.out.println("Redirecting to manager dashboard"); 
                    response.sendRedirect("managerDashboard.jsp");
                } else if ("STAFF".equals(user.getRole())) {
                    System.out.println("Redirecting to staff dashboard"); 
                    response.sendRedirect(request.getContextPath() + "/StaffTaskServlet");
                }
            } else {
                throw new AuthenticationException("Invalid username or password");
            }
        } catch (AuthenticationException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (Exception e) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, "Failed to initialize login service", e);
        }
    }
}