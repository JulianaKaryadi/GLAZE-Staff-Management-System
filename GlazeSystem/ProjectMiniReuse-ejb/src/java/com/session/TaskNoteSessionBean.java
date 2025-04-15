/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.session;

import com.entities.TaskNotes;
import com.entities.Tasks;
import com.entities.Users;
import com.interfaces.TaskNoteInterface;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author LENOVO
 */
@Stateless
public class TaskNoteSessionBean implements TaskNoteInterface {

    @PersistenceContext(unitName = "ProjectMiniReuse-ejbPU")
    private EntityManager em;

    @Override
    public TaskNotes createTaskNote(TaskNotes taskNote) {
        em.persist(taskNote);
        return taskNote;
    }

    @Override
    public void deleteTaskNote(Integer noteId) {
        TaskNotes taskNote = em.find(TaskNotes.class, noteId);
        if (taskNote != null) {
            em.remove(taskNote);
        }
    }

    @Override
    public TaskNotes getTaskNoteById(Integer noteId) {
        return em.find(TaskNotes.class, noteId);
    }

    @Override
    public List<TaskNotes> getNotesForTask(Tasks task) {
        return em.createQuery("SELECT tn FROM TaskNotes tn WHERE tn.taskId = :task", TaskNotes.class)
                 .setParameter("task", task)
                 .getResultList();
    }

    @Override
    public List<TaskNotes> getNotesByUser(Users user) {
        return em.createQuery("SELECT tn FROM TaskNotes tn WHERE tn.userId = :user", TaskNotes.class)
                 .setParameter("user", user)
                 .getResultList();
    }

    @Override
    public List<TaskNotes> getNotesByDateRange(Date startDate, Date endDate) {
        return em.createQuery("SELECT tn FROM TaskNotes tn WHERE tn.createdAt BETWEEN :startDate AND :endDate", TaskNotes.class)
                 .setParameter("startDate", startDate)
                 .setParameter("endDate", endDate)
                 .getResultList();
    }
}
