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
</head>
<style>
html {
	overflow-x: hidden;
	background: #012548;
}

select {
	margin-top: 5px;
}
#lastForm td{
	line-height: 30px;
}
#lastForm td .tdSpan{
	display: inline-block;
    overflow: hidden;
    text-align: left;
    text-overflow: ellipsis;
    width: 90%;
    word-spacing: normal;
}

#lastForm td input[type="checkbox"] {    
    margin-top: -20px;
}
@-moz-document url-prefix() { #lastForm td input[type="checkbox"] {margin-top: -17px;}}

</style>
<body class="subBody">
	<div style="padding:15px;width: 50%;height:100%;">
		<table width="100%" height="100%" border="0">
			<tr>
				<td width="50%" valign="top">
					<div style="margin-left: 10px;padding:0px;width: 100%;height:100%;">
						<form action="" id="lastForm" method="post" name="addForm3">
							<table width="80%" height="292" border="0" style="color: white;font-size: 14px"
								cellpadding="10" cellspacing="5">
								<tr>
									<td width="15%" align="right">任务名称：</td>
									<td width="70%">${sessionScope.DataImport_Add_Task.taskName}</td>
								</tr>
								<tr>
									<td width="15%" align="right">选择连接：</td>
									<td>${sessionScope.DataImport_Add_Task.dbConnName}<input
										type="hidden" id="updateFlagId" name="updateFlag"
										value="${updateFlag}" /> <input
										type="hidden" id="taskIdId" name="taskId"
										value="${taskId}" /> <input type="hidden"
										value="${sessionScope.DataImport_Add_Task.dbConnId}"
										name="addForm.dbConnId" id="dbConnId"> <input
										type="hidden"
										value="${sessionScope.DataImport_Add_Task.dbConnName}"
										name="addForm.dbConnName" id="dbConnName"> <input
										type="hidden"
										value="${sessionScope.DataImport_Add_Task.taskName}"
										name="addForm.taskName" id="dbTaskName">
										<input type="hidden" value="${sessionScope.DataImport_Add_Task.taskType}" name="addForm.taskType"
							id="dbTaskType">
									</td>
								</tr>
								<tr>
									<td width="15%" align="right">源数据：</td>
									<td><c:if
											test="${sessionScope.DataImport_Add_Task.sqlFlag}">
										${sessionScope.DataImport_Add_Task.sqlText}
									    </c:if> <c:if test="${!sessionScope.DataImport_Add_Task.sqlFlag}">
										${sessionScope.DataImport_Add_Task.sourceTable}
									    </c:if></td>
								</tr>
								<tr>
									<td width="15%" align="right">目标数据：</td>
									<td>${sessionScope.DataImport_Add_Task.targetTable}</td>
								</tr>
								<tr>
									<td colspan="2" style="line-height:10px;">&nbsp;</td>
								</tr>
								<tr>
									<td colspan="2"><table width="100%" border="0" style="color: white;font-size: 14px;" cellpadding="0" cellspacing="0" >
											<tr style="">
												<td width="33%"><div class="fieldsTitle">源字段名</div></td>
												<td width="28%"><div class="fieldsTitle">源字段类型</div></td>
												<td><div class="fieldsTitle">目标字段</div></td>
											</tr>
											<tr>
												<td colspan="3" style="line-height:8px;">&nbsp;</td>
											</tr>
											<c:forEach
												items="${sessionScope.DataImport_Add_Task.sourceFields}"
												var="sFields" varStatus="status">
												<tr>
													<td><input type="checkbox"
														name="addForm.checkSourceFields"
														id="CheckBox${status.index}" value="${sFields.key}"
														onclick="changSel(${status.index})" /><span class="tdSpan">${sFields.key}</span></td>
													<td><span id="Type${status.index}">${sFields.value}</span></td>
													<td><select style="width:70%;height:25px" name="addForm.checkTargetFields"
														onchange="showInParentsFields(this.id,1)" 
														id="${status.index}">
															<option selected="selected" value="">未选择</option>
															<c:forEach
																items="${sessionScope.DataImport_Add_Task.targetFields}"
																var="tFields">
																<option value="${tFields.key}">${tFields.key}
																	(${tFields.value})</option>
															</c:forEach>
													</select></td>
												</tr>
											</c:forEach>
										</table></td>
								</tr>
								<tr>
									<td width="25%"><div align="right">&nbsp;</div></td>
						            <td width="70%" style="padding-top: 20px">
											<input type="button" class="button white" id="before3"
												value="上一步"" />
											<input type="button" class="button white" name="nextButton"
												id="next4" value="完成" />
										</td>
								</tr>
							</table>
						</form>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.js"></script>
	<script type="text/javascript">
	var checkTargetMap = new Array();
		$(document).ready(function() {
			var selects = document.getElementsByTagName("select");
			$.each(selects, function() {
			    ($(this)).attr("disabled", "disabled");
			});
			window.parent.window.changeDataTitle("数据导入-选择字段关系");
			$('#before3').click(function() {
			    addForm3.action = "<%=basePath%>nextDImpBTablePage.action";
			    addForm3.submit();
			});
			$('#next4').click(function() {
			    var checkFields = $("form :input[type='checkbox']");
			    var checkflag = false;
			    var checkId = "";
			    $.each(checkFields, function() {
			        if(($(this)).attr("checked")=="checked"){
			            checkflag = true;
			            checkId = ($(this)).attr("id");
			        }
			    });
			    
			    if(checkflag){
			       var tCheckId = checkId.substring(8,checkId.length);
			       if($("#"+tCheckId).val()!=""&&$("#"+tCheckId).val()!=undefined){
			           addForm3.action = "<%=basePath%>nextDImpDFinalPage.action";
				       addForm3.submit();
			       }else{
			          window.parent.window.hintManager.showHint("字段对应关系选择不合规");
			       }
			    }else{
			          window.parent.window.hintManager.showHint("字段对应关系选择不合规");
			    }
			    
			});

			var checkSFields = "${sessionScope.DataImport_Add_Task.checkSourceFields}";
			var checkTFields = "${sessionScope.DataImport_Add_Task.checkTargetFields}";
			var checkSArray = checkSFields.split(",");
			var checkTArray = checkTFields.split(",");
			$.each($("input[type='checkbox']"),function() {
					for (var i = 0; i < checkSArray.length; i++) {
						if ($(this).val() == checkSArray[i].trim()) {
							($(this)).attr("checked","checked");
							var checkId = ($(this)).attr("id");
							var selId = checkId.substring(8,checkId.length);
							$("#" + selId).removeAttr("disabled");
							$("#" + selId).val(checkTArray[i].trim());
							checkSArray[i] = "";
							showInParentsFields(selId,1);
						}
					}
			});
		});
		
		function changSel(id) {
			var checkBoxFlag = $("#" + "CheckBox" + id).attr("checked");
			if (checkBoxFlag == "checked") {
				$("#" + id).removeAttr("disabled");
			} else {
				$("#" + id).attr("disabled", "disabled");
				$("#" + id).val("");
				showInParentsFields(id, 0);
			}
		}
		var configLists = new Array();
		function checkFieldsConfig(id, sourceFName, sourceFType, targetFields) {
			this.id = id;
			this.sourceFName = sourceFName;
			this.sourceFType = sourceFType;
			this.targetFields = targetFields;
		}
		function showInParentsFields(id, flag) {
		    if($.inArray($("#" + id).val(),checkTargetMap)!=-1){
		         window.parent.window.hintManager.showHint("该对应字段已经选择过");
		         $("#" + id).val("");
		         checkTargetMap[id] = "";
		    }else{
		        checkTargetMap[id] = $("#" + id).val();
		    }
		    
			//先查询是否已经存在对象，存在则替换
			var exsitFlag = false;
			var conIndex = 0;
			for (var i = 0; i < configLists.length; i++) {
				var tempData = configLists[i];
				if (tempData.id == id) {
					exsitFlag = true;
					conIndex = i;
					tempData.sourceFName = $("#" + "CheckBox" + id).val();
					tempData.sourceFType = $("#" + "Type" + id).text();
					tempData.targetFields = $("#" + id).find("option:selected")
							.text();
				}
			}
			if (flag == 1) {
				if (!exsitFlag) {
					var checkBoxFlag = $("#" + "CheckBox" + id).attr("checked");
					if (checkBoxFlag == "checked") {
						var tempConf = new checkFieldsConfig(id, $("#" + "CheckBox" + id).val(), $("#" + "Type" + id).text(), $("#" + id).find("option:selected").text());
						configLists.push(tempConf);
					}
				}
			}else if(flag==0){
			   configLists.splice(conIndex,1);
			}
			var tempStr = "";
			for (var j = 0; j < configLists.length; j++) {
				var confData = configLists[j];
				tempStr = tempStr
						+ $.trim(confData.sourceFName)
						+ "("
						+ $.trim(confData.sourceFType)
						+ ") -->  "
						+ $.trim(confData.targetFields)
						+ "<br/>";
			}
			window.parent.window.showFieldInfo(tempStr);
			//}
		}
	</script>
</body>

</html>