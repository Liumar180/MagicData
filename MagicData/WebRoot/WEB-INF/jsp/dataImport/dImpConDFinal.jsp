<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ contextPath + "/";
	pageContext.setAttribute("base", contextPath);
%>
<title>数据导入-选择数据库连接</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link href="<%=basePath%>/styles/css/common.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>/styles/css/buttons.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>/styles/css/jquery-ui.min.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>/styles/css/ui.jqgrid.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>/styles/css/dataImport/createDataImport.css" rel="stylesheet" type="text/css">
</head>
<style>
html {
	overflow-x: hidden;
	background: #012548;
}
</style>
<body class="subBody" style="height: 200px">
	<div style="padding:20px;width: 50%;height:100%;">
		<table width="100%" height="100%" border="0">
			<tr>
				<td width="100%" valign="top" align="center">
					<div
						style="float:left;margin-left: 18%;padding-top:20px;width: 50%;height:100%;">
						<table width="80%" border="0" style="color: white;font-size: 14px">
							<!-- <tr align="center">
								<td width="35%" height="40px">任务名称</td>
								<td width="35%">创建时间</td>
								<td>状态</td>
								<td width="15%">操作</td>
							</tr> -->					
							<tr align="center">
							    <%-- <c:if test="${returnShowDIT==null}"><td colspan="4">操作错误，请确认表单数据的正确性后，再次操作</td></c:if>
							    <c:if test="${returnShowDIT!=null}"> --%>
							    <td colspan="4">任务建立完成</td>
								<%-- <td width="35%" height="40px">${returnShowDIT.taskName}</td>
								<td width="35%">${returnShowDIT.sourceName}</td>
								<td>${returnShowDIT.checkTargetTypes}</td> --%>
								<%-- <td width="15%" align="center"><c:if
										test="${returnShowDIT.runStatus==0}">
										<a href="javascript:startTask(${returnShowDIT.id})" class="executeButton">执行</a>
									</c:if>
								</td> --%>
								<%-- </c:if> --%>
							</tr>
						</table>
						<br />
						<div align="center">
							<input type="button" class="button white" name="nextButton"
								value="再次配置" onclick="createNew()" />
						</div>
						<!-- <div id="runningDiv">
						  <div>正在执行导入任务...</div>
						</div> -->
					</div>
				</td>
			</tr>
		</table>
	</div>
	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/scripts/ui/jquery-ui.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
		    window.parent.window.changeDataTitle("数据导入-完成");
		    window.parent.window.flushGrid();
			/* $("#runningDiv").dialog({
	              hide:true,
	              autoOpen:false,	   
	              width:400,
	              height:200,
	              modal:true,
	              title:"导入任务执行",
	              overlay: {opacity: 0.5,overflow:'auto',backgroundColor: '#fff',},
	              buttons:{ 
                       "关闭":function(){ 
                               $("#runningDiv").dialog("close"); 
                          } 
                  }
	        }); */
	        
		});
		
		function createNew(){
		   window.parent.window.showNameInfo("");
		   window.parent.window.showConnInfo("", "");
		   window.parent.window.showSourceInfo("");
		   window.parent.window.showTargetInfo("")
		   window.parent.window.showFieldInfo(""); 
		   window.location = "<%=basePath%>getDImpAConPage.action?updateFlag=0";
		}
		
	    function startTask(taskId){
	        $("#runningDiv").load("<%=basePath%>ajax_dataImport/executeTask.action", {"taskId" : taskId});
			$("#runningDiv").dialog("open");
		}
	</script>
</body>

</html>