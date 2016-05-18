// JavaScript Document
$(function(){
	$(".mainCon-top .iconSpan").click(function(){
   		$(this).parent(".mainCon-top").siblings("#imgBox").toggle();
	});
	$(".mainCon-top2 .iconSpan2").click(function(){
   		$(this).parent(".mainCon-top2").siblings(".tableDiv").toggle();
	});
	/*表格的click事件*/
	$(".tableDiv .table tr").click(function(){
		$(this).addClass("setontr").siblings("tr").removeClass("setontr");
	});
	/*点击图片出现的弹出层*/
	$(".thirdleft img").click(function(){
		$('#addmodalmyModal').modal('show')
	});
	/*点击图片出现的弹出层-表格的选中状态*/
	$(".commTable .table tr").click(function(){
  		$(this).addClass("setontr1").siblings("tr").removeClass("setontr1");
  	});
	/*4月25日删除数据源弹出层*/
	$(".delspan").click(function(){
		$('#deldataSource').modal('show')
	});
	$(".headerBox .navMenu li").click(function(){
		var _this = this;
		var extend = $(_this).attr("extend");
		$(".headerBox .navMenu li").removeClass("li-seton");
		$(_this).addClass("li-seton");
		$(".level").css("display", "none");
		$("."+extend+".level").css("display", "block");
	});
})



// 1、saveTask.action加任务描述taskDesc