<%-- 
    Document   : employeelist
    Created on : Feb 3, 2016, 3:17:10 PM
    Author     : sapura-mac-pro-cto-C02PC1MWG3QT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" 
           uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sapuraglobal.hrms.dto.TitleDTO" %>

<!DOCTYPE html>
<html>
  <head>
     <%@include file="head.jsp"%>
       <script>
         $(document).ready(function () {
             /*
             $("#dateJoin").on("change",function(){
                 alert('join date');
             });
             */
             $('#dateJoin').datepicker(); 
             $('#probDue').datepicker(); 
             $('#saveBtn').click(function ()
             {
                var errMsg = '';
                if($('#name').val()==='')
                {
                    //alert('Name cannot be empty.');
                    errMsg=errMsg+'Name cannot be empty.\n';
                }
                if($('#email').val()==='')
                {
                    //alert('email cannot be empty.');
                    errMsg=errMsg+'Email cannot be empty.\n';
                }
                if($('#login').val()==='')
                {
                    errMsg=errMsg+'Login cannot be empty.\n';
                }
                if($('#dateJoin').val()==='')
                {
                    errMsg=errMsg+'Date Join cannot be empty.\n';
                }
                if($('#probDue').val()==='')
                {
                    errMsg=errMsg+'Probation Date cannot be empty.\n';
                }
                if($('#base').val()==='')
                {
                    errMsg=errMsg+'Base Leave cannot be empty.\n';
                }
                if($('#max').val()==='')
                {
                    errMsg=errMsg+'Max Leave cannot be empty.\n';
                    
                }
                if (errMsg === '')
                {
                    //$('#action').val('A'); 
                    $('#myForm').submit();   
                }
                else
                {
                    alert(errMsg);
                }
                
             }
             );
             
         });
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
                    Employee Details
                </c:if>
                <c:if test="${param.action ne 'U'}">
                    Add Employee
                </c:if>    
                <button type="button" class="btn btn-primary pull-right" id="saveBtn">Save</button>
            </h1>    
        </div>
        <br/>
        <!-- Main content -->
                 <form action="employeeEdit" method="post" id="myForm" class="form-horizontal">
     <div class="box-body">
         <c:if test="${param.action eq 'U'}">
                    <input type="hidden" value="E" id="action" name="action"/>
         </c:if>
         <c:if test="${param.action ne 'U'}">
                    <input type="hidden" value="A" id="action" name="action"/>
         </c:if>
                  <input type="hidden" value="${requestScope.user.id}" name="userId"/>
                  
                       <c:if test="${not empty requestScope.error}">
                          <div class="alert alert-danger">
                          ${requestScope.error}
                          </div>
                        </c:if>   

                  <span class="content form-control ">
                    <div class="form-group">
                     <label class=" control-label col-sm-1">*Name</label>
                     <div class="col-sm-3">
                        <input type="text" class="form-control" name="name" id="name"
                               value="${requestScope.user.name}"/>   
                     </div>
                     <label class=" control-label col-sm-2">*Email</label>
                     <div class="col-sm-3">
                        <input type="text" class="form-control" name="email" id="email"
                               value="${requestScope.user.email}"/>   
                     </div>
                    </div>
                      
                    <div class="form-group">
                     <label class=" control-label col-sm-1">Department</label>
                     <div class="col-sm-3">
                        <select class="form-control" id="dept" name="dept">
                            <c:choose>
                                <c:when test="${!empty requestScope.deptList}">
                                    <c:forEach var="entry" items="${requestScope.deptList}">
                                        <option value="${entry.id}"
                                                ${requestScope.user.dept.dept.id == entry.id ? 'selected' : ''}>
                                            ${entry.description}
                                        </option>
                                    </c:forEach>
                                </c:when>
                            </c:choose>
                        </select>                          
                     </div>
                     <label class=" control-label col-sm-2">Mobile</label>
                     <div class="col-sm-3">
                        <input type="text" class="form-control" name="mobile" id="mobile"
                               value="${requestScope.user.phone}"/>   
                     </div>
                    </div> 
                    <div class="form-group">
                     <label class=" control-label col-sm-1">Job Title</label>
                     <div class="col-sm-3">
                        <select class="form-control" id="title" name="title">
                            <c:choose>
                                <c:when test="${!empty requestScope.titleList}">
                                    <c:forEach var="titleEntry" items="${requestScope.titleList}">
                                        <option value="${titleEntry.id}"
                                                ${requestScope.user.title.id == titleEntry.id ? 'selected' : ''}>
                                            ${titleEntry.description}
                                        </option>
                                    </c:forEach>
                                </c:when>
                            </c:choose>
                        </select> 
                     </div>
                     <label class=" control-label col-sm-2">Office</label>
                     <div class="col-sm-3">
                        <input type="text" class="form-control" name="office"
                               value="${requestScope.user.office}"/>   
                     </div>
                    </div> 
                    <div class="form-group">
                     <label class=" control-label col-sm-1">Reports:</label>
                     <div class="col-sm-3">
                        <select class="form-control" id="mgr" name="mgr">
                            <option value="0">None</option>
                            <c:choose>
                                <c:when test="${!empty requestScope.mgrList}">
                                    <c:forEach var="entry" items="${requestScope.mgrList}">
                                        <option value="${entry.id}"  
                                                 ${requestScope.user.approver == entry.id ? 'selected' : ''}>
                                        ${entry.name}
                                        </option>
                                    </c:forEach>
                                </c:when>
                            </c:choose>
                        </select>                      
                     </div>
                     <label class=" control-label col-sm-2">User Role</label>
                     <div class="col-sm-3">
                        <select class="form-control" id="role" name="role">
                            <c:choose>
                                <c:when test="${!empty requestScope.roleList}">
                                    <c:forEach var="entry" items="${requestScope.roleList}">
                                        <option value="${entry.id}"
                                                 ${requestScope.user.role.role.id == entry.id ? 'selected' : ''}>
                                            ${entry.description}
                                        </option>
                                    </c:forEach>
                                </c:when>
                            </c:choose>
                        </select>                         
                     </div>
                    </div>
                  </span>
                  
                  <span class="content form-control">
                    <div class="form-group">
                     <label class=" control-label col-sm-1">*Login</label>
                     <div class="col-sm-3">
                         <input type="text" class="form-control" name="login" id="login"
                                value="${requestScope.user.login}"/>   
                     </div>
                    </div>
                      
                    <div class="form-group">
                     <label class=" control-label col-sm-1">*Date Joined</label>
                     <div class="col-sm-3">
                        <fmt:formatDate pattern="MM/dd/yyyy" value="${requestScope.user.dateJoin}" var="join"/>                               
                         
                        <input type="text" class="form-control" id="dateJoin" name="dateJoin"
                               value="${join}"/>   
                     </div>
                     <label class=" control-label col-sm-2">*Probation End</label>
                     <div class="col-sm-3">
                        <fmt:formatDate pattern="MM/dd/yyyy" value="${requestScope.user.probationDue}" var="prob"/>                               
                         
                        <input type="text" class="form-control" id="probDue" name="probDue"
                               value="${prob}"/>   
                     </div>
                    </div> 
                    <div class="form-group">
                     <label class=" control-label col-sm-1">*Leave Entitlement</label>
                     <div class="col-sm-3">
                        <input type="text" class="form-control" id="base" name="base"
                               value="${requestScope.entAnnual.current}"/>   
                     </div>
                     <label class=" control-label col-sm-2">*Max Leave<br/>Entitlement</label>
                     <div class="col-sm-3">
                        <input type="text" class="form-control" name="max" id="max"
                               value="${requestScope.entAnnual.max}"/>   
                     </div>
                    </div> 
                    <c:if test="${param.action eq 'U'}">
                                   
                    <div class="form-group">
                           <label class=" control-label col-sm-1">Leave Balance</label>
                           <div class="col-sm-3">

                              <input type="text" class="form-control" name="balance"
                                     value="${requestScope.entAnnual.balance}"/>   
                           </div>
                    </div> 
                    </c:if>       
                    <!-- 
                    <div class="form-group">
                     <label class=" control-label col-sm-1">*Current Leave<br/>Balance</label>
                     <div class="col-sm-3">
                        <input type="text" class="form-control" name="balance" id="balance"
                               value="${requestScope.ent.balance}"/>   
                     </div>
                    </div>
                    -->
                      
                    <!--     
                    <div class="form-group">
                     <label class=" control-label col-sm-1">Category</label>
                     <div class="col-sm-3">
                        <select class="form-control" id="title">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                        </select>                         
                     </div>
                    </div> 
                    -->
                  <c:if test="${param.action eq 'U'}">
  
                   <a href ='leaveEnt?action=U&id=${requestScope.user.login}' ><button type="button" class="btn btn-primary pull-right" id="nextBtn">Edit Leave Entitlement</button></a>
                  </c:if>
                  </span> 
                        </div>  
                   
                 </form>
            <!-- Main content -->
      </div>
      <%@include file="footer.jsp" %>
  </body>      



</html>
