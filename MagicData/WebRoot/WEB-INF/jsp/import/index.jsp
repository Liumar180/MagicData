<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>数据导入</title>
    <base href="<%=basePath %>">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta name="renderer" content="webkit">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	 <!-- CSS Bootstrap & Custom -->
    <link href="styles/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="styles/css/font-awesome.min.css" rel="stylesheet" type="text/css">  
    <link href="styles/flexslider.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="styles/css/templatemo_style.css" rel="stylesheet" type="text/css">
    <link href="styles/css/base.css" rel="stylesheet" type="text/css">
    
    <link href="styles/css/style.css" rel="stylesheet" type="text/css">
    <link href="styles/css/customCss.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/styles/css/menuStyle.css" />
    <!--9月15日添加的样式  -->
    <link href="styles/css/themes/default/easyui.css" rel="stylesheet" type="text/css">
    <link href="styles/css/themes/icon.css" rel="stylesheet" type="text/css">
    <link href="styles/css/demo.css" rel="stylesheet" type="text/css">
    <link href="styles/css/tabEmail.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" type="text/css" href="styles/import/css/common.css"/>
    <link href="styles/css/import.css" rel="stylesheet" type="text/css">
    
    <!--9月15日添加的样式结束  -->
    <script src="scripts/modernizr.js"></script>
    <script type="text/javascript" src="<%=basePath%>/scripts/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.wheelmenu.js"></script>
	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.subWheelmenu.js"></script>
	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.toolWheelmenu.js"></script>
   <!--9月16日添加样式-开始  -->
    <link href="styles/css/emailDialog/jquery.tagsinput.css" rel="stylesheet" type="text/css">
    <link href="styles/css/emailDialog/jquery-ui.css" rel="stylesheet" type="text/css">
    <link href="styles/css/emailDialog/tccEmail.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="scripts/laydate-v1.1/laydate/laydate.js"></script>
	<script type="text/javascript" src="scripts/grid.locale-cn.js"></script>
	<script type="text/javascript" src="scripts/jquery.jqGrid.min.js"></script>
    
    <!--9月16日添加样式-结束  -->
    <!-- add by hanxue start 数据导出 -->
    <link rel="stylesheet" type="text/css" href="styles/css/indexImportDialag.css" />
    <!-- add by hanxue end -->
    <style type="text/css">
		html{height:100%}
		body{height:100%;margin:0;padding:0;font-size:15px;}
		.div-relative{position:relative; height:100%; width:100%} 
		.div-a{ position:absolute; right:100px; bottom:30px;width:40px; height:130px} 
		.olControlButtonItemActive {
         position: absolute;
         cursor: pointer; 
         top: 5px;
         right: 0px;
         background-image: url("images/map/back.png");
         width: 18px;
         height: 18px;
         -moz-user-select:none;  
           }
        .olControlPanel {
	        width: 25px;
	        height: 25px;
	        top: 5px;
	        right: 0px;
          }
    </style>
  </head>
 <body onselectstart="return false">
 	<div class="mask" id="mask">
 		<div class="maskDetail"></div>
 	</div>
    <div id="box_addModel" class="box" style="display: none;">
		<div class="blackBg"></div>
  		<div class="box1" style="min-width: 666px;min-height: 500px;"></div>
	</div>
	
	<div id="box_addEdge" class="box" style="display: none;">
		<div class="blackBg"></div>
  		<div class="box1" style="min-width: 666px;min-height: 500px;"></div>
	</div>
	
	<div id="selectDBSource" style="display:none;">
		<div class="blackBg"></div>
  		<div class="box1" style="min-width: 666px;min-height: 500px;"></div>
	</div>
	
	<div id="selectDBTables" style="display:none;">
		<div class="blackBg"></div>
  		<div class="box1" style="min-width: 666px;min-height: 500px;"></div>
	</div>
	
	<div id="box_addDBSource" class="box" style="display: none;">
		<div class="blackBg"></div>
  		<div class="box1" style="min-width: 666px;min-height: 500px;">
  			<h1>添加DB</h1>
  			<div class="DBSourceDetail">
  				<div class="DBSourceList DBSourceName">
					<div class="item">DB名称：</div>
					<input type="text" class="addDBSourcelName" id="connectionName"/>
			    </div>
			    <div class="DBSourceList">
					<div class="item">DB参数&nbsp;&nbsp;&nbsp;&nbsp;</div>
					<div class="item" style="width: 200px;">参数值</div>
			    </div>
			    <div style="height: 1px;width: 100%;background: black;float: left;margin: 3px 0;"></div>
			    <div class="DBSourceParam" style="height: 80%;overflow-y: auto;overflow-x: hidden;float: left;">
	  				<div class="DBSourceList type">
						<div class="item">数据库类型：</div>
						<select id="connectionDB" style="" onchange="ImportText(this,'')">
							<option value="MYSQL" selected="">MySQL</option>
							<option value="ORACLE">Oracle</option>
							<option value="MS SQL">MS SQL</option>
							<option value="DB2">DB2</option>
							<option value="AS/400">AS/400</option>
							<option value="SYBASE">Sybase</option>
							<option value="POSTGRESQL">Postgresql</option>
							<option value="DERBY">Derby</option>
							<option value="FIREBIRD">Firebird</option>
							<option value="GREENPLUM">Greenplum</option>
							<option value="H2">H2</option>
							<option value="HadoopHive">HadoopHive</option>
							<option value="SQLITE">Sqlite</option>
						</select>
				    </div>
			    </div>
  			</div>
			<div>
				<input type="button" class="xgBtn" onclick="addDBSourceDoSubmit(this)" value="测试" action="test"/>
				<input type="button" class="closeBtn" onclick="addDBSourceCloseWin()" value="关闭" />
			</div>
		</div>
	</div>
	
	<div id="selectModel" class="box" style="display: none;">
  		<div class="blackBg"></div>
  		<div class="box1" style="min-width: 666px;min-height: 500px;">
  			<h1>选择模型</h1>
  			<div class="selectModelDetail">
  				<div class="selectModelList"></div>
			    <div class="" style="width: 254px;margin-left: 20px;">
			    	<span>当前选择模型的属性信息：</span>
			    </div>
			    <div class="selectedModelProperty"></div>
  			</div>
			<div></div>
		</div>
	</div>
	
	<div id="selectEdges" class="box" style="display: none;">
  		<div class="blackBg"></div>
  		<div class="box1" style="min-width: 666px;min-height: 500px;">
  			<h1>选择线型</h1>
  			<div class="selectEdgesDetail">
  				<div class="selectEdgesList"></div>
			    <div class="" style="width: 254px;margin-left: 20px;">
			    	<span>当前选择线型的属性信息：</span>
			    </div>
			    <div class="selectedEdgesProperty"></div>
  			</div>
			<div></div>
		</div>
	</div>
	
	<div class='hintMsg'></div>
	<div id="menuDiv" style="visibility: hidden;position:absolute;z-index:2"></div>
	<div class="icontainer clearfix" >
		<div class='top' style="overflow-x: auto;">
			<table style="height: 100%;">
				 <tr>
				 	<td title="添加数据源"><div class="DBTable">+</div></td>
				 </tr>
			</table>
		</div>
		<div class='middle'>
			<table id="sampleData"></table>
		</div>
		<div class='down'>
			<div class='down_left clearfix'></div>
			<div class='down_middle clearfix'>
				<div class='displayDiv' id="svgImg"></div>
				<div class='zoomline' style='width:30px; height:150px;position: absolute; top: 36%;left: 16%;'></div>
				<div class="" id="" style="line-height: 5%;height: 5%;margin-left: 10px;width: 160px;color: white;">DB和模型对应关系：</div>
				<div class="map" id="mapping" style="height: 49%;overflow-y: auto;"></div>
			</div>
			<div class='down_right clearfix'>
				<div id="previewSample" style="float: left;line-height: 45px;height: 45px;width: 100px;background-color: rgba(20,20,20,0.7);color: white;">预览事例数据</div>
				<div id="saveAndDoTask" style="float: left;line-height: 45px;height: 45px;width: 100px;background-color: rgba(20,20,20,0.7);color: white;">保存并执行</div>
				<div id="justSaveTask" style="line-height: 45px;height: 45px;width: 100px;background-color: rgba(20,20,20,0.7);color: white;">保存</div>
			</div>
		</div>
	</div>
  <!-- JavaScripts -->
  <!--9月15日添加的js  -->
	<script type="text/javascript" src="scripts/jquery.easyui.min.js"></script>
    <!--9月15日添加的js  -->
  <script type="text/javascript" src='scripts/d3.v3.js' ></script> 
  <script type="text/javascript" src='scripts/d3-tip.js' ></script>
  <script type="text/javascript" src="scripts/customJs.js"></script>
  <script type="text/javascript" src="scripts/jquery.wheelmenu.js"></script>
    <!--11月11日添加的js--开始  -->
  <script type="text/javascript" src="scripts/index/qq.js"></script>
  <!--11月11日添加的js--结束  -->
  <script type="text/javascript" src="scripts/graph_tabsHandler_import.js"></script>
  <script src="styles/bootstrap/js/bootstrap.min.js"></script>
  <script src="styles/bootstrap/js/bootstrap-prompts-alert.js"></script>
  <script src="scripts/jquery.singlePageNav.js"></script>
<!--   <script src="scripts/templatemo_custom.js"></script> -->
  <script defer src="scripts/jquery.flexslider.js"></script>
  <script type="text/javascript" src="scripts/tab.js"></script>
  <script type="text/javascript" src="scripts/OpenLayers.js"></script> 
  <!-- /*点击邮件出现的弹出层*/ -->
  <!--9月16日添加的js--开始  -->
  <script type="text/javascript" src="scripts/emailDialog/jquery.tagsinput.js"></script>
	<!-- modify by hanxue 数据导出start -->
	<script type="text/javascript" src="scripts/ui/jquery-ui.min.js"></script>
	<!-- <script type="text/javascript" src="scripts/emailDialog/jquery-ui.min.js"></script> -->
	<!-- modify by hanxue 数据导出end -->
  <script type="text/javascript" src="scripts/emailDialog/szzlCommPlug.js"></script>
  <script type="text/javascript" src="scripts/emailDialog/easing.js"></script>
  <!--9月16日添加的js--结束  -->
  <script type="text/javascript" src="scripts/email.js"></script>
	<script type="text/javascript" src="scripts/emailDialog/easing.js"></script>
  <script type="text/javascript" src="scripts/emailDialog/imgScroll.js"></script>
  <!--9月21日添加的js--开始  -->
  <script type="text/javascript" src="scripts/index/advanceSearch.js"></script>
  <script type="text/javascript" src="scripts/index/search.js"></script>
  <script type="text/javascript" src="styles/zTree/js/jquery.ztree.core-3.5.js"></script>
  <script type="text/javascript" src="styles/zTree/js/jquery.ztree.excheck-3.5.js"></script>
  <link href="styles/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css">
  <!-- jqgrid -->
  <link rel="stylesheet" type="text/css" href="styles/css/ui.jqgrid.css">
  <link rel="stylesheet" type="text/css" href="styles/css/jquery-ui.min.css">
  <link rel="stylesheet" type="text/css" href="styles/css/customCss.css">
		<script type="text/javascript" src="scripts/lawCase/common.js" ></script>
  <!--9月21日添加的js--结束  -->
  <!--add by hanxue 数据导出start-->
	<script type="text/javascript" src="scripts/index/saveSvgAsPng.js"></script>
	<!--add by hanxue 数据导出end-->
	<!--16年1月14日添加历史记录js-->
	<script type="text/javascript" src="scripts/history.js"></script> 
	<script type="text/javascript">
		function ImportText(obj, dataJSON){
			var databasename = "";
			if(undefined == dataJSON.connectionDB || "" == dataJSON.connectionDB){
				databasename = $(obj).children('option:selected').val();
				dataJSON = {
					"connectionServierName": "",
					"connectionPort": "",
					"connectionDBUserName": "",
					"connectionDBPassword": "",
					"connectionDBTableName": "",
					"connectionDB": "",
					"connectionName": "",
					"connectionDBPasswordBak": ""
				};
			}else{
				databasename = dataJSON.connectionDB;
			}
			$(".DBSourceParam .param").remove();
			$("#connectionName").val(dataJSON.connectionName);
			$("#connectionDB").find("option[value='"+dataJSON.connectionDB.toUpperCase()+"']").attr("selected",true);
			$(".DBSourceParam").append('<div class="DBSourceList param">' +
											'<div class="item">主机名称：</div>' +
											'<input type="text" class="addDBSourceParam" value="'+dataJSON.connectionServierName+'" id="connectionServierName" placeholder="192.168.1.1"/>' +
										'</div>' +
										'<div class="DBSourceList param">' +
											'<div class="item">数据库名称：</div>' +
											'<input type="text" class="addDBSourceParam" value="'+dataJSON.connectionDBTableName+'" id="connectionDBTableName" placeholder="magicdata"/>' +
										'</div>');
			if(databasename == "MYSQL"||databasename == "DERBY"||databasename == "ORACLE"||databasename == "HadoopHive"||databasename == "H2"||databasename == "DB2"||databasename == "MSSQL"||databasename == "SYBASE"||databasename == "POSTGRESQL"||databasename == "GREENPLUM"||databasename == "FIREBIRD"){
				var port = dataJSON.connectionPort;
				if(databasename == "MYSQL"){port = 3306}else if(databasename == "ORACLE"){port = 1521}else if(databasename == "HadoopHive"){port = 9000}else if(databasename == "MSSQL"){port = 1433}else if(databasename == "H2"){port = 8082}else if(databasename == "DB2"){port = 50000}else if(databasename == "SYBASE"){port = 5001}else if(databasename == "POSTGRESQL"||databasename == "GREENPLUM"){port = 5432}else if(databasename == "FIREBIRD"){port = 3050}else if(databasename == "DERBY"){port = 1527}
				var portObj = $('<div class="DBSourceList param">' +
									'<div class="item">端口号：</div>' +
								'</div>');
				var portInputObj = $('<input type="text" class="addDBSourceParam" value="' + port + '" id="connectionPort" placeholder="'+port+'"/>');
				portInputObj.keyup(function(){
					var _this = this;
					var port = $(_this).val();
					$(_this).val(port.replace(/\D/g,''));
					if(port > 65535){
						$(_this).val("");
						hintManager.showHint("端口号不能超过65535");
					}
				});
				portObj.append(portInputObj);
				$(".DBSourceParam").append(portObj);
				var userInfo = $('<div class="DBSourceList param">' +
									'<div class="item">用户名：</div>' +
									'<input type="text" class="addDBSourceParam" value="'+dataJSON.connectionDBUserName+'" id="connectionDBUserName" placeholder="admin"/>' +
								'</div>' +
								'<div class="DBSourceList param">' +
									'<div class="item">密码：</div>' +
									'<input type="password" class="addDBSourceParam" value="'+dataJSON.connectionDBPasswordBak+'" id="connectionDBPassword"/>' +
								'</div>');
				$(".DBSourceParam").append(userInfo);
			}else if(databasename == "AS/400"){//no Port
				var userInfo = $('<div class="DBSourceList param">' +
									'<div class="item">用户名：</div>' +
									'<input type="text" class="addDBSourceParam" id="connectionDBUserName" placeholder="admin"/>' +
								'</div>' +
								'<div class="DBSourceList param">' +
									'<div class="item">密码：</div>' +
									'<input type="password" class="addDBSourceParam" id="connectionDBPassword"/>' +
								'</div>');
				$(".DBSourceParam").append(userInfo);
			}else if(databasename == "SQLITE"){// no 数据库名称 port & username & passport
			}
		}
		function randomString(len) {
			len = len || 32;
			var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
			var maxPos = chars.length;
			var pwd = '';
			for (i = 0; i < len; i++) {
				pwd += chars.charAt(Math.floor(Math.random() * maxPos));
			}
			return pwd;
		}
		
		$(document).click(function(event){
			var eo=$(event.target);
			if($(".select_box").is(":visible") && eo.attr("class")!="option" && !eo.parent(".option").length)
			$('.option').hide();
		});
		
		var GraphJSON = {
			"vertices" : [],
			"edges" : [],
			"database" : [{
				"tableList": [
          			{
                		"id": 1,
                		"sql" : "",
                		"columnPropertyList" : [],
                		"edgeList": [],
						"vertexList": []
               		}
             	]
			}]
		};
		
		function initDBSource(){
			maskManager.show();
			var selectDBS = "";
			$("#selectDBSource .box1").empty();
			$.ajax({
				url:pageVariate.base+"/ajax_dataImport/getDataUrl.action",
				type: "post",
				dataType: "json",
				success: function(data){
					maskManager.hide();
					$("#selectDBSource .box1").append('<h1>选择数据源</h1>');
					$("#selectDBSource .box1").append('<div class="selectDBSDetail">' +
															'<div class="selectDBSList"></div>' +
															'<div class="" style="width: 254px;margin-left: 20px;">' +
																'<span>当前选择模型的属性信息：</span>' +
															'</div>' +
															'<div class="selectedDBSProperty"></div>' +
														'</div>');
					$("#selectDBSource .box1").append('<div></div>');
					for(var i in data.dataBaseJson){
						var item = $('<div class="item" title="' + data.dataBaseJson[i].connectionName + '" extend="'+ i +'" clicked="false">' +
										'<img src="images/img/'+i+'.png" style="width: 100px;height: 100px;margin: 0 5px;" onerror="javascript:this.src=\'images/img/default.png\'">' +
										'<span extend="'+i+'">' + data.dataBaseJson[i].connectionName + '</span>' +
									'</div>');
						item.click(function(){
							var _this = this;
							$(".selectedDBSProperty").empty();
							$(_this).parent().children().css("border", "solid 1px rgba(0, 0, 0, 0)");
							$(_this).css("border", "solid 1px red");
							$(_this).parent().children().attr("clicked", "false");
							$(_this).attr("clicked", "true");
							var id = $(_this).attr("extend");
							selectDBS = id;
							for(var j in data.dataBaseJson[selectDBS]){
								var proType = data.dataBaseJson[selectDBS][j];
								$(".selectedDBSProperty").append('<div class="item" style="width: 480px;line-height: 40px;">' +
																	'<div><span>'+j+'</span>：<span>'+proType+'</span></div>' +
																'</div>');
							}
						});
						item.hover(function(){
							var _this = this;
							$(_this).css("border", "solid 1px red");
							var modifyObj = $('<div class="function" style="position: absolute;left: 7px;bottom: 20px;">修改</div>');
							modifyObj.click(function(){
								var id = $(_this).attr("extend");
								selectDBS = id;
								$("#selectDBSource").hide();
								ImportText("", data.dataBaseJson[selectDBS]);
								$("#box_addDBSource").show();
							});
							$(_this).append(modifyObj);
							var deleteObj = $('<div class="function" style="position: absolute;right: 7px;bottom: 20px;">删除</div>');
							deleteObj.click(function(){
								var id = $(_this).attr("extend");
								selectDBS = id;
								var DBName = $(_this).attr("title");
								confirm("删除此数据库，将会关联此数据库未执行的任务一并删除，是否确认删除："+DBName,function(){
									console.log(data.dataBaseJson[selectDBS]);
									$.ajax({
										type : "post",
										url : pageVariate.base + "/ImportDelete.action",
										data : data.dataBaseJson[selectDBS],
										success : function(taskID){
											hintManager.showSuccessHint("删除DB配置成功!");
											$(_this).remove();
										}
									});
								});
							});
							$(_this).append(deleteObj);
						},
						function(){
							var _this = this;
							var clicked = $(_this).attr("clicked");
							if("false" == clicked){
								$(_this).css("border", "solid 1px rgba(0, 0, 0, 0)");
							}
							$(_this).find(".function").remove();
						});
						$(".selectDBSList").append(item);
					}
					
					if(GraphJSON.database[0].connectionDB == undefined || GraphJSON.database[0].connectionDB == ""){
					}else{
						var selectedDBS = $(".selectDBSList").find(".item[extend='"+GraphJSON.database[0].id+"']");
						$(".selectedDBSProperty").empty();
						selectedDBS.css("border", "solid 1px red");
						selectedDBS.attr("clicked", "true");
						var id = selectedDBS.attr("extend");
						selectDBS = id;
						for(var j in data.dataBaseJson[selectDBS]){
							var proType = data.dataBaseJson[selectDBS][j];
							$(".selectedDBSProperty").append('<div class="item" style="width: 480px;line-height: 40px;">' +
																'<div><span>'+j+'</span>：<span>'+proType+'</span></div>' +
															'</div>');
						}
					}
					
					var addNewDBSource = $('<div class="item">' +
									'<img src="images/img/Person.png" style="width: 100px;height: 100px;margin: 0 5px;" onerror="javascript:this.src=\'images/img/default.png\'">' +
									'<span>添加新DB</span>' +
								'</div>');
					addNewDBSource.click(function(){
						$("#selectDBSource").hide();
						ImportText("#connectionDB", "");
						$("#box_addDBSource").show();
					});
					addNewDBSource.hover(function(){
						var _this = this;
						$(_this).css("border", "solid 1px red");
					},
					function(){
						var _this = this;
						$(_this).parent().children().css("border", "solid 1px rgba(0, 0, 0, 0)");
					});
					$(".selectDBSList").append(addNewDBSource);
					
					var suerBtn = $('<input type="button" class="xgBtn" value="确定" />');
					suerBtn.click(function(){
						$("#selectDBSource").hide();
						maskManager.show();
						var _this = this;
						var tableDataDetail = "";
						if(selectDBS == ""){
							maskManager.hide();
							alert("请选择DB!!");
						}else{
							for(var dbs in data.dataBaseJson[selectDBS]){
								GraphJSON.database[0][dbs] = data.dataBaseJson[selectDBS][dbs];
							}
							$.ajax({
								type:"post",
								url:pageVariate.base+"/dataManage/findAllTables.action",
								data:{"dbConnId":selectDBS},
								success:function(tableData){
									maskManager.hide();
									tableDataDetail = tableData;
									$("#selectDBTables").show();
									$("#selectDBTables .box1").empty();
									var DBTablesTitle = $('<h1><div style="width: 81%;float:left;">请选择数据源</div></h1>');
									var DBTablesSel = $('<div>SQL语句</div>');
									DBTablesTitle.append(DBTablesSel);
									$("#selectDBTables .box1").append(DBTablesTitle);
									DBTablesSel.click(function(){
										var _this = this;
										var action = DBTablesSel.html(); 
										if(action == "SQL语句"){
											$(_this).html("表结构");
											$("#selectDBTables .box1 .selectTablesDetail").empty();
											$("#selectDBTables .box1 .selectTablesDetail").append('<div class="selectTablesList"></div>');
											
											for(var i in tableDataDetail){
												var item = $('<div class="item" title="' + i + '">' +
																'<img src="images/img/'+i+'.png" style="width: 100px;height: 100px;margin: 0 5px;" onerror="javascript:this.src=\'images/img/default.png\'">' +
																'<span extend="'+i+'">' + i + '</span>' +
															'</div>');
												item.click(function(){
													var _this = this;
													var id = $(_this).find("span").attr("extend");
													console.log(tableDataDetail[id]);
												});
												$("#selectDBTables .box1 .selectTablesDetail .selectTablesList").append(item);
											}
											
										}else if(action == "表结构"){
											$(_this).html("SQL语句");
											$("#selectDBTables .box1 .selectTablesDetail").empty();
											$("#selectDBTables .box1 .selectTablesDetail").append('<div style="margin-left: 50px;float: left;margin-bottom: 15px;">请在此处输入SQL语句</div>');
											$("#selectDBTables .box1 .selectTablesDetail").append('<input type="text" value="test"/>');
											$("#selectDBTables .box1 .selectTablesDetail").append('<textarea style="width: 90%;height: 60%;resize: none;" placeholder="请在此处输入SQL语句..." autofocus>'+GraphJSON.database[0].tableList[0].sql+'</textarea>');
											var div = $('<div style="height: 25%;overflow-y: auto;"></div>');
											$("#selectDBTables .box1 .selectTablesDetail").append(div);
											for(var i in tableDataDetail){
												div.append('<span style="margin-left:10px;float: left;width: 45%;border-right: solid 1px black;">'+i+';</span>');
											}
										}
									});
									
									$("#selectDBTables .box1").append('<div class="selectTablesDetail"></div>');
									$("#selectDBTables .box1 .selectTablesDetail").append('<div style="margin-left: 50px;float: left;margin-bottom: 15px;">请在此处输入SQL语句</div>');
									$("#selectDBTables .box1 .selectTablesDetail").append('<input type="text" value="test"/>');
									$("#selectDBTables .box1 .selectTablesDetail").append('<textarea style="width: 90%;height: 60%;resize: none;" placeholder="请在此处输入SQL语句..." autofocus>'+GraphJSON.database[0].tableList[0].sql+'</textarea>');
									var div = $('<div style="height: 25%;overflow-y: auto;"></div>');
									$("#selectDBTables .box1 .selectTablesDetail").append(div);
									for(var i in tableDataDetail){
										div.append('<span style="margin-left:10px;float: left;width: 45%;border-right: solid 1px black;">'+i+';</span>');
									}
									$("#selectDBTables .box1").append('<div></div>');
									var DBTablesSuerBtn = $('<input type="button" class="xgBtn" value="确定" />');
									DBTablesSuerBtn.click(function(){
										var _this = this;
										var sql = $("#selectDBTables .box1 .selectTablesDetail textarea").val().toLowerCase();
										//TODO 需要的判断select from等字段是否重复
										var commaArray = sql.split(" from ")[0].split(",");
										var columnPropertyList = "";
										for(var column = 0; column < commaArray.length; column ++){
											var spaceArray = commaArray[column].replace(/select/g, "").split(" ");
											for(var space = 0; space < spaceArray.length; space ++){
												if("" != spaceArray[space]){
													columnPropertyList += (spaceArray[space]+",");
													break;
												}
											}
										}
										columnPropertyList = columnPropertyList.substr(0, columnPropertyList.length-1);
										
										$(".icontainer .top table tr").empty();
										var showName = $("#selectDBTables .box1 .selectTablesDetail input").val();
										var tdTable = $('<td style="position: relative;"><div class="DBTable">' + showName + '</div></td>');
										GraphJSON.database[0].tableList[0].sqlIdentify = showName;
										GraphJSON.database[0].tableList[0].sql = sql;
										tdTable.click(function(){
											var _this = this;
											$(".down_left.clearfix").empty();
											$("#mapping").empty();
											setSampleData(columnPropertyList);
										});
										tdTable.hover(function(){
											var _this = this;
											var modifyObj = $('<div class="function" style="position: absolute;left: 7px;bottom: 20px;">修改</div>');
											modifyObj.click(function(){
												initDBSource();
											});
											$(_this).append(modifyObj);
											var deleteObj = $('<div class="function" style="position: absolute;right: 7px;bottom: 20px;">删除</div>');
											deleteObj.click(function(){
												var DBName = $(_this).find(".DBTable").html();
												confirm("删除该数据源，将会删除该任务，是否确认删除：" + DBName,function(){
													/* $.ajax({
														type : "post",
														url : pageVariate.base + "/ajax_dataImport/deleteTaskById.action",
														data : {
															taskId : taskId
														},
														success : function(taskID){
															hintManager.showSuccessHint("删除任务成功!");
															$(_this).remove();
														}
													}); */
												});
											});
											$(_this).append(deleteObj);
										},function(){
											var _this = this;
											$(_this).find(".function").remove();
										});
										$(".icontainer .top table tr").append(tdTable);
										
										$("#selectDBTables").hide();
									});
									$(".selectTablesDetail").next().append(DBTablesSuerBtn);
									var gobackBtn = $('<input type="button" class="gobackBtn" value="返回" />');
									gobackBtn.click(function(){
										var _this = this;
										$("#selectDBTables").hide();
										$("#selectDBSource").show();
									});
									$(".selectTablesDetail").next().append(gobackBtn);
									var DBTablesCancelBtn = $('<input type="button" class="closeBtn" value="关闭" />');
									DBTablesCancelBtn.click(function(){
										var _this = this;
										$("#selectDBTables").hide();
									});
									$(".selectTablesDetail").next().append(DBTablesCancelBtn);
								}
							});
						}
					});
					$(".selectDBSDetail").next().append(suerBtn);
					var cancelBtn = $('<input type="button" class="closeBtn" value="关闭" />');
					cancelBtn.click(function(){
						var _this = this;
						$("#selectDBSource").hide();
					});
					$(".selectDBSDetail").next().append(cancelBtn);
					$("#selectDBSource").show();
				}
			});
		}
		
		$(function(){
			ImportText("#connectionDB", "");
			$(".DBTable").click(function(){
				initDBSource();
			});
			
			if("" != window.location.search){
				var search = window.location.search;
				var taskID = search.split("=")[1];
				$.ajax({
					type : "post",
					url : pageVariate.base+"/ajax_dataImport/editTask.action",
					data : {
						taskId : taskID
					},
					success : function(taskValue){
						GraphJSON = JSON.parse(taskValue.xmlPath);
						var InitData = {
							nodes : nodes,
							edges : edges
						}
						graphDrawInit(InitData);
						var vertexList = new Array();
						for(var v = 0; v < GraphJSON.database[0].tableList[0].vertexList.length; v ++){
							var json = {};
							for(var i in GraphJSON.database[0].tableList[0].vertexList[v]){
								json[i] = GraphJSON.database[0].tableList[0].vertexList[v][i];
							}
							vertexList.push(json);
						}
						var edgeList = new Array();
						for(var e = 0; e < GraphJSON.database[0].tableList[0].edgeList.length; e ++){
							var json = {};
							for(var i in GraphJSON.database[0].tableList[0].edgeList[e]){
								json[i] = GraphJSON.database[0].tableList[0].edgeList[e][i];
							}
							edgeList.push(json);
						}
						expandNode(vertexList, edgeList);
						
						var sql = GraphJSON.database[0].tableList[0].sql
						var commaArray = sql.split(" from ")[0].split(",");
						var columnPropertyList = "";
						for(var column = 0; column < commaArray.length; column ++){
							var spaceArray = commaArray[column].replace(/select/g, "").split(" ");
							for(var space = 0; space < spaceArray.length; space ++){
								if("" != spaceArray[space]){
									columnPropertyList += (spaceArray[space]+",");
									break;
								}
							}
						}
						columnPropertyList = columnPropertyList.substr(0, columnPropertyList.length-1);
						$(".down_left.clearfix").empty();
						$("#mapping").empty();
						setSampleData(columnPropertyList);
						
						
						$(".icontainer .top table tr").empty();
						var sqlIdentify = GraphJSON.database[0].tableList[0].sqlIdentify;
						if(!sqlIdentify)
							sqlIdentify = GraphJSON.database[0].connectionName;
						var tdTable = $('<td style="position: relative;"><div class="DBTable">' + sqlIdentify + '</div></td>');
						tdTable.click(function(){
							var _this = this;
							$(".down_left.clearfix").empty();
							$("#mapping").empty();
							setSampleData(columnPropertyList);
						});
						tdTable.hover(function(){
							var _this = this;
							var modifyObj = $('<div class="function" style="position: absolute;left: 7px;bottom: 20px;">修改</div>');
							modifyObj.click(function(){
								initDBSource();
							});
							$(_this).append(modifyObj);
							var deleteObj = $('<div class="function" style="position: absolute;right: 7px;bottom: 20px;">删除</div>');
							deleteObj.click(function(){
								var DBName = $(_this).find(".DBTable").html();
								confirm("删除该数据源，将会删除该任务，是否确认删除：" + DBName,function(){
									/* $.ajax({
										type : "post",
										url : pageVariate.base + "/ajax_dataImport/deleteTaskById.action",
										data : {
											taskId : taskId
										},
										success : function(taskID){
											hintManager.showSuccessHint("删除任务成功!");
											$(_this).remove();
										}
									}); */
								});
							});
							$(_this).append(deleteObj);
						},function(){
							var _this = this;
							$(_this).find(".function").remove();
						});
						$(".icontainer .top table tr").append(tdTable);
					}
				});
			}
			
			function findStrongPro(pro, proList){
				for(var i = 0; i < proList.length; i ++){
					if(proList[i].value == pro){
						return proList[i].strong;
					}
				}
				return false;
			}
			
			function joinInGraphJSON(){
				/* var itemList = $("#mapping .item");
				var columnPropertyList = GraphJSON.database[0].tableList[0].columnPropertyList;
				for(var j = 0; j < columnPropertyList.length; j ++){
					columnPropertyList[j].columnPropertyList = [];
					for(var i = 0; i < itemList.length; i ++){
						var txt = $(itemList[i]).find(".select_box .select_txt");
						var idDetail = $(itemList[i]).find(".id").html();
						var name = txt.attr("name");
						var extend = txt.attr("extend");
						if(columnPropertyList[j].name == idDetail){
							var node = findNode(extend);
							columnPropertyList[j].columnPropertyList.push({ 
									"strongProperty": findStrongPro(name, node.property), 
	                                "labelType": "vertex", 
	                                "label": node.uuid, 
	                                "propertyType": "string", 
	                                "property": name
	                            });
	                       	node.strongProperty = {
	                       			"vertexPropertyName" : node.uuid,
	                       			"vertexPropertyNameSqlDatabase": columnPropertyList[j].name,
	                                "vertexPropertyTypeSqlDatabase": columnPropertyList[j].type,
	                                "propertyType": "string",
	                                "property": name
	                       		}
						}
					}
				} */
				/* for(var x = 0; x < edges.length; x ++){
					var edgeList = GraphJSON.database[0].tableList[0].edgeList;
					for(var y = 0; y < edgeList.length; y ++){
						if(edges[x].uuid == edgeList[y].edgeLabelName){
							edgeList[y].custormPropertyList.push({
		                          "inEdgeProperty": edges[x].source.strongProperty,
		                          "outEdgeProperty": edges[x].target.strongProperty
		                    });
						}
					}
				} */
			}
			
			$("#justSaveTask").click(function(){
				joinInGraphJSON();
				$.ajax({
					type : "post",
					url : pageVariate.base+"/ajax_dataImport/saveTask.action",
					data : {
						taskName : randomString(5),
						taskType : 1,
						jsonStr : JSON.stringify(GraphJSON)
					},
					success : function(taskID){
						hintManager.showSuccessHint("保存任务成功!");
					}
				});
			});
			
			$("#saveAndDoTask").click(function(){
				joinInGraphJSON();
				$.ajax({
					type : "post",
					url : pageVariate.base+"/ajax_dataImport/saveTask.action",
					data : {
						taskName : randomString(5),
						taskType : 1,
						jsonStr : JSON.stringify(GraphJSON)
					},
					success : function(taskID){
						hintManager.showSuccessHint("保存任务成功!");
						$.ajax({
							type : "post",
							url : pageVariate.base+"/ajax_dataImport/startTask.action",
							data : {
								taskID : taskID
							},
							success : function(){
								hintManager.showSuccessHint("执行任务成功!");
							}
						});
					}
				});
			});
			
			function previewData(id){
				var vertexList = GraphJSON.database[0].tableList[0].vertexList;
				for(var v = 0; v < vertexList.length; v ++){
					var contObj = $('<div class="secondCon"></div>');
					$(".down_right.clearfix").append(contObj);
					var headObj = $('<div class="clsHead1 clearfix">' +
										'<h3>'+vertexList[v].vertexTypeName+'</h3>' +
										'<span>' +
											'<img src="images/img/1.png" width="15" height="9" />' +
										'</span>' +
									'</div>');
					headObj.click(function(){
						var _this = this;
						var display = $(_this).next().css("display");
						if(display == "block"){
							$(_this).next().css("display", "none");
						}else if(display == "none"){
							$(_this).next().css("display", "block");
						}
					});
					contObj.append(headObj);
					var propertyObj = $('<div class="clsContent1 clearfix" style="display: none;"></div>');
					contObj.append(propertyObj);
					var custormVertexPropertyList = vertexList[v].custormVertexPropertyList;
					for(var p = 0; p < custormVertexPropertyList.length; p ++){
						var sampleDataID = custormVertexPropertyList[p].databaseColumnId;
						var sampleData = "";
						if(sampleDataID <= -1){
							sampleData = "该属性没有做对应";
						}else{
							sampleData = $($($("#sampleData").find(".ui-widget-content")[id]).find("td")[sampleDataID]).html();
							if(!sampleData){
								sampleData = "该属性没有做对应";
							}
						}
						var propertyDetailObj = $('<div style="float: left;width: 100%;">' +
													'<div style="float: left;width: 45%;">' +
														'<span class="fr" style="font-size: 25px;">'+custormVertexPropertyList[p].propertyName+'</span>' +
													'</div>' +
													'<div style="float: left;width: 5%;font-size: 25px;">' +
														':' +
													'</div>' +
													'<div style="float: left;width: 45%;">' +
														'<span class="fl" style="font-size: 25px;">'+sampleData+'</span>' +
													'</div>' +
												'</div>');
						propertyObj.append(propertyDetailObj);
					}
				}
			}
			
			$("#previewSample").click(function(){
				var id = 0;
				previewData(id);
			});
		});
	
		function setSampleData(columnPropertyList){
			maskManager.show();
			// {"rows":[{"c.casename":"杀人案","p.pocnname":"王五"},{"c.casename":"不知道啥案","p.pocnname":"小黑"}]}
			var colNames = [];
			var colModel = [];
			var columnArray = columnPropertyList.split(",");
			for(var i = 0; i < columnArray.length; i ++){
				colNames.push(columnArray[i]);
				colModel.push({
					"name" : columnArray[i],
					"index" : columnArray[i],
					"width" : "6%",
					"align" : "center"
				});
			}
			
			$(".middle").empty();
			$(".middle").append('<table id="sampleData"></table>');
			
			var tableWidth = $(".middle").width()-12;
			//加载表格
	 		$("#sampleData").jqGrid({
				//url:"dataManage/queryTopFiveRecords.action?tableName="+tableName,//单表获取五条数据
				url:pageVariate.base+"dataManage/queryTopFiveRecordsBySQL.action",
				postData:{"sql":GraphJSON.database[0].tableList[0].sql,"column":columnPropertyList},
				datatype:"json", //数据来源，本地数据
				mtype:"POST",//提交方式
				height:'88%',//高度，表格高度。可为数值、百分比或'auto'
				width:tableWidth,//这个宽度不能为百分比
				colNames:colNames,
				colModel:colModel,
				rownumbers:true,//添加左侧行号
				viewrecords: true,//是否在浏览导航栏显示记录总数
				rowNum:10,//每页显示记录数
				onSelectRow : function(id,status) {//选中单行
					var _this = this;
					$(_this).find("tr").css("background", "rgba(0,0,0,0)");
					$(_this).find("tr[id='"+id+"']").css("background", "#264494");
					previewData(id);
				},
				onSelectAll : function(aRowids,status){//全部对象的选中状态
				},
			    jsonReader: {
		            repeatitems : false
			    },
				gridComplete:function(){
					maskManager.hide();
					setRowHeight("sampleData");
					var columnArray = columnPropertyList.split(",");
					GraphJSON.database[0].tableList[0].columnPropertyList = [];
					for(var c = 0; c < columnArray.length; c ++){
						GraphJSON.database[0].tableList[0].columnPropertyList.push({
													                        "columnName": columnArray[c],
													                        "columntype": "string",
													                        "columnId": c
													                    });
					}
					setcolumn(columnArray);
				}
			});
		}
		
		function setcolumn(columnArray){
			/* var columnMap = $('<div class="item">' +
							'</div>');
			var select_box = $('<div class="select_box" style="margin: 0 auto;">' +
									'<span class="select_txt" name="">请选择属性</span>' +
									'<div class="option">' +
										'<span class="" type="">请选择属性</span>' +
									'</div>' +
								'</div>');
			select_box.click(function(){
				event.stopPropagation();
				$(this).find(".option").toggle();
				$(this).parent().siblings().find(".option").hide();
			}); */
			for(var c = 0; c < columnArray.length; c ++){
				var column = $('<div class="column">'+columnArray[c]+'</div>');
				$(".down_left.clearfix").append(column);
				/* var span = $('<span class="" name="">'+ columnArray[c] +'</span>');
				select_box.find(".option").append(span); */
			}
			
			/* select_box.find(".option span").click(function(){
				var _this = this;
				var value = $(_this).text();
				var name = $(_this).attr('name');
				$(_this).parent().siblings(".select_txt").text(value);
				$(_this).parent().siblings(".select_txt").attr("name", name);
			});
			columnMap.append(select_box);
			var delPropertyMap = $('<div class="" style="float: left;line-height: 32px;width: 50px;cursor: pointer;">X</div>');
			delPropertyMap.click(function(){
				var _this = this;
				$(_this).parent().remove();
			});
			columnMap.append(delPropertyMap);
			$("#mapping").append(columnMap); */
		}
		
		/* function setcolumn(columnID){
			var column = $('<div class="column">'+columnID+'</div>');
			column.click(function(){
				var _this = this;
				var columnName = $(_this).html();
				var columnsInMap = $("#mapping").find(".id");
				for(var i = 0; i < columnsInMap.length; i ++){
					if($(columnsInMap[i]).html() == columnName){
						return false;
					}
				}
				var columnMap = $('<div class="item">' +
									'<div class="id">'+columnName+'</div>' +
								'</div>');
				var select_box = $('<div class="select_box" style="margin: 0 auto;">' +
									'<span class="select_txt" name="">请选择属性</span>' +
							        '<div class="option">' +
							        	'<span class="" type="">请选择属性</span>' +
							        '</div>' +
							    '</div>');
				select_box.click(function(){
					event.stopPropagation();
					$(this).find(".option").toggle();
					$(this).parent().siblings().find(".option").hide();
				});
				if(pageVariate.currentNode != ""){
					var propertyList = pageVariate.currentNode.property;
					for(var i = 0; i < propertyList.length; i ++){
						var span = $('<span class="" extend="'+ pageVariate.currentNode.uuid +'" name="'+propertyList[i].value+'">'+ pageVariate.currentNode.type + "__" +propertyList[i].value+'</span>');
						select_box.find(".option").append(span);
					}
				}
				select_box.find(".option span").click(function(){
					var _this = this;
					var value = $(_this).text();
					var extend = $(_this).attr('extend');
					var name = $(_this).attr('name');
					$(_this).parent().siblings(".select_txt").text(value);
					$(_this).parent().siblings(".select_txt").attr("extend", extend);
					$(_this).parent().siblings(".select_txt").attr("name", name);
				});
				columnMap.append(select_box);
				var delPropertyMap = $('<div class="" style="float: left;line-height: 32px;width: 50px;cursor: pointer;">X</div>');
				delPropertyMap.click(function(){
					var _this = this;
					$(_this).parent().remove();
				});
				columnMap.append(delPropertyMap);
				$("#mapping").append(columnMap);
			});
			$(".down_left.clearfix").append(column);
		} */
		
  		
		/* 设置行高  */
		function setRowHeight(id){
		   var grid = $("#"+id);
		   grid.closest(".ui-jqgrid-bdiv").css({ 'overflow-x' : 'hidden' });
	       var ids = grid.getDataIDs();  
	       for (var i = 0; i < ids.length; i++) {  
	           grid.setRowData ( ids[i], false, {height: 30});  
	       }
		}
		
  		var forceWhich = 1;//add by hanxue用于禁止按住右键时拖拽节点
  		
	$(function(){
		$("#content").focus();
	});
	function closeWin(){
		$("#selectModel").hide();
	}
	function addModelDoSubmit(){
		var data = {
			model:{},
			property:[]
		};
		var newModelNameObj = $("#box_addModel .modelList.modelName");
		data.model.name = newModelNameObj.find("input").val();
		data.model.type = newModelNameObj.find("select").find("option:selected").val();
		if(!data.model.name){
			alert("请输入模型名称！！");
		}else{
			var properties = $("#box_addModel .modelList.property");
			for(var i = 0; i < properties.length; i ++){
				var json = {
					"name": "",
					"type": "",
					"strong": 0,
					"index": 0
				};
				
				json.name = $(properties[i]).find("input").val();
				json.type = $(properties[i]).find("select").find("option:selected").val();
				var checked = $(properties[i]).find(".strong").attr("checked");
				if("checked" == checked){
					json.strong = 1;
				}else if("undefined" == checked || undefined == checked){
					json.strong = 0;
				}
				var isIndex = $(properties[i]).find(".isIndex").attr("checked");
				if("checked" == isIndex){
					json.index = 1;
				}else if("undefined" == isIndex || undefined == isIndex){
					json.index = 0;
				}
				if(json.name){
					data.property.push(json);
				}
			}
			$.ajax({
				type : "post",
				url : pageVariate.base + "/titanStruManage/addTitanVertex.action",
				dataType:"json",
				data:{
					vertexName : data.model.name,
					vertexJson : JSON.stringify(data.property)
				},
				success:function(data){
					hintManager.showSuccessHint("添加成功!");
					initModelList();
					$("#box_addModel").hide();
				},
				error:function(data){
					hintManager.showHint("添加失败!");
				}
			});
		}
	}
	
	function addEdgeDoSubmit(){
		var data = {
			model:{},
			property:[]
		};
		var newModelNameObj = $("#box_addEdge .modelList.modelName");
		data.model.name = newModelNameObj.find("input").val();
		data.model.type = newModelNameObj.find("select").find("option:selected").val();
		if(!data.model.name){
			alert("请输入模型名称！！");
		}else{
			var properties = $("#box_addEdge .modelList.property");
			for(var i = 0; i < properties.length; i ++){
				var json = {
					"name": "",
					"type": "",
					"strong": 0,
					"index": 0
				};
				
				json.name = $(properties[i]).find("input").val();
				json.type = $(properties[i]).find("select").find("option:selected").val();
				var checked = $(properties[i]).find(".strong").attr("checked");
				if("checked" == checked){
					json.strong = 1;
				}else if("undefined" == checked || undefined == checked){
					json.strong = 0;
				}
				var isIndex = $(properties[i]).find(".isIndex").attr("checked");
				if("checked" == isIndex){
					json.index = 1;
				}else if("undefined" == isIndex || undefined == isIndex){
					json.index = 0;
				}
				if(json.name){
					data.property.push(json);
				}
			}
			$.ajax({
				type : "post",
				url : pageVariate.base + "/titanStruManage/addTitanEdge.action",
				dataType:"json",
				data:{
					edgeName : data.model.name,
					EdgeJson : JSON.stringify(data.property)
				},
				success:function(data){
					hintManager.showSuccessHint("添加成功!");
					graphManager.showConnect();
					$("#box_addEdge").hide();
				},
				error:function(data){
					hintManager.showHint("添加失败!");
				}
			});
		}
	}
	function addModelCloseWin(){
		$("#box_addModel").hide();
		$("#selectModel").show();
	}
	
	function addEdgeCloseWin(){
		$("#box_addEdge").hide();
		$("#selectEdges").show();
	}
	
	function addDBSourceDoSubmit(obj){
		var action = $(obj).attr("action");
		if("sure" == action){
			var connectionName = $('#connectionName').val();
			var connectionDB = $('#connectionDB').val();
			var connectionServierName = $('#connectionServierName').val();
			var connectionDBTableName = $('#connectionDBTableName').val();
			var connectionPort = $('#connectionPort').val();
			var connectionDBUserName = $('#connectionDBUserName').val();
			var connectionDBPassword = $('#connectionDBPassword').val();
			if(connectionName==""){
				hintManager.showHint("请填写连接名");
				return;
			}
			$.ajax({
				type : "post",
				url : pageVariate.base + "/ImportSave.action",
				data : {
					connectionName:connectionName,
					connectionDB:connectionDB,
					connectionServierName:connectionServierName,
					connectionDBTableName:connectionDBTableName,
					connectionPort:connectionPort,
					connectionDBUserName:connectionDBUserName,
					connectionDBPassword:connectionDBPassword
				},
				dataType : "json",
				success : function(data){
					initDBSource();
					$("#box_addDBSource").hide();
					$(obj).attr("action", "test");
					$(obj).val("测试");
				},
				error : function(){
					hintManager.showHint("连接名不能相同");
				}
			});
		}else if("test" == action){
			var connectionName = $('#connectionName').val();
			var connectionDB = $('#connectionDB').val();
			var connectionServierName = $('#connectionServierName').val();
			var connectionDBTableName = $('#connectionDBTableName').val();
			var connectionPort = $('#connectionPort').val();
			var connectionDBUserName = $('#connectionDBUserName').val();
			var connectionDBPassword = $('#connectionDBPassword').val();
			$.ajax({
				type : "post",
				url : pageVariate.base + "MagicData/Import.action",
				data:{
					connectionName:connectionName,
					connectionDB:connectionDB,
					connectionServierName:connectionServierName,
					connectionDBTableName:connectionDBTableName,
					connectionPort:connectionPort,
					connectionDBUserName:connectionDBUserName,
					connectionDBPassword:connectionDBPassword 
				},
				dataType:"json",
				success:function(data){
					$(obj).attr("action", "sure");
					$(obj).val("确定");
					hintManager.showSuccessHint(data.rows[0].result);
				},
				error:function(data){
					hintManager.showHint('连接失败!');
				}
			});
		}
	}
	function addDBSourceCloseWin(){
		$("#box_addDBSource").hide();
		$("#selectDBSource").show();
	}
	
	function doSubmit(type){
		var modelDetalInput = $(".modelDetail").find("input");
		var json = {
			"_type": "vertex",
			"name":{
				"type":"",
				"value":""
			},
			"properties":{
				"type": "list",
				"value":[]
			}
		}
		var subNode = {
			"image": "img/"+type+".png",
			"name": "s",
			"type": type,
			"uuid": type,
			"weight": 4
		}
		json.name.value = $(modelDetalInput[0]).val();
		json.name.type = $(modelDetalInput[0]).next().val();
		for(var i = 1; i < modelDetalInput.length; i ++){
			var subJson = {
				"type": "", 
				"value": ""
			}
			subJson.value = $(modelDetalInput[i]).val();
			subJson.type = $(modelDetalInput[i]).next().val();
			json.properties.value.push(subJson);
		}
		console.log(json);
		nodes.push(subNode);
		graphDrawUpdate();
		$("#selectModel").hide();
	}
	
	function initBoxAddModel(){
		$("#box_addModel .box1").empty();
		$("#box_addModel .box1").append('<h1>添加模型</h1>' +
										'<div class="modelDetail">' +
											'<div class="modelList modelName">' +
												'<div class="item">模型名称：</div>' +
												'<input type="text" class="addModelName"/>' +
												'<select>' +
													'<option>string</option>' +
													'<option>Number</option>' +
													'<option>Varchar2</option>' +
												'</select>' +
											'</div>' +
											'<div class="modelList">' +
												'<div class="item">属性名称&nbsp;&nbsp;&nbsp;&nbsp;</div>' +
												'<div class="item" style="width: 200px;">属性值</div>' +
												'<div class="item" style="width: 144px;">属性类型</div>' +
												'<div class="item" style="width: 55px;">属强</div>' +
												'<div class="item" style="width: 55px;">索引</div>' +
											'</div>' +
											'<div style="height: 1px;width: 100%;background: black;float: left;margin: 3px 0;"></div>' +
											'<div class="modelListPar"></div>' +
										'</div>' +
										'<div>' +
											'<input type="button" class="xgBtn" onclick="addModelDoSubmit()" value="确定" />' +
											'<input type="button" class="closeBtn"  onclick="addModelCloseWin()" value="关闭" />' +
										'</div>');
		$("#box_addModel .box1 .modelDetail .addModelName").blur(function(){
			var _this = this;
			var inputValue = $(_this).val();
			if(inputValue){
				$.ajax({
					type:"post",
					url:pageVariate.base+"/dataManage/getTitanStructure.action",
					success:function(msg){
						var modelList = $("#box_addModel .box1 .modelListPar .modelList.property");
						modelList.remove();
						var data = {
							nodes : msg[0]
						}
						for(var type in data.nodes){
							if(type == inputValue){
								var selectClass = data.nodes[inputValue];
								for(var x = 0; x < selectClass.length; x ++){
									var propertyObj = $('<div class="modelList property">' +
															'<div class="item">模型属性：</div>' +
															'<input type="text" value="'+selectClass[x].name+'" class="addModelProperty"/>' +
															'<select>' +
																'<option>string</option>' +
																'<option>Number</option>' +
																'<option>Varchar2</option>' +
															'</select>' +
															'<input type="checkbox" class="strong"/>' +
															'<input type="checkbox" class="isIndex"/>' +
														'</div>');
									var delPropertyObj = $('<div class="Property del">X</div>');
									delPropertyObj.click(function(){
										var _this = this;
										$(_this).parent().remove();
									});
									propertyObj.append(delPropertyObj);
									modelList = $("#box_addModel .box1 .modelListPar .modelList.property");
									if(modelList.length == 0){
										$("#box_addModel .box1 .modelListPar").prepend(propertyObj);
									}else{
										$(modelList[modelList.length-1]).after(propertyObj);
									}
								}
							}
						}
					}
				});
			}
		});
		var addPropertyObj = $('<div class="modelList" style="width: 50px;min-width: 50px;margin-left: 40%;">' +
									'+' +
								'</div>');
		addPropertyObj.click(function(){
			var propertyObj = $('<div class="modelList property">' +
									'<div class="item">模型属性：</div>' +
									'<input type="text" class="addModelProperty"/>' +
									'<select>' +
										'<option>string</option>' +
										'<option>Number</option>' +
										'<option>Varchar2</option>' +
									'</select>' +
									'<input type="checkbox" class="strong"/>' +
									'<input type="checkbox" class="isIndex"/>' +
								'</div>');
			var delPropertyObj = $('<div class="Property del">X</div>');
			delPropertyObj.click(function(){
				var _this = this;
				$(_this).parent().remove();
			});
			propertyObj.append(delPropertyObj);
			var modelList = $("#box_addModel .box1 .modelListPar .modelList.property");
			if(modelList.length == 0){
				$("#box_addModel .box1 .modelListPar").prepend(propertyObj);
			}else{
				$(modelList[modelList.length-1]).after(propertyObj);
			}
		});
		$("#box_addModel .box1 .modelListPar").append(addPropertyObj);
	}
	
	
	
	function initModelList(){
		if($(".down_left.clearfix").children().length <= 0){
			hintManager.showHint("请添加数据源!");
			return false;
		}
		var selectModel = "";
	  	$("#selectModel").show();
	  	$(".selectModelDetail").next().empty();
	  	$(".selectModelList").empty();
	  	$(".selectedModelProperty").empty();
	  	maskManager.show();
		$.ajax({
			type:"post",
			url:pageVariate.base+"/dataManage/getTitanStructure.action",
			success:function(msg){
				maskManager.hide();
				var data = {
					nodes : msg[0],
					edges : msg[1]
				}
				for(var i in data.nodes){
					var item = $('<div class="item" title="' + i + '" extend="' + i + '" clicked="false">' +
									'<img src="images/img/'+i+'.png" style="width: 100px;height: 100px;margin: 0 5px;" onerror="javascript:this.src=\'images/img/default.png\'">' +
									'<span>' + i + '</span>' +
								'</div>');
					item.click(function(){
						var _this = this;
						$(".selectedModelProperty").empty();
						$(_this).parent().children().css("border", "solid 1px rgba(0, 0, 0, 0)");
						$(_this).parent().children().attr("clicked", "false");
						$(_this).attr("clicked", "true");
						$(_this).css("border", "solid 1px red");
						var type = $(_this).find("span").html();
						selectModel = type; 
						for(var j = 0; j < data.nodes[type].length; j ++){
							var proName = data.nodes[type][j].name;
							var proType = data.nodes[type][j].type;
							var proStrong = data.nodes[type][j].strong || 0;
							var proIndex = data.nodes[type][j].index || 0;
							$(".selectedModelProperty").append('<div class="item">' +
																	'<div title="名称：'+proName+'">名称：<span>'+proName+'</span></div>' +
																	'<div title="类型：'+proType+'">类型：<span>'+proType+'</span></div>' +
																	'<div title="属强：'+proStrong+'">属强：<span>'+proStrong+'</span></div>' +
																	'<div title="索引：'+proIndex+'">索引：<span>'+proIndex+'</span></div>' +
																'</div>');
						}
						var addNewModelProperty = $('<div class="item">' +
														'<div>+</div>' +
													'</div>');
						addNewModelProperty.click(function(){
							$("#selectModel").hide();
							initBoxAddModel();
							$("#box_addModel").show();
							$.ajax({
								type:"post",
								url:pageVariate.base+"/dataManage/getTitanStructure.action",
								success:function(msg){
									var modelList = $("#box_addModel .box1 .modelListPar .modelList.property");
									modelList.remove();
									var data = {
										nodes : msg[0]
									}
									for(var type in data.nodes){
										if(type == selectModel){
											$("#box_addModel .box1 .modelDetail .addModelName").val(selectModel);
											$("#box_addModel .box1 .modelDetail .modelName .addModelName").attr("disabled", "disabled");
											$("#box_addModel .box1 .modelDetail .modelName").find("select").attr("disabled", "disabled");
											var selectClass = data.nodes[selectModel];
											selectClass.push({
												name : "",
												type : "string",
												strong : 0,
												index : 0
											});
											for(var x = 0; x < selectClass.length; x ++){
												var propertyObj = $('<div class="modelList property">' +
																		'<div class="item">模型属性：</div>' +
																		'<input type="text" value="'+selectClass[x].name+'" class="addModelProperty" disabled="disabled"/>' +
																		'<select disabled="disabled">' +
																			'<option>string</option>' +
																			'<option>Number</option>' +
																			'<option>Varchar2</option>' +
																		'</select>' +
																		'<input type="checkbox" class="strong" disabled="disabled"/>' +
																		'<input type="checkbox" class="isIndex" disabled="disabled"/>' +
																	'</div>');
												var options = propertyObj.find("option");
												for(option = 0; option < options.length; option ++){
													if($(options[option]).html() == selectClass[x].type){
														$(options[option]).attr("selected", true);
													}
												}
												if(selectClass[x].strong == 1){
													propertyObj.find(".strong").attr("checked", true);
												}
												if(selectClass[x].index == 1){
													propertyObj.find(".isIndex").attr("checked", true);
												}
												if(selectClass[x].name == ""){
													var delPropertyObj = $('<div class="Property del">X</div>');
													delPropertyObj.click(function(){
														var _this = this;
														$(_this).parent().remove();
													});
													propertyObj.append(delPropertyObj);
													propertyObj.find(".addModelProperty").removeAttr("disabled");
													propertyObj.find("select").removeAttr("disabled");
													propertyObj.find(".strong").removeAttr("disabled");
													propertyObj.find(".isIndex").removeAttr("disabled");
												}
												modelList = $("#box_addModel .box1 .modelListPar .modelList.property");
												if(modelList.length == 0){
													$("#box_addModel .box1 .modelListPar").prepend(propertyObj);
												}else{
													$(modelList[modelList.length-1]).after(propertyObj);
												}
											}
										}
									}
								}
							});
						});
						$(".selectedModelProperty").append(addNewModelProperty);
					});
					item.hover(function(){
						var _this = this;
						$(_this).css("border", "solid 1px red");
						var modifyObj = $('<div class="function" style="position: absolute;left: 7px;bottom: 20px;">修改</div>');
						modifyObj.click(function(){
							var id = $(_this).attr("extend");
							selectModel = id;
							$("#selectModel").hide();
							initBoxAddModel();
							$("#box_addModel").show();
							$.ajax({
								type:"post",
								url:pageVariate.base+"/dataManage/getTitanStructure.action",
								success:function(msg){
									var modelList = $("#box_addModel .box1 .modelListPar .modelList.property");
									modelList.remove();
									var data = {
										nodes : msg[0]
									}
									for(var type in data.nodes){
										if(type == selectModel){
											$("#box_addModel .box1 .modelDetail .addModelName").val(selectModel);
											$("#box_addModel .box1 .modelDetail .modelName .addModelName").attr("disabled", "disabled");
											$("#box_addModel .box1 .modelDetail .modelName").find("select").attr("disabled", "disabled");
											var selectClass = data.nodes[selectModel];
											for(var x = 0; x < selectClass.length; x ++){
												var propertyObj = $('<div class="modelList property">' +
																		'<div class="item">模型属性：</div>' +
																		'<input type="text" value="'+selectClass[x].name+'" class="addModelProperty"/>' +
																		'<select>' +
																			'<option>string</option>' +
																			'<option>Number</option>' +
																			'<option>Varchar2</option>' +
																		'</select>' +
																		'<input type="checkbox" class="strong"/>' +
																		'<input type="checkbox" class="isIndex"/>' +
																	'</div>');
												var options = propertyObj.find("option");
												for(option = 0; option < options.length; option ++){
													if($(options[option]).html() == selectClass[x].type){
														$(options[option]).attr("selected", true);
													}
												}
												if(selectClass[x].strong == 1){
													propertyObj.find(".strong").attr("checked", true);
												}
												if(selectClass[x].index == 1){
													propertyObj.find(".isIndex").attr("checked", true);
												}
												var delPropertyObj = $('<div class="Property del">X</div>');
												delPropertyObj.click(function(){
													var _this = this;
													$(_this).parent().remove();
												});
												propertyObj.append(delPropertyObj);
												modelList = $("#box_addModel .box1 .modelListPar .modelList.property");
												if(modelList.length == 0){
													$("#box_addModel .box1 .modelListPar").prepend(propertyObj);
												}else{
													$(modelList[modelList.length-1]).after(propertyObj);
												}
											}
										}
									}
								}
							});
						});
						$(_this).append(modifyObj);
						var deleteObj = $('<div class="function" style="position: absolute;right: 7px;bottom: 20px;">删除</div>');
						deleteObj.click(function(){
							var id = $(_this).attr("extend");
							selectModel = id;
							var DBName = $(_this).attr("title");
							confirm("删除该类型，将会关联此数据库未执行的任务一并删除，是否确认删除："+DBName,function(){
								$(_this).remove();
								/* $.ajax({
									type : "post",
									url : pageVariate.base + "/ImportDelete.action",
									data : data.dataBaseJson[selectModel],
									success : function(taskID){
										hintManager.showSuccessHint("删除DB配置成功!");
										$(_this).remove();
									}
								}); */
							});
						});
						$(_this).append(deleteObj);
					},
					function(){
						var _this = this;
						var clicked = $(_this).attr("clicked");
						if("false" == clicked){
							$(_this).css("border", "solid 1px rgba(0, 0, 0, 0)");
						}
						$(_this).find(".function").remove();
					});
					$(".selectModelList").append(item);
				}
				var addNewModel = $('<div class="item">' +
										'<img src="images/img/Person.png" style="width: 100px;height: 100px;margin: 0 5px;" onerror="javascript:this.src=\'images/img/default.png\'">' +
										'<span>添加新模型</span>' +
									'</div>');
				addNewModel.click(function(){
					$("#selectModel").hide();
					initBoxAddModel();
					$("#box_addModel").show();
				});
				$(".selectModelList").append(addNewModel);
				var suerBtn = $('<input type="button" class="xgBtn" value="确定" />');
				suerBtn.click(function(){
					var _this = this;
					if(selectModel == ""){
						alert("请选择模型类型!!");
					}else{
						$("#selectModel").hide();
						var propertyList = new Array();
						for(var property = 0; property < data.nodes[selectModel].length; property ++){
							var name = data.nodes[selectModel][property].name;
							var type = data.nodes[selectModel][property].type;
							var strong = data.nodes[selectModel][property].strong || 0;
							var isIndex = data.nodes[selectModel][property].index || 0;
							propertyList.push({
								"propertyName" : name,
								"propertytype" : type,
								"isStrongProperty" : parseInt(strong),
								"isBuildIndex" : parseInt(isIndex),
								"databaseColumnId" : -1,
								"propertyId" : property,
								"propertyCardinality" : "SET"
							})
						}
						var uuid = randomString(32);
						var subNode = {
							"image": "img/"+selectModel+".png",
							"name": selectModel + (nodes.length+1),
							"type": selectModel,
							"uuid": uuid,
							"custormVertexPropertyList": propertyList
						}
						nodes.push(subNode);
						graphDrawUpdate();
						/* GraphJSON.vertices.push({
							"_id" : uuid,
				            "_type": "vertex",
				            "name": {
				                "type": "string", 
				                "value": selectModel + nodes.length
				            },
				            "properties": {
				                "type": "list", 
				                "value": propertyList
				            }
				        }); */
						GraphJSON.database[0].tableList[0].vertexList.push({
							"vertexId": nodes.length-1,
							"uuid" : uuid,
							"custormVertexPropertyList": propertyList,
							"vertexTypeName": selectModel,
							"image": "img/"+selectModel+".png",
							"name": selectModel + nodes.length,
							"type": selectModel,
						});
					}
				});
				$(".selectModelDetail").next().append(suerBtn);
				var cancelBtn = $('<input type="button" class="closeBtn" value="关闭" />');
				cancelBtn.click(function(){
					var _this = this;
					$("#selectModel").hide();
				});
				$(".selectModelDetail").next().append(cancelBtn);
			}
		});
	}
	
	$("#addModel").click(function(){
		initModelList();
	});
  	var qqgroupcount=0;
//    $(function(){
// 	   $('#tt').tabs('add',{
// 			title: 'QQ',
// 			closable: true,
// 			content: "<div class='zoomQQline0' style='width:10px;z-index:999; height:10px;position: absolute; top:100px;left: 15px margin-top:-120px;  float:left;border:0px solid #F00'></div><div class='displayQQDiv0' style='width:100%; height:100%;'><div>"
			
// 		});
// 	   getQQgroup("","",0);}
//    );

var getRepeatElementTotal = function (array) {
    var count = 1;
    var sum = new Array(); //存放数组array中每个不同元素的出现的次数
    for (var i = 0; i < array.length; i++) {
      for (var j = i + 1; j < array.length; j++) {
        if (array[i] == array[j]) {
          count++; //用来计算与当前这个元素相同的个数
          array.splice(j, 1); //没找到一个相同的元素，就要把它移除掉，
          j--;
        }
      }
      sum[i] = count; //并且将有多少个当前这样的元素的个数存入sum数组中
      count = 1; //再将count重新赋值，进入下一个元素的判断
    }

    var newsum = new Array(); //  sum;
    for (var item = 0; item < sum.length; item++) {
      newsum[item] = sum[item];
    }
    newsum.sort(function sortNumber(a, b) {
      return a - b
    });
    var first = 0; //存放出现次数最多的元素，以及个数
    var fcount = 1; //计算出现次数最多的元素总共有多少个
    for (var i = 0; i < sum.length; i++) {
      if (sum[i] == newsum[newsum.length - 1]) {
        first = sum[i];
        fcount++;
      }

    }
    return first;
  }

	  function date2LocationPx(date, startTime, endTime) {
	    var heightTime = $(".commStyle.clearfix").width();
	    var left = parseInt(((new Date(date)).getTime() - (new Date(startTime)).getTime()) / ((new Date(endTime)).getTime() + 2592000000 - (new Date(startTime)).getTime()) * ((heightTime)));
	    return left;
	  }
	function drawTimeLine(date) {
	    $("#timeline_dates").empty();
	    $("#timeline_histograms").empty();
	    var dateReplace = [];
	    var dataTmpArrayStart = [];
	    var dataTmpArrayEnd = [];
	    for (var i = 0; i < date.length; i++) {
	      if (date[i]["startTime"].indexOf("tepf") < 0 && date[i]["endTime"].indexOf("tepf") < 0) {
	        dataTmpArrayStart.push(date[i]["startTime"].replace(/"/g, ""));
	        dataTmpArrayEnd.push(date[i]["endTime"].replace(/"/g, ""));
	      }
	    }
	    dataTmpArrayStart = dataTmpArrayStart.sort();
	    dataTmpArrayEnd = dataTmpArrayEnd.sort();
	    for (var j = 0; j < dataTmpArrayStart.length; j++) {
	      for (var i = 0; i < date.length; i++) {
	        if (date[i]["startTime"].replace(/"/g, "") == dataTmpArrayStart[j]) {
	          dateReplace.push(date[i]);
	          continue;
	        }
	      }
	    }
	    var dateYear = [];
	    var yearStart = (new Date(dataTmpArrayStart[0])).getFullYear(); //2015
	    var monthStart = (new Date(dataTmpArrayStart[0])).getMonth() + 1; //04
	    var yearEnd = (new Date(dataTmpArrayEnd[dataTmpArrayEnd.length - 1])).getFullYear(); //2016
	    var monthEnd = (new Date(dataTmpArrayEnd[dataTmpArrayEnd.length - 1])).getMonth() + 1; //08
	    var cnm = 0;
	    for (var i = 0; i <= ((yearEnd - yearStart) * 12 - monthStart + monthEnd); i++) {
	      if (i < 12 - monthStart) {
	        dateYear.push(yearStart + "-" + (monthStart + i));
	        cnm = i;
	      } else {
	        dateYear.push((yearStart + 1) + "-" + (i - cnm));
	      }
	    }
	    var itemDetail = 1;
	    var widthTime = $(".commStyle.clearfix").width();
	    var yearDetail = parseInt(widthTime / dateYear.length);
	    for (var i = 0; i < dateYear.length; i++) {
	      $("#timeline_dates").append("<li title=\"" + dateYear[i] + "\" style='border-left: 2px solid #00F;border-right: 2px solid #00F;width: " + yearDetail + "px'><a style=\"color:white;\" href=\"javascript:void(0);\">" + dateYear[i] + "</a></li>");
	    }
	    var allTime = new Array();
	    for (var i = 0; i < widthTime; i++) {
	      allTime[i] = 0;
	    }
	    var height = 10;
	    var leftArray = [];
	    var leftArrayTmp = [];
	    var heightArray = [];
	    for (var i = 0; i < dateReplace.length; i++) {
	      var startTime = date2LocationPx(dateReplace[i]["startTime"].replace(/"/g, ""), "" + dateYear[0], "" + (dateYear[dateYear.length - 1]));
	      var endTime = date2LocationPx(dateReplace[i]["endTime"].replace(/"/g, ""), "" + dateYear[0], "" + (dateYear[dateYear.length - 1]));
	      leftArray.push(startTime);
	      for (var j = 0; j <= endTime - startTime; j++) {
	        leftArrayTmp.push(startTime + j);
	      }
	    }
	    var first = getRepeatElementTotal(leftArrayTmp);
	    var leftArrayReaded = [];
	    for (var i = 0; i < leftArray.length; i++) {
	      var right = date2LocationPx(dateReplace[i]["endTime"].replace(/"/g, ""), "" + dateYear[0], "" + (dateYear[dateYear.length - 1]));
	      for(var j = leftArray[i]; j <= right; j ++){
	        allTime[j] += parseFloat(55 / first);
	      }
	    }
	    for(var i = 0; i < allTime.length; i ++){
	      $("#timeline_histograms").append('<div style="float: left;width: 1px;background-color: rgba(255, 255, 255, 0.8);' +
	      'position: absolute;bottom: 0px;left: ' + i + 'px;height: ' + (allTime[i]) + 'px;">' +
	      '</div>');
	    }
	    $("#timeline_bgDiv").css("width", Math.ceil(yearDetail * dateYear.length - 1));
	    $("#timeline_Detail").css("width", Math.ceil(yearDetail * dateYear.length));
	    var rs = new Resize("timeline_dragDiv", {
	        Max : true,
	        mxContainer : "timeline_bgDiv"
	      });
	    rs.Set("timeline_rRight", "right");
	    rs.Set("timeline_rLeft", "left");
	    new Drag("timeline_dragDiv", {
	      Limit : true,
	      mxContainer : "timeline_bgDiv",
	      onMove : function () {
	        var count = nodes.length;
	        var selectedNodes = new Array();
	        var unSelectedNodes = new Array();
	        for (var i = 0; i < count; i++) {
	        	if(nodes[i]["subList"] != undefined && nodes[i]["subList"].length > 0){
	        		for(var j = 0; j < nodes[i]["subList"].length; j ++){
	        			var hasBeginning = nodes[i]["subList"][j].time;
	        			var hasEnd = nodes[i]["subList"][j].time;
	        			if (hasBeginning && hasEnd) {
			              var timestamp_start = date2LocationPx(hasBeginning.replace(/"/g, ""), "" + dateYear[0], "" + (dateYear[dateYear.length - 1]));
			              var timestamp_end = date2LocationPx(hasEnd.replace(/"/g, ""), "" + dateYear[0], "" + (dateYear[dateYear.length - 1]));
			              var left = this.Drag.offsetLeft - 1;
			              var right = this.Drag.offsetLeft + this.Drag.clientWidth;
			              if (
			                (left <= timestamp_start && timestamp_start <= timestamp_end && timestamp_end <= right)
			                 || (left <= timestamp_start && timestamp_start <= right) && (right <= timestamp_end)
			                 || (timestamp_start <= left && left <= timestamp_end && timestamp_end <= right)
			                 || (timestamp_start <= left && left <= right && right <= timestamp_end)) {
			                  selectedNodes.push(nodes[i]);
			                  if("own" != nodes[i].type){
							  	$(".n"+nodes[i].uuid +" image").attr("href", "images/img/"+nodes[i].type+"Sel.png");
							  }
			              } else {
			                unSelectedNodes.push(nodes[i]);
			                if("own" != nodes[i].type){
			                	$(".n"+nodes[i].uuid +" image").attr("href", "images/img/"+nodes[i].type+".png");
			                }
			              }
			            }
	        		}
	        	}
	        }
	      }
	    });
	  }
  $(function(){
	   $.ajax({
          type:"post",
          url:pageVariate.base+"/query/getTreeJson.action",
          success:function(data){
	        	pageVariate.tempData = eval(data);
       	   		zNodes=pageVariate.tempData;
          }
       });
       var initLayoutImage = function(){
           var layouts = $("#layout").children();
           for(var i = 0; i < layouts.length; i ++){
               var oldImage = $(layouts[i]).children().attr("src");
               var newImage = oldImage.replace("Sel.png", ".png");
               $(layouts[i]).children().attr("src", newImage);
           }
       }
       $("#layoutBreadthfirst").click(function(){
       	  init_layout();
       	  initLayoutImage();
       	  on_layout("breadthfirst");
       	  var _this = this;
       	  var oldImage = $(_this).children().attr("src");
       	  var newImage = oldImage.replace(".png", "Sel.png");
       	  $(_this).children().attr("src", newImage);
       	  
       });
       $("#layoutCose").click(function(){
     	  init_layout();
       	  initLayoutImage();
     	  on_layout("cose");
       	  var _this = this;
       	  var oldImage = $(_this).children().attr("src");
       	  var newImage = oldImage.replace(".png", "Sel.png");
       	  $(_this).children().attr("src", newImage);
       });
       $("#layoutRandom").click(function(){
      	  init_layout();
       	  initLayoutImage();
       	  on_layout("random");
       	  var _this = this;
       	  var oldImage = $(_this).children().attr("src");
       	  var newImage = oldImage.replace(".png", "Sel.png");
       	  $(_this).children().attr("src", newImage);
       });
       $("#layoutCircle").click(function(){
       	  init_layout();
       	  initLayoutImage();
       	  on_layout("circle");
       	  var _this = this;
       	  var oldImage = $(_this).children().attr("src");
       	  var newImage = oldImage.replace(".png", "Sel.png");
       	  $(_this).children().attr("src", newImage);
       });
       $("#layoutGrid").click(function(){
       	  init_layout();
       	  initLayoutImage();
       	  on_layout("grid");
       	  var _this = this;
       	  var oldImage = $(_this).children().attr("src");
       	  var newImage = oldImage.replace(".png", "Sel.png");
       	  $(_this).children().attr("src", newImage);
       });
       $("#layoutConcentric").click(function(){
       	  init_layout();
       	  initLayoutImage();
       	  on_layout("concentric");
       	  var _this = this;
       	  var oldImage = $(_this).children().attr("src");
       	  var newImage = oldImage.replace(".png", "Sel.png");
       	  $(_this).children().attr("src", newImage);
       });
       
       $("#addTimeDataToLine").click(function(){
       	  var dataArray = new Array();
          for(var i = 0; i < nodes.length; i ++){
            if(nodes[i]["subList"] != undefined && nodes[i]["subList"].length > 0){
              for(var j = 0; j < nodes[i]["subList"].length; j ++){
                var time = nodes[i]["subList"][j].time;
                dataArray.push({
                  startTime : time,
                      endTime : time
                    });
              }
            }
          }
          drawTimeLine(dataArray);
       });
	   //add by hanxue start 导出
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
	        shadow: false,
	        bgiframe: true,
	        overlay: {opacity: 1,overflow:'auto',backgroundColor: '#fff',}
	    });
    	var bh = $("body").height(); 
	    var bw = $("body").width(); 
	    $("#exportingDiv").dialog("close");
	    $("#exportingDiv").parent().css({left: (bw-200)/2,top:bh/2,height:170,border:0});
	    $("#exportingDiv").css({border:0});
	    $(".window-shadow").removeAttr("style");
	    //add by hanxue end
      	document.onkeydown = function(e) {
			e = e ? e : window.event;
			var keyCode = e.which ? e.which : e.keyCode;
			if(keyCode == 17)
				pageVariate.pressCtrl = true;
		}
		document.onkeyup = function(e) {
			e = e ? e : window.event;
			var keyCode = e.which ? e.which : e.keyCode;
			if(keyCode == 17)
				pageVariate.pressCtrl = false;
		}
    }); 
     /*#################操作时间轴操作相关函#########################*/
             /*点击菜单时间轴联动*/
            // $(".tccBox").show();
     var currentTimeLineId="";
     
    //时间轴缓存变量
    var timeLineVariate = {
    	stratYearTime:"",//还原年起始时间
    	endYearTime:"",//还原年结束年时间
    	yearTimeCha:"",//时间差值（时间范围值）
    	dayStartTime:"",//查询具体天的时间
    	dayStartTimeMonth:"",//还原月起始时间
    	dayEndTimeMonth:"",//还原月结束年时间
    	firstTimeFormat:true
    };
  
    </script>

  <script type="text/javascript">
  /*加载菜单 by hx*/
    function menuClick(menuParam){
      if(null!=menuParam&&menuParam!=""){
        hideAll("wheel");
        $("#menuDiv").css("visibility","hidden");
        menuClickLindage(menuParam);
      }
    }

    
    function initMenu(type){
    	$.ajax({
             type:"post",
             url:"${base}getMenuData.action?type="+type,
             async:false,
             success:function(data){
               $("#menuDiv").empty();
               $("#menuDiv").html(data);
             }
          });
          $(".wheel-button").wheelmenu({
    	    trigger: "click",
    	    animationSpeed:"fast",
    	    liSize:rootnumber,
    	    angle:[0,360]
    	},rootWheelR);
    }

    function disableMenu(){
    	var wheelChildren = $("#wheel").children();
    	for(var i = 0; i < wheelChildren.length; i ++){
    		$(wheelChildren[i]).css("pointer-events", "none");
    	}
    }
    
    function ableMenu(){
    	var wheelChildren = $("#wheel").children();
    	for(var i = 0; i < wheelChildren.length; i ++){
    		$(wheelChildren[i]).css("pointer-events", "");
    	}
    }
    
    function selectNodeStatus(){
    	var d = pageVariate.currentNode;
    	var type = d.image.replace(/\//g, "").replace("img", "").replace(".png", "");
		//当前没有选中的呼出菜单选中，操作完成还原
		var index = pageVariate.selNode.indexOf(d);
		if(index>-1){
			pageVariate.currentSelFlag = true;
		}else{
			$(".n"+d.uuid +" image").attr("href", "images/img/"+type+"Sel.png");
			pageVariate.selNode.push(d);
		}
    }
    
    function showMenu(event,nodeImg){
        if($("#menuDiv").css("visibility")=="visible"){
            $(this).bind('contextmenu',function(e){  
             return false;  
            });
            hideAll("wheel");
            /* $("#menuDiv").load("#getMenuData"); */
            $("#menuDiv").css("visibility","hidden");
        }else{
            $(this).bind('contextmenu',function(e){  
            	return false;  
            });
            var x = event.pageX-280-50;
            var y = event.pageY-180+10;
            var imgX = "";
            var imgY = "";
            try{
            	imgX = nodeImg.offset().left;
                imgY = nodeImg.offset().top;
            }catch(e){}
            if(null!=imgX&&imgX!=0&&imgX!=undefined){
         	   $("#menuDiv").css("margin-left",imgX-220-38);
            }else{
         	   $("#menuDiv").css("margin-left",x);
            }
            if(null!=imgY&&imgY!=0&&imgY!=undefined){
         	   $("#menuDiv").css("margin-top",imgY-190+138);
            }else{
         	   $("#menuDiv").css("margin-top",y);
            }
			var type = pageVariate.currentNode.image.replace(/\//g, "").replace("img", "").replace(".png", "");
			if(type != ""){
				console.log(type);
			}
			initMenu("Person");
			disableMenu();
			if(true){
				ableMenu();
				selectNodeStatus();
				$("#menuDiv").css("visibility","visible");
				$(".wheel-button").trigger("click");
			}else if(pageVariate.IM.indexOf(type) > -1){
				selectNodeStatus();
				$("#menuDiv").css("visibility","visible");
				$(".wheel-button").trigger("click");
				$($("#wheel").children()[4]).css("pointer-events", "");
				$($("#wheel").children()[5]).css("pointer-events", "");
			}else if(type == "Group"){
				selectNodeStatus();
				$("#menuDiv").css("visibility","visible");
				$(".wheel-button").trigger("click");
				$($("#wheel").children()[5]).css("pointer-events", "");
			}else{
				console.log("当前右键节点为： " + type);
				return false;
			}
        }
    }
    
    /******************************查询入口 start**************************************/   
      /*发送mail*/
	function sendmails(id){
		var str=document.getElementsByName("mailcheckbox"+id);
		var objarray=str.length;
		var oblist=new Array();
		var oblistcount=0;
		var result=new Array();
		for (var i=0;i<objarray;i++){
		  if(str[i].checked == true){
			oblist[oblistcount]=str[i].value;
			++oblistcount;
		  }
		}
		for (var i=0;i<oblist.length;i++){
				result[i]=emailmap.get(oblist[i]).vertexID;
		}
		if(oblistcount == 0){
			 hintManager.showHint("请至少选择一个mail！");
		}else{
			//跳到首页 TODO
			// .....
			graphManager.sendmailToGraph(result);
			$(".easyui-tabs").tabs('select', 0);
			
			
		}
	}
    //页面缓存变量
    var pageVariate = {
   		root:"",//查询对象
   		personId:"",//查询对象ID
   		uuid:"",//查询对象唯一标示
   		types:[],//事件类型
   		eventFlag:false,//当前显示状态
   		startTime:"",//查询开始时间
   		endTime:"",//查询结束时间
   		startTimeStr:"",//时间轴所需
   		endTimeStr:"",//时间轴所需
		initStartTime:"",
		initEndTime:"",
   		fristFlag:true,
   	    selNode:[],//选中节点
   	 	tempDeleteId:[],//删除id
   	    tempData:"",
   	 	openMap:new Map(),//展开缓存
   	 	currentSelFlag:false,
   	 	typeMap:new Map(),//查询type缓存
   	 	selectAllFlag:true,//列表全选标记
   		base:"${base}",
		initFlag:true,
		pressCtrl:false,
		isClickNode : false,
		currentNode:"",
		IM:["QQ"]
    }
	
  	$(function(){
  		searchManager.emptyIdCache();
		//回车查询
		$('#content').on('keypress',function(event){
	           if(event.keyCode == "13"){
	        	   searchContent("");
	           }
	       });
	});
    var pageNO=0;
    /*查询入口*/
  	function searchContent(more){
  		maskManager.show();
  		var content = $('#content').val();
  		var propertySel = $(".select_txt").attr("name");
  		if(more==""){pageNO=0;}else{
  			content = $('#'+more+'tabvalue').val();
  			pageNO=pagemap.get(more)+1;
  		}
  		if("emailAll" == propertySel){
  			$.ajax({
		         type:"post",
		         dataType:"json",
		         url:"${base}query/getEmailsFullText.action",
		         data:{
	  				 property:propertySel,
		        	 content:content,
		        	 allText:advanceSearch.get("allTextOfSearch"),
		        	 titleOfSearch:advanceSearch.get("titleOfSearch"), 
		        	 mailAddressOfSearch:advanceSearch.get("mailAddressOfSearch"), 
		        	 contectOfSearch:advanceSearch.get("contectOfSearch"), 
		        	 filenameOfSearch:advanceSearch.get("filenameOfSearch"), 
		        	 filecontectOfSearch:advanceSearch.get("filecontectOfSearch"), 
		        	 hotwordOfSearch:advanceSearch.get("hotwordOfSearch"), 
		        	 tagOfSearch:advanceSearch.get("tagOfSearch"), 
		        	 folderOfSearch: advanceSearch.get("folderOfSearch"), 
		        	 datefromSearch:advanceSearch.get("datefromSearch"), 
		        	 datetoSearch:advanceSearch.get("datetoSearch"), 
		        	 pageNo:pageNO,
		        	 pageSize:5,
		        	 date:new Date().getTime() 
		         },
		         success:function(data){
		         	maskManager.hide();
		        	 //++pageNO;
		        	var emails = eval(data);
		        	
		        	if(emails && emails.length>0){
		        		// TODO
		        		if(more==""){getmaillist(emails,"");}else{pagemap.put(more,pageNO);getmaillist(emails,more);}
		        	}else{
		        		hintManager.showHint("未查询到邮件数据。");
		        	}
		           },
		         error:function(){
		         	maskManager.hide();
		        	hintManager.showHint("未查询到邮件数据。");
		         }
		     });
  		}else{
	  		$.ajax({
		         type:"post",
		         url:"${base}query/getObj.action",
		         data:{
	  				 property:propertySel,
		        	 content:content,
		        	 date:new Date().getTime() 
		         },
		         dataType:"json",
		         success:function(data){
		         	maskManager.hide();
	//	            pageVariate.root = $.parseJSON(data);
		        	pageVariate.tempData = eval(data);
		        	var nodeSize = pageVariate.tempData.nodes.length;
		            if(nodeSize == 1){
		            	searchManager.searchLinkage(pageVariate.tempData);
		            	$(".easyui-tabs").tabs('select', 0);
		            }else if(nodeSize > 1){
		            	searchManager.moreNodeHandle(pageVariate.tempData);
		            	$(".easyui-tabs").tabs('select', 0);
		            }else{
		             	hintManager.showHint("未查询到数据。");
		            }
		           },
		         error:function(){
		         	maskManager.hide();
		        	hintManager.showHint("未查询到数据。");
		         }
		     });
  		}
  	}
  	var emailmap = new Map();
  	var pagemap=new Map();
  	/******************************查询入口  end**************************************/ 
  
  	/******************************整合菜单、时间轴 、地图 start**************************************/
  	/* 菜单点击
  	 * 此处配置按钮执行的方法
     * 1search=查询,2connect=关联,3openList=展开,4clear=清空,5delete=删除,6map=地图
     * 11object=对象,13net=网络
     * 831all=所有(3级),832bbs=帖子,833doctor=医疗,834talk=通话,835login=网站登录,836mail=邮件
    */
  	function menuClickLindage(menuParam){
		var type = pageVariate.currentNode.image.replace(/\//g, "").replace("img", "").replace(".png", "");
  		var re=/^83\d{1}.*/;
  		if(re.test(menuParam)){//事件查询
  			var searchflag = true;
  			if("831all" == menuParam){
  				searchflag = changeState("event_all");
  	  		}else if("832bbs" == menuParam){
  	  			searchflag = changeState("event_BBSEvent");
  	  		}else if("833doctor" == menuParam){
  	  			searchflag = changeState("event_MedicalEvent");
  	  		}else if("834talk" == menuParam){
  	  			searchflag = changeState("event_CallEvent");
  	  		}else if("835login" == menuParam){
  	  			searchflag = changeState("event_LoginEvent");
  	  		}else if("836mail" == menuParam){
  	  			searchflag = changeState("event_EmailEvent");
  	  		}else if("837hotel" == menuParam){
  	  			searchflag = changeState("event_StayEvent");
  	  		}
  			if(searchflag){
	  			//删除事件相关节点
				deteleEventNode(pageVariate.uuid);
	  			graphManager.showEvent(pageVariate.types,pageVariate.startTime,pageVariate.endTime);//展示事件
  			}
  		}else{
  			changeState(menuParam);
  			if("2connect" == menuParam){
  				graphManager.showConnect();
  	  		}else if("2process" == menuParam){
  	  			collapseNode(pageVariate.uuid);//删除子节点
  	  		}else if("3openList" == menuParam){
  	  			graphManager.showDetail();
	  	  	}else if("4clear" == menuParam){
		  	  	//清空
		  	  	confirm("是否确认清空？",function(){
		  	  		emptyNode();
		  	  		myChart.clear();
		  	  		myChart.setOption(myChart.initOption);
		  	  	});
	  	  	}else if("5delete" == menuParam){
	  	  		if(type == "Person"){
					//删除当前节点及边
			  	  	confirm("是否确认删除？",function(){
			  	  		deteleSelNode(pageVariate.selNode);
			  	  		myChart.clear();
			  	  		myChart.setOption(myChart.initOption);
			  	  		//以后可能要判断删除的点是不是最后操作的
			  	  		pageVariate.personId = "";
			  	  		pageVariate.uuid = "";
			  	  		pageVariate.initStartTime = "";
			  	  		pageVariate.initEndTime = "";
			  	  	});//修改为删除选中节点
		  	  	}else if(pageVariate.IM.indexOf(type) > -1){
  	  			   maskManager.show();
	  	  		   $.ajax({
			          type:"post",
			          url:pageVariate.base+"/ajaxSearchQq/searchQQFriendsByQq.action",
			          data : {
						qq : pageVariate.currentNode.name
					  },
			          success:function(data){
			          	maskManager.hide();
				        var eventData = eval(data);
						expandNode(eventData.nodes,eventData.edges);
			          }
			       });
  	  			}
  	  		}else if("6map" == menuParam){
  	  			if(type == "Person"){
		  			var eventType = ["LoginEvent"];
  	  			}else if(pageVariate.IM.indexOf(type) > -1){
  	  			  	maskManager.show();
  	  				$.ajax({
			          type:"post",
			          url:pageVariate.base+"/ajaxSearchQq/searchgGroupsByQq.action",
			          data : {
						qq : pageVariate.currentNode.name
					  },
			          success:function(data){
			          	maskManager.hide();
				        var eventData = eval(data);
						expandNode(eventData.nodes,eventData.edges);
			          }
			       	});
  	  			}else if(type == "Group"){
  	  			    maskManager.show();
  	  				$.ajax({
			          type:"post",
			          url:pageVariate.base+"/ajaxSearchQq/searchGroupMembers.action",
			          data : {
						groupNum : pageVariate.currentNode.num
					  },
			          success:function(data){
			          	maskManager.hide();
				        var eventData = eval(data);
						expandNode(eventData.nodes,eventData.edges);
			          }
			       });
  	  			}
  	  		}else if("7group" == menuParam){
  	  			
  	  		}else if("1search" == menuParam){
  	  			
  	  		}else if("10all" == menuParam){
  	  			
  	  		}else if("11object" == menuParam){
  	  			graphManager.showGroupRelative();
  	  		}else if("12event" == menuParam){
  	  			
  	  		}else if("13net" == menuParam){
  	  			graphManager.showRelative();
  	  		}
  		}
  		
  		//还原按钮状态
  		if("5delete" != menuParam && "4select" != menuParam){
  			graphOperation.resetCurrentNode();
  		}
  	}
  	
  	
	/*记录菜单状态
	 *id为菜单的ID，查询事件的菜单ID格式为event_type,全部为event_all
	 */
  	function changeState(id){
  		var markArr = id.split("_");
  		if(markArr.length == 2 && markArr[0] == "event"){
  			pageVariate.eventFlag="event";
  			var type = markArr[1]; 
  			if("all" == type){
  				pageVariate.types=[];
  			}else{
  				var typeArr = pageVariate.typeMap.get(pageVariate.personId);
  				if (typeArr) {
  					var index = typeArr.indexOf(type);
  					if(index>-1){
  						//return false;
  						for(var i = index;i<(typeArr.length-1);i++){
  							var tempStr = typeArr[i];
  							typeArr[i] = typeArr[i+1];
  							typeArr[i+1] = tempStr;
  						}
//   					typeArr.splice(index, 1);
  					}else{
  						typeArr.push(type);
  					}
  				}else{
  					typeArr = [];
  					typeArr.push(type);
  					pageVariate.typeMap.put(pageVariate.personId,typeArr);
  				}
  				pageVariate.types = typeArr;
  			}
  		}else if("6map" == id){
  			pageVariate.eventFlag="map";
  		}else{
  			pageVariate.eventFlag="event";//暂定
  		}
  		return true;
  	}
	
	
  	/*拖动时间轴图联动*/
  	var timeLineDragFlag = true;
  	function graphLinkage(start,end){
	  	if(start && end){
  			if(timeLineDragFlag){
	  			timeLineDragFlag = false;
				pageVariate.startTime=start;
				pageVariate.endTime=end;
		  		if(pageVariate.eventFlag == "event"){
					deteleEventNode(pageVariate.uuid);//删除事件相关节点
		  			graphManager.showEvent(pageVariate.types,pageVariate.startTime,pageVariate.endTime);
		  		}else{
		  			maskManager.hide();
		  		}
	  			timeLineDragFlag = true;
  			}
	  	}
  	}
  	
	/* 过滤属性联动 */
    function changeProperty(type){
  	  var vertexProperty = $.parseJSON('${vertexPropertyJson}');
  	  var pros = vertexProperty[type];
  	  if(pros){
  		  var html = "";
  		  for(var i = 0;i < pros.length; i++){
     			var temp = pros[i];
     			html += "<option value='"+temp+"'>"+temp+"</option>";
  		  }
  		  $(".filterProperty").html(html);
  		  $(".filterContent").val("");
  	  }
    }
    
    /* 过滤、反选 */
    function filterGraph(flag){
  	  if(flag == "checkInvert"){
  		  tabsManager.graphHighlightInvert();
  		  return;
  	  }
  	  var type = $(".filterType").val();
  	  var property = $(".filterProperty").val();
  	  var content = $(".filterContent").val();
  	  if(content.replace(/(^\s*)|(\s*$)/g, "") == "" ){
  		  hintManager.showHint("过滤内容不能为空，请输入！");
  	  }else{
  		  if(flag == "check"){
  			  tabsManager.filterGraphHighlight(type,property,content); 
  		  }
  	  }
    }
    
    /* 获取页面点的id数组 （不包括任务属性节点）*/
    function getPageVertexIds(){
    	var idarr = []; 
		$.each(nodes,function(i,node){
			var type = node.type;
			if("Person" == type){
				idarr.push(node.id);
			}else if(typeof(type) == "string" && type.endWith("Event")){
				var eventNodes = node.subList;
				eventNodes.forEach(function(node,index){
					idarr.push(node.id);
				})
			}
		});
		return idarr;
    }
	
  	/******************************整合菜单、时间轴 、地图 end**************************************/
  	
 	$(function(){
  			/*------头部select框--------*/
           $(".property_div").click(function(event){   
                        event.stopPropagation();
                        $(this).find(".option").toggle();
                        $(this).parent().siblings().find(".option").hide();
                        $(".hideDiv").hide();
           });
           $(document).click(function(event){
                        var eo=$(event.target);
                        if($(".property_div").is(":visible") && eo.attr("class")!="option" && !eo.parent(".option").length)
                        $('.option').hide();  
                        
                        $(".hideDiv").hide(); 
                   });
           /*赋值给文本框*/
           $(".option span").click(function(){
                        var value=$(this).text();
                        $(this).parent().siblings(".select_txt").text(value);
                        var classAttr = $(this).attr('class');
						$(".select_txt").attr("name", classAttr);
                        $("#select_value").val(value);
                       /* $(".select_txt").attr('name',)); */
           })
           
           /************************头部隐藏div************************/
	   		$(".hideDiv ul li").live("click",function(){
	   			var nodes = pageVariate.tempData.nodes;
	   			var uuid = this.id;
	   			for(var i=nodes.length-1;i>-1;i--){
	   				var node = nodes[i];
					if(uuid != node.uuid){
						nodes.remove(node);
	   				}
	   			}
	   			searchManager.searchLinkage(pageVariate.tempData);
	   			pageVariate.tempData ="";
	   			$(".hideDiv").hide(); 
	   		})
           
           /************************ 事件列表控制  ********************************/
         	//选择
			$(".serTit .li1 .check").click(function(){
				if(pageVariate.selectAllFlag==true){
					$('.serList ul li.s0 input').attr('checked',true);
					pageVariate.selectAllFlag=false;
				}else{
					$('.serList ul li.s0 input').attr('checked',false);
					pageVariate.selectAllFlag=true;
				}
			});
			
			//删除
			$('.btnDel').click(function(){
				if($('.serList ul li.s0 input:checked').length==0){
					hintManager.showHint("请选择要删除的项！");
				}else{
					confirm("是否确认删除？", function(){
						var checkID=new Array();
						$('.serList ul li.s0 input:checked').each(function(i,d){
							checkID[i]=d.id;	
						})
						listDeteleLinkage(tabsManager.list_node,checkID);
						$('.serList ul li.s0 input:checked').parents('.serUl').next('.serDetail').remove();//这个与下面的顺序不能变
						$('.serList ul li.s0 input:checked').parents('.serUl').remove();
					})
					
				}
			});
			//选择，全选和全不选
			$('.checkInvert.time').click(function(){
				var len = $('.serList ul li.s0 input:checked').length;
				var _this = this;
				if(len==0){
					$('.serList ul li.s0 input').attr('checked',true);
					$(_this).text("全不选");
				}else{
					$('.serList ul li.s0 input').each(function(){
						   $(this).attr("checked",!this.checked);              
				     });
				     $(_this).text("全选");
				}
			});	
			
   	})
  </script>
 <script type="text/javascript">//属性编辑
 	//隐藏遮罩层
 	$(".historytccBox").hide();
 
	//双击修改，可编辑
	$('.addDiv span b').live("dblclick",function(e){
		$('.aDiv').hide().siblings('b').show();
		$(this).hide();
		$(this).siblings('.aDiv').show(); 		
	});

	//取消
	$('.aDiv .nosureA').live("click",function(e){
		$('.aDiv').hide().siblings('b').show();
	});

	//确定
	$('.aDiv .sureA').live("click",function(e){
		var nowNum = $(this).parents('.aDiv').children('input').val();
		nowNum = nowNum.replace(/(^\s*)|(\s*$)/g, "");
		if(nowNum == "" ){
	  		hintManager.showHint("属性内容不能为空，请输入！");
			return;
		}
		var tempArr = $(this).attr("name").split(",");
		var vertexId = tempArr[0];
		var type = tempArr[1];
		var property = tempArr[2];
		var title = property+"："+nowNum;
		/* alert("nowNum:"+nowNum+",vertexId:"+vertexId+",type:"+type+",property:"+property); */
		if(type=="Phone"&&property=="phonenum"){
			if(!(/^1[3|4|5|6|7|8]\d{9}$/.test(nowNum))){
				hintManager.showHint("请输入正确的手机号码！");
				return;
			} 	
		}else if(type=="Email"||property=="email"){
			if(!(/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.(?:com|cn)$/).test(nowNum)){
				hintManager.showHint("请输入正确的邮箱！");
				return;
			}
		/* }else if(type=="Person"&&property=="idcard"){
			if(!((/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/).test(nowNum))){
				hintManager.showHint("请输入正确的身份证号码！");
				return;
			} */
		}else if(type=="Account"&&property=="regip"){
			if(!(/^(0|[1-9]?|1\d\d?|2[0-4]\d|25[0-5])\.(0|[1-9]?|1\d\d?|2[0-4]\d|25[0-5])\.(0|[1-9]?|1\d\d?|2[0-4]\d|25[0-5])\.(0|[1-9]?|1\d\d?|2[0-4]\d|25[0-5])$/).test(nowNum)){
				hintManager.showHint("请输入正确的ip地址！");
				return;
			}
		} 
		$(this).parents('.aDiv').parents('span').attr("title",title);
		$('.aDiv').hide().siblings('b').show();
		$(this).parents('.aDiv').siblings('b').text(nowNum);
		$(this).parents('.aDiv').siblings('b').show();
		
		$.ajax({
			type:"post",
			url:"ajaxHis/saveHistoryProperty.action",
			data:{
				"propertyHistory.vertexId":vertexId,
				"propertyHistory.type":type,
				"propertyHistory.value":nowNum,
			    "propertyHistory.property":property
			},
			dataType:"json",
			success:function(data){
				$(".node.n"+pageVariate.uuid).find("tspan").html(nowNum);
			},
			error:function(){
			 hintManager.showHint("编辑属性异常，请联系管理员！");
			}
 		});
	}); 
	
	/*查看历史记录*/
    $(".historyIcon").live("click",function() {
    	var tempArr = $(this).attr("name").split(",");
		var vertexId = tempArr[0];
		var type = tempArr[1];
		var property = tempArr[2];
        var ajData = {
            title: type+"-"+property+"属性的编辑历史记录",
            url: "query/viewHistoryPage.action?propertyHistory.vertexId="+vertexId+"&propertyHistory.property="+property,
            title1: ""
        };
        parentPop(ajData);
    });
	
	//点击其他地方
	$(document).bind("click",function(e){ 	
		var target = $(e.target); 	
		if(target.parents(".addDiv").length == 0){ 	
			$('.aDiv').hide().siblings('b').show();	
		} 	
	})    

	function parentPop(data) {
		$(".historytccBox").show();
		$(".historytccBox h3").text(data.title);
		$("#pop_iframe").attr("src", data.url);
	}
	
	/*关闭弹出层*/
	function dismissParentPop() {
		$(".historytccBox").hide();
		$("#pop_iframe").attr("src", "");
	}
	//add by hanxue start 导出
	function exportView(){
		//1.截图 保存截图2.获得数据集合3.生成数据集合
		var nodeIds = getPageVertexIds().join(",");
		var currDate = new Date();
		var currTimeStr = currDate.getTime();
		if(nodeIds!= null&&nodeIds!=''){
			confirm('确定要导出吗?',function(){
				var svgDiv = $("#svgImg").find("svg")[0];
				svgAsPngUri(svgDiv, null, function(uri) {
			        var data = {
	                    	viewImg:uri,//encodeURIComponent(uri)
	                  	    currentTimeStr:currTimeStr
			        };
			        $.ajax({
	                    url:"ajaxExpImgView/saveViewImg.action",//用于文件上传的服务器端请求地址
	                    type : "POST",
	                    data: data,
	                    dataType: 'json',
	                    async : false,
	                    success:function(data){
	                    	var url="ajaxExpView/exportViewObjs.action?currentTimeStr="+currDate.getTime()+"&ids="+nodeIds;
	                    	var bh = $("body").height(); 
	                        var bw = $("body").width(); 
	                    	$("#fullbg").css({ height:bh, width:bw, display:"block"}); 
	        				$("#exportingDiv").dialog("open");
	        				$.ajax( {
	        					type : "POST",
	        					url : url,
	        					success : function(data){
	        						var subDir = data.currentTimeStr;
	        					    var dlFileUrl = '<%=basePath%>fileManage/downloadFile.action?fileNameD=Views.zip&type=view&subDir='+subDir;
	        					    $("#downloadFrame").attr("src",dlFileUrl);
	        					    $("#exportingDiv").dialog("close");
	        						$("#fullbg").removeAttr('style');
	        					}
	        				});
	                    },
	                    error: function (data, status, e){
	                         parent.hintManager.showHint("图片上传异常，请联系管理员！");
	                    }
	                });
			    });
			});
		}else{
			parent.hintManager.showHint("节点为空时不可以导出");
		}
	}
	//add by hanxue end
 </script>

</body>
</html>
