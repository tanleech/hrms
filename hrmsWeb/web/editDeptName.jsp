<%-- 
    Document   : addDept
    Created on : Feb 15, 2016, 1:26:00 PM
    Author     : sapura-mac-pro-cto-C02PC1MWG3QT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List" %>
<%@ page import="com.sapuraglobal.hrms.dto.DeptDTO" %>

<!DOCTYPE html>
<html>
  <head>
     <%@include file="head.jsp"%>
     
 <script>
         $(document).ready(function () {
             $('#saveBtn').click(function (e)
             {
                 //alert('submit');
                 if($('#name').val()=== '')
                 {
                     alert('Department name cannot be empty.');
                     //$('#name').val('<div class="alert alert-danger">Department name cannot be empty</div>');
                     e.preventDefault();
                 }
                 else
                 {
                     $('#action').val('U');
                     $('#myForm').submit();
                 }
                 //window.location.href="deptList";
             }      
             );
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
      <div class="content-wrapper">
          
        <!-- Content Header (Page header) -->
        <div class="content-header">
            <h1>Edit Department
                <button type="button" class="btn btn-primary pull-right" id="saveBtn">Save</button>
            </h1>    
        </div>
        <br/>
        <!-- Main content -->
                 <form action="addDept" method="post" id="myForm" class="form-horizontal">
                  <span class="content form-control">
                        <c:if test="${not empty requestScope.error}">
                          <div class="alert alert-danger">
                          ${requestScope.error}
                          </div>
                        </c:if>   
                    <div class="form-group">
                        
                     <label class=" control-label col-sm-1">Name</label>
                     <div class="col-sm-3" id="nameContainer">
                        <input type="hidden" value="" name="action" id="action"/> 
                        <input type="hidden" value="${param.dept}" name="oldName" id="oldName"/> 
                        <input type="text" class="form-control" name="name" id="name" value="${param.dept}"/>   
                     </div>
                    </div>
                  </span> 
                 </form>
            <!-- Main content -->
      </div>
      <%@include file="footer.jsp" %>
  </body>      



</html>

