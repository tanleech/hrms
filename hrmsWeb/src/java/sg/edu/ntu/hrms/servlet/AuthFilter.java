/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.servlet;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sg.edu.ntul.hrms.dto.AccessDTO;
import sg.edu.ntul.hrms.dto.UserDTO;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@WebFilter(
		urlPatterns  = {"/employee","/deptList","/addDept","/employeeEdit",
                                "/addTitle","/titleList","/roleList","/roleEdit",
                                "/leaveEnt","/leaveSettings","/leaveEndAdd","/leaveTxn",
                                "/leaveTxnAdd","/leaveTxnApprove"},
		servletNames = {"employeeEdit","deptList","employee","addDept",
                                "employeeEdit","addTitle","titleList","roleList","roleEdit",
                                "leaveEnt","leaveSettings","leaveEndAdd","leaveTxn",
                                "leaveTxnAdd","leaveTxnApprove"},  
		filterName= "/AuthFilter",
	    dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD}
		)
public class AuthFilter implements Filter {
    
    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    public AuthFilter() {
    }    
    
    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthFilter:DoBeforeProcessing");
        }
 
        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
    }    
    
    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthFilter:DoAfterProcessing");
        }
        
        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
         HttpSession session = ((HttpServletRequest) request).getSession();
         UserDTO user = (UserDTO)session.getAttribute("User");
         HashMap accessList = (HashMap)session.getAttribute("access");
         System.out.println("doFilter");
    	if(session!=null && user !=null)
    	{
    		//authenticated
    		//check the access rights
                //List<AccessDTO> list = user.getRole().getRole().getAccessList();
                //System.out.println("accessList: "+user.getRole().getRole().getAccessList());
                String url = ((HttpServletRequest)request).getRequestURL().toString();
                String module = getModuleName(url);
                String queryString = ((HttpServletRequest)request).getQueryString();
                System.out.println("qry: "+queryString);
                System.out.println("module: "+module);
                if(!hasAccess(module,queryString,accessList,user.isIsManager()))
                {
                    HttpServletResponse httpResponse = (HttpServletResponse) response;
                    httpResponse.sendRedirect(request.getServletContext().getContextPath() + "/noAccess.jsp");
                    
                }
                else
                {

                    
                    chain.doFilter(request, response);
                }
    		
    	}
    	else
    	{
    		HttpServletResponse httpResponse = (HttpServletResponse) response;
    		httpResponse.sendRedirect(request.getServletContext().getContextPath() + "/login");
    	}    
    }
    private boolean hasAccess(String module,String action, HashMap accessTab, boolean isManager)
    {
        boolean hasAccess=false;
        if(module.equals("employee"))
        {
            AccessDTO access =(AccessDTO)accessTab.get("Employee");
            if(access.getAccess()>=1)
            {
                hasAccess=true;
            }
        }
        else if (module.equals("employeeEdit")||module.equals("leaveEnt")||module.equals("leaveEntAdd"))
        {
            AccessDTO access =(AccessDTO)accessTab.get("Employee");
            if(access.getAccess()==2)
            {
                hasAccess=true;
            }
            
        }
        else if (module.equals("deptList"))
        {
            AccessDTO access =(AccessDTO)accessTab.get("Department");
            if(access.getAccess()>=1)
            {
                hasAccess=true;
            }
        }
        else if (module.equals("deptEdit")||module.equals("addDept"))
        {
            AccessDTO access =(AccessDTO)accessTab.get("Department");
            if(access.getAccess()==2)
            {
                hasAccess=true;
            }
            
        }
        else if (module.equals("roleList"))
        {
            AccessDTO access =(AccessDTO)accessTab.get("User Roles");
            if(access.getAccess()>=1)
            {
                hasAccess=true;
            }
            
        }
        else if (module.equals("roleEdit")||module.equals("addRole"))
        {
            AccessDTO access =(AccessDTO)accessTab.get("User Roles");
            if(access.getAccess()==2)
            {
                hasAccess=true;
            }
            
        }
        else if (module.equals("titleList"))
        {
            AccessDTO access =(AccessDTO)accessTab.get("Job Title");
            if(access.getAccess()>=1)
            {
                hasAccess=true;
            }
        }
        else if (module.equals("addTitle"))
        {
            AccessDTO access =(AccessDTO)accessTab.get("Job Title");
            if(access.getAccess()==2)
            {
                hasAccess=true;
            }
            
        }
        else if (module.equals("leaveSettings"))
        {
           AccessDTO access =(AccessDTO)accessTab.get("Leave Setting");
           if(action==null&&access.getAccess()>=1)
           {
                hasAccess=true;
           }
           if(action!=null)
           {
            System.out.println("actionStr: "+action);
            if(action.contains("action=U"))
            {
                 if(access.getAccess()==2)
                 {
                     hasAccess=true;
                 }
            }
            else if(action.contains("action=A")||action.contains("action=D")
                    ||action.contains("action=E"))
            {
                 if(access.getAccess()==2)
                 {
                     hasAccess=true;
                 }

            }    
           }
        }
        else if(module.equals("leaveTxn"))
        {
           AccessDTO access =(AccessDTO)accessTab.get("Leave");
           if(action!=null)
           {
            System.out.println("actionStr: "+action);
            if(action.contains("action=list")||action.contains("action=Q"))
            {
                 if(access.getAccess()>=1)
                 {
                     hasAccess=true;
                 }
            }
           }
           else
           {
                if(access.getAccess()>=1)
                 {
                     hasAccess=true;
                 }
           }
        }
        else if(module.equals("leaveTxnAdd"))
        {
            AccessDTO access =(AccessDTO)accessTab.get("Leave");
            if(access.getAccess()>=1)
            {
                     hasAccess=true;
            }

        }
        else if(module.equals("leaveTxnApprove"))
        {
            AccessDTO access =(AccessDTO)accessTab.get("Leave");
            if(access.getAccess()>=1 && isManager)
            {
                     hasAccess=true;
            }

        }
        
            
        return hasAccess;
    }
    private String getModuleName(String url)
    {

        String last = url.substring(url.lastIndexOf('/') + 1);
        return last; 
        
    }
    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {        
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {                
                log("AuthFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthFilter()");
        }
        StringBuffer sb = new StringBuffer("AuthFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);        
        
        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);                
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");                
                pw.print(stackTrace);                
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);        
    }
    
}
