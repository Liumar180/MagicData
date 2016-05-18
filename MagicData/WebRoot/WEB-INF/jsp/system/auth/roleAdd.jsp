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
    <title>角色添加</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<link rel="stylesheet" type="text/css" href="styles/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="styles/systemManage/css/common.css"/>
	<link rel="stylesheet" type="text/css" href="styles/systemManage/css/systemManage.css"/>
	<link rel="stylesheet" type="text/css" href="styles/zTree/css/zTreeStyle/zTreeStyle.css">
	<style type="text/css">
	</style>
  </head>
  
  <body>
    <div class="addCon">
    	<c:choose>
		   <c:when test="${empty role.id}">
				<form id="saveRoleForm" method="post" action="ajaxauth/saveRole.action">
		   </c:when>
		   <c:otherwise>
		      	<form id="saveRoleForm" method="post" action="ajaxauth/updateRole.action">
			  	<input type="hidden"  name="role.id" value="${role.id}" />
			  	<input type="hidden"  name="role.createTime" value="${role.createTime}" />
		   </c:otherwise>
	  	</c:choose>
	  	<input type="hidden"  id="perIds" name="perIds" value="" />
		<div class="form-group" style="width:100%;">
			<label class="labelTit">角色名称</label>
			<c:choose>
		   		<c:when test="${empty role.id}">
					<input type="text" id="roleName" name="role.roleName" value="${role.roleName}" class="form-control required" placeholder="请输入名称" />
			   </c:when>
			   <c:otherwise>
					<input type="text" id="roleName" readonly="readonly" name="role.roleName" value="${role.roleName}" class="form-control required" placeholder="请输入名称" />
			   </c:otherwise>
		  	</c:choose>
		</div>
		<div class="form-group" style="width:100%;">
			<label class="labelTit">角色职能</label>
			<textarea name="role.description" id="description" rows="3" cols="" class="required">${role.description}</textarea>
		</div>
		<div class="form-group" style="width:100%;">
			<label class="labelTit">角色权限</label>
			<div class="treeDiv">
				<ul id="treeid" class="ztree"></ul>
			</div>
		</div>
   		<div style="clear: both;"></div>
   		<c:if test="${!detailFlag}">
	   		<div class="btnDiv">
				<span onclick="saveRole()" class="btn btn-primary sureBtn">保存</span>
				<span onclick="cancelSave()" class="btn btn-default qxBtn">取消</span>
			</div>
   		</c:if>
		</form>
	</div>
	<script type="text/javascript" src="scripts/jquery.min.js" ></script>
	<script type="text/javascript" src="scripts/systemManage/systemManage.js" ></script>
	<script type="text/javascript" src="styles/zTree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="styles/zTree/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript">
		$(function(){
			initTree();
		});
		
		/* 初始化资源树 */
		var setting = {
				check: {
					enable: true
				},
				data: {
					simpleData: {
						enable: true
					}
				}
			};
		var zTreeObj;
		function initTree(){
			$.ajax({
		         type:"post",
		         url:"ajaxauth/loadPermissionTree.action?roleId=${role.id}",
		         async : false,
		         dataType:"json",
		         success:function(data){
		        	 var treeData = data.treeData;
		        	 zTreeObj = $.fn.zTree.init($("#treeid"),setting,treeData);
		         },
		         error:function(){
		        	 parent.hintManager.showHint("获取资源树异常，请联系管理员！");
		         }
		     });
		}
		
		//提交
		function saveRole(){
			var validateFlag = true;
			$("form :input.required").each(function(){
				var temp = $(this).val();
				if(temp.trim() == ""){
					validateFlag = false;
					return false;
				}
	        });
			if(!validateFlag){
				parent.hintManager.showHint("角色名称、角色描述不能为空，请输入！");
				return;
			}
			var roleNameVal = $("#roleName").val().trim();
			var descriptionVal = $("#description").val().trim();
			if(roleNameVal.length>64){
				parent.hintManager.showHint("角色名称不能超过64位！");
				return;
			}
			if(descriptionVal.length>120){
				parent.hintManager.showHint("角色职能不能超过120位！");
				return;
			}
			//验证角色名称不能重复
		    if(${empty role.id}){
				var roleName = $("#roleName").val().trim();
				$.ajax({
			         type:"post",
			         url:"ajaxauth/validateRoleName.action",
			         data:{"role.roleName":roleName},
			         async : false,
			         dataType:"json",
			         success:function(data){
			        	 var flag = data.flag;
			        	 if(flag){
			        		 validateFlag = false;
			        		 parent.hintManager.showHint("角色名称不能重复，请重输入！");
			        	 }else{
			        		 validateFlag = true;
			        	 }
			         },
			         error:function(){
			        	 validateFlag = false;
			        	 parent.hintManager.showHint("查询角色异常，请联系管理员！");
			         }
			     });
				if(!validateFlag){
					return;
				}
		    }
			var checkNodes = zTreeObj.getCheckedNodes(true);
			var perIds = "";
			var len = checkNodes.length;
			for(var i = 0;i < len; i++) {
     			var temp = checkNodes[i];
     			if(i+1 == len){
     				perIds += temp.id;
     			}else{
     				perIds += temp.id + ",";
     			}
			}
			$("#perIds").val(perIds);
			//保存角色
			var action = $("#saveRoleForm").attr("action");
			$.ajax({
		         type:"post",
		         url:action,
		         data:$("#saveRoleForm").serialize(),
		         async : false,
		         dataType:"json",
		         success:function(data){
					 window.parent.dismissParentPop();
		        	 parent.window.frames["text"].refreshList();
		         },
		         error:function(){
		        	 parent.hintManager.showHint("保存角色异常，请联系管理员！");
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
