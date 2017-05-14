<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link rel="stylesheet" href="<%=basePath%>admin/css/themes/default/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="<%=basePath%>admin/css/themes/icon.css" type="text/css"></link>
<link rel="stylesheet" href="<%=basePath%>admin/css/base.css" type="text/css"></link>
<script type="text/javascript"  src="<%=basePath%>admin/js/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>admin/js/jquery.easyui.min.js"></script>

<script>
     function initTable(){
        $("#dg").datagrid({
           url:'<%=basePath%>user/query.action',
           idField:'id',
           fitColumns:true,
           pagination:true,
           toolbar:'#tb',
           //singleSelect:true,
           pageSize:15,
           pageList:[1,2,3,4,5,10,15,20,30,50],
           loadFilter : function(data) {
        	   data['rows']=data['list'];
        	   /* 
                console.log("======data=========");
				console.info(data);
				console.info(data['list']);
				console.info(data['rows']);
				console.log("======data=========");
				 */
				return data;
				
		   },
           columns:[[  
                {field:'opt',checkbox:true},
                {field:'id',title:'序号',width:100}, 
                {field:'name',title:'姓名',width:100}, 
                {field:'state',title:'状态',width:200,
                	formatter:function(value,row,index){
                		//console.log("value="+value);
                		if(value==0){
                			return "正常";//0:未读   1:已接受  2:驳回
                		}
                		if(value==1){
                			return "已禁用";//0:未读   1:已接受  2:驳回
                		}
                	}	
                }
           ]]
       });
   }
   $(document).ready(function(){
          initTable();
   });
</script>


<div id="tb">
    <div style="height:40px; width:100%; border-bottom:1px solid #ccc;">
      <a  onclick="addClass()" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">添加</a>
      <a  onclick="editClass()" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
      <a  onclick="delClass()"  class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
    </div>
</div>
<table id="dg" class="table">
                                         
</table>


<div id="inputDialog">
	<form method="post">
		<div style="text-align:center;">
			<input type="text" placeholder="请设置姓名" name="name" required="required">
			<input type="text" placeholder="请设置密码" name="password" required="required" id="u_pwd">
			<input type="text" placeholder="重复输入密码" name="repwd" required="required" id="u_repwd">
		</div>
	</form>
</div>

<div id="editDialog">
	<form method="post">
		<input type="hidden" name="id"/>
		<!-- <input type="hidden" name="state"/> -->
		<select name="state">
		  	<option value ="0">正常使用</option>
		  	<option value ="1">禁止使用</option>
		</select>
		<input type="text" placeholder="请设置姓名" name="name" required="required">
	</form>
</div>

<script type="text/javascript">
	$(document).ready(function(){
	    init();
	}); 
	
	function init(){
        //添加框
        $("#inputDialog").dialog({
 	  	  title:'添加管理员',
 	  	  width:700,
 	  	  height:300,
 	  	  closed:true,
 	  	  buttons:[{ iconCls:'icon-ok', text:'保存',  handler :add },
 	  	           { iconCls:'icon-cancel',  text:'返回',  handler :closeAddDialog}]
        });
          
        //修改框
        $("#editDialog").dialog({
           closed:true,
	       title:"修改管理员信息",
	       width:700,
	       height:300,
	       buttons:[{ iconCls:'icon-ok', text:'保存',   handler :update },
	                { iconCls:'icon-cancel',  text:'返回', handler :closeEditDialog}] 	
	   	});
	}
	//添加
    function add(){
		var pwd=$("#u_pwd").val();
		var repwd=$("#u_repwd").val();
		if(pwd!=repwd){
			alert("两次输入密码不一致，请重新输入");
			return null;
		}
        $("#inputDialog form").form("submit",{
           url:"<%=basePath%>user/add.action",
           onSubmit:function(){
               var flag=$("#inputDialog form").form("validate");
            return flag;
           },
           success:function(result){
            $.messager.show({title:'信息提示',msg:"添加成功"});
            $("#inputDialog").dialog("close");//窗口关闭
            $("#dg").datagrid("reload");//重新加载
            $("#dg").datagrid("clearSelections");//取消选择
           }
        });
     }
               
     //修改信息
     function update(){
        $("#editDialog form").form("submit",{
           url:'<%=basePath%>user/update.action',
           onSubmit:function(){
              var flag=$("#editDialog form").form("validate");
              return flag;
           },
           success:function(result){
              $.messager.show({title:'信息提示',msg:"修改成功，用户状态已更新"});
              $("#dg").datagrid("clearSelections");
              $("#dg").datagrid("reload");//重新加载
              $("#editDialog").dialog("close");
           }
       });
     }
      
     //关闭添加框
     function closeAddDialog(){
      	$("#inputDialog").dialog("close");
     }
      
     //关闭修改框
     function closeEditDialog(){
      	$("#editDialog").dialog("close");
     }
     
     //添加函数
     function addClass(){
        $("#inputDialog").dialog("open");   
     }
 
     //修改信息函数
     function editClass(){
        var rows=$("#dg").datagrid("getSelections");
        if(rows.length!=1){
            alert("只能选择一行");
            return;
        }
        $("#editDialog").dialog("open");
        $("#editDialog form").form("load",rows[0]);
     }
       
     //删除
     function delClass(){
  		var rows=$("#dg").datagrid("getSelections");
        if(rows.length==0){
              $.messager.alert("信息提示","请选择待删除的记录！");
              return;
        }
        $.messager.confirm("信息提示","确实要删除该条预约请求么？",function(flag){
            if(flag){
                var arr=[];
                $.each(rows,function(){
                       var str="id="+this.id;
                       arr.push(str);
                });
                var param=arr[0];
                if(arr.length>1){
                       param=arr.join("&");
                }
                $.post("<%=basePath%>user/delete.action",param,function(result){
                       $.messager.show({title:'信息提示',msg:"删除成功"});
                       $("#dg").datagrid("reload");	
                },"json");
            }
         });
      }
       
      //重置按钮
	  function reset(){
         $("#inputDialog form").get(0).reset();
         $("#editDialog form").get(0).reset();
      }
     
</script>





















