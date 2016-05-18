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
			html,body{background-position:center,center;}
			</style>
		<script type="text/javascript">
		function eidtperson(id){
			var url="fileManage/viewFileEdit.action?id="+id;
			var ryData = {
					title:"编辑文件",
					url:url
				};
				parent.parentPop(ryData);
		}
		function delperson(id){
				var url="fileManage/viewFileDel.action?ids="+id+"&rootType=file";
				confirm('确定要删除吗?',function(){
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
		<div class="userconBox">
			<div class="userCon clearfix">
				<div class="xxinnerCon">
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
				</div>												
			</div>						
		</div>
		<script type="text/javascript">
		function openwin(url) {
			var filenames = encodeURIComponent(encodeURIComponent("${fileinfo.fileName}"));
			var ids = ${fileinfo.id};
			url ="<%=basePath%>fileManage/downloadFile.action?fileNameD="+filenames+"&id="+ids;
		 	window.open (url);
		}
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
		</script>
  </body>
</html>
