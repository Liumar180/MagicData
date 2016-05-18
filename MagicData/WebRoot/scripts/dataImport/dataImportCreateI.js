$(function(){
	$('form :input').change(function(){
        var $parent = $(this).parent();
        $parent.find(".formtips").remove();
        if( $(this).is('#taskName') ){
     	 if(this.value==""){
     		 var errorMsg = '任务名不能为空';
     		 $parent.append('<span class="formtips onError"><br/>'+errorMsg+'</span>');
     	 }else if(! /^[a-zA-Z\d\u4E00-\u9FA5\uF900-\uFA2D]{0,16}$/.test(this.value)){
     		 var errorMsg = '任务名格式错误，只能包含数字、字母和汉字，长度小于16个字符';
              $parent.append('<span class="formtips onError"><br/>'+errorMsg+'</span>');
     	 }else{
     	     var checkRes = "";
     	     var taskId = $("#taskId").val();
     		 $.ajax({
    				url: $("#baseUrl").val()+"/ajax_dataImport/checkTaskName.action",
    				type: "POST",
    				data: {taskName:this.value,taskId:taskId} ,
    				async: false,
    				success: function(data){
    					checkRes = data.checkResult;
    				}
    			});
     		 if(checkRes == "true"){
         		 var errorMsg = "该任务名已经存在";
         		 $parent.append('<span class="formtips onError"><br/>'+errorMsg+'</span>');
         	 }
     		 if(checkRes == "null"){
         		 var errorMsg = "任务名不能为空";
         		 $parent.append('<span class="formtips onError"><br/>'+errorMsg+'</span>');
         	 }
     	 }
        }
      }).keyup(function(){
           $(this).triggerHandler("change");
      });
	
	  $('#firstAddButton').click(function(){
        $("form :input[type='text']").trigger('change');
        var numError = $('form .onError').length;
        if(numError){
            return false;
        }else{
        	$("#loadingDiv").dialog("open");
        	addDIForm.submit();
        }
     });
	  
	$('#next4').click(function() {
		$("form :input[type='text']").trigger('change');
        var numError = $('form .onError').length;
        if(numError){
            return false;
        }else{
        	addDIForm.action = $("#baseUrl").val()+"/nextDImpDFinalPage.action";
        	addDIForm.submit();
        }
	});
});