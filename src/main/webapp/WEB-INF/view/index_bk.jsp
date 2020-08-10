<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="icon" type="image/jpg" href="<%= request.getContextPath() %>/assets/images/profess.png" /> 
    <title>Rapidbite Template</title>
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
                    <h2>Highest Selling Stock<small>This Month</small></h2>
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
                    <h2>Highest Selling Stock <a onclick="urclick()" class="btn btn-default">click</a><small>Last Thirty(30) Days</small></h2>
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
                    <h2>Monthly Sales<small></small></h2>
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
            
             <!-- footer content -->
        <jsp:include page="include/footer.jsp" />
       
        
         </div>          
        </div>
       
    <jsp:include page="include/basic_js.jsp" />
     
    <script>  
    
    jQuery(document).ready(function($) {

    	$('#msg').html("This is updated by jQuery")

    });

  function urclick(){ 
     alert("script working");
  }
function high_selling_item(){ 
    $.ajax({
        type: 'POST',
        dataType:'JSON',
        url: '../database/price_report.php',
        data:'action_type=monthly',        
        beforeSend: function () {
            $('.loading-overlay').show();
        },
        success: function (html) {              
             var w1 = parseInt(html[0].month_total);   var w2 = parseInt(html[1].month_total);
             var w3 = parseInt(html[2].month_total);   var w4 = parseInt(html[3].month_total); 
             var w5 = parseInt(html[4].month_total);
             var prices = [w1, w2, w3, w4, w5]; 
             var weeks = [html[0].description, html[1].description, html[2].description, html[3].description, html[4].description];
             
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
    });
    
}  
function last_thirty_days(){ 
    $.ajax({
        type: 'POST',
        dataType:'JSON',
        url: '../database/price_report.php',
        data:'action_type=lasthy',        
        beforeSend: function () {
            $('.loading-overlay').show();
        },
        success: function (html) {              
             var w1 = parseInt(html[0].month_total);   var w2 = parseInt(html[1].month_total);
             var w3 = parseInt(html[2].month_total);   var w4 = parseInt(html[3].month_total); 
             var w5 = parseInt(html[4].month_total);
             var prices = [w1, w2, w3, w4, w5]; 
             var weeks = [html[0].description, html[1].description, html[2].description, html[3].description, html[4].description];
             
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
        }
    });
    
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
    </script>    
    </body>
</html>
