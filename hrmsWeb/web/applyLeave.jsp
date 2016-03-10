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
     
// Count days from d0 to d1 inclusive, excluding weekends
        function calculateDays( d0, d1 )
        {
            var ndays = 1 + Math.round((d1.getTime()-d0.getTime())/(24*3600*1000));
            var nsaturdays = Math.floor((ndays + d0.getDay()) / 7);
            return ndays - 2*nsaturdays + (d0.getDay()==6) - (d1.getDay()==5);
        }
         $(document).ready(function () {
             $('#submit').click(function ()
             {
                 $('#myForm').submit();    
             }      
             );
             $('#compute').click(function()
             {
                 var d1 = new Date($('#startDate').val());
                 var d2 = new Date($('#endDate').val());
                 var days = calculateDays(d1,d2);
                 if(d1.getDay() === d2.getDay())
                 {
                     if($('#start_slot').val()===$('#end_slot').val())
                     {
                         days = days - 0.5+1;
                     }
                     else
                     {
                         days = days +1;
                     }
                 }
                 else
                 {
                     
                    if($('#start_slot').val()==='AM' && $('#end_slot').val()==='AM')
                     {
                         days = days - 1;
                     }
                 }
                $('#taken').val(days);
                 
             }      
             );
             $('#leaveType').change(function ()
             {
                var type = $('#leaveType').val();
                alert('type: '+type);
                $.get("leaveTxn?action=Q&typeId="+type, function(data, status){
                        alert("Data: " + data + "\nStatus: " + status);
                        //alert(data);
                        $('#balance').val(data);
                        
                    });
             }
             );
             $('#startDate').datepicker();
             $('#endDate').datepicker();
     
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
                 Apply Leave
                <button type="button" class="btn btn-primary pull-right" id="submit">Submit</button>
            </h1>    
        </div>
        <br/>
        <!-- Main content -->
               <div class="box-body">
                  <form action="leaveTxnAdd" method="post" id="myForm" class="form-horizontal">
                    <input type="hidden" value="" id="action" name="action"/>
                    <input type="hidden" value="${requestScope.user.login}" id="login" name="login" />
                    <span class="content form-control" id="panel" style="height: 100%">
                    <div class="form-group">  
                     <label class=" control-label col-sm-1">Type</label>
                     <div class="input-group col-sm-3">
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
                    </div> 
                    <div class="form-group">  
                     <label class=" control-label col-sm-1">Start Date</label>
                     <div class="input-group col-sm-3">
                         <input value="" class="form-control pull-right"  id="startDate" name="startDate"/>
                         <div class="input-group-addon">
                                      <i class="fa fa-calendar"></i>
                         </div>
                         <select class="form-control" id="start_slot" name="start_slot">
                            <option value="AM">
                                AM
                            </option>    
                            <option value="PM">
                                PM
                            </option>    
                        </select>
                     </div>
                    </div>
                    <div class="form-group">  
                     <label class=" control-label col-sm-1">End Date</label>
                        <div class="input-group col-sm-3">
                           <input type="text" class="form-control pull-right" id="endDate" name="endDate"
                                             value=""/>
                                    <div class="input-group-addon">
                                      <i class="fa fa-calendar"></i>
                                    </div>
                            <select class="form-control" id="end_slot" name="end_slot">
                            <option value="PM">
                                PM
                            </option>    
                            <option value="AM">
                                AM
                            </option>    
                        </select>
                        </div>   
                    </div>
                    <div class="form-group">  
                     <label class=" control-label col-sm-1">Total</label>
                     <div class="input-group col-sm-3">
                         <input value="" name="taken" id="taken"/>
                         <button type="button" class="btn btn-primary pull-right" id="compute">Compute</button>
                     </div>
                    </div>                         
                    <div class="form-group">  
                     <label class=" control-label col-sm-1">Balance</label>
                     <div class="input-group col-sm-3">
                         <input value="" name="balance" id="balance"/>
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

