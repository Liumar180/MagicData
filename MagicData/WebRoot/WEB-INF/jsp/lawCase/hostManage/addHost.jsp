<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
	<head>
	<base href="<%=basePath%>">
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		<link href="styles/lawcase/css/bootstrap.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="styles/lawcase/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="styles/lawcase/css/my.css"/>
		<script type="text/javascript" src="scripts/lawCase/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="scripts/lawCase/my.js" ></script>
		<script type="text/javascript" src="scripts/lawCase/common.js" ></script>
		<script type="text/javascript" src="scripts/lawCase/Popup.js" ></script>
		<script type="text/javascript" src="scripts/lawCase/inputSelect.js" ></script>
		
		<script type="text/javascript" charset="utf-8" src="scripts/ueditor-jsp/ueditor.config.js"></script>
        <script type="text/javascript" charset="utf-8" src="scripts/ueditor-jsp/ueditor.all.min.js"> </script>
	    <script type="text/javascript" charset="utf-8" src="scripts/ueditor-jsp/lang/zh-cn/zh-cn.js"></script>
	    <script type="text/javascript" src="styles/bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="styles/bootstrap/js/bootstrap-prompts-alert.js"></script> 
	</head>
	<body>
		<!--点击添加按钮出现的弹出层-->
			<div class="zjaddCon">
			<%-- <input type="hidden" id="responsiblePersonHidden" name="responsiblePerson" value="${hosts.responsiblePerson}" /><!-- 责任人 --> --%>
			<input type="hidden" id="directionsHidden" name="directions" value="${hosts.directions}" /><!-- 方向 -->
			<input type="hidden" id="rootType" name="rootType" value="${rootType}" />
			<input type="hidden" id="rootId" name="rootId" value="${rootId}" />
			<input type="hidden" id="point" name="point" value="${point}"/>
				<div class="form-group">
					<label class="labelTit">主机名称</label>
					<c:choose>
				   <c:when test="${empty rootId}">
				      	<input id="hostName" name="hostName" value="${hosts.hostName}" type="text" class="form-control" placeholder="请输入主机名称" />
				   </c:when>
				   <c:otherwise>
						<input id="relationPoint" name="hostName" value="${hosts.hostName}" type="text" class="form-control" placeholder="请输入主机名称" />
						<div class="hideDiv"></div>
				   </c:otherwise>
			  	</c:choose>	
				</div>
                <div class="form-group">
					<label class="labelTit">主机IP</label>
					<input type="hidden" id="cTime" name="cTime" value="${hosts.createTime}" >
					<input type="hidden" id="hId" name="hid" value="${hosts.id}" />
					<input type="text" id="hostIp" name="hostIp" value="${hosts.hostIp}" class="form-control" placeholder="请输入主机IP" />
				</div>
				<div class="form-group">
					<label class="labelTit">所在地</label>
					<input type="text" id="location" name="location" value="${hosts.location}" class="form-control" placeholder="请输入所在地" />
				</div>
				<div class="form-group">
                  		<label class="labelTit">主机类别</label>				
					<select class="form-control select" id="hostType" name="hostType">
					    <c:forEach items="${dataDictionary.hostType}" var="hostType" >
							<c:choose>
							   <c:when test="${hostType.value == hosts.hostType}">
							      	<option value="${hostType.key}" selected="selected">${hostType.value}</option>
							   </c:when>
							   <c:otherwise>
							   		<option value="${hostType.key}">${hostType.value}</option>  
							   </c:otherwise>
						  	</c:choose>
						</c:forEach>						   
					</select>
                 		</div>							
				<div class="form-group">
					<label class="labelTit">提供商</label>
					<input type="text" id="provider" name="provider" value="${hosts.provider}" class="form-control" placeholder="请输入提供商" />
				</div>
				<div class="form-group">
					<label class="labelTit">操作系统</label>
					<input type="text" id="operateSystem" name="operateSystem" value="${hosts.operateSystem}" class="form-control" placeholder="请输入操作系统" />
				</div>
                <div class="form-group">
					<label class="labelTit">Mac地址</label>
					<input type="text" id="macAddress" name="macAddress" value="${hosts.macAddress}" class="form-control" placeholder="请输入Mac地址" />
				</div>
				<div class="form-group">
                 <label class="labelTit">责任人</label>
                 <select class="form-control select" name="responsiblePerson" id="zrr">
					<c:forEach items="${userList}" var="user" >
						<c:choose>
						   <c:when test="${user.userName == hosts.responsiblePerson}">
						      	<option value="${user.userName}" selected="selected">${user.userName}</option>
						   </c:when>
						   <c:otherwise>
								<option value="${user.userName}">${user.userName}</option>
						   </c:otherwise>
					  	</c:choose>
					</c:forEach>
				</select>
                 			<!-- <div class='multi_select' style="z-index:1000;" type="hostUser"></div> -->
                 		</div>
				<div class="form-group">
                  		<label class="labelTit">主机状态</label>				
					<select class="form-control select" id="hostState" name="hostState">
						<c:forEach items="${dataDictionary.hostStatus}" var="level" >
							<c:choose>
							   <c:when test="${level.value == hosts.hostState}">
							      	<option value="${level.key}" selected="selected">${level.value}</option>
							   </c:when>
							   <c:otherwise>
							   		<option value="${level.key}">${level.value}</option>  
							   </c:otherwise>
						  	</c:choose>
						</c:forEach>
					</select>
                 		</div>
                 		<div class="form-group">
                  		<label class="labelTit">重要等级</label>				
					  <select class="form-control select" id="importantLevel" name="importantLevel">
						<c:forEach items="${dataDictionary.level}" var="lev" >
							<c:choose>
							   <c:when test="${lev.value == hosts.importantLevel}">
							      	<option  value="${lev.key}" selected="selected">${lev.value}</option>
							   </c:when>
							   <c:otherwise>
							   		<option  value="${lev.key}">${lev.value}</option>  
							   </c:otherwise>
						  	</c:choose>
						</c:forEach>
					</select>
                 		</div>                   		                   																                   
                 		<div class="form-group">
                  		<label class="labelTit">所属方向</label>
                  		<div class='multi_select1' style="z-index:1000;" type="hostDirection"></div>
                 		</div>	  
				<div class="form-group">
                  		<label class="labelTit">控制状态</label>				
					<select class="form-control select" id="controlState" name="controlState">
					    <c:forEach items="${dataDictionary.controlStatus}" var="control" >
					<c:choose>
					   <c:when test="${control.value == hosts.controlState}">
					      	<option  value="${control.key}" selected="selected">${control.value}</option>
					   </c:when>
					   <c:otherwise>
					   		<option  value="${control.key}">${control.value}</option>  
					   </c:otherwise>
				  	</c:choose>
				</c:forEach>							   
					</select>
                 		</div>	
                 		<div class="form-group textDiv">
					<label class="labelTit labelTit1">安装服务</label>
					<textarea name="installationService" id="installationService" rows="6" cols="">${hosts.installationService}</textarea>
				</div>
 		          <div class="clear"></div>
              		<div class="zaBox">
              			<span class="zaLeft">描述</span>
              			<div class="zaRight">
              				<script id="hostEditor" type="text/plain" style="width:1024px;height:200px;">${hosts.descriptionContents}</script>
              			</div>
              		</div>
    		<div style="clear: both;"></div>
    		<div class="btnDiv">
				<button class="btn btn-primary sureBtn" onclick="addHostsInfo()">保存</button>
				<button class="btn btn-default qxBtn" onclick="exit()">取消</button>
			</div>
		</div>
<script type="text/javascript">
		$(function(){
			
			var selectValues2 = [];
			
			var directionkeys = $.parseJSON('${directionKeys}');
			var checkdirectioncodes = "${hosts.directions}".split(",");
			for(var i = 0;i < directionkeys.length; i++) {
     			var temp = directionkeys[i];
     			if(checkdirectioncodes.indexOf(temp)>-1){
     				selectValues2.push(true);
     			}else{
     				selectValues2.push(false);
     			}
			}
			
			$('.multi_select1').MSDL({
	    		'width': 'auto',
	    		'data':$.parseJSON('${directionTexts}'),
				'value':directionkeys,
				'value_selected':selectValues2
	  		});
			
		});
		
		//多选下拉框
		function selectHandler(selectData){
			var key = selectData.value_arr;
			//var text = selectData.text_arr; 
			var flag = selectData.type;
			/* if(flag =="hostUser"){
				$("#responsiblePersonHidden").val(text.toString());
			}else  */if(flag =="hostDirection"){
				$("#directionsHidden").val(key.toString());
			}
		}
		        
		function exit(){
			window.parent.dismissParentPop();
		}

		/* var hostEditor = new UE.ui.Editor({initialFrameHeight:200,initialFrameWidth:900});
		hostEditor.render("hostEditor"); */
		var aue = UE.getEditor('hostEditor');
		var regIp = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."  
			   +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."  
			   +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."  
			   +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$"; 
		
		function addHostsInfo(){
			var cTime = $('#cTime').val();
			var relationPoint = $('#relationPoint').val();
			var rootType = $('#rootType').val();
			var rootId = $('#rootId').val();
		    var hid=$('#hId').val();
		    var point = $('#point').val();
		    if(point == undefined && point.length == 0){
		    	point ="false";
		    }
			var hostName=$('#hostName').val();
			if(hostName == undefined){
				hostName =relationPoint.trim();
			}else{
				hostName.trim();
			}
		    if(hostName.length==0){
		    	parent.hintManager.showHint("对不起，主机名不能为空或者为空格！");   
	            return false;
		    }else if(hostName.length > 45){
		    	parent.hintManager.showHint("输入主机名称内容过长，请重新输入！"); 
		    	return false;
		    }
			var hostIp=$('#hostIp').val().trim();
			if(!hostIp.match(regIp)){    
				parent.hintManager.showHint("对不起，您输入的IP不正确，请重新输入...");   
	            return false;
	        }else if(hostIp.length == 0){
	        	parent.hintManager.showHint("对不起，主机IP不能为空或者为空格！");   
	            return false;
	        }
			var location=$('#location').val().trim();
			if(location.length > 45){
				parent.hintManager.showHint("输入所在地内容过长，请重新输入！");   
	            return false;
			}
			var hostType=$('#hostType').val();
			var provider=$('#provider').val().trim();
			if(provider.length > 45){
				parent.hintManager.showHint("输入提供商内容过长，请重新输入！");   
	            return false;
			}
			var operateSystem=$('#operateSystem').val().trim();
			if(operateSystem.length > 45){
				parent.hintManager.showHint("输入操作系统内容过长，请重新输入！");   
	            return false;
			}
			var reg_name=/[A-F\d]{2}-[A-F\d]{2}-[A-F\d]{2}-[A-F\d]{2}-[A-F\d]{2}-[A-F\d]{2}/; 
			var macAddress=$('#macAddress').val().trim();
			if(!macAddress.match(reg_name) && macAddress.length != 0){
				parent.hintManager.showHint("mac地址格式不正确！mac地址格式为00-24-21-19-BD-E4！");   
	            return false;
			}
			var zrr=$('#zrr').val();
			var hostState=$('#hostState').val();
			var importantLevel=$('#importantLevel').val();
			var directions=$('#directionsHidden').val();
			if(directions.length==0){
		    	parent.hintManager.showHint("所属方向不能为空！");   
	            return false;
		    }
			var controlState=$('#controlState').val();
			var installationService=$('#installationService').val().trim();
			if(installationService.length > 500){
				parent.hintManager.showHint("输入安装服务内容过长，请重新输入！");   
	            return false;
			}
			var descriptionContents=UE.getEditor('hostEditor').getContent().trim();
			if(descriptionContents.length > 500){
				parent.hintManager.showHint("输入描述内容过长，请重新输入！");   
	            return false;
			}
				 $.ajax({
			         type:"post",
			         url:"hostManage/saveHost.action",
			         data:{
			        	 	hostIp:hostIp,
			        	 	hostName:hostName,
			        	 	location:location,
			        	 	hostType:hostType,
			        	 	provider:provider,
			        	 	operateSystem:operateSystem,
			        	 	macAddress:macAddress,
			        	 	responsiblePerson:zrr,
			        	 	hostState:hostState,
			        	 	importantLevel:importantLevel,
			        	 	directions:directions,
			        	 	controlState:controlState,
			        	 	installationService:installationService,
			        	 	descriptionContents:descriptionContents,
			        	 	hid:hid,
			        	 	relationPoint:relationPoint,
			        	 	rootType:rootType,
			        	 	rootId:rootId,
			        	 	cTime:cTime
			         
			         },
			         async : false,
			         dataType:"json",
			         success:function(data){
			            setCookie('need_refresh', 'yes',1);
			            if(point !='true'  && hid.length != 0){
			            	parent.text.location.href="hostManage/searchHostDetails.action?hid="+data;
			            }else if(point != 'add' && hid.length == 0){
			            	parent.text.location.reload();
			            }
			            
			        	$('.tccBox', parent.document).hide();
			         }
			     }); 
		}
		function setCookie(c_name, value, expiredays){
        	var exdate=new Date();
        	exdate.setDate(exdate.getDate() + expiredays);
        	document.cookie=c_name+ "=" + escape(value) + ((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
        	}

		$("#relationPoint").on('input',function(e){  
			 var name = $(this).val();
			 var hostId = $('#rootId').val();
			 $.ajax({
		         type:"post",
		         url:"hostajax/findHostByName.action",
		         data:{
		        	 hostName:name,
		        	 hostId:hostId
		         },
		         async : false,
		         dataType:"json",
		         success:function(data){
		        	var hostList = data.hostList;
	 	        	if(hostList && hostList.length){
		 	        	var str = '<ul class="list-unstyled">';
		 	        	$.each(hostList,function(i,obj){
		 	     			str += '<li onclick="selectObjectLinkage(\''+obj.id+'\',\''+obj.hostName+'\')">';
		 	     			str += obj.hostName
		 	     			str += '</li>';
		 	     		})
			 	   		str += '</ul>';
	 	        	}else{
	 	        		var str = '<ul class="list-unstyled">';
	 	        		str += '<li>noResult</li>';
			 	   		str += '</ul>';
	 	        	}
		 	   		
		 	   		$('.hideDiv').html(str);
		 	   		$('.hideDiv').show();
		         },
		         error:function(){
		        	 parent.hintManager.showHint("查询案件异常，请联系管理员！");
		         }
		     });
			 
			 
		}); 
		//选中对象关联
		function selectObjectLinkage(id,value){
			confirm('已经存在对象：'+value+',是否查看重复对象？',function(){
			window.location.href="lawCaseRelation/getTypeObj.action?rootId=${rootId}&rootType=${rootType}&typeId="+id+"&type=${hostType}";
			});
		}
</script>
	</body>
</html>