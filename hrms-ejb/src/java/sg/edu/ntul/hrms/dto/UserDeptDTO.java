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
@Table(name= "UserDept")  
public class UserDeptDTO implements java.io.Serializable {
    
    @Id @GeneratedValue
    @Column(name = "id")
    private int id;
    
    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="Dept_id")
    private DeptDTO dept;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="User_id")
    private UserDTO user;
    
    @Column(name="manager")
    private String manager;
    
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

    public DeptDTO getDept() {
        return dept;
    }

    public void setDept(DeptDTO dept) {
        this.dept = dept;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
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
