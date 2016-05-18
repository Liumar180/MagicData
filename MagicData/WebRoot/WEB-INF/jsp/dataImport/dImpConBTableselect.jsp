<%@ page language="java"
	import="java.util.*,com.integrity.dataSmart.common.DataType" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'DatabaseCon.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<!-- CSS Bootstrap & Custom -->
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
/*11月5日--马 */
#sqlSpan{
	width:70%;
	display:inline-block;
	margin-top: 5px;
}
#sqlSpan textarea{
	width:100%;
}
#sqlSpan .button{
	margin-top:10px;
	margin-bottom:10px;
}
.select{
	width:70%;
	height:30px;
}
/* 结束 */
.noTitleStuff .ui-dialog-titlebar  {display:none}
.noTitleStuff .ui-dialog {
     bord:0px;
     overflow: hidden;
     background-color:#000000;
     background-color:rgba(0,0,0,0.0);  
     margin-left: 0px;
     }
.ui-widget-content {
     overflow: hidden;
     background-color:#000000;
     background-color:rgba(0,0,0,0.0);  
     background-image:none;
     border:0px;
}

.noTitleStuff .ui-dialog-content{overflow: hidden;padding:0px;padding-top: 5px;padding-left: 1px}
</style>
<body style="height: 100%" class="subBody">
		<div
			style="padding:20px;width: 60%;height:210px;">
			<form action="<%=basePath%>nextDImpCFieldPage.action" method="post" name="addForm2" class="form form1" id="addDIIForm">
				<table width="100%" height="100%" border="0" cellspacing="10px" cellpadding="20px" style="color: white;font-size: 14px">
					<tr>
						<td width="25%"><div align="right">任务名称：</div></td>
						<td width="70%">${sessionScope.DataImport_Add_Task.taskName}</td>
					</tr>
					<tr>
						<td width="25%"><div align="right">选择连接：</div></td>
						<td width="70%">${sessionScope.DataImport_Add_Task.dbConnName}
						<input type="hidden" value="${sessionScope.DataImport_Add_Task.dbConnId}" name="addForm.dbConnId"
							id="dbConnId">
						<input type="hidden" value="${sessionScope.DataImport_Add_Task.dbConnName}" name="addForm.dbConnName"
							id="dbConnName">
						<input type="hidden" value="${sessionScope.DataImport_Add_Task.taskName}" name="addForm.taskName"
							id="dbTaskName">
						<input type="hidden" value="${sessionScope.DataImport_Add_Task.taskType}" name="addForm.taskType"
							id="dbTaskType">
						<input type="hidden" value="${sessionScope.DataImport_Add_Task.checkSourceFields}" name="addForm.checkSourceFields"/>
						<input type="hidden" value="${sessionScope.DataImport_Add_Task.checkTargetFields}" name="addForm.checkTargetFields"/>
						<input type="hidden" id="updateFlagId" name="updateFlag" value="${updateFlag}"/>	
						<input type="hidden" id="taskIdId" name="taskId" value="${taskId}"/>	
						</td>
					</tr>
					<tr>
						<td  width="25%"><div align="right">源数据库表：</div></td>
						<td width="70%"><select id="connectionTableselect"
							style="width:60%;height:25px" name="addForm.sourceTable"
							onchange="window.parent.window.showSourceInfo(this.value)">
							<option selected="selected" value="">未选择</option>
							<c:forEach items="${sessionScope.DataImport_Add_Task.tableFields}" var="tableFields">
							<option value="${tableFields.key}">${tableFields.key}</option>
							</c:forEach>
							</select>
					    </td>
					</tr>
					<tr>
						<td width="25%"></td>
						<td width="70%"><input type="checkbox" id="sqlFlagBox"
							name="addForm.sqlFlag" value="1" onclick="changeSQL()" />添加自定义SqL(需测试成功)<span
							id="sqlSpan"></span></td>
					</tr>
					<tr>
						<td width="25%"><div align="right">目标数据源：</div></td>
						<td width="70%"><select id="targetTableselect" style="width:60%;height:25px" class="select" name="addForm.targetTable" onchange="window.parent.window.showTargetInfo(this.value)">
								<option selected="selected" value="">未选择</option>
								<option value="<%=DataType.IMPORTDATAEMAIL%>"><%=DataType.IMPORTDATAEMAIL%></option>
								<option value="<%=DataType.IMPORTDATAACCOUNT%>"><%=DataType.IMPORTDATAACCOUNT%></option>
								<option value="<%=DataType.IMPORTDATAPHONE%>"><%=DataType.IMPORTDATAPHONE%></option>
								<option value="<%=DataType.IMPORTDATAQQ%>"><%=DataType.IMPORTDATAQQ%></option>
								<option value="<%=DataType.IMPORTDATAHOTEL%>"><%=DataType.IMPORTDATAHOTEL%></option>
						</select></td>
					</tr>
					<tr>
					    <td width="25%"><div align="right">&nbsp;</div></td>
						<td width="70%" style="padding-top: 20px">
							
								<input type="button" class="button white" id="before1"
									value="上一步"  />
									<input type="button" class="button white" name="nextButton" value="下一步" id="secAddButton" />
							
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="fullbg"></div> 
		<div id="loadingDiv" align="center">
			<div  class="dataLoad" style="width:290px;margin-left: 7%">
			     <span id="dataLoadSpan" ></span><br/>
			     <img alt="Loading" src="<%=basePath%>images/img/4.gif" >
			</div>
		</div>
</body>
<script src="<%=basePath%>scripts/modernizr.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/ui/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/dataImport/dataImportCreateII.js"></script>
<script type="text/javascript">
  $().ready(function() {
	    document.getElementById("targetTableselect").options[0].selected = true;
	    window.parent.window.changeDataTitle("数据导入-选择数据源");
	    $("#loadingDiv").dialog({
	              dialogClass: 'noTitleStuff' ,
	              hide:true,
	              autoOpen:false,	   
	              width:350,
	              height:170,
	              resizable : false,
	              modal:false,
	              title:"",
	              draggable:false,
	              bgiframe: true,
	              overlay: {opacity: 0.5,overflow:'auto',backgroundColor: '#fff',},
	             open: function() {
	                                 document.getElementById("dataLoadSpan").innerHTML = "<p>正在测试SQL语句...</p>";
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
	    $('#before1').click(function() {
			    addDIIForm.action = "<%=basePath%>getDImpAConPage.action?rebackFlag=1";
			    addDIIForm.submit();
		});
	    var sqlFlag = "${sessionScope.DataImport_Add_Task.sqlFlag}";
	    var source = "${sessionScope.DataImport_Add_Task.sourceTable}";
	    var target = "${sessionScope.DataImport_Add_Task.targetTable}";
	    if(target!=""){
	       $("#targetTableselect").val(target);
	    }
	    if(sqlFlag=="true"){
	       $("#sqlFlagBox").attr("checked","checked");
	       changeSQL();
	       $("#secAddButton").removeAttr("disabled");//对于已保存的SQL无需测试
	       $("#sqlTextid").text("${sessionScope.DataImport_Add_Task.sqlText}");
	    }else if(source!=""){
	       $("#connectionTableselect").val(source);
	    }
	});
  function disSecAddButt(){
         $("#secAddButton").attr("disabled","disabled");
         $("#sqlTestId").removeAttr("disabled");
         document.getElementById("sqlTestRspan").innerHTML = "";
  }
  function changeSQL(){
      if($("#sqlFlagBox").attr("checked")=="checked"){
         document.getElementById("connectionTableselect").options[0].selected = true;
         $("#connectionTableselect").attr("disabled","disabled");
         $("#secAddButton").attr("disabled","disabled");
         var innerH='<textarea id="sqlTextid" name="addForm.sqlText" rows="3" onblur="window.parent.window.showSourceInfo(this.value)" onkeypress="window.parent.window.showSourceInfo(this.value)" onchange="disSecAddButt()"></textarea><input type="button" name="Submit32" id="sqlTestId"value="测试" class="button white" onclick="sqltest()"/><span id="sqlTestRspan"></span>';
	     document.getElementById("sqlSpan").innerHTML =innerH;
      }else{
         $("#connectionTableselect").removeAttr("disabled");
         $("#secAddButton").removeAttr("disabled");
         var innerH='';
	     document.getElementById("sqlSpan").innerHTML =innerH;
      }
	  
  }

  function sqltest(){
	  var databaseid= $('#dbConnId').val();
	  var sqltest=$('#sqlTextid').val();
	  $("#loadingDiv").dialog("open");
	  $.ajax({
	         type:"post",
	         url:"<%=basePath%>sqlTest.action",
	         async: true,
			data : {
				dbConnId: databaseid,
				sqlTest : sqltest
			},
			dataType : "json",
			success : function(data) {
			    var res = data.rows[0].result;
			    if("SUCCESS"==res){
			        document.getElementById("dataLoadSpan").innerHTML = "<p>SQL语句测试成功</p>";
			        document.getElementById("sqlTestRspan").innerHTML = "SQL语句测试成功";
			        $("#secAddButton").removeAttr("disabled");
			        
			        setInterval(function(){
			           $("#fullbg").hide(); 
			           $("#loadingDiv").dialog("close"); 
			           $("#sqlTestId").attr("disabled","disabled");
			        },2000);
			    }else{
			        document.getElementById("dataLoadSpan").innerHTML = "<p>SQL语句测试失败</p>";
			        document.getElementById("sqlTestRspan").innerHTML = "SQL语句测试失败";
				    setInterval(function(){
			           $("#fullbg").hide(); 
			           $("#loadingDiv").dialog("close"); 
			           $("#sqlTestId").removeAttr("disabled");
			        },2000);
			    }
			},
			error : function() {
				 document.getElementById("dataLoadSpan").innerHTML = "<p>SQL语句测试失败</p>";
				 document.getElementById("sqlTestRspan").innerHTML = "SQL语句测试失败";
				 setInterval(function(){
			           $("#fullbg").hide(); 
			           $("#loadingDiv").dialog("close"); 
			           $("#sqlTestId").removeAttr("disabled");
			     },2000);
			}
		});

	}
</script>
</html>
