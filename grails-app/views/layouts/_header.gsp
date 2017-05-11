

<header class="main-header">
    <!-- Logo -->
    <a href="index2.html" class="logo">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini"><b>A</b>LT</span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg"><b>Admin</b>LTE</span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
        <!-- Sidebar toggle button-->
        <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
        </a>

        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                <!-- Messages: style can be found in dropdown.less-->
                <li class="dropdown messages-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-envelope-o"></i>
                        <span class="label label-success">4</span>
                    </a>
                    <ul class="dropdown-menu">
                        <li class="header">You have 4 messages</li>
                        <li>
                            <!-- inner menu: contains the actual data -->
                            <ul class="menu">
                                <li><!-- start message -->
                                    <a href="#">
                                        <div class="pull-left">
                                            <img src="${assetPath(src:'user2-160x160.jpg')}" class="img-circle" alt="User Image">
                                        </div>
                                        <h4>
                                            Support Team
                                            <small><i class="fa fa-clock-o"></i> 5 mins</small>
                                        </h4>
                                        <p>Why not buy a new awesome theme?</p>
                                    </a>
                                </li>
                                <!-- end message -->
                                <li>
                                    <a href="#">
                                        <div class="pull-left">
                                            <img src="${assetPath(src:'user3-128x128.jpg')}" class="img-circle" alt="User Image">
                                        </div>
                                        <h4>
                                            AdminLTE Design Team
                                            <small><i class="fa fa-clock-o"></i> 2 hours</small>
                                        </h4>
                                        <p>Why not buy a new awesome theme?</p>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <div class="pull-left">
                                            <img src="${assetPath(src:'user4-128x128.jpg')}" class="img-circle" alt="User Image">
                                        </div>
                                        <h4>
                                            Developers
                                            <small><i class="fa fa-clock-o"></i> Today</small>
                                        </h4>
                                        <p>Why not buy a new awesome theme?</p>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <div class="pull-left">
                                            <img src="${assetPath(src:'user3-128x128.jpg')}" class="img-circle" alt="User Image">
                                        </div>
                                        <h4>
                                            Sales Department
                                            <small><i class="fa fa-clock-o"></i> Yesterday</small>
                                        </h4>
                                        <p>Why not buy a new awesome theme?</p>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <div class="pull-left">
                                            <img src="${assetPath(src:'user4-128x128.jpg')}" class="img-circle" alt="User Image">
                                        </div>
                                        <h4>
                                            Reviewers
                                            <small><i class="fa fa-clock-o"></i> 2 days</small>
                                        </h4>
                                        <p>Why not buy a new awesome theme?</p>
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <li class="footer"><a href="#">See All Messages</a></li>
                    </ul>
                </li>
                <!-- Notifications: style can be found in dropdown.less -->
                <li class="dropdown notifications-menu">

                    <sec:ifLoggedIn>
                        <%
                        def notificationUnreadList = notificationService.notificationInfo().notificationUnreadList
                        def totalNotificationUnread = notificationService.notificationInfo().notificationUnread
                        %>
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-bell-o"></i>
                            <span class="label label-warning" id="notificationNum">${totalNotificationUnread}</span>
                        </a>

                        <ul class="dropdown-menu">
                            <li class="header" id="totalmessage"></li>
                            <li>
                                <!-- inner menu: contains the actual data -->
                                <ul class="menu" id="messageList">
                                    <g:each var="notificationMessage" in="${notificationUnreadList}" status="stt">
                                        <%
                                            def flag = false;
                                        %>
                                        <sec:access expression="hasRole('ROLE_MANAGER')">
                                            <%
                                             flag = true;
                                            %>
                                        </sec:access>
                                        <g:if test="${flag}">

                                            <a href="${createLink(action: 'checkOvertime', controller: 'overTime', params:[overTimeMasterId: notificationMessage.masterId, notificationId: notificationMessage.id])}">
                                                <i class="fa fa-users text-aqua"></i>${notificationMessage.message}
                                            </a>
                                        </g:if><g:else>
                                            <a href="${createLink(action: 'employeeCheckAgain', controller: 'overTime', params:[overTimeMasterId: notificationMessage.masterId, notificationId: notificationMessage.id])}">
                                                <i class="fa fa-users text-aqua"></i>${notificationMessage.message}
                                            </a>
                                    </g:else>

                                        <!--<sec:ifAllGranted roles='ROLE_MANAGER'>
                                            <li>
                                                <a href="${createLink(action: 'checkOvertime', controller: 'overTime', params:[overTimeMasterId: notificationMessage.masterId, notificationId: notificationMessage.id])}">
                                                    <i class="fa fa-users text-aqua"></i>${notificationMessage.message}
                                                </a>
                                            </li>
                                        </sec:ifAllGranted>
                                        <sec:ifAllGranted roles='ROLE_EMPLOYEE'>
                                            <li>
                                                <a href="${createLink(action: 'employeeCheckAgain', controller: 'overTime', params:[overTimeMasterId: notificationMessage.masterId, notificationId: notificationMessage.id])}">
                                                    <i class="fa fa-users text-aqua"></i>${notificationMessage.message}
                                                </a>
                                            </li>
                                        </sec:ifAllGranted>-->
                                        <!--<li>-->
                                        <!--<a href="#">-->
                                        <!--<i class="fa fa-warning text-yellow"></i> Very long description here that may not fit into the-->
                                        <!--page and may cause design problems-->
                                        <!--</a>-->
                                        <!--</li>-->
                                        <!--<li>-->
                                        <!--<a href="#">-->
                                        <!--<i class="fa fa-users text-red"></i> 5 new members joined-->
                                        <!--</a>-->
                                        <!--</li>-->
                                        <!--<li>-->
                                        <!--<a href="#">-->
                                        <!--<i class="fa fa-shopping-cart text-green"></i> 25 sales made-->
                                        <!--</a>-->
                                        <!--</li>-->
                                        <!--<li>-->
                                        <!--<a href="#">-->
                                        <!--<i class="fa fa-user text-red"></i> You changed your username-->
                                        <!--</a>-->
                                        <!--</li>-->

                                    </g:each>
                                </ul>
                            </li>
                            <li class="footer"><a href="#">View all</a></li>
                        </ul>


                </li>
                <!-- Tasks: style can be found in dropdown.less -->
                <li class="dropdown tasks-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-flag-o"></i>
                        <span class="label label-danger">9</span>
                    </a>
                    <ul class="dropdown-menu">
                        <li class="header">You have 9 tasks</li>
                        <li>
                            <!-- inner menu: contains the actual data -->
                            <ul class="menu">
                                <li><!-- Task item -->
                                    <a href="#">
                                        <h3>
                                            Design some buttons
                                            <small class="pull-right">20%</small>
                                        </h3>
                                        <div class="progress xs">
                                            <div class="progress-bar progress-bar-aqua" style="width: 20%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                                                <span class="sr-only">20% Complete</span>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                                <!-- end task item -->
                                <li><!-- Task item -->
                                    <a href="#">
                                        <h3>
                                            Create a nice theme
                                            <small class="pull-right">40%</small>
                                        </h3>
                                        <div class="progress xs">
                                            <div class="progress-bar progress-bar-green" style="width: 40%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                                                <span class="sr-only">40% Complete</span>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                                <!-- end task item -->
                                <li><!-- Task item -->
                                    <a href="#">
                                        <h3>
                                            Some task I need to do
                                            <small class="pull-right">60%</small>
                                        </h3>
                                        <div class="progress xs">
                                            <div class="progress-bar progress-bar-red" style="width: 60%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                                                <span class="sr-only">60% Complete</span>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                                <!-- end task item -->
                                <li><!-- Task item -->
                                    <a href="#">
                                        <h3>
                                            Make beautiful transitions
                                            <small class="pull-right">80%</small>
                                        </h3>
                                        <div class="progress xs">
                                            <div class="progress-bar progress-bar-yellow" style="width: 80%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                                                <span class="sr-only">80% Complete</span>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                                <!-- end task item -->
                            </ul>
                        </li>
                        <li class="footer">
                            <a href="#">View all tasks</a>
                        </li>
                    </ul>
                </li>
                <!-- User Account: style can be found in dropdown.less -->
                <li class="dropdown user user-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <img src="${assetPath(src:'user2-160x160.jpg')}" class="user-image" alt="User Image">
                        <span class="hidden-xs"><sec:loggedInUserInfo field='username'/></span>
                    </a>
                    <ul class="dropdown-menu">
                        <!-- User image -->
                        <li class="user-header">
                            <img src="${assetPath(src:'user2-160x160.jpg')}" class="img-circle" alt="User Image">

                            <p>
                                Alexander Pierce - Web Developer
                                <small>Member since Nov. 2012</small>
                            </p>
                        </li>
                        <!-- Menu Body -->
                        <li class="user-body">
                            <div class="row">
                                <div class="col-xs-4 text-center">
                                    <a href="#">Followers</a>
                                </div>
                                <div class="col-xs-4 text-center">
                                    <a href="#">Sales</a>
                                </div>
                                <div class="col-xs-4 text-center">
                                    <a href="#">Friends</a>
                                </div>
                            </div>
                            <!-- /.row -->
                        </li>
                        <!-- Menu Footer-->
                        <li class="user-footer">
                            <div class="pull-left">
                                <a href="#" class="btn btn-default btn-flat">Profile</a>
                            </div>
                            <div class="pull-right">
                                <a href="#" class="btn btn-default btn-flat">Sign out</a>
                            </div>
                        </li>
                    </ul>
                </li>
                <!-- Control Sidebar Toggle Button -->
                <li>
                    <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
                </li>
            </ul>
            </sec:ifLoggedIn>
        </div>
    </nav>
</header>
<sec:ifAllGranted roles='ROLE_MANAGER'>
    <script>
        $( document ).ready(function() {
                var socket = new SockJS("${createLink(uri: '/stomp')}");
                var client = Stomp.over(socket);

                client.connect({}, function() {
                    client.subscribe("/user/queue/messagetouser", function(message) {
                        var data = JSON.parse(message.body);
                        $('#notificationNum').text(data.notificationUnreadMessage);
                        $('#messageList').append("<li><a href='checkOvertime?overTimeMasterId="+data.overTimeMasterId +"&notificationId="+data.notificationId+"'><i class='fa fa-users text-aqua'>"+data.message+"</i><a></li>");
                    });
                });
            });
    </script>
</sec:ifAllGranted >

<sec:ifAllGranted roles='ROLE_EMPLOYEE'>
    <script>
        $( document ).ready(function() {
                var socket = new SockJS("${createLink(uri: '/stomp')}");
                var client = Stomp.over(socket);

                client.connect({}, function() {
                    client.subscribe("/user/queue/messagetoemployee", function(message) {
                        var data = JSON.parse(message.body);
                        $('#notificationNum').text(data.notificationUnreadMessage);

                        $('#messageList').append("<li><a href='${request.contextPath}/employeeCheckAgain?overTimeMasterId="+data.overTimeMasterId +"&notificationId="+data.notificationId+"'><i class='fa fa-users text-aqua'>"+data.message+"</i><a></li>");

                    });
                });
            });
    </script>
</sec:ifAllGranted >


