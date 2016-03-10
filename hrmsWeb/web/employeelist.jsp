<%-- 
    Document   : employeelist
    Created on : Feb 3, 2016, 3:17:10 PM
    Author     : sapura-mac-pro-cto-C02PC1MWG3QT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List" %>
<%@ page import="com.sapuraglobal.hrms.dto.UserDTO" %>

 
<!DOCTYPE html>
<html>
  <head>
     <%@include file="head.jsp"%>
     <script>
         function submitDateRange()
         {
              var url = $('#myForm').attr('action'); // get the action of current context form
              var frmData = $('#myForm').serialize(); // get the data of current context form
              //ajax post.
              $.post(url, frmData,
              function (data,status) {
                  //alert(status);
                  //$("#empTab").DataTable({"data":data});
                    render(data);
               });   
         }
         function render(data)
         {
             var arr = JSON.parse(data);
                    var i;
                    var out = "<table id=\"empTab\" class=\"table table-bordered table-hover\">"+
                    "<thead>"+
                    "  <tr> " +
                    "  <th>Employee</th> "+
                    "  <th>Email</th> <th>Department</th><th>Job Title</th><th>Manager</th><th>Date Joined</th></tr>"
                    +"</thead>"+
                    "<tbody>";

                    for(i = 0; i < arr.length; i++) {
                        out += "<tr><td>" +
                        "<a href=employeeEdit?action=U&id=" +arr[i].id+">"        
                        +arr[i].name +
                        "</a></td><td>" +
                        arr[i].email +
                        "</td><td>" +
                        arr[i].dept +
                       "</td><td>" +
                        arr[i].title +
                        "</td><td>" +
                        //arr[i].category +
                        //"</td><td>" +
                        arr[i].manager +
                        "</td><td>" +
                        arr[i].datejoin +
                        "</td></tr>";
                    }
                    out +="</tbody></table>";
                    //document.getElementById("id01").innerHTML = out;
                    //alert(out);
                    //$("#userRows").html(out);
                    $("#tab").html(out);
                    $('#empTab').DataTable({
                        "paging": true,
                        "lengthChange": false,
                        "searching": true,
                        "ordering": true,
                        "info": true,
                        "autoWidth": true,
                        "pageLength": 7
                      });
            }
         $(document).ready(function () {
             $('#Add').click(function ()
             {
                window.location.href="employeeEdit"; 
             }      
             );
             $('#daterange').daterangepicker();
             //$('#daterange').data('daterangepicker').setStartDate(moment().subtract('1','months'));
             //$('#daterange').data('daterangepicker').setEndDate(moment());
             submitDateRange();
             $("#daterange").on("change",function(){
                 submitDateRange();
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
            <h1>Employees  
                <button type="button" class="btn btn-primary pull-right" id="Add">Add</button>
            </h1>    
        </section>
        <!-- Main content -->
        <section class="content">
          <div class="row">
            <div class="col-xs-12">
                   <form action="employee" method="post" id="myForm">
                    <table>
                        <tr>
                            <td>
                                Filter:
                            </td>
                            <td>
                                <div class="form-group">
                                  <div class="input-group">
                                      <input type="text" class="form-control pull-right" id="daterange" name="dateRange"
                                             value="<%=request.getAttribute("dateRange") %>"
                                             />
                                    <div class="input-group-addon">
                                      <i class="fa fa-calendar"></i>
                                    </div>
                                    
                                  </div><!-- /.input group -->
                                </div><!-- /.form group -->                            
                            </td>
                        </tr>
                    </table>
            </form>
                
              <div class="box">
                <div class="box-header">
                </div><!-- /.box-header -->
                <div class="box-body" id="tab">
                </div><!-- /.box-body -->
              </div><!-- /.box -->
            </div>
        <!-- Main content -->
        </section>  
      </div><!-- /.content-wrapper -->
      <%@include file="footer.jsp" %>
  </body>



</html>
