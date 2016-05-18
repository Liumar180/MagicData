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
    <title>注册页面</title>
    <script type="text/javascript" src="<%=basePath%>/scripts/jquery.min.js"></script>
      <link href="<%=basePath%>/styles/css/font-awesome.min.css" rel="stylesheet" type="text/css">  
      <link href="<%=basePath%>/styles/flexslider.css" rel="stylesheet" type="text/css" media="screen" />
      <link href="<%=basePath%>/styles/css/templatemo_style.css" rel="stylesheet" type="text/css">
      <link href="<%=basePath%>/styles/css/base.css" rel="stylesheet" type="text/css">
      <link href="<%=basePath%>/styles/css/index.css" rel="stylesheet" type="text/css">
      <link href="<%=basePath%>/styles/css/style.css" rel="stylesheet" type="text/css">
       <style type="text/css">
		html{height:100%}
		body{height:100%;margin:0;padding:0;font-size:15px;}
		.div-relative{position:relative; height:100%; width:100%} 
		.div-a{ position:absolute; right:100px; bottom:30px;width:40px; height:130px} 
		.olControlButtonItemActive {
         position: absolute;
         cursor: pointer; 
         top: 5px;
         right: 0px;
         background-image: url("images/map/back.png");
         width: 18px;
         height: 18px;
           }

        .olControlPanel {
        width: 25px;
        height: 25px;
        top: 5px;
        right: 0px;
          }

    </style>
  </head>
  
  <body>
    <div  style="margin:200px" id="login">
        <form action="register.action" name="frm" method="post">  
        <div style="font-size:30px; color: #ccc;margin:50px">数据魔方用户管理</div>
	    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户名&nbsp;&nbsp;<input type="text" name="username" style="width:150px"><br><br>  </span>
	    <span>请设置密码&nbsp;&nbsp;<input type="password" name="pwd" style="width:150px"><br><br> </span> 
	    <span>请确认密码&nbsp;&nbsp;<input type="password" name="confirmPwd" style="width:150px"><br><br></span>
	    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	          &nbsp;&nbsp;&nbsp;验证码&nbsp;&nbsp;<input type="text" name="randNumInput" id="randNumInput" style="width:150px"/>
	        <img src="image.jsp" align="absmiddle" id="code" onclick="reloadcode()"><br><br>    </span>
	    <input type="button" value="注册" onclick="register()">  
    </div>
    
    
    </form>  
  </body>
</html>
<script>
function register(){
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
	else if(frm.confirmPwd.value == "")
	{
		alert("请填写确认密码！");
		canSubmit = false;
		frm.confirmPwd.focus();
		return false;
	}
	else if(frm.confirmPwd.value !=frm.pwd.value)
	{
		alert("两次输入的密码不一致");
		canSubmit = false;
		frm.confirmPwd.focus();
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
$("#randNumInput").bind("keydown",function(event){
    if ( event.keyCode == 13 )		// 回车键
	{
    	register();
	}
});
</script>
