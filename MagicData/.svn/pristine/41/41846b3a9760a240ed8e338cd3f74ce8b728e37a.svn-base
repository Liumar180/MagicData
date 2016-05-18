<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>消息提示</title>
<link href="style/layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="contentcontainer" style="width:500px;margin-top:100px; margin:auto;">
    <div class="headings alt"> 
    	<h2>消息提示</h2> 
    </div> 
	<div class="contentbox" style="height:200px;min-height:200px; text-align: center;">
		<br/>
		<p><s:property value="#request.message"/></p>
   		<p><input type="button" class="btn" value="返回" onclick="javascript:window.location.href='<s:property value="#request.urlAddress"/>'"/></p>
	</div>
</div>
</body>
</html>
