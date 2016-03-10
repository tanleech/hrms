<%-- 
    Document   : titlelist
    Created on : Feb 3, 2016, 3:17:10 PM
    Author     : sapura-mac-pro-cto-C02PC1MWG3QT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.util.List" %>
<%@ page import="com.sapuraglobal.hrms.dto.DeptDTO" %>


<!DOCTYPE html>
<html>
  <head>
     <%@include file="head.jsp"%>
     <script>
         $(document).ready(function () {
             $('#Add').click(function ()
             {
                window.location.href="addTitle"; 
             }      
             );
             $('#deptTab').DataTable({
                        "paging": true,
                        "lengthChange": false,
                        "searching": true,
                        "ordering": true,
                        "info": true,
                        "autoWidth": true,
                        "pageLength": 7
                      });
         }
          );
     </script>
  </head>


    
  <body class="hold-transition skin-blue sidebar-mini">
      <!-- header -->
      <%@include file="header.jsp"%>
      <!-- Left side column. contains the logo and sidebar -->
      <%@include file="sidemenu.jsp"%>
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>Job Titles  
                <button type="button" class="btn btn-primary pull-right" id="Add">Add</button>
            </h1>    
        </section>
        <!-- Main content -->
        <section class="content">
          <div class="row">
            <div class="col-xs-12">
              <div class="box">
                <div class="box-header">
                </div><!-- /.box-header -->
                <div class="box-body" id="tab">
                <table id="deptTab" class="table table-bordered table-hover">
                    <thead>
                      <tr> 
                        <th>Job Title</th>
                        <th>Employees</th>
                      </tr>
                    </thead>
                    <tbody>
                            <c:choose>
                                <c:when test="${!empty requestScope.titleList}">
                                    <c:forEach var="entry" items="${requestScope.titleList}">
                                        <tr>
                                            <td>
                                                <c:out value="${entry.description}"/>                                                
                                            </td>
                                            <td>
                                                <c:out value="${fn:length(entry.employees)}"/>                                                
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                            </c:choose>                        
                    </tbody>
                </table>    
                </div><!-- /.box-body -->
              </div><!-- /.box -->
            </div>
        <!-- Main content -->
        </section>  
      </div><!-- /.content-wrapper -->
      <%@include file="footer.jsp" %>
  </body>



</html>
