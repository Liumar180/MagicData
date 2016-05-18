<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("base",basePath);
String username=(String)session.getAttribute("username");
Integer roleType=(Integer)session.getAttribute("roleType");
request.setAttribute("username",username);
request.setAttribute("roleType",roleType);
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>数据导入</title>
		
		<!-- <link href="<%=basePath%>/styles/css/userList.css" rel="stylesheet" type="text/css"> -->
		<link href="<%=basePath%>/styles/css/ui.jqgrid.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath%>/styles/css/dataImport/jquery-ui.min.css" rel="stylesheet" type="text/css">
		<link href="<%=basePath%>/styles/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/css/dataImport/dataImport.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/css/dataImport/imgscroll.css"/>
		
	</head>
	<body>
		<div class="headerBox">
			<div class="container">
				<nav role="navigation" class="navbar">
					<div class="navbar-header">
	      				<a href="javascript:void(0)" class="navbar-brand"><img src="<%=basePath%>images/import/DataSmart-logo.png"/></a>
	      				<span class="dataSpan"><em>|</em>数据导入</span>
	   				</div>
	   				<div class="navMenu">
	   					<ul class="list-unstyled">
	   						<li extend="taskList" class="li-seton">任务列表</li>
	   						<li extend="mainCon">任务配置</li>
	   					</ul>
	   				</div>
				</nav>
			</div>
		</div>
		<!--内容区域-->
		<div class="mainCon level" style="">
			<div class="container">
				<div class="mainCon-top">
					<h3>指定源</h3>
					<span class="borderSpan"></span>
					<span class="addSpan"><a href="javascript:void(0)">添加源</a></span>
					<span class="iconSpan"><img src="<%=basePath%>images/import/packup.png"/></span>
				</div>
              	<div id="imgBox">
				    <div class="boxCont" id="">
				    	<a href="javascript:void(0)" class="preButtn" id="prevBtn"></a>
				    	<div class="imgList" id="scrollConbox">
							<div id="GUN">
								<div class="imgScroll" id="imgScroll2"></div>
							</div>
				    	</div>
				    	<a href="javascript:void(0)" class="nextButtn" id="nextBtn"></a>
				    </div>
				</div>
              	<!--第二部分-->
              	<div class="clear"></div>
              	<div class="mainCon-top2">
					<h3>选中源的样例数据</h3>
					<span class="borderSpan2"></span>
					<span class="iconSpan2"><img src="<%=basePath%>images/import/packup.png"/></span>
				</div>
				<div class="tableDiv">
					<div class="table-responsive">
					   	<table class="table">
					      	<thead><tr></tr></thead>
					      	<tbody></tbody>
					   	</table>							   
					</div>
				</div>
				<!--第三部分-->
				<div class="maincon-third clearfix">
					<div class="thirdleft pull-left">
						<h3>拓扑图</h3>
						<div class='displayDiv' id="svgImg" style="width:100%;height: 530px;background-color:#0d2433;"></div>
						<div class='zoomline' style='width:30px; height:150px;position: absolute; top: -10px;left: 0;'></div>
					</div>
					<div class="thirdcenter pull-left">
						<h3>数据库关系</h3>
						<div class="whereRelation">
							<select class="tableName" style="width: 18%;" ></select>
							<select class="columnName" style="width: 18%;" ></select>
							<input type="text" value="" style="width: 18%;" />
							<select class="tableName" style="width: 18%;" ></select>
							<select class="columnName" style="width: 18%;" ></select>
						</div>
						<h3 style="margin-top: 20px;">模型和数据源对应</h3>
						<div class="thirdcenterCon">
							<div class="modelMapDBS"></div>
						</div>
					</div>
					<div class="thirdright pull-left">
						<div class="thirdright-top clearfix">
							<p class="leftP pull-left"><b id="previewNodeName">没有选择点</b></p>
							<p class="rightP pull-right"><span id="previewNodeType"></span></p>
						</div>
						<div class="thirdrightCon">
							<div class="thirdrightinnerCon">
								<div class="topTable">
									<h3>对象属性</h3>
									<div class="table-responsive">
									   	<table class="table prevew property">
									      	<thead>
									         	<tr>
										         	<th class="firstTh">类型</th>
										         	<th>值</th>
												</tr>
									      	</thead>
									      	<tbody></tbody>
									   	</table>							   
									</div>
								</div>
								<div class=" topTable botTable">
									<h3>关联对象</h3>
									<div class="table-responsive">
									   	<table class="table prevew relation">
									      	<thead>
									         	<tr>
										         	<th class="firstTh">类型</th>
										         	<th>名称</th>
												</tr>
									      	</thead>
									      	<tbody></tbody>
									   	</table>							   
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--第四部分按钮-->
				<div class="clear"></div>
				<div class="mainCon-foot clearfix">
					<div class="leftBtn pull-left">
						<a href="javascript:void(0)">帮助</a>
					</div>
					<div class="centerBtn pull-left">
						<a href="javascript:void(0)" class="saveMap">保存操作图</a>
						<a href="javascript:void(0)" class="loadMap">加载操作图</a>
					</div>
					<div class="rightBtn pull-right">
						<a class="PreviewBtn" href="javascript:void(0)">预览对应</a>
						<a class="saveBtn" href="javascript:void(0)">保存任务</a>
					</div>
				</div>
			</div>			
		</div>
		
		<div class="taskList level" style="height: 93%;">
			<!-- <iframe src="<%=basePath%>import/getImportPage.action" style="width: 100%;height: 100%;"></iframe> -->
			<div class="userCon clearfix" style="height: 95%;padding-top: 0px;">
				<div style='width:100%;margin:0 auto'>  
					<table id="dataList"></table>
					<div id="pager"></div>
				</div>
			</div>
		</div>
		
		<!--保存的弹出层-->
		<div class="modal fade" id="savemyModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		   	<div class="modal-dialog">
		      	<div class="modal-content savemodal-content">	
		      		<div class="modal-header">
		      			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>           
         			</div>
		         	<div class="modal-body savemodal-body">
		         		<p>请输入任务名称：</p>
		         		<div class="inputdiv">
		         			<input type="" name="" id="taskName" value="" />
		         			<span>*（必填项）</span>
		         		</div>
		         		<p>请对任务进行描述：</p>
		         		<textarea name="" rows="" cols="" id="taskDesc"></textarea>
		         	</div>
			        <div class="modal-footer">
			            <button type="button" class="btn bcBtn saveTask">保存</button>
			            <button type="button" class="btn qxBtn" data-dismiss="modal">取消</button>
			        </div>
		      </div>
			</div>
		</div>
		<!--添加模型的弹出层-->
		<div class="modal fade" id="addmodalmyModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		   	<div class="modal-dialog addmodal-dialog">
		      	<div class="modal-content addmodal-content">	
		      		<div class="modal-header">		      			
		      			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>  
		      			<h4 class="modal-title" id="">添加模型</h4>
         			</div>
		         	<div class="modal-body addmodal-body clearfix">
		         		<div class="leftaddCon pull-left">
		         			<p class="lefttitlep">选择节点</p>
		         			<div class="leftaddBtn pull-right"><a href="javascript:void(0)">Add</a></div>
		         			<div class="table-responsive commTable">
							   	<table class="table modelList" style="table-layout:fixed">
							      	<thead>
							         	<tr>
								         	<th class="firstTh">节点名称</th>
								         	<th>操作</th>
										</tr>
							      	</thead>
							      	<tbody></tbody>
							   	</table>							   
							</div>
	         			</div>
		         		<div class="rightaddCon pull-right">
		         			<p class="righttitlep">节点属性</p>
		         			<div class="rightaddBtn pull-right"><a href="javascript:void(0)">Add</a></div>
		         			<div class="table-responsive commTable">
							   	<table class="table propertyList">
							      	<thead>
							         	<tr>
								         	<th class="firstTh">名称</th>
								         	<th>类型</th>
								         	<th>属强</th>
								         	<th>索引</th>
								         	<th>值类型</th>
										</tr>
							      	</thead>
							      	<tbody></tbody>
							   	</table>							   
							</div>
		         		</div>
		         	</div>
					<div class="modal-footer">
						<button type="button" class="btn bcBtn addNode">确定</button>
						<button type="button" class="btn qxBtn" data-dismiss="modal">取消</button>
					</div>
		      </div>
			</div>
		</div>
		<!--添加模型的弹出层--结束-->
		<!--添加模型里--添加节点的弹出层--开始-->
		<div class="modal fade" id="addnodemyModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		   	<div class="modal-dialog nodemodal-dialog">
		      	<div class="modal-content addnodemodal-content">	
		      		<div class="modal-header">		      			
		      			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>  
		      			<h4 class="modal-title" id="">添加模型<em>>></em><span>添加节点</span></h4>
         			</div>
		         	<div class="modal-body addnodemodal-body clearfix">
		         		<div class="modalnameDiv">
		         			<div class="form-group clearfix">
		         				<label class="labelTit">模型名称</label>
								<input type="text" id="modalName" class="form-control" placeholder="请输入模型名称" />
		         			</div>	
		         		</div>
		         		<div class="nodeconDiv">		         			
		         			<div class="topnode clearfix">
		         				<ul class="list-unstyled clearfix">
		         					<li>属性名称</li>
		         					<li>属性类型</li>
		         					<li>值类型</li>
		         					<li class="sq">属强</li>
		         					<li class="sy">索引</li>
		         					<li class="add">
		         						<div class="addnodeBtn">
		         							<a href="javascript:void(0)">Add</a>
		         						</div>
		         					</li>
		         				</ul>
		         				<div class="topnodeCon">
		         					<ul class="list-unstyled clearfix">
		         						<li>
		         							<input type="text" class="attributeName" />
		         						</li>
		         						<li>
		         							<select class="attributeType">
												<option value="java.lang.String">字符串</option>
												<option value="java.lang.Character">字符</option>
												<option value="java.lang.Boolean">布尔</option>
												<option value="java.lang.Byte">字节</option>
												<option value="java.lang.Short">短</option>
												<option value="java.lang.Integer">整形</option>
												<option value="java.lang.Long">长整形</option>
												<option value="java.lang.Float">浮点</option>
											</select>
		         						</li>
		         						<li>
		         							<select class="attributeCardinality">
		         								<option value="1">单个值</option>
												<option value="2">不可重复</option>
												<option value="3">可以重复</option>
		         							</select>
		         						</li>
		         						<li class="sq">
		         							<input type="checkbox" name="" id="" class="attributeSQ"/>
		         						</li>
		         						<li class="sy">
		         							<input type="checkbox" name="" id="" class="attributeSY"/>
		         						</li>
		         						<li class="add delnode">
		         							<img src="<%=basePath%>images/import/nodeDelete.png"/>
		         						</li>
		         					</ul>
		         				</div>
		         			</div>
		         		</div>
		         	</div>
		         	<div class="modal-footer">
			            <button type="button" class="btn bcBtn addNode">保存</button>
			            <button type="button" class="btn qxBtn">返回</button>
			        </div>
		      </div>
			</div>
		</div>
		<!-- 添加节点--结束-->
		<!--添加模型里--添加节点---  添加节点属性的弹出层--开始-->
		<div class="modal fade" id="addnodeattrmyModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		   	<div class="modal-dialog nodeattrmodal-dialog">
		      	<div class="modal-content addnodeattrmodal-content">	
		      		<div class="modal-header">		      			
		      			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>  
		      			<h4 class="modal-title" id="">添加模型<em>>></em><span>添加<b>model1</b>节点属性</span></h4>
         			</div>
		         	<div class="modal-body addnodeattrmodal-body clearfix">
		         		<div class="nodeattrconDiv">		         			
		         			<div class="topnodeattr clearfix">
		         				<ul class="list-unstyled clearfix">
		         					<li>属性名称</li>
		         					<li>属性类型</li>
		         					<li>值类型</li>
		         					<li class="sq">属强</li>
		         					<li class="sy">索引</li>
		         					<li class="add">
		         						<div class="addnodeattrBtn">
		         							<a href="javascript:void(0)">Add</a>
		         						</div>
		         					</li>
		         				</ul>
		         				<div class="topnodeattrCon">
		         					<ul class="list-unstyled clearfix">
		         						<li>
		         							<input type="text" class="attributeName" />
		         						</li>
		         						<li>
		         							<select class="attributeType">
												<option value="java.lang.String">字符串</option>
												<option value="java.lang.Character">字符</option>
												<option value="java.lang.Boolean">布尔</option>
												<option value="java.lang.Byte">字节</option>
												<option value="java.lang.Short">短</option>
												<option value="java.lang.Integer">整形</option>
												<option value="java.lang.Long">长整形</option>
												<option value="java.lang.Float">浮点</option>
											</select>
		         						</li>
		         						<li>
		         							<select class="attributeCardinality">
		         								<option value="1">单个值</option>
												<option value="2">不可重复</option>
												<option value="3">可以重复</option>
		         							</select>
		         						</li>
		         						<li class="sq">
		         							<input type="checkbox" name="" id="" class="attributeSQ"/>
		         						</li>
		         						<li class="sy">
		         							<input type="checkbox" name="" id=""  class="attributeSY"/>
		         						</li>
		         						<li class="add delnode1">
		         							<img src="<%=basePath%>images/import/nodeDelete.png"/>
		         						</li>
		         					</ul>		         				
		         				</div>
		         			</div>
		         		</div>
		         	</div>
		         	<div class="modal-footer">
			            <button type="button" class="btn bcBtn addProperty">保存</button>
			            <button type="button" class="btn qxBtn">返回</button>
			        </div>
		      </div>
			</div>
		</div>
		<!-- 添加节点属性--结束-->
		<!--添加数据源的弹出层-->
		<div class="modal fade" id="adddataSource" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		   	<div class="modal-dialog adddatamodal-dialog">
		      	<div class="modal-content adddatamodal-content">	
		      		<div class="modal-header">		      			
		      			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>  
		      			<h4 class="modal-title" id="">选择数据源</h4>
         			</div>
		         	<div class="modal-body adddatamodal-body clearfix">
		         		<div class="datatabDiv">
		         			<ul class="list-unstyled tabUl clearfix">
		         				<li class="licur">数据库</li>
		         				<li>CSV</li>
		         			</ul>		         			         		
		         			<div class="datatabCon clearfix">
				         		<div class="leftadddataCon pull-left">
				         			<p class="leftdatatitlep">选择数据源</p>
				         			<div class="leftadddataBtn pull-right"><a href="javascript:void(0)">添加数据库</a></div>
				         			<div class="table-responsive commTable">
									   	<table class="table dblist" style="table-layout:fixed">
									      	<thead>
									         	<tr>
										         	<th class="firstTh">节点名称</th>
										         	<th>操作</th>
												</tr>
									      	</thead>
									      	<tbody></tbody>
									   	</table>							   
									</div>
			         			</div>
			         			<div class="arrowdiv">
				         			<p class="namep">详情...</p>
				         		</div>
				         		<div class="rightadddataCon pull-right">
				         			<p class="rightdatatitlep">数据源信息</p>
				         			<div class="dataMessage"></div>
				         		</div>
		         			</div>
			         		<div class="datatabCon hidetabCon clearfix">
			         			<!--csv左侧-->
			         			<div class="csvLeft pull-left">
			         				<p class="csvleftp">选择数据源</p>
			         				<div class="csvaddBtn pull-right"><a href="javascript:void(0)">添加CSV文件</a></div>
				         			<div class="table-responsive commTable csvleftTable">
									   	<table class="table csvlist" style="table-layout:fixed">
									      	<thead>
									         	<tr>
										         	<th class="firstTh col-md-9">文件名称</th>
										         	<th class="col-md-3" style="text-align: center;">操作</th>
												</tr>
									      	</thead>
									      	<tbody></tbody>
									   	</table>							   
									</div>
			         			</div>
			         			<!--csv右侧-->
			         			<div class="csvRight pull-right">
			         				<p class="csvrightTit">数据源信息</p>
			         				<div class="csvrightCon" style="display: none;">
				         				<div class="csvrightTable">
				         					<table class="table csvSampleData">
				         						<thead></thead>
										      	<tbody></tbody>
				         					</table>
				         				</div>
				         				<div class="utfDiv clearfix">
				         					<span>选择文件编码：</span>
				         					<select name="" class="form-control">
				         						<option value="UTF-8">UTF-8</option>
				         						<option value="ANSI">ANSI</option>
				         						<option value="GBK">GBK</option>
				         						<option value="GB2312">GB2312</option>
				         						<option value="GB18030">GB18030</option>
				         					</select>
				         				</div>
				         				<div class="fieldSymbol">
				         					<p>选择字段的分隔符号：</p>
				         					<div class="symbolList">
				         						<label class="checkbox-inline">
      												<input type="radio" name="optionsRadiosinline" id="" value="option1" extend=",">
												逗号</label>
   												<label class="checkbox-inline">
													<input type="radio" name="optionsRadiosinline" id="" value="option2" extend=";">
												分号</label>
   												<label class="checkbox-inline">
													<input type="radio" name="optionsRadiosinline" id="" value="option2" extend="tab">
												Tab</label>
   												<label class="checkbox-inline">
													<input type="radio" name="optionsRadiosinline" id="" value="option2" extend="space">
												空格</label>
   												<label class="checkbox-inline">
													<input type="radio" name="optionsRadiosinline" id="" value="option2" extend="other">
												其他</label>
   												<input type="text" class="form-control" id="" value="" />
				         					</div>
				         				</div>
				         				<div class="headerRadio">
											<span class="comSpan">是否有表头：</span>
											<div class="headerradioList">			
												<label class="checkbox-inline">
													<input type="radio" name="titleFlag" value="option1" extend="true">是
												</label>
												<label class="checkbox-inline">
													<input type="radio" name="titleFlag" value="option1" extend="false">否
												</label>
											</div>
										</div>
				         				<div class="fieldsDiv">
				         					<p>选择需要的字段：</p>
				         					<div class="fieldsCon"></div>
				         				</div>
			         				</div>
			         			</div>
			         			<!--右侧结束-->
			         			<div class="clear"></div>
			         		</div>
		         		</div>	
		         	</div>
					<div class="modal-footer">
						<button type="button" class="btn bcBtn addDBS">确定</button>
						<button type="button" class="btn qxBtn" data-dismiss="modal">取消</button>
					</div>
		      </div>
			</div>
		</div>
		<!--添加数据源的弹出层--结束-->
		<!--添加数据源--添加数据库弹出层开始-->
		<div class="modal fade" id="adddatabasemyModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		   	<div class="modal-dialog databasemodal-dialog">
		      	<div class="modal-content adddatabasemodal-content"></div>
			</div>
		</div>
		<!-- 添加数据源--添加数据库弹出层--结束-->
		<!--添加数据源--SQL语句与表结构开始---->
		<div class="modal fade" id="SQL" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		   	<div class="modal-dialog addsqlmodal-dialog">
		      	<div class="modal-content addsqlmodal-content">
		      		<div class="modal-header">		      			
		      			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>  
		      			<h4 class="modal-title" id="">添加数据源<em>>></em><span>SQL语句与表结构</span></h4>
         			</div>
		         	<div class="modal-body addsqlmodal-body clearfix">
		         		<div class="sqltabDiv">
		         			<ul class="list-unstyled sqlUl clearfix">
		         				<li class="sqllicur">SQL语句</li>
		         				<li>表结构</li>
		         			</ul>		         			         		
		         			<div class="sqltabCon clearfix">
				         		<p>请在此输入SQL语句</p>
				         		<textarea cols="" rows="8">select  idlocaltion, person ,emailid ,phone, emailacount  from has_conflict_test</textarea>
		         			</div>
			         		<div class="sqltabCon hidesqltabCon clearfix tableList"></div>
		         		</div>	
		         	</div>
		         	<div class="modal-footer">
			            <button type="button" class="btn bcBtn">测试</button>
			            <button type="button" class="btn qxBtn">返回</button>
			        </div>
		      </div>
			</div>
		</div>
		<!--添加数据源--SQL语句与表结构的弹出层--结束-->
		<!--删除数据源的弹出层--开始-->
		<div class="modal fade" id="showDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		   	<div class="modal-dialog">
		      	<div class="modal-content deldataSource-content">	
		      		<div class="modal-header">
		      			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>  
		      			<h4 class="modal-title" id="">提示</h4>
         			</div>
		      	</div>
			</div>
		</div>
		<!--删除数据源的弹出层--结束-->
		<!--删除模板的弹出层--开始-->
		<div class="modal fade" id="deltemplate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		   	<div class="modal-dialog">
		      	<div class="modal-content deltemplate-content">	
		      		<div class="modal-header">		      			
		      			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>  
		      			<h4 class="modal-title" id="">提示</h4>
         			</div>
		         	<div class="modal-body commondel-body clearfix">
		         		<p><em><img src="<%=basePath%>images/import/question.png"/></em>确定要删除改模版吗？</p>
		         	</div>
		         	<div class="modal-footer deltemplate-footer">
			            <button type="button" class="btn bcBtn">确定</button>
			            <button type="button" class="btn qxBtn" data-dismiss="modal">取消</button>
			        </div>
		      	</div>
			</div>
		</div>
		<!--删除模板的弹出层--结束-->
		<!--清空模板的弹出层--开始-->
		<div class="modal fade" id="emptytemplate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		   	<div class="modal-dialog">
		      	<div class="modal-content emptytemplate-content">	
		      		<div class="modal-header">		      			
		      			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>  
		      			<h4 class="modal-title" id="">提示</h4>
         			</div>
		         	<div class="modal-body commondel-body clearfix">
		         		<p><em><img src="<%=basePath%>images/import/question.png"/></em>确定要清空模版吗？？</p>
		         	</div>
		         	<div class="modal-footer emptytemplate-footer">
			            <button type="button" class="btn bcBtn">确定</button>
			            <button type="button" class="btn qxBtn" data-dismiss="modal">取消</button>
			        </div>
		      	</div>
			</div>
		</div>
		<!--清空模板的弹出层--结束-->
		<!--选择模板的弹出层--开始-->
		<div class="modal fade" id="choosetemplate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		   	<div class="modal-dialog choosetemplate-dialog">
		      	<div class="modal-content choosetemplate-content">	
		      		<div class="modal-header">
		      			<h4 class="modal-title" id="myModalLabel">选择模板</h4>
         			</div>
		         	<div class="modal-body choosetemplate-body clearfix">
		         		<ul class="list-unstyled choosetemplateUl clearfix"></ul>
		         	</div>
		         	<div class="modal-footer choosetemplate-footer">
			            <button type="button" class="btn bcBtn">确定</button>
			            <button type="button" class="btn qxBtn" data-dismiss="modal">取消</button>
			        </div>
		      	</div>
			</div>
		</div>
		<!--选择模板的弹出层--结束-->
		<!--保存模板的弹出层--开始-->
		<div class="modal fade" id="savetemplate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		   	<div class="modal-dialog">
		      	<div class="modal-content savetemplate-content">	
		      		<div class="modal-header">
		      			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>           
         			</div>
		         	<div class="modal-body savetemplate-body">
		         		<p>请输入模板名称：</p>
		         		<div class="inputdiv">
		         			<input type="" name="" id="" value="" />
		         			<span>*（必填项）</span>
		         		</div>
		         		<p>请对模板进行描述：</p>
		         		<textarea name="" rows="" cols=""></textarea>
		         	</div>
			        <div class="modal-footer">
			            <button type="button" class="btn bcBtn">保存</button>
			            <button type="button" class="btn qxBtn" data-dismiss="modal">取消</button>
			        </div>
		      </div>
			</div>
		</div>
		<!--保存模板的弹出层--结束-->
		<!--csv--点击csv页面add按钮出现的弹出层-->
		<div class="modal fade" id="addCsv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		   	<div class="modal-dialog addcsv-dialog">
		      	<div class="modal-content addCsv-content"></div>
			</div>
		</div>
		<!--csv--点击csv页面add按钮出现的弹出层--结束-->
		<script type="text/javascript" src="<%=basePath%>/scripts/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/scripts/ajaxfileupload.js"></script>
		<script type="text/javascript" src="<%=basePath%>/scripts/grid.locale-cn.js"></script>
		<script type="text/javascript" src="<%=basePath%>/scripts/jquery.jqGrid.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/styles/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/styles/bootstrap/js/bootstrap-prompts-alert.js"></script>
		
		<!-- <script src="<%=basePath%>scripts/easing.js"></script> -->
 		<script type="text/javascript" src="<%=basePath%>scripts/import/imgScroll.js"></script>
		<!-- <script type="text/javascript" src='/MagicData/scripts/d3.v3.js' ></script>
		<script type="text/javascript" src='/MagicData/scripts/d3-tip.js' ></script> -->
		<script type="text/javascript" src='<%=basePath%>scripts/d3.v3.js' ></script>
		<script type="text/javascript" src='<%=basePath%>scripts/d3-tip.js' ></script>
		<script type="text/javascript" src="<%=basePath%>/scripts/saveSvgAsPng.js"></script>
		<script src="<%=basePath%>scripts/import/dataImort.js" type="text/javascript" charset="utf-8"></script>
 		<script type="text/javascript" src="<%=basePath%>scripts/import/main.js"></script>
		<script type="text/javascript">
			pageVariate = {
				root:"",//查询对象
				personId:"",//查询对象ID
				uuid:"",//查询对象唯一标示
				types:[],//事件类型
				eventFlag:false,//当前显示状态
				startTime:"",//查询开始时间
				endTime:"",//查询结束时间
				startTimeStr:"",//时间轴所需
				endTimeStr:"",//时间轴所需
				initStartTime:"",
				initEndTime:"",
				fristFlag:true,
				selNode:[],//选中节点
				tempDeleteId:[],//删除id
				tempData:"",
				openMap:new Map(),//展开缓存
				currentSelFlag:false,
				typeMap:new Map(),//查询type缓存
				selectAllFlag:true,//列表全选标记
				base:"${base}",
				initFlag:true,
				pressCtrl:false,
				isClickNode : false,
				currentNode:"",
				IM:["QQ"],
				drawEdge:false
			}
			$(function(){
				var H = $(".userCon").height();
				//加载表格
				$("#dataList").jqGrid({
					url:"<%=basePath%>ajax_dataImport/getImportTaskList.action",
					datatype:"json", //数据来源，本地数据
					mtype:"POST",//提交方式
					height:H,//高度，表格高度。可为数值、百分比或'auto'
					//width:1000,//这个宽度不能为百分比
					autowidth:true,//自动宽
					colNames:['任务名称','数据源类型', '源表名/SQL', '创建时间', '执行状态', '操作'],
					colModel:[
						{name:'taskName',index:'taskName', width:'10%',align:'center'},
						{name:'targetName',index:'targetName', width:'5%', align:'center'},
						{name:'sourceType',index:'sourceType', width:'10%', align:'center',
							formatter:function(cellvalue, options, row){
								   if(cellvalue==0){
										   return row.sourceName;
								   }else if(cellvalue==1){
										  return row.sqlOrder;
								   }
							} 
						},
						{name:'dateStr',index:'dateStr', width:'10%', align:'center'},
						{name:'runStatus',index:'runStatus', width:'5%', align:'center',
							formatter:function(cellvalue, options, row){
								   if(cellvalue==0){
										   return "未执行";
								   }else if(cellvalue==2){
										  return "已执行";
								   }else if(cellvalue==-1){
										  return "执行错误";
								   }
							} 
						},
						{name:'runStatus',index:'runStatus', width:'5%', align:'center',
							formatter:function(cellvalue, options, row){
								   if(cellvalue==0){
									   return '<a href="javascript:void(0)" class="executeButton" name="'+row.id+'"><img src="<%=basePath%>/images/img/zx.png" /></a>'+
											  '<a href="javascript:void(0)" class="deleteButton deleteA" name="'+row.id+'"><img src="<%=basePath%>/images/img/del.png" /></a>'+
											  '<a href="javascript:void(0)" class="editButton" name="'+row.id+'"><img src="<%=basePath%>/images/img/bj.png" /></a>';
								   }else{
									   return "--";
								   }
							} 
						}
					],
					rownumbers:false,//添加左侧行号
					//altRows:true,//设置为交替行表格,默认为false
					//sortname:'createDate',
					//sortorder:'asc',
					viewrecords: true,//是否在浏览导航栏显示记录总数
					rowNum:15,//每页显示记录数
					rowList:[10,20,50],//用于改变显示行数的下拉列表框的元素数组。
					jsonReader: {
							root:"dataRows",    // 数据行（默认为：rows）
							page: "curPage.pageNo",     // 当前页
							total: "curPage.totalPages",    // 总页数
							records: "curPage.totalRecords",// 总记录数
							repeatitems : false                // 设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
					},
					prmNames:{rows:"curPage.pageSize",page:"curPage.pageNo"},
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
					grid.setRowData ( ids[i], false, {height: 34});  
				}
			}
			//刷新表格
			function flushGrid(){
				$("#dataList").jqGrid().trigger("reloadGrid");
			}
			//任务执行
			$(".executeButton").live("click",function(){
				var taskId = $(this).attr("name");
				$.ajax({
					type:"post",
					url:"<%=basePath%>ajax_dataImport/judgeExecute.action",
					data:{ date:new Date().getTime() },
					dataType:"json",
					success:function(data){
						var result = data.result;
						if(result == true){
							hintManager.showHint("当前有正在执行的任务，请稍后执行！");
						}else{//执行任务
							setTimeout("flushGrid()",5000);
							$.ajax({
								type:"post",
								url:"<%=basePath%>ajax_dataImport/startTask.action",
								data:{
									taskId:taskId
								},
								dataType:"json",
								success:function(data){
									var result = data.result;
									if(result == true){
										hintManager.showSuccessHint("任务成功执行完成！");
									}else{
										hintManager.showHint("任务执行失败，请联系管理员！");
									}
								},
								error:function(){
									hintManager.showHint("执行任务异常，请联系管理员！");
								}
							});
						}
					},
					error:function(){
						hintManager.showHint("当前有正在执行的任务，请稍后执行！");
					}
				});
			});
			
			//删除任务
			$(".deleteButton").live("click",function(){
				var taskId = $(this).attr("name");
				confirm("是否确认删除此任务？",function(){
					$.ajax({
						 type:"post",
						 url:"<%=basePath%>ajax_dataImport/deleteTaskById.action",
						 data:{
							 taskId:taskId,
							 date:new Date().getTime()
						 },
						 dataType:"json",
						 success:function(data){
							 flushGrid();
						 },
						 error:function(){
							 hintManager.showHint("删除任务异常，请联系管理员！");
						 }
					 });
				});
			});
			$(".editButton").live("click",function(){
				var taskId = $(this).attr("name");
				$(".headerBox").find("li[extend='mainCon']").click();
				disPage.editTask(taskId);
			});
		</script>
	</body>
</html>
