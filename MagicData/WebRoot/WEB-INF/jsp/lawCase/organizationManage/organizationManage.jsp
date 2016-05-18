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
		<title>组织管理</title>
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
		<div class="userTab" style="width:100%;">
			<span class="spanSeton" onclick="flushListPage('orgCard')">组织卡片</span>
			<span onclick="flushListPage('orgList')">组织列表</span>
		</div>
		<div class="userconBox"  style="width:100%;">
			<div class="userCon clearfix">
			    <!-- 卡片条件 -->
				<div class="commodity-screening">
					<div class="commodity-cont">
						<div class="selected-conditions">
							<label class="selected-label">检索条件</label>
							<div class="apend"></div>
							<p class="tiShi">共有<em id="totalRecords">${pageModel.totalRecords}</em>个附和条件的对象</p>
						</div>
						<c:forEach items="${orgSearchPreWapper.cardConditionMap}" var="cardCond"  varStatus="ccStatus">
						<div class="selected-list clearfix" id="${ccStatus.index+1}">
						     <label>${cardCond.value}</label>
						     <div class="fldiv">
						     <c:forEach items="${orgSearchPreWapper.cardSubConditionMap}" var="cardSubCond" >
						     <c:if test="${cardSubCond.key==cardCond.key}">
						       <c:forEach items="${cardSubCond.value}" var="condition" >
						         <span onclick="pagingData('1','${condition.key}','${cardSubCond.key}');" value="${condition.key}">${condition.value}</span>
						       </c:forEach>
						     </c:if>
						     </c:forEach></div>
						</div>
						</c:forEach>
					</div>
				</div>
				<!-- 卡片列表 -->
				<div class="imglistBox clearfix">
					<div id="cardList">
		            </div>
					<div class="clear"></div>	
					<!--分页-->
					<div class="fenye clearfix">
				    	<ul class="pageUl list-unstyled clearfix">
				        	<li id="first">首页</li>
				            <li id="top" onclick="topclick()">上一页</li>
				            <li class="xifenye" id="xifenye">
				            	<a id="xiye"></a>/<a id="mo"></a>
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
				    <!-- 列表工具栏 -->
					<div class="leftBar pull-left">
						<ul class="list-unstyled clearfix">
							<li><a href="javascript:void(0)" class="addzz zzSpan">添加</a></li>
							<li class="delete"><a href="javascript:void(0)" onclick="deleteOrg()">删除</a></li>
							<li class="dc"><a href="javascript:exportO()">导出</a></li>
						</ul>
					</div>
					<div class="rightBar pull-right">
					    <!-- 列表查询条件end -->
						<div class="searchbar">
							<div class="select_box">
							     <c:forEach items="${orgSearchPreWapper.listConditionMap}" var="listCond"  begin="0" end="0">
							     <span class="select_txt" name="${listCond.key}">${listCond.value}</span>
							     </c:forEach>
								<a class="selet_open"></a>
						        <div class="option">
						             <c:forEach items="${orgSearchPreWapper.listConditionMap}" var="listCond" >
						                  <span class="${listCond.key}">${listCond.value}</span>
						             </c:forEach>
						        </div>   
							</div>
							 <div class="search_div">
							    <c:forEach items="${orgSearchPreWapper.listSubConditionMap}" var="listSubCond"  begin="0" end="0">
							    <input class="commonSelect  currentSelect"  type='text'  id="${listSubCond.key}" name="${listSubCond.key}" placeholder='搜索内容'/>
							    </c:forEach>
            					<c:forEach items="${orgSearchPreWapper.listSubConditionMap}" var="listSubCond" >
            					    <c:if test="${listSubCond.value==null}">
            					    <input class="commonSelect hideSelect"  type='text'  id="${listSubCond.key}" name="${listSubCond.key}" placeholder='搜索内容'/>
            					    </c:if>
            					    <c:if test="${listSubCond.value!=null}">
            					    <select class="commonSelect hideSelect" name="${listSubCond.key}"  id="${listSubCond.key}">
									        <c:forEach items="${listSubCond.value}" var="valueMap" >
								   		          <option value="${valueMap.key}">${valueMap.value}</option>  
									        </c:forEach>
								    </select>
            					    </c:if>
						        </c:forEach>
            					<span class="searchSpan"  type="button" onclick="getListData();"><img title="搜索" src="images/lawCase/search.png" width="46px" height="32px"></span>
            					<span class="searchSpan"  type="button" onclick="getListNoCondition();"><img title="清空条件" src="images/lawCase/clear.png" width="46px" height="32px"></span>
           					</div>
						</div>
						<!-- 列表查询条件end -->
					</div>
				</div>
				<!--表格-->
				<div class="table" style='width:100%;margin:0 auto'>
					<table id="dataList"></table>
					<div id="pager"></div>
				</div>
			</div>
		</div>
<!-- jqgrid -->
<script type="text/javascript" src="scripts/jquery.min.js"></script>
<script type="text/javascript" src="scripts/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="scripts/grid.locale-cn.js"></script>
  	
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
	var tableWidth = 0;
	var H = 0;
	$(function(){
		tableWidth = $(".userCon").width()-12;
		H = $(".userCon").height()-85;
		pagingData(1);
		//加载表格
 		loadList();
	});
	
	
	/*--------------------------------------------------------------------- 主页面处理- ----------------------——————————————*/
	//点击标签页刷新
	function flushListPage(flag){
		if("orgCard" == flag){
			pagingData(currentPageNo_card,null,null);
		}else if("orgList" == flag){
			getListData();
		}
	}
	
	
	/*---------------------------------------------------------------------卡片处理--------------------------——————————————*/
	/* 卡片列表start */
	var currentPageNo_card = 1;
	var cardCondMap = new Map();
	/* 获得卡片选择数据条件*/
	function getCondDataFromMap(){
		var dataResult = "";
		for(var i=0;i<cardCondMap.arr.length;i++){
			var obj = cardCondMap.arr[i];
			if(null!=obj.key||obj.key!=undefined){
			   if(i+1==cardCondMap.arr.length){
				     dataResult += "'"+obj.key+"':'"+obj.value+"'";
			   }else{
				    dataResult +=  "'"+obj.key+"':'"+obj.value+"'"+",";
			   }
			}
		}
		return dataResult;
    }
    /*删除卡片条件*/
	function commondel(key){
		if(key){
		    var delKey = null;
		    var delValue = $("#append_"+key).attr("value");
		    for(var i=0;i<cardCondMap.arr.length;i++){
		        var obj = cardCondMap.arr[i];
		        if(obj.value==delValue){
		            delKey = obj.key;
		            break;
		        }
		    }
		    if(null!=delKey||delKey!=undefined){
		       cardCondMap.remove(delKey);
			   pagingData(1);
			}
		}
	} 
    /* 卡片分页查询 pageNo-当前页号 */
	function pagingData(pageNo,value,key){
		currentPageNo_card = pageNo;
		cardCondMap.put(key,value);
		var condData = "";
		var condSubData = getCondDataFromMap();
		if(null!=condSubData&&""!=condSubData){
		       condData = "[{"+condSubData+"}]";
		}
		$.ajax({
	         type:"post",
	         url:"ajax_lawCaseOrg/getOrgList.action",
	         data:{ "searchType":'${searchType}',"cardPageNo":pageNo, "cardPageSize":50, "condJsonStr":condData},
	         async : false,
	         dataType:"json",
	         success:function(data){
	        	 var records = data.pageModel.totalRecords;//修改记录数
	        	 $("#totalRecords").html(records);
	        	 $("#xiye").html(data.pageModel.pageNo);
	        	 $("#mo").html(data.pageModel.totalPage);
	        	 var list = data.pageModel.list; //修改卡片列表
	        	 var len = list.length;
	        	 var html = "";
	     		 for(var i = 0;i < len; i++) {
	     			var temp = list[i];
	     			var imgFile = temp.orgImage;
	     			if(null==imgFile||imgFile==''||imgFile==undefined){
	     			   html += '<div class="imgList"><a href="lawCaseOrg/viewOrgDetail.action?orgObj.id='+temp.id+'"><div class="img_box"><img src="images/lawCase/moren/organaization.jpg"/></div><div class="hoverbox">';
	     			}else{
	     			   html += '<div class="imgList"><a href="lawCaseOrg/viewOrgDetail.action?orgObj.id='+temp.id+'"><div class="img_box">'
	     			                 +'<img src="images/lawCase/uploadImg/organization/' + temp.orgImage +'"/>'
	     			                 +'</div><div class="hoverbox">';
	     			}
	     			html += '<p>名称：<span>'+temp.orgCName+'</span></p>';
	     			html += '<p>方向：<span>'+temp.orgDirectionStr+'</span></p>';
	     			html += '<p>等级：<span>'+temp.orgImportLevelStr+'</span></p>';
                	html += '</div><p class="imgTitle">'+temp.orgCName+'</p></a></div>';
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
	/* 卡片列表end */
	
	
	/*-----------------------------------------------------------------------列表处理--------------------------——————————————*/
	
	//当前选择的List条件的name值
	var org_selectName = $(".select_txt").attr("name");
	
	/* 列表条件联动*/
	function conditionLinkage(nameAttr){
	    $(".currentSelect").addClass("hideSelect").removeClass("currentSelect");
		$("#"+nameAttr).addClass("currentSelect").removeClass("hideSelect");
		org_selectName = nameAttr;
	}
	
	function getListNoCondition(){
		$(".select_txt").attr("name", "orgCName");
		$(".select_txt").html("组织名称");
		conditionLinkage("orgCName");
		getListData();
	}
	
	/* 设置List的行高  */
	function setRowHeight(id){
	    var grid = $("#"+id);  
	    grid.closest(".ui-jqgrid-bdiv").css({ 'overflow-x' : 'hidden' });
        var ids = grid.getDataIDs();  
        for (var i = 0; i < ids.length; i++) {  
            grid.setRowData ( ids[i], false, {height: 30});  
         }
	}
	
	/* 初始化列表grid  */
   function loadList(){
       $("#dataList").jqGrid({
			url:"<%=basePath%>ajax_lawCaseOrg/getOrgList.action",
			datatype:"local", //数据来源，本地数据
			mtype:"POST",//提交方式
			height:H,//高度，表格高度。可为数值、百分比或'auto'
			width:tableWidth,//这个宽度不能为百分比
			colNames:['组织名称','创建时间','级别','控制状态','所属方向','所在地', '负责人','操作'],
			colModel:[
			    {name:'orgCName',index:'orgCName', width:'10%',align:'center'},
			    {name:'createTime',index:'createTime', width:'14%', align:'center',formatter:'date'},
			    {name:'orgImportLevelStr',index:'orgImportLevel', width:'6%', align:'center'},
			    {name:'orgControlStatusStr',index:'orgControlStatus', width:'6%', align:'center'},
			    {name:'orgDirectionStr',index:'orgDirectionStr', width:'8%', align:'center'},
			    {name:'orgLocation',index:'orgLocation', width:'6%', align:'center'},
			    {name:'orgDutyPersonStr', width:'14%', align:'center',sortable:false},
			    {name:'caseName',width:'5%',sortable:false,align:'center',
			    	formatter:function(cellvalue, options, row){
			    		 return '<a href="lawCaseOrg/viewOrgDetail.action?orgObj.id='+row.id+'" class="linkA" ><img src="images/lawCase/xqBtn.png"></a>&nbsp;&nbsp;'+
			    		 		'<a href="javascript:void(0)" onclick="updateOrgById('+row.id+')" class="linkA" ><img src="images/img/bj.png" /></a>';
			    	} 
			    }
			],
			rownumbers:false,
			viewrecords: true,//是否在浏览导航栏显示记录总数
			//loadonce:true,
			rowNum:10,//每页显示记录数
			rowList:[10,20,50,500],//用于改变显示行数的下拉列表框的元素数组。
			multiselect: true,
			sortname: "${pageModel.sidx == null ? 'orgCName' : pageModel.sidx}",	//从Pager对象中读取当前排序字段
   		    sortorder: "${pageModel.sord == null ? 'asc' : pageModel.sord}",
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
		    prmNames:{rows:"pageModel.pageSize",page:"pageModel.pageNo",sort:"pageModel.sidx",order:"pageModel.sord"},
			pager:'#pager',
			gridComplete:function(){
				setRowHeight("dataList");
			}
		});
    }
   
    /* 读取List中的数据  */
   function getListData(){
       var searchVal = $("#"+org_selectName).val();
       var condJsonStr = null;
       if(null!=searchVal&&searchVal.trim()!=""&&searchVal!=undefined){
            condJsonStr = "[{"+ "'" + org_selectName + "':'" + searchVal + "'" + "}]";
       }
       var param = { "searchType":"orgList","condJsonStr":condJsonStr};
       $("#dataList").jqGrid('setGridParam',{datatype:'json'});
       $("#dataList").jqGrid('setGridParam',{postData:param});
       $("#dataList").trigger("reloadGrid");
    }
     
	 /*删除组织*/
	 function deleteOrg(){
		if(tempIds.length == 0){
			parent.hintManager.showHint("请至少选择一条数据...");
		}else{
			confirm("删除将失去所有与其他对象的关联，确认删除？？",function(){
				$.ajax({
			         type:"post",
			         url:"ajax_lawCaseOrg/deleteOrgs.action",
			         data:{
			        	 "ids":tempIds.toString()
			         },
			         async : false,
			         dataType:"json",
			         success:function(data){
		 	        	getListData();
			         },
			         error:function(){
			        	 parent.hintManager.showHint("删除组织异常，请联系管理员！");
			         }
			     });
			});
		}
	 }
	 	/*编辑组织弹出层*/
	function updateOrgById(id){
		var ajData = {
		  	title:"编辑组织",
		  	url:"lawCaseOrg/viewOrgUpdate.action?orgObj.id="+id
	  	};
	  	parent.parentPop(ajData);
	}
	function exportO(){
		var	rowIds = $("#dataList").jqGrid('getGridParam','selarrrow');
		if(rowIds!= null&&rowIds!=''){
			confirm('确定要导出吗?',function(){
				var url="ajax_exportLC/exportLawObjs.action?lawCaseType=organization&ids="+rowIds;
				parent.openExportDiv();
				$.ajax( {
					type : "POST",
					url : url,
					success : function(data){
						var subDir = data.currentTimeStr;
						var dlFileUrl = '<%=basePath%>fileManage/downloadFile.action?fileNameD=Organizations.zip&type=organization&subDir='+subDir;
						parent.closeExportDiv(dlFileUrl);
					}
				});
			});
		}else{
			parent.hintManager.showHint("请选择列表中进行导出的组织");
		}
	}
	function openwin(url) {
	 	window.open (url);
	}
</script>
</body>
</html>
