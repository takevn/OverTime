<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- Sidebar user panel -->
        <div class="user-panel">
            <div class="pull-left image">
                <img src="${assetPath(src:'user2-160x160.jpg')}" class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">
                <p><sec:loggedInUserInfo field='username'/></p>
                <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
            </div>
        </div>
        <!-- search form -->
        <form action="#" method="get" class="sidebar-form">
            <div class="input-group">
                <input type="text" name="q" class="form-control" placeholder="Search...">
              <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                </button>
              </span>
            </div>
        </form>
        <!-- /.search form -->
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu">
            <li class="header">MAIN NAVIGATION</li>
            <li class="active treeview">
                <a href="#">
                    <i class="fa fa-dashboard"></i> <span>Dashboard</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                </a>
                <ul class="treeview-menu">
                    <li class="active"><a href="${createLink(controller:'overTime', action:'index')}" /><i class="fa fa-circle-o"></i>Create OverTime</a></li>
                    <li class="active"><a href="${createLink(controller:'overTime', action:'show')}" /><i class="fa fa-circle-o"></i>Show OverTime</a></li>
                    <sec:ifAllGranted roles='ROLE_HR'>
                        <li class="active">
                            <%
                                def numOfUnassignTask = notificationService.getUnassignTaskOfHr()
                            %>
                            <a href="${createLink(controller:'overTime', action:'show')}" /><i class="fa fa-circle-o"></i>UnAssignTask    <span class="label label-warning" id="notificationNumHr">${numOfUnassignTask?:0}</span></a>
                        </li>
                    </sec:ifAllGranted>
                    <li class="active"><a href="${createLink(controller:'humanManage', action:'index')}" /><i class="fa fa-circle-o"></i>Personal Infomation</a></li>
                    <sec:ifAnyGranted roles='ROLE_HR,ROLE_MANAGER'>
                        <li class="active"><a href="${createLink(controller:'humanManage', action:'employeeManage')}" /><i class="fa fa-circle-o"></i>Employee Infomation</a></li>
                    </sec:ifAnyGranted>
                    <li class="active"><a href="${createLink(controller:'overTime', action:'showComeLateAndTakeLeave')}" /><i class="fa fa-circle-o"></i>Show TakeLeave and Come late</a></li>
                </ul>
            </li>

        </ul>
    </section>
    <!-- /.sidebar -->
</aside>

<script>
    $( document ).ready(function() {
            var socket = new SockJS("${createLink(uri: '/stomp')}");
            var client = Stomp.over(socket);


            client.connect({}, function() {
                client.subscribe("/topic/messagetoHR", function(message) {
                    var num = parseInt($('#notificationNumHr').html());
                    alert(message.body);
                    $('#notificationNumHr').html(num+1);
                });
            });


    });
</script>
