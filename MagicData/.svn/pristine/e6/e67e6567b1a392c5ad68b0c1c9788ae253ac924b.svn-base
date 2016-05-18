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
	<title>主机详情</title>
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
		<div class="userconBox">
			<div class="userCon clearfix">
				<div class="xxinnerCon">
					<div class="table-responsive col-md-12 tableDiv">
					    <input type="hidden" id="rootType" name="rootType" value="${rootType}"/>
				      	<input type="hidden" id="hostId" name="hostId" value="${hostDetails.id}"/>
				      	<input type="hidden" id="hostName" name="hostName" value="${hostDetails.hostName}"/>
				   		<table class="table table-bordered">
				   		<thead>
				   		<tr>
				   		      <th colspan="8" style="text-align: left;">主机:&nbsp${hostDetails.hostName}</th>
				   		</tr>
				   		</thead>
				   		<tbody>
					      	<tr>									      		
					            <td class="col-md-1">主机名称:</td>
					            <td class="col-md-3">${hostDetails.hostName}</td>	
					            <td class="col-md-1">所在地:</td>
					            <td class="col-md-3">${hostDetails.location}</td>
					            <td class="col-md-1">操作系统:</td>	
					            <td class="col-md-3">${hostDetails.operateSystem}</td>
					         </tr>
					         <tr>
					            <td class="col-md-1">控制状态:</td>
					            <td class="col-md-3">${hostDetails.controlState}</td>	
					            <td class="col-md-1">Mac地址:</td>
					            <td class="col-md-3">${hostDetails.macAddress}</td>
					            <td class="col-md-1">主机类型:</td>	
					            <td class="col-md-3">${hostDetails.hostType}</td>
					         </tr>
					         <tr>
					            <td class="col-md-1">提供商:</td>
					            <td class="col-md-2">${hostDetails.provider}</td>	
					            <td class="col-md-1">责任人:</td>
					            <td class="col-md-2">${hostDetails.responsiblePerson}</td>
					            <td class="col-md-1">主机状态:</td>
					            <td class="col-md-2">${hostDetails.hostState}</td>		
					         </tr>
					         <tr>
					            <td class="col-md-1">重要程度:</td>
					            <td class="col-md-3">${hostDetails.importantLevel}</td>	
					            <td class="col-md-1">所属方向:</td>
					            <td class="col-md-3">${hostDetails.directions}</td>
					            <td class="col-md-1">IP  地  址:</td>
					            <td class="col-md-3">${hostDetails.hostIp}</td>		
					         </tr>
					        <!-- <tr>
					            <td class="col-md-1">关键词:</td>
					            <td class="col-md-2" colspan="9">涉疆  涉藏   敌对</td>									            
					        </tr> -->
					        <tr>
					            <td class="col-md-1">安装服务:</td>
					            <td class="col-md-2" colspan="9">${hostDetails.installationService}</td>									            
					        </tr> 
					      	<tr>
					            <td class="col-md-1">描      述:</td>
					            <td class="col-md-2" colspan="9">${hostDetails.descriptionContents}</td>									            
					        </tr>
					        </tbody>
							</table>
					</div>
					<div class="clear"></div>
										<!--域名情况列表-->
					<div class="ymBox">
						<h3 class="addymBtn ymSpan">域名情况</h3>
						<div class="table-responsive col-md-12 tableDiv zjtableDiv">
							<table class="table table-bordered">
							   <thead>
							       <tr>
							         <th>域名</th>
							         <th>注册时间</th>
							         <th>过期时间</th>
							         <th>域名解析机构</th>
							         <th>域名解析服务器</th>
							         <th>域名服务商</th>
							         <th>数据库类型</th>
							         <th>服务器类型</th>
							         <th>程序类型</th>
							         <th>操作</th>		
							      </tr>
							   </thead>
						<c:forEach items="${domainList}" var="list">
							   <tbody>
							      <tr>
							         <td class="col-md-1">${list.domain }</td>
							         <td class="col-md-1">${list.regTime}</td>										         
							         <td class="col-md-1">${list.expiredTime}</td>
							         <td class="col-md-2">${list.domainResolution}</td>
							         <td class="col-md-1">${list.domainResolutionServices}</td>										         
							         <td class="col-md-1">${list.domainServiceProvider}</td>
							         <td class="col-md-1">${list.databaseType}</td>
							         <td class="col-md-1">${list.serviceType}</td>										         
							         <td class="col-md-1">${list.developeLanguage}</td>
							      </tr>												     
							   </tbody>
							</c:forEach>
							</table>
						</div>
					</div>
					<div class="clear"></div>
					<!--漏洞情况列表-->
					<div class="ldBox">
						<h3 class="addldBtn ldSpan">漏洞情况</h3>
						<div class="table-responsive col-md-12 tableDiv zjtableDiv">
							<table class="table table-bordered">
							   <thead>
							      <tr>
							         <th>漏洞信息</th>
							         <th>利用方式</th>
							         <th>利用工具</th>
							         <th>权限类型</th>
							         <th>后门</th>
							         <th>备注</th>
							         <th>操作</th>		
							      </tr>
							   </thead>
						<c:forEach items="${loopholesList}" var="list">
							   <tbody>
							      <tr>
							         <td class="col-md-2">${list.vulnerabilityDescription}</td>
							         <td class="col-md-2">${list.methods}</td>										         
							         <td class="col-md-2">${list.useTools}</td>
							         <td class="col-md-1">${list.authorityType}</td>
							         <td class="col-md-1">${list.hiddenDoor}</td>										         
							         <td class="col-md-2">${list.remarks}</td>										         
							      </tr>											      
							   </tbody>
							</c:forEach>
							</table>
						</div>
					</div>								
				</div>												
			</div>
		</div>
	</body>
</html>