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
    
    <title>My JSP 'peopleLayout.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/common.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/my.css"/>
	
	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>scripts/lawCase/my.js" ></script>
	<script type="text/javascript" src="<%=basePath%>scripts/lawCase/common.js" ></script>
	<script type="text/javascript" src="<%=basePath%>scripts/lawCase/Popup.js" ></script>
	<script type="text/javascript" src="<%=basePath%>scripts/lawCase/inputSelect.js" ></script>
	    <script src="<%=basePath%>scripts/laydate-v1.1/laydate/laydate.js"></script>
	    <script type="text/javascript">
		function savePVinfo(){
			    var poid=$('#poid').val();
				var id=$('#pvid').val();
				var pvname=$('#pvname').val();
				var pvaccounttype=$('#pvaccounttype').val();
				var pvusername=$('#pvusername').val();//
				var pvcontrolstatus=$('#pvcontrolstatus').val();///
				var pvcontrolmeasures=$('#pvcontrolmeasures').val();//
				var pvpassword=$('#pvpassword').val();//
				var pvloginaddress=$('#pvloginaddress').val();
				var pvremark=$('#pvremark').val();
				if(pvname==''){
					parent.hintManager.showHint("对象名称不能为空，请输入！");
					return;
				}
				if(pvusername==''){
					parent.hintManager.showHint("用户名不能为空，请输入！");
					return;
				}
				if(pvpassword==''){
					parent.hintManager.showHint("密码不能为空，请输入！");
					return;
				}
			    $.ajax({
			         type:"post",
			         url:"<%=basePath%>peoplemanage/viewPeoplePVSave.action",
			         data:{
			        		id:id,
			        		poid:poid,
			        		pvname:pvname,
			        		pvaccounttype:pvaccounttype,
			        		pvusername:pvusername,
			        		pvcontrolstatus:pvcontrolstatus,
			        		pvcontrolmeasures:pvcontrolmeasures,
			        		pvpassword:pvpassword,
			        		pvloginaddress:pvloginaddress,
			        		pvremark:pvremark
			         },
			         async : false,
			         dataType:"json",
			         success:function(data){
			        	//$(".tccBox").hide();
			        	$('.tccBox', parent.document).hide();
			        	 parent.text.location.href="<%=basePath%>peoplemanage/peopleMangeDetail.action?id="+data;
			         }
			     });
		} 
		</script>
  </head>
  
  <body>
  <div class="xnsfaddCon">
			<div class="form-group" style="float: none;">
				<label class="labelTit">虚拟名称</label>
				<input type="text" class="form-control" placeholder="请输入对象名称"  id="pvname" value="${peoplevirtual.pvname}" />
				<input type="hidden" id="poid" value="${peoplevirtual.poid}" />
				<input type="hidden" id="pvid" value="${peoplevirtual.pvid}" />
			</div>
			<div class="form-group">
        		<label class="labelTit">账号类型</label>				
				<select class="form-control select"  id="pvaccounttype">
				    <c:forEach items="${dataDictionary.accounttype}" var="level" >
							<c:choose>
							   <c:when test="${level.key == peoplevirtual.pvaccounttype}">
							      	<option value="${level.key}" selected="selected">${level.value}</option>
							   </c:when>
							   <c:otherwise>
							   		<option value="${level.key}">${level.value}</option>  
							   </c:otherwise>
						  	</c:choose>
						</c:forEach>	
				</select>
    		</div>
			<div class="form-group">
				<label class="labelTit">用户名</label>
				<input type="text" class="form-control" placeholder="请输入用户名"  id="pvusername" value="${peoplevirtual.pvusername}"/>
			</div>
			<div class="form-group">
        		<label class="labelTit">控制状态</label>				
				<select class="form-control select"  id="pvcontrolstatus">
				    <c:forEach items="${dataDictionary.controlStatus}" var="level" >
							<c:choose>
							   <c:when test="${level.key == peoplevirtual.pvcontrolstatus}">
							      	<option value="${level.key}" selected="selected">${level.value}</option>
							   </c:when>
							   <c:otherwise>
							   		<option value="${level.key}">${level.value}</option>  
							   </c:otherwise>
						  	</c:choose>
						</c:forEach>							    
				</select>
    		</div>
    		<div class="form-group">
        		<label class="labelTit">控制措施</label>				
				<select class="form-control select"  id="pvcontrolmeasures">
				    <c:forEach items="${dataDictionary.controlmeasures}" var="level" >
							<c:choose>
							   <c:when test="${level.key == peoplevirtual.pvcontrolmeasures}">
							      	<option value="${level.key}" selected="selected">${level.value}</option>
							   </c:when>
							   <c:otherwise>
							   		<option value="${level.key}">${level.value}</option>  
							   </c:otherwise>
						  	</c:choose>
						</c:forEach>							    
				</select>
    		</div>
    		<div style="clear: both;"></div>
			<div class="form-group textDiv">
				<label class="labelTit labelTit1">密码</label>
				<input type="password" class="form-control"  id="pvpassword" value="${peoplevirtual.pvpassword}"/>
			</div>
			<div class="form-group textDiv">
				<label class="labelTit labelTit1">登录地址</label>
				<input type="text" class="form-control"  id="pvloginaddress" value="${peoplevirtual.pvloginaddress}"/>
			</div>
			<div class="form-group textDiv">
				<label class="labelTit labelTit1">备注</label>
				<textarea name="pvremark" id="pvremark" rows="5" cols="">${peoplevirtual.pvremark}</textarea>
			</div>
			<div style="clear: both;"></div>
    		<div class="btnDiv">
				<span class="btn btn-primary sureBtn" onclick="savePVinfo()">保存</span>
					<span class="btn btn-default qxBtn" onclick="window.parent.dismissParentPop()">取消</span>
			</div>
		</div>
  </body>
</html>
