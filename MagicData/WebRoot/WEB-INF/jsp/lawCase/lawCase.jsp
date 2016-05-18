<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>案件管理</title>
		<link href="<%=basePath%>styles/lawcase/css/bootstrap.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/ajControl.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/my.css"/>
		<link rel="stylesheet" type="text/css" href="styles/css/customCss.css">
		<link rel="stylesheet" type="text/css" href="styles/lawcase/css/lawDialag.css"/>
		<script type="text/javascript" src="<%=basePath%>scripts/lawCase/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="scripts/ui/jquery-ui.min.js"></script>
		
		<script type="text/javascript" src="<%=basePath%>scripts/lawCase/my.js" ></script>
		<script type="text/javascript" src="<%=basePath%>scripts/lawCase/common.js" ></script>
		<script type="text/javascript" src="<%=basePath%>scripts/lawCase/Popup.js" ></script>
		<script type="text/javascript" src="<%=basePath%>scripts/lawCase/inputSelect.js" ></script>
		<script type="text/javascript" src="<%=basePath%>scripts/lawCase/page.js" ></script>
		<script type="text/javascript" src="scripts/customJs.js"></script>
		<script type="text/javascript" src="scripts/ajaxsetup.js"></script>
	</head>
	<body>
		<div class="container">
			<nav class="navbar" role="navigation">
				<div class="navbar-header">
      				<a class="navbar-brand" href="#"><img src="images/lawCase/DataSmart-logo.png"/></a>
      				<span class="dataSpan"><em>|</em>案件管理</span>
   				</div>
<!--    				<div>
      				<p class="navbar-text navbar-right">
      					<img src="images/lawCase/personIcon.png"/>
         				<a href="#" class="navbar-link">返回</a>
      				</p>
   				</div> -->
			</nav>
			<div class="mainCon clearfix">
				<div class="leftCon pull-left">
					<div class="leftInner1">
						<div class="leftInner2">
							<ul class="list-unstyled">
								<li class="setOn"><a href="case/viewCaseMange.action" target="text">案件管理</a></li>
								<li class="zz"><a href="lawCaseOrg/getOrgManagerPage.action?searchType=orgCard" target="text">组织管理</a></li>
								<li class="ry"><a href="peoplemanage/peopleMangeIndex.action" target="text">人员管理</a></li>
								<li class="wj"><a href="filemanage/fileMangeIndex.action" target="text">文件管理</a></li>
								<li class="zj"><a href="hostManage/info.action" target="text">主机管理</a></li>

								<!--<li class="gz"><a href="javascript:void(0)">工作配置</a></li>-->
							</ul>
						</div>
					</div>								
				</div>
				<div class="rightCon pull-right">
					<iframe src="case/viewCaseMange.action" id="rightIframe" name="text" frameborder="0" width="100%" height="100%"></iframe>
				</div>
			</div>
			<!--点击编辑出现的弹出层-->
			<div class="tccBox">
				<div class="bg"></div>
				<div class="addBox">
					<div class="addConBox">
						<h3>添加案件</h3>
						<b></b>
						<iframe src="case/viewCaseAdd.action"  id="pop_iframe" name="fc" frameborder="0" width="100%" height="95%"></iframe>						
					</div>
				</div>
			</div>
		</div>
		<div class='hintMsg'></div>
		<div id="fullbg"></div> 
		<div id="exportingDiv">
			<div  class="dataLoad" >
			     <p>正在导出数据...</p><br/>
			     <img alt="Loading" src="<%=basePath%>images/img/4.gif">
			     <iframe frameborder="0" width="0" height="0" id="downloadFrame" src=""></iframe>
			</div>
		</div>
		<script type="text/javascript">
			function parentPop(data) {
				$(".tccBox").show();
				$(".tccBox h3").text(data.title);
				$("#pop_iframe").attr("src", data.url);
			}
			/*关闭弹出层*/
			function dismissParentPop() {
				$(".tccBox").hide();
				$("#pop_iframe").attr("src", "");
			}
			$(function(){
				$("#exportingDiv").dialog({
		              dialogClass: 'noTitleStuff' ,
		              hide:true,
		              autoOpen:false,	   
		              width:200,
		              height:170,
		              resizable : false,
		              modal:false,
		              title:"",
		              draggable:false,
		              bgiframe: true,
		              overlay: {opacity: 1,overflow:'auto',backgroundColor: '#fff',},
		              open: function() {
	                                     var win = $(window);
	                                     $(this).parent().css({   position:'absolute',
	                                            left: (win.width() - $(this).parent().outerWidth())/2,
	                                            top: (win.height() - $(this).parent().outerHeight())/2
	                                     });
	                                     var bh = $("body").height(); 
	                                     var bw = $("body").width(); 
	                                     $("#fullbg").css({ height:bh, width:bw, display:"block"  }); 
	                             }
		             });
			});
			function openExportDiv() {
				$("#exportingDiv").dialog("open");
			}
			function closeExportDiv(url) {
			    $("#downloadFrame").attr("src",url);
			    $("#exportingDiv").dialog("close");
				$("#fullbg").removeAttr('style');
			}
		</script>
	</body>
</html>
