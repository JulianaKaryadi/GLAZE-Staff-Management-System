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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LENOVO
 */
@Entity
@Table(name = "TASK_NOTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TaskNotes.findAll", query = "SELECT t FROM TaskNotes t"),
    @NamedQuery(name = "TaskNotes.findByNoteId", query = "SELECT t FROM TaskNotes t WHERE t.noteId = :noteId"),
    @NamedQuery(name = "TaskNotes.findByCreatedAt", query = "SELECT t FROM TaskNotes t WHERE t.createdAt = :createdAt")})
public class TaskNotes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "NOTE_ID")
    private Integer noteId;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "NOTE_CONTENT")
    private String noteContent;
    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "TASK_ID", referencedColumnName = "TASK_ID")
    @ManyToOne(optional = false)
    private Tasks taskId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne(optional = false)
    private Users userId;

    public TaskNotes() {
    }

    public TaskNotes(Integer noteId) {
        this.noteId = noteId;
    }

    public TaskNotes(Integer noteId, String noteContent) {
        this.noteId = noteId;
        this.noteContent = noteContent;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Tasks getTaskId() {
        return taskId;
    }

    public void setTaskId(Tasks taskId) {
        this.taskId = taskId;
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
        hash += (noteId != null ? noteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TaskNotes)) {
            return false;
        }
        TaskNotes other = (TaskNotes) object;
        if ((this.noteId == null && other.noteId != null) || (this.noteId != null && !this.noteId.equals(other.noteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.TaskNotes[ noteId=" + noteId + " ]";
    }
    
}
