<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">       
    <link rel="icon" type="image/jpg" href="<%= request.getContextPath() %>/assets/images/profess.png" /> 
    <title>IMS Solution</title>
    <jsp:include page="include/basic_css.jsp" />    
  </head>

  <body class="nav-md">
    <div class="container body">
      <div class="main_container">
           <!-- side bar -->
       <jsp:include page="include/sidebar.jsp" />

        <!-- top navigation -->
        <jsp:include page="include/top_navigation.jsp" />
       
        <!-- page content -->
        <div class="right_col" role="main">
         <div class="">                       
            <div class="row">                
             <div id="dataModal" class="modal fade">
 <div class="modal-dialog modal-lg">
  <div class="modal-content">
   <div class="modal-header">
   <a href="<%= request.getContextPath() %>/Requisition" class="close">&times;</a>     
    <h4 class="modal-title">Requisition Status</h4>
   </div>
   <div class="modal-body" id="employee_detail">
    
   </div>
   <div class="modal-footer">
   	<a href="<%= request.getContextPath() %>/Requisition" class="btn btn-default">Close</a>    
   </div>
  </div>
 </div>
</div>                   
            </div>
            
            
            <div class="row">
     <div class="panel panel-default users-content">          
      <span class="loading-overlay" style="display:none"><img src="<%= request.getContextPath() %>/assets/build/images/btn-ajax-loader.gif" height="18" width="18" /></span>
        
        <div class="panel-body formData" id="addUser">
                <h2 id="actionLabel">Other Details</h2>
                <form class="form" id="addForm">                    
                    <div class="form-group col-md-6 col-sm-6 col-xs-12">
                        <label>Reason for Request</label>                       
                         <textarea rows="4" cols="50" class="form-control" name="reason" id="reason"></textarea>                      
                    </div>              
                    <div class="form-group col-md-4 col-sm-6 col-xs-12">
                        <label>Requisition type </label>                        
                        <select name="req_type" id="req_type" class="form-control" >                           
                             <option value="U">Urgent</option>
                             <option selected="selected" value="N">Not Urgent</option>                              		                                                        
                         </select>                        
                    </div>  
                    <div class="form-group col-md-10 col-sm-6 col-xs-12"> 
                    <input type="hidden" name="userId" id="user_id"/>                    
                    <a href="javascript:void(0);" class="btn btn-warning" onclick="$('#addUser').slideUp();">Cancel</a>
                    <a href="javascript:void(0);" class="btn btn-success" onclick="create_request()">Create Request</a>
                	</div>
                </form>
            </div>    
    </div>
    </div>   
       
         </div>          
        </div>
        <!-- /page content -->

        <!-- footer content -->
        <jsp:include page="include/footer.jsp" />
        <!-- /footer content -->
      </div>
    </div>
    
	 <jsp:include page="include/basic_js.jsp" />	     
    <script>
   
    function create_request(){   
    	 var reason = $('#reason').val();      
         var req_type = $('#req_type').val(); 
	    $.ajax({
	        type: 'POST',	        
	        url: 'api/capreq',
	        data: 'reason='+reason+'&req_type='+req_type,
	        beforeSend: function () { 
                $('.loading-overlay').show();
            },
	        success:function(data){	        	
	        $('.loading-overlay').fadeOut("slow"); 
	        if($.trim(data) === "ok"){	
	        $('#addUser').hide();	
	        $('#employee_detail').html("Request placed successfully");
            $('#dataModal').modal({backdrop: false}); 
	        }
	        else{
	        	alert("Error: "+data);
	        }
	                
	        }
	    });
	}       
 
 
  
    </script>
    </body>
</html>
