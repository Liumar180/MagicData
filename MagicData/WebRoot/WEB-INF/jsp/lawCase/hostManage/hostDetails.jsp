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
	<link rel="stylesheet" type="text/css" href="styles/lawcase/css/ajControl.css"/>
	<script type="text/javascript" src="styles/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="styles/bootstrap/js/bootstrap-prompts-alert.js"></script>							 
	<style type="text/css">
		/*iframe的背景  */
		html,body{background:none;}
	</style>
	</head>
	<body>
		<div class="userTab">
			<span class="spanSeton">基本信息</span>
			<!-- <span>对象关系</span> -->
		</div>
		<div class="userconBox">
			<div class="userCon clearfix">
				<div class="xxinnerCon">
					<!--人员基本详情工具栏-->
					<div class="jbxxtoolBar clearfix">
						<div class="xxselect_box">
							<span class="ajselect_txt">添加关联对象</span>
							<a class="selet_open"></a>
					        <div class="ajoption">
					            <span class="ajSpan-relation">案件</span>
					            <span class="zzSpan-relation">组织</span>
					            <span class="rySpan-relation">人员</span>
					            <span class="wjSpan-relation">文件</span>
					            <span class="zjSpan-relation">主机</span>						
					        </div>   
						</div>
						<div class="xxselect_box xxselect_box1">
							<span class="ajselect_txt">添加对象详情</span>
							<a class="selet_open"></a>
					        <div class="ajoption">
					            <span class="ymSpan">域名情况</span>
					            <span class="ldSpan">漏洞情况</span>									           
					        </div>   
						</div>
						<div class="zjxqBj"><span>编辑</span></div>
						<div class="zjxqDel"><span>删除</span></div>
						<!-- <div><span onclick="history.go(-1)">上一步</span></div> -->
					</div>	
					<!--人员基本详情工具栏--结束-->
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
					        </tr></tbody>
							</table>
					</div>
					<div class="clear"></div>								
					<!--域名情况列表-->
					<div class="ymBox">
						<h3 class="addymBtn ymSpan" style="width: 110px">域名情况</h3>
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
							         <td class="col-md-2">
							         	<a href="javascript:void(0)" class="ymbjA" onclick="updateDomain(${list.id})"></a>
							         	<a href="javascript:void(0)" class="ymdelA" onclick="deleteDomain(${list.id});" ></a>
							         </td>
							      </tr>												     
							   </tbody>
							</c:forEach>
							</table>
						</div>
					</div>
					<div class="clear"></div>
					<!--漏洞情况列表-->
					<div class="ldBox">
						<h3 class="addldBtn ldSpan" style="width: 110px">漏洞情况</h3>
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
							         <td class="col-md-2">
							         	<a href="javascript:void(0)" class="ldbjA" onclick="updateLoopholes(${list.id})"></a>
							         	<a href="javascript:void(0)" class="lddelA" onclick="deleteLoopholes(${list.id})" ></a>
							         </td>
							      </tr>											      
							   </tbody>
							</c:forEach>
							</table>
						</div>
					</div>
					<div class="clear"></div>
					<!--相关案件-->
					<div class="xgzzBox clearfix">
						<h3>相关案件</h3>
						<div class="personImg">
							<ul class="list-unstyled">
								<c:forEach items="${allRelation.caseList}" var="caseObj" >
									<li>
										<img src="images/lawCase/moren/case.jpg" onclick="relationDetails('case/caseRelationDetail.action?caseObject.id=${caseObj.id}','案件(${caseObj.caseName})基本信息')"/>
										<p><span>${caseObj.caseName}</span><a href="javascript:void(0)" onclick="delRelation('${caseObj.id}','case')" class="delA">删除</a></p>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
					<!--相关组织-->
					<div class="xgzzBox clearfix">
						<h3>相关组织</h3>
						<div class="zzImg">
							<ul class="list-unstyled">
								<c:forEach items="${allRelation.organList}" var="org" >
									<li>
										<c:if test="${org.orgImage==null||org.orgImage==''}">
											<img src="images/lawCase/moren/organaization.jpg" 
												         onclick="relationDetails('lawCaseOrg/orgRelationDetail.action?orgObj.id=${org.id}','组织(${org.orgCName})详细信息')"/>
											</c:if>
											<c:if test="${org.orgImage!=null&&org.orgImage!=''}">
											<img src="images/lawCase/uploadImg/organization/${org.orgImage}" 
												         onclick="relationDetails('lawCaseOrg/orgRelationDetail.action?orgObj.id=${org.id}','组织(${org.orgCName})详细信息')"/>
										</c:if>
										<p><span>${org.orgCName}</span><a href="javascript:void(0)" onclick="delRelation('${org.id}','organization')" class="delA">删除</a></p>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
					<!--相关人员-->
					<div class="xgpersonBox clearfix" >
						<h3>相关人员</h3>
						<div class="personImg">
							<ul class="list-unstyled">
								<c:forEach items="${allRelation.peopleList}" var="people" >
									<li>
										<c:if test="${people.poimage!=null&&people.poimage!=''}">
											<img src="${people.poimage}" onclick="relationDetails('peoplemanage/peopleMangeDetailR.action?id=${people.id}','人员(${people.pocnname})详细信息')"/>
										</c:if>
										<c:if test="${people.poimage==null||people.poimage==''}">
											<img src="images/lawCase/moren/people.jpg" onclick="relationDetails('peoplemanage/peopleMangeDetailR.action?id=${people.id}','人员(${people.pocnname})详细信息')"/>
										</c:if>
										<p><span>${people.pocnname}</span><a href="javascript:void(0)" onclick="delRelation('${people.id}','people')" class="delA">删除</a></p>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
					<!--相关文件-->
					<div class="xgzzBox clearfix">
						<h3>相关文件</h3>
						<div class="personImg">
							<ul class="list-unstyled">
								<c:forEach items="${allRelation.fileList}" var="file" >
									<li>
										<a href="javascript:void(0)" onclick="relationDetails('fileManage/fileMangeDetailR.action?id=<c:out value="${file.id}"></c:out>','${file.fileName}')">
											<img src="images/lawCase/moren/file.jpg"/>
										</a>
										<p><span>${file.fileName}</span><a href="javascript:void(0)" onclick="delRelation('${file.id}','file')" class="delA">删除</a></p>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
					<!--相关主机-->
					<div class="xgzzBox clearfix">
						<h3>相关主机</h3>
						<div class="personImg">
							<ul class="list-unstyled">
								<c:forEach items="${allRelation.hostList}" var="host" >
									<li>
										<img src="images/lawCase/moren/host.jpg" onclick="relationDetails('hostManage/searchHostDetails.action?hid='+${host.id}+'&&relationDetails=true','主机基本信息');"/>
										<p><span>${host.hostName}</span><a href="javascript:void(0)" onclick="delRelation('${host.id}','host')" class="delA">删除</a></p>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>								
				</div>												
			</div>
			<!-----工作关系----->
			<div class="userCon hidegzgxCon clearfix">
				
			</div>
			<!-----工作关系-结束----->
		</div>
		<script type="text/javascript">
		var hostId = $('#hostId').val();
		var hostName =$('#hostName').val();
		 function deleteDomain(Ids){
			 if(Ids!= null && Ids !=''){
				 confirm('是否确定删除？',function(){
					 $.ajax( {
							type : "POST",
							url : "hostManage/delDomain.action",
							data:{
								did:Ids,
								hostId:hostId,
					         },
					         async : false,
					         dataType:"json",
							success : function(data){
					        	 location.href="hostManage/searchHostDetails.action?hid="+data;
							}
						}); 
				 });
					}else{
						parent.hintManager.showHint("请选择删除对象！");
					}
		 }
		 
		 function updateDomain(id){
				if(id!= null && id !=''){
					var zjData = {
						title:"编辑域名情况",
						url:"hostManage/forUpdateDomain.action?did="+id+"&&hostName="+encodeURIComponent(encodeURIComponent(hostName))
					};
					parent.parentPop(zjData);
					}else{
						parent.hintManager.showHint("请选择一个编辑对象！");
					}
			 }
		 
		 function deleteLoopholes(Ids){
			 if(Ids!= null && Ids !=''){
				 confirm('是否确定删除？',function(){
					 $.ajax( {
							type : "POST",
							url : "hostManage/delLoopholes.action",
							data:{
								lid:Ids,
								hostId:hostId,
					         },
					         async : false,
					         dataType:"json",
							success : function(data){
					        	 location.href="hostManage/searchHostDetails.action?hid="+data;
							}
						});
				 });
					}else{
						parent.hintManager.showHint("请选择删除对象！");
					}
		 }
		 function updateLoopholes(id){
				if(id!= null && id !=''){
					var zjData = {
						title:"编辑漏洞情况",
						url:"hostManage/forUpdateLoopholes.action?lid="+id+"&&hostName="+encodeURIComponent(encodeURIComponent(hostName))
					};
					parent.parentPop(zjData);
					}else{
						parent.hintManager.showHint("请选择一个编辑对象！");
					}
			 }
				 
		
		
		/*添加域名情况弹出层*/
		$(".ymSpan").click(function(){
			var ymData = {
				title:"添加域名情况",
				url:"hostManage/forDomain.action?hostId="+hostId+"&&hostName="+encodeURIComponent(encodeURIComponent(hostName))
			};
			parent.parentPop(ymData);
		 })
		/*添加漏洞情况弹出层*/
		$(".ldSpan").click(function(){
			var ldData = {
				title:"添加漏洞情况",
				url:"hostManage/forLoopholes.action?hostId="+hostId+"&&hostName="+encodeURIComponent(encodeURIComponent(hostName))
			};
			parent.parentPop(ldData);
		 })
		 
		$(".zjxqBj").click(function(){
		if(hostId!= null && hostId.length !=''){
			var zjData = {
				title:"编辑主机对象",
				url:"hostManage/updateHostsByid.action?hid="+hostId
			};
			parent.parentPop(zjData);
			}else{
				parent.hintManager.showHint("请选择一个编辑对象！");
			}
	 })
	 
	 	 $(".zjxqDel").click(
			 function(){
				 var rootType = $('#rootType').val();
				 if(hostId!= null && hostId !=''){
					 confirm('删除将失去所有与其他对象的关联，确认删除？',function(){
						 $.ajax( {
								type : "POST",
								url : "hostManage/delHostsByid.action?ids="+hostId+"&&rootType="+rootType,
								success : function(){
						        	 location.href="hostManage/info.action";
								}
							});
					 });
						}else{
							parent.hintManager.showHint("请选择删除对象！");
						}
			 }
			 )
			 //删除单一关联关系
			 function delRelation(rid,rtype){
				 var rootType = $('#rootType').val();
				 if(hostId!= null && hostId !=''){
					 confirm('是否确定删除当前关联对象?',function(){
						 $.ajax( {
								type : "POST",
								url : "hostManage/delSingleRelation.action",
								data:{
									hid:hostId,
									rootType:rootType,
									relId:rid,
									relType:rtype,
						         },
						         async : false,
						         dataType:"json",
								success : function(data){
						        	 location.href="hostManage/searchHostDetails.action?hid="+data;
								}
							});
					 });
						}else{
							parent.hintManager.showHint("请选择删除对象！");
						}
			 }
			 
			 
			 /*对象关联-案件*/
		$(".ajSpan-relation").click(function(){
		  	var ajData = {
			  	title:"添加关联案件",
			  	url:"${relationAction.case_relationAction}",
			  	title1:""
		  	};
		  	parent.parentPop(ajData);
		 })
		  /*对象关联-组织*/
		$(".zzSpan-relation").click(function(){
		  	var ajData = {
			  	title:"添加关联组织",
			  	url:"${relationAction.organization_relationAction}"
		  	};
		  	parent.parentPop(ajData);
		 })
		  /*对象关联-人员*/
		$(".rySpan-relation").click(function(){
		  	var ajData = {
			  	title:"添加关联人员",
			  	url:"${relationAction.people_relationAction}"
		  	};
		  	parent.parentPop(ajData);
		 })
		  /*对象关联-文件*/
		$(".wjSpan-relation").click(function(){
		  	var ajData = {
			  	title:"添加关联文件",
			  	url:"${relationAction.file_relationAction}"
		  	};
		  	parent.parentPop(ajData);
		 })
		  /*对象关联-主机*/
		$(".zjSpan-relation").click(function(){
			var ajData = {
			  	title:"添加关联主机",
			  	url:"${relationAction.host_relationAction}"
		  	};
		  	parent.parentPop(ajData);
		 })
	
		function flashDetailPage(){
			 location.reload(true); 
		 }
		
	    function relationDetails(url,title){
	    	var zjData = {
					title:title,
					url:url
				};
				parent.parentPop(zjData);
	    }

	    function rsLoad(){
	    	var hostId = $('#hostId').val();
	    	location.href="hostManage/searchHostDetails.action?hid="+hostId;
	    }
	    
		</script>
	</body>
</html>