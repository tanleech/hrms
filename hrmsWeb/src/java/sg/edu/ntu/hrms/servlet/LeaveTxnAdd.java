/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.servlet;

import sg.edu.ntu.hrms.servlet.helper.Utility;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.ntu.hrms.ejb.LeaveBeanLocal;
import sg.edu.ntul.hrms.dto.LeaveTxnDTO;
import sg.edu.ntul.hrms.dto.LeaveTypeDTO;
import sg.edu.ntul.hrms.dto.StatusDTO;
import sg.edu.ntul.hrms.dto.UserDTO;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@WebServlet(
    urlPatterns = {"/leaveTxnAdd"}
)

public class LeaveTxnAdd extends HttpServlet {
    @EJB
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
        String page = "/leaveTxn?action=list";

        String lvTypeId = request.getParameter("leaveType");
        String startDate = request.getParameter("startDate");
        Date startDt=null,endDt=null;
        try
        {
           startDt = Utility.format(startDate, "MM/dd/yyyy");
           String endDate = request.getParameter("endDate");
           endDt = Utility.format(endDate, "MM/dd/yyyy");
 
        }catch(ParseException pe)
        {
            pe.printStackTrace();
        }
  
        String startSlot = request.getParameter("start_slot");

        String endSlot = request.getParameter("end_slot");

        String days = request.getParameter("taken");
        
        Double daysTaken = Double.parseDouble(days);
        UserDTO user = (UserDTO)request.getSession().getAttribute("User");
        LeaveTxnDTO txn = new LeaveTxnDTO();
        txn.setDays(daysTaken);
        
        txn.setStart(startDt);
        txn.setEnd(endDt);
        
        txn.setStart_slot(startSlot);
        txn.setEnd_slot(endSlot);
        
        System.out.println("leaveTypeId="+lvTypeId);
        LeaveTypeDTO typeDTO = leaveBean.getLeaveSetting(Integer.parseInt(lvTypeId));
        txn.setLeaveType(typeDTO);
        
        //LeaveEntDTO entDTO = leaveBean.getLeaveEnt(Integer.parseInt(lvTypeId), user.getId());
        double bal = leaveBean.getLeaveBalance(typeDTO, user);
        
        StatusDTO statusDTO = leaveBean.getStatus("pending");
        
        txn.setStatus(statusDTO);
        txn.setUser(user);
        
        leaveBean.applyLeave(txn);
        leaveBean.updateLeaveEnt(typeDTO.getId(), user.getId(), bal-daysTaken);
        //update the balance
        
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
