<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'addFile.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="styles/lawcase/css/bootstrap.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="styles/lawcase/css/common.css"/>
	<link rel="stylesheet" type="text/css" href="styles/lawcase/css/my.css"/>
	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.min.js"></script>
	<script type="text/javascript" src="scripts/lawCase/my.js" ></script>
	<script type="text/javascript" src="scripts/lawCase/common.js" ></script>
	<script type="text/javascript" src="scripts/lawCase/Popup.js" ></script>
	<script type="text/javascript" src="scripts/lawCase/inputSelect.js" ></script>
    <script type="text/javascript" src="styles/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="styles/bootstrap/js/bootstrap-prompts-alert.js"></script>
    <script src="<%=basePath%>scripts/laydate-v1.1/laydate/laydate.js"></script>
  </head>
  
  <body>
    <div class="wjaddCon">
				<div class="form-group commonDiv">
					<label class="labelTit">文件名称</label>
					<c:choose>
				   <c:when test="${empty rootId}">
				      	<input type="text" class="form-control" placeholder="自动填充" id="fileName"  value="${fileinfo.fileName}" readonly/>
				   </c:when>
				   <c:otherwise>
					   <input type="text" class="form-control" placeholder="请输入文件名称" id="relationPoint"  value="${fileinfo.fileName}"/>
						<div class="hideDiv"></div>
						<input type="hidden" class="form-control"  id="fileNameH"/>
						<input type="hidden" class="form-control" placeholder="请输入姓名" id="arootId" value="${rootId}"/>
						<input type="hidden" class="form-control" placeholder="请输入姓名" id="arootType" value="${rootType}"/>
				   </c:otherwise>
			  	</c:choose>
					<input type="hidden" class="form-control" placeholder="请输入姓名" id="id" value="${fileinfo.id}"/>
					<input type="hidden" id="directionHidden" name="fileinfo.direction" value="${fileinfo.direction}"/><!-- 方向 -->
				</div>
				<div class="form-group commonDiv">
	        			<label class="labelTit">责任人</label>
						<select class="form-control select"  id="responsiblePerson" >
						    <c:forEach items="${userList}" var="level" >
								<c:choose>
								   <c:when test="${level.userName == personinfo.podutyman}">
								      	<option value="${level.userName}" selected="selected">${level.userName}</option>
								   </c:when>
								   <c:otherwise>
								   		<option value="${level.userName}">${level.userName}</option>  
								   </c:otherwise>
							  	</c:choose>
							</c:forEach>
						</select>
	    		</div>
				<div class="form-group commonDiv">
	        		<label class="labelTit">所属方向</label>				
					<div class='multi_select' id="direction" type="directionType" onblur="selectHandler(this)"></div>		
	    		</div>														                    		                		                   		
	    		<div class="form-group textfile commonDiv">
	    			<label class="labelTit">文件</label>
					<div class="file-box">
						<input type='text' name='annexUrl' id='annexUrl' class='txt'  value="${fileinfo.fileName}"/>  
						<input type='button' class='btnLl' value='浏览...' />
						<input type="file" name="fileField" class="file" id="fileField" size="28" onchange="filechange(this)" />
					</div>
	    		</div>
	    		<div style="clear: both;"></div>
	    		<div class="btnDiv">
					<button class="btn btn-primary sureBtn" onclick="saveFileinfo()">保存</button>
					<button class="btn btn-default qxBtn"  onclick="window.parent.dismissParentPop()">取消</button>
				</div>
			</div>
			<script type="text/javascript" src="<%=basePath%>/scripts/ajaxfileupload.js"></script>
			<script type="text/javascript">
			$("#relationPoint").on('input',function(e){  
				 //var fileName =encodeURI(encodeURI($(this).val())) ;
				 var fileName = $(this).val() ;
				 var id = $('#arootId').val();
				 $.ajax({
			         type:"post",
			         url:"fileManage/viewFileAddRSearch.action",
			         data:{
			        	 fileName:fileName,
			        	 id:id
			         },
			         async : false,
			         success:function(data){
			        	 var string = '<ul class="list-unstyled">';
	      					var list=data.split(",");
	      					if(list.length>1){
	      						for(var i=0;list.length-1>i;i++){
		      						string+='<li onclick=selectLi("'+list[i].split(";")[0]+'","'+list[i].split(";")[1]+'")>'+list[i].split(";")[1]+'</li>';
		      					}
	      						string += '</ul>';
	      					}else{
	      						string += '<li>noResult</li>';
	      						string += '</ul>';
	      					}
	      					
	      					$('.hideDiv').html(string);
	      					$(".hideDiv").show();
			         },
			         error:function(){
			        	 parent.hintManager.showHint("查询文件异常，请联系管理员！");
			         }
			     });
				 
			}); 
			function saveFileinfo(){
			    var id=$('#id').val();
			    var relationPoint = $('#relationPoint').val();
			    var fileName=$('#fileName').val();
			    if(fileName == undefined){
			    	fileName=relationPoint;
			    	if(fileName==''){
				    	parent.hintManager.showHint("文件名称不能为空，请输入！");
						return;
				    }
			    	if(fileName!=$('#fileField').val().split('\\')[$('#fileField').val().split('\\').length-1]){
				    	parent.hintManager.showHint("文件名称跟上传文件名称必须一致，请修改名称后再上传！");
						return;
				    }
			    }
			    //fileName = encodeURIComponent(encodeURIComponent(fileName));
				var direction=$('#directionHidden').val();
				if(direction==''){
			    	parent.hintManager.showHint("请选择方向");
					return;
			    }
				var responsiblePerson=$('#responsiblePerson').val();
				var annexUrl=$('#annexUrl').val();
				if(annexUrl==''){
			    	parent.hintManager.showHint("请选择上传文件");
					return;
			    }
				var rootId=$('#arootId').val();
				var rootType=$('#arootType').val();
				$.ajaxFileUpload({
                    url:"fileManage/fileMangeSave.action",//用于文件上传的服务器端请求地址
                    secureuri:false,//一般设置为false
                    fileElementId:'fileField',//文件上传空间的id属性
                    data:{
		        	 	id:id,
		        	 	fileName:fileName,
		        	 	annexUrl:annexUrl,
		        	 	direction:direction,
			        	responsiblePerson:responsiblePerson,
		            	rootId:rootId,
		            	rootType:rootType
		         },
                    dataType: 'String',//返回值类型 一般设置为json
                    async : false,
                    success:function(data, status){
                    	if(rootId!=undefined){
                    		window.parent.text.location.reload();
                    	}else{
                    		window.parent.dismissParentPop();
                    		if("${fromtype}" == "detail" ){
                    			window.parent.text.location.reload();
                        	}else{
                        		parent.window.frames["text"].searchPList();
                        	}
                    	}
                    	 
		        	 $('.tccBox', parent.document).hide();
		        	 parent.hintManager.showInfoHint("文件上传完成！");
                    },
                    error: function (data, status, e){
                         parent.hintManager.showHint("文件上传异常，请联系管理员！");
                    }
                });
			    
		} 
			//多选下拉框
			function selectHandler(selectData){
				var key = selectData.value_arr;
				var text = selectData.text_arr; 
				var flag = selectData.type;
				if(flag =="directionType"){
					$("#directionHidden").val(key.toString());
				}
			}
  $(function(){
	//下拉复选框
		var selectValues = [];
		var directionkeys = $.parseJSON('${directionKeys}');
		var directionTexts = $.parseJSON('${directionTexts}');
		var checkdirectioncodes = "${fileinfo.direction}".split(",");
		var selectValuesH = "";
		for(var i = 0;i < directionTexts.length; i++) {
 			var temp = directionTexts[i];
 			if(checkdirectioncodes.indexOf(temp)>-1){
 				selectValues.push(true);
 				
 			}else{
 				selectValues.push(false);
 			}
		}
		for(var i = 0;i < directionTexts.length; i++) {
 			var temp = directionTexts[i];
 			if(checkdirectioncodes.indexOf(temp)>-1){
 				selectValuesH+=directionkeys[i]+",";
 				
 			}
		}
		$('#directionHidden').val(selectValuesH.substring(0, selectValuesH.length-1));
		$('.multi_select').MSDL({
			'width': 'auto',
    		'data':directionTexts,
			'value':directionkeys,
			'value_selected':selectValues
		});
		laydate({
			   elem: '#createTime'
			}); 
	});
  function selectLi(id,value){
		 $(".hideDiv").hide();
		 $("#fileName").val(value);
		 $("#fileNameH").val(value);
		  confirm('已经存在对象：'+value+',是否查看重复对象',function(){
			 var ryData = {
						title:"文件关联关系",
						url:"<%=basePath%>lawCaseRelation/getTypeObj.action?rootId=${rootId}&rootType=${rootType}&typeId="+id+"&type=${fileType}"
					};
					parent.parentPop(ryData);
		 }); 
	 }
  function filechange(a){
		var img=$('#fileField').val();
		$('#annexUrl').val(img);
		$('#fileName').val(img.split('\\')[img.split('\\').length-1]);
		$('#relationPoint').val(img.split('\\')[img.split('\\').length-1]);
	}
  </script>
  
  </body>
  
</html>
