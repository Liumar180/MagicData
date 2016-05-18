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
			<span>工作记录</span>
			<span>工作配置</span>
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
						<!-- <div class="xxselect_box xxselect_box1">
							<span class="ajselect_txt">添加对象详情</span>
							<a class="selet_open"></a>
					        <div class="ajoption">
					            <span class="workjlSpan">工作记录</span>
					            <span class="workpzSpan">工作配置</span>								            						
					        </div>   
						</div> -->
						<div class="ajxqBj bjAj"><span>编辑</span></div>
						<div class="ajxqDel" onclick="deleteCase()"><span>删除</span></div>
					</div>	
					<!--案件详情工具栏--结束-->
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
						            <td class="col-md-3">${caseObject.dateStr}</td>
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
			<!--工作记录-->
			<div class="userCon hideworkcon clearfix" style="padding-bottom:0px;">
				<div class="workJl">
					<!--工作记录左侧-->
					<div class="workLeft pull-left">
						<div class="workleftTop ajworkleftTop clearfix">
							<span class="addjlSpan workjlSpan"><a href="javascript:void(0)" class="addBtn">添加</a></span>
							<span class="changejlSpan"><a href="javascript:void(0)">修改</a></span>
							<span class="deljlSpan"><a href="javascript:void(0)" >删除</a></span>
							<span class="exportjlSpan"><a href="javascript:void(0)" >导出</a></span>
						</div>
						<div class="workleftTab">
							<ul class="list-unstyled recordSelect">
								<c:forEach items="${recordPage.list}" var="record" varStatus="status">
									<c:choose>
									   <c:when test="${status.index == 0}">
											<li class="liCur" name="${record.id}">
									   </c:when>
									   <c:otherwise>
									      	<li class="" name="${record.id}">
									   </c:otherwise>
								  	</c:choose>
										<div class="liTop clearfix">
											<h3 class="pull-left">${record.title}</h3>
											<span class="pull-right">${record.dateStr}</span>
										</div>
										<p class="liContent">${record.conent}</p>
									</li>
								</c:forEach>
							</ul>
							
						</div>
						<div class="loadMore" id="recordMoreDiv">
							<span id="recordPageSpan1">${recordPage.pageNo}/${recordPage.totalPage}</span>页&nbsp;更多&nbsp;<span id="recordPageSpan2">共${recordPage.totalRecords}条</span>
						</div>
					</div>								
					<!--工作记录右侧-->
					<div class="workJlxx">
					<c:forEach items="${recordPage.list}" var="record" varStatus="status">
						<c:choose>
						   <c:when test="${status.index == 0}">
						   		<div class="workrightCon pull-left" id="recordDetail${record.id}">
						   </c:when>
						   <c:otherwise>
								<div class="workrightCon hideworkrightCon pull-left" id="recordDetail${record.id}">
						   </c:otherwise>
					  	</c:choose>
							<div class="workrightTop clearfix">
								<h1>${record.title}</h1>
								<span>${record.dateStr}</span>
							</div>
							<div class="workrightBottom">
								<p>${record.conent}</p>
							</div>
						</div>
					</c:forEach>
					</div>
				</div>
			</div>
			<!-----工作配置----->
			<div class="userCon hideworkcon clearfix">
				<div class="workPz">
					<!--工作配置左侧-->
					<div class="workLeft pull-left">
						<div class="workleftTop ajworkleftTop clearfix">
							<span class="addpzSpan workpzSpan"><a href="javascript:void(0)" class="addBtn">添加</a></span>
							<span class="changepzSpan"><a href="javascript:void(0)">修改</a></span>
							<span class="delpzSpan"><a href="javascript:void(0)">删除</a></span>
							<span class="exportpzSpan"><a href="javascript:void(0)" >导出</a></span>
						</div>
						<div class="workleftTab">
							<ul class="list-unstyled allocationSelect">
								<c:forEach items="${allocationPage.list}" var="allocation" varStatus="status">
									<c:choose>
									   <c:when test="${status.index == 0}">
											<li class="liCur" name="${allocation.id}">
									   </c:when>
									   <c:otherwise>
									      	<li class="" name="${allocation.id}">
									   </c:otherwise>
								  	</c:choose>
										<div class="liTop clearfix">
											<h3 class="pull-left">${allocation.typeName}：${allocation.mainValue}</h3>
											<span class="pull-right">${allocation.dateStr}</span>
										</div>
										<p class="liContent">${allocation.allocation}</p>
									</li>
								</c:forEach>
							</ul>							
						</div>
						<div class="loadMore" id="allocationMoreDiv">
							<span id="allocationPageSpan1">${allocationPage.pageNo}/${allocationPage.totalPage}</span>页&nbsp;更多&nbsp;<span id="allocationPageSpan2">共${allocationPage.totalRecords}条</span>
						</div>
					</div>								
					<!--工作配置右侧-->
					<div class="workPzxx">
					<c:forEach items="${allocationPage.list}" var="allocation" varStatus="status">
						<c:choose>
						   <c:when test="${status.index == 0}">
						   		<div class="workrightCon pull-left" id="allocationDetail${allocation.id}">
						   </c:when>
						   <c:otherwise>
								<div class="workrightCon hideworkrightCon pull-left" id="allocationDetail${allocation.id}">
						   </c:otherwise>
					  	</c:choose>
							<div class="workrightTop clearfix">
								<h1>${allocation.typeName}：${allocation.mainValue}</h1>
								<span class="pull-right">${allocation.dateStr}</span>
							</div>
							<div class="workrightBottom workpzrightBottom">
								<p class="pzP1">
									<span>对象类型：${allocation.typeName}</span>
									<span>具体对象：${allocation.mainValue}</span>
									<span>负责人：${allocation.principal}</span>
								</p>
								<h3 class="pzh3">方案配置情况：</h3>
								<p class="">${allocation.allocation}</p>
								<h3 class="pzh3">效果反馈：</h3>
								<p class="">${allocation.result}</p>
								<h3 class="pzh3">备注：</h3>
								<p class="">${allocation.memo}</p>
							</div>
						</div>
					</c:forEach>
					</div>
				</div>
			</div>
			<!--工作配置结束-->
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
		var allRelation = '${allRelationJson}';
		/*编辑案件弹出层*/
		$(".bjAj").click(function(){
		  	var ajData = {
			  	title:"编辑案件",
			  	url:"case/viewCaseUpdate.action?skipFlag=detail&caseObject.id=${caseObject.id}"
		  	};
		  	parent.parentPop(ajData);
		 })
		 
		 /*删除案件*/
		 function deleteCase(){
			confirm("删除将失去所有与其他对象的关联，确认删除？",function(){
				window.location.href="case/deleteCase.action?caseObject.id=${caseObject.id}";
			});
		 }
		/*添加工作记录弹出层*/
		$(".workjlSpan").click(function(){
			var jlData = {
				title:"添加工作记录",
				url:"case/viewWorkRecordAdd.action?record.caseId=${caseObject.id}"
			};
			parent.parentPop(jlData);
		 })
		 /*编辑工作记录弹出层*/
		$(".changejlSpan").click(function(){
			var id = $(".recordSelect .liCur").attr("name");
			if(id){
				var jlData = {
					title:"编辑工作记录",
					url:"case/viewRecordUpdate.action?record.id="+id
				};
				parent.parentPop(jlData);
			}else{
				 parent.hintManager.showHint("请选中一条工作记录！");
			}
		 })
		 
		/*删除工作记录*/
		$(".deljlSpan").click(function(){
			var id = $(".recordSelect .liCur").attr("name");
			if(id){
				confirm("是否确认删除此工作记录？",function(){
					$.ajax({
				         type:"post",
				         url:"ajaxCase/deleteWorkRecord.action",
				         data:{
				        	 "record.id":id
				         },
				         async : false,
				         dataType:"json",
				         success:function(data){
// 				        	 $(".recordSelect .liCur").remove();
// 				        	 $("#recordDetail"+id).remove();
							 refreshRecord(1);
				         },
				         error:function(){
				        	 parent.hintManager.showHint("删除工作记录异常，请联系管理员！");
				         }
				     });
				});
			}else{
				parent.hintManager.showHint("请选中一条工作记录！");
			}
		 })
		 /*导出工作记录到excel*/
		 $(".exportjlSpan").click(function(){
			 var pageParm = $("#recordPageSpan1").html();
				var arr = pageParm.split("/");
				var pageNo = Number(arr[0]);
				confirm('确定要导出Excel吗?',function(){
					parent.openExportDiv();
					$.ajax({
				         type:"POST",
				         url:"ajax_exportWorkRecord/exportRecordListPage.action",
				         data:{
				        	 "recordPage.pageNo":pageNo,
				        	 "recordPage.pageSize":"${recordPage.pageSize}",
				        	 "caseObject.id":"${caseObject.id}"
				         },
				         dataType:'json',
				         success : function(data){
				        	var currentTimeStr = data;
							var dlFileUrl = '<%=basePath%>ajax_exportWorkRecord/downloadWorkRecordExcel.action?currentTimeStr='+currentTimeStr+'&caseObject.id='+${caseObject.id};
							parent.closeExportDiv(dlFileUrl);
						 }
				     });
				});
		 });
		 /*添加工作配置弹出层*/
		$(".workpzSpan").click(function(){
			var pzData = {
				title:"添加工作配置",
				url:"case/viewWorkAllocationAdd.action?allocation.caseId=${caseObject.id}"
			};
			parent.parentPop(pzData);
		 })
		  /*编辑工作配置弹出层*/
		$(".changepzSpan").click(function(){
			var id = $(".allocationSelect .liCur").attr("name");
			if(id){
				var pzData = {
					title:"编辑工作配置",
					url:"case/viewAllocationUpdate.action?allocation.id="+id
				};
				parent.parentPop(pzData);
			}else{
				parent.hintManager.showHint("请选中一条工作配置！");
			}
		 })
		 /*删除工作配置*/
		$(".delpzSpan").click(function(){
			var id = $(".allocationSelect .liCur").attr("name");
			if(id){
				confirm("是否确认删除此工作配置？",function(){
					$.ajax({
				         type:"post",
				         url:"ajaxCase/deleteWorkAllocation.action",
				         data:{
				        	 "allocation.id":id
				         },
				         async : false,
				         dataType:"json",
				         success:function(data){
// 				        	 $(".allocationSelect .liCur").remove();
// 				        	 $("#allocationDetail"+id).remove();
				        	 refreshAllocation(1);
				         },
				         error:function(){
				        	 parent.hintManager.showHint("删除工作配置异常，请联系管理员！");
				         }
				     });
				});
			}else{
				parent.hintManager.showHint("请选中一条工作配置！");
			}
		 })
		 /*导出工作配置到excel*/
		 $(".exportpzSpan").click(function(){
			var pageParm = $("#allocationPageSpan1").html();
			var arr = pageParm.split("/");
			var pageNo = Number(arr[0]);
			confirm('确定要导出Excel吗?',function(){
				parent.openExportDiv();
				$.ajax({
			         type:"POST",
			         url:"<%=basePath%>ajax_exportAllocation/exportAllocationListPage.action",
			         dataType:'json',
			         data:{
			        	 "allocationPage.pageNo":pageNo,
			        	 "allocationPage.pageSize":"${allocationPage.pageSize}",
			        	 "caseObject.id":"${caseObject.id}"
			         },
			         success : function(data){
			        	var currentTimeStr = data;
						var dlFileUrl = '<%=basePath%>ajax_exportAllocation/downloadAllocationExcel.action?currentTimeStr='+currentTimeStr+'&caseObject.id='+${caseObject.id};
						parent.closeExportDiv(dlFileUrl);
					 }
			     });
			});
		 });
		 
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
			var rootType = "${rootType}";
			var rootId = "${caseObject.id}";
			confirm("是否确认删除关联对象？",function(){
				$.ajax({
			         type:"post",
			         url:"ajaxCase/deleteCaseRelationObj.action",
			         data:{
			        	 "rootType":"${rootType}",
			        	 "rootId":"${caseObject.id}",
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
		
		/* 刷新记录列表 */
		function refreshRecord(pageNo,flag){
			$.ajax({
		         type:"post",
		         url:"ajaxCase/findRecordListPage.action",
		         data:{
		        	 "recordPage.pageNo":pageNo,
		        	 "recordPage.pageSize":"${recordPage.pageSize}",
		        	 "caseObject.id":"${caseObject.id}"
		         },
		         async : false,
		         dataType:"json",
		         success:function(data){
		        	 var recordPage = data.recordPage;
		        	 var records = recordPage.totalRecords;
		        	 pageNo = recordPage.pageNo;
		        	 var totalPage = recordPage.totalPage;
		        	 $("#recordPageSpan1").html(pageNo+"/"+totalPage);
		        	 $("#recordPageSpan2").html("共"+records+"条");
		        	 //追加记录列表
		        	 var list = recordPage.list;
			       	 var len = list.length;
		        	 var html1 = "";
			       	 var html2 = "";
		    		 for(var i = 0;i < len; i++) {
		    			var record = list[i];
		    			html1 += '<li class="" name="'+record.id+'">';
		    			html1 += '<div class="liTop clearfix">';
		    			html1 += '<h3 class="pull-left">'+record.title+'</h3>';
		    			html1 += '<span class="pull-right">'+record.dateStr+'</span>';
		    			html1 += '</div>';
		    			html1 += '<p class="liContent">'+record.conent+'</p>';
		    			html1 += '</li>';
		    			
		    			html2 += '<div class="workrightCon hideworkrightCon pull-left" id="recordDetail'+record.id+'">';
		    			html2 += '<div class="workrightTop clearfix">';
		    			html2 += '<h1>'+record.title+'</h1>';
		    			html2 += '<span>'+record.dateStr+'</span>';
		    			html2 += '</div>';
		    			html2 += '<div class="workrightBottom">';
		    			html2 += '<p>'+record.conent+'</p>';
		    			html2 += '</div>';
		    			html2 += '</div>';
		    			
		    	     }
		    		 if("append" == flag){
			     		 $(".recordSelect").append(html1);
			     		 $(".workJlxx").append(html2);
		    		 }else{
		    			 $(".recordSelect").html(html1);
			     		 $(".workJlxx").html(html2);
		    		 }
		         },
		         error:function(){
		        	 parent.hintManager.showHint("查询工作记录异常，请联系管理员！");
		         }
		     });
		}
		
		/* 加载记录 */
		$("#recordMoreDiv").click(function(){
			var pageParm = $("#recordPageSpan1").html();
			var arr = pageParm.split("/");
			var pageNo = Number(arr[0]);
			var totalPage = Number(arr[1]);
			if(pageNo<totalPage){
				pageNo = pageNo + 1;
				refreshRecord(pageNo,"append");
			}else{
				 parent.hintManager.showHint("没有更多的工作记录！");
			}
		});
		
		/* 刷新配置列表 */
		function refreshAllocation(pageNo,flag){
			$.ajax({
		         type:"post",
		         url:"ajaxCase/findAllocationListPage.action",
		         data:{
		        	 "allocationPage.pageNo":pageNo,
		        	 "allocationPage.pageSize":"${allocationPage.pageSize}",
		        	 "caseObject.id":"${caseObject.id}"
		         },
		         async : false,
		         dataType:"json",
		         success:function(data){
		        	 var allocationPage = data.allocationPage;
		        	 var records = allocationPage.totalRecords;
		        	 pageNo = allocationPage.pageNo;
		        	 var totalPage = allocationPage.totalPage;
		        	 $("#allocationPageSpan1").html(pageNo+"/"+totalPage);
		        	 $("#allocationPageSpan2").html("共"+records+"条");
		        	 //追加配置列表
		        	 var list = allocationPage.list;
		        	 var len = list.length;
		        	 var html1 = "";
		        	 var html2 = "";
		     		 for(var i = 0;i < len; i++) {
		     			var allocation = list[i];
		     			html1 += '<li class="" name="'+allocation.id+'">';
		     			html1 += '<div class="liTop clearfix">';
		     			html1 += '<h3 class="pull-left">'+allocation.typeName+'：'+allocation.mainValue+'</h3>';
		     			html1 += '<span class="pull-right">'+allocation.dateStr+'</span>';
		     			html1 += '</div>';
		     			html1 += '<p class="liContent">'+allocation.allocation+'</p>';
		     			html1 += '</li>';
		     			
		     			html2 += '<div class="workrightCon hideworkrightCon pull-left" id="allocationDetail'+allocation.id+'">';
		     			html2 += '<div class="workrightTop clearfix">';
		     			html2 += '<h1>'+allocation.typeName+'：'+allocation.mainValue+'</h1>';
		     			html2 += '<span class="pull-right">'+allocation.dateStr+'</span>';
		     			html2 += '</div>';
		     			html2 += '<div class="workrightBottom workpzrightBottom">';
		     			html2 += '<p class="pzP1">';
		     			html2 += '<span>对象类型：'+allocation.typeName+'</span>';
		     			html2 += '<span>具体对象：'+allocation.mainValue+'</span>';
		     			html2 += '<span>负责人：'+allocation.principal+'</span>';
		     			html2 += '</p>';
		     			html2 += '<h3 class="pzh3">方案配置情况：</h3>';
		     			html2 += '<p class="">'+allocation.allocation+'</p>';
		     			html2 += '<h3 class="pzh3">效果反馈：</h3>';
		     			html2 += '<p class="">'+allocation.result+'</p>';
		     			html2 += '<h3 class="pzh3">备注：</h3>';
		     			html2 += '<p class="">'+allocation.memo+'</p>';
		     			html2 += '</div>';
		     			html2 += '</div>';
		     	     }
		     		 if("append" == flag){
			     		 $(".allocationSelect").append(html1);
			     		 $(".workPzxx").append(html2);
		    		 }else{
		    			 $(".allocationSelect").html(html1);
			     		 $(".workPzxx").html(html2);
		    		 }
		         },
		         error:function(){
		        	 parent.hintManager.showHint("查询工作配置异常，请联系管理员！");
		         }
		     });
		}
		
		/* 加载配置 */
		$("#allocationMoreDiv").click(function(){
			var pageParm = $("#allocationPageSpan1").html();
			var arr = pageParm.split("/");
			var pageNo = Number(arr[0]);
			var totalPage = Number(arr[1]);
			if(pageNo<totalPage){
				pageNo = pageNo + 1;
				refreshAllocation(pageNo,"append");
			}else{
				 parent.hintManager.showHint("没有更多的工作配置！");
			}
		});
		
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
