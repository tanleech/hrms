/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntul.hrms.dto;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@Entity  
@Table(name= "LeaveType")  
public class LeaveTypeDTO implements java.io.Serializable {
    
    @Id @GeneratedValue
    @Column(name = "id")
    private int id;
    
    @Column(name = "descr")
    private String description;
    
    @Column(name = "days")
    private double days;
    
    @Column(name = "mandatory")
    private String mandatory;
    
    @Column(name = "annualIncre")
    private double annualIncre;
    
    @Column(name = "cf")
    private double carriedForward;

    @Column(name = "created")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "modified")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modified;
    
     public int getId() {
        return id;
    }

    public double getDays() {
        return days;
    }

    public void setDays(double days) {
        this.days = days;
    }

    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    public double getAnnualIncre() {
        return annualIncre;
    }

    public void setAnnualIncre(double annualIncre) {
        this.annualIncre = annualIncre;
    }

    public double getCarriedForward() {
        return carriedForward;
    }

    public void setCarriedForward(double carriedForward) {
        this.carriedForward = carriedForward;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
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
