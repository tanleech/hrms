/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.ejb;

import sg.edu.ntul.hrms.dto.ModuleDTO;
import java.util.List;
import javax.ejb.Stateless;
import org.hibernate.Session;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@Stateless
public class ModuleBean implements ModuleBeanLocal {

    @Override
    public List<ModuleDTO> getAllModules() {
        
        List results=null;
        Session session=null;
        try
        {
            session = DaoDelegate.getInstance().create();
            results =  session.createQuery("SELECT DISTINCT module FROM com.sapuraglobal.hrms.dto.ModuleDTO module left join fetch module.accessList").list();
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
    
    
}
