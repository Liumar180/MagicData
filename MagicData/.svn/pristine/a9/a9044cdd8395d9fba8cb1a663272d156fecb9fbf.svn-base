// JavaScript Document
$(document).ready(function(e) {
	  //tab
   $(".tabBox li a").click(function(){
	  $(this).addClass("setOn").parents('li').siblings().children("a").removeClass("setOn");
	  var tabIndex = $(".tabBox li a").index(this); 
	  $(".tabCon").eq(tabIndex).show().siblings(".tabCon").hide();
	});
	//下拉打开，关闭
	$(".clsHead").click(function(){//图片单击事件
	  if($(".clsContent").is(":visible")){//如果内容可见
		$(".clsHead span img")
		.attr("src","images/img/2.png")//改变图片	
		$(".clsContent").css("display","none");//隐藏内容
	  }else{
		$(".clsHead span img")
		.attr("src","images/img/1.png");
		$(".clsContent").css("display","block");
	   }
	});
	//实体属性--下拉打开，关闭
	$(".clsHead1").click(function(){//图片单击事件
	  if($(".clsContent1").is(":visible")){//如果内容可见
		$(".clsHead1 span img")
		.attr("src","images/img/2.png")//改变图片	
		$(".clsContent1").css("display","none");//隐藏内容
	  }else{
		$(".clsHead1 span img")
		.attr("src","images/img/1.png");
		$(".clsContent1").css("display","block");
	   }
	});
	//7月16日加效果
	$(".iIcon").live("click",function(){
		if($(this).siblings(".listDiv").is(":hidden")){//如果内容可见
			$(this).find("img").attr("src","images/img/down1.png");
			$(this).siblings(".listDiv").css("display","block");
	  }else{
			
			$(this).find("img").attr("src","images/img/top.png")//改变图片	
			$(this).siblings(".listDiv").css("display","none");//隐藏内容
	   }
	});
	//过滤器下拉打开，关闭
	$(".clsHead_filter").click(function(){//图片单击事件
	  if($(".clsContent_filter").is(":visible")){//如果内容可见
		$(".clsHead_filter span img")
		.attr("src","images/img/2.png")//改变图片	
		$(".clsContent_filter").css("display","none");//隐藏内容
	  }else{
		$(".clsHead_filter span img")
		.attr("src","images/img/1.png");
		$(".clsContent_filter").css("display","block");
	   }
	});
	//过滤器--下拉打开，关闭
	$(".clsHead_filter_timeline").click(function(){//图片单击事件
	  if($(".clsContent_filter_timeline").is(":visible")){//如果内容可见
		$(".clsHead_filter_timeline span img")
		.attr("src","images/img/2.png")//改变图片	
		$(".clsContent_filter_timeline").css("display","none");//隐藏内容
	  }else{
		$(".clsHead_filter_timeline span img")
		.attr("src","images/img/1.png");
		$(".clsContent_filter_timeline").css("display","block");
	   }
	});
});