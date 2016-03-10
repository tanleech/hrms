<%-- 
    Document   : header
    Created on : Feb 3, 2016, 3:42:03 PM
    Author     : sapura-mac-pro-cto-C02PC1MWG3QT
--%>

<%@page import="com.sapuraglobal.hrms.dto.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>

      <header class="main-header">
        <!-- Logo -->
        <div class="logo">
          <!-- logo for regular state and mobile devices -->
          <span class="logo-lg"><b>Sapura HR System</b></span>
        </div>
        <!-- Header Navbar: style can be found in header.less -->
        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top" role="navigation">
            <!-- Sidebar toggle button-->
            <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
                <span class="sr-only">Toggle navigation</span>
            </a>
            <!-- User Account Menu -->
        <!-- Navbar Right Menu -->
        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                <!-- User Account Menu -->
                <li class="dropdown user user-menu">
                    <!-- Menu Toggle Button -->
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <span class="hidden-xs">
                            <b>
                            <% 
                                UserDTO user = ((UserDTO)session.getAttribute("User"));
                                if(user!=null)
                                {
                            %>        
                                <%=user.getName()%>
                               
                            <%    
                                }
                            %>
                            </b></span>
                    </a>
                    <ul class="dropdown-menu">

                        <!-- Menu Footer-->
                        <li class="user-footer">

                            <div class="pull-right">
                                <a href="logout" class="btn btn-default btn-flat">Sign out</a>
                            </div>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
      </nav>
      </header>

