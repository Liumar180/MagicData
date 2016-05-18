
function hideAll(elStr) {
		  try{
			  var elObj = $("#"+elStr);
			  var lv3Ul = elObj.find(".ulLv3*");
			  lv3Ul.removeClass("active").css("visibility", "hidden").hide();
			  var lv2Ul = elObj.find(".ulLv2*");
			  lv2Ul.removeClass("active").css("visibility", "hidden").hide();
			  elObj.removeClass("active").css("visibility", "hidden").hide();
			  var iEL = elObj.find("i");
			  iEL.each(function() {
				  var iELClass = $(this).attr("class");
				  if(iELClass.indexOf("-click")>0){
					  var tempClassStr = iELClass.substring(0,iELClass.indexOf("-click"));
					  $(this).removeClass(iELClass);
					  $(this).attr("class",tempClassStr);
				  }
			  });
			  
		  }catch(e){}
}
function clearClickIco(elStr){
	var elObj = $("#"+elStr);
	var iEL = elObj.find("i");
	iEL.each(function() {
		  var iELClass = $(this).attr("class");
		  if(iELClass.indexOf("-click")>0){
			  var tempClassStr = iELClass.substring(0,iELClass.indexOf("-click"));
			  $(this).removeClass(iELClass);
			  $(this).attr("class",tempClassStr);
		  }
	});
}
function changeClass(iId,flag){
	  var currentClass = $("#"+iId).attr("class");
	  if(currentClass.indexOf("-click")>0&&flag==0){
		  var tempClassStr = currentClass.substring(0,currentClass.indexOf("-click"));
		  $("#"+iId).removeClass(currentClass);
		  $("#"+iId).attr("class",tempClassStr);
	  }else if(currentClass.indexOf("-click")==-1&&flag==1){
		  $("#"+iId).attr("class",currentClass+"-click");
	  }
}