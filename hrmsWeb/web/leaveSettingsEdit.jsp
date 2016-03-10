<%-- 
    Document   : addDept
    Created on : Feb 15, 2016, 1:26:00 PM
    Author     : sapura-mac-pro-cto-C02PC1MWG3QT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" 
       uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sapuraglobal.hrms.dto.DeptDTO" %>

<!DOCTYPE html>
<html>
  <head>
     <%@include file="head.jsp"%>
     
 <script>
         $(document).ready(function () {
             $('#saveBtn').click(function ()
             {
                 //alert('submit');
                <c:if test="${param.action eq 'U'}">
                 $('#action').val('E');
                </c:if>
                <c:if test="${param.action eq 'U'}">
                 $('#action').val('A');
                </c:if>
                    
                 
                 $('#myForm').submit();
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
            <h1>
                <c:if test="${param.action eq 'U'}">
                    Update Leave Setting
                </c:if>
                <c:if test="${param.action ne 'U'}">
                    Add Leave Setting
                </c:if>    
                <button type="button" class="btn btn-primary pull-right" id="saveBtn">Save</button>
            </h1>    
        </div>
        <br/>
        <!-- Main content -->
                 <form action="leaveSettings" method="post" id="myForm" class="form-horizontal">
                  <input type="hidden" value="${requestScope.leaveType.id}" id="id" name="id"/>
                  <input type="hidden" value="${param.action}" id="action" name="action"/>
                  <c:if test="${not empty requestScope.error}">
                          <div class="alert alert-danger">
                          ${requestScope.error}
                          </div>
                  </c:if>
                  <span class="content form-control">
                    <div class="form-group">
                     <label class=" control-label col-sm-1">Leave Type:</label>
                     <div class="col-sm-3">
                        <input type="text" class="form-control" name="leaveType"
                               value="${requestScope.leaveType.description}"/>   
                     </div>
                     <label class=" control-label col-sm-2">Entitlement:</label>
                     <div class="col-sm-3">
                        <input type="text" class="form-control" name="ent"
                               value="${requestScope.leaveType.days}"/>   
                     </div>
                    </div>
                    <div class="form-group">
                     <label class=" control-label col-sm-1">Year Increment:</label>
                     <div class="col-sm-3">
                        <input type="text" class="form-control" name="annualIncre"
                               value="${requestScope.leaveType.annualIncre}"/>   
                     </div>
                     <label class=" control-label col-sm-2">Carried Forward %</label>
                     <div class="col-sm-3">
                        <input type="text" class="form-control" name="cf"
                               value="${requestScope.leaveType.carriedForward}"/>   
                     </div>
                    </div>
                      
                   <div class="form-group">
                     <label class=" control-label col-sm-1">Policy:</label>
                     <div class="col-sm-3">
                         <select class="form-control" name="mandatory">
                             <option value="Y" ${requestScope.leaveType.mandatory == 'Y' ? 'selected' : ''}>Default</option>
                             <option value="N" ${requestScope.leaveType.mandatory == 'N' ? 'selected' : ''}>Optional</option>
                         </select>
                     </div>
                    </div>                      
                  </span> 
                 </form>
            <!-- Main content -->
      </div>
      <%@include file="footer.jsp" %>
  </body>      



</html>

