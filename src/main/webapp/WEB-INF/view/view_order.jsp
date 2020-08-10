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
            <% 				
				String req_id = (String)session.getAttribute("req_id");                
				 if(req_id != null){ %>
				 	 <input type="hidden" name="req_id" id="req_id" value="<%= req_id %>"/> 
				 	 <input type="hidden" name="url_id" id="url_id" value="<%= request.getContextPath() %>"/> 
				 	 
					 
					 <% }
				%>
            
            <div class="row">
     <div class="panel panel-default users-content">          
      <span class="loading-overlay" style="display:none"><img src="<%= request.getContextPath() %>/assets/build/images/btn-ajax-loader.gif" height="18" width="18" /></span>
        
        <div class="panel-body" id="addUser">
                 <div id="posts_content">        
             
    			 </div> 
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
    $(document).ready( function () { 
    	searchFilter();
    	});
    
    //window.onload = function(){ searchFilter(); };
    
    
    function searchFilter() {            
        var id = $("#req_id").val(); 
        var url2 = $("#url_id").val();  
        
       $.ajax({
            type: 'POST',            
            url: url2+'/api/findbyordid',
            data:'id='+id,
            beforeSend: function () { 
                $('.loading-overlay').show();
            },
            success: function (data) {   
                $('#posts_content').html(data);
                $('.loading-overlay').fadeOut("slow");            	
            }
        });
        
    }     
    
    function submit(id,st){ 
    	var url2 = $("#url_id").val();
    	var status = {S:"Submitted", A:"Ordered"};
	    $.ajax({
	        type: 'GET',	        
	        url: url2+'/api/upord/'+id+"/"+st,
	        data: 'id='+id,
	        beforeSend: function () { 
                $('.loading-overlay').show();
            },
	        success:function(data){  	            
	        if($.trim(data) === "ok"){ 	        	
	        	alert("Item(s) "+status[st]+" successfully");
	        	$("#addUser").hide();
	        }
	        else{
	        	alert("Data not submitted");
	        }
	            $('.loading-overlay').fadeOut("slow");      
	        }
	    });
	}      
    
	
  
    </script>
    </body>
</html>
