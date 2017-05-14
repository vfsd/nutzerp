<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<title>王永福——财务报销预约信息管理系统</title>
	<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
	<link rel="stylesheet" href="admin/css/bootstrap.min.css">
	<script type="text/javascript" src="admin/js/jquery-2.0.3.min.js"></script>
	<!-- 把user id复制到一个js变量 -->
	<script type="text/javascript">
	    var base = '${base}';
	    $(function() {
	        $("#login_button").click(function() {
	        	alert("login");
	            $.ajax({
	            	url : base + "/user/login",
	                type: "POST",
	                data:{"username":$("#username").val(),"password":$("#password").val()},
	                error: function(request) {
	                    alert("Connection error");
	                },
	                dataType:"json",
	                success: function(data) {
	                    alert(data);
	                    if (data == true) {
	                        alert("登陆成功");
	                        location.reload();
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
			<div id="login_div">
			    <%-- <form action="${base}/user/login" id="loginForm" method="post" > --%>
			    <div id="loginForm">
			    	用户名 <input name="username" type="text" value="admin" id="username">
			                 密码 <input name="password" type="password" value="123456" id="password">
			        <button id="login_button">提交</button>
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