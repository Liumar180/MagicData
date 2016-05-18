<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
	<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
   	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>域名情况</title>
	<link href="styles/lawcase/css/bootstrap.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="styles/lawcase/css/common.css"/>
	<link rel="stylesheet" type="text/css" href="styles/lawcase/css/my.css"/>
	<script type="text/javascript" src="scripts/lawCase/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="scripts/lawCase/my.js" ></script>
	<script type="text/javascript" src="scripts/lawCase/common.js" ></script>
	<script type="text/javascript" src="scripts/lawCase/Popup.js" ></script>
	<script type="text/javascript" src="scripts/lawCase/inputSelect.js" ></script>
	<script src="scripts/laydate-v1.1/laydate/laydate.js"></script>
	</head>
	<body>
		<!-- 域名情况--点击添加按钮出现的弹出层-->
		<div class="ymaddCon">
			<div class="form-group" style="float: none; width: 50%;">
				<label class="labelTit" style="width: 20%;">对象名称</label>
				<input type="hidden" id="dId" name="did" value="${domains.id}" />
				<input type="hidden" id="hostId" name="hostId" value="${hostId}" />
				<input type="text" id="objectName" name="objectName" value="${hostName}" class="form-control" readonly="readonly" />
			</div>
			<div class="form-group zjform-group">
				<label class="labelTit">域名</label>
				<input type="text" id="domain" name="domain" value="${domains.domain}" class="form-control" placeholder="请输入域名" />
			</div>
			<div class="form-group zjform-group">
				<label class="labelTit">域名解析机构</label>
				<input type="text" id="domainResolution" name="domainResolution" value="${domains.domainResolution}" class="form-control" placeholder="请输入域名解析机构" />
			</div>
			<div class="form-group zjform-group">
				<label class="labelTit">域名解析服务器</label>
				<input type="text" id="domainResolutionServices" name="domainResolutionServices" value="${domains.domainResolutionServices}" class="form-control" placeholder="请输入域名解析服务器" />
			</div>							
			<div class="form-group">
				<label class="labelTit">域名服务商</label>
				<input type="text" id="domainServiceProvider" name="domainServiceProvider" value="${domains.domainServiceProvider}" class="form-control" placeholder="请输入域名服务商" />
			</div>
			<div class="form-group">
				<label class="labelTit">数据库类型</label>
				<input type="text" id="databaseType" name="databaseType" value="${domains.databaseType}" class="form-control" placeholder="请输入数据库类型" />
			</div>
			<div class="form-group">
				<label class="labelTit">注册时间</label>
				<input type="text" id="regTime" name="regTime" value="${domains.regTime}" class="form-control" placeholder="请输入注册时间" />
			</div>
			<div class="form-group">
				<label class="labelTit">过期时间</label>
				<input type="text" id="expiredTime" name="expiredTime" value="${domains.expiredTime}" class="form-control" placeholder="请输入过期时间" />
			</div>
    		<div class="form-group">
				<label class="labelTit">服务器类型</label>
				<input type="text" id="serviceType" name="serviceType" value="${domains.serviceType}" class="form-control" placeholder="请输入服务器类型" />
			</div>
			<div class="form-group">
				<label class="labelTit">开发语言</label>
				<input type="text" id="developeLanguage" name="developeLanguage" value="${domains.developeLanguage}" class="form-control" placeholder="请输入开发语言" />
			</div>
    		<div style="clear: both;"></div>														
			<div class="form-group textDiv">
				<label class="labelTit labelTit1">备注</label>
				<textarea name="remarkes" id="remarkes" rows="5" cols="">${domains.remarkes}</textarea>
			</div>
			<div style="clear: both;"></div>
    		<div class="btnDiv">
				<button class="btn btn-primary sureBtn" onclick="addDomainInfo()">保存</button>
				<button class="btn btn-default qxBtn" onclick="exit()">取消</button>
			</div>
		</div>
		<script type="text/javascript">
		$(function(){
			laydate({
				   elem: '#regTime'
				}); 
			laydate({
				   elem: '#expiredTime'
				});
		});
		function exit(){
			window.parent.dismissParentPop();
		}
		var myDate = new Date();
		var today = myDate.toLocaleDateString(); 
		function addDomainInfo(){
			var dId = $('#dId').val();
			var hostId=$('#hostId').val();
		    var objectName=$('#objectName').val();
			var domain=$('#domain').val().trim();
			if(domain.length==0){
		    	parent.hintManager.showHint("对不起，域名不能为空或者为空格！");   
	            return false;
		    }else if(domain.length > 30){
				parent.hintManager.showHint("输入域名内容过长，请重新输入！");   
	            return false;
			}
			var domainResolution=$('#domainResolution').val().trim();
			if(domainResolution.length > 500){
				parent.hintManager.showHint("输入域名解析机构内容过长，请重新输入！");   
	            return false;
			}
			var domainResolutionServices=$('#domainResolutionServices').val().trim();
			if(domainResolutionServices.length > 500){
				parent.hintManager.showHint("输入域名解析服务器内容过长，请重新输入！");   
	            return false;
			}
			var domainServiceProvider=$('#domainServiceProvider').val().trim();
			if(domainServiceProvider.length > 30){
				parent.hintManager.showHint("输入域名服务商内容过长，请重新输入！");   
	            return false;
			}
			var databaseType=$('#databaseType').val().trim();
			if(databaseType.length > 20){
				parent.hintManager.showHint("输入数据库类型内容过长，请重新输入！");   
	            return false;
			}
			var regTime=$('#regTime').val();
			var expiredTime=$('#expiredTime').val();
			var arys1= new Array();      
			var arys2= new Array();
			var arys3= new Array();      
			var arys4= new Array();
			if(regTime.length == 0){
				parent.hintManager.showHint("对不起，注册时间不能为空！");
				return false;
			}else if(expiredTime.length == 0){
				parent.hintManager.showHint("对不起，过期时间不能为空！");
				return false;
			}else if(today != null){
				arys3=regTime.split('-');      
			    var sdate=new Date(arys3[0],parseInt(arys3[1]-1),arys3[2]);      
			    arys4=today.split('/');      
			    var edate=new Date(arys4[0],parseInt(arys4[1]-1),arys4[2]);
			    if(sdate > edate) {
					parent.hintManager.showHint("注册日期大于当前日期，请重新输入！");
				    return false;         
				}
			}else if(regTime != null && expiredTime != null) {      
			    arys1=regTime.split('-');      
			    var sdate=new Date(arys1[0],parseInt(arys1[1]-1),arys1[2]);      
			    arys2=expiredTime.split('-');      
			    var edate=new Date(arys2[0],parseInt(arys2[1]-1),arys2[2]);      
			if(sdate > edate) {
				parent.hintManager.showHint("注册日期大于过期日期，请重新输入！");
			    return false;         
			}  
			    }      
			
			
			
			var serviceType=$('#serviceType').val().trim();
			if(serviceType.length > 25){
				parent.hintManager.showHint("输入服务器类型内容过长，请重新输入！");   
	            return false;
			}
			var developeLanguage=$('#developeLanguage').val().trim();
			if(developeLanguage.length > 25){
				parent.hintManager.showHint("输入开发语言内容过长，请重新输入！");   
	            return false;
			}
			var remarkes=$('#remarkes').val().trim();
			if(remarkes.length > 500){
				parent.hintManager.showHint("输入备注内容过长，请重新输入！");   
	            return false;
			}
			$.ajax({
		         type:"post",
		         url:"hostManage/addDomain.action",
		         data:{
		        	 objectName:objectName,
		        	 domain:domain,
		        	 domainResolution:domainResolution,
		        	 domainResolutionServices:domainResolutionServices,
		        	 domainServiceProvider:domainServiceProvider,
		        	 databaseType:databaseType,
		        	 regTime:regTime,
		        	 expiredTime:expiredTime,
		        	 serviceType:serviceType,
		        	 developeLanguage:developeLanguage,
		        	 remarkes:remarkes,
		        	 hostId:hostId,
		        	 dId:dId,
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