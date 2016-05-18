<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("base",basePath);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>数据魔方</title>
		<link href="styles/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link href="styles/css/login.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=basePath%>/scripts/jquery.min.js"></script>
		<script src="styles/bootstrap/js/bootstrap.min.js"></script>
  		<script src="styles/bootstrap/js/bootstrap-prompts-alert.js"></script>
	</head>
	<body>
		<div class="box">
			<div class="container-fluid container1">
				<div class="logoBox">
					<img src="images/img/loginLogo.png"/>
				</div>
				<div class="loginCon clearfix">
					<h1>欢迎登录</h1>
					<div class="form clearfix">
						<form name="frm" class="clearfix" action="getUserInfo.action" method="post"  onsubmit="return false;" autocomplete="off">
							 <div class="form-group clearfix">
							      <div class="usernameBox">
							      	<span class="glyphicon glyphicon-user"></span>
							         <input type="text" class="form-control" id="username" name="username"
							            placeholder="用户名">
							      </div>
   							</div>
						   <div class="form-group clearfix">
						      <div class="passwordBox">
						      	<span class="glyphicon glyphicon-lock"></span>
						         <input type="password" class="form-control" id="pwd" name="pwd"
						            placeholder="密码">
						      </div>
						   </div>
						   <div class="form-group clearfix">
						      <div class="yzmBox">
						      	<span class="glyphicon glyphicon-star-empty"></span>
						         <input type="yzm" class="form-control" id="randNumInput"  name="randNumInput" 
						            placeholder="验证码">
						          <span class="code"><img src="image.jsp"  id="code" onclick="reloadcode()"/></span>
						      </div>
						   </div>
							<div class="form-group clearfix">
							<button type="button" class="btn loginBtn" onclick="login();" autocomplete="off">登&nbsp;&nbsp;录</button>
							</div>
							
						</form>
					</div>
				</div>
				<div class="loginFooter">
					<p>版权所有：北京永信至诚科技有限公司</p>
					<p>公司地址：北京市海淀区东北旺西路8号中关村软件园云基地C座3层</p>
					<p>京ICP备12023396号    京公网安备110108013184514</p>
				</div>
			</div>
		</div>
	</body>
</html>
<script>

<c:if test='${not empty resultmap.resultMsg}'>
	$(function(){
		alert("${resultmap.resultMsg}");
		$("#username").val("${resultmap.username}");
		$("#pwd").val("${resultmap.pwd}");
	});
</c:if>

function login(){
  var canSubmit = true;
  if(frm.username.value == "")
	{
		alert("请填写用户名！");
		canSubmit = false;
		frm.username.focus();
		return false;
	}
	else if(frm.pwd.value == "")
	{
		alert("请填写密码！");
		canSubmit = false;
		frm.pwd.focus();
		return false;
	}
    else if(frm.randNumInput.value == "")
	{
		alert("请填写验证码！");
		canSubmit = false;
		frm.randNumInput.focus();
		return false;
	}
	else if(frm.randNumInput.value.length!=4){
	    alert("验证码必须为4位！");
	    frm.randNumInput.value="";
		canSubmit = false;
		frm.randNumInput.focus();
		return false;
	}
	if(canSubmit){
		frm.submit();
	}
}
function reloadcode(){
      var verify=document.getElementById('code');
      verify.setAttribute('src','image.jsp?it='+Math.random());
}
if(top.location.href.indexOf("/index.action") <= -1 && ${empty resultmap.resultMsg})
{
	top.location.href = "./index.action";
}
$("#randNumInput").bind("keydown",function(event){
    if ( event.keyCode == 13 )		// 回车键
	{
    	login();
	}
});
</script>