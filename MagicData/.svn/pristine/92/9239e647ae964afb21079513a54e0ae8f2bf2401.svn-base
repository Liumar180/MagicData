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
		function savePNinfo(){
			    var poid=$('#poid').val();
				var id=$('#pnid').val();
				var pnname=$('#pnname').val();
				var pntype=$('#pntype').val();
				var pnnumber=$('#pnnumber').val();//
				var pnremark=$('#pnremark').val();
				if(pnname==''){
					parent.hintManager.showHint("对象名称不能为空，请输入！");
					return;
				}
				if(pnnumber==''){
					parent.hintManager.showHint("号码不能为空，请输入！");
					return;
				}
			    $.ajax({
			         type:"post",
			         url:"<%=basePath%>peoplemanage/viewPeoplePNSave.action",
			         data:{
			        		id:id,
			        		poid:poid,
			        		pnname:pnname,
			        		pntype:pntype,
			        		pnnumber:pnnumber,
			        		pnremark:pnremark
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
		<div class="zjnumaddCon">
			<div class="form-group">
				<label class="labelTit">号码名称</label>
				<input type="text" class="form-control" placeholder="请输入号码名称"  id="pnname" value="${phonenumber.pnname}" />
				<input type="hidden" id="poid" value="${phonenumber.poid}" />
				<input type="hidden" id="pnid" value="${phonenumber.pnid}" />
			</div>
			<div class="form-group">
        		<label class="labelTit">号码类型</label>				
				<select class="form-control select" id="pntype">
				     <c:forEach items="${dataDictionary.phonetype}" var="level" >
							<c:choose>
							   <c:when test="${level.key == phonenumber.pntype}">
							      	<option value="${level.key}" selected="selected">${level.value}</option>
							   </c:when>
							   <c:otherwise>
							   		<option value="${level.key}">${level.value}</option>  
							   </c:otherwise>
						  	</c:choose>
						</c:forEach>							    
				</select>
    		</div>
			<div class="form-group" style="width: 35%;">
				<label class="labelTit">号码</label>
				<input type="text" class="form-control" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" style="width: 60%;" placeholder="请输入号码"  id="pnnumber" value="${phonenumber.pnnumber}" />
			</div>							                    		
    		<div style="clear: both;"></div>														
			<div class="form-group textDiv">
				<label class="labelTit labelTit1">备注</label>
				<textarea name="pnremark" id="pnremark" rows="5" cols="">${phonenumber.pnremark}</textarea>
			</div>
			<div style="clear: both;"></div>
    		<div class="btnDiv">
				<span class="btn btn-primary sureBtn" onclick="savePNinfo()">保存</span>
					<span class="btn btn-default qxBtn" onclick="window.parent.dismissParentPop()">取消</span>
			</div>
		</div>
  </body>
</html>
