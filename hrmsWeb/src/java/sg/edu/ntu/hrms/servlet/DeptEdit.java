/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.servlet;

import sg.edu.ntu.hrms.servlet.helper.BeanHelper;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.ntu.hrms.ejb.DeptBeanLocal;
import sg.edu.ntu.hrms.ejb.UserBeanLocal;
import sg.edu.ntu.hrms.dto.DeptDTO;
import sg.edu.ntu.hrms.dto.UserDTO;
import sg.edu.ntu.hrms.dto.UserDeptDTO;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@WebServlet(
    urlPatterns = {"/deptEdit"}
)

public class DeptEdit extends HttpServlet {
    @EJB
    private UserBeanLocal userBean;
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
            String action = request.getParameter("action");
            System.out.println("action: "+action);
            
            String dept = request.getParameter("dept");
            System.out.println("dept: "+dept);
            
            String page = "/deptEdit.jsp";
            
            if(action!=null)
            {   
                if(action.equals("A"))
                {
                        List<UserDTO>userList = new BeanHelper().getAllUsers(userBean);
                        request.setAttribute("usrList", userList);
                        request.setAttribute("dept",dept);
                        page="/deptAddEmp.jsp";

                }
                else if (action.equals("D"))
                {
                    String userId = request.getParameter("userId");
                    DeptDTO deptDto = deptBean.getDepartment(dept);
                    deptBean.unassignEmployee(Integer.parseInt(userId), deptDto.getId());
                    page = "/deptEdit?action=U&dept="+dept;                    
                }
                else if (action.equals("AM"))
                {
                    String emp = request.getParameter("manager");
                    System.out.println("mgr: "+emp);
                    UserDTO empDto = userBean.getUser(emp);
                    empDto.setIsManager(true);
                    //get dept name
                    DeptDTO deptDto = deptBean.getDepartment(dept);
                    deptBean.unassignManager(deptDto.getId());
                    int result = deptBean.assignManager(empDto.getId(),deptDto.getId());
                    if(result==0)
                       deptBean.assignEmployee(empDto, deptDto);
                    page="";
                    /*
                     response.setContentType("text/html");
                     PrintWriter out = response.getWriter();
                     out.write(json);
                     out.flush();
                    */
                }
                else if (action.equals("AS"))
                {
                    //deptBean.addEmployee(userDTO, true);
                    String emp = request.getParameter("empLogin");
                    UserDTO empDto = userBean.getUser(emp);

                    //get dept name
                    //String deptName = request.getParameter("name");
                    DeptDTO deptDto = deptBean.getDepartment(dept);

                    if(deptBean.getUserDept(empDto.getId(), deptDto.getId())!=null)
                    {
                        request.setAttribute("error", "Employee already assigned.");
                        List<UserDTO>userList = new BeanHelper().getAllUsers(userBean);
                        request.setAttribute("usrList", userList);
                        request.setAttribute("dept",dept);
                        page="/deptAddEmp.jsp";
                    }
                    else
                    {
                      deptBean.updateEmployee(empDto.getId(), deptDto.getId());
                      System.out.println("id: "+empDto.getId());
                      //deptBean.assignEmployee(empDto, deptDto);
                      page = "/deptEdit?action=U&dept="+dept;
                    }
                }
                else if (action.equals("U"))
                {
                   DeptDTO deptData = deptBean.getDepartment(dept);
                   //retrieve the manager
                   List<UserDeptDTO> employees = deptData.getEmployees();
                   boolean found=false;
                   int i=0;
                   while(!found && employees!=null && !employees.isEmpty() &&i<employees.size() )
                   {
                       UserDeptDTO emp = employees.get(i);
                       if(emp.getManager()!=null && emp.getManager().equals("Y"))
                       {
                           found=true;
                           request.setAttribute("manager", emp );
                           employees.remove(i);
                       }
                       i++;
                   }
                   List<UserDTO> userList = new BeanHelper().getAllUsers(userBean);
                   request.setAttribute("usrList", userList);
                   request.setAttribute("employeeList", employees);
                   request.setAttribute("dept", dept);
                   page="/deptEdit.jsp";
                }
            }
        if(!page.isEmpty())
        {
           RequestDispatcher view = getServletContext().getRequestDispatcher(page); 
           view.forward(request,response);     
        }   
     }
    
    private List<UserDTO> parseObj(String jsonData)
    {   
        List<UserDTO> results = new ArrayList();
        JsonParser parser = Json.createParser(new StringReader(jsonData));
        while (parser.hasNext()) 
        {
            Event event = parser.next();
            System.out.println("event: "+event.toString());
            if(event.VALUE_STRING.equals(event))
            {
               System.out.println("value: "+parser.getString());
               try
               {
                UserDTO entry = new UserDTO();
                entry.setId(Integer.parseInt(parser.getString()));
                results.add(entry);
               }catch(NumberFormatException numex)
               {
                   System.out.println("not a number");
               }
            }
    
        }
        parser.close();
        
        return results;
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
