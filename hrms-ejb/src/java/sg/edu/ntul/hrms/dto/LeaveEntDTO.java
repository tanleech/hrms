/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntul.hrms.dto;

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
@Table(name= "Leave_ent")  
public class LeaveEntDTO implements java.io.Serializable {
    
    @Id @GeneratedValue
    @Column(name = "id")
    private int id;


    @Column(name = "carriedOver")
    private double carriedOver;
    
    @Column(name = "current")
    private double current ;

    @Column(name = "balance")
    private double balance;
    
    @Column(name = "max")
    private double max;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="User_id")
    private UserDTO user;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="LeaveType_id")
    private LeaveTypeDTO leaveType;

    public double getMax() {
        return max;
    }

   public void setMax(double max) {
        this.max = max;
    }

    @Column(name = "created")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "modified")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modified;
    
     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public double getCarriedOver() {
        return carriedOver;
    }

    public void setCarriedOver(double carriedOver) {
        this.carriedOver = carriedOver;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
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
