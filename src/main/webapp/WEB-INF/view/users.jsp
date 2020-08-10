<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
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
         <div class="">
            <div class="page-title">
              <div class="title_left">
                <h3>JustSteph Staff</h3>
              </div>

              <div class="title_right">
                <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
                  <div class="input-group">
                    <input id="keywords" name="keywords" type="text" class="form-control" placeholder="Search for..." onkeyup="searchFilter()">
                    <span class="input-group-btn">
                              <button class="btn btn-default" type="button" onclick="searchFilter()">Go!</button>
                          </span>
                  </div>
                </div>
              </div>
            </div>            
            <div class="row">                
             <div id="dataModal" class="modal fade">
 <div class="modal-dialog modal-lg">
  <div class="modal-content">
   <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">&times;</button>
    <h4 class="modal-title">Rapidbite Staff</h4>
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
            
            
            <div class="row">
     <div class="panel panel-default users-content">
      <div class="panel-heading">
          <div class="post-search-panel">   
    <select id="sortBy">
        
    </select>
    <select id="showLimit">
            
    </select>
              <span class="loading-overlay" style="display:none"><img src="<%= request.getContextPath() %>/assets/build/images/btn-ajax-loader.gif" height="18" width="18" /></span>
    <ul class="nav navbar-right panel_toolbox">
    <li><a href="javascript:void(0);" class="glyphicon glyphicon-plus" id="addLink" onclick="javascript:$('#addUser').slideToggle();">Add</a>
        </li>                     
                      
                    </ul>  
          </div>
          </div>
      
           <div class="panel-body none formData" id="addUser" style="display:none">
                <h2 id="actionLabel">Create User</h2>
                <form class="form" id="addForm">                    
                    <div class="form-group col-md-4 col-sm-6 col-xs-12">
                        <label>Firstname</label>
                        <input type="text" class="form-control" name="firstname" id="firstname"/>
                    </div>
                    <div class="form-group col-md-4 col-sm-6 col-xs-12">
                        <label>Lastname</label>
                        <input type="text" class="form-control" name="lastname" id="lastname"/>
                    </div>
                    <div class="form-group col-md-4 col-sm-6 col-xs-12">
                        <label>Username</label>
                        <input type="username" class="form-control" name="username" id="username"/>
                    </div>  
                     <div class="form-group col-md-4 col-sm-6 col-xs-12">
                        <label>Password</label>
                        <input type="password" class="form-control" name="password" id="password"/>
                    </div> 
                    <div class="form-group col-md-4 col-sm-6 col-xs-12">
                        <label>Password again</label>
                        <input type="password" class="form-control" name="password2" id="password2"/>
                    </div>                                         
                    <div class="form-group col-md-4 col-sm-6 col-xs-12">
                        <label>Role</label>
                        <select name="role" id="role" class="form-control" >   
                            <option value="">Choose</option>
                            <option value="OP">Operator</option>                           
                            <option value="AU">Authorizer</option>                                                                     
                         </select>
                    </div>                      
                    <a href="javascript:void(0);" class="btn btn-warning" onclick="$('#addUser').slideUp();">Cancel</a>
                    <a href="javascript:void(0);" class="btn btn-success" onclick="userAction('add')">Create User</a>
                </form>
            </div>
         <div class="panel-body none formData" id="editForm">
                <h2 id="actionLabel">Edit User </h2>
                <form class="form" id="userForm"> 
                    <input type="text" name="idEdit" id="idEdit"/>
                    <div class="form-group col-md-4 col-sm-6 col-xs-12">
                        <label>First Name</label>
                        <input type="text" class="form-control" name="fnEdit" id="fnEdit"/>
                    </div>
                    <div class="form-group col-md-4 col-sm-6 col-xs-12">
                        <label>Last Name</label>
                        <input type="text" class="form-control" name="lnEdit" id="lnEdit" />
                    </div>
		    <div class="form-group col-md-4 col-sm-6 col-xs-12">
                        <label>Role</label>
                         <select name="roleEdit"  id="roleEdit" class="form-control">                              
                             
                         </select>
                    </div>               
                                       
                    <input type="hidden" name="idEdit2" id="idEdit2" />
                    <a href="javascript:void(0);" class="btn btn-warning" onclick="$('#editForm').slideUp();">Cancel</a>
                    <a href="javascript:void(0);" class="btn btn-success" onclick="userAction('edit')">Update User</a>
                </form>
            </div>
            
   <div id="posts_content" class="table-responsive">        
             <table class="table table-hover table-striped">
                 <thead>
                    <tr>
                        <th></th>
                        <th>FIRST NAME</th>
                        <th>LAST NAME</th>
                        <th>USERNAME</th>  
                        <th>ROLE</th> 
                        <th>DATE CREATED</th>
                        <th>ACTION</th>
                    </tr>
                </thead>
    
             </table>          
                     
    </div> 
    </div>
    </div>   

        <a href="javascript:void(0);" class="btn btn-success" onclick="searchFilter()">Test User Data</a>
         </div>          
        </div>
        <!-- /page content -->

        <!-- footer content -->
        <jsp:include page="include/footer.jsp" />
        <!-- /footer content -->
      </div>
    </div>
	<script src="<%= request.getContextPath() %>/assets/js/fastclick.js"></script> 
	      
    <script>

    function searchFilter(page_num) { 
        page_num = page_num?page_num:0;
        document.cookie = "current_page=" +page_num; 
        
        /*var keywords = $('#keywords').val();
        var sortBy = $('#sortBy').val();
        var showLimit = $('#showLimit').val();
        var tableName = 'users';*/
        var showLimit = 10;
        
        $.ajax({
            type: 'POST',
            url: "rest/user",
            data:'page='+page_num+'&limit='+showLimit,
            beforeSend: function () { //alert("It got to before "+page_num+" limit"+showLimit);
                $('.loading-overlay').show();
            },
            success: function (html) { //alert("Data: "+html);
                $('#posts_content').html(html);
                $('.loading-overlay').fadeOut("slow");
            }
        });
    }    
    
    
    
/* function userAction(type,id){
 if (confirm("Do you want to perform this action?. Click OK to Continue or Cancel to ignore") === true) {    
    id = (typeof id == "undefined")?'':id;
    var statusArr = {add:"added",edit:"updated",delete:"deleted"};
    var userData = '';
    if (type == 'add'){
        userData = $("#addUser").find('.form').serialize()+'&action_type='+type;
    }
    else if (type == 'edit'){
        userData = $("#editForm").find('.form').serialize()+'&action_type='+type;
    }else{
        userData = 'action_type='+type+'&id='+id;
    }
    $.ajax({
        type: 'POST',
        url: '../database/user.php',
        data: userData,
        success:function(msg){
        if($.trim(msg) === "ok"){
                alert('Action Completed successfully.'); 
                setTimeout(' window.location.href = "users.php"; ',400);               
                
        }                  
        else{
                setTimeout(' window.location.href = "users.php"; ',400);  
                //alert(msg);
            }
        }
    });
    }
}

 */
    </script>
    </body>
</html>
