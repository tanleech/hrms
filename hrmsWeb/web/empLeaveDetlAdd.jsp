<%-- 
    Document   : addDept
    Created on : Feb 15, 2016, 1:26:00 PM
    Author     : sapura-mac-pro-cto-C02PC1MWG3QT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.util.List" %>

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
             $('#leaveType').change(function ()
             {
                 var type = $('#leaveType').val();
                $.get("leaveEntAdd?action=T&typeId="+type, function(data, status){
                        //alert("Data: " + data + "\nStatus: " + status);
                        //alert(data);
                        $('#days').val(data);
                        
                    });
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
      <div class="content-wrapper" id="main">
          
        <!-- Content Header (Page header) -->
        <div class="content-header">
            <h1>
                 Assign Leave Entitlement
                <button type="button" class="btn btn-primary pull-right" id="assignBtn">Assign</button>
            </h1>    
        </div>
        <br/>
        <!-- Main content -->
               <div class="box-body">
                  <form action="leaveEntAdd" method="post" id="myForm" class="form-horizontal">
                    <input type="hidden" value="" id="action" name="action"/>
                    <input type="hidden" value="${requestScope.user.login}" id="login" name="login" />
                    <span class="content form-control" id="panel" style="height: 100%">
                    <div class="form-group">
                     <label class=" control-label col-sm-1">${requestScope.user.name}</label>
                    </div>
                    <div class="form-group">  
                     <label class=" control-label col-sm-1">Leave Entitlement</label>
                     <div class="col-sm-3">
                        <select class="form-control" id="leaveType" name="leaveType">
                            <option value="0">
                                None
                            </option>    
                            <c:choose>
                                <c:when test="${!empty requestScope.typeList}">
                                    <c:forEach var="entry" items="${requestScope.typeList}">
                                        <option value="${entry.id}">${entry.description}</option>
                                    </c:forEach>
                                </c:when>
                            </c:choose>
                        </select>                         
                     </div>
                    <div class="form-group">  
                     <label class=" control-label col-sm-1">No of days</label>
                     <div class="col-sm-3">
                         <input value="" id="days" name="days" readonly/>
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

