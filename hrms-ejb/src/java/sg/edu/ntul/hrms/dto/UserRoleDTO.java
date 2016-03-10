/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntul.hrms.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@Entity  
@Table(name= "UserRole")  
public class UserRoleDTO implements Serializable{
    
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    
    @Column(name="created")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;
    
    @Column(name="modified")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modified;
    
    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="Role_id")
    private RoleDTO role;
    
    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="User_id")
    private UserDTO user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
    
    

}
