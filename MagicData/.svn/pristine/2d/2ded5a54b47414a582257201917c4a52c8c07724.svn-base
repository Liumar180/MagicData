function getRootPath(){
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht+projectName);
}
function getQQgroup(nodes,edges,qqcount){
//	 nodes = [ { num: "123456" , "image" : "/scripts/img/QQqun.png",fixed:"" }, { num: "234234", "image" : "/scripts/img/qq.png" ,fixed:""},
//	                { num: "31231", "image" : "/scripts/img/qq.png",fixed:""}, { num: "1533451", "image" : "/scripts/img/qq.png",fixed:""},
//	                { num: "12312312", "image" : "/scripts/img/qq.png",fixed:""}, { num: "23423225", "image" : "/scripts/img/qq.png" ,fixed:""},
//	                { num: "1231231", "image" : "/scripts/img/QQqun.png" ,fixed:""},{ num: "1231231222", "image" : "/scripts/img/qq.png",fixed:""},
//	                { num: "1233312312", "image" : "/scripts/img/qq.png",fixed:""},{ num: "1231222312", "image" : "/scripts/img/qq.png",fixed:""}];
//	   
//	   edges = [ { source : 0 , target: 1 , "relation":"qq" } , { source : 0 , target: 2  , "relation":"groupOwner"} ,
//	                 { source : 0 , target: 3  , "relation":"qq"} , { source : 0 , target: 4  , "relation":"qq"} ,
//	                 { source : 0 , target: 5 , "relation":"qq" } , { source : 4 , target: 6 , "relation":"qq" } , { source : 6 , target: 7 , "relation":"groupOwner" }
//	                 , { source : 6 , target: 8 , "relation":"qq" }, { source : 6 , target: 9 , "relation":"qq" }];
		var width = $(".displayQQDiv"+qqcount).innerWidth() - 2;
		var height = $(".displayQQDiv"+qqcount).innerHeight() - 2;
		var img_w = 50;
		var img_h = 50;
		var	graphZoomqq = d3.behavior.zoom().scaleExtent([1, 5]).on("zoom", zoomedqq).on("zoomend",zoomendqq);	
		var svgqq = d3.select('.displayQQDiv'+qqcount).append('svg').attr('width', width).attr('height', height);
		svgqq.call(graphZoomqq);
		var svg_gqq=svgqq.append('g');
		svg_gqq.call(graphZoomqq);
		var force = d3.layout.force()
							.nodes(nodes)
							.links(edges)
							.size([width,height])
							.linkDistance(150)
							.charge(-600)
							.start();
		var edges_line = svg_gqq.append("g").call(graphZoomqq).selectAll("line")
								.data(edges)
								.enter()
								.append("line")
								.style("stroke",function(d){
									if(d.relation=="groupOwner"){
										return "#f27800";
									}else{return "#004F89";}
									
								})
								.style("stroke-width",2);
								
//			var edges_text = svg.selectAll(".linetext")
//								.data(edges)
//								.enter()
//								.append("text")
//								.attr("class","linetext")
//								.text(function(d){
//									return d.relation;
//								});
			//提示框tip
			var tipqq = d3.tip()
						.attr("class","d3-tip")
						.html(function(d){
							var type = d.type;
							if("group" == type){
								var html = "群号:"+(d.num?d.num:"")+"<br/>";
								html += "群名称:"+(d.groupname?d.groupname:"")+"<br/>";
								html += "创建人:"+(d.ownerqq?d.ownerqq:"")+"<br/>";
								html += "创建时间:"+(d.createtime?d.createtime:"")+"<br/>";
								html += "群描述:"+(d.groupdesc?d.groupdesc:"")+"<br/>";
								return html;
							}else{
								return "QQ:"+(d.num?d.num:"")+"<br/>昵称:"+(d.nickname?d.nickname:"");
							}
						});
			svg_gqq.call(tipqq);
			
					//判断鼠标双击
		var isdbqq;
		//固定顶点
		var drag = force.drag().on("dragstart", function(d, i) {
			d3.event.sourceEvent.stopPropagation();
			d.fixed = true; //拖拽开始后设定被拖拽对象为固定
	 }).on("drag",function(d,i){
		 	d.isdrag2=d.x+d.y;
	 });
		var nodes_img =svg_gqq.append("g").call(graphZoomqq).selectAll("image")
								.data(nodes)
								.enter()
								.append("image")
								.attr("width",img_w)
								.attr("height",img_h)
								.attr("xlink:href",function(d){
									return getRootPath()+d.image;
								}) .call(force.drag).on('click', function(d) {
									 isdbqq=false;
									 window.setTimeout(cc, 200);
								  function cc(){	 
									if(isdbqq!=false)return;
									if(d.isdrag!=d.isdrag2){d.isdrag=d.isdrag2;return;};
									clickNode(d);
								    }
								}) .on("dblclick", function(d, i) {
									 isdbqq=true;
									d3.event.stopPropagation();
									d.fixed = false;
								})
								.on("mouseover",tipqq.show)
								  .on("mouseout",tipqq.hide) ;
			
			var text_dx_qq = -20;
			var text_dy_qq = 20;
			
			var nodes_text = svg_gqq.append("g").call(graphZoomqq).selectAll(".nodetext")
								.data(nodes)
								.enter()
								.append("text")
								.attr("class","nodetext")
								.attr("dx",text_dx_qq)
								.attr("dy",text_dy_qq)
								.text(function(d){
									return d.num;
								});
			
				
			force.on("tick", function(){
				
				//更新连接线的位置
				 edges_line.attr("x1",function(d){ return subtractqq(d,"x1"); });
				 edges_line.attr("y1",function(d){ return subtractqq(d,"y1");});
				 edges_line.attr("x2",function(d){ return subtractqq(d,"x2"); });
				 edges_line.attr("y2",function(d){return subtractqq(d,"y2"); });
				 
				 //更新连接线上文字的位置
//				 edges_text.attr("x",function(d){ return (d.source.x + d.target.x) / 2 ; });
//				 edges_text.attr("y",function(d){ return (d.source.y + d.target.y) / 2 ; });
				 
				 
				 //更新结点图片和文字
				 nodes_img.attr("x",function(d){ return d.x - img_w/2; });
				 nodes_img.attr("y",function(d){ return d.y - img_h/2; });
				 
				 nodes_text.attr("x",function(d){ return d.x; });
				 nodes_text.attr("y",function(d){ return d.y + img_w/2; });
		
			});
			function subtractqq(d,loc){
				var x1 = d.source.x,
				y1 = d.source.y;
				x2 = d.target.x,
				y2 = d.target.y;

				var l=Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
				var l1=25;//缩短的长度
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
		
		/******************************全屏加缩放************************************* */	
			//缩放轴	
			var cirscaleqq=0;
			var iszoomlineqq=true;
			var zoom_x_qq=30;
			var zoom_y_qq=80;
			var add_r_qq=10;
			var drag_r_qq=5;
			var scaleqq = 1.1;
		    var bar_len_qq =92;
			var zoom_line_qq=d3.select('.zoomQQline'+qqcount).append('svg').attr("width","50px").attr("height","210px").append("g").attr("cursor","pointer");  
			var addqq=zoom_line_qq.append("g").attr("class","addqq");
			var cutqq=zoom_line_qq.append("g").attr("class","cutqq");
		

			//加
			addqq.append("circle")
			   .attr("cx",zoom_x_qq)
			   .attr("cy",zoom_y_qq)
			   .attr("fill","#f00")
			   .attr("fill-opacity","0.1")
			   .attr("r",add_r_qq)
			   .attr("stroke","#fff");
			addqq.append("line")
			   .attr("x1",zoom_x_qq-5)
			   .attr("y1",zoom_y_qq)
			   .attr("x2",zoom_x_qq+5)
			   .attr("y2",zoom_y_qq)
			   .attr("stroke","#fff")
			   .attr("fill","none");

			addqq.append("line")
			   .attr("x1",zoom_x_qq)
			   .attr("y1",zoom_y_qq+5)
			   .attr("x2",zoom_x_qq)
			   .attr("y2",zoom_y_qq-5)
			   .attr("stroke","#fff")
			   .attr("fill","none");
			//减
			cutqq.append("circle")
			   .attr("cx",zoom_x_qq)
			   .attr("cy",(zoom_y_qq+bar_len_qq+add_r_qq+add_r_qq))
			    .attr("stroke","#fff")
			   .attr("fill-opacity","0.1")
			   .attr("fill","#061431")
			   .attr("r",add_r_qq);
			cutqq.append("line")
			   .attr("x1",zoom_x_qq-5)
			   .attr("y1",(zoom_y_qq+bar_len_qq+add_r_qq+add_r_qq))
			   .attr("x2",zoom_x_qq+5)
			   .attr("y2",(zoom_y_qq+bar_len_qq+add_r_qq+add_r_qq))
			   .attr("stroke","#fff");
			
			//轴
			zoom_line_qq.append("g").call(graphZoomqq);
			zoom_line_qq.append("line")
			   .attr("x1",zoom_x_qq)
			   .attr("y1",(zoom_y_qq+add_r_qq))
			   .attr("x2",zoom_x_qq)
			   .attr("y2",(zoom_y_qq+add_r_qq+bar_len_qq))
			    .attr("stroke","#fff");
		

			//拖动
			var cirqq=zoom_line_qq.append("circle")
			   .attr("cx",zoom_x_qq)
			   .attr("cy",(zoom_y_qq+add_r_qq+bar_len_qq-drag_r_qq))
			   .attr("fill","#009cFF")
			   .attr("r",6);
			cirqq.append("g").call(graphZoomqq);
			var dragmoveqq=function dragged() {
				d3.event.sourceEvent.stopPropagation(); 
				  var dy = d3.event.y; 
		          var diffY = dy ;  //+currentY - 30 ; 
		          if(diffY  > (zoom_y_qq+add_r_qq+bar_len_qq-drag_r_qq)){//bar_len_qq + border_len+ margin 
		              diffY = zoom_y_qq+add_r_qq+bar_len_qq-drag_r_qq;
		              return ;
		          }
		          if(diffY <(add_r_qq+zoom_y_qq+drag_r_qq)){ //border_len+ margin 
		              diffY =add_r_qq+zoom_y_qq+drag_r_qq;
		              return ;
		          } 
		          
		          scaleqq =((zoom_y_qq+add_r_qq+bar_len_qq-drag_r_qq-diffY)/(bar_len_qq-drag_r_qq-drag_r_qq))*4+1; 
		          cirqq.attr("cy",diffY);
		        /*svg.attr("transform","scale(" + scale + ")" );*/
				};
			var dragendqq=function dragendqq() { 
				iszoomlineqq=false;
				graphZoomqq.custom_scale(scaleqq,[width/2,height/2]);
				iszoomlineqq=true;
				};
			cirqq.call(d3.behavior.drag().on("drag",dragmoveqq).on("dragend",dragendqq));
			addqq.on("click",function (){graphZoomqq.custom_scale((graphZoomqq.scale()+1),[width/2,height/2]);});
			cutqq.on("click",function (){graphZoomqq.custom_scale((graphZoomqq.scale()-1),[width/2,height/2]);});
			function zoomendqq() {
				if(iszoomlineqq){
					var cy=zoom_y_qq+add_r_qq+bar_len_qq-drag_r_qq-(bar_len_qq*(cirscaleqq)/4);

			          if(cy  > (zoom_y_qq+add_r_qq+bar_len_qq-drag_r_qq)){//bar_len_qq + border_len+ margin 
			        	  cy = zoom_y_qq+add_r_qq+bar_len_qq-drag_r_qq;
			
			          }
			          if(cy <(add_r_qq+zoom_y_qq+drag_r_qq)){ //border_len+ margin 
			        	  cy =add_r_qq+zoom_y_qq+drag_r_qq;
			            
			          } 
					cirqq.attr("cy",cy);
				};
				
			}
	/******************************全屏加缩放************************************* */	
			var scrollFunc=function(e){ 
				e=e || window.event; 
				if(e.wheelDelta){//IE/Opera/Chrome 
					if(e.wheelDelta==120)  {
					//向上滚动事件 
						graphZoomqq.custom_scale((graphZoomqq.scale()+1),[width/2,height/2]);
					}else { 
						//向下滚动事件<br> 
						graphZoomqq.custom_scale((graphZoomqq.scale()-1),[width/2,height/2]);
					} 
				}else if(e.detail){ 
					//Firefox 
					if(e.detail==-3) { 
					//向上滚动事件<br> 
						graphZoomqq.custom_scale((graphZoomqq.scale()+1),[width/2,height/2]);
					}else { 
					//向下滚动事件<br> 
						graphZoomqq.custom_scale((graphZoomqq.scale()-1),[width/2,height/2]);
					} 
				} 
				}; 
				if(document.addEventListener){ 
				//adding the event listerner for Mozilla 
				document.addEventListener("DOMMouseScroll" ,scrollFunc, false); 
				} 
				//IE/Opera/Chrome 
				window.onmousewheel=document.onmousewheel=scrollFunc;
			
			function zoomedqq() {
				cirscaleqq=d3.event.scale-1;
				svg_gqq.attr("transform","translate(" + d3.event.translate + ")scale(" + d3.event.scale + ")");
			}	
			
}		
