$(function(){
	/*添加案件弹出层*/
	$(".ajSpan").click(function(){
	  	var ajData = {
		  	title:"添加案件",
		  	url:"case/viewCaseAdd.action",
		  	title1:""
	  	};
	  parent.parentPop(ajData);
	 })
	 /*导入案件弹出层*/
	$(".importCasesSpan").click(function(){
	  	var ajData = {
		  	title:"导入案件",
		  	url:"case/viewCaseImport.action",
		  	title1:""
	  	};
	  parent.parentPop(ajData);
	 })
	
	/*添加组织弹出层*/
	$(".zzSpan").click(function(){
		var zzData = {
			title:"添加组织",
			url:"lawCaseOrg/viewOrgAdd.action"
		};
	  	parent.parentPop(zzData);
	 })
	/*编辑组织弹出层*/
	$(".bjZz").click(function(){
		var zzData = {
			title:"编辑组织",
			url:"组织弹出层.html"
		};
	  	parent.parentPop(zzData);
	 })  
	/*添加人员弹出层*/
	$(".rySpan").click(function(){
		var ryData = {
			title:"添加人员",
			url:"peoplemanage/viewPeopleAdd.action"
		};
		parent.parentPop(ryData);
	}) 
	/*编辑人员弹出层*/
	$(".bjRy").click(function(){
		var ryData = {
			title:"编辑人员",
			url:"人员弹出层.html"
		};
		parent.parentPop(ryData);
	}) 
	/*添加文件弹出层*/
	$(".wjSpan").click(function(){
		var wjData = {
			title:"添加文件",
			url:"fileManage/viewFileAdd.action"
		};
		parent.parentPop(wjData);
	 })
	/*编辑文件弹出层*/
	$(".bjWj").click(function(){
		var wjData = {
			title:"编辑文件",
			url:"文件弹出层.html"
		};
		parent.parentPop(wjData);
	 })
	/*添加主机弹出层*/
	$(".zjSpan").click(function(){
		var zjData = {
			title:"添加主机",
			url:"hostManage/addHost.action"
		};
		parent.parentPop(zjData);
	 })
	/*添加虚拟身份弹出层*/
	$(".xxsfSpan").click(function(){
		var xxsfData = {
			title:"添加虚拟身份",
			url:"虚拟身份弹出层.html"
		};
		parent.parentPop(xxsfData);
	 })
	/*编辑虚拟身份弹出层*/
	$(".xnsfbjA").click(function(){
		var xxsfData = {
			title:"编辑虚拟身份",
			url:"虚拟身份弹出层.html"
		};
		parent.parentPop(xxsfData);
	 })
	/*添加证件号码弹出层*/
	$(".zjnumSpan").click(function(){
		var zjnumData = {
			title:"添加证件号码",
			url:"证件号码弹出层.html"
		};
		parent.parentPop(zjnumData);
	 })
	/*编辑证件号码弹出层*/
	$(".zjnumbjA").click(function(){
		var zjnumData = {
			title:"编辑证件号码",
			url:"证件号码弹出层.html"
		};
		parent.parentPop(zjnumData);
	 })
	/*添加电话号码弹出层*/
	$(".phonenumSpan").click(function(){
		var phonenumData = {
			title:"添加电话号码",
			url:"电话号码弹出层.html"
		};
		parent.parentPop(phonenumData);
	 })
	/*编辑电话号码弹出层*/
	$(".phonenumbjA").click(function(){
		var phonenumData = {
			title:"编辑电话号码",
			url:"电话号码弹出层.html"
		};
		parent.parentPop(phonenumData);
	 })

	/*点击关闭按钮*/
	$(".tccBox b").click(function(event){
    	$(".tccBox").hide();
		event.preventDefault();
  	});
  	/*点击取消按钮*/
  	$(".tccBox .qxBtn").click(function(){
  		parent.dismissParentPop();
  	});


  	
   
  	 //弹出层js
  function fc(){
  		var W = $(window).width();
    	var WindowH = $(window).height();
		var wH = $(window).height();
    	var H = $(document).height();
	    if(W <= 1280){
	      $('.bg').css('width','1280px')  
	    }else{ 
	      $('.bg').css('width','100%')  
	    }
    	$('.bg').css('height',H);    	
  }
  fc(); 
	  $(window).resize(function(){
	    fc();
	  });
	  $(window).load(function(){
	    fc();
	  });
})
