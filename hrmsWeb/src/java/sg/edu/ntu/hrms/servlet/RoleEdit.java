/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.ntu.hrms.ejb.AccessBeanLocal;
import sg.edu.ntu.hrms.ejb.ModuleBeanLocal;
import sg.edu.ntu.hrms.dto.AccessDTO;
import sg.edu.ntu.hrms.dto.ModuleDTO;
import sg.edu.ntu.hrms.dto.RoleDTO;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@WebServlet(
    urlPatterns = {"/roleEdit"}
)

public class RoleEdit extends HttpServlet {
    @EJB
    private ModuleBeanLocal moduleBean;
    @EJB
    private AccessBeanLocal accessBean;
    


    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
        response.setContentType("text/html;charset=UTF-8");
            
        String action = request.getParameter("action");
        System.out.println("action: "+action);
        String page = "/roleEdit.jsp";
        if(action==null||action.isEmpty())
        {
            List<ModuleDTO> moduleList = moduleBean.getAllModules();
            System.out.println("setting moduleList");
            request.getSession().setAttribute("moduleList",moduleList);
        }
        else if(action.equals("A"))
        {   
            RoleDTO role = prepare(request);
            //update ejb
            if(accessBean.getRole(role.getDescription())!=null)
            {
              request.setAttribute("error", "Duplicate role name not allowed.");
              page = "/roleEdit.jsp";
            }
            else
            {
              accessBean.addRole(role);
              page = "/roleList"; 

            }  
              //clear the modulelist in session.
            //request.getSession().removeAttribute("moduleList");
            //page = "/roleList"; 
        }
        else if(action.equals("U"))
        {
            //get the role description
            String description = request.getParameter("role");
            RoleDTO roleDTO = accessBean.getRole(description);
            
            request.setAttribute("roleData", roleDTO);
            String flag ="N";
            for(int i=0; i<roleDTO.getAccessList().size();i++)
            {
                AccessDTO entry = roleDTO.getAccessList().get(i);
                if(entry.getAccess()==1)
                {
                    flag="Y";
                }
            }
            request.setAttribute("sys", flag);
            //clear the modulelist in session.
            //request.getSession().removeAttribute("moduleList");
            page = "/roleEdit.jsp?action=U";
        }
        else if(action.equals("E"))
        {
            RoleDTO role = prepare(request);
            //accessBean.update(role);
            int id = accessBean.getRole(role.getDescription()).getId();
            accessBean.update(id, role.getAccessList());
            page = "/roleList"; 
        }
        RequestDispatcher view = getServletContext().getRequestDispatcher(page); 
        view.forward(request,response);     
    }
    
    private RoleDTO prepare(HttpServletRequest request)
    {
            List<ModuleDTO> moduleList = (List)request.getSession().getAttribute("moduleList");
            if(moduleList==null)
            {
                moduleList = moduleBean.getAllModules();
            }
            List<AccessDTO> accessList = new ArrayList();
            RoleDTO role = new RoleDTO();
            String roleName = request.getParameter("name");
            role.setDescription(roleName);
            
            //system access
            //String acr = request.getParameter("system");
            System.out.println("module: "+moduleList);
            for(int i=0;i<moduleList.size();i++)
            {
                ModuleDTO module = moduleList.get(i);
                AccessDTO access = new AccessDTO();
                /*
                if(acr.equals("0"))
                {
                  access.setAccess(0);
                }
                */
                //else
                {
                    String rights = request.getParameter(module.getName());
                    System.out.println("rights: "+rights);
                    System.out.println("module name: "+module.getName());
                    access.setAccess(Integer.parseInt(rights));
                }
                access.setRole(role);
                access.setModule(module);
                accessList.add(access);
            }//end for
            role.setAccessList(accessList);
            return role;

    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
