<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ contextPath + "/";
	pageContext.setAttribute("base", contextPath);
%>
<title>数据导入-选择数据库连接</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link href="<%=basePath%>styles/css/common.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>styles/css/buttons.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>styles/css/dataImport/createDataImport.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>/styles/css/jquery-ui.min.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>/styles/css/ui.jqgrid.css" rel="stylesheet" type="text/css">

</head>
<style>
html {
	overflow-x: hidden;
	background: #012548;
}

.taskName{width:70%;  line-height: 30px;}
.noTitleStuff .ui-dialog-titlebar  {display:none}
.noTitleStuff .ui-dialog {
     bord:0px;
     overflow: hidden;
     background-color:#000000;
     background-color:rgba(0,0,0,0.0);  
     margin-left: 0px}
.ui-widget-content {
     overflow: hidden;
     background-color:#000000;
     background-color:rgba(0,0,0,0.0);  
     background-image:none;
     border:0px;
}
.noTitleStuff .ui-dialog-content{overflow: hidden;padding:0px;padding-top: 5px;padding-left: 1px}
</style>
<body style="height: 100%;" class="subBody">
<input type="hidden" id="baseUrl" value="${base}"/>
		<div
			style="padding:20px;width: 60%;height:210px;">
			<form action="<%=basePath%>nextDImpBTablePage.action" method="post" name="addForm" class="form" id="addDIForm">
				<table width="100%" height="200px" border="0" style="color: white;font-size: 14px">
				    <tr>
				        <td width="25%">&nbsp;</td>
						<td width="70%">
						<c:if test="${errorStr!=null}">
						<span class="errorMessage">${errorStr}</span>
						</c:if>
						</td>
					</tr>
					<tr>
						<td width="25%"><div align="right">任务名称：</div></td>
						<td width="70%"><input type="text" name="addForm.taskName" id="taskName" class="taskName" onblur="showInParentName(this.value)" value="${sessionScope.DataImport_Add_Task.taskName}" style="width: 60%;height: 25px"/></td>
					</tr>
					<tr>
						<td width="25%"><div align="right">任务类型：</div></td>
						<td width="70%">
						  <select name="addForm.taskType" id="taskType"  style="width: 60%;height: 25px" onchange="changeTaskTypeDiv(this.value)">
						    <option selected="selected" value="1">数据库导入</option>
						    <option value="2">.eml上传文件导入</option>
						  </select>
						  </td>
					</tr>
					<tr>
						<td width="25%"><div align="right" ><span id="typeTitle">选择数据库连接：</span></div></td>
						<td width="70%">
						<div id="typeInput">
						 <select id="connectionDBselect"
							 name="addForm.dbConnId" style="width: 60%;height: 25px"
							 onchange="showInParentDIConf(this.value)" >
						 </select>
						</div>
						<input type="hidden" id="updateFlagId"
							name="updateFlag" value="${updateFlag}"/>
						<input type="hidden" id="taskId"
							name="taskId" value="${taskId}"/>
						<input type="hidden" id="rebackFlagId"
							name="rebackFlag" value="${rebackFlag}"/>
						</td>
					</tr>
					<c:if test="${updateFlag=='1'&&sessionScope.DataImport_Add_Task.taskType!=2}">
					<tr>
						<td colspan="2" width="70%"><div align="center" style="width: 100%">
								   <span style="font-size: 12px;color: orange;"> 修改导入任务的数据库连接，会重置当前任务的所有配置</span>							
						</div></td>
					</tr>
					</c:if>
					<tr>
					    <td width="25%"><div align="right">&nbsp;</div></td>
						<td width="70%" style="padding-top: 20px">
						       <div id="nextButtonDiv" >
								<input type="button" class="button white"  name="nextButton" id="firstAddButton"
									value="下一步" /></div>
								<div id="finDiv">
								<input type="button" class="button white" name="nextButton"
												id="next4" value="完成" /></div>
							</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="fullbg"></div> 
		<div id="loadingDiv">
			<div  class="dataLoad" >
			     <p>正在获取数据库信息...</p><br/>
			     <img alt="Loading" src="<%=basePath%>images/img/4.gif">
			</div>
		</div>
	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.js"></script>
	<script type="text/javascript" src="<%=basePath%>/scripts/ui/jquery-ui.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/scripts/dataImport/dataImportCreateI.js"></script>
	<script type="text/javascript">
	   var dataBaseMap = "";
		var updateFlag = "${updateFlag}";
		$(document).ready(function() {
	         window.parent.window.changeDataTitle("数据导入-选择数据库连接");
	         $("#loadingDiv").dialog({
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
	              overlay: {opacity: 0.5,overflow:'auto',backgroundColor: '#fff',},
	             open: function() {
                                     var win = $(window);
                                     $(this).parent().css({   position:'absolute',
                                            left: (win.width() - $(this).parent().outerWidth())/2,
                                            top: (win.height() - $(this).parent().outerHeight())/2
                                     });
                                     var bh = $("body").height(); 
                                     var bw = $("body").width(); 
                                     $("#fullbg").css({ height:bh, width:bw, display:"block"  }); //$("#fullbg,#dialog").hide(); 
                             }
	             });
	       /*  if("${errorStr}"!=null&&"${errorStr}"!=""&&"${errorStr}"!=undefined&&"${errorStr}"!="null"||"1"=="${rebackFlag}"){
	            getDataUrl();
	        } */
	        getDataUrl();
	        $("#nextButtonDiv").css("display","inherit");
	        $("#finDiv").css("display","none");
	        try{window.parent.window.showDataImpDiv();}catch(e){}
	        if(updateFlag=="1"){
	           showInParentName($("#taskName").val());
	           //showInParentDIConf($("#connectionDBselect").val());
	         var jsTaskType =  "${sessionScope.DataImport_Add_Task.taskType}";
	           if(jsTaskType=="2"){
	                  $("#taskType").val(2);
	                  changeTaskTypeDiv(2);
	           }else{
	                  var sqlFlag = "${sessionScope.DataImport_Add_Task.sqlFlag}";
	                  if("true"==sqlFlag){
	                       window.parent.window.showSourceInfo("${sessionScope.DataImport_Add_Task.sqlText}");
	                  }else{
	                       window.parent.window.showSourceInfo("${sessionScope.DataImport_Add_Task.sourceTable}");
	                  }
	                  window.parent.window.showTargetInfo("${sessionScope.DataImport_Add_Task.targetTable}");
	           
	                  var checkSFields = "${sessionScope.DataImport_Add_Task.checkSourceFields}";
	                  var checkSFieldsType = "${sessionScope.DataImport_Add_Task.checkSourceFieldsType}";
			          var checkTFields = "${sessionScope.DataImport_Add_Task.checkTargetFields}";
			          var checkTFieldsType = "${sessionScope.DataImport_Add_Task.checkTargetFieldsType}";
			          var checkSArray = checkSFields.split(",");
			          var checkSTArray = checkSFieldsType.split(",");
			          var checkTArray = checkTFields.split(",");
			          var checkTTArray = checkTFieldsType.split(",");
			          var tempStr = "";
			          for (var i = 0; i < checkSArray.length; i++) {
			              tempStr = tempStr
				       		+ $.trim(checkSArray[i])
				       	    + "("
					       	+ $.trim(checkSTArray[i])
					       	+ ") -->  "
					       	+ $.trim(checkTArray[i])
					       	+ "("
					       	+ $.trim(checkTTArray[i])
					       	+ ") "
					       	+ "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				       window.parent.window.showFieldInfo(tempStr);
	           }
	           }
	        }
	    });//页面ready操作完成
	    
	    //根据任务类型转换页面
	    function changeTaskTypeDiv(typeVal){
	      if(typeVal=='2'){
	         $("#typeTitle").html("选择.eml文件路径：");
	         var innerHtmlEmlStr = "<select id='uploadEmlSelect' name='addForm.uploadFile' "
	                                            +"style='width: 60%;height: 25px'"
                                                +"onchange='showInParentEmlPath(this.value)'></select>";
	         $("#typeInput").html(innerHtmlEmlStr);
	         getUploadFileDir();
	         $("#nextButtonDiv").css("display","none");
	         $("#finDiv").css("display","inherit");
	         try{window.parent.window.showEmlImpDiv();}catch(e){}
	      }else{
	         /* $("#typeTitle").html("");
	         $("#typeInput").html(""); */
	         $("#typeTitle").html("选择数据库连接：");
	         var innerHtmlConnStr = "<select id='connectionDBselect' name='addForm.dbConnId' "
	                                            +"style='width: 60%;height: 25px'"
	                                            +"onchange='showInParentDIConf(this.value)'></select>";
	         $("#typeInput").html(innerHtmlConnStr);
	         getDataUrl();
	         $("#nextButtonDiv").css("display","inherit");
	         $("#finDiv").css("display","none");
	         try{window.parent.window.showDataImpDiv();}catch(e){}
	      }
	    }
	    
	    //获得文件夹路径列表
	   function getUploadFileDir(){
	       $("#uploadEmlSelect").attr("disabled","disabled");
		   $("#firstAddButton").attr("disabled","disabled");
		   $("#uploadEmlSelect").empty();
		   $("#uploadEmlSelect").append("<option value='' selected='selected'>请选择已上传文件所在目录</option>"); 
	       $.ajax({
		        url: "${base}/ajax_dataImport/getUploadFileDir.action", 
		        type: "post",
		        dataType: "json",
		        async: true,
		        success: function(data){
		            var map = data.uploadFileJson;
		            if($(map).size()>-1){
		                 $.each(map,function(key,value){     
		                    $("#uploadEmlSelect").append("<option value='"+value+"'>"+key+"</option>");      
                         });  
                         $("#uploadEmlSelect").removeAttr("disabled");
                          $("#firstAddButton").removeAttr("disabled");
                          var uploadFileVar = "${sessionScope.DataImport_Add_Task.uploadFile}";
                          try{$("#uploadEmlSelect").val(uploadFileVar);}catch(e){}
                          var selText = $('#uploadEmlSelect  option:selected').text();
                          window.parent.window.showEmlPath(selText);
		            }else{
		                 window.parent.window.hintManager.showHint("无法获得上传文件路径列表");
		            }
			    }
			});
	    }
	    
	    //回显数据库连接信息
		function showInParentDIConf(selVal) {
			/* if (selVal == "" || selVal == undefined) {
			} else {
				var selectText = $("#connectionDBselect").find(
						"option:selected").text();
				$("#connectionDBselName").val(selectText);
			} */
			window.parent.window.showConnInfo(dataBaseMap, selVal);
			window.parent.window.clearOther();
		}
		
		//回显任务名称
		function showInParentName(val){
		    window.parent.window.showNameInfo(val);
		}
		
		//回显eml文件信息
		function showInParentEmlPath(selVal) {
		    var selText = $('#uploadEmlSelect  option:selected').text();
		    if(null==selText||selText==undefined){
		       window.parent.window.showEmlPath(selVal);
		    }else{
		        window.parent.window.showEmlPath(selText);
		    }
			
		}
		//获得数据库连接列表
		function getDataUrl(){
		   $("#connectionDBselect").attr("disabled","disabled");
		   $("#firstAddButton").attr("disabled","disabled");
		   $("#connectionDBselect").empty();
		   $("#connectionDBselect").append("<option value='' selected='selected'>请选择</option>"); 
	       $.ajax({
		        url: "${base}/ajax_dataImport/getDataUrl.action", 
		        type: "post",
		        dataType: "json",
		        async: true,
		        success: function(data){
		            var map = data.dataBaseJson;
		            dataBaseMap = data.dataBaseJson;
		            if($(map).size()>-1){
		                 $.each(map,function(key,value){     
		                    $("#connectionDBselect").append("<option value='"+value.id+"'>"+value.connectionName+"</option>");      
                         });  
                         $("#connectionDBselect").removeAttr("disabled");
                          $("#firstAddButton").removeAttr("disabled");
                         try{$("#connectionDBselect").val("${sessionScope.DataImport_Add_Task.dbConnId}");}catch(e){}
                         window.parent.window.showConnInfo(dataBaseMap, "${sessionScope.DataImport_Add_Task.dbConnId}");
		            }else{
		                 window.parent.window.hintManager.showHint("无法获得数据库连接列表");
		            }
			    }
			});
		}
	</script>
</body>

</html>
