<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'skip.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.min.js"></script>
  	<script>
	    window.parent.dismissParentPop();
	    var rootType = "${rootType}";
	    var action = "";
	    if(rootType){
	    	switch(rootType){
				case "case":
					action = "${relationAction.case_detailAction}";
					break;
				case "file":
					action = "${relationAction.file_detailAction}";
					break;
				case "host":
					action = "${relationAction.host_detailAction}";
					break;
				case "organization":
					action = "${relationAction.organization_detailAction}";
					break;
				case "people":
					action = "${relationAction.people_detailAction}";
					break;
				default:
					break;
			}
	    }else{
	    	action ="lawCarOrg/getOrgManagerPage.action";
	    }
	    $("#rightIframe",window.parent.document).attr("src", action);
  	</script>
  </body>
</html>
