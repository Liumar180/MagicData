<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
<head>
     <base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
   	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>案件详情</title>
	<link href="styles/lawcase/css/bootstrap.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="styles/lawcase/css/common.css"/>
	<link rel="stylesheet" type="text/css" href="styles/lawcase/css/my.css"/>
	<link rel="stylesheet" type="text/css" href="styles/lawcase/css/zjControl.css"/>
	<script type="text/javascript" src="scripts/lawCase/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="scripts/lawCase/my.js" ></script>
	<script type="text/javascript" src="scripts/lawCase/common.js" ></script>
	<script type="text/javascript" src="scripts/lawCase/caseXq.js" ></script>
	<script type="text/javascript" src="scripts/lawCase/Popup.js" ></script>
	<script type="text/javascript" src="scripts/lawCase/inputSelect.js" ></script>
	<style type="text/css">
	html,body{background-position:center,center;}
	</style>
	</head>
	<body>
		<div class="userCon clearfix">
			<div class="xxinnerCon">
				<div class="table-responsive col-md-12 tableDiv">
			   		<table class="table table-bordered">
					      <thead>
					         <tr> 
					            <th colspan="8">案件：${caseObject.caseName}</th>					            
					         </tr>
				      	  </thead>
					      <tbody>
					      	<tr>
					      		<td class="col-md-2"  rowspan="4"><img src="images/lawCase/moren/case.jpg"/></td>
					            <td class="col-md-1">案件名称:</td>
					            <td class="col-md-2">${caseObject.caseName}</td>	
					            <td class="col-md-1">案件组长:</td>
					            <td class="col-md-2">${caseObject.caseLeader}</td>
					            <td class="col-md-1">案件级别:</td>	
					            <td class="col-md-3">${caseObject.caseLevelName}</td>
					         </tr>
					         <tr>
					            <td class="col-md-1">对象状态:</td>
					            <td class="col-md-2">${caseObject.caseStatusName}</td>	
					            <td class="col-md-1">所属方向:</td>
					            <td class="col-md-2">${caseObject.directionName}</td>
					            <td class="col-md-1">成立时间:</td>	
					            <td class="col-md-3">${caseObject.createTime}</td>
					         </tr>
					         <tr>
					            <td class="col-md-1">督办人员:</td>
					            <td class="col-md-2">${caseObject.caseSupervisor}</td>	
					            <td class="col-md-1">案件成员:</td>
					            <td class="col-md-1" colspan="3">${caseObject.caseUserNames}</td>									            
					         </tr>
<!-- 						        <tr> -->
<!-- 						            <td class="col-md-1">关键词:</td> -->
<!-- 						            <td class="col-md-2" colspan="5">涉疆  涉藏   敌对</td>									             -->
<!-- 						        </tr> -->
					        <tr>
					            <td class="col-md-2">案件目标:</td>
					            <td class="col-md-2" colspan="8">${caseObject.caseAim}</td>									            
					        </tr>
					       	<tr>
					            <td class="col-md-2">案件备注:</td>
					            <td class="col-md-2" colspan="8">${caseObject.memo}</td>									            
					        </tr>									         									        									     									         								         									        									        
					     </tbody>
			   		</table>
				</div>
				<div class="clear"></div>
			</div>												
		</div>
	</body>
</html>