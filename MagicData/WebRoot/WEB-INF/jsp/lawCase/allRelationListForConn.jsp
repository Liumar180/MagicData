<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ contextPath + "/";
	pageContext.setAttribute("base", contextPath);
%>
<link href="<%=basePath%>styles/css/userList.css" rel="stylesheet"
	type="text/css">
<link href="<%=basePath%>styles/css/customCss.css" rel="stylesheet"
	type="text/css">
<link href="<%=basePath%>styles/css/jquery-ui.min.css" rel="stylesheet"
	type="text/css">
<link href="<%=basePath%>styles/css/ui.jqgrid.css" rel="stylesheet"
	type="text/css">
<link href="<%=basePath%>styles/css/commTcc.css" rel="stylesheet"
	type="text/css">
<style type="text/css">
</style>
<title>关系页面关联页</title>
</head>
<style type="text/css">
.ui-state-highlight {
	border: 1px solid #18439C;
	background: #1D1C1D none repeat scroll
}
.btndiv input{
	background:#012548;
	border: 1px solid #009cff;
    border-radius: 4px;
    line-height: 24px;
    cursor: pointer;
    color:#fff;
    padding:0px 12px;
}
</style>
<body>
	<div style="width:100%;" align="center">
		<!-- 上半部分 -->
		<div class="ui-jqgrid-view" style="width:98%">
			<div class="ui-state-default ui-jqgrid-hdiv" style="width:100%">
				<div class="ui-jqgrid-hbox" style="width:100%">
					<table class="ui-jqgrid-btable" style="width:100%">
						<thead>
							<tr class="ui-jqgrid-labels">
								<th class="ui-state-default ui-th-column ui-th-ltr"
									colspan="${fn:length(tdLabel)}" width="100%" align="left">
									<span class="ui-jqgrid-resize ui-jqgrid-resize-ltr"
									style="cursor: col-resize;"></span>
									<div style="margin-left: 5px" align="left">对象列表</div>
								</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
		<div class="ui-jqgrid ui-widget ui-widget-content ui-corner-all" style="width:98%;height: 15%">
		<div class="ui-jqgrid-view" style="width:100%;">
			<div class="ui-state-default ui-jqgrid-hdiv" style="width:100%">
				<div class="ui-jqgrid-hbox" style="width:100%">
					<table class="ui-jqgrid-btable" style="width:100%">
						<thead>
							<tr class="ui-jqgrid-labels">
								<c:forEach var="labelObj" items="${tdLabel}">
									<th class="ui-state-default ui-th-column ui-th-ltr">${labelObj}</th>
								</c:forEach>
							</tr>
						</thead>
					</table>
				</div>
			</div>
			<div class="ui-jqgrid-bdiv"
				style="width:100%;margin-top: 0px;">
				<div style="position:relative;">
					<table class="ui-jqgrid-btable" style="width:100%" cellpadding="0"
						cellspacing="0" border="0">
						<tbody>
							<tr style="height: 0" class="rootTdfirstRow">
								<c:forEach var="InfoObj" items="${tdInfo}">
									<td></td>
								</c:forEach>
							</tr>
							<tr class="ui-widget-content ui-state-highlight jqgrow ui-row-ltr"
								onclick="getSubInfo(${typeId},'${type}')"
								onmouseover="$(this).removeClass('ui-state-highlight');$(this).addClass('ui-state-hover')"
								onmouseout="$(this).removeClass('ui-state-hover');$(this).addClass('ui-state-highlight')">
								<!-- ui-row-ltr ui-state-hover -->
								<c:forEach var="InfoObj" items="${tdInfo}">
									<td style="text-align:center;" ><c:out value="${connObj[InfoObj]}" /></td>
								</c:forEach>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		</div>
		<!-- 下半部分 -->
		<div class="ui-jqgrid-view" style="width:98%">
			<div class="ui-state-default ui-jqgrid-hdiv" style="width:100%">
				<div class="ui-jqgrid-hbox" style="width:100%">
					<table class="ui-jqgrid-btable" style="width:100%">
						<thead>
							<tr class="ui-jqgrid-labels">
								<th class="ui-state-default ui-th-column ui-th-ltr"
									colspan="${fn:length(tdLabel)}" width="100%" align="left">
									<span class="ui-jqgrid-resize ui-jqgrid-resize-ltr"
									style="cursor: col-resize;"></span>
									<div style="margin-left: 5px" align="left">相关对象</div>
								</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
		<div style="width: 98%">
			<table id="subInfoList"></table>
		</div>
		<div style="width: 98%" align="right">
			<div style="margin-right: 1%;margin-top: 5px" class="btndiv">
			   <input type="button" value="确定" onclick="saveRelation(${typeId},'${type}')"/> 
			   <input type="button" value="取消"  onclick="parent.dismissParentPop()"/>
			</div>
		</div>
	</div>
	<div class='hintMsg'></div>
	<script type="text/javascript" src="<%=basePath%>scripts/jquery.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>scripts/ui/jquery-ui.min.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/scripts/grid.locale-cn.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/scripts/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/scripts/customJs.js"></script>
	<!-- 自定义脚本 -->
	<script type="text/javascript">
	var map = new Map();
	$(document).ready(function() {
	   var fieldNum = ${fn:length(tdLabel)};
	   var tdWidth = 100/fieldNum;
	   $(".rootTdfirstRow td").css("width",tdWidth+"%");  	
	   $("#subInfoList").jqGrid({
		        datatype:"local",//"json"
		        //data:data.subResultList, 
		        // mtype:"POST",//提交方式
		        height:'60%',//高度，表格高度。可为数值、百分比或'auto'
		        autowidth:true,//自动宽
		        colNames:${subJqColName},/* data.ColNs*/
		        colModel:${subJqColModel},//[{name:'0', width:'50%',align:'center'},{name:'1', width:'50%',align:'center'}],//data.ColMs,
		        rownumbers: false,
		        sortable:false,
		        scroll:25,
		        viewrecords: false,//是否在浏览导航栏显示记录总数
			    emptyrecords:"未找到符号条件的记录",
			    loadtext: "正在加载数据......",
			    multiselect: true, //---select 传值	
			    jsonReader:{
			           root: "rows", 
			    },
	            /* localReader: {// jsonReader: {
	                    root:"data.subResultList",    // 数据行（默认为：rows）
                        repeatitems: false,
                        cell: "cell"
	            }, */
			    onSelectRow : function( id , status,row) {
			        var objId = $("#subInfoList").jqGrid('getRowData', id).id;
					if (status) {
						map.put(objId , status);
					} else {
						map.remove(objId);    
					}	
				},
				/** 全部对象的选中状态 */
				onSelectAll : function(aRowids,status ){
				    if(aRowids.length!=0){
						if (status) {
							for(var i=0;i<aRowids.length;i++){
							    var objId = $("#subInfoList").jqGrid('getRowData', aRowids[i]).id;
								map.put(objId,true);
							}
						} else {
							map.removeAll(map.arr);
						}
					}
				}
		    });	 
		    getSubInfo(${typeId},'${type}');
	});

   function getSubInfo(typeId,type){
       $.ajax({
				         type:"post",
				         url:"<%=basePath%>ajax_lawCaseRelation/getSubRelation.action",
				         data:{
				        	 typeId:typeId,
				        	 type:type
			  	         },
				         dataType:"json",
				         async: false,
				         success:function(data){
				        	 //var result = data.subResultList;
				        	 creategrid(data); 	 
				        	// hintManager.showSuccessHint("任务成功执行");
				         },
				         error:function(){
				        	 hintManager.showHint("获得关联关系错误，请联系管理员！");
				         }
				     });
    }
	
	function creategrid(data) {
          //subInfoList.addJSONData(data.subResultList); */
          //$("#subInfoList").setGridParam(data,data.subResultList);
          //$("#subInfoList").addRowData(data.subResultList);
          $("#subInfoList").clearGridData();
          var rows = data.subResultList;
          for(var i=0;i<rows.length;i++){
		        $("#subInfoList").jqGrid('addRowData',i+1,rows[i]);
	       }
    }
    
    //获得选中的行对象
   function getSelRows(){
		var ids = "";
		for(var i=0;i<map.arr.length;i++){
			var obj = map.arr[i];
			if(i+1==map.arr.length){
				ids += obj.key;
			}else{
				ids += obj.key+",";
			}
		}
		return ids;
    }
    function saveRelation(typeId,type){
       var selIds = getSelRows();
       //alert(sellIds);
        $.ajax({
			 type:"post",
			 url:"<%=basePath%>ajax_lawCaseRelation/addAllRelation.action",
		     data:{
		         rootId:'${rootId}',
		         rootType:'${rootType}',
				 typeId:typeId,
				 type:type,
				 relationIds:selIds
			  },
			  dataType:"json",
			  async: false,
			  success:function(data){
			     /* try{parent.flashDetailPage();}catch(e){} */
			     window.parent.text.location.reload();
			     parent.dismissParentPop();
			  },
			 error:function(){ hintManager.showHint("添加关联关系错误，请联系管理员！");}
		});
    }
    function setCookie(c_name, value, expiredays){
    	var exdate=new Date();
    	exdate.setDate(exdate.getDate() + expiredays);
    	document.cookie=c_name+ "=" + escape(value) + ((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
    	}
	</script>
</body>
</html>
