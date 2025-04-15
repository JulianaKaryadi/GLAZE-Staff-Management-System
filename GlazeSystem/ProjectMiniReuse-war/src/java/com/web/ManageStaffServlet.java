/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.web;

import com.entities.Users;
import com.exception.DataValidationException;
import com.exception.UserException;
import com.interfaces.UserInterface;
import java.io.IOException;
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
public class ManageStaffServlet extends HttpServlet {

    @EJB
    private UserInterface userBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "add":
                    addUser(request);
                    break;
                case "edit":
                    editUser(request);
                    break;
                case "delete":
                    deleteUser(request);
                    break;
                default:
                    throw new DataValidationException("Invalid action: " + action);
            }
            response.sendRedirect(request.getContextPath() + "/ManageStaffServlet");
        } catch (DataValidationException | UserException e) {
            handleError(request, response, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            handleError(request, response, "An unexpected error occurred. Please try again later.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Users> userList = userBean.getAllUsers();
            request.setAttribute("userList", userList);
            request.getRequestDispatcher("manageStaff.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            handleError(request, response, "Failed to load the user list.");
        }
    }

    private void addUser(HttpServletRequest request) throws DataValidationException, UserException {
        Users newUser = new Users();
        newUser.setUsername(request.getParameter("username"));
        newUser.setPassword(request.getParameter("password"));
        newUser.setFullName(request.getParameter("fullName"));
        newUser.setEmail(request.getParameter("email"));
        newUser.setRole(request.getParameter("role"));
        newUser.setIsActive(true);
        newUser.setCreatedAt(new Date());

        userBean.createUser(newUser);
    }

    private void editUser(HttpServletRequest request) throws DataValidationException, UserException {
        Integer userId = Integer.parseInt(request.getParameter("userId"));
        Users user = userBean.getUserById(userId);

        if (user == null) {
            throw new UserException("User not found with ID: " + userId);
        }

        user.setUsername(request.getParameter("username"));
        user.setFullName(request.getParameter("fullName"));
        user.setEmail(request.getParameter("email"));
        user.setRole(request.getParameter("role"));

        String newPassword = request.getParameter("password");
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            user.setPassword(newPassword);
        }

        userBean.updateUser(user);
    }

    private void deleteUser(HttpServletRequest request) throws DataValidationException, UserException {
        try {
            Integer userId = Integer.parseInt(request.getParameter("userId"));
            userBean.deleteUser(userId);
        } catch (NumberFormatException e) {
            throw new DataValidationException("Invalid user ID.");
        }
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws IOException, ServletException {
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("manageStaff.jsp").forward(request, response);
    }
}
