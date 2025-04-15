package com.web;

import com.entities.Shifts;
import com.entities.Users;
import com.interfaces.ShiftInterface;
import com.interfaces.UserInterface;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ManageShiftsServlet extends HttpServlet {
    
    @EJB
    private ShiftInterface shiftBean;
    
    @EJB
    private UserInterface userBean;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("userId");
            
            // Get all staff members
            List<Users> staffList = userBean.getUsersByRole("STAFF");
            request.setAttribute("staffList", staffList);
            
            // Get date range for a broader period (e.g., 3 months)
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1); // One month back
            Date startDate = cal.getTime();
            
            cal.add(Calendar.MONTH, 3);  // Three months forward
            Date endDate = cal.getTime();
            
            // Get all shifts for the date range
            List<Shifts> shiftList = shiftBean.getShiftsByDateRange(startDate, endDate);
            request.setAttribute("shiftList", shiftList);
            
            // Forward the request to the JSP page
            request.getRequestDispatcher("/manageShifts.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            switch (action) {
                case "add":
                    addShift(request);
                    break;
                case "edit":
                    editShift(request);
                    break;
                case "delete":
                    deleteShift(request);
                    break;
            }
            response.sendRedirect(request.getContextPath() + "/ManageShiftsServlet");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
    
    private void addShift(HttpServletRequest request) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        
        Shifts shift = new Shifts();
        
        // Set user
        Integer staffId = Integer.valueOf(request.getParameter("userId"));
        Users staff = userBean.getUserById(staffId);
        shift.setUserId(staff);
        
        // Set creator
        Integer creatorId = (Integer) request.getSession().getAttribute("userId");
        Users creator = userBean.getUserById(creatorId);
        shift.setCreatorId(creator);
        
        // Set dates and times
        shift.setShiftDate(dateFormat.parse(request.getParameter("shiftDate")));
        shift.setStartTime(timeFormat.parse(request.getParameter("startTime")));
        shift.setEndTime(timeFormat.parse(request.getParameter("endTime")));
        
        shift.setShiftType(request.getParameter("shiftType"));
        shift.setCreatedAt(new Date());
        shift.setUpdatedAt(new Date());
        
        shiftBean.createShift(shift);
    }
    
    private void editShift(HttpServletRequest request) throws ParseException {
        Integer shiftId = Integer.valueOf(request.getParameter("shiftId"));
        Shifts shift = shiftBean.getShiftById(shiftId);
        
        if (shift != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            
            Integer staffId = Integer.valueOf(request.getParameter("userId"));
            Users staff = userBean.getUserById(staffId);
            shift.setUserId(staff);
            
            shift.setShiftDate(dateFormat.parse(request.getParameter("shiftDate")));
            shift.setStartTime(timeFormat.parse(request.getParameter("startTime")));
            shift.setEndTime(timeFormat.parse(request.getParameter("endTime")));
            shift.setShiftType(request.getParameter("shiftType"));
            shift.setUpdatedAt(new Date());
            
            shiftBean.updateShift(shift);
        }
    }
    
    private void deleteShift(HttpServletRequest request) {
        Integer shiftId = Integer.valueOf(request.getParameter("shiftId"));
        shiftBean.deleteShift(shiftId);
    }
}
