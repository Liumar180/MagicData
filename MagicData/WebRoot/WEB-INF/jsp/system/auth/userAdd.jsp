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
    <title>用户添加</title>
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
		   <c:when test="${empty user.id}">
				<form id="saveUserForm" method="post" action="ajaxauth/saveUser.action">
		   </c:when>
		   <c:otherwise>
		      	<form id="saveUserForm" method="post" action="ajaxauth/updateUser.action">
			  	<input type="hidden" id="userId" name="user.id" value="${user.id}" />
			  	<input type="hidden"  name="user.status" value="${user.status}" />
			  	<input type="hidden"  name="user.createTime" value="${user.createTime}" />
		   </c:otherwise>
	  	</c:choose>
	  	<input type="hidden"  id="roleIds" name="roleIds" value="" />
		<div class="form-group">
			<label class="labelTit">用户名称</label>
			<c:choose>
			   <c:when test="${empty user.id}">
					<input type="text" id="userName" name="user.userName" value="${user.userName}" class="form-control required" placeholder="请输入名称" />
			   </c:when>
			   <c:otherwise>
			   		<input type="text" id="userName" readonly="readonly" name="user.userName" value="${user.userName}" class="form-control required" placeholder="请输入名称" />
			   </c:otherwise>
		  	</c:choose>
		</div>
		<div style="clear: both;"></div>
		<div class="form-group">
			<label class="labelTit">密码</label>
			<input type="password" id="password" name="user.password" value="${user.password}" class="form-control required" placeholder="请输入密码" />
		</div>
		<div class="form-group">
			<label class="labelTit">确认密码</label>
			<input type="password" id="confpassword" value="${user.password}" class="form-control required" placeholder="请输入确认密码" />
		</div>
		<div class="form-group">
			<label class="labelTit">姓名</label>
			<input type="text" id="name" name="user.name" value="${user.name}" class="form-control" placeholder="请输入姓名" />
		</div>
		<div class="form-group">
			<label class="labelTit">电话</label>
			<input type="text" id="phone" name="user.phone" value="${user.phone}" class="form-control" placeholder="请输入电话" />
		</div>
		<div class="form-group comDiv">
			<label class="labelTit">描述</label>
			<textarea id="description" name="user.description" rows="3" cols="" >${user.description}</textarea>
		</div>
		<div class="form-group comDiv">
			<label class="labelTit" >角色</label>
			<div class="treeDiv usertreeDiv">
				<ul id="treeid" class="ztree" ></ul>
			</div>
		</div>
   		<div style="clear: both;"></div>
   		<c:if test="${!detailFlag}">
	   		<div class="btnDiv">
				<span onclick="saveUser()" class="btn btn-primary sureBtn">保存</span>
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
			 if(${detailFlag}){
				$('input').attr("readonly","readonly");
				$('textarea').attr("readonly","readonly");
			} 
		});
		
		/* 初始化角色树 */
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
		         url:"ajaxauth/loadRoleTree.action?userId=${user.id}",
		         async : false,
		         dataType:"json",
		         success:function(data){
		        	 var treeData = data.treeData;
		        	 zTreeObj = $.fn.zTree.init($("#treeid"),setting,treeData);
		         },
		         error:function(){
		        	 parent.hintManager.showHint("获取角色树异常，请联系管理员！");
		         }
		     });
		}
		
		//提交
		function saveUser(){
			var validateFlag = true;
			$("form :input.required").each(function(){
				var temp = $(this).val();
				if(temp.trim() == ""){
					validateFlag = false;
					return false;
				}
	        });
			if(!validateFlag){
				parent.hintManager.showHint("用户名称、密码、确认密码不能为空，请输入！");
				return;
			}
			//校验用户名
			var userNameVal = $("#userName").val();
			var regUserName = /^[a-zA-Z0-9_-]+$/;
			if(!regUserName.test(userNameVal)||userNameVal.length>64||userNameVal.length<6){
				parent.hintManager.showHint("用户名只能包括字母、数字、_ 、 -，长度为6-64位！");
				return;
			}
			//校验密码
			var pwdVal = $("#password").val().trim();
			var regPwd = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{2,}$/;
			var confPwdVal = $("#confpassword").val().trim();
			if(pwdVal!='6!e@9#a1fbZdA4C51e'){
				if(!regPwd.test(pwdVal)||pwdVal.length<6||pwdVal.length>16){
					parent.hintManager.showHint("密码必须由数字和字母组成，长度为6-16位！");
					return;
				} 
				if(pwdVal!=confPwdVal){
					parent.hintManager.showHint("两次密码输入不一致，请重新输入！");
					return;
				}
			}
			//校验姓名
			var nameVal = $("#name").val().trim();
			if(typeof(nameVal)!="undefind"&&nameVal!=''){
				if(nameVal.length>64){
					parent.hintManager.showHint("姓名不能超过64位！");
					return;
				}
			}
			//校验手机号
			var phoneVal = $("#phone").val().trim();
			if(phoneVal!=''){
				var regPhone = /^1[3|4|5|6|7|8]\d{9}$/;
				var regMobile = /^\d{3,4}-?\d{7,9}$/;
				if(!regPhone.test(phoneVal)&&!regMobile.test(phoneVal)){
					parent.hintManager.showHint("请输入正确的手机号码或固定电话！");
					return;
				}
			}
			var descriptionVal = $("#description").val().trim();
			if(descriptionVal.length>120){
				parent.hintManager.showHint("描述不能超过120位！");
				return;
			}
			//验证用户名称不能重复
			if(${empty user.id}){
				var userName = $("#userName").val().trim();
				$.ajax({
			         type:"post",
			         url:"ajaxauth/validateUserName.action",
			         data:{"user.userName":userName},
			         async : false,
			         dataType:"json",
			         success:function(data){
			        	 var flag = data.flag;
			        	 if(flag){
			        		 validateFlag = false;
			        		 parent.hintManager.showHint("用户名称不能重复，请重新输入！");
			        	 }else{
			        		 validateFlag = true;
			        	 }
			         },
			         error:function(){
			        	 validateFlag = false;
			        	 parent.hintManager.showHint("查询用户异常，请联系管理员！");
			         }
			     });
				if(!validateFlag){
					return;
				}
			}
			var checkNodes = zTreeObj.getCheckedNodes(true);
			var roleIds = "";
			var len = checkNodes.length;
			for(var i = 0;i < len; i++) {
     			var temp = checkNodes[i];
     			if(i+1 == len){
     				roleIds += temp.id;
     			}else{
     				roleIds += temp.id + ",";
     			}
			}
			$("#roleIds").val(roleIds);
			//保存用户
			var action = $("#saveUserForm").attr("action");
			$.ajax({
		         type:"post",
		         url:action,
		         data:$("#saveUserForm").serialize(),
		         async : false,
		         dataType:"json",
		         success:function(data){
		        	 var userId = $("#userId").val();
		        	 if(typeof(userId)=='undefined'||userId==''){
		        		 parent.hintManager.showSuccessHint("创建用户成功！");
		        	 }else{
		        		 parent.hintManager.showSuccessHint("修改用户成功！");
		        	 }
					 window.parent.dismissParentPop();
		        	 parent.window.frames["text"].refreshList();
		         },
		         error:function(){
		        	 parent.hintManager.showHint("保存用户异常，请联系管理员！");
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
