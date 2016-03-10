/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.ejb;

import sg.edu.ntul.hrms.dto.TitleDTO;
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
public class TitleBean implements TitleBeanLocal {

    @Override
    public List<TitleDTO> getAllTitles() {
    
        List results=null;
        Session session=null;
        try
        {
            session = DaoDelegate.getInstance().create();
            results = session.createQuery("SELECT DISTINCT title FROM com.sapuraglobal.hrms.dto.TitleDTO title left join fetch title.employees").list();
            

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
    public void addTitle(TitleDTO title) {
        java.util.Date current = new java.util.Date();
        title.setCreated(current);
        title.setModified(current);
        Session session = null;
        Transaction txn = null;
        try
        {
            session =  DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            session.persist(title);
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
    public TitleDTO getTitle(int id) {
        List results=null;
        Session session=null;
        try
        {
            session = DaoDelegate.getInstance().create();
            Query qry = session.createQuery("SELECT title FROM com.sapuraglobal.hrms.dto.TitleDTO title WHERE title.id = :id");
            qry.setParameter("id", id);
            results = qry.list();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }
        
        return (TitleDTO)results.get(0);
    }

    @Override
    public TitleDTO getTitleByName(String descr) {
        
        List results=null;
        Session session=null;
        TitleDTO data=null;
        try
        {
            session = DaoDelegate.getInstance().create();
            Query qry = session.createQuery("SELECT title FROM com.sapuraglobal.hrms.dto.TitleDTO title WHERE title.description = :descr");
            qry.setParameter("descr", descr);
            results = qry.list();
            if(results!=null&&results.size()>0)
            {
               data = (TitleDTO)results.get(0);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }
        
        return data ;
    }
    
    
}
