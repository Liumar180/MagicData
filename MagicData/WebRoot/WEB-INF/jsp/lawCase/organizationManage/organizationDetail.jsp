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
    <title>案件详情</title>
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
			html,body{background:none;}
 </style>
  <body>
    <div class="userTab">
			<span class="spanSeton">基本信息</span>
			<!-- <span>对象关系</span> -->
		</div>
		<div class="userconBox">
			<div class="userCon clearfix">
				<div class="xxinnerCon">
					<!--案件详情工具栏-->
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
						<div class="ajxqBj bjAj"><span>编辑</span></div>
						<div class="ajxqDel" onclick="deleteOrg()"><span>删除</span></div>
					</div>	
					<!--案件详情工具栏--结束-->
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
						      		<img width="116px" height="138px" src="<%=basePath%>images/lawCase/uploadImg/organization/${orgObj.orgImage}"/>
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
					<!--相关案件-->
					<div class="xgpersonBox clearfix">
						<h3>相关案件</h3>
						<div class="personImg">
							<ul class="list-unstyled">
								<c:forEach items="${allRelation.caseList}" var="caseObj" >
									<li class="caseList${caseObj.id}">
										<img src="images/lawCase/moren/case.jpg" onclick="relationDetails('case/caseRelationDetail.action?caseObject.id=${caseObj.id}','案件(${caseObj.caseName})详细信息')"/>
										<p><span>${caseObj.caseName}</span><a href="javascript:void(0)" class="delA" onclick="deleteRelation('${typeMap.caseType}','${caseObj.id}','caseList${caseObj.id}');">删除</a></p>
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
									<li class="organList${org.id}">
										<c:if test="${org.orgImage==null||org.orgImage==''}">
										<img src="images/lawCase/moren/organaization.jpg" 
											         onclick="relationDetails('lawCaseOrg/orgRelationDetail.action?orgObj.id=${org.id}','组织(${org.orgCName})详细信息')"/>
										</c:if>
										<c:if test="${org.orgImage!=null&&org.orgImage!=''}">
										<img src="images/lawCase/uploadImg/organization/${org.orgImage}" 
											         onclick="relationDetails('lawCaseOrg/orgRelationDetail.action?orgObj.id=${org.id}','组织(${org.orgCName})详细信息')"/>
										</c:if>
										<p><span>${org.orgCName}</span><a href="javascript:void(0)" class="delA" onclick="deleteRelation('${typeMap.organizationType}','${org.id}','organList${org.id}');">删除</a></p>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
					<!--相关人员-->
					<div class="xgpersonBox clearfix">
						<h3>相关人员</h3>
						<div class="personImg">
							<ul class="list-unstyled">
								<c:forEach items="${allRelation.peopleList}" var="people" >
									<li class="peopleList${people.id}">
										<c:if test="${people.poimage!=null&&people.poimage!=''}">
											<img src="${people.poimage}" onclick="relationDetails('peoplemanage/peopleMangeDetailR.action?id=${people.id}','人员(${people.pocnname})详细信息')"/>
										</c:if>
										<c:if test="${people.poimage==null||people.poimage==''}">
											<img src="images/lawCase/moren/people.jpg" onclick="relationDetails('peoplemanage/peopleMangeDetailR.action?id=${people.id}','人员(${people.pocnname})详细信息')"/>
										</c:if>
										<p><span>${people.pocnname}</span><a href="javascript:void(0)" class="delA" onclick="deleteRelation('${typeMap.peopleType}','${people.id}','peopleList${people.id}');">删除</a></p>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
					<!--相关文件-->
					<div class="xgpersonBox clearfix">
						<h3>相关文件</h3>
						<div class="personImg">
							<ul class="list-unstyled">
								<c:forEach items="${allRelation.fileList}" var="file" >
									<li class="fileList${file.id}">
										<a href="javascript:void(0)" onclick="relationDetails('fileManage/fileMangeDetailR.action?id=<c:out value="${file.id}"></c:out>','${file.fileName}')">
											<img src="images/lawCase/moren/file.jpg"/>
										</a>
										<p><span>${file.fileName}</span><a href="javascript:void(0)" class="delA" onclick="deleteRelation('${typeMap.fileType}','${file.id}','fileList${file.id}');">删除</a></p>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
					<!--相关主机-->
					<div class="xgpersonBox clearfix">
						<h3>相关主机</h3>
						<div class="personImg">
							<ul class="list-unstyled">
								<c:forEach items="${allRelation.hostList}" var="host" >
									<li class="hostList${host.id}">
										<img src="images/lawCase/moren/host.jpg" onclick="relationDetails('hostManage/searchHostDetails.action?hid=${host.id}&relationDetails=true','主机(${host.hostName})详细信息')"/>
										<p><span>${host.hostName}</span><a href="javascript:void(0)" class="delA" onclick="deleteRelation('${typeMap.hostType}','${host.id}','hostList${host.id}');">删除</a></p>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
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
	<script type="text/javascript">
		/*编辑案件弹出层*/
		$(".bjAj").click(function(){
		  	var ajData = {
			  	title:"编辑组织",
			  	url:"lawCaseOrg/viewOrgUpdate.action?skipFlag=detail&orgObj.id=${orgObj.id}"
		  	};
		  	parent.parentPop(ajData);
		 })
		 
		 /*删除组织*/
		 function deleteOrg(){
			confirm("删除将失去所有与其他对象的关联，确认删除？？",function(){
				window.location.href="lawCaseOrg/deleteOrg.action?id=${orgObject.id}&&searchType=orgCard";	
			});
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
			  	url:"${relationAction.organization_relationAction}",
			  	title1:""
		  	};
		  	parent.parentPop(ajData);
		 })
		  /*对象关联-人员*/
		$(".rySpan-relation").click(function(){
		  	var ajData = {
			  	title:"添加关联人员",
			  	url:"${relationAction.people_relationAction}",
			  	title1:""
		  	};
		  	parent.parentPop(ajData);
		 })
		  /*对象关联-文件*/
		$(".wjSpan-relation").click(function(){
		  	var ajData = {
			  	title:"添加关联文件",
			  	url:"${relationAction.file_relationAction}",
			  	title1:""
		  	};
		  	parent.parentPop(ajData);
		 })
		  /*对象关联-主机*/
		$(".zjSpan-relation").click(function(){
		  	var ajData = {
			  	title:"添加关联主机",
			  	url:"${relationAction.host_relationAction}",
			  	title1:""
		  	};
		  	parent.parentPop(ajData);
		 })
		 
		 function deleteRelation(type,id,cla){
			confirm("是否确认删除关联对象？",function(){
				$.ajax({
			         type:"post",
			         url:"ajax_lawCaseOrg/deleteOrgRelation.action",
			         data:{
			        	 "id":'${orgObj.id}',
			        	 "relationType":type,
			        	 "relationId":id
			         },
			         async : false,
			         dataType:"json",
			         success:function(data){
			        	 $("."+cla).remove();
			         },
			         error:function(){
			        	 parent.hintManager.showHint("删除关联对象异常，请联系管理员！");
			         }
			     });
			});
		 }

		
		/* 关联对象详情 */
		function relationDetails(url,title){
	    	var zjData = {
				title:title,
				url:url
			};
			parent.parentPop(zjData);
	    }
		 
		 //页面刷新
		 function flashDetailPage(){
			 location.reload(true); 
		 }
	</script>
  </body>
</html>
