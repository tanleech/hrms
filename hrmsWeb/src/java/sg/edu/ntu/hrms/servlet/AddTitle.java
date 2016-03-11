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
import sg.edu.ntu.hrms.ejb.TitleBeanLocal;
import sg.edu.ntu.hrms.dto.TitleDTO;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@WebServlet(
    urlPatterns = {"/addTitle"}
)
public class AddTitle extends HttpServlet {

    @EJB(beanName="TitleBean")
    private TitleBeanLocal titleBean;
    

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
        String page="/addTitle.jsp";
        if(name!=null&&!name.isEmpty())
        {
            TitleDTO titleDTO = new TitleDTO();
            titleDTO.setDescription(name);
            if(titleBean.getTitleByName(name)!=null)
            {
                request.setAttribute("error", "Duplicate Title name is not allowed");
                System.out.println("duplicate title name redirect to page: "+page);
                //page="/addTitle.jsp";
            }
            else
            {
                titleBean.addTitle(titleDTO);
                page = "/titleList";
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
