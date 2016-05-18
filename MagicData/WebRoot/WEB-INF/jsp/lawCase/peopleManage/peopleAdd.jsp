<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'peopleLayout.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/common.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/my.css"/>
	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>scripts/lawCase/my.js" ></script>
	<script type="text/javascript" src="<%=basePath%>scripts/lawCase/common.js" ></script>
	<script type="text/javascript" src="<%=basePath%>scripts/lawCase/Popup.js" ></script>
	<script type="text/javascript" src="<%=basePath%>scripts/lawCase/inputSelect.js" ></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>scripts/ueditor-jsp/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>scripts/ueditor-jsp/ueditor.all.min.js"> </script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>scripts/ueditor-jsp/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript" src="styles/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="styles/bootstrap/js/bootstrap-prompts-alert.js"></script> 
	<script src="<%=basePath%>scripts/laydate-v1.1/laydate/laydate.js"></script>
	<script type="text/javascript" src="<%=basePath%>/scripts/ajaxfileupload.js"></script>
	<script type="text/javascript">
	var imgas={"1":"jpg","2":"bmp","3":"tiff","4":"gif","5":"psd","6":"JPEG","7":"png"};
	var reg = '^[A-Za-z]+$';	
		 function selectLi(id,value){
			 $(".hideDiv").hide();
			 $("#apocnname").val(value);
			 $("#apocnnameH").val(value);
			 confirm('已经存在对象：'+value+',是否查看重复对象',function(){
				 var ryData = {
							title:"人员关联关系",
							url:"lawCaseRelation/getTypeObj.action?rootId=${rootId}&rootType=${rootType}&typeId="+id+"&type=${peopleType}"
							//url:"<%=basePath%>lawCaseRelation/getTypeObj.action?rootId="+${personinfoR.id}+"&rootType=people&typeId="+id+"&type=people",
						};
						parent.parentPop(ryData);
			 });
		 }
	function savePersoninfo(){
			    var id=$('#aid').val();
			    var relationPoint = $('#relationPoint').val();
			    var pocnname=$('#apocnname').val();
			    if(pocnname==''){
			    	parent.hintManager.showHint("人员名称不能为空，请输入！");
					return;
			    }else if(pocnname == undefined){
			    	pocnname=relationPoint;
			    }
				var ponamespell=$('#aponamespell').val();
				var poenname=$('#apoenname').val();
				if(poenname!=""&&!poenname.match(reg)){
					parent.hintManager.showHint("英文名必须为英文字母组成！");
					return false;
				}
				var poalias=$('#apoalias').val();
				var posex=$('#aposex').val();//
				var pobirthday=$('#apobirthday').val();//
				var pocountry=$('#apocountry').val();//
				var ponational=$('#aponational').val();//
				var pohukou=$('#apohukou').val();
				var podutyman=$('#apodutyman').val();//
				var popersonstatus=$('#apopersonstatus').val();//
				var poimportantlevel=$('#apoimportantlevel').val();//
				if(poimportantlevel==''){
			    	parent.hintManager.showHint("请选择重要程度！");
					return;
			    }
				var podirectionof=$('#apodirectionofHidden').val();//
				if(podirectionof==''){
			    	parent.hintManager.showHint("请选择所属方向！");
					return;
			    }
				var pocontrolstatus=$('#apocontrolstatus').val();//
				var polocation=$('#apolocation').val();
				var poimage=$('#apoimage').val();
				
				var imgSuffix = poimage.split(".")[poimage.split(".").length-1].toLowerCase()
				if(poimage!=''){
					/* for (var key in imgas) { 
						if(imgas[key].toLowerCase()==poimage.split(".")[poimage.split(".").length-1].toLowerCase()){
							alert(imgas[key].toLowerCase+"-------"+poimage.split(".")[poimage.split(".").length-1]);
							poimage=$('#apoimage').val();
							break;
						}else{
							poimage='null';
						}
			        }  */
			        if(imgSuffix!="jpg"&&imgSuffix!="bmp"&&imgSuffix!="tiff"&&imgSuffix!="gif"&&imgSuffix!="psd"&&imgSuffix!="jpeg"&&imgSuffix!="png"){
			        	parent.hintManager.showHint("只能上传JPG,BMP,TIFF,GIF,PNG格式。");
						return;
			        }
					/* if(poimage=='null'){
				    	parent.hintManager.showHint("只能上传JPG,BMP,TIFF,GIF,PNG格式。");
						return;
				    } */
				}
				var rootId=$('#arootId').val();
				var rootType=$('#arootType').val();
				var podescription=UE.getEditor('aeditor').getContent();
				$.ajaxFileUpload({
                    url:"peoplemanage/peopleMangeSave.action",//用于文件上传的服务器端请求地址
                    secureuri:false,//一般设置为false
                    fileElementId:'fileField',//文件上传空间的id属性
                    data:{
                    	id:id,
		        	 	pocnname:pocnname,
	            		ponamespell:ponamespell,
	            		poenname:poenname,
	            		poalias:poalias,
	            		posex:posex,
	            		pobirthday:pobirthday,
	            		pocountry:pocountry,
	            		ponational:ponational,
	            		pohukou:pohukou,
	            		podutyman:podutyman,
	            		popersonstatus:popersonstatus,
	            		poimportantlevel:poimportantlevel,
	            		podirectionof:podirectionof,
	            		pocontrolstatus:pocontrolstatus,
	            		polocation:polocation,
	            		poimage:poimage,
	            		podescription:podescription,
	            		rootId:rootId,
	            		rootType:rootType
		         },
                    dataType: 'String',//返回值类型 一般设置为json
                    async : false,
                    success:function(data, status){
                    	if(rootId!=undefined){
                    		window.parent.text.location.reload();
                    	}else{
                    		if("${fromtype}" != "detail" ){
                        	 	parent.window.frames["text"].searchPList();
                        	}else{
                        		 window.parent.text.location.reload();
                        	}
                    	}
                    	 
		        	 $('.tccBox', parent.document).hide();
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
			if(flag =="apodirectionofType"){
				$("#apodirectionofHidden").val(key.toString());
			}
		}
		</script>
  </head>
  
  <body>
    <div class="ryaddCon">
				<div class="form-group">
					<label class="labelTit">姓名</label>
				<c:choose>
				   <c:when test="${empty rootId}">
				      	<input type="text" class="form-control" placeholder="请输入姓名" id="apocnname" value="${personinfo.pocnname}"/>
				   </c:when>
				   <c:otherwise>
					   <input type="hidden" class="form-control" placeholder="请输入姓名" id="arootId" value="${rootId}"/>
						<input type="hidden" class="form-control" placeholder="请输入姓名" id="arootType" value="${rootType}"/>
						<input type="text" class="form-control" placeholder="请输入姓名" id="relationPoint" value="${personinfo.pocnname}"/>
						<div class="hideDiv"></div>
				   </c:otherwise>
			  	</c:choose>
					<input type="hidden" class="form-control" placeholder="请输入姓名" id="apocnnameH"/>
					<input type="hidden" id="apodirectionofHidden" name="personinfo.apodirectionof" value="${personinfo.podirectionof}" /><!-- 方向 -->
					<input type="hidden" class="form-control" placeholder="请输入姓名" id="aid" value="${personinfo.id}"/>
					
				</div>
				<div class="form-group">
					<label class="labelTit">拼音</label>
					<input type="text" class="form-control" placeholder="可自动识别"  id="aponamespell"  value="${personinfo.ponamespell}" />
				</div>
				<div class="form-group">
					<label class="labelTit">英文名</label>
					<input type="text" class="form-control" placeholder="请输入英文名"  id="apoenname"   value="${personinfo.poenname}" />
				</div>
				<div class="form-group">
            			<label class="labelTit">责任人</label>
						<select class="form-control select"  id="apodutyman" >
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
				<div class="form-group">
					<label class="labelTit">别名</label>
					<input type="text" class="form-control" placeholder="请输入别名"  id="apoalias"   value="${personinfo.poalias}" />
				</div>
				<div class="form-group">
            		<label class="labelTit">性别</label>				
					<select class="form-control select"  id="aposex" >
					    <option>男</option>
					    <option>女</option>						   
					</select>
        		</div>
        		
        		<div class="form-group">
					<label class="labelTit">出生日期</label>
					<input type="text" class="form-control" placeholder="请输入出生日期"  id="apobirthday"   value="${personinfo.pobirthday}"/>
				</div>
				<div class="form-group">
            		<label class="labelTit">国籍</label>				
					<select class="form-control select"  id="apocountry" >
					    <c:forEach items="${dataDictionary.country}" var="level" >
							<c:choose>
							   <c:when test="${level.value == personinfo.pocountry}">
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
            		<label class="labelTit">民族</label>				
					<select class="form-control select"  id="aponational" >
					    <c:forEach items="${dataDictionary.nation}" var="level" >
							<c:choose>
							   <c:when test="${level.value == personinfo.ponational}">
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
					<label class="labelTit">户籍地</label>
					<input type="text" class="form-control" placeholder="请输入户籍地"  id="apohukou"  value="${personinfo.pohukou}"/>
				</div>							
				
        		<div class="form-group">
            		<label class="labelTit">人员状态</label>				
					<select class="form-control select"  id="apopersonstatus" >
						<c:forEach items="${dataDictionary.personStatus}" var="level" >
							<c:choose>
							   <c:when test="${level.value == personinfo.popersonstatus}">
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
            		<label class="labelTit">重要程度</label>				
					<select class="form-control select"  id="apoimportantlevel" >
					    <c:forEach items="${dataDictionary.level}" var="level" >
							<c:choose>
							   <c:when test="${level.value == personinfo.poimportantlevel}">
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
            		<div class='multi_select' id="apodirectionof" type="apodirectionofType" onblur="selectHandler(this)"></div>			
        		</div>	  
				<div class="form-group">
            		<label class="labelTit">控制状态</label>				
					<select class="form-control select"  id="apocontrolstatus" >
					    <c:forEach items="${dataDictionary.controlStatus}" var="level" >
							<c:choose>
							   <c:when test="${level.value == personinfo.pocontrolstatus}">
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
					<label class="labelTit">所在地</label>
					<input type="text" class="form-control" placeholder="请输入所在地"  id="apolocation"  value="${personinfo.polocation}"/>
				</div>														                    		                		                   		
        		<div class="form-group textfile">
        			<label class="labelTit">照片</label>
					<div class="file-box">
						<input type='text' name='apoimage' id="apoimage" class='txt'  value="${personinfo.poimage}"/>  
						<input type='button' class='btnLl' value='浏览...' />
						<input type="file" name="fileField" class="file" id="fileField" size="28" onchange="document.getElementById('apoimage').value=this.value" />
					</div>
        		</div>	
        		<div class="clear"></div>
        		<div class="zaBox">
        			<span class="zaLeft">个人简介</span>
        			<div class="zaRight">
        				 <script id="aeditor" type="text/plain" style="width:1024px;height:200px;">${personinfo.podescription}</script>
        			</div>
        		</div>
        		<div style="clear: both;"></div>
	    		<div class="btnDiv">
					<span class="btn btn-primary sureBtn" onclick="savePersoninfo()">保存</span>
					<span class="btn btn-default qxBtn" onclick="window.parent.dismissParentPop()">取消</span>
				</div>
		</div>
		<script type="text/javascript">
		$("#relationPoint").on('input',function(e){  
			 //var pocnname =encodeURI(encodeURI($(this).val())) ;
			 var pocnname = $(this).val();
			 var id = $('#arootId').val();
			 $.ajax({
		         type:"post",
		         url:"peoplemanage/viewPeopleAddRSearch.action",
		         data:{
		        	 pocnname:pocnname,
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
		        	 parent.hintManager.showHint("查询人员异常，请联系管理员！");
		         }
		     });
			 
		}); 
  $(function(){
	//下拉复选框
		var selectValues = [];
		var selectValuesH ="";
		var directionkeys = $.parseJSON('${directionKeys}');
		var directionTexts = $.parseJSON('${directionTexts}');
		var checkdirectioncodes = "${personinfo.podirectionof}".split(",");
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
		$('#apodirectionofHidden').val(selectValuesH.substring(0, selectValuesH.length-1));
		$('.multi_select').MSDL({
			'width': 'auto',
    		'data':directionTexts,
			'value':directionkeys,
			'value_selected':selectValues
		});
		laydate({
			   elem: '#apobirthday'
			}); 
		var ue = UE.getEditor('aeditor');
	});
  </script>
  </body>
  
</html>
