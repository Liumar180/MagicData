<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>属性历史</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="styles/css/ui.jqgrid.css">
	<link rel="stylesheet" type="text/css" href="styles/css/jquery-ui.min.css">
	<style type="text/css">
		body{margin:0px;}
		.historyCon{
			background: #3a507e;
			border: 1px solid #20349a;
			border-radius: 0px 0px 6px 6px;
			padding: 10px 6px 0px 6px;
			overflow-y: auto;
			height: 95%;
		}
	</style>
  </head>
  
  <body>
  	<div class="historyCon">
	    <!--表格-->
		<div class="table" style='width:100%;margin:0 auto;'>
			<table id="dataList"></table>
			<div id="pager"></div>
		</div>
	</div>
	<script type="text/javascript" src="scripts/jquery.min.js"></script>
	<script type="text/javascript" src="scripts/grid.locale-cn.js"></script>
	<script type="text/javascript" src="scripts/jquery.jqGrid.min.js"></script>
	<script type="text/javascript">
	$(function(){
		$(parent.document.getElementById("pop_iframe")).parent().parent().parent().show();
		//加载表格  
 		$("#dataList").jqGrid({
			url:"ajaxHis/findHistoryList.action",
			datatype:"json",
			mtype:"POST",
			postData:{
				"propertyHistory.vertexId":"${propertyHistory.vertexId}",
				"propertyHistory.property":"${propertyHistory.property}"
			},
			height:"84%",
			autowidth:true,
			colNames:['属性值','修改时间','用户'],
			colModel:[
			    {name:'value',index:'value', width:'50%',align:'center'},
			    {name:'updateTime',index:'updateTime', width:'30%', align:'center',formatter:'date',formatoptions:{srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i:s'}},
			    {name:'userName',index:'userName', width:'10%', align:'center'}
			],
			rownumbers:true,
			viewrecords: true,
			rowNum:10,
			rowList:[10,20,50],
// 			multiselect: true,
		    jsonReader: {
		            root:"pageModel.list",    // 数据行（默认为：rows）
		            page: "pageModel.pageNo",     // 当前页
		            total: "pageModel.totalPage",    // 总页数
		            records: "pageModel.totalRecords",// 总记录数
		            repeatitems : false                // 设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
		    },
		    prmNames:{rows:"pageModel.pageSize",page:"pageModel.pageNo"},
			pager:'#pager',
			gridComplete:function(){
				setRowHeight("dataList");
			}
		});
	})
	
	/* 设置行高  */
	function setRowHeight(id){
		var grid = $("#"+id);  
       var ids = grid.getDataIDs();  
       for (var i = 0; i < ids.length; i++) {  
           grid.setRowData ( ids[i], false, {height: 30});  
       }
	}
	</script>
  </body>
</html>
