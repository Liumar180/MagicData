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
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>案件管理</title>
		<link rel="stylesheet" type="text/css" href="styles/lawcase/css/bootstrap.css" >
		<link rel="stylesheet" type="text/css" href="styles/lawcase/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="styles/lawcase/css/ajControl.css"/>
		<link rel="stylesheet" type="text/css" href="styles/lawcase/css/my.css"/>
		<!-- jqgrid -->
		<link rel="stylesheet" type="text/css" href="styles/css/ui.jqgrid.css">
		<link rel="stylesheet" type="text/css" href="styles/css/jquery-ui.min.css">
		<link rel="stylesheet" type="text/css" href="styles/css/customCss.css">
		<style type="text/css">
			/*iframe的背景  */
			html,body{background:none;}
			.hideSelect{display:none;}
			.heddenoverflow{overflow:hidden;}
		</style>
	</head>
	<body>
		<div class="userTab">
			<span class="spanSeton" onclick="flushListPage('card')">案件卡片</span>
			<span onclick="flushListPage('grid')">案件列表</span>
		</div>
		<div class="userconBox">
			<div class="userCon clearfix">
				<div class="commodity-screening">
					<div class="commodity-cont">
						<div class="selected-conditions">
							<label class="selected-label">检索条件</label>
							<div class="apend"></div>
							<p class="tiShi">共有<em id="totalRecords">${pageModel.totalRecords}</em>个符合条件的对象</p>
						</div>
						<div class="selected-list clearfix" id="1">
							<label>按方向</label>
							<div class="fldiv">
								<c:forEach items="${dataDictionary.direction}" var="mymap" ><span onclick="pagingData('1','${mymap.key}','0');" value="${mymap.key}">${mymap.value}</span></c:forEach>
							</div>
						</div>
						<div class="selected-list clearfix" id="2">
							<label>按年份</label>
							<div class="fldiv">
								<c:forEach items="${years}" var="year" >
									<span onclick="pagingData('1','${year}','1');" value="${year}">${year}</span>
								</c:forEach>
							</div>
						</div>
						<div class="selected-list clearfix" id="3">
							<label>按级别</label>
							<div class="fldiv">
								<c:forEach items="${dataDictionary.level}" var="level" >
									<span onclick="pagingData('1','${level.key}','2');" value="${level.key}">${level.value}</span>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
				<div class="imglistBox clearfix">
					<div id="cardList">
					<c:forEach items="${pageModel.list}" var="obj" varStatus="status">
		            	<div class="imgList">
							<a href="case/viewCaseDetail.action?caseObject.id=${obj.id}">
								<div class="img_box">										
	                  				<img src="images/lawCase/moren/case.jpg"/>
	             				</div>
	          					<!--鼠标OVER后出现的详细信息浮动层 -->
	                            <div class="hoverbox"> 
	                            	<p>名称：<span>${obj.caseName}</span></p>
	                            	<p>方向：<span>${obj.directionName}</span></p>
	                            	<p>等级：<span>${obj.caseLevelName}</span></p>
	                            </div>
	                        	<p class="imgTitle">${obj.caseName}</p>
	                        </a>
	                    </div>    
		            </c:forEach>
		            </div>
					<div class="clear"></div>	
					<!--分页-->
					<div class="fenye clearfix">
				    	<ul class="pageUl list-unstyled clearfix">
				        	<li id="first">首页</li>
				            <li id="top" onclick="topclick()">上一页</li>
				            <li class="xifenye" id="xifenye">
				            	<a id="xiye">${pageModel.pageNo}</a>/<a id="mo">${pageModel.totalPage}</a>
				                <div class="xab" id="xab" style="display:none">
				                	<ul class="list-unstyled" id="uljia">
				                    	
				                    </ul>
				                </div>
				            </li>
				            <li id="down" onclick="downclick()">下一页</a></li>
				            <li id="last">末页</li>
							<!-- 卡片列表pageSize			            
							<li class="lastLi"> 
				            	<select class="fenyeSelect">
				            		<option>7</option>
				            		<option>14</option>
				            		<option>21</option>
				            		<option>28</option>
				            		<option>35</option>
				            		<option>42</option>
				            	</select>
				            </li>
				            -->
				        </ul>
					</div>
				</div>
			</div>
			<div class="userCon hideUsercon clearfix heddenoverflow">
				<div class="topbar clearfix">
					<div class="leftBar pull-left">
						<ul class="list-unstyled clearfix">
							<li><a href="javascript:void(0)" class="addAj ajSpan">添加</a></li>
<!-- 							<li class="bj"><a href="javascript:void(0)" class="bjAj">编辑</a></li> -->
							<li class="delete"><a href="javascript:void(0)" onclick="deleteCase()">删除</a></li>
							<li class="dr"><a href="javascript:void(0)" class="importCasesSpan">导入</a></li>
							<li class="dc">
								<a href="javascript:void(0)">导出&nbsp;&nbsp;<img alt="" src="images/lawCase/exportIcon.png"></a>
								<!-- <div class="searchbar hidesearchbar">
									<div class="select_box_export">
										<span class="select_txt1" id="exportSelect" name="exportWord">导出word</span>
										<a class="selet_open"></a>
								        <div class="option1">
								            <span class="exportWord">导出word</span>
								            <span class="exportExcel">导出excel</span>
								        </div>   
									</div>
								</div> -->
								<div class="hideselectDiv">
									<span class="exportWord" onclick='exportFile("exportWord")'>导出word</span>
								    <span class="exportExcel" onclick='exportFile("exportExcel")'>导出excel</span>
								</div>
							</li>
						</ul>
					</div>
					<input type="hidden"  name="imgTimeNum"  id="imgTimeNum" />
					<input type="file" id="excel" name="excel" style="display: none;" />
					<!-- <input type='text' name='excelFileName' id="excelFileName" /> -->
					
					<div class="rightBar pull-right">
						<div class="searchbar">
							<div class="select_box">
								<span class="select_txt" id="select_casecond" name="caseName">案件名称</span>
								<a class="selet_open"></a>
						        <div class="option">
						            <span class="caseName">案件名称</span>
						            <span class="caseLevel">案件级别</span>
						            <span class="caseStatus">案件状态</span>
						            <span class="directionCode">所属方向</span>
						            <span class="caseLeader">案件组长</span>
						        </div>   
							</div>
							 <div class="search_div">
            					<input class="search_input caseNameClass currentSelect"  type='text' id="content" name="content" placeholder='搜索内容'/>
            					<select class="commonSelect hideSelect caseLevelClass" name="content">
									<c:forEach items="${dataDictionary.level}" var="level" >
								   		<option value="${level.key}">${level.value}</option>  
									</c:forEach>
								</select>
            					<select class="commonSelect hideSelect caseStatusClass" name="content">
								    <c:forEach items="${dataDictionary.caseStatus}" var="caseStatus" >
										<option value="${caseStatus.key}">${caseStatus.value}</option>
									</c:forEach>
								</select>
								<select class="commonSelect hideSelect directionCodeClass" name="content">
								    <c:forEach items="${dataDictionary.direction}" var="direction" >
										<option value="${direction.key}">${direction.value}</option>
									</c:forEach>
								</select>
            					<select class="commonSelect hideSelect caseLeaderClass" name="content">
									<c:forEach items="${userList}" var="user" >
										<option value="${user.userName}">${user.userName}</option>
									</c:forEach>
								</select>
            					<span class="searchSpan"  type="button" onclick="searchList();"><img title="搜索" src="images/lawCase/search.png" width="46px" height="32px"></span>
            					<span class="searchSpan"  type="button" onclick="getListNoCondition();"><img title="清空条件" src="images/lawCase/clear.png" width="46px" height="32px"></span>
           					</div>
						</div>
					</div>
				</div>
				<!--表格-->
				<div class="table" style='width:100%;height:85%;margin:0 auto;'>
					<table id="dataList"></table>
					<div id="pager"></div>
				</div>
			</div>
		</div>
<!-- jqgrid -->
<script type="text/javascript" src="scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/scripts/ajaxfileupload.js"></script>
<script type="text/javascript" src="scripts/grid.locale-cn.js"></script>
<script type="text/javascript" src="scripts/jquery.jqGrid.min.js"></script>
  	
<script type="text/javascript" src="scripts/lawCase/my.js" ></script>
<script type="text/javascript" src="scripts/lawCase/common.js" ></script>
<script type="text/javascript" src="scripts/lawCase/Popup.js" ></script>
<script type="text/javascript" src="scripts/lawCase/inputSelect.js" ></script>
<script type="text/javascript" src="scripts/lawCase/page.js" ></script>
<script type="text/javascript" src="scripts/customJs.js"></script>
<script type="text/javascript" src="styles/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="styles/bootstrap/js/bootstrap-prompts-alert.js"></script>
		
<script type="text/javascript">
	var tempIds = [];
	$(function(){
		var tableWidth = $(".userCon").width()-12;
		var H = $(".userCon").height()-80;
		//加载表格
 		$("#dataList").jqGrid({
			url:"ajaxCase/getCaseList.action",
			datatype:"json", //数据来源，本地数据
			mtype:"POST",//提交方式
			height:H,//高度，表格高度。可为数值、百分比或'auto'
			width:tableWidth,//这个宽度不能为百分比
	// 		autowidth:true,//自动宽
			colNames:['案件名称','创建时间','案件级别','案件状态','所属方向','案件组长', '督办人员','案件成员','操作'],
			colModel:[
			    //{name:'id',index:'id', width:'10%', align:'center' },
			    {name:'caseName',index:'caseName', width:'10%',align:'center'},
			    {name:'dateStr',index:'dateStr', width:'14%', align:'center'},
			    {name:'caseLevelName',index:'caseLevelName', width:'6%', align:'center'},
			    {name:'caseStatusName',index:'caseStatusName', width:'6%', align:'center'},
			    {name:'directionName',index:'directionName', width:'8%', align:'center'},
			    {name:'caseLeader',index:'caseLeader', width:'6%', align:'center'},
			    {name:'caseSupervisor',index:'caseSupervisor', width:'6%', align:'center'},
			    {name:'caseUserNames',index:'caseUserNames', width:'8%', align:'center'},
			    {name:'caseName',index:'caseName', width:'5%',align:'center',
			    	formatter:function(cellvalue, options, row){
			    		 return '<a href="case/viewCaseDetail.action?caseObject.id='+row.id+'" class="linkA" title="详情"><img src="images/lawCase/xqBtn.png"></a>&nbsp;&nbsp;'+
			    		 		'<a href="javascript:void(0)" onclick="updateCaseById('+row.id+')" class="linkA" title="编辑"><img src="images/img/bj.png" /></a>';
			    	} 
			    }
			],
			rownumbers:true,//添加左侧行号
			//altRows:true,//设置为交替行表格,默认为false
			//sortname:'createDate',
			//sortorder:'asc',
			viewrecords: true,//是否在浏览导航栏显示记录总数
			//loadonce:true,
			rowNum:10,//每页显示记录数
			rowList:[10,20,50,500],//用于改变显示行数的下拉列表框的元素数组。
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
	   grid.closest(".ui-jqgrid-bdiv").css({ 'overflow-x' : 'hidden' });
       var ids = grid.getDataIDs();  
       for (var i = 0; i < ids.length; i++) {  
           grid.setRowData ( ids[i], false, {height: 30});  
       }
	}
	
	
	
	//列表条件联动
	var case_selectName = $("#select_casecond").attr("name");
	var case_selectConent;
	function conditionLinkage(classAttr){
		var classname =  classAttr+"Class";
		$(".currentSelect").addClass("hideSelect").removeClass("currentSelect");
		$("."+classname).addClass("currentSelect").removeClass("hideSelect");
		case_selectName = classAttr;
	}
	
	function getListNoCondition(){
		$("#select_casecond").attr("name", "caseName");
		$("#select_casecond").html("案件名称");
		conditionLinkage("caseName");
		searchList();
	}
	
	//条件查询列表
	function searchList(){
		tempIds = [];
		case_selectConent = $(".currentSelect").val();
		var currentCondition = {"condition":case_selectName,"conditionValue":case_selectConent};
		$("#dataList").jqGrid('setGridParam',{postData:currentCondition}).trigger("reloadGrid");
	}
	
	//刷新表格
    function flushGrid(){
    	searchList();
//     	$("#dataList").jqGrid().trigger("reloadGrid");
    }
	
	/* 卡片分页实现 */
	var conditionArr = new Array("","","");
	var currentPageNo_card = 1;
	function pagingData(pageNo,key,index){
		currentPageNo_card = pageNo;
		if(index){
			conditionArr[index] = key;
		}
		$.ajax({
	         type:"post",
	         url:"ajaxCase/getCaseList.action",
	         data:{
	        	 "pageModel.pageNo":pageNo,
	        	 "pageModel.pageSize":50,
	        	 "caseObject.directionCode":conditionArr[0],
	        	 "caseObject.by1":conditionArr[1],
	        	 "caseObject.caseLevel":conditionArr[2]
	         },
	         async : false,
	         dataType:"json",
	         success:function(data){
// 	        	 data = $.parseJSON(data);
	        	 //修改记录数
	        	 var records = data.pageModel.totalRecords;
	        	 $("#totalRecords").html(records);
	        	 //修改卡片列表
	        	 var list = data.pageModel.list;
	        	 var len = list.length;
	        	 var html = "";
	     		 for(var i = 0;i < len; i++) {
	     			var temp = list[i];
	     			html += '<div class="imgList"><a href="case/viewCaseDetail.action?caseObject.id='+temp.id+'"><div class="img_box"><img src="images/lawCase/moren/case.jpg"/></div><div class="hoverbox">';
	     			html += '<p>名称：<span>'+temp.caseName+'</span></p>';
	     			html += '<p>方向：<span>'+(temp.directionName?temp.directionName:"")+'</span></p>';
	     			html += '<p>等级：<span>'+(temp.caseLevelName?temp.caseLevelName:"")+'</span></p>';
                	html += '</div><p class="imgTitle">'+temp.caseName+'</p></a></div>';
	     	     }
	     		 $("#cardList").html(html);
	     		//图片的浮层
	     		InfoHover();
	         },
	         error:function(){
	        	 parent.hintManager.showHint("查询案件卡片列表异常，请联系管理员！");
	         }
	     });
	}
	
	//点击标签页刷新
	function flushListPage(flag){
		if("card" == flag){
			pagingData(currentPageNo_card,null,null);
		}else if("grid" == flag){
			searchList();
		}
	}
	
	//删除条件
	function commondel(id){
		if(id){
			var index = id-1
			conditionArr[index] = "";
			pagingData(1);
		}
	} 
	
	/*编辑案件弹出层*/
	function updateCaseById(id){
		var ajData = {
		  	title:"编辑案件",
		  	url:"case/viewCaseUpdate.action?caseObject.id="+id
	  	};
	  	parent.parentPop(ajData);
	}
	 
	 /*删除案件*/
	 function deleteCase(){
		if(tempIds.length == 0){
			parent.hintManager.showHint("请至少选择一个案件！");
		}else{
			confirm("删除将失去所有与其他对象的关联，确认删除？？",function(){
				$.ajax({
			         type:"post",
			         url:"ajaxCase/deleteCases.action",
			         data:{
			        	 "ids":tempIds.toString()
			         },
			         async : false,
			         dataType:"json",
			         success:function(data){
		 	        	flushGrid();
			         },
			         error:function(){
			        	 parent.hintManager.showHint("删除案件异常，请联系管理员！");
			         }
			     });
			});
		}
	 }
	 function exportFile(name){
		 $(".hideselectDiv").css("display","none");
		 if(name=="exportWord"){
			 var	rowIds = $("#dataList").jqGrid('getGridParam','selarrrow');
				if(rowIds!= null&&rowIds!=''){
					confirm('确定要导出Word吗?',function(){
						var url="ajax_exportLC/exportLawObjs.action?lawCaseType=case&ids="+rowIds;
						parent.openExportDiv();
						$.ajax( {
							type : "POST",
							url : url,
							success : function(data){
								var subDir = data.currentTimeStr;
							    var dlFileUrl = '<%=basePath%>fileManage/downloadFile.action?fileNameD=Cases.zip&type=case&subDir='+subDir;
							    parent.closeExportDiv(dlFileUrl);
							}
						});
					});
				}else{
					parent.hintManager.showHint("请选择列表中进行导出的数据");
				}
		 }else if(name=="exportExcel"){
			 var	rowIds = $("#dataList").jqGrid('getGridParam','selarrrow');
				if(rowIds!= null&&rowIds!=''){
					confirm('确定要导出Excel吗?',function(){
						var url="ajax_exportLCExcel/exportLawObjs.action?ids="+rowIds;
						parent.openExportDiv();
						$.ajax( {
							type : "POST",
							url : url,
							success : function(data){
								var currentTimeStr = data.currentTimeStr;
								var dlFileUrl = '<%=basePath%>ajax_exportLCExcel/downloadLCExel.action?currentTimeStr='+currentTimeStr;
							    parent.closeExportDiv(dlFileUrl); 
							}
						});
					});
				}else{
					parent.hintManager.showHint("请从列表中选择要导出的数据");
				}
		 }
	 }
	 function exportC(){
			var	rowIds = $("#dataList").jqGrid('getGridParam','selarrrow');
			if(rowIds!= null&&rowIds!=''){
				confirm('确定要导出Word吗?',function(){
					var url="ajax_exportLC/exportLawObjs.action?lawCaseType=case&ids="+rowIds;
					parent.openExportDiv();
					$.ajax( {
						type : "POST",
						url : url,
						success : function(data){
							var subDir = data.currentTimeStr;
						    var dlFileUrl = '<%=basePath%>fileManage/downloadFile.action?fileNameD=Cases.zip&type=case&subDir='+subDir;
						    parent.closeExportDiv(dlFileUrl);
						}
					});
				});
			}else{
				parent.hintManager.showHint("请选择列表中进行导出的数据");
			}
		}
		function exportToExcel(){
			var	rowIds = $("#dataList").jqGrid('getGridParam','selarrrow');
			if(rowIds!= null&&rowIds!=''){
				confirm('确定要导出Excel吗?',function(){
					var url="ajax_exportLCExcel/exportLawObjs.action?ids="+rowIds;
					parent.openExportDiv();
					$.ajax( {
						type : "POST",
						url : url,
						success : function(data){
							var currentTimeStr = data.currentTimeStr;
							var dlFileUrl = '<%=basePath%>ajax_exportLCExcel/downloadLCExel.action?currentTimeStr='+currentTimeStr;
						    parent.closeExportDiv(dlFileUrl); 
						}
					});
				});
			}else{
				parent.hintManager.showHint("请从列表中选择要导出的数据");
			}
			
		}
		
</script>
</body>
</html>
