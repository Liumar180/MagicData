<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>数据导入</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="styles/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="styles/css/userList.css" rel="stylesheet" type="text/css">
	<link href="styles/css/customCss.css" rel="stylesheet" type="text/css">
	<link href="styles/css/importData.css" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>/styles/css/dataImport/createDataImport.css" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>/styles/css/ui.jqgrid.css" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>/styles/css/jquery-ui.min.css" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>/styles/css/commTcc.css" rel="stylesheet" type="text/css">
  </head>
  <body>
<!-- 			<div class="userlistlogoBox clearfix">
				<span class="logoSpan"><img src="images/img/title.png"/></span>
				<p class="p1">数据导入</p>
				<div class="topRight">
					<ul>
						<li><a href="index.action">返回首页</a></li>
						<li class="curUser"><a href="quit.action">退出</a></li>
					</ul>
				</div>
			</div> -->
			<div class="">
				<div class="">
					<div class="userTab">
						<span class="spanSeton">任务列表</span>
						<span id="dbtabid">数据源配置</span>
						<span id="csvdb">csv数据源配置</span>
						<span id="taskConfigId">任务配置</span>
						<span id="newTaskConfigId">new task to configure</span>
					</div>
					<div class="userCon clearfix" >
						<div style='width:99%;margin:0 auto'>  
							<table id="dataList"></table>
							<div id="pager"></div>
						</div>
					</div>
					<div class="userCon hideUsercon clearfix">
						<div id="Content-Left">
						   		<label>数据库类型 :</label>
						    	<select id="connectionDB" style="margin-left:9px;" onchange="ImportText(this,'')">
									<option value ="MYSQL" selected>MySQL</option>
									<option value ="ORACLE">Oracle</option>
									<option value ="MSSQL">MS SQL</option>
									<option value ="DB2">DB2</option>
									<option value ="AS/400">AS/400</option>
									<option value ="SYBASE">Sybase</option>
									<option value ="POSTGRESQL">Postgresql</option>
									<option value ="DERBY">Derby</option>
									<option value ="FIREBIRD">Firebird</option>
									<option value ="GREENPLUM">Greenplum</option>
									<option value ="H2">H2</option>
									<option value ="HadoopHive">HadoopHive</option>
									<option value ="SQLITE">Sqlite</option>
								</select>
						    		<table id="databaseTable" border="0" cellpadding="3" cellspacing="0" >
						    			<tr class="trWidth">
						    				<td align="right">连接名：</td>
						    				<td align="left">
						    					<input id="connectionName">
						    					<input type="hidden" id="connectionID" >
						    				</td>
						    			</tr>
						    			
						    			<tr class="trWidth">
						    				<td align="right">主机名称：</td>
						    				<td align="left"><input id="connectionServerName"></td>
						    			</tr>
						    			
						    			<tr class="trWidth">
						    				<td align="right">数据库名称：</td>
						    				<td align="left"><input id="connectionDbName"></td>
						    			</tr>
						    			
						    			<tr class="trWidth">
						    				<td align="right">端口号：</td>
						    				<td align="left"><input id="connectionPort" value="3306" onkeyup="port()"></td>
						    			</tr>
						    			
						    			<tr class="trWidth">
						    				<td align="right">用户名：</td>
						    				<td align="left"><input id="connectionDBUserName" ></td>
						    			</tr>
						    			
						    			<tr class="trWidth">
						    				<td align="right">密码：</td>
						    				<td align="left"><input type="password" id="connectionDBPassword" ></td>
						    			</tr>
						    			
						    			<tr>
						    				<td colspan="2" align="center"  id="dbConButtons">
						    					<input type="button" class="test"  onclick="testDBCon()" value="测试">
						    					<input type="button" class="save" onclick="saveDBCon()" value="新增">
						    				</td>
						    			<tr>
						    		</table>						    	
							</div>
					    <div id="Content-Main">
					        	<div class="tableBox_r">
						    		<table id="dblist" class="table" >
						    			<thead>  
									        <tr>  
									            <th class="col-md-2">NO.</th> 
									            <th class="col-md-3">连接名</th>  
									            <th class="col-md-3">数据库</th>  
									            <th class="col-md-2">操作</th>
									        </tr>  
									    </thead>  
									    <tbody id="tbody">  
									    	<tr>
								         		<td>1</td>
								         		<td>admin</td>
								         		<td>超级管理员</td>
									         	<td>
									         		<a href="javascript:void(0)" class="bjA"></a>
										            <a href="javascript:void(0)" class="delA"></a>
									         	</td>
								      		</tr>
									    </tbody> 						
						    		</table>
						    	</div>
							</div>
						<div id="tbody1"></div>					    
					</div>
					<div class="userCon hideUsercon clearfix">
						<div id="Content-Left">
							<input type="hidden" id="csvHiddenId"  />
							<p class="importP clearfix">
	         					<span class="importSpan comSpan pull-left" id="csvFileSpan">请先导入文件：</span>
		         				<a href="javascript:void(0);" class="a-upload" id="uploadcsva" >选择文件
		         					<input type="file" value="" name="csv" id="csv" >			         					
		         				</a>
	         					<input type="text" disabled="disabled" class="showFileName" value="" id="csvFileName">
	         				</p>
		         			<div class="utfDiv clearfix">
	         					<span class="comSpan">选择文件编码：</span>
	         					<select id="fileEncoding" name="fileEncoding" class="form-control">
	         						<option value="UTF-8">UTF-8</option>
	         						<option value="ANSI">ANSI</option>
	         						<option value="GBK">GBK</option>
	         						<option value="GB2312">GB2312</option>
	         						<option value="GB18030">GB18030</option>
	         					</select>
	         				</div>
	         				<div class="fieldSymbol">
	         					<span class="comSpan">分隔符：</span>
	         					<div class="fieldsymbolList">    						
									<input type="text" class="form-control" id="fileSeparator" />
	         					</div>
	         				</div>
	         				<div class="headerRadio">
	         					<span class="comSpan">是否有表头：</span>
	         					<div class="headerradioList">    						
									<label class="checkbox-inline">
										<input type="radio" id="titleFlag" name="titleFlag" value="true" checked>是
									</label>
									<label class="checkbox-inline">
										<input type="radio" id="titleFlag" name="titleFlag" value="false">否
									</label>
	         					</div>
	         				</div>
	         				<div class="newaddDiv">
	         					<input type="button" class="save" id="uploadCsvBtn" onclick="uploadCsv()" value="新增">
	         					<input type="button" class="save" id="updateCsvBtn" style="display: none;" onclick="updateCsv()" value="修改">
	         				</div>
						</div>
					    <div id="Content-Main">
					        	<div class="tableBox_r">
						    		<table id="dblist" class="table" >
						    			<thead>  
									        <tr>  
									            <th class="col-md-1">NO.</th> 
									            <th class="col-md-2">文件名称</th>  
									            <th class="col-md-2">文件编码</th>  
									            <th class="col-md-1">分隔符</th>
									            <th class="col-md-1">包含表头</th>
									            <th class="col-md-2">操作</th>
									        </tr>  
									    </thead>  
									    <tbody id="tbody_csv">  
									    	<tr>
								         		<td>1</td>
								         		<td>admin</td>
								         		<td>超级管理员</td>
									         	<td>
									         		<a href="javascript:void(0)" class="bjA"></a>
										            <a href="javascript:void(0)" class="delA"></a>
									         	</td>
								      		</tr>
									    </tbody> 						
						    		</table>
						    	</div>
							</div>
						<div id="tbody1"></div>					    
					</div>
					<div class="userCon hideUsercon clearfix" align="center">
					   <!-- 数据导入配置start -->
						<div id="main">
							<div class="div1">
								<div class="dataTitle">
									<span id="dataTitle">数据导入-选择连接</span>
								</div>
								<table width="96%" height="100%" border="0">
									<tr>
										<td width="50%" valign="top" align="left">
										<div class="dataLeft">
										  <iframe src="<%=basePath%>getDImpAConPage.action?updateFlag=0" width="100%" height="97%" frameborder="0" style="background-color: #012548;margin-top:3px; padding-right: 2px;" id="dCreateFrame"  name="dCreateFrame"></iframe>
										</div>
										</td>
										<td width="50%" valign="top">
											<div class="dataRight">
												<div style="padding:20px">
													<p><span class="spanLHead" >数据库导入配置信息</span></p>
													<p><span class="spanL">任务名称：</span><span id="dbTaskNameSpan" class="spanR"></span></p>
													<div style="width: 100%" id="dataImpViewDiv">
													<p><span class="spanL">数据库连接：</span><span id="dbConnNameSpan" class="spanR"></span></p>
													<div id="dbConfDiv" style="margin-left:145px; line-height:30px;font-size: 13px;"></div>
													<p><span class="spanL">源数据：</span><span id="dbSourceTableSpan" class="spanR"></span></p>
													<p><span class="spanL">目标数据：</span><span id="dbTargetTableSpan" class="spanR"></span></p>
													<p><span class="spanL">字段关联：</span><span id="dbFieldsSpan" class="spanR"></span><br /></p>
													</div>
													<div style="width: 100%" id="emlImpViewDiv">
													<p><span class="spanL">.eml文件路径：</span><span id="emlPathSpan" class="spanR"></span></p>
													</div>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
					<div class="userCon clearfix" >
						<div style='width:99%;margin:0 auto'>  
							<iframe src="<%=basePath%>common/DataImport.action" width="100%" height="97%" frameborder="0" style="background-color: #012548;margin-top:3px; padding-right: 2px;" id=""  name=""></iframe>
						</div>
					</div>
					<!-- 数据导入配置end -->
				</div>
			</div>	
    <div class='hintMsg'></div>
    <div class="tccBox">
    	<div class="bg"></div>
        <div class="rwConBox" style="z-index:1000;">
        	<b class="closeB"></b>
            <div class="barBox" id="barBox">
            	<p class="titP"><span id='taskName'></span></p>
	            <div class="rwCon">
	            	<div class="table-responsive col-md-12">
					   		<table class="table table-bordered">
							      <thead>
							         <tr> 
							            <th colspan="6">详细信息</th>					            
							         </tr>
						      	  </thead>
							      <tbody>
							      	<tr>								      		
							            <td class="col-md-2 bgColor">数据源连接名</td>
							            <td class="col-md-4"  id='dbConnName'></td>	
							            <td class="col-md-2 bgColor">数据库类型</td>
							            <td class="col-md-4" id='dbType'></td>						   				            
							         </tr>
							         <tr>								      		
							            <td class="col-md-2 bgColor">数据源主机</td>
							            <td class="col-md-4"  id='hostName'></td>	
							            <td class="col-md-2 bgColor">数据库名称</td>
							            <td class="col-md-4" id='dataBaseName'></td>							           					   				            
							         </tr>
							         <tr>								      		
							            <td class="col-md-2 bgColor">源表名/SQL</td>
							            <td class="col-md-4"  id='sourceTable'></td>	
							            <td class="col-md-2 bgColor">目标表</td>
							            <td class="col-md-4" id='targetTable'></td>						   				            
							         </tr>							         
							         <tr>								      		
							            <td class="col-md-2 bgColor">选择源字段</td>
							            <td class="col-md-4"  id='checkSourceFields'></td>	
							            <td class="col-md-2 bgColor">选择目标字段</td>
							            <td class="col-md-4" id='checkTargetFields'></td>						   				            
							         </tr>
							         <tr>								      		
							            <td class="col-md-2 bgColor">创建时间</td>
							            <td class="col-md-4"  id='borntime'></td>	
							            <td class="col-md-2 bgColor">执行情况</td>
							            <td class="col-md-4" id='runStatus'></td>						   				            
							         </tr>
							         <tr>								      		
							            <td class="col-md-2 bgColor">总条数</td>
							            <td class="col-md-4"  id='totality'></td>	
							            <td class="col-md-2 bgColor">已导入条数</td>
							            <td class="col-md-4" id='importCount'></td>						   				            
							         </tr>			          
							     </tbody>
					   		</table>
						</div>  	
	            </div>        		
            </div>
        </div>
    </div>
    <script type="text/javascript" src="<%=basePath%>/scripts/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/scripts/grid.locale-cn.js"></script>
    <script type="text/javascript" src="<%=basePath%>/scripts/jquery.jqGrid.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/scripts/customJs.js"></script>
    <script type="text/javascript" src="<%=basePath%>/styles/bootstrap/js/bootstrap.min.js"></script>
  	<script type="text/javascript" src="<%=basePath%>/styles/bootstrap/js/bootstrap-prompts-alert.js"></script>
  	<script type="text/javascript" src="<%=basePath%>/scripts/ajaxfileupload.js"></script>
   
    <script type="text/javascript">
    $(function(){
    	getAllCsvList();
    });
    
    $("#csv").live("change",function(){
    	var fileVal = $(this).val();
    	$("#csvFileName").val(fileVal);
    });
    
    function uploadCsv(){
    	var fileEncoding = $('#fileEncoding').val();
    	var titleFlag = $('input[name="titleFlag"]:checked').val();
    	var fileSeparator = $("#fileSeparator").val();
    	var csv = $("#csv").val();
    	if(csv==''||csv==null||typeof(csv)=="undefined"){
    		hintManager.showHint("上传文件不能为空，请选择文件！");
    	}else{
    		if(!csv.endWith(".csv")){
    			hintManager.showHint("该文件不是csv文件，请重新选择！");
    		}else{
    			if(fileSeparator==''||fileSeparator==null||typeof(fileSeparator)=="undefined"){
            		hintManager.showHint("文件分隔符不能为空，请输入！");
            		return ;
            	}
    			$.ajaxFileUpload({
			         type:"post",
			         url:"csvDataManage/csvUpload.action",
			         dataType:"JSON",
			         data:{"fileEncoding":fileEncoding,"fileSeparator":fileSeparator,"titleFlag":titleFlag},
			         fileElementId : "csv", 
			         success:function(data){
			        	 data = JSON.parse(data);
			        	 var result = data.result;
			        	 if(result.success==true){
			        		 parent.hintManager.showInfoHint("csv文件上传成功！");
			        		 getAllCsvList();
			        	 }else{
			        		 parent.hintManager.showHint(result.message);
			        	 }
			         },
			         error:function(){
			         	parent.hintManager.showHint("csv上传异常，请联系管理员！");
			         }
			     });
    		}
    	}
    }
    
    function editCsvDataBefore(id){
    	$("#csvdb").text('csv数据源配置-修改');
    	$.ajax({
	         type:"post",
	         url:"csvDataManage/findCsvDataById.action?id="+id,
	         dataType:"json",
	         success:function(data){
	        	 $("#csvFileSpan").html("文件名称：");
	        	 $("#uploadcsva").hide();
	        	 var csvData = data.csvData;
	        	 $("#csvHiddenId").val(csvData.id);
	        	 $("#csvFileName").val(csvData.fileName);
	        	 $("#fileEncoding").val(csvData.fileEncoding);
	             $("input[name='titleFlag'][value='"+csvData.titleFlag+"']").attr("checked",true);
	        	 $("#fileSeparator").val(csvData.fileSeparator);
	        	 $("#uploadCsvBtn").hide();
	        	 $("#updateCsvBtn").show();
	         }
	     });
    }
    
    function updateCsv(){
    	var id = $("#csvHiddenId").val();
    	var fileEncoding = $('#fileEncoding').val();
    	var titleFlag = $('input[name="titleFlag"]:checked').val();
    	var fileSeparator = $("#fileSeparator").val();
    	if(fileSeparator==''||fileSeparator==null||typeof(fileSeparator)=="undefined"){
    		hintManager.showHint("文件分隔符不能为空，请输入！");
    		return ;
    	}
    	$.ajax({
	         type:"post",
	         url:"csvDataManage/updateCsvData.action",
	         dataType:"json",
	         data:{"id":id,"fileEncoding":fileEncoding,"titleFlag":titleFlag,"fileSeparator":fileSeparator},
	         success:function(data){
	        	 getAllCsvList();
	        	 $("#uploadcsva").show();
	        	 $("#csvFileName").val("");
	        	 $("#csvFileSpan").html("请先导入文件：");
	        	 $("#fileEncoding").val("UTF-8");
	        	 $("input[name='titleFlag'][value='true']").attr("checked",true);
	        	 $("#fileSeparator").val("");
	        	 $("#uploadCsvBtn").show();
	        	 $("#updateCsvBtn").hide();
	         }
    	});
    }
    
    function deleteCsvData(id){
    	confirm("确认要删除csv文件吗？",function(){
    		$.ajax({
	   	         type:"post",
	   	         url:"csvDataManage/deleteCsvData.action?id="+id,
	   	         dataType:"json",
	   	         success:function(data){
		        	 if(data.success==true){
		        		 parent.hintManager.showInfoHint("csv文件删除成功！");
		        		 getAllCsvList();
		        		 $("#uploadcsva").show();
			        	 $("#csvFileName").val("");
			        	 $("#csvFileSpan").html("请先导入文件：");
			        	 $("#fileEncoding").val("UTF-8");
			        	 $("input[name='titleFlag'][value='true']").attr("checked",true);
			        	 $("#fileSeparator").val("");
			        	 $("#uploadCsvBtn").show();
			        	 $("#updateCsvBtn").hide();
		        	 }else{
		        		 parent.hintManager.showHint(result.message);
		        	 }
	   	         },
		         error:function(){
		        	 parent.hintManager.showHint("删除csv文件异常，请稍后重试！");
		         }
   	     	});
    	});
    }
    
    function getCsvDB(id){
    	$("#csvdb").text('csv数据源配置');
    	$.ajax({
	         type:"post",
	         url:"csvDataManage/findCsvDataById.action?id="+id,
	         dataType:"json",
	         success:function(data){
	        	 $("#csvFileSpan").html("文件名称：");
	        	 $("#uploadcsva").hide();
	        	 var csvData = data.csvData;
	        	 $("#csvHiddenId").val(csvData.id);
	        	 $("#csvFileName").val(csvData.fileName);
	        	 $("#fileEncoding").val(csvData.fileEncoding);
	             $("input[name='titleFlag'][value='"+csvData.titleFlag+"']").attr("checked",true);
	        	 $("#fileSeparator").val(csvData.fileSeparator);
	        	 $("#uploadCsvBtn").hide();
	        	 $("#updateCsvBtn").hide();
	         }
	     });
    	
    	
    }
    
    function getAllCsvList(){
		var innerH='';
        document.getElementById("tbody_csv").innerHTML =innerH;
		$.ajax({
	         type:"post",
	         url:"csvDataManage/findCsvFileList.action",
	         dataType:"json",
	         success:function(data){
	           for(var i = 0; data.list.length-1>=i;i++){
	        	   if(data.list[i].titleFlag==true){
	        		   data.list[i].titleFlag = "是";
	        	   }else{
	        		   data.list[i].titleFlag = "否";
	        	   }
	          	   innerH+="<tr>"+
	          	   				"<td align='left' name='id' ><div  onclick='getCsvDB("+data.list[i].id+")' >"+(i+1)+"</div></td>"+
	          	   				"<td align='center'><div  onclick='getCsvDB("+data.list[i].id+")' >"+data.list[i].fileName+"</div></td>"+
	          	   				"<td align='center'><div  onclick='getCsvDB("+data.list[i].id+")' >"+data.list[i].fileEncoding+"</div></td>"+
	          	   				"<td align='center'><div  onclick='getCsvDB("+data.list[i].id+")' >"+data.list[i].fileSeparator+"</div></td>"+
	          	   				"<td align='center'><div  onclick='getCsvDB("+data.list[i].id+")' >"+data.list[i].titleFlag+"</div></td>"+
	          	   				"<td ><a onclick='editCsvDataBefore("+data.list[i].id+")' class=bjA></a> <a onclick='deleteCsvData("+data.list[i].id+")' class=delA></a></td></tr>";
	           } 
	           document.getElementById("tbody_csv").innerHTML =innerH;
	          	$("#dblist tbody_csv tr").hover(function() {
	    			$(this).addClass("blue");
	    		}, function() {
	    			$(this).removeClass("blue");
	    		});
	          	var trs = document.getElementById('tbody_csv').getElementsByTagName('tr');  
	        	  for( var i=0; i<trs.length; i++ ){  
	        		    trs[i].onmousedown = function(){  
	        		    	 for( var o=0; o<trs.length; o++ ){  
	        		    		 tronmousedown(trs,this);   
	        		    	 }   
	        		    };
	        	  } 
	           },
	         error:function(){
	        	 parent.hintManager.showHint("获取文件列表异常，请稍后重试！");
	         }
	     });
	}
    
    function port(){
    	
    	var port=$('#connectionPort').val();
    	$('#connectionPort').val(port.replace(/\D/g,''));
    	if(port>65535 & port>0){
    		$('#connectionPort').val('65535');
        	hintManager.showHint("端口号不能超过65535");
    	}
    		
    }
    $(function(){
		//tab
		$(".userTab span").click(function(){
	  		$(this).addClass("spanSeton").siblings("span").removeClass("spanSeton");
	  		var tabIndex = $(".userTab span").index(this); 
	  		$(".userCon").eq(tabIndex).show().siblings(".userCon").hide();
		});
		
		$(".spanBtn").click(function(){
		  $(".adduserBox").show();
	 	});
	 	  
		$(".adduserBox .closeBtn").click(function(event){
	    	$(".adduserBox").hide();
			event.preventDefault();
	  	});
		
		probar = function(id){
			if(id!=null&&id!=0){
				probar.id = id;
			}
			(function()
			{
				$.post('<%=basePath%>ajax_dataImport/taskValueMax.action',{'id':probar.id},function(data){
					var tv = data.importCount/data.totality*100;
					v = tv.toFixed(2)+"%";
					if(data.isFinish==1){
						if(data.importCount!=null&&data.totality!=null&&data.totality!=0){
							$('#probarValue').html(v);
							$('#probarValueStyle').attr('style','width: '+v+';');
							$(".progress").attr("title",v);
							//setTimeout("probar()",1000);//1秒执行一次
							setTimeout("probar()",3000);//30秒执行一次
							return;
						}else{
							v = "0%";
							$('#probarValue').html(v);
							$('#probarValueStyle').attr('style','width: '+v+';');
							$(".progress").attr("title",v);
// 							setTimeout("probar()",1000);//一秒执行一次
							setTimeout("probar()",1000);//30秒执行一次
							return;
						}
					}else if(data.isFinish==2){
						$('#probarValue').html('100%');
						$('#probarValueStyle').attr('style','width:100%;');
						$(".progress").attr("title",'100%');
						//任务执行完成刷新表格
						setTimeout("flushGrid()",5000);
						return;
					}else{
						window.location.reload();
						return;
					}
				});
			})();
			return '<div class="progress" title="0%">'+
	   			 		'<div id="probarValueStyle" class="meter red" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">'+
	      		 			'<span id="probarValue">0%</span>'+
	   			 		'</div>'+
				   '</div>';
		};
		var H = $(".userCon").height()-108;
		//加载表格
		 $("#dataList").jqGrid({
				url:"<%=basePath%>ajax_dataImport/getImportTaskList.action",
		        datatype:"json", //数据来源，本地数据
		        mtype:"POST",//提交方式
		        height:H,//高度，表格高度。可为数值、百分比或'auto'
		        //width:1000,//这个宽度不能为百分比
		        autowidth:true,//自动宽
		        colNames:['创建人','任务名称', '任务类型', '详情描述', '创建时间','执行状态', '操作'],
		        colModel:[
		            {name:'createPerson',index:'createPerson', width:'10%', align:'center' },
		            {name:'taskName',index:'taskName', width:'10%',align:'center',
		            	formatter:function(cellvalue, options, row){
		            		 return '<a href="javascript:void(0)" class="linkA"  onclick="showTaskDetail(\''+row.id+'\')">'+cellvalue+'</a>'
		            	} 
		            },
		            {name:'taskType',index:'taskType', width:'10%', align:'center'},
		            {name:'taskDesc',index:'taskDesc', width:'35%', align:'center'},
		            {name:'dateStr',index:'dateStr', width:'15%', align:'center'},
		            {name:'runStatus',index:'runStatus', width:'10%', align:'center',
		            	formatter:function(cellvalue, options, row){
		            	       if(cellvalue==0){
		            		           return "未执行";
		            		   }else if(cellvalue==1){
		            		          return probar(row.id);
		            		   }else if(cellvalue==2){
		            		          return "已执行";
		            		   }else if(cellvalue==-1){
		            		          return "执行错误";
		            		   }else if(cellvalue==-2){
		            		          return "配置文件异常";
		            		   }else if(cellvalue==-3){
		            		          return "数据库异常";
		            		   }else if(cellvalue==-5){
		            		          return "Hbase连接异常";
		            		   }else if(cellvalue==-6){
		            		          return "json格式验证";
		            		   }else if(cellvalue==-7){
		            		          return "json合理性验证";
		            		   }else if(cellvalue==-8){
		            		          return "CSV文件读取异常";
		            		   }
		            	} 
		            },
		            {name:'runStatus',index:'runStatus', width:'10%', align:'center',
		            	formatter:function(cellvalue, options, row){
		            	       if(cellvalue==0){
		            	    	   return '<a href="javascript:void(0)" class="executeButton" name="'+row.id+'"><img src="images/img/zx.png" /></a>'+
		            	    	   		  '<a href="javascript:void(0)" class="deleteButton deleteA" name="'+row.id+'"><img src="images/img/del.png" /></a>'+
		            	    	   		  '<a href="javascript:void(0)" class="editButton" name="'+row.id+'"><img src="images/img/bj.png" /></a>';
		            		   }else{
		            			   return "--";
		            		   }
		            	} 
		            }
		        ],
		        rownumbers:false,//添加左侧行号
		        //altRows:true,//设置为交替行表格,默认为false
		        //sortname:'createDate',
		        //sortorder:'asc',
		        viewrecords: true,//是否在浏览导航栏显示记录总数
		        rowNum:15,//每页显示记录数
		        rowList:[10,20,50],//用于改变显示行数的下拉列表框的元素数组。
		       
	            jsonReader: {
	                    root:"dataRows",    // 数据行（默认为：rows）
	                    page: "curPage.pageNo",     // 当前页
	                    total: "curPage.totalPages",    // 总页数
	                    records: "curPage.totalRecords",// 总记录数
	                    repeatitems : false                // 设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
	            },
	            prmNames:{rows:"curPage.pageSize",page:"curPage.pageNo"},
		        pager:'#pager',
		        gridComplete:function(){
		        	setRowHeight("dataList");
		        }
		    });
	})
	
	/* 设置行高  */
	function setRowHeight(id){
		var grid = $("#"+id);  
		grid.closest(".ui-jqgrid-bdiv").css({ 'overflow-x' : 'hidden' });
        var ids = grid.getDataIDs();  
        for (var i = 0; i < ids.length; i++) {  
            grid.setRowData ( ids[i], false, {height: 30});  
        }
	}
    
    //任务详细信息
    function showTaskDetail(id){
    	$.ajax({
	         type:"post",
	         url:"<%=basePath%>ajax_dataImport/showTaskDetail.action",
	         data:{
	        	 taskId:id
 	         },
 	         async : false,
	         dataType:"json",
	         success:function(data){
	        	 var taskDetail = data.taskDetail;
	        	 updateTaskDetailHtml(taskDetail);
	         },
	         error:function(){
	        	 hintManager.showHint("查询任务详细信息异常，请联系管理员！");
	         }
	     });
    	$(".tccBox").show();
    }

    /* 修改任务详细HTML的值 */
    function updateTaskDetailHtml(taskDetail){
    	$("#taskName").html(taskDetail.taskName);
    	$("#dbConnName").html(taskDetail.dbConnName);
    	$("#dbType").html(taskDetail.dbType);
    	$("#hostName").html(taskDetail.hostName);
    	$("#dataBaseName").html(taskDetail.dataBaseName);
    	var sqlFalg = taskDetail.sqlFlag;
    	if(true == sqlFalg){
    		$("#sourceTable").html(taskDetail.sqlText);
    	}else{
	    	$("#sourceTable").html(taskDetail.sourceTable);
    	}
    	$("#targetTable").html(taskDetail.targetTable);
    	$("#checkSourceFields").html(taskDetail.checkSourceFields);
    	$("#checkTargetFields").html(taskDetail.checkTargetFields);
    	$("#borntime").html(taskDetail.borntime);
    	$("#runStatus").html(taskDetail.runStatus);
    	$("#totality").html(taskDetail.totality);
    	$("#importCount").html(taskDetail.importCount);
    }
    /* 关闭弹出层*/
    $('.rwConBox .closeB').click(function(event){
        $('.tccBox').hide();
    	event.preventDefault();
      });
    
    //刷新表格
    function flushGrid(){
    	$("#dataList").jqGrid().trigger("reloadGrid");
    }
	
  	//任务执行
	$(".executeButton").live("click",function(){
		var taskId = $(this).attr("name");
		$.ajax({
	         type:"post",
	         url:"<%=basePath%>ajax_dataImport/judgeExecute.action",
	         data:{ date:new Date().getTime() },
	         dataType:"json",
	         success:function(data){
	        	 var result = data.result;
	        	 if(result == true){
	        		 hintManager.showHint("当前有正在执行的任务，请稍后执行！");
	        	 }else{//执行任务
					setTimeout("flushGrid()",5000);
					
					$.ajax({
				         type:"post",
				         url:"<%=basePath%>ajax_dataImport/startTask.action",
				         data:{
				        	 taskId:taskId
			  	         },
				         dataType:"json",
				         success:function(data){
				        	 var result = data.result;
				        	 if(result == true){
				        		 hintManager.showSuccessHint("任务成功执行完成！");
				        	 }else{
				        		 hintManager.showHint("任务执行失败，请联系管理员！");
				        	 }
				         },
				         error:function(){
				        	 hintManager.showHint("执行任务异常，请联系管理员！");
				         }
				     });
	        	 }
	         },
	         error:function(){
	        	 hintManager.showHint("当前有正在执行的任务，请稍后执行！");
	         }
	     });
		
	});
  	
	//删除任务
	$(".deleteButton").live("click",function(){
		var taskId = $(this).attr("name");
		confirm("是否确认删除此任务？",function(){
			$.ajax({
		         type:"post",
		         url:"<%=basePath%>ajax_dataImport/deleteTaskById.action",
		         data:{
		        	 taskId:taskId,
		        	 date:new Date().getTime()
	  	         },
		         dataType:"json",
		         success:function(data){
		        	 flushGrid();
		         },
		         error:function(){
		        	 hintManager.showHint("删除任务异常，请联系管理员！");
		         }
		     });
		});
	});
	
	//修改任务
	$(".editButton").live("click",function(){
		var taskId = $(this).attr("name");
		// TODO 修改任务实现
		$('#taskConfigId').click();
		editTask(taskId);
	});
  
	function getAllDT(){
		var innerH='';
        document.getElementById("tbody").innerHTML =innerH;
		$.ajax({
	         type:"post",
	         url:"<%=basePath%>ImportGetAll.action",
	         dataType:"json",
	         success:function(data){
	           for(var i = 0; data.rows.length-1>=i;i++){
	          		innerH+="<tr><td align='left' name='id' ><div  onclick='getDB("+data.rows[i].id+")' >"+(i+1)+"</div></td><td align='center'><div  onclick='getDB("+data.rows[i].id+")' >"+data.rows[i].connectionName+"</div></td><td align='center'><div  onclick='getDB("+data.rows[i].id+")' >"+data.rows[i].connectionDB+"</div></td><td ><a onclick='editDBConBefore("+data.rows[i].id+")' class=bjA></a> <a onclick='deleteDBCon("+data.rows[i].id+")' class=delA></a></td></tr>";
	          	 } 
	          	 document.getElementById("tbody").innerHTML =innerH;
	          	$("#dblist tbody tr").hover(function() {
	    			$(this).addClass("blue");
	    		}, function() {
	    			$(this).removeClass("blue");
	    		});
	          	var trs = document.getElementById('tbody').getElementsByTagName('tr');  
	        	  for( var i=0; i<trs.length; i++ ){  
	        		    trs[i].onmousedown = function(){  
	        		    	 for( var o=0; o<trs.length; o++ ){  
	        		    		 tronmousedown(trs,this);   
	        		    		}   
	        		    };  
	        		   } 
	           },
	         error:function(){
	        	// hintManager.showHint("数据源配置暂无数据。");
	         }
	     });
		
	}
	
	$(function(){
		getAllDT();
	  
 });
    
    function deleteDBCon(id){
    	confirm("删除此数据库，将会关联此数据库未执行的任务一并删除。",function(){
    		var connectionName = $('#connectionName').val();
      		var connectionDB = $('#connectionDB').val();
      		var connectionServerName = $('#connectionServerName').val();
      		var connectionDbName = $('#connectionDbName').val();
      		var connectionPort = $('#connectionPort').val();
      		var connectionDBUserName = $('#connectionDBUserName').val();
      		var connectionDBPassword = $('#connectionDBPassword').val();
      		var connectionID =id;
      		
      		$.ajax({
      	         type:"post",
      	         url:"<%=basePath%>ImportDelete.action",
      	         data:{
      	        	 connectionName:connectionName,
      	        	 connectionDB:connectionDB,
      	        	 connectionServerName:connectionServerName,
      	        	 connectionDbName:connectionDbName,
      	        	 connectionPort:connectionPort,
      	        	 connectionDBUserName:connectionDBUserName,
      	        	 connectionDBPassword:connectionDBPassword,
      	        	 id:connectionID
      	         },
      	         dataType:"json",
      	         success:function(data){
      	        	getAllDT();
      	           },
      	         error:function(){
      	        	 hintManager.showHint("有正在执行的任务，请执行完成任务后再进行删除。");
      	         }
      	     });
    	});
    }
    function getDB(obj){
    	$('#dbtabid').text('数据源配置');
  	  $.ajax({
  	         type:"post",
  	         url:"<%=basePath%>ImportGetDb.action",
  	         data:{
  	        	 id:obj
  	         },
  	         dataType:"json",
  	         success:function(data){
  	        	 ImportText('',data.rows[0].connectionDB);
  	        		$('#connectionDB').val(data.rows[0].connectionDB);
  	        	 	$('#connectionName').val(data.rows[0].connectionName);
  		       		$('#connectionServerName').val(data.rows[0].connectionServerName);
  		       		$('#connectionDbName').val(data.rows[0].connectionDbName);
  		       		$('#connectionPort').val(data.rows[0].connectionPort);
  		       		$('#connectionDBUserName').val(data.rows[0].connectionDBUserName);
  		       		$('#connectionDBPassword').val(data.rows[0].connectionDBPassword);
  		       		$('#connectionID').val(data.rows[0].id);
  		       		
  	           }
  	     });
    }
    function tronmousedown(trs,obj){  
  	    for( var o=0; o<trs.length; o++ ){  
  	     if( trs[o] == obj ){  
  	      trs[o].style.backgroundColor = '#104E8B';  
  	     }  
  	     else{  
  	      trs[o].style.backgroundColor = '';  
  	     }  
  	    }
    }
     /*查询入口*/
    	function testDBCon(){
  	   
    		var exp = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."  
				   +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."  
				   +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."  
				   +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
    		var connectionName = $('#connectionName').val();
    		if(connectionName.trim().length==0){
		    	parent.hintManager.showHint("连接名不能为空或者为空格！");   
	            return false;
		    }
    		var connectionDB = $('#connectionDB').val();
    		var connectionServerName = $('#connectionServerName').val();
    		var rep = connectionServerName.match(exp);
    		if(rep == null){
    			parent.hintManager.showHint("请输入正确的主机名称（ip）！");   
	            return false;
    		}
    		var connectionDbName = $('#connectionDbName').val();
    		if(connectionDbName.trim().length==0){
		    	parent.hintManager.showHint("数据库名称不能为空或者为空格！");   
	            return false;
		    }
    		var connectionPort = $('#connectionPort').val();
    		var connectionDBUserName = $('#connectionDBUserName').val();
    		var connectionDBPassword = $('#connectionDBPassword').val();
    		var id = $('#connectionID').val();
    		$.ajax({
  	         type:"post",
  	         url:"<%=basePath%>Import.action",
  	         data:{
  	        	 connectionName:connectionName,
  	        	 connectionDB:connectionDB,
  	        	 connectionServerName:connectionServerName,
  	        	 connectionDbName:connectionDbName,
  	        	 connectionPort:connectionPort,
  	        	 connectionDBUserName:connectionDBUserName,
  	        	 connectionDBPassword:connectionDBPassword,
  	        	 id:id
  	         },
  	         dataType:"json",
  	         success:function(data){
  	        		hintManager.showSuccessHint(data.rows[0].result);
  	           },
  	           error:function(data){
    	  	        		hintManager.showHint('连接失败!');
    	  	           }
  	     });
    	}
    	 /*存储入口*/
    	function saveDBCon(){
    		var connectionName = $('#connectionName').val();
    		var connectionDB = $('#connectionDB').val();
    		var connectionServerName = $('#connectionServerName').val();
    		var connectionDbName = $('#connectionDbName').val();
    		var connectionPort = $('#connectionPort').val();
    		var connectionDBUserName = $('#connectionDBUserName').val();
    		var connectionDBPassword = $('#connectionDBPassword').val();
    		if(connectionName==""){
    			hintManager.showHint("请填写连接名");
    			return;
    		}
    		$.ajax({
  	         type:"post",
  	         url:"<%=basePath%>ImportSave.action",
  	         data:{
  	        	 connectionName:connectionName,
  	        	 connectionDB:connectionDB,
  	        	 connectionServerName:connectionServerName,
  	        	 connectionDbName:connectionDbName,
  	        	 connectionPort:connectionPort,
  	        	 connectionDBUserName:connectionDBUserName,
  	        	 connectionDBPassword:connectionDBPassword
  	        	 
  	         },
  	         dataType:"json",
  	         success:function(data){
  	        	getAllDT();
  	           },
  	         error:function(){
  	        	 hintManager.showHint("连接名不能相同");
  	         }
  	     });
    	}
    	 /*修改*/
   	 function editDBConBefore(id){
   		 $.ajax({
  	         type:"post",
  	         url:"<%=basePath%>ImportGetDb.action",
  	         data:{
  	        	 id:id
  	         },
  	         dataType:"json",
  	         success:function(data){
  	        	 ImportText('',data.rows[0].connectionDB);
  	        		$('#connectionDB').val(data.rows[0].connectionDB);
  	        	 	$('#connectionName').val(data.rows[0].connectionName);
  		       		$('#connectionServerName').val(data.rows[0].connectionServerName);
  		       		$('#connectionDbName').val(data.rows[0].connectionDbName);
  		       		$('#connectionPort').val(data.rows[0].connectionPort);
  		       		$('#connectionDBUserName').val(data.rows[0].connectionDBUserName);
  		       		$('#connectionDBPassword').val(data.rows[0].connectionDBPassword);
  		       		$('#connectionID').val(data.rows[0].id);
  		       	$('#dbtabid').text('数据源配置-修改');
  				document.getElementById("dbConButtons").innerHTML ="";
  				var innerhtml='<input type="button"  class="test" onclick="testDBCon()" value="测试"><input type="button" class="save" onclick="editDBCon(id)"  value="修改">';
  				document.getElementById("dbConButtons").innerHTML =innerhtml;
  	           }
  	     });
   		 
   	 }
    	function editDBCon(id){
    		var connectionName = $('#connectionName').val();
    		var connectionDB = $('#connectionDB').val();
    		var connectionServerName = $('#connectionServerName').val();
    		var connectionDbName = $('#connectionDbName').val();
    		var connectionPort = $('#connectionPort').val();
    		var connectionDBUserName = $('#connectionDBUserName').val();
    		var connectionDBPassword = $('#connectionDBPassword').val();
    		var connectionID = $('#connectionID').val();
    			$.ajax({
    		         type:"post",
    		         url:"<%=basePath%>ImportEdit.action",
    		         data:{
    		        	 connectionName:connectionName,
    		        	 connectionDB:connectionDB,
    		        	 connectionServerName:connectionServerName,
    		        	 connectionDbName:connectionDbName,
    		        	 connectionPort:connectionPort,
    		        	 connectionDBUserName:connectionDBUserName,
    		        	 connectionDBPassword:connectionDBPassword,
    		        	 id:connectionID
    		         },
    		         dataType:"json",
    		         success:function(data){
    		        	 getAllDT();
    		           },
    		         error:function(){
    		        	 hintManager.showHint("有正在执行的任务，请执行完成任务后再进行修改。或进行修改连接名已存在。");
    		         }
    		     });
    		
    	}
    	 function ImportText(obj,databasetype){
    		 var databasename="";
    		 if(databasetype==""){
    			databasename=$(obj).children('option:selected').val();
    		 }else{
    			databasename=databasetype;
    		 }
    		 if(databasename=="MYSQL"||databasename=="DERBY"||databasename=="ORACLE"||databasename=="HadoopHive"||databasename=="H2"||databasename=="DB2"||databasename=="MSSQL"||databasename=="SYBASE"||databasename=="POSTGRESQL"||databasename=="GREENPLUM"||databasename=="FIREBIRD"){
    			document.getElementById("databaseTable").innerHTML ="";
    			var port="";
    			if(databasename=="MYSQL"){port=3306}else if(databasename=="ORACLE"){port=1521}else if(databasename=="HadoopHive"){port=9000}else if(databasename=="MSSQL"){port=1433}else if(databasename=="H2"){port=8082}else if(databasename=="DB2"){port=50000}else if(databasename=="SYBASE"){port=5001}else if(databasename=="POSTGRESQL"||databasename=="GREENPLUM"){port=5432}else if(databasename=="FIREBIRD"){port=3050}else if(databasename=="DERBY"){port=1527}
    			var innerH='<tr class="trWidth"><td align="right">连接名：</td><td align="left"><input id="connectionName"><input type="hidden" id="connectionID" ></td></tr><tr class="trWidth"><td align="right">主机名称：</td><td align="left"><input id="connectionServerName"></td></tr><tr class="trWidth"><td  align="right">数据库名称：</td><td align="left"><input  id="connectionDbName"></td></tr><tr class="trWidth"><td  align="right">端口号：</td><td align="left"><input id="connectionPort" value="'+port+'"  onkeyup="port()"></td></tr><tr  class="trWidth"><td align="right">用户名：</td><td align="left"><input id="connectionDBUserName" ></td></tr><tr class="trWidth"><td align="right">密码：</td><td align="left"><input type="password" id="connectionDBPassword" ></td></tr><tr><td colspan="2" align="center" id="dbConButtons"><input type="button"  class="test" onclick="testDBCon()" value="测试"><input type="button" class="save" onclick="saveDBCon()"  value="新增"></td><tr>';
    			document.getElementById("databaseTable").innerHTML =innerH;
    		 }else if(databasename=="AS/400"){//no Port
    			document.getElementById("databaseTable").innerHTML ="";
    			var innerH='<tr class="trWidth"><td  align="right" >连接名：</td><td align="left"><input id="connectionName"><input id="connectionPort" type="hidden"><input type="hidden" id="connectionID" ></td></tr><tr class="trWidth"><td align="right">主机名称：</td><td align="left"><input id="connectionServerName"></td></tr><tr class="trWidth"><td  align="right">数据库名称：</td><td align="left"><input  id="connectionDbName"></td></tr><tr  class="trWidth"><td align="right">用户名：</td><td align="left"><input id="connectionDBUserName" ></td></tr><tr class="trWidth"><td align="right">密码：</td><td align="left"><input type="password" id="connectionDBPassword" ></td></tr><tr><td colspan="2" align="center"  id="dbConButtons"><input type="button"  class="test" onclick="testDBCon()" value="测试"><input type="button" class="save" onclick="saveDBCon()"  value="新增"></td><tr>';
    			document.getElementById("databaseTable").innerHTML =innerH;
    		 }else if(databasename=="SQLITE"){// no 数据库名称 port & username & passport
    			document.getElementById("databaseTable").innerHTML ="";
    			var innerH='<tr class="trWidth"><td align="right">连接名：</td><td align="left"><input id="connectionName"><input  id="connectionDbName" type="hidden" ><input id="connectionDBUserName" type="hidden" ><input  id="connectionDBPassword"   type="hidden" ><input id="connectionPort" type="hidden"><input type="hidden" id="connectionID" ></td></tr><tr class="trWidth"><td align="right">主机名称：</td><td align="left"><input id="connectionServerName"></td></tr><tr><td colspan="2" align="center"  id="dbConButtons"><input type="button"  class="test" onclick="testDBCon()" value="Test"><input type="button" class="save" onclick="saveDBCon()"  value="Save"></td><tr>';
    			
    			document.getElementById("databaseTable").innerHTML =innerH;
    		 }
    	 }
	</script>
		<!-- 数据 导入配置start-->
	<script type="text/javascript">
	    $("#taskConfigId").click(function(){
	       try{ dCreateFrame.window.getDataUrl();}catch(e){}
	    });
	    /*编辑任务*/
	    function editTask(taskId){
	      var newSrc = "<%=basePath%>getDImpAConPage.action?updateFlag=1&&firstUpdate=1&&taskId="+taskId;
	      <%-- $("#dCreateFrame").src="<%=basePath%>getDImpAConPage.action?updateFlag=1&&taskId="+taskId;
	      $("#dCreateFrame").reload(); --%>
	      $("#dCreateFrame").attr("src", newSrc);
	      /* $("#dCreateFrame").contentWindow.location.reload(true); */
	    }
	    /*回显任务名称信息*/
	    function showNameInfo(val){
	       $("#dbTaskNameSpan").html(val);
	    }
	     /*清除数据库外其他信息*/
	    function clearOther(){
	        $("#dbSourceTableSpan").html("");
			$("#dbTargetTableSpan").html("");
			$("#dbFieldsSpan").html("");
	    }
		/*回显数据库连接信息*/
		function showConnInfo(dataBaseMap, selVal) {
			if (selVal == "" || selVal == undefined) {
				$("#dbConfDiv").html("");
				$("#dbConnNameSpan").html("");
			} else {
				$.map(
								dataBaseMap,
								function(value, key) {
									if (key == selVal) {
										var connName = "";
										var connIp = "";
										var connPort = "";
										var connDBName = "";
										var connUserName = "";
										var connPwd = "";
										$
												.map(
														value,
														function(objValue,
																objKey) {
															console.log("key:"+objKey);
															if (objKey == "connectionName") {
																connName = objValue;
															}
															if (objKey == "connectionServerName") {
																connIp = objValue;
															}
															if (objKey == "connectionPort") {
																connPort = objValue;
															}
															if (objKey == "connectionDbName") {
																connDBName = objValue;
															}
															if (objKey == "connectionDBUserName") {
																connUserName = objValue;
															}
															if (objKey == "connectionDbName") {
																connPwd = objValue;
															}
														});
										var tempConStr = "数据库地址：" + connIp
												+ "<br/>" + "数据库端口：" + connPort
												+ "<br/>" + "数据库名称："
												+ connDBName + "<br/>" + "登陆名："
												+ connUserName + "<br/>"
												+ "密码：" + connPwd + "<br/>";
										var connNameStr = "<input type='button' id='dbConnButt' value='+' onclick='changDBConf(this.value)'/>"
												+ connName;
										$("#dbConfDiv").html(tempConStr);
										$("#dbConfDiv").css("visibility",
												"hidden");
										$("#dbConfDiv").css("height", "0px");
										$("#dbConnNameSpan").html(connNameStr);
										return;
									}
								});
			}

		}
		/*回显源数据信息*/
		function showSourceInfo(sourceValue) {
			$("#dbFieldsSpan").html("");
			$("#dbSourceTableSpan").html(sourceValue);
		}
		/*回显目标数据信息*/
		function showTargetInfo(targetValue) {
			$("#dbFieldsSpan").html("");
			$("#dbTargetTableSpan").html(targetValue);
		}
		/*回显源字段选择*/
		function showFieldInfo(targetValue) {
			$("#dbFieldsSpan").html(targetValue);
		}
		function changDBConf(buttVal) {
			if (buttVal == "+") {
				$("#dbConfDiv").css("visibility", "visible");
				$("#dbConfDiv").css("height", "");
				$("#dbConnButt").val("-");
			} else if (buttVal == "-") {
				$("#dbConfDiv").css("visibility", "hidden");
				$("#dbConfDiv").css("height", "0px");
				$("#dbConnButt").val("+");
			}
		}
		function changeDataTitle(titleVal){
		   $("#dataTitle").html(titleVal);
		}
		function showDataImpDiv(){
		   $("#dataImpViewDiv").css("display","inherit");
	        $("#emlImpViewDiv").css("display","none");
		}
		function showEmlImpDiv(){
		   $("#emlImpViewDiv").css("display","inherit");
	        $("#dataImpViewDiv").css("display","none");
		}
		function showEmlPath(sourceValue) {
			$("#emlPathSpan").html(sourceValue);
		}
	</script>
	<!-- 数据 导入配置end-->
	
	
  </body>
</html>
