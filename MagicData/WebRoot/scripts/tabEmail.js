// JavaScript Document
$(function(){
	var $hoverDiv = null;
	$(".emailDiv").hover(function(){
		if($hoverDiv != null) {
			$hoverDiv.hide();
		}
		$hoverDiv = $(this).next(".hoverDiv");
		$hoverDiv.show();
	})
	<!--查看更多的效果--开始-->
		var liHeight=$('.height .tabulList').height();
		var count=$('.height .tabulList').length;
		var dheight=liHeight*1;
		var allheight=liHeight*count;
		var n=1;
		$('.btnTest').click(function(){			
			n++;
			h=dheight*n;
			if(h>allheight){
				$('.btnTest').hide();
			}	
		})
	<!--查看更多的效果--结束-->
	<!--全选开始-->
	/*var i=true;
	$(".tabulTit .li1 input").click(function(){
		if(i==true){
			$('.tabulList .checkP input').prop('checked',true);
			i=false;
		}else{
			$('.tabulList .checkP input').prop('checked',false);
			i=true;
		}
	});*/
	<!--全选结束-->
	
	/*var index = 0;
	function addPanel11(){
		index++;
		$('#tt').tabs('add',{
			title: 'Tab'+index,
			content: '<div style="padding:10px 0px">Content'+index+'</div>',
			closable: true
		});
	}
	function removePanel(){
		var tab = $('#tt').tabs('getSelected');
		if (tab){
			var index = $('#tt').tabs('getTabIndex', tab);
			$('#tt').tabs('close', index);
		}
	}*/
})