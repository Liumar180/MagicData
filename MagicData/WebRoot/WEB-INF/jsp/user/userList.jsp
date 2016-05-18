<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("base",basePath);
String newusername=(String)session.getAttribute("username");
request.setAttribute("newusername",newusername);
int i=1;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>数据魔方</title>
		<link href="styles/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link href="styles/css/userList.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=basePath%>/scripts/jquery.min.js"></script>
		<script src="styles/bootstrap/js/bootstrap.min.js"></script>
  <script src="styles/bootstrap/js/bootstrap-prompts-alert.js"></script>
	</head>
	<body>
		<div class="userBox">
			<div class="container-fluid container1">
				<div class="userlistlogoBox clearfix">
					<span class="logoSpan"><img src="images/img/title.png"/></span>
					<p class="p1">用户管理</p>
					<div class="topRight">
						<ul>
							<li><a href="index.action">返回首页</a></li>
							<li class="curUser"><a href="quit.action">退出</a></li>
						</ul>
					</div>
				</div>
				<div class="conBox">
					<div class="userCon clearfix">
						<div class="userconTop clearfix">
							<span class="spanBtn">＋添加用户</span>
						</div>
						<form name="frm" action="getAllUser.action" method="post">
						<div class="userSearch form-group clearfix">
							<div class="searchDiv col-md-4 pull-left">
								<span class="pull-left">用户名称：</span>
								<input type="text" class="form-control pull-left" id="username" name="username" value="<c:if test="${usernameInfo!=null}">${usernameInfo}</c:if>" />
							</div>
							<div class="ssBtn pull-left">
								<button class="btn cxBtn" onclick="doQuery();">查询</button>
								<button class="btn czBtn" onclick="resetForm();">重置</button>
							</div>		
						</div>
						<!--表格-->
						<input type="hidden" id ="pageSize" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
						<input type="hidden" id="pageNo" name="pageNo">
						<div class="form-group tableBox">
							<table class="table">
								<thead>
								    <tr>
								        <th class="col-md-2">用户序号</th>
								        <th class="col-md-2">用户名称</th>
								        <th class="col-md-2">用户类型</th>
								        <th class="col-md-3">操作</th>
								    </tr>
								</thead>
	   							<tbody>
								<c:forEach items="${userlist}" var="result" varStatus="status">
									<tr>
							         	<td><c:out value="<%=i++ %>"/></td>
							         	<td><c:out value="${result.userName}"/></td>
							         	<td>
								         	<c:if test="${result.roleType==1}">超级管理员</c:if>
											<c:if test="${result.roleType==2}">普通管理员</c:if>
										</td>
							         	<td>
							         		<!-- <a href="javascript:void(0)" class="bjA"></a> -->
							         		
								            <c:choose>  
											   <c:when test="${result.roleType==1}">
											         <a href="javascript:void(0)" class="a nodel"></a>
											   </c:when> 
											   <c:otherwise>
											         <a href="javascript:void(0)" onclick='deleteUser(${result.id})' class="a delA"></a>
											   </c:otherwise>  
											</c:choose>
								            <c:choose>
										       <c:when test="${result.status==0}">
											        <c:if test="${result.roleType!=1}">
											         	<a href="javascript:void(0)" onclick='modifyStatus(${result.id},1)' class="a closeA"></a>
											        </c:if>
											        <c:if test="${result.roleType==1}">
											         	<a href="javascript:void(0)" style='display: none;' class="a closeA bwWrapper"></a>
											        </c:if>
											   </c:when> 
											   <c:otherwise>
											   		<c:if test="${result.roleType!=1}">
											         	<a href="javascript:void(0)" onclick='modifyStatus(${result.id},0)' class="a openA"></a> 
											         </c:if>
											         <c:if test="${result.roleType==1}">
												         	<a href="javascript:void(0)" style='display: none;' class="a closeA"></a>
											        </c:if>
											   </c:otherwise>
											</c:choose>
							         	</td>
							      	</tr>
							      </c:forEach>
	   							</tbody>
							</table>
						</div>
						</form>
						<!--翻页-->
						<div class="pageDiv form-group" id="PageCtrl_Table">
							<ul class="pagecon list-unstyled">
								<li><a href="#">第 <c:out value="${pageInfo.pageNo}"/>/<c:out value="${pageInfo.totalPages}"/> 页</a></li>
								<li><a href="#">共 <c:out value="${pageInfo.totalRecords}"/> 条</a></li>
								<li><span>每页</span><input type="text" class="inputText pull-left" size="3" id="PageCtrl_pageSize" value="<c:out value="${pageInfo.pageSize}"/>"><span>条</span>&nbsp;&nbsp;</li>					  		
						  		<li><span>前往第</span><input type="text" value="" class="inputText pull-left" size="2" id="PageCtrl_pageGoto"/><span>页</span></li>
				      		</ul>
				      		<div class="divA pull-right">
				      			<a href="javascript:void(0);" id="PageCtrl_pageFirst" onclick="gotoPage(1)">首页</a>
						  		<a href="javascript:void(0);" id="PageCtrl_pagePrev"  onclick="gotoPrevPage()">上页</a>
						  		<a href="javascript:void(0);" id="PageCtrl_pageNext"  onclick="gotoNextPage()" class="nextPage">下页</a>
						  		<a href="javascript:void(0);" id="PageCtrl_pageLast"  onclick="gotoPage(<c:out value="${pageInfo.totalPages}"/>)">末页</a>
				      		</div>
						</div>
					</div>
				</div>
			</div>
			<form action="register.action" name="registerfrm" method="post">  
		         <div class="adduserBox">
						<div class="bg"></div>
						<div class="adduser col-md-4">
							<div class="adduserCon">
								<h3>数据魔方用户管理</h3>
								<div class="form-group">
									<label>用户名：</label>
									<input type="text" name="username" class="form-control" value="" />
								</div><div class="form-group">
									<label>请设置密码：</label>
									<input type="password" name="pwd" class="form-control" value="" />
								</div>
								<div class="form-group">
									<label>请确认密码：</label>
									<input type="password" name="confirmPwd" class="form-control" value="" />
								</div>
								<div class="form-group clearfix">
									<label>验证码：</label>
									<input type="text"  name="randNumInput" id="randNumInput"  class="yzm form-control" value="" />
									<img src="image.jsp" align="absmiddle" id="code" onclick="reloadcode()">
								</div>
								<div class="form-group">
									<input type="button" class="btn zcBtn col-md-offset-3" onclick="register()" value="注册">
									<button class="btn closeBtn">关闭</button>
								</div>
							</div>
						</div>
				</div>
			</form>
		</div>
	</body>
</html>
<!--添加用户-->
<script type="text/javascript">
	$(function(){
		$(".spanBtn").click(function(){
		  $(".adduserBox").show();
	 	})  
		$(".adduserBox .closeBtn").click(function(event){
	    	$(".adduserBox").hide();
			event.preventDefault();
	  	});
	})
   function doQuery(){
	   frm.submit();
   }
   function resetForm(){
	   frm.username.value="";
   }
   var vPageNo = <c:out value="${pageInfo.pageNo}" default="0"/>;
	var vPageSize = <c:out value="${pageInfo.pageSize}" default="0"/>;
	var vTotalPages = <c:out value="${pageInfo.totalPages}" default="0"/>;
	var vTotalRecords = <c:out value="${pageInfo.totalRecords}" default="0"/>;
	function gotoPrevPage(){
		if(vPageNo>1)
			return gotoPage(vPageNo - 1);
		else 
			alert("已到第一页！")
	}
	
	function gotoNextPage(){
		if(vPageNo < vTotalPages)    <%--小于总页数--%>
			return gotoPage(vPageNo + 1);
		else 
			alert("已到最后一页！")
	}
	
	function gotoPage(toPage){
		if(toPage == vPageNo){
			alert("无页可翻！");
			return;
		}
		if(frm){
			if(frm.pageNo){
				frm.pageNo.value = toPage;
				frm.submit();
			} else
				alert("没找到pageNo文本框，请联系开发人员！");
		}	else
			alert("没找到frm，请联系开发人员！");
	}
	
	function changePageSize(pageSize){
		if(frm){
			if(frm.pageSize){
				frm.pageSize.value = pageSize;
				frm.pageNo.value = 1;
				frm.submit();
			} else
				alert("没找到pageSize文本框，请联系开发人员！");
		}	else
			alert("没找到frm，请联系开发人员！");
	}
	$("#PageCtrl_Table").bind("keydown",function(event){
        if ( event.keyCode == 13 )		// 回车键
		{
			var tagEvent = event.srcElement ? event.srcElement : event.target;
			if(tagEvent.id == "PageCtrl_pageGoto"){
				var pn = tagEvent.value;
				if(isNaN(pn) || pn < 1 || pn > vTotalPages)
					alert("请输入 1 -- " + vTotalPages + " 之间的数字！");
				else
					gotoPage(pn);
			} else if(tagEvent.id == "PageCtrl_pageSize") {
				var ps = tagEvent.value;
				if(isNaN(ps) || ps < 1 || ps > vTotalRecords)
					alert("请输入 1 -- " + vTotalRecords + " 之间的数字！");
				else 
					changePageSize(ps);
			}
		}
    });
	function register(){
		  var canSubmit = true;
		  if(registerfrm.username.value == "")
			{
				alert("请填写用户名！");
				canSubmit = false;
				registerfrm.username.focus();
				return false;
			}
			else if(registerfrm.pwd.value == "")
			{
				alert("请填写密码！");
				canSubmit = false;
				registerfrm.pwd.focus();
				return false;
			}
			else if(registerfrm.confirmPwd.value == "")
			{
				alert("请填写确认密码！");
				canSubmit = false;
				registerfrm.confirmPwd.focus();
				return false;
			}
			else if(registerfrm.confirmPwd.value !=registerfrm.pwd.value)
			{
				alert("两次输入的密码不一致");
				canSubmit = false;
				registerfrm.confirmPwd.focus();
				return false;
			}
		    else if(registerfrm.randNumInput.value == "")
			{
				alert("请填写验证码！");
				canSubmit = false;
				registerfrm.randNumInput.focus();
				return false;
			}
			else if(registerfrm.randNumInput.value.length!=4){
			    alert("验证码必须为4位！");
			    registerfrm.randNumInput.value="";
				canSubmit = false;
				registerfrm.randNumInput.focus();
				return false;
			}
			if(canSubmit){
				registerfrm.submit();
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
		function deleteUser(id){
			confirm("是否确认删除用户？",function(){
				window.location.href = "deleteUser.action?id="+id
				}
			);
		}
		
		function modifyStatus(id,status){
			if(status==1){
				confirm("是否确认禁用用户？",function(){
					window.location.href = "modifyStatus.action?status="+status+"&id="+id
					}
				);
			}else{
				confirm("是否确认启用用户？",function(){
					window.location.href = "modifyStatus.action?status="+status+"&id="+id
					}
				);
			}
		}
</script>