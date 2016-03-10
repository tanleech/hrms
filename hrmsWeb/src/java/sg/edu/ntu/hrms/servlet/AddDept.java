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
import sg.edu.ntu.hrms.ejb.DeptBeanLocal;
import sg.edu.ntu.hrms.dto.DeptDTO;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@WebServlet(
    urlPatterns = {"/addDept"}
)
public class AddDept extends HttpServlet {
    @EJB
    private DeptBeanLocal deptBean;
    

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
        String name = request.getParameter("name");
        String action = request.getParameter("action");
        //System.out.println("name: "+name);
        String page="/addDept.jsp";
        String oldName = request.getParameter("oldName");
        if(name!=null&&!name.isEmpty())
        {
            DeptDTO deptDTO = new DeptDTO();
            deptDTO.setDescription(name);
            if(deptBean.getDepartment(name)==null)
            {
              if(action!=null&&action.equals("U"))
              {
                  
                  deptBean.updateDept(oldName, name);
              }
              else
              {
                  deptBean.addDept(deptDTO);
              }
              //request.getSession().setAttribute("dept", name);
              //page = "/deptEdit";
              page = "/deptList";
            }
            else
            {
                
                //duplicate name
                request.setAttribute("error", "Duplicate department name.");
                if(action!=null&&action.equals("U"))
                {
                  page="/editDeptName.jsp?dept="+oldName;
                }
                
            }
        }
        RequestDispatcher view = getServletContext().getRequestDispatcher(page); 
        view.forward(request,response);           
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
