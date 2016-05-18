/**
 * 图和标签页操作
 */

/** ****************************graph start************************************* */
var graphManager = {
	/* 展示详细图 */
	showDetail : function() {
		var uuidArr = pageVariate.openMap.get(pageVariate.uuid);
		if(uuidArr){
			$.each(uuidArr,function(i,uuid){
				removeNode(uuid);
			});
		}
		updateGraphAndCache();
		pageVariate.openMap.remove(pageVariate.uuid);
		$.ajax({
			type : "post",
			dataType : "json",
			url : pageVariate.base+"query/getDetailById.action",
			data : {
				personId : pageVariate.personId,
				maxIndex : nodes.length-1,
				nodeIndex : findNodeIndex(pageVariate.uuid)
			},
            async : false,
			success : function(data) {
				var detailData = eval(data);
				var arr = [];
				if(uuidArr && uuidArr.length >= detailData.nodes.length){
					var idArray = [];
					$.each(detailData.nodes,function(i,node){
						idArray.push(node.id);
					});
					searchManager.deleteIdCache(idArray);
				}else{
					expandNode(detailData.nodes,detailData.edges);
					$.each(detailData.nodes,function(i,node){
						arr.push(node.uuid);
					});
					pageVariate.openMap.put(pageVariate.uuid,arr);
				}
			},
			error : function() {
				hintManager.showHint("服务器繁忙，请稍后重试！");
			}
		});
	},
	/* 查询条件节点显示-first */
	showDetail_first : function() {
		var content = $('#content').val();
		var propertySel = $(".select_txt").attr("name");
		$.ajax({
			type : "post",
			dataType : "json",
			url : pageVariate.base+"query/getDetailById.action",
			data : {
				property:propertySel,
	        	content:content,
				personId : pageVariate.personId,
				maxIndex : nodes.length-1,
				nodeIndex : findNodeIndex(pageVariate.uuid)
			},
       		async : false,
			success : function(data) {
				var detailData = eval(data);
				expandNode(detailData.nodes,detailData.edges);
				var arr = [];
				$.each(detailData.nodes,function(i,node){
					arr.push(node.uuid);
				});
				pageVariate.openMap.put(pageVariate.uuid,arr);
			},
			error : function() {
				hintManager.showHint("服务器繁忙，请稍后重试！");
			}
		});
	},
	/* 根据人物ID查询事件 */
	showEvent : function(types, start, end) {
		$.ajax({
			type : "post",
			async: false,
			dataType : "json",
			url : pageVariate.base+"query/getEventById.action",
			data : {
				personId : pageVariate.personId,
				types : types.toString(),
				startTime : start,
				endTime : end,
				maxIndex : nodes.length-1,
				nodeIndex : findNodeIndex(pageVariate.uuid),
				date : new Date().getTime()
			},
			success : function(data) {
				var eventData = eval(data);
				expandNode(eventData.nodes,eventData.edges);
			},
			error : function() {
				hintManager.showHint("未查询到人物相关的事件！");
			}
		});
	},
	/* 人物关联 */
	showConnect : function() {
		if(pageVariate.selNode.length != 2){
			return hintManager.showHint("关联只能选择两个人物节点！");
		}
		
		$("#selectEdges").show();
		$(".selectEdgesDetail").next().empty();
	  	$(".selectEdgesList").empty();
	  	$(".selectedEdgesProperty").empty();
		$.ajax({
			type:"post",
			url:pageVariate.base+"/dataManage/getTitanStructure.action",
			success:function(msg){
				var data = {
					nodes : msg[0],
					edges : msg[1]
				}
				for(var i in data.edges){
					var item = $('<div class="item" title="' + i + '" extend="' + i + '" clicked="false">' +
									'<img src="images/img/'+i+'.png" style="width: 100px;height: 100px;" onerror="javascript:this.src=\'images/img/default.png\'">' +
									'<span>' + i + '</span>' +
								'</div>');
					item.click(function(){
						var _this = this;
						$(".selectedEdgesProperty").empty();
						$(_this).parent().children().css("border", "solid 1px rgba(0, 0, 0, 0)");
						$(_this).css("border", "solid 1px red");
						$(_this).parent().children().attr("clicked", "false");
						$(_this).attr("clicked", "true");
						var type = $(_this).find("span").html();
						selectEdges = type; 
						for(var j = 0; j < data.edges[type].length; j ++){
							var proName = data.edges[type][j].name;
							var proType = data.edges[type][j].type;
							var proStrong = data.edges[type][j].strong || 0;
							var proIndex = data.edges[type][j].index || 0;
							$(".selectedEdgesProperty").append('<div class="item">' +
																	'<div title="名称：'+proName+'">名称：<span>'+proName+'</span></div>' +
																	'<div title="类型：'+proType+'">类型：<span>'+proType+'</span></div>' +
																	'<div title="属强：'+proStrong+'">属强：<span>'+proStrong+'</span></div>' +
																	'<div title="索引：'+proIndex+'">索引：<span>'+proIndex+'</span></div>' +
																'</div>');
						}
						var addNewEdgesProperty = $('<div class="item">' +
														'<div>+</div>' +
													'</div>');
						addNewEdgesProperty.click(function(){
							$("#selectEdges").hide();
							initBoxAddEdges();
							$("#box_addEdge").show();
							$.ajax({
								type:"post",
								url:pageVariate.base+"/dataManage/getTitanStructure.action",
								success:function(msg){
									var modelList = $("#box_addEdge .box1 .modelListPar .modelList.property");
									modelList.remove();
									var data = {
										edges : msg[1]
									}
									for(var type in data.edges){
										if(type == selectEdges){
											$("#box_addEdge .box1 .modelDetail .addModelName").val(selectEdges);
											$("#box_addEdge .box1 .modelDetail .modelName .addModelName").attr("disabled", "disabled");
											$("#box_addEdge .box1 .modelDetail .modelName").find("select").attr("disabled", "disabled");
											var selectClass = data.edges[selectEdges];
											selectClass.push({
												name : "",
												type : "string",
												strong : 0,
												index : 0
											});
											for(var x = 0; x < selectClass.length; x ++){
												var propertyObj = $('<div class="modelList property">' +
																		'<div class="item">线型属性：</div>' +
																		'<input type="text" value="'+selectClass[x].name+'" class="addModelProperty" disabled="disabled"/>' +
																		'<select disabled="disabled">' +
																			'<option>string</option>' +
																			'<option>Number</option>' +
																			'<option>Varchar2</option>' +
																		'</select>' +
																		'<input type="checkbox" class="strong" disabled="disabled"/>' +
																		'<input type="checkbox" class="isIndex" disabled="disabled"/>' +
																	'</div>');
												var options = propertyObj.find("option");
												for(option = 0; option < options.length; option ++){
													if($(options[option]).html() == selectClass[x].type){
														$(options[option]).attr("selected", true);
													}
												}
												if(selectClass[x].strong == 1){
													propertyObj.find(".strong").attr("checked", true);
												}
												if(selectClass[x].index == 1){
													propertyObj.find(".isIndex").attr("checked", true);
												}
												if(selectClass[x].name == ""){
													var delPropertyObj = $('<div class="Property del">X</div>');
													delPropertyObj.click(function(){
														var _this = this;
														$(_this).parent().remove();
													});
													propertyObj.append(delPropertyObj);
													propertyObj.find(".addModelProperty").removeAttr("disabled");
													propertyObj.find("select").removeAttr("disabled");
													propertyObj.find(".strong").removeAttr("disabled");
													propertyObj.find(".isIndex").removeAttr("disabled");
												}
												modelList = $("#box_addEdge .box1 .modelListPar .modelList.property");
												if(modelList.length == 0){
													$("#box_addEdge .box1 .modelListPar").prepend(propertyObj);
												}else{
													$(modelList[modelList.length-1]).after(propertyObj);
												}
											}
										}
									}
								}
							});
						});
						$(".selectedEdgesProperty").append(addNewEdgesProperty);
					});
					item.hover(function(){
						var _this = this;
						$(_this).css("border", "solid 1px red");
						var modifyObj = $('<div class="function" style="position: absolute;left: 7px;bottom: 20px;">修改</div>');
						modifyObj.click(function(){
							var id = $(_this).attr("extend");
							selectEdges = id;
							$("#selectEdges").hide();
							initBoxAddEdges();
							$("#box_addEdge").show();
							$.ajax({
								type:"post",
								url:pageVariate.base+"/dataManage/getTitanStructure.action",
								success:function(msg){
									var modelList = $("#box_addEdge .box1 .modelListPar .modelList.property");
									modelList.remove();
									var data = {
										edges : msg[1]
									}
									for(var type in data.edges){
										if(type == selectEdges){
											$("#box_addEdge .box1 .modelDetail .addModelName").val(selectEdges);
											$("#box_addEdge .box1 .modelDetail .modelName .addModelName").attr("disabled", "disabled");
											$("#box_addEdge .box1 .modelDetail .modelName").find("select").attr("disabled", "disabled");
											var selectClass = data.edges[selectEdges];
											for(var x = 0; x < selectClass.length; x ++){
												var propertyObj = $('<div class="modelList property">' +
																		'<div class="item">线型属性：</div>' +
																		'<input type="text" value="'+selectClass[x].name+'" class="addModelProperty"/>' +
																		'<select>' +
																			'<option>string</option>' +
																			'<option>Number</option>' +
																			'<option>Varchar2</option>' +
																		'</select>' +
																		'<input type="checkbox" class="strong"/>' +
																		'<input type="checkbox" class="isIndex"/>' +
																	'</div>');
												var options = propertyObj.find("option");
												for(option = 0; option < options.length; option ++){
													if($(options[option]).html() == selectClass[x].type){
														$(options[option]).attr("selected", true);
													}
												}
												if(selectClass[x].strong == 1){
													propertyObj.find(".strong").attr("checked", true);
												}
												if(selectClass[x].index == 1){
													propertyObj.find(".isIndex").attr("checked", true);
												}
												var delPropertyObj = $('<div class="Property del">X</div>');
												delPropertyObj.click(function(){
													var _this = this;
													$(_this).parent().remove();
												});
												propertyObj.append(delPropertyObj);
												modelList = $("#box_addEdge .box1 .modelListPar .modelList.property");
												if(modelList.length == 0){
													$("#box_addEdge .box1 .modelListPar").prepend(propertyObj);
												}else{
													$(modelList[modelList.length-1]).after(propertyObj);
												}
											}
										}
									}
								}
							});
						});
						$(_this).append(modifyObj);
						var deleteObj = $('<div class="function" style="position: absolute;right: 7px;bottom: 20px;">删除</div>');
						deleteObj.click(function(){
							var id = $(_this).attr("extend");
							selectEdges = id;
							var DBName = $(_this).attr("title");
							confirm("删除该类型，将会关联此数据库未执行的任务一并删除，是否确认删除："+DBName,function(){
								$(_this).remove();
								/* $.ajax({
									type : "post",
									url : pageVariate.base + "/ImportDelete.action",
									data : data.dataBaseJson[selectEdges],
									success : function(taskID){
										hintManager.showSuccessHint("删除DB配置成功!");
										$(_this).remove();
									}
								}); */
							});
						});
						$(_this).append(deleteObj);
					},
					function(){
						var _this = this;
						var clicked = $(_this).attr("clicked");
						if("false" == clicked){
							$(_this).css("border", "solid 1px rgba(0, 0, 0, 0)");
						}
						$(_this).find(".function").remove();
					});
					$(".selectEdgesList").append(item);
				}
				var addNewModel = $('<div class="item">' +
										'<img src="images/img/Person.png" style="width: 100px;height: 100px;margin: 0 5px;" onerror="javascript:this.src=\'images/img/default.png\'">' +
										'<span>添加新线型</span>' +
									'</div>');
				addNewModel.click(function(){
					$("#selectEdges").hide();
					initBoxAddEdges();
					$("#box_addEdge").show();
				});
				$(".selectEdgesList").append(addNewModel);
				var suerBtn = $('<input type="button" class="xgBtn" value="确定" />');
				suerBtn.click(function(){
					var _this = this;
					if(selectEdges == ""){
						alert("请选择模型类型!!");
					}else{
						var propertyList = new Array();
						for(var property = 0; property < data.edges[selectEdges].length; property ++){
							var name = data.edges[selectEdges][property].name;
							var type = data.edges[selectEdges][property].type;
							var strong = data.edges[selectEdges][property].strong || 0;
							var isIndex = data.edges[selectEdges][property].index || 0;
							propertyList.push({
								"propertyName" : name,
								"propertytype" : type,
								"isStrongProperty" : parseInt(strong),
								"isBuildIndex" : parseInt(isIndex),
								"databaseColumnId" : -1,
								"propertyId" : property,
								"propertyCardinality" : "SINGLE"
							})
						}
						var uuid = randomString(32);
						var sourceNO = nodes.indexOf(pageVariate.selNode[0]);
						var targetNO = nodes.indexOf(pageVariate.selNode[1]);
						var eventData = {
								nodes : [],
								edges : []
							};
						eventData.edges.push({
								"source":sourceNO,
								"target":targetNO,
								"direction":"out",
								"relation":selectEdges,
								"uuid": uuid,
								"custormPropertyList" : propertyList
							});
						/* GraphJSON.edges.push({
					            "_id": uuid,
					            "_type": "edge",
					            "_outV": targetNO,
					            "_inV": sourceNO,
					            "_label": uuid,
					            "property": {
					                "type": "list",
					                "value": propertyList
					            }
					        }); */
						GraphJSON.database[0].tableList[0].edgeList.push({
		                    "id": edges.length,
							"uuid" : uuid,
		                    "custormPropertyList": propertyList,
		                    "lableName": selectEdges,
							"inVertexId": sourceNO,
							"outVertexId": targetNO,
							"source":sourceNO,
							"target":targetNO,
							"direction":"out",
							"relation":selectEdges
						});
				       	expandNode(eventData.nodes,eventData.edges);
						$("#selectEdges").hide();
					}
				});
				$(".selectEdgesDetail").next().append(suerBtn);
				var cancelBtn = $('<input type="button" class="closeBtn" value="关闭" />');
				cancelBtn.click(function(){
					var _this = this;
					$("#selectEdges").hide();
				});
				$(".selectEdgesDetail").next().append(cancelBtn);
			}
		});
	},
	/* 展示关系网络图 */
	showRelative : function() {
		$.ajax({
			type : "post",
			dataType : "json",
			url : pageVariate.base+"query/getRelativeById.action",
			data : {
				personId : pageVariate.personId,
				maxIndex : nodes.length-1,
				nodeIndex : findNodeIndex(pageVariate.uuid),
				date : new Date().getTime()
			},
			dataType : "json",
			success : function(data) {
				var eventData = eval(data);
				expandNode(eventData.nodes,eventData.edges);
			},
			error : function() {
				hintManager.showHint("未查询到人物相关的关系网络！");
			}
		});
	},
	/* 根据查询的邮件展示图 */
	sendmailToGraph : function(ids) {
		$.ajax({
			type : "post",
			dataType : "json",
			url : pageVariate.base+"query/getMailRelativeByIds.action",
			data : {
				ids:ids.toString(),
				maxIndex : nodes.length-1,
				date : new Date().getTime()
			},
			dataType : "json",
			success : function(data) {
				var eventData = eval(data);
				if(pageVariate.fristFlag){
					 $('.displayDiv').show();
					 $('.zoomline').show();
		        	 graphDrawInit(eventData);
		        	 graphDrawUpdate();
		        	 pageVariate.fristFlag = false;
				}else{
					expandNode(eventData.nodes,eventData.edges);
				}
			},
			error : function() {
				hintManager.showHint("未查询到邮件及相关节点！");
			}
		});
	},
	/* 群组关系图 */
	showGroupRelative : function() {
		$.ajax({
			type : "post",
			dataType : "json",
			url : pageVariate.base+"query/getGroupRelativeById.action",
			data : {
				personId : pageVariate.personId,
				date : new Date().getTime()
			},
			dataType : "json",
			success : function(data) {
				
				var eventData = eval(data);
				
				if(eventData.nodes!=""){
					qqgroupcount+=1;
					$('#tt').tabs('add',{
						title: '群组',
						closable: true,
						content: "<div class='zoomQQline"+qqgroupcount+"' style='width:10px;z-index:999; height:10px;position: absolute; top:100px;left: 15px margin-top:-120px;  float:left;border:0px solid #F00'></div><div class='displayQQDiv"+qqgroupcount+"' style='width:100%; height:100%;border:0px solid #F00'><div>"
							
					});
					getQQgroup(eventData.nodes,eventData.edges,qqgroupcount);
				}else{
					hintManager.showHint("未查询到人物相关的QQ群信息！");
				}
				
			},
			error : function() {
				hintManager.showHint("未查询到人物相关的关系网络！");
			}
		});
	}
}

	function initBoxAddEdges(){
		$("#box_addEdge .box1").empty();
		$("#box_addEdge .box1").append('<h1>添加线型</h1>' +
										'<div class="modelDetail">' +
											'<div class="modelList modelName">' +
												'<div class="item">线型名称：</div>' +
												'<input type="text" class="addModelName"/>' +
												'<select>' +
													'<option>string</option>' +
													'<option>Number</option>' +
													'<option>Varchar2</option>' +
												'</select>' +
											'</div>' +
											'<div class="modelList">' +
												'<div class="item">属性名称&nbsp;&nbsp;&nbsp;&nbsp;</div>' +
												'<div class="item" style="width: 200px;">属性值</div>' +
												'<div class="item" style="width: 144px;">属性类型</div>' +
												'<div class="item" style="width: 55px;">属强</div>' +
												'<div class="item" style="width: 55px;">索引</div>' +
											'</div>' +
											'<div style="height: 1px;width: 100%;background: black;float: left;margin: 3px 0;"></div>' +
											'<div class="modelListPar"></div>' +
										'</div>' +
										'<div>' +
											'<input type="button" class="xgBtn" onclick="addEdgeDoSubmit()" value="确定" />' +
											'<input type="button" class="closeBtn"  onclick="addEdgeCloseWin()" value="关闭" />' +
										'</div>');
		$("#box_addEdge .box1 .modelDetail .addModelName").blur(function(){
			var _this = this;
			var inputValue = $(_this).val();
			if(inputValue){
				$.ajax({
					type:"post",
					url:pageVariate.base+"/dataManage/getTitanStructure.action",
					success:function(msg){
						var modelList = $("#box_addEdge .box1 .modelListPar .modelList.property");
						modelList.remove();
						var data = {
							nodes : msg[0]
						}
						for(var type in data.nodes){
							if(type == inputValue){
								var selectClass = data.nodes[inputValue];
								for(var x = 0; x < selectClass.length; x ++){
									var propertyObj = $('<div class="modelList property">' +
															'<div class="item">模型属性：</div>' +
															'<input type="text" value="'+selectClass[x].name+'" class="addModelProperty"/>' +
															'<select>' +
																'<option>string</option>' +
																'<option>Number</option>' +
																'<option>Varchar2</option>' +
															'</select>' +
															'<input type="checkbox" class="strong"/>' +
															'<input type="checkbox" class="isIndex"/>' +
														'</div>');
									var delPropertyObj = $('<div class="Property del">X</div>');
									delPropertyObj.click(function(){
										var _this = this;
										$(_this).parent().remove();
									});
									propertyObj.append(delPropertyObj);
									modelList = $("#box_addEdge .box1 .modelListPar .modelList.property");
									if(modelList.length == 0){
										$("#box_addEdge .box1 .modelListPar").prepend(propertyObj);
									}else{
										$(modelList[modelList.length-1]).after(propertyObj);
									}
								}
							}
						}
					}
				});
			}
		});
		var addPropertyObj = $('<div class="modelList" style="width: 50px;min-width: 50px;margin-left: 40%;">' +
									'+' +
								'</div>');
		addPropertyObj.click(function(){
			var propertyObj = $('<div class="modelList property">' +
									'<div class="item">模型属性：</div>' +
									'<input type="text" class="addModelProperty"/>' +
									'<select>' +
										'<option>string</option>' +
										'<option>Number</option>' +
										'<option>Varchar2</option>' +
									'</select>' +
									'<input type="checkbox" class="strong"/>' +
									'<input type="checkbox" class="isIndex"/>' +
								'</div>');
			var delPropertyObj = $('<div class="Property del">X</div>');
			delPropertyObj.click(function(){
				var _this = this;
				$(_this).parent().remove();
			});
			propertyObj.append(delPropertyObj);
			var modelList = $("#box_addEdge .box1 .modelListPar .modelList.property");
			if(modelList.length == 0){
				$("#box_addEdge .box1 .modelListPar").prepend(propertyObj);
			}else{
				$(modelList[modelList.length-1]).after(propertyObj);
			}
		});
		$("#box_addEdge .box1 .modelListPar").append(addPropertyObj);
	}

/******************************graph end************************************* */

/******************************svg  start************************************* */
	var graphWidth = $(".displayDiv").innerWidth() - 2,
		graphHeight = $(".displayDiv").innerHeight() - 2,
		img_w = 60,
		img_h = 60,
		shiftKey=false,
		text_dx = 0,
		text_dy =35,
		line_text_dx = 0,
		line_text_dy = 0,
		nodes = [],
		edges = [],
		svg = d3.select('.displayDiv').append('svg').attr('width', graphWidth).attr('height', graphHeight).attr('id', "displaySvg"),
		svg_g = svg.append("g");
		force = d3.layout.force().nodes(nodes).links(edges).size([ graphWidth, graphHeight ]).linkDistance(function(d){
			var sourceType = d.source.image.replace(/\//g, "").replace("img", "").replace(".png", "");
			var targetType = d.target.image.replace(/\//g, "").replace("img", "").replace(".png", "");
			if(sourceType == "QQ" && targetType == "Group"){
				return 1000;
			}else if(sourceType == "QQ" && targetType == "QQ"){
				return 300;
			}else if(sourceType == "Group" && targetType == "QQ"){
				return 80;
			}else if(sourceType == "Person" && targetType == "QQ"){
				return 60
			}else{
				return 200;
			}
		}).charge([-1000]),
		edges_line = svg_g.selectAll("line"),
		edges_text = svg_g.selectAll(".linetext"),
		edges_polygon=svg_g.selectAll("polygon"),
		nodes_img = svg_g.selectAll("image"),
		nodeEnter=svg_g.selectAll("g.node"),
		nodes_text = svg_g.selectAll(".nodetext"),
		graphZoom = d3.behavior.zoom().scaleExtent([0.4, 5]).on("zoom", zoomed).on("zoomend",zoomend);
/******************************全屏加缩放************************************* */	
		//缩放轴	
		var cirscale=0;
		var iszoomline=true;
		var cynow=0;
		//加号中心点的x坐标
		var zoom_x=30;
		//加号中心点的y坐标
		var zoom_y=100;
		//加号外层圆的半径
		var add_r=10;
		//拖动的实心圆的半径
		var drag_r=5;
		var scale = 1.1;
		var current_scale = 1.1; 
	    var currentY = 0;
	    //线的长度
	    var bar_len =100;
	    //缩放轴svg宽度
	    var zoom_line_width="50px";
	    //缩放轴svg高度
	    var zoom_line_height="265px";
	    
		var zoom_line=d3.select('.zoomline').append('svg').attr("width",zoom_line_width).attr("height",zoom_line_height).append("g").attr("cursor","pointer");  
		var add=zoom_line.append("g").attr("class","add");
		var cut=zoom_line.append("g").attr("class","cut");
		var hisGraph=zoom_line.append("g").attr("id", "addModel");
		hisGraph.append("svg:image")
	      .attr("width", 25)
	      .attr("height", 25)
	      .attr("x",17)
	      .attr("y",55)
	      .attr("xlink:href", "images/layout/hisGraph.png");
		var alldata=[{x1:zoom_x-6,y1:zoom_y-46,x2:zoom_x-1,y2:zoom_y-46,x3:zoom_x-6,y3:zoom_y-41},{x1:zoom_x+6,y1:zoom_y-46,x2:zoom_x+1,y2:zoom_y-46,x3:zoom_x+6,y3:zoom_y-41},{x1:zoom_x-6,y1:zoom_y-34,x2:zoom_x-1,y2:zoom_y-34,x3:zoom_x-6,y3:zoom_y-39},{x1:zoom_x+6,y1:zoom_y-34,x2:zoom_x+1,y2:zoom_y-34,x3:zoom_x+6,y3:zoom_y-39}];
		var normaldata=[{x1:zoom_x-1,y1:zoom_y-39,x2:zoom_x-1,y2:zoom_y-34,x3:zoom_x-6,y3:zoom_y-39},{x1:zoom_x+1,y1:zoom_y-39,x2:zoom_x+1,y2:zoom_y-34,x3:zoom_x+6,y3:zoom_y-39},{x1:zoom_x-1,y1:zoom_y-41,x2:zoom_x-1,y2:zoom_y-46,x3:zoom_x-6,y3:zoom_y-41},{x1:zoom_x+1,y1:zoom_y-41,x2:zoom_x+1,y2:zoom_y-46,x3:zoom_x+6,y3:zoom_y-41}];   
		var zoomlinedata=[{x1:zoom_x-6,y1:zoom_y-46,x2:zoom_x+6,y2:zoom_y-34},{x1:zoom_x+6,y1:zoom_y-46,x2:zoom_x-6,y2:zoom_y-34}]
		var zoomlinedata2=[{x1:zoom_x-6,y1:zoom_y-46,x2:zoom_x-2,y2:zoom_y-42},{x1:zoom_x+6,y1:zoom_y-34,x2:zoom_x+2,y2:zoom_y-38},{x1:zoom_x+6,y1:zoom_y-46,x2:zoom_x+2,y2:zoom_y-42},{x2:zoom_x-6,y2:zoom_y-34,x1:zoom_x-2,y1:zoom_y-38}]
		var scwidth=document.body.clientWidth;
		var scheight=document.body.clientHeight;
		//加
		add.append("circle")
		   .attr("cx",zoom_x)
		   .attr("cy",zoom_y)
		   .attr("fill","#f00")
		   .attr("fill-opacity","0.1")
		   .attr("r",add_r)
		   .attr("stroke","#fff");
		add.append("line")
		   .attr("x1",zoom_x-5)
		   .attr("y1",zoom_y)
		   .attr("x2",zoom_x+5)
		   .attr("y2",zoom_y)
		   .attr("stroke","#fff")
		   .attr("fill","none");

		add.append("line")
		   .attr("x1",zoom_x)
		   .attr("y1",zoom_y+5)
		   .attr("x2",zoom_x)
		   .attr("y2",zoom_y-5)
		   .attr("stroke","#fff")
		   .attr("fill","none");
		
		//轴
		zoom_line.append("line")
		   .attr("x1",zoom_x)
		   .attr("y1",(zoom_y+add_r))
		   .attr("x2",zoom_x)
		   .attr("y2",(zoom_y+add_r+bar_len))
		    .attr("stroke","#fff");
		
		
		//拖动
		var dragmove = function() {
			d3.event.sourceEvent.stopPropagation(); 
			  var dy = d3.event.y; 
	          var diffY = dy ;  //+currentY - 30 ; 
	          if(diffY  > (zoom_y+add_r+bar_len-drag_r)){//bar_len + border_len+ margin 
	              diffY = zoom_y+add_r+bar_len-drag_r;
	              return ;
	          }
	          if(diffY <(add_r+zoom_y+drag_r)){ //border_len+ margin 
	              diffY =add_r+zoom_y+drag_r;
	              return ;
	          } 
	          console.log((zoom_y+add_r+bar_len/2-drag_r-diffY)/(bar_len/2)*0.6) 
	           console.log((zoom_y+add_r+bar_len/2-drag_r-diffY)/(bar_len/2)*4) 
	          if((zoom_y+add_r+bar_len/2-drag_r-diffY)>0){
	        	  scale =((zoom_y+add_r+bar_len-drag_r-diffY)/(bar_len-drag_r-drag_r))*4+1; 
	          }else{
	        	  scale =(zoom_y+add_r+bar_len/2-drag_r-diffY)/(bar_len/2)*0.6+1
	          } 
	          cir.attr("cy",diffY);
	        /*svg.attr("transform","scale(" + scale + ")" );*/
			}
		var dragend = function() { 
			iszoomline=false;
			graphZoom.custom_scale(scale,[graphWidth/2,graphHeight/2]);
			iszoomline=true;
			}
		//拖动的实心圆
		var cir=zoom_line.append("circle")
		   .attr("cx",zoom_x)
		   .attr("cy",(zoom_y+add_r+bar_len/2-drag_r))
		   .attr("fill","#009cFF")
		   .attr("r",6)
		   .call(d3.behavior.drag().on("drag",dragmove).on("dragend",dragend));

		//减
		cut.append("circle")
		   .attr("cx",zoom_x)
		   .attr("cy",(zoom_y+bar_len+add_r+add_r))
		    .attr("stroke","#fff")
		   .attr("fill-opacity","0.1")
		   .attr("fill","#061431")
		   .attr("r",add_r);
		cut.append("line")
		   .attr("x1",zoom_x-5)
		   .attr("y1",(zoom_y+bar_len+add_r+add_r))
		   .attr("x2",zoom_x+5)
		   .attr("y2",(zoom_y+bar_len+add_r+add_r))
		   .attr("stroke","#fff");
		add.on("click",function(){
			graphZoom.custom_scale((graphZoom.scale()+1),[graphWidth/2,graphHeight/2])
		});
		cut.on("click",function (){
			if(graphZoom.scale()>1){
				graphZoom.custom_scale((graphZoom.scale()-1),[graphWidth/2,graphHeight/2]);
			}else{
				graphZoom.custom_scale((graphZoom.scale()-0.6/4),[graphWidth/2,graphHeight/2]);
			}						
		});
		function zoomend() {
			if(iszoomline){
				var cy;	
				if(cirscale>0){
					 cy=zoom_y+add_r+bar_len/2-drag_r-((bar_len/2)*cirscale/5);
				}else{
					 cy=zoom_y+add_r+bar_len/2-drag_r-((bar_len/2)*cirscale/0.6);
				}
				console.log(cirscale)
				console.log(zoom_y+add_r+bar_len-drag_r)
				console.log(cy)
	            if(cy  > (zoom_y+add_r+bar_len-drag_r)){//bar_len + border_len+ margin 
	        	  cy = zoom_y+add_r+bar_len-drag_r;
	            }
	            if(cy <(add_r+zoom_y+drag_r)){ //border_len+ margin 
	         	  cy =add_r+zoom_y+drag_r;
	            } 
			    cir.attr("cy",cy);
			}
		}
/******************************全屏加缩放************************************* */		
	function zoomed() {
		cirscale = d3.event.scale-1;	
		svg_g.attr("transform","translate(" + d3.event.translate + ")scale(" + d3.event.scale + ")");
	}
	
	function graphDrawInit(data){
		nodes = data.nodes;
		edges = data.edges;
		svg.call(graphZoom).on("dblclick.zoom", null);
		
		svg.on('click',function(){
			if(!pageVariate.isClickNode){
				clearSelStatus();
				tabsManager.clearShow(".personPage");
				tabsManager.clearShow(".maincon");
				tabsManager.histogramShow(1,nodes);
				$('.first a').click();
			}
			pageVariate.isClickNode = false;
		});
		//区域选中
		svg.on('dblclick',function(){
			svg.on('.zoom',null);
			svg_g.datum(function() { return {selected: false, previouslySelected: false}; })
			.call(d3.svg.brush()
	        .x(d3.scale.identity().domain([-1000000, 1000000]))
	        .y(d3.scale.identity().domain([-1000000, 1000000]))
	        .on("brushstart", function(d) {
	        	nodeEnter = svg_g.selectAll("g.node");
        		pageVariate.selNode = [];
        		$("rect").css("fill-opacity", ".1").css("stroke", "#009bfa");  
        		nodeEnter.each(function(d) { d.previouslySelected = shiftKey && d.selected; });
        	 })
        	 .on("brush", function() {
		          var extent = d3.event.target.extent();
		          nodeEnter.classed("selected", function(d) {
		            if( d.selected = d.previouslySelected ^(extent[0][0] <= d.x && d.x < extent[1][0]&& extent[0][1] <= d.y && d.y < extent[1][1])){
		            	brushNode(d);
		            }
		          });
	 		  })
	 		  .on("brushend", function() {
		        	svg_g.style("fill-opacity", "1");
			        d3.event.target.clear();
			        d3.select(this).call(d3.event.target);
			        svg_g.on('.brush',null);
		        	$(".background").remove();
		        	svg.call(graphZoom).on("dblclick.zoom", null);
		        	svg_g.classed("brush",false);
	        	})
	        );
					
		});
	}
	
	function clearSelStatus(){
		// 1、更改图片信息
		for(var i = 0; i < pageVariate.selNode.length; i ++){
			var oldImage = $(".n"+ pageVariate.selNode[i].uuid +" image").attr("href");
			var newImage = oldImage.replace("Sel.png", ".png");
			$(".n"+ pageVariate.selNode[i].uuid +" image").attr("href", newImage);
		}
		// 2、清空缓存的选中节点
		pageVariate.selNode = [];
	}
	
	function graphDrawUpdate() {
		force.nodes(nodes).links(edges);
		edges_text = edges_text.data(edges, function(d){ return d.uuid; });
		text = edges_text.enter().append("text")
				.attr("class", "linetext")
				.attr("dx", line_text_dx)
				.attr("dy", line_text_dy)
				.style("text-anchor","middle");
				//.attr("x",function(d){ return (d.source.x + d.target.x) / 2; });

		text.selectAll("tspan")
			.data(function(d) {
				return [
						{data:d.relation,dy:"-2"}
					]
				/* var relation=d.relation;
				if(relation.substr(0,8)=="relation"){
					return [
						        {data:"relation",dy:"-2"},
						        {data:d.relation.substr(9,d.relation.length),dy:"1em"}
					       ]
				}else if(relation.substr(0,7)=="connect"){
					return [{data:"connect",dy:"-2"},{data:relation.substr(8,relation.length),dy:"1em"}]
				}else if(relation.substr(0,8)=="nickname"){
					return [{data:relation.substr(9,relation.length),dy:"12"}]
				}else{
					return [{data:" ",dy:"-2"},{data:" ",dy:"1em"}]
				} */
			})
			.enter()
			.append("tspan")
			.attr("x",'0')
			.attr("dy",function(d){
				return d.dy;
			})
			.style("text-anchor","middle")
			.text(function(d){
				return d.data;
			});
		edges_text.exit().remove();
		
		//提示框tip
		var tip = d3.tip()
			.attr("class","d3-tip")
			.html(function(d){
				 var tempArr;
				 var id = d.id;
				 var type = d.type;
				 if(typeof(id) == "string") tempArr = id.split("-");
				 var subList = d.subList
				 if(type=="Person"){return "name: "+d.name?d.name:""}
				 if(subList){
					 if(type=="EmailEvent"){return "count: "+subList.length+"<br/>"+ "from: "+tempArr[0]+"<br/>"+"to: "+tempArr[1]}
					 if(type=="LoginEvent" || type=="StayEvent"){return "count: "+subList.length}
					 if(type=="CallEvent"){return "count: "+subList.length+"<br/>"+"from: "+tempArr[0]+"<br/>"+"to: "+tempArr[1]}
				 }else{
	//							 return "type: "+type;
					 return "";
				 }
			});
		svg_g.call(tip);
		
		//判断鼠标双击
		var isdb;
		//固定顶点
		var isdrag=true;

		var drag = force.drag()
			.on("dragstart", function(d, i) {
				d3.event.sourceEvent.stopPropagation();

				d.fixed = true; //拖拽开始后设定被拖拽对象为固定
			})
			.on("drag",function(d,i){
			 	d.isdrag2=d.x+d.y;
			})
			.on("drag.force", function(d,i){//add by hanxue 重写“drag.force”方法 用于禁止按住右键时拖拽节点
		     if(forceWhich!=3){
		    	 d.px = d3.event.x, d.py = d3.event.y;
			     force.resume();
		    }
		 });
		
		//绘制结点
		 var node =svg_g.selectAll("g.node").data(nodes, function(d) { return d.uuid;});
		 nodeEnter = node.enter().append("svg:g")
	        .attr("class", function(d) {
				return "node n" + d.uuid;
			})
	        .call(drag)
	        .on('click', function(d) {
				isdb=false;
				if(isdb!=false)
					return;
				if(d.isdrag!=d.isdrag2){
					d.isdrag=d.isdrag2;
					return;
				}
				clickNode(d);
				pageVariate.isClickNode = true;
			})
			.on("dblclick", function(d, i) {
				 isdb=true;
				 d3.event.stopPropagation();
				 d.fixed = false;
				 pageVariate.isClickNode = true;
			})
			.on("mousedown", function(d,i) {
				forceWhich = d3.event.which;//add by hanxue 记录鼠标左右键，用于禁止按住右键时拖拽节点
				moused(d3.event,d);
			})
			.on("mouseover", function(d){
                var _this = this;
                var transform = $(_this).attr("transform").split("scale")[0];
                $(_this).attr("transform",transform+"scale(1.1)");
            })
            .on("mouseout", function(d){
            	var _this = this;
            	var transform = $(_this).attr("transform").split("scale")[0];
                $(_this).attr("transform",transform+"scale(1.0)");
            })
			.attr("x",function(d,i){return i+100;})
			.attr("y",function(d,i){return i+100});		 
	
		 var moused = function(e,d){
			 if(e.which == 3){
				pageVariate.personId = d.id;
	            pageVariate.uuid = d.uuid;
	            pageVariate.currentNode = d;
				showMenu(e,$(".n"+d.uuid +" image"));//添加图片对象 方便定位
	          }
		}
		 nodeEnter.append("svg:image")
	      .attr("width", img_w)
	      .attr("height", img_h)
	      .attr("xlink:href", function(d) {
					return "images/" + d.image;
				})
		  .on("mouseover",tip.show)
		  .on("mouseout",tip.hide);
		 text = nodeEnter.append("svg:text")
	     .attr("class", "nodetext")
	     .attr("transform","translate(24,57)")
	  /*   .attr("dx", line_text_dx)
	     .attr("dy", 40)
	     .style("text-anchor","middle");*/
	     //.attr("x",function(d){ return (d.source.x + d.target.x) / 2; });

		text.selectAll("tspan")
		.data(function(d) {
			var subList = d.subList;
			if(d.type=="Person" || d.type=="own"){
				  return [{data:d.name,dy:"1em",c:"name"}];
			}else if(d.type=="CallEvent" || d.type=="EmailEvent" || d.type=="LoginEvent" || d.type=="StayEvent"){
				if(subList){
					return [{data:subList.length,dy:"1em",c:"count"}];
				}else{
					return [{data:"",c:"name"}];
				}
			}else if(d.type=="QQ" || d.type=="Group"){
				return [{data:d.name,c:"name"}];
			}else{
				return [{data:d.name,dy:"1em",c:"name"}];
			}
		})
		.enter()
		.append("tspan")
		.attr("x",'0')
//		.attr("dx",function(d) {return d.dx;})
		.attr("dy",function(d) {return d.dy;})
		.attr("class",function(d) {return d.c;})
		.attr("text-anchor","middle")
		.text(function(d) {
			return d.data;
		});
		node.exit().remove();
		
		  //绘制连接线	
		edges_line = edges_line.data(edges, function(d){ return d.uuid; });
		edges_line.enter().append("line").style("stroke", "#004F89").style("stroke-width", 2);
		edges_line.on('click', function(d) {
			clickEdge(d);
		})
		.on("mouseover", function(d){
            var _this = this;
            $(_this).css({"stroke":"red","cursor": "pointer"});
        })
        .on("mouseout", function(d){
        	var _this = this;
        	$(_this).css({"stroke":"rgb(0, 79, 137)","cursor": "none"});
        })
		edges_line.exit().remove();
		
		edges_polygon=edges_polygon.data(edges,function(d){return d.uuid;});
		edges_polygon.enter().append("polygon").attr("fill","#3333FF").attr("stroke-width",3);
		edges_polygon.exit().remove();
		/*nodes_img = nodes_img.data(nodes, function(d){ return d.uuid; });
		nodes_img.enter().append(
				"image").attr("width", img_w).attr("height", img_h).attr(
				"xlink:href", function(d) {
					return "images/" + d.image;
				})
				.attr("class", function(d) {
					return d.uuid;
				})
				.call(drag)
				.on('click', function(d) {
					 isdb=false;
					 window.setTimeout(cc, 200);
				  function cc(){	 
					if(isdb!=false)return;
					if(d.isdrag!=d.isdrag2){d.isdrag=d.isdrag2;return;};
					clickNode(d);
				    }
				})
				.on("mouseover",tip.show)
				.on("mouseout",tip.hide);
		//给nodes绑定双击事件，解除拖动固定				
		nodes_img.on("dblclick", function(d, i) {
			 isdb=true;
			d3.event.stopPropagation();
			d.fixed = false;
		})
		nodes_img.on("mousedown", function(d,i) {
			if("Person" == d.type){
				moused(d3.event,d);
			}
		})
		var moused = function(e,d){
			 if(e.which == 3){
				 //当前没有选中的呼出菜单选中，操作完成还原
				var index = pageVariate.selNode.indexOf(d);
				if(index>-1){
					pageVariate.currentSelFlag = true;
				}else{
					$(".n"+d.uuid +" image").attr("href", "images/img/"+d.type+"Sel.png");
					pageVariate.selNode.push(d);
				}
				showMenu(e);
	            pageVariate.personId = d.id;
	            pageVariate.uuid = d.uuid;
	          }
		}
		nodes_img.exit().remove();

		nodes_text = nodes_text.data(nodes, function(d){ return d.uuid; });*/
		/*nodes_text.enter().append(
				"text").attr("class", "nodetext").attr("dx", text_dx).attr(
				"dy", text_dy).text(function(d) {
			return d.name;
		});*/
		/*text = nodes_text.enter()
						 .append("text")
					     .attr("class", "nodetext")
					     .attr("dx", line_text_dx)
					     .attr("dy", line_text_dy)
					     .style("text-anchor","middle");
					     //.attr("x",function(d){ return (d.source.x + d.target.x) / 2; });

		text.selectAll("tspan")
	        .data(function(d) {
	      	  if(d.type=="Person" || d.type=="own"){
	      		  return [{data:d.name,dy:"32",c:"name"}];
	      	  }else if(d.type=="EmailEvent"){
	      		  return [{data:d.title,dy:"32",c:"title"},{data:d.time,dy:"1em",c:"time"}];
	      	  }else if(d.type=="LoginEvent"){
	      		  return [{data:d.domain,dy:"32",c:"title"},{data:d.time,dy:"1em",c:"time"}];
	      	  }else if(d.type=="CallEvent"){
	      		  return [{data:d.time,dy:"32",c:"time"},{data:d.long,dy:"1em",c:"long"}];
	      	  }else{
	      		 return [{data:"",dy:"32",c:"name"}];
	      	  }
		  	})
	        .enter()
	        .append("tspan")
	        .attr("x",'0')
	        .attr("dx",function(d) {return d.dx;})
	        .attr("dy",function(d) {return d.dy;})
	        .attr("class",function(d) {return d.c;})
	        .style("text-anchor","middle")
	        .text(function(d) {return d.data;});
		nodes_text.exit().remove();*/
        
		force.start();
		tabsManager.histogramShow(1,pageVariate.selNode);
		$('.first a').click();
		maskManager.hide();
    }
	
	/**还原按钮状态*/
	function resetCurrentNode(){
		$("#menuDiv").html("");
		if(pageVariate.currentSelFlag){
			pageVariate.currentSelFlag = false;
		}else{
			var type = pageVariate.currentNode.image.replace(/\//g, "").replace("img", "").replace(".png", "");
			pageVariate.selNode.remove(pageVariate.currentNode);
			$(".n"+pageVariate.currentNode.uuid +" image").attr("href", "images/img/"+type+".png");
		}
	}
	
	
	function clickEdge(d){
		var propertyList = d.custormPropertyList;
		// if(pageVariate.currentNode == d){
			// var index = pageVariate.selNode.indexOf(d);
			// if(index < 0){
				// pageVariate.selNode.push(d);
				// $(".n"+d.uuid +" image").attr("href", "images/img/"+type+"Sel.png");
				// pageVariate.currentNode = d;
			// }else{
				// pageVariate.selNode.splice(index, 1);
				// $(".n"+d.uuid +" image").attr("href", "images/img/"+type+".png");
				// pageVariate.currentNode = "";
			// }
		// }else{
			// if(!pageVariate.pressCtrl){
				// clearSelStatus();
				// pageVariate.currentNode = "";
			// }
			// pageVariate.selNode.push(d);
			// $(".n"+d.uuid +" image").attr("href", "images/img/"+type+"Sel.png");
			// pageVariate.currentNode = d;
		// }
		
		$("#mapping").empty();
		for(var i = 0; i < propertyList.length; i ++){
			var columnObjs = $(".down_left.clearfix .column");
				var columnMap = $('<div class="item">' +
									'<div class="id">'+propertyList[i].propertyName+'</div>' +
								'</div>');
				var select_box = $('<select class="option" style="float: left;">' +
										'<option selected=true class="" type="">请选择属性</option>' +
									'</select>');
									
				select_box.change(function(){
					var _this = this;
					var property = $(this).prev().html();
					var columnPropertyList = GraphJSON.database[0].tableList[0].columnPropertyList;
					var currentVal = $(_this).children('option:selected').val();
					var edgeList = GraphJSON.database[0].tableList[0].edgeList;
					for(var i = 0; i < edgeList.length; i ++){
						var edge = edgeList[i];
						if(d.uuid == edge.uuid){
							for(var j = 0; j < edge.custormPropertyList.length; j ++){
								if(edge.custormPropertyList[j].propertyName == property){
									for(var column = 0; column < columnPropertyList.length; column ++){
										if(columnPropertyList[column].columnName == currentVal){
											edge.custormPropertyList[j].databaseColumnId = columnPropertyList[column].columnId;
											edge.custormPropertyList[j].databaseColumnValue = columnPropertyList[column].columnName;
										}
									}
								}
							}
						}
					}
				});
			
				for(var columnObj = 0; columnObj < columnObjs.length; columnObj ++){
					var columnName = $(columnObjs[columnObj]).html();
					var span = $('<option class="" name="">'+ columnName +'</option>');
					select_box.append(span);
				}
				
				columnMap.append(select_box);
				var delPropertyMap = $('<div class="" style="float: left;line-height: 32px;width: 50px;cursor: pointer;">X</div>');
				delPropertyMap.click(function(){
					var _this = this;
					$(_this).parent().remove();
				});
				columnMap.append(delPropertyMap);
				$("#mapping").append(columnMap);
			
		}
	}
	
	/**点击选中*/
	function clickNode(d){
		var type = d.type;
		var propertyList = d.custormVertexPropertyList;
		if(pageVariate.currentNode == d){
			var index = pageVariate.selNode.indexOf(d);
			if(index < 0){
				pageVariate.selNode.push(d);
				$(".n"+d.uuid +" image").attr("href", "images/img/"+type+"Sel.png");
				pageVariate.currentNode = d;
			}else{
				pageVariate.selNode.splice(index, 1);
				$(".n"+d.uuid +" image").attr("href", "images/img/"+type+".png");
				pageVariate.currentNode = "";
			}
		}else{
			if(!pageVariate.pressCtrl){
				clearSelStatus();
				pageVariate.currentNode = "";
			}
			pageVariate.selNode.push(d);
			$(".n"+d.uuid +" image").attr("href", "images/img/"+type+"Sel.png");
			pageVariate.currentNode = d;
		}
		
		if($(".n"+d.uuid +" image").attr("href").indexOf("Sel.png") > 0){
			$("#mapping").empty();
			for(var i = 0; i < propertyList.length; i ++){
				var columnObjs = $(".down_left.clearfix .column");
				var columnMap = $('<div class="item">' +
									'<div class="id">'+propertyList[i].propertyName+'</div>' +
								'</div>');
				var select_box = $('<select class="option" style="float: left;">' +
										'<option selected=true class="" type="">请选择属性</option>' +
									'</select>');
				select_box.change(function(){
					var _this = this;
					var property = $(this).prev().html();
					var columnPropertyList = GraphJSON.database[0].tableList[0].columnPropertyList;
					var currentVal = $(_this).children('option:selected').val();
					var vertexList = GraphJSON.database[0].tableList[0].vertexList;
					for(var i = 0; i < vertexList.length; i ++){
						var vertex = vertexList[i];
						if(pageVariate.currentNode.uuid == vertex.uuid){
							for(var j = 0; j < vertex.custormVertexPropertyList.length; j ++){
								if(vertex.custormVertexPropertyList[j].propertyName == property){
									for(var column = 0; column < columnPropertyList.length; column ++){
										if(columnPropertyList[column].columnName == currentVal){
											vertex.custormVertexPropertyList[j].databaseColumnId = columnPropertyList[column].columnId;
											vertex.custormVertexPropertyList[j].databaseColumnValue = columnPropertyList[column].columnName;
										}
									}
								}
							}
						}
					}
				});
				for(var columnObj = 0; columnObj < columnObjs.length; columnObj ++){
					var columnName = $(columnObjs[columnObj]).html();
					var span = $('<option class="" name="">'+ columnName +'</option>');
					select_box.append(span);
				}
				columnMap.append(select_box);
				select_box.get(0).selectedIndex = propertyList[i].databaseColumnId + 1;
				var delPropertyMap = $('<div class="" style="float: left;line-height: 32px;width: 50px;cursor: pointer;">X</div>');
				delPropertyMap.click(function(){
					var _this = this;
					$(_this).parent().remove();
				});
				columnMap.append(delPropertyMap);
				$("#mapping").append(columnMap);
			}
		}
		
		/* if($(".n"+d.uuid +" image").attr("href").indexOf("Sel.png") > 0){
			for(var x = 0; x < $("#mapping .select_box .select_txt").length; x ++){
				if($($("#mapping .select_box .select_txt")[x]).html() == "请选择属性"){
					var optionEle = $($("#mapping .select_box .select_txt")[x]).next();
					optionEle.empty();
					optionEle.append('<span class="" type="">请选择属性</span>');
					for(var i = 0; i < propertyList.length; i ++){
						var span = $('<span class="" extend="'+ pageVariate.currentNode.uuid +'" name="'+propertyList[i].value+'">'+ pageVariate.currentNode.type + "__" +propertyList[i].value+'</span>');
						optionEle.append(span);
					}
					optionEle.find("span").click(function(){
						var _this = this;
						var value = $(_this).text();
						var extend = $(_this).attr('extend');
						var name = $(_this).attr('name');
						$(_this).parent().siblings(".select_txt").text(value);
						$(_this).parent().siblings(".select_txt").attr("extend", extend);
						$(_this).parent().siblings(".select_txt").attr("name", name);
					});
				}
			}
		}
		if(pageVariate.selNode.length < 1){
			for(var x = 0; x < $("#mapping .select_box .select_txt").length; x ++){
				if($($("#mapping .select_box .select_txt")[x]).html() == "请选择属性"){
					var optionEle = $($("#mapping .select_box .select_txt")[x]).next();
					optionEle.empty();
					optionEle.append('<span class="" type="">请选择属性</span>');
					optionEle.find("span").click(function(){
						var _this = this;
						var value=$(_this).text();
						$(_this).parent().siblings(".select_txt").text(value);
					});
				}
			}
		} */
	}
	
	/**点框选选中*/
	function brushNode(d){
		var type = d.type;
		if("own" != type){
			var index = pageVariate.selNode.indexOf(d);
			if(index>-1){
			}else{
				$(".n"+d.uuid +" image").attr("href", "images/img/"+type+"Sel.png");
				pageVariate.selNode.push(d);
			}
		}
		tabsManager.histogramShow(0,pageVariate.selNode);
	}
	
	/**节点展开*/
    function expandNode(childNodes,childLinks){
        childNodes.forEach(function(node){
        	nodes.push(node);
        });

         childLinks.forEach(function(link){
        	 edges.push(link);
        });
        
        graphDrawUpdate();
    }
    
    /**节点关闭*/
    function collapseNode(uuid){
        removeChildNodes(uuid,false);
        updateGraphAndCache();
    }
    
    /**删除事件相关子节点*/
    function deteleEventNode(uuid){
        removeChildNodes(uuid,true);
        //updateGraphAndCache();
        searchManager.deleteIdCache(pageVariate.tempDeleteId);
        pageVariate.tempDeleteId=[];
    }
    
    /**删除节点及子节点*/
    function deteleNode(uuid){
        removeChildNodes(uuid,false);
        removeNode(uuid);
        
        updateGraphAndCache();
    }
    
    /**删除选中的节点及子节点*/
    function deteleSelNode(nodeArr){
    	var tempArr = [];
    	nodeArr.forEach(function(node,index){
    		tempArr.push(node);
    	});
    	tempArr.forEach(function(node,index){
    		if(node.type == "Person"){
    			removeChildNodes(node.uuid,false);
    		}
    		removeNode(node.uuid);
    	})
    	updateGraphAndCache();
    	$(".serList").empty();
    	$(".personPage").empty();
    }
    
    /**删除未选中的节点及子节点*/
    function deteleUnSelNode(){
    	var nodeArr = pageVariate.selNode;
    	var tempArr = [];
    	nodes.forEach(function(node,index){
    		if(nodeArr.indexOf(node) < 0){
    			tempArr.push(node);
    		}
    	});
    	tempArr.forEach(function(node,index){
    		if(node.type == "Person"){
    			removeChildNodes(node.uuid,false);
    		}
    		removeNode(node.uuid);
    	})
    	updateGraphAndCache();
    	$(".serList").empty();
    	$(".personPage").empty();
    }
    
    /**更新图及删除缓存*/
    function updateGraphAndCache(){
        graphDrawUpdate();
        searchManager.deleteIdCache(pageVariate.tempDeleteId);
        pageVariate.tempDeleteId=[];
    }
    
    /**清空graph*/
    function emptyNode(){
    	nodes = [];
		edges = [];
		graphDrawUpdate();
		searchManager.emptyIdCache();
		
		//选中的缓存清空
		pageVariate.selNode = [];
		//查询事件类型缓存清空
		pageVariate.typeMap = new Map();
		//展开缓存清空
		pageVariate.openMap = new Map();
		
		pageVariate.personId = "";
        pageVariate.uuid = "";
        pageVariate.initStartTime = "";
        pageVariate.initEndTime = "";
		
		pageVariate.tempDeleteId=[];
    	$(".serList").empty();
    	$(".personPage").empty();
    }
    	
    	
	force.on("tick", function() {

		//更新连接线的位置
		edges_line.attr("x1", function(d) {
			return subtract2(d,"x1");
		});
		edges_line.attr("y1", function(d) {
			return subtract2(d,"y1");
		});
		edges_line.attr("x2", function(d) {
			return subtract2(d,"x2");
		});
		edges_line.attr("y2", function(d) {
			return subtract2(d,"y2");
		});
		//更新箭头位置
		edges_polygon.attr("points",function(d){
			var x2,y2;
			if(d.direction == "out"){
				x2 =subtract(d,"x2"),
				y2 = subtract(d,"y2");
			}else{
				x2 =subtract(d,"x1"),
				y2 = subtract(d,"y1");
			}
			return  x2+" "+y2+" "+(x2-7)+" "+(y2+15)+" "+x2+" "+(y2+8)+" "+(x2+7)+" "+(y2+15)
			
		})
		.attr("transform",function(d){
			var x2,y2;
			var x1,y1,rotate;
			var sourceNode = findNode(d.source.uuid);
			var targetNode = findNode(d.target.uuid);
			if(d.direction == "out"){
				x1 = sourceNode.x;
				y1 = sourceNode.y;
				x2 =subtract(d,"x2");
				y2 = subtract(d,"y2");
			}else{
				x1 = targetNode.x;
				y1 = targetNode.y;
				x2 =subtract(d,"x1");
				y2 = subtract(d,"y1");
			}	
			var rotate=Math.atan2(y2 - y1, x2 - x1) / (Math.PI / 180)+90;
			return  "rotate(" +rotate+","+x2+","+y2+ ")" ;
		});
		//更新连接线上文字的位置
		/*edges_text.attr("x", function(d) {
			return (d.source.x + d.target.x) / 2;
		});
		edges_text.attr("y", function(d) {
			return (d.source.y + d.target.y) / 2;
		});*/
				
		edges_text.attr("transform",function(d){
			var x1 = d.source.x,
				y1 = d.source.y;
			var x2 = d.target.x,
				y2 = d.target.y;
			var rotate=Math.atan2(y2 - y1, x2 - x1) / (Math.PI / 180);
			if(rotate>90&&rotate<180){
				rotate=rotate+180;
			}
			if(rotate>-180&&rotate<-90){
				rotate=rotate+180;
			}
			return "translate(" + (d.source.x + d.target.x) / 2 + "," + (d.source.y + d.target.y) / 2+ ")"+
			  "rotate(" +rotate+ ")" ;
		});
		
	
		//更新结点图片和文字
		svg_g.selectAll("g.node").attr("transform", function(d) {
			return "translate(" +(d.x - img_w / 2)+ "," +( d.y - img_h / 2)+ ")";
		});
		/*nodes_img.attr("x", function(d) {
			return d.x - img_w / 2;
		});
		nodes_img.attr("y", function(d) {
			return d.y - img_h / 2;
		});*/
		
		/*nodes_text.attr("text-anchor","middle");
		nodes_text.attr("transform", function(d) {
			return "translate(" +d.x+ "," +d.y+ ")";
		});*/
		/*nodes_text.attr("x", function(d) {
			return d.x
		});
		nodes_text.attr("y", function(d) {
			return d.y ;
		});*/
	});
	
	//查找节点
	function findNode(uuid){
	    for(var i = 0;i < nodes.length; i++) {
	        if (nodes[i]['uuid']==uuid ) return nodes[i];
	    }
	    return null;
	}

	//查找节点所在索引号
	function findNodeIndex(uuid){
		for(var i = 0;i < nodes.length; i++) {
	        if (nodes[i]['uuid']==uuid ) return i;
	    }
	    return -1;
	}
	
	//查找节点所在索引号(根据ID查，多个返回第一个)
	function findNodeIndexById(id){
		for(var i = 0;i < nodes.length; i++) {
	        if (nodes[i]['id']==id ) return i;
	    }
	    return -1;
	}
	
	//删除节点
	function removeNode(uuid){
//		flag = flag?true:pageVariate.uuid != uuid;
//		if(flag){
			var i=0,
				j=0,
				k=0,
				n=findNode(uuid);//获取节点
			if(n){
				var ind = n.index;
				while(i<edges.length){
					if(edges[i]['source'].uuid == n.uuid || edges[i]['target'].uuid == n.uuid)
						edges.splice(i,1);
					else
						++i;
				}
				pageVariate.tempDeleteId.push(n.id);
				nodes.splice(ind,1);
				
				/*while(j<edges.length){
					var sourceIndex = edges[j]['source'].index;
					if(sourceIndex > ind){
						edges[j]['source'] = nodes[sourceIndex-1];
					}
					var targetIndex = edges[j]['target'].index;
					if(targetIndex > ind){
						edges[j]['target'] = nodes[targetIndex-1];
					}
					++j;
				}*/
				while(k<nodes.length){
					var index = nodes[k].index;
					if(index > ind){
						nodes[k].index -= 1;
					}
					++k;
				}
				
				//选中的缓存删除
				var index = pageVariate.selNode.indexOf(n);
				if(index>-1){
					pageVariate.selNode.splice(index, 1);
				}
				//查询事件类型缓存删除
				if(n.type == "Person"){
					pageVariate.typeMap.remove(n.id);
					pageVariate.openMap.remove(n.uuid);
				}
			}
//		}
	}

	//删除节点下的子节点，同时清除link信息,eventFlag只删除事件相关节点
	function removeChildNodes(uuid,eventFlag){
		//修改为删除相邻节点，不进行递归，不管方向
		var node=findNode(uuid);
	    var childNodes=[];
	    edges.forEach(function(link,index){
	    	var tNode = link['target'];
	    	var sNode = link['source'];
	    	var tType = tNode.type;
	    	var sType = sNode.type;
	    	if(eventFlag){
	    		if(typeof(tType) == "string" && tType.endWith("Event")){
	    			link['source'].uuid==node.uuid 
	    			&& childNodes.push(tNode);
	    		}
	    		if(typeof(sType) == "string" && sType.endWith("Event")){
	    			link['target'].uuid==node.uuid 
	    			&& childNodes.push(sNode);
	    		}
	    	}else{
	    		link['source'].uuid==node.uuid 
	    		&& childNodes.push(tNode);
	    		
	    		link['target'].uuid==node.uuid 
	    		&& childNodes.push(sNode);
	    	}
	    });
	    childNodes.forEach(function(node){
	    	removeNode(node.uuid);
	    });
		
		
	    /*var node=findNode(uuid);

	    var linksToDelete=[],
	        childNodes=[],
	        tempDelete=[];
	    
	    edges.forEach(function(link,index){
	    	var tNode = link['target'];
	    	var type = tNode.type;
	    	if(eventFlag){
	    		if(typeof(type) == "string" && type.endWith("Event")){
	    			link['source']==node 
	    			&& linksToDelete.push(index) 
	    			&& childNodes.push(tNode);
	    		}
	    	}else{
	    		link['source']==node 
	    		&& linksToDelete.push(index) 
	    		&& childNodes.push(tNode);
	    	}
	    });

	    linksToDelete.reverse().forEach(function(index){
	    	edges.splice(index,1);
	    });
	    var tempDeleteEdge = [];
	    var remove=function(node){
	    	tempDelete.push(node.uuid);
	        var length=edges.length;
	        for(var i=length-1;i>=0;i--){
	        	var source = edges[i]['source']
	        	if(source){
	        		if (source == node ){
	        			var target=edges[i]['target'];
	        			if(target.uuid != uuid){
	        				if(tempDeleteEdge.indexOf(i) == -1){
//	            		   		edges.splice(i,1);
	        					tempDeleteEdge.push(i);
	        					remove(target);
	        				}
	        				//nodes.splice(findNodeIndex(node.uuid),1);
	        			}
	        		}
	        	}
	        }
	    }

	    childNodes.forEach(function(node){
	        remove(node);
	    });
	    
	    tempDeleteEdge.sort(function(a,b){return a<b?1:-1});
	    tempDeleteEdge.forEach(function(index){
	    	edges.splice(index,1);
	    });
	    
	    tempDelete.forEach(function(uuid){
	    	removeNode(uuid);
	    });*/
	}
	
	function subtract(d,loc){
		/*var x1 = d.source.x,
		y1 = d.source.y;
		x2 = d.target.x,
		y2 = d.target.y;*/
		
		var sourceNode = findNode(d.source.uuid);
		var targetNode = findNode(d.target.uuid);
		var x1 = sourceNode.x;
		y1 = sourceNode.y,
		x2 = targetNode.x,
		y2 = targetNode.y;

		var l=Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
		var l1=28;//缩短的长度
		if (loc == 'x1') {
			return x1+(l1/l)*(x2-x1);
		}else if (loc == 'y1') {
			return y1+(l1/l)*(y2-y1);
		}else if (loc == 'x2') {
			return x1+((l-l1)/l)*(x2-x1);
		}else if (loc == 'y2') {
			return y1+((l-l1)/l)*(y2-y1);
		}
	}
	
	function subtract2(d,loc){
		/*var x1 = d.source.x,
		y1 = d.source.y,
		x2 = d.target.x,
		y2 = d.target.y;*/
		var sourceNode = findNode(d.source.uuid);
		var targetNode = findNode(d.target.uuid);
		var x1 = sourceNode.x;
		y1 = sourceNode.y,
		x2 = targetNode.x,
		y2 = targetNode.y;

		var l=Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
		var l1=29;//缩短的长度
		if (loc == 'x1') {
			return x1+(l1/l)*(x2-x1);
		}else if (loc == 'y1') {
			return y1+(l1/l)*(y2-y1);
		}else if (loc == 'x2') {
			return x1+((l-l1)/l)*(x2-x1);
		}else if (loc == 'y2') {
			return y1+((l-l1)/l)*(y2-y1);
		}
	}	
	
	/** 列表删除同步更新图 */
	function listDeteleLinkage(node,ids){
		var uuid = node.uuid;
		var n=findNode(uuid);
		var subList=node.subList;
	    for(var i=0;i<ids.length;i++){
	    	var tempId = ids[i];
	    	for(var j=0;j<subList.length;j++){
	    		var eventId = subList[j].id;
	    		if(eventId == tempId){
	    			subList.splice(j, 1);
	    			break;
	    		}
	    	}
	    }
	    var len = subList.length;
	    if(len == 0){
	    	removeNode(uuid);
	        updateGraphAndCache();
	    }else{
	    	$(".n"+uuid +" text tspan").text(len);
//	    	graphDrawUpdate();
	    }
	}
	
/** ****************************svg end************************************* */

/** ****************************tabs start************************************* */

var tabsManager = {
	recWidth : 100,// 矩形宽度
	browserUl : d3.select('.browserProperty').append('ul'),
	vpro : [ 'index', 'weight', 'x', 'y', 'px', 'py', 'fixed', 'id', 'type','image','uuid','isdrag','isdrag2','previouslySelected','selected' ],// 需要过滤的属性
	fpro : [ 'type', 'hash', 'id'],
	list_node:'',
	/* 直方图展示 1表示其他   2表示地图 */
	histogramShow : function(flag,data) {
		var datas;
		if(flag == 1){
			datas = nodes;
		}else{
			datas = data;
		}
		this.appendType(datas);
		this.appendTable(datas);
	},
	/* 展开对象统计信息 */
	appendType : function(datas) {
		var all = datas.length;
		var showNumber = 2;
		var btnIsShow = false;
		var scale = this.recWidth / all;
		var personCount = 0;
		var showData = {};
		$.each(datas, function(i, value) {
			var type = value.image.replace(/\//g, "").replace("img", "").replace(".png", "");
			//var type = value.type;
			if(!showData[type])
				showData[type] = {count:0}
			showData[type].count ++;
		});
		console.log(showData);
		$("#allTypeList").empty();
		$('#allObj').html(' （'+all+'） ');
		
		for(var i in showData){
			var dataHideLi = '<li class="clearfix" style="display:none;" title="' + i + '">';
			var dataShowLi = '<li class="clearfix" title="' + i + '">';
	    	var other = '<img style="float: left;" src="images/histograms/'+i+'.png" onerror="javascript:this.src=\'images/histograms/default.png\'"/>' +
				    	'<i>'+showData[i].count+'</i>' +
				    	'<div class="bar fl">' +
				        	'<div class="leftBar fl"></div>' +
				        	'<div class="rightBar fr" style="width:'+((showData[i].count*scale)+"%")+'">' +
				        	'</div>' +
				    	'</div>' +
				    '</li>';
	    	if(showNumber > 0){
	    		$("#allTypeList").append(dataShowLi+other);
	    		btnIsShow = false;
	    		$("#allTypeList").css("overflow-y", "hidden");
	    	}else{
	    		$("#allTypeList").append(dataHideLi+other);
	    		btnIsShow = true;
	    		$("#allTypeList").css("overflow-y", "scroll");
			}
		    showNumber --;
		}
		if(btnIsShow){
			var li = $('<li></li>');
			var less = $('<div style="float: left;margin-left: 67%;cursor: pointer;background-color: #4dafe3;padding: 2px 6px;border-radius: 4px;">更少</div>');
			li.append(less);
			less.click(function(){
				$("#allTypeList").children().css("display", "none");
				for(var i = 0; i < 2; i ++){
					$($("#allTypeList").children()[i]).css("display", "block");
				}
				$($("#allTypeList").children()[$("#allTypeList").children().length-1]).css("display", "block");
			});
			var more = $('<div style="float: right;margin-right: 10%;cursor: pointer;background-color: #4dafe3;padding: 2px 6px;border-radius: 4px;">更多</div>');
			li.append(more);
			more.click(function(){
				$("#allTypeList").children().css("display", "block");
			});
			$("#allTypeList").append(li)
		}
	},
	/* 展开对象详细信息 */
	appendTable : function(datas) {
		this.fillHtml(1,datas,"histogramDiv");
	},
	/* 浏览器详细信息 */
	browserShow : function(node) {
		this.infoHtml(node);
	},
	/* 清楚指定ID或者Class的标签内容  */
	clearShow : function(domNode){
		$(domNode).css("display", "none");
	},
	infoHtml:function(node){
		tabsManager.list_node = node;
		var type=node.type;
		if(type=='Person'){
			$(".maincon").css("display","none");
			$(".personPage").css("display","block");
		    this.fillHtml(2,node,"personPage");
		}else{
			var subList=node.subList;
			//没有subList不是统计节点
			if(!subList){
				$(".maincon").css("display","none");
				$(".personPage").css("display","block");
			    return this.fillHtml(2,node,"personPage");
			}
			pageVariate.selectAllFlag=true;
			$(".personPage").css("display","none");
			$(".maincon").css("display","block");
		    //外层循环，共要进行arr.length次求最大值操作
		    for(var i=0;i<subList.length;i++){
		        //内层循环，找到第i大的元素，并将其和第i个元素交换
		        for(var j=i;j<subList.length;j++){
		            if(subList[i].time<subList[j].time){
		                //交换两个元素的位置
		                var temp=subList[i];
		                subList[i]=subList[j];
		                subList[j]=temp;
		            }
		        }
		    }

			var str='';
			if(type=='CallEvent'){
				$.each(subList,function(i,one){
					str+='<ul class="serUl clearfix"><li class="s0"><input type="checkbox" class="checkbox" id="'+one.id+'"/></li> <li class="s1" title="'+one.time+" "+one.long+'">'+one.time+" "+one.long+'</li><li class="s2"><img src="images/img/top.png" width="12" height="7" /></li></ul><div class="serDetail"><ul class="clearfix"  ><li title="'+one.from+'">主叫人:'+one.from+'</li><li title="'+one.to+'">被叫人:'+one.to+'</li><li title="'+one.time+'">通话日期:'+one.time+'</li><li  title="'+one.long+'">通话时长:'+one.long+'</li></ul></div>';
				})
			}else if(type=='EmailEvent'){
				$.each(subList,function(i,one){
					str+='<ul class="serUl clearfix"><li class="s0"><input type="checkbox" class="checkbox" id="'+one.id+'"/></li> <li class="s1" onclick="toShowEmailDetails('+one.id+')" title="'+one.time+" "+one.title+'">'+one.time+" "+one.title+'</li><li class="s2"><img src="images/img/top.png" width="12" height="7" /></li></ul><div class="serDetail"><ul class="clearfix"><li title="'+one.time+'">时间:'+one.time+'</li><li title="'+one.title+'">主题:'+one.title+'</li><li title="'+one.from+'">发件人:'+one.from+'</li><li title="'+one.to+'">收件人:'+one.to+'</li><li title="'+one.content+'">内容:'+one.content+'</li></ul></div>';
				}) 
			}else if(type=='LoginEvent'){
				$.each(subList,function(i,one){
					str+='<ul class="serUl clearfix"><li class="s0"><input type="checkbox" class="checkbox" id="'+one.id+'"/></li> <li class="s1" title="'+one.time+" "+one.domain+'">'+one.time+" "+one.domain+'</li><li class="s2"><img src="images/img/top.png" width="12" height="7" /></li></ul><div class="serDetail"><ul class="clearfix"><li title="'+one.time+'">时间:'+one.time+'</li><li title="'+one.username+'">用户名:'+one.username+'</li><li title="'+one.domain+'">登陆网站:'+one.domain+'</li><li title="'+one.ip+'">IP:'+one.ip+'</li></ul></div>';
				}) 
			}else if(type=='StayEvent'){
				$.each(subList,function(i,one){
					str+='<ul class="serUl clearfix"><li class="s0"><input type="checkbox" class="checkbox" id="'+one.id+'"/></li> <li class="s1" title="'+one.arrivaldate+" "+one.hotelname+'">'+one.arrivaldate+" "+one.hotelname+'</li><li class="s2"><img src="images/img/top.png" width="12" height="7" /></li></ul><div class="serDetail"><ul class="clearfix"><li title="'+one.hotelname+'">酒店名称:'+one.hotelname+'</li><li title="'+one.orderno+'">订单号:'+one.orderno+'</li><li title="'+one.roomno+'">房间号:'+one.roomno+'</li><li title="'+one.arrivaldate+'">入住时间:'+one.arrivaldate+'</li><li title="'+one.departuredate+'">离店时间:'+one.departuredate+'</li></ul></div>';
				}) 
			}
			$(".serList").empty();
			$(".serList").append(str);
				
			//单击展示
			$(".serList ul li.s2 img").each(function(){
			   $(this).live("click",function(){			   
					$(this).parents(".serUl").next(".serDetail").slideToggle().siblings(".serDetail").hide();  
					var srcAttr = $(this).attr("src");
					var Img = $(".serList ul li.s2").children("img");  
					$(Img).attr("src","images/img/top.png");
					if (srcAttr === "images/img/down1.png") {
						$(this).attr("src","images/img/top.png");
					} else {
						$(this).attr("src","images/img/down1.png");	
					}				  
				});	
			}); 
		}
	},
	/* 填充HTML，var为nodes数组或单个节点，className为填充元素的class */
	fillHtml:function(flag,val,className){
		var countMap = new Map();
		if(isArray(val)){
			$.each(val,function(i,node){
				tabsManager.countMapOpt(node, countMap);
			})
		}else{
			tabsManager.countMapOpt(val, countMap);
		}
		$('.'+className).html('');
		var mapArr = countMap.arr;
		
		//属性编辑时需用到id type
		var vertexId = countMap.get("id");
		var vertexType = countMap.get("type");
		
		var str = "";
		if(flag==1){//直方图
			$.each(mapArr,function(i,map){
				var key = map.key;
				if(key == "id" || key == "type") return true;
				var innerMap = map.value;
				str += '<div><p class="pTit"><span>';
				str += key + '</span><em>（' + innerMap.get(key) + '）</em></p>';
				
				str += '<ul class="clearfix">';
				var innerArr = innerMap.arr;
				$.each(innerArr,function(i,map){
					var key2 = map.key;
					if(typeof(key2) == "string" && key2.startWith("hash_") && key2.endWith("_hash")){
						var countArr = map.value;
						var pMap = countArr[1];
						var conent = "";
						var type = pMap.type;
						var mainKey = tabsManager.getMainKey(type);
						var mainValue = pMap[mainKey];
						var mainConent = mainKey+"："+mainValue;
						//add by hanxue start
						var parses = {};
						//add by hanxue end
						str += '<li '+((flag==1)?('onclick="tabsManager.clickLinkageGraph(\''+key+'\',\''+pMap.hash+'\')"'):'')+' ><em></em><span class="name fl" title="'+mainConent+'">' + mainConent
						+ '</span><i class="iIcon fl"><img src="images/img/top.png" width="15" height="9" /></i> <span class="fr">'
						+ countArr[0]
						+ '</span><div class="clear"></div>'
						+ '<div class="listDiv">';
						for(var k in pMap){
							if(tabsManager.fpro.indexOf(k) == -1 && k != mainKey){
								var subConent = k+"："+pMap[k];
								//add by liubaofen
								str += '<div style="display:inline-block;" class="addDiv1">'
								    +  '	<span class="commonSpan" title="'+subConent+'">';
								//add by hanxue
								if((type=="Account"&&mainKey=="username")||(type=="Email"&&mainKey=="email")){
									parses[1]= mainValue;
									if(mainKey=="username"){
										if(k=="uid"){
											parses[0]=pMap[k];
										}
									}else if(mainKey=="email"){
										parses[0]="email";
									}
									if(k=="password"){
										parses[2]=pMap[k];
										str += ' <input type="button" value="破解" onclick="javascript:addPTAjax('
											+"'"+parses[0]+"','"+parses[1]+"','"+parses[2]+"'"
											+')"/>';
									}
								}
								str += ' <strong style="font-weight:normal;float:left;">'+k+'：</strong>'
								    +  '	<b>'+pMap[k]+'</b>'
								    +  '	</span>'			    								
								    +  '</div>'
								//add by liubaofen end
							}
						}
						str += '</div></li>'
					}else{
						var count = map.value;
						if (key != key2) {
							str += '<li '+((flag==1)?('onclick="tabsManager.clickLinkageGraph(\''+key+'\',\''+key2+'\')"'):'')+' ><em></em><span class="name fl" title="'+key2+'">' + key2
							+ '</span> <span class="fr">'
							+ count
							+ '</span></li>';
						}
					}
				});
				str += '</ul>';
				str += '</div>';
			});
		}else{//浏览器
			$.each(mapArr,function(i,map){
				var key = map.key;
				if(key == "id" || key == "type") return true;
				var innerMap = map.value;
				str += '<div class="findClass"><p class="pTit"><span>';
				str += key + '</span><em>（' + innerMap.get(key) + '）</em></p>';
				str += '<ul class="clearfix">';
				var innerArr = innerMap.arr;
				$.each(innerArr,function(i,map){
					var key2 = map.key;
					if(typeof(key2) == "string" && key2.startWith("hash_") && key2.endWith("_hash")){
						var countArr = map.value;
						var pMap = countArr[1];
						var conent = "";
						var type = pMap.type;
						var mainKey = tabsManager.getMainKey(type);
						var mainValue = pMap[mainKey];
						var mainConent = mainKey+"："+mainValue;
						str += '<li>'
						    +  '	<em class="iconEm"></em>'
						    +  '	<div style="display:inline-block;" class="addDiv addDiv1 fl">'
						    +  '		<span title="'+mainConent+'" class="name1 commonSpan fl">'
						    +  '			<strong style="font-weight:normal;float:left;">'+mainKey+'：</strong>'
						    +  '			<b>'+mainValue+'</b>'
						    +  '			<div class="aDiv" >'
						    +  '				<input type="text" />'
						    +  '				<div class="btndiv">'
						    +  '					<a class="sureA fl" name="'+pMap.id+','+type+','+mainKey+'" href="javascript:void(0);">确定</a>'
						    +  '					<a class="nosureA" href="javascript:void(0);">取消</a>'
						    +  '				</div>'
						    +  '			</div>'
						    +  '		</span>'
						    +  '	</div>'
						    +  '	<i class="iIcon fl"><img width="15" height="9" src="images/img/top.png"></i>'
						    +  '	<i class="historyIcon fl" name="'+pMap.id+','+type+','+mainKey+'"><img width="12" height="12" src="images/img/historyIcon.png"></i>'
						    +  '	<span class="fr">'+countArr[0]+'</span>'
						    +  '	<div class="clear"></div>'
						    +  '	<div class="listDiv addDiv1" style="display: none;">';
					    for(var k in pMap){
							if(tabsManager.fpro.indexOf(k) == -1 && k != mainKey){
								var subConent = k+"："+pMap[k];
								str += '<div style="display:inline-block;" class="addDiv addDiv1">'
								    +  '	<span class="commonSpan" title="'+subConent+'">'
								    +  '		<strong style="font-weight:normal;float:left;">'+k+'：</strong>'
								    +  '		<b>'+pMap[k]+'</b>'
								    +  '		<div class="aDiv" id="111">'
								    +  '			<input type="text" />'
								    +  '			<div class="btndiv">'
								    +  '				<a class="sureA fl" name="'+pMap.id+','+type+','+k+'" href="javascript:void(0);">确定</a>'
								    +  '				<a class="nosureA" href="javascript:void(0);">取消</a>'
								    +  '			</div>'
								    +  '		</div>'
								    +  '	</span>'			    								
								    +  '</div>'
								    +  '<i class="historyIcon" name="'+pMap.id+','+type+','+k+'"><img width="12" height="12" src="images/img/historyIcon.png"></i>';
							}
						}
					    str += '	</div>'
						    +  '</li>';
					}else{
						var count = map.value;
						if (key != key2) {
							str += '<li>'
		    					+  '	<em class="iconEm"></em>'
		    					+  '	<div style="display:inline-block;" class="addDiv fl">'
		    					+  '		<span title="'+key2+'" class="name1 commonSpan fl">'
		    					+  '			<b>'+key2+'</b>'
		    					+  '			<div class="aDiv" >'
		    					+  '				<input type="text" />'
		    					+  '				<div class="btndiv">'
		    					+  '					<a class="sureA fl" name="'+vertexId+','+vertexType+','+key+'" href="javascript:void(0);">确定</a>'
		    					+  '					<a class="nosureA" href="javascript:void(0);">取消</a>'
		    					+  '				</div>'
		    					+  '			</div>'
		    					+  '		</span>'
		    					+  '	</div>'
		    					+  '	<i class="historyIcon fl" name="'+vertexId+','+vertexType+','+key+'"><img width="12" height="12" src="images/img/historyIcon.png"></i>'
		    					+  '	<span class="fr">'+count+'</span>'
		    					+  '</li>';
						}
					}
				});
				str += '</ul>';
				str += '</div>';
			});
		}
		$('.'+className).html(str);
		$("div.secondCon li span").click(function(e){
			e.preventDefault();
			var _this = this;
			$("div.secondCon li span").css("background", "rgba(1, 1, 1, 0)");
			$(_this).css("background", "#283147");
		});
		
	},
	/* 图高亮反选 */
	graphHighlightInvert : function() {
		var tempArr = [];
		$.each(nodes,function(i,node){
			var index = pageVariate.selNode.indexOf(node);
			var type = node.type;
			if(index>-1){
				$(".n"+node.uuid +" image").attr("href", "images/img/"+type+".png");
			}else{
				$(".n"+node.uuid +" image").attr("href", "images/img/"+type+"Sel.png");
				tempArr.push(node);
			}
		});
		pageVariate.selNode= tempArr;
		if(pageVariate.selNode.length < 2){
			tabsManager.browserShow(pageVariate.selNode[0]);
		}else{
			tabsManager.clearShow(".personPage");
			tabsManager.clearShow(".maincon");
		}
	},
	/* 过滤图显示高亮 */
	filterGraphHighlight : function(vertexType,key,value) {
		var countSel = 0;
		var countNo = 0;
		clearSelStatus();
		$.each(nodes,function(i,node){
			var type = node.type;
			if(vertexType == type){
				var index = pageVariate.selNode.indexOf(node);
				for(var prokey in node){
					var pro = node[prokey];
					if(isArray(pro)){
						var flag = false;
						$.each(pro,function(i,proMap){
							for(var prokey2 in proMap){
								var pro2 = proMap[prokey2];
								if(key == prokey2 && value == pro2){
									if(index>-1){
										countSel++;
									}else{
										$(".n"+node.uuid +" image").attr("href", "images/img/"+type+"Sel.png");
										pageVariate.selNode.push(node);
										countNo++;
									}
									flag = true;
									return false;
								}
							}
						});
						if(flag){
							break;
						}
					}else{
						if(key == prokey && value == pro){
							if(index>-1){
								countSel++;
							}else{
								$(".n"+node.uuid +" image").attr("href", "images/img/"+type+"Sel.png");
								pageVariate.selNode.push(node);
								countNo++;
							}
							break;
						}
					}
				}
			}
		});
		if(pageVariate.selNode.length < 2){
			tabsManager.browserShow(pageVariate.selNode[0]);
		}else{
			tabsManager.clearShow(".personPage");
			tabsManager.clearShow(".maincon");
		}
		if(countNo == 0 && countSel == 0){
			hintManager.showWarnHint("未查找到此条件过滤的节点！");
		}else if(countSel > 0){
			hintManager.showInfoHint("此条件过滤的节点已经选中！");
		}
	},
	/* 点击属性图高亮 */
	clickLinkageGraph : function(key,value) {
		clearSelStatus();
		$.each(nodes,function(i,node){
			var type = node.type;
			var index = pageVariate.selNode.indexOf(node);
			if(index<=-1){
				var pro = node[key];
				if(isArray(pro)){
					$.each(pro,function(i,proMap){
						var hash = proMap.hash;
						if(value == hash){
							$(".n"+node.uuid +" image").attr("href", "images/img/"+type+"Sel.png");
							pageVariate.selNode.push(node);
							return false;
						}
					});
				}else{
					if(value == pro){
						$(".n"+node.uuid +" image").attr("href", "images/img/"+node.type+"Sel.png");
						pageVariate.selNode.push(node);
					}
				}
			}
		});
		if(pageVariate.selNode.length < 2){
			tabsManager.browserShow(pageVariate.selNode[0]);
		}else{
			tabsManager.clearShow(".personPage");
			tabsManager.clearShow(".maincon");
		}
	},
	/* 填充HTML调用 */
	countMapOpt : function(node,countMap) {
		if("own" != node.type){//展开节点不加入统计
			for(var key in node){
				if (tabsManager.vpro.indexOf(key) == -1) {
					if(key == "subList"){
						var eventNodes = node[key];
						eventNodes.forEach(function(node,index){
							for(var prokey in node){
								if (tabsManager.vpro.indexOf(prokey) == -1) {
									subCount(node,prokey,countMap);
								}
							}
						})
					}else{
						subCount(node,key,countMap);
					}
				}
			}
		}
		
		//节点统计
		function subCount(node,key,countMap){
			//属性编辑时需用到id type
			countMap.put("id",node.id);
			countMap.put("type",node.type);
			
			var value = node[key];
			var map = countMap.get(key);
			if (map) {
				if(isArray(value)){
					var proMap = map;
					proMap.put(key, value.length+proMap.get(key));
					$.each(value,function(i,p){
						var hash = p.hash;
						var hashKey = "hash_"+hash+"_hash";
						var arr = proMap.get(hashKey);
						if (arr) {
							arr[0] +=1;
							proMap.put(hashKey, arr);
						}else {
							var newarr = [];
							newarr.push(1);
							newarr.push(p);
							proMap.put(hashKey, newarr);
						}
					});
				}else{
					var proMap = map;
					proMap.put(key, 1+proMap.get(key));
					if (proMap.get(value)) {
						proMap.put(value, 1+proMap.get(value));
					}else {
						proMap.put(value, 1);
					}
				}
			}else {
				var proMap = new Map();
				if(isArray(value)){
					$.each(value,function(i,p){
						var hash = p.hash;
						var hashKey = "hash_"+hash+"_hash";
						var arr = proMap.get(hashKey);
						if (arr) {
							arr[0] +=1;
							proMap.put(hashKey, arr);
						}else {
							var newarr = [];
							newarr.push(1);
							newarr.push(p);
							proMap.put(hashKey, newarr);
						}
					});
					proMap.put(key, value.length);
				}else{
					proMap.put(value, 1);
					proMap.put(key, 1);
				}
				countMap.put(key, proMap);
			}
		}
	},
	/* 根据类型获取主key */
	getMainKey : function(type) {
		var mainKey = "";
		switch(type){
			case "Phone":
				mainKey = "phonenum";
				break;
			case "Account":
				mainKey = "username";
				break;
			case "Email":
				mainKey = "email";
				break;
			case "Location":
				mainKey = "address";
				break;
			case "IM":
				mainKey = "numid";
				break;
			case "Resume":
				mainKey = "title";
				break;
			default:
				break;
		}
		return mainKey;
	}

}

/** ****************************tabs end************************************* */

/** ****************************searchResult start************************************* */
/* 查询结果处理 */
var searchManager = {
	/*单个*/
	searchLinkage:function(data){
  		pageVariate.root = data;
    	pageVariate.personId = pageVariate.root.nodes[0].id;
    	pageVariate.uuid = pageVariate.root.nodes[0].uuid;
		
		if(findNodeIndexById(pageVariate.personId) == -1){
			if(pageVariate.fristFlag){
				 $('.displayDiv').show();
				 $('.zoomline').show();
	        	 graphDrawInit(pageVariate.root);
	        	 graphDrawUpdate();
	        	 pageVariate.fristFlag = false;
			}else{
				var InitData = {
					nodes : nodes,
					edges : edges
				}
				graphDrawInit(InitData);
				expandNode(pageVariate.root.nodes,pageVariate.root.edges);
			}
			this.modifyIdCache(pageVariate.personId);
			//展示属性节点
   			graphManager.showDetail_first();
   			
            showTimeLline(pageVariate.personId,pageVariate.types);//时间轴联动
		}else{
			hintManager.showHint("该节点在页面中已存在！");
		}
		$(".hideDiv").hide(); 
  	},
    /*多个*/
  	moreNodeHandle:function(data){
  		var nodes = data.nodes;
  		$('.hideDiv').html('');
  		var str = '<ul>';
  		var joinStr = function(node,key){
  			var proValue = "";
  			var proArr = node[key];
  			var mainKey = tabsManager.getMainKey(key);
  			var pl = proArr.length;
  			$.each(proArr,function(i,p){
  				proValue += p[mainKey];
  				if (i!=pl-1) {
  					proValue += ",";
				}
  			});
  			return proValue;
  		}
  		$.each(nodes,function(i,node){
  			str += '<li id="'+node.uuid+'">';
  			str += node.name?('name : '+node.name+'<br/>'):'';
  			str += node.Email?('email : '+joinStr(node,"Email")+'<br/>'):'';
  			str += node.Phone?('phone : '+joinStr(node,"Phone")+'<br/>'):'';
  			str += node.idcard?('idcard : '+node.idcard):'';
  			str += node.IM?('IM : '+joinStr(node,"IM")+'<br/>'):'';
  			str += '</li>';
  		})
		str += '</ul>';
		
		$('.hideDiv').html(str);
		$('.hideDiv').show();
  	},
  	modifyIdCache : function(id) {
		$.ajax({
			type : "post",
			async: false,
			dataType : "json",
			url : pageVariate.base+"query/modifyIdCache.action",
			data : {
				personId : id,
				date : new Date().getTime()
			},
			dataType : "json",
			success : function() {
			}
		});
	},
	deleteIdCache : function(ids) {
		$.ajax({
			type : "post",
			async: false,
			dataType : "json",
			url : pageVariate.base+"query/deleteIdCache.action",
			data : {
				ids:ids.toString(),
				date : new Date().getTime()
			},
			dataType : "json",
			success : function() {
			}
		});
	},
	emptyIdCache : function() {
		$.ajax({
			type : "post",
			async: false,
			dataType : "json",
			url : pageVariate.base+"query/emptyIdCache.action",
			data : {
				date : new Date().getTime()
			},
			dataType : "json",
			success : function() {
			}
		});
	}
}
//显示简历信息
function toShowResumeDetails(vertexId){
	$(".tccBox").show();
	var str = "";
    if(vertexId){
         $.ajax({
          type : "post",  
          async : false, //同步执行  
          url : pageVariate.base+"query/queryResumeByVid.action", 
          data : {"VertexID":vertexId},  
          dataType : "json", //返回数据形式为json  
          success : function(datas) {
          if (datas) {
          var Title = datas.title == undefined?"":datas.title;
          var Keywords = datas.keyWords == undefined?"":datas.keyWords;
          var creationDate = datas.creationDate == undefined?"":datas.creationDate;
          var City = datas.city == undefined?"":datas.city;
          var content = datas.description ==undefined?"对不起，不存在简历内容信息！":datas.description;

          str += '<div class="emailCon-top clearfix"><h3>'+Title+ '</h3>'+
			    	  '<div class="topEmailCon">'+
			        	' <div class="keywordBox clearfix">'+
			            ' <span class="keyword">关键词：</span>'+
			            ' <div class="tagList">';
			    	  if(Keywords != ""){
			    		  str += '<b class="tagB"  >'+Keywords+'</b>';
			    	  }
						str +='</div>'+
				               '</div>'+
				               '</div>'+
						'<div class=list><em> 类 &nbsp;型:</em><span class="spanCon fl"><i>'+ datas.type + '</i></span></div><br>'+
						'<div class=list><em> 时 &nbsp;间: </em><span class="spanCon fl"><i>'+ creationDate + '</i></span></div><br>'+
						'<div class=list><em> 城 &nbsp;市: </em><span class="spanCon fl"><i>'+City + '</i></span></div>'; 
		           str += '</span></div></div>'+ 
		           '<div class=emailCon-bottom><pre>'+content+ '</pre></div>';
	              }
	               $('.barBox').html(str);
	           },  
	          error : function(errorMsg) {  
	        	  hintManager.showHint("未查询到简历数据");
	             }  
	        });
  }
    //$.parser.parse(str);
    //$('#tags_1').tagsInput({width:'auto'});
}
/** ****************************searchResult end************************************* */