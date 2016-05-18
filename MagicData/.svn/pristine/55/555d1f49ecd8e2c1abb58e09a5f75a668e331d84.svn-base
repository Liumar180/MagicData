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
    <base href="<%=basePath%>">
    
    <title>My JSP 'fileDetail.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
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
		<script type="text/javascript" src="styles/bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="styles/bootstrap/js/bootstrap-prompts-alert.js"></script> 
        
		<style type="text/css">
		/*iframe的背景  */
		html,body{background:none;}
		</style>
		<script type="text/javascript">
		function eidtperson(id){
			var url="fileManage/viewFileEdit.action?id="+id+"&from=detail";
			var ryData = {
					title:"编辑文件",
					url:url
				};
				parent.parentPop(ryData);
		}
		function delperson(id){
				var url="fileManage/viewFileDel.action?ids="+id+"&rootType=file";
				confirm('删除将失去所有与其他对象的关联，确认删除？',function(){
					$.ajax( {
						type : "POST",
						url : url,
						success : function(){
							location.href ="fileManage/fileMangeIndex.action";
						}
					});
				});
		}
		function relationDetail(url,name){
			var ryData = {
					title:name+"-详细信息",
					url:url
				};
			parent.parentPop(ryData);
	}
		var fileId = '<s:property value="#session.fileinfo.id"/>';
		var fileName ='<s:property value="#session.fileinfo.fileName"/>';
		//删除单一关联关系
		 function delRelation(rid,rtype){
			 var rootType = $('#rootType').val();
			 if(fileId!= null && fileId !=''){
				confirm('确定删除当前关联对象?',function(){
				   $.ajax( {
						type : "POST",
						url : "<%=basePath%>fileManage/delRelationship.action",
						data:{
							id:fileId,
							rootType:rootType,
							relId:rid,
							relType:rtype,
				         },
				         async : false,
				         dataType:"json",
						success : function(data){
				        	 location.href="<%=basePath%>fileManage/fileMangeDetail.action?id="+fileId;
						}
					});
				});
					}else{
						parent.hintManager.showHint("请选择删除对象");
					}
		 }
		</script>
  </head>
  
  <body>
    <div class="userTab">
			<span class="spanSeton">基本信息</span>
			<!-- <span>对象关系</span> -->
		</div>
		<div class="userconBox">
			<div class="userCon clearfix">
				<div class="xxinnerCon">
					<!--文件管理-基本信息--详情工具栏-->
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
						<div class="ryxqBj" onclick="eidtperson(<s:property value="#session.fileinfo.id"/>)"><span>编辑</span></div>
						<div class="ryxqDel" onclick="delperson(<s:property value="#session.fileinfo.id"/>)"><span>删除</span></div>
					</div>	
					<!--文件管理-基本信息--工具栏--结束-->
					<div class="table-responsive col-md-12 tableDiv">
					
				   		<table class="table table-bordered">
						      <thead>
						         <tr> 
						            <th colspan="8">文件：<s:property value="#session.fileinfo.fileName"/></th>					            
						         </tr>
					      	  </thead>
						      <tbody>
						      	<tr>	
						      		<input type="hidden" id="rootType" name="rootType" value="${rootType}"/>								      		
						            <td class="col-md-1">文件MD5:</td>
						            <td class="col-md-2"><s:property value="#session.fileinfo.fileMD5"/> </td>
						            <td class="col-md-1">所属方向:</td>
						            <td class="col-md-2"><s:property value="#session.fileinfo.direction"/></td>
						            <td class="col-md-1">责任人:</td>	
						            <td class="col-md-3"><s:property value="#session.fileinfo.responsiblePerson"/></td>
						         </tr>									         
						         <tr>
						            <td class="col-md-1">创建时间:</td>
						            <td class="col-md-2">${fileinfo.createTimeString}</td>	
						            <td class="col-md-1">文件名称:</td>
						            <td class="col-md-2"  colspan="4"><s:property value="#session.fileinfo.fileName"/>	 <a href="javascript:void(0)" onclick="openwin('${fileinfo.id}')">下载</a></td>									            
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
									
										<li>
											<a href="javascript:void(0)" onclick="relationDetail('case/caseRelationDetail.action?caseObject.id=${caseObj.id}','案件(${caseObj.caseName})')"/>
												<img src="<%=basePath%>images/lawCase/moren/case.jpg"/>
											</a>
											<p><span>${caseObj.caseName}</span><a href="javascript:void(0)" class="delA">删除</a></p>
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
										<p><span>${org.orgCName}</span><a href="javascript:void(0)" class="delA">删除</a></p>
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
					<div class="xgpersonBox clearfix">
						<h3>相关文件</h3>
						<div class="personImg">
							<ul class="list-unstyled">
								<c:forEach items="${allRelation.fileList}" var="file" >
									<li>
										<a href="javascript:void(0)" onclick="relationDetail('fileManage/fileMangeDetailR.action?id=<c:out value="${file.id}"></c:out>','${file.fileName}')">
											<img src="<%=basePath%>images/lawCase/moren/file.jpg"/>
										</a>
										<p><span>${file.fileName}</span><a href="javascript:void(0)" onclick="delRelation('${file.id}','file')"  class="delA">删除</a></p>
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
									
										<li>
											<a href="javascript:void(0)" onclick="relationDetail('hostManage/searchHostDetails.action?hid='+${host.id}+'&&relationDetails=true','${host.hostName}')">
												<img src="<%=basePath%>images/lawCase/moren/host.jpg"/>
											</a>
											<p><span>${host.hostName}</span><a href="javascript:void(0)" onclick="delRelation('${host.id}','host')"  class="delA">删除</a></p>
										</li>
									
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>												
			</div>						
			<!-----对象关系----->
			<div class="userCon hidegzgxCon clearfix">
				
			</div>
			<!--对象关系结束-->
		</div>
		<script type="text/javascript">
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
		 function openwin(url) {
			var filenames = encodeURIComponent(encodeURIComponent("${fileinfo.fileName}"));
			var ids = ${fileinfo.id};
			url ="<%=basePath%>fileManage/downloadFile.action?fileNameD="+filenames+"&id="+ids;
			window.open (url);
			}
		</script>
  </body>
</html>
