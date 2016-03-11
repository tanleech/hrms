/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.servlet;

import sg.edu.ntu.hrms.servlet.helper.Utility;
import java.io.IOException;
import java.util.Date;
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
import sg.edu.ntu.hrms.dto.LeaveEntDTO;
import sg.edu.ntu.hrms.dto.UserDTO;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@WebServlet(
    urlPatterns = {"/leaveEnt"}
)

public class LeaveEnt extends HttpServlet {
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
            String action = request.getParameter("action");
            System.out.println("action: "+action);
            String page = "/employeeLeaveDetl.jsp";
            
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
                   System.out.println("action is A");
                   String login = request.getParameter("id");
                   UserDTO user = userBean.getUser(login);
                   request.setAttribute("typeList", leaveBean.getAllLeaveSettings());
                   request.setAttribute("user", user);
                   page="/empLeaveDetlAdd.jsp";
                }
                else if (action.equals("D"))
                {
                   //String id = request.getParameter("id");
                   //leaveBean.deleteLeaveSetting(Integer.parseInt(id));
                    String entId = request.getParameter("entId");
                    String userId = request.getParameter("userId");
                    leaveBean.deleteLeaveEnt(Integer.parseInt(entId),Integer.parseInt(userId));
                    
                }
                else if (action.equals("U"))
                {
                 String login = request.getParameter("id");
                 UserDTO user = userBean.getUser(login);
                 List<LeaveEntDTO> entList = leaveBean.getLeaveEntList(login);
                 boolean found = false;
                 int i =0;
                 LeaveEntDTO annualEnt=null;
                 while(!found && i < entList.size())
                 {
                     LeaveEntDTO ent = entList.get(i);
                     if(ent.getLeaveType().getDescription().equals("Annual"))
                     {
                         found=true;
                         //user.setLeaveEnt(entList);
                         annualEnt = ent;
                         request.setAttribute("entAnnual", ent);
                     }
                     i++;
                 }
                 
                 //compute Annual Accured
                 Date now = new Date();
                 Date begin = Utility.getYearBeginTime();
                 double days = Utility.computeDaysBetween(begin, now);
                 System.out.println("days between: "+days);
                 double accured = (days/365.0) * annualEnt.getCurrent();
                 
                 request.setAttribute("typeList", leaveBean.getAllLeaveSettings());
                 request.setAttribute("user", user);
                 request.setAttribute("entList", entList);
                 request.setAttribute("accured", accured);
                 
                 page="/employeeLeaveDetl.jsp";

                }
                else if (action.equals("E"))
                {
                   String id = request.getParameter("id");
                   System.out.println("id: "+id);
                   //LeaveTypeDTO leaveType = prepare(request);
                   //leaveType.setId(Integer.parseInt(id));
                   //leaveBean.updateLeaveSetting(leaveType);
                   page="/leaveSettings.jsp";
                    
                }
                //request.setAttribute("action", "");
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
