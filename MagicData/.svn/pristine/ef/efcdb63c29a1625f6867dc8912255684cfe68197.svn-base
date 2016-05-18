<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>用户管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="styles/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="styles/systemManage/css/common.css"/>
	<link rel="stylesheet" type="text/css" href="styles/systemManage/css/systemManage.css"/>
	<link rel="stylesheet" type="text/css" href="styles/css/ui.jqgrid.css">
	<link rel="stylesheet" type="text/css" href="styles/css/jquery-ui.min.css">			
	<style type="text/css">
		/*iframe背景*/
		html,body{background: center,center;}
	</style>
  </head>
  
  <body>
    <div class="userconBox">
		<div class="userCon clearfix">
			<div class="topbar clearfix">
				<div class="leftBar pull-left">
					<ul class="list-unstyled clearfix">
						<li><a href="javascript:void(0)" class="userSpan">添加</a></li>
						<li class="delete"><a href="javascript:void(0)" onclick="deleteUser()">删除</a></li>
					</ul>
				</div>
				<div class="rightBar pull-right">
					<div class="searchbar">
						 <div class="search_div">
           					<input class="search_input"  type='text' id="content" name="content" placeholder='用户名称搜索'/>
           					<span class="searchSpan"  type="button" onclick="refreshList();"><img src="images/systemManage/searchBtn.png" width="16px" height="16px"></span>
          				</div>
					</div>
				</div>
			</div>
			<!--表格-->
			<div class="table" style='width:100%;margin:0 auto;'>
				<table id="dataList"></table>
				<div id="pager"></div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="scripts/jquery.min.js" ></script>
	<script type="text/javascript" src="scripts/grid.locale-cn.js"></script>
	<script type="text/javascript" src="scripts/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="scripts/systemManage/systemManage.js" ></script>
	<script type="text/javascript" src="scripts/customJs.js"></script>
	<script type="text/javascript" src="styles/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="styles/bootstrap/js/bootstrap-prompts-alert.js"></script>
	<script type="text/javascript">
		var tempIds = [];
		$(function(){
			//加载表格
			var H = $(".userCon").height()-108;
	 		$("#dataList").jqGrid({
				url:"ajaxauth/findUserList.action",
				datatype:"json", //数据来源，本地数据
				mtype:"POST",//提交方式
				height:H,//高度，表格高度。可为数值、百分比或'auto'
				autowidth:true,//自动宽
				colNames:['用户名称','姓名','电话','创建时间','描述','操作'],
				colModel:[
				    {name:'userName',index:'userName', width:'10%',align:'center'},
				    {name:'name',index:'name', width:'10%',align:'center'},
				    {name:'phone',index:'phone', width:'10%',align:'center'},
				    {name:'createTime',index:'createTime', width:'15%', align:'center',formatter:'date'},
				    {name:'description',index:'description', width:'20%', align:'center'},
				    {name:'caseName',index:'caseName', width:'10%',align:'center',
				    	formatter:function(cellvalue, options, row){
				    		 return '<a href="javascript:void(0)" onclick="detailUserById('+row.id+')" class="linkA" title="详情"><img src="images/lawCase/xqBtn.png"></a>&nbsp;&nbsp;'+
				    		 		'<a href="javascript:void(0)" onclick="updateUserById('+row.id+')" class="linkA" title="编辑"><img src="images/img/bj.png" /></a>&nbsp;&nbsp;'+
				    		 		(row.id==1?'':(row.status==0?'<a href="javascript:void(0)" onclick="enabledUserById('+row.id+','+row.status+')" class="linkA" title="禁用"><img src="images/lawCase/disabled.png" /></a>':'<a href="javascript:void(0)" onclick="enabledUserById('+row.id+','+row.status+')" class="linkA" title="启用"><img src="images/lawCase/enabled.png" /></a>'));
				    	} 
				    }
				],
				rownumbers:true,//添加左侧行号
				viewrecords: true,//是否在浏览导航栏显示记录总数
				//loadonce:true,
				rowNum:10,//每页显示记录数
				rowList:[10,20,50],//用于改变显示行数的下拉列表框的元素数组。
				multiselect: true,
				onSelectRow : function(id,status) {//选中单行
					if(status){
						tempIds.push(id);
					}else{
						tempIds.remove(id);
					}
				},
				onSelectAll : function(aRowids,status){//全部对象的选中状态
					if (status) {
						tempIds = aRowids;
					}else {
						tempIds = [];
					}
				},
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
		
		/* 刷新表格 */
        function refreshList(){
	    	tempIds = [];
			var userName = $("#content").val().trim();
			var currentCondition = {"user.userName":userName};
			$("#dataList").jqGrid('setGridParam',{postData:currentCondition}).trigger("reloadGrid");
		}
		
		
		/*添加用户弹出层*/
	    $(".userSpan").click(function() {
            var ajData = {
                title: "添加用户",
                url: "auth/viewAddUserPage.action",
                title1: ""
            };
            parent.parentPop(ajData);
        })
        
        /*用户编辑弹出层*/
        function updateUserById(id){
	    	var ajData = {
                title: "编辑用户",
                url: "auth/viewUpdateUserPage.action?user.id="+id,
                title1: ""
            };
            parent.parentPop(ajData);
		}
		
		/* 启用、禁用用户 */
		function enabledUserById(id,status){
			var msg;
			if(status == 0){
				msg = "是否要禁用用户？";
			}else{
				msg = "是否要启用用户？";
			}
			confirm(msg,function(){
				$.ajax({
			         type:"post",
			         url:"ajaxauth/enabledUserById.action",
			         data:{
			        	 "user.id":id,
			        	 "user.status":status
			         },
			         async : false,
			         dataType:"json",
			         success:function(data){
			        	 refreshList();
			         },
			         error:function(){
			        	 parent.hintManager.showHint("删除用户异常，请联系管理员！");
			         }
			     });
			});
		}
		
	    /*用户详细弹出层*/
        function detailUserById(id){
	    	var ajData = {
                title: "用户详细信息",
                url: "auth/viewDetailUserPage.action?user.id="+id,
                title1: ""
            };
            parent.parentPop(ajData);
		}
        
	    /*删除用户*/
		 function deleteUser(){
			if(tempIds.length == 0){
				parent.hintManager.showHint("请至少选择一个用户！");
			}else{
				for (var i = 0; i < tempIds.length; i++) {  
					var tempid = tempIds[i];
					if(tempid == 1){
						parent.hintManager.showHint("系统管理员用户不能删除，请重新选择！");
						return;
					}
			    }
				confirm("是否确认删除？",function(){
					$.ajax({
				         type:"post",
				         url:"ajaxauth/deleteUsers.action",
				         data:{
				        	 "ids":tempIds.toString()
				         },
				         async : false,
				         dataType:"json",
				         success:function(data){
				        	 refreshList();
				         },
				         error:function(){
				        	 parent.hintManager.showHint("删除用户异常，请联系管理员！");
				         }
				     });
				});
			}
		 }
        
	</script>
  </body>
</html>
