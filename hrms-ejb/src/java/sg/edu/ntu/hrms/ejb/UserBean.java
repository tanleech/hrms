/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.ejb;

import sg.edu.ntu.hrms.dto.RoleDTO;
import sg.edu.ntu.hrms.dto.UserDTO;
import sg.edu.ntu.hrms.dto.UserRoleDTO;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@Stateless
public class UserBean implements UserBeanLocal {
    
    @Override
    public UserDTO authenticate(String loginId, String password, boolean useLDAP) {
        
        Context initCtx;
        String url,baseDn;
        UserDTO userData = null;
        try {
           if(useLDAP)
           {
            initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            // Look up our data source
            url = (String)envCtx.lookup("LDAP_URL");
            baseDn = (String)envCtx.lookup("LDAP_BASE");
            
       	    Hashtable<String,String> env = new Hashtable<String,String>();
  	    env.put(Context.INITIAL_CONTEXT_FACTORY, 
	    "com.sun.jndi.ldap.LdapCtxFactory");
	    env.put(Context.PROVIDER_URL, url);
                
            // Authenticate as S. User and password "mysecret"
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, "uid="+loginId+","+baseDn);
            env.put(Context.SECURITY_CREDENTIALS, password);

	    // Create initial context
	    DirContext ctx = new InitialDirContext(env);
	    ctx.close();
            //authenticated get UserDTO
            userData = getUser(loginId);
            System.out.println("user id: "+userData.getId());
            
           }
           else
           {
               userData = getUser(loginId);
               System.out.println("user id: "+userData.getId());
               if(!password.equals(userData.getPassword()))
               {
                   userData=null;
               }
           }
            
            
	} catch (NamingException e) {
	    e.printStackTrace();
	}
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
            
        
        return userData;
    }
  
    @Override 
    public UserDTO getUser(String loginId)
    {
        UserDTO data=null;
        String hql = "FROM sg.edu.ntu.hrms.dto.UserDTO U left join fetch U.role WHERE U.login = :userLogin";
        Session session=null;
        try{
            session = DaoDelegate.getInstance().create();
            Query query = session.createQuery(hql);
            query.setParameter("userLogin", loginId);
            List results = query.list();
            if(results!=null && !results.isEmpty())
            {
               data = (UserDTO) results.get(0);
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }
        return data;
    }


    @Override
    public void createUser(UserDTO user) {
        java.util.Date current = new java.util.Date();
        user.setCreated(current);
        user.setModified(current);
        Session session = null;
        Transaction txn = null;
        try
        {
            session = DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            user.setDeleted("N");
            /*
            user.getDept().setCreated(current);
            user.getDept().setModified(current);

            user.getRole().setCreated(current);
            user.getRole().setModified(current);
            
            user.getLeaveEnt().get(0).setCreated(current);
            user.getLeaveEnt().get(0).setModified(current);
            */
            
            session.persist(user);
            //deptBean.addEmployees(userList, dept);
            
            txn.commit(); 
        }catch (Exception ex)
        {
            txn.rollback();
            ex.printStackTrace();
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }
    }

    @Override
    public List<UserDTO> getAllUsers(Date from, Date to){
        
        List<UserDTO> results = null;
        String hql = "FROM sg.edu.ntu.hrms.dto.UserDTO U left join fetch U.dept WHERE U.dateJoin BETWEEN :stDate "
                +    "AND :edDate AND U.deleted='N'";
        Session session = null;
        try
        {
            session = DaoDelegate.getInstance().create();
            Query query = session.createQuery(hql);
            query.setParameter("stDate", from);
            query.setParameter("edDate", to);

            results = query.list();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }
        return results;
    }

    @Override
    public void assignRole(UserDTO userDto, RoleDTO roleDto) {
        
        java.util.Date current = new java.util.Date();
        UserRoleDTO userRole = new UserRoleDTO();
        userRole.setCreated(current);
        userRole.setModified(current);
        Session session = null;
        Transaction txn = null;
        try
        {
            session = DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            userRole.setUser(userDto);
            userRole.setRole(roleDto);
            session.persist(userRole);
            //deptBean.addEmployees(userList, dept);
            
            txn.commit(); 
        }catch (Exception ex)
        {
            txn.rollback();
            ex.printStackTrace();
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }

        
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        
        java.util.Date current = new java.util.Date();
        Session session = null;
        Transaction txn = null;
        try
        {
            session = DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            System.out.println("id: "+userDTO.getId());
            UserDTO user = (UserDTO) session.get(UserDTO.class, userDTO.getId());
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setPhone(userDTO.getPhone());
            user.setOffice(userDTO.getOffice());
            user.setLogin(userDTO.getLogin());
            user.setDateJoin(userDTO.getDateJoin());
            user.setProbationDue(userDTO.getProbationDue());
            user.setTitle(userDTO.getTitle());
            user.setModified(current);
            user.setApprover(userDTO.getApprover());
            session.saveOrUpdate(user);
            //deptBean.addEmployees(userList, dept);
            txn.commit(); 
        }catch (Exception ex)
        {
            txn.rollback();
            ex.printStackTrace();
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }
    }

    @Override
    public void updateRole(int userId, int roleId) {
        
        java.util.Date current = new java.util.Date();
        Session session = null;
        Transaction txn = null;
        try
        {
            session = DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            System.out.println("roleId: "+roleId);
            System.out.println("userId: "+userId);
            
            Query qry = session.createQuery("UPDATE sg.edu.ntu.hrms.dto.UserRoleDTO userRole SET userRole.role.id=:roleId WHERE userRole.user.id=:userId");
            qry.setParameter("roleId", roleId);
            qry.setParameter("userId", userId);
            qry.executeUpdate();
            txn.commit(); 
        }catch (Exception ex)
        {
            txn.rollback();
            ex.printStackTrace();
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }

    }

    @Override
    public List<UserDTO> getReporteeList(int userId) {
        
        Session session = null;
        Transaction txn = null;
        List<UserDTO> results = null;
        try
        {
            session = DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            Query qry = session.createQuery("FROM sg.edu.ntu.hrms.dto.UserDTO user WHERE user.approver=:userId");
            qry.setParameter("userId", userId);
            results = qry.list();
        }catch (Exception ex)
        {
            txn.rollback();
            ex.printStackTrace();
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }

        return results;
    }
    
    
    
    
}
