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
    <title>关联组织详情</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/common.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/ajControl.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/my.css"/>
  </head>
  <style type="text/css">
			/*iframe的背景  */
			html,body{background-position:center,center;}
 </style>
  <body>

		<div class="userconBox">
			<div class="userCon clearfix">
				<div class="xxinnerCon" style="display: inherit;;">
					
					<div class="table-responsive col-md-12 tableDiv">
				   		<table class="table table-bordered">
						      <thead>
						         <tr> 
						            <th colspan="8">组织：${orgObj.orgCName}</th>					            
						         </tr>
					      	  </thead>
						      <tbody>
						      	<tr>
						      		<td class="col-md-2"  rowspan="4">
						      		<c:if test="${empty orgObj.orgImage}">
						      		<img src="images/lawCase/moren/organaization.jpg"/>
						      		</c:if>
						      		<c:if test="${!empty orgObj.orgImage}">
						      		<img width="116px" height="138px"  src="images/lawCase/uploadImg/organization/${orgObj.orgImage}"/>
						      		</c:if>
						      		</td>
						            <td class="col-md-1">组织名称:</td>
						            <td class="col-md-2">${orgObj.orgCName}</td>	
						            <td class="col-md-1">拼音:</td>
						            <td class="col-md-1">${orgObj.orgSpell}</td>
						            <td class="col-md-1">英文名称:</td>
						            <td class="col-md-3">${orgObj.orgEName}</td>	
						         </tr>
						         <tr>
						            <td class="col-md-1">别名:</td>
						            <td class="col-md-2">${orgObj.orgAlias}</td>	
						         	<td class="col-md-1">组织级别:</td>	
						            <td class="col-md-1">${orgObj.orgImportLevelStr}</td>
						            <td class="col-md-1">所属方向:</td>
						            <td class="col-md-3">${orgObj.orgDirectionStr}</td>
						         </tr>
						         <tr>
						            <td class="col-md-1">对象状态:</td>
						            <td class="col-md-2">${orgObj.orgStatusStr}</td>	
						            <td class="col-md-1">控制状态:</td>
						            <td class="col-md-1">${orgObj.orgControlStatusStr}</td>
						            <td class="col-md-1">成立时间:</td>	
						            <td class="col-md-3"><fmt:formatDate value="${orgObj.createTime}" type="date"/></td>
						         </tr>
						         <tr>
						            <td class="col-md-1">所在地:</td>	
						            <td class="col-md-2" >${orgObj.orgLocation}</td>
						            <td class="col-md-1">录入人:</td>
						            <td class="col-md-1">${orgObj.orgInputPersonStr}</td>	
						            <td class="col-md-1">负责人:</td>
						            <td class="col-md-3" >${orgObj.orgDutyPersonStr}</td>	
						         </tr>  
<!-- 						        <tr> -->
<!-- 						            <td class="col-md-1">关键词:</td> -->
<!-- 						            <td class="col-md-2" colspan="5">涉疆  涉藏   敌对</td>									             -->
<!-- 						        </tr> -->
						        <tr>
						            <td class="col-md-2">组织纲领:</td>
						            <td class="col-md-2" colspan="8">${orgObj.orgRemark}</td>									            
						        </tr>
						       	<tr>
						            <td class="col-md-2">简介:</td>
						            <td class="col-md-2" colspan="8">${orgObj.orgDescription}</td>									            
						        </tr>									         									        									     									         								         									        									        
						     </tbody>
				   		</table>
					</div>
					<div class="clear"></div>
				</div>												
			</div>
		</div>
	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>scripts/lawCase/my.js" ></script>
	<script type="text/javascript" src="<%=basePath%>scripts/lawCase/common.js" ></script>
	<script type="text/javascript" src="<%=basePath%>scripts/lawCase/Popup.js" ></script>
	<script type="text/javascript" src="<%=basePath%>scripts/lawCase/inputSelect.js" ></script>
	<script type="text/javascript" src="<%=basePath%>scripts/lawCase/caseXq.js" ></script>
	<script type="text/javascript" src="styles/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>styles/bootstrap/js/bootstrap-prompts-alert.js"></script>
  </body>
</html>
