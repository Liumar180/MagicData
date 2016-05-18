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
    <title>案件添加</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/common.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/my.css"/>
  </head>
  
  <body>
    <!--点击添加按钮出现的弹出层-->
	<div class="addCon">
		<c:choose>
		   <c:when test="${empty caseObject.id && empty rootId}">
				<form id="saveCaseForm" method="post" action="ajaxCase/saveCase.action">
		   </c:when>
		   <c:when test="${!empty rootId}">
				<form id="saveCaseForm" method="post" action="case/saveCaseRelation.action">
		   </c:when>
		   <c:otherwise>
		      	<form id="saveCaseForm" method="post" action="ajaxCase/updateCase.action">
		   </c:otherwise>
	  	</c:choose>		
			<input type="hidden" id="caseId" name="caseObject.id" value="${caseObject.id}" />
			<input type="hidden" id="caseUserNamesHidden" name="caseObject.caseUserNames" value="${caseObject.caseUserNames}" /><!-- 案件成员 -->
			<input type="hidden" id="directionCodeHidden" name="caseObject.directionCode" value="${caseObject.directionCode}" /><!-- 方向 -->
			<input type="hidden" id="caseAimHidden" name="caseObject.caseAim" value="" /><!-- 案件目标 -->
			<input type="hidden" id="memoHidden" name="caseObject.memo" value="" /><!-- 备注 -->
			<input type="hidden"  name="rootType" value="${rootType}" />
			<input type="hidden"  name="rootId" value="${rootId}" />
			<input type="hidden" name="caseNameOrg" value="${caseObject.caseName}" id="caseNameOrg" />
			<div class="form-group">
				<label class="labelTit">专案名称</label>
				<c:choose>
				   <c:when test="${empty rootId}">
				      	<input name="caseObject.caseName" value="${caseObject.caseName}" type="text" class="form-control required" placeholder="请输入专案名称" />
				   </c:when>
				   <c:otherwise>
						<input id="caseNameId" name="caseObject.caseName" value="${caseObject.caseName}" type="text" class="form-control required" placeholder="请输入专案名称" />
						<div class="hideDiv"></div>
				   </c:otherwise>
			  	</c:choose>		
				
			</div>
			<div class="form-group">
				<label class="labelTit">成立时间</label>
				<input name="caseObject.createTime" readonly="readonly" value="${caseObject.createTime}" id="timeId" type="text" class="form-control required" placeholder="请输入时间" />
			</div>									
			<div class="form-group">
        		<label class="labelTit">专案级别</label>				
				<select class="form-control select" name="caseObject.caseLevel">
					<c:forEach items="${dataDictionary.level}" var="level" >
						<c:choose>
						   <c:when test="${level.key == caseObject.caseLevel}">
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
        		<label class="labelTit">专案组长</label>				
				<select class="form-control select" name="caseObject.caseLeader">
					<c:forEach items="${userList}" var="user" >
						<c:choose>
						   <c:when test="${user.userName == caseObject.caseLeader}">
						      	<option value="${user.userName}" selected="selected">${user.userName}</option>
						   </c:when>
						   <c:otherwise>
								<option value="${user.userName}">${user.userName}</option>
						   </c:otherwise>
					  	</c:choose>
					</c:forEach>
				</select>
    		</div>
    		<div class="form-group">
        		<label class="labelTit">督办人员</label>				
				<select class="form-control select" name="caseObject.caseSupervisor">
				    <c:forEach items="${userList}" var="user" >
				    	<c:choose>
						   <c:when test="${user.userName == caseObject.caseSupervisor}">
						      	<option value="${user.userName}" selected="selected">${user.userName}</option>
						   </c:when>
						   <c:otherwise>
								<option value="${user.userName}">${user.userName}</option>
						   </c:otherwise>
					  	</c:choose>
					</c:forEach>
				</select>
    		</div>
    		<div class="form-group">
        			<label class="labelTit">专案成员</label>
					<div class='multi_select' style="z-index:1000;" type="caseUser"></div>
    		</div>
    		<div class="form-group">
        		<label class="labelTit">案件状态</label>				
				<select class="form-control select" name="caseObject.caseStatus">
				    <c:forEach items="${dataDictionary.caseStatus}" var="caseStatus" >
				    	<c:choose>
						   <c:when test="${caseStatus.key == caseObject.caseStatus}">
						      	<option value="${caseStatus.key}" selected="selected">${caseStatus.value}</option>
						   </c:when>
						   <c:otherwise>
								<option value="${caseStatus.key}">${caseStatus.value}</option>
						   </c:otherwise>
					  	</c:choose>
					</c:forEach>
				</select>
    		</div>
        	<div class="form-group">
				<label class="labelTit">所属方向</label>
				<!--<input type="password" class="form-control" placeholder="请输入方向" />-->
				<div class='multi_select1' style="z-index:1000;" type="caseDirection"></div>
			</div>	 
    		<div class="zaBox">
    			<span class="zaLeft">专案目标</span>
    			<div class="zaRight">
    				<script id="caseAimId" type="text/plain" style="width:1024px;height:200px;">${caseObject.caseAim}</script>
    			</div>
    		</div>
    		<div class="bzBox">
    			<span class="bzLeft">备注信息</span>
    			<div class="bzRight">
    				<script id="memoId" type="text/plain" style="width:1024px;height:200px;">${caseObject.memo}</script>
    			</div>
    		</div>
    		<div style="clear: both;"></div>
    		<div class="btnDiv">
				<span onclick="saveObj()" class="btn btn-primary sureBtn">保存</span>
				<span onclick="cancelSave()" id="cancel" class="btn btn-default qxBtn">取消</span>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>scripts/lawCase/my.js" ></script>
	<script type="text/javascript" src="<%=basePath%>scripts/lawCase/common.js" ></script>
	<script type="text/javascript" src="<%=basePath%>scripts/lawCase/Popup.js" ></script>
	<script type="text/javascript" src="<%=basePath%>scripts/lawCase/caseXq.js" ></script>
	<script type="text/javascript" src="<%=basePath%>scripts/lawCase/inputSelect.js" ></script>
	<script type="text/javascript" src="<%=basePath%>scripts/customJs.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>scripts/ueditor-jsp/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>scripts/ueditor-jsp/ueditor.all.min.js"> </script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>scripts/ueditor-jsp/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" src="styles/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="styles/bootstrap/js/bootstrap-prompts-alert.js"></script> 
    <script type="text/javascript" src="<%=basePath%>scripts/laydate-v1.1/laydate/laydate.js"></script>
	<script type="text/javascript">
		function selectLi(id,value){
			 $(".hideDiv").hide();
			 confirm('已经存在对象：'+value+',是否查看重复对象',function(){
				 var ryData = {
							title:"案件关联关系",
							url:"lawCaseRelation/getTypeObj.action?rootId=${rootId}&rootType=${rootType}&typeId="+id+"&type=${caseType}"
							//url:"<%=basePath%>lawCaseRelation/getTypeObj.action?rootId="+${personinfoR.id}+"&rootType=people&typeId="+id+"&type=people",
						};
						parent.parentPop(ryData);
			 });
		 }
		$(function(){
			//下拉复选框
			var selectValues1 = [];
			var selectValues2 = [];
			var usernames = $.parseJSON('${userNames}');
			var checkusernames = "${caseObject.caseUserNames}".split(",");
			for(var i = 0;i < usernames.length; i++) {
     			var temp = usernames[i];
     			if(checkusernames.indexOf(temp)>-1){
     				selectValues1.push(true);
     			}else{
     				selectValues1.push(false);
     			}
			}
			var directionkeys = $.parseJSON('${directionKeys}');
			var checkdirectioncodes = "${caseObject.directionCode}".split(",");
			for(var i = 0;i < directionkeys.length; i++) {
     			var temp = directionkeys[i];
     			if(checkdirectioncodes.indexOf(temp)>-1){
     				selectValues2.push(true);
     			}else{
     				selectValues2.push(false);
     			}
			}
			$('.multi_select').MSDL({
	    		'width': 'auto',
	    		'data': usernames,
				'value':$.parseJSON('${userIds}'),
				'value_selected':selectValues1
	  		});
			
			$('.multi_select1').MSDL({
	    		'width': 'auto',
	    		'data':$.parseJSON('${directionTexts}'),
				'value':directionkeys,
				'value_selected':selectValues2
	  		});
			//初始化日期
			laydate({
				elem: '#timeId'
			}); 
		});
		
		//初始化文本框
		var caseAimEditor = new UE.ui.Editor({initialFrameHeight:200,initialFrameWidth:900});
		caseAimEditor.render("caseAimId");
		var caseAimE = UE.getEditor("caseAimId");
		
		var memoEditor = new UE.ui.Editor({initialFrameHeight:200,initialFrameWidth:900});
		memoEditor.render("memoId");
		var memoE = UE.getEditor("memoId");
		
		//多选下拉框
		function selectHandler(selectData){
			var key = selectData.value_arr;
			var text = selectData.text_arr; 
			var flag = selectData.type;
			
			if(flag =="caseUser"){
				$("#caseUserNamesHidden").val(text.toString());
			}else if(flag =="caseDirection"){
				$("#directionCodeHidden").val(key.toString());
			}
		}
		
		//提交
		function saveObj(){
			var validateFlag = true;
			$("form :input.required").each(function(){
				var temp = $(this).val();
				if(temp.trim() == ""){
					validateFlag = false;
					return false;
				}
	        });
			if(!validateFlag){
				parent.hintManager.showHint("案件名称、创建时间不能为空，请输入！");
				return;
			}
			var caseAimText = caseAimE.getContent();
			var memoText = memoE.getContent();
			$("#caseAimHidden").val(caseAimText);
			$("#memoHidden").val(memoText);
			//保存案件
			var action = $("#saveCaseForm").attr("action");
			if(action.startWith("ajax")){
				$.ajax({
			         type:"post",
			         url:action,
			         data:$("#saveCaseForm").serialize(),
			         async : false,
			         dataType:"json",
			         success:function(data){
			        	 if(data.result.repeatFlag=="true"){
			        		 parent.hintManager.showHint(data.result.message);
			        	 }else{
			        		 window.parent.dismissParentPop();
				        	 if("${skipFlag}" == "detail"){
				        		 window.parent.text.location.reload();
				        	 }else{
					        	 parent.window.frames["text"].searchList();
				        	 }
			        	 }
			         },
			         error:function(){
			        	 parent.hintManager.showHint("保存案件异常，请联系管理员！");
			         }
			     });
			}else{
				$.ajax({
			         type:"post",
			         url:"ajaxCase/findCaseByNameExact.action",
			         data:$("#saveCaseForm").serialize(),
			         async : false,
			         dataType:"json",
			         success:function(data){
			        	 if(data.result.repeatFlag=="true"){
			        		 parent.hintManager.showHint(data.result.message);
			        	 }else{
			        		 $("#saveCaseForm").submit();//关联对象
			        	 }
			         },
			         error:function(){
			        	 parent.hintManager.showHint("保存案件关联异常，请联系管理员！");
			         }
			     });
			}
		}
		
		//取消
		function cancelSave(){
			window.parent.dismissParentPop();
		}
		
		
		$("#caseNameId").on('input',function(e){  
			 var name = $(this).val();
			 name = name.trim();
			 if(name){
				 $.ajax({
			         type:"post",
			         url:"ajaxCase/findCaseByName.action",
			         data:{
			        	 "caseObject.caseName":name,
			        	 "caseObject.id":"${rootId}"
			         },
			         async : false,
			         dataType:"json",
			         success:function(data){
			        	var caseList = data.caseList;
		 	        	if(caseList && caseList.length){
			 	        	var str = '<ul class="list-unstyled">';
			 	        	$.each(caseList,function(i,obj){
			 	     			str += '<li onclick="selectLi(\''+obj.id+'\',\''+obj.caseName+'\')">';
			 	     			str += obj.caseName
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
				 
			 }
		}); 
		
		//选中对象关联
		function selectObjectLinkage(id){
			window.location.href="lawCaseRelation/getTypeObj.action?rootId=${rootId}&rootType=${rootType}&typeId="+id+"&type=${caseType}";
		}
	</script>
  </body>
</html>
