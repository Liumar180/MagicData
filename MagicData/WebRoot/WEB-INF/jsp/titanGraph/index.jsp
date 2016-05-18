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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>数据魔方</title>
    <base href="<%=basePath %>">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta name="renderer" content="webkit">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	 <!-- CSS Bootstrap & Custom -->
    <link href="styles/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="styles/css/font-awesome.min.css" rel="stylesheet" type="text/css">  
    <link href="styles/flexslider.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="styles/css/templatemo_style.css" rel="stylesheet" type="text/css">
    <link href="styles/css/base.css" rel="stylesheet" type="text/css">
    <link href="styles/css/index.css" rel="stylesheet" type="text/css">
    <link href="styles/css/style.css" rel="stylesheet" type="text/css">
    <link href="styles/css/customCss.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/styles/css/menuStyle.css" />
    <!--9月15日添加的样式  -->
    <link href="styles/css/themes/default/easyui.css" rel="stylesheet" type="text/css">
    <link href="styles/css/themes/icon.css" rel="stylesheet" type="text/css">
    <link href="styles/css/demo.css" rel="stylesheet" type="text/css">
    <link href="styles/css/tabEmail.css" rel="stylesheet" type="text/css">
    
    <!--9月15日添加的样式结束  -->
    <script src="scripts/modernizr.js"></script>
    <script type="text/javascript" src="<%=basePath%>/scripts/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.wheelmenu.js"></script>
	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.subWheelmenu.js"></script>
	<script type="text/javascript" src="<%=basePath%>/scripts/jquery.toolWheelmenu.js"></script>
   <!--9月16日添加样式-开始  -->
    <link href="styles/css/emailDialog/jquery.tagsinput.css" rel="stylesheet" type="text/css">
    <link href="styles/css/emailDialog/jquery-ui.css" rel="stylesheet" type="text/css">
    <link href="styles/css/emailDialog/tccEmail.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="scripts/laydate-v1.1/laydate/laydate.js"></script>
    <script type="text/javascript" src="<%=basePath%>/scripts/cytoscape.js"></script>
    <script type="text/javascript" src="<%=basePath%>/scripts/layout.js"></script>
    <script type="text/javascript" src="<%=basePath%>/scripts/timeline/init.js"></script>
    <script type="text/javascript" src="<%=basePath%>/scripts/timeline/Resize.js"></script>
    <script type="text/javascript" src="<%=basePath%>/scripts/timeline/Drag.js"></script>
    <script type="text/javascript" src="<%=basePath%>/scripts/saveSvgAsPng.js"></script>
    <script type="text/javascript" src="scripts/grid.locale-cn.js"></script>
	<script type="text/javascript" src="scripts/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="scripts/ajaxsetup.js"></script>
    
    <!--9月16日添加样式-结束  -->
    <!-- add by hanxue start 数据导出 -->
    <link rel="stylesheet" type="text/css" href="styles/css/indexImportDialag.css" />
    <!-- add by hanxue end -->
    <style type="text/css">
		html{height:100%}
		body{height:100%;margin:0;padding:0;font-size:15px;}
		.div-relative{position:relative; height:100%; width:100%} 
		.div-a{ position:absolute; right:100px; bottom:30px;width:40px; height:130px} 
		.olControlButtonItemActive {
         position: absolute;
         cursor: pointer; 
         top: 5px;
         right: 0px;
         background-image: url("images/map/back.png");
         width: 18px;
         height: 18px;
         -moz-user-select:none;  
           }
        .olControlPanel {
	        width: 25px;
	        height: 25px;
	        top: 5px;
	        right: 0px;
          }
		#timeline_Detail {height: auto;position: relative;background: url('images/timeline/dot.gif') left 0px top 6px repeat-x;;}
		#timeline_dates {height: 30px;overflow: hidden;margin-left: 0px;}
		#timeline_dates li {float: left;height: 10px;text-align: center;background: url('images/timeline/biggerdot.png') center top 8px no-repeat;
				  padding-top: 10px;
				}
		#timeline_dates a {line-height: 20px;}
		#timeline_rRight,#timeline_rLeft{
			position:absolute;
			background:#C00;
			width:1px;
			height:100%;
			z-index:5;
			cursor:e-resize;
		}
		#timeline_rRight{right:-1px;}
		#timeline_rLeft{left:-1px;}
		#timeline_bgDiv{height:60px;position:relative;background-color: rgba(0, 0, 0, 0);border: 1px solid #2548a7;}
		#timeline_dragDiv{width:100px; height:60px; background:rgba(20, 42, 101, .7);;position: absolute; left: 1px; top: 0px;}
		.layout{
			float: left;
			margin: 0 5px;
			cursor: pointer;
		}
    </style>
  </head>
  
  
  
  
  
 <body onselectstart="return false">
 	<div class="mask" id="mask">
 		<div class="maskDetail"></div>
 	</div>
 	<div id="box" class="box">
  		<div class="blackBg"></div>
  		<div class="box1">
  			<h1>保存历史记录</h1>
			<form name="history" id="history" action="" method="post">
				<span><label style="">记录名称</label><input type="text" id="historyName" name="historyName"/><br><br></span>
				<span>
					<input type="button" class="xgBtn" onclick="doSubmit()" value="确定" />
					<input type="button" class="closeBtn"  onclick="closeWin()" value="关闭" />
				</span>
			</form>
		</div>
	</div>
    <div class="topNavbar clearfix" >
            <a href="#" class="navbar-brand" style='padding:18px 0px 0px 50px'><img src="images/img/DataSmart-logo.png"></a>
  
                                  
            <span class="dataSpan"><em>|</em>数据分析</span>
            
    
                   
         <div class="searchbar clearfix" >          
           <div class='property_div' >
           	 <!-- <select name="property" id="propertySel">   
		        <option value="name" selected>姓名</option>   
		        <option value="email">邮箱</option> 
		        <option value="phonenum">电话</option>   
		        <option value="idcard">身份证</option>   
		      </select> -->
		      	 <span class="select_txt" name="name">姓名</span><a class="selet_open"></a>
		         <div class="option">
		            <span class="name" onclick="document.getElementById('content').disabled='';document.getElementById('content').value='';">姓名</span>
		            <span class="email" onclick="document.getElementById('content').disabled='';document.getElementById('content').value='';">邮箱</span>
		            <span class="phonenum" onclick="document.getElementById('content').disabled='';document.getElementById('content').value='';">电话</span>
		            <span class="numid" onclick="document.getElementById('content').disabled='';document.getElementById('content').value='';">IM</span>
		            <span class="idcard" onclick="document.getElementById('content').disabled='';document.getElementById('content').value='';">身份证</span>
		            <span class="username" onclick="document.getElementById('content').disabled='';document.getElementById('content').value='';">用户名</span>
		            <span class="password" onclick="document.getElementById('content').disabled='';document.getElementById('content').value='';">密码</span>
		            <span class="resume" onclick="document.getElementById('content').disabled='';document.getElementById('content').value='';">简历内容</span>
		            <span class="emailAll" onclick="advancedSearch()">邮件查询</span>
		         </div>   
           </div>
           <div class='search_div' >
            	<input class='search_input'  type='text' id='content' name='content' placeholder=' 搜索内容'/>
            	<button  type="button" value='' onclick='searchContent("")'><img src="images/search/searchBtn.png"></button>
	            <div class="hideDiv">
	            </div>
           </div>
            <span id="advancedsearch">高级搜索</span>
        </div>
         <div class="personal fr">
             <ul>
              	<li  class="topIcon5"><a href="javascript:void(0)" class="userlistA" onclick="openPwdTask()">破解任务</a></li> 
                <c:if test="${roleType==1}">
               		<li><a href="getAllUser.action">用户列表页</a></li>
                </c:if>
                <!-- add by hanxue start 导出 -->
				<!-- <li  class="topIcon4" style="margin-top:6px;"><a href="javascript:exportView()" class="userlistA">导出</a></li> -->
				<!-- add by hanxue end -->
             </ul>
             
       </div>
    </div>
<!-- 菜单 -->
<!--9月15日新改  -->
	<div id="tt" class="easyui-tabs" data-options="tools:'#tab-tools'">
		<div  title="首页" style="padding:10px 0px;overflow:hidden;">
			<div class="icontainer clearfix" >
        <div id="menuDiv" style="visibility: hidden;position:absolute;z-index:2"></div>
        <div class='left_top' >
          <div class="mapDiv" style='display:none;width:100%; height:100%;background: #f1efe9;'>
				<table width='100%'  border="0" cellpadding="0" cellspacing="2" height='100%'>
					<tr>
						<td  style="width:100%;height:100%;">
							<div id="map"  class="div-relative"></div>
						</td>
					</tr>
				</table>
			</div>
          <!-- 可视化展示-->
          <div class='zoomline' style='display:none;width:30px; height:150px;position: absolute; top:70px;left: -5px;'></div>
          <div class='zoomline' id="layout" style='display:none;width:10%; height:25px;position: absolute; top:100px;left: 45px;'>
          	<div class="layout" id="layoutBreadthfirst" title="广度布局">
          	  <img src="images/layout/breadthfirst.png" style="height:22px;width:22px;"/>
            </div>
            <div class="layout" id="layoutCose" title="网格布局">
              <img src="images/layout/cose.png"  style="height:22px;width:22px;"/>
            </div>
            <div class="layout" id="layoutRandom" title="随机布局">
              <img src="images/layout/random.png"  style="height:22px;width:22px;"/>
            </div>
            <div class="layout" id="layoutCircle" title="环形布局">
              <img src="images/layout/circle.png"  style="height:22px;width:22px;"/>
            </div>
            <div class="layout" id="layoutGrid" title="盒子布局">
              <img src="images/layout/grid.png"  style="height:22px;width:22px;"/>
            </div>
            <div class="layout" id="layoutConcentric" title="同心布局">
              <img src="images/layout/concentric.png"  style="height:22px;width:22px;"/>
            </div>
          </div>
          <div class='displayDiv' style='display:none;width:100%; height:100%;' id="svgImg"></div>
		</div>
			<!--<div class="left_down_shrink" style="float: left;width: 76%;border: 6px solid red;position: relative;">
			  <div id="timelineShrink" style="position: absolute;left: 50%;top: -6px;width: 0;height: 0;border-left: 20px solid transparent;border-right: 20px solid transparent;border-top: 12px solid black;"></div>
			</div>-->
			<!-- 时间轴 -->
			<div class='left_down' > 
			 	<div class="bar_div"  id="main"></div>
			</div>
			<div class='right clearfix' >
				<div class="rightCon">
		    	<div class="tabBox clearfix">
		        	<ul class="clearfix">
		            	<li class="first"><a href="javascript:void(0)" class="setOn" title="直方图"> </a></li>
		                <li class="second"><a href="javascript:void(0)" title="浏览器"></a></li>
		                <li class="third"><a href="javascript:void(0)" title="过滤器"></a></li>
		                <li class="last"> <a href="javascript:void(0)" title="历史查询记录"></a></li>
		            </ul>       
		        </div>
		   	 	<div class="tabCon" style="overflow-y: none;">
		        	<div class="firstCon">
		              <div class="clsHead clearfix">
		                <h3>对象类型</h3>
		                <span><img src="images/img/1.png" width="15" height="9" /></span>
		              </div>
		              <div class="clsContent clearfix">
		              	<p class="pTit"><span>当前对象</span><em id="allObj">（0）</em></p>
		                <ul id="allTypeList">
			                <li class="clearfix">
			                	<img style="float: left;" src="images/histograms/Person.png" onerror="javascript:this.src='images/histograms/default.png'"/>
			                	<i id="allPerson">0</i>
			                	<div class="bar fl">
				                	<div class="leftBar fl"></div>
				                	<div class="rightBar fr"></div>
			                	</div>
			                </li>
			                <li class="clearfix">
			                	<img style="float: left;" src="images/histograms/CallEvent.png"/>
			                	<i id="allEvent">0</i>
		                		<div class="bar fl">
		                			<div class="leftBar fl"></div>
		                			<div class="rightBar fr"></div>
	               				</div>
	            			</li>
		                </ul>
		              </div>
		            </div>
		          	<div class="secondCon">
		            	<div class="clsHead1 clearfix">
		                <h3>实体属性</h3>
		                <span><img src="images/img/1.png" width="15" height="9" /></span>
		              </div>
		              <div class="histogramDiv clsContent1 clearfix" style="height: 70%;"></div>
		            </div>
		        </div>
		    	<div class="browserDiv tabCon hiddenCon clsContent2">
		    		<div class="maincon clearfix" style="display: none">
						<div class="chjBox">
							<div class="serListBox" >
								<ul class="serTit clearfix">
									<li class="li2"><a class="btnDel" href="javascript:void(0);">删除</a></li> 
									<li class="li3"><a href="javascript:void(0);" class="checkInvert time" id="checkAll">全选</a></li>     
									<li class="li3"><a href="javascript:void(0);" class="checkInvert time" id="inverseCheck">反选</a></li>    
								</ul>
								<div class="serList">
					
								</div>
							</div>
						</div>
					</div>
		    		<div class="personPage" style="dispaly:none"></div>
		    	</div>
		    	<div class="tabCon hiddenCon1">
		    		<div class="firstCon">
		              <div class="clsHead_filter clearfix">
		                <h3>过滤器</h3>
		                <span><img src="images/img/1.png" width="15" height="9" /></span>
		              </div>
		              <div class="clsContent_filter clearfix">
		              	<div class="pTit" style="margin-top: 20px;">
			              	<div class="commStyle clearfix">
				    			<span>类型</span>
				    			<select class="filterType" onchange="changeProperty(this.value)">
				    				<c:forEach items="${vertexType}" var="type" >
				    					<option value="${type}">${type}</option>
				    				</c:forEach>
				    			</select>
				    		</div>
				    		<div class="commStyle clearfix">
				    			<span>属性</span>
				    			<select class="filterProperty">
				    				<c:forEach items="${vertexProperty.Person}" var="property" >
				    					<option value="${property}">${property}</option>
				    				</c:forEach>
				    			</select>
				    		</div>
				    		<div class="commStyle clearfix">
				    			<span>内容</span>
				    			<input type="text" class="inputBg filterContent" />
				    		</div>
				    		<div class="buttonDiv">
								<a href="javascript:void(0);" class="check" style="width:70px;" onclick="filterGraph('check')">过滤</a>
								<a href="javascript:void(0);" class="checkInvert" style="width:70px;" onclick="filterGraph('checkInvert')">反选</a>
							</div>
						</div>
		              </div>
		            </div>
		            <div class="secondCon">
	            		<div class="clsHead_filter_timeline clearfix">
			                <h3>时间轴</h3>
			                <span><img src="images/img/1.png" width="15" height="9" /></span>
		             	</div>
		              	<div class="clsContent_filter_timeline clearfix">
			              	<div class="pTit" style="margin-top: 20px;">
				    			<div id="timeline_bgDiv">
					                <div id="timeline_histograms"></div>
					                <div id="timeline_dragDiv">
					                  <div id="timeline_rRight"></div>
					                  <div id="timeline_rLeft"></div>
					                </div>
					            </div>
					            <div id="timeline_Detail">
					                <ul id="timeline_dates"></ul>
					            </div>
					    		<div class="buttonDiv">
									<a href="javascript:void(0);" class="check" style="width:45%;" id="addTimeDataToLine">添加时间数据到时间轴</a>
					    		</div>
			              	</div>
		              	</div>
		            </div>
		    	</div>
		    	<div class="tabCon hiddenCon" style="overflow-y: hidden;">
		    		<div class="hisGraphBtn">
		    			<input type="button" class="gobackBtn" onclick="setHistoryGraph()" value="回滚"/>
		    			<input type="button" class="gobackBtn" onclick="showDetailImage()" value="详情"/>
		    			<input type="button" class="gobackBtn" onclick="delHisGraph()" value="删除"/>
		    		</div>
		    		<div class="table" id="hisGraphList"></div>
		    		<div class="moreBtn" onclick="loadHisGraph()">更多</div>
					<div id="hisGraphImage" class="box">
				  		<div class="blackBg"></div>
				  		<div class="box1" style="width: 45%;height: 35%;">
							<img id="hisGraphDetailImage" src="" />
							<div id="hisGraphDetail"></div>
							<span>
								<input type="button" class="xgBtn" onclick="setHistoryGraph()" value="回滚" />
								<input type="button" class="closeBtn"  onclick="closeHisGraphWin()" value="关闭" />
							</span>
						</div>
					</div>
		    	</div>
		    </div>
			</div>
			<!--<div class="right_shrink" style="float: right;width: 2px;border: 6px solid red;height: 100%;position: relative;top: -540px;right: 24%;">
			  <div id="propertyShrink" style="position: absolute;left: -6px;top: 40%;width: 0;height: 0;border-top: 20px solid transparent;border-left: 12px solid black;border-bottom: 20px solid transparent;"></div>
			</div>-->
      </div>	
		</div>
	</div>
	<div id="tab-tools">
<!-- 		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="addPanel()"></a> -->
		<!--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="removePanel()"></a>-->
	</div>
	<!--9月15日结束  -->
<!--   邮件弹出层 -->
  <div class="tccBox">
    	<div class="bg"></div>
        <div class="emailCon" style="z-index:1000;">
        	<b class="closeB"></b>
            <div class="barBox" id="barBox">
         
            </div>
        </div>
    </div>
     <!--邮件高级查询页面 --开始 -->
    	<div class="searchtccBox" id="emailsearch">
    		<div class="bg"></div>
        	<div class="gjsearchCon" style="z-index:1000;">
        		<h3>邮件高级查询管理</h3>
        		<b class="closeB" onclick="$('.searchtccBox').hide()"></b>
           		<div class="gjsearchBox">          			
           			<ul class="clearfix">
           				<li class="gjsearchLi">
           					<span>全文：</span>
           					<input type="text"  id="allTextOfSearch" />
           				</li>
           				<li class="gjsearchLi">
           					<span>主题：</span>
           					<input type="text"  id="titleOfSearch" />
           				</li>
           				<li class="gjsearchLi">
           					<span>收件人/发件人：</span>
           					<input type="text"  id="mailAddressOfSearch" />
           				</li>
           				<li class="gjsearchLi">
           					<span>邮件正文：</span>
           					<input type="text"  id="contectOfSearch" />
           				</li>
           				<li class="gjsearchLi">
           					<span>附件文件名：</span>
           					<input type="text" id="filenameOfSearch"/>
           				</li>
           				<li class="gjsearchLi">
           					<span>附件内容：</span>
           					<input type="text" id="filecontectOfSearch"/>
           				</li>
           				<li class="gjsearchLi">
           					<span>开始日期：</span>
           					<input onclick="laydate(from).skin('yahui')" placeholder="请输入日期" class="laydate-icon" id="datefromSearch" height="50px" />
           				</li>
           				<li class="gjsearchLi">
           					<span>结束日期：</span>
           					<input  onclick="laydate(to).skin('yahui')" placeholder="请输入日期" class="laydate-icon" id="datetoSearch"/>
           				</li>
           				<li class="gjsearchLi">
           					<span>热词：</span>
           					<input type="text"  id="hotwordOfSearch"/>
           				</li>
           				<li class="gjsearchLi">
           					<span>标签：</span>
           					<input type="text" id="tagOfSearch"/>
           				</li>
           				<li class="lastli">
           					<span class="lastliSpan">文件夹：</span>
           					<!--<input type="text" id="folderOfSearch"/>  -->
           					<div class="catalogDiv">
           						<ul id="treeDemo" class="ztree"></ul>
           					</div>
           				</li>
           			</ul>
				         
           			<div class="gjsearchbtnBox">
           				<a href="javascript:void(0)" onclick="submitAdvanceSearch()" class="qdBtn">查询</a>
           				<a href="javascript:void(0)" onclick="clearSearchText()" class="qxBtn">清空</a>
           				<a href="javascript:void(0)" onclick="$('.searchtccBox').hide()"  class="qxBtn">取消</a>
           			</div>
           		</div>
           	</div>
    	</div>
    <!--邮件高级查询页面 --结束 -->
    
     <!--高级查询弹出输入查询条件页面 --开始5月9日李富生添加 -->
    <div class="searchtccBox"  id="advancesearch">
    		<div class="bg"></div>
        	<div class="gjsearchCon" style="z-index:1000;">
        		<h3>高级查询</h3>
        		<b class="closeB" onclick="$('.searchtccBox').hide()"></b>
           		<div class="gjsearchBox clearfix">
           			<div class="nodenameDiv">
           	            <span class="nodeName">查询节点名称</span>
           		 		<select name="queryable" id="queryable">   
                          </select>      
                     </div> 
                     <div class="clear"></div>			
           			<ul class="clearfix"  id="point">   
           			</ul>   
           			<div class="gjsearchbtnBox">
           				<a href="javascript:void(0)" id="query" class="qdBtn">查询</a>
           				<a href="javascript:void(0)"  id="clear" class="qxBtn">清空</a>
           				<a href="javascript:void(0)" onclick="$('.searchtccBox').hide()"  class="qxBtn">取消</a>
           			</div>
           		</div>
           	</div>
           	
           	
    	</div>
     <!--高级查询页面 --结束5月9日添加 -->
     
      <!--高级查询弹出查询内容列表显示页面 --开始2016年5月9日李富生添加 -->
    <div class="searchtccBox"  id="tableshow">
    		 <div class="bg"></div> 
    		<div class="gjsearchCon" style="z-index:1000" id="tableshowall1">
        	<h3>查询结果显示</h3>
            <b class="closeB" onclick="$('.searchtccBox').hide()">  
     	     <div class="gjsearchCon" style="z-index:500"   id="tableshowall" ></div>  		    			
            </div>
     	</div>
     <!--高级查询页面 --结束2016年5月9日添加 -->
           	

   
     
  <div class='hintMsg'></div>
   <!--1月14日加首页右侧历史记录弹出层-->
	<div class="historytccBox">
		<div class="bg"></div>
		<div class="historyBox">
			<div class="historyConBox">
				<h3>添加案件</h3>
				<b></b>
				<iframe src=""  id="pop_iframe" name="fc" frameborder="0" width="100%" height="95%"></iframe>						
			</div>
		</div>
	</div>
	<!-- add by hanxue start 导出 -->
	<div id="fullbg"></div>
	<div id="exportingDiv">
		<div class="dataLoad">
			<p>正在导出数据...</p>
			<br /> <img alt="Loading" src="<%=basePath%>images/img/4.gif">
			<iframe frameborder="0" width="0" height="0" id="downloadFrame"
				src=""></iframe>
		</div>
	</div>
	<div id="pwdTaskDiv">
		<div>
			<iframe frameborder="0" width="840" height="490" id="pwdTaskFrame"></iframe>
		</div>
	</div>
	<!-- add by hanxue end -->
     <div id="menuHist" class="easyui-menu" style="width:315px;">
       <div data-options="name:'deleteSelObjects'">删除选中节点</div>
       <div data-options="name:'deleteUnSelObjects',iconCls:'icon-save'">删除未选中节点</div>
       <div class="menu-sep"></div>
       <div data-options="name:'copyToClipboard'" id="clip_button" style="width:100px;height:100px;">复制文字到剪切板</div>
     </div>
     <span id="clip_container"></span>
  <!-- JavaScripts -->
  <!--9月15日添加的js  -->
	<script type="text/javascript" src="scripts/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="scripts/tabEmail.js"></script>
    <!--9月15日添加的js  -->
  <script type="text/javascript" src='scripts/d3.v3.js' ></script> 
  <script type="text/javascript" src='scripts/d3-tip.js' ></script>
  <script type="text/javascript" src="scripts/customJs.js"></script>
  <script type="text/javascript" src="scripts/jquery.wheelmenu.js"></script>
    <!--11月11日添加的js--开始  -->
  <script type="text/javascript" src="scripts/index/qq.js"></script>
  <!--11月11日添加的js--结束  -->
  <script type="text/javascript" src="scripts/graph_tabsHandler.js"></script>
  <script src="styles/bootstrap/js/bootstrap.min.js"></script>
  <script src="styles/bootstrap/js/bootstrap-prompts-alert.js"></script>
  <script src="scripts/jquery.singlePageNav.js"></script>
<!--   <script src="scripts/templatemo_custom.js"></script> -->
  <script defer src="scripts/jquery.flexslider.js"></script>
  <script type="text/javascript" src="scripts/echarts.js"></script>
  <script type="text/javascript" src="scripts/tab.js"></script> 
  <script type="text/javascript" src="scripts/bar_echartsBar.js"></script> 
  <script type="text/javascript" src="scripts/OpenLayers.js"></script> 
  <!-- /*点击邮件出现的弹出层*/ -->
  <!--9月16日添加的js--开始  -->
  <script type="text/javascript" src="scripts/emailDialog/jquery.tagsinput.js"></script>
	<!-- modify by hanxue 数据导出start -->
	<script type="text/javascript" src="scripts/ui/jquery-ui.min.js"></script>
	<!-- <script type="text/javascript" src="scripts/emailDialog/jquery-ui.min.js"></script> -->
	<!-- modify by hanxue 数据导出end -->
  <script type="text/javascript" src="scripts/emailDialog/szzlCommPlug.js"></script>
  <script type="text/javascript" src="scripts/emailDialog/easing.js"></script>
  <script type="text/javascript" src="scripts/emailDialog/imgScroll.js"></script>
  <!--9月16日添加的js--结束  -->
  <script type="text/javascript" src="scripts/email.js"></script>
	<script type="text/javascript" src="scripts/emailDialog/easing.js"></script>
  <script type="text/javascript" src="scripts/emailDialog/imgScroll.js"></script>
  <!--9月21日添加的js--开始  -->
  <script type="text/javascript" src="scripts/index/advanceSearch.js"></script>
  <script type="text/javascript" src="scripts/index/search.js"></script>
  <script type="text/javascript" src="styles/zTree/js/jquery.ztree.core-3.5.js"></script>
  <script type="text/javascript" src="styles/zTree/js/jquery.ztree.excheck-3.5.js"></script>
  <link href="styles/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css">
  <!-- jqgrid -->
  <link rel="stylesheet" type="text/css" href="styles/css/ui.jqgrid.css">
  <link rel="stylesheet" type="text/css" href="styles/css/jquery-ui.min.css">
  <link rel="stylesheet" type="text/css" href="styles/css/customCss.css">
  <!--9月21日添加的js--结束  -->
  <!--add by hanxue 数据导出start-->
	<script type="text/javascript" src="scripts/index/saveSvgAsPng.js"></script>
	<!--add by hanxue 数据导出end-->
	<script type="text/javascript" src="scripts/ZeroClipboard.js"></script>
<!--16年1月14日添加历史记录js-->
<script type="text/javascript" src="scripts/history.js"></script> 

<!--16年1月14日lifusheng添加动态增加节点属性js-->
<script type="text/javascript" src="scripts/advancedsearch/dynamicquery.js"></script> 

  <script type="text/javascript">
  		
  		/*$(".left_down").css("height").replace("px", "")
  		$("#timelineShrink").click(function(){
  			var _this = this;
  			var state = $(".left_down").css("display");
  			if(state == "none"){
  				$(".left_down").show();
	  			$(".left_top").css("height", "70%");
	  			$(_this).css("border-top", "12px solid black");
	  			$(_this).css("border-bottom", "0px");
	  			$(".right_shrink").css("top", "-540px");
	  			tozoomline.attr("visibility","show");
				allbtn.attr("visibility","show");
				tonomline.attr("visibility","hidden");
				nombtn.attr("visibility","hidden");
				svg.attr("height",graphHeight);
				isall=false;
  			}else if(state == "block"){
	  			$(".left_down").hide();
	  			$(".left_top").css("height", "100%");
	  			$(_this).css("border-top", "0px");
	  			$(_this).css("border-bottom", "12px solid black");
	  			$(".right_shrink").css("top", "-777px");
	  			tozoomline.attr("visibility","hidden");
				allbtn.attr("visibility","hidden");
				tonomline.attr("visibility","show");
				nombtn.attr("visibility","show");
				svg.attr("height",scheight-150);
				isall=true;
  			}
  		});
  		$("#propertyShrink").click(function(){
  			var _this = this;
  			var state = $(".right.clearfix").css("display");
  			if(state == "none"){
  				$(".right.clearfix").show();
	  			$(".left_top").css("width", "75%");
	  			$(".right_shrink").css("right", "24%");
	  			$(_this).css("border-left", "12px solid black");
	  			$(_this).css("border-right", "0px");
	  			$(".left_down_shrink").css("width", "76%");
	  			$(".left_down").css("width", "74%");
	  			tozoomline.attr("visibility","show");
				allbtn.attr("visibility","show");
				tonomline.attr("visibility","hidden");
				nombtn.attr("visibility","hidden");
				svg.attr("width",graphWidth);
				isall=false;
  			}else if(state == "block"){
	  			$(".right.clearfix").hide();
	  			$(".left_top").css("width", "100%");
	  			$(".right_shrink").css("right", "1%");
	  			$(_this).css("border-left", "0px");
	  			$(_this).css("border-right", "12px solid black");
	  			$(".left_down_shrink").css("width", "99%");
	  			$(".left_down").css("width", "96%");
	  			tozoomline.attr("visibility","hidden");
				allbtn.attr("visibility","hidden");
				tonomline.attr("visibility","show");
				nombtn.attr("visibility","show");
				svg.attr("width",scwidth);
				isall=true;
  			}
  		});*/
  		var click=0;
  		var forceWhich = 1;//add by hanxue用于禁止按住右键时拖拽节点
  		var showFormatData = {
			"name" : {
				colNames : "版本名称",
				width: "10%",
				formatter: function(cellvalue){
					return cellvalue;
				}
			},
			"time" : {
				colNames : "时间",
				width: "14%",
				formatter: function(cellvalue){
					return cellvalue.replace("T", " ");
				} 
			}
		};
	var hisGraphCurrentClickID = "";
	var loadCompleteData = new Array();
	var hisGraphData = {
		pageSize: 10,
  		pageNo: 1
	};
	
	function loadHisGraph(){
		$.ajax({
			url:"${base}/ajaxGraphHis/findGraphHistoryList.action",
			type:"POST",
			data:hisGraphData,
			dataType:"json",
			success:function(data) {
				if(data.pageModel.list.length <= 0){
					return false;
				}
				var hisGraphList = "";
				if(hisGraphData.pageNo == 1){
					hisGraphList = $('<div class="box1"></div>')
				}else{
					hisGraphList = $('<div class="box1" style="background-color:rgba(0,0,0,0);"></div>')
				}
				$("#hisGraphList").append(hisGraphList);
				var hisGraphListDetail = $('<div style="float: left;width: 100%;" extend="'+data.pageModel.list[0].id+'">' +
												'<img style="float:left;height: 50px;width:25%;margin: 2% 1% 2% 3%;" src="'+data.pageModel.list[0].image+'" />' +
												'<div class="boxrightCon">' +
													'<div style="padding: 2%;">' +
														'<div class="namediv">名称：'+data.pageModel.list[0].name+'</div>' +
														'<div class="timediv">时间：'+data.pageModel.list[0].time.replace("T", " ")+'</div>' +
													'</div>' +
												'</div>' +
											'</div>');
				hisGraphListDetail.click(function(){
					var _this = this;
					var children = $(_this).parent().parent().children();
					for(var i = 0; i < children.length; i ++){
						$(children[i]).css("background-color", "rgba(0,0,0,0)");
					}
					$(_this).parent().css("background-color", "#08225e");
					hisGraphCurrentClickID = $(_this).attr("extend");
				});
				hisGraphList.append(hisGraphListDetail);
				hisGraphCurrentClickID = data.pageModel.list[0].id;
				loadCompleteData.push(data.pageModel.list[0]);
				for(var i = 1; i < data.pageModel.list.length; i ++){
					var hisGraphList = $('<div class="box1"style="background-color:rgba(0,0,0,0);"></div>')
					$("#hisGraphList").append(hisGraphList);
					var hisGraphListDetail = $('<div style="float: left;width: 100%;" extend="'+data.pageModel.list[i].id+'">' +
													'<img style="float:left;height: 50px;width:25%;margin: 2% 1% 2% 3%;" src="'+data.pageModel.list[i].image+'" />' +
													'<div class="boxrightCon">' +
														'<div style="padding: 2%;">' +
															'<div class="hisGraphLabel">名称：'+data.pageModel.list[i].name+'</div>' +
															'<div class="hisGraphLabel">时间：'+data.pageModel.list[i].time.replace("T", " ")+'</div>' +
														'</div>' +
													'</div>' +
												'</div>');
					hisGraphListDetail.click(function(){
						var _this = this;
						var children = $(_this).parent().parent().children();
						for(var i = 0; i < children.length; i ++){
							$(children[i]).css("background-color", "rgba(0,0,0,0)");
						}
						$(_this).parent().css("background-color", "#08225e");
						hisGraphCurrentClickID = $(_this).attr("extend");
					});
					hisGraphList.append(hisGraphListDetail);
					loadCompleteData.push(data.pageModel.list[i]);
				}
				hisGraphData.pageNo ++;
			},
			complete:function(){
				console.log("FilterSave request completed!");
			},
			error:function(){
				console.log("FilterSave request error!");
			}
		});
	}
	$(function(){
		$("#hisGraphList").empty();
		loadHisGraph();
		$("#content").focus();
		
	});
	
	function showDetailImage(){
		if(hisGraphCurrentClickID == ""){
			hintManager.showHint("请选择历史版本!");
			return false;
		}
		$("#hisGraphDetail").empty();
		for(var i = 0; i < loadCompleteData.length; i ++){
			if(loadCompleteData[i].id == hisGraphCurrentClickID){
				$("#hisGraphDetailImage").attr("src", loadCompleteData[i].image);
				for(var j in loadCompleteData[i]){
					if(showFormatData[j])
						$("#hisGraphDetail").append("<div style=\"padding: 4%;\">" +
												"<span>"+showFormatData[j].colNames+" ： </span>" +
												"<span>"+loadCompleteData[i][j]+"</span>" +
											"</div>");
				}
				$("#hisGraphImage").show();
			}
		}
	}
	
	function delHisGraph(){
		if(hisGraphCurrentClickID == ""){
			hintManager.showHint("请选择历史版本!");
			return false;
		}
		confirm("是否确认删除？",function(){
  	  		$.ajax({
				url:"${base}/ajaxGraphHis/deleteGraphHistory.action?id="+hisGraphCurrentClickID,
				dataType:"json",
				success:function(data) {
					var children = $("#hisGraphList").children();
					for(var i = 0; i < children.length; i ++){
						var extend = $($(children[i]).children()[0]).attr("extend");
						if(extend == hisGraphCurrentClickID){
							$(children[i]).remove();
						}
					}
					children = $("#hisGraphList").children();
					if(children.length == 0){
						hisGraphData = {
					  		pageSize: 10,
			  				pageNo: 1
					  	};
						loadHisGraph();
					}else{
						$($("#hisGraphList").children()[0]).css("background-color", "#08225e");
						hisGraphCurrentClickID = $($($("#hisGraphList").children()[0]).children()[0]).attr("extend");
					}
				},
				complete:function(){
					console.log("FilterSave request completed!");
				},
				error:function(){
					console.log("FilterSave request error!");
				}
			});
  	  	});
	}
		
	function setHistoryGraph(){
		if(hisGraphCurrentClickID == ""){
			hintManager.showHint("请选择历史版本!");
			return false;
		}
		$(".displayDiv").css("display", "block");
		$(".zoomline").css("display", "block");
		for(var i = 0; i < loadCompleteData.length; i ++){
			if(loadCompleteData[i].id == hisGraphCurrentClickID){
				pageVariate.fristFlag = false;
				var data = {
					nodes : [],
					edges : [] 
				};
				var relation = {};
				if(loadCompleteData[i].nodes){
					data.nodes = JSON.parse(loadCompleteData[i].nodes);
					for(var x = 0; x < data.nodes.length; x ++){
						var newNodeIndex = graphOperation.findNodeIndexById(data.nodes[x].id);
						if(newNodeIndex != -1){
							graphOperation.removeNode(nodes[newNodeIndex].uuid);
							pageVariate.tempDeleteId.push(data.nodes[x].id);
						}
					}
				}
				graphOperation.updateGraphAndCache();
				if(loadCompleteData[i].edges){
					data.edges = JSON.parse(loadCompleteData[i].edges);
				}
				if(loadCompleteData[i].edges){
					relation = JSON.parse(loadCompleteData[i].relation);
					for(var m in relation){
						var currentRelation = pageVariate.openMap.get(m);
						if(!currentRelation)
							currentRelation = new Array();
						for(var item = 0; item < relation[m].length; item ++){
							if(currentRelation.indexOf(relation[m][item]) < 0){
								currentRelation.push(relation[m][item]);
							}
						}
						pageVariate.openMap.put(m, currentRelation);
					}
				}
				var InitData = {
					nodes : nodes,
					edges : edges
				}
				graphDrawInit(InitData);
				if(data.nodes.length > 0){
					expandNode(data.nodes, data.edges);
					//TODO
					// 1、点需要根据ID判断是否已经存在
					var ids = new Array();
					for(var x = 0; x < data.nodes.length; x ++){
						ids.push(data.nodes[x].id)
					}
					$.ajax({
						type : "post",
						async: false,
						dataType : "json",
						url : pageVariate.base+"ajaxGraphHis/synchronousData.action",
						data : {
							ids:ids.toString()
						},
						dataType : "json",
						success : function() {
							console.log("同步数据："+data);
						}
					});
				}
			}
		}
		$("#hisGraphImage").hide();
	}
		
	function closeHisGraphWin(){
		$("#hisGraphImage").hide();
	}
		
  
  function closeWin(){
  	$("#box").hide();
  }
  function doSubmit(){
  		svgAsPngUri(document.getElementById("displaySvg"), {}, function(uri) {
  			var relation = {};
  			for(var i = 0; i < nodes.length; i ++){
  				if(nodes[i].type == "Person"){
					var result = pageVariate.openMap.get(nodes[i].uuid);
					if(result && result.length != 0){
						relation[nodes[i].uuid] = result;
					}
  				}
  			}
  			var historyName = $("#historyName").val();
		  	var myJsonSData = {
			  "nodes": JSON.stringify(nodes),
			  "edges": JSON.stringify(edges),
			  "relation": JSON.stringify(relation),
			  "image": uri,
			  "name" : historyName
			};
			if(!historyName){
				alert("请输入要保存版本的名字");
				return false;
			}
			if(myJsonSData.nodes == "[]"){
				alert("拓扑图上没有节点，无法保存历史版本");
				$("#box").hide();
				return false;
			}
			
			$.ajax({
			  url:"${base}ajaxGraphHis/saveGraphHistory.action",
			  type:"POST",
			  dataType:"json",
			  async: false,      //ajax同步
			  data:myJsonSData,
			  success:function(data) {
			  	$("#box").hide();
			  	$("#hisGraphList").empty();
			  	hisGraphData = {
			  		pageSize: 10,
			  		pageNo: 1
			  	};
			  	loadHisGraph();
			  	$('.last a').click();
			  },
			  complete:function(){
			    console.log("FilterSave request completed!");
			  },
			  error:function(){
			    console.log("FilterSave request error!");
			  }
			});
		});
  	}
  
	  $("#addHisGraph").click(function(){
	  	$("#box").show();
	  	$("#historyName").focus();
	  });
	  $("#exportView").click(function(){
		  exportView();
	  });
  var qqgroupcount=0;
//    $(function(){
// 	   $('#tt').tabs('add',{
// 			title: 'QQ',
// 			closable: true,
// 			content: "<div class='zoomQQline0' style='width:10px;z-index:999; height:10px;position: absolute; top:100px;left: 15px margin-top:-120px;  float:left;border:0px solid #F00'></div><div class='displayQQDiv0' style='width:100%; height:100%;'><div>"
			
// 		});
// 	   getQQgroup("","",0);}
//    );

var getRepeatElementTotal = function (array) {
    var count = 1;
    var sum = new Array(); //存放数组array中每个不同元素的出现的次数
    for (var i = 0; i < array.length; i++) {
      for (var j = i + 1; j < array.length; j++) {
        if (array[i] == array[j]) {
          count++; //用来计算与当前这个元素相同的个数
          array.splice(j, 1); //没找到一个相同的元素，就要把它移除掉，
          j--;
        }
      }
      sum[i] = count; //并且将有多少个当前这样的元素的个数存入sum数组中
      count = 1; //再将count重新赋值，进入下一个元素的判断
    }

    var newsum = new Array(); //  sum;
    for (var item = 0; item < sum.length; item++) {
      newsum[item] = sum[item];
    }
    newsum.sort(function sortNumber(a, b) {
      return a - b
    });
    var first = 0; //存放出现次数最多的元素，以及个数
    var fcount = 1; //计算出现次数最多的元素总共有多少个
    for (var i = 0; i < sum.length; i++) {
      if (sum[i] == newsum[newsum.length - 1]) {
        first = sum[i];
        fcount++;
      }

    }
    return first;
  }

	  function date2LocationPx(date, startTime, endTime) {
	    var heightTime = $(".commStyle.clearfix").width();
	    var left = parseInt(((new Date(date)).getTime() - (new Date(startTime)).getTime()) / ((new Date(endTime)).getTime() + 2592000000 - (new Date(startTime)).getTime()) * ((heightTime)));
	    return left;
	  }
	function drawTimeLine(date) {
	    $("#timeline_dates").empty();
	    $("#timeline_histograms").empty();
	    var dateReplace = [];
	    var dataTmpArrayStart = [];
	    var dataTmpArrayEnd = [];
	    for (var i = 0; i < date.length; i++) {
	      if (date[i]["startTime"].indexOf("tepf") < 0 && date[i]["endTime"].indexOf("tepf") < 0) {
	        dataTmpArrayStart.push(date[i]["startTime"].replace(/"/g, ""));
	        dataTmpArrayEnd.push(date[i]["endTime"].replace(/"/g, ""));
	      }
	    }
	    dataTmpArrayStart = dataTmpArrayStart.sort();
	    dataTmpArrayEnd = dataTmpArrayEnd.sort();
	    for (var j = 0; j < dataTmpArrayStart.length; j++) {
	      for (var i = 0; i < date.length; i++) {
	        if (date[i]["startTime"].replace(/"/g, "") == dataTmpArrayStart[j]) {
	          dateReplace.push(date[i]);
	          continue;
	        }
	      }
	    }
	    var dateYear = [];
	    var yearStart = (new Date(dataTmpArrayStart[0])).getFullYear(); //2015
	    var monthStart = (new Date(dataTmpArrayStart[0])).getMonth() + 1; //04
	    var yearEnd = (new Date(dataTmpArrayEnd[dataTmpArrayEnd.length - 1])).getFullYear(); //2016
	    var monthEnd = (new Date(dataTmpArrayEnd[dataTmpArrayEnd.length - 1])).getMonth() + 1; //08
	    var cnm = 0;
	    for (var i = 0; i <= ((yearEnd - yearStart) * 12 - monthStart + monthEnd); i++) {
	      if (i < 12 - monthStart) {
	        dateYear.push(yearStart + "-" + (monthStart + i));
	        cnm = i;
	      } else {
	        dateYear.push((yearStart + 1) + "-" + (i - cnm));
	      }
	    }
	    var itemDetail = 1;
	    var widthTime = $(".commStyle.clearfix").width();
	    var yearDetail = parseInt(widthTime / dateYear.length);
	    for (var i = 0; i < dateYear.length; i++) {
	      $("#timeline_dates").append("<li title=\"" + dateYear[i] + "\" style='border-left: 2px solid #00F;border-right: 2px solid #00F;width: " + yearDetail + "px'><a style=\"color:white;\" href=\"javascript:void(0);\">" + dateYear[i] + "</a></li>");
	    }
	    var allTime = new Array();
	    for (var i = 0; i < widthTime; i++) {
	      allTime[i] = 0;
	    }
	    var height = 10;
	    var leftArray = [];
	    var leftArrayTmp = [];
	    var heightArray = [];
	    for (var i = 0; i < dateReplace.length; i++) {
	      var startTime = date2LocationPx(dateReplace[i]["startTime"].replace(/"/g, ""), "" + dateYear[0], "" + (dateYear[dateYear.length - 1]));
	      var endTime = date2LocationPx(dateReplace[i]["endTime"].replace(/"/g, ""), "" + dateYear[0], "" + (dateYear[dateYear.length - 1]));
	      leftArray.push(startTime);
	      for (var j = 0; j <= endTime - startTime; j++) {
	        leftArrayTmp.push(startTime + j);
	      }
	    }
	    var first = getRepeatElementTotal(leftArrayTmp);
	    var leftArrayReaded = [];
	    for (var i = 0; i < leftArray.length; i++) {
	      var right = date2LocationPx(dateReplace[i]["endTime"].replace(/"/g, ""), "" + dateYear[0], "" + (dateYear[dateYear.length - 1]));
	      for(var j = leftArray[i]; j <= right; j ++){
	        allTime[j] += parseFloat(55 / first);
	      }
	    }
	    for(var i = 0; i < allTime.length; i ++){
	      $("#timeline_histograms").append('<div style="float: left;width: 1px;background-color: rgba(255, 255, 255, 0.8);' +
	      'position: absolute;bottom: 0px;left: ' + i + 'px;height: ' + (allTime[i]) + 'px;">' +
	      '</div>');
	    }
	    $("#timeline_bgDiv").css("width", Math.ceil(yearDetail * dateYear.length - 1));
	    $("#timeline_Detail").css("width", Math.ceil(yearDetail * dateYear.length));
	    var rs = new Resize("timeline_dragDiv", {
	        Max : true,
	        mxContainer : "timeline_bgDiv"
	      });
	    rs.Set("timeline_rRight", "right");
	    rs.Set("timeline_rLeft", "left");
	    new Drag("timeline_dragDiv", {
	      Limit : true,
	      mxContainer : "timeline_bgDiv",
	      onMove : function () {
	        var count = nodes.length;
	        var selectedNodes = new Array();
	        var unSelectedNodes = new Array();
	        for (var i = 0; i < count; i++) {
	        	if(nodes[i]["subList"] != undefined && nodes[i]["subList"].length > 0){
	        		for(var j = 0; j < nodes[i]["subList"].length; j ++){
	        			var hasBeginning = nodes[i]["subList"][j].time;
	        			var hasEnd = nodes[i]["subList"][j].time;
	        			if (hasBeginning && hasEnd) {
			              var timestamp_start = date2LocationPx(hasBeginning.replace(/"/g, ""), "" + dateYear[0], "" + (dateYear[dateYear.length - 1]));
			              var timestamp_end = date2LocationPx(hasEnd.replace(/"/g, ""), "" + dateYear[0], "" + (dateYear[dateYear.length - 1]));
			              var left = this.Drag.offsetLeft - 1;
			              var right = this.Drag.offsetLeft + this.Drag.clientWidth;
			              if (
			                (left <= timestamp_start && timestamp_start <= timestamp_end && timestamp_end <= right)
			                 || (left <= timestamp_start && timestamp_start <= right) && (right <= timestamp_end)
			                 || (timestamp_start <= left && left <= timestamp_end && timestamp_end <= right)
			                 || (timestamp_start <= left && left <= right && right <= timestamp_end)) {
			                  selectedNodes.push(nodes[i]);
			                  if(!graphOperation.judgeOWN(nodes[i])){
							  	$(".n"+nodes[i].uuid +" image").attr("href", "images/img/"+nodes[i].type+"Sel.png");
							  }
			              } else {
			                unSelectedNodes.push(nodes[i]);
			                if(!graphOperation.judgeOWN(nodes[i])){
			                	$(".n"+nodes[i].uuid +" image").attr("href", "images/img/"+nodes[i].type+".png");
			                }
			              }
			            }
	        		}
	        	}
	        }
	      }
	    });
	  }
	var bh; 
    var bw; 
  $(function(){
	   $.ajax({
          type:"post",
          url:pageVariate.base+"/query/getTreeJson.action",
          async:false,
          success:function(data){
	        	pageVariate.tempData = eval(data);
       	   zNodes=pageVariate.tempData;
          }
       });
       var initLayoutImage = function(){
           var layouts = $("#layout").children();
           for(var i = 0; i < layouts.length; i ++){
               var oldImage = $(layouts[i]).children().attr("src");
               var newImage = oldImage.replace("Sel.png", ".png");
               $(layouts[i]).children().attr("src", newImage);
           }
       }
       $("#layoutBreadthfirst").click(function(){
       	  init_layout();
       	  initLayoutImage();
       	  on_layout("breadthfirst");
       	  var _this = this;
       	  var oldImage = $(_this).children().attr("src");
       	  var newImage = oldImage.replace(".png", "Sel.png");
       	  $(_this).children().attr("src", newImage);
       	  
       });
       $("#layoutCose").click(function(){
     	  init_layout();
       	  initLayoutImage();
     	  on_layout("cose");
       	  var _this = this;
       	  var oldImage = $(_this).children().attr("src");
       	  var newImage = oldImage.replace(".png", "Sel.png");
       	  $(_this).children().attr("src", newImage);
       });
       $("#layoutRandom").click(function(){
      	  init_layout();
       	  initLayoutImage();
       	  on_layout("random");
       	  var _this = this;
       	  var oldImage = $(_this).children().attr("src");
       	  var newImage = oldImage.replace(".png", "Sel.png");
       	  $(_this).children().attr("src", newImage);
       });
       $("#layoutCircle").click(function(){
       	  init_layout();
       	  initLayoutImage();
       	  on_layout("circle");
       	  var _this = this;
       	  var oldImage = $(_this).children().attr("src");
       	  var newImage = oldImage.replace(".png", "Sel.png");
       	  $(_this).children().attr("src", newImage);
       });
       $("#layoutGrid").click(function(){
       	  init_layout();
       	  initLayoutImage();
       	  on_layout("grid");
       	  var _this = this;
       	  var oldImage = $(_this).children().attr("src");
       	  var newImage = oldImage.replace(".png", "Sel.png");
       	  $(_this).children().attr("src", newImage);
       });
       $("#layoutConcentric").click(function(){
       	  init_layout();
       	  initLayoutImage();
       	  on_layout("concentric");
       	  var _this = this;
       	  var oldImage = $(_this).children().attr("src");
       	  var newImage = oldImage.replace(".png", "Sel.png");
       	  $(_this).children().attr("src", newImage);
       });
       
       $("#addTimeDataToLine").click(function(){
       	  var dataArray = new Array();
          for(var i = 0; i < nodes.length; i ++){
            if(nodes[i]["subList"] != undefined && nodes[i]["subList"].length > 0){
              for(var j = 0; j < nodes[i]["subList"].length; j ++){
                var time = nodes[i]["subList"][j].time;
                dataArray.push({
                  startTime : time,
                      endTime : time
                    });
              }
            }
          }
          drawTimeLine(dataArray);
       });
	   //add by hanxue start 导出
	    $("#exportingDiv").dialog({
	        dialogClass: 'noTitleStuff' ,
	        hide:true,
	        autoOpen:false,	   
	        width:200,
	        height:170,
	        resizable : false,
	        modal:false,
	        title:"",
	        draggable:false,
	        shadow: false,
	        bgiframe: true,
	        overlay: {opacity: 1,overflow:'auto',backgroundColor: '#fff',}
	    });
    	bh = $("body").height(); 
	    bw = $("body").width(); 
	    $("#exportingDiv").dialog("close");
	    $("#exportingDiv").parent().css({left: (bw-200)/2,top:(bh-170)/2,height:170,border:0});
	    $("#exportingDiv").css({border:0});
	    $(".window-shadow").removeAttr("style");
	    $("#pwdTaskDiv").dialog({
	    	dialogClass: 'noTitleStuff' ,
	        hide:true,
	        autoOpen:false,	   
	        width:850,
	        height:500,
	        resizable : false,
	        modal:false,
	        title:"破解任务",
	        draggable:false,
	        shadow: false,
	        bgiframe: true,
	        overlay: {opacity: 1,overflow:'auto',backgroundColor: '#fff'},
	        onClose: function(event, ui) { $("#fullbg").removeAttr('style'); }
	    });
	    $("#pwdTaskDiv").dialog("close");
	    //add by hanxue end
      	document.onkeydown = function(e) {
			e = e ? e : window.event;
			var keyCode = e.which ? e.which : e.keyCode;
			if(e.ctrlKey && keyCode == 17)
				pageVariate.pressCtrl = true;
		}
		document.onkeyup = function(e) {
			e = e ? e : window.event;
			var keyCode = e.which ? e.which : e.keyCode;
			if(e.ctrlKey && keyCode == 17)
				pageVariate.pressCtrl = false;
		}
    }); 
     /*#################操作时间轴操作相关函#########################*/
  
    </script>

  <script type="text/javascript">
  /*加载菜单 by hx*/
    function menuClick(menuParam){
      if(null!=menuParam&&menuParam!=""){
        hideAll("wheel");
        $("#menuDiv").css("visibility","hidden");
        menuClickLindage(menuParam);
      }
    }

    
    function initMenu(type){
		if(handleUtil.judgeEvent(type)){
			type = "Event";
		}
    	$.ajax({
             type:"post",
             url:"${base}getMenuData.action?type="+type,
             async:false,
             success:function(data){
               $("#menuDiv").empty();
               $("#menuDiv").html(data);
             }
          });
          $(".wheel-button").wheelmenu({
    	    trigger: "click",
    	    animationSpeed:"fast",
    	    liSize:rootnumber,
    	    angle:[0,360]
    	},rootWheelR);
    }

    function disableMenu(){
    	var wheelChildren = $("#wheel").children();
    	for(var i = 0; i < wheelChildren.length; i ++){
    		$(wheelChildren[i]).css("pointer-events", "none");
    	}
    }
    
    function ableMenu(){
    	var wheelChildren = $("#wheel").children();
    	for(var i = 0; i < wheelChildren.length; i ++){
    		$(wheelChildren[i]).css("pointer-events", "");
    	}
    }
    
    function selectNodeStatus(){
    	var d = pageVariate.currentNode;
    	var type = d.image.replace(/\//g, "").replace("img", "").replace(".png", "");
		//当前没有选中的呼出菜单选中，操作完成还原
		var index = pageVariate.selNode.indexOf(d);
		if(index>-1){
			pageVariate.currentSelFlag = true;
		}else{
			$(".n"+d.uuid +" image").attr("href", "images/img/"+type+"Sel.png");
			pageVariate.selNode.push(d);
		}
    }
    
    function showMenu(event,nodeImg){
        if($("#menuDiv").css("visibility")=="visible"){
            $(this).bind('contextmenu',function(e){  
             return false;  
            });
            hideAll("wheel");
            /* $("#menuDiv").load("#getMenuData"); */
            $("#menuDiv").css("visibility","hidden");
        }else{
            $(this).bind('contextmenu',function(e){  
            	return false;  
            });
            var x = event.pageX-280-50;
            var y = event.pageY-180+10;
            var imgX = "";
            var imgY = "";
            try{
            	imgX = nodeImg.offset().left;
                imgY = nodeImg.offset().top;
            }catch(e){}
            if(null!=imgX&&imgX!=0&&imgX!=undefined){
         	   $("#menuDiv").css("margin-left",imgX-230-38);
            }else{
         	   $("#menuDiv").css("margin-left",x);
            }
            if(null!=imgY&&imgY!=0&&imgY!=undefined){
         	   $("#menuDiv").css("margin-top",imgY-230+138);
            }else{
         	   $("#menuDiv").css("margin-top",y);
            }
			var type = pageVariate.currentNode.image.replace(/\//g, "").replace("img", "").replace(".png", "");
			if(type != ""){
				if(type=="IM"){
					type = "QQ";
				}
			}
			initMenu(type);
			disableMenu();
			if(type == "Person"){
				ableMenu();
				selectNodeStatus();
				//添加对周围属性展开的判断
				//1、判断有多少需要展开的节点
				$.ajax({
					type : "post",
					dataType : "json",
					url : pageVariate.base+"query/getDetailById.action",
					data : {
						personId : pageVariate.personId,
						maxIndex : nodes.length-1,
						nodeIndex : graphOperation.findNodeIndex(pageVariate.uuid)
					},
                    async : false,
					success : function(data) {
						var detailData = eval(data);
						var arr = [];
						var idArray = [];
						$.each(detailData.nodes,function(i,node){
							arr.push(node.uuid);
							idArray.push(node.id);
						});
						backendCacheManager.deleteIdCache(idArray);
						//2、查看当前展示了多少节点
						var uuidArr = pageVariate.openMap.get(pageVariate.uuid);
						if (uuidArr && uuidArr.length >= detailData.nodes.length) {
							$("#subWheelLi2").attr("class", "customicon-3closeList");
						}else{
							$("#subWheelLi2").attr("class", "customicon-3openList");
						}
					},
					error : function() {
						hintManager.showHint("服务器繁忙，请稍后重试！");
					}
				});
				$("#menuDiv").css("visibility","visible");
				$(".wheel-button").trigger("click");
			}else if(pageVariate.IM.indexOf(type) > -1){
				selectNodeStatus();
				$("#menuDiv").css("visibility","visible");
				$(".wheel-button").trigger("click");
				$($("#wheel").children()[4]).css("pointer-events", "");
				$($("#wheel").children()[5]).css("pointer-events", "");
			}else if(type == "Group"){
				selectNodeStatus();
				$("#menuDiv").css("visibility","visible");
				$(".wheel-button").trigger("click");
				$($("#wheel").children()[5]).css("pointer-events", "");
			}else if(handleUtil.judgeEvent(type)){
				selectNodeStatus();
				$("#menuDiv").css("visibility","visible");
				$(".wheel-button").trigger("click");
				$($("#wheel").children()[4]).css("pointer-events", "");
				$($("#wheel").children()[5]).css("pointer-events", "");
			}else{
				console.log(type);
				return false;
			}
        }
    }
    
    /******************************查询入口 start**************************************/   
      /*发送mail*/
	function sendmails(id){
		var str=document.getElementsByName("mailcheckbox"+id);
		var objarray=str.length;
		var oblist=new Array();
		var oblistcount=0;
		var result=new Array();
		for (var i=0;i<objarray;i++){
		  if(str[i].checked == true){
			oblist[oblistcount]=str[i].value;
			++oblistcount;
		  }
		}
		for (var i=0;i<oblist.length;i++){
				result[i]=emailmap.get(oblist[i]).vertexID;
		}
		if(oblistcount == 0){
			 hintManager.showHint("请至少选择一个mail！");
		}else{
			//跳到首页 TODO
			// .....
			searchManager.sendmailToGraph(result);
			$(".easyui-tabs").tabs('select', 0);
			
			
		}
	}
    //页面缓存变量
    var pageVariate = {
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
		clip : "",
		isClickNode : false,
		currentNode:"",
		IM:["QQ"],
		viewConfig:$.parseJSON('${pageViewConfig}'),
		queryableMap:$.parseJSON('${queryableMap}'),
		aliasAll:$.parseJSON('${aliasAll}'),
		click:0
    }
	
  	$(function(){
  		backendCacheManager.emptyIdCache();
		//回车查询
	
		$('#content').on('keypress',function(event){
	           if(event.keyCode == "13"){
	        	 searchContent("");
// 	        	   searchshow("");
	           }
	       });
	});
    var pageNO=0;
    /*查询入口*/
  	function searchContent(more){
  		
  		maskManager.show();
  		var content = $('#content').val();
  		var propertySel = $(".select_txt").attr("name");
  		if(more==""){pageNO=0;}else{
  			content = $('#'+more+'tabvalue').val();
  			pageNO=pagemap.get(more)+1;
  		}
  		if("emailAll" == propertySel){
  			$.ajax({
		         type:"post",
		         dataType:"json",
		         url:"${base}query/getEmailsFullText.action",
		         data:{
	  				 property:propertySel,
		        	 content:content,
		        	 allText:advanceSearch.get("allTextOfSearch"),
		        	 titleOfSearch:advanceSearch.get("titleOfSearch"), 
		        	 mailAddressOfSearch:advanceSearch.get("mailAddressOfSearch"), 
		        	 contectOfSearch:advanceSearch.get("contectOfSearch"), 
		        	 filenameOfSearch:advanceSearch.get("filenameOfSearch"), 
		        	 filecontectOfSearch:advanceSearch.get("filecontectOfSearch"), 
		        	 hotwordOfSearch:advanceSearch.get("hotwordOfSearch"), 
		        	 tagOfSearch:advanceSearch.get("tagOfSearch"), 
		        	 folderOfSearch: advanceSearch.get("folderOfSearch"), 
		        	 datefromSearch:advanceSearch.get("datefromSearch"), 
		        	 datetoSearch:advanceSearch.get("datetoSearch"), 
		        	 pageNo:pageNO,
		        	 pageSize:50,
		        	 date:new Date().getTime() 
		         },
		         success:function(data){
		         	maskManager.hide();
		        	 //++pageNO;
		        	var emails = eval(data);
		        	
		        	if(emails && emails.length>0){
		        		// TODO
		        		if(more==""){getmaillist(emails,"");}else{pagemap.put(more,pageNO);getmaillist(emails,more);}
		        	}else{
		        		hintManager.showHint("未查询到邮件数据。");
		        	}
		           },
		         error:function(){
		         	maskManager.hide();
		        	hintManager.showHint("未查询到邮件数据。");
		         }
		     });
  		}else{
	  		$.ajax({
		         type:"post",
		         /* url:"${base}query/getObj.action", */ 
		         url:"${base}query/findObjByFull.action", 
		         data:{
	  				 property:propertySel,
		        	 content:content,
		        	 date:new Date().getTime() 
		         },
		         dataType:"json",
		         success:function(data){
		        	 console.log(data);
		        	var click=pageVariate.click;
	        	   	click=click+1; 
	        	 	maskManager.hide();
		        	pageVariate.tempData = eval(data);
		        	var nodeSize = pageVariate.tempData.nodes.length;        	
		            if(nodeSize == 1){
		            	searchManager.searchLinkage(pageVariate.tempData);
		            	$(".easyui-tabs").tabs('select', 0);
		            }else if(nodeSize > 1){
		            	searchManager.moreNodeHandle(pageVariate.tempData);
		            	$(".easyui-tabs").tabs('select', 0);
		          		searchshow(pageVariate.tempData,click);
		            }else{
		             	hintManager.showHint("未查询到数据。");
		            }
		           },
		         error:function(){
		         	maskManager.hide();
		        	hintManager.showHint("未查询到数据。");
		         }
		     });
  		}
  	}
  	var emailmap = new Map();
  	var pagemap=new Map();
  	/******************************查询入口  end**************************************/ 
  
  	/******************************整合菜单、时间轴 、地图 start**************************************/
  	/* 菜单点击
  	 * 此处配置按钮执行的方法
     * 1search=查询,2connect=关联,3openList=展开,4clear=清空,5delete=删除,6map=地图
     * 11object=对象,13net=网络
     * 831all=所有(3级),832bbs=帖子,833doctor=医疗,834talk=通话,835login=网站登录,836mail=邮件
    */
  	function menuClickLindage(menuParam){
		var type = pageVariate.currentNode.image.replace(/\//g, "").replace("img", "").replace(".png", "");
  		if(type = "IM"){
  			type = "QQ";
  		}
		var re=/^83\d{1}.*/;
  		if(re.test(menuParam)){//事件查询
  			var searchflag = true;
  			if("831all" == menuParam){
  				searchflag = changeState("event_all");
  	  		}else if("832bbs" == menuParam){
  	  			searchflag = changeState("event_BBSEvent");
  	  		}else if("833doctor" == menuParam){
  	  			searchflag = changeState("event_MedicalEvent");
  	  		}else if("834talk" == menuParam){
  	  			searchflag = changeState("event_CallEvent");
  	  		}else if("835login" == menuParam){
  	  			searchflag = changeState("event_LoginEvent");
  	  		}else if("836mail" == menuParam){
  	  			searchflag = changeState("event_EmailEvent");
  	  		}else if("837hotel" == menuParam){
  	  			searchflag = changeState("event_StayEvent");
  	  		}
  			if(searchflag){
	  			//删除事件相关节点
				graphOperation.deteleEventNode(pageVariate.uuid);
				searchManager.showEvent(pageVariate.types,pageVariate.startTime,pageVariate.endTime);//展示事件
	  			timeLineOperation.currentTimeLineId="";
	  			timeLineOperation.showTimeLineById("all","all");//时间轴联动
  			}
  		}else{
  			changeState(menuParam);
  			if("2connect" == menuParam){
  				searchManager.showConnect();
  	  		}else if("2process" == menuParam){
  	  			graphOperation.collapseNode(pageVariate.uuid);//删除子节点
  	  		}else if("3openList" == menuParam){
  	  			searchManager.showDetail();
	  	  	}else if("4clear" == menuParam){
		  	  	//清空
		  	  	confirm("是否确认清空？",function(){
		  	  		graphOperation.emptyNode();
		  	  		myChart.clear();
		  	  		myChart.setOption(myChart.initOption);
		  	  	});
	  	  	}else if("5delete" == menuParam){
	  	  		if(type == "Person"){
	  	  			//删除节点及人物节点的子节点
			  	  	deteleSelectNodes();
		  	  	}else if(pageVariate.IM.indexOf(type) > -1){
  	  			   maskManager.show();
	  	  		   $.ajax({
			          type:"post",
			          url:pageVariate.base+"ajaxSearchQq/searchQQFriendsByQq.action",
			          data : {
						qq : pageVariate.currentNode.numid
					  },
			          success:function(data){
			          	maskManager.hide();
				        var eventData = eval(data);
				        graphOperation.expandNode(eventData.nodes,eventData.edges);
			          }
			       });
  	  			}else if(handleUtil.judgeEvent(type)){
// 					alert("mergeNode");
					graphOperation.mergeNodes(pageVariate.selNode);
				}
  	  		}else if("6map" == menuParam){
  	  			if(type == "Person"){
  	  			    pageVariate.eventFlag="map";
		  			var eventType = ["LoginEvent"];
		  			showTimeLline(pageVariate.personId,eventType);//统计地图相关事件
	  	  			showMap(pageVariate.personId,pageVariate.startTime,pageVariate.endTime);
  	  			}else if(pageVariate.IM.indexOf(type) > -1){
  	  			  	maskManager.show();
  	  				$.ajax({
			          type:"post",
			          url:pageVariate.base+"ajaxSearchQq/searchgGroupsByQq.action",
			          data : {
						qq : pageVariate.currentNode.numid
					  },
			          success:function(data){
			          	maskManager.hide();
				        var eventData = eval(data);
				        graphOperation.expandNode(eventData.nodes,eventData.edges);
			          }
			       	});
  	  			}else if(type == "Group"){
  	  			    maskManager.show();
  	  				$.ajax({
			          type:"post",
			          url:pageVariate.base+"ajaxSearchQq/searchGroupMembers.action",
			          data : {
						groupNum : pageVariate.currentNode.numid
					  },
			          success:function(data){
			          	maskManager.hide();
				        var eventData = eval(data);
				        graphOperation.expandNode(eventData.nodes,eventData.edges);
			          }
			       });
  	  			}else if(handleUtil.judgeEvent(type)){
// 					alert("unmergeNode");
					graphOperation.splitNode(pageVariate.selNode);
				}
  	  		}else if("7group" == menuParam){
  	  			
  	  		}else if("1search" == menuParam){
  	  			
  	  		}else if("10all" == menuParam){
  	  			
  	  		}else if("11object" == menuParam){
  	  			/* searchManager.showGroupRelative(); */
  	  		}else if("12event" == menuParam){
  	  			
  	  		}else if("13net" == menuParam){
  	  			searchManager.showRelative();
  	  		}
  		}
  		//还原按钮状态
  		if("5delete" != menuParam && "4select" != menuParam){
  			graphOperation.resetCurrentNode();
  		}
  	}
  	
  	
	/*记录菜单状态
	 *id为菜单的ID，查询事件的菜单ID格式为event_type,全部为event_all
	 */
  	function changeState(id){
  		var markArr = id.split("_");
  		if(markArr.length == 2 && markArr[0] == "event"){
  			pageVariate.eventFlag="event";
  			var type = markArr[1]; 
  			if("all" == type){
  				pageVariate.types=[];
  			}else{
  				var typeArr = pageVariate.typeMap.get(pageVariate.personId);
  				if (typeArr) {
  					var index = typeArr.indexOf(type);
  					if(index>-1){
  						//return false;
  						for(var i = index;i<(typeArr.length-1);i++){
  							var tempStr = typeArr[i];
  							typeArr[i] = typeArr[i+1];
  							typeArr[i+1] = tempStr;
  						}
//   					typeArr.splice(index, 1);
  					}else{
  						typeArr.push(type);
  					}
  				}else{
  					typeArr = [];
  					typeArr.push(type);
  					pageVariate.typeMap.put(pageVariate.personId,typeArr);
  				}
  				pageVariate.types = typeArr;
  			}
  		}else if("6map" == id){
  			//pageVariate.eventFlag="map";
  		}else{
  			pageVariate.eventFlag="event";//暂定
  		}
  		return true;
  	}
	
	/* 删除选中节点 */
	function deteleSelectNodes(){
		confirm("是否确认删除？",function(){
  	  		graphOperation.deteleSelNode(pageVariate.selNode);
  	  		myChart.clear();
  	  		myChart.setOption(myChart.initOption);
  	  		//以后可能要判断删除的点是不是最后操作的
  	  		pageVariate.personId = "";
  	  		pageVariate.uuid = "";
  	  		pageVariate.initStartTime = "";
  	  		pageVariate.initEndTime = "";
  	  	});//修改为删除选中节点
	}
	
  	
  	/*点击菜单或拖动时间轴map联动*/
  	function showMap(id,start,end){
  		//TODO 实现map展示
  		$.ajax({
			type : "post",
			dataType : "json",
			url : pageVariate.base+"query/getMapData.action",
			data : {
				personId : id,
				startTime : start,
				endTime : end,
				date : new Date().getTime()
			},
			dataType : "json",
			success : function(data) {
				maskManager.hide();
				var localtion = eval(data);
				//直方图展示
				tabsManager.histogramShow(2,localtion);
				$('.first a').click();
				
				//地图展示
				$('.displayDiv').hide();
				$('.zoomline').hide();
				$('.div-relative').html('');
				$('.mapDiv').show();
				init(localtion);
			},
			error : function() {
				hintManager.showHint("服务器繁忙，请稍后重试！");
			}
		});
  	}
  	
  	/* 过滤属性联动 */
    function changeProperty(type){
  	  var vertexProperty = $.parseJSON('${vertexPropertyJson}');
  	  var pros = vertexProperty[type];
  	  if(pros){
  		  var html = "";
  		  for(var i = 0;i < pros.length; i++){
     			var temp = pros[i];
     			html += "<option value='"+temp+"'>"+temp+"</option>";
  		  }
  		  $(".filterProperty").html(html);
  		  $(".filterContent").val("");
  	  }
    }
    
    /* 过滤、反选 
       flag - 过滤反选标识
       scopeflag - 反选范围标识
    */
    function filterGraph(flag,scopeflag){
  	  if(flag == "checkInvert"){
  		  if(scopeflag == "timeLine"){
  		      var events = graphOperation.findEventById(timeLineOperation.currentTimeLineId);
  			  tabsManager.graphHighlightInvert(events);
  		  }else{
	  		  tabsManager.graphHighlightInvert(nodes);
  		  }
  		  return;
  	  }
  	  var type = $(".filterType").val();
  	  var property = $(".filterProperty").val();
  	  var content = $(".filterContent").val();
  	  if(content.replace(/(^\s*)|(\s*$)/g, "") == "" ){
  		  hintManager.showHint("过滤内容不能为空，请输入！");
  	  }else{
  		  if(flag == "check"){
  			  tabsManager.filterGraphHighlight(type,property,content); 
  		  }
  	  }
    }
    
    /* 获取页面点的id数组 （不包括任务属性节点）*/
    function getPageVertexIds(){
    	var idarr = []; 
		$.each(nodes,function(i,node){
			var type = node.type;
			if("Person" == type){
				idarr.push(node.id);
			}else if(typeof(type) == "string" && type.endWith("Event")){
				var eventNodes = node.subList;
				eventNodes.forEach(function(node,index){
					idarr.push(node.id);
				})
			}
		});
		return idarr;
    }
	
  	/******************************整合菜单、时间轴 、地图 end**************************************/
  	
 	$(function(){
  			/*------头部select框--------*/
           $(".property_div").click(function(event){   
                        event.stopPropagation();
                        $(this).find(".option").toggle();
                        $(this).parent().siblings().find(".option").hide();
                        $(".hideDiv").hide();
           });
           $(document).click(function(event){
                        var eo=$(event.target);
                        if($(".property_div").is(":visible") && eo.attr("class")!="option" && !eo.parent(".option").length)
                        $('.option').hide();  
                        
                        $(".hideDiv").hide(); 
                   });
           /*赋值给文本框*/
           $(".option span").click(function(){
                        var value=$(this).text();
                        $(this).parent().siblings(".select_txt").text(value);
                        var classAttr = $(this).attr('class');
						$(".select_txt").attr("name", classAttr);
                        $("#select_value").val(value);
                       /* $(".select_txt").attr('name',)); */
           })
           
           /************************头部隐藏div************************/
	   		$(".hideDiv ul li").live("click",function(){
	   			var nodes = pageVariate.tempData.nodes;
	   			var uuid = this.id;
	   			for(var i=nodes.length-1;i>-1;i--){
	   				var node = nodes[i];
					if(uuid != node.uuid){
						nodes.remove(node);
	   				}
	   			}
	   			searchManager.searchLinkage(pageVariate.tempData);
	   			pageVariate.tempData ="";
	   			$(".hideDiv").hide(); 
	   		})
           
           /************************ 事件列表控制  ********************************/
         	//选择
			/* $(".serTit .li1 .check").click(function(){
				if(pageVariate.selectAllFlag==true){
					$('.serList ul li.s0 input').attr('checked',true);
					pageVariate.selectAllFlag=false;
				}else{
					$('.serList ul li.s0 input').attr('checked',false);
					pageVariate.selectAllFlag=true;
				}
			}); */
			
			//删除
			$('.btnDel').click(function(){
				if($('.serList ul li.s0 input:checked').length==0){
					hintManager.showHint("请选择要删除的项！");
				}else{
					confirm("是否确认删除？", function(){
						var checkID=new Array();
						$('.serList ul li.s0 input:checked').each(function(i,d){
							checkID[i]=d.id;	
						})
						graphOperation.listDeteleLinkage(tabsManager.list_node,checkID);
						$('.serList ul li.s0 input:checked').parents('.serUl').next('.serDetail').remove();//这个与下面的顺序不能变
						$('.serList ul li.s0 input:checked').parents('.serUl').remove();
					})
					
				}
			});
			//选择，全选和全不选
			/* $('.checkInvert.time').click(function(){
				var len = $('.serList ul li.s0 input:checked').length;
				var _this = this;
				if(len==0){
					$('.serList ul li.s0 input').attr('checked',true);
					$(_this).text("全不选");
				}else{
					$('.serList ul li.s0 input').each(function(){
						   $(this).attr("checked",!this.checked);              
				     });
				     $(_this).text("全选");
				}
			});	 */
			//全选，反选
			$("#checkAll").click(function(){
				var str = $('input[name="leftcheckbox"]');
				var len = str.length;
				for(var i=0;i<len;i++){
					str[i].checked = true;
				}
				
			});
			$("#inverseCheck").click(function(){
				var str = $('input[name="leftcheckbox"]');
				var len = str.length;
				for(var i=0;i<len;i++){
					if(str[i].checked == true){
						str[i].checked = false;
					}else if(str[i].checked == false){
						str[i].checked = true;
					}
				}
			});
			
   	})
  </script>
  
  <!--<script language="javascript" src="http://app.mapabc.com/apis?t=javascriptmap&v=3&key=b0a7db0b3a30f944a21c3682064dc70ef5b738b062f6479a5eca39725798b1ee300bd8d5de3a4ae3"></script> -->
  <!-- 地图 -->  
  <script type="text/javascript">
		var options = 
				{
					projection: "EPSG:900913",
					displayProjection: "EPSG:4326",
					units: 'm'
				};
		
		var map;
		function init(data){
             map = new OpenLayers.Map("map",options);		
			/**********************加载图层 开始*******************************/

		var base = new OpenLayers.Layer.WMS("osm数据",
					//"http://192.168.20.183:8080/geoserver/test/gwc/service/wms"
					"${geoserverMapUrl}"
					, {
						'layers' : 'chinamap',
						transparent : true,
						format : 'image/png'
					}, {
						
						singleTile: true, 
						ratio: 1, 
						isBaseLayer : true

					});	
			

			map.addLayers([base]);
			map.addControl(new OpenLayers.Control.MousePosition());
			map.addControl(new OpenLayers.Control.ScaleLine());	
			
			 
		//添加图标控制层和button图标
 	    var button = new OpenLayers.Control.Button({
 			displayClass:"olControlButton", trigger: function(){
	 				$('.displayDiv').show();
	 				$('.zoomline').show();
					$('.mapDiv').hide();
					tabsManager.histogramShow(1,nodes);
					$('.first a').click();
					//还原地图之前的界面状态
					var typeArr = pageVariate.typeMap.get(pageVariate.personId);
	  				if (!typeArr) {
	  					typeArr = []
	  				}
	  				pageVariate.types = typeArr;
	  				pageVariate.eventFlag="event";
					showTimeLline(pageVariate.personId,pageVariate.types);
 				}
 			});
 	    var panel = new OpenLayers.Control.Panel({defaultControl: button});
 	        panel.addControls([button]);
 	        map.addControl(panel);
//              var button = new OpenLayers.Control.Button({
//              displayClass: "MyButton", function(){alert("1111111111111")}});
//              map.addControls([button]);

			//添加要打点图标样式、尺寸
			//map.addControl(new OpenLayers.Control.LayerSwitcher());
			var markers = new OpenLayers.Layer.Markers( "Markers" );
            map.addLayer(markers);
            map.setCenter(new OpenLayers.LonLat(108.93,34.27).transform( 
                    new OpenLayers.Projection("EPSG:4326"), 
                    map.getProjectionObject() ), 4);
            
            var size = new OpenLayers.Size(28,28);
            var offset = new OpenLayers.Pixel(-(size.w/2), -size.h);
            //页面上进行打点
            for(var i=0;i<data.length;i++){
            	var mapJson = data[i];
            	var icon = new OpenLayers.Icon('images/map/'+mapJson.type+'.png', size, offset);
            	var marker= new OpenLayers.Marker(new OpenLayers.LonLat(mapJson.longitude,mapJson.latitude).transform( 
                            new OpenLayers.Projection("EPSG:4326"),map.getProjectionObject()),icon);
            	
            	marker.events.register("click",marker, function(evt){
            		var k = markers.markers.indexOf(this);
            	    tabsManager.browserShow(data[k]);
					$('.second a').click();
            		OpenLayers.Event.stop(evt); 
                 	});
            	markers.addMarker(marker);
//              	marker.events.register('mousedown', marker, function(evt) {
//            		    alert(""); 
//              		tabsManager.browserShow(mapJson);
//              		console.log(mapJson);
//             		OpenLayers.Event.stop(evt); 
//            	});
            }
           
            //添加LayerSwitcher图层控制控件
            map.addControl(new OpenLayers.Control.LayerSwitcher());
            

            LonLat.transform(new OpenLayers.Projection("EPSG:900913"),new OpenLayers.Projection("EPSG:4326"));
           
			//放大到全屏
			map.zoomToMaxExtent();

			
		}

$("#confirmNewPwd").bind("keydown",function(event){
	    if ( event.keyCode == 13 )		// 回车键
		{
	    	doSubmit();
		}
});

/*tab页  */
var index = 0;
function addPanel(){
	index++;
	$('#tt').tabs('add',{
		title: 'Tab'+index,
		content: '<div style="padding:10px">Content'+index+'</div>',
		closable: true
	});
}
$('#dd').click(function(){
	$(".tccBox").show();
})

/*邮件层添加关键词标签  */
function onAddTag(tag) {
	alert("Added a tag: " + tag);
}
function onRemoveTag(tag) {
	alert("Removed a tag: " + tag);
}
function onChangeTag(input,tag) {
	alert("Changed a tag: " + tag);
}

function addPTAjax(uid,username,pwd){
    $.ajax({
		         type:"post",
		         url:"<%=basePath%>ajax_pwdCrack/addPwdTask.action",
		         data:{
		            "ptForm.uuid":uid,
		            "ptForm.userName":username,
		            "ptForm.pwdEncrypt":pwd
		         },
		         dataType:"json",
		         success:function(data){
		            hintManager.showSuccessHint(data.returnResult);
		         },
		         error:function(){
		        	 hintManager.showHint("新增任务异常，请联系管理员！");
		         }
	 });
}

function showPTList(){
    window.location.href="<%=basePath%>pwdCrack/getPTList.action"; 
}
$(function() {
	$('#tags_1').tagsInput({width:'auto'});
});


 </script>
 <script type="text/javascript">//属性编辑
 	//隐藏遮罩层
 	$(".historytccBox").hide();
 
	//双击修改，可编辑
	$('.addDiv span b').live("dblclick",function(e){
		$('.aDiv').hide().siblings('b').show();
		$(this).hide();
		$(this).siblings('.aDiv').show(); 		
	});

	//取消
	$('.aDiv .nosureA').live("click",function(e){
		$('.aDiv').hide().siblings('b').show();
	});

	//确定
	$('.aDiv .sureA').live("click",function(e){
		var nowNum = $(this).parents('.aDiv').children('input').val();
		nowNum = nowNum.replace(/(^\s*)|(\s*$)/g, "");
		if(nowNum == "" ){
	  		hintManager.showHint("属性内容不能为空，请输入！");
			return;
		}
		var tempArr = $(this).attr("name").split(",");
		var vertexId = tempArr[0];
		var type = tempArr[1];
		var property = tempArr[2];
		var title = property+"："+nowNum;
		/* alert("nowNum:"+nowNum+",vertexId:"+vertexId+",type:"+type+",property:"+property); */
		if(type=="Phone"&&property=="phonenum"){
			if(!(/^1[3|4|5|6|7|8]\d{9}$/.test(nowNum))){
				hintManager.showHint("请输入正确的手机号码！");
				return;
			} 	
		}else if(type=="Email"||property=="email"){
			if(!(/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.(?:com|cn)$/).test(nowNum)){
				hintManager.showHint("请输入正确的邮箱！");
				return;
			}
		/* }else if(type=="Person"&&property=="idcard"){
			if(!((/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/).test(nowNum))){
				hintManager.showHint("请输入正确的身份证号码！");
				return;
			} */
		}else if(type=="Account"&&property=="regip"){
			if(!(/^(0|[1-9]?|1\d\d?|2[0-4]\d|25[0-5])\.(0|[1-9]?|1\d\d?|2[0-4]\d|25[0-5])\.(0|[1-9]?|1\d\d?|2[0-4]\d|25[0-5])\.(0|[1-9]?|1\d\d?|2[0-4]\d|25[0-5])$/).test(nowNum)){
				hintManager.showHint("请输入正确的ip地址！");
				return;
			}
		} 
		$(this).parents('.aDiv').parents('span').attr("title",title);
		$('.aDiv').hide().siblings('b').show();
		$(this).parents('.aDiv').siblings('b').text(nowNum);
		$(this).parents('.aDiv').siblings('b').show();
		
		$.ajax({
			type:"post",
			url:"ajaxHis/saveHistoryProperty.action",
			data:{
				"propertyHistory.vertexId":vertexId,
				"propertyHistory.type":type,
				"propertyHistory.value":nowNum,
			    "propertyHistory.property":property
			},
			dataType:"json",
			success:function(data){
				if(type == "Person"){
					if(property == "name"){
						$(".node.n"+pageVariate.uuid).find("tspan").html(nowNum);
						graphOperation.findNode(pageVariate.uuid).name = nowNum;
					}else{
						var arrays = pageVariate.openMap.get(pageVariate.uuid);
						if(arrays.length != 0){
							for(var i=0;i<arrays.length;i++){
								var vid = graphOperation.findNode(pageVariate.openMap.get(pageVariate.uuid)[i]).id;
								if(vertexId+property == vid){
									var uuid = graphOperation.findNode(pageVariate.openMap.get(pageVariate.uuid)[i]).uuid;
							        $(".node.n"+uuid).find("tspan").html(nowNum);
							        graphOperation.findNode(pageVariate.openMap.get(pageVariate.uuid)[i]).name = nowNum;
							        graphOperation.findNode(pageVariate.uuid)[property] = nowNum;
								}
							  }
						}else{
							//未展开时处理
							graphOperation.findNode(pageVariate.uuid).property = nowNum;
						}
					}
				}else{
					var arrays = pageVariate.openMap.get(pageVariate.uuid);
					if(arrays.length != 0){
					for(var i=0;i<arrays.length;i++){
						var vid = graphOperation.findNode(pageVariate.openMap.get(pageVariate.uuid)[i]).id;
						if(vertexId == vid){
							var uuid = graphOperation.findNode(pageVariate.openMap.get(pageVariate.uuid)[i]).uuid;
					        $(".node.n"+uuid).find("tspan").html(nowNum);
					        graphOperation.findNode(pageVariate.openMap.get(pageVariate.uuid)[i]).name = nowNum;
					        for(var j = 0; j < graphOperation.findNode(pageVariate.uuid)[type].length; j ++){
					        	if(graphOperation.findNode(pageVariate.uuid)[type][j].id == vid){
					        		graphOperation.findNode(pageVariate.uuid)[type][j][property] = nowNum;
					        	}
					        }
						}
				   }
					}else{
						//未展开时处理
						for(var j = 0; j < graphOperation.findNode(pageVariate.uuid)[type].length; j ++){
							graphOperation.findNode(pageVariate.uuid)[type][j][property] = nowNum;
				        }
					}
				}
				tabsManager.histogramShow(1,nodes);
			},
			error:function(){
			 hintManager.showHint("编辑属性异常，请联系管理员！");
			}
 		});
	}); 
	 
	/*查看历史记录*/
    $(".historyIcon").live("click",function() {
    	var tempArr = $(this).attr("name").split(",");
		var vertexId = tempArr[0];
		var type = tempArr[1];
		var property = tempArr[2];
        var ajData = {
            title: type+"-"+property+"属性的编辑历史记录",
            url: "query/viewHistoryPage.action?propertyHistory.vertexId="+vertexId+"&propertyHistory.property="+property,
            title1: ""
        };
        parentPop(ajData);
    });
	
	//点击其他地方
	$(document).bind("click",function(e){ 	
		var target = $(e.target); 	
		if(target.parents(".addDiv").length == 0){ 	
			$('.aDiv').hide().siblings('b').show();	
		} 	
	})    

	function parentPop(data) {
		$(".historytccBox").show();
		$(".historytccBox h3").text(data.title);
		$("#pop_iframe").attr("src", data.url);
	}
	
	/*关闭弹出层*/
	function dismissParentPop() {
		$(".historytccBox").hide();
		$("#pop_iframe").attr("src", "");
	}
	//add by hanxue start 导出
	function exportView(){
		//1.截图 保存截图2.获得数据集合3.生成数据集合
		var nodeIds = getPageVertexIds().join(",");
		var currDate = new Date();
		var currTimeStr = currDate.getTime();
		if(nodeIds!= null&&nodeIds!=''){
			confirm('确定要导出吗?',function(){
				var svgDiv = $("#svgImg").find("svg")[0];
				svgAsPngUri(svgDiv, null, function(uri) {
			        var data = {
	                    	viewImg:uri,//encodeURIComponent(uri)
	                  	    currentTimeStr:currTimeStr
			        };
			        $.ajax({
	                    url:"ajaxExpImgView/saveViewImg.action",//用于文件上传的服务器端请求地址
	                    type : "POST",
	                    data: data,
	                    dataType: 'json',
	                    async : false,
	                    success:function(data){
	                    	var url="ajaxExpView/exportViewObjs.action?currentTimeStr="+currDate.getTime()+"&ids="+nodeIds;
	                    	var bh = $("body").height(); 
	                        var bw = $("body").width(); 
	                    	$("#fullbg").css({ height:bh, width:bw, display:"block"}); 
	        				$("#exportingDiv").dialog("open");
	        				$.ajax( {
	        					type : "POST",
	        					url : url,
	        					success : function(data){
	        						var subDir = data.currentTimeStr;
	        					    var dlFileUrl = '<%=basePath%>fileManage/downloadFileAnalysis.action?fileNameD=Views.zip&type=view&subDir='+subDir;
	        					    $("#downloadFrame").attr("src",dlFileUrl);
	        					    $("#exportingDiv").dialog("close");
	        						$("#fullbg").removeAttr('style');
	        					}
	        				});
	                    },
	                    error: function (data, status, e){
	                         parent.hintManager.showHint("图片上传异常，请联系管理员！");
	                    }
	                });
			    });
			});
		}else{
			parent.hintManager.showHint("节点为空时不可以导出");
		}
	}
	function openPwdTask(){
		$("#pwdTaskFrame").attr("src", "<%=basePath%>pwdCrack/getPTList.action");
		$("#pwdTaskDiv").parent().css("left",(bw-850)/2);
		$("#pwdTaskDiv").parent().css("height","500px");
		if(bh>500){
			$("#pwdTaskDiv").parent().css("top",(bh-500)/2);
		}else{
			$("#pwdTaskDiv").parent().css("top",0);
		}
		$("#pwdTaskDiv").parent().css("background-color","RGBA(214, 218, 224,1)");
		$("#pwdTaskDiv").dialog("open");
		$("#fullbg").css({ height:bh, width:bw, display:"block"}); 
	}
	//add by hanxue end
	//add by yangxiuwu start
	$(function(){
		/*$("#allPerson").parent().bind('contextmenu', function (e) {
			e.preventDefault();
          	graphOperation.clearSelStatus();
			$('#menuHist').menu('show', {
	            left: e.pageX,
	            top: e.pageY
	        });
		});*/
	    var clipboardFlag = true;
		function clipboardInit() {  
			pageVariate.clip = new ZeroClipboard.Client();
			pageVariate.clip.setHandCursor(true);
			pageVariate.clip.addEventListener('mouseover', function (client, text) {
				clipboardFlag = false;
			});
			pageVariate.clip.addEventListener('mouseout', function (client, text) {
				clipboardFlag = true;
			});
			pageVariate.clip.addEventListener('complete', function (client, text) {
				alert("复制成功，您可以Ctrl+V粘贴");
				pageVariate.clip.destroy();
				clipboardInit();
			});
		}
		clipboardInit();
		
		$('#menuHist').menu({
		  hideOnUnhover:false,
	      onClick:function(item){
	        switch(item.name){
	          case "deleteSelObjects" :
	          	graphOperation.deteleSelNode(pageVariate.selNode);
	            break;
	          case "deleteUnSelObjects" :
	          	graphOperation.deteleUnSelNode();
	            break;
              case "copyToClipboard" :
	            break;
	        }
	      },
	      onHide:function(){
	      	if(clipboardFlag){
		      	pageVariate.clip.destroy();
				clipboardInit();
			}
			clipboardFlag = true;
	      }
	    });
	});

	//add by yangxiuwu end
 </script>

</body>
</html>
