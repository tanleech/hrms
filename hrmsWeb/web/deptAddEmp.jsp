<%-- 
    Document   : addDept
    Created on : Feb 15, 2016, 1:26:00 PM
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
             $('#assignBtn').click(function ()
             {
                 $('#action').val('AS');
                 $('#myForm').submit();    
             }      
             );
             $('#mgr').change(function ()
             {
                 alert('change');
             }
             ); 
             $('#deptTab').on('click','.del',function()
                {
                  $(this).closest( 'tr').remove();
                }      
             );
             $('#deptTab').DataTable({
                        "paging": false,
                        "lengthChange": false,
                        "searching": false,
                        "ordering": false,
                        "info": true,
                        "autoWidth": true
                      });
     
         }
          );
     </script>     
  </head>
    
  <body class="hold-transition skin-blue sidebar-mini" >
      <!-- header -->
      <%@include file="header.jsp"%>
      <!-- Left side column. contains the logo and sidebar -->
      <%@include file="sidemenu.jsp"%>
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper" id="main">
          
        <!-- Content Header (Page header) -->
        <div class="content-header">
            <h1>
                 Assign Employee
                <button type="button" class="btn btn-primary pull-right" id="assignBtn">Assign</button>
            </h1>    
        </div>
        <br/>
        <!-- Main content -->
               <div class="box-body">
                  <c:if test="${not empty requestScope.error}">
                      <div class="alert alert-danger">
                          ${requestScope.error}
                      </div>
                  </c:if>  
                  <form action="deptEdit" method="post" id="myForm" class="form-horizontal">
                    <input type="hidden" value="" id="action" name="action"/>
                    <input type="hidden" value="${requestScope.dept}" id="dept" name="dept"/>
                    <span class="content form-control" id="panel" style="height: 100%">
                    <div class="form-group">
                     <label class=" control-label col-sm-1">${requestScope.dept}</label>
                    </div>
                    <div class="form-group">  
                     <label class=" control-label col-sm-1">Employee</label>
                     <div class="col-sm-3">
                        <select class="form-control" id="emp" name="empLogin">
                            <c:choose>
                                <c:when test="${!empty requestScope.usrList}">
                                    <c:forEach var="entry" items="${requestScope.usrList}">
                                        <option value="${entry.login}">${entry.name}</option>
                                    </c:forEach>
                                </c:when>
                            </c:choose>
                        </select>                         
                     </div>
                    </div>
                    </span> 
                 </form>
            <!-- Main content -->
               </div>
      </div>
      <%@include file="footer.jsp" %>
  </body>      



</html>

