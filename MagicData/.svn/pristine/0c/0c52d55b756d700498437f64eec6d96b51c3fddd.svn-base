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
		<script type="text/javascript" src="<%=basePath%>styles/bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="<%=basePath%>styles/bootstrap/js/bootstrap-prompts-alert.js"></script> 
		<style type="text/css">
		/*iframe的背景  */
		html,body{background:none;}
		</style>
		<script type="text/javascript">
		function eidtperson(id){
			var url="peoplemanage/viewPeopleEdit.action?id="+id+"&from=detail";
			var ryData = {
					title:"编辑人员",
					url:url
				};
				parent.parentPop(ryData);
		}
		function delperson(id){
				var url="peoplemanage/viewPeopleDel.action?ids="+id+"&rootType=people";
				confirm('删除将失去所有与其他对象的关联，确认删除？',function(){
					$.ajax( {
						type : "POST",
						url : url,
						success : function(){
							location.href ="peoplemanage/peopleMangeIndex.action";
						}
					});
				});
		}
		function openPV(id,poid){
			if(id!=''){
				var url="peoplemanage/viewPeoplePV.action?id="+id;
				var ryData = {
						title:"虚拟身份",
						url:url
					};
					parent.parentPop(ryData);
			}else{
				var url="peoplemanage/viewPeoplePV.action?poid="+poid;
				var ryData = {
						title:"虚拟身份",
						url:url
					};
					parent.parentPop(ryData);
			}
		}
		function delPV(id,poid){
			confirm('确定要删除吗?',function(){
				var url="peoplemanage/viewPVDel.action?id="+id;
				$.ajax( {
					type : "POST",
					url : url,
					success : function(){
						location.href ="<%=basePath%>peoplemanage/peopleMangeDetail.action?id="+poid;
					}
				});
              });
	}
		function openDN(id,poid){
			if(id!=''){
				var url="peoplemanage/viewPeopleDN.action?id="+id;
				var ryData = {
						title:"证件号码",
						url:url
					};
					parent.parentPop(ryData);
			}else{
				var url="peoplemanage/viewPeopleDN.action?poid="+poid;
				var ryData = {
						title:"证件号码",
						url:url
					};
					parent.parentPop(ryData);
			}
		}
		function delDN(id,poid){
			confirm('确定要删除吗?',function(){
				var url="peoplemanage/viewDNDel.action?id="+id;
				$.ajax( {
					type : "POST",
					url : url,
					success : function(){
						location.href ="<%=basePath%>peoplemanage/peopleMangeDetail.action?id="+poid;
					}
				});
              });
			
	}
		function openPN(id,poid){
			if(id!=''){
				var url="peoplemanage/viewPeoplePN.action?id="+id;
				var ryData = {
						title:"电话号码",
						url:url
					};
					parent.parentPop(ryData);
			}else{
				var url="peoplemanage/viewPeoplePN.action?poid="+poid;
				var ryData = {
						title:"电话号码",
						url:url
					};
					parent.parentPop(ryData);
			}
		}
		function delPN(id,poid){
			confirm('确定要删除吗?',function(){
				var url="peoplemanage/viewPNDel.action?id="+id;
				$.ajax( {
					type : "POST",
					url : url,
					success : function(){
						location.href ="<%=basePath%>peoplemanage/peopleMangeDetail.action?id="+poid;
					}
				});
              });
		}
		
		function addPR(poid){
			var ryData = {
					title:"添加关联对象",
					url:"<%=basePath%>peoplemanage/viewPeopleAddR.action?id="+poid
				};
				parent.parentPop(ryData);
		}
		function relationDetail(url,name){
			var ryData = {
					title:name+"-详细信息",
					url:url
				};
			parent.parentPop(ryData);
	}
		var peopleId = '<s:property value="#session.personinfo.id"/>';
		var peopleName ='<s:property value="#session.personinfo.pocnname"/>';
		//删除单一关联关系
		 
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
					            <span class="" onclick="openPV('','<s:property value="#session.personinfo.id"/>')">虚拟身份</span>
					            <span class=""  onclick="openDN('','<s:property value="#session.personinfo.id"/>')">证件号码</span>	
					             <span class="" onclick="openPN('','<s:property value="#session.personinfo.id"/>')">电话号码</span>	
					        </div>   
						</div>
						<div class="ryxqBj" onclick="eidtperson(<s:property value="#session.personinfo.id"/>)"><span>编辑</span></div>
						<div class="ryxqDel" onclick="delperson(<s:property value="#session.personinfo.id"/>)"><span>删除</span></div>
					</div>	
					<!--人员基本详情工具栏--结束-->
					<div class="table-responsive col-md-12 tableDiv">
					<input type="hidden" id="rootType" name="rootType" value="${rootType}"/>
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
						<h3 class="addxnsfBtn pvclass" onclick="openPV('','<s:property value="#session.personinfo.id"/>')"  style="width: 110px">虚拟身份</h3>
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
							         <th>操作</th>
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
							         <td class="col-md-1">
							         	<a href="javascript:openPV('${plist.pvid}','<s:property value="#session.personinfo.id"/>')" class=""></a>
							         	<a href="javascript:delPV('${plist.pvid}','<s:property value="#session.personinfo.id"/>')" class="xnsfdelA"></a>
							         </td>
							      </tr>	
							      </c:forEach>	
							   </tbody>
							</table>
						</div>
					</div>
					<div class="clear"></div>
					<!--证件号码列表-->
					<div class="zjnumBox">
						<h3 class="addzjnumBtn"  onclick="openDN('','<s:property value="#session.personinfo.id"/>')"  style="width: 110px">证件号码</h3>
						<div class="table-responsive col-md-12 tableDiv rytableDiv">
							<table class="table table-bordered">
							   <thead>
							      <tr>
							         <th>证件类型</th>
							         <th>证件号码</th>
							         <th>备注信息</th>
							         <th>操作</th>		
							      </tr>
							   </thead>
							   <tbody>
							    <c:forEach items="${documentnumberList}" var="plist"  varStatus="status">
							      <tr>
							         <td class="col-md-2">${plist.dntype}</td>
							         <td class="col-md-4">${plist.dnnumber}</td>										         
							         <td class="col-md-4">${plist.dnremark}</td>
							         <td class="col-md-2">
							         	<a href="javascript:openDN('${plist.dnid}','<s:property value="#session.personinfo.id"/>')" class=""></a>
							         	<a href="javascript:delDN('${plist.dnid}','<s:property value="#session.personinfo.id"/>')" class="zjnumdelA"></a>
							         </td>
							      </tr>	
							       </c:forEach>										      
							   </tbody>
							</table>
						</div>
					</div>
					<div class="clear"></div>
					<!--电话号码列表-->
					<div class="phonenumBox">
						<h3 class="addphonenumBtn"  onclick="openPN('','<s:property value="#session.personinfo.id"/>')"  style="width: 110px">电话号码</h3>
						<div class="table-responsive col-md-12 tableDiv rytableDiv">
							<table class="table table-bordered">
							   <thead>
							      <tr>
							         <th>类型</th>
							         <th>号码</th>
							         <th>备注信息</th>
							         <th>操作</th>		
							      </tr>
							   </thead>
							   <tbody>
							    <c:forEach items="${phonenumberList}" var="plist"  varStatus="status">
							      <tr>
							         <td class="col-md-2">${plist.pntype}</td>
							         <td class="col-md-4">${plist.pnnumber}</td>										         
							         <td class="col-md-4">${plist.pnremark}</td>
							         <td class="col-md-2">
							         <a href="javascript:openPN('${plist.pnid}','<s:property value="#session.personinfo.id"/>')" class=""></a>
							         	<a href="javascript:delPN('${plist.pnid}','<s:property value="#session.personinfo.id"/>')" class="phonenumdelA"></a>
							         </td>
							      </tr>	
							      </c:forEach>											 
							   </tbody>
							</table>
						</div>
					</div>
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
										<img src="../images/lawCase/moren/organaization.jpg" 
											         onclick="relationDetails('lawCaseOrg/orgRelationDetail.action?orgObj.id=${org.id}','组织(${org.orgCName})详细信息')"/>
										</c:if>
										<c:if test="${org.orgImage!=null&&org.orgImage!=''}">
										<img src="../images/lawCase/uploadImg/organization/${org.orgImage}" 
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
			<!-----工作关系----->
			<div class="userCon hidegzgxCon clearfix">
				
			</div>
			<!-----工作关系-结束----->
		</div>
		<script type="text/javascript">
		function delRelation(rid,rtype){
			 var rootType = $('#rootType').val();
			 if(peopleId!= null && peopleId !=''){
				 confirm('确定删除当前关联对象?',function(){
				   $.ajax({
						type : "POST",
						url : "<%=basePath%>peoplemanage/delRelationship.action",
						data:{
							id:peopleId,
							rootType:rootType,
							relId:rid,
							relType:rtype,
				         },
				         async : false,
				         dataType:"json",
						success : function(data){
				        	 location.href="<%=basePath%>peoplemanage/peopleMangeDetail.action?id="+peopleId;
						}
					});
				 });
			}else{
				parent.hintManager.showHint("请选择删除对象");
			}
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
