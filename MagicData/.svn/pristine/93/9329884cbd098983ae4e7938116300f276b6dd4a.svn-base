$(function(){
//分页的显示与隐藏
	$("#xifenye").click(function(){
		$("#uljia").empty();
		$("#xab").toggle();
		//显示出的一共多少页
		var uljia=$("#uljia");
		var page=parseInt($("#xiye").html());//获取当前的页数
		var pages=parseInt($("#mo").html());//获取当前的总页数
		for(var i=1;i<=pages;i++)
		{
			var H="<li  onclick='fl("+i+","+pages+")'>"+i+"</li>";//添加一共多少页和点击事件
			
			uljia.append(H);
		}
		scrolltop(page);
	})
})
//点击分页显示的方法
function fl(p1,p2){
	//var p=p1;
	$("#xiye").empty();
	$("#xiye").html(p1);//给显示的页数赋值
	
	commonPage(p1);
}
//分页的的上一页和下一页
function topclick(){
	var v=document.getElementById("xiye");
	var num=v.innerHTML;
	if(num>1)
	{
		num--;
		v.innerHTML=num;
		var hei=25*num-25;
		$("#xab").scrollTop(hei);
	}
	
	commonPage(num);
}
function downclick(){
	var pages=parseInt($("#mo").html());//获取当前的总页数
	var v=$("#xiye");
	var num=parseInt(v.html());
	if(num < pages){
		num = ++num;
		v.html(num);
		scrolltop(num);
	}
	commonPage(num);
}
$(function(){
	
//分页的的首页和未页
	$("#first").bind("click",function(){
			var v=document.getElementById("xiye");
			v.innerHTML=1;
			scrolltop(v.innerHTML);
			commonPage(1);
		})
	$("#last").bind("click",function(){
			var v=document.getElementById("xiye");
			var l=document.getElementById("mo");
			v.innerHTML=l.innerHTML;
			scrolltop(v.innerHTML);
			commonPage(v.innerHTML);
		})
	//点击body隐藏下拉翻页
	$(document).bind("click",function(e){ 	
		var target = $(e.target); 	
		if(target.parents(".xifenye").length == 0){ 	
			$("#xab").hide(); 	
		} 	
	})   
})
//滚动条
function scrolltop(top){
	var hei=25*top-25;
	$("#xab").scrollTop(hei);
}

//分页数据实现
function commonPage(pageNo){
	if(typeof(pagingData) != "undefined"){
		//各自实现
		pagingData(pageNo);
		//图片的浮层
		InfoHover();
       
	}
}
function InfoHover(){
	 $(".imglistBox .imgList").each(function(index, element) {
			var tli = $(this);
     	tli.hover(function(){
				$(this).find(".hoverbox").show();
			},function(){
				$(this).find(".hoverbox").hide();
			});
 	});
}


