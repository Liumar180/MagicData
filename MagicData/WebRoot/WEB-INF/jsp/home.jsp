<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

pageContext.setAttribute("base",basePath);
String username=(String)session.getAttribute("username");
Integer roleType=(Integer)session.getAttribute("roleType");
request.setAttribute("username",username);
request.setAttribute("roleType",roleType);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
	<head>
	<base href="<%=basePath %>">
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>DataSmart</title>
		<link href="styles/home/css/bootstrap.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="styles/home/css/dataIndex.css"/>
		<link href="styles/css/base.css" rel="stylesheet" type="text/css">	
		<link rel="stylesheet" type="text/css" href="styles/css/customCss.css">
		
	</head>
	<body>
		<div class="container">
			<nav class="navbar clearfix" role="navigation">
				<div class="navbar-header pull-left">
      				<a class="navbar-brand" href="#"><img src="styles/home/img/DataSmart-logo.png"/></a>
   				</div>
   				<div class="rightNav pull-right">
      				<p class="navbar-text pull-left">
      					<img src="styles/home/img/personIcon.png"/>
         				<a href="javascript:void(0)" title="点击更改用户密码" class="navbar-link" onclick="showMask()"><%=username %></a>
      				</p>
      				<div class="navIcon pull-right">
      					<span class="icon" ><img src="styles/home/img/navImg1.png" /><em>菜单</em></span>
      					<div class="navList">
      						<ul class="list-unstyled">
      							<c:if test="${fn:contains(permissionList,'dataAnalyze')}">
      								<li><a href="common/DataSmart.action" target="_blank">数据分析</a></li>
      							</c:if>
      							<c:if test="${fn:contains(permissionList,'caseManage')}">
	      							<li class="caseLi"><a href="case/caseMangeIndex.action" target="_blank">案件管理</a></li>
      							</c:if>
      							<c:if test="${fn:contains(permissionList,'systemManage')}">
      								<li class="systemLi"><a href="system/viewSystemPage.action" target="_blank">系统管理</a></li>
      							</c:if>
      							<c:if test="${fn:contains(permissionList,'systemManage')}">
      								<li class=""><a href="common/DataImportNew.action" target="_blank">数据导入</a></li>
      							</c:if>
      						</ul>
      					</div>
      				</div>
      				<p class="navbar-text exit pull-left">
      					<img src="styles/home/img/exitIcon.png"/>
         				<a href="quit.action" class="navbar-link exitText">退出</a>
      				</p>
   				</div>
   				
			</nav>
			<div class="mainCon clearfix">
				<img src="styles/home/img/mainImg.jpg"/>
			</div>
			<div class="bottomDiv clearfix">
				<span class="lefttopIcon"></span>
				<span class="leftdownIcon"></span>
				<span class="righttopIcon"></span>
				<span class="rightdownIcon"></span>
				<div class="cardDiv">
					<h3><span></span>社交类</h3>
					<div class="cardCon">
						<p>数据量：7000万</p>
						<p>数据量：新浪微博、人人网、豆瓣网、开心网、世纪佳缘、知乎网、大街网......</p>
					</div>
				</div>
				<div class="cardDiv">
					<h3><span class="shopping"></span>购物类</h3>
					<div class="cardCon">
						<p>数据量：7000万</p>
						<p>数据量：新浪微博、人人网、豆瓣网、开心网、世纪佳缘、知乎网、大街网......</p>
					</div>
				</div>
				<div class="cardDiv">
					<h3><span class="kd"></span>快递类</h3>
					<div class="cardCon">
						<p>数据量：7000万</p>
						<p>数据量：新浪微博、人人网、豆瓣网、开心网、世纪佳缘、知乎网、大街网......</p>
					</div>
				</div>
				<div class="cardDiv">
					<h3><span class="lt"></span>论坛类</h3>
					<div class="cardCon">
						<p>数据量：7000万</p>
						<p>数据量：新浪微博、人人网、豆瓣网、开心网、世纪佳缘、知乎网、大街网......</p>
					</div>
				</div>
			</div>
		</div>
		<!--admin修改密码弹出层-->
		<div id="box" class="box">
	  		<div class="blackBg"></div>
	  		<div class="box1">
	  			<h1>修改密码</h1>
	  			<div style="margin-top:-10px;width:145px;height:40px; background: url(${base}images/img/topIcon.png) 0px -87px no-repeat;"><%=username %></div>
				<form name="frm" id="pwdform" action="" method="post">
					<span><label style="">原密码：</label><input type="password" id="oldPwd" name="oldPwd" class="required"/><br><br></span>
					<span><label style="">新密码：</label><input type="password" id="newPwd" name="newPwd" class="required"/><br><br></span>
					<span><label style="">确认新密码：</label><input type="password" id="confirmNewPwd" name="confirmNewPwd" class="required"/> <br><br></span>
					<span>
						<input type="button" class="xgBtn" onclick="doSubmit()" value="修改" />
						<input type="button" class="closeBtn"  onclick="closeWin()" value="关闭" />
					</span>
				</form>
			</div>
		</div> 
		<div class='hintMsg'></div>
		<script type="text/javascript" src="scripts/home/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="scripts/home/js/bootstrap.js"></script>
		<script type="text/javascript" src="scripts/home/js/dataIndex.js" ></script>
		<script type="text/javascript" src="styles/bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="styles/bootstrap/js/bootstrap-prompts-alert.js"></script> 
        <script type="text/javascript" src="scripts/customJs.js"></script>
        <script type="text/javascript" src="scripts/ajaxsetup.js"></script>
		<script type="text/javascript">
		function showMask(){
			$("form :input.required").each(function(){
				$(this).val("");
	        });
			$(".box").show();
		}
		function closeWin(){
			$(".box").hide();
		}

		/* 修改密码 */
		function doSubmit(){
			var validateFlag = true;
			$("form :input.required").each(function(){
				var temp = $(this).val();
				if(temp.trim() == ""){
					validateFlag = false;
					return;
				}
	        });
			if(!validateFlag){
				hintManager.showHint("密码输入不能为空，请输入！");
				return;
			}
			var oldPwd = $("#oldPwd").val().trim();
			var newPwd = $("#newPwd").val().trim();
			var confirmNewPwd = $("#confirmNewPwd").val().trim();
			var regPwd = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{2,}$/;
			if(!regPwd.test(newPwd)||newPwd.length<6||newPwd.length>16){
				hintManager.showHint("密码必须由数字和字母组成，长度为6-16位！");
				return;
			} 
			if(newPwd != confirmNewPwd){
				hintManager.showHint("新密码和确认密码不一致，请重新填写！");
				return;
			}
			if(newPwd == oldPwd){
				hintManager.showHint("新密码和原密码相同，请重新填写！");
				return;
			}
			//修改密码
			confirm("是否确认修改密码？",function(){
				$.ajax({
			         type:"post",
			         url:"ajaxauth/updateUserPassword.action",
			         data:{
			        	"oldPwd":oldPwd,
			        	"newPwd":newPwd
			         },
			         async : false,
			         dataType:"json",
			         success:function(data){
						 if(data.flag){
							 hintManager.showSuccessHint("修改密码成功！");
							 closeWin();
						 }else{
							 hintManager.showHint(data.msg);
						 }
			         },
			         error:function(){
			        	 hintManager.showHint("密码修改异常，请联系管理员！");
			         }
			     });
			});
		}
		
		</script>
	</body>
</html>
