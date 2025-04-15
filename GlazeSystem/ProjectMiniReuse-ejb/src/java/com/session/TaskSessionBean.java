/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.session;

import com.entities.Tasks;
import com.entities.Users;
import com.interfaces.TaskInterface;
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
public class TaskSessionBean implements TaskInterface {

    @PersistenceContext(unitName = "ProjectMiniReuse-ejbPU")
    private EntityManager em;

    @Override
    public Tasks createTask(Tasks task) {
        em.persist(task);
        return task;
    }

    @Override
    public Tasks updateTask(Tasks task) {
        return em.merge(task);
    }

    @Override
    public void deleteTask(Integer taskId) {
        Tasks task = em.find(Tasks.class, taskId);
        if (task != null) {
            em.remove(task);
        }
    }

    @Override
    public Tasks getTaskById(Integer taskId) {
        return em.find(Tasks.class, taskId);
    }

    @Override
    public List<Tasks> getTasksByAssignedUser(Users user) {
        return em.createQuery("SELECT t FROM Tasks t WHERE t.assignedToId = :user", Tasks.class)
                 .setParameter("user", user)
                 .getResultList();
    }

    @Override
    public List<Tasks> getTasksByCreator(Users creator) {
        return em.createQuery("SELECT t FROM Tasks t WHERE t.creatorId = :creator", Tasks.class)
                 .setParameter("creator", creator)
                 .getResultList();
    }

    @Override
    public List<Tasks> getTasksByStatus(String status) {
        return em.createQuery("SELECT t FROM Tasks t WHERE t.status = :status", Tasks.class)
                 .setParameter("status", status)
                 .getResultList();
    }

    @Override
    public List<Tasks> getTasksByPriority(String priority) {
        return em.createQuery("SELECT t FROM Tasks t WHERE t.priority = :priority", Tasks.class)
                 .setParameter("priority", priority)
                 .getResultList();
    }

    @Override
    public List<Tasks> getTasksDueByDate(Date dueDate) {
        return em.createQuery("SELECT t FROM Tasks t WHERE t.dueDate = :dueDate", Tasks.class)
                 .setParameter("dueDate", dueDate)
                 .getResultList();
    }

    @Override
    public List<Tasks> getOverdueTasks() {
        Date currentDate = new Date();  // Get the current date
        return em.createQuery("SELECT t FROM Tasks t WHERE t.dueDate < :currentDate AND t.status != 'COMPLETED'", Tasks.class)
                 .setParameter("currentDate", currentDate)
                 .getResultList();
    }

    @Override
    public void updateTaskStatus(Integer taskId, String newStatus) {
        Tasks task = em.find(Tasks.class, taskId);
        if (task != null) {
            task.setStatus(newStatus);
            em.merge(task);
        }
    }
}
