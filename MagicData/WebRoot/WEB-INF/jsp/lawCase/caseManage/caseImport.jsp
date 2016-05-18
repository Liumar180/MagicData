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
    <title>案件导入</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/common.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/my.css"/>
	
	<link rel="stylesheet" type="text/css" href="styles/css/ui.jqgrid.css">
	<link rel="stylesheet" type="text/css" href="styles/css/jquery-ui.min.css">
	<link rel="stylesheet" type="text/css" href="styles/css/customCss.css">
  </head>
  
  <body>
    <!--点击添加按钮出现的弹出层-->
	<div class="wjaddCon caseimgportCon">
		<div class="form-group commonDiv1">
       		<div class="titDiv">导入操作需先下载excel模板，如已下载可直接点击浏览选择excel文件导入</div>
   		</div>	
		<div class="form-group commonDiv" style="margin-top:20px;">
       		<label class="labelTit">导入模板</label>	
       		<div class="file-box case-div">			
				<a href="<%=basePath%>/images/caseExcelModel/caseModel.xls">下载</a>
			</div>
   		</div>														                    		                		                   		
   		<div class="form-group textfile commonDiv">
   			<label class="labelTit">excel文件</label>
			<div class="file-box">
				<input type='text' name='excelName' id='excelName' class='txt' />
				<input type='button' class='btnLl' value='浏览...' />
				<input type="file" name="excel" class="file" id="excel" size="28" onchange="filechange(this)" />
			</div>
   		</div>
   		<div style="clear: both;"></div>
   		<div class="btnDiv">
			<button class="btn btn-primary sureBtn" onclick="importC()">导入</button>
			<button class="btn btn-default qxBtn"  onclick="window.parent.dismissParentPop()">取消</button>
		</div>
		<div class="form-group commonDiv1">
			<div id="messageDiv" class="titDiv" style="margin-top:50px;display: none;"></div>
		</div>
		<div style="clear: both;"></div>
		<div id="myGridDiv" class="form-group commonDiv1" style="display: none;">
			失败记录如下：
			<div style='width:100%;margin:0 auto;'>
				<table id="dataList"></table>
				<div id="pager"></div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="<%=basePath%>scripts/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>scripts/ajaxfileupload.js"></script>
	<script type="text/javascript" src="<%=basePath%>scripts/grid.locale-cn.js"></script>
	<script type="text/javascript" src="<%=basePath%>scripts/jquery.jqGrid.min.js"></script>
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
	function loadGrid(data){
		$("#dataList").jqGrid({
// 			url:"",
			datatype:"local",
			data:data,
			height:'50%',//高度，表格高度。可为数值、百分比或'auto'
	 		autowidth:true,//自动宽
			colNames:['案件名称','创建时间','案件级别','案件状态','所属方向','案件组长', '督办人员','案件成员'],
			colModel:[
			    //{name:'id',index:'id', width:'10%', align:'center' },
			    {name:'caseName',index:'caseName', width:'10%',align:'center'},
			    {name:'dateStr',index:'dateStr', width:'14%', align:'center'},
			    {name:'caseLevelName',index:'caseLevelName', width:'6%', align:'center'},
			    {name:'caseStatusName',index:'caseStatusName', width:'6%', align:'center'},
			    {name:'directionName',index:'directionName', width:'8%', align:'center'},
			    {name:'caseLeader',index:'caseLeader', width:'6%', align:'center'},
			    {name:'caseSupervisor',index:'caseSupervisor', width:'6%', align:'center'},
			    {name:'caseUserNames',index:'caseUserNames', width:'8%', align:'center'}
			],
			rownumbers:true,//添加左侧行号
			jsonReader: {
	            root:"data.dataMap.failList",    // 数据行（默认为：rows）
	            repeatitems : false                // 设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
	    	},
	    	complete: function(data) {
	    	    var data = JSON.parse(data);
	    	    jQuery("#dataList")[0].addJSONData(data);
	    	}
		});
	}
	function filechange(a){
		var excelname = $("#excel").val();
		$("#excelName").val(excelname);
	}
	function importC(){
			var excelname = $("#excel").val();
			if(typeof(excelname)!='undefined'&&excelname!=null&&excelname!=''){
				$.ajaxFileUpload({
					type:"post",
                    url:"ajax_importLC/importLawObjs.action",//用于文件上传的服务器端请求地址
                    secureuri:false,//一般设置为false
                    fileElementId:'excel',//文件上传空间的id属性
                    async : false,
                    dataType : "JSON",
                    success : function(data, status){
                    	parent.window.frames["text"].searchList();//先让父页面刷新数据
                    	data = $.parseJSON(data);
                    	var success = data.dataMap.success;
                    	var message = data.dataMap.message;
                    	$("#messageDiv").css("display","block");
                    	$("#messageDiv").html(message);
                    	var failList = data.dataMap.failList;
                		var myjsongrid = eval(failList);
                    	if(success==true&&myjsongrid.length>0){
                    		$("#myGridDivTitle").css("display","block");
                    		$("#myGridDiv").css("display","block");
                        	var mygrid = jQuery("#dataList")[0]; 
    						loadGrid(myjsongrid);
                    	}
                   	    /*  $('#dataList').jqGrid().clearGridData();  
                   	    $('#dataList').jqGrid('setGridParam',{data:myjsongrid}).trigger('reloadGrid', [{page:1}]);    */ 
                    	//window.parent.dismissParentPop();//这是关闭当前页面的
                    },
                    error : function (data, status, e){
                    	console.log(data);
                        parent.hintManager.showHint("excel导入异常,请重试！");
                    }
				});
			}else{
				parent.hintManager.showHint("请选择要导入的文件！");
				return;
			} 
			
		}
	</script>
  </body>
</html>
