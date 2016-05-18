<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
    <link href="<%=basePath%>/styles/css/common.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>/styles/css/buttons.css" rel="stylesheet" type="text/css" />
    <script src="scripts/modernizr.js"></script>
    <script type="text/javascript" src="<%=basePath%>/scripts/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.wheelmenu.js"></script>
	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.subWheelmenu.js"></script>
    <script type="text/javascript" src="<%=basePath%>/scripts/jquery.toolWheelmenu.js"></script>
	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.js"></script>
	
	
  </head>
  <style>
  .div1{display:block; float:left;border:0px solid #000000; width:100%; height:88%;}
  .div2{display:block; float:left;border:0px solid #F00; width:94.5%; background:#F2F2F2;text-align:center}
  .div3{display:block; float:left;border:0px solid #7171C6; width:100%;}
  body{ text-align:center}
  </style>
  <script type="text/javascript">
  function nextStep(){
	  //$('#tableSelectedListform').submit();
  }
  function beforeStep(){
	  //$('#tableSelectedListform').submit();
	  window.navigate("<%=basePath%>databaseConnectedTableselect.action"); 
	  //window.history.back(-1); 
  }
  function selectTable(){
	  var trs = document.getElementById('columnlisttbody').getElementsByTagName('tr');  
	  var innerH='';
	  for( var i=0; i<trs.length; i++ ){
		  if(trs[i].style.backgroundColor == 'cornflowerblue'){
			  innerH+="<tr>"+trs[i].innerHTML+"</tr>";
		  }
	  }  
	  document.getElementById("selectedcolumnlisttbody").innerHTML =innerH;
  }
  function selectTr(obj){
	  if(obj.style.backgroundColor == 'cornflowerblue'){obj.style.backgroundColor = 'white';}else{obj.style.backgroundColor = 'cornflowerblue';}
  }
  function getTableList(obj){
	  var connectionID=$(obj).children('option:selected').val();
	  alert(connectionID);
	  $.ajax({
	         type:"post",
	         url:"<%=basePath%>ImportGetPropertysByTables.action",
	         data:{
	        	 dbid:connectionID
	         },
	         dataType:"json",
	         success:function(data){
	          var innerH='<tr><td><input type="hidden" id="columnlisthiddenid" value="'+data.rows[0].id+'"/></td></tr>';
	           for(var i = 0; data.rows.length-1>=i;i++){
	          		innerH+="<tr onclick='selectTr(this)'><td align='center'>"+data.rows[i].connectionDBTableName+"</td></tr>";
	          	 }
	          	 document.getElementById("columnlisttbody").innerHTML =innerH;
	           },
	         error:function(){
	        	 hintManager.showHint("没有查询到人物数据，请检查输入是否正确！");
	         }
	     });
  }
  $(function(){
	  $.ajax({
	         type:"post",
	         url:"<%=basePath%>getDBPropertyByTable.action",
	         dataType:"json",
	         success:function(data){
	          //var innerH='';
	           for(var i = 0; data.rows.length-1>=i;i++){
	        	   $("#connectionDBselect").append("<option value='"+data.rows[i].id+"-"+data.rows[i].dbid+"'>"+data.rows[i].connectionDBTableName+"----"+data.rows[i].connectionName+"----"+data.rows[i].connectionDB+"</option>");
	          	//	innerH+="<tr  onclick='getDB(this)'><td align='center' name='id'>"+data.rows[i].id+"</td><td align='center'>"+data.rows[i].connectionName+"</td><td align='center'>"+data.rows[i].connectionDB+"</td><td><a onclick='editDBCon()' >Edit</a> </td><td><a   onclick='deleteDBCon()' >Delete</a></td></tr>";
	          	 }
	          //	 document.getElementById("tbody").innerHTML =innerH;
	           },
	         error:function(){
	        	 hintManager.showHint("没有查询到人物数据，请检查输入是否正确！");
	         }
	     });
	 

 });
  
   </script>
  <body bgcolor="#DBDBDB">
     <script type="text/javascript">
$().ready(function() {

	var $qrCode = $("#qrCode");
	var $mainNav = $("#mainNav li");
	var $currentMainNav = $("#mainNav li.current");
	var $navHighlight = $("#navHighlight");
	
	if ($currentMainNav.size() > 0) {
		$navHighlight.css({
			width: $currentMainNav.outerWidth() + 1,
			left: $currentMainNav.position().left - 1
		});
		
		$mainNav.hover(
			function() {
				var $this = $(this);
				$navHighlight.css({
					width: $this.outerWidth() + 1,
					left: $this.position().left - 1
				});
			}, function() {
				$navHighlight.css({
					width: $currentMainNav.outerWidth() + 1,
					left: $currentMainNav.position().left - 1
				});
			}
		);
	}
});
</script>
<div class="header" >
	<div class="top" >
		<div class="topNav" >
			<div id="mainNav" class="mainNav" style="width:100%;text-align:center">
				<ul>
					<li>
						<a href="<%=basePath%>databaseConnected.action">配置</a>
					</li>
					<li class="current">
						<a href="<%=basePath%>databaseConnectedTableselect.action">导入</a>
					</li>
					<li>
						<a href="/">帮助</a>
					</li>
							
				</ul>
			<span id="navHighlight" class="navHighlight"></span>
		</div>
		</div>
	</div>
	
</div>
<div id="main" class="main">
 <div class="div1">
 		<div style="background:#F2F2F2;text-align:left;"><span>数据导入-属性选择</span></div>
    	<div class="div2" style=' height:30px;padding:30px;text-align:center;  '>
    		
    		<select id="connectionDBselect" style="width:200px;height:32px" onchange="getTableList(this)">
				 <option value='' selected></option>
			</select>
    		
    	</div>
    	<div class="div3" >
    		<div style="width:280px;float:left">&nbsp;</div>
	    	<div style="width:300px;height:350px;overflow:auto;direction:ltr;font-size:12px;float:left;border:0px solid #7171C6;background:white;">
	    		<table id="columnlist" border="0" cellpadding="3" cellspacing="0" bgColor=#FFFAF0>
	    			<thead>  
				        <tr bgcolor="#63B8FF"  >  
				            <th width="93" style="text-align:center;">所有属性</th>  
				        </tr>  
				    </thead>  
				    <tbody id="columnlisttbody" >  
				    </tbody> 
	
	    		</table>
	    	</div>
	    	<div style="width:200px;height:350px;overflow:auto;direction:ltr;font-size:12px;float:left"><br><br><br><br><br><br><br><br><br><br><button class="cupid-blue" onclick="selectTable()">选择</button></div>
	    	<div style="width:300px;height:350px;overflow:auto;direction:ltr;font-size:12px;float:left;background:white;">
	    		<table id="columnSelectedList" border="0" cellpadding="3" cellspacing="0" bgColor=#FFFAF0 >
	    			<thead>  
				        <tr bgcolor="#63B8FF">  
				            <th width="93" style="text-align:center;">选择属性</th>  
				        </tr>  
				    </thead>  
				    <tbody id="selectedcolumnlisttbody">  
				       
				    </tbody> 
	
	    		</table>
	    	</div>
	    	
	    	
    	</div>
    	<div style="border:0px solid #7171C6;width:100%;height:70px;overflow:auto;direction:ltr;font-size:12px;float:left"><br><br><button class="button white" onclick="beforeStep()">上一步</button>&nbsp;&nbsp;&nbsp;<button class="button white" onclick="">下一步</button></div>
    </div>
    
</div>	
   
  </body>
</html>
