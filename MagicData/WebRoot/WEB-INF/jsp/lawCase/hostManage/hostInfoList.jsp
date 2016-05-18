<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>主机管理</title>
		<link rel="stylesheet" type="text/css" href="styles/lawcase/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="styles/lawcase/css/common.css" />
		<link rel="stylesheet" type="text/css" href="styles/lawcase/css/zjControl.css" />
		<link rel="stylesheet" type="text/css" href="styles/lawcase/css/my.css" />
		<!-- jqgrid -->
		<link href="styles/css/ui.jqgrid.css" rel="stylesheet" type="text/css" />
		<link href="styles/css/jquery-ui.min.css" rel="stylesheet" type="text/css" />
		<!-- jqgrid -->
		<script type="text/javascript" src="scripts/jquery.min.js"></script>
		<script type="text/javascript" src="scripts/grid.locale-cn.js"></script>
    	<script type="text/javascript" src="scripts/jquery.jqGrid.min.js"></script>
		<script type="text/javascript" src="scripts/lawCase/my.js" ></script>
		<script type="text/javascript" src="scripts/lawCase/common.js" ></script>
		<script type="text/javascript" src="scripts/lawCase/Popup.js" ></script>
		<script type="text/javascript" src="scripts/lawCase/inputSelect.js" ></script>
		<script type="text/javascript" src="scripts/lawCase/page.js" ></script>
		<script type="text/javascript" src="styles/bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="styles/bootstrap/js/bootstrap-prompts-alert.js"></script> 
	    <style type="text/css">
		/*iframe的背景  */
		html,body{background:none;}
	</style>
	</head>
	<body>
		<div class="userTab">
		<input type="hidden" name="pageNo" id="pn" value="1" />
		<span class="spanSeton" id="zjkp" >主机卡片</span>
		<span onclick="reList()">主机列表</span>
		</div>
			<div class="userconBox">
				<div class="userCon clearfix">
					<div class="commodity-screening">
						<div class="commodity-cont">
							<div class="selected-conditions">
								<label class="selected-label">检索条件</label>
								<div class="apend" ></div>
								<p class="tiShi">共有<em id="totalRecords">${pageModel.totalRecords}</em>个符合条件的对象</p>
				</div>
				<div class="selected-list clearfix" id="1">
					<label>按方向</label>
					<div class="fldiv">
					<c:forEach items="${dataDictionary.direction}" var="mymap">
					<input type="hidden" name="directions" value="${mymap.key}" />
					<span value="${mymap.value}" onclick="pagingData('1','${mymap.value}','${mymap.key}')" >${mymap.value}</span>
					</c:forEach>
						</div>
					</div>
					<div class="selected-list clearfix" id="2">
						<label>按级别</label>
						<div class="fldiv">
						<c:forEach items="${dataDictionary.level}" var="fx" >
						<input type="hidden" name="importantLevel"  value="${fx.key}" />
						    <span value="${fx.value}" onclick="pagingData('1','${fx.value}','${fx.key}')">${fx.value}</span>
						</c:forEach>
						</div>
					</div>
				</div>
			</div>
			<div class="imglistBox">
			<div id="cardList">
			 <%-- <c:forEach items="${pageModel.list}" var="list" >
               <div class="imgList">
			      <a href="hostManage/searchHostDetails.action?hid=${list.id}">
				     <div class="img_box">										
               				<img src="images/lawCase/moren/host.jpg"/>
             				</div>
             				<!--鼠标OVER后出现的详细信息浮动层 -->
                            <div class="hoverbox"> 
                            	<p>主机名：<span><c:out value="${list.hostName}"></c:out></span></p>
                            	<p>主机IP：<span><c:out value="${list.hostIp}"></c:out></span></p>
                            	<p>方向：<span><c:out value="${list.directions}"></c:out></span></p>
                            	<p>等级：<span><c:out value="${list.importantLevel}"></c:out></span></p>  
                            </div>
                           	<p class="imgTitle">${list.hostName}</p>
                           </a>
                       </div>
              </c:forEach> --%>
              </div>
              <div class="clear"></div>
				<div class="fenye clearfix">
		    	<ul class="pageUl list-unstyled clearfix">
		        	<li id="first">首页</li>
		            <li id="top" onclick="topclick()">上一页</li>
		            <li class="xifenye" id="xifenye">
		            	<a id="xiye" >${pageModel.pageNo}</a>/<a id="mo">${pageModel.totalPage }</a>
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
				<div class="userCon hideUsercon clearfix">
					<div class="topbar clearfix">
						<div class="leftBar pull-left">
							<ul class="list-unstyled clearfix">
								<li><a href="javascript:void(0)"  id="zjSpan">添加</a></li>
								<!-- <li class="bj"><a href="javascript:void(0)" class="bjZj">编辑</a></li> -->
								<li class="delete"><a href="javascript:void(0)" id="sczj" >删除</a></li>
								<li class="dc"><a href="javascript:exportH()">导出</a></li>
							</ul>
						</div>
						<div class="rightBar pull-right">
							<div class="searchbar">
								<div class="select_box">
									<span class="select_txt" name="hostName" id="parameter">主机名称</span>
									<a class="selet_open"></a>
							        <div class="option">
							            <span class="hoName" onclick="changeInput('主机名称')">主机名称</span>
							            <span  class="ip" onclick="changeInput('主机IP')">主机IP</span>
							            <span class="level" onclick="changeInput('重要等级')" >重要等级</span>
							            <span class="type" onclick="changeInput('主机类型')">主机类型</span>
							            <span class="zjzt" onclick="changeInput('主机状态')">主机状态</span>
							            <span class="kzzt" onclick="changeInput('控制状态')">控制状态</span>
							            <span class="ssfx" onclick="changeInput('所属方向')">所属方向</span>
							            <span class="zrr" onclick="changeInput('责任人')">责任人</span>
							            <span class="tgs" onclick="changeInput('提供商')">提供商</span>
							        </div>   
								</div>
								 <div class="search_div">
	            					<input class="search_input"  type='text' id="content" name="content" placeholder='搜索内容'/>
	            					<span class="searchSpan"  type="button"><img title="搜索" src="images/lawCase/search.png" onclick="searchList()"  width="46px" height="32px"></span>
	            					<span class="searchSpan"  type="button" onclick="getListNoCondition();"><img title="清空条件" src="images/lawCase/clear.png" width="46px" height="32px"></span>
	           					</div>
							</div>
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
	$("#zjSpan").click(function(){
		var zjData = {
			title:"添加主机",
			url:"hostManage/addHost.action?point=add"
		};
		parent.parentPop(zjData);
	 })
	$(pagingData(1,"sx","sx"));
	setInterval(function(){//每隔100毫秒检查一次。
		var flag = getCookie('need_refresh');
	    if(flag === 'yes'){
	    	reTable();
	        removeCookie('need_refresh');//删除cookie避免重复执行。
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
	$('#zjkp').click(function(){
		var pn = $('#pn').val();
		pagingData(pn,"sx","sx");
	})
    var cartlist = {}; 
	function commondel(key){
		if(key == '1'){
			delete cartlist['direction'];
		}else if(key == '2'){
			delete cartlist['level'];
		}
		pagingData(1,"del","del");
	}
	/* 卡片分页实现 */
	function pagingData(pageNo,value,key){
		$('#pn').val(pageNo);
		var str = "";
		if(value != 'del' && value !='sx'){
			if(key != undefined){
		key = key.substring(0,key.length-3);
			}
		
		cartlist[key] = value;
		}
		for (var keys in cartlist) {
			str +=","+cartlist[keys]+"-"+keys;
        } 
		 $.ajax({
	         type:"post",
	         url:"hostManage/searchHostsList.action?datas="+encodeURIComponent(encodeURIComponent(str)),
	         data:{
	        	 "pageModel.pageNo":pageNo,
	        	 "pageModel.pageSize":50
	         },
	         async : false,
	         dataType:"json",
	         success:function(data){
	        	//修改记录数
	        	 var records = data.pageModel.totalRecords;
	        	 var totalPage = data.pageModel.totalPage;
	        	 var pageNo = data.pageModel.pageNo;
	        	 $('#totalRecords').html(records);
	        	 $('#xiye').html(pageNo);
	        	 $('#mo').html(totalPage);
	        	 //修改卡片列表
	        	 var list = data.pageModel.list;
	        	 var len = list.length;
	        	 var html = "";
	     		 for(var i = 0;i < len; i++) {
	     			var temp = list[i];
	     			html += '<div class="imgList">';
	     			html += '<a href="hostManage/searchHostDetails.action?hid='+temp.id+'">';
	     			html += '<div class="img_box">	';									
	     			html += '<img src="images/lawCase/moren/host.jpg"/>';
	     			html += '</div>';
      				html += '<div class="hoverbox"> ';
      				html += '<p>主机名：<span>'+temp.hostName+'</span></p>';
      				html += '<p>主机IP：<span>'+temp.hostIp+'</span></p>';
      				html += '<p>方向：<span>'+temp.directions+'</span></p>';
      				html += '<p>等级：<span>'+temp.importantLevel+'</span></p> ' ;
      				html += '</div>';
      				html += '<p class="imgTitle">'+temp.hostName+'</p>';
      				html += '</a>';
      				html += '</div>'; 
	     	     }
	     		 $("#cardList").html(html);
	     		 // TODO hover!
	         },
	         error:function(){
	        	 hintManager.showHint("查询案件卡片列表异常，请联系管理员！");
	         }
	     });
		 InfoHover();
	}
	function orderDetial(id){
		location.href="hostManage/searchHostDetails.action?hid="+id;
	}
	function modifyHost(rowIds){
		if(rowIds!= null && rowIds != ''){
			var zjData = {
				title:"编辑主机",
				url:"hostManage/updateHostsByid.action?hid="+rowIds+"&&point=true"
			};
			parent.parentPop(zjData);
			}else{
		        parent.hintManager.showHint("请选择一个编辑对象");
			}
	 }
	 
	 $("#sczj").click(
			 function(){
				 var Ids = $("#dataList").jqGrid('getGridParam','selarrrow');
				 if(Ids!= null && Ids !=''){
					 confirm('删除将失去所有与其他对象的关联，确认删除？',function(){
						 $.ajax( {
								type : "POST",
								url : "hostManage/delHostsByid.action?ids="+Ids+"&&rootType=host",
								success : function(){
									reTable();
								},
								error:function(){
						        	 parent.hintManager.showHint("删除主机异常，请联系管理员！");
						         }
							});
					 });
						}else{
					       parent.hintManager.showHint("请选择删除对象！");
						}
			 }
			 )
    function reTable(){
		$("#dataList").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
	}
	 function reList(){
		 searchList($('#dataList').getGridParam('page'),$('#dataList').getGridParam('rowNum'));
	 }
	 function searchList(pageNos,pageSize){
		var selete =  $('#parameter').text();//查询类型
		var content = $('#content').val();//查询参数
		if(pageNos==undefined){
			pageNos=1;
		}
		if(pageSize==undefined){
			pageSize = 50;
			
		}
		if(selete =="主机名称"){
			currentPostData = {"hostName":content,"hostIp":"","importLevel":"",
					"hostType":"","hostStatus":"","conStatus":"","directions":"","person":"","provider":"",
					"pageModel.pageNo":pageNos,"pageModel.pageSize":pageSize};
		}else if(selete =="主机IP"){
			currentPostData = {"hostName":"","hostIp":content,"importLevel":"",
					"hostType":"","hostStatus":"","conStatus":"","directions":"","person":"","provider":"",
					"pageModel.pageNo":pageNos,"pageModel.pageSize":pageSize};	
		}else if(selete =="重要等级"){
			currentPostData = {"hostName":"","hostIp":"","importLevel":content,
					"hostType":"","hostStatus":"","conStatus":"","directions":"","person":"","provider":"",
					"pageModel.pageNo":pageNos,"pageModel.pageSize":pageSize};
		}else if(selete =="主机类型"){
			currentPostData = {"hostName":"","hostIp":"","importLevel":"",
					"hostType":content,"hostStatus":"","conStatus":"","directions":"","person":"","provider":"",
					"pageModel.pageNo":pageNos,"pageModel.pageSize":pageSize};
		}else if(selete =="主机状态"){
			currentPostData = {"hostName":"","hostIp":"","importLevel":"",
					"hostType":"","hostStatus":content,"conStatus":"","directions":"","person":"","provider":"",
					"pageModel.pageNo":pageNos,"pageModel.pageSize":pageSize};
		}else if(selete =="控制状态"){
			currentPostData = {"hostName":"","hostIp":"","importLevel":"",
					"hostType":"","hostStatus":"","conStatus":content,"directions":"","person":"","provider":"",
					"pageModel.pageNo":pageNos,"pageModel.pageSize":pageSize};
		}else if(selete =="所属方向"){
			currentPostData = {"hostName":"","hostIp":"","importLevel":"",
					"hostType":"","hostStatus":"","conStatus":"","directions":content,"person":"","provider":"",
					"pageModel.pageNo":pageNos,"pageModel.pageSize":pageSize};
		}else if(selete =="责任人"){
			currentPostData = {"hostName":"","hostIp":"","importLevel":"",
					"hostType":"","hostStatus":"","conStatus":"","directions":"","person":content,"provider":"",
					"pageModel.pageNo":pageNos,"pageModel.pageSize":pageSize};
		}else if(selete =="提供商"){
			currentPostData = {"hostName":"","hostIp":"","importLevel":"",
					"hostType":"","hostStatus":"","conStatus":"","directions":"","person":"","provider":content,
					"pageModel.pageNo":pageNos,"pageModel.pageSize":pageSize};
		}
		$("#dataList").jqGrid('setGridParam',{
			postData:currentPostData,
			url:"hostajax/searchHostsList.action",
            jsonReader: {
            	root:"pageModel.list",    // 数据行（默认为：rows）
                page: "pageModel.pageNo",     // 当前页
                total: "pageModel.totalPage",    // 总页数
                records: "pageModel.totalRecords",// 总记录数
                repeatitems : false                // 设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
        },
        prmNames:{rows:"pageModel.pageSize",page:"pageModel.pageNo"}
	}).trigger("reloadGrid");
	}
	  
	//加载表格
		var tableWidth = $(".userCon").width()-13;
		var H = $(".userCon").height()-80;
 	   $("#dataList").jqGrid({
				url:"hostajax/searchHostsList.action",
		        datatype:"json", //数据来源，本地数据
		        mtype:"POST",//提交方式
		        height:H,//高度，表格高度。可为数值、百分比或'auto'
		        width:tableWidth,//这个宽度不能为百分比
		        autowidth:false,//自动宽
		        multiselect:true,
		        colNames:['','主机名称', '主机ip', '主机类型', '提供商','重要等级','所属方向','操作系统','所在地','创建时间','操作'],
		        colModel:[
		            {name:'id',index:'id', width:'0%',align:'center',hidden:true},
		            {name:'hostName',index:'hostName', width:'11%',align:'center'},
		            {name:'hostIp',index:'hostIp', width:'12%', align:'center'},
		            {name:'hostType',index:'hostType', width:'10%', align:'center'},
		            {name:'provider',index:'provider', width:'8%', align:'center'},
		            {name:'importantLevel',index:'importantLevel', width:'8%', align:'center'},
		            {name:'directions',index:'directions', width:'8%', align:'center'},
		            {name:'operateSystem',index:'operateSystem', width:'8%', align:'center'},
		            {name:'location',index:'location', width:'6%', align:'center'},
		            {name:'createTime',index:'createTime', width:'20%', align:'center',formatter:'date',formatoptions:{srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i:s'}},
		            {name:'',index:'', width:'8%', align:'center',
		            	formatter:function(cellvalue, options, row){
          		           return '<a href="javascript:void(0);" onclick="orderDetial(\''+row.id+'\')" title="详情"><img src="images/lawCase/xqBtn.png" /></a>&nbsp'+
          		           '<a href="javascript:void(0);" onclick="modifyHost(\''+row.id+'\')" title="编辑"><img src="images/img/bj.png" /></a>';
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
		        rowList:[10,20,50,500],//用于改变显示行数的下拉列表框的元素数组。
		        jsonReader: {
                    root:"pageModel.list",    // 数据行（默认为：rows）
                    page: "pageModel.pageNo",     // 当前页
                    total: "pageModel.totalPage",    // 总页数
                    records: "pageModel.totalRecords",// 总记录数
                    repeatitems : false                // 设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
            },
            prmNames:{rows:"pageModel.pageSize",page:"pageModel.pageNo"},
	        pager:'#pager',
	        onPaging:function(pgButton){
	        		var page = $('#dataList').getGridParam('page'); // current page
		            var lastpage = $('#dataList').getGridParam('lastpage');
		            var rows = $('#dataList').getGridParam('rowNum');
		            if(lastpage>page &&page>1){
	        			if(pgButton=='next_pager'){
			        		searchList(page+1,rows);
			        	}else if(pgButton=='prev_pager'){
			        		searchList(page-1,rows);
			        	}else if(pgButton=='last_pager'){
			        		searchList(lastpage,rows);
			        	}else if(pgButton=='first_pager'){
			        		searchList(1,rows);
			        	}
	        		}else if(lastpage==page){
	        			 if(pgButton=='prev_pager'){
			        		searchList(page-1,rows);
			        	}else if(pgButton=='first_pager'){
			        		searchList(1,rows);
			        	}
	        		}else if(page==1){
	        			if(pgButton=='next_pager'){
			        		searchList(page+1,rows);
			        	}else if(pgButton=='last_pager'){
			        		searchList(lastpage,rows);
			        	}
	        		}
	        	
	        }, 
		        gridComplete:function(){
		        	setRowHeight("dataList");
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
		function changeInput(type){
			var htmlc;
			if(type=="重要等级"){
				 htmlc='<select  class="select_txt" id="content" style="width:220px">'+
					<c:forEach items="${dataDictionary.level}" var="map" >
						'<option value="${map.key}">${map.value}</option>'+
					</c:forEach>
					'</select>'+
					'<span class="searchSpan"  type="button"><img title="搜索" src="images/lawCase/search.png"  width="46px" height="32px" onclick="searchList()"></span>'+
					'<span class="searchSpan"  type="button" onclick="getListNoCondition();"><img title="清空条件" src="images/lawCase/clear.png" width="46px" height="32px"></span>';
				}else if(type=="主机类型"){
					 htmlc='<select  class="select_txt" id="content" style="width:220px">'+
						<c:forEach items="${dataDictionary.hostType}" var="map" >
							'<option value="${map.key}">${map.value}</option>'+
						</c:forEach>
						'</select>'+
						'<span class="searchSpan"  type="button"><img title="搜索" src="images/lawCase/search.png"  width="46px" height="32px" onclick="searchList()"></span>'+
						'<span class="searchSpan"  type="button" onclick="getListNoCondition();"><img title="清空条件" src="images/lawCase/clear.png" width="46px" height="32px"></span>';
				}else if(type=="主机状态"){
					 htmlc='<select  class="select_txt" id="content" style="width:220px">'+
						<c:forEach items="${dataDictionary.hostStatus}" var="map" >
							'<option value="${map.key}">${map.value}</option>'+
						</c:forEach>
						'</select>'+
						'<span class="searchSpan"  type="button"><img title="搜索" src="images/lawCase/search.png"  width="46px" height="32px" onclick="searchList()"></span>'+
						'<span class="searchSpan"  type="button" onclick="getListNoCondition();"><img title="清空条件" src="images/lawCase/clear.png" width="46px" height="32px"></span>';
				}else if(type=="控制状态"){
					 htmlc='<select  class="select_txt" id="content" style="width:220px">'+
						<c:forEach items="${dataDictionary.controlStatus}" var="map" >
							'<option value="${map.key}">${map.value}</option>'+
						</c:forEach>
						'</select>'+
						'<span class="searchSpan"  type="button"><img title="搜索" src="images/lawCase/search.png"  width="46px" height="32px" onclick="searchList()"></span>'+
						'<span class="searchSpan"  type="button" onclick="getListNoCondition();"><img title="清空条件" src="images/lawCase/clear.png" width="46px" height="32px"></span>';
				}else if(type=="所属方向"){
					 htmlc='<select  class="select_txt" id="content" style="width:220px">'+
						<c:forEach items="${dataDictionary.direction}" var="map" >
							'<option value="${map.key}">${map.value}</option>'+
						</c:forEach>
						'</select>'+
						'<span class="searchSpan"  type="button"><img title="搜索" src="images/lawCase/search.png"  width="46px" height="32px" onclick="searchList()"></span>'+
						'<span class="searchSpan"  type="button" onclick="getListNoCondition();"><img title="清空条件" src="images/lawCase/clear.png" width="46px" height="32px"></span>';
			   }else{
					    htmlc='<input class="search_input"  type="text" id="content" name="content" placeholder="搜索内容"/>'+
       					'<span class="searchSpan"  type="button"><img title="搜索" src="images/lawCase/search.png"  width="46px" height="32px" onclick="searchList()"></span>'+
       					'<span class="searchSpan"  type="button" onclick="getListNoCondition();"><img title="清空条件" src="images/lawCase/clear.png" width="46px" height="32px"></span>';
						}
			$(".search_div").html(htmlc);
		}
		function exportH(){
			var	rowIds = $("#dataList").jqGrid('getGridParam','selarrrow');
			if(rowIds!= null&&rowIds!=''){
				confirm('确定要导出吗?',function(){
					var url="ajax_exportLC/exportLawObjs.action?lawCaseType=host&ids="+rowIds;
					parent.openExportDiv();
					$.ajax( {
						type : "POST",
						url : url,
						success : function(data){
							var subDir = data.currentTimeStr;
							var dlFileUrl = '<%=basePath%>fileManage/downloadFile.action?fileNameD=Hosts.zip&type=host&subDir='+subDir;
							parent.closeExportDiv(dlFileUrl);
						}
					});
				});
			}else{
				parent.hintManager.showHint("请选择列表中进行导出的人员");
			}
		}
		
		function getListNoCondition(){
			changeInput();
			$(".select_txt").html("主机名称");
			$(".select_txt").attr("name", "hostName");
			searchList();
		}
		
		function openwin(url) {
		 	window.open (url);
		}
	</script>
		</body>
</html>