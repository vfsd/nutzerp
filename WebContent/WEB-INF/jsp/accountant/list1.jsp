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
           url:'<%=basePath%>msg/query1.action',
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
                {field:'state',title:'状态',width:200,
                	formatter:function(value,row,index){
                		//console.log("value="+value);
                		if(value==0){
                			return "未读";//0:未读   1:已接受  2:驳回
                		}
                		if(value==1){
                			return "已接受";//0:未读   1:已接受  2:驳回
                		}
                		if(value==2){
                			return "已驳回";//0:未读   1:已接受  2:驳回
                		}
                	}	
                },
                {field:'timeGroup',title:'预约时间',width:300},
                {field:'content',title:'预约内容',width:600}
           ]]
       });
   }
   $(document).ready(function(){
          initTable();
   });
</script>


<div id="tb">
    <div style="height:40px; width:100%; border-bottom:1px solid #ccc;">
      <a  onclick="editClass()" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">接受</a>
      <a  onclick="editClass1()"  class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">驳回</a>
      <a  onclick="editClass2()"  class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">未读预约</a>
      <a  onclick="editClass3()"  class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">所有预约</a>
    </div>
</div>
<table id="dg" class="table">
                                         
</table>



<script type="text/javascript">
     
     //添加函数
     function addClass(){
        $("#inputDialog").dialog("open");   
     }
 
     //接受预约请求
     function editClass(){
        var rows=$("#dg").datagrid("getSelections");
        if(rows.length!=1){
            alert("只能选择一行");
            return;
        }
        $.post("<%=basePath%>msg/update1.action",{id:rows[0].id,state:1},function(result){
            $.messager.show({title:'信息提示',msg:"已接受该条预约请求"});
            $("#dg").datagrid("reload");	
     	},"json");
        $("#dg").datagrid("reload");//重新加载
     }
     
   	//拒绝预约请求
     function editClass1(){
        var rows=$("#dg").datagrid("getSelections");
        if(rows.length!=1){
            alert("只能选择一行");
            return;
        }
        $.post("<%=basePath%>msg/update1.action",{id:rows[0].id,state:2},function(result){
            $.messager.show({title:'信息提示',msg:"已拒绝该条预约请求"});
            $("#dg").datagrid("reload");	
     	},"json");
        $("#dg").datagrid("reload");//重新加载
     }
       
     
     //搜索未读预约
     function editClass2(){
            $("#dg").datagrid("load",{state:0});
     }
     
   	 //搜索所有预约
     function editClass3(){
            $("#dg").datagrid("load",{state:4});
     }
      
</script>





















