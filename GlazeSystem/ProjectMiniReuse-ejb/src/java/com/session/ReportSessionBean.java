/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.session;

import com.entities.Tasks;
import com.entities.Users;
import com.interfaces.ReportInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author LENOVO
 */
@Stateless
public class ReportSessionBean implements ReportInterface {

    @PersistenceContext(unitName = "ProjectMiniReuse-ejbPU")
    private EntityManager em;

    @Override
    public long countTotalTasks() {
        return em.createQuery("SELECT COUNT(t) FROM Tasks t", Long.class)
                 .getSingleResult();
    }

    @Override
    public long countTasksByStatus(String status) {
        return em.createQuery("SELECT COUNT(t) FROM Tasks t WHERE t.status = :status", Long.class)
                 .setParameter("status", status)
                 .getSingleResult();
    }

    @Override
    public long countTasksByUser(Users user) {
        return em.createQuery("SELECT COUNT(t) FROM Tasks t WHERE t.assignedToId = :user", Long.class)
                 .setParameter("user", user)
                 .getSingleResult();
    }

    @Override
    public double getAverageTaskCompletionTime() {
        List<Tasks> tasks = em.createQuery(
            "SELECT t FROM Tasks t WHERE t.completedAt IS NOT NULL", 
            Tasks.class
        ).getResultList();

        if (tasks.isEmpty()) {
            return 0.0;
        }

        long totalTime = 0;
        for (Tasks task : tasks) {
            long diff = task.getCompletedAt().getTime() - task.getCreatedAt().getTime();
            totalTime += diff;
        }

        double avgTimeInMillis = (double) totalTime / tasks.size();

        double avgTimeInHours = avgTimeInMillis / (60 * 60 * 1000);

        return avgTimeInHours;
    }

    @Override
    public List<Users> getTopPerformingUsers() {
        return em.createQuery(
            "SELECT t.assignedToId FROM Tasks t WHERE t.status = 'COMPLETED' GROUP BY t.assignedToId ORDER BY COUNT(t) DESC", 
            Users.class
        ).setMaxResults(5)
         .getResultList();
    }
    
}
