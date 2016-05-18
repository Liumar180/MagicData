<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>


<html>
 <link href="styles/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
     <script type="text/javascript" src="scripts/jquery.min.js"></script>
  <script src="styles/bootstrap/js/bootstrap.min.js"></script>
  <script src="styles/bootstrap/js/bootstrap-prompts-alert.js"></script>
	<script language="javascript">
		<c:if test='${not empty resultmap.resultMsg}'>
			$(function(){
		   		confirm('<c:out value="${resultmap.resultMsg}"/>',function(){
		   			window.location.href="${resultmap.resultjsp}";
		   		},function(){
		   			window.location.href="${resultmap.resultjsp}";
		   		});
			});
		</c:if>
	</script>
</html>