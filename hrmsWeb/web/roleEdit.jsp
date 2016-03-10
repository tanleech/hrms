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
             $('#system').change(function ()
             {
                if($('#system').val()=='0')
                {
                   //alert('inactive'); 
                   $('select').each(function () {
                       //alert('select: '+this);
                       jQuery(this).val('0');
                   });
                }
             }   
             );
             $('#updateBtn').click(function ()
             {
                 $('#action').val('E');
                 $('#myForm').submit();
             }      
             );
     
             $('#saveBtn').click(function ()
             {
                 //alert('submit');
                 $('#action').val('A');
                 $('#myForm').submit();
                 //window.location.href="roleList";
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
                    Update role
                </c:if> 
                <c:if test="${param.action ne 'U'}">
                   Add Role
                </c:if>   
                <c:if test="${param.action eq 'U'}">
                     <button type="button" class="btn btn-primary pull-right" id="updateBtn">Update</button>
                </c:if> 
                <c:if test="${param.action ne 'U'}">
                     <button type="button" class="btn btn-primary pull-right" id="saveBtn">Save</button>
                </c:if>     
            </h1>    
        </div>
        <br/>
        <!-- Main content -->
        <form action="roleEdit" method="post" id="myForm" class="form-horizontal ">
            <input type="hidden" value="" id="action" name="action"/>
                  <c:if test="${not empty requestScope.error}">
                          <div class="alert alert-danger">
                          ${requestScope.error}
                          </div>
                  </c:if>
            <div class="form-group">
                     <label class=" control-label col-sm-2">Name</label>
                     <div class="col-sm-3">
                         <input type="text" class="form-control" name="name"
                            value='<c:out value="${requestScope.roleData.description}"/>'/>
                     </div>
            </div>
            <c:if test="${param.action ne 'U'}">         
                <c:forEach var="entry" items="${sessionScope.moduleList}">
                <div class="form-group">
                    <c:if test="${entry.name == 'Email Notification'}">
                         <label class=" control-label col-sm-2">Email Notification</label>
                         <div class="col-sm-3">
                            <select class="form-control" id="email" name="${entry.name}">
                               <option value="0">No</option>
                               <option value="1">Yes</option>
                            </select>
                         </div>
                    </c:if>
                    <c:if test="${entry.name != 'Email Notification'}">
                        <c:if test="${entry.name != 'Leave'}">
                         <label class=" control-label col-sm-2"><c:out value="${entry.name}"/></label>
                         <div class="col-sm-3">
                                <select class="form-control" name="${entry.name}">
                                   <option value="0">No Access</option>
                                   <option value="1">View</option>
                                   <option value="2">View & Edit</option>
                                </select>
                         </div>
                        </c:if>
                        <c:if test="${entry.name == 'Leave'}">
                         <label class=" control-label col-sm-2"><c:out value="${entry.name}"/></label>
                         <div class="col-sm-3">
                                <select class="form-control" name="${entry.name}">
                                   <option value="0">No Access</option>
                                   <option value="1">Apply or Approved</option>
                                   <option value="2">View & Edit</option>
                                </select>
                         </div>
                        </c:if>
                         
                    </c:if>               
                </div>                     
                </c:forEach>
            </c:if>
            <c:if test="${param.action eq 'U'}">         
            <c:forEach var="entry" items="${requestScope.roleData.accessList}">
            <div class="form-group">
                <c:if test="${entry.module.name == 'Email Notification'}">
                     <label class=" control-label col-sm-2">Email Notification</label>
                     <div class="col-sm-3">
                        <select class="form-control" id="email" name="${entry.module.name}">
                           <option value="0" ${entry.access == 0 ? 'selected' : ''}>No</option>
                           <option value="1" ${entry.access == 1 ? 'selected' : ''}>Yes</option>
                        </select>
                     </div>
                </c:if>
                <c:if test="${entry.module.name != 'Email Notification'}">
                     <c:if test="${entry.module.name != 'Leave'}">
                     
                       <label class=" control-label col-sm-2"><c:out value="${entry.module.name}"/></label>
                        <div class="col-sm-3">
                            <select class="form-control" name="${entry.module.name}">
                               <option value="0" ${entry.access == 0 ? 'selected' : ''}>No Access</option>
                               <option value="1" ${entry.access == 1 ? 'selected' : ''}>View</option>
                               <option value="2" ${entry.access == 2 ? 'selected' : ''}>View & Edit</option>
                            </select>
                        </div>
                     </c:if>
                     <c:if test="${entry.module.name == 'Leave'}">
                     
                       <label class=" control-label col-sm-2"><c:out value="${entry.module.name}"/></label>
                        <div class="col-sm-3">
                            <select class="form-control" name="${entry.module.name}">
                               <option value="0" ${entry.access == 0 ? 'selected' : ''}>No Access</option>
                               <option value="1" ${entry.access == 1 ? 'selected' : ''}>Apply or Approve</option>
                               <option value="2" ${entry.access == 2 ? 'selected' : ''}>View & Edit</option>
                            </select>
                        </div>
                     </c:if>
                       
                </c:if>               
            </div>                     
            </c:forEach>
            </c:if>
        </form>
            <!-- Main content -->
      </div>
      <%@include file="footer.jsp" %>
  </body>      



</html>

