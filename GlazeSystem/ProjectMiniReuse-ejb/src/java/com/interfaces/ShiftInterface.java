/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.interfaces;

import com.entities.Shifts;
import com.entities.Users;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
/**
 *
 * @author LENOVO
 */
@Local
public interface ShiftInterface {
    Shifts createShift(Shifts shift);
    Shifts updateShift(Shifts shift);
    void deleteShift(Integer shiftId);
    Shifts getShiftById(Integer shiftId);
    
    List<Shifts> getAllShifts();
    List<Shifts> getShiftsByUser(Users user);
    List<Shifts> getShiftsByDateRange(Date startDate, Date endDate);
    List<Shifts> getShiftsByUserAndDateRange(Users user, Date startDate, Date endDate);
}
