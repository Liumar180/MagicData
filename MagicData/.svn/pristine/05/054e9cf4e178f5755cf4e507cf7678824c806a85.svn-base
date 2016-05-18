$(function(){
	/*案件详情效果*/
  	$(".xxselect_box").click(function(event){   
   		event.stopPropagation();
        $(this).find(".ajoption").toggle();
        $(this).parent().siblings().find(".ajoption").hide();
   	});
   	$(document).click(function(event){
        var eo=$(event.target);
        if($(".xxselect_box").is(":visible") && eo.attr("class")!="ajoption" && !eo.parent(".ajoption").length)
        $('.ajoption').hide();           
   	});
  	
  	/*案件详情--相关人员以及相关组织-删除按钮*/
  	$(".personImg p .delA").click(function(){
  		//$(this).parents("li").remove();
  	})
  	/*相关组织*/
  	$(".zzImg p .zzdelA").click(function(){
  		//$(this).parents("li").remove();
  	})
  	
  	
  	/*--工作记录tab--*/
  	$(".workleftTab li").live("click",function(){
	  	$(this).addClass("liCur").siblings("li").removeClass("liCur");
	  	var tabIndex = $(".workleftTab li").index(this); 
	  	$(".workrightCon").eq(tabIndex).show().siblings(".workrightCon").hide();
	});
  	
 }) 	