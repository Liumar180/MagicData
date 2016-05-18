<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>恢复出厂</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="styles/lawcase/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="styles/systemManage/css/reset.css"/>
  </head>
  
  <body>
  	<div class="resetDiv">恢复出厂设置功能会删除系统现有的数据，还原到最初的安装状态。请谨慎使用！</div>
  	<div class="resetbtnDiv">
    	<input type="button" class="resetBtn"  onclick="reset()" value="恢复出厂设置">
    </div>
    
    <script type="text/javascript" src="scripts/jquery.min.js"></script>
    <script type="text/javascript" src="styles/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="styles/bootstrap/js/bootstrap-prompts-alert.js"></script>
    <script type="text/javascript">
    	function reset(){
    		confirm("恢复出厂设置功能会删除系统现有的数据，还原到最初的安装状态。确认恢复出厂设置？",function(){
	    		$.ajax({
			         type:"post",
			         url:"ajaxReset/resetData.action",
			         async : false,
			         dataType:"json",
			         success:function(data){
						 if(data.flag){
						 	parent.hintManager.showInfoHint("恢复出厂设置成功。");
						 }else{
							parent.hintManager.showHint("恢复出厂设置失败，请联系管理员！");
						 }
			         },
			         error:function(){
			        	 parent.hintManager.showHint("恢复出厂设置异常，请联系管理员！");
			         }
			     });
			});
    	}
    </script>
  </body>
</html>
