<!DOCTYPE html>
<html xmlns:asset="http://www.w3.org/1999/XSL/Transform" xmlns:g="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>AdminLTE 2 | Log in</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.7 -->
    <asset:stylesheet src="/css/font-awesome.min.css"/>
    <!--<link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css">-->
    <!-- Font Awesome -->
    <asset:stylesheet src="/css/font-awesome.min.css"/>
    <!--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">-->
    <!-- Ionicons -->
    <asset:stylesheet src="/css/ionicons.min.css"/>
    <!--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">-->
    <!-- Theme style -->
    <asset:stylesheet src="/css/AdminLTE.min.css"/>
    <!--<link rel="stylesheet" href="../../dist/css/AdminLTE.min.css">-->
    <!-- iCheck -->
    <asset:stylesheet src="/css/blue.css"/>
    <!--<link rel="stylesheet" href="../../plugins/iCheck/square/blue.css">-->

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>

    <!--<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>-->
    <!--<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>-->
    <asset:javascript src="/html5shiv.min.js"/>
    <asset:javascript src="/respond.min.js"/>
    <![endif]-->
</head>
<body class="hold-transition login-page">
<div class="login-box">
    <div class="login-logo">
        <a href="../../index2.html"><b>Admin</b>LTE</a>
    </div>
    <!-- /.login-logo -->
    <div class="login-box-body">
        <p class="login-box-msg">Sign in to start your session</p>

        <!--<form action="../../index2.html" method="post">-->
        <!--<div class="form-group has-feedback">-->
        <!--<input type="email" class="form-control" placeholder="Email">-->
        <!--<span class="glyphicon glyphicon-envelope form-control-feedback"></span>-->
        <!--</div>-->
        <!--<div class="form-group has-feedback">-->
        <!--<input type="password" class="form-control" placeholder="Password">-->
        <!--<span class="glyphicon glyphicon-lock form-control-feedback"></span>-->
        <!--</div>-->
        <!--<div class="row">-->
        <!--<div class="col-xs-8">-->
        <!--<div class="checkbox icheck">-->
        <!--<label>-->
        <!--<input type="checkbox"> Remember Me-->
        <!--</label>-->
        <!--</div>-->
        <!--</div>-->
        <!--&lt;!&ndash; /.col &ndash;&gt;-->
        <!--<div class="col-xs-4">-->
        <!--<button type="submit" class="btn btn-primary btn-block btn-flat">Sign In</button>-->
        <!--</div>-->
        <!--&lt;!&ndash; /.col &ndash;&gt;-->
        <!--</div>-->
        <!--</form>-->

        <g:form controller="login" action="authenticate" method="POST">
            <!--<ul class="nav navbar-nav collapse navbar-collapse">
                <li class="dropdown"><a href="#">Select Your Company<i class="fa fa-angle-down"></i></a>
                    <ul role="menu" class="sub-menu">
                        <li><a href="#">LG Vietnam</a></li>
                        <li><a href="#">Samsung Electronics Vietnam</a></li>
                        <li><a href="#">Hyundai Corporation</a></li>
      <li><a href="#">EM Partners</a></li>
                    </ul>
                </li>
            </ul>-->
            <input type="text" placeholder="Employee ID" name="username"/>
            <input type="password" placeholder="Password" name="password"/>
            <span>
            <input type="checkbox" class="checkbox">
      Keep me logged in
      </span>
            <button type="submit" class="btn btn-default">Login</button>
        </g:form>


        <div class="social-auth-links text-center">
            <p>- OR -</p>
            <a href="#" class="btn btn-block btn-social btn-facebook btn-flat"><i class="fa fa-facebook"></i> Sign in
                using
                Facebook</a>
            <a href="#" class="btn btn-block btn-social btn-google btn-flat"><i class="fa fa-google-plus"></i> Sign in
                using
                Google+</a>
        </div>
        <!-- /.social-auth-links -->

        <a href="#">I forgot my password</a><br>
        <a href="register.html" class="text-center">Register a new membership</a>

    </div>
    <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- jQuery 2.2.3 -->
<script src="../../plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="../../bootstrap/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="../../plugins/iCheck/icheck.min.js"></script>
<script>
  $(function () {
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
    });
  });

</script>
</body>
</html>
