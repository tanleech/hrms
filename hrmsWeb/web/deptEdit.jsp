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
             $('#addBtn').click(function ()
             {
                window.location.href = 'deptEdit?action=A&dept='+$('#department').val();
             }      
             );
             $('#mgr').change(function ()
             {
                   var url = $('#myForm').attr('action'); 
                   $('#action').val('AM');
                   var frmData = $('#myForm').serialize(); 
                    //ajax post.
                    $.post(url, frmData,
                    function (data,status) {
                        location.reload();
                     });   

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
                <c:if test="${param.action eq 'U'}">
                    Update Department
                </c:if>
                <c:if test="${param.action ne 'U'}">
                    Add Department
                </c:if>    
                <button type="button" class="btn btn-primary pull-right" id="addBtn">Add</button>
            </h1>    
        </div>
        <br/>
        <!-- Main content -->
               <div class="box-body">
 
                  <form action="deptEdit" method="post" id="myForm" class="form-horizontal">
                    <input type="hidden" value="" id="action" name="action"/>
                    <span class="content form-control" id="panel" style="height: 100%">
                    <div class="form-group">
                     <label class=" control-label col-sm-1">Name</label>
                     <div class="col-sm-3">
                         <input type="text" class="form-control" name="dept" readonly
                              id="department"  value="${requestScope.dept}"/>
                     </div>
                    </div>
                    <div class="form-group">  
                     <label class=" control-label col-sm-1">Manager</label>
                     <div class="col-sm-3">
                        <select class="form-control" id="mgr" name="manager">
                            <option value="0">None</option>
                            <c:choose>
                                <c:when test="${!empty requestScope.usrList}">
                                    <c:forEach var="entry" items="${requestScope.usrList}">
                                        <option value="<c:out value="${entry.login}"/>" ${requestScope.manager.user.name == entry.name ? 'selected' : ''}><c:out value="${entry.name}"/></option>
                                    </c:forEach>
                                </c:when>
                            </c:choose>
                        </select>                         
                     </div>
                    </div>
                <table id="deptTab" class="table table-bordered table-hover">
                    <thead>
                      <tr> 
                          <th colspan="2">Name</th>
                      </tr>
                    </thead>
                    <tbody>
                         <c:forEach var="emp" items="${requestScope.employeeList}">
                          <tr>   
                            <td width="40%">
                               ${emp.user.name}
                            </td>
                            <td class="pull-right">
                               <a href="deptEdit?action=D&userId=${emp.user.id}&dept=${requestScope.dept}"  class="del" id="delBtn">
                                 <span class="glyphicon glyphicon-remove"/>
                               </a>
                            </td>
                          </tr> 
                        </c:forEach>
                    </tbody>
                </table>
                     
                </span> 
                 </form>
            <!-- Main content -->
               </div>
      </div>
      <%@include file="footer.jsp" %>
  </body>      



</html>

