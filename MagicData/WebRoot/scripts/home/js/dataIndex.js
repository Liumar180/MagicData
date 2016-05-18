$(function() {
	$(".navIcon .icon").click(function(){
		if($(this).siblings(".navList").is(":hidden")){//如果内容可见
			$(this).find("img").attr("src","styles/home/img/navImg2.png");
			$(this).siblings(".navList").css("display","block");
		}else{				
			$(this).find("img").attr("src","styles/home/img/navImg1.png")//改变图片	
			$(this).siblings(".navList").css("display","none");//隐藏内容
		}
	})
	//点击其他地方该下拉内容隐藏
	$(document).bind("click",function(e){ 	
		var target = $(e.target); 	
		if(target.parents(".navIcon").length == 0){ 
			$(".navIcon .icon").find("img").attr("src","styles/home/img/navImg1.png")//改变图片	
			$(".navList").hide(); 			
		} 	
	})
	$(".navList li").click(function(){
		$(this).parents(".navList").hide();
		$(".navIcon .icon").find("img").attr("src","styles/home/img/navImg1.png")//改变图片	
	})
})