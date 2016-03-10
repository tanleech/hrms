<%-- 
    Document   : employeelist
    Created on : Feb 3, 2016, 3:17:10 PM
    Author     : sapura-mac-pro-cto-C02PC1MWG3QT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" 
           uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
  <head>
     <%@include file="head.jsp"%>
       <script>
         $(document).ready(function () {
             $('#dateJoin').datepicker(); 
             $('#probDue').datepicker();
             $('#saveBtn').click(function ()
             {
                //$('#action').val('A'); 
                alert('update');
                var url = $('#myForm').attr('action'); 
                $('#action').val('E');
                var frmData = $('#myForm').serialize(); 
                    //ajax post.
                    $.post(url, frmData,
                    function (data,status) {
                        //$('#computedCF').val(data);
                        alert(data);
                        location.reload();
                        
                     }); 
                //$('#myForm').submit();   

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
                 Employee Leave Details
                 <a href="leaveEnt?action=A&id=${requestScope.user.login}"> <button type="button" class="btn btn-primary pull-right" id="addBtn">Add</button>
                 </a>
            </h1>    
        </div>
        <br/>
        <!-- Main content -->

        <!-- Main content -->
        <form action="leaveSettings" method="post" id="myForm" class="form-horizontal">
                    <div class="box-body">

                <span class="content form-control" id="panel" style="height: 100%">
                           <div class="form-group">
                           <label class=" control-label col-sm-1">Annual Accrued</label>
                           <div class="col-sm-3">
                              <fmt:formatNumber type="number" maxFractionDigits="1" value="${requestScope.accured}" var="accrued"/>
                              <input type="text" class="form-control" name="annualAccrued"
                                     value="${accrued}" readonly/>   
                           </div>
                           <label class=" control-label col-sm-2">Carried Over</label>
                           <div class="col-sm-3">
                               
                              <input type="text" class="form-control" name="cf"
                                     value="${requestScope.entAnnual.carriedOver}"/>   
                           </div>
                          </div>
                          <div class="form-group">
                           <label class=" control-label col-sm-1">Total Balance</label>
                           <div class="col-sm-3">
                              <input type="text" class="form-control" name="bal"
                                     value="${requestScope.entAnnual.balance}"readonly/>
                           </div>
                           <label class=" control-label col-sm-2">Computed CF(25%)</label>
                           <div class="col-sm-3">
                              <input type="text" class="form-control" name="computedCF" id="computedCF"
                                     value="${requestScope.entAnnual.carriedOver}"readonly/>   
                           </div>
                           <div class="form-group">
                               <div class="col-sm-1">
                                   <button type="button" class="btn btn-primary pull-left" id="saveBtn">Update</button>
                               </div>
                           </div>
                          </div>
                <table id="entTab" class="table table-bordered table-hover">
                    <thead>
                      <tr> 
                        <th>Leave Type</th>
                        <th>No of days</th>
                        <th>Delete</th>
                        
                      </tr>
                    </thead>
                    <tbody>
                            <c:choose>
                                <c:when test="${!empty requestScope.entList}">
                                    <c:forEach var="entry" items="${requestScope.entList}">
                                        <c:if test="${entry.leaveType.description ne 'Annual'}">
                                        <tr>
                                            <td>
                                              ${entry.leaveType.description}
                                            </td>
                                            <td>
                                                ${entry.current}
                                            </td>
                                            <td>
                                              <a href="leaveEntAdd?action=D&entId=${entry.id}&userId=${requestScope.user.id}&loginId=${requestScope.user.login}"  class="del" id="delBtn">
                                                <span class="glyphicon glyphicon-remove"/>
                                              </a>
                                            </td>
                                            
                                        </tr>
                                            
                                        </c:if>
                                        
                                    </c:forEach>
                                </c:when>
                            </c:choose>                        
                    </tbody>
                </table>
               </span>
            <!-- Main content -->
        </div> 
      </form>                               
      </div>
  </body>      

 <%@include file="footer.jsp" %>

</html>
