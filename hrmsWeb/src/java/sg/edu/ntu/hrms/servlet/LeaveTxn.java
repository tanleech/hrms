/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.servlet;

import sg.edu.ntu.hrms.servlet.helper.BeanHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.ntu.hrms.ejb.LeaveBeanLocal;
import sg.edu.ntu.hrms.ejb.UserBeanLocal;
import sg.edu.ntu.hrms.dto.AccessDTO;
import sg.edu.ntu.hrms.dto.LeaveTxnDTO;
import sg.edu.ntu.hrms.dto.LeaveTypeDTO;
import sg.edu.ntu.hrms.dto.UserDTO;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@WebServlet(
    urlPatterns = {"/leaveTxn"}
)

public class LeaveTxn extends HttpServlet {
    @EJB(beanName="LeaveBean")
    private LeaveBeanLocal leaveBean;
    @EJB(beanName="UserBean")
    private UserBeanLocal userBean;
    
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
        String page = "/applyLeave.jsp";

        List<LeaveTypeDTO> typeList = leaveBean.getAllLeaveSettings();
        request.setAttribute("typeList", typeList);
        
        String action = request.getParameter("action");
        if(action!=null)
        {
            if(action.equals("Q"))
            {
                String typeIdStr = request.getParameter("typeId");
                int typeId = (int) Double.parseDouble(typeIdStr);
                System.out.println("typeId: "+typeId);
                LeaveTypeDTO typeDTO= new LeaveTypeDTO();
                typeDTO.setId(typeId);
                UserDTO userDTO = (UserDTO)request.getSession().getAttribute("User");
                HashMap map = new BeanHelper().getUserTab(userBean);
                System.out.println("approver: "+userDTO.getApprover());
                String appr = (String)map.get(userDTO.getApprover());
                userDTO.setApproverName(appr);
                double balance = leaveBean.getLeaveBalance(typeDTO, userDTO);
                PrintWriter out = response.getWriter();
                out.write(String.valueOf(balance));
                out.flush();
                page="";

            }
            else if(action.equals("list"))
            {
                List<LeaveTxnDTO> txnList = null;
                HashMap map = new BeanHelper().getUserTab(userBean);
                HashMap accessTab = (HashMap)request.getSession().getAttribute("access");
                UserDTO userDTO = (UserDTO)request.getSession().getAttribute("User");
                AccessDTO access = (AccessDTO)accessTab.get("Leave");
                int acr = access.getAccess();
                System.out.println("acr: "+acr);
                if(acr==1)
                {
                    if(!userDTO.isIsManager())
                    {
                      txnList = leaveBean.getLeaveRecords(userDTO.getId());
                    }
                    else
                    {
                      txnList = leaveBean.getTxnForApprover(userDTO.getId());
                      request.setAttribute("isManager", "Y");
                        
                    }
                }
                else if (acr==2)
                {
                   txnList = leaveBean.getAllTxn();
                }   


                for(int i=0;i<txnList.size();i++)
                {
                    LeaveTxnDTO txn = txnList.get(i);
                    txn.getUser().setApproverName((String)map.get(txn.getUser().getApprover()));
                }
                request.setAttribute("leaveTxnlist", txnList);
                page="/leaveList.jsp";
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
