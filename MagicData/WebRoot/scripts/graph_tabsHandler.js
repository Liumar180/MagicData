
/* *****************************svg  start************************************* */
	var joinString = "^=-=^";
	var graphWidth = $(".left_top").innerWidth() - 2,
		graphHeight = $(".left_top").innerHeight() - 2,
		img_w = 60,
		img_h = 60,
		shiftKey=false,
		text_dx = 0,
		text_dy =35,
		line_text_dx =0,
		line_text_dy =0,
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
/* *****************************全屏加缩放************************************* */	
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
	    var zoom_line_height="300px";
	    
		var zoom_line=d3.select('.zoomline').append('svg').attr("width",zoom_line_width).attr("height",zoom_line_height).append("g").attr("cursor","pointer");  
		var add=zoom_line.append("g").attr("class","add");
		var cut=zoom_line.append("g").attr("class","cut");
		var toall=zoom_line.append("g");
		var hisGraph=zoom_line.append("g").attr("id", "addHisGraph");
		var exportView=zoom_line.append("g").attr("id", "exportView");
		var allbtn=toall.selectAll("polygon.toall");
		var nombtn=toall.selectAll("polygon.tonomarl")
		var tozoomline=toall.selectAll("line.zoomline");
		var tonomline=toall.selectAll("line.nomline");
		//document.getElementById("displaySvg").addEventListener("click", function(){alert("test");});
		//$("#displaySvg g").on("click", function(){
		//	console.log(this);
		//});
		//全屏
		toall.append("rect")
			 .attr("x",zoom_x-10)
		     .attr("y",zoom_y-50)
		     .attr("width",20)
		     .attr("height",20)
		     .attr("fill-opacity","0.1")
		      .attr("stroke","#fff")
		      .attr("fill","#f00");
		hisGraph.append("title").text("保存历史记录");
		hisGraph.append("svg:image")
	      .attr("width", 25)
	      .attr("height", 25)
	      .attr("x",30-13)
	      .attr("y",zoom_y+140)
	      .attr("xlink:href", "images/layout/hisGraph.png");
		exportView.append("title").text("导出");
		exportView.append("svg:image")
		  .attr("width",25)
		  .attr("height",25)
		  .attr("x",30-13)
		  .attr("y",zoom_y+170)
		  .attr("xlink:href","images/layout/exportIcon1.png");
		var  alldata=[{x1:zoom_x-6,y1:zoom_y-46,x2:zoom_x-1,y2:zoom_y-46,x3:zoom_x-6,y3:zoom_y-41},{x1:zoom_x+6,y1:zoom_y-46,x2:zoom_x+1,y2:zoom_y-46,x3:zoom_x+6,y3:zoom_y-41},{x1:zoom_x-6,y1:zoom_y-34,x2:zoom_x-1,y2:zoom_y-34,x3:zoom_x-6,y3:zoom_y-39},{x1:zoom_x+6,y1:zoom_y-34,x2:zoom_x+1,y2:zoom_y-34,x3:zoom_x+6,y3:zoom_y-39}];
		var  normaldata=[{x1:zoom_x-1,y1:zoom_y-39,x2:zoom_x-1,y2:zoom_y-34,x3:zoom_x-6,y3:zoom_y-39},{x1:zoom_x+1,y1:zoom_y-39,x2:zoom_x+1,y2:zoom_y-34,x3:zoom_x+6,y3:zoom_y-39},{x1:zoom_x-1,y1:zoom_y-41,x2:zoom_x-1,y2:zoom_y-46,x3:zoom_x-6,y3:zoom_y-41},{x1:zoom_x+1,y1:zoom_y-41,x2:zoom_x+1,y2:zoom_y-46,x3:zoom_x+6,y3:zoom_y-41}];   
		var zoomlinedata=[{x1:zoom_x-6,y1:zoom_y-46,x2:zoom_x+6,y2:zoom_y-34},{x1:zoom_x+6,y1:zoom_y-46,x2:zoom_x-6,y2:zoom_y-34}]
		var zoomlinedata2=[{x1:zoom_x-6,y1:zoom_y-46,x2:zoom_x-2,y2:zoom_y-42},{x1:zoom_x+6,y1:zoom_y-34,x2:zoom_x+2,y2:zoom_y-38},{x1:zoom_x+6,y1:zoom_y-46,x2:zoom_x+2,y2:zoom_y-42},{x2:zoom_x-6,y2:zoom_y-34,x1:zoom_x-2,y1:zoom_y-38}]
		allbtn=allbtn.data(alldata)
			          .enter()
			          .append("polygon")
			          .attr("points",function(d){
			        	    return d.x1+" "+d.y1+" "+d.x2+" "+d.y2+" "+d.x3+" "+d.y3
			          })
			          .attr("fill","#fff")
			          .attr("class","toall");
		nombtn=nombtn.data(normaldata)
			        .enter()
			        .append("polygon")
			        .attr("points",function(d){
			      	    return d.x1+" "+d.y1+" "+d.x2+" "+d.y2+" "+d.x3+" "+d.y3
			        })
			        .attr("fill","#fff")
			        .attr("class","tonomarl")
			        .attr("visibility","hidden");
	   
		tozoomline=tozoomline.data(zoomlinedata)
                    .enter()
                    .append("line")
                    .attr("x1",function(d){return d.x1;})
         		   .attr("y1",function(d){return d.y1;})
        		   .attr("x2",function(d){return d.x2;})
        		   .attr("y2",function(d){return d.y2;})
        		   .attr("stroke","#fff")
        		   .attr("class","zoomline");
		tonomline= tonomline.data(zoomlinedata2)
		           .enter()
		           .append("line")
		           .attr("x1",function(d){return d.x1;})
				   .attr("y1",function(d){return d.y1;})
				   .attr("x2",function(d){return d.x2;})
				   .attr("y2",function(d){return d.y2;})
				   .attr("stroke","#fff")
				   .attr("class","nomline")
				   .attr("visibility","hidden");
		var isall=false;
		var scwidth=document.body.clientWidth;
		var scheight=document.body.clientHeight;
		toall.on('click',function(){	
			if(!isall){
					$('.left_down').hide();
					$('.right.clearfix').hide();
					tozoomline.attr("visibility","hidden");
					allbtn.attr("visibility","hidden");
					tonomline.attr("visibility","show");
					nombtn.attr("visibility","show");
					svg.attr("width",scwidth);
					svg.attr("height",scheight-150);
					isall=true;
					/*$(".left_top").css("height", "100%");
		  			$("#timelineShrink").css("border-top", "0px");
		  			$("#timelineShrink").css("border-bottom", "12px solid black");
		  			$(".right_shrink").css("top", "-823px");
		  			$(".left_top").css("width", "100%");
		  			$(".right_shrink").css("right", "1%");
		  			$("#propertyShrink").css("border-left", "0px");
		  			$("#propertyShrink").css("border-right", "12px solid black");
		  			$(".left_down_shrink").css("width", "99%");
		  			$(".left_down").css("width", "96%");*/
				}else{
					$('.left_down').show();
					$('.right.clearfix').show();
					tozoomline.attr("visibility","show");
					allbtn.attr("visibility","show");
					tonomline.attr("visibility","hidden");
					nombtn.attr("visibility","hidden");
					svg.attr("width",graphWidth); 
					svg.attr("height",graphHeight);
					isall=false;
					/*$(".left_top").css("height", "70%");
		  			$("#timelineShrink").css("border-top", "12px solid black");
		  			$("#timelineShrink").css("border-bottom", "0px");
		  			$(".right_shrink").css("top", "-576px");
		  			$(".left_top").css("width", "75%");
		  			$(".right_shrink").css("right", "24%");
		  			$("#propertyShrink").css("border-left", "12px solid black");
		  			$("#propertyShrink").css("border-right", "0px");
		  			$(".left_down_shrink").css("width", "76%");
		  			$(".left_down").css("width", "74%");*/
				}
		})
		

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
//	          console.log((zoom_y+add_r+bar_len/2-drag_r-diffY)/(bar_len/2)*0.6) 
//	           console.log((zoom_y+add_r+bar_len/2-drag_r-diffY)/(bar_len/2)*4) 
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
//				console.log(cirscale)
//				console.log(zoom_y+add_r+bar_len-drag_r)
//				console.log(cy)
	            if(cy  > (zoom_y+add_r+bar_len-drag_r)){//bar_len + border_len+ margin 
	        	  cy = zoom_y+add_r+bar_len-drag_r;
	            }
	            if(cy <(add_r+zoom_y+drag_r)){ //border_len+ margin 
	         	  cy =add_r+zoom_y+drag_r;
	            } 
			    cir.attr("cy",cy);
			}
		}
/* *****************************全屏加缩放************************************* */		
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
				graphOperation.clearSelStatus();
				tabsManager.clearShow(".personPage");
				tabsManager.clearShow(".maincon");
				tabsManager.histogramShow(1,nodes);
				$('.first a').click();
				timeLineOperation.showTimeLineById("all","all");
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
		            	graphOperation.brushNode(d);
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
	
	function graphDrawUpdate() {
		var flag = 0;
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
				flag ++;
				var relation=d.relation;
				if(relation.substr(0,8)=="relation"){
					return [
						        {data:"relation",dy:"-2"},
						        {data:relation.substr(9,relation.length),dy:"1em"}
					       ]
				}else if(relation.substr(0,7)=="connect"){
					return [{data:"connect",dy:"-2"},{data:relation.substr(8,relation.length),dy:"1em"}]
				}else if(relation.substr(0,8)=="nickname"){
					if(flag%2)
						return [{data:relation.substr(9,relation.length),dy:"12"}]
					else
						return [{data:relation.substr(9,relation.length),dy:"-2"}]
				}else{
					return [{data:" ",dy:"-2"},{data:" ",dy:"1em"}]
				}
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
				 if(typeof(id) == "string") tempArr = id.split(joinString);
				 var subList = d.subList
//				 if(type=="Person"){return "name: "+d.name?d.name:""}
				 if(subList){
					 if(type=="EmailEvent"){return "count: "+subList.length+"<br/>"+ "from: "+tempArr[0]+"<br/>"+"to: "+tempArr[1]}
					 if(type=="LoginEvent" || type=="StayEvent"){return "count: "+subList.length}
					 if(type=="CallEvent"){return "count: "+subList.length+"<br/>"+"from: "+tempArr[0]+"<br/>"+"to: "+tempArr[1]}
				 }
				 
				 var configArr = pageVariate.viewConfig[type];;
				 var first = configArr[0];
				 var second = configArr[1];
				 
				 var firstValue = d[first];
				 if(firstValue){
					 return first+" : "+firstValue;
				 }else if(second){
					 var secondValue = d[second];
					 if(secondValue){
						 return second+" : "+secondValue; 
					 }else{
						 return "";
					 }
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
				graphOperation.clickNode(d);
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
				 if("Person" == d.type){
					 pageVariate.personId = d.id;
				 }
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
			if(subList){
				return [{data:subList.length,dy:"1em",c:"count"}];
			}else{
				var configArr = pageVariate.viewConfig[d.type];
				var first=null;
				var second=null;
				if(configArr != undefined){
					first = configArr[0];
					second = configArr[1];
				}else{
					return;
				}
				
				var firstValue = d[first];
				if(firstValue){
					return [{data:firstValue,dy:"1em",c:"name"}];
				}else if(second){
					var secondValue = d[second];
					if(secondValue){
						return [{data:secondValue,dy:"1em",c:"name"}]; 
					}else{
						return [{data:"",c:"name"}];
					}
				}
			}
			
			/*var subList = d.subList;
			if(d.type=="Person" || d.type=="own"){
				//先修改为没有name显示email
				var email = d.Email?d.Email[0].email:"";
				var value = d.name?d.name:email;
				return [{data:value,dy:"1em",c:"name"}];
			}else if(d.type=="CallEvent" || d.type=="EmailEvent" || d.type=="LoginEvent" || d.type=="StayEvent"){
				if(subList){
					return [{data:subList.length,dy:"1em",c:"count"}];
				}else{
					return [{data:"",c:"name"}];
				}
			}else if(d.type=="QQ" || d.type=="Group"){
				return [{data:d.name,c:"name"}];
			}else{
				return [{data:"",c:"name"}];
			}*/
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
					graphOperation.clickNode(d);
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
//			if(d.direction == "out"){
				x2 =subtract(d,"x2"),
				y2 = subtract(d,"y2");
//			}else{
//				x2 =subtract(d,"x1"),
//				y2 = subtract(d,"y1");
//			}
			return  x2+" "+y2+" "+(x2-7)+" "+(y2+15)+" "+x2+" "+(y2+8)+" "+(x2+7)+" "+(y2+15)
			
		})
		.attr("transform",function(d){
			var x2,y2;
			var x1,y1,rotate;
			var sourceNode = graphOperation.findNode(d.source.uuid);
			var targetNode = graphOperation.findNode(d.target.uuid);
//			if(d.direction == "out"){
				x1 = sourceNode.x;
				y1 = sourceNode.y;
				x2 =subtract(d,"x2");
				y2 = subtract(d,"y2");
//			}else{
//				x1 = targetNode.x;
//				y1 = targetNode.y;
//				x2 =subtract(d,"x1");
//				y2 = subtract(d,"y1");
//			}	
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
	
	function subtract(d,loc){
		/*var x1 = d.source.x,
		y1 = d.source.y;
		x2 = d.target.x,
		y2 = d.target.y;*/
		
		var sourceNode = graphOperation.findNode(d.source.uuid);
		var targetNode = graphOperation.findNode(d.target.uuid);
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
		var sourceNode = graphOperation.findNode(d.source.uuid);
		var targetNode = graphOperation.findNode(d.target.uuid);
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
	
	
/* ****************************svg end************************************* */
	
/* ****************************节点操作 开始************************************* */
var graphOperation = {
	/**判断是否是own节点*/
	judgeOWN : function(v){
		var ownf = v.own;
		if("own" == ownf){
			return true;
		}
		return false;
	},
	/**清空选中*/
	clearSelStatus : function (){
		// 1、更改图片信息
		for(var i = 0; i < pageVariate.selNode.length; i ++){
			var oldImage = $(".n"+ pageVariate.selNode[i].uuid +" image").attr("href");
			var newImage = oldImage.replace("Sel.png", ".png");
			$(".n"+ pageVariate.selNode[i].uuid +" image").attr("href", newImage);
		}
		// 2、清空缓存的选中节点
		pageVariate.selNode = [];
	},
	/**还原按钮状态*/
	resetCurrentNode : function (){
		$("#menuDiv").html("");
		if(pageVariate.currentSelFlag){
			pageVariate.currentSelFlag = false;
		}else{
			var type = pageVariate.currentNode.image.replace(/\//g, "").replace("img", "").replace(".png", "");
			pageVariate.selNode.remove(pageVariate.currentNode);
			$(".n"+pageVariate.currentNode.uuid +" image").attr("href", "images/img/"+type+".png");
		}
	},
	/**点击选中*/
	clickNode : function (d){
		var type = d.type;
		/*if("EmailEvent" == type){
			showEmailDetail(d.id);
		}*/
		if(!graphOperation.judgeOWN(d)){
			if(!pageVariate.pressCtrl){
				graphOperation.clearSelStatus();
				tabsManager.browserShow(d);
				$('.second a').click();
			}
			var index = pageVariate.selNode.indexOf(d);
			if(index < 0){
				pageVariate.selNode.push(d);
				$(".n"+d.uuid +" image").attr("href", "images/img/"+type+"Sel.png");
			}else{
				pageVariate.selNode.splice(index, 1);
				$(".n"+d.uuid +" image").attr("href", "images/img/"+type+".png");
			}
			if(pageVariate.selNode.length > 1){
				tabsManager.clearShow(".personPage");
				tabsManager.clearShow(".maincon");
				$('.first a').click();
			}else{
				tabsManager.browserShow(pageVariate.selNode[0]);
				$('.second a').click();
			}
			if(type=="Person"){
				if(!timeLineOperation.currentTimeLineId){
					timeLineOperation.currentTimeLineId = d.id;
				}
				//timeLineOperation.showTimeLineById(d);
				if(timeLineOperation.currentTimeLineId != d.id){
					timeLineOperation.currentTimeLineId = d.id;
					//var typeArr = pageVariate.typeMap.get(d.id);
					timeLineOperation.showTimeLineById(d.id,graphOperation.findEventById(d.id));
				}
			}
		}else{
//			var imgpath = d.image;
//			//获取节点的真是类型
//			var proType = imgpath.substring(imgpath.lastIndexOf("/")+1,imgpath.length-4);
			//简历展示
			if("Resume" == type){
				graphOperation.toShowResumeDetails(d.id);
			}
		}
		tabsManager.histogramShow(0,pageVariate.selNode);
	},
	/**点框选选中*/
	brushNode : function (d){
		var type = d.type;
		if(!graphOperation.judgeOWN(d)){
			var index = pageVariate.selNode.indexOf(d);
			if(index>-1){
			}else{
				$(".n"+d.uuid +" image").attr("href", "images/img/"+type+"Sel.png");
				pageVariate.selNode.push(d);
			}
		}
		tabsManager.histogramShow(0,pageVariate.selNode);
	},
	/**节点展开*/
	expandNode : function (childNodes,childLinks){
        childNodes.forEach(function(node){
        	nodes.push(node);
        });

         childLinks.forEach(function(link){
        	 edges.push(link);
        });
        
        graphDrawUpdate();
    },
    /**节点关闭*/
    collapseNode : function (uuid){
        graphOperation.removeChildNodes(uuid,false);
        graphOperation.updateGraphAndCache();
    },
    /**删除事件相关子节点*/
    deteleEventNode : function (uuid){
        graphOperation.removeChildNodes(uuid,true);
        //graphOperation.updateGraphAndCache();
        backendCacheManager.deleteIdCache(pageVariate.tempDeleteId);
        pageVariate.tempDeleteId=[];
    },
    /**删除节点及子节点*/
    deteleNode : function (uuid){
        graphOperation.removeChildNodes(uuid,false);
        graphOperation.removeNode(uuid);
        
        graphOperation.updateGraphAndCache();
    },
    /**删除选中的节点及子节点*/
    deteleSelNode : function (nodeArr){
    	var tempArr = [];
    	nodeArr.forEach(function(node,index){
    		tempArr.push(node);
    	});
    	tempArr.forEach(function(node,index){
    		if(node.type == "Person"){
    			graphOperation.removeChildNodes(node.uuid,false);
    		}
    		graphOperation.removeNode(node.uuid);
    	})
    	graphOperation.updateGraphAndCache();
    	$(".serList").empty();
    	$(".personPage").empty();
    },
    /**删除未选中的节点及子节点*/
    deteleUnSelNode : function (){
    	var nodeArr = pageVariate.selNode;
    	var tempArr = [];
    	nodes.forEach(function(node,index){
    		if(nodeArr.indexOf(node) < 0){
    			tempArr.push(node);
    		}
    	});
    	tempArr.forEach(function(node,index){
    		if(node.type == "Person"){
    			graphOperation.removeChildNodes(node.uuid,false);
    		}
    		graphOperation.removeNode(node.uuid);
    	})
    	graphOperation.updateGraphAndCache();
    	$(".serList").empty();
    	$(".personPage").empty();
    },
    /**更新图及删除缓存*/
    updateGraphAndCache : function (){
        graphDrawUpdate();
        backendCacheManager.deleteIdCache(pageVariate.tempDeleteId);
        pageVariate.tempDeleteId=[];
    },
    /**清空graph*/
    emptyNode : function (){
    	nodes = [];
		edges = [];
		graphDrawUpdate();
		backendCacheManager.emptyIdCache();
		
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
    },
	/**查找节点*/
	findNode : function (uuid){
	    for(var i = 0;i < nodes.length; i++) {
	        if (nodes[i]['uuid']==uuid ) return nodes[i];
	    }
	    return null;
	},
	/**查找节点所在索引号*/
	findNodeIndex : function (uuid){
		for(var i = 0;i < nodes.length; i++) {
	        if (nodes[i]['uuid']==uuid ) return i;
	    }
	    return -1;
	},
	/**查找节点所在索引号(根据ID查，多个返回第一个)*/
	findNodeIndexById : function (id){
		for(var i = 0;i < nodes.length; i++) {
	        if (nodes[i]['id']==id ) return i;
	    }
	    return -1;
	},
	/**查找节点(根据ID查，多个返回第一个)*/
	findNodeById : function (id){
		if(id){
			for(var i = 0;i < nodes.length; i++) {
				if (nodes[i]['id']==id ) return nodes[i];
			}
		}
	    return null;
	},
	/**删除节点*/
	removeNode : function (uuid){
//			flag = flag?true:pageVariate.uuid != uuid;
//			if(flag){
			var i=0,
				j=0,
				k=0,
				n=graphOperation.findNode(uuid);//获取节点
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
//			}
	},
	/**删除节点下的子节点，同时清除link信息,eventFlag只删除事件相关节点*/
	removeChildNodes : function (uuid,eventFlag){
		//修改为删除相邻节点，不进行递归，不管方向
		var node=graphOperation.findNode(uuid);
	    var childNodes=[];
	    edges.forEach(function(link,index){
	    	var tNode = link['target'];
	    	var sNode = link['source'];
	    	var tType = tNode.type;
	    	var sType = sNode.type;
	    	if(eventFlag){
	    		if(handleUtil.judgeEvent(tType)){
	    			link['source'].uuid==node.uuid 
	    			&& childNodes.push(tNode);
	    		}
	    		if(handleUtil.judgeEvent(sType)){
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
	    	graphOperation.removeNode(node.uuid);
	    });
		
		
	    /*var node=graphOperation.findNode(uuid);

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
//		            		   		edges.splice(i,1);
		        					tempDeleteEdge.push(i);
		        					remove(target);
		        				}
		        				//nodes.splice(graphOperation.findNodeIndex(node.uuid),1);
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
		    	graphOperation.removeNode(uuid);
		    });*/
	},
	/** 列表删除同步更新图 */
	listDeteleLinkage : function (node,ids){
		var uuid = node.uuid;
		var n=graphOperation.findNode(uuid);
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
	    	graphOperation.removeNode(uuid);
	        graphOperation.updateGraphAndCache();
	    }else{
	    	$(".n"+uuid +" text tspan").text(len);
//		    	graphDrawUpdate();
	    }
	},
	/**拆分节点*/
	splitNode : function (vertexs){
		if(!vertexs) return;
		var tempnodes = [];
		vertexs.forEach(function(node,index){
			tempnodes.push(node);
		});
		var newIds = [];
		for(var i=0;i<tempnodes.length;i++){
			var vertex = tempnodes[i];
			var subList = vertex.subList;
			if(subList){
				//先删除统计节点
				var uuid = vertex.uuid;
				
				//查找节点的关联节点uuid
				var tempArr = graphOperation.findFromAndTo(vertex);
				var from = tempArr[0];
				var to = tempArr[1];
				
				graphOperation.removeNode(uuid);
				graphOperation.updateGraphAndCache();
				
				//拆分
				for(var j=0;j<subList.length;j++){
					var event = subList[j];
					newIds.push(event.id);
					graphOperation.addNodeEdge(event,from,to);
				}
				graphDrawUpdate();
			}
		}
		//更新后端节点id缓存
		if(newIds.length > 0){
			backendCacheManager.modifyIdCache(newIds.toString());
		}
		//清空选中状态
		graphOperation.clearSelStatus();
	},
	/**合并节点*/
	mergeNodes : function (vertexs){
		if(vertexs){
			var tempnodes = [];
			vertexs.forEach(function(node,index){
				tempnodes.push(node);
			});
			var countMap = new Map();
			var fromMap = new Map();
			var toMap = new Map();
			var existNode = new Map();
			for(var i=0;i<tempnodes.length;i++){
				var vertex = tempnodes[i];
				var subList = vertex.subList;
				if(!subList){
					var type = vertex.type;
					if(handleUtil.judgeEvent(type)){
						var uuid = vertex.uuid;
						var typeKey = handleUtil.getKeyByEvent(vertex);
						//节点合并统计
						var countNode = countMap.get(typeKey);
						if(!countNode){
							countNode = graphOperation.findNodeById(typeKey);
							if(countNode){
								var tempuuid = countNode.uuid;
								existNode.put(tempuuid,countNode);
							}
						}
						if(countNode){
							countNode.subList.push(vertex);
						}else{
							var countNodeMap = {};
							var subList=[vertex];
							countNodeMap["subList"] = subList;
							countNodeMap["type"] = type;
							countNodeMap["id"] = typeKey;
							countNodeMap["image"] = "img/"+type+".png";
							countNodeMap["uuid"] = handleUtil.getUUID();
							//临时存储下统计节点
							countMap.put(typeKey,countNodeMap);
							//临时存储关联节点uuid
							var tempArr = graphOperation.findFromAndTo(vertex);
							fromMap.put(typeKey,tempArr[0]);
							toMap.put(typeKey,tempArr[1]);
						}
						//删除节点
						graphOperation.removeNode(uuid);
					}
				}
			}
			graphOperation.updateGraphAndCache();
			
			var mapArr = countMap.arr;
			var newIds = [];
			$.each(mapArr,function(i,map){
				var key = map.key;
				var countNodeMap = map.value;
				newIds.push(countNodeMap.id);
				var from = fromMap.get(key);
				var to = toMap.get(key);
				//添加节点和边
				graphOperation.addNodeEdge(countNodeMap,from,to)
			});
			//更新后端节点id缓存
			backendCacheManager.modifyIdCache(newIds.toString());
			
	        graphDrawUpdate();
	        //更新节点文字
	        var existNodeArr = existNode.arr;
	        $.each(existNodeArr,function(i,map){
	        	var uuid = map.key;
				var node = map.value;
		        $(".node.n"+uuid).find("tspan").html(node.subList.length);
			});
	        //清空选中状态
			graphOperation.clearSelStatus();
		}
	},
	/**查找节点的关联节点uuid - 返回数组第一个元素是source数组，第二个元素是target数组*/
	findFromAndTo : function (vertex){
		var uuid = vertex.uuid;
		var from = [];
		var to = [];
		for(var i=0;i<edges.length;i++){
			var edge = edges[i];
			var  sourceuuid = edge['source'].uuid;
			var targetuuid = edge['target'].uuid;
			if(sourceuuid == uuid){
				to.push(targetuuid);
			}
			if(targetuuid == uuid){
				from.push(sourceuuid);
			}
		}
		return [from,to];
	},
	/**
	 * 添加节点和边 
	 * event 需要添加的节点
	 * from 起点uuid数组
	 * to 目标点uuid数组
	 */
	addNodeEdge : function (event,from,to){
		nodes.push(event);
		var maxIndex = nodes.length-1;
		if(from){
			for(var k=0;k<from.length;k++){
				var fuuid = from[k];
				var index = graphOperation.findNodeIndex(fuuid);
				if(index != -1){
					var edgeMap = {};
					edgeMap["source"] = index;
					edgeMap["target"] = maxIndex;
					edgeMap["direction"] = "out";
					edgeMap["relation"] = "";
					edgeMap["uuid"] = handleUtil.getUUID();
					edges.push(edgeMap);
				}
			}
		}
		if(to){
			for(var k=0;k<to.length;k++){
				var tuuid = to[k];
				var index = graphOperation.findNodeIndex(tuuid);
				if(index != -1){
					var edgeMap = {};
					edgeMap["source"] = maxIndex;
					edgeMap["target"] = index;
					edgeMap["direction"] = "out";
					edgeMap["relation"] = "";
					edgeMap["uuid"] = handleUtil.getUUID();
					edges.push(edgeMap);
				}
			}
		}
	},
	/**根据人物id查找节点的关联的事件节点数组，参数是当前点击节点的id，如果不传参数统计全部事件节点 */
	findEventById : function (currId){
		var currNode = graphOperation.findNodeById(currId);
		return graphOperation.findEventByNode(currNode);
	},
	/**查找人物节点的关联的事件节点数组，参数是当前点击的节点，如果不传参数统计全部事件节点 */
	findEventByNode : function (vertex){
		var events = [];
		if(vertex){
			var uuid = vertex.uuid;
			for(var i=0;i<edges.length;i++){
				var edge = edges[i];
				var source = edge['source'];
				var target = edge['target'];
				var sourceuuid = source.uuid;
				var targetuuid = target.uuid;
				if(sourceuuid == uuid ){
					var type = target.type;
					if(handleUtil.judgeEvent(type) && events.indexOf(target) == -1){
						events.push(target);
					}
				}
				if(targetuuid == uuid){
					var type = source.type;
					if(handleUtil.judgeEvent(type) && events.indexOf(source) == -1){
						events.push(source);
					}
				}
			}
		}else{
			for(var i=0;i<nodes.length;i++){
				var tempnode = nodes[i];
				var type = tempnode.type;
				if(handleUtil.judgeEvent(type)){
					events.push(tempnode);
				}
			}
		}
		return events;
	},
	/**显示简历信息*/
	toShowResumeDetails : function (vertexId){
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
}
/* ****************************节点操作 结束************************************* */

/* ****************************util end************************************* */
var handleUtil = {
	getUUID : function(){
	    var s = [];
	    var hexDigits = "0123456789abcdef";
	    for (var i = 0; i < 36; i++) {
	        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	    }
	    s[14] = "4";  
	    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); 
	    s[8] = s[13] = s[18] = s[23] = "-";
	 
	    var uuid = s.join("");
	    return uuid;
	},
	judgeEvent : function(type){
		return typeof(type) == "string" && type.endWith("Event");
	},
	/**根据事件类型获取统计节点id值*/
	getKeyByEvent : function(vertex){
		var type = vertex.type;
		var typeKey = "";
		switch (type) {
		case "CallEvent":
			typeKey = vertex.from+joinString+vertex.to;
			break;
		case "EmailEvent":
			typeKey = vertex.from+joinString+vertex.to.toString().replace(new RegExp(",","gm"), ";");
			break;
		case "StayEvent":
			typeKey = vertex.hotelid+joinString+vertex.orderno+joinString+vertex.hotelflag;
			break;
		default:
			var personId = "";
			for(var i=0;i<edges.length;i++){
				var edge = edges[i];
				var targetuuid = edge['target'].uuid;
				if(targetuuid == uuid){
					personId = edge['source'].id;
					break;
				}
			}
			typeKey = personId+joinString+type;
			break;
		}
		return typeKey;
	},
	/**根据事件类型获取节点时间值*/
	getTimeByEvent : function(vertex){
		var type = vertex.type;
		var time;
		switch (type) {
		case "StayEvent":
			time = vertex.arrivedate;
			break;
		default:
			time = vertex.time;
			break;
		}
		return time;
	}
}
/* ****************************util end************************************* */	
		
/* ****************************时间轴操作 开始************************************* */
/**时间轴缓存变量*/
var timeLineVariate = {
	stratYearTime:"",//还原年起始时间
	endYearTime:"",//还原年结束年时间
	yearTimeCha:"",//时间差值（时间范围值）
	dayStartTime:"",//查询具体天的时间
	dayStartTimeMonth:"",//还原月起始时间
	dayEndTimeMonth:"",//还原月结束年时间
	firstTimeFormat:true
};
var timeLineOperation = {
	  	timeLineDragFlag : true,
	  	currentTimeLineId : "",
	    timeIdDragHistory : new Map(),
	    /**拖动时间轴图联动*/
	  	graphLinkage : function (start,end){
		  	if(start && end){
	  			if(timeLineOperation.timeLineDragFlag){
		  			timeLineOperation.timeLineDragFlag = false;
					pageVariate.startTime=start;
					pageVariate.endTime=end;
					//记录时间轴拖动历史
					if(timeLineOperation.currentTimeLineId){
						var temps = [];
						temps[0] = start;
						temps[1] = end;
						timeLineOperation.timeIdDragHistory.put(timeLineOperation.currentTimeLineId,temps);
					}
					
			  		if(pageVariate.eventFlag == "event"){
//	 					graphOperation.deteleEventNode(pageVariate.uuid);//删除事件相关节点
//	 		  			searchManager.showEvent(pageVariate.types,pageVariate.startTime,pageVariate.endTime);
						timeLineOperation.timeDragHighlight(start,end);
			  		}else if(pageVariate.eventFlag == "map"){//地图
//	 		  			showMap(pageVariate.personId,pageVariate.startTime,pageVariate.endTime);
			  			showMap(pageVariate.personId,(getDateForStringDate(start)).getTime(),(getDateForStringDate(end)).getTime());
			  		}else{
			  			maskManager.hide();
			  		}
		  			timeLineOperation.timeLineDragFlag = true;
	  			}
		  	}
	  	},
		/**时间轴拖动图高亮*/
		timeDragHighlight : function (start,end){
			graphOperation.clearSelStatus();
			if(start && end){
				var vertexs = graphOperation.findEventById(timeLineOperation.currentTimeLineId);
				$.each(vertexs,function(i,node){
					var type = node.type;
					if(handleUtil.judgeEvent(type)){
						var subList = node.subList;
						if(subList){//统计节点
							for(var i=0;i<subList.length;i++){
								var event = subList[i];
								var time = handleUtil.getTimeByEvent(event);
								if(time>=start && time<=end){
									$(".n"+node.uuid +" image").attr("href", "images/img/"+type+"Sel.png");
									pageVariate.selNode.push(node);
									break;
								}
							}
						}else{
							var time = handleUtil.getTimeByEvent(node);
							if(time>=start && time<=end){
								$(".n"+node.uuid +" image").attr("href", "images/img/"+type+"Sel.png");
								pageVariate.selNode.push(node);
							}
						}
					}
				});
			}
		},
		/** 时间轴联动 */
	  	showTimeLineById : function (id,currentNodes){
	 	   if(!id||!currentNodes){
	 		   hintManager.showHint("获得节点信息失败，请联系管理员");
	 	   }else{
	 		   var eventTypes = [];
	 	   	   var eventTimes = [];
	 	       var dragTime = null;
	 	       var allFlag = false;
	 		   if("all"==id&&"all"==currentNodes){
	 			   if(timeLineOperation.currentTimeLineId=="all") return;
	 			   currentNodes = nodes;
	 			   allFlag = true;
	 		   }else{
	 			   dragTime = timeLineOperation.timeIdDragHistory.get(timeLineOperation.currentTimeLineId);
	 		   }
	 		   $.each(currentNodes,function(i,node){
	 				var type = node.type.trim();
	 				if(typeof(type) == "string" && type.endWith("Event")){
	 					if(eventTypes.indexOf(type)==-1){
	 						eventTypes.push(type);
	 					}
	 					var eventNodes = node.subList;
	 					if(eventNodes==undefined||eventNodes==null){
	 						eventTimes.push(type+"="+node.time.trim());
	 					}else{
	 						eventNodes.forEach(function(subnode,index){
	 							eventTimes.push(type+"="+subnode.time.trim());
	 						});
	 					}
	 				}
	 		   });
	 		   showTimeLine(eventTypes,eventTimes,dragTime);
	 		   if(allFlag){
	 			   timeLineOperation.currentTimeLineId="all";
	 		   }
	 	   }
	    }
	  	
        /*点击菜单时间轴联动(时间轴统计当前页面节点，修改前)*/
/*     function showTimeLline(id,types){
		    isYearType=true;
		    var typeStr = "";
	    if(id){
	    	 var options = myChart.getOption(); 
	    	 try{typeStr = types.toString()}catch(e){typeStr = "";}
	    	 myChart.clear();
			 var startime=pageVariate.startTimeStr;
	    	 var endtime=pageVariate.endTimeStr;
	    	 //alert(startm+"------------"+endtm);
	         $.ajax({  
	          type : "post",  
	          async : false, //同步执行  
	          url : pageVariate.base+"bar/queryYearsById.action", 
	          data : {"id":id,"types":typeStr,"startm":startime,"endtm":endtime},  
	          dataType : "json", //返回数据形式为json  
	          success : function(result) {          	
	              if (result) { 
	            	  timeLineVariate.yearTimeCha=result.dateYear;
	             	  options.title.text = result.title; 
	                  options.legend.data = result.legend;                                      
	                  options.xAxis[0].data = result.xcategory;
					  if(pageVariate.initFlag){
						  pageVariate.initStartTime = result.xcategory[0];
						  pageVariate.initEndTime = result.xcategory[result.xcategory.length-1];
						  pageVariate.initFlag = false;
					  }
					  options.series=result.series;
	               myChart.hideLoading();  
	               myChart.setOption(options);
	               if(timeLineVariate.firstTimeFormat){
	            	   	 timeLineVariate.firstTimeFormat = false;
		                 var sd=myChart.component.xAxis.option.xAxis[0].data;
			  			 var frimt=sd[0];  
			  	       	 var endmt=sd[sd.length-1];
			  	       	 if(frimt.length<8 &&endmt.length<8 && frimt<endmt&&frimt.length>5 &&endmt.length>5){
			  	          	timeLineVariate.stratYearTime=frimt;
			  	            timeLineVariate.endYearTime=endmt; 
			  	        	getLastDaytoLong(frimt,endmt); 
		  	       	      }
	               }
		  	       	 
		  	       	if(startime.length>14){ isYearType=false;} 
		  	       	 
	               }  
	           },  
	          error : function(errorMsg) {  
	        	  hintManager.showHint("服务器繁忙，请稍后重试！");
	              myChart.hideLoading();  
	             }  
	        });
	  }
	}  */
}
/* ****************************时间轴操作 结束************************************* */

/* *****************************tabs start************************************* */

var tabsManager = {
	recWidth : 100,// 矩形宽度
	browserUl : d3.select('.browserProperty').append('ul'),
	vpro : [ 'index', 'weight', 'x', 'y', 'px', 'py', 'fixed', 'id', 'type','image','uuid','isdrag','isdrag2','previouslySelected','selected' ],// 需要过滤的属性
	fpro : [ 'type', 'hash', 'id'],
	list_node:'',
	/** 直方图展示 1表示其他   2表示地图 */
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
	/** 展开对象统计信息 */
	appendType : function(datas) {
		var all = datas.length;
		var showNumber = 2;
		var btnIsShow = false;
		var scale = this.recWidth / all;
		var personCount = 0;
		var showData = {};
		$.each(datas, function(i, value) {
			var imagepath = value.image;
			var type = "";
			if(imagepath){
				type = imagepath.replace(/\//g, "").replace("img", "").replace(".png", "");
			}else{
				type = value.type;
			}
			if(!showData[type])
				showData[type] = {count:0}
			showData[type].count ++;
		});
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
	    		// $("#allTypeList").css("overflow-y", "hidden");
	    	}else{
	    		$("#allTypeList").append(dataHideLi+other);
	    		btnIsShow = true;
	    		// $("#allTypeList").css("overflow-y", "scroll");
			}
		    showNumber --;
		}
		if(btnIsShow){
			var li = $('<li></li>');
			var loadBtn = $('<div style="float: right;margin-right: 10%;cursor: pointer;background-color: #4dafe3;padding: 2px 6px;border-radius: 4px;">更多</div>');
			li.append(loadBtn);
			loadBtn.click(function(){
				var _this = this;
				var action = $(_this).html();
				if("更少" == action){
					$("#allTypeList").children().css("display", "none");
					for(var i = 0; i < 2; i ++){
						$($("#allTypeList").children()[i]).css("display", "block");
					}
					$($("#allTypeList").children()[$("#allTypeList").children().length-1]).css("display", "block");
					$(_this).html("更多");
				}else if("更多" == action){
					$("#allTypeList").children().css("display", "block");
					$(_this).html("更少");
				}
			});
			$("#allTypeList").append(li)
		}
	},
	/** 展开对象详细信息 */
	appendTable : function(datas) {
		this.fillHtml(1,datas,"histogramDiv");
	},
	/** 浏览器详细信息 */
	browserShow : function(node) {
		if(node) this.infoHtml(node);
	},
	/** 清楚指定ID或者Class的标签内容  */
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
					str+='<ul class="serUl clearfix"><li class="s0"><input type="checkbox" name="leftcheckbox" class="checkbox" id="'+one.id+'"/></li> <li class="s1" title="'+one.time+" "+one.long+'">'+one.time+" "+one.long+'</li><li class="s2"><img src="images/img/top.png" width="12" height="7" /></li></ul><div class="serDetail"><ul class="clearfix"  ><li title="'+one.from+'">主叫人:'+one.from+'</li><li title="'+one.to+'">被叫人:'+one.to+'</li><li title="'+one.time+'">通话日期:'+one.time+'</li><li  title="'+one.long+'">通话时长:'+one.long+'</li></ul></div>';
				})
			}else if(type=='EmailEvent'){
				$.each(subList,function(i,one){
					str+='<ul class="serUl clearfix"><li class="s0"><input type="checkbox" name="leftcheckbox" class="checkbox" id="'+one.id+'"/></li> <li class="s1" onclick="toShowEmailDetails('+one.id+')" title="'+one.time+" "+one.title+'">'+one.time+" "+one.title+'</li><li class="s2"><img src="images/img/top.png" width="12" height="7" /></li></ul><div class="serDetail"><ul class="clearfix"><li title="'+one.time+'">时间:'+one.time+'</li><li title="'+one.title+'">主题:'+one.title+'</li><li title="'+one.from+'">发件人:'+one.from+'</li><li title="'+one.to+'">收件人:'+one.to+'</li><li title="'+one.content+'">内容:'+one.content+'</li></ul></div>';
				}) 
			}else if(type=='LoginEvent'){
				$.each(subList,function(i,one){
					str+='<ul class="serUl clearfix"><li class="s0"><input type="checkbox" name="leftcheckbox" class="checkbox" id="'+one.id+'"/></li> <li class="s1" title="'+one.time+" "+one.domain+'">'+one.time+" "+one.domain+'</li><li class="s2"><img src="images/img/top.png" width="12" height="7" /></li></ul><div class="serDetail"><ul class="clearfix"><li title="'+one.time+'">时间:'+one.time+'</li><li title="'+one.username+'">用户名:'+one.username+'</li><li title="'+one.domain+'">登陆网站:'+one.domain+'</li><li title="'+one.ip+'">IP:'+one.ip+'</li></ul></div>';
				}) 
			}else if(type=='StayEvent'){
				$.each(subList,function(i,one){
					str+='<ul class="serUl clearfix"><li class="s0"><input type="checkbox" name="leftcheckbox" class="checkbox" id="'+one.id+'"/></li> <li class="s1" title="'+one.arrivaldate+" "+one.hotelname+'">'+one.arrivaldate+" "+one.hotelname+'</li><li class="s2"><img src="images/img/top.png" width="12" height="7" /></li></ul><div class="serDetail"><ul class="clearfix"><li title="'+one.hotelname+'">酒店名称:'+one.hotelname+'</li><li title="'+one.orderno+'">订单号:'+one.orderno+'</li><li title="'+one.roomno+'">房间号:'+one.roomno+'</li><li title="'+one.arrivaldate+'">入住时间:'+one.arrivaldate+'</li><li title="'+one.departuredate+'">离店时间:'+one.departuredate+'</li></ul></div>';
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
	/** 填充HTML，var为nodes数组或单个节点，className为填充元素的class */
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
								var pwdCrackTempSrc = "";
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
										pwdCrackTempSrc = ' <a href="javascript:void(0)"><img alt="破解密码" src="images/img/pwdCrack.png" title="破解密码" onclick="javascript:addPTAjax('
											+"'"+parses[0]+"','"+parses[1]+"','"+parses[2]+"'"
											+')"/></a>';
									}
								}
								str += ' <strong style="font-weight:normal;float:left;">'+k+'：</strong>'
								    +  '	<b>'+pMap[k]+'</b>'
								    + pwdCrackTempSrc
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
		$("div.secondCon li span").bind('contextmenu', function (e) {
			e.preventDefault();
			var _this = this;
			$("div.secondCon li span").css("background", "rgba(1, 1, 1, 0)");
			$(_this).css("background", "#283147");
          	graphOperation.clearSelStatus();
          	pageVariate.clip.setText( $(_this).html() );
          	eval($(_this).parent().attr("onclick"));
			$('#menuHist').menu('show', {
	            left: e.pageX,
	            top: e.pageY
	        });
			pageVariate.clip.glue('clip_button', 'clip_container');
		});
	},
	/** 图高亮反选 */
	graphHighlightInvert : function(vertexs) {
		var tempArr = [];
		$.each(vertexs,function(i,node){
			var index = pageVariate.selNode.indexOf(node);
			var type = node.type;
			if(!graphOperation.judgeOWN(node)){
				if(index>-1){
					$(".n"+node.uuid +" image").attr("href", "images/img/"+type+".png");
				}else{
					$(".n"+node.uuid +" image").attr("href", "images/img/"+type+"Sel.png");
					tempArr.push(node);
				}
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
	/**过滤选中公用*/
	nodeFilter : function(key,value,vertexType){
		graphOperation.clearSelStatus();
		var typeFlag = true;//是否判断节点类型
		var caseFlag = false;//是否忽略大小写
		if(vertexType){
			caseFlag = true;
			value = value.toLowerCase();
		}
		$.each(nodes,function(i,node){
			var type = node.type;
			if(vertexType){
				typeFlag = (vertexType == type);
			}
			if(typeFlag){
//				var index = pageVariate.selNode.indexOf(node);
				for(var prokey in node){
					var pro = node[prokey];
					if(isArray(pro)){
						var flag = false;
						$.each(pro,function(i,proMap){
							var hash = proMap.hash;
							if(hash){
								if(prokey == key & value == hash){
									$(".n"+node.uuid +" image").attr("href", "images/img/"+type+"Sel.png");
									pageVariate.selNode.push(node);
									return false;
								}
							}else{
								for(var prokey2 in proMap){
									var pro2 = proMap[prokey2];
									if(key == prokey2 && (caseFlag?((pro2+"").toLowerCase().indexOf(value) > -1):(pro2 == value))){
//									if(index>-1){
//									}else{
										$(".n"+node.uuid +" image").attr("href", "images/img/"+type+"Sel.png");
										pageVariate.selNode.push(node);
//									}
										flag = true;
										return false;
									}
								}
							}
						});
						if(flag){
							break;
						}
					}else{
						if(key == prokey && (caseFlag?((pro+"").toLowerCase().indexOf(value) > -1):(pro == value))){
//							if(index>-1){
//							}else{
								$(".n"+node.uuid +" image").attr("href", "images/img/"+type+"Sel.png");
								pageVariate.selNode.push(node);
//							}
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
	},
	/** 过滤图显示高亮 */
	filterGraphHighlight : function(vertexType,key,value) {
		tabsManager.nodeFilter(key,value,vertexType);
	},
	/** 点击属性图高亮 */
	clickLinkageGraph : function(key,value) {
		tabsManager.nodeFilter(key,value);
	},
	/** 填充HTML调用 */
	countMapOpt : function(node,countMap) {
		if(!graphOperation.judgeOWN(node)){//展开节点不加入统计
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
	/** 根据类型获取主key */
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

/* * ****************************tabs end************************************* */

/* * ****************************searchResult start************************************* */
/** 查询结果处理 */
var searchManager = {
	/**单个*/
	searchLinkage:function(data){
  		pageVariate.root = data;
    	pageVariate.personId = pageVariate.root.nodes[0].id;
    	pageVariate.uuid = pageVariate.root.nodes[0].uuid;
		
		if(graphOperation.findNodeIndexById(pageVariate.personId) == -1){
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
				graphOperation.expandNode(pageVariate.root.nodes,pageVariate.root.edges);
			}
			backendCacheManager.modifyIdCache(pageVariate.personId);
			//展示属性节点
			searchManager.showDetail_first();
   			
            //showTimeLline(pageVariate.personId,pageVariate.types);//时间轴联动 现已去掉
		}else{
			hintManager.showHint("该节点在页面中已存在！");
		}
		$(".hideDiv").hide(); 
  	},
    /**多个*/
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
  	/** 展示详细图 */
	showDetail : function() {
		var uuidArr = pageVariate.openMap.get(pageVariate.uuid);
		if(uuidArr){
			$.each(uuidArr,function(i,uuid){
				graphOperation.removeNode(uuid);
			});
		}
		graphOperation.updateGraphAndCache();
		pageVariate.openMap.remove(pageVariate.uuid);
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
				if(uuidArr && uuidArr.length >= detailData.nodes.length){
					var idArray = [];
					$.each(detailData.nodes,function(i,node){
						idArray.push(node.id);
					});
					backendCacheManager.deleteIdCache(idArray);
				}else{
					graphOperation.expandNode(detailData.nodes,detailData.edges);
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
	/** 查询条件节点显示-first */
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
				nodeIndex : graphOperation.findNodeIndex(pageVariate.uuid)
			},
       		async : false,
			success : function(data) {
				var detailData = eval(data);
				graphOperation.expandNode(detailData.nodes,detailData.edges);
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
	/** 根据人物ID查询事件 */
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
				nodeIndex : graphOperation.findNodeIndex(pageVariate.uuid),
				date : new Date().getTime()
			},
			success : function(data) {
				var eventData = eval(data);
				graphOperation.expandNode(eventData.nodes,eventData.edges);
			},
			error : function() {
				hintManager.showHint("未查询到人物相关的事件！");
			}
		});
	},
	/** 人物关联 */
	showConnect : function() {
		var len = pageVariate.selNode.length;
		var temparr = [];
		for(var i = 0;i < len; i++) {
			var tempnode = pageVariate.selNode[i];
			if(tempnode.type=="Person"){
				temparr.push(tempnode);
			}
	    }
		if(temparr.length != 2){
			return hintManager.showHint("关联只能选择两个人物节点！");
		}
		var node1 = temparr[0];
		var node2 = temparr[1];
		$.ajax({
			type : "post",
			dataType : "json",
			url : pageVariate.base+"query/getConnectByIds.action",
			data : {
				personId : node1.id,
				connectId : node2.id,
				maxIndex : nodes.length-1,
				nodeIndex : graphOperation.findNodeIndex(node1.uuid),
				connectIndex : graphOperation.findNodeIndex(node2.uuid),
				date : new Date().getTime()
			},
			dataType : "json",
			success : function(data) {
				var eventData = eval(data);
				graphOperation.expandNode(eventData.nodes,eventData.edges);
			},
			error : function() {
				hintManager.showHint("选中的人物节点没有关联关系！");
			}
		});
	},
	/** 展示关系网络图 */
	showRelative : function() {
		$.ajax({
			type : "post",
			dataType : "json",
			url : pageVariate.base+"query/getRelativeById.action",
			data : {
				personId : pageVariate.personId,
				maxIndex : nodes.length-1,
				nodeIndex : graphOperation.findNodeIndex(pageVariate.uuid),
				date : new Date().getTime()
			},
			dataType : "json",
			success : function(data) {
				var eventData = eval(data);
				graphOperation.expandNode(eventData.nodes,eventData.edges);
			},
			error : function() {
				hintManager.showHint("未查询到人物相关的关系网络！");
			}
		});
	},
	/** 根据查询的邮件展示图 */
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
					graphOperation.expandNode(eventData.nodes,eventData.edges);
				}
			},
			error : function() {
				hintManager.showHint("未查询到邮件及相关节点！");
			}
		});
	},
	/** 群组关系图 */
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

var backendCacheManager = {
	modifyIdCache : function(idsStr) {//新增idStr(id逗号分隔)
  		if(idsStr){
  			$.ajax({
  				type : "post",
  				async: false,
  				dataType : "json",
  				url : pageVariate.base+"query/modifyIdCache.action",
  				data : {
  					ids : idsStr,
  					date : new Date().getTime()
  				},
  				dataType : "json",
  				success : function() {
  				}
  			});
  		}
	},
	deleteIdCache : function(ids) {//id数组
		if(ids && ids.length > 0){
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
		}
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

/** ****************************searchResult end************************************* */