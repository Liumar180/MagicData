<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>系统管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="styles/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="styles/systemManage/css/common.css"/>
	<link rel="stylesheet" type="text/css" href="styles/systemManage/css/systemManage.css"/>
	<link rel="stylesheet" type="text/css" href="styles/css/customCss.css">
	<script type="text/javascript" src="scripts/jquery.min.js" ></script>
	<script type="text/javascript" src="scripts/systemManage/systemManage.js" ></script>
	<script type="text/javascript" src="scripts/customJs.js"></script>
	<script type="text/javascript" src="scripts/ajaxsetup.js"></script>
  </head>
  
  <body>
		<div class="container">
			<nav class="navbar" role="navigation">
				<div class="navbar-header">
      				<a class="navbar-brand" href="#"><img src="images/systemManage/DataSmart-logo.png"/></a>
      				<span class="dataSpan"><em>|</em>系统管理</span> 
   				</div>
<!--    				<div>
      				<p class="navbar-text navbar-right">
      					<img src="images/systemManage/personIcon.png"/>
         				<a href="#" class="navbar-link">系统管理员</a>
      				</p>
   				</div> -->
			</nav>
			<div class="mainCon clearfix">
				<div class="leftCon pull-left">
					<div class="leftInner1">
						<div class="leftInner2">
							<ul class="list-unstyled">
								<c:if test="${fn:contains(permissionList,'userManage')}">
									<li class="setOn"><a href="auth/viewUserPage.action" target="text">用户管理</a></li>
      							</c:if>
      							<c:if test="${fn:contains(permissionList,'roleManage')}">
									<li class="role"><a href="auth/viewRolePage.action" target="text">角色管理</a></li>
      							</c:if>
      							<c:if test="${fn:contains(permissionList,'logManage')}">
									<li class="log"><a href="logManage/logManageIndex.action" target="text">日志管理</a></li>
      							</c:if>
      							<c:if test="${fn:contains(permissionList,'objectManage')}">
									<li class="object"><a href="ObjectManage/infos.action" target="text">对象管理</a></li>
      							</c:if>
      							<c:if test="${fn:contains(permissionList,'dataManage')}">
									<li class="data"><a href="import/getImportPage.action" target="text">数据管理</a></li>
      							</c:if>
      							<c:if test="${fn:contains(permissionList,'factoryReset')}">
	      							<li class="reset"><a href="reset/viewResetPage.action" target="text">恢复出厂</a></li>
      							</c:if>
      							<li class="sysConf"><a href="system/viewSystemConfigPage.action" target="text">系统配置</a></li>
							</ul>
						</div>
					</div>								
				</div>
				<div class="rightCon pull-right">
					<iframe src="" name="text" frameborder="0" width="100%" height="100%"></iframe>
				</div>
			</div>
			<!--点击编辑出现的弹出层-->
			<div class="tccBox">
				<div class="bg"></div>
				<div class="addBox">
					<div class="addConBox">
						<h3>添加案件</h3>
						<b></b>
						<iframe src=""  id="pop_iframe" name="pop_iframe" frameborder="0" width="100%" height="95%"></iframe>						
					</div>
				</div>
			</div>
		</div>
		<div class='hintMsg'></div>
		<script type="text/javascript">
			function parentPop(data) {
				$(".tccBox").show();
				$(".tccBox h3").text(data.title);
				$("#pop_iframe").attr("src", data.url);
			}
			/*关闭弹出层*/
			function dismissParentPop() {
				$(".tccBox").hide();
				$("#pop_iframe").attr("src", "");
			}
		</script>
	</body>
</html>
