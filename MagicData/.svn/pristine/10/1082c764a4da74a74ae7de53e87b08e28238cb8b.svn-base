<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("base",basePath);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>数据魔方</title>
		<script type="text/javascript" src="<%=basePath%>scripts/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>scripts/ajaxfileupload.js"></script>
		<script type="text/javascript">
			function filechange(a){
				var licensename = $("#license").val();
				$("#licenseName").val(licensename);
			}
			function licenseUpload(){
				var licenseName = $("#licenseName").val();
				if(typeof(licenseName)!='undefined'&&licenseName!=null&&licenseName!=''){
					$.ajaxFileUpload({
						type:"post",
	                    url:"<%=basePath%>ajaxauth/licenseUpload.action",//用于文件上传的服务器端请求地址
	                    secureuri:false,//一般设置为false
	                    fileElementId:'license',//文件上传空间的id属性
	                    async : false,
	                    dataType : "JSON",
	                    success : function(data, status){
	                    	data = $.parseJSON(data);
	                    	//alert(data.dataMap.success);
	                    	if(data.dataMap.success==true){
	                    		window.location = "quit.action";
	                    	}
	                    },
	                    error : function (data, status, e){
	                    	console.log(data);
	                    }
					});
				}else{
					alert("请先选择license!");
				}
			}
		</script>
	</head>
	<body>
		<div >
			授权过期，请联系管理员！
		</div>
		<div>
			<input type='text' name='licenseName' id='licenseName' class='txt' />
			<input type="file" name="license" class="file" id="license" size="28" onchange="filechange(this)" />
			<input type="button" value="上传" id="upload" onclick="licenseUpload()" />
		</div>
	</body>
</html>
