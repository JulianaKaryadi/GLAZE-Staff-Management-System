/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.interfaces;

import com.entities.Users;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LENOVO
 */
@Local
public interface UserInterface {
    // CRUD Operations for Users
    Users createUser(Users user);
    Users updateUser(Users user);
    void deleteUser(Integer userId);
    Users getUserById(Integer userId);
    Users getUserByUsername(String username);
    
    // Additional User-specific methods
    List<Users> getAllUsers();
    List<Users> getUsersByRole(String role);
    List<Users> getActiveUsers();
    
    // Authentication
    boolean authenticateUser(String username, String password);
}