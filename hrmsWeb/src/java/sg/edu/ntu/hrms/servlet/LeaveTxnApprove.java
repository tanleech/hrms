/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.servlet;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.ntu.hrms.ejb.LeaveBeanLocal;
import sg.edu.ntu.hrms.dto.LeaveTypeDTO;
import sg.edu.ntu.hrms.dto.StatusDTO;
import sg.edu.ntu.hrms.dto.UserDTO;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@WebServlet(
    urlPatterns = {"/leaveTxnApprove"}
)

public class LeaveTxnApprove extends HttpServlet {
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

        String page = "/leaveTxn?action=list";

        String txnId = request.getParameter("txn");
        if(action!=null)
        {
            if(action.equals("APPRV"))
            {
                StatusDTO status = leaveBean.getStatus("approved");
                leaveBean.approveLeave(Integer.parseInt(txnId), status.getId());
            }
            else if(action.equals("REJ"))
            {
                StatusDTO status = leaveBean.getStatus("rejected");
                leaveBean.approveLeave(Integer.parseInt(txnId), status.getId());
                //add on to the leave balance
                String typeId = request.getParameter("typeId");
                String userId = request.getParameter("userId");
                double days = Double.parseDouble(request.getParameter("days"));
                
                //LeaveEntDTO entDTO = leaveBean.getLeaveEnt(Integer.parseInt(typeId), Integer.parseInt(userId));
                LeaveTypeDTO typeDTO = new LeaveTypeDTO();
                typeDTO.setId(Integer.parseInt(typeId));
                
                UserDTO userDTO = new UserDTO();
                userDTO.setId(Integer.parseInt(userId));
                double bal = leaveBean.getLeaveBalance(typeDTO, userDTO);

                leaveBean.updateLeaveEnt(Integer.parseInt(typeId),Integer.parseInt(userId) , bal+days);
            }
        }
        
        if(!page.isEmpty())
        {
            RequestDispatcher view = getServletContext().getRequestDispatcher(page); 
            view.forward(request,response);     
        }
     }
    /*
    private LeaveEntDTO prepare(HttpServletRequest request)
    {
        
        String cf = request.getParameter("cf");
        
        LeaveTypeDTO type = new LeaveTypeDTO();
        type.setDescription(leaveType);
        type.setMandatory(mandatory);
        type.setDays(Double.parseDouble(ent));
        type.setAnnualIncre(Double.parseDouble(annualIncre));
        type.setCarriedForward(Double.parseDouble(cf));
        
        return type;
    }
    */
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
