/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@Entity  
@Table(name= "User")  
public class UserDTO implements Serializable{
    
    @Id @GeneratedValue
    @Column(name = "id")
    private int id;
    
    @Column(name="name")
    private String name;
    
    @Column(name="phone")
    private String phone;
    
    @Column(name="office")
    private String office;
    
    @Column(name="password")
    private String password;
    

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }
    

    @Column(name="email")
    private String email;
    
    @Column(name="deleted")
    private String deleted;
    
    @Column(name="datejoin")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateJoin;
    
    @Column(name="probdue")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date probationDue;
    
    @Column(name="created")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;
    
    @Column(name="modified")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modified;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="Title_id")
    private TitleDTO title;
    
    //@Transient
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.MERGE)
    @JoinColumn(name="User_id")
    private UserDeptDTO dept;
    
    //@Transient
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.MERGE)
    @JoinColumn(name="User_id")
    private UserRoleDTO role;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="User_id")
    private List<LeaveEntDTO> leaveEnt;
    
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="User_id")
    private List<LeaveTxnDTO> leaveTxn;


    /*
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="approver")
    */
    @Column(name="approver")
    private int approver;
    
    @Column(name="login")
    private String login;
    
    @Transient
    private boolean isManager=false;
    
    @Transient
    private String approverName;

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    public UserRoleDTO getRole() {
        return role;
    }

    public void setRole(UserRoleDTO role) {
        this.role = role;
    }

    public boolean isIsManager() {
        return isManager;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }

    public List<LeaveEntDTO> getLeaveEnt() {
        return leaveEnt;
    }

    public void setLeaveEnt(List<LeaveEntDTO> leaveEnt) {
        this.leaveEnt = leaveEnt;
    }

    public List<LeaveTxnDTO> getLeaveTxn() {
        return leaveTxn;
    }

    public void setLeaveTxn(List<LeaveTxnDTO> leaveTxn) {
        this.leaveTxn = leaveTxn;
    }
    
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserDeptDTO getDept() {
        return dept;
    }

    public void setDept(UserDeptDTO dept) {
        this.dept = dept;
    }


    public int getApprover() {
        return approver;
    }

    public void setApprover(int approver) {
        this.approver = approver;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public Date getDateJoin() {
        return dateJoin;
    }

    public void setDateJoin(Date dateJoin) {
        this.dateJoin = dateJoin;
    }

    public Date getProbationDue() {
        return probationDue;
    }

    public void setProbationDue(Date probationDue) {
        this.probationDue = probationDue;
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

    public TitleDTO getTitle() {
        return title;
    }

    public void setTitle(TitleDTO title) {
        this.title = title;
    }


}
