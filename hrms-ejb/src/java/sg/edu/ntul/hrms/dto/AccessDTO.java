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
@Table(name= "Access")
public class AccessDTO implements Serializable{
    
    @Id @GeneratedValue
    @Column(name = "id")
    private int id;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="Module_id")
    private ModuleDTO module;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="Role_id")
    private RoleDTO role;
    
    @Column(name = "created")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "modified")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modified;
    
    @Column(name = "access")
    private int access;
    

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }


    public ModuleDTO getModule() {
        return module;
    }

    public void setModule(ModuleDTO module) {
        this.module = module;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
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
