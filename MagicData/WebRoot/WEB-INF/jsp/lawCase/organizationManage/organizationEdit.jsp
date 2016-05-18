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
    <title>组织添加</title>
    
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
				<c:when test="${empty orgObj.id && empty rootId}">
					<form id="saveOrgForm" method="post"action="ajax_lawCaseOrg/saveOrg.action">
				</c:when>
				<c:when test="${!empty rootId}">
				      <form id="saveOrgForm" method="post" action="lawCaseOrg/saveOrgRelation.action"  enctype="multipart/form-data">
		        </c:when>
		        <c:otherwise>
		      	      <form id="saveOrgForm" method="post" action="ajax_lawCaseOrg/updateOrg.action" >
		        </c:otherwise>
	  	</c:choose>	
	  	    <c:if test="${!empty orgObj.id}">
			<input type="hidden"  id="orgId" name="orgObj.id" value="${orgObj.id}" />
			</c:if>
			<input type="hidden" id="orgDutyHidden" name="orgObj.orgDutyPersonIds" value="${orgObj.orgDutyPersonIds}" /><!-- 负责人 -->
			<input type="hidden" id="orgDirectionHidden" name="orgObj.orgDirectionCodes" value="${orgObj.orgDirectionCodes}" /><!-- 方向 -->
			<input type="hidden" id="orgRemarkHidden" name="orgObj.orgRemark" value=""/><!-- 纲领 -->
			<input type="hidden" id="orgDesHidden" name="orgObj.orgDescription" value="" /><!-- 简介 -->
			<input type="hidden"  name="rootType" value="${rootType}" />
			<input type="hidden"  name="rootId" value="${rootId}" />
			<input type="hidden"  name="imgTimeNum"  id="imgTimeNum" />
			<input type="hidden"  id="imgHidden" value="${orgObj.orgImage}"/>
			<div class="form-group">
				<label class="labelTit">组织名称</label>
				<c:choose>
				   <c:when test="${empty rootId}">
				      	<input name="orgObj.orgCName" value="${orgObj.orgCName}" type="text" class="form-control required" placeholder="请输入组织名称"  onblur="checkOrgName(this)"/>
				   </c:when>
				   <c:otherwise>
						<input id="orgNameId"  name="orgObj.orgCName" value="${orgObj.orgCName}" type="text" class="form-control required" placeholder="请输入组织名称" onblur="checkOrgName(this)"/>
						<div class="hideDiv"></div>
				   </c:otherwise>
			  	</c:choose>	
			</div>
			<div class="form-group">
				<label class="labelTit">拼音</label>
				<input name="orgObj.orgSpell" value="${orgObj.orgSpell}" type="text" class="form-control" />
			</div>
			<div class="form-group">
				<label class="labelTit">英文名</label>
				<input name="orgObj.orgEName" value="${orgObj.orgEName}" type="text" class="form-control" />
			</div>
			<div class="form-group">
				<label class="labelTit">	别名</label>
				<input name="orgObj.orgAlias" value="${orgObj.orgAlias}" type="text" class="form-control" />
			</div>
			<div class="form-group">
				<label class="labelTit">成立时间</label>
				<input name="orgObj.createTime" readonly="readonly" value="${orgObj.createTime}" id="timeId" type="text" class="form-control required" placeholder="请输入时间" />
			</div>	
			<div class="form-group">
        		<label class="labelTit">组织状态</label>				
				<select class="form-control select" name="orgObj.orgStatus">
				    <c:forEach items="${dataDictionary.orgStatus}" var="orgStatus" >
				    	<c:choose>
						   <c:when test="${orgStatus.key == orgObj.orgStatus}">
						      	<option value="${orgStatus.key}" selected="selected">${orgStatus.value}</option>
						   </c:when>
						   <c:otherwise>
								<option value="${orgStatus.key}">${orgStatus.value}</option>
						   </c:otherwise>
					  	</c:choose>
					</c:forEach>
				</select>
    		</div>			
    		<div class="form-group">
				<label class="labelTit">所在地</label>
				<input name="orgObj.orgLocation" value="${orgObj.orgLocation}" type="text" class="form-control" />
			</div>					
			<div class="form-group">
        		<label class="labelTit">控制状态</label>				
				<select class="form-control select" name="orgObj.orgControlStatus">
				    <c:forEach items="${dataDictionary.controlStatus}" var="controlStatus" >
				    	<c:choose>
						   <c:when test="${controlStatus.key == orgObj.orgControlStatus}">
						      	<option value="${controlStatus.key}" selected="selected">${controlStatus.value}</option>
						   </c:when>
						   <c:otherwise>
								<option value="${controlStatus.key}">${controlStatus.value}</option>
						   </c:otherwise>
					  	</c:choose>
					</c:forEach>
				</select>
    		</div>
    		<div class="form-group textfile">
        			<label class="labelTit">照片</label>
					<div class="file-box">
					    <div id="imgInputDive"><input type='text' name='orgObj.orgImage' id="orgImage" class='txt'  value="${orgObj.orgImage}"/></div>
						<input type='button' class='btnLl' value='浏览...' />
						<input type="file" name="orgImg" class="file" id="orgImg" size="28" onchange="changeImg(this.value)" />
					</div>
            </div>
			<div class="form-group">
        		<label class="labelTit">重要程度</label>				
				<select class="form-control select" name="orgObj.orgImportLevel">
					<c:forEach items="${dataDictionary.level}" var="level" >
						<c:choose>
						   <c:when test="${level.key == orgObj.orgImportLevel}">
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
				<label class="labelTit">所属方向</label>
				<div class='multi_select_direction' style="z-index:1000;" type="orgDirection"></div>
			</div>
    		<div class="form-group">
        			<label class="labelTit">责任人</label>
					<div class='multi_select_dutyPerson' style="z-index:1000;" type="orgDutyPerson"></div>
    		</div>
	        <%-- <div class="form-group" style="width: 100%">
                  <div id="imgDiv" style="display: none;float: left;margin-left: 10%">
                      <img width="116" height="138"  src="images/lawCase/uploadImg/organization/${orgObj.orgImage}"/></div>
            </div> --%>

    		<div class="zaBox">
    			<span class="zaLeft">组织纲领</span>
    			<div class="zaRight">
    				<script id="orgRemarkId" type="text/plain" style="width:1024px;height:200px;">${orgObj.orgRemark}</script>
    			</div>
    		</div>
    		<div class="bzBox">
    			<span class="bzLeft">简介</span>
    			<div class="bzRight">
    				<script id="orgDescriptionId" type="text/plain" style="width:1024px;height:200px;">${orgObj.orgDescription}</script>
    			</div>
    		</div>
    		<div style="clear: both;"></div>
    		<div class="btnDiv">
				<span onclick="saveObj()" class="btn btn-primary sureBtn">保存</span>
				<span onclick="window.parent.dismissParentPop();" id="cancel" class="btn btn-default qxBtn">取消</span>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/scripts/ajaxfileupload.js"></script>
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
							title:"组织关联关系",
							url:"lawCaseRelation/getTypeObj.action?rootId=${rootId}&rootType=${rootType}&typeId="+id+"&type=${targetType}"
						};
						parent.parentPop(ryData);
			 }); 
		 }
		$(function(){
		    var imgFile='${orgObj.orgImage}';
		    if(null==imgFile||""==imgFile||imgFile==undefined){
		       $("#imgDiv").css("display","none");
		       $("#imgInputDiv").css("display","inherit");
		    }else{
		       $("#imgInputDiv").css("display","none");
		       $("#imgDiv").css("display","inherit");
		    }
			//处理组织负责人复选框
			var dutyPersionSelect = [];
			var usernames = $.parseJSON('${userNames}');
			var dutyPersonNames = "${orgObj.orgDutyPersonStr}".split(",");
			if(null!=usernames&&usernames!=undefined){
			    for(var i = 0;i < usernames.length; i++) {
     			    var temp = usernames[i];
     			    if(dutyPersonNames.indexOf(temp)>-1){
     				    dutyPersionSelect.push(true);
     			    }else{
     				   dutyPersionSelect.push(false);
     			    }
			    }
			}
			
			
			$('.multi_select_dutyPerson').MSDL({
	    		'width': 'auto',
	    		'data': usernames,
				'value':$.parseJSON('${userIds}'),
				'value_selected':dutyPersionSelect
	  		});
	  		
			//下拉组织所属方向复选框
			var directionSelect = [];
			var directionkeys = $.parseJSON('${directionKeys}');
			var checkdirectioncodes = "${orgObj.orgDirectionCodes}".split(",");
			for(var i = 0;i < directionkeys.length; i++) {
     			var temp = directionkeys[i];
     			if(checkdirectioncodes.indexOf(temp)>-1){
     				directionSelect.push(true);
     			}else{
     				directionSelect.push(false);
     			}
			}
			$('.multi_select_direction').MSDL({
	    		'width': 'auto',
	    		'data':$.parseJSON('${directionTexts}'),
				'value':directionkeys,
				'value_selected':directionSelect
	  		});
	  		
			//初始化日期
			laydate({
				elem: '#timeId'
			}); 
		});
		
		//初始化文本框
		//组织纲领
		var orgRemarkEditor = new UE.ui.Editor({initialFrameHeight:200,initialFrameWidth:900});
		orgRemarkEditor.render("orgRemarkId");
		var orgRemarkE = UE.getEditor("orgRemarkId");
		//组织描述
		var orgDesEditor = new UE.ui.Editor({initialFrameHeight:200,initialFrameWidth:900});
		orgDesEditor.render("orgDescriptionId");
		var orgDesE = UE.getEditor("orgDescriptionId");
		
		//根据多选下拉框内的选择确定负责人及方向的值
		function selectHandler(selectData){
			var key = selectData.value_arr;
			var text = selectData.text_arr; 
			var flag = selectData.type;
			
			if(flag =="orgDutyPerson"){
				$("#orgDutyHidden").val(key.toString());
			}else if(flag =="orgDirection"){
				$("#orgDirectionHidden").val(key.toString());
			}
		}
		
		$("#orgNameId").on('input',function(e){  
			 var name = $(this).val();
			 name = name.trim();
			 if(name){
				 $.ajax({
			         type:"post",
			         url:"<%=basePath%>ajax_lawCaseOrg/findOrgsByName.action",
			         data:{
			        	 "orgObj.orgCName":name,
			        	 "rootId":"${rootId}",
			        	 "rootType":"${rootType}"
			         },
			         async : false,
			         dataType:"json",
			         success:function(data){
			        	var orgList = data.orgList;
			        	var str = "";
		 	        	if(orgList && orgList.length){
			 	        	str = '<ul class="list-unstyled">';
			 	        	$.each(orgList,function(i,obj){
			 	     			str += '<li onclick="selectLi(\''+obj.id+'\',\''+obj.orgCName+'\')">';
			 	     			str += obj.orgCName;
			 	     			str += '</li>';
			 	     		});
				 	   		str += '</ul>';
		 	        	}else{
		 	        		str = '<ul class="list-unstyled">';
		 	        		str += '<li>noResult</li>';
				 	   		str += '</ul>';
		 	        	}
			 	   		
			 	   		$('.hideDiv').html(str);
			 	   		$('.hideDiv').show();
			         },
			         error:function(){
			        	 parent.hintManager.showHint("查询组织异常，请联系管理员！");
			         }
			     });
				 
			 }
		});
		
		//选中对象关联
		function selectObjLink(id){
			window.location.href="lawCaseRelation/getTypeObj.action?rootId=${rootId}&rootType=${rootType}&typeId="+id+"&type=${targetType}";
		}
		
		//选中图像处理
		function changeImg(imgValue){
		    document.getElementById('orgImage').value=imgValue;
		    if(null!=imgValue&&""!=imgValue&&imgValue!=undefined){
		       $("#imgDiv").css("display","none");
		       $("#imgInputDiv").css("display","inherit");
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
				parent.hintManager.showHint("组织名称、创建时间不能为空，请输入！");
				return;
			}
			
			var currentId = null;
			var currentName = $("input[name='orgObj.orgCName']").val();
			var tempId = $("#orgId").val();
			if(tempId!=undefined){
				currentId = tempId;
			}
			validateFlag = validateOrgName(currentName,currentId);
			if(!validateFlag){return;}
			
			var orgRemarkText = orgRemarkE.getContent();
			var orgDesText = orgDesE.getContent();
			$("#orgRemarkHidden").val(orgRemarkText);
			$("#orgDesHidden").val(orgDesText);
			        
			 var imgFileVal = document.getElementById('orgImage').value;
			 var imgImgHidden = document.getElementById('imgHidden').value;
			 var imgTimeNum = (new Date()).getTime()+"";
			 $("#imgTimeNum").val(imgTimeNum);
			// document.getElementById('orgImage').value=imgTimeNum+imgFileVal;
			 if(null!=imgFileVal&&imgFileVal!=undefined&&imgFileVal!=''){
			   if((null==imgImgHidden||""==imgImgHidden)||(imgImgHidden!=imgFileVal)){
			        $.ajaxFileUpload({
                      url:"<%=basePath%>ajax_lawCaseOrg/saveOrgImg.action",//用于文件上传的服务器端请求地址
                      secureuri:false,//一般设置为false
                      fileElementId:'orgImg',//文件上传空间的id属性
                      data:{
			        	imgTimeNum:imgTimeNum
			         },
                      dataType: 'json',//返回值类型 一般设置为json
                      async : false,
                      success:{},
                      error: function (data, status, e){
                           parent.hintManager.showHint("图片上传异常，请联系管理员！");
                      }
                  });
			   }
			 }

			//保存案件
			var action = $("#saveOrgForm").attr("action");
			var formdata = $("#saveOrgForm").serialize();
			if(action.startWith("ajax")){
				$.ajax({
			         type:"post",
			         url:action,
			         data:formdata,
			         async : false,
			         dataType:"json",
			         success:function(data){
			        	 var mess = data.resultMess;
			        	 if(mess!=null&&mess!=undefined&&mess!=""){
			        		 parent.hintManager.showHint(data.resultMess);
			        	 }else{
			        		 window.parent.dismissParentPop();
				        	 if("${skipFlag}" == "detail"){
				        		 window.parent.text.location.reload();
				        	 }else{
					        	 parent.window.frames["text"].getListData();
				        	 }
			        	 }
			         },
			         error:function(){
			        	 parent.hintManager.showHint("保存组织异常，请联系管理员！");
			         }
			     });
			}else{
				$("#saveOrgForm").submit();//关联对象
			}
		}		
		
		function checkOrgName(note){
			var currentId = null;
			var currentName = note.value;
			var tempId = $("#orgId").val();
			if(tempId!=undefined){
				currentId = tempId;
			}
			validateOrgName(currentId,currentName);
		}
		
		function validateOrgName(currentId,currentName){
			var checkFormFlag = true;
			$.ajax({
		         type:"post",
		         url:"<%=basePath%>ajax_lawCaseOrg/checkOrgNameExsit.action",
		         data:{
		        	 checkName:currentName,
		        	 checkId:currentId
		         },
		         async : false,
		         dataType:"json",
		         success:function(data){
					var exsitStr = data.nameExsitFlag;
					if(exsitStr=="T"){
						checkFormFlag=false;
						parent.hintManager.showHint("该名称的组织已经存在");
					}else{
						checkFormFlag=true;
					}
		         },
		         error:function(){
		        	 parent.hintManager.showHint("组织名称校验异常，请联系管理员！");
		         }
		     });
			return checkFormFlag;
		}
		
	</script>
  </body>
</html>
