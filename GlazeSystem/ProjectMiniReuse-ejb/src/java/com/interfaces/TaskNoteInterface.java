/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.interfaces;

import com.entities.TaskNotes;
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
public interface TaskNoteInterface {
    // CRUD Operations for Task Notes
    TaskNotes createTaskNote(TaskNotes taskNote);
    void deleteTaskNote(Integer noteId);
    TaskNotes getTaskNoteById(Integer noteId);
    
    // Task Note-specific queries
    List<TaskNotes> getNotesForTask(Tasks task);
    List<TaskNotes> getNotesByUser(Users user);
    List<TaskNotes> getNotesByDateRange(Date startDate, Date endDate);
}
