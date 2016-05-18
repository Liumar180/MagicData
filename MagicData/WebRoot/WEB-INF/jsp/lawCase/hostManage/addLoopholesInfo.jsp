<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
   	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>漏洞情况</title>
	<link href="styles/lawcase/css/bootstrap.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="styles/lawcase/css/common.css"/>
	<link rel="stylesheet" type="text/css" href="styles/lawcase/css/my.css"/>
	<script type="text/javascript" src="scripts/lawCase/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="scripts/lawCase/my.js" ></script>
	<script type="text/javascript" src="scripts/lawCase/common.js" ></script>
	<script type="text/javascript" src="scripts/lawCase/Popup.js" ></script>
	<script type="text/javascript" src="scripts/lawCase/inputSelect.js" ></script>
	</head>
	<body>
		<!--漏洞情况--点击添加按钮出现的弹出层-->
		<div class="ldaddCon">
			<div class="form-group">
				<label class="labelTit">对象名称</label>
				<input type="hidden" id="lId" name="lid" value="${loopholes.id}" />
				<input type="hidden" id="hostId" name="hostId" value="${hostId}" />
				<input type="text" id="objectName" name="objectName" value="${hostName}" class="form-control" readonly="readonly" />
			</div>
			<div class="form-group">
        		<label class="labelTit">权限类型</label>				
				<input type="text" id="authorityType" name="authorityType" value="${loopholes.authorityType}" class="form-control" placeholder="请输入权限类型" />
    		</div>														                    		
    		<div style="clear: both;"></div>
    		<div class="form-group textDiv">
				<label class="labelTit labelTit1">漏洞描述</label>
				<textarea id="ldms" name="vulnerabilityDescription" rows="5" cols="">${loopholes.vulnerabilityDescription}</textarea>
			</div>
			<div class="form-group textDiv">
				<label class="labelTit labelTit1">利用方法</label>
				<textarea id="methods" name="methods" rows="5" cols="">${loopholes.methods }</textarea>
			</div>
			<div class="form-group textDiv">
				<label class="labelTit labelTit1">利用工具</label>
				<textarea id="useTools" name="useTools" rows="5" cols="">${loopholes.useTools }</textarea>
			</div>
			<div class="form-group textDiv">
				<label class="labelTit labelTit1">隐藏后门</label>
				<textarea id="hiddenDoor" name="hiddenDoor" rows="5" cols="">${loopholes.hiddenDoor }</textarea>
			</div>
			<div class="form-group textDiv">
				<label class="labelTit labelTit1">备注</label>
				<textarea id="remarks" name="remarks" rows="5" cols="">${loopholes.remarks }</textarea>
			</div>
			<div style="clear: both;"></div>
    		<div class="btnDiv">
				<button class="btn btn-primary sureBtn" onclick="addLoopholes()">保存</button>
				<button class="btn btn-default qxBtn" onclick="exit()">取消</button>
			</div>
		</div>
		<script type="text/javascript">
		function exit(){
			window.parent.dismissParentPop();
		}
		function addLoopholes(){
			var lId = $('#lId').val();
		    var hostId=$('#hostId').val();
			var objectName=$('#objectName').val();
			var authorityType=$('#authorityType').val().trim();
			if(authorityType.length==0){
		    	parent.hintManager.showHint("对不起，权限类型不能为空或者为空格！");   
	            return false;
		    }else if(authorityType.length > 30){
				parent.hintManager.showHint("输入权限类型内容过长，请重新输入！");   
	            return false;
			}
			var ldms=$('#ldms').val().trim();
			if(ldms.length==0){
		    	parent.hintManager.showHint("对不起，漏洞描述不能为空或者为空格！");   
	            return false;
		    }else if(ldms.length > 500){
				parent.hintManager.showHint("输入漏洞描述内容过长，请重新输入！");   
	            return false;
			}
			var methods=$('#methods').val().trim();
			if(methods.length > 500){
				parent.hintManager.showHint("输入利用方法内容过长，请重新输入！");   
	            return false;
			}
			var useTools=$('#useTools').val().trim();
			if(useTools.length > 500){
				parent.hintManager.showHint("输入利用工具内容过长，请重新输入！");   
	            return false;
			}
			var hiddenDoor=$('#hiddenDoor').val().trim();
			if(hiddenDoor.length > 500){
				parent.hintManager.showHint("输入隐藏后门内容过长，请重新输入！");   
	            return false;
			}
			var remarks=$('#remarks').val().trim();
			if(remarks.length > 500){
				parent.hintManager.showHint("输入备注内容过长，请重新输入！");   
	            return false;
			}
			$.ajax({
		         type:"post",
		         url:"hostManage/addLoopholes.action",
		         data:{
		        	 hostId:hostId,
		        	 objectName:objectName,
		        	 authorityType:authorityType,
		        	 vulnerabilityDescription:ldms,
		        	 methods:methods,
		        	 useTools:useTools,
		        	 hiddenDoor:hiddenDoor,
		        	 remarks:remarks,
		        	 lId:lId,
		         },
		         async : false,
		         dataType:"json",
		         success:function(data){
		        	$('.tccBox', parent.document).hide();
		        	 parent.text.location.href="hostManage/searchHostDetails.action?hid="+data;
		         }
		     });

			
	}
		
		</script>
	</body>
</html>
