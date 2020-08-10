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
              <jsp:include page="include/top_title.jsp" />
         <div class="">            
<div class="row">
              <div class="col-md-6 col-sm-6 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>Highest Requisition<small>This Month</small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                      <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                        <ul class="dropdown-menu" role="menu">
                          <li><a href="#"></a>
                          </li>
                          <li><a href="#"></a>
                          </li>
                        </ul>
                      </li>
                      <li><a class="close-link"><i class="fa fa-close"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <canvas id="high_stock"></canvas>
                  </div>
                </div>
              </div>

              <div class="col-md-6 col-sm-6 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>Highest Ordering <small>Last Thirty(30) Days</small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                      <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                        <ul class="dropdown-menu" role="menu">
                          <li><a href="#"></a>
                          </li>
                          <li><a href="#"></a>
                          </li>
                        </ul>
                      </li>
                      <li><a class="close-link"><i class="fa fa-close"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <canvas id="lasthy"></canvas>
                  </div>
                </div>
              </div>
            </div> 
      <div class="row">
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>Monthly Approved Stock<small></small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                      <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                        <ul class="dropdown-menu" role="menu">
                          <li><a href="#"></a>
                          </li>
                          <li><a href="#"></a>
                          </li>
                        </ul>
                      </li>
                      <li><a class="close-link"><i class="fa fa-close"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <canvas id="rpieChart"></canvas>
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
	 <script src="<%= request.getContextPath() %>/assets/js/Chart.min.js"></script>   
	      
    <script>
    $(document).ready( function () {
    	high_selling_item();	
    	 last_thirty_days();
    	 monthly_report();    	 
    	});
    
    function high_selling_item(){             
                 var w1 = parseInt(40);   var w2 = parseInt(24);
                 var w3 = parseInt(54);   var w4 = parseInt(34); 
                 var w5 = parseInt(56);
                 var prices = [w1, w2, w3, w4, w5]; 
                 var weeks = ["TV Socket","AC Generator", "Adaptor", "Blue Ray Switch", "Audio Recorder"];
                 
                   var ctx = document.getElementById("high_stock").getContext('2d');
                   var myChart = new Chart(ctx, {
                     type: 'bar',
                     data: {
                       labels: weeks,
                       datasets: [{
                         backgroundColor: [
                           "#2ecc71",
                           "#3498db",
                           "#95a5a6",
                           "#9b59b6", 
                           "#9b59b7" 
                         ],
                         data: prices
                       }]
                     }
                   });  
                           
    }  
    function last_thirty_days(){                       
                 var w1 = parseInt(64);   var w2 = parseInt(79);
                 var w3 = parseInt(23);   var w4 = parseInt(86); 
                 var w5 = parseInt(89);
                 var prices = [w1, w2, w3, w4, w5]; 
                 var weeks = ["Laptop","Inverter", "Large Screen", "Globe Switch", "Decoder"];
                 
                   var ctx = document.getElementById("lasthy").getContext('2d');
                   var myChart = new Chart(ctx, {
                     type: 'bar',
                     data: {
                       labels: weeks,
                       datasets: [{
                         backgroundColor: [
                           "#2ecc71",
                           "#3498db",
                           "#95a5a6",
                           "#9b59b6", 
                           "#9b59b7" 
                         ],
                         data: prices
                       }]
                     }
                   });  
                   get_noti();
    } 
    function monthly_report() {  
       var w1 = parseInt(45);   var w2 = parseInt(32);
                 var w3 = parseInt(34);   var w4 = parseInt(2);
                 var prices = [w1, w2, w3, w4]; 
                 var weeks = ['Week 1', 'Week 2', 'Week 3', 'Week 4'];
                 
                   var ctx = document.getElementById("rlineChart").getContext('2d');
                   var myChart = new Chart(ctx, {
                     type: 'line',
                     data: {
                       labels: weeks,
                       datasets: [{
                         backgroundColor: "rgba(153,255,51,0.6)",
                         data: prices
                       }]
                     }
                   });   
                   
                   var ctx = document.getElementById("rbarChart").getContext('2d');
                   var myChart = new Chart(ctx, {
                     type: 'bar',
                     data: {
                       labels: weeks,
                       datasets: [{
                         backgroundColor: [
                           "#2ecc71",
                           "#3498db",
                           "#95a5a6",
                           "#9b59b6"                      
                         ],
                         data: prices
                       }]
                     }
                   });
                   
                   var ctx = document.getElementById("rpieChart").getContext('2d');
                   var myChart = new Chart(ctx, {
                     type: 'pie',
                     data: {
                       labels: weeks,
                       datasets: [{
                         backgroundColor: [
                           "#2ecc71",
                           "#3498db",
                           "#95a5a6",
                           "#9b59b6"                      
                         ],
                         data: prices
                       }]
                     }
                   });
                   
                   var ctx = document.getElementById("rdoughnutChart").getContext('2d');
                   var myChart = new Chart(ctx, {
                     type: 'doughnut',
                     data: {
                       labels: weeks,
                       datasets: [{
                         backgroundColor: [
                           "#2ecc71",
                           "#3498db",
                           "#95a5a6",
                           "#9b59b6"                      
                         ],
                         data: prices
                       }]
                     }
                   });   
                
                
                $('.loading-overlay').fadeOut("slow");   
        
    }
    
    function get_noti(){ 
	
	 $.ajax({
        type: 'GET',	        
        url: 'api/showalert',
        data: 'id=id',
        beforeSend: function () { 
            $('.loading-overlay').show();
        },
        success:function(data){  	                    
        	$('#presentation').html(data);      
        }
    });
   
}    
    
    </script>
    <script>
    
    </script>
    </body>
</html>
