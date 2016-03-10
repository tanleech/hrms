/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.ejb;

import sg.edu.ntu.hrms.dto.LeaveEntDTO;
import sg.edu.ntu.hrms.dto.LeaveTxnDTO;
import sg.edu.ntu.hrms.dto.LeaveTypeDTO;
import sg.edu.ntu.hrms.dto.StatusDTO;
import sg.edu.ntu.hrms.dto.UserDTO;
import java.util.List;
import javax.ejb.Stateless;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author michael-PC
 */
@Stateless
public class LeaveBean implements LeaveBeanLocal {

    @Override
    public void saveLeaveSetting(LeaveTypeDTO leaveType) {
        
        java.util.Date current = new java.util.Date();
        Session session = null;
        Transaction txn = null;
        leaveType.setCreated(current);
        leaveType.setModified(current);
        try
        {
            
            session =  DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            session.persist(leaveType);
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

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public List<LeaveTypeDTO> getAllLeaveSettings() {
        List results=null;
        Session session=null;
        try
        {
            session = DaoDelegate.getInstance().create();
            results =  session.createQuery("FROM sg.edu.ntu.hrms.dto.LeaveTypeDTO leaveType").list();
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
    public void deleteLeaveSetting(int typeId) {
        
        List results=null;
        Session session=null;
        Transaction txn = null;
        try
        {
            session = DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            Query delQry = session.createQuery("DELETE FROM sg.edu.ntu.hrms.dto.LeaveTypeDTO where id = :typeId");
            delQry.setParameter("typeId", typeId);
            delQry.executeUpdate();
            txn.commit();
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            if(txn!=null)
            {
                txn.rollback();
            }
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }

    }

    @Override
    public LeaveTypeDTO getLeaveSetting(int id) {
        Session session=null;
        LeaveTypeDTO result = null;
        try
        {
            session = DaoDelegate.getInstance().create();
            Query qry =  session.createQuery("FROM sg.edu.ntu.hrms.dto.LeaveTypeDTO WHERE id = :typeId");
            qry.setParameter("typeId", id);
            result = (LeaveTypeDTO)qry.list().get(0);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }
        return result;
    }


    @Override
    public void updateLeaveSetting(LeaveTypeDTO type) {
        
        Session session=null;
        Transaction txn = null;
        java.util.Date current = new java.util.Date();
        try
        {
            session = DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            Query updateQry = session.createQuery("UPDATE sg.edu.ntu.hrms.dto.LeaveTypeDTO "
                    + " set description = :descr, days = :ent, mandatory = :option, annualIncre = :annual, cf = :cf, modified =:modified"
                    + " where id = :typeId");
            updateQry.setParameter("typeId", type.getId());
            updateQry.setParameter("descr", type.getDescription());
            System.out.println("days: "+type.getDays());
            updateQry.setParameter("ent", type.getDays());
            updateQry.setParameter("option", type.getMandatory());
            updateQry.setParameter("annual", type.getAnnualIncre());
            updateQry.setParameter("cf", type.getCarriedForward());
            updateQry.setParameter("modified", current);
            updateQry.executeUpdate();
            txn.commit();
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            if(txn!=null)
            {
                txn.rollback();
            }
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }

        
        
    }

    
    @Override
    public void applyLeave(LeaveTxnDTO leaveTxn) {
        Session session=null;
        Transaction txn = null;
        java.util.Date current = new java.util.Date();
        try
        {
            session = DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            leaveTxn.setCreated(current);
            leaveTxn.setModified(current);
            session.persist(leaveTxn);            
            txn.commit();
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            if(txn!=null)
            {
                txn.rollback();
            }
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }

        
    }

    @Override
    public double getLeaveBalance(LeaveTypeDTO leaveType, UserDTO user) {
        Session session=null;
        LeaveEntDTO result = null;
        double bal = -1.0;
        try
        {
            session = DaoDelegate.getInstance().create();
            Query qry =  session.createQuery("FROM sg.edu.ntu.hrms.dto.LeaveEntDTO ent WHERE ent.user.id = :userId and ent.leaveType.id = :typeId");
            qry.setParameter("userId",user.getId());
            System.out.println("leave typeId: "+leaveType.getId());
            qry.setParameter("typeId",leaveType.getId());
            
            List<LeaveEntDTO> entList = qry.list();
            if(entList==null||entList.isEmpty())
            {
                bal = 0;
            }
            else
            {
                result = (LeaveEntDTO)qry.list().get(0);
                bal = result.getBalance();
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
        return bal;

    }

    @Override
    public List<LeaveEntDTO> getLeaveEntList(String loginId) {
        
        Session session=null;
        List<LeaveEntDTO> result = null;
        try
        {
            session = DaoDelegate.getInstance().create();
            Query qry =  session.createQuery("SELECT ent FROM sg.edu.ntu.hrms.dto.LeaveEntDTO ent left join fetch ent.leaveType WHERE ent.user.login = :loginId");
            qry.setParameter("loginId", loginId);
            result = qry.list();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }
        return result;
    }

    @Override
    public void addLeaveEnt(LeaveEntDTO leaveEnt) {

        Session session=null;
        java.util.Date current = new java.util.Date();
        Transaction txn = null;

        try
        {
            session = DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            leaveEnt.setCreated(current);
            leaveEnt.setModified(current);
            session.persist(leaveEnt);
            txn.commit();
        }
        catch(Exception ex)
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
    public LeaveTypeDTO getLeaveType(String leaveType) {
       Session session=null;
        List<LeaveTypeDTO> results = null;
        try
        {
            session = DaoDelegate.getInstance().create();
            Query qry =  session.createQuery("FROM sg.edu.ntu.hrms.dto.LeaveTypeDTO type WHERE type.description = :descr");
            qry.setParameter("descr", leaveType);
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
        if(results!=null&&!results.isEmpty())
        {
            return (LeaveTypeDTO)results.get(0); 
        }
        else
        {
            return null;
        }
    }

    @Override
    public List<LeaveTypeDTO> getLeaveSettings(boolean mandatory) {
        Session session=null;
        List<LeaveTypeDTO> results = null;
        String flag="N";
        try
        {
            if(mandatory)
            {
                flag="Y";
            }
            session = DaoDelegate.getInstance().create();
            Query qry =  session.createQuery("FROM sg.edu.ntu.hrms.dto.LeaveTypeDTO WHERE mandatory = :man");
            qry.setParameter("man", flag);
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
        return results;
    }

    @Override
    public void deleteLeaveEnt(int entId, int userId) {
        
        Session session=null;
        Transaction txn = null;
        try
        {
            session = DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            Query delQry = session.createQuery("DELETE FROM sg.edu.ntu.hrms.dto.LeaveEntDTO ent where id = :entId and ent.user.id=:userId");
            delQry.setParameter("entId", entId);
            delQry.setParameter("userId", userId);
            delQry.executeUpdate();
            txn.commit();
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            if(txn!=null)
            {
                txn.rollback();
            }
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }

        
    }

    @Override
    public List<LeaveTxnDTO> getAllTxn() {
        List results=null;
        Session session=null;
        try
        {
            session = DaoDelegate.getInstance().create();
            results =  session.createQuery("FROM sg.edu.ntu.hrms.dto.LeaveTxnDTO txn left join fetch txn.user").list();
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
    public List<LeaveTxnDTO> getTxnForApprover(int approver) {
        List results=null;
        Session session=null;
        try
        {
            session = DaoDelegate.getInstance().create();
            Query qry =  session.createQuery("FROM sg.edu.ntu.hrms.dto.LeaveTxnDTO txn left join fetch txn.user WHERE txn.user.approver =:approverId");
            qry.setParameter("approverId", approver);
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
        
        return results;
    }

    @Override
    public List<LeaveTxnDTO> getLeaveRecords(int userId) {
        List results=null;
        Session session=null;
        try
        {
            session = DaoDelegate.getInstance().create();
            Query qry =  session.createQuery("FROM sg.edu.ntu.hrms.dto.LeaveTxnDTO txn WHERE txn.user.id =:userId");
            qry.setParameter("userId", userId);
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
        
        return results;
    }

    @Override
    public StatusDTO getStatus(String descr) {
        Session session=null;
        StatusDTO status = null;
        try
        {
            session = DaoDelegate.getInstance().create();
            Query qry =  session.createQuery("FROM sg.edu.ntu.hrms.dto.StatusDTO status WHERE status.description =:descr");
            qry.setParameter("descr", descr);
            status = (StatusDTO)qry.list().get(0);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }
        
        return status;
        
    }

    @Override
    public void updateLeaveEnt(int leaveTypeId, int userId, double days) {
        Session session=null;
        Transaction txn = null;
        java.util.Date current = new java.util.Date();
        try
        {
            session = DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            Query updateQry = session.createQuery("UPDATE sg.edu.ntu.hrms.dto.LeaveEntDTO ent "
                    + " set  ent.balance = :bal, ent.modified =:modified "
                    + " where ent.leaveType.id = :typeId AND ent.user.id = :userId");
            System.out.println("typeId: "+leaveTypeId);
            updateQry.setParameter("typeId", leaveTypeId);
            updateQry.setParameter("userId", userId);
            updateQry.setParameter("bal", days);
            updateQry.setParameter("modified", current);
            updateQry.executeUpdate();
            txn.commit();
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            if(txn!=null)
            {
                txn.rollback();
            }
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }

        
    }

    @Override
    public void approveLeave(int txnId, int status) {
        
        Session session=null;
        Transaction txn = null;
        java.util.Date current = new java.util.Date();
        try
        {
            session = DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            Query updateQry = session.createQuery("UPDATE sg.edu.ntu.hrms.dto.LeaveTxnDTO txn "
                    + " set  txn.status.id = :status, txn.modified =:modified "
                    + " where txn.id = :txnId");
            updateQry.setParameter("status", status);
            updateQry.setParameter("txnId", txnId);
            updateQry.setParameter("modified", current);
            updateQry.executeUpdate();
            txn.commit();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            if(txn!=null)
            {
                txn.rollback();
            }
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }
        
    }

    @Override
    public LeaveTxnDTO getTxn(int txnId) {
        List results=null;
        Session session=null;
        LeaveTxnDTO txn = null;
        try
        {
            session = DaoDelegate.getInstance().create();
            Query qry =  session.createQuery("FROM sg.edu.ntu.hrms.dto.LeaveTxnDTO txn WHERE txn.id =:txnId");
            qry.setParameter("txnId", txnId);
            results = qry.list();
            txn = (LeaveTxnDTO)results.get(0);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }
        
        return txn;

    }

        
    
}
