/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.interfaces;

import com.entities.Tasks;
import com.entities.Users;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LENOVO
 */
@Local
public interface TaskInterface {
    // CRUD Operations for Tasks
    Tasks createTask(Tasks task);
    Tasks updateTask(Tasks task);
    void deleteTask(Integer taskId);
    Tasks getTaskById(Integer taskId);
    
    // Task-specific queries
    List<Tasks> getTasksByAssignedUser(Users user);
    List<Tasks> getTasksByCreator(Users creator);
    List<Tasks> getTasksByStatus(String status);
    List<Tasks> getTasksByPriority(String priority);
    
    // Date-based queries
    List<Tasks> getTasksDueByDate(Date dueDate);
    List<Tasks> getOverdueTasks();
    
    // Status update
    void updateTaskStatus(Integer taskId, String newStatus);
}
