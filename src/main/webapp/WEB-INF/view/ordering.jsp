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
    <link href="<%= request.getContextPath() %>/assets/css/smart_cart.min.css" rel="stylesheet" type="text/css" />
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
    <button type="button" class="close" data-dismiss="modal">&times;</button>
    <h4 class="modal-title">Detail</h4>
   </div>
   <div class="modal-body" id="employee_detail">
    
   </div>
   <div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
   </div>
  </div>
 </div>
</div>                   
            </div>
            
            
            <div class="row" style="margin-top:-30px">
     <div class="panel panel-default users-content">          
           
         <div class="panel-body formData" id="addUser" style="margin-top:-30px">
             <div class="row" >
            <div class="col-md-6">
               <h4>Enter Item code:</h4>  <input type="text" name="item_code" id="item_code" class="form-control" />  
              </div>
              <div class="col-md-6">
               
              </div>
                </div>          
                
                <div class="row">
            <div class="col-md-6">
                <div class="panel panel-default">                	
                    <div class="panel-heading">                        
                        <select id="product_cat" class="form-control" onchange="addDiv()" >
                          <option value="">Choose Item </option>
                        </select>                       
                    </div>
                    <div class="panel-body">
                        <div class="row" id="product_show">
                            <!-- BEGIN PRODUCTS -->                                             
                            <div class="col-md-12 col-sm-12">
                                <div class="sc-product-item thumb">
                                     <div class="caption">
                                        <h4 data-name="product_name" id="product_name"> </h4>
                                        <p data-name="product_desc" id="product_desc" > </p>
                                        <hr class="line">
                                        
                                        <div>                                      
                                            <div class="form-group2">
                                                <input class="sc-cart-item-qty" name="product_quantity" min="1" value="1" type="number">
                              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <small id="bonus_percent"> in stock</small>
                                            </div> <br />
                                            <strong class="price pull-left" id="price"></strong>
                                             
                                            <input name="product_price" id="product_price" value="" type="hidden" />
                                            <input name="product_id" id="product_id" value=" " type="hidden" />
                                            
                                            <button class="sc-add-to-cart btn btn-success btn-sm pull-right">Add to cart</button>
                                        </div>                                       
                                        <div class="clearfix"></div>                                         
                                    </div>                                    
                                </div>                              
                                
                            </div>   
                            <!-- END PRODUCTS -->
                        </div>
                    </div>
                </div>
                
            </div>
            <div class="col-md-1"></div>
            <aside class="col-md-5" style="margin-top:-50px">
                
                <!-- Cart submit form -->
                <form action="<%= request.getContextPath() %>/Show-Order" method="POST"> 
                    <!-- SmartCart element -->
                    <div id="smartcart"></div>
                </form>
                
            </aside>
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
	 <script src="<%= request.getContextPath() %>/assets/js/jquery.smartCart.min.js" type="text/javascript"></script>     
    <script>
    $(document).ready( function () {    	
    	$('#smartcart').smartCart();
    	getAllItems();
    	});    
    
    
    function viewitem(id){     	
	    $.ajax({
	        type: 'GET',	        
	        url: 'api/viewitem/'+id,
	        data: 'id='+id,
	        beforeSend: function () { 
                $('.loading-overlay').show();
            },
	        success:function(data){ 
	        	
	        	$('#employee_detail').html(data);
	            $('#dataModal').modal({backdrop: false});           
	            $('.loading-overlay').fadeOut("slow");  	            
	                
	        }
	    });
	}       
 function userAction(type,id){	 
 if (confirm("Do you want to "+type+" item?. Click OK to Continue or Cancel to ignore") === true) {    
    //id = (typeof id == "undefined")?'':id;
    var statusArr = {add:"added",edit:"updated",delete:"deleted"};
    var userData = '';
    var url = '';    
    if (type === 'add'){         
        userData = $("#addUser").find('.form').serialize(); 
        url = 'api/additem';
    }
    else if (type === 'edit'){        	      
        userData = $("#editForm").find('.form').serialize();
        url = 'api/upitem';        
    }else{
    	url = 'api/delitem/'+id;
        userData = 'id='+id;
    }    
      
    $.ajax({
        type: 'POST',
        url: url,
        data: userData,
        beforeSend: function () { 
            $('.loading-overlay').show();
        },
        success:function(msg){
        if($.trim(msg) === "ok"){
                alert('Item '+statusArr[type]+' successfully.'); 
                var allcookies = document.cookie;              
                var cookiearray = allcookies.split(';'); 
                var value = 0;
                value = cookiearray[0].split('=')[1];                
                value = parseInt(value);
                searchFilter(value);                
               $('.form')[0].reset();
               $('.formData').slideUp();                
                //setTimeout(' window.location.href = "users.php"; ',400);             
        }                  
        else{
                //setTimeout(' window.location.href = "users.php"; ',400);  
                alert("Error: "+msg);
            }
        $('.loading-overlay').fadeOut("slow");      
        }
    });
    }
}
 function addDiv(){ 
     var  sel = $('#product_cat').val();
     var content = sel.split(":");            
     $("#product_id").val(content[0]);
      $("#product_price").val(content[3]);
       $("#product_name").html(content[1]);
       var price = parseInt(content[3]);
        price = price.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
        $("#price").html(price); 
         $("#product_desc").html(content[2]); 
         $("#bonus_percent").html(content[4]);               
         $("#edit_price").show();
         $("#npdiv").hide();
                 } 
 
 function getAllItems(){     	
	    $.ajax({
	        type: 'GET',
	        dataType:'JSON',
	        url: 'api/allitems',
	        data: 'id=all',
	        beforeSend: function () { 
             $('.loading-overlay').show();
           },
	        success:function(data){         
	        for (var i=0; i < data.length; i++) {  
  $("#product_cat").append("<option value='"+data[i].code+":"+data[i].name+":"+data[i].category+":"+data[i].price+":"+data[i].balance+" in stock :"+data[i].maxLevel+"'>"+ data[i].name +"</option>");
	        
             }  
	        $('.loading-overlay').fadeOut("slow");      
	        }
	    });
	}  
 
  
    </script>
    </body>
</html>
