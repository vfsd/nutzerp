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
           url:'<%=basePath%>accountant/query.action',
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
                {field:'id',title:'序号'},  
                {field:'name',title:'姓名'},
                {field:'windowNum',title:'窗口号'}
           ]]
       });
   }
   $(document).ready(function(){
          initTable();
   });
</script>


<div id="tb">
    <div style="height:40px; width:100%; border-bottom:1px solid #ccc;">
      <a  onclick="editClass()" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">立即预约</a>
      
    </div>
</div>
<table id="dg" class="table">
                                         
</table>


<div id="editDialog">
	<form method="post">
		<input type="hidden" name="accountantId" id="accountant_id"/>
		<input type="hidden" name="windowNum"/>
		<input type="hidden" id="test-v" name="thisTimeGroup"/>
		<div>
			<table class="accountant_table">
				<tr>
					<td><p id="time1">8:00-8:15</p></td>
					<td><p id="time2">8:15-8:30</p></td>
					<td><p id="time3">8:30-8:45</p></td>
					<td><p id="time4">8:45-9:00</p></td>
					<td><p id="time5">9:00-9:15</p></td>
					<td><p id="time6">9:15-9:30</p></td>
					<td><p id="time7">9:30-9:45</p></td>
					<td><p id="time8">9:45-10:00</p></td>
				</tr>
				<tr>
					<td><p id="time9">10:00-10:15</p></td>
					<td><p id="time10">10:15-10:30</p></td>
					<td><p id="time11">10:30-10:45</p></td>
					<td><p id="time12">10:45-11:00</p></td>
					<td><p id="time13" style="">11:00-11:15</p></td>
					<td><p id="time14">11:15-11:30</p></td>
					<td><p id="time15">11:30-11:45</p></td>
					<td><p id="time16">11:45-12:00</p></td>
				</tr>
				<tr>
					<td><p id="time17">12:00-12:15</p></td>
					<td><p id="time18">12:15-12:30</p></td>
					<td><p id="time19">12:30-12:45</p></td>
					<td><p id="time20">12:45-13:00</p></td>
					<td><p id="time21">13:00-13:15</p></td>
					<td><p id="time22">13:15-13:30</p></td>
					<td><p id="time23">13:30-13:45</p></td>
					<td><p id="time24">13:45-14:00</p></td>
				</tr>
				<tr>
					<td><p id="time25">14:00-14:15</p></td>
					<td><p id="time26">14:15-14:30</p></td>
					<td><p id="time27">14:30-14:45</p></td>
					<td><p id="time28">14:45-15:00</p></td>
					<td><p id="time29">15:00-15:15</p></td>
					<td><p id="time30">15:15-15:30</p></td>
					<td><p id="time31">15:30-15:45</p></td>
					<td><p id="time32">15:45-16:00</p></td>
				</tr>
				<tr>
					<td><p id="time33">16:00-16:15</p></td>
					<td><p id="time34">16:15-16:30</p></td>
					<td><p id="time35">16:30-16:45</p></td>
					<td><p id="time36">16:45-17:00</p></td>
					<td><p id="time37">17:00-17:15</p></td>
					<td><p id="time38">17:15-17:30</p></td>
					<td><p id="time39">17:30-17:45</p></td>
					<td><p id="time40">17:45-18:00</p></td>
				</tr>
			</table>
		</div>
		<div style="text-align:center;">
			<textarea rows="3" cols="80" name="content" placeholder="请输入预约内容" required="required" style="margin:0 auto;"></textarea>
		</div>
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
	       title:"预约",
	       width:800,
	       height:400,
	       buttons:[{ iconCls:'icon-ok', text:'保存',   handler :update },
	                { iconCls:'icon-cancel',  text:'返回', handler :closeEditDialog}] 	
	   	});
	}
	//添加
    function add(){
        $("#inputDialog form").form("submit",{
           url:"<%=basePath%>user/addAction.action",
           onSubmit:function(){
               var flag=$("#inputDialog form").form("validate");
            return flag;
           },
           success:function(result){
            $.messager.show({title:'信息提示',msg:eval(result)});
            $("#inputDialog").dialog("close");//窗口关闭
            $("#dg").datagrid("reload");//重新加载
            $("#dg").datagrid("clearSelections");//取消选择
           }
        });
     }
               
     //保存信息
     function update(){
        $("#editDialog form").form("submit",{
           url:'<%=basePath%>msg/receivePost.action',
           onSubmit:function(){
              var flag=$("#editDialog form").form("validate");
              return flag;
           },
           success:function(result){
        	  //console.log(result);
              $.messager.show({title:'信息提示',msg:"预约成功"});
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
        //$('#editDialog').dialog('refresh');
        $("#editDialog").dialog("open");
        $("#accountant_id").val(rows[0].id);
        //$("#test-v").val("");
        //var kk = $(" #test-v").val();
        //$("#test-v").val(kk+"/nn");
        $("#editDialog form").form("load",rows[0]);
        initTimeGroup(rows[0].id);
     }
     
     //初始化时间组
     function initTimeGroup(iid){
    	 var timeGroup ="";
    	 $.post("<%=basePath%>accountant/getTimeGroup.action", { "iid": iid },function(data){
	     	var arr = data.split("/");
	     	for(var i=0;i<arr.length;i++){
	     		$(".accountant_table").find("tr").each(function(){
		     	    var tdArr = $(this).children();
		     	    for(var j=0;j<tdArr.length;j++){
		     	    	if(tdArr.eq(j).find("p").text()==arr[i]){
		     	    		$(tdArr.eq(j)).css("background-color","red");
		     	    		$(tdArr.eq(j)).text("被预约");
		     	    	}
		     	    }
		     	});
	     	}
	   	 }, "json");
     }
     
       
     //删除
     function delClass(){
  		var rows=$("#dg").datagrid("getSelections");
        if(rows.length==0){
              $.messager.alert("信息提示","请选择待删除的记录！");
              return;
        }
        $.messager.confirm("信息提示","确实要删除"+rows[0].name+"用户么？",function(flag){
            if(flag){
                var arr=[];
                $.each(rows,function(){
                       var str="iid="+this.id;
                       arr.push(str);
                });
                var param=arr[0];
                if(arr.length>1){
                       param=arr.join("&");
                }
                $.post("<%=basePath%>user/delByIdAction.action",param,function(result){
                       $.messager.show({title:'信息提示',msg:result});
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
      
      //
      function SetTimeValue(){
    	  //console.log($(this));
    	  var kk = $("#test-v").val();
    	  var val = $(this).text();
    	  $("#test-v").val(kk+"/"+val);
      }
      
      $(".accountant_table p").click(function () {
    	  //console.log($(this).text());
    	  var result = $(" #test-v").val();
    	  var reg=/^(^([\\u4E00-\\u9FA5]|[\\uFE30-\\uFFA0]))*$/;
    	  var kk=result.replace(reg,'');
    	  //console.log(kk);
    	  var val = $(this).text();
    	  if (kk !== null || kk !== undefined || kk !== ''|| kk !== ' ') {
    		  $("#test-v").val(kk+"/"+val);
    	  }else{
    		  $("#test-v").val(val);
    	  }
    	  $(this).css("background-color","#337CA6");
    	  $(this).css("color","#fff");
      });
</script>





















