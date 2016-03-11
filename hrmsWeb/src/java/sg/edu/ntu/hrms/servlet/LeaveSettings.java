/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.servlet;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.ntu.hrms.ejb.LeaveBeanLocal;
import sg.edu.ntu.hrms.dto.LeaveTypeDTO;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@WebServlet(
    urlPatterns = {"/leaveSettings"}
)

public class LeaveSettings extends HttpServlet {
    @EJB(beanName="LeaveBean")
    private LeaveBeanLocal leaveBean;
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
            String page = "/leaveSettings.jsp";
            
            /*
            String leaveType = request.getParameter("leaveType");
            String ent = request.getParameter("ent");
            String mandatory = request.getParameter("mandatory");
            String annualIncre = request.getParameter("annualIncre");
            String cf = request.getParameter("cf");
            */

            if(action!=null)
            {
                if (action.equals("A"))
                {
                    /*
                   LeaveTypeDTO type = new LeaveTypeDTO();
                   type.setDescription(leaveType);
                   type.setMandatory(mandatory);
                   type.setDays(Integer.parseInt(ent));
                   type.setAnnualIncre(Integer.parseInt(annualIncre));
                   type.setCarriedForward(Integer.parseInt(cf));
                    */
                   LeaveTypeDTO type = prepare(request);
                   if(leaveBean.getLeaveType(type.getDescription())!=null)
                   {
                       request.setAttribute("error", "Duplicate name not allowed");
                       page = "/leaveSettingsEdit.jsp";
                   }
                   else
                   {
                       leaveBean.saveLeaveSetting(type);
                   }
                    //page="/leaveSettings";
                }
                else if (action.equals("D"))
                {
                   String id = request.getParameter("id");
                   leaveBean.deleteLeaveSetting(Integer.parseInt(id));
                }
                else if (action.equals("U"))
                {
                   String id = request.getParameter("id");
                   LeaveTypeDTO type = leaveBean.getLeaveSetting(Integer.parseInt(id));
                   request.setAttribute("leaveType", type);
                   page="/leaveSettingsEdit.jsp?action=U";
                }
                else if (action.equals("E"))
                {
                   String id = request.getParameter("id");
                   System.out.println("id: "+id);
                   LeaveTypeDTO leaveType = prepare(request);
                   leaveType.setId(Integer.parseInt(id));
                   leaveBean.updateLeaveSetting(leaveType);
                   page="/leaveSettings.jsp";
                    
                }
                request.setAttribute("action", "");
            }
        if(page.equals("/leaveSettings.jsp"))    
        {
            List<LeaveTypeDTO> leaveList = leaveBean.getAllLeaveSettings();
            request.setAttribute("leaveTypelist", leaveList);            
        }
        
        RequestDispatcher view = getServletContext().getRequestDispatcher(page); 
        view.forward(request,response);     
     }
    
    private LeaveTypeDTO prepare(HttpServletRequest request)
    {
        String leaveType = request.getParameter("leaveType");
        String ent = request.getParameter("ent");
        System.out.println("ent: "+ent);
        String mandatory = request.getParameter("mandatory");
        String annualIncre = request.getParameter("annualIncre");
        String cf = request.getParameter("cf");
        double cfVal=0,entVal=0,increVal=0;
        if(cf!=null&&!cf.isEmpty())
        {
            cfVal = Double.parseDouble(cf);
        }
        if(ent!=null&&ent.isEmpty())
        {
            entVal = Double.parseDouble(ent);
        }
        if(annualIncre!=null&&annualIncre.isEmpty())
        {
            increVal = Double.parseDouble(annualIncre);
        }
        
        
        LeaveTypeDTO type = new LeaveTypeDTO();
        type.setDescription(leaveType);
        type.setMandatory(mandatory);
        type.setDays(Double.parseDouble(ent));
        type.setAnnualIncre(Double.parseDouble(annualIncre));
        type.setCarriedForward(cfVal);
        
        return type;
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
