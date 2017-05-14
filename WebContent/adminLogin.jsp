<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<title>管理员登录——财务报销预约信息管理系统</title>
	<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
	<link rel="stylesheet" href="admin/css/bootstrap.min.css">
	<link rel="stylesheet" href="admin/css/login.css">
	<script type="text/javascript" src="admin/js/jquery-2.0.3.min.js"></script>
	<!-- 把user id复制到一个js变量 -->
	<script type="text/javascript">
	    var base = '${base}';
	    $(function() {
	        $("#login_button").click(function() {
	        	//alert("login");
	            $.ajax({
	            	url : base + "/admin/login",
	                type: "POST",
	                data:{"username":$("#username").val(),"password":$("#password").val()},
	                error: function(request) {
	                    alert("Connection error");
	                },
	                dataType:"json",
	                success: function(data) {
	                    //alert(data);
	                    if (data == true) {
	                        //alert("登陆成功");
	                        //location.reload();
	                        location.href= base+"/admin/toMain";
	                    } else {
	                        alert("登陆失败,请检查账号密码")
	                    }
	                }
	            });
	            return false;
	        });
	    });
		</script>
	</head>
	<body>
		<section>
			<div class="login_div">
			    <%-- <form action="${base}/user/login" id="loginForm" method="post" > --%>
			    <div id="loginForm">
			    	<div class="input-group input-group-lg">
					  <input name="username" type="text" class="form-control" placeholder="用户名" id="username">
					</div>
					<div class="input-group input-group-lg">
					  <input name="password" type="password" class="form-control" placeholder="密码" id="password">
					</div>
			        <div class="btn-group btn-group-justified" role="group" aria-label="">
					  <div class="btn-group" role="group">
					    <button type="button" class="btn btn-primary btn-login"  id="login_button">登录</button>
					  </div>
					</div>
			    </div>
			    <!-- </form> -->
			</div>
			<div id="user_info_div">
			    <p id="userInfo"></p>
			    <a href="${base}/user/logout">登出</a>
			</div>
		</section>
	</body>
</html>