$(function() {
	/*
	 * @author lifusheng
	 */
	                   var click=0;
//点击高级查询触发的事件
$("#advancedsearch").click(function() {
	                    click=click+1;
						var queryableMap = pageVariate.queryableMap;
						var aliasAll =pageVariate.aliasAll;
						var m = "";
						if(click==1){
						    	for ( var key in queryableMap) {
									// 动态增加下拉框
									     if(key=="Person"){
									         $("#queryable").append("<option value='" + key + "' selected='selected'>" +pageVariate.aliasAll.Person[key]+ "</option>");
											 for (var i = 0; i < queryableMap.Person.length; i++) {
												var personquerable = queryableMap.Person[i];
												var personalias = aliasAll.Person[personquerable];
												m += " <li class='gjsearchLi'>";
												m += "<span>" + personalias+ "</span>";
												var realid = "Person"+ i;
												m += "<input type='text'  id='"+ realid + "'  />";
												m += "</li>";
											 }
										        $("#point").html(m);
									     }
										if(key!="Person"){
										      $("#queryable").append("<option value='" + key + "'>" + pageVariate.aliasAll[key][key]+ "</option>");
										}
							   }
						}
						$("#queryable").bind("change",function() {
											m = "";
											var selectvalue = $("#queryable  option:selected").attr("value");
											// 页面添加
											for (var i = 0; i < queryableMap[selectvalue].length; i++) {
												var querable = queryableMap[selectvalue][i];
												var alias = aliasAll[selectvalue][querable];
												if (querable == "arrivedate"| querable == "departuredate") {
													m += " <li class='gjsearchLi'>";
													m += "<span>" + alias+ "</span>";
													var realid = selectvalue+ i;
													m += "<input type='text'  id='"+ realid + "'  />";
													m += "</li>";
												}
												else {
													m += " <li class='gjsearchLi'>";
													m += "<span>" + alias+ "</span>";
													var realid = selectvalue+ i;
													m += "<input type='text'  id='"+ realid + "'  />";
													m += "</li>";
												}

											}
											$("#point").html(m);
	                     })
						 $("#advancesearch").show();
});
 //点击查询触发的事件      
 $("#query").click(function() {
	                        var queryableMap = pageVariate.queryableMap;
		                    var aliasAll =pageVariate.aliasAll;
		                    var selectvalue = $("#queryable  option:selected").attr("value");
              	             //构建查询的json字符串                                      
		                    var json = "{";
		                    json += "\"";
		                    json += selectvalue;
		                    json += "\"";
		                    json += ":{";
		                   for (var i = 0; i < queryableMap[selectvalue].length; i++) {
			                      var id = selectvalue + i;
			                      var m = $("#" + id).val();
  			                          m=trim(m);
			                      if(m!=""){
			                          json += "\"";
								  json += queryableMap[selectvalue][i];
								  json += "\"";
								  json += ":";
								  json += "\"";
								  json += m;
								  json += "\"" + ",";
											          }
			                     if (i == queryableMap[selectvalue].length - 1) {
				                     json = json.substring(0, json.length - 1);
				                     json += "}}";
			                                                                     }
			               }
		                    //当用户什么都不输入的时候控制不让用户提交
		                    var sum = 0;
		                    $("input[type=text]").each(function(i){
		                            var text = $(this).val();
		                            if(text!=""){
		                                  sum += 1;
		                            }
		                    });
		                    if(sum==0){
		                    alert("请输入查询条件");
		                    }
		              		//向后台传递json数据进行查询
	                       if(sum!=0){
	                    	   $.ajax({  
		               				type : "post",  
		               			    url : pageVariate.base + "query/findObjByMoreProperty.action", 
		               	 	        data:{"searchJson":json},
		               				dataType: "json",
		               				success : function(data){
		               					alert(121);
		               					var click=pageVariate.click;
		            	        	   	click=click+1; 
		               					maskManager.hide();
		               					pageVariate.tempData = eval(data);
		               					console.log(pageVariate.tempData);
		               		        	var nodeSize = pageVariate.tempData.nodes.length;
		               		            if(nodeSize == 1){
		               		            	searchManager.searchLinkage(pageVariate.tempData);
		               		            	$(".easyui-tabs").tabs('select', 0);
		               		            }else if(nodeSize > 1){
		               		            	searchshow(pageVariate.tempData,click);
		               		            	$("#tableshow").show();
		               		            	searchManager.moreNodeHandle(pageVariate.tempData);
		               		            	$(".easyui-tabs").tabs('select', 0);
		               		            }else{
		               		            	$("#tableshow").show();
		               		             	hintManager.showHint("未查询到数据。");
		               		            }
		               				},
		               		        error:function(){
		               		        	alert(111);
		               		         	maskManager.hide();
		               		        	hintManager.showHint("未查询到数据。");
		               		        }
		               			});
	                        }   
	                       
	                       //弹出查询结果展示页面
/*	                     if(sum!=0){  
//	                       searchshow("");
	                       $("#tableshow").show();
	                     }*/
});
	//清空
$("#clear").click(function() {
	$("input").val("");
	                          });
	
                                          })
//删除左右两端的空格
function trim(str){ 
　　     return str.replace(/(^\s*)|(\s*$)/g, "");
　　 }

function searchshow(data,click){
	//lifusheng5月12日增加的动态查询内容***********
	//**************获取表头*************//	
	 var aliasAll =pageVariate.aliasAll;
//	 var connect="<h3>查询结果显示</h3>";
	 var connect="";
	 var tdconnect = "";
	 var thcollection="";
	 var tdcollection="";
//	 connect+= "<b class='closeB' onclick=\"$('.searchtccBox').hide()\"></b>";
	 var type;
	 var td;
	 var uuid;
	 var count=0;
	 $.each(aliasAll, function(index, element) {
	    $.each(data.nodes, function(queryindex, value) {
	    	if(value.type!=	type){
			   	type=value.type;
			   	if(index==type){
				  	connect+= " <ul class='clearfix' > ";
		        	connect+= "<li class='gjsearchLi'>" ;
		        	connect+= "<span>节点名称：" +element[index]+ "</span>" ;
		        	connect+= "</li>" ;
		        	connect+= "</ul>" ;
		        	connect+= "<table class=''"+ index + "''  width='100%'  border='0' cellpadding='0' cellspacing='2'>" ;
		        	connect+= "<thead>" ;
		        	connect+= "<tr>";
		            $.each(element, function(i, e) {
		            	if(i!=index){	
		            		connect+= "<th>" + e + "</th>";
				        }
				    })
		            connect+= "</tr>";
		            connect+= "</thead>";
		            connect+= "<tbody id='"+ index + "'>";
			        connect+= "</tbody>";
		            connect+= "</table>";   
			   }
	    	}
		 	
		})
	 })
	
   $("#tableshowall").html(connect);
	//对应填数据  ******************
   $.each(data.nodes, function(queryindex, value) {
	  	   type=value.type;
   	   if(type != "_default" && type != "QQ"){
	   	   var collection=aliasAll[type];
	       thcollection += "{";
	       for(var key in  collection){  
			  if(type!=key){
				 thcollection += "\"";
				 thcollection += key;
				 thcollection += "\"";
				 thcollection += ":";
				 thcollection += "\"";
				 thcollection += key;
				 thcollection += "\"";
				 thcollection += ",";
			  }
	      }  
	      thcollection = thcollection.substring(0, thcollection.length - 1);
	      thcollection += "}";
	      var thcollectionjson = $.parseJSON(thcollection); 
    	  uuid=value.uuid;
    	  tdcollection = "<tr  onclick=clickline(this,'"+type+"','"+uuid+"');>";
	      for(var key1 in thcollectionjson){
	    	  if(value[key1]!=undefined){
	    		  tdcollection += "<td>";
	    		  tdcollection += value[key1];
	    	      tdcollection += "</td>";
	    	   }
	    	  if(value[key1]==undefined){
	    		  tdcollection += "<td>";
	    		  tdcollection += "";
	       		  tdcollection += "</td>";
	       	  }
	      }
       	tdcollection += "</tr>"
       	$("#"+type).append(tdcollection);
       	thcollection="";

     }
   })
 //对应填数据  ******************           
$("#tableshow").show();
 //lifusheng5月12日增加的动态查询内容************************	
}

function clickline(obj,type,uuid){
	 var singlelinedata;
	 var nodes = pageVariate.tempData.nodes;
	 for(var i=nodes.length-1;i>-1;i--){
			var node = nodes[i];
			if(uuid != node.uuid){
				nodes.remove(node);
			}
	}
	searchManager.searchLinkage(pageVariate.tempData);
	$(".easyui-tabs").tabs('select', 0);
}

