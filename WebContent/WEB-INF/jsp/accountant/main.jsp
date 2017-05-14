<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
  <head>
   <base href="<%=basePath%>">
   <title>王永福—财务报销预约信息管理系统</title>
   <link rel="stylesheet" href="<%=basePath%>admin/css/themes/default/easyui.css" type="text/css"></link>
   <link rel="stylesheet" href="<%=basePath%>admin/css/themes/icon.css" type="text/css"></link>
   <link rel="stylesheet" href="<%=basePath%>admin/css/main.css" type="text/css"></link>
   <script type="text/javascript"  src="<%=basePath%>admin/js/jquery-2.0.3.min.js"></script>
   <script type="text/javascript" src="<%=basePath%>admin/js/jquery.easyui.min.js"></script>
   
   <meta http-equiv="X-UA-Compatible" content="IE=8" >
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
      <section>
      		<div id="cc" class="easyui-layout" style="width:100%;height:800px;">
			    <div data-options="region:'north',split:true" style="height:170px;background-color:#337CA6;">
			    	<%-- <img src="<%=basePath%>admin/images/top.png" style="margin:0 auto;"/> --%>
			    	<h1 style="color:#fff;font-weight:bold;font-size:50px;text-align:center;margin:0 auto;font-family:'微软雅黑';padding-top:30px;">财务报销预约信息管理系统</h1>
			    </div>
			    <div data-options="region:'south',split:true" style="height:100px;">
			    
			    </div>
			    <div data-options="region:'east',title:'信息公告',split:true" style="width:260px;">
			    	
			    </div>
			    <div data-options="region:'west',title:'预约管理',split:true,animate:true" style="width:180px;">
			    	<div title="预约管理" id="left_menu">
                        <ul id="tt" class="easyui-tree">
						    
		                    <li><a data-url="<%=basePath%>msg/goMsg.action" >预约管理</a></li>
		                    <li><a data-url="<%=basePath%>news/toList.action" >公告管理</a></li>
		                       
						</ul>
                    </div>
			    </div>
			    <div data-options="region:'center'" style="padding:5px;background:#eee;">
			    	<div id="main_menu" class="easyui-tabs" data-options="region:'center'" style="height:460px;overflow:hidden;">
			    		<div class="user_msg" data-options="iconCls:'icon-help',title:'首页'" >
			    			<h2>财务管理人员操作中心</h2>
			    			<h3>当前时间：<span id="now_time"></span></h3>
			    			<h4>姓名：<%=request.getSession().getAttribute("loginAccountantName") %></h4>
			    			
			    		</div>
			    	</div>
			    </div>
			</div>
      </section>  
  </body>
  
  
</html>


<script>
	$(document).ready(function() {
			
			$('ul>li', '#left_menu').click(function() {
					var p = $(this).text();
					var url = $('a', this).data('url');//获取action
					var flag=$("#main_menu").tabs('exists',p);//判断该页面是否已经打开
				    if(flag){
						$("#main_menu").tabs('select',p);//如果该页面已经打开，就跳转到该页面
					}else{
						//如果没打开，就重新打开一个页面
						$('#main_menu').tabs('add',{title : p,closable : true,fit:true,content:'<iframe src='+url+' style="width:100%;height:100%;border:none"></iframe>',});
					}
			});
			
			//定义鼠标移过li标签的时候，该li标签的属性变化
			$('ul>li', '#left_menu').hover(function() {
				$(this).animate({
					'background' : '#D4E2FA'
				});
			}, function() {
				$(this).animate({
					'background' : '#fff'
				});
			});
			
	});
	
	function addTab(tt,t_url,t_icon){
				var t_title=tt;
				var tt_url=t_url;
				var strs= new Array();
				strs=t_url.split("?");
				var u_n= new Array();
				u_n=strs[1].split("=");
				var tt_url=strs[0]+"?userId="+u_n[1];
				var t_flag=$("#main_menu").tabs('exists',t_title);
				$("#main_menu").tabs('add',{title :t_title,closable : true,fit:true,content:'<iframe src='+tt_url+' style="width:100%;height:100%;border:none"></iframe>'});
	}
	
</script>
 


