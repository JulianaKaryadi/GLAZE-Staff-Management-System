/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.session;

import com.entities.Shifts;
import com.entities.Users;
import com.interfaces.ShiftInterface;
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
public class ShiftSessionBean implements ShiftInterface {
    
    @PersistenceContext(unitName = "ProjectMiniReuse-ejbPU")
    private EntityManager em;

    @Override
    public Shifts createShift(Shifts shift) {
        em.persist(shift);
        return shift;
    }

    @Override
    public Shifts updateShift(Shifts shift) {
        return em.merge(shift);
    }

    @Override
    public void deleteShift(Integer shiftId) {
        Shifts shift = em.find(Shifts.class, shiftId);
        if (shift != null) {
            em.remove(shift);
        }
    }

    @Override
    public Shifts getShiftById(Integer shiftId) {
        return em.find(Shifts.class, shiftId);
    }

    @Override
    public List<Shifts> getAllShifts() {
        return em.createQuery("SELECT s FROM Shifts s", Shifts.class)
                 .getResultList();
    }

    @Override
    public List<Shifts> getShiftsByUser(Users user) {
        return em.createQuery("SELECT s FROM Shifts s WHERE s.userId = :user", Shifts.class)
                 .setParameter("user", user)
                 .getResultList();
    }

    @Override
    public List<Shifts> getShiftsByDateRange(Date startDate, Date endDate) {
        return em.createQuery("SELECT s FROM Shifts s WHERE s.shiftDate BETWEEN :startDate AND :endDate", Shifts.class)
                 .setParameter("startDate", startDate)
                 .setParameter("endDate", endDate)
                 .getResultList();
    }

    @Override
    public List<Shifts> getShiftsByUserAndDateRange(Users user, Date startDate, Date endDate) {
        return em.createQuery("SELECT s FROM Shifts s WHERE s.userId = :user AND s.shiftDate BETWEEN :startDate AND :endDate", Shifts.class)
                 .setParameter("user", user)
                 .setParameter("startDate", startDate)
                 .setParameter("endDate", endDate)
                 .getResultList();
    }
}