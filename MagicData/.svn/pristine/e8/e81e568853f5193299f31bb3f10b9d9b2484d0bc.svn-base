<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>WeelMenu</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!-- 调试用
	<script type="text/javascript" src="scripts/jquery.min.js"></script>
	<script type="text/javascript" src="scripts/jquery.wheelmenu.js"></script> 
	-->
	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/styles/css/menuStyle.css" />

 </head>
  
  <body align="left">
  <table width="700" height="100%">
    <tr>
    <td valign="top" width="100%" height="100%">
    <div class="main" align="left" style="margin-left:260px">
		  <a class="wheel-button" id="#wheel" ></a>
	      <ul id="wheel">
	        <c:forEach items="${requestScope.menuList}" var="entry" varStatus="status">
	           <li class="item" style="text-align:center;text-align: center;">
	               <div class="wheel-button${status.index}" id="#subWheel${status.index}" >
	                       <a class="levelOneA" onclick="menuClick('${entry.hrefString}')" >  </a>
	                        <i class="${entry.icoClassString}" id="subWheelLi${status.index}" style="width: 69px;height: 114px"></i>
	               </div>
	             <c:if test="${entry.hasSubFlag}">
	             <ul id="subWheel${status.index}" data-angle="${entry.angleString}" class="ulLv2">
	              <c:forEach items="${entry.subMenuPojos}" var="subEntry" varStatus="subStatus">
	                <li class="item" style="text-align:center;text-align: center;">
	                     <div class="wheel-button${status.index}${subStatus.index}" id="#subWheel${status.index}${subStatus.index}" style="width: 64px;height: 134px">
	                       <a class="levelOneB"  onclick="menuClick('${subEntry.hrefString}')" >  </a>
	                        <i class="${subEntry.icoClassString}"  id="subWheelLi2${status.index}${subStatus.index}" style="width: 64px;height: 134px"></i>
	                    </div>
	                     <%-- 
	                     <a class="wheel-button${status.index}${subStatus.index}" id="#subWheel${status.index}${subStatus.index}"  onclick="menuClick('${subEntry.hrefString}')" style="width: 64px;height: 134px">
	                     <i class="${subEntry.icoClassString}"  id="subWheelLi2${status.index}${subStatus.index}" style="width: 64px;height: 134px"></i>
	                    </a>
	                    --%>  
	                    <c:if test="${subEntry.hasSubFlag}">
	                         <ul id="subWheel${status.index}${subStatus.index}" data-angle="${subEntry.angleString}" class="ulLv3">
	                              <c:forEach items="${subEntry.subMenuPojos}" var="sSubEntry" varStatus="sSubStatus">
	                                 <li class="item">
	                                    <a  onclick="menuClick('${sSubEntry.hrefString}')"  style="width: 80px;height: 80px">
	                                         <i class="${sSubEntry.icoClassString}"  id="subWheelLi3${status.index}${subStatus.index}${sSubStatus.index}"  style="width: 80px;height: 80px"  onmouseover="changeClass(this.id,1)" onmouseout="changeClass(this.id,0)"></i>
	                                    </a>
	                                 </li>
	                              </c:forEach>
	                         </ul>
	                    </c:if>
	                </li>
	              </c:forEach>
	              </ul>
	              </c:if>
	           </li>
	        </c:forEach>
	      </ul>
	      
	    </div>
    </td>
    </tr>
    
  </table>
<%-- <div style="display: none;">
<c:forEach items="${requestScope.imgNames}"  var="entryImg" >
<img alt="" src="<%=basePath%>images/menu/${entryImg}">
</c:forEach>
</div>	 --%>
	<script type="text/javascript">
	
	    var rootButton = $(".wheel-button");
	    var rootWheelR= 79;
    	var rootnumber = ${fn:length(requestScope.menuList)};    	
    	
     	/*调试用
     	$(".wheel-button").wheelmenu({
     	    trigger: "click",
     	    animationSpeed:"fast",
     	    liSize:rootnumber,
     	    angle:[0,360]
     	},rootWheelR); 
     	*/
        
    	<c:forEach items="${requestScope.menuList}" var="jsData" varStatus="jsStatus">
	           $(".wheel-button${jsStatus.index}").subWheelmenu({
	                 trigger: "click",
      		         animationSpeed:"fast",
      		         level: 2,
      		         rParam:0.95,
      		         subIds:"${jsStatus.index}",
      		         hasSub:"${jsData.hasSubFlag}",
      		         length:${fn:length(requestScope.menuList)}
    	       },rootButton,rootWheelR);
    	    
    	       <c:if test="${jsData.hasSubFlag}">
    	         <c:forEach items="${jsData.subMenuPojos}" var="jsSubData" varStatus="jsSubStatus">
    	           $(".wheel-button${jsStatus.index}${jsSubStatus.index}").subWheelmenu({
	                   trigger: "click",
      		           animationSpeed:"fast",
      		           level: 3,
      		           rParam:0.95,
      		           subIds:"${jsStatus.index}${jsSubStatus.index}",
      		           hasSub:"${jsData.hasSubFlag}",
      		           length:${fn:length(jsData.subMenuPojos)}
    	            },rootButton,rootWheelR);
    	         </c:forEach>
    	      </c:if>   
    	      
	    </c:forEach>
/*      $(document).ready(function() {
         var imgnames = '${requestScope.imgNames}';
         preloadImages(imgnames);
     });
function preloadImages(arguments) {
  for (var i = 0; i < arguments.length; i++) {
    $("<img />").attr("src", arguments[i]);
  }
} */
  	</script>
    
  </body>
</html>
