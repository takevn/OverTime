<!DOCTYPE html>

<%@ page import="book.NotificationService" %>
<html xmlns:asset="http://www.w3.org/1999/XSL/Transform" xmlns:g="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>AdminLTE 2 | Dashboard</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.7 -->
    <asset:stylesheet src="/css/bootstrap.min.css"/>
    <!--<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">-->
    <!-- Font Awesome -->
    <asset:stylesheet src="/css/font-awesome.min.css"/>
    <!--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">-->
    <!-- Ionicons -->
    <asset:stylesheet src="/css/ionicons.min.css"/>
    <!--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">-->
    <!-- Theme style -->
    <asset:stylesheet src="/css/AdminLTE.min.css"/>
    <!--<link rel="stylesheet" href="dist/css/AdminLTE.min.css">-->
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <asset:stylesheet src="/css/_all-skins.min.css"/>
    <!--<link rel="stylesheet" href="dist/css/skins/_all-skins.min.css">-->
    <!-- iCheck -->
    <asset:stylesheet src="/css/blue.css"/>
    <!--<link rel="stylesheet" href="plugins/iCheck/flat/blue.css">-->
    <!-- Morris chart -->
    <asset:stylesheet src="/css/morris.css"/>
    <!--<link rel="stylesheet" href="plugins/morris/morris.css">-->
    <!-- jvectormap -->
    <asset:stylesheet src="/css/jquery-jvectormap-1.2.2.css"/>
    <!--<link rel="stylesheet" href="plugins/jvectormap/jquery-jvectormap-1.2.2.css">-->
    <!-- Date Picker -->
    <asset:stylesheet src="/css/datepicker3.css"/>
    <!--<link rel="stylesheet" href="plugins/datepicker/datepicker3.css">-->
    <!-- Daterange picker -->
    <asset:stylesheet src="/css/daterangepicker.css"/>
    <!--<link rel="stylesheet" href="plugins/daterangepicker/daterangepicker.css">-->
    <!-- bootstrap wysihtml5 - text editor -->
    <asset:stylesheet src="/css/bootstrap3-wysihtml5.min.css"/>

    <!--<link rel="stylesheet" href="plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">-->

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>

    <!--<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>-->
    <asset:javascript src="/html5shiv.min.js"/>
    <!--<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>-->
    <asset:javascript src="/respond.min.js"/>
    <!-- jQuery 2.2.3 -->
    <asset:javascript src="/jquery-2.2.3.min.js"/>
    <!--<script src="plugins/jQuery/jquery-2.2.3.min.js"></script>-->
    <!-- jQuery UI 1.11.4 -->
    <asset:javascript src="/jquery-ui.min.js"/>
    <!--<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>-->
    <!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
   <!-- <script>
    $.widget.bridge('uibutton', $.ui.button);
    </script>-->
    <!-- Bootstrap 3.3.7 -->
    <asset:javascript src="/bootstrap.min.js"/>
    <!--<script src="bootstrap/js/bootstrap.min.js"></script>-->
    <!-- Morris.js charts -->
    <asset:javascript src="/raphael-min.js"/>
    <!--<script src="https://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>-->
    <!--<asset:javascript src="/morris.min.js"/>-->
    <!--<script src="plugins/morris/morris.min.js"></script>-->
    <!-- Sparkline -->
    <asset:javascript src="/jquery.sparkline.min.js"/>
    <!--<script src="plugins/sparkline/jquery.sparkline.min.js"></script>-->
    <!-- jvectormap -->
    <asset:javascript src="/jquery-jvectormap-1.2.2.min.js"/>
    <!--<script src="plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>-->
    <asset:javascript src="/jquery-jvectormap-world-mill-en.js"/>
    <!--<script src="plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>-->
    <!-- jQuery Knob Chart -->
    <asset:javascript src="/jquery.knob.js"/>
    <!--<script src="plugins/knob/jquery.knob.js"></script>-->
    <!-- daterangepicker -->
    <asset:javascript src="/moment.min.js"/>
    <!--<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.11.2/moment.min.js"></script>-->
    <asset:javascript src="/daterangepicker.js"/>
    <!--<script src="plugins/daterangepicker/daterangepicker.js"></script>-->
    <!-- datepicker -->
    <asset:javascript src="/bootstrap-datepicker.js"/>
    <!--<script src="plugins/datepicker/bootstrap-datepicker.js"></script>-->
    <!-- Bootstrap WYSIHTML5 -->
    <asset:javascript src="/bootstrap3-wysihtml5.all.min.js"/>
    <!--<script src="plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>-->
    <!-- Slimscroll -->
    <asset:javascript src="/jquery.slimscroll.min.js"/>
    <!--<script src="plugins/slimScroll/jquery.slimscroll.min.js"></script>-->
    <!-- FastClick -->
    <asset:javascript src="/fastclick.js"/>
    <!--<script src="plugins/fastclick/fastclick.js"></script>-->
    <!-- AdminLTE App -->
    <asset:javascript src="/app.min.js"/>
    <!--<script src="dist/js/app.min.js"></script>-->
    <!-- AdminLTE dashboard demo (This is only for demo purposes) -->
    <!--<asset:javascript src="/dashboard.js"/>-->
    <!--<script src="dist/js/pages/dashboard.js"></script>-->
    <!-- AdminLTE for demo purposes -->
    <asset:javascript src="/demo.js"/>
    <!--<script src="dist/js/demo.js"></script>-->
    <asset:javascript src="/sockjs-0.3.4.js" />
    <asset:javascript src="/stomp.js" />

    <![endif]-->
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">


    <%
        def notificationService = grailsApplication.mainContext.getBean("notificationService")
    %>
    <g:set var="notificationService" value="${notificationService}" />
    <g:render template="/layouts/header"/>
    <!-- Left side column. contains the logo and sidebar -->

    <g:render template="/layouts/sidebar"/>
    <!-- Content Wrapper. Contains page content -->
    <g:layoutBody/>
    <!-- /.content-wrapper -->
    <g:render template="/layouts/footer"/>

    <!-- Control Sidebar -->
    <g:render template="/layouts/controlsidebar"/>
    <!-- /.control-sidebar -->
    <!-- Add the sidebar's background. This div must be placed
         immediately after the control sidebar -->
    <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->


</body>
</html>
