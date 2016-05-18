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
    
    <title>工作记录</title>
    
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
    <!--工作记录--点击添加按钮出现的弹出层-->			
	<div class="workjladdCon">
		<c:choose>
		   <c:when test="${empty record.id}">
				<form id="saveRecordForm" method="post" action="ajaxCase/saveRecord.action">
		   </c:when>
		   <c:otherwise>
		      	<form id="saveRecordForm" method="post" action="ajaxCase/updateRecord.action">
		   </c:otherwise>
	  	</c:choose>
	  	<input type="hidden" name="record.id" value="${record.id}" /> 	         
	  	<input type="hidden" name="record.caseId" value="${record.caseId}" />  
	  	<input type="hidden"  name="record.createTime" value="${record.createTime}" />        		
		<div class="form-group textDiv">
			<label class="labelTit labelTit2">标题</label>
			<input type="text" name="record.title" value="${record.title}" class="form-control form-control1 required" placeholder="请输入标题" />
		</div>
		<div class="form-group textDiv">
			<label class="labelTit labelTit1">内容</label>
			<textarea name="record.conent" rows="6" cols="" class="required">${record.conent}</textarea>
		</div>
<!-- 		<div class="form-group textDiv"> -->
<!-- 			<label class="labelTit labelTit2">创建时间</label> -->
<!-- 			<input type="text" id="timeId" name="record.createTime" value="${record.createTime}" class="form-control form-control2" placeholder="请输入时间" /> -->
<!-- 		</div> -->
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
    <script type="text/javascript" src="<%=basePath%>scripts/laydate-v1.1/laydate/laydate.js"></script>
	<script type="text/javascript">
		
		$(function(){
			//初始化日期
			laydate({
				elem: '#timeId'
			}); 
		});
		
		//提交
		function saveObj(){
			//验证
			var validateFlag = true;
			$("form .required").each(function(){
				var temp = $(this).val();
				if(temp.replace(/(^\s*)|(\s*$)/g, "") == ""){
					validateFlag = false;
					return false;
				}
	        });
			if(!validateFlag){
				parent.hintManager.showHint("标题、内容不能为空，请输入！");
				return;
			}
// 			$("#saveRecordForm").submit();
			var action = $("#saveRecordForm").attr("action");
			$.ajax({
		         type:"post",
		         url:action,
		         data:$("#saveRecordForm").serialize(),
		         async : false,
		         dataType:"json",
		         success:function(data){
					 window.parent.dismissParentPop();
		        	 parent.window.frames["text"].refreshRecord(1);
		         },
		         error:function(){
		        	 parent.hintManager.showHint("保存工作记录异常，请联系管理员！");
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
