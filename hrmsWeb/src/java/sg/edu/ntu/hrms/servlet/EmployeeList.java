/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.servlet;

import sg.edu.ntu.hrms.servlet.helper.BeanHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.ntu.hrms.ejb.UserBeanLocal;
import sg.edu.ntul.hrms.dto.UserDTO;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@WebServlet(
    urlPatterns = {"/employee"}
)
public class EmployeeList extends HttpServlet {

    @EJB
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
        String dtRange = request.getParameter("dateRange");
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        if(dtRange==null||dtRange.isEmpty())
        {
          Date current = new Date();  
          Calendar frmCal = Calendar.getInstance();
          frmCal.add(Calendar.MONTH, -1);
          String frmDate = formatter.format(frmCal.getTime());
          String toDate  = formatter.format(current);
          StringBuilder sb = new StringBuilder();
          //sb.append(frmDate+" - "+toDate);
          sb.append(frmDate).append(" - ").append(toDate);
          
          request.setAttribute("dateRange",sb.toString());
          RequestDispatcher view = getServletContext().getRequestDispatcher("/employeelist.jsp"); 
          view.forward(request,response);           
        }
        else
        {
          //json
            
          //Get the employees 
          StringTokenizer st = new StringTokenizer(dtRange,"-");
          String stDate = st.nextToken().trim();
          String edDate = st.nextToken().trim();
	  try {

		Date fromDate = formatter.parse(stDate);
                Date toDate = formatter.parse(edDate);

                List<UserDTO> userList = userBean.getAllUsers(fromDate,toDate);
                //convert to json array
                String json = convertToJson(userList,new BeanHelper().getUserTab(userBean));
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.write(json);
                out.flush();
  	  } catch (ParseException e) {
		e.printStackTrace();
	  }
            
            
        }
    }
    /*
    private HashMap getMap(HttpServletRequest request)
    {
        HashMap map;
        if(request.getSession().getAttribute("allUsers")!=null)
        {
            map = (HashMap)request.getSession().getAttribute("allUsers");
        }
        else
        {
            map = new HashMap();
            List<UserDTO> allUsers = new BeanHelper().getAllUsers(userBean);
            for(int i=0;i<allUsers.size();i++)
            {
                UserDTO user = allUsers.get(i);
                map.put(user.getId(), user.getName());
            }
            request.getSession().setAttribute("allUsers", map);
        }
        return map;
    }
    */
    private String convertToJson(List<UserDTO> userList, HashMap map)
    {
        JsonArrayBuilder array = Json.createArrayBuilder();
        for(int i=0;i<userList.size();i++)
        {
            UserDTO user = userList.get(i);
            Date datejoin = user.getDateJoin();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String joinDateStr = formatter.format(datejoin);
            
            String approver = "";
            
             if(map.containsKey(user.getApprover()))
             {
                 approver = (String) map.get(user.getApprover());
             }
            String deptDescr=""; 
            if(user.getDept()!=null)
            {
                deptDescr = user.getDept().getDept().getDescription();
            }
            array.add(
            Json.createObjectBuilder()
                    .add("id", user.getLogin())
                    .add("name",  user.getName())
                    .add("email", user.getEmail())
                    .add("dept", deptDescr)
                    //.add("dept", user.getDept().getDept().getDescription())
                    .add("title", user.getTitle().getDescription())
                    //.add("category","coming")
                    .add("manager",approver)
                    .add("datejoin",joinDateStr)
            );
            
        }
        return array.build().toString();
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
