// JavaScript Document
$(function(){
	//弹出层js
  function fc(){
    var W = $(window).width();
    var WindowH = $(window).height();
	var wH = $(window).height();
    var H = $(document).height();
    if(W <= 1024){
      $('.bg').css('width','1024px')  
    }else{ 
      $('.bg').css('width','100%')  
    }
    $('.bg').css('height',H);
	fc(); 
	  $(window).resize(function(){
		fc();
	  });
	  $(window).load(function(){
		fc();
	  });
   }
   $('.but').click(function(){
	    $(".tccBox").show();
	    var vertexId=9994305960;
	    showEmailDetail(vertexId);
	 
    });
  
	$('.emailCon .closeB').click(function(event){
    $('.tccBox').hide();
	event.preventDefault();
  });
});


function toShowEmailDetails(vertexId){
	if(!vertexId) {
		hintManager.showHint("没有查找到邮件节点！");
		return;
	}
	$(".tccBox").show();
	var str = "";
    if(vertexId){
	         $.ajax({  
	          type : "post",  
	          async : false, //同步执行  
	          url : pageVariate.base+"bar/queryEmailById.action", 
	          data : {"emid":vertexId},  
	          dataType : "json", //返回数据形式为json  
	          success : function(datas) {
	              if (datas) {
	            	
	            	  str +='<div class="topEmailCon">'+
	                	' <div class="keywordBox clearfix">'+
	                    	' <span class="keyword">热 词：</span>'+
	                      '   <div class="tagList">';
	$.each(datas.keywords,function(n,value){
			str += '<b class="tagB"  >'+value+'</b>';
	});
	var showLabels = datas.showLabel == undefined?"":datas.showLabel;
	 str += '  </div>'+
	                   ' </div>'+
	                   ' <div class="addtagBox clearfix"><input id="eid" type="hidden"  value="'+vertexId+'" />'+
	                    	'<span class="addtag">添加标签：</span>'+
	                      '  <div class="addtagList">'+
	                        	'<form>'+
						'<p><input id="tags_1" type="text" class="tags" value="'+showLabels+'" /></p>'+
					'</form>'+
	                        '</div>'+
	                    '</div>'+
	               '</div>'+
	 '<div class="emailCon-top clearfix"><h3>'+ datas.title + '</h3>'+
		'<div class=list><em> 发件人:</em><span class="spanCon fl"><i>'+ datas.from + '</i></span></div><br>'+
		'<div class=list><em> 时  &nbsp;&nbsp;间: </em><span class="spanCon fl"><i>'+ datas.sendtime + '</i></span></div><br>'+
		'<div class=list><em> 收件人: </em><span class="spanCon fl"><i>'+datas.tos + '</i></span></div><br><div class=list><em>'+
		'附 &nbsp;&nbsp;件:</em><span class="fl">'; 

	if(datas.attachfilenames!=null){
	       for(var i=0;i<datas.attachfilenames.length;i++){
					str +='<a href="'+pageVariate.base+'bar/downLoadEmail.action?fileName='+ datas.attachfilenames[i] +'&vertexId='+ vertexId +'" title="请下载">'+
					'&nbsp&nbsp<img src=images/echarts/appendix.png />&nbsp'+datas.attachfilenames[i] +'</a>';}
			       }
			            	  str += '</span></div></div>'+ '<div class=emailCon-bottom><pre>'+datas.content + '</pre></div>';
	              }  
	               $('.barBox').html(str);
	           },  
	          error : function(errorMsg) {  
	        	  hintManager.showHint("未查询到邮件数据。");
	             }  
	        });
  }
    //$.parser.parse(str);
    $('#tags_1').tagsInput({width:'auto'});
}

function saveLabels(labes){
	var Id = $('#eid').val();
    if(Id){
	         $.ajax({  
	          type : "post",  
	          async : false, //同步执行  
	          url : pageVariate.base+"query/saveShowLabels.action", 
	          data : {"Id":Id,"labels":labes},  
	          dataType : "json", //返回数据形式为json  
	          success : function(datas) {
	              if (datas) {
	            	  alert(datas);
	              }  
	           },  
	          error : function(errorMsg) {  
	        	  hintManager.showHint("未查询到邮件数据。");
	             }  
	        });
  }
}


