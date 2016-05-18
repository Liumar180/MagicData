<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

	<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>人员管理--详情页</title>
		<link href="<%=basePath%>styles/lawcase/css/bootstrap.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/my.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/ryControl.css"/>
		<script type="text/javascript" src="<%=basePath%>scripts/lawCase/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>scripts/lawCase/my.js" ></script>
		<script type="text/javascript" src="<%=basePath%>scripts/lawCase/common.js" ></script>
		<script type="text/javascript" src="<%=basePath%>scripts/lawCase/caseXq.js" ></script>
		<script type="text/javascript" src="<%=basePath%>scripts/lawCase/Popup.js" ></script>
		<script type="text/javascript" src="<%=basePath%>scripts/lawCase/inputSelect.js" ></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/ajControl.css"/>
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
						            <th colspan="8" style="text-align: left;">人员：<s:property value="#session.personinfo.pocnname"/></th>					            
						         </tr>
					      	  </thead>
						      <tbody>
						      	<tr>
						      		<td class="col-md-2"  rowspan="5" style="vertical-align: middle;"><img src="<s:property value="#session.personinfo.poimage"/>"/></td>
						            <td class="col-md-1">中文名称:</td>
						            <td class="col-md-2"><s:property value="#session.personinfo.pocnname"/></td>	
						            <td class="col-md-1">人员别名:</td>
						            <td class="col-md-2"><s:property value="#session.personinfo.poalias"/></td>
						            <td class="col-md-1">英文名称:</td>	
						            <td class="col-md-3"><s:property value="#session.personinfo.poenname"/></td>
						         </tr>
						         <tr>
						            <td class="col-md-1">中文拼音:</td>
						            <td class="col-md-2"><s:property value="#session.personinfo.ponamespell"/></td>	
						            <td class="col-md-1">性      别:</td>
						            <td class="col-md-2"><s:property value="#session.personinfo.posex"/></td>
						            <td class="col-md-1">所属方向:</td>	
						            <td class="col-md-3"><s:property value="#session.personinfo.podirectionof"/></td>
						         </tr>
						         <tr>
						            <td class="col-md-1">民      族:</td>
						            <td class="col-md-2"><s:property value="#session.personinfo.ponational"/></td>	
						            <td class="col-md-1">国      籍:</td>
						            <td class="col-md-2"><s:property value="#session.personinfo.pocountry"/></td>
						            <td class="col-md-1">重要等级:</td>
						            <td class="col-md-2"><s:property value="#session.personinfo.poimportantlevel"/></td>		
						         </tr>
						         <tr>
						            <td class="col-md-1">所在地:</td>
						            <td class="col-md-2"><s:property value="#session.personinfo.polocation"/></td>	
						            <td class="col-md-1">户籍地:</td>
						            <td class="col-md-2"><s:property value="#session.personinfo.pohukou"/></td>
						            <td class="col-md-1">责任人:</td>
						            <td class="col-md-2"><s:property value="#session.personinfo.podutyman"/></td>		
						         </tr>
						         <tr>
						            <td class="col-md-1">人员状态:</td>
						            <td class="col-md-2"><s:property value="#session.personinfo.popersonstatus"/></td>	
						            <td class="col-md-1">控制状态:</td>
						            <td class="col-md-2"><s:property value="#session.personinfo.pocontrolstatus"/></td>		
						            <td class="col-md-1"></td>
						            <td class="col-md-2"></td>	
						         </tr>
						        <tr>
						            <td class="col-md-2">人员简介:</td>
						            <td class="col-md-2" colspan="8">${personinfo.podescription}</td>									            
						        </tr>										       										         									       										</tbody>
				   		</table>
					</div>
					
					<div class="clear"></div>
					<!--虚拟身份列表-->
					<div class="xxsfBox">
						<h3 style="width: 110px">虚拟身份</h3>
						<div class="table-responsive col-md-12 tableDiv rytableDiv">
							<table class="table table-bordered">
							   <thead>
							      <tr>
							         <th>账号类型</th>
							         <th>账号</th>
							         <th>密码</th>
							         <th>控制状态</th>
							         <th>控制措施</th>
							         <th>备注</th>
							      </tr>
							   </thead>
							   <tbody>
							   <c:forEach items="${peoplevirtualList}" var="plist"  varStatus="status">
							      <tr>
							         <td class="col-md-1">${plist.pvaccounttype}</td>
							         <td class="col-md-2">${plist.pvusername}</td>
							         <td class="col-md-2">${plist.pvpassword}</td>
							         <td class="col-md-1">${plist.pvcontrolstatus}</td>
							         <td class="col-md-1">${plist.pvcontrolmeasures}</td>
							         <td class="col-md-4">${plist.pvremark}</td>
							      </tr>	
							      </c:forEach>	
							   </tbody>
							</table>
						</div>
					</div>
					<div class="clear"></div>
					<!--证件号码列表-->
					<div class="zjnumBox">
						<h3 style="width: 110px">证件号码</h3>
						<div class="table-responsive col-md-12 tableDiv rytableDiv">
							<table class="table table-bordered">
							   <thead>
							      <tr>
							         <th>证件类型</th>
							         <th>证件号码</th>
							         <th>备注信息</th>
							      </tr>
							   </thead>
							   <tbody>
							    <c:forEach items="${documentnumberList}" var="plist"  varStatus="status">
							      <tr>
							         <td class="col-md-2">${plist.dntype}</td>
							         <td class="col-md-4">${plist.dnnumber}</td>										         
							         <td class="col-md-4">${plist.dnremark}</td>
							      </tr>	
							       </c:forEach>										      
							   </tbody>
							</table>
						</div>
					</div>
					<div class="clear"></div>
					<!--电话号码列表-->
					<div class="phonenumBox">
						<h3 style="width: 110px">电话号码</h3>
						<div class="table-responsive col-md-12 tableDiv rytableDiv">
							<table class="table table-bordered">
							   <thead>
							      <tr>
							         <th>类型</th>
							         <th>号码</th>
							         <th>备注信息</th>
							      </tr>
							   </thead>
							   <tbody>
							    <c:forEach items="${phonenumberList}" var="plist"  varStatus="status">
							      <tr>
							         <td class="col-md-2">${plist.pntype}</td>
							         <td class="col-md-4">${plist.pnnumber}</td>										         
							         <td class="col-md-4">${plist.pnremark}</td>
							      </tr>	
							      </c:forEach>											 
							   </tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			
  </body>
</html>
