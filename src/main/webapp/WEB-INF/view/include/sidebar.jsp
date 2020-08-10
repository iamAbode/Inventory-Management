 <div class="col-md-3 left_col">
          <div class="left_col scroll-view">
            <div class="navbar nav_title" style="border: 0;">
                <a href="<%= request.getContextPath() %>/home" class="site_title"><i class="fa fa-paw"></i> <span>Back Office</span></a>
            </div>

            <div class="clearfix"></div>

            <!-- menu profile quick info -->
            <div class="profile clearfix">
              <div class="profile_pic">
               <img src="<%= request.getContextPath() %>/assets/images/profess.png" alt="..."  height="50" class="img-circle profile_img" /> 
               </div>
              <div class="profile_info">               
                <h6><?= date("l, M d, Y"); ?></h6>
              </div>
            </div>
            <!-- /menu profile quick info -->
                
            <br />

            <!-- sidebar menu -->
            <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
              <div class="menu_section">
                <h3>Menu</h3>
                <ul class="nav side-menu">
                    <li><a><i class="fa fa-industry"></i>ITEM MANAGEMENT<span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="<%= request.getContextPath() %>/Manage-Item">Capture Item</a></li>                 
                      <li><a href="<%= request.getContextPath() %>/Manage-Category">Create Category</a></li>
                      <li><a href="#">Create Brand</a></li>
                      <li><a href="#">Unit of Measurement</a></li>                      
                    </ul>
                  </li>                  
                    <li><a><i class="fa fa-money"></i>REQUISITION<span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="<%= request.getContextPath() %>/Requisition">Capture Requisition</a></li>  
                      <li><a href="<%= request.getContextPath() %>/All-Requisition">Manage Requisition</a></li>                      
                    </ul>
                  </li>
                  <% String dept = (String)session.getAttribute("dept");
                      if(dept != null && dept.equals("IMSPA123")){    %>
                  <li><a><i class="fa fa-money"></i>ORDERING<span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">                    
                      <li><a href="<%= request.getContextPath() %>/All-Requisition">View Requisition</a></li> 
                      <li><a href="<%= request.getContextPath() %>/Ordering">Capture Order</a></li>  
                      <li><a href="<%= request.getContextPath() %>/All-Orders">Manage Order</a></li>   
                      <li><a href="<%= request.getContextPath() %>/Receipt-Purchase">Receipt of Purchases</a></li> 
                      <li><a href="#">View Pend Items</a></li> 
                      <li><a href="#">Fulfill Requisition</a></li>                    
                    </ul>
                  </li>
                   <%  } %>
                  <li><a><i class="fa fa-compress"></i> STORE<span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                     <li><a href="<%= request.getContextPath() %>/Receive-Goods">Receive Goods</a></li>    
                     <li><a href="#">Release Goods</a></li>                   
                    </ul>
                  </li> 
                 
                  <li><a><i class="fa fa-bar-chart"></i> MIS & REPORTS<span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="#">Stock Register</a></li> 
                      <li><a href="#">Transfer Register</a></li>
                      <li><a href="#">Stock Card</a></li>
                      <li><a href="#">Item Purchased</a></li>
                      <li><a href="#">Item Pending Receipt</a></li>    
                      <li><a href="#">Good Received Note</a></li> 
                      <li><a href="#">Item Yearly Purchases</a></li>
                      <li><a href="#">Item Monthly Purchases</a></li> 
                      <li><a href="#">Monthly Requisition Fulfilled</a></li>
                    </ul>
                  </li>                 
                  <li><a><i class="fa fa-user-md"></i>SYS ADMIN<span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="<%= request.getContextPath() %>/Manage-Departments">Manage Departments</a></li>
                      <li><a href="<%= request.getContextPath() %>/Manage-Users">Manage Users</a></li> 
                      <li><a href="<%= request.getContextPath() %>/Manage-Vendors">Manage Vendors</a></li>                                      
                    </ul>
                  </li> 
                   
                </ul>
              </div>
              
            </div>
            <!-- /sidebar menu -->

            <!-- /menu footer buttons -->
            <div class="sidebar-footer hidden-small">
              <a data-toggle="tooltip" data-placement="top" title="Settings">
                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="FullScreen">
                <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="Lock">
                <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="Logout" href="../logout.php">
                <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
              </a>
            </div>
            <!-- /menu footer buttons -->
          </div>
        </div>