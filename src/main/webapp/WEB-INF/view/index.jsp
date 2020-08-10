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
    <title>IMS</title>
    <jsp:include page="include/basic_css.jsp" />  
     
    </head>

  <body style="background-color: #FFF">
   <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
        <div class="container-fluid">				
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="divider"></div>            
			<div class="">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                      <span class="sr-only">Toggle navigation</span>
                      
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
               <img class="sm-margin" width="50" height="50" src="<%= request.getContextPath() %>/assets/images/bpe.png" style="border-radius:5px" />
		                 
            </div>			
            
        </div>
        <!-- /.container -->
    </nav> <br><br><br><br> 
     <div class="row" > 
    <div class="col-md-1"></div>
	
    <div class="col-md-10"> 
            <div class=""> 				
                <center><h2><strong>INVENTORY MANAGEMENT MODULE</strong></h2><!--<img class="sm-margin" height="90" src="home/images/just_steph.jpg" style="border-radius:5px" />--></center> 
            </div>
	<div>
    <center>
        <br><br>
        <div class="col-md-4"></div>
         <div class="col-md-4">
        <h3>Enter your credentials</h3><br>       
        
        <form class="form-signin" method="post" id="login-form">
            
        <div id="error">
        <!-- error will be shown here ! -->
        </div> 
            
        <div class="form-group">
        <input type="text" class="form-control" placeholder="Email" name="email" id="email" />
        <span id="check-e"></span>
        </div>        
        <div class="form-group ">        
        <input type="password" class="form-control" placeholder="Password" name="password" id="password" data-toggle="password" />
        </div>        
            
        <div class="form-group ">
           <button type="button" class="btn btn-default" name="btn-login" id="btn-login" onclick="submitForm()">
      <span class="glyphicon glyphicon-log-in"></span> &nbsp; Sign In
   </button> 
        </div>             
        </form>
    </center> <br>
     </div>
     <div class="col-md-4"></div>
     </div>
    </div>	
<div class="col-md-1"></div>	
  </div>
  <footer class="navbar navbar-fixed-bottom">
          <div class="pull-right">
             <center>&copy;2019</center>
          </div>
          <div class="clearfix"></div>         
</footer>
<jsp:include page="include/basic_js.jsp" />
	      
  <script>
function submitForm()
    {  
             
	  var email = $('#email').val();
	  var password = $('#password').val();
         if(email.length < 1 || password.length < 1 ) {
		 alert("Please enter your credentials to login");
		  //e.preventDefault();
                  return false;
	  }        
       
   $.ajax({    
   type : 'GET',
   url: 'api/login/'+email+'/'+password,
   data: 'id=id',
   beforeSend: function()
   { 
    $("#error").fadeOut();
    $("#btn-login").html('<span class="glyphicon glyphicon-transfer"></span> &nbsp; sending ...');
   },
   success :  function(response)
      {      
     if($.trim(response) === "ok"){          
       $("#btn-login").html(' &nbsp; Signing In ...');
      setTimeout(' window.location.href = "./home"; ',5);
     }    
     else{       
      $("#error").fadeIn(500, function(e){   
          
    $("#error").html('<div class="alert alert-danger"> <span class="glyphicon glyphicon-info-sign"></span> &nbsp; '+response+' !</div>');
           $("#btn-login").html('<span class="glyphicon glyphicon-log-in"></span> &nbsp; Sign In');
           //e.preventDefault();
         });
     }
     }
   });
    return false;     
  } 
</script> 

    </body>
</html>
