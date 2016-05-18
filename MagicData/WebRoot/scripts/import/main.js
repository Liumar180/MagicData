
/******************************消息提示 start**************************************/
var hintManager = {
	showHint:function (msg){//错误提示
		var item = $("<div class='hintMsg' style='z-index: 10000;position: fixed;top: 0px;height: 35px;width: 100%;text-align: center;line-height: 35px;font-size: 20px;'>"+msg+"</div>");
		item.css("background-color","#e53232");
		item.css("color","#fff");
		$("body").append(item);
		window.setTimeout(hintManager.hideHint,3000); 
	},
	showWarnHint:function (msg){//警告
		var item = $("<div class='hintMsg' style='z-index: 10000;position: fixed;top: 0px;height: 35px;width: 100%;text-align: center;line-height: 35px;font-size: 20px;'>"+msg+"</div>");
		item.css("background-color","#fe6c6c");
		item.css("color","#fff");
		$("body").append(item);
		window.setTimeout(hintManager.hideHint,3000); 
	},
	showInfoHint:function (msg){//消息
		var item = $("<div class='hintMsg' style='z-index: 10000;position: fixed;top: 0px;height: 35px;width: 100%;text-align: center;line-height: 35px;font-size: 20px;'>"+msg+"</div>");
		item.css("background-color","#afe3fd");
		item.css("color","#245269");
		$("body").append(item);
		window.setTimeout(hintManager.hideHint,3000); 
	},
	showSuccessHint:function (msg){//通过
		var item = $("<div class='hintMsg' style='z-index: 10000;position: fixed;top: 0px;height: 35px;width: 100%;text-align: center;line-height: 35px;font-size: 20px;'>"+msg+"</div>");
		item.css("background-color","#77d461");
		item.css("color","#2b542c");
		$("body").append(item);
		window.setTimeout(hintManager.hideHint,3000); 
	},
	hideHint:function (){
		$(".hintMsg").remove();
	}
}
/******************************消息提示 end**************************************/

/** ****************************mask end************************************* */
var maskManager = {
	show : function(message){
		if(!message){
			$(".maskDetail").html("正在处理中，请稍后。。。");
		}else{
			$(".maskDetail").html(message);
		}
		$("#mask").css("display", "block");
	},
	hide : function(){
		$("#mask").css("display", "none");
		$(".maskDetail").html("正在处理中，请稍后。。。");
	},
}
/** ****************************mask end************************************* */

graph = {
	createSVG:function(domId){
		//创建SVG
		var _this = this;
			_this.graphWidth = $(".displayDiv").innerWidth() - 2,
			_this.graphHeight = $(".displayDiv").innerHeight() - 2;
			_this.img_w = 60,
			_this.img_h = 60,
			_this.shiftKey=false,
			_this.text_dx = 0,
			_this.text_dy =35,
			_this.line_text_dx = 0,
			_this.line_text_dy = 0,
			_this.nodes = [],
			_this.edges = [],
			_this.svg = d3.select('.displayDiv').append('svg').attr('width', _this.graphWidth).attr('height', _this.graphHeight).attr("id", "displaySVG"),
			_this.svg_g = _this.svg.append("g");
			_this.force = d3.layout.force().nodes(_this.nodes).links(_this.edges).size([ _this.graphWidth, _this.graphHeight ]).linkDistance(function(d){
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
			_this.nodeEnter = _this.svg_g.selectAll("g.node"),
			_this.graphZoom = d3.behavior.zoom().scaleExtent([0.4, 5]);
		/******************************全屏加缩放************************************* */	
		
		
	},
	createTools:function(domId){
		//创建Tools
		var _this = this;
		//判断鼠标双击
		_this.isdb;
		//固定顶点
		_this.isdrag = true;
		//缩放轴	
		_this.cirscale = 0;
		_this.iszoomline = true;
		//加号中心点的x坐标
		_this.zoom_x=30;
		//加号中心点的y坐标
		_this.zoom_y=100;
		//加号外层圆的半径
		_this.add_r=10;
		//拖动的实心圆的半径
		_this.drag_r=5;
		_this.scale = 1.1;
		//线的长度
		_this.bar_len =100;
		//缩放轴svg宽度
		var zoom_line_width="150px";
		//缩放轴svg高度
		var zoom_line_height="265px";
		var zoom_line = d3.select('.zoomline').append('svg').attr("width",zoom_line_width).attr("height",zoom_line_height).append("g").attr("cursor","pointer");  
		_this.add=zoom_line.append("g").attr("class","add");
		_this.cut=zoom_line.append("g").attr("class","cut");
		_this.toolAddModel = zoom_line.append("g").attr("id", "addModel");
		_this.toolAddModel.append("title").text("添加模型");
		_this.toolAddModel.append("svg:image")
			.attr("width", 25)
			.attr("height", 25)
			.attr("x",17)
			.attr("y",55)
			.attr("xlink:href", pageVariate.base+"images/import/add.png");
		// del
		_this.toolDelNode = zoom_line.append("g")
			.attr("id", "delNode")
			.attr("width", 125)
			.attr("height", 125);

		_this.toolDelNode.append("title").text("删除模型");
		_this.toolDelNode.append("svg:image")
			.attr("width", 25)
			.attr("height", 25)
			.attr("x",47)
			.attr("y",55)
			.attr("xlink:href", pageVariate.base+"images/import/delete.png");
		// empty
		_this.toolEmptyNodes = zoom_line.append("g")
			.attr("id", "emptySVG");

		_this.toolEmptyNodes.append("title").text("清空模型");
		_this.toolEmptyNodes.append("svg:image")
			.attr("width", 25)
			.attr("height", 25)
			.attr("x",77)
			.attr("y",55)
			.attr("xlink:href", pageVariate.base+"images/import/empty.png");
		// edge
		_this.toolDrawEdge = zoom_line.append("g")
		.attr("id", "drawEdge");
		

		_this.toolDrawEdge.append("title").text("画线");
		_this.toolDrawEdge.append("svg:image")
			.attr("width", 25)
			.attr("height", 25)
			.attr("x",107)
			.attr("y",55)
			.attr("xlink:href", pageVariate.base+"images/import/line.png");
		//加
		_this.add.append("circle")
			.attr("cx",_this.zoom_x)
			.attr("cy",_this.zoom_y)
			.attr("fill","#f00")
			.attr("fill-opacity","0.1")
			.attr("r",_this.add_r)
			.attr("stroke","#fff");
		_this.add.append("line")
			.attr("x1",_this.zoom_x-5)
			.attr("y1",_this.zoom_y)
			.attr("x2",_this.zoom_x+5)
			.attr("y2",_this.zoom_y)
			.attr("stroke","#fff")
			.attr("fill","none");
		_this.add.append("line")
			.attr("x1",_this.zoom_x)
			.attr("y1",_this.zoom_y+5)
			.attr("x2",_this.zoom_x)
			.attr("y2",_this.zoom_y-5)
			.attr("stroke","#fff")
			.attr("fill","none");
		//轴
		zoom_line.append("line")
			.attr("x1",_this.zoom_x)
			.attr("y1",(_this.zoom_y+_this.add_r))
			.attr("x2",_this.zoom_x)
			.attr("y2",(_this.zoom_y+_this.add_r+_this.bar_len))
			.attr("stroke","#fff");


		//拖动的实心圆
		_this.cir = zoom_line.append("circle")
		   .attr("cx",_this.zoom_x)
		   .attr("cy",(_this.zoom_y+_this.add_r+_this.bar_len/2-_this.drag_r))
		   .attr("fill","#009cFF")
		   .attr("r",6);
		   


		//减
		_this.cut.append("circle")
		   .attr("cx",_this.zoom_x)
		   .attr("cy",(_this.zoom_y+_this.bar_len+_this.add_r+_this.add_r))
		    .attr("stroke","#fff")
		   .attr("fill-opacity","0.1")
		   .attr("fill","#061431")
		   .attr("r",_this.add_r);
		_this.cut.append("line")
		   .attr("x1",_this.zoom_x-5)
		   .attr("y1",(_this.zoom_y+_this.bar_len+_this.add_r+_this.add_r))
		   .attr("x2",_this.zoom_x+5)
		   .attr("y2",(_this.zoom_y+_this.bar_len+_this.add_r+_this.add_r))
		   .attr("stroke","#fff");

	},
	initEvent:function(){
		//初始化svg，tools等事件
		var _this = this;
		function zoomend() {
			if(_this.iszoomline){
				var cy;	
				if(_this.cirscale>0){
					 cy = _this.zoom_y+_this.add_r+_this.bar_len/2-_this.drag_r-((_this.bar_len/2)*_this.cirscale/5);
				}else{
					 cy=_this.zoom_y+_this.add_r+_this.bar_len/2-_this.drag_r-((_this.bar_len/2)*_this.cirscale/0.6);
				}
	            if(cy  > (_this.zoom_y+_this.add_r+_this.bar_len-_this.drag_r)){
	        	  cy = _this.zoom_y+_this.add_r+_this.bar_len-_this.drag_r;
	            }
	            if(cy <(_this.add_r+_this.zoom_y+_this.drag_r)){
	         	  cy =_this.add_r+_this.zoom_y+_this.drag_r;
	            } 
			    _this.cir.attr("cy",cy);
			}
		}
		function zoomed() {
			_this.cirscale = d3.event.scale-1;	
			_this.svg_g.attr("transform","translate(" + d3.event.translate + ")scale(" + d3.event.scale + ")");
		}
		function dragend() { 
			_this.iszoomline = false;
			_this.graphZoom.custom_scale(_this.scale,[_this.graphWidth/2,_this.graphHeight/2]);
			_this.iszoomline = true;
		}
		//拖动
		function dragmove(){
			d3.event.sourceEvent.stopPropagation(); 
			var dy = d3.event.y; 
			var diffY = dy ;
			if(diffY  > (_this.zoom_y+_this.add_r+_this.bar_len-_this.drag_r)){
				diffY = _this.zoom_y+_this.add_r+_this.bar_len-_this.drag_r;
				return ;
			}
			if(diffY <(_this.add_r+_this.zoom_y+_this.drag_r)){
				diffY =_this.add_r+_this.zoom_y+_this.drag_r;
				return ;
			} 
			console.log((_this.zoom_y+_this.add_r+_this.bar_len/2-_this.drag_r-diffY)/(_this.bar_len/2)*0.6) 
			console.log((_this.zoom_y+_this.add_r+_this.bar_len/2-_this.drag_r-diffY)/(_this.bar_len/2)*4) 
			if((_this.zoom_y+_this.add_r+_this.bar_len/2-_this.drag_r-diffY)>0){
				_this.scale =((_this.zoom_y+_this.add_r+_this.bar_len-_this.drag_r-diffY)/(_this.bar_len-_this.drag_r-_this.drag_r))*4+1; 
			}else{
				_this.scale =(_this.zoom_y+_this.add_r+_this.bar_len/2-_this.drag_r-diffY)/(_this.bar_len/2)*0.6+1
			} 
			_this.cir.attr("cy",diffY);
		}
		function subtract(d,loc){
			var sourceNode = _this.findNode(d.source.uuid);
			var targetNode = _this.findNode(d.target.uuid);
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
			var sourceNode = _this.findNode(d.source.uuid);
			var targetNode = _this.findNode(d.target.uuid);
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
		
		graph.force.on("tick", function(){
			var edges_line = graph.svg_g.selectAll("line");
			var edges_text = graph.svg_g.selectAll(".linetext");
			var edges_polygon = graph.svg_g.selectAll("polygon");
			var nodes_img = graph.svg_g.selectAll("image");
			var nodeEnter = graph.svg_g.selectAll("g.node");
			var nodes_text = graph.svg_g.selectAll(".nodetext");
			
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
					return x2+" "+y2+" "+(x2-7)+" "+(y2+15)+" "+x2+" "+(y2+8)+" "+(x2+7)+" "+(y2+15);
				})
				.attr("transform",function(d){
					var x2,y2;
					var x1,y1,rotate;
					var sourceNode = _this.findNode(d.source.uuid);
					var targetNode = _this.findNode(d.target.uuid);
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
					var rotate = Math.atan2(y2 - y1, x2 - x1) / (Math.PI / 180)+90;
					return "rotate(" +rotate+","+x2+","+y2+ ")" ;
				});
			edges_text.attr("transform",function(d){
				var x1 = d.source.x,
					y1 = d.source.y;
				var x2 = d.target.x,
					y2 = d.target.y;
				var rotate = Math.atan2(y2 - y1, x2 - x1) / (Math.PI / 180);
				if(rotate>90&&rotate<180){
					rotate=rotate+180;
				}
				if(rotate>-180&&rotate<-90){
					rotate=rotate+180;
				}
				return "translate(" + (d.source.x + d.target.x) / 2 + "," + (d.source.y + d.target.y) / 2+ ")"+
				"rotate(" +rotate+ ")" ;
			});
			nodeEnter.attr("transform", function(d) {
				return "translate(" +(d.x - _this.img_w / 2)+ "," +( d.y - _this.img_h / 2)+ ")";
			});
		});
		_this.graphZoom.on("zoom", zoomed).on("zoomend",zoomend);
		_this.toolAddModel.on("click",function(){
			if($(".imgScroll").find(".imgDiv").length>0){
				titanMan.showNodes("nodes");
			}else{
				hintManager.showWarnHint("请在指定源处添加数据源!");
			}
		});
		_this.toolDelNode.on("click",function(){
			if(graph.nodes.length > 0){
				disPage.showDialog("是否确认删除选中节点？", function(){
					graph.deleteNodes(pageVariate.selNode);
				});
			}else{
				hintManager.showWarnHint("没有模型可以删除!");
			}
		});
		_this.toolEmptyNodes.on("click",function(){
			if(graph.nodes.length > 0){
				disPage.showDialog("是否确认清空？", graph.emptyNodes);
			}else{
				hintManager.showWarnHint("没有模型可以清除!");
			}
		});

		_this.toolDrawEdge.on("click",function(){
			if(pageVariate.selNode.length != 1){
				hintManager.showWarnHint("请确保只选择了一个模型!");
			}else{
				var _this = this;
				$(_this).find("image").attr("href", pageVariate.base+"images/import/lineClick.png");
				pageVariate.drawEdge = true;
				pageVariate.pressCtrl = true;
			}
		});
		

		_this.cir.call(d3.behavior.drag().on("drag",dragmove).on("dragend",dragend));

		_this.add.on("click",function(){
			_this.graphZoom.custom_scale((_this.graphZoom.scale()+1),[_this.graphWidth/2,_this.graphHeight/2])
		});
		_this.cut.on("click",function (){
			if(_this.graphZoom.scale()>1){
				_this.graphZoom.custom_scale((_this.graphZoom.scale()-1),[_this.graphWidth/2,_this.graphHeight/2]);
			}else{
				_this.graphZoom.custom_scale((_this.graphZoom.scale()-0.6/4),[_this.graphWidth/2,_this.graphHeight/2]);
			}						
		});
		
		_this.svg.call(_this.graphZoom).on("dblclick.zoom", null);
		
		_this.svg.on('click',function(){
			// if(!pageVariate.isClickNode){
				// graphOperation.clearSelStatus();
				// tabsManager.clearShow(".personPage");
				// tabsManager.clearShow(".maincon");
				// tabsManager.histogramShow(1,nodes);
				// $('.first a').click();
				// timeLineOperation.showTimeLineById("all","all");
			// }
			// pageVariate.isClickNode = false;
		});
		//区域选中
		_this.svg.on('dblclick',function(){
			_this.svg.on('.zoom',null);
			_this.svg_g.datum(function() { return {selected: false, previouslySelected: false}; })
			.call(d3.svg.brush()
	        .x(d3.scale.identity().domain([-1000000, 1000000]))
	        .y(d3.scale.identity().domain([-1000000, 1000000]))
	        .on("brushstart", function(d) {
	        	pageVariate.selNode = [];
        		$("rect").css("fill-opacity", ".1").css("stroke", "#009bfa");  
        		_this.nodeEnter.each(function(d) { d.previouslySelected = _this.shiftKey && d.selected; });
        	 })
        	 .on("brush", function() {
		          var extent = d3.event.target.extent();
		          _this.nodeEnter.classed("selected", function(d) {
		            if( d.selected = d.previouslySelected ^(extent[0][0] <= d.x && d.x < extent[1][0]&& extent[0][1] <= d.y && d.y < extent[1][1])){
		            	graph.brushNodes(d);
		            }
		          });
	 		  })
	 		  .on("brushend", function() {
		        	_this.svg_g.style("fill-opacity", "1");
			        d3.event.target.clear();
			        d3.select(this).call(d3.event.target);
			        _this.svg_g.on('.brush',null);
		        	$(".background").remove();
		        	_this.svg.call(_this.graphZoom).on("dblclick.zoom", null);
		        	_this.svg_g.classed("brush",false);
	        	})
	        );
					
		});
	},
	drawUpdate:function(){
		//更新拓扑图
		var _this = this;
		_this.force.nodes(_this.nodes).links(_this.edges);
		
		//提示框tip
		var tip = d3.tip()
			.attr("class","d3-tip")
			.html(function(d){
				var tempArr;
				var id = d.id;
				var type = d.type;
				if(typeof(id) == "string")
					tempArr = id.split("-");
				var subList = d.subList;
				if(type=="Person"){
					return "name: "+d.name?d.name:""
				}
				if(subList){
					if(type=="EmailEvent"){return "count: "+subList.length+"<br/>"+ "from: "+tempArr[0]+"<br/>"+"to: "+tempArr[1]}
					if(type=="LoginEvent" || type=="StayEvent"){return "count: "+subList.length}
					if(type=="CallEvent"){return "count: "+subList.length+"<br/>"+"from: "+tempArr[0]+"<br/>"+"to: "+tempArr[1]}
				}else{
					return "";
				}
			});
		_this.svg_g.call(tip);
		
		var drag = _this.force.drag()
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
					_this.force.resume();
				}
			});
		var moused = function(e,d){
			if(e.which == 3){
				pageVariate.personId = d.id;
				pageVariate.uuid = d.uuid;
				pageVariate.currentNode = d;
				showMenu(e,$(".n"+d.uuid +" image"));//添加图片对象 方便定位
			}
		}

		//绘制结点
		var node = _this.svg_g.selectAll("g.node").data(_this.nodes, function(d) { return d.uuid;});
		var nodeEnter = node.enter().append("svg:g")
			.attr("class", function(d) {
				return "node n" + d.uuid;
			})
			.call(drag)
			.on('click', function(d){
				_this.isdb = false;
				if(_this.isdb != false)
					return;
				if(d.isdrag != d.isdrag2){
					d.isdrag = d.isdrag2;
					return;
				}
				_this.clickNode(d);
				pageVariate.isClickNode = true;
			})
			.on("dblclick", function(d, i) {
				_this.isdb=true;
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
		nodeEnter.append("svg:image")
			.attr("width", 60)
			.attr("height", 60)
			.attr("xlink:href", function(d) {
				return pageVariate.base+d.image;
			})
			.on("mouseover",tip.show)
			.on("mouseout",tip.hide);
		var nodeText = nodeEnter.append("svg:text")
			.attr("class", "nodetext")
			.attr("transform","translate(24,57)")

		nodeText.selectAll("tspan")
			.data(function(d) {
				var subList = d.subList;
				return [{data:d.name,dy:"1em",c:"name"}];
				/* 
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
				} */
			})
			.enter()
			.append("tspan")
			.attr("x",'0')
			.attr("dy",function(d) {return d.dy;})
			.attr("class",function(d) {return d.c;})
			.attr("text-anchor","middle")
			.text(function(d) {
				return d.data;
			});
		node.exit().remove();
		
		var edges_text = _this.svg_g.selectAll(".linetext").data(_this.edges, function(d){ return d.uuid; });
		var edgeText = edges_text.enter().append("svg:text")
			.attr("class", "linetext")
			.attr("dx", _this.line_text_dx)
			.attr("dy", _this.line_text_dy)
			.style("text-anchor","middle");

		edgeText.selectAll("tspan")
			.data(function(d) {
				return [
					{data:d.relation,dy:"-2"}
				]
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

		//绘制连接线	
		var edges_line = _this.svg_g.selectAll("line").data(_this.edges, function(d){ return d.uuid; });
		edges_line.enter().append("line")
			.style("stroke", "#004F89")
			.style("stroke-width", 2)
			.on('click', function(d) {
				graph.clickEdge(d);
			})
			.on("mouseover", function(d){
				var _this = this;
				$(_this).css({"stroke":"red","cursor": "pointer"});
			})
			.on("mouseout", function(d){
				var _this = this;
				$(_this).css({"stroke":"rgb(0, 79, 137)","cursor": "none"});
			});
		edges_line.exit().remove();
		
		var edges_polygon = _this.svg_g.selectAll("polygon").data(_this.edges,function(d){return d.uuid;});
		edges_polygon.enter().append("polygon").attr("fill","#3333FF").attr("stroke-width",3);
		edges_polygon.exit().remove();
		
		_this.force.start();
	},
	expandNode:function(nNodes, nEdges){
		//node-->nodes
		var _this = this;
        nNodes.forEach(function(node){
        	_this.nodes.push(node);
        });
		nEdges.forEach(function(link){
			_this.edges.push(link);
        });
        _this.drawUpdate();
    },
	clearSelStatus:function(){
		// 1、更改图片信息
		for(var i = 0; i < pageVariate.selNode.length; i ++){
			var oldImage = $(".n"+ pageVariate.selNode[i].uuid +" image").attr("href");
			var newImage = oldImage.replace("Sel.png", ".png");
			$(".n"+ pageVariate.selNode[i].uuid +" image").attr("href", newImage);
		}
		// 2、清空缓存的选中节点
		pageVariate.selNode = [];
	},
	clickNode:function(node){
		//点击拓扑图节点
		var _this = this;
		var type = node.type;
		var propertyList = node.custormVertexPropertyList;
		if(pageVariate.currentNode == node){
			// var index = pageVariate.selNode.indexOf(node);
			// if(index < 0){
				// pageVariate.selNode.push(node);
				// $(".n"+node.uuid +" image").attr("href", pageVariate.base+"images/img/"+type+"Sel.png");
				// pageVariate.currentNode = node;
			// }
			// else{
				// pageVariate.selNode.splice(index, 1);
				// $(".n"+node.uuid +" image").attr("href", pageVariate.base+"images/img/"+type+".png");
				// pageVariate.currentNode = "";
			// }
			_this.clearSelStatus();
			$(".n"+node.uuid +" image").attr("href", pageVariate.base+"images/img/"+type+"Sel.png");
			pageVariate.selNode.push(node);
			pageVariate.pressCtrl = false;
			pageVariate.drawEdge = false;
			$("#drawEdge").find("image").attr("href", pageVariate.base+"images/import/line.png");
			if($(".n"+node.uuid +" image").attr("href").indexOf("Sel.png") > 0){
				$("#mapping").empty();
				disPage.showMapping(node, "nodes");
			}
		}else{
			if(!pageVariate.pressCtrl){
				_this.clearSelStatus();
				pageVariate.currentNode = "";
			}
			pageVariate.selNode.push(node);
			$(".n"+node.uuid +" image").attr("href", pageVariate.base+"images/img/"+type+"Sel.png");
			pageVariate.currentNode = node;
			if(pageVariate.drawEdge){
				titanMan.showNodes("edges");
			}else{
				if($(".n"+node.uuid +" image").attr("href").indexOf("Sel.png") > 0){
					$("#mapping").empty();
					disPage.showMapping(node, "nodes");
				}
			}
		}
	},
	clickEdge:function(d){
		//点击拓扑图连线
		var propertyList = d.custormPropertyList;
		$("#mapping").empty();
		pageVariate.currentNode = d;
		disPage.showMapping(d, "edges");
	},
	brushNodes:function(node){
		//框选
		var type = node.type;
		var index = pageVariate.selNode.indexOf(node);
		if(index>-1){
		}else{
			$(".n"+node.uuid +" image").attr("href", pageVariate.base+"images/img/"+type+"Sel.png");
			pageVariate.selNode.push(node);
		}
	},
	getSelNodes:function(){
		//获取选中节点
		return pageVariate.selNode;
	},
	getUnSelNodes:function(){
		//获取非选中节点
		var _this = this;
    	var tempArr = [];
    	_this.nodes.forEach(function(node,index){
    		if(pageVariate.selNode.indexOf(node) < 0){
    			tempArr.push(node);
    		}
    	});
		return tempArr;
	},
	deleteNodes:function(nodes){
		//删除节点
		var _this = this;
		for(var i = 0; i < nodes.length; i ++){
			var uuid = nodes[i].uuid;
			if(uuid){
				var index = nodes[i].index;
				while(i < _this.edges.length){
					if(_this.edges[i]['source'].uuid == nodes[i].uuid || _this.edges[i]['target'].uuid == nodes[i].uuid)
						_this.edges.splice(i, 1);
					else
						++i;
				}
				_this.nodes.splice(index,1);
			}
		}
		_this.drawUpdate();
	},
	deleteChildNodes:function(){
		//删除子节点
	},
	emptyNodes:function(){
		//清空节点
		graph.nodes = [];
		graph.edges = [];
		graph.drawUpdate();
		//选中的缓存清空
		pageVariate.selNode = [];
		//查询事件类型缓存清空
		pageVariate.typeMap = new Map();
		//展开缓存清空
		pageVariate.openMap = new Map();
		
		pageVariate.personId = "";
        pageVariate.uuid = "";
	},
	findNode:function(uuid){
		//查找节点
		var _this = this;
		for(var i = 0;i < _this.nodes.length; i++) {
	        if (_this.nodes[i]['uuid'] == uuid ){
				return _this.nodes[i];
			}
	    }
	    return null;
	},
	findNodeByID:function(id){
		//查找节点
		var _this = this;
		for(var i = 0;i < _this.nodes.length; i++) {
	        if (_this.nodes[i]['id'] == id ){
				return _this.nodes[i];
			}
	    }
	    return null;
	},
	findNodeIndex:function(uuid){
		var _this = this;
		for(var i = 0;i < _this.nodes.length; i++) {
	        if (_this.nodes[i]['uuid'] == uuid ){
				return i;
			}
	    }
	    return null;
	},
	findNodeIndexById:function(id){
		var _this = this;
		for(var i = 0;i < _this.nodes.length; i++) {
	        if (_this.nodes[i]['id'] == id ){
				return i;
			}
	    }
	    return null;
	},
	findRelatedNodes:function(uuid){
		//获取关联节点
		var _this = this;
		var relatNodes = {};
		for(var i = 0; i < _this.edges.length; i ++){
			if(_this.edges[i].source.uuid == uuid){
				var type = _this.edges[i].target.type;
				if(!relatNodes[type]){
					relatNodes[type] = new Array();
				}
				relatNodes[type].push(_this.edges[i].target);
			}
			if(_this.edges[i].target.uuid == uuid){
				var type = _this.edges[i].source.type;
				if(!relatNodes[type]){
					relatNodes[type] = new Array();
				}
				relatNodes[type].push(_this.edges[i].source);
			}
		}
		return relatNodes;
	},
}

titanMan = {
	titanData:"",
	showNodes:function(type){
		//展示节点列表
		$.ajax({
			type:"post",
			url:"/MagicData/dataManage/getTitanStructure.action",
			success:function(msg){
				var thead = $("#addmodalmyModal").find("table[class='table modelList']").find("tbody");
				thead.empty();
				titanMan.titanData = msg;
				var data = {
					nodes : msg[0],
					edges : msg[1]
				}
				for(var i in data[type]){
					var trObj = $('<tr extend='+i+'></tr>');
					
					disPage.xxxx(trObj, {
						nStyle:{
							"background":"red"
						},
						dStyle:{
							"background":"white"
						},
						clickEvent:titanMan.showProperies,
						args:{
							"type" : type
						}
					},{
						nStyle:{
							"background":"red"
						},
						dStyle:{
							"background":"white"
						}
					},{
						nStyle:{
							"background":"red"
						},
						dStyle:{
							"background":"white"
						}
					});
					thead.append(trObj);
					trObj.append('<td class="col-md-6">' +
									'<span><img src="'+pageVariate.base+'images/img/'+i+'.png" style="width: 66px;height:66px;"/></span>' +
									'<span>'+ i +'</span>' +		           
								'</td>');
					var tdObj = $('<td class="col-md-6"></td>');
					trObj.append(tdObj);
					var editObj = $('<span class="editSpan"><img src="'+pageVariate.base+'images/import/bjIcon.png"/></span>');
					editObj.click(function(){
						var _this = this;
						var identity = $(_this).parent().parent().attr("extend");
						titanMan.modifyNode(type, identity);
					});
					tdObj.append(editObj);
					var delObj = $('<span class="deleteSpan"><img src="'+pageVariate.base+'images/import/deleteIcon.png"/></span>');
					delObj.click(function(){
						var _this = this;
						var identity = $(_this).parent().parent().attr("extend");
						titanMan.deleteNode(type, identity);
					});
					tdObj.append(delObj);
				}
				/*添加节点的弹出层*/
				$(".leftaddBtn").unbind("click");
				$(".leftaddBtn").attr("type", type);
				$(".leftaddBtn").click(function(){
					var _this = this;
					var type = $(_this).attr("type");
					titanMan.modifyNode(type);
				});
				/*添加节点属性弹出层*/
				$(".rightaddBtn").unbind("click");
				$(".rightaddBtn").attr("type", type);
				$(".rightaddBtn").click(function(){
					var _this = this;
					var type = $(_this).attr("type");
					var identity = titanMan.getCurrentNodeType();
					titanMan.modifyNode(type, identity);
				});
				var btnContainer = $("#addmodalmyModal").find(".modal-content.addmodal-content");
				btnContainer.attr("type", type);
				function randomString(len) {
					len = len || 32;
					var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
					var maxPos = chars.length;
					var pwd = '';
					for (i = 0; i < len; i++) {
						pwd += chars.charAt(Math.floor(Math.random() * maxPos));
					}
					return pwd;
				}
				btnContainer.find("button[class='btn bcBtn addNode']").unbind("click");
				btnContainer.find("button[class='btn bcBtn addNode']").click(function(){
					var type = btnContainer.attr("type");
					var nodeType = titanMan.getCurrentNodeType();
					if(!nodeType){
						alert("请选择节点类型!");
						return false;
					}
					var propertyList = new Array();
					for(var property = 0; property < data[type][nodeType].length; property ++){
						var proName = data[type][nodeType][property].name;
						var proType = data[type][nodeType][property].type;
						var proCardinality = data[type][nodeType][property].cardinality || 0;
						var proStrong = data[type][nodeType][property].strong || 0;
						var proIsIndex = data[type][nodeType][property].index || 0;
						propertyList.push({
							"propertyName" : proName,
							"propertyType" : proType,
							"isStrongProperty" : parseInt(proStrong),
							"isBuildTitanIndex" : parseInt(proIsIndex) || parseInt(proStrong),
							"isBuildSolrIndex" : parseInt(proIsIndex) || parseInt(proStrong),
							"databaseColumnId" : -1,
							"propertyId" : property,
							"propertyCardinality" : parseInt(proCardinality)
						});
					}
					var uuid = randomString(32);
					if(type == "nodes"){
						var subNode = {
							"image": "images/img/"+nodeType+".png",
							"name": nodeType + (graph.nodes.length+1),
							"type": nodeType,
							"uuid": uuid,
							"custormVertexPropertyList": propertyList
						}
						graph.nodes.push(subNode);
						graph.drawUpdate();
						GraphJSON.database.tableList[0].vertexList.push({
							"vertexId": graph.nodes.length-1,
							"uuid" : uuid,
							"custormVertexPropertyList": propertyList,
							"image": "images/img/"+nodeType+".png",
							"name": nodeType + graph.nodes.length,
							"type": nodeType,
						});
					}else if(type == "edges"){
						var sourceNO = graph.nodes.indexOf(pageVariate.selNode[0]);
						var targetNO = graph.nodes.indexOf(pageVariate.selNode[1]);
						var eventData = {
								nodes : [],
								edges : []
							};
						eventData.edges.push({
								"source":sourceNO,
								"target":targetNO,
								"direction":"out",
								"relation":nodeType,
								"uuid": uuid,
								"custormPropertyList" : propertyList
							});
						GraphJSON.database.tableList[0].edgeList.push({
							"id": graph.edges.length,
							"uuid" : uuid,
							"custormPropertyList": propertyList,
							"lableName": nodeType,
							"inVertexId": sourceNO,
							"outVertexId": targetNO,
							"source":sourceNO,
							"target":targetNO,
							"direction":"out",
							"relation":nodeType
						});
						graph.expandNode(eventData.nodes,eventData.edges);
						graph.clearSelStatus();
						pageVariate.selNode.push(pageVariate.currentNode);
						$(".n"+pageVariate.currentNode.uuid +" image").attr("href", pageVariate.base+"images/img/"+pageVariate.currentNode.type+"Sel.png");
						pageVariate.pressCtrl = false;
						pageVariate.drawEdge = false;
						$("#drawEdge").find("image").attr("href", pageVariate.base+"images/import/line.png");
					}
					$("#addmodalmyModal").modal('hide');
					btnContainer.find("button[class='btn bcBtn addNode']").unbind("click");
				});
				$('#addmodalmyModal').modal({backdrop: 'static', keyboard: false});
			}
		});
	},
	showProperies:function(args){
		var tbody = $("#addmodalmyModal").find("table[class='table propertyList']").find("tbody")
		tbody.empty();
		var data = {
			nodes : titanMan.titanData[0],
			edges : titanMan.titanData[1]
		}
		var propertyList = data[args.type][args.identity];
		for(var i = 0; i < propertyList.length; i ++){
			var trObj = $('<tr>' +
							'<td class="col-md-2">'+propertyList[i].name+'</td>' +
							'<td class="col-md-2">'+propertyList[i].type+'</td>' +
							'<td class="col-md-2">'+propertyList[i].strong+'</td>' +
							'<td class="col-md-2">'+propertyList[i].index+'</td>' +
							'<td class="col-md-2">'+propertyList[i].cardinality+'</td>' +
						'</tr>');
			disPage.xxxx(trObj, {
				nStyle:{
					"background":"red"
				},
				dStyle:{
					"background":"white"
				},
				clickEvent:function(){},
				args:{}
			},{
				nStyle:{
					"background":"red"
				},
				dStyle:{
					"background":"white"
				}
			},{
				nStyle:{
					"background":"red"
				},
				dStyle:{
					"background":"white"
				}
			});
			
			tbody.append(trObj);
		}
	},
	modifyNode:function(type, identity){
		if(arguments.length != 1){
			if(!identity){
				alert("请选择节点类型!");
				return false;
			}
		}
		$("#addmodalmyModal").modal('hide');
		$('#addnodemyModal').modal({backdrop: 'static', keyboard: false});
		var tbody = $("#addnodemyModal").find(".topnodeCon");
		tbody.empty();
		var data = {
			nodes : titanMan.titanData[0],
			edges : titanMan.titanData[1]
		}
		$("#modalName").val(identity);
		var propertyList = data[type][identity] || new Array();
		for(var i = 0; i < propertyList.length; i ++){
			var ulObj = titanMan.addProperty();
			ulObj.find(".attributeName").val(propertyList[i].name);
			ulObj.find(".attributeType").find("option[value='"+propertyList[i].type+"']").attr("selected", true);
			ulObj.find(".attributeCardinality").find("option[value='"+propertyList[i].cardinality+"']").attr("selected", true);
			
			if(propertyList[i].strong == 1){
				ulObj.find(".attributeSQ").attr("checked", true);
			}
			if(propertyList[i].index == 1){
				ulObj.find(".attributeSY").attr("checked", true);
			}
		}
		var bcBtn = $('#addnodemyModal').find("button[class='btn bcBtn addNode']");
		bcBtn.unbind("click");
		bcBtn.attr("extend", type);
		bcBtn.click(function(){
			var _this = this;
			var type = $(_this).attr("extend");
			titanMan.addNode(type);
			//TODO YXW
		});
		var qxBtn = $('#addnodemyModal').find("button[class='btn qxBtn']");
		qxBtn.unbind("click");
		qxBtn.click(function(){
			$("#addmodalmyModal").modal({backdrop: 'static', keyboard: false});
			$('#addnodemyModal').modal("hide");
		});
	},
	addNode:function(type){
		var dataBody = $("#addnodemyModal").find(".modal-body.addnodemodal-body.clearfix")
		var data = {
			model:{},
			property:[]
		};
		data.model.name = dataBody.find("#modalName").val();
		if(!data.model.name){
			alert("请输入模型名称！！");
		}else{
			var properties = dataBody.find(".topnodeCon ul");
			for(var i = 0; i < properties.length; i ++){
				var json = {
					"name": "",
					"type": "",
					"cardinality": 0,
					"strong": 0,
					"index": 0
				};
				
				json.name = $(properties[i]).find("input[class='attributeName']").val();
				json.type = $(properties[i]).find(".attributeType").find("option:selected").val();
				json.cardinality = parseInt($(properties[i]).find(".attributeCardinality").find("option:selected").val());
				var checked = $(properties[i]).find("input[class='attributeSQ']").attr("checked");
				if("checked" == checked){
					json.strong = 1;
				}else if("undefined" == checked || undefined == checked){
					json.strong = 0;
				}
				var isIndex = $(properties[i]).find("input[class='attributeSY']").attr("checked");
				if("checked" == isIndex){
					json.index = 1;
				}else if("undefined" == isIndex || undefined == isIndex){
					json.index = 0;
				}
				if(json.name){
					data.property.push(json);
				}
			}
			var url = "";
			var postData = {};
			if("nodes" == type){
				url = pageVariate.base + "/titanStruManage/addTitanVertex.action";
				postData.vertexName = data.model.name,
				postData.vertexJson = JSON.stringify(data.property)
			}else if("edges" == type){
				url = pageVariate.base + "/titanStruManage/addTitanEdge.action";
				postData.edgeName = data.model.name,
				postData.edgeJson = JSON.stringify(data.property)
			}
			if(url && !jQuery.isEmptyObject(postData)){
				$.ajax({
					type : "post",
					url : url,
					dataType:"json",
					data:postData,
					success:function(data){
						$("#addmodalmyModal").modal({backdrop: 'static', keyboard: false});
						$('#addnodemyModal').modal("hide");
						titanMan.showNodes(type);
					},
					error:function(data){
						alert("添加失败!");
					}
				});
			}
		}
	},
	deleteNode:function(){
		
	},
	getCurrentNodeType:function(){
		var thead = $("#addmodalmyModal").find("table[class='table modelList']").find("tbody");
		var type = thead.find("tr[click='true']").attr("extend");
		return type;
	},
	addProperty:function(){
		var tbody = $("#addnodemyModal").find(".topnodeCon");
		var ulObj = $('<ul class="list-unstyled clearfix">' +
						'<li>' +
							'<input type="text" class="attributeName" />' +
						'</li>' +
						'<li>' +
							'<select class="attributeType">' +
								'<option value="java.lang.String">字符串</option>' +
								'<option value="java.lang.Character">字符</option>' +
								'<option value="java.lang.Boolean">布尔</option>' +
								'<option value="java.lang.Byte">字节</option>' +
								'<option value="java.lang.Short">短</option>' +
								'<option value="java.lang.Integer">整形</option>' +
								'<option value="java.lang.Long">长整形</option>' +
								'<option value="java.lang.Float">浮点</option>' +
							'</select>' +
						'</li>' +
						'<li>' +
							'<select class="attributeCardinality">' +
								'<option value="1">单个值</option>' +
								'<option value="2">不可重复</option>' +
								'<option value="3">可以重复</option>' +
							'</select>' +
						'</li>' +
						'<li class="sq">' +
							'<input type="checkbox" name="" id="" class="attributeSQ"/>' +
						'</li>' +
						'<li class="sy">' +
							'<input type="checkbox" name="" id="" class="attributeSY"/>' +
						'</li>' +
						'<li class="add delnode">' +
							'<img src="'+pageVariate.base+'images/import/nodeDelete.png"/>' +
						'</li>' +
					'</ul>');
		tbody.append(ulObj);
		return ulObj;
	}
}

DBSource = {
	dbsData:"",
	currentDBS:"",
	allTables:"",
	showDBSrc:function(){
		$.ajax({
			type:"post",
			url:"/MagicData/ajax_dataImport/getDataUrl.action",
			success:function(msg){
				$('#adddataSource').find(".dataMessage").empty();
				$('#adddataSource').modal({backdrop: 'static', keyboard: false});
				var thead = $("#adddataSource").find("table[class='table dblist']").find("tbody");
				thead.empty();
				DBSource.dbsData = msg.dataBaseJson;
				for(var i in msg.dataBaseJson){
					var trObj = $('<tr extend='+i+'></tr>');
					disPage.xxxx(trObj, {
						nStyle:{
							"background-color":"#bcdeff"
						},
						dStyle:{
							"background":"white"
						},
						clickEvent:DBSource.showProperies,
						args:{}
					},{
						nStyle:{
							"background-color":"#bcdeff"
						},
						dStyle:{
							"background":"white"
						}
					},{
						nStyle:{
							"background-color":"#bcdeff"
						},
						dStyle:{
							"background":"white"
						}
					});
					thead.append(trObj);
					trObj.append('<td class="col-md-6">' +
									'<span><img src="'+pageVariate.base+'images/import/thirdimg1.png"/></span>' +
									'<span>'+ msg.dataBaseJson[i].connectionName +'</span>' +		           
								'</td>');
					var tdObj = $('<td class="col-md-6"></td>');
					trObj.append(tdObj);
					var editObj = $('<span class="editSpan"><img src="'+pageVariate.base+'images/import/bjIcon.png"/></span>');
					editObj.click(function(){
						var _this = this;
						var identity = $(_this).parent().parent().attr("extend");
						DBSource.currentDBS = identity;
						var args = DBSource.dbsData[identity];
						var action = DBSource.displayDBSPro(args);
						$("#adddataSource").modal('hide');
						var testBtn = $("#adddatabasemyModal").find(".btn.bcBtn.testBtn");
						testBtn.unbind("click");
						testBtn.click(function(){
							var _this = this;
							DBSource.testDBSrc(action);
						});
					});
					tdObj.append(editObj);
					var delObj = $('<span class="deleteSpan"><img src="'+pageVariate.base+'images/import/deleteIcon.png"/></span>');
					delObj.click(function(){
						var _this = this;
						var identity = $(_this).parent().parent().attr("extend");
						DBSource.deleteDBSrc(identity);
					});
					tdObj.append(delObj);
				}
				var btnContainer = $("#adddataSource").find(".modal-content.adddatamodal-content");
				btnContainer.find("button[class='btn bcBtn addDBS']").unbind("click");
				btnContainer.find("button[class='btn bcBtn addDBS']").click(function(){
					var dbsID = DBSource.getCurrentDBSource();
					if(!dbsID){
						alert("请选择连接库!");
						return false;
					}
					$.ajax({
						type:"post",
						url:"/MagicData/dataManage/findAllTables.action",
						data:{"dbConnId":dbsID},
						success:function(tableData){
							DBSource.allTables = tableData;
							for(var dbs in msg.dataBaseJson[dbsID]){
								GraphJSON.database[dbs] = msg.dataBaseJson[dbsID][dbs];
							}
							$("#adddataSource").modal('hide');
							DBSource.showSQL();
						}
					});
				});
			}
		});
	},
	showProperies:function(args){
		$(".dataMessage").empty();
		for(var j in DBSource.dbsData[args.identity]){
			$(".dataMessage").append('<p><span>'+j+'：</span><span>'+DBSource.dbsData[args.identity][j]+'</span></p>');
		}
	},
	testDBSrc:function(action){
		var connectionName = $('#connectionName').val();
		var connectionDB = $('#connectionDB').val();
		var connectionServerName = $('#connectionServerName').val();
		var connectionDbName = $('#connectionDbName').val();
		var connectionPort = $('#connectionPort').val();
		var connectionDBUserName = $('#connectionDBUserName').val();
		var connectionDBPassword = $('#connectionDBPassword').val();
		var testData = {
			connectionName:connectionName,
			connectionDB:connectionDB,
			connectionServerName:connectionServerName,
			connectionDbName:connectionDbName,
			connectionPort:connectionPort,
			connectionDBUserName:connectionDBUserName,
			connectionDBPassword:connectionDBPassword 
		}
		$.ajax({
			type : "post",
			url : "/MagicData/Import.action",
			dataType : "json",
			data : testData,
			success:function(data){
				var testBtn = $("#adddatabasemyModal").find(".btn.bcBtn.testBtn");
				testBtn.unbind("click");
				if(action){
					testBtn.html("确定");
					testBtn.click(function(){
						var _this = this;
						DBSource.addDBSrc(testData);
					});
				}else{
					testBtn.html("修改");
					testBtn.click(function(){
						var _this = this;
						testData.id = DBSource.currentDBS;
						DBSource.modifyDBSrc(testData);
					});
				}
			},
			error:function(data){
			}
		});
	},
	testSQL:function(sqlArray){
		for(var s in sqlArray){
			var columnString = sqlArray[s].removeLastComma();
			var sql = "select " + columnString + " from " + s;
			var result = ""
			$.ajax({
				type : "post",
				url:"/MagicData/dataManage/queryTopFiveRecordsBySQL.action",
				dataType:"json",
				async: false,
				data:{"sql":sql,"column":columnString},
				success:function(data){
					result = data.success;
					return result;
				},
				error:function(data){
					result = "false";
					return result;
				}
			});
			if(result == "false"){
				return result;
			}
		}
		return result;
	},
	modifyDBSrc:function(testData){
		$.ajax({
			type : "post",
			url : "/MagicData/ImportEdit.action",
			dataType : "json",
			data : testData,
			success : function(data){
				DBSource.showDBSrc();
			},
			error : function(){
				hintManager.showHint("连接名不能相同");
			}
		});
	},
	addDBSrc:function(testData){
		$.ajax({
			type : "post",
			url : "/MagicData/ImportSave.action",
			dataType : "json",
			data : testData,
			success : function(data){
				DBSource.showDBSrc();
			},
			error : function(){
				hintManager.showHint("连接名不能相同");
			}
		});
	},
	deleteDBSrc:function(identity){
		confirm("删除此数据库，将会关联此数据库未执行的任务一并删除，是否确认删除？", function(){
			$.ajax({
				type : "post",
				url : "/MagicData/ImportDelete.action",
				data : DBSource.dbsData[identity],
				success : function(taskID){
					$(_this).remove();
				}
			});
		});
	},
	showAllTables:function(){
		$("#SQL").find(".tableList").empty();
		for(var table in DBSource.allTables){
			var tableObj = $(
				'<div class="table1">' +
					'<p class="tabletitleP">'+ table +'</p>' +
					'<div class="table1Con clearfix">' +
						'<ul class="list-unstyled clearfix"></ul>' +
					'</div>' +
				'</div>');
			$("#SQL").find(".tableList").append(tableObj);
			for(var i in DBSource.allTables[table]){
				tableObj.find("ul").append(
					'<li>' +
						'<span class="tablefieldSpan">'+ i +'</span>' +
						'<input type="checkbox" name="" value="" />' +
					'</li>');
			}
		}
		var btnContainer = $("#SQL").find(".modal-content.addsqlmodal-content");
		btnContainer.find("button[class='btn bcBtn']").unbind("click");
		btnContainer.find("button[class='btn bcBtn']").html("测试");
		btnContainer.find("button[class='btn bcBtn']").click(function(){
			var tableListObj = $("#SQL").find(".tableList").find(".table1");
			var sql = "select ";
			var tab = " from "
			for(var i = 0; i < tableListObj.length; i ++){
				var inputs = $(tableListObj[i]).find("input");
				for(var p = 0; p < inputs.length; p ++){
					var check = $(inputs[p]).attr("checked");
					if(check == "checked"){
						var tableName = $(tableListObj[i]).find(".tabletitleP").html();
						tab += (tableName+",");
						break;
					}
				}
				var liObj = $(tableListObj[i]).find("li");
				for(var j = 0; j < liObj.length; j ++){
					var ch = $(liObj[j]).find("input").attr("checked");
					if(ch){
						var col = $(liObj[j]).find("span").html();
						sql += (tableName+"."+col+",");
					}
				}
				
			}
			sql = sql.removeLastComma();
			tab = tab.removeLastComma();
			sql+=tab;
			console.log(sql)
			var sqlArray = DBSource.parseSQL(sql);
			var result = DBSource.testSQL(sqlArray);
			if(result == "false"){
				return false;
			}
			var _this = this;
			$(_this).html("确定");
			$(_this).unbind("click");
			$(_this).click(function(){
				GraphJSON.database.tableList[0].sql = sql;
				var index = 0;
				var colIndex = 0;
				for(var s in sqlArray){
					disPage.addTitle({
						imgSrc : pageVariate.base+"images/import/topImg1.png",
						total : 48,
						table : s,
						columns : sqlArray[s]
					});
					var tableName = {
							"id": index,
							"tableName": s
						};
					if(GraphJSON.database.tableList[0].tablesNameList.indexOf(tableName) == -1){
						GraphJSON.database.tableList[0].tablesNameList.push(tableName);
					}
					var colArr = sqlArray[s].split(",");
					for(var i = 0; i < colArr.length; i ++){
						if(colArr[i] != ""){
							var col = {
									"tableId": index,
									"columnName": colArr[i],
									"columnType": "java.lang.String",
									"columnId": colIndex
								};
							if(GraphJSON.database.tableList[0].columnPropertyList.indexOf(col) == -1){
								GraphJSON.database.tableList[0].columnPropertyList.push(col);
							}
						}
						colIndex ++;
					}
					
					index ++;
				}
				disPage.whereRelation(sqlArray);
				disPage.showMapping({});
				$('#SQL').modal('hide');
				btnContainer.find("button[class='btn bcBtn']").unbind("click");
			});
		});
		btnContainer.find("button[class='btn qxBtn']").unbind("click");
		btnContainer.find("button[class='btn qxBtn']").click(function(){
			$('#SQL').modal('hide');
			$("#adddataSource").modal({backdrop: 'static', keyboard: false});
			btnContainer.find("button[class='btn qxBtn']").unbind("click");
		});
		$('#SQL').modal({backdrop: 'static', keyboard: false});
		return true;
		
		tableDataDetail = tableData;
		$("#selectDBTables").show();
		$("#selectDBTables .box1").empty();
		var DBTablesTitle = $('<h1><div style="width: 81%;float:left;">请选择数据源</div></h1>');
		var DBTablesSel = $('<div>SQL语句</div>');
		DBTablesTitle.append(DBTablesSel);
		$("#selectDBTables .box1").append(DBTablesTitle);
		DBTablesSel.click(function(){
			var _this = this;
			var action = DBTablesSel.html(); 
			if(action == "SQL语句"){
				$(_this).html("表结构");
				$("#selectDBTables .box1 .selectTablesDetail").empty();
				$("#selectDBTables .box1 .selectTablesDetail").append('<div class="selectTablesList"></div>');
				
				for(var i in tableDataDetail){
					var item = $('<div class="item" title="' + i + '">' +
									'<img src="'+pageVariate.base+'images/img/'+i+'.png" style="width: 100px;height: 100px;margin: 0 5px;" onerror="javascript:this.src=\''+pageVariate.base+'images/img/default.png\'">' +
									'<span extend="'+i+'">' + i + '</span>' +
								'</div>');
					item.click(function(){
						var _this = this;
						var id = $(_this).find("span").attr("extend");
						console.log(tableDataDetail[id]);
					});
					$("#selectDBTables .box1 .selectTablesDetail .selectTablesList").append(item);
				}
				
			}else if(action == "表结构"){
				$(_this).html("SQL语句");
				$("#selectDBTables .box1 .selectTablesDetail").empty();
				$("#selectDBTables .box1 .selectTablesDetail").append('<div style="margin-left: 50px;float: left;margin-bottom: 15px;">请在此处输入SQL语句</div>');
				$("#selectDBTables .box1 .selectTablesDetail").append('<input type="text" value="test"/>');
				$("#selectDBTables .box1 .selectTablesDetail").append('<textarea style="width: 90%;height: 60%;resize: none;" placeholder="请在此处输入SQL语句..." autofocus>'+GraphJSON.database.tableList[0].sql+'</textarea>');
				var div = $('<div style="height: 25%;overflow-y: auto;"></div>');
				$("#selectDBTables .box1 .selectTablesDetail").append(div);
				for(var i in tableDataDetail){
					div.append('<span style="margin-left:10px;float: left;width: 45%;border-right: solid 1px black;">'+i+';</span>');
				}
			}
		});
		
		$("#selectDBTables .box1").append('<div class="selectTablesDetail"></div>');
		$("#selectDBTables .box1 .selectTablesDetail").append('<div style="margin-left: 50px;float: left;margin-bottom: 15px;">请在此处输入SQL语句</div>');
		$("#selectDBTables .box1 .selectTablesDetail").append('<input type="text" value="test"/>');
		$("#selectDBTables .box1 .selectTablesDetail").append('<textarea style="width: 90%;height: 60%;resize: none;" placeholder="请在此处输入SQL语句..." autofocus>'+GraphJSON.database.tableList[0].sql+'</textarea>');
		var div = $('<div style="height: 25%;overflow-y: auto;"></div>');
		$("#selectDBTables .box1 .selectTablesDetail").append(div);
		for(var i in tableDataDetail){
			div.append('<span style="margin-left:10px;float: left;width: 45%;border-right: solid 1px black;">'+i+';</span>');
		}
		$("#selectDBTables .box1").append('<div></div>');
		var DBTablesSuerBtn = $('<input type="button" class="xgBtn" value="确定" />');
		DBTablesSuerBtn.click(function(){
			var _this = this;
			var sql = $("#selectDBTables .box1 .selectTablesDetail textarea").val().toLowerCase();
			var sqlArray = DBSource.parseSQL(sql);
			var showName = $("#selectDBTables .box1 .selectTablesDetail input").val();
			GraphJSON.database.tableList[0].sql = sql;
			for(var s in sqlArray){
				disPage.addTitle({
					imgSrc : pageVariate.base+"images/import/topImg1.png",
					total : 48,
					table : s,
					columns : sqlArray[s]
				});
			}
			disPage.whereRelation(sqlArray);
			disPage.showMapping({});
		});
		$(".selectTablesDetail").next().append(DBTablesSuerBtn);
		var gobackBtn = $('<input type="button" class="gobackBtn" value="返回" />');
		gobackBtn.click(function(){
			var _this = this;
			$("#selectDBTables").hide();
			$("#selectDBSource").show();
		});
		$(".selectTablesDetail").next().append(gobackBtn);
		var DBTablesCancelBtn = $('<input type="button" class="closeBtn" value="关闭" />');
		DBTablesCancelBtn.click(function(){
			var _this = this;
			$("#selectDBTables").hide();
		});
		$(".selectTablesDetail").next().append(DBTablesCancelBtn);
	},
	parseSQL:function(sql){
		//TODO 需要的判断select from等字段是否重复
		// select id, x, a from test;
		// select a.id, a.x, a.a from test a;
		// select a.id, a.x, b.a from test a, test2 b;
		/* {
			test : id, x, a
		}
		{
			test : id, x, a
		}
		{
			test : id, x
			test2 : a
		} */
		sql = sql.split(" where ")[0].trim().replace(/;/g, "");
		var json = {};
		var allColumn = sql.split(" from ")[0].trim().replace(/select/g, "").split(",");
		var columnPropertyList = "";
		for(var c = 0; c < allColumn.length; c ++){
			var column = allColumn[c].trim();
			if(column.indexOf(".") > -1){
				var tc = column.split(".");
				if(!json[tc[0]])
					json[tc[0]] = "";
				json[tc[0]] += (tc[1]+",");
			}else{
				columnPropertyList += (column + ",");
			}
		}
		var result = {};
		var flag = 0;
		var tableArray = sql.split(" from ")[1].trim().split(",");
		for(var t = 0; t < tableArray.length; t ++){
			var table = tableArray[t].trim();
			if(jQuery.isEmptyObject(json)){
				result[table] = columnPropertyList;
			}else{
				for(var j in json){
					var table2 = table.split(" ")[0];
					var table3 = table.split(" ")[1];
					if(j == table3 || j == table2){
						result[table2] = json[j].removeLastComma();
					}
				}
			}
		}
		return result;
	},
	displayDBSPro:function(args){
		var disPro = $("#adddatabasemyModal").find(".modal-content.adddatabasemodal-content");
		disPro.empty();
		disPro.append(
			'<div class="modal-header">' +
				'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
				'<h4 class="modal-title" id="">添加数据源<em>>></em><span>添加数据库</span></h4>' +
			'</div>' +
			'<div class="modal-body adddatabasemodal-body clearfix">' +
				'<div class="databasenameDiv">' +
					'<div class="form-group clearfix">' +
						'<label class="labelTit">数据库名称</label>' +
						'<input type="text" id="connectionName" class="form-control" placeholder="请输入数据库名称" />' +
					'</div>' +
				'</div>' +
				'<div class="databaseconDiv">' +
					'<div class="topdatabase clearfix">' +
						'<ul class="list-unstyled clearfix">' +
							'<li>数据库参数</li>' +
							'<li>数据库类型</li>' +
							'<li>主机名称</li>' +
							'<li>数据库名称</li>' +
							'<li>端口号</li>' +
							'<li>用户名</li>' +
							'<li>密码</li>' +
						'</ul>' +
						'<div class="topdatabaseCon">' +
							'<ul class="list-unstyled clearfix">' +
								'<li>参数值：</li>' +
								'<li>' +
									'<select id="connectionDB" name="connectionDB" onchange="DBSource.ImportText(this,\'\')">' +
										'<option value="MYSQL" selected="true">MySQL</option>' +
										'<option value="ORACLE">Oracle</option>' +
										'<option value="MS SQL">MS SQL</option>' +
										'<option value="DB2">DB2</option>' +
										'<option value="AS/400">AS/400</option>' +
										'<option value="SYBASE">Sybase</option>' +
										'<option value="POSTGRESQL">Postgresql</option>' +
										'<option value="DERBY">Derby</option>' +
										'<option value="FIREBIRD">Firebird</option>' +
										'<option value="GREENPLUM">Greenplum</option>' +
										'<option value="H2">H2</option>' +
										'<option value="HadoopHive">HadoopHive</option>' +
										'<option value="SQLITE">Sqlite</option>' +
									'</select>' +
								'</li>' +
								'<li>' +
									'<input type="text" class="addDBSourceParam" value="" id="connectionServerName" placeholder="192.168.1.1"/>' +
								'</li>' +
								'<li>' +
									'<input type="text" class="addDBSourceParam" value="" id="connectionDbName" placeholder="magicdata"/>' +
								'</li>' +
							'</ul>' +
						'</div>' +
					'</div>' +
				'</div>' +
			'</div>' +
			'<div class="modal-footer">' +
				'<button type="button" class="btn bcBtn testBtn">测试</button>' +
				'<button type="button" class="btn qxBtn">返回</button>' +
			'</div>'
		);
		$("button[class='close']").remove();
		var qxBtn = disPro.find("button[class='btn qxBtn']");
		qxBtn.unbind("click");
		qxBtn.click(function(){
			$("#adddataSource").modal({backdrop: 'static', keyboard: false});
			$('#adddatabasemyModal').modal("hide");
		});
		if(arguments.length > 0){
			disPro.find("h4 span").html(args.title);
			disPro.find("#connectionName").val(args.connectionName);
			disPro.find("#connectionDB").find("option[value='"+args.connectionDB.toUpperCase()+"']").attr("selected",true);
			DBSource.ImportText("", args);
			$('#adddatabasemyModal').modal({backdrop: 'static', keyboard: false});
			return false;
		}
		
		disPro.find(".topdatabaseCon").find("ul").append('<li>' +
									'<input type="text" class="addDBSourceParam" value="3306" id="connectionPort" placeholder="3306"/>' +
								'</li>');
		disPro.find(".topdatabaseCon").find("ul").append('<li>' +
									'<input type="text" class="addDBSourceParam" value="" id="connectionDBUserName" placeholder="admin"/>' +
								'</li>');
		disPro.find(".topdatabaseCon").find("ul").append('<li>' +
									'<input type="password" class="addDBSourceParam" value="" id="connectionDBPassword"/>' +
								'</li>');
		$('#adddatabasemyModal').modal({backdrop: 'static', keyboard: false});
		return true;
	},
	ImportText:function(obj, dataJSON){
		var databasename = "";
		if(undefined == dataJSON.connectionDB || "" == dataJSON.connectionDB){
			databasename = $(obj).children('option:selected').val();
		}else{
			databasename = dataJSON.connectionDB;
		}
		$("#adddatabasemyModal").find(".addDBSourceParam").remove();
		var disPro = $("#adddatabasemyModal").find(".modal-content.adddatabasemodal-content");
		disPro.find(".topdatabaseCon").find("ul").append('<li>' +
									'<input type="text" class="addDBSourceParam" value="" id="connectionServerName" placeholder="192.168.1.1"/>' +
								'</li>');
		disPro.find(".topdatabaseCon").find("ul").append('<li>' +
									'<input type="text" class="addDBSourceParam" value="" id="connectionDbName" placeholder="magicdata"/>' +
								'</li>');
		if(databasename == "MYSQL"||databasename == "DERBY"||databasename == "ORACLE"||databasename == "HadoopHive"||databasename == "H2"||databasename == "DB2"||databasename == "MSSQL"||databasename == "SYBASE"||databasename == "POSTGRESQL"||databasename == "GREENPLUM"||databasename == "FIREBIRD"){
			var port = dataJSON.connectionPort || "";
			if(databasename == "MYSQL"){port = 3306}else if(databasename == "ORACLE"){port = 1521}else if(databasename == "HadoopHive"){port = 9000}else if(databasename == "MSSQL"){port = 1433}else if(databasename == "H2"){port = 8082}else if(databasename == "DB2"){port = 50000}else if(databasename == "SYBASE"){port = 5001}else if(databasename == "POSTGRESQL"||databasename == "GREENPLUM"){port = 5432}else if(databasename == "FIREBIRD"){port = 3050}else if(databasename == "DERBY"){port = 1527}
			disPro.find(".topdatabaseCon").find("ul").append('<li>' +
										'<input type="text" class="addDBSourceParam" value="'+port+'" id="connectionPort" placeholder="3306"/>' +
									'</li>');
			disPro.find(".topdatabaseCon").find("ul").append('<li>' +
										'<input type="text" class="addDBSourceParam" value="" id="connectionDBUserName" placeholder="admin"/>' +
									'</li>');
			disPro.find(".topdatabaseCon").find("ul").append('<li>' +
										'<input type="password" class="addDBSourceParam" value="" id="connectionDBPassword"/>' +
									'</li>');
		}else if(databasename == "AS/400"){//no Port
			disPro.find(".topdatabaseCon").find("ul").append('<li>' +
										'<input type="text" class="addDBSourceParam" value="" id="connectionDBUserName" placeholder="admin"/>' +
									'</li>');
			disPro.find(".topdatabaseCon").find("ul").append('<li>' +
										'<input type="password" class="addDBSourceParam" value="" id="connectionDBPassword"/>' +
									'</li>');
		}else if(databasename == "SQLITE"){// no 数据库名称 port & username & passport
		}
		disPro.find("#connectionServerName").val(dataJSON.connectionServerName || "");
		disPro.find("#connectionDbName").val(dataJSON.connectionDbName || "");
		disPro.find("#connectionDBUserName").val(dataJSON.connectionDBUserName || "");
		disPro.find("#connectionDBPassword").val(dataJSON.connectionDBPasswordBak || "");
	},
	getCurrentDBSource:function(){
		var thead = $("#adddataSource").find("table[class='table dblist']").find("tbody");
		var dbsID = thead.find("tr[click='true']").attr("extend");
		return dbsID;
	},
	showSQL:function(){
		var btnContainer = $("#SQL").find(".modal-content.addsqlmodal-content");
		btnContainer.find("button[class='btn bcBtn']").unbind("click");
		btnContainer.find("button[class='btn bcBtn']").html("测试");
		btnContainer.find("button[class='btn bcBtn']").click(function(){
			var sql = $("#SQL").find("textarea").val().toLowerCase();
			var sqlArray = DBSource.parseSQL(sql);
			var result = DBSource.testSQL(sqlArray);
			if(result == "false"){
				return false;
			}
			var _this = this;
			$(_this).html("确定");
			$(_this).unbind("click");
			$(_this).click(function(){
				GraphJSON.database.tableList[0].sql = sql;
				var index = 0;
				var colIndex = 0;
				for(var s in sqlArray){
					disPage.addTitle({
						imgSrc : pageVariate.base+"images/import/topImg1.png",
						total : 48,
						table : s,
						columns : sqlArray[s]
					});
					var tableName = {
							"id": index,
							"tableName": s
						};
					if(GraphJSON.database.tableList[0].tablesNameList.indexOf(tableName) == -1){
						GraphJSON.database.tableList[0].tablesNameList.push(tableName);
					}
					var colArr = sqlArray[s].split(",");
					for(var i = 0; i < colArr.length; i ++){
						if(colArr[i] != ""){
							var col = {
									"tableId": index,
									"columnName": colArr[i],
									"columnType": "java.lang.String",
									"columnId": colIndex
								};
							if(GraphJSON.database.tableList[0].columnPropertyList.indexOf(col) == -1){
								GraphJSON.database.tableList[0].columnPropertyList.push(col);
							}
						}
						colIndex ++;
					}
					
					index ++;
				}
				disPage.whereRelation(sqlArray);
				disPage.showMapping({});
				$('#SQL').modal('hide');
				btnContainer.find("button[class='btn bcBtn']").unbind("click");
			});
		});
		btnContainer.find("button[class='btn qxBtn']").unbind("click");
		btnContainer.find("button[class='btn qxBtn']").click(function(){
			$('#SQL').modal('hide');
			$("#adddataSource").modal({backdrop: 'static', keyboard: false});
			btnContainer.find("button[class='btn qxBtn']").unbind("click");
		});
		$('#SQL').modal({backdrop: 'static', keyboard: false});
	}
}

csvSource = {
	csvData : "",
	currentCSV : "",
	selCSV : {},
	showCSVsrc : function(){
		$.ajax({
			type:"post",
			url:"/MagicData/csvDataManage/findCsvFileList.action",
			success:function(msg){
				var data = {};
				for(var i = 0; i < msg.list.length; i ++){
					data[msg.list[i].id+"trusteye"+msg.list[i].fileName.split(".")[0]] = msg.list[i];
				}
				$("#adddataSource").find(".csvrightCon").css("display", "none");
				var tbody = $("#adddataSource").find("table[class='table csvlist']").find("tbody");
				tbody.empty();
				csvSource.csvData = data;
				for(var i in data){
					var trObj = $('<tr extend='+i+'></tr>');
					
					
					trObj.click(function(){
						var _this = this;
						$(_this).parent().children().css({
							"background":"white"
						});
						$(_this).parent().children().attr("click", "false");
						var args = {};
						var identity = $(_this).attr("extend");
						args.identity = identity;
						csvSource.currentCSV = identity;
						csvSource.showProperies(args);
						
						
						for(var i in csvSource.selCSV){
							if(csvSource.selCSV[i].length > 0){
								$(_this).parent().find('tr[extend="'+i+'"]').css({
									"background":"#bcdeff"
								});
								$(_this).parent().find('tr[extend="'+i+'"]').attr("click", "true");
							}
						}
						$(_this).css({
							"background":"#bcdeff"
						});
						$(_this).attr("click", "true");
						
					});
					trObj.hover(function(){
						var _this = this;
						$(_this).parent().children().css({
							"background":"white"
						});
						$(_this).css({
							"background":"#bcdeff"
						});
						$(_this).parent().find("[click='true']").css({
							"background":"#bcdeff"
						});
					},function(){
						var _this = this;
						$(_this).parent().children().css({
							"background":"white"
						});
						$(_this).parent().find("[click='true']").css({
							"background":"#bcdeff"
						});
					});
					
					
					
					
					tbody.append(trObj);
					trObj.append('<td class="col-md-9">' +
									'<span><img src="'+pageVariate.base+'images/import/thirdimg1.png"/></span>' +
									'<span>'+ data[i].fileName +'</span>' +		           
								'</td>');
					var tdObj = $('<td class="col-md-3" style="text-align: center;"></td>');
					trObj.append(tdObj);
					var editObj = $('<span class="editSpan"><img src="'+pageVariate.base+'images/import/bjIcon.png"/></span>');
					editObj.click(function(){
						var _this = this;
						var identity = $(_this).parent().parent().attr("extend");
						identity = identity.split("trusteye")[0];
						$.ajax({
							type:"post",
							url:"/MagicData/csvDataManage/findCsvDataById.action?id="+identity,
							dataType:"json",
							success:function(data){
								var csvData = data.csvData;
								var action = csvSource.displayCSVPro(csvData);
								$("#adddataSource").modal('hide');
								var testBtn = $("#addCsv").find(".btn.bcBtn");
								testBtn.html("更新");
								testBtn.unbind("click");
								testBtn.click(function(){
									var _this = this;
									csvSource.modifyCSVSrc(identity);
								});
							}
						});
					});
					tdObj.append(editObj);
					var delObj = $('<span class="deleteSpan"><img src="'+pageVariate.base+'images/import/deleteIcon.png"/></span>');
					delObj.click(function(){
						var _this = this;
						var identity = $(_this).parent().parent().attr("extend");
						identity = identity.split("trusteye")[0];
						csvSource.deleteCSVSrc(identity);
					});
					tdObj.append(delObj);
				}
				var btnContainer = $("#adddataSource").find(".modal-content.adddatamodal-content");
				btnContainer.find("button[class='btn bcBtn addDBS']").unbind("click");
				btnContainer.find("button[class='btn bcBtn addDBS']").click(function(){
					var csvID = csvSource.getCurrentCSVSource();
					if(!csvID){
						alert("请选择csv文件!");
						return false;
					}
					var index = 0;
					var colIndex = 0;
					for(var i in csvSource.selCSV){
						if(csvSource.selCSV[i].length > 0){
							disPage.addTitle({
								imgSrc : pageVariate.base+"images/import/topImg1.png",
								total : 48,
								table : i,
								columns : csvSource.selCSV[i].join().replace(/"/g, "")
							});
						}
						var isHasHeader = csvSource.csvData[i].titleFlag;
						if(isHasHeader)
							isHasHeader = 1;
						else
							isHasHeader = 0;
						
						var tableName = {
							"id": index,
							"delimeter":csvSource.csvData[i].fileSeparator,
							"isHasHeader":isHasHeader,
							"idTableName": i,
							"tableName": i.split("trusteye")[1]
						};
						if(GraphJSON.database.tableList[0].tablesNameList.indexOf(tableName) == -1){
							GraphJSON.database.tableList[0].tablesNameList.push(tableName);
						}
						
						for(var j = 0; j < csvSource.selCSV[i].length; j ++){
							if(csvSource.selCSV[i][j] != ""){
								var col = {
										"tableId": index,
										"columnName": csvSource.selCSV[i][j],
										"columnType": "java.lang.String",
										"columnId": colIndex
									};
								if(GraphJSON.database.tableList[0].columnPropertyList.indexOf(col) == -1){
									GraphJSON.database.tableList[0].columnPropertyList.push(col);
								}
							}
							colIndex ++;
						}
						index ++;
					}
					disPage.whereRelation(csvSource.selCSV);
					var list = GraphJSON.database.tableList[0].columnPropertyList;
					var sql = "select ";
					for(var l = 0; l < list.length; l ++){
						sql += (GraphJSON.database.tableList[0].tablesNameList[list[l].tableId].tableName+"."+list[l].columnName + ", ");
					}
					sql = sql.removeLastComma();
					sql += " from ";
					var tables = GraphJSON.database.tableList[0].tablesNameList;
					for(var t = 0; t < tables.length; t ++){
						sql += (tables[t].tableName + ", ");
					}
					sql = sql.removeLastComma();
					
					var errorSql = "select ";
					for(var l = 0; l < list.length; l ++){
						errorSql += (GraphJSON.database.tableList[0].tablesNameList[list[l].tableId].idTableName+"."+list[l].columnName + ", ");
					}
					errorSql = errorSql.removeLastComma();
					errorSql += " from ";
					var tables = GraphJSON.database.tableList[0].tablesNameList;
					for(var t = 0; t < tables.length; t ++){
						errorSql += (tables[t].idTableName + ", ");
					}
					errorSql = errorSql.removeLastComma();
					
					GraphJSON.database.tableList[0].sql = sql;
					GraphJSON.database.tableList[0].errorSql = errorSql;
					console.log(sql);
					console.log(errorSql);
					
					$("#adddataSource").modal("hide");
				});
			}
		});
	},
	showProperies : function(args){
		var key = args.identity;
		$.ajax({
			type : "post",
			url : "/MagicData/csvDataManage/findCsvSampleData.action",
			data : {id : key.split("trusteye")[0]},
			success : function(msg){
				$("#adddataSource").find(".csvrightCon").css("display", "block");
				var columns = $("#adddataSource").find(".fieldsDiv").find(".fieldsCon");
				columns.empty();
				var thead = $("#adddataSource").find("table[class='table csvSampleData']").find("thead");
				thead.empty();
				var trObjH = $("<tr><th class=\"firstTh\">列</th></tr>");
				thead.append(trObjH);
				for(var i in msg.finalMap.rows[0]){
					trObjH.append('<th class="">'+i+'</th>');
					columns.append('<label class="checkbox-inline">' +
										'<input type="checkbox" id="" value="'+i+'"><span>' + i + 
									'</span></label>');
					if(csvSource.selCSV[key] && csvSource.selCSV[key].indexOf(i) > -1){
						columns.find("input[value='"+i+"']").attr("checked", true);
					}
				}
				columns.find("input[type='checkbox']").click(function(){
					var _this = this;
					if(!csvSource.selCSV[key]){
						csvSource.selCSV[key] = new Array();
					}else if(csvSource.selCSV[key].length > 0){
						
					}
					var checkValue = $(_this).parent().find("span").html();
					if($(_this).attr("checked") == "checked" || $(_this).attr("checked") == true){
						csvSource.selCSV[csvSource.currentCSV].push(checkValue);
						console.log(csvSource.selCSV);
					}else{
						csvSource.selCSV[csvSource.currentCSV].remove(checkValue);
						console.log(csvSource.selCSV);
					}
					var children = trObjH.children();
					for(var h = 0; h < children.length; h ++){
						if(csvSource.selCSV[csvSource.currentCSV].indexOf($(children[h]).html()) > -1){
							$(children[h]).css("background-color", "red");
						}else{
							$(children[h]).css("background-color", "white");
						}
					}
				});
				var tbody = $("#adddataSource").find("table[class='table csvSampleData']").find("tbody");
				tbody.empty();
				for(var i = 0; i < msg.finalMap.rows.length; i ++){
					var trObj = $("<tr><td class=\"\">"+(i+1)+"</td></tr>");
					tbody.append(trObj);
					for(var j in msg.finalMap.rows[i]){
						trObj.append('<td class="">'+msg.finalMap.rows[i][j]+'</td>');
					}
				}
				
				for(var i in csvSource.csvData){
					if(csvSource.csvData[i].id+"trusteye"+csvSource.csvData[i].fileName.split(".")[0] == key){
						var selectObj = $("#adddataSource").find("select[class='form-control']")
						selectObj.find("option[value='"+csvSource.csvData[i].fileEncoding+"']").attr("selected",true);
						// selectObj.find("option[value='"+csvSource.csvData[i].fileEncoding+"']").attr("disabled","disabled");
						$("#adddataSource").find(".symbolList").find("input[extend='"+csvSource.csvData[i].fileSeparator+"']").attr("checked", true);
						// $("#adddataSource").find(".symbolList").attr("disabled","disabled");
						$("#adddataSource").find(".headerRadio").find("input[extend='"+csvSource.csvData[i].titleFlag+"']").attr("checked", csvSource.csvData[i].titleFlag);
					}
				}
				
				
			}
		});
	},
	testDBSrc:function(action){
		var connectionName = $('#connectionName').val();
		var connectionDB = $('#connectionDB').val();
		var connectionServerName = $('#connectionServerName').val();
		var connectionDbName = $('#connectionDbName').val();
		var connectionPort = $('#connectionPort').val();
		var connectionDBUserName = $('#connectionDBUserName').val();
		var connectionDBPassword = $('#connectionDBPassword').val();
		var testData = {
			connectionName:connectionName,
			connectionDB:connectionDB,
			connectionServerName:connectionServerName,
			connectionDbName:connectionDbName,
			connectionPort:connectionPort,
			connectionDBUserName:connectionDBUserName,
			connectionDBPassword:connectionDBPassword 
		}
		$.ajax({
			type : "post",
			url : "/MagicData/Import.action",
			dataType : "json",
			data : testData,
			success:function(data){
				var testBtn = $("#adddatabasemyModal").find(".btn.bcBtn.testBtn");
				testBtn.unbind("click");
				if(action){
					testBtn.html("确定");
					testBtn.click(function(){
						var _this = this;
						DBSource.addDBSrc(testData);
					});
				}else{
					testBtn.html("修改");
					testBtn.click(function(){
						var _this = this;
						testData.id = DBSource.currentDBS;
						DBSource.modifyDBSrc(testData);
					});
				}
			},
			error:function(data){
			}
		});
	},
	testSQL:function(sqlArray){
		for(var s in sqlArray){
			var columnString = sqlArray[s].removeLastComma();
			var sql = "select " + columnString + " from " + s;
			$.ajax({
				type : "post",
				url:"/MagicData/dataManage/queryTopFiveRecordsBySQL.action",
				dataType:"json",
				async: false,
				data:{"sql":sql,"column":columnString},
				success:function(data){
					if(data.success == "false"){
						return false;
					}
				},
				error:function(data){
				}
			});
		}
		return true;
	},
	modifyCSVSrc:function(identity){
		var fileEncoding = $("#addCsv").find("select").find("option:selected").val();
    	var fileSeparator = $("#addCsv").find(".symbolList").find('input:radio:checked').attr("extend");
		if(fileSeparator == "other"){
			fileSeparator = $("#addCsv").find(".symbolList .form-control").val();
		}
    	if(fileSeparator==''||fileSeparator==null||typeof(fileSeparator)=="undefined"){
    		hintManager.showHint("文件分隔符不能为空，请输入！");
    		return ;
    	}
		var titleFlag = $("#addCsv").find(".headerRadio").find('input:radio:checked').attr("extend");
		$.ajax({
	        type:"post",
	        url:"/MagicData/csvDataManage/updateCsvData.action",
	        dataType:"json",
	        data:{"id":identity,"fileEncoding":fileEncoding,"titleFlag":titleFlag,"fileSeparator":fileSeparator},
	        success:function(data){
	        	csvSource.showCSVsrc();
				$("#addCsv").modal('hide');
				$("#adddataSource").modal({backdrop: 'static', keyboard: false});
	         }
    	});
	},
	addCSVSrc:function(){
    	var fileEncoding = $("#addCsv").find("select").find("option:selected").val();
    	var fileSeparator = $("#addCsv").find(".symbolList").find('input:radio:checked').attr("extend");
		if(fileSeparator == "other"){
			fileSeparator = $("#addCsv").find(".symbolList .form-control").val();
		}
    	if(fileSeparator==''||fileSeparator==null||typeof(fileSeparator)=="undefined"){
    		hintManager.showHint("文件分隔符不能为空，请输入！");
    		return ;
    	}
		var titleFlag = $("#addCsv").find(".headerRadio").find('input:radio:checked').attr("extend");
    	var csv = $("#csv").val();
    	if(csv==''||csv==null||typeof(csv)=="undefined"){
    		hintManager.showHint("上传文件不能为空，请选择文件！");
    	}else{
    		if(!csv.endWith(".csv")){
    			hintManager.showHint("该文件不是csv文件，请重新选择！");
    		}else{
    			$.ajaxFileUpload({
			         type:"post",
			         url:"/MagicData/csvDataManage/csvUpload.action",
			         dataType:"JSON",
			         data:{"fileEncoding":fileEncoding,"fileSeparator":fileSeparator,"titleFlag":titleFlag},
			         fileElementId : "csv", 
			         success:function(data){
			        	 data = JSON.parse(data);
			        	 var result = data.result;
			        	 if(result.success==true){
			        		 hintManager.showInfoHint("csv文件上传成功！");
							 csvSource.showCSVsrc();
			        		 $("#addCsv").modal('hide');
							 $("#adddataSource").modal({backdrop: 'static', keyboard: false});
			        	 }else{
			        		 hintManager.showHint(result.message);
			        	 }
			         },
			         error:function(){
			         	hintManager.showHint("csv上传异常，请联系管理员！");
			         }
			     });
    		}
    	}
    },
	deleteCSVSrc:function(identity){
		confirm("确认要删除csv文件吗？",function(){
    		$.ajax({
	   	        type:"post",
	   	        url:"/MagicData/csvDataManage/deleteCsvData.action?id="+identity,
	   	        dataType:"json",
	   	        success:function(data){
		        	if(data.success==true){
		        		hintManager.showInfoHint("csv文件删除成功！");
		        		csvSource.showCSVsrc();
		        	}else{
		        		hintManager.showHint(result.message);
		        	}
	   	         },
		         error:function(){
		        	hintManager.showHint("删除csv文件异常，请稍后重试！");
		         }
   	     	});
    	});
	},
	parseSQL:function(sql){
		//TODO 需要的判断select from等字段是否重复
		// select id, x, a from test;
		// select a.id, a.x, a.a from test a;
		// select a.id, a.x, b.a from test a, test2 b;
		/* {
			test : id, x, a
		}
		{
			test : id, x, a
		}
		{
			test : id, x
			test2 : a
		} */
		sql = sql.split(" where ")[0].trim().replace(/;/g, "");
		var json = {};
		var allColumn = sql.split(" from ")[0].trim().replace(/select/g, "").split(",");
		var columnPropertyList = "";
		for(var c = 0; c < allColumn.length; c ++){
			var column = allColumn[c].trim();
			if(column.indexOf(".") > -1){
				var tc = column.split(".");
				if(!json[tc[0]])
					json[tc[0]] = "";
				json[tc[0]] += (tc[1]+",");
			}else{
				columnPropertyList += (column + ",");
			}
		}
		var result = {};
		var flag = 0;
		var tableArray = sql.split(" from ")[1].trim().split(",");
		for(var t = 0; t < tableArray.length; t ++){
			var table = tableArray[t].trim();
			if(jQuery.isEmptyObject(json)){
				result[table] = columnPropertyList;
			}else{
				for(var j in json){
					var table2 = table.split(" ")[0];
					var table3 = table.split(" ")[1];
					if(j == table3){
						result[table2] = json[j].removeLastComma();
					}
				}
			}
		}
		return result;
	},
	displayCSVPro:function(args){
		var disPro = $("#addCsv").find(".modal-content.addCsv-content");
		disPro.empty();
		disPro.append(
			'<div class="modal-header">' +
				'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
				'<h4 class="modal-title" id="">添加数据源</h4>' +
			'</div>' +
			'<div class="modal-body addCsv-body">' +
				'<input type="hidden" id="csvHiddenId"  />' +
				'<p class="importP clearfix">' +
					'<span class="importSpan comSpan pull-left" id="csvFileSpan">请先导入文件：</span>' +
					'<a href="javascript:void(0);" class="a-upload" id="uploadcsva" >选择文件' +
						'<input type="file" value="" name="csv" id="csv" >' +
					'</a>' +
					'<input type="text" disabled="disabled" class="showFileName" value="" id="csvFileName">' +
				'</p>' +
				'<div class="utfDiv clearfix">' +
					'<span>选择文件编码：</span>' +
					'<select name="" class="form-control">' +
						'<option value="UTF-8">UTF-8</option>' +
						'<option value="ANSI">ANSI</option>' +
						'<option value="GBK">GBK</option>' +
						'<option value="GB2312">GB2312</option>' +
						'<option value="GB18030">GB18030</option>' +
					'</select>' +
				'</div>' +
				'<div class="fieldSymbol">' +
					'<p>选择字段的分隔符号：</p>' +
					'<div class="symbolList">' +
						'<label class="checkbox-inline">' +
							'<input type="radio" name="optionsRadiosinline1" id="" value="" extend=",">' +
						'逗号</label>' +
						'<label class="checkbox-inline">' +
							'<input type="radio" name="optionsRadiosinline1" id="" value="" extend=";">' +
						'分号</label>' +
						'<label class="checkbox-inline">' +
							'<input type="radio" name="optionsRadiosinline1" id="" value="" extend="tab">' +
						'Tab</label>' +
						'<label class="checkbox-inline">' +
							'<input type="radio" name="optionsRadiosinline1" id="" value="" extend="space">' +
						'空格</label>' +
						'<label class="checkbox-inline">' +
							'<input type="radio" name="optionsRadiosinline1" id="" value="" extend="other">' +
						'其他</label>' +
						'<input type="text" class="form-control" id="" value="" />' +
					'</div>' +
				'</div>' +
				'<div class="headerRadio">' +
					'<span class="comSpan">是否有表头：</span>' +
					'<div class="headerradioList">' +				
						'<label class="checkbox-inline">' +
							'<input type="radio" name="titleFlag1" value="" extend="true">是' +
						'</label>' +
						'<label class="checkbox-inline">' +
							'<input type="radio" name="titleFlag1" value="" extend="false">否' +
						'</label>' +
					'</div>' +
				'</div>' +
			'</div>' +
			'<div class="modal-footer">' +
				'<button type="button" class="btn bcBtn">添加</button>' +
				'<button type="button" class="btn qxBtn">返回</button>' +
			'</div>'
		);
		$("button[class='close']").remove();
		if(arguments.length > 0){
			$("#csvFileSpan").html("文件名称：");
			$("#uploadcsva").hide();
			$("#csvHiddenId").val(args.id);
			$("#csvFileName").val(args.fileName);
			disPro.find("option[value='"+args.fileEncoding+"']").attr("selected",true);
			disPro.find("input[extend='"+args.fileSeparator+"']").attr("checked", "checked");
			disPro.find(".headerRadio").find("input[extend='"+args.titleFlag+"']").attr("checked", true);
		}
		var qxBtn = disPro.find("button[class='btn qxBtn']");
		qxBtn.unbind("click");
		qxBtn.click(function(){
			$("#adddataSource").modal({backdrop: 'static', keyboard: false});
			$('#addCsv').modal("hide");
		});
		$('#addCsv').modal({backdrop: 'static', keyboard: false});
		return true;
	},
	ImportText:function(obj, dataJSON){
		var databasename = "";
		if(undefined == dataJSON.connectionDB || "" == dataJSON.connectionDB){
			databasename = $(obj).children('option:selected').val();
		}else{
			databasename = dataJSON.connectionDB;
		}
		$("#adddatabasemyModal").find(".addDBSourceParam").remove();
		var disPro = $("#adddatabasemyModal").find(".modal-content.adddatabasemodal-content");
		disPro.find(".topdatabaseCon").find("ul").append('<li>' +
									'<input type="text" class="addDBSourceParam" value="" id="connectionServerName" placeholder="192.168.1.1"/>' +
								'</li>');
		disPro.find(".topdatabaseCon").find("ul").append('<li>' +
									'<input type="text" class="addDBSourceParam" value="" id="connectionDbName" placeholder="magicdata"/>' +
								'</li>');
		if(databasename == "MYSQL"||databasename == "DERBY"||databasename == "ORACLE"||databasename == "HadoopHive"||databasename == "H2"||databasename == "DB2"||databasename == "MSSQL"||databasename == "SYBASE"||databasename == "POSTGRESQL"||databasename == "GREENPLUM"||databasename == "FIREBIRD"){
			var port = dataJSON.connectionPort || "";
			if(databasename == "MYSQL"){port = 3306}else if(databasename == "ORACLE"){port = 1521}else if(databasename == "HadoopHive"){port = 9000}else if(databasename == "MSSQL"){port = 1433}else if(databasename == "H2"){port = 8082}else if(databasename == "DB2"){port = 50000}else if(databasename == "SYBASE"){port = 5001}else if(databasename == "POSTGRESQL"||databasename == "GREENPLUM"){port = 5432}else if(databasename == "FIREBIRD"){port = 3050}else if(databasename == "DERBY"){port = 1527}
			disPro.find(".topdatabaseCon").find("ul").append('<li>' +
										'<input type="text" class="addDBSourceParam" value="'+port+'" id="connectionPort" placeholder="3306"/>' +
									'</li>');
			disPro.find(".topdatabaseCon").find("ul").append('<li>' +
										'<input type="text" class="addDBSourceParam" value="" id="connectionDBUserName" placeholder="admin"/>' +
									'</li>');
			disPro.find(".topdatabaseCon").find("ul").append('<li>' +
										'<input type="password" class="addDBSourceParam" value="" id="connectionDBPassword"/>' +
									'</li>');
		}else if(databasename == "AS/400"){//no Port
			disPro.find(".topdatabaseCon").find("ul").append('<li>' +
										'<input type="text" class="addDBSourceParam" value="" id="connectionDBUserName" placeholder="admin"/>' +
									'</li>');
			disPro.find(".topdatabaseCon").find("ul").append('<li>' +
										'<input type="password" class="addDBSourceParam" value="" id="connectionDBPassword"/>' +
									'</li>');
		}else if(databasename == "SQLITE"){// no 数据库名称 port & username & passport
		}
		disPro.find("#connectionServerName").val(dataJSON.connectionServerName || "");
		disPro.find("#connectionDbName").val(dataJSON.connectionDbName || "");
		disPro.find("#connectionDBUserName").val(dataJSON.connectionDBUserName || "");
		disPro.find("#connectionDBPassword").val(dataJSON.connectionDBPasswordBak || "");
	},
	getCurrentCSVSource:function(){
		var tbody = $("#adddataSource").find("table[class='table csvlist']").find("tbody");
		var csvID = tbody.find("tr[click='true']").attr("extend");
		return csvID;
	}
}

mapping = {
	mapData:"",
	save:function(){
		svgAsPngUri(document.getElementById("displaySVG"), null, function(uri) {
			var nodes = [];
			for(var i = 0; i < graph.nodes.length; i ++){
				var json = {};
				for(var j in graph.nodes[i]){
					if(j != "custormVertexPropertyList"){
						json[j] = graph.nodes[i][j];
					}else{
						var List = graph.nodes[i]["custormVertexPropertyList"];
						var newList = new Array();
						for(var l = 0; l < List.length; l ++){
							var newJSON = {};
							for(var n in List[l]){
								newJSON[n] = List[l][n];
								newJSON["databaseColumnId"] = -1;
							}
							newList.push(newJSON);
						}
						json["custormVertexPropertyList"] = newList;
					}
				}
				nodes.push(json);
			}
			var edges = [];
			for(var i = 0; i < graph.edges.length; i ++){
				var json = {};
				for(var j in graph.edges[i]){
					if(j != "custormPropertyList"){
						json[j] = graph.edges[i][j];
					}else{
						var List = graph.edges[i]["custormPropertyList"];
						var newList = new Array();
						for(var l = 0; l < List.length; l ++){
							var newJSON = {};
							for(var n in List[l]){
								newJSON[n] = List[l][n];
								newJSON["databaseColumnId"] = -1;
							}
							newList.push(newJSON);
						}
						json["custormPropertyList"] = newList;
					}
				}
				json.source = graph.findNodeIndex(graph.edges[i].source.uuid);
				json.target = graph.findNodeIndex(graph.edges[i].target.uuid);
				edges.push(json);
			}
			$.ajax({
				type : "post",
				url : pageVariate.base + "/dataManage/saveImportMapping.action",
				data : {
					"name" : $("#savetemplate").find("input").val(),
					"desc" : $("#savetemplate").find("textarea").val(),
					"nodes" : JSON.stringify(nodes),
					"edges" : JSON.stringify(edges),
					"image" : uri
				},
				success : function(data){
					console.log(data);
				}
			});
		});
	},
	load:function(){
		$.ajax({
			type:"post",
			url:pageVariate.base + "/dataManage/getImportMappingList.action",
			success:function(msg){
				mapping.mapData = msg;
				$('#choosetemplate').modal({backdrop: 'static', keyboard: false});
				var ul = $("#choosetemplate").find("ul");
				ul.empty();
				for(var i = 0; i < msg.list.length; i ++){
					var li = $('<li extend=' + msg.list[i].id + '>' +
									'<h3>' + msg.list[i].name + '</h3>' +
									'<div class="callImg"><img style="height: 100px;" src="' + msg.list[i].image + '"/></div>' +
									'<p>' + msg.list[i].desc + '</p>' +
								'</li>');
					
					disPage.xxxx(li, {
						nStyle:{
							"background-color":"#bcdeff"
						},
						dStyle:{
							"background-color":"white"
						},
						clickEvent:function(){},
						args:{}
					},
					{
						nStyle:{
							"background-color":"#bcdeff"
						},
						dStyle:{
							"background-color":"white"
						},
						clickEvent:function(args){
							for(var i = 0;i < mapping.mapData.length; i ++){
								if(mapping.mapData[i].id == $(args.obj).attr("extend")){
								$("body").append('<div class="informationDiv" style="position:absolute;z-index: 10000;">' +
												'<p class="clearfix"><em>创建人：</em><span class="">张三</span></p>' +
												'<p class="clearfix"><em>创建时间：</em><span>2016-04-16</span></p>' +
												'<p class="clearfix"><em>详情描述：</em><span>记录联系人之间的通话网络事件，以及一些关键词的搜集。</span></p>' +
											'</div>');
								}
							}
						},
						args:{}
					},
					{
						nStyle:{
							"background-color":"#bcdeff"
						},
						dStyle:{
							"background-color":"white"
						},
						clickEvent:function(args){
							$("body").find(".informationDiv").remove();
						},
						args:{}
					});
					ul.append(li);
				}
				var bcBtn = $("#choosetemplate").find("button[class='btn bcBtn']");
				bcBtn.unbind("click");
				bcBtn.click(function(){
					var id = mapping.getCurrentNode();
					if(!id){
						alert("请选择mapping!");
						return false;
					}
					graph.emptyNodes();
					for(var i = 0; i < mapping.mapData.list.length; i ++){
						if(mapping.mapData.list[i].id == id){
							var nodes = JSON.parse(mapping.mapData.list[i].nodes);
							var edges = JSON.parse(mapping.mapData.list[i].edges);
							GraphJSON.database.tableList[0].vertexList = nodes;
							GraphJSON.database.tableList[0].edgeList = edges;
							graph.expandNode(nodes, edges);
						}
					}
					$('#choosetemplate').modal("hide");
				});
			}
		});
	},
	getCurrentNode:function(){
		return $("#choosetemplate").find("li[click='true']").attr("extend");
	}
}

disPage = {
	// xxxx:function(obj, nStyle, dStyle, clickEvent, args){
	xxxx:function(obj, clickArg, hoverArg, nohoverArg){
		obj.click(function(){
			var _this = this;
			$(_this).parent().children().css(clickArg.dStyle);
			$(_this).parent().children().attr("click", "false");
			$(_this).css(clickArg.nStyle);
			$(_this).attr("click", "true");
			var identity = $(_this).attr("extend");
			clickArg.args.identity = identity;
			clickArg.args.obj = _this;
			clickArg.clickEvent(clickArg.args);
		});
		obj.hover(function(e){
			var _this = this;
			$(_this).parent().children().css(hoverArg.dStyle);
			$(_this).css(hoverArg.nStyle);
			$(_this).parent().find("[click='true']").css(hoverArg.nStyle);
			if(!hoverArg.args){
				hoverArg.args = {};
			}
			hoverArg.args.obj = _this;
			if(hoverArg.clickEvent){
				hoverArg.clickEvent(hoverArg.args);
			}
		},function(){
			var _this = this;
			$(_this).parent().children().css(nohoverArg.dStyle);
			$(_this).parent().find("[click='true']").css(nohoverArg.nStyle);
			if(!nohoverArg.args){
				nohoverArg.args = {};
			}
			nohoverArg.args.obj = _this;
			if(nohoverArg.clickEvent){
				nohoverArg.clickEvent(nohoverArg.args);
			}
		});
	},
	addTitle:function(data){
		var _this = this;
		var div = $('<div class="imgDiv" extend="'+data.table+':'+data.columns+'"></div>');
		_this.xxxx(div, {
			nStyle:{
				"border": "2px solid #70c1ff"
			},
			dStyle:{
				"border": "0px solid #70c1ff"
			},
			clickEvent:function(args){
				disPage.showDBSrcSamples(args);
			},
			args:{}
		},{
			nStyle:{
				"border": "2px solid #70c1ff"
			},
			dStyle:{
				"border": "0px solid #70c1ff"
			}
		},{
			nStyle:{
				"border": "2px solid #70c1ff"
			},
			dStyle:{
				"border": "0px solid #70c1ff"
			}
		});
		
		$(".imgScroll").append(div);
		var tableName = data.table;
		if(data.table.indexOf("trusteye")>-1){
			tableName = data.table.split("trusteye")[1]+".csv";
		}
		var a = $('<a href="javascript:void(0)">' +
					'<span class="imgspan pull-left"><img src="'+data.imgSrc+'"/></span>' +
					'<div class="divcontent pull-left">' +
						'<p>'+tableName+'</p>' +
						'<p>Number of rows : '+data.total+'</p>' +
					'</div>' +
				 '</a>');
		div.append(a);
		var hover = $('<div class="hoverdiv">' +
						'<span class="bjspan">编辑</span>' +
						'<span class="delspan">删除</span>' +
					'</div>');
		div.append(hover);
		
	},
	showDBSrcSamples:function(args){
		var sampleHeadTr = $(".tableDiv .table thead tr");
		var sampleTbody = $(".tableDiv .table tbody");
		sampleHeadTr.empty();
		sampleTbody.empty();
		if(GraphJSON.database["sourceType"] == 0){
			var argsArr = args.identity.split(":");
			var colArr = argsArr[1].split(",");
			for(var i = 0; i < colArr.length; i ++){
				if(colArr[i] != ""){
					sampleHeadTr.append('<th>'+colArr[i]+'</th>');
				}
			}
			var columnString = argsArr[1].removeLastComma();
			var sql = "select " + columnString + " from " + argsArr[0];
			$.ajax({
				type : "post",
				url:"/MagicData/dataManage/queryTopFiveRecordsBySQL.action",
				dataType:"json",
				data:{"sql":sql,"column":columnString},
				success:function(data){
					if(data.success == "false"){
						console.log(data.message);
					}else if(data.success == "true"){
						for(var i = 0; i < data.finalMap.rows.length; i ++){
							var sampleTr = $('<tr class="detail">');
							disPage.xxxx(sampleTr, {
								nStyle:{
									"background-color":"#bcdeff"
								},
								dStyle:{
									"background-color":"#6eb6e4"
								},
								clickEvent:function(){},
								args:{}
							},{
								nStyle:{
									"background-color":"#bcdeff"
								},
								dStyle:{
									"background-color":"#6eb6e4"
								}
							},{
								nStyle:{
									"background-color":"#bcdeff"
								},
								dStyle:{
									"background-color":"#6eb6e4"
								}
							});
							sampleTbody.append(sampleTr);
							var row = data.finalMap.rows[i];
							for(var j in row){
								sampleTr.append('<td class="col-md-1">'+row[j]+'</td>');
							}
						}
						$(".tableDiv .table tbody").find("tr:eq(0)").attr("click", "true");
						$(".tableDiv .table tbody").find("tr:eq(0)").css("background", "#0081d7");
					}
				},
				error:function(data){
				}
			});
		}else if(GraphJSON.database["sourceType"] == 1){
			var key = args.identity;
			$.ajax({
				type : "post",
				url : "/MagicData/csvDataManage/findCsvSampleData.action",
				data : {id : args.identity.split("trusteye")[0]},
				success : function(data){
					var titles = csvSource.selCSV[key.split(":")[0]]
					for(var i in data.finalMap.rows[0]){
						if(titles.indexOf(i) > -1){
							sampleHeadTr.append('<th>'+i+'</th>');
						}
					}
					for(var i = 0; i < data.finalMap.rows.length; i ++){
						var sampleTr = $('<tr class="detail">');
						disPage.xxxx(sampleTr, {
							nStyle:{
								"background-color":"#bcdeff"
							},
							dStyle:{
								"background-color":"#6eb6e4"
							},
							clickEvent:function(){},
							args:{}
						},{
							nStyle:{
								"background-color":"#bcdeff"
							},
							dStyle:{
								"background-color":"#6eb6e4"
							}
						},{
							nStyle:{
								"background-color":"#bcdeff"
							},
							dStyle:{
								"background-color":"#6eb6e4"
							}
						});
						sampleTbody.append(sampleTr);
						var row = data.finalMap.rows[i];
						for(var j in row){
							if(titles.indexOf(j) > -1){
								sampleTr.append('<td class="col-md-1">'+row[j]+'</td>');
							}
						}
					}
					$(".tableDiv .table tbody").find("tr:eq(0)").attr("click", "true");
					$(".tableDiv .table tbody").find("tr:eq(0)").css("background", "#0081d7");
				}
			});
		}
	},
	showMapping:function(node, type){
		var _this = this;
		if(jQuery.isEmptyObject(node)){
			return false;
		}
		$(".modelMapDBS").empty();
		var propertyList = node.custormVertexPropertyList || node.custormPropertyList || new Array();
		var columns = disPage.getColumns();
		var colArr = new Array();
		if(columns){
			var argsArr = columns.split(":");
			colArr = argsArr[1].split(",");
		}
		for(var i = 0; i < propertyList.length; i ++){
			var div = $('<div class="form-group">' +
							'<label class="labelTit">'+propertyList[i].propertyName+'</label>' +
						'</div>');
			if(propertyList[i].isStrongProperty == 1){
				div.css("border", "solid 1px red");
			}
			var selectObj = $('<select class="form-control" name=""><option class="" type="">请选择字段</option></select>')
			selectObj.attr("type", type)
			for(var col = 0; col < colArr.length; col ++){
				if(propertyList[i].databaseColumnValue == colArr[col]){
					selectObj.append('<option selected=true value="'+colArr[col]+'">'+colArr[col]+'</option>');
				}else{
					selectObj.append('<option value="'+colArr[col]+'">'+colArr[col]+'</option>');
				}
			}
			selectObj.change(function(){
				var _this = this;
				var property = $(this).prev().html();
				var columnPropertyList = GraphJSON.database.tableList[0].columnPropertyList;
				var currentVal = $(_this).children('option:selected').val();
				var type = selectObj.attr("type");
				if(type == "nodes"){
					var vertexList = GraphJSON.database.tableList[0].vertexList;
					for(var i = 0; i < vertexList.length; i ++){
						var vertex = vertexList[i];
						if(pageVariate.currentNode.uuid == vertex.uuid){
							for(var j = 0; j < vertex.custormVertexPropertyList.length; j ++){
								if(vertex.custormVertexPropertyList[j].propertyName == property){
									for(var column = 0; column < columnPropertyList.length; column ++){
										if(columnPropertyList[column].columnName == currentVal && columnPropertyList[column].tableId == $("#GUN").find(".imgDiv[click='true']").index()){
											vertex.custormVertexPropertyList[j].databaseColumnId = columnPropertyList[column].columnId;
											vertex.custormVertexPropertyList[j].databaseColumnValue = columnPropertyList[column].columnName;
										}
									}
								}
							}
						}
					}
				}else if(type == "edges"){
					var edgeList = GraphJSON.database.tableList[0].edgeList;
					for(var edgeItem = 0; edgeItem < edgeList.length; edgeItem ++){
						var edge = edgeList[edgeItem];
						if(pageVariate.currentNode.uuid == edge.uuid){
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
				}
			});
			div.append(selectObj);
			$(".modelMapDBS").append(div);
		}
	},
	whereRelation:function(args){
		var tableList = $(".mainCon").find(".whereRelation").find(".tableName");
		tableList.empty();
		tableList.append('<option value="" extend="">请选择表</option>');
		for(var i in args){
			var tableName = "";
			if(i.indexOf("trusteye")>-1){
				tableName = i.split("trusteye")[1]+".csv";
			}else{
				tableName = i;
			}
			var extend = "";
			if(typeof(args[i]) == "string"){
				extend = args[i].replace(/"/g, "");
			}else if(typeof(args[i]) == "object"){
				extend = args[i].join().replace(/"/g, "");
			}
			tableList.append('<option value="'+i+'" extend="'+extend+'">'+tableName+'</option>');
		}
		tableList.change(function(){
			var _this = this;
			var currentVal = $(_this).children('option:selected').val();
			var currentCols = $(_this).children('option:selected').attr("extend");
			var columnList = $(".mainCon").find(".whereRelation").find(".columnName");
			$(_this).next().empty();
			$(_this).next().append('<option value="" extend="">请选择字段</option>');
			var cols = currentCols.split(",");
			for(var j = 0; j < cols.length; j ++){
				$(_this).next().append('<option value="'+cols[j]+'">'+cols[j]+'</option>');
			}
		});
		var lastColumnList = $(".mainCon").find(".whereRelation").find(".columnName").last();
		lastColumnList.change(function(){
			var _this = this;
			var cl1 = $(_this).children('option:selected').val();
			var cl2 = $(_this).prev().children('option:selected').val();
			var cl3 = $(_this).prev().prev().val();
			var cl4 = $(_this).prev().prev().prev().children('option:selected').val();
			var cl5 = $(_this).prev().prev().prev().prev().children('option:selected').val();
			GraphJSON.database.tableList[0].sql += (" where "+cl5.split("trusteye")[1]+"."+cl4+cl3+cl2.split("trusteye")[1]+"."+cl1);
			GraphJSON.database.tableList[0].errorSql += (" where "+cl5+"."+cl4+cl3+cl2+"."+cl1);
		});
	},
	previewData:function(){
		var curCliObj = $(".tableDiv .table tbody").find(".detail[click='true']");
		var preProBody = $(".maincon-third").find("table[class='table prevew property'] tbody");
		preProBody.empty();
		$("#previewNodeName").html(pageVariate.currentNode.name);
		$("#previewNodeType").html(pageVariate.currentNode.type);
		var vertexList = GraphJSON.database.tableList[0].vertexList;
		for(var v = 0; v < vertexList.length; v ++){
			if(vertexList[v].uuid == pageVariate.currentNode.uuid){
				var custormVertexPropertyList = vertexList[v].custormVertexPropertyList;
				for(var p = 0; p < custormVertexPropertyList.length; p ++){
					var sampleDataID = custormVertexPropertyList[p].databaseColumnId;
					var sampleData = "";
					if(sampleDataID <= -1){
						sampleData = "该属性没有做对应";
					}else{
						sampleData = curCliObj.find("td:eq("+(sampleDataID)+")").html();
						if(!sampleData){
							sampleData = "没有样例数据";
						}
					}
					preProBody.append('<tr>' +
										'<td class="col-md-6">'+custormVertexPropertyList[p].propertyName+'</td>' +
										'<td class="col-md-6">'+sampleData+'</td>' +
									'</tr>');
				}
			}
		}
		var preRelatBody = $(".maincon-third").find("table[class='table prevew relation'] tbody");
		preRelatBody.empty();
		var relatNodes = graph.findRelatedNodes(pageVariate.currentNode.uuid);
		for(var n in relatNodes){
			for(var r = 0; r < relatNodes[n].length; r ++){
				if(r == 0){
					preRelatBody.append('<tr>' +
										'<td class="col-md-6">'+n+'</td>' +
										'<td class="col-md-6">'+relatNodes[n][r].name+'</td>' +
									'</tr>');
				}else{
					preRelatBody.append('<tr>' +
										'<td class="col-md-6"></td>' +
										'<td class="col-md-6">'+relatNodes[n][r].name+'</td>' +
									'</tr>');
				}
			}
		}
	},
	checkGraphJSON:function(){
		var vertexList = GraphJSON.database.tableList[0].vertexList;
		for(var i = 0; i < vertexList.length; i ++){
			var custormVertexPropertyList = vertexList[i].custormVertexPropertyList;
			for(var j = 0; j < custormVertexPropertyList.length; j ++){
				if(custormVertexPropertyList[j].isStrongProperty == 1 && custormVertexPropertyList[j].databaseColumnId == -1){
					return false;
				}
			}
		}
		return true;
	},
	saveTask:function(){
		var taskName = $("#taskName").val();
		if(!taskName){
			alert("任务名称");
			return false;
		}
		var taskDesc = $("#taskDesc").val();
		$.ajax({
			type : "post",
			url : "/MagicData/ajax_dataImport/saveTask.action",
			data : {
				taskName : taskName,
				taskType : 1,
				jsonStr : JSON.stringify(GraphJSON),
				taskDesc : taskDesc
			},
			success : function(taskID){
				$('#savemyModal').modal("hide");
				// hintManager.showSuccessHint("保存任务成功!");
			}
		});
	},
	editTask:function(taskID){
		$.ajax({
			type : "post",
			url : pageVariate.base+"/ajax_dataImport/editTask.action",
			data : {
				taskId : taskID
			},
			success : function(taskValue){
				GraphJSON = JSON.parse(taskValue.xmlPath);
				console.log(GraphJSON);
				if(GraphJSON.database.sourceType == 0){
					$.ajax({
						type:"post",
						url:"/MagicData/dataManage/findAllTables.action",
						data:{"dbConnId":GraphJSON.database.id},
						success:function(){
							var sqlArray = DBSource.parseSQL(GraphJSON.database.tableList[0].sql);
							var index = 0;
							var colIndex = 0;
							for(var s in sqlArray){
								disPage.addTitle({
									imgSrc : pageVariate.base+"images/import/topImg1.png",
									total : 48,
									table : s,
									columns : sqlArray[s]
								});
							}
							disPage.whereRelation(sqlArray);
							disPage.showMapping({});
							var vertexList = new Array();
							for(var v = 0; v < GraphJSON.database.tableList[0].vertexList.length; v ++){
								var json = {};
								for(var i in GraphJSON.database.tableList[0].vertexList[v]){
									json[i] = GraphJSON.database.tableList[0].vertexList[v][i];
								}
								vertexList.push(json);
							}
							var edgeList = new Array();
							for(var e = 0; e < GraphJSON.database.tableList[0].edgeList.length; e ++){
								var json = {};
								for(var i in GraphJSON.database.tableList[0].edgeList[e]){
									json[i] = GraphJSON.database.tableList[0].edgeList[e][i];
								}
								edgeList.push(json);
							}
							graph.expandNode(vertexList, edgeList);
						}
					});
				}else if(GraphJSON.database.sourceType == 1){
					var sqlArray = DBSource.parseSQL(GraphJSON.database.tableList[0].errorSql);
					var index = 0;
					var colIndex = 0;
					for(var s in sqlArray){
						csvSource.selCSV[s] = new Array();
						var newArray = sqlArray[s].split(",");
						for(var i = 0;i < newArray.length; i ++){
							csvSource.selCSV[s].push(newArray[i]);
						}
						disPage.addTitle({
							imgSrc : pageVariate.base+"images/import/topImg1.png",
							total : 48,
							table : s,
							columns : sqlArray[s]
						});
					}
					disPage.whereRelation(sqlArray);
					disPage.showMapping({});
					var vertexList = new Array();
					for(var v = 0; v < GraphJSON.database.tableList[0].vertexList.length; v ++){
						var json = {};
						for(var i in GraphJSON.database.tableList[0].vertexList[v]){
							json[i] = GraphJSON.database.tableList[0].vertexList[v][i];
						}
						vertexList.push(json);
					}
					var edgeList = new Array();
					for(var e = 0; e < GraphJSON.database.tableList[0].edgeList.length; e ++){
						var json = {};
						for(var i in GraphJSON.database.tableList[0].edgeList[e]){
							json[i] = GraphJSON.database.tableList[0].edgeList[e][i];
						}
						edgeList.push(json);
					}
					graph.expandNode(vertexList, edgeList);
				}
			}
		});
	},
	showDialog:function(title, func){
		var container = $("#showDialog").find(".modal-content.deldataSource-content");
		container.empty();
		container.append('<div class="modal-header">' +
							'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
							'<h4 class="modal-title" id="">提示</h4>' +
						'</div>' +
						'<div class="modal-body commondel-body clearfix">' +
							'<p><em><img src="'+pageVariate.base+'images/import/question.png"/></em>'+title+'</p>' +
						'</div>' +
						'<div class="modal-footer deldata-footer">' +
							'<button type="button" class="btn bcBtn">确定</button>' +
							'<button type="button" class="btn qxBtn" data-dismiss="modal">取消</button>' +
						'</div>');
		container.find("button[class='btn bcBtn']").unbind("click");
		container.find("button[class='btn bcBtn']").click(function(){
			if(func){
				func();
			}
			$("#showDialog").modal("hide");
		});
		$("#showDialog").modal({backdrop: 'static', keyboard: false});
	},
	getColumns:function(){
		return $(".imgScroll").find(".imgDiv[click='true']").attr("extend");
	}
}

$(function(){
	$("button[class='close']").remove();
	Array.prototype.indexOf = function(val) {
		for (var i = 0; i < this.length; i++) {
			if (this[i] == val) return i;
		}
		return -1;
	};
	Array.prototype.remove = function(val) {
		var index = this.indexOf(val);
		if (index > -1) {
			this.splice(index, 1);
		}
	};
	/*
	 * 自定义字符串操作
	 */
	String.prototype.endWith=function(str){
		if(str==null||str==""||this.length==0||str.length>this.length)
		  return false;
		if(this.substring(this.length-str.length)==str)
		  return true;
		else
		  return false;
		return true;
	}
	String.prototype.startWith=function(str){
		if(str==null||str==""||this.length==0||str.length>this.length)
		  return false;
		if(this.substr(0,str.length)==str)
		  return true;
		else
		  return false;
		return true;
	}

	String.prototype.trim=function(){
		if(!this) return "";
		return this.replace(/(^\s*)|(\s*$)/g, "");
	}
	if (!String.prototype.trim){
		String.prototype.trim = function(){
			return this.replace(/^\s+/, "").replace(/\s+$/,"");
		}
	}
	String.prototype.removeLastComma=function(str){
		var newStr = this.trim();
		if(newStr[newStr.length-1] == ","){
			return newStr.substr(0, newStr.length-1) + " ";
		}
		return newStr;
	}
	$(".btn.bcBtn.saveTask").click(function(){
		disPage.saveTask();
	});
	/*点击保存出现的弹出层*/
	$(".saveBtn").click(function(){
		if(graph.nodes.length>0){
			if(disPage.checkGraphJSON()){
				$('#savemyModal').modal({backdrop: 'static', keyboard: false});
			}else{
				disPage.showDialog("模型和数据源对应有问题");
			}
		}else{
			hintManager.showWarnHint("请确保拓扑图中有模型!");
		}
	});
	$(".btn.bcBtn.addProperty").click(function(){
		titanMan.addProperty("node");
	});
	/*点击最上面按钮出现-添加数据源-的弹出层*/
	$(".addSpan").click(function(){
		DBSource.showDBSrc();
	});
	$(".addnodeBtn").click(function(){
		titanMan.addProperty();
	});
	$(".addnodeattrBtn").click(function(){
		$(".topnodeattrCon").append('<ul class="list-unstyled clearfix">' +
		         						'<li>' +
		         							'<input type="text" class="attributeName" />' +
		         						'</li>' +
		         						'<li>' +
		         							'<select class="attributeType">' +
												'<option value="java.lang.String">字符串</option>' +
												'<option value="java.lang.Character">字符</option>' +
												'<option value="java.lang.Boolean">布尔</option>' +
												'<option value="java.lang.Byte">字节</option>' +
												'<option value="java.lang.Short">短</option>' +
												'<option value="java.lang.Integer">整形</option>' +
												'<option value="java.lang.Long">长整形</option>' +
												'<option value="java.lang.Float">浮点</option>' +
											'</select>' +
		         						'</li>' +
		         						'<li>' +
		         							'<select class="attributeCardinality">' +
		         								'<option value="1">单个值</option>' +
												'<option value="2">不可重复</option>' +
												'<option value="3">可以重复</option>' +
		         							'</select>' +
		         						'</li>' +
		         						'<li class="sq">' +
		         							'<input type="checkbox" name="" id="" class="attributeSQ"/>' +
		         						'</li>' +
		         						'<li class="sy">' +
		         							'<input type="checkbox" name="" id="" class="attributeSY"/>' +
		         						'</li>' +
		         						'<li class="add delnode1">' +
		         							'<img src="'+pageVariate.base+'images/import/nodeDelete.png"/>' +
		         						'</li>' +
		         					'</ul>');
	});
	/*添加数据源--添加数据库弹出层*/
  	$(".leftadddataBtn").click(function(){
		var action = DBSource.displayDBSPro();
		var testBtn = $("#adddatabasemyModal").find(".btn.bcBtn.testBtn");
		testBtn.unbind("click");
		testBtn.html("测试");
		testBtn.click(function(){
			var _this = this;
			DBSource.testDBSrc(action);
		});
  		$("#adddataSource").modal('hide');
	});
	/*csv--点击csv页面add按钮出现的弹出层*/
	$(".csvaddBtn").click(function(){
		var action = csvSource.displayCSVPro();
		$("#adddataSource").modal('hide');
		var bcBtn = $("#addCsv").find(".btn.bcBtn");
		bcBtn.unbind("click");
		bcBtn.click(function(){
			var _this = this;
			csvSource.addCSVSrc();
		});
	});
	$(".PreviewBtn").click(function(){
		if(graph.nodes.length>0){
			disPage.previewData();
		}else{
			hintManager.showWarnHint("请确保拓扑图中有模型!");
		}
	});
	/*4月27日选择模板弹出层*/
	$(".loadMap").click(function(){
		if($(".imgScroll").find(".imgDiv").length>0){
			mapping.load();
		}else{
			hintManager.showWarnHint("请在指定源处添加数据源!");
		}
	});
	/*4月27日选择模板弹出层*/
	$(".saveMap").click(function(){
		if(graph.nodes.length>0){
			$('#savetemplate').modal({backdrop: 'static', keyboard: false});
		}else{
			hintManager.showWarnHint("请确保拓扑图中有模型!");
		}
	});
	$("#savetemplate").find("button[class='btn bcBtn']").click(function(){
		mapping.save();
	});
	/*添加数据源里的tab切换*/
  	$(".datatabDiv ul li").click(function(){
  		$(this).addClass("licur").siblings("li").removeClass("licur");
  		var tabIndex = $(".datatabDiv ul li").index(this); 
		GraphJSON.database["sourceType"] = tabIndex;
  		$(".datatabCon").eq(tabIndex).show().siblings(".datatabCon").hide();
		if(tabIndex == 0){
			DBSource.showDBSrc();
		}else if(tabIndex == 1){
			csvSource.showCSVsrc();
			GraphJSON.database["connectionRootDirectory"] = "hdfs://192.168.40.11:8020/csvfiles/";
		}
	});
	/*添加数据源里的tab切换*/
  	$(".sqltabDiv ul li").click(function(){
  		$(this).addClass("sqllicur").siblings("li").removeClass("sqllicur");
  		var tabIndex = $(".sqltabDiv ul li").index(this); 
  		$(".sqltabCon").eq(tabIndex).show().siblings(".sqltabCon").hide();
		
		if(tabIndex == 0 && pageVariate.currentSQLAction != tabIndex){
			DBSource.showSQL();
			pageVariate.currentSQLAction = tabIndex;
		}else if(tabIndex == 1 && pageVariate.currentSQLAction != tabIndex){
			DBSource.showAllTables();
			pageVariate.currentSQLAction = tabIndex;
		}
	});
	$("#csv").live("change",function(){
    	var fileVal = $(this).val();
    	$("#csvFileName").val(fileVal);
    });
	document.onmousemove=function(e){
		e=e? e:window.event;
		$("body").find(".informationDiv").css({
			top:e.screenY,
			left:e.screenX
		});
	}
	graph.createSVG();
	graph.createTools();
	graph.initEvent();
	$(".mainCon.level").css("display", "none");
	// $("body").on("click", "input[type='checkbox']", function(){
		// var _this  = this;
		// var status = $(_this).attr("checked");
		// if(!status){
			// $(_this).attr("checked", true);
		// }else{
			// $(_this).attr("checked", false);
		// }
	// });
});

GraphJSON = {
	"vertices" : [],
	"edges" : [],
	"database" : {
		"sourceType": 0,
		"tableList": [
			{
				"id": 0,
				"sql" : "",
				"columnPropertyList" : [],
				"edgeList": [],
				"vertexList": [],
				"tablesNameList": []
			}
		]
	}
};

/*
GraphJSON = {
	"vertices": [],
	"edges": [],
	"database": {
		"tableList": [{
			"id": 0/1,
			"sql": "select  phonefrom, phoneto ,frompersoname ,topersonname , fromphonehome,tophonehome , phonedate  from phone_event",
			"columnPropertyList": [{
				"columnName": "phonefrom",
				"columnType": "string",
				"columnId": 0
			},
			{
				"columnName": "phoneto",
				"columnType": "string",
				"columnId": 1
			},
			{
				"columnName": "frompersoname",
				"columnType": "string",
				"columnId": 2
			},
			{
				"columnName": "topersonname",
				"columnType": "string",
				"columnId": 3
			},
			{
				"columnName": "fromphonehome",
				"columnType": "string",
				"columnId": 4
			},
			{
				"columnName": "tophonehome",
				"columnType": "string",
				"columnId": 5
			},
			{
				"columnName": "phonedate",
				"columnType": "string",
				"columnId": 6
			}],
			"edgeList": [{
				"id": 0,
				"uuid": "HER8nePgZdC5uS4p9wqXZSNAqMnnksfj",
				"custormPropertyList": [{
					"isStrongProperty": 0,
					"isBuildTitanIndex": 0,
					"databaseColumnId": -1,
					"propertyId": 0
				}],
				"lableName": "own",
				"inVertexId": 2,
				"outVertexId": 0,
				"source": 2,
				"target": 0,
				"direction": "out",
				"relation": "own"
			},
			{
				"id": 1,
				"uuid": "cavVmqQlTZB3cwVhLkdTpHLOsG8uLV0b",
				"custormPropertyList": [{
					"propertyName": "eventtime",
					"propertyType": "long",
					"isStrongProperty": 0,
					"isBuildTitanIndex": 1,
					"databaseColumnId": -1,
					"propertyId": 0,
					"propertyCardinality": "single"
				}],
				"lableName": "callfrom",
				"inVertexId": 2,
				"outVertexId": 1,
				"source": 2,
				"target": 1,
				"direction": "out",
				"relation": "callfrom"
			},
			{
				"id": 2,
				"uuid": "CGv2wnnvfxf7Yg6SfkJ1wRvcVA3Wp5mQ",
				"custormPropertyList": [{
					"isStrongProperty": 0,
					"isBuildTitanIndex": 0,
					"databaseColumnId": -1,
					"propertyId": 0
				}],
				"lableName": "own",
				"inVertexId": 4,
				"outVertexId": 3,
				"source": 4,
				"target": 3,
				"direction": "out",
				"relation": "own"
			},
			{
				"id": 3,
				"uuid": "r9rlW4AoD7EShG0oRmHAiYsq6XdssWJn",
				"custormPropertyList": [{
					"propertyName": "eventtime",
					"propertyType": "long",
					"isStrongProperty": 0,
					"isBuildTitanIndex": 1,
					"databaseColumnId": -1,
					"propertyId": 0,
					"propertyCardinality": "single"
				}],
				"lableName": "callto",
				"inVertexId": 1,
				"outVertexId": 4,
				"source": 1,
				"target": 4,
				"direction": "out",
				"relation": "callto"
			}],
			"vertexList": [{
				"vertexId": 0,
				"uuid": "b6Ie2Hzmk96TZDoH7imYoXPejYytOWkE",
				"custormVertexPropertyList": [{
					"propertyName": "model",
					"propertyType": "string",
					"isStrongProperty": 0,
					"isBuildTitanIndex": 0,
					"databaseColumnId": 4,
					"propertyId": 0,
					"propertyCardinality": "single",
					"databaseColumnValue": "fromphonehome"
				},
				{
					"propertyName": "phonenum",
					"propertyType": "string",
					"isStrongProperty": 1,
					"isBuildTitanIndex": 1,
					"databaseColumnId": 0,
					"propertyId": 1,
					"propertyCardinality": "single",
					"databaseColumnValue": "phonefrom"
				}],
				"vertexTypeName": "Phone",
				"image": "img/Phone.png",
				"name": "Phone1",
				"type": "Phone"
			},
			{
				"vertexId": 1,
				"uuid": "8mphIAcx9zdaBShQ8Ogz2FUfv2c05P8l",
				"custormVertexPropertyList": [{
					"propertyName": "from",
					"propertyType": "string",
					"isStrongProperty": 1,
					"isBuildTitanIndex": 0,
					"databaseColumnId": 0,
					"propertyId": 0,
					"propertyCardinality": "single",
					"databaseColumnValue": "phonefrom"
				},
				{
					"propertyName": "to",
					"propertyType": "string",
					"isStrongProperty": 1,
					"isBuildTitanIndex": 0,
					"databaseColumnId": 1,
					"propertyId": 1,
					"propertyCardinality": "single",
					"databaseColumnValue": "phoneto"
				},
				{
					"propertyName": "time",
					"propertyType": "long",
					"isStrongProperty": 0,
					"isBuildTitanIndex": 0,
					"databaseColumnId": -1,
					"propertyId": 2,
					"propertyCardinality": "single"
				},
				{
					"propertyName": "long",
					"propertyType": "long",
					"isStrongProperty": 0,
					"isBuildTitanIndex": 0,
					"databaseColumnId": -1,
					"propertyId": 3,
					"propertyCardinality": "single"
				}],
				"vertexTypeName": "CallEvent",
				"image": "img/CallEvent.png",
				"name": "CallEvent2",
				"type": "CallEvent"
			},
			{
				"vertexId": 2,
				"uuid": "3jdrdwi8s3PPPlDEkfTcByABsJVzLDgX",
				"custormVertexPropertyList": [{
					"propertyName": "name",
					"propertyType": "string",
					"isStrongProperty": 0,
					"isBuildTitanIndex": 1,
					"databaseColumnId": 2,
					"propertyId": 0,
					"propertyCardinality": "single",
					"databaseColumnValue": "frompersoname"
				},
				{
					"propertyName": "idcard",
					"propertyType": "string",
					"isStrongProperty": 1,
					"isBuildTitanIndex": 1,
					"databaseColumnId": -1,
					"propertyId": 1,
					"propertyCardinality": "single"
				},
				{
					"propertyName": "country",
					"propertyType": "string",
					"isStrongProperty": 0,
					"isBuildTitanIndex": 0,
					"databaseColumnId": -1,
					"propertyId": 2,
					"propertyCardinality": "single"
				},
				{
					"propertyName": "sex",
					"propertyType": "string",
					"isStrongProperty": 0,
					"isBuildTitanIndex": 0,
					"databaseColumnId": -1,
					"propertyId": 3,
					"propertyCardinality": "single"
				},
				{
					"propertyName": "birthday",
					"propertyType": "string",
					"isStrongProperty": 0,
					"isBuildTitanIndex": 0,
					"databaseColumnId": -1,
					"propertyId": 4,
					"propertyCardinality": "single"
				}],
				"vertexTypeName": "Person",
				"image": "img/Person.png",
				"name": "Person3",
				"type": "Person"
			},
			{
				"vertexId": 3,
				"uuid": "iZJCTLJbmR0q3lYGVD6JQjdYpcJnkpGD",
				"custormVertexPropertyList": [{
					"propertyName": "model",
					"propertyType": "string",
					"isStrongProperty": 0,
					"isBuildTitanIndex": 0,
					"databaseColumnId": 5,
					"propertyId": 0,
					"propertyCardinality": "single",
					"databaseColumnValue": "tophonehome"
				},
				{
					"propertyName": "phonenum",
					"propertyType": "string",
					"isStrongProperty": 1,
					"isBuildTitanIndex": 1,
					"databaseColumnId": 1,
					"propertyId": 1,
					"propertyCardinality": "single",
					"databaseColumnValue": "phoneto"
				}],
				"vertexTypeName": "Phone",
				"image": "img/Phone.png",
				"name": "Phone4",
				"type": "Phone"
			},
			{
				"vertexId": 4,
				"uuid": "sz7LnIjMUNcKjH2BpfNlErhpEmKuB8MQ",
				"custormVertexPropertyList": [{
					"propertyName": "name",
					"propertyType": "string",
					"isStrongProperty": 0,
					"isBuildTitanIndex": 1,
					"databaseColumnId": 3,
					"propertyId": 0,
					"propertyCardinality": "single",
					"databaseColumnValue": "topersonname"
				},
				{
					"propertyName": "idcard",
					"propertyType": "string",
					"isStrongProperty": 1,
					"isBuildTitanIndex": 1,
					"databaseColumnId": -1,
					"propertyId": 1,
					"propertyCardinality": "single"
				},
				{
					"propertyName": "country",
					"propertyType": "string",
					"isStrongProperty": 0,
					"isBuildTitanIndex": 0,
					"databaseColumnId": -1,
					"propertyId": 2,
					"propertyCardinality": "single"
				},
				{
					"propertyName": "sex",
					"propertyType": "string",
					"isStrongProperty": 0,
					"isBuildTitanIndex": 0,
					"databaseColumnId": -1,
					"propertyId": 3,
					"propertyCardinality": "single"
				},
				{
					"propertyName": "birthday",
					"propertyType": "string",
					"isStrongProperty": 0,
					"isBuildTitanIndex": 0,
					"databaseColumnId": -1,
					"propertyId": 4,
					"propertyCardinality": "single"
				}],
				"vertexTypeName": "Person",
				"image": "img/Person.png",
				"name": "Person5",
				"type": "Person"
			}],
			"sqlIdentify": "test"
		}],
		"id": 1,
		"connectionServerUrl": "jdbc:mysql://192.168.40.11:3306/magicdata",
		"connectionServierName": "192.168.40.11",
		"connectionPort": "3306",
		"connectionDbName": "magicdata",
		"connectionDBUserName": "root",
		"connectionDBPassword": "*******",
		"connectionDriverName": "com.mysql.jdbc.Driver",
		"connectionDB": "MYSQL",
		"connectionName": "798",
		"connectionDBPasswordBak": "yanfa2bu@yxzc.com.cn"
	}
}*/