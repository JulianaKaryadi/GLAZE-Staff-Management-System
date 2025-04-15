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
public interface ReportInterface {
    // Complex reporting methods
    long countTotalTasks();
    long countTasksByStatus(String status);
    long countTasksByUser(Users user);
    
    // Performance and productivity metrics
    double getAverageTaskCompletionTime();
    List<Users> getTopPerformingUsers();
}
