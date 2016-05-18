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
		<title>人员管理</title>
		<link href="<%=basePath%>styles/lawcase/css/bootstrap.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/ryControl.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/lawcase/css/my.css"/>
		<!-- jqgrid -->
		<link href="<%=basePath%>/styles/css/ui.jqgrid.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath%>/styles/css/jquery-ui.min.css" rel="stylesheet" type="text/css">
		<!-- jqgrid -->
		<script type="text/javascript" src="<%=basePath%>/scripts/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/scripts/grid.locale-cn.js"></script>
    	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.jqGrid.min.js"></script>
		<script src="<%=basePath%>scripts/laydate-v1.1/laydate/laydate.js"></script>
		<script type="text/javascript" src="<%=basePath%>scripts/lawCase/my.js" ></script>
		<script type="text/javascript" src="<%=basePath%>scripts/lawCase/common.js" ></script>
		<script type="text/javascript" src="<%=basePath%>scripts/lawCase/Popup.js" ></script>
		<script type="text/javascript" src="<%=basePath%>scripts/lawCase/inputSelect.js" ></script>
		<script type="text/javascript" charset="utf-8" src="<%=basePath%>scripts/ueditor-jsp/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>scripts/ueditor-jsp/ueditor.all.min.js"> </script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>scripts/ueditor-jsp/lang/zh-cn/zh-cn.js"></script>
	    <script type="text/javascript" src="<%=basePath%>scripts/lawCase/page.js" ></script>
	    <script type="text/javascript" src="styles/bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="styles/bootstrap/js/bootstrap-prompts-alert.js"></script> 
	    <style type="text/css">
		/*iframe的背景  */
		html,body{background:none;}
	</style>
	<script type="text/javascript">
	var cartlist = {}; 
	
	function commondel(type){
		delete cartlist[type];
		pagingData('1');
	}
		$(function(){
				 var tableWidth = $(".userCon").width()-14;
				 var H = $(".userCon").height()-80;
				  $("#peopleinfoTableList").jqGrid({
					// url:"<%=basePath%>ajaxPeopel/peopleMangeList.action",
			        datatype:"json", //数据来源，本地数据
			        mtype:"POST",//提交方式
			        height:H,//高度，表格高度。可为数值、百分比或'auto'
		 	        width:tableWidth,//这个宽度不能为百分比
				  //  autowidth:false,//自动宽
				    multiselect:true,
			        colNames:['','姓名','责任人', '人员状态', '重要等级', '所属方向', '控制状态', '所在地','操作'],//, '描述','照片', '拼音', '英文名','别名''出生日期',, '性别',  '国籍', '民族', '户籍地'
			        colModel:[
						{name:'id',index:'id', width:'0%',align:'center',hidden:true},
						{name:'pocnname',index:'pocnname', width:'10%',align:'center'},
			            {name:'podutyman',index:'podutyman', width:'10%', align:'center'},
			            {name:'popersonstatus',index:'popersonstatus', width:'10%', align:'center'},
			            {name:'poimportantlevel',index:'poimportantlevel', width:'10%', align:'center'},
			            {name:'podirectionof',index:'podirectionof', width:'15%', align:'center'},
			            {name:'pocontrolstatus',index:'pocontrolstatus', width:'10%', align:'center'},
			            {name:'polocation',index:'polocation', width:'15%', align:'center'},
			            {name:'',index:'', width:'10%',align:'center',
					    	formatter:function(cellvalue, options, row){
					    		 return '<a href="peoplemanage/peopleMangeDetail.action?id='+row.id+'" class="linkA"  title="详情"><img src="images/lawCase/xqBtn.png"></a>&nbsp;&nbsp;'+
					    		 		'<a href="javascript:void(0)" onclick="eidtperson(\''+row.id+'\')" class="linkA" title="编辑"><img src="images/img/bj.png" /></a>';
					    	} 
					    }
			        ],  
			        rownumbers:true,//添加左侧行号
			        viewrecords: true,//是否在浏览导航栏显示记录总数
			        //loadonce:true,
			        rowNum:10,//每页显示记录数
			        rowList:[10,20,50,500],//用于改变显示行数的下拉列表框的元素数组。
		            jsonReader: {
		                    root:"pageModel.list",    // 数据行（默认为：rows）
		                    page: "pageModel.pageNo",     // 当前页
		                    total: "pageModel.totalPage",    // 总页数
		                    records: "pageModel.totalRecords",// 总记录数
		                    repeatitems : false                // 设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
		            },
	            	prmNames:{rows:"pageModel.pageSize",page:"pageModel.pageNo"},
			        pager:'#peoplePager',
			        onPaging:function(pgButton){
			        		var page = $('#peopleinfoTableList').getGridParam('page'); // current page
				            var lastpage = $('#peopleinfoTableList').getGridParam('lastpage'); 
			        		var rowNum=$('#peopleinfoTableList').getGridParam('rowNum'); 
			        		if(lastpage>page &&page>1){
			        			if(pgButton=='next_peoplePager'){
					        		searchPList(page+1,rowNum);
					        	}else if(pgButton=='prev_peoplePager'){
					        		searchPList(page-1,rowNum);
					        	}else if(pgButton=='last_peoplePager'){
					        		searchPList(lastpage,rowNum);
					        	}else if(pgButton=='first_peoplePager'){
					        		searchPList(1,rowNum);
					        	}
			        		}else if(lastpage==page){
			        			 if(pgButton=='prev_peoplePager'){
					        		searchPList(page-1,rowNum);
					        	}else if(pgButton=='first_peoplePager'){
					        		searchPList(1,rowNum);
					        	}
			        		}else if(page==1){
			        			if(pgButton=='next_peoplePager'){
					        		searchPList(page+1,rowNum);
					        	}else if(pgButton=='last_peoplePager'){
					        		searchPList(lastpage,rowNum);
					        	}
			        		}
			        },
			        gridComplete:function(){
			        	setRowHeight("peopleinfoTableList");
			        }
			        
				  });
			     
				 /* 设置行高  */
					function setRowHeight(id){
						var grid = $("#"+id); 
						grid.closest(".ui-jqgrid-bdiv").css({ 'overflow-x' : 'hidden' });
				        var ids = grid.getDataIDs();  
				        for (var i = 0; i < ids.length; i++) {  
				            grid.setRowData ( ids[i], false, {height: 30});  
				        }
					} 
					
				});
		
		/* 卡片分页实现 */
		function pagingData(pageNo,value,type){
			var podirectionof;
			var year;
			var poimportantlevel;
			if(value!=""){
				cartlist[type] = value;
			}
			for (var key in cartlist) { 
	            if(key==1){
	            	podirectionof=cartlist[key].trim();
	            }else if(key==2){
	            	year=cartlist[key].trim();
	            }else if(key==3){
	            	poimportantlevel=cartlist[key].trim();
	            }
	        } 
			$.ajax({
		         type:"post",
		         url:"<%=basePath%>ajaxPeopel/peopleMangeList.action",
		         data:{
		        	 "pageModel.pageNo":pageNo,
		        	 "pageModel.pageSize":50,
		        	 podirectionof:podirectionof,
		        	 poimportantlevel:poimportantlevel,
		        	 year:year
		         },
		         async : false,
		         dataType:"json",
		         success:function(data){
//	 	        	 data = $.parseJSON(data);
		        	 //修改记录数
		        	 var records = data.pageModel.totalRecords;
		        	 $("#totalRecords").html(records);
		        	 $("#mo").html(data.pageModel.totalPage);
		        	 //修改卡片列表
		        	 var list = data.pageModel.list;
		        	 var len = list.length;
		        	 var html = "";
		     		 for(var i = 0;i < len; i++) {    
		     			var temp = list[i];
		     			html += '<div class="imgList"><a href="peoplemanage/peopleMangeDetail.action?id='+temp.id+'"><div class="img_box"><img src="'+temp.poimage+'"/></div><div class="hoverbox">';
		     			html += '<p>姓名：<span>'+temp.pocnname+'</span></p>';
		     			html += '<p>民族：<span>'+temp.ponational+'</span></p>';
		     			html += '<p>方向：<span>'+temp.podirectionof+'</span></p>';
		     			html += '<p>等级：<span>'+temp.poimportantlevel+'</span></p>';
	                	html += '</div><p class="imgTitle">'+temp.pocnname+'</p></a></div>';
		     	     }
		     		 $("#peopleCList").html(html);
		     		InfoHover();
		     		 // TODO hover!
		         },
		         error:function(){
		        	 hintManager.showHint("查询人员卡片列表异常，请联系管理员！");
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
		
		function eidtperson(rowIds){
				var url="peoplemanage/viewPeopleEdit.action?id="+rowIds;
				var ryData = {
						title:"编辑人员",
						url:url
					};
				parent.parentPop(ryData);
		}
		
		function delperson(){
				var	rowIds = $("#peopleinfoTableList").jqGrid('getGridParam','selarrrow');
				if(rowIds!= null&&rowIds!=''){
					confirm('删除将失去所有与其他对象的关联，确认删除？',function(){
						var url="peoplemanage/viewPeopleDel.action?ids="+rowIds+"&rootType=people";
						$.ajax( {
							type : "POST",
							url : url,
							success : function(){
								$("#peopleinfoTableList").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
							}
						});
					});
				}else{
					parent.hintManager.showHint("请选择列表中进行删除的人员");
				}
			}
		function searchPList(page,pageSize){
			var value=$('#content').val();
		if(page==undefined){
			page=1;
		}
		if(pageSize==undefined){
			pageSize=10;
		}
			var currentPostData ;
			if($("#contentname").text()=="姓名"){
				 currentPostData = {
						"pocnname":value,
						"podutyman":'',
						"popersonstatus":'',
						"poimportantlevel":'',
						"podirectionof":'',
						"pocontrolstatus":'',
						"polocation":'',
						"pageNo":page,
						"pageSize":pageSize
						};
			}else if($("#contentname").text()=="责任人"){
				currentPostData = {
						"podutyman":value,
						"pocnname":'',
						"popersonstatus":'',
						"poimportantlevel":'',
						"podirectionof":'',
						"pocontrolstatus":'',
						"polocation":'',
						"pageNo":page,
						"pageSize":pageSize
						};
			}else if($("#contentname").text()=="人员状况"){
				currentPostData = {
						"popersonstatus":value,
						"pocnname":'',
						"podutyman":'',
						"poimportantlevel":'',
						"podirectionof":'',
						"pocontrolstatus":'',
						"polocation":'',
						"pageNo":page,
						"pageSize":pageSize
						};
			}else if($("#contentname").text()=="重要等级"){
				currentPostData = {
						"pocnname":'',
						"podutyman":'',
						"popersonstatus":'',
						"podirectionof":'',
						"pocontrolstatus":'',
						"polocation":'',
						"poimportantlevel":value,
						"pageNo":page,
						"pageSize":pageSize
						};
			}else if($("#contentname").text()=="所属方向"){
				currentPostData = {
						"pocnname":'',
						"podutyman":'',
						"popersonstatus":'',
						"poimportantlevel":'',
						"pocontrolstatus":'',
						"polocation":'',
						"podirectionof":value,
						"pageNo":page,
						"pageSize":pageSize
						};
			}else if($("#contentname").text()=="控制状况"){
				currentPostData = {
						"pocnname":'',
						"podutyman":'',
						"popersonstatus":'',
						"poimportantlevel":'',
						"podirectionof":'',
						"polocation":'',
						"pocontrolstatus":value,
						"pageNo":page,
						"pageSize":pageSize
						};
			}else if($("#contentname").text()=="所在地"){
				currentPostData = {
						"pocnname":'',
						"podutyman":'',
						"popersonstatus":'',
						"poimportantlevel":'',
						"podirectionof":'',
						"pocontrolstatus":'',
						"polocation":value,
						"pageNo":page,
						"pageSize":pageSize
						};
			}
			$("#peopleinfoTableList").jqGrid('setGridParam',{
														postData:currentPostData,
														url:"<%=basePath%>peoplemanage/peopleSeList.action",
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
		function freshenTablist(type){
			if(type=="cardList"){
				//卡片列表刷新
				pagingData($("#xiye").html());
			}else{
				searchPList($('#peopleinfoTableList').getGridParam('page'),$('#peopleinfoTableList').getGridParam('rowNum'));
			}
		}
		function changeInput(type){
			var htmlc;
			if(type=="人员状况"){
				 htmlc='<select  class="select_txt" id="content" style="width:200px">'+
				<c:forEach items="${dataDictionary.personStatus}" var="map" >
					'<option value="${map.key}">${map.value}</option>'+
				</c:forEach>
				'</select>'+
				'<span class="searchSpan"  type="button"><img title="搜索" src="images/lawCase/search.png" width="46px" height="32px" onclick="searchPList()"></span>'+
				'<span class="searchSpan"  type="button" onclick="getListNoCondition();"><img title="清空条件" src="images/lawCase/clear.png" width="46px" height="32px"></span>';
			}else if(type=="所属方向"){
				 htmlc='<select  class="select_txt" id="content" style="width:200px;overflow: hidden;" >'+
					<c:forEach items="${dataDictionary.direction}" var="map" >
						'<option value="${map.key}">${map.value}</option>'+
					</c:forEach>
					'</select>'+
					'<span class="searchSpan"  type="button"><img title="搜索" src="images/lawCase/search.png" width="46px" height="32px" onclick="searchPList()"></span>'+
					'<span class="searchSpan"  type="button" onclick="getListNoCondition();"><img title="清空条件" src="images/lawCase/clear.png" width="46px" height="32px"></span>';
				}else if(type=="重要等级"){
				 htmlc='<select  class="select_txt" id="content" style="width:200px">'+
					<c:forEach items="${dataDictionary.level}" var="map" >
						'<option value="${map.key}">${map.value}</option>'+
					</c:forEach>
					'</select>'+
					'<span class="searchSpan"  type="button"><img title="搜索" src="images/lawCase/search.png" width="46px" height="32px" onclick="searchPList()"></span>'+
					'<span class="searchSpan"  type="button" onclick="getListNoCondition();"><img title="清空条件" src="images/lawCase/clear.png" width="46px" height="32px"></span>';
				}else if(type=="控制状况"){
						 htmlc='<select  class="select_txt" id="content" style="width:200px">'+
							<c:forEach items="${dataDictionary.controlStatus}" var="map" >
								'<option value="${map.key}">${map.value}</option>'+
							</c:forEach>
							'</select>'+
							'<span class="searchSpan"  type="button"><img title="搜索" src="images/lawCase/search.png" width="46px" height="32px" onclick="searchPList()"></span>'+
							'<span class="searchSpan"  type="button" onclick="getListNoCondition();"><img title="清空条件" src="images/lawCase/clear.png" width="46px" height="32px"></span>';
						}else{
							 htmlc='<input class="search_input"  type="text" id="content" name="content" placeholder="搜索内容"/>'+
         						'<span class="searchSpan"  type="button"><img title="搜索" src="images/lawCase/search.png" width="46px" height="32px" onclick="searchPList()"></span>'+
         						'<span class="searchSpan"  type="button" onclick="getListNoCondition();"><img title="清空条件" src="images/lawCase/clear.png" width="46px" height="32px"></span>';
							}
			$(".search_div").html(htmlc);
		}
		function exportP(){
			var	rowIds = $("#peopleinfoTableList").jqGrid('getGridParam','selarrrow');
			if(rowIds!= null&&rowIds!=''){
				confirm('确定要导出吗?',function(){
					var url="ajax_exportLC/exportLawObjs.action?lawCaseType=people&ids="+rowIds;
					parent.openExportDiv();
					$.ajax( {
						type : "POST",
						url : url,
						success : function(data){
							var subDir = data.currentTimeStr;
							var dlFileUrl = '<%=basePath%>fileManage/downloadFile.action?fileNameD=Peopels.zip&type=people&subDir='+subDir;
							parent.closeExportDiv(dlFileUrl);
						}
					});
				});
			}else{
				parent.hintManager.showHint("请选择列表中进行导出的人员");
			}
		}
		
		function getListNoCondition(){
			$(".select_txt").html("姓名");
			changeInput();
			searchPList();
		}
		
		function openwin(url) {
		 	window.open (url);
		}
		</script>
  </head>
  
  <body>
   <div class="userTab">
			<span id="Pcard" class="spanSeton" onclick="freshenTablist('cardList')">人员卡片</span>
			<span id="Plist" onclick="freshenTablist('list')">人员列表</span>
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
								<c:forEach items="${dataDictionary.direction}" var="mymap" >
									<span name="${mymap.key}" onclick="pagingData('1','${mymap.key}','1');">${mymap.value}</span>
								</c:forEach>
							</div>
						</div>
						<div class="selected-list clearfix" id="2">
							<label>按年份</label>
							<div class="fldiv">
								<c:forEach items="${years}" var="year" >
									<span name="${year}"  onclick="pagingData('1','${year}','2');">${year}</span>
								</c:forEach>
							</div>
						</div>
						<div class="selected-list clearfix" id="3">
							<label>按级别</label>
							<div class="fldiv">
								<c:forEach items="${dataDictionary.level}" var="level" >
									<span name="${level.key}"  onclick="pagingData('1','${level.key}','3');">${level.value}</span>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
				<div class="imglistBox">
					<div id="peopleCList">
						<c:forEach items="${pageModel.list}" var="plist"  varStatus="status">
								<div class="imgList">
									<a href="peoplemanage/peopleMangeDetail.action?id=<c:out value="${plist.id}"></c:out>">
										<div class="img_box">										
		                      				<img src="<c:out value="${plist.poimage}"></c:out>"/>
		                 				</div>
	                  				<!--鼠标OVER后出现的详细信息浮动层 -->
		                                <div class="hoverbox"> 
		                                	<p>姓名：<span><c:out value="${plist.pocnname}"></c:out></span></p>
		                                	<p>民族：<span><c:out value="${plist.ponational}"></c:out></span></p>
		                                	<p>方向：<span><c:out value="${plist.podirectionof}"></c:out></span></p>
		                                	<p>等级：<span><c:out value="${plist.poimportantlevel}"></c:out></span></p>  
		                                </div>
	                                	<p class="imgTitle">${plist.pocnname}</p>
	                                </a>
	                            </div>
	                     </c:forEach>
	                  </div>
			                     <div class="clear"></div>
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
							        </ul>
								</div>	
							
				</div>
			</div>
			<div class="userCon hideUsercon clearfix">
				<div class="topbar clearfix">
					<div class="leftBar pull-left">
						<ul class="list-unstyled clearfix">
							<li><a href="javascript:void(0)" class="addRy rySpan">添加</a></li>
							<li class="delete"><a href="javascript:delperson()">删除</a></li>
							<li class="dc"><a href="javascript:exportP()">导出</a></li>
						</ul>
					</div>
					<div class="rightBar pull-right">
						<div class="searchbar">
							<div class="select_box">
								<span class="select_txt" name="name" id="contentname">姓名</span>
								<a class="selet_open"></a>
						        <div class="option">
						       		<span class="" onclick="changeInput('姓名')">姓名</span>
						            <span class="" onclick="changeInput('责任人')">责任人</span>
						            <span class="" onclick="changeInput('人员状况')">人员状况</span>
						            <span class="" onclick="changeInput('重要等级')">重要等级</span>
						            <span class="" onclick="changeInput('所属方向')">所属方向</span>
						            <span class="" onclick="changeInput('控制状况')">控制状况</span>
						            <span class="" onclick="changeInput('所在地')">所在地</span>
						        </div>   
							</div>
							 <div class="search_div">
								<input class="search_input"  type='text' id="content" name="content" placeholder='搜索内容'/>
            					<span class="searchSpan"  type="button"><img title="搜索" src="images/lawCase/search.png" width="46px" height="32px" onclick="searchPList()"></span>
            					<span class="searchSpan"  type="button" onclick="getListNoCondition();"><img title="清空条件" src="images/lawCase/clear.png" width="46px" height="32px"></span> 
           					</div>
						</div>
					</div>
				</div>
				<!--表格-->
				<div class="table" style='width:100%;margin:0 auto'>
					<table id="peopleinfoTableList"></table>
					<div id="peoplePager"></div>
				</div>
			</div>
		</div>
  </body>
</html>
