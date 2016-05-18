<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'DatabaseCon.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<!-- CSS Bootstrap & Custom -->
    <link href="<%=basePath%>/styles/css/common.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>/styles/css/buttons.css" rel="stylesheet" type="text/css" />
    <script src="scripts/modernizr.js"></script>
    <script type="text/javascript" src="<%=basePath%>/scripts/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.wheelmenu.js"></script>
	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.subWheelmenu.js"></script>
    <script type="text/javascript" src="<%=basePath%>/scripts/jquery.toolWheelmenu.js"></script>
	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.js"></script>
	
	
  </head>
  <style>
  .div1{display:block; float:left;border:0px solid #000000; width:100%; height:88%;}
  .div2{display:block; float:left;border:0px solid #F00; width:94.5%; background:#F2F2F2;text-align:center}
  .div3{display:block; float:left;border:0px solid #7171C6; width:100%;}
  body{ text-align:center}
  </style>
  <script type="text/javascript">
  function nextStep(){
	  $('#tableSelectedListform').submit();
  }
 
  function getTableList(obj){
	  var connectionID=$(obj).children('option:selected').val();
	  $.ajax({
	         type:"post",
	         url:"<%=basePath%>ImportGetAllTables.action",
	         data:{
	        	 id:connectionID
	         },
	         dataType:"json",
	         success:function(data){
	          $('#connectionDBid').val(data.rows[0].id);
	           for(var i = 0; data.rows.length-1>=i;i++){
	          		//innerH+="<tr onclick='selectTr(this)'><td align='center'>"+data.rows[i].connectionDBTableName+"</td></tr>";
	          		$("#connectionTableselect").append("<option value='"+data.rows[i].connectionDBTableName+"'>"+data.rows[i].connectionDBTableName+"</option>");
	          	 }
	          	// document.getElementById("tablelisttbody").innerHTML =innerH;
	           },
	         error:function(){
	        	 hintManager.showHint("没有查询到人物数据，请检查输入是否正确！");
	         }
	     });
  }
  $(function(){
	  $.ajax({
	         type:"post",
	         url:"<%=basePath%>getDBTables.action",
	         dataType:"json",
	         success:function(data){
	          //var innerH='';
	           for(var i = 0; data.rows.length-1>=i;i++){
	        	   $("#connectionDBselect").append("<option value='"+data.rows[i].id+"'>"+data.rows[i].connectionName+"----"+data.rows[i].connectionDB+"</option>");
	          	//	innerH+="<tr  onclick='getDB(this)'><td align='center' name='id'>"+data.rows[i].id+"</td><td align='center'>"+data.rows[i].connectionName+"</td><td align='center'>"+data.rows[i].connectionDB+"</td><td><a onclick='editDBCon()' >Edit</a> </td><td><a   onclick='deleteDBCon()' >Delete</a></td></tr>";
	          	 }
	          //	 document.getElementById("tbody").innerHTML =innerH;
	           getTableList('',data.rows[0].id);
	           },
	         error:function(){
	        	 hintManager.showHint("没有查询到人物数据，请检查输入是否正确！");
	         }
	     });
	 

 });
  function addSQL(){
	  var innerH='<tr><td colspan="2"><input type="button" name="Submit22" value="-" onclick="delSQL()"/>添加自定义SqL</td></tr> <tr><td><div align="right">名称: </div></td><td><input name="textfield422" type="text" size="10" /></td></tr><tr><td valign="top"><div align="right">SQL语句：</div></td><td><label><textarea name="textarea" rows="3"></textarea><input type="button" name="Submit32" value="测试" onclick="sqltest()"/></label></td></tr><tr><td><div align="right">目标数据源：</div></td><td><select name="select2"><option>person</option><option>telphone</option><option>event</option></select></td></tr>';
	  document.getElementById("sqltable").innerHTML =innerH;
  }
  function delSQL(){
	  document.getElementById("sqltable").innerHTML ="";
	  var innerH='<tr><td height="20px" colspan="2"><input type="button" name="Submit22" value="+" onclick="addSQL()"/>添加自定义SqL</td></tr><tr><td height="117px"></td></tr>';
	  document.getElementById("sqltable").innerHTML =innerH;
  }
  function sqltest(){
	  var databaseid= $('#connectionDBid').val();
	  var sqltest=$('#connectionDBid').val();
	  $.ajax({
	         type:"post",
	         url:"<%=basePath%>sqlTest.action",
	         data:{
	        	 id:databaseid,
	        	 sqltest:sqltest
	         },
	         dataType:"json",
	         success:function(data){
	          //var innerH='';
	          // for(var i = 0; data.rows.length-1>=i;i++){
	        	 //  $("#connectionDBselect").append("<option value='"+data.rows[i].id+"'>"+data.rows[i].connectionName+"----"+data.rows[i].connectionDB+"</option>");
	          	//	innerH+="<tr  onclick='getDB(this)'><td align='center' name='id'>"+data.rows[i].id+"</td><td align='center'>"+data.rows[i].connectionName+"</td><td align='center'>"+data.rows[i].connectionDB+"</td><td><a onclick='editDBCon()' >Edit</a> </td><td><a   onclick='deleteDBCon()' >Delete</a></td></tr>";
	          	 //}
	          //	 document.getElementById("tbody").innerHTML =innerH;
	          // getTableList('',data.rows[0].id);
	           },
	         error:function(){
	        	 hintManager.showHint("没有查询到人物数据，请检查输入是否正确！");
	         }
	     });
	  alert("sqltest");
	 
  }
   </script>
  <body bgcolor="#DBDBDB">
     <script type="text/javascript">
$().ready(function() {

	var $qrCode = $("#qrCode");
	var $mainNav = $("#mainNav li");
	var $currentMainNav = $("#mainNav li.current");
	var $navHighlight = $("#navHighlight");
	
	if ($currentMainNav.size() > 0) {
		$navHighlight.css({
			width: $currentMainNav.outerWidth() + 1,
			left: $currentMainNav.position().left - 1
		});
		
		$mainNav.hover(
			function() {
				var $this = $(this);
				$navHighlight.css({
					width: $this.outerWidth() + 1,
					left: $this.position().left - 1
				});
			}, function() {
				$navHighlight.css({
					width: $currentMainNav.outerWidth() + 1,
					left: $currentMainNav.position().left - 1
				});
			}
		);
	}
});
</script>
<div class="header" >
	<div class="top" >
		<div class="topNav" >
			<div id="mainNav" class="mainNav" style="width:100%;text-align:center">
				<ul>
					<li>
						<a href="<%=basePath%>databaseConnected.action">配置</a>
					</li>
					<li class="current">
						<a href="<%=basePath%>databaseConnectedTableselect.action">导入</a>
					</li>
					<li>
						<a href="/">帮助</a>
					</li>
							
				</ul>
			<span id="navHighlight" class="navHighlight"></span>
		</div>
		</div>
	</div>
	
</div>

<div id="main" class="main">
 <div class="div1">
	<div style="height:80px;background:#F2F2F2;text-align:left;"><span>数据导入-表设置</span></div>
   	<table width="93%" height="399" border="1" bordercolor="#000000">
		  <tr>
		    <td width="50%" valign="top"><table width="100%" height="292" border="1" bordercolor="#A0A0A0">
		      <tr>
		        <td width="221"><div align="right">选择连接：</div></td>
		        <td width="316">
		        	<select id="connectionDBselect" style="width:200px;height:30px" onchange="getTableList(this)">
						 <option value='' selected></option>
					</select>
				</td>
		      </tr>
		      <tr>
		        <td><div align="left"> 源数据库表：</div></td>
		        <td>目标数据源：</td>
		      </tr>
		      <tr>
		        <td><label></label>
		          <select id="connectionTableselect" style="width:200px;height:30px"> </select>
		        </td>
		        <td><select name="select17">
		          <option>person</option>
		          <option>telphone</option>
		          <option>event</option>
		                </select></td>
		      </tr>
		
		      <tr>
		        <td><input type="hidden" id="connectionDBid"></td>
		        <td>&nbsp;</td>
		      </tr>
		      <tr>
		        <td colspan="2">
		        <table id="sqltable" width="100%" height="100%"border="1" bordercolor="#A0A0A0">
		        	<tr>
		        		<td height="20px" colspan="2"><input type="button" name="Submit22" value="+" onclick="addSQL()"/>添加自定义SqL</td>
				    </tr>
				    <tr><td height="117px"></td></tr>
		        </table>
		        </td>
		      </tr>
		      <tr>
		        <td colspan="2"><div align="center"> 
		            <input type="button" class="button white" name="Submit2" value="下一步" onclick=""/>
		       </div></td>
		      </tr>
		    </table>    
		    <p>&nbsp;</p>
		    </td>
		    <td width="50%" valign="top"><p>数据库导入配置：</p>
		      <p>数据库连接：
		        <label>
		          <input type="button" name="Submit" value="+" />
		          </label>
		        MySQLTest</p>
		      <p>导入数据库名：emailmanager</p>
		    <p>表对应关系：</p></td>
		  </tr>
	</table>
    </div>
</div>	
   
  </body>
</html>
