/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LENOVO
 */
@Entity
@Table(name = "SHIFTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Shifts.findAll", query = "SELECT s FROM Shifts s"),
    @NamedQuery(name = "Shifts.findByShiftId", query = "SELECT s FROM Shifts s WHERE s.shiftId = :shiftId"),
    @NamedQuery(name = "Shifts.findByShiftDate", query = "SELECT s FROM Shifts s WHERE s.shiftDate = :shiftDate"),
    @NamedQuery(name = "Shifts.findByStartTime", query = "SELECT s FROM Shifts s WHERE s.startTime = :startTime"),
    @NamedQuery(name = "Shifts.findByEndTime", query = "SELECT s FROM Shifts s WHERE s.endTime = :endTime"),
    @NamedQuery(name = "Shifts.findByShiftType", query = "SELECT s FROM Shifts s WHERE s.shiftType = :shiftType"),
    @NamedQuery(name = "Shifts.findByCreatedAt", query = "SELECT s FROM Shifts s WHERE s.createdAt = :createdAt"),
    @NamedQuery(name = "Shifts.findByUpdatedAt", query = "SELECT s FROM Shifts s WHERE s.updatedAt = :updatedAt")})
public class Shifts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SHIFT_ID")
    private Integer shiftId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SHIFT_DATE")
    @Temporal(TemporalType.DATE)
    private Date shiftDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "START_TIME")
    @Temporal(TemporalType.TIME)
    private Date startTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "END_TIME")
    @Temporal(TemporalType.TIME)
    private Date endTime;
    @Size(max = 20)
    @Column(name = "SHIFT_TYPE")
    private String shiftType;
    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "UPDATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @JoinColumn(name = "CREATOR_ID", referencedColumnName = "USER_ID")
    @ManyToOne(optional = false)
    private Users creatorId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne(optional = false)
    private Users userId;

    public Shifts() {
    }

    public Shifts(Integer shiftId) {
        this.shiftId = shiftId;
    }

    public Shifts(Integer shiftId, Date shiftDate, Date startTime, Date endTime) {
        this.shiftId = shiftId;
        this.shiftDate = shiftDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getShiftId() {
        return shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    public Date getShiftDate() {
        return shiftDate;
    }

    public void setShiftDate(Date shiftDate) {
        this.shiftDate = shiftDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getShiftType() {
        return shiftType;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Users getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Users creatorId) {
        this.creatorId = creatorId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (shiftId != null ? shiftId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Shifts)) {
            return false;
        }
        Shifts other = (Shifts) object;
        if ((this.shiftId == null && other.shiftId != null) || (this.shiftId != null && !this.shiftId.equals(other.shiftId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Shifts[ shiftId=" + shiftId + " ]";
    }
    
}
