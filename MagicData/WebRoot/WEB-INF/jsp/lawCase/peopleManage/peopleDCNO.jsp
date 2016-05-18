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
	    var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
		function saveDNinfo(){
			    var poid=$('#poid').val();
				var id=$('#dnid').val();
				var dnname=$('#dnname').val();
				var dntype=$('#dntype').val();
				var dnnumber=$('#dnnumber').val();//
				
				var dnremark=$('#dnremark').val();
				if(dnname==''){
					parent.hintManager.showHint("对象名称不能为空，请输入！");
					return;
				}
				if(dnnumber==''){
					parent.hintManager.showHint("证件号码不能为空，请输入！");
					return;
				}else if(!dnnumber.match(reg)){
					parent.hintManager.showHint("请输入合法的证件号码！");
					return false;
				}
			    $.ajax({
			         type:"post",
			         url:"<%=basePath%>peoplemanage/viewPeopleDNSave.action",
			         data:{
			        		id:id,
			        		poid:poid,
			        		dnname:dnname,
			        		dntype:dntype,
			        		dnnumber:dnnumber,
			        		dnremark:dnremark
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
				<label class="labelTit">证件名称</label>
				<input type="text" class="form-control" placeholder="请输入证件名称"  id="dnname" value="${documentnumber.dnname}" />
				<input type="hidden" id="poid" value="${documentnumber.poid}" />
				<input type="hidden" id="dnid" value="${documentnumber.dnid}" />
			</div>
			<div class="form-group">
        		<label class="labelTit">证件类型</label>				
				<select class="form-control select" id="dntype">
				     <c:forEach items="${dataDictionary.DCtype}" var="level" >
							<c:choose>
							   <c:when test="${level.key == documentnumber.dntype}">
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
				<label class="labelTit">证件号码</label>
				<input type="text" class="form-control" style="width: 60%;" placeholder="请输入证件号码"  id="dnnumber" value="${documentnumber.dnnumber}" />
			</div>							                    		
    		<div style="clear: both;"></div>														
			<div class="form-group textDiv">
				<label class="labelTit labelTit1">备注</label>
				<textarea name="dnremark" id="dnremark" rows="5" cols="">${documentnumber.dnremark}</textarea>
			</div>
			<div style="clear: both;"></div>
    		<div class="btnDiv">
				<span class="btn btn-primary sureBtn" onclick="saveDNinfo()">保存</span>
					<span class="btn btn-default qxBtn" onclick="window.parent.dismissParentPop()">取消</span>
			</div>
		</div>
  </body>
</html>
