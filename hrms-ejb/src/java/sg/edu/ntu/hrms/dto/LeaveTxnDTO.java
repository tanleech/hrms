/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.dto;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@Entity  
@Table(name= "Leave_txn")  
public class LeaveTxnDTO implements java.io.Serializable {
    
    @Id @GeneratedValue
    @Column(name = "id")
    private int id;
    
    @Column(name = "start")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date start;
    
    @Column(name = "end")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date end;
    
    @Column(name = "start_slot")
    private String start_slot;
    
    @Column(name = "end_slot")
    private String end_slot;
    
   @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="status_id")
    private StatusDTO status;
    
    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="User_id")
    private UserDTO user;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="LeaveType_id")
    private LeaveTypeDTO leaveType;
    
    @Column(name = "days")
    private double days;
    
    @Column(name = "created")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "modified")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modified;

    public double getDays() {
        return days;
    }

    public void setDays(double days) {
        this.days = days;
    }
    
    
    
     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getStart_slot() {
        return start_slot;
    }

    public void setStart_slot(String start_slot) {
        this.start_slot = start_slot;
    }

    public String getEnd_slot() {
        return end_slot;
    }

    public void setEnd_slot(String end_slot) {
        this.end_slot = end_slot;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public LeaveTypeDTO getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveTypeDTO leaveType) {
        this.leaveType = leaveType;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
    
}
