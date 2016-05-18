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
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextPath+"/";
	pageContext.setAttribute("base",contextPath);
%>
<link href="<%=basePath%>styles/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
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
/*进度条  */
.progress {
	margin: 6px 0px;
}

#Container {
	width: 100%;
	margin: 0 auto; /*设置整个容器在浏览器中水平居中*/
}

#Content {
	height: 89%;
	margin-top: 20px;
}

#Content-Left {
	/* height:70%; */
	width: 35%;
	margin: 0px 0px 0px 90px;
	float: left;
	color: #fff;
}

#Content-Left label {
	width: 100px;
	text-align: right;
	display: inline-block;
	line-height: 32px;
}

#Content-Left tr {
	color: #fff;
}

#Content-Left tr td {
	width: 110px;
}

#connectionDB,#Content-Left tr.trWidth td input {
	width: 270px;
	height: 32px;
	background: #002744;
	border: 1px solid #014769;
	color: #fff;
	padding: 3px;
	margin: 9px 0px;
}

#Content-Left .test,#Content-Left .save {
	background: #012548;
	border: 1px solid #009cff;
	padding: 4px 15px;
	color: #fff;
	border-radius: 5px;
}

#Content-Left .save {
	margin: 10px 0px 0px 28px;
}

#Content-Main {
	width: 50%;
	margin: 0px;
	float: left;
}
.ui-jqgrid .ui-pg-table td{
	color:#fff;
}
</style>
<title>密码破解任务列表</title>
</head>
<style type="text/css">
</style>
<body margintop="0" marginleft="0">
	<div class="userBox">
		<div class="container-fluid container1" >
			<%-- <div class="userlistlogoBox clearfix">
				<span class="logoSpan"><img src="<%=basePath%>images/img/title.png" /></span>
				<p class="p1">密码破解</p>
				<div class="topRight">
					<ul>
						<li><a href="<%=basePath%>common/DataSmart.action">返回首页</a></li>
					</ul>
				</div>
			</div>
			<div class="conBox">
				<div class="userconBox">
					<div class="userCon clearfix"> --%>
						<div style='width:100%;height:85%;margin:0px;'>
							<div style="width:100%;height: 30px;padding-bottom: 3px;background-image: url(../images/img/pwbg.png);">
							     <div id="butBoxId" style="display: none;">
								      <a href="javascript:void(0)"><img src="<%=basePath%>images/img/pwdDel.png" id="btn-del"  align="top"/></a>
							     </div>
							</div>
							<table id="dataList"></table>
							<div id="pager"></div>
						</div>
				<!-- 	</div>
				</div>
			</div> -->
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
	<script type="text/javascript"
		src="<%=basePath%>/scripts/ui/jqGrid.formatter.js"></script>
	<script type="text/javascript" src="<%=basePath%>/scripts/customJs.js"></script>
	<!-- 自定义脚本 -->
	<script type="text/javascript">
var map = new Map();
//获得选中的行对象
function getDelRows(){
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

//刷新表格
function flushGrid(){
    	$("#dataList").jqGrid().trigger("reloadGrid");
}
    
$(document).ready(function() {

         $("#butBoxId").css("display","none");
		 $("#dataList").jqGrid({
				url:"<%=basePath%>ajax_pwdCrack/getPTListByPage.action",
		        datatype:"json", //数据来源，本地数据
		        mtype:"POST",//提交方式
		        height:'90%',//高度，表格高度。可为数值、百分比或'auto'
		        autowidth:true,//自动宽
		        colNames:['UUID','用户名', '待破解密码','破解结果', '创建时间', '执行状态', '操作'],
		        colModel:[
		            {name:'uuid',index:'uuid', width:'10%',align:'center',title:false},
		            {name:'userName',index:'userName', width:'10%', align:'center',title:false},
		            {name:'pwdEncrypt',index:'pwdEncrypt', width:'10%', align:'center',title:false,sortable:false},
		            {name:'pwdCrack',index:'pwdCrack', width:'10%',title:false,align:'center',title:false,sortable:false},
		            {name:'createTime',index:'createTime', width:'10%', align:'center',sorttype:'date',formatter:'date',
								    formatoptions: {
		                    	 	 srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'
		                }
		            },
		            {name:'runStatus',index:'runStatus', width:'5%',title:false,align:'center',
		            	formatter:function(cellvalue, options, row){
		            	       if(cellvalue==2){
		            	              //return "正在执行";
		            	            return probar(row.id);
		            		   }else if(cellvalue==1){
		            		           return "未执行";
		            		   }else if(cellvalue==0){
		            		          return "执行完成";
		            		   }else if(cellvalue==-1){
		            		          return "执行错误";
		            		   }
		            	} 
		            },
		            {name:'runStatus',index:'runStatus', width:'5%',title:false,align:'center',
		            	formatter:function(cellvalue, options, row){
		            	        if(cellvalue==2){return  '';}
		            	       else if(cellvalue==0){
		            	    	   return  '<a href="javascript:void(0)" class="deleteButton deleteA" name="'+row.id+'"><img src="<%=basePath%>images/img/del.png" /></a>';
		            		   }else{
		            			   return '<a href="javascript:void(0)" class="executeButton" name="'+row.id+'"><img src="<%=basePath%>images/img/zx.png" /></a>&nbsp;'+
		            	    	   		  '<a href="javascript:void(0)" class="deleteButton deleteA" name="'+row.id+'"><img src="<%=basePath%>images/img/del.png" /></a>';
		            		   }
		            	} 
		            }
		        ],
		        rownumbers: false,
		        sortable:true,
		        sortname: "${pi.sidx}",	//从Pager对象中读取当前排序字段
   		        sortorder: "${pi.sord}", //从Pager对象中读取当前排序方向 
		        viewrecords: true,//是否在浏览导航栏显示记录总数
		        rowNum:"${pi.pageSize}",//每页显示记录数
		        rowList:[15,20,25],//用于改变显示行数的下拉列表框的元素数组。
			    emptyrecords:"未找到符合条件的记录",
			    multiselect: true, //---select 传值	
	            jsonReader: {
	                    root:"pi.dataList",    // 数据行（默认为：rows）
	                    page: "pi.pageNo",     // 当前页
	                    total: "pi.totalPages",    // 总页数
	                    records: "pi.totalRecords",// 总记录数
                        repeatitems: false,
                        cell: "cell"
	            },
			    onSelectRow : function( id , status) {
					if (status) {
						map.put(id , status);
						$(this).data("currentSelection",+id);
					} else {
						$(this).removeData("currentSelection");
						map.remove(id);    
					}	
					if(map.arr.length<2){
						$("#butBoxId").css("display","none");
					}else {
						$("#butBoxId").css("display","inherit");
					}
				},
				/** 全部对象的选中状态 */
				onSelectAll : function(aRowids,status ){
				    if(aRowids.length!=0){
						if (status) {
							for(var i=0;i<aRowids.length;i++){
								map.put(aRowids[i],true);
							}
						  $("#butBoxId").css("display","inherit");
						} else {
							map.removeAll(map.arr);
							$("#butBoxId").css("display","none");
						}
					}
				},
	            prmNames:{rows:"pi.pageSize",page:"pi.pageNo",sidx:"pi.sidx",sord:"pi.sord"},
		        pager:'#pager',
		        gridComplete:function(){
		        	setRowHeight("dataList");
		        }
		    });
		    
		    //删除任务
	        $(".deleteButton").live("click",function(){
             	 delTask(1);
	        });
	        
	        $("#btn-del").click(function() {
		        delTask(0);								
	        });
	        
	        //任务执行
	       $(".executeButton").live("click",function(){
	   	         var runId = $(this).attr("name");
	   	          $(".executeButton").live("click",function(){
	   	              return false;
	   	          });
		         $.ajax({
				         type:"post",
				         url:"<%=basePath%>ajax_pwdCrack/runPwdTask.action",
				         data:{
				        	 runId:runId
			  	         },
				         dataType:"json",
				         async: false,
				         success:function(data){
				        	 var result = data.returnResult;
				        	 if(result == "SUCCESS"){
				        		 hintManager.showSuccessHint("任务成功执行");
				        		 flushGrid();
				        	 }else{
				        	     alert(result);
				        		 hintManager.showSuccessHint(result);
				        		 flushGrid();
				        	 }	        	 
				         },
				         error:function(){
				        	 hintManager.showHint("执行任务异常，请联系管理员！");
				         }
				     });
	        });
	     
	     	probar = function(id){
			     (function(){
				      $.post('<%=basePath%>ajax_pwdCrack/getRunningPencent.action',{'id':id},function(data){
					      if(data.pwdPencent!='-1'&&data.pwdPencent!='100'){
					            var v = data.pwdPencent+"%";
						        if(data.pwdPencent==null||data.pwdPencent==undefined){
						             v="0%";
						        }
							     $('#probarValue'+id).html(v);
							     $('#probarValueStyle'+id).attr('style','width: '+v+';');
							     setTimeout("probar("+id+")",15000);//15秒执行一次
							    return;
					      } else if(data.pwdPencent=='100'){
						        $('#probarValue'+id).html('100%');
						        $('#probarValueStyle'+id).attr('style','width:100%;');
						        //任务执行完成刷新表格
						        setTimeout("flushGrid()",5000);
						        return;
					      } else if(data.pwdPencent=='-1'){
					            flushGrid();
					      }else{
						        window.location.reload();
						        return;
					      }
				});
			})();
			return '<div class="progress">'+
	   			 '<div id="probarValueStyle'+id+'" class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">'+
	      		 '<span id="probarValue'+id+'">0%</span>'+
	   			 '</div>'+
				 '</div>';
		};
});

//设置记录行高
function setRowHeight(id){
		var grid = $("#"+id);  
        var ids = grid.getDataIDs();  
        for (var i = 0; i < ids.length; i++) {  
            grid.setRowData ( ids[i], false, {height: 30} );  
        }
}
//删除对象
function delTask(delType){
        var delids="";
        if(delType==1){
           delids = $(".deleteButton").attr("name");
        }else{
           delids=getDelRows();
        }
        if(confirm("是否确认删除这些任务？")){
           $.ajax({
		         type:"post",
		         url:"<%=basePath%>ajax_pwdCrack/deletePTById.action",
		         data:{
		        	delids:delids
	  	         },
		         dataType:"json",
		         success:function(data){
		            alert(data.returnResult);
		            hintManager.showHint(data.returnResult);
		        	flushGrid();
		         },
		         error:function(){
		        	 hintManager.showHint("删除任务异常，请联系管理员！");
		         }
		     });
        }
}
	</script>
</body>
</html>
