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
    
    <title>系统配置</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="styles/lawcase/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="styles/systemManage/css/reset.css"/>
	
	<style type="text/css">
		.abc {
		    color: #ffb517;
		    width: 78%;
		}
		tr .tdWidth {
			width: 22%;
		}
		.systemDiv {
    		border: 2px solid #1b31b6;
    		/* height: 100%; */
    		padding: 0 10px 10px;
		}
		#tableid,#tableid2,#tableid3{
			width:100%;
			margin:10px auto 0px auto;
		}
		#tableid .titleTr,#tableid2 .titleTr,#tableid3 .titleTr{
			width:100%;
			height:40px;
			line-height:40px;
			background:#264494;
			color:#30fffd;
		}
		#tableid .firstTd{
			padding-left:40px;
		}
		#tableid2 .firstTd{
			padding-left:40px;
			width: 30%;
		}
		#tableid3 .firstTd{
			padding-left:40px;
			width: 34%;
		}
		#tableid select{
			height:32px;
			lien-height:32px;
			background: #091c43;
    		border: 1px solid #2548a6;
    		padding-left:5px;
		}
		.a-upload {
		    padding: 4px 10px;
		    line-height: 20px;
		    position: relative;
		    cursor: pointer;
		    color: #fff;
		    background: #1c3f8e;
		    border: 1px solid #3b66dd;
		    border-radius: 4px;
		    overflow: hidden;
		    display: inline-block;
		    *display: inline;
		    *zoom: 1;
		    font-size:14px;
		    float: left;
		}
		
		.a-upload  input {
		    position: absolute;
		    /* font-size: 100px; */
		    right: 0;
		    top: 0;
		    opacity: 0;
		    filter: alpha(opacity=0);
		    cursor: pointer
		}
		
		.a-upload:hover {
		    color: #fff;
		    background: #1c3f8e;
		    border-color: #3b66dd;
		    text-decoration: none
		}
		.showFileName{
			/* width: 20%; */
			height: 30px;
			line-height: 30px;
			display: inline-block;
			float: left;
			margin-left: 20px;
			border: none;
			background: none;
		}
		.userTab{
			width: 96%;
			margin: 0 auto;
			border-bottom: 1px solid #009cff;
		}
		.userTab span{
			color: #fff;
			margin-right: 10px;
			cursor: pointer;
			background: #012548;
			border: 1px solid #009cff;
			padding: 6px 20px;
			border-radius: 5px 5px 0px 0px;
			border-bottom: none;
			display: inline-block;
		}
		.userTab span.spanSeton{
			background: #00538e;
			border: 1px solid #009cff;
		}
		.userconBox{
			padding-top:20px;
		}
		.hideUsercon{
			display: none;
		}

	</style>
  </head>
  
  <body>
  	<div>
  		<div class="userTab">
			<span class="spanSeton">默认显示属性</span>
			<span >可查询属性</span>
			<span >属性别名</span>
		</div>
		<div class="userCon clearfix" >
			<div class="systemDiv">
				<table id="tableid" class="list-unstyled clearfix">
					<tr class="titleTr">
						<td class="firstTd">节点类型</td>
						<td>第一默认值</td>
						<td>第二默认值</td>
						<td>图片预览</td>
						<td>图标修改</td>
					</tr>
				</table>
			  	<div class="resetbtnDiv">
			    	<input type="button" class="resetBtn"  onclick="saveDefaltAttr()" value="保存">
			    </div>
		    </div>
		</div>
		<div class="userCon hideUsercon clearfix">
			<div class="systemDiv">
				<table id="tableid2" class="list-unstyled clearfix">
					<tr class="titleTr">
						<td class="firstTd">节点类型</td>
						<td>可查询属性</td>
					</tr>
				</table>
				<div class="resetbtnDiv">
			    	<input type="button" class="resetBtn"  onclick="saveQueryable()" value="保存">
			    </div>
			</div>
		</div>
		<div class="userCon hideUsercon clearfix">
			<div class="systemDiv">
				<table id="tableid3" class="list-unstyled clearfix">
					<tr class="titleTr">
						<td class="firstTd">类型-属性名称</td>
						<td>类型-属性别名</td>
					</tr>
				</table>
				<div class="resetbtnDiv">
			    	<input type="button" class="resetBtn"  onclick="saveAlias()" value="保存">
			    </div>
			</div>
		</div>
  	</div>
    <script type="text/javascript" src="scripts/jquery.min.js"></script>
    <script type="text/javascript" src="styles/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="styles/bootstrap/js/bootstrap-prompts-alert.js"></script>
	<script type="text/javascript" src="scripts/ajaxfilesupload.js"></script>
    <script type="text/javascript">
    	var datas ;
    	var showArr;
    	var aliasAll;
    	$(function(){
    		$(".userTab span").click(function(){
    	  		$(this).addClass("spanSeton").siblings("span").removeClass("spanSeton");
    	  		var tabIndex = $(".userTab span").index(this); 
    	  		$(".userCon").eq(tabIndex).show().siblings(".userCon").hide();
    		});
    		
    		/*方法加载顺序getTitanAlias（）、getTitanStr、getDefaultShow**/
    		getTitanAlias();
    		
    		function getTitanStr(){
    			$.ajax({
        			type:"post",
        			url:"dataManage/getTitanStructure.action",
        			success:function(data){
        				datas = data;
        				getDefaultShow();
        				var vertexs = data[0];
        				var html2 = "";
        				for(var lable in vertexs){
        					var aliasArr = aliasAll[lable];
    						html2 += "<tr style='line-height: 50px;'>";
        					html2 += "<td style='width:18%;padding-left:40px;'>"+aliasArr[lable]+"</td>";
        					var propertyArr = vertexs[lable];
        					if(propertyArr){
        						html2 += "<td style='width: 58%;' class='tdWidth checkboxtd'>";
        						for(var i=0;i<propertyArr.length;i++){
        							var index = propertyArr[i].index;
        							var queryable = propertyArr[i].queryable;
        							if(index==1){
        								if(queryable==1){
        									html2 += "<input type='checkbox' name='"+propertyArr[i].name+"' id='"+propertyArr[i].name+"' checked />"+aliasArr[propertyArr[i].name]+"&nbsp;&nbsp;&nbsp;"; 
        								}else{
        									html2 += "<input type='checkbox' name='"+propertyArr[i].name+"' id='"+propertyArr[i].name+"'    />"+aliasArr[propertyArr[i].name]+"&nbsp;&nbsp;&nbsp;"; 
        								}
        							}
        						}
        						html2 += "</td>";
        					}
        					html2 += "</tr>";
        				}
        				$("#tableid2").append(html2);
        			}
        		});
    		}
    		
    		function getTitanAlias(){
    			$.ajax({
        			type:"post",
        			url:"titanStruManage/findTitanAlias.action",
        			success:function(data){
        				aliasAll = data;
        				getTitanStr();
        				for(var label in data){
        					var aliasArr = data[label];
        					for(var attr in aliasArr){
        						var line = "";
        						line += "<tr style='margin-top:4px;'>";
        						if(label==attr){
        							line += "<td class='firstTd vertexname'>"+attr+"</td>";
        							line += "<td ><input class='attrInput' type='text' style='color:black;' value='"+aliasArr[attr]+"'  /></td>"
        						}else{
        							line += "<td class='firstTd'>"+attr+"</td>";
        							line += "<td ><input class='attrInput' type='text' style='color:black;' value='"+aliasArr[attr]+"'  /></td>"
        						}
        						line += "</tr>";
        						$("#tableid3").append(line);
        					}
        				}
        			}
        		});
    		}
    		
    		function getDefaultShow(){
    			$.ajax({
        			type:"post",
        			url:"titanStruManage/findDefaultShow.action",
        			success:function(data){
        				showArr = data;
        				var vertexs = datas[0];
        				var vertexsView = data;
        				var html = "";
        				for(var lable in vertexs){
    						html += "<tr>";
        					html += "<td style='width:18%;padding-left:40px;'>"+lable+"</td>";
        					var propertyArr = vertexs[lable];
        					
        					var viewArr = vertexsView[lable];
        					var first = viewArr[0];//第一个默认显示值
        					var	second = viewArr[1];//第二个默认值
        					if(propertyArr){
        						html += "<td class='tdWidth'><select class='abc' name='"+lable+"-0'>";
        						var tempHtml = "";
//         						var fileHtml = "";
    	    					for(var i=0;i<propertyArr.length;i++){
    	    						var property = propertyArr[i].name;
    	    						if(first && first == property){
    	    							html += "<option value='"+property+"' selected='selected'>"+property+"</option>";
    	    						}else{
    	    							html += "<option value='"+property+"'>"+property+"</option>";  
    	    						}
    	    						if(second && second == property){
    	    							tempHtml += "<option value='"+property+"' selected='selected'>"+property+"</option>";
    	    						}else{
    	    							tempHtml += "<option value='"+property+"'>"+property+"</option>";  
    	    						}
//     	    						fileHtml += ;
    	    					}
    	    					html += "</select></td>";
    	    					
    	    					html += "<td class='tdWidth'><select class='abc' name='"+lable+"-1'>";
    	    					html += tempHtml;
    	    					html += "</select></td>";
    	    					html += "<td style='height:60px;width:80px;'><img src='images/img/"+lable+".png'  style='height:50px;width:50px;'/></td>";
    	    					html += "<td class='tdWidth' style='margin-top:10px';><a href='javascript:void(0);' class='a-upload'><input type='file' id='"+lable+"' name='file' class='a-input' />点击这里上传文件</a><input type='text' disabled='disable' class='showFileName' value='' id='fileName'></td>";
        					}
        					html += "</tr>";
        				}
        				$("#tableid").append(html);
        			}
    			});
    		}
    		
    		//5月3日增加
    		$(".a-input").live("change",function(){
    			var fileVal = $(this).val();
           		$(this).parent().next().val(fileVal);
    		})
    	})
    	
    	function saveAlias(){
   			var attrInputArr = $(".attrInput");
   			var i=0;
   			for(var label in aliasAll){
   				var aliasArr = aliasAll[label];
   				for(var lab in aliasArr){
   					aliasArr[lab] = $(attrInputArr[i]).val();
   					i++;
   				}
   			}
   			$.ajax({
    			type:"post",
    			url:"titanStruManage/updateTitanAlias.action",
    			dataType:"json",
		        data:{"aliasAll" : JSON.stringify(aliasAll)},
    			success:function(data){
    				parent.hintManager.showInfoHint("修改属性别名成功！");
    				getTitanAlias();
    			}
    		}); 			
   		}
    	
    	function saveQueryable(){
    		var vertexs = datas[0];
    		var typeArr = [];
    		for(var label in vertexs){
    			typeArr.push(label);
    		}
    		
    		$(".checkboxtd").each(function(i,d){
    			var inputArr = $(this).find("input[type='checkbox']");
    			for(var k=0;k<vertexs[typeArr[i]].length;k++){
    				for(var j=0;j<inputArr.length;j++){
    					if(vertexs[typeArr[i]][k].name==$(inputArr[j]).attr("name")){
    						if($(inputArr[j]).attr("checked")=="checked"){
    							vertexs[typeArr[i]][k].queryable="1";
    						}else{
    							vertexs[typeArr[i]][k].queryable="0";
    						}
    					}
    				}
				}
    		});
    		datas[0] = vertexs;
    		$.ajax({
    			type:"post",
    			url:"titanStruManage/updateTitanVertexFull.action",
    			dataType:"json",
		        data:{"fullJson" : JSON.stringify(datas)},
    			success:function(data){
    				parent.hintManager.showInfoHint("保存设置成功。");
    				getTitanStr();
    			}
    		});
    	}
    	
    	function saveDefaltAttr(){
    		var fileIds  = [];
    		var fileIdsImg = [];
    		var flag = false;
    		$("input[type='file']").each(function(){
    			var id = $(this).attr("id");
				var value = $(this).val();
				if(value){
					var img = value.substring(value.lastIndexOf("\\")+1, value.length);
					
					/* if(id+".png" == img){
						fileIds.push(id);
					}else{
						parent.hintManager.showHint("图片名称必须为节点类型，图片格式为.png。请重新上传！");
						flag = true;
						return false;
					} */
					fileIds.push(id);
					fileIdsImg.push(id);
				}
				
	        });
    		if(flag) return;
    		confirm("是否要重新设置默认显示值？",function(){
	    		$("select").each(function(){
	    			var name = $(this).attr("name");
					var value = $(this).val();
					var nameArr = name.split("-");
					showArr[nameArr[0]][nameArr[1]] = value;
		        });
	    		$.ajaxFileUpload({
			         type:"post",
			         url:"titanStruManage/uploadImgs.action",
			         dataType:"json",
			         data:{"showArr" : JSON.stringify(showArr).replace(new RegExp("\"","g"),"'"),"fileIdsImg":fileIdsImg},
			         fileElementId : fileIds, 
			         success:function(data){
			        	 var result = data.result;
			        	 if(result){
						 	parent.hintManager.showInfoHint("保存设置成功。");
			        	 }else{
			        		parent.hintManager.showHint("保存设置失败，请联系管理员！");
			        	 }
			         },
			         error:function(){
			         	parent.hintManager.showHint("保存设置异常，请联系管理员！");
			         }
			     });
			});
    	}
       
    </script>
  </body>
</html>
