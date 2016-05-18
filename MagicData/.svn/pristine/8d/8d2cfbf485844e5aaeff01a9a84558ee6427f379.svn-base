<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
	<base href="<%=basePath %>" >
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>对象管理</title>
		<link href="styles/bootstrap/css/bootstrap.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="styles/systemManage/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="styles/systemManage/css/systemManage.css"/>			
		<!-- jqgrid -->
		<link href="styles/css/ui.jqgrid.css" rel="stylesheet" type="text/css" />
		<link href="styles/css/jquery-ui.min.css" rel="stylesheet" type="text/css" />
		<!-- jqgrid -->
		<script type="text/javascript" src="scripts/customJs.js"></script>
		<script type="text/javascript" src="scripts/jquery.min.js"></script>
		<script type="text/javascript" src="scripts/grid.locale-cn.js"></script>
    	<script type="text/javascript" src="scripts/jquery.jqGrid.min.js"></script>
		<script type="text/javascript" src="scripts/systemManage/systemManage.js" ></script>
		<script type="text/javascript" src="styles/bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="styles/bootstrap/js/bootstrap-prompts-alert.js"></script> 
	</head>
	<style type="text/css">
		/*iframe背景*/
		html,body{background: center,center;}
	</style>
	<body>
	<div class="userconBox">
		<div class="userCon clearfix">
			<div class="topbar clearfix">
				<div class="leftBar pull-left">
					<ul class="list-unstyled clearfix">
						<li><a href="javascript:void(0)" class="objectSpan">添加</a></li>
						<li class="delete"><a href="javascript:void(0)" id="scdx">删除</a></li>
					</ul>
				</div>
			</div>
			<!--表格-->
			<div class="table" style='width:100%;margin:0 auto'>
				<table id="dataList"></table>
				<div id="pager"></div>
			</div>
		</div>
	</div>
 <script type="text/javascript">
	 $("#scdx").click(
			 function(){
				 var Ids = $("#dataList").jqGrid('getGridParam','selarrrow');
				 if(Ids!= null && Ids !=''){
					 confirm('是否确定删除？',function(){
						 $.ajax( {
								type : "POST",
								url : "ObjectManage/delObjByid.action?ids="+Ids,
								success : function(){
									reTable();
								},
								error:function(){
									parent.hintManager.showHint("删除对象异常！");
						         }
							});
					 });
						}else{
							parent.hintManager.showHint("请选择删除对象！");
						}
			 }
			 )
		setInterval(function(){//每隔100毫秒检查一次。
			var flag = getCookie('ObjRefresh');
		    if(flag === 'yes'){
		    	reTable();
		        removeCookie('ObjRefresh');//删除cookie避免重复执行。
		    }
		}, 100);
		
		function getCookie(name)
		{
		    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
		 
		    if(arr=document.cookie.match(reg))
		 
		        return (arr[2]);
		    else
		        return null;
		}
		function removeCookie(name)
		{
		    var exp = new Date();
		    exp.setTime(exp.getTime() - 1);
		    var cval=getCookie(name);
		    if(cval!=null)
		        document.cookie= name + "="+cval+";expires="+exp.toGMTString();
		}
		function reTable(){
			$("#dataList").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
		}
		//加载表格
		var tableWidth = $(".userCon").width()-2;
		var H = $(".userCon").height()-108;
	 	   $("#dataList").jqGrid({
					url:"Objectajax/searchList.action",
			        datatype:"json", //数据来源，本地数据
			        mtype:"POST",//提交方式
			        height:H,//高度，表格高度。可为数值、百分比或'auto'
			        width:tableWidth,//这个宽度不能为百分比
			        autowidth:false,//自动宽
			        
			        multiselect:true,
			        colNames:['','对象名称', '对象描述', '对象属性','创建时间','操作'],
			        colModel:[
			            {name:'id',index:'id', width:'0%',align:'center',hidden:true},
			            {name:'name',index:'name', width:'10%',align:'center'},
			            {name:'details',index:'details', width:'20%', align:'center'},
			            {name:'property',index:'property', width:'45%', align:'center'},
			            {name:'createTime',index:'createTime', width:'20%', align:'center',formatter:'date',formatoptions:{srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i:s'}},
			            {name:'',index:'', width:'5%', align:'center',
			            	formatter:function(cellvalue, options, row){
	          		           return '<a href="javascript:void(0);" onclick="modifyObject(\''+row.id+'\')" title="编辑"><img src="images/img/bj.png" /></a>';
			            	} 
			            }
			        ],
			        rownumbers:true,//添加左侧行号
			        //altRows:true,//设置为交替行表格,默认为false
			        //sortname:'createDate',
			        //sortorder:'asc',
			        //loadonce:true,
			        viewrecords: true,//是否在浏览导航栏显示记录总数
			        rowNum:10,//每页显示记录数
			        rowList:[10,20,50],//用于改变显示行数的下拉列表框的元素数组。
			       
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
			function modifyObject(rowIds){
				if(rowIds!= null && rowIds != ''){
					var zjData = {
						title:"编辑对象",
						url:"ObjectManage/updateObjByid.action?oid="+rowIds
					};
					parent.parentPop(zjData);
					}else{
				        parent.hintManager.showHint("请选择一个编辑对象");
					}
			 }
		</script>
	</body>
</html>