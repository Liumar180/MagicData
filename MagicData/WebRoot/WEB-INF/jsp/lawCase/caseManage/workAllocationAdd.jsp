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
    <title>工作配置</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/common.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/my.css"/>
  </head>
  
  <body>
    <!--工作配置--点击添加按钮出现的弹出层-->
	<div class="workpzaddCon">
		<c:choose>
		   <c:when test="${empty allocation.id}">
				<form id="saveAllocationForm" method="post" action="ajaxCase/saveAllocation.action">
		   </c:when>
		   <c:otherwise>
		      	<form id="saveAllocationForm" method="post" action="ajaxCase/updateAllocation.action">
		   </c:otherwise>
	  	</c:choose>	
	  	<input type="hidden" name="allocation.id" value="${allocation.id}" /> 
	  	<input type="hidden" name="allocation.caseId" value="${allocation.caseId}" /> 
	  	<input type="hidden"  name="allocation.createTime" value="${allocation.createTime}" /> 
		<div class="form-group">
       		<label class="labelTit">对象类型</label>				
			<select class="form-control select" name="allocation.type" onchange="linkageObject(this.value)">
				<c:forEach items="${objectMap}" var="obj" >
					<c:choose>
					   <c:when test="${obj.key == allocation.type}">
					      	<option value="${obj.key}" selected="selected">${obj.value}</option>
					   </c:when>
					   <c:otherwise>
							<option value="${obj.key}">${obj.value}</option>
					   </c:otherwise>
				  	</c:choose>
				</c:forEach>
			</select>
   		</div>
   		<div class="form-group">
       		<label class="labelTit">具体对象</label>				
			<select class="form-control select" id="objectSelect" name="allocation.objectId">
				<c:forEach items="${typeObjects}" var="object" >
					<c:choose>
					   <c:when test="${object.id == allocation.objectId}">
					      	<option value="${object.id}" selected="selected"><c:out value="${object[typePro]}"/></option>
					   </c:when>
					   <c:otherwise>
							<option value="${object.id}"><c:out value="${object[typePro]}"/></option>
					   </c:otherwise>
				  	</c:choose>
				</c:forEach>
			</select>
   		</div>
   		<div class="form-group">
       		<label class="labelTit">负责人</label>				
			<select class="form-control select" name="allocation.principal">
				<c:forEach items="${userList}" var="user" >
					<c:choose>
					   <c:when test="${user.userName == allocation.principal}">
					      	<option value="${user.userName}" selected="selected">${user.userName}</option>
					   </c:when>
					   <c:otherwise>
							<option value="${user.userName}">${user.userName}</option>
					   </c:otherwise>
				  	</c:choose>
				</c:forEach>
			</select>
   		</div>
<!-- 		<div class="form-group"> -->
<!-- 			<label class="labelTit">发送时间</label> -->
<!-- 			<input type="text" id="timeId" name="allocation.createTime" value="${allocation.createTime}" class="form-control" placeholder="请输入时间" /> -->
<!-- 		</div> -->
		<div class="form-group textDiv">
			<label class="labelTit labelTit1">方案配置情况</label>
			<textarea name="allocation.allocation" rows="6" cols="" class="required">${allocation.allocation}</textarea>
		</div>
		<div class="form-group textDiv">
			<label class="labelTit labelTit1">效果反馈</label>
			<textarea name="allocation.result"  rows="4" cols="">${allocation.result}</textarea>
		</div>
   		<div class="form-group textDiv">
			<label class="labelTit labelTit1 labelTit2">备注</label>
			<textarea name="allocation.memo"  rows="2" cols="">${allocation.memo}</textarea>
		</div>
		<div style="clear: both;"></div>
   		<div class="btnDiv">
			<span onclick="saveObj()" class="btn btn-primary sureBtn">保存</span>
			<span onclick="cancelSave()" id="cancel" class="btn btn-default qxBtn">取消</span>
		</div>
		</form>
	</div>
	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>scripts/lawCase/my.js" ></script>
	<script type="text/javascript" src="<%=basePath%>scripts/lawCase/common.js" ></script>
	<script type="text/javascript" src="<%=basePath%>scripts/lawCase/Popup.js" ></script>
	<script type="text/javascript" src="<%=basePath%>scripts/lawCase/caseXq.js" ></script>
	<script type="text/javascript" src="<%=basePath%>scripts/lawCase/inputSelect.js" ></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>scripts/ueditor-jsp/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>scripts/ueditor-jsp/ueditor.all.min.js"> </script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>scripts/ueditor-jsp/lang/zh-cn/zh-cn.js"></script>
<!--     <script type="text/javascript" src="<%=basePath%>scripts/laydate-v1.1/laydate/laydate.js"></script> -->
	<script type="text/javascript">
		$(function(){
			 //初始化具体对象
			 var type = $("select[name='allocation.type']").val();
			 linkageObject(type);
		}); 
		
		var type_property = $.parseJSON('${type_propertyJson}');
		var ar = $.parseJSON(parent.window.frames["text"].allRelation);
		var objId = "${allocation.objectId}";
		function linkageObject(type){
// 			var type = obj.value;
			var pro = type_property[type];
			var objs = "";
			switch(type){
				case "case":
					objs = ar.caseList;
					break;
				case "file":
					objs = ar.fileList;
					break;
				case "host":
					objs = ar.hostList;
					break;
				case "organization":
					objs = ar.organList;
					break;
				case "people":
					objs = ar.peopleList;
					break;
				default:
					break;
			}
       	 	var html = "";
			if(objs){
				var len = objs.length;
	    		for(var i = 0;i < len; i++) {
	    			var temp = objs[i];
	    			if(objId == temp.id){
		    			html += '<option value="'+temp.id+'" selected="selected">'+temp[pro]+'</option>';
	    			}else{
		    			html += '<option value="'+temp.id+'">'+temp[pro]+'</option>';
	    			}
	    	    }
			}
    		$("#objectSelect").html(html);
			
			
			/* $.ajax({
		         type:"post",
		         url:"ajaxCase/findObjectsByType.action",
		         data:{
		        	 "allocation.type":type,
		        	 "allocation.caseId":"${allocation.caseId}"
		         },
		         async : false,
		         dataType:"json",
		         success:function(data){
		        	 var objs = data.typeObjects;
		        	 var len = objs.length;
		        	 var html = "";
		     		 for(var i = 0;i < len; i++) {
		     			var temp = objs[i];
		     			html += '<option value="'+temp.id+'">'+temp[pro]+'</option>';
		     	     }
		     		 $("#objectSelect").html(html);
		         },
		         error:function(){
		        	 parent.hintManager.showHint("查询具体对象异常，请联系管理员！");
		         }
		     }); */
		}
		
		//提交
		function saveObj(){
			var obj = $("#objectSelect").val();
			if(!obj){
				parent.hintManager.showHint("此类型没有关联的对象，不能配置此类型请重新选择对象类型！");
				return;
			}
			var validateFlag = true;
			$("form .required").each(function(){
				var temp = $(this).val();
				if(temp.replace(/(^\s*)|(\s*$)/g, "") == ""){
					validateFlag = false;
					return false;
				}
	        });
			if(!validateFlag){
				parent.hintManager.showHint("方案配置不能为空，请输入！");
				return;
			}
// 			$("#saveAllocationForm").submit();
			var action = $("#saveAllocationForm").attr("action");
			$.ajax({
		         type:"post",
		         url:action,
		         data:$("#saveAllocationForm").serialize(),
		         async : false,
		         dataType:"json",
		         success:function(data){
					 window.parent.dismissParentPop();
		        	 parent.window.frames["text"].refreshAllocation(1);
		         },
		         error:function(){
		        	 parent.hintManager.showHint("保存工作配置异常，请联系管理员！");
		         }
		     });
		}
		
		//取消
		function cancelSave(){
			window.parent.dismissParentPop();
		}
	</script>
  </body>
</html>
