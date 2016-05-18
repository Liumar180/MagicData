/**
 * 生成柱状图操作
 */

/** ****************************bar start************************************* */
var isYearType = true;
var myChart;
var eCharts;
require.config({
	paths : {
		echarts : './scripts'
	}
});
require([ 'echarts', 'echarts/chart/bar', 'echarts/chart/line', ], DrawEChart // 异步加载的回调函数绘制图表
);
// 创建ECharts图表方法
function DrawEChart(ec) {
	eCharts = ec;
	myChart = eCharts.init(document.getElementById('main'));
	myChart.showLoading({
		text : "图表数据正在努力加载..."
	});
	// myChart.refresh();
	myChart.hideLoading();
	// 定义图表options
	var options = {
		title : {
			textStyle : {
				fontSize : 12,
				// fontWeight: 'bolder',
				color : '#FFFFFF'
			},
			text : 'TimeLine'
		},

		grid : {
			x : 25,
			y : 28,
			x2 : 25,
			y2 : 44,
			borderWidth : 0.1
		},

		tooltip : {
			trigger : 'axis',
			axisPointer : {
				type : 'line' // 默认为直线，可选为：'line' | 'shadow'
			},
		    showDelay : 500
		},

		legend : {
			textStyle : {
				fontSize : 12,
				// fontWeight: 'bolder',
				color : '#FFFFFF'
			},
			data : []

		},

		toolbox : {
			show : true,
			feature : {
				/*
				 * leftButton:{//自定义按钮 这里增加，leftbuttons可以随便取名字 show:true,//是否显示
				 * title:'上一条', //鼠标移动上去显示的文字 color : '#75B05D',
				 * icon:'images/echarts/testleft.png', //图标 option:{},
				 * onclick:function() {//点击事件 if(nodes.length <= 0){ return
				 * false; } maskManager.show(); var
				 * sd=myChart.component.xAxis.option.xAxis[0].data; var
				 * lastY=sd[0]; var pId=pageVariate.personId; var
				 * tps=pageVariate.types; var
				 * yearcha=timeLineVariate.yearTimeCha; if(lastY.length<6){ var
				 * dayVals=timeLineVariate.dayStartTime; dayVals =
				 * dayVals.replace(/-/g, '/'); var date = new Date(dayVals);
				 * date.setDate(date.getDate() - 1); var lastValueday =
				 * date.getFullYear() + '-'+ (parseInt(date.getMonth()) + 1) +
				 * '-'+ date.getDate();
				 * timeLineVariate.dayStartTime=lastValueday;
				 * lastY=lastValueday+" "+"00"+":"+"00"+":"+"00"; } if(pId){ var
				 * options = myChart.getOption(); myChart.clear(); $.ajax({ type :
				 * "post", async : false, //同步执行 url :
				 * pageVariate.base+"bar/queryLastYears.action", data :
				 * {"lastYear":lastY,"id":pId,"types":tps.toString(),"yearcha":yearcha},
				 * dataType : "json", //返回数据形式为json success : function(result) {
				 * if (result) { if(lastY.length>14){ options.title.text
				 * =result.title+" "+ lastValueday; }else{ options.title.text =
				 * result.title;} options.legend.data = result.legend;
				 * options.xAxis[0].data = result.xcategory;
				 * options.series=result.series; myChart.hideLoading();
				 * myChart.setOption(options); var
				 * lsdh=myChart.component.xAxis.option.xAxis[0].data; var
				 * frimt=lsdh[0]; var endmt=lsdh[lsdh.length-1];
				 * if(frimt!=""&&frimt!=null&&endmt!=""&&endmt!=null&&frimt<endmt&&frimt.length>6&&endmt.length>6){
				 * getLastDaytoLong(frimt,endmt); }
				 * if(frimt!=""&&frimt!=null&&endmt!=""&&endmt!=null&&frimt<endmt&&frimt.length<6&&endmt.length<6){
				 * var dayVals=timeLineVariate.dayStartTime;
				 * endmt=endmt.substring(0, 2); frimt=dayVals+" "+
				 * frimt+":"+"00"; endmt=dayVals+" "+ endmt+":"+"59"+":"+"59";
				 * getLastDaytoLong(frimt,endmt); } } }, error :
				 * function(errorMsg) { maskManager.hide();
				 * hintManager.showHint("服务器繁忙，请稍后重试！"); myChart.hideLoading(); }
				 * }); } } },
				 */
				mark : {
					show : false
				},
				dataView : {
					show : false,
					readOnly : true
				},
				magicType : {
					show : true,
					type : [ 'line', 'bar' ]
				},
				reCheckButton : {
					show : true,
					title : "反选",
					color : '#05FAEC',
					icon : 'images/echarts/reCheck.png',
					option : {},
					onclick : function() {
						filterGraph('checkInvert', 'timeLine');
					}
				},
				delButton : {
					show : true,
					title : "删除",
					color : '#FB1605',
					icon : 'images/echarts/delete.png',
					option : {},
					onclick : function() {
						deteleSelectNodes();
					}
				},
				resetButton : {// 自定义按钮 resetButton
					show : true,// 是否显示
					title : '还原', // 鼠标移动上去显示的文字
					color : '#B99E2A',
					icon : 'images/echarts/restore.png', // 图标
					onclick : function() {// 点击事件
						if (nodes.length <= 0) {
							return false;
						}
						// alert(timeLineVariate.dayStartTimeMonth);
						if (isYearType) {
							var start1 = timeLineVariate.stratYearTime;
							var endt1 = timeLineVariate.endYearTime;
							getLastDaytoLong(start1, endt1);
							/*
							 * var pId = pageVariate.personId; var tps =
							 * pageVariate.types;
							 */
							if (timeLineOperation.currentTimeLineId == "all") {
								timeLineOperation.currentTimeLineId = "";
								timeLineOperation.showTimeLineById("all", "all");
							} else {
								timeLineOperation.showTimeLineById(timeLineOperation.currentTimeLineId,
										graphOperation.findEventById(timeLineOperation.currentTimeLineId));
							}
							// showTimeLline(pId,tps);

						} else {
							var start1 = timeLineVariate.dayStartTimeMonth;
							var endt1 = timeLineVariate.dayEndTimeMonth;
							getLastDaytoLong(start1, endt1);
							/*
							 * var pId = pageVariate.personId; var tps =
							 * pageVariate.types;
							 */
							if (timeLineOperation.currentTimeLineId == "all") {
								timeLineOperation.currentTimeLineId = "";
								timeLineOperation.showTimeLineById("all", "all");
							} else {
								timeLineOperation.showTimeLineById(timeLineOperation.currentTimeLineId,
										graphOperation.findEventById(timeLineOperation.currentTimeLineId));
							}
							// showTimeLline(pId,tps);
						}
					}
				},
				saveAsImage : {
					show : false
				},

			/*
			 * rightButton:{ show:true, title:'下一条', color : '#75B05D',
			 * icon:'images/echarts/testright.png', option:{},
			 * onclick:function() {//点击事件 if(nodes.length <= 0){ return false; }
			 * var sd=myChart.component.xAxis.option.xAxis[0].data; var
			 * nextY=sd[sd.length-1]; var pId=pageVariate.personId; var
			 * tps=pageVariate.types; var yearcha=timeLineVariate.yearTimeCha;
			 * if(nextY.length<6){ var dayVals=timeLineVariate.dayStartTime;
			 * dayVals = dayVals.replace(/-/g, '/'); var date = new
			 * Date(dayVals); date.setDate(date.getDate() + 1); var nextValueday =
			 * date.getFullYear() + '-'+ (parseInt(date.getMonth()) + 1) + '-'+
			 * date.getDate(); timeLineVariate.dayStartTime=nextValueday;
			 * nextY=nextValueday+" "+"00"+":"+"00"+":"+"00"; } if(pId){ var
			 * options = myChart.getOption(); myChart.clear(); //通过Ajax获取下一条数据
			 * $.ajax({ type : "post", async : false, //同步执行 url :
			 * pageVariate.base+"bar/queryNextYears.action", data :
			 * {"nextYear":nextY,"id":pId,"types":tps.toString(),"yearcha":yearcha},
			 * dataType : "json", //返回数据形式为json success : function(result) { if
			 * (result) { if(nextY.length>14){ options.title.text =
			 * result.title+" "+nextValueday; }else{ options.title.text =
			 * result.title;} options.legend.data = result.legend;
			 * options.xAxis[0].data = result.xcategory;
			 * options.series=result.series; myChart.hideLoading();
			 * myChart.setOption(options); var
			 * nsdh=myChart.component.xAxis.option.xAxis[0].data; var
			 * frimt=nsdh[0]; var endmt=nsdh[nsdh.length-1];
			 * if(frimt!=""&&frimt!=null&&endmt!=""&&endmt!=null&&frimt<endmt&&frimt.length>6&&endmt.length>6){
			 * getLastDaytoLong(frimt,endmt); }
			 * if(frimt!=""&&frimt!=null&&endmt!=""&&endmt!=null&&frimt<endmt&&frimt.length<6&&endmt.length<6){
			 * var dayVals=timeLineVariate.dayStartTime;
			 * endmt=endmt.substring(0, 2); frimt=dayVals+" "+ frimt+":"+"00";
			 * endmt=dayVals+" "+ endmt+":"+"59"+":"+"59";
			 * getLastDaytoLong(frimt,endmt); } } }, error : function(errorMsg) {
			 * hintManager.showHint("服务器繁忙，请稍后重试！"); myChart.hideLoading(); }
			 * }); } } }
			 */
			}
		},

		calculable : true,
		dataZoom : {
			orient : "horizontal",// 水平显示
			show : true,// 显示滚动条
			showDetail : true,
			realtime : false,
			// start:1,
			// end:99,
			handleColor : 'rgba(70,120,180,0.5)',
			dataBackgroundColor : '#1B3648',
			// fillerColor:'#1B3648',
			handleSize : 1,
			height : 20
		},

		xAxis : [ {
			splitLine : {
				show : false
			},
			type : 'category',
			data : [ '' ],

			axisLabel : {
				// interval:'1', //设置X轴数据显示格式
				textStyle : {
					color : '#FFFFFF'
				}
			}
		// axisTick: { // 坐标轴小标记
		// show: true,
		// inside : false,
		// length :5,
		// lineStyle: {
		// color: '#FFFFFF',
		// width: 2
		// }
		// }
		} ],

		yAxis : [ {
			splitLine : {
				show : false
			},
			type : 'value',
			axisLabel : {
				formatter : '{value}',
				textStyle : {
					color : '#FFFFFF',
					fontSize : 5
				}
			}
		// splitArea: {show: true}
		} ],

		series : [ {
			type : 'bar',
			data : [ '' ]
		} ]
	};

	myChart.setOption(options); // 先把可选项注入myChart中
	myChart.initOption = options;
	var ecConfig = require('echarts/config');
	/* myChart中的点击交互事件 */
	myChart.on('click', function(param) {
		var mntimes = param.name;

		var sd = myChart.component.xAxis.option.xAxis[0].data;
		var frimt = sd[0];
		var endmt = sd[sd.length - 1];
		if (frimt.length < 8 && endmt.length < 8 && frimt <= endmt
				&& frimt.length > 6 && endmt.length > 6) {
			timeLineVariate.stratYearTime = frimt;
			timeLineVariate.endYearTime = endmt;
		} else if (frimt.length >= 8 && endmt.length >= 8 && frimt <= endmt
				&& frimt.length > 6 && endmt.length > 6) {
			timeLineVariate.dayStartTimeMonth = frimt;
			timeLineVariate.dayEndTimeMonth = endmt;
		}

		if (mntimes.length > 5 && mntimes != "" && mntimes != null
				&& mntimes.length < 8) {
			getChartDataMonth(mntimes);
		}
		if (mntimes != "" && mntimes != null && mntimes.length > 8) {
			getChartDataDays(mntimes);
		}
	});
	/* 时间轴拖动事件 */
	function eConsole(param) {
		// alert("拖动");
		var sd = myChart.component.xAxis.option.xAxis[0].data;
		var frimt = sd[0];
		var endmt = sd[sd.length - 1];
		if (frimt.length < 8 && endmt.length < 8 && frimt <= endmt
				&& frimt.length > 6 && endmt.length > 6) {
			timeLineVariate.stratYearTime = frimt;
			timeLineVariate.endYearTime = endmt;
		}
		if (frimt.length > 8 && endmt.length > 8) {
			timeLineVariate.dayStartTimeMonth = frimt;
			timeLineVariate.dayEndTimeMonth = endmt;
		}
		if (frimt != "" && frimt != null && endmt != "" && endmt != null
				&& frimt <= endmt && frimt.length > 6 && endmt.length > 6) {
			getLastDaytoLong(frimt, endmt);
		}
		if (frimt != "" && frimt != null && endmt != "" && endmt != null
				&& frimt <= endmt && frimt.length < 6 && endmt.length < 6) {
			var dayVals = timeLineVariate.dayStartTime;
			// frimt=frimt.substring(0, 2);
			endmt = endmt.substring(0, 2);
			frimt = dayVals + " " + frimt + ":" + "00";
			endmt = dayVals + " " + endmt + ":" + "59" + ":" + "59";
			getLastDaytoLong(frimt, endmt);
		}
		if (window.event) {
			if (event.button == 0) {
				// alert('请1秒后拖动！');
				// sleep(800);
				return true;
			}
		}
		document.oncontextmenu = eConsole;
	}
	myChart.on(ecConfig.EVENT.DATA_ZOOM, eConsole);
}

/*
 * if (window.event) { if (event.button == 0) { //alert('请1秒后拖动！'); sleep(5000);
 * //return true; } document.oncontextmenu= getLastDaytoLong; }
 */

function sleep(n) {
	var start = new Date().getTime();
	while (true)
		if (new Date().getTime() - start > n)
			break;
}
/* 时间值转化为毫秒数 */
function getLastDaytoLong(frimt, endmt) {
	// alert(frimt+"ii"+endmt);
	pageVariate.startTimeStr = frimt;// 时间轴所需
	pageVariate.endTimeStr = endmt;
	var starttime = "";
	var endtime = "";
	if (frimt && endmt && frimt.length < 8 && endmt.length < 8) {
		frimtYM = frimt + "-" + "01" + " " + "00" + ":" + "00" + ":" + "00";
		// starttime = (getDateForStringDate(frimtYM)).getTime();
		starttime = frimtYM;
		var year = endmt.split('-')[0];
		var month = endmt.split('-')[1];
		var n_year = year;
		var n_month = month++;
		if (month > 12) {
			n_month -= 12;
			n_year++;
		}
		var n_day = new Date(n_year, n_month, 1);// 获取本年本月第一天
		var n_endtm = new Date(n_day.getTime() - 1000 * 60 * 60 * 24).getDate();// 获取本月最后一天
		var month1 = endmt.split('-')[1];
		var n_Y_m_d = year + '-' + month1 + '-' + n_endtm;
		n_Y_m_d = n_Y_m_d + " " + "23" + ":" + "59" + ":" + "59";
		// endtime = (getDateForStringDate(n_Y_m_d)).getTime();
		endtime = n_Y_m_d;
		// alert(frimtYM+"-----"+n_Y_m_d);
	}
	if (frimt && endmt && frimt.length > 8 && endmt.length > 8
			&& frimt.length < 14 && endmt.length < 14) {
		frimtYMR = frimt + " " + "00" + ":" + "00" + ":" + "00";
		endmtYMR = endmt + " " + "23" + ":" + "59" + ":" + "59";
		// starttime = (getDateForStringDate(frimtYMR)).getTime();
		// endtime = (getDateForStringDate(endmtYMR)).getTime();
		starttime = frimtYMR;
		endtime = endmtYMR;
		// alert(frimtYMR+"-----"+endmtYMR);
	}
	if (frimt && endmt && frimt.length > 14 && endmt.length > 14) {
		starttime = (getDateForStringDate(frimt)).getTime();
		endtime = (getDateForStringDate(endmt)).getTime();
		starttime = frimt;
		endtime = endmt;
		// alert(frimt+"-----"+endmt);
	}
	// alert(starttime+"-------"+endtime);
	timeLineOperation.graphLinkage(starttime, endtime);
	//timeLineOperation.timeDragHighlight(starttime, endtime);

}

/* 传递默认时间值 */
function getDefaultDaytoLong(frimt, endmt) {
	// alert(frimt+"ii"+endmt);
	pageVariate.startTimeStr = frimt;// 时间轴所需
	pageVariate.endTimeStr = endmt;
	var starttime = 0;
	var endtime = 0;
	if (frimt != "" && frimt != null && endmt != "" && endmt != null
			&& frimt.length < 8 && endmt.length < 8) {
		frimtYM = frimt + "-" + "01" + " " + "00" + ":" + "00" + ":" + "00";
		starttime = (getDateForStringDate(frimtYM)).getTime();
		var year = endmt.split('-')[0];
		var month = endmt.split('-')[1];
		var n_year = year;
		var n_month = month++;
		if (month > 12) {
			n_month -= 12;
			n_year++;
		}
		var n_day = new Date(n_year, n_month, 1);// 获取本年本月第一天
		var n_endtm = new Date(n_day.getTime() - 1000 * 60 * 60 * 24).getDate();// 获取本月最后一天
		var month1 = endmt.split('-')[1];
		var n_Y_m_d = year + '-' + month1 + '-' + n_endtm;
		n_Y_m_d = n_Y_m_d + " " + "23" + ":" + "59" + ":" + "59";
		endtime = (getDateForStringDate(n_Y_m_d)).getTime();
		// alert(frimtYM+"-----"+n_Y_m_d);
	}
	pageVariate.startTime = starttime;
	pageVariate.endTime = endtime;
}

/* 点击按月加载数据 */
function getChartDataMonth(mntimes) {
	isYearType = true;
	var pId = pageVariate.personId;
	var tps = pageVariate.types;
	if (mntimes.length < 8 && pId) {
		var options = myChart.getOption();
		myChart.clear();
		$.ajax({
			type : "post",
			async : false, // 同步执行
			url : pageVariate.base + "bar/queryMonth.action",
			data : {
				"month" : mntimes,
				"id" : pId,
				"types" : tps.toString()
			},
			dataType : "json", // 返回数据形式为json
			success : function(result) {
				if (result) {
					options.title.text = result.title;
					options.legend.data = result.legend;
					options.xAxis[0].data = result.xcategory;
					options.series = result.series;
					myChart.hideLoading();
					myChart.setOption(options);
					var nsdh = myChart.component.xAxis.option.xAxis[0].data;
					var frimt = nsdh[0];
					var endmt = nsdh[nsdh.length - 1];
					timeLineVariate.dayStartTimeMonth = frimt;
					timeLineVariate.dayEndTimeMonth = endmt;
					if (frimt < endmt) {
						getLastDaytoLong(frimt, endmt);
					}
				}
			},
			error : function(errorMsg) {
				hintManager.showHint("服务器繁忙，请稍后重试！");
				myChart.hideLoading();
			}
		});
	}
}

/* 点击按天加载数据 */
function getChartDataDays(mntimes) {
	isYearType = false;
	var pId = pageVariate.personId;
	var tps = pageVariate.types;
	timeLineVariate.dayStartTime = mntimes;

	var eventTypes = [];
	var eventTimes = [];
	var currentNodes = graphOperation.findEventById(pId);
	$.each(currentNodes, function(i, node) {
		var type = node.type.trim();
		if (typeof (type) == "string" && type.endWith("Event")) {
			if (eventTypes.indexOf(type) == -1) {
				eventTypes.push(type);
			}
			var eventNodes = node.subList;
			if (eventNodes == undefined || eventNodes == null) {
				eventTimes.push(type + "=" + node.time.trim());
			} else {
				eventNodes.forEach(function(subnode, index) {
					eventTimes.push(type + "=" + subnode.time.trim());
				});
			}
		}
	});

	if (mntimes.length > 8 && pId) {
		var options = myChart.getOption();
		myChart.clear();
		$.ajax({
			type : "post",
			async : false, // 同步执行
			url : pageVariate.base + "bar/queryDays.action",
			data : {
				"days" : mntimes,
				"types" : eventTypes,
				"eventTimes" : eventTimes
			/*
			 * "id" : pId, "types" : tps.toString()
			 */
			},
			dataType : "json", // 返回数据形式为json
			traditional : true,
			success : function(result) {
				if (result) {
					if (mntimes.length > 8) {
						options.title.text = result.title + "  " + mntimes;
					} else {
						options.title.text = result.title;
					}
					options.legend.data = result.legend;
					options.xAxis[0].data = result.xcategory;
					options.series = result.series;
					options.dataZoom.start = 0;
					options.dataZoom.end = 100;
					myChart.hideLoading();
					myChart.setOption(options);
					 
					var nsdh = myChart.component.xAxis.option.xAxis[0].data;
					var frimt = nsdh[0];
					var endmt = nsdh[nsdh.length - 1];
					frimt = mntimes + " " + "00" + ":" + "00" + ":" + "00";
					endmt = mntimes + " " + "23" + ":" + "59" + ":" + "59";
					if (frimt < endmt) {
						getLastDaytoLong(frimt, endmt);
					}
				}
			},
			error : function(errorMsg) {
				hintManager.showHint("服务器繁忙，请稍后重试！");
				myChart.hideLoading();
			}
		});
	}
}

/**
 * 浏览器兼容new Date(str)
 */
function getDateForStringDate(strDate) {
	var s = strDate.split(" ");
	var s1 = s[0].split("-");
	var s2 = s[1].split(":");
	return new Date(s1[0], s1[1] - 1, s1[2], s2[0], s2[1], s2[2]);
}

/**
 * 根据节点信息显示时间轴
 * 
 * @param eventTypes
 * @param eventTimes
 * @param dragTime
 */
function showTimeLine(eventTypes, eventTimes, dragTime) {
	if (eventTypes.length > 0 && eventTimes.length > 0) {
		var chartOptions = myChart.getOption();
		if (chartOptions.title == undefined) {
			myChart.setOption(myChart.initOption);
			chartOptions = myChart.getOption();
		}
		myChart.clear();
		try {
			typeStr = types.toString();
		} catch (e) {
			typeStr = "";
		}
		pageVariate.types = eventTypes;
		$
				.ajax({
					type : "post",
					async : false, // 同步执行
					url : pageVariate.base + "bar/queryAllNodes.action",
					data : {
						"types" : eventTypes,
						"eventTimes" : eventTimes,
						"dragTime" : dragTime
					},
					dataType : "json", // 返回数据形式为json
					traditional : true,
					success : function(result) {
						if (result) {
							timeLineVariate.yearTimeCha = result.dateYear;
							chartOptions.title.text = result.title;
							chartOptions.legend.data = result.legend;
							chartOptions.xAxis[0].data = result.xcategory;
							if (pageVariate.initFlag) {
								pageVariate.initStartTime = result.xcategory[0];
								pageVariate.initEndTime = result.xcategory[result.xcategory.length - 1];
								pageVariate.initFlag = false;
							}
							chartOptions.series = result.series;
							if (null != dragTime && dragTime != undefined) {
								chartOptions.dataZoom.start = result.dragList[0];
								chartOptions.dataZoom.end = result.dragList[1];
								timeLineOperation.timeDragHighlight(dragTime[0], dragTime[1]);
							} else {
								chartOptions.dataZoom.start = 0;
								chartOptions.dataZoom.end = 100;
							}
							myChart.hideLoading();
							myChart.setOption(chartOptions);
							if (timeLineVariate.firstTimeFormat) {
								timeLineVariate.firstTimeFormat = false;
								var sd = myChart.component.xAxis.option.xAxis[0].data;
								var frimt = sd[0];
								var endmt = sd[sd.length - 1];
								if (frimt.length < 8 && endmt.length < 8
										&& frimt < endmt && frimt.length > 5
										&& endmt.length > 5) {
									timeLineVariate.stratYearTime = frimt;
									timeLineVariate.endYearTime = endmt;
									getLastDaytoLong(frimt, endmt);
								}
							}
						}
					},
					error : function(errorMsg) {
						hintManager.showHint("服务器繁忙，请稍后重试！");
						myChart.hideLoading();
					}
				});
	}
}
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