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
    <script src="scripts/modernizr.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.wheelmenu.js"></script>
	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.subWheelmenu.js"></script>
    <script type="text/javascript" src="<%=basePath%>/scripts/jquery.toolWheelmenu.js"></script>
	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.js"></script>
	
	
  </head>
  <style>
  #Container{
    width:100%;
    margin:0 auto;/*设置整个容器在浏览器中水平居中*/
}
#Content{
     height:89%;
    margin-top:20px;
     
}
#Content-Left{
     height:70%;
    width:45%;
    margin:20px;
    float:left;
    align:center;
}
#Content-Main{
    height:70%;
    width:30%;
    margin:20px;
    float:left;
}
  body{ text-align:center}
  </style>
  <script type="text/javascript">
  
  function tronmousedown(obj){  
  
  } 
  $(function(){
	  $.ajax({
	         type:"post",
	         url:"<%=basePath%>ImportGetAll.action",
	         dataType:"json",
	         success:function(data){
	          var innerH='';
	           for(var i = 0; data.rows.length-1>=i;i++){
	          		innerH+="<tr><td align='center' name='id' ><div style='width:83px;word-wrap:break-word;'  onclick='getDB("+data.rows[i].id+")' >"+(i+1)+"</div></td><td align='center'><div style='width:110px;word-wrap:break-word;' onclick='getDB("+data.rows[i].id+")' >"+data.rows[i].connectionName+"</div></td><td align='center'><div style='width:110px;word-wrap:break-word;'  onclick='getDB("+data.rows[i].id+")' >"+data.rows[i].connectionDB+"</div></td><td width=90px><a onclick='editDBCon("+data.rows[i].id+")' >Edit</a> </td><td  width=70px><a   onclick='deleteDBCon("+data.rows[i].id+")'>Delete</a></td></tr>";
	          	 }
	          	 document.getElementById("tbody").innerHTML =innerH;
	           },
	         error:function(){
	        	 hintManager.showHint("没有查询到人物数据，请检查输入是否正确！");
	         }
	     });
	  
 });
  function deleteDBCon(id){
	  var connectionName = $('#connectionName').val();
		var connectionDB = $('#connectionDB').val();
		var connectionServerName = $('#connectionServerName').val();
		var connectionDBTableName = $('#connectionDBTableName').val();
		var connectionPort = $('#connectionPort').val();
		var connectionDBUserName = $('#connectionDBUserName').val();
		var connectionDBPassword = $('#connectionDBPassword').val();
		var connectionID =id;
		$.ajax({
	         type:"post",
	         url:"<%=basePath%>ImportDelete.action",
	         data:{
	        	 connectionName:connectionName,
	        	 connectionDB:connectionDB,
	        	 connectionServerName:connectionServerName,
	        	 connectionDBTableName:connectionDBTableName,
	        	 connectionPort:connectionPort,
	        	 connectionDBUserName:connectionDBUserName,
	        	 connectionDBPassword:connectionDBPassword,
	        	 id:connectionID
	         },
	         dataType:"json",
	         success:function(data){
	        	 window.location.reload();
	           },
	         error:function(){
	        	 hintManager.showHint("没有查询到人物数据，请检查输入是否正确！");
	         }
	     });
  }
  function getDB(obj){
	  $.ajax({
	         type:"post",
	         url:"<%=basePath%>ImportGetDb.action",
	         data:{
	        	 id:obj
	         },
	         dataType:"json",
	         success:function(data){
	        	 ImportText('',data.rows[0].connectionDB);
	        		$('#connectionDB').val(data.rows[0].connectionDB);
	        	 	$('#connectionName').val(data.rows[0].connectionName);
		       		$('#connectionServerName').val(data.rows[0].connectionServerName);
		       		$('#connectionDBTableName').val(data.rows[0].connectionDBTableName);
		       		$('#connectionPort').val(data.rows[0].connectionPort);
		       		$('#connectionDBUserName').val(data.rows[0].connectionDBUserName);
		       		$('#connectionDBPassword').val(data.rows[0].connectionDBPassword);
		       		$('#connectionID').val(data.rows[0].id);
		       		
	           },
	         error:function(){
	        	 hintManager.showHint("没有查询到人物数据，请检查输入是否正确！");
	         }
	     });
	  var trs = document.getElementById('tbody').getElementsByTagName('tr');  
	  for( var i=0; i<trs.length; i++ ){  
		    trs[i].onmousedown = function(){  
		    	 for( var o=0; o<trs.length; o++ ){  
		    		 tronmousedown(trs,this);   
		    		}   
		    };  
		   } 
  }
  function tronmousedown(trs,obj){  
	    for( var o=0; o<trs.length; o++ ){  
	     if( trs[o] == obj ){  
	      trs[o].style.backgroundColor = '#DFEBF2';  
	     }  
	     else{  
	      trs[o].style.backgroundColor = '';  
	     }  
	    }
  }
   /*查询入口*/
  	function testDBCon(){
	   
  		var connectionName = $('#connectionName').val();
  		var connectionDB = $('#connectionDB').val();
  		var connectionServerName = $('#connectionServerName').val();
  		var connectionDBTableName = $('#connectionDBTableName').val();
  		var connectionPort = $('#connectionPort').val();
  		var connectionDBUserName = $('#connectionDBUserName').val();
  		var connectionDBPassword = $('#connectionDBPassword').val();
  		$.ajax({
	         type:"post",
	         url:"<%=basePath%>Import.action",
	         data:{
	        	 connectionName:connectionName,
	        	 connectionDB:connectionDB,
	        	 connectionServerName:connectionServerName,
	        	 connectionDBTableName:connectionDBTableName,
	        	 connectionPort:connectionPort,
	        	 connectionDBUserName:connectionDBUserName,
	        	 connectionDBPassword:connectionDBPassword 
	         },
	         dataType:"json",
	         success:function(data){
	           alert( data.rows[0].result);
	           },
	         error:function(){
	        	 hintManager.showHint("没有查询到人物数据，请检查输入是否正确！");
	         }
	     });
  	}
  	 /*存储入口*/
  	function saveDBCon(){
  		var connectionName = $('#connectionName').val();
  		var connectionDB = $('#connectionDB').val();
  		var connectionServerName = $('#connectionServerName').val();
  		var connectionDBTableName = $('#connectionDBTableName').val();
  		var connectionPort = $('#connectionPort').val();
  		var connectionDBUserName = $('#connectionDBUserName').val();
  		var connectionDBPassword = $('#connectionDBPassword').val();
  		if(connectionName==""){
  			alert("请填写连接名");
  			return;
  		}
  		$.ajax({
	         type:"post",
	         url:"<%=basePath%>ImportSave.action",
	         data:{
	        	 connectionName:connectionName,
	        	 connectionDB:connectionDB,
	        	 connectionServerName:connectionServerName,
	        	 connectionDBTableName:connectionDBTableName,
	        	 connectionPort:connectionPort,
	        	 connectionDBUserName:connectionDBUserName,
	        	 connectionDBPassword:connectionDBPassword
	        	 
	         },
	         dataType:"json",
	         success:function(data){
	        	 window.location.reload();
	           },
	         error:function(){
	        	 hintManager.showHint("没有查询到人物数据，请检查输入是否正确！");
	         }
	     });
  	}
  	 /*修改*/
  	function editDBCon(id){
  		var connectionName = $('#connectionName').val();
  		var connectionDB = $('#connectionDB').val();
  		var connectionServerName = $('#connectionServerName').val();
  		var connectionDBTableName = $('#connectionDBTableName').val();
  		var connectionPort = $('#connectionPort').val();
  		var connectionDBUserName = $('#connectionDBUserName').val();
  		var connectionDBPassword = $('#connectionDBPassword').val();
  		var connectionID = $('#connectionID').val();
  		if(connectionID!=id){
  			alert("选中后进行修改");
  		}else{
  			$.ajax({
  		         type:"post",
  		         url:"<%=basePath%>ImportEdit.action",
  		         data:{
  		        	 connectionName:connectionName,
  		        	 connectionDB:connectionDB,
  		        	 connectionServerName:connectionServerName,
  		        	 connectionDBTableName:connectionDBTableName,
  		        	 connectionPort:connectionPort,
  		        	 connectionDBUserName:connectionDBUserName,
  		        	 connectionDBPassword:connectionDBPassword,
  		        	 id:connectionID
  		         },
  		         dataType:"json",
  		         success:function(data){
  		        	 window.location.reload();
  		           },
  		         error:function(){
  		        	 hintManager.showHint("没有查询到人物数据，请检查输入是否正确！");
  		         }
  		     });
  		}
  		
  	}
  	 function ImportText(obj,databasetype){
  		 var databasename="";
  		 if(databasetype==""){
  			databasename=$(obj).children('option:selected').val();
  		 }else{
  			databasename=databasetype;
  		 }
  		 if(databasename=="Mysql"||databasename=="Oracle"||databasename=="HadoopHive"||databasename=="H2"||databasename=="DB2"||databasename=="SQLServer"||databasename=="Sybase"||databasename=="Postgresql"||databasename=="Greenplum"||databasename=="Firebird"){
  			document.getElementById("databaseTable").innerHTML ="";
  			var port="";
  			if(databasename=="Mysql"){port=3306}else if(databasename=="Oracle"){port=1521}else if(databasename=="HadoopHive"){port=9000}else if(databasename=="SQLServer"){port=1433}else if(databasename=="H2"){port=8082}else if(databasename=="DB2"){port=50000}else if(databasename=="Sybase"){port=5001}else if(databasename=="Postgresql"||databasename=="Greenplum"){port=5432}else if(databasename=="Firebird"){port=3050}
  			//var innerH='<tr><td>连接名：</td><td><input type="text" id="connectionName" ><input type="hidden" id="connectionID" ></td></tr><tr><td>主机名称：</td><td><input type="text" id="connectionServerName" ></td></tr><tr><td>数据库名称：</td><td><input type="text" id="connectionDBTableName" ></td></tr><tr><td>端口号：</td><td><input type="text" id="connectionPort" ></td></tr><tr><td>用户名：</td><td><input type="text" id="connectionDBUserName" ></td></tr><tr><td>密码：</td><td><input type="password" id="connectionDBPassword" ></td></tr><tr><td><input type="radio" name="sqlServer" value="SqlServer" checked="checked" />SqlServer</td><td><input type="radio" name="noServer" value="NoServer"  />NoServer</td></tr><tr><td colspan="2"><input type="button" name="test" value="Test" onclick="testDBCon()"><input type="button" name="Save" value="Save" onclick="saveDBCon()"><input type="button" name="Edit" value="Edit" onclick="editDBCon()"><input type="button" name="Delete" value="Delete" onclick="deleteDBCon()"></td><tr>';
  			//var innerH='<tr><td align="center"><input class="easyui-textbox" style="width:200px;height:32px" id="connectionName" data-options="prompt:\'连接名\'"><input type="hidden" id="connectionID" ></td></tr><tr><td>&nbsp;</td></tr><tr><td align="center"><input class="easyui-textbox" style="width:200px;height:32px" id="connectionServerName" data-options="prompt:\'主机名称\'"></td></tr><tr><td>&nbsp;</td></tr><tr><td align="center"><input class="easyui-textbox" style="width:200px;height:32px" id="connectionDBTableName" data-options="prompt:\'数据库名称\'"></td></tr><tr><td>&nbsp;</td></tr><tr><td align="center"><input class="easyui-textbox" style="width:200px;height:32px" id="connectionPort" data-options="prompt:\'端口号\'" value="3306"></td></tr><tr><td>&nbsp;</td></tr><tr><td align="center"><input class="easyui-textbox" style="width:200px;height:32px" id="connectionDBUserName" data-options="prompt:\'Username\'",iconCls:\'icon-man\',iconWidth:38"></td></tr><tr><td>&nbsp;</td></tr><tr><td align="center"><input class="easyui-textbox" style="width:200px;height:32px" type="password" id="connectionDBPassword" data-options="prompt:\'密码\'"></td></tr><tr><td>&nbsp;</td></tr><tr><td align="center"><input type="radio" name="sqlServer" value="SqlServer" checked="checked" />SqlServer<input type="radio" name="noServer" value="NoServer"  />NoServer</td></tr><tr><td colspan="2"><a  class="easyui-linkbutton" data-options="iconCls:\'icon-add\'" onclick="testDBCon()">Test</a><a  class="easyui-linkbutton" data-options="iconCls:\'icon-add\'" onclick="saveDBCon()">Save</a></td><tr>';
  			var innerH='<tr><td width="250" align="right">连接名&nbsp;&nbsp;</td><td align="left">&nbsp;<input style="width:200px;height:32px" id="connectionName"><input type="hidden" id="connectionID" ></td></tr><tr><td>&nbsp;</td></tr><tr><td width="120" align="right">主机名称&nbsp;&nbsp;</td><td align="left">&nbsp;<input style="width:200px;height:32px" id="connectionServerName"></td></tr><tr><td>&nbsp;</td></tr><tr><td width="120" align="right">数据库名称&nbsp;&nbsp;</td><td align="left">&nbsp;<input style="width:200px;height:32px" id="connectionDBTableName"></td></tr><tr><td>&nbsp;</td></tr><tr><td width="120" align="right">端口号&nbsp;&nbsp;</td><td align="left">&nbsp;<input  style="width:200px;height:32px" id="connectionPort" value="'+port+'"></td></tr><tr><td>&nbsp;</td></tr><tr><td width="120" align="right">Username&nbsp;&nbsp;</td><td align="left">&nbsp;<input style="width:200px;height:32px" id="connectionDBUserName" ></td></tr><tr><td>&nbsp;</td></tr><tr><td width="120" align="right">密码&nbsp;&nbsp;</td><td align="left">&nbsp;<input  style="width:200px;height:32px" type="password" id="connectionDBPassword" ></td></tr><tr><td>&nbsp;</td></tr><tr><td width="120" align="left"></td><td align="left">&nbsp;<input type="radio" name="sqlServer" value="SqlServer" checked="checked" />SqlServer&nbsp;&nbsp;&nbsp;<input type="radio" name="noServer" value="NoServer"  />NoServer</td></tr><tr><td>&nbsp;</td></tr><tr><td colspan="2" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"  style="width:50px;" onclick="testDBCon()" value="Test">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onclick="saveDBCon()" style="width:50px;" value="Save"></td><tr>';
  			document.getElementById("databaseTable").innerHTML =innerH;
  			
  		 }else if(databasename=="AS400"){
  			document.getElementById("databaseTable").innerHTML ="";
  		//	var innerH='<tr><td>连接名：</td><td><input type="text" id="connectionName" ><input type="hidden" id="connectionID" ></td></tr><tr><td>主机名称：</td><td><input type="text" id="connectionServerName" ></td></tr><tr><td>数据库名称：</td><td><input type="text" id="connectionDBTableName"  disabled="ture"></td></tr><tr><td>端口号：</td><td><input type="text" id="connectionPort"  disabled="ture"></td></tr><tr><td>用户名：</td><td><input type="text" id="connectionDBUserName" ></td></tr><tr><td>密码：</td><td><input type="password" id="connectionDBPassword" ></td></tr><tr><td><input type="radio" name="sqlServer" value="SqlServer" checked="checked" />SqlServer</td><td><input type="radio" name="noServer" value="NoServer"  />NoServer</td></tr><tr><td colspan="2"><input type="button" name="test" value="Test" onclick="testDBCon()"><input type="button" name="Save" value="Save" onclick="saveDBCon()"><input type="button" name="Edit" value="Edit" onclick="editDBCon()"><input type="button" name="Delete" value="Delete" onclick="deleteDBCon()"></td><tr>';
  			var innerH='<tr><td width="250" align="right">连接名&nbsp;&nbsp;</td><td align="left">&nbsp;<input style="width:200px;height:32px" id="connectionName"><input type="hidden" id="connectionID" ></td></tr><tr><td>&nbsp;</td></tr><tr><td width="120" align="right">主机名称&nbsp;&nbsp;</td><td align="left">&nbsp;<input style="width:200px;height:32px" id="connectionServerName"></td></tr><tr><td>&nbsp;</td></tr><tr><td width="120" align="right">数据库名称&nbsp;&nbsp;</td><td align="left">&nbsp;<input style="width:200px;height:32px" id="connectionDBTableName"  disabled="ture"></td></tr><tr><td>&nbsp;</td></tr><tr><td width="120" align="right">端口号&nbsp;&nbsp;</td><td align="left">&nbsp;<input  style="width:200px;height:32px" id="connectionPort"  disabled="ture"></td></tr><tr><td>&nbsp;</td></tr><tr><td width="120" align="right">Username&nbsp;&nbsp;</td><td align="left">&nbsp;<input style="width:200px;height:32px" id="connectionDBUserName" ></td></tr><tr><td>&nbsp;</td></tr><tr><td width="120" align="right">密码&nbsp;&nbsp;</td><td align="left">&nbsp;<input  style="width:200px;height:32px" type="password" id="connectionDBPassword" ></td></tr><tr><td width="120" align="left"></td><td align="left">&nbsp;<input type="radio" name="sqlServer" value="SqlServer" checked="checked" />SqlServer&nbsp;&nbsp;&nbsp;<input type="radio" name="noServer" value="NoServer"  />NoServer</td></tr><tr><td>&nbsp;</td></tr><tr><td colspan="2" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"  style="width:50px;" onclick="testDBCon()" value="Test">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onclick="saveDBCon()" style="width:50px;" value="Save"></td><tr>';
  			
  			document.getElementById("databaseTable").innerHTML =innerH;
  		 }else if(databasename=="Derby"||databasename=="Impala"){
  			var port="";
  			if(databasename=="Derby"){port=1527}else if(databasename=="Impala"){port=21050}
  			document.getElementById("databaseTable").innerHTML ="";
  			//var innerH='<tr><td>连接名：</td><td><input type="text" id="connectionName" ><input type="hidden" id="connectionID" ></td></tr><tr><td>主机名称：</td><td><input type="text" id="connectionServerName" ></td></tr><tr><td>数据库名称：</td><td><input type="text" id="connectionDBTableName" ></td></tr><tr><td>端口号：</td><td><input type="text" id="connectionPort" ></td></tr><tr><td>用户名：</td><td><input type="text" id="connectionDBUserName"  disabled="ture"></td></tr><tr><td>密码：</td><td><input type="password" id="connectionDBPassword"  disabled="ture"></td></tr><tr><td><input type="radio" name="sqlServer" value="SqlServer" checked="checked" />SqlServer</td><td><input type="radio" name="noServer" value="NoServer"  />NoServer</td></tr><tr><td colspan="2"><input type="button" name="test" value="Test" onclick="testDBCon()"><input type="button" name="Save" value="Save" onclick="saveDBCon()"><input type="button" name="Edit" value="Edit" onclick="editDBCon()"><input type="button" name="Delete" value="Delete" onclick="deleteDBCon()"></td><tr>';
  			var innerH='<tr><td width="250" align="right">连接名&nbsp;&nbsp;</td><td align="left">&nbsp;<input style="width:200px;height:32px" id="connectionName"><input type="hidden" id="connectionID" ></td></tr><tr><td>&nbsp;</td></tr><tr><td width="120" align="right">主机名称&nbsp;&nbsp;</td><td align="left">&nbsp;<input style="width:200px;height:32px" id="connectionServerName"></td></tr><tr><td>&nbsp;</td></tr><tr><td width="120" align="right">数据库名称&nbsp;&nbsp;</td><td align="left">&nbsp;<input style="width:200px;height:32px" id="connectionDBTableName"></td></tr><tr><td>&nbsp;</td></tr><tr><td width="120" align="right">端口号&nbsp;&nbsp;</td><td align="left">&nbsp;<input  style="width:200px;height:32px" id="connectionPort" value="'+port+'"></td></tr><tr><td>&nbsp;</td></tr><tr><td width="120" align="right">Username&nbsp;&nbsp;</td><td align="left">&nbsp;<input style="width:200px;height:32px" id="connectionDBUserName" disabled="ture" ></td></tr><tr><td>&nbsp;</td></tr><tr><td width="120" align="right">密码&nbsp;&nbsp;</td><td align="left">&nbsp;<input  style="width:200px;height:32px" type="password" id="connectionDBPassword" disabled="ture" ></td></tr><tr><td>&nbsp;</td></tr><tr><td width="120" align="left"></td><td align="left">&nbsp;<input type="radio" name="sqlServer" value="SqlServer" checked="checked" />SqlServer&nbsp;&nbsp;&nbsp;<input type="radio" name="noServer" value="NoServer"  />NoServer</td></tr><tr><td>&nbsp;</td></tr><tr><td colspan="2" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"  style="width:50px;" onclick="testDBCon()" value="Test">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onclick="saveDBCon()" style="width:50px;" value="Save"></td><tr>';
  			document.getElementById("databaseTable").innerHTML =innerH;
  		 }else if(databasename=="Sqlite"){
  			document.getElementById("databaseTable").innerHTML ="";
  			var innerH='<tr><td>连接名：</td><td><input type="text" id="connectionName" ><input type="hidden" id="connectionID" ></td></tr><tr><td>主机PATH：</td><td><input type="text" id="connectionServerName" ></td></tr><tr><td>数据库名称：</td><td><input type="text" id="connectionDBTableName"  disabled="ture"></td></tr><tr><td>端口号：</td><td><input type="text" id="connectionPort"  disabled="ture"></td></tr><tr><td>用户名：</td><td><input type="text" id="connectionDBUserName"  disabled="ture"></td></tr><tr><td>密码：</td><td><input type="password" id="connectionDBPassword"  disabled="ture"></td></tr><tr><td><input type="radio" name="sqlServer" value="SqlServer" checked="checked" />SqlServer</td><td><input type="radio" name="noServer" value="NoServer"  />NoServer</td></tr><tr><td colspan="2"><input type="button" name="test" value="Test" onclick="testDBCon()"><input type="button" name="Save" value="Save" onclick="saveDBCon()"><input type="button" name="Edit" value="Edit" onclick="editDBCon()"><input type="button" name="Delete" value="Delete" onclick="deleteDBCon(this)"></td><tr>';
  			var innerH='<tr><td width="250" align="right">连接名&nbsp;&nbsp;</td><td align="left">&nbsp;<input style="width:200px;height:32px" id="connectionName"><input type="hidden" id="connectionID" ></td></tr><tr><td>&nbsp;</td></tr><tr><td width="120" align="right">主机名称&nbsp;&nbsp;</td><td align="left">&nbsp;<input style="width:200px;height:32px" id="connectionServerName"></td></tr><tr><td>&nbsp;</td></tr><tr><td width="120" align="right">数据库名称&nbsp;&nbsp;</td><td align="left">&nbsp;<input style="width:200px;height:32px" id="connectionDBTableName"  disabled="ture"></td></tr><tr><td>&nbsp;</td></tr><tr><td width="120" align="right">端口号&nbsp;&nbsp;</td><td align="left">&nbsp;<input  style="width:200px;height:32px" id="connectionPort"  disabled="ture"></td></tr><tr><td>&nbsp;</td></tr><tr><td width="120" align="right">Username&nbsp;&nbsp;</td><td align="left">&nbsp;<input style="width:200px;height:32px" id="connectionDBUserName"  disabled="ture"></td></tr><tr><td>&nbsp;</td></tr><tr><td width="120" align="right">密码&nbsp;&nbsp;</td><td align="left">&nbsp;<input  style="width:200px;height:32px" type="password" id="connectionDBPassword"  disabled="ture"></td></tr><tr><td>&nbsp;</td></tr><tr><td width="120" align="left"></td><td align="left">&nbsp;<input type="radio" name="sqlServer" value="SqlServer" checked="checked" />SqlServer&nbsp;&nbsp;&nbsp;<input type="radio" name="noServer" value="NoServer"  />NoServer</td></tr><tr><td>&nbsp;</td></tr><tr><td colspan="2" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"  style="width:50px;" onclick="testDBCon()" value="Test">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onclick="saveDBCon()" style="width:50px;" value="Save"></td><tr>';
  			
  			document.getElementById("databaseTable").innerHTML =innerH;
  		 }
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
<div id="Container">
      <div class="header" >
		<div class="top" >
			<div class="topNav" >
				<div id="mainNav" class="mainNav" style="width:900px;text-align:center">
					<ul>
						<li class="current">
							<a href="<%=basePath%>databaseConnected.action">配置</a>
						</li>
						<li>
							<a href="<%=basePath%>getDImpPage.action">导入</a>
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
    <div id="Content">
        <div id="Content-Left">
        	<br /><br />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<select id="connectionDB" style="width:200px;height:32px" onchange="ImportText(this,'')">
					  <option value ="Mysql" selected>MySQL</option>
					  <option value ="Oracle">Oracle</option>
					  <option value ="SQLServer">SQLServer</option>
					  <option value ="DB2">DB2</option>
					  <option value ="AS400">AS/400</option>
					  <option value ="Sybase">Sybase</option>
					  <option value ="Postgresql">Postgresql</option>
					  <option value ="Derby">Derby</option>
					  <option value ="Firebird">Firebird</option>
					  <option value ="Greenplum">Greenplum</option>
					  <option value ="H2">H2</option>
					  <option value ="HadoopHive">HadoopHive</option>
					  <option value ="Impala">Impala</option>
					  <option value ="Sqlite">Sqlite</option>
				</select><br/><br/>
	    		<table id="databaseTable" border="0" cellpadding="3" cellspacing="0" >
	    			<tr>
	    				<td width="250" align="right">连接名&nbsp;&nbsp;</td>
	    				<td align="left">
	    					&nbsp;<input style="width:200px;height:32px" id="connectionName">
	    					<input type="hidden" id="connectionID" >
	    				</td>
	    			</tr>
	    			<tr>
	    				<td>&nbsp;</td>
	    			</tr>
	    			<tr>
	    			<td width="120" align="right">主机名称&nbsp;&nbsp;</td>
	    				<td align="left">&nbsp;<input style="width:200px;height:32px" id="connectionServerName"></td>
	    			</tr>
	    			<tr>
	    				<td>&nbsp;</td>
	    			</tr>
	    			<tr>
	    			<td width="120" align="right">数据库名称&nbsp;&nbsp;</td>
	    				<td align="left">&nbsp;<input style="width:200px;height:32px" id="connectionDBTableName"></td>
	    			</tr>
	    			<tr>
	    				<td>&nbsp;</td>
	    			</tr>
	    			<tr>
	    			<td width="120" align="right">端口号&nbsp;&nbsp;</td>
	    				<td align="left">&nbsp;<input  style="width:200px;height:32px" id="connectionPort" value="3306"></td>
	    			</tr>
	    			<tr>
	    				<td>&nbsp;</td>
	    			</tr>
	    			<tr>
	    			<td width="120" align="right">Username&nbsp;&nbsp;</td>
	    				<td align="left">&nbsp;<input style="width:200px;height:32px" id="connectionDBUserName" ></td>
	    			</tr>
	    			<tr>
	    				<td>&nbsp;</td>
	    			</tr>
	    			<tr>
	    			<td width="120" align="right">密码&nbsp;&nbsp;</td>
	    				<td align="left">&nbsp;<input  style="width:200px;height:32px" type="password" id="connectionDBPassword" ></td>
	    			</tr>
	    			<tr>
	    				<td>&nbsp;</td>
	    			</tr>
	    			<tr>
	    				<td width="120" align="left"></td>
	    				<td align="left">&nbsp;<input type="radio" name="sqlServer" value="SqlServer" checked="checked" />SqlServer&nbsp;&nbsp;&nbsp;<input type="radio" name="noServer" value="NoServer"  />NoServer</td>
	    			</tr>
	    			<tr>
	    				<td>&nbsp;</td>
	    			</tr>
	    			<tr>
	    				<td colspan="2" align="center">
	    				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"  style="width:50px;" onclick="testDBCon()" value="Test">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    				<input type="button" onclick="saveDBCon()" style="width:50px;" value="Save">
	    				</td>
	    			<tr>
	    		</table>
	    	
		</div>
        <div id="Content-Main">
        <br><br>
	    		<table id="dblist" border="0" cellpadding="3" cellspacing="0" bgColor=#FFFAF0 >
	    			<thead style="display:block;">  
				        <tr bgcolor="#63B8FF">  
				            <th width="83" style="text-align:center;">NO.</th> 
				            <th width="110" style="text-align:center;">连接名</th>  
				            <th width="110" style="text-align:center;">数据库</th>  
				            <th width="70">修改</th> 
				            <th width="60">删除</th> 
				        </tr>  
				    </thead>  
				    <tbody id="tbody"  style="height:300px;overflow:auto;display:block;">  
				       
				    </tbody> 
	
	    		</table>
		</div>
		<div id="tbody1"></div>
    </div>
    
</div>
   
  </body>
</html>
