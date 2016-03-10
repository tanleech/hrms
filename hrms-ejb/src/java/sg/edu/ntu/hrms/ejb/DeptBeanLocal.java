/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.ejb;

import sg.edu.ntul.hrms.dto.DeptDTO;
import sg.edu.ntul.hrms.dto.UserDTO;
import sg.edu.ntul.hrms.dto.UserDeptDTO;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@Local
public interface DeptBeanLocal {

    List<DeptDTO> getAllDepts();

    void addDept(DeptDTO deptDTO);

    DeptDTO getDepartment(String deptDescr);

    UserDeptDTO getUserDept(int userId, int deptId);

    void unassignManager(int deptId);

    void assignEmployee(UserDTO userDTO, DeptDTO deptDTO); 

    void unassignEmployee(int userId, int deptId);

    int assignManager(int userId, int deptId);

    int updateEmployee(int userId, int deptId);

    int updateDept(String oldName, String newName);
    
}
