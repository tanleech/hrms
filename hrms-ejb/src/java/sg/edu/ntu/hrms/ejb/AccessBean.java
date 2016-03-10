/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.ejb;

import sg.edu.ntul.hrms.dto.AccessDTO;
import sg.edu.ntul.hrms.dto.RoleDTO;
import java.util.List;
import javax.ejb.Stateless;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@Stateless
public class AccessBean implements AccessBeanLocal {

    @Override
    public List<RoleDTO> getAllRoles() {
        
        List results=null;
        Session session=null;
        try
        {
            session = DaoDelegate.getInstance().create();
            //results =  session.createQuery("FROM com.sapuraglobal.hrms.dto.RoleDTO role").list();
            results = session.createQuery("SELECT DISTINCT role FROM com.sapuraglobal.hrms.dto.RoleDTO role left join fetch role.userRoleList").list();
            
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


    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void addRole(RoleDTO roleDTO) {
        java.util.Date current = new java.util.Date();
        roleDTO.setCreated(current);
        roleDTO.setModified(current);
        Session session = null;
        Transaction txn = null;
        try
        {
            session =  DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            //set all the accesslist
            List<AccessDTO> accessList = roleDTO.getAccessList();
            for(int i=0;i<accessList.size();i++)
            {
                accessList.get(i).setCreated(current);
                accessList.get(i).setModified(current);
            }
            
            session.save(roleDTO);
            txn.commit();
            
        }catch (Exception ex)
        {
            if(txn!=null)
            {
                txn.rollback();
            }
            ex.printStackTrace();
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }

    }

    @Override
    public RoleDTO getRole(String descr) {
        List results=null;
        Session session=null;
        try
        {
            session = DaoDelegate.getInstance().create();
            //results =  session.createQuery("FROM com.sapuraglobal.hrms.dto.RoleDTO role").list();
            String qry = "SELECT DISTINCT role FROM com.sapuraglobal.hrms.dto.RoleDTO role left join fetch role.accessList WHERE role.description = :descr";
            Query query = session.createQuery(qry);
            query.setParameter("descr", descr);
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
        
        return (RoleDTO)results.get(0);
    }

    @Override
    public void update(int roleId, List<AccessDTO>accessList) {
        java.util.Date current = new java.util.Date();
        Session session = null;
        Transaction txn = null;
        try
        {
            session =  DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            String hql = "UPDATE com.sapuraglobal.hrms.dto.AccessDTO acr set acr.access = :acr, acr.modified = :modify WHERE acr.role.id = :roleId and acr.module.id=:moduleId";
            Query qry = session.createQuery(hql);
            //set all the accesslist
            for(int i=0;i<accessList.size();i++)
            {
                AccessDTO access = (AccessDTO) accessList.get(i);
                System.out.println("acr: "+access.getAccess());
                qry.setParameter("acr", access.getAccess());
                qry.setParameter("modify", current);
                qry.setParameter("roleId", roleId);
                qry.setParameter("moduleId", access.getModule().getId());
                System.out.println("role: "+roleId);
                int result =qry.executeUpdate();
                //txn.commit();
            }
            txn.commit();
        }catch (Exception ex)
        {
            if(txn!=null)
            {
                txn.rollback();
            }
            ex.printStackTrace();
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }
        
    }

    @Override
    public List<AccessDTO> getAccessRights(int roleId) {
        List results=null;
        Session session=null;
        try
        {
            session = DaoDelegate.getInstance().create();
            //results =  session.createQuery("FROM com.sapuraglobal.hrms.dto.RoleDTO role").list();
            String qry = "SELECT access FROM com.sapuraglobal.hrms.dto.AccessDTO access WHERE access.role.id = :roleId";
            Query query = session.createQuery(qry);
            query.setParameter("roleId", roleId);
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
    
    
    
}
