<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>日志管理</title>
		<link href="styles/bootstrap/css/bootstrap.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="styles/systemManage/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="styles/systemManage/css/systemManage.css"/>	
		<!-- jqgrid -->
		<link href="styles/css/ui.jqgrid.css" rel="stylesheet" type="text/css" />
		<link href="styles/css/jquery-ui.min.css" rel="stylesheet" type="text/css" />		
		<script type="text/javascript" src="scripts/jquery.min.js"></script>	
		<script type="text/javascript" src="scripts/systemManage/systemManage.js" ></script>
		<script type="text/javascript" src="scripts/grid.locale-cn.js"></script>
    	<script type="text/javascript" src="scripts/jquery.jqGrid.min.js"></script>
    	<script src="scripts/laydate-v1.1/laydate/laydate.js"></script>
	</head>
	<style type="text/css">
		/*iframe背景*/
		html,body{background: center,center;}
	</style>
	<body>
		<div class="userconBox">
			<div class="userCon clearfix">
				<div class="topbar clearfix">
					<div class="form-group">
						<label class="labelTit">用户名称：</label>
						<input type="text" class="form-control" placeholder="请输入用户名称" id="userName"/>
					</div>
					<div class="form-group">
						<label class="labelTit">开始时间：</label>
						<input type="text" class="form-control" placeholder="请输入时间" id="creatTime"/>
					</div>
					<div class="form-group">
						<label class="labelTit">结束时间：</label>
						<input type="text" class="form-control" placeholder="请输入时间" id="endTime" />
					</div>	
					<div class="form-group">
		        		<label class="labelTit">操作类型：</label>				
						<select class="form-control select" id="operationType">
							<option></option>
						    <option value="add">添加</option>
						    <option value="search">查询</option>
						    <option value="edit">修改</option>
						    <option value="del">删除</option>
						</select>
		    		</div>
		    		<div class="searchDiv pull-left" onclick="search(1,10)">搜索</div>
				</div>
				<!--表格-->
				<div class="table" style='width:100%;margin:0 auto'>
					<table id="dataList"></table>
					<div id="pager"></div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
		var htmlc="<option></option>";
			<c:forEach items="${dataDictionary.operationType}" var="map" >
			htmlc+='<option value="${map.key}">${map.value}</option>'
			</c:forEach>
			$('#operationType').html(htmlc);
			
		function search(page,rowNum){
			var currentPostData = {
					"creatTime":$("#creatTime").val(),
					"userName":$("#userName").val(),
					"operationType":$("#operationType").val(),
					"endTime":$("#endTime").val(),
					"pageNo":page,
					"pageSize":rowNum
					};
			$("#dataList").jqGrid('setGridParam',{
				postData:currentPostData,
				 url:"logManage/getLogList.action",
	            prmNames:{rows:"pageSize",page:"page"},
	            jsonReader: {
                    root:"list",    // 数据行（默认为：rows）
                    page: "pageNo",     // 当前页
                    total: "totalPage",    // 总页数
                    records: "totalRecords",// 总记录数
                    repeatitems: false                // 设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
            }
			}).trigger("reloadGrid");
		}
		var starttime={
				   elem: '#creatTime',
				   istime: false,
				   istoday: true,
				   festival: true,
				   choose: function(datas){
				    	endtime.min = datas; //开始日选好后，重置结束日的最小日期
				    	endtime.start = datas; //将结束日的初始值设定为开始日
				    }
				};
		var endtime={
				   elem: '#endTime',
				   istime: false,
				   istoday: true,
				   festival: true,
				   choose: function(datas){
				    	starttime.max = datas; //结束日选好后，重置开始日的最大日期
				   }
				};
		laydate(starttime); 
		laydate(endtime); 
		//加载表格
		var tableWidth = $(".userCon").width()-2;
		var H = $(".userCon").height()-100;
	 	   $("#dataList").jqGrid({
					url:"logManage/getLogList.action",
			        datatype:"json", //数据来源，本地数据
			        mtype:"POST",//提交方式
			        height:H,//高度，表格高度。可为数值、百分比或'auto'
			        width:tableWidth,//这个宽度不能为百分比
			        autowidth:false,//自动宽
			        multiselect:false,
			        colNames:['','用户', '操作类型','操作', '操作时间'],
			        colModel:[
			            {name:'id',index:'id', width:'0%',align:'center',hidden:true},
			            {name:'userName',index:'userName', width:'10%',align:'center'},
			            {name:'operationType',index:'operationType', width:'10%', align:'center'},
			            {name:'operation',index:'operation', width:'50%', align:'center'},
			            {name:'creatTime',index:'creatTime', width:'20%', align:'center'}
			        ],
			        rownumbers:true,//添加左侧行号
			        rownumWidth:70,//设置行号列宽度
			        viewrecords: true,//是否在浏览导航栏显示记录总数
			        //loadonce:true,
			        rowNum:10,//每页显示记录数
			        rowList:[10,20,50,500],//用于改变显示行数的下拉列表框的元素数组。
			        jsonReader: {
	                    root:"list",    // 数据行（默认为：rows）
	                    page: "pageNo",     // 当前页
	                    total: "totalPage",    // 总页数
	                    records: "totalRecords",// 总记录数
	                    repeatitems : false                // 设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
	            },
	            prmNames:{rows:"pageSize",page:"pageNo"},
		        pager:'#pager',
		        onPaging:function(pgButton){
		        	if($('#content').val()!=''){
		        		var page = $('#dataList').getGridParam('page'); // current page
		        		var rowNum= $('#dataList').getGridParam('rowNum'); 
			            var lastpage = $('#dataList').getGridParam('lastpage'); 
		        		if(lastpage>page &&page>1){
		        			if(pgButton=='next_pager'){
		        				search(page+1,rowNum);
				        	}else if(pgButton=='prev_pager'){
				        		search(page-1,rowNum);
				        	}else if(pgButton=='last_pager'){
				        		search(lastpage,rowNum);
				        	}else if(pgButton=='first_pager'){
				        		search(1,rowNum);
				        	}
		        		}else if(lastpage==page){
		        			 if(pgButton=='prev_pager'){
		        				 search(page-1,rowNum);
				        	}else if(pgButton=='first_pager'){
				        		search(1,rowNum);
				        	}
		        		}else if(page==1){
		        			if(pgButton=='next_pager'){
		        				search(page+1,rowNum);
				        	}else if(pgButton=='last_pager'){
				        		search(lastpage,rowNum);
				        	}
		        		}
		        	}
		        },
			        gridComplete:function(){
			        	$("#pager_center").removeAttr("style");
			        	$("#pager_center").addClass('centerCss'); 
			        	setRowHeight("dataList");
			        }
			    });
		
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
