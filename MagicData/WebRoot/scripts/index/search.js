function checkboxSelected(checked,selName){
	
	 var selOper = document.getElementsByName(selName);
	   var len = selOper.length;
	   for(var i=0; i<len;i++)
	   {
	     if(checked==true)
	     {
	    	 
	        selOper[i].checked = true;
	     }else{
	       selOper[i].checked = false;
	     }
	   
	   } 
}

function getmaillist(emails,more){
    	var results="";
    	var m;
    	if(more==""){
    		var content= $('#content').val();
    		index++;
    		 m=index;
    		 
    		 results='<div  style="padding:10px 0px">'+
						'<div class="indextabCOn emailtabCon">'+
				      	  '<div class="tabUlBox" >'+
				            	'<ul class="tabulTit clearfix">'+
				            		//new add 全选、反选
//				            		'<li class="li1"><input type="checkbox" \/></li>'+
					            	' <li class="li4"><a class="qxBtn" href="javascript:void(0);" onclick="checkSelEmail('+m+')">全选</a><\/li> '+
					        		' <li class="li4"><a class="fxBtn" href="javascript:void(0);" onclick="nocheckSelEmail('+m+')">反选</a><\/li> '+
					               ' <li class="li2"><a class="fsBtn" href="javascript:void(0);" onclick="sendmails('+m+')">关系网络</a><\/li> '+
				                   ' <li class="li3"><a href="javascript:void(0);" class="tqBtn"  onclick="filtermail('+m+')">热词提取</a><\/li>'+
				            	'<\/ul>'+
				               ' <div class="innerEmailtabCon" id='+index+'maildiv >'+
							'<\/div>'+ 
						'<\/div>'+ 
				'<\/div><input type="hidden" id='+index+'tabvalue  value='+content+'/>';
				$('#tt').tabs('add',{
					title: '邮件 '+index,
					closable: true,
					content: results
					
				});
				pagemap.put(index,0);
    	}else{m=more;}
				results='';
				
				for(var i =0; i<emails.length;i++){
				//results+=emails[i].title;
				emailmap.put(emails[i].id,emails[i]);
				results+= '<div class="tabulList clearfix">'+
				 		'<div class="checkP">'+'<input type="checkbox" name="mailcheckbox'+m+'"  value="'+emails[i].id+'"   \/>'+'<\/div>'+
				    '<div class="emailDiv">'+
				       ' <dl class="clearfix">'+
				           ' <dt><img src="images/img/e_mail.png" width="24" height="17" \/></dt>'+
				           ' <dd>'+
				             '   <h1>'+emails[i].title+'<\/h1>'+
				              '  <p>发送时间：'+emails[i].sendtime+'<\/p>'+
				              '  <p>发件人：<span>'+emails[i].from+'<\/span><\/p>'+
				              '  <p>收件人：<span>'+emails[i].tos+'<\/span><\/p>'+
				              '  <p>抄送：<span>'+emails[i].copyTos+'<\/span><\/p>'+
				          '  <\/dd>'+
				       ' <\/dl>'+
				   ' <\/div>'+
				   ' <div class="hoverDiv"><div class="hoverdivCon"><div class="hoverIcon"><\/div>'+
				      '  <div class="listdiv1 clearfix"><b>主题：<\/b><p>'+emails[i].title+'<\/p><\/div>'+
				       ' <div class="listdiv1 clearfix"><b>发送时间：<\/b><p><span>'+emails[i].sendtime+'<\/span></p><\/div>'+
				       ' <div class="listdiv1 clearfix"><b>发件人：<\/b><p><span>'+emails[i].from+'<\/span><\/p><\/div>'+
				       ' <div class="listdiv1 clearfix"><b>收件人：<\/b><p><span>'+emails[i].tos+'<\/span><\/p><\/div>'+
				       ' <div class="listdiv1 clearfix"><b>抄送：<\/b><p><span>'+emails[i].copyTos+'<\/span><\/p><\/div> <div class="listdiv1 clearfix"><b>热词：<\/b><p>';
				   if(emails[i].keywords != null){	
				$.each(emails[i].keywords,function(n,value){
				   		results+='<em>'+value+'<\/em>';
					});
				   }
				      
				       results+= '<\/p><\/div> <div class="listdiv1 clearfix"><b>附件名称：<\/b><p><span>'+emails[i].attachfilenames+'<\/span><\/p><\/div>'+
				       ' <div class="btnXx">'+
				        '<a href=javascript:toShowEmailDetails("'+emails[i].vertexID+'") >查看详情<\/a>'+
				       ' <\/div>'+
				   ' <\/div>'+     	                    	
				'<\/div><\/div>';
				}
				results+='<div class="btntestBox" id="btntestBox'+m+'" >'+ 
	           		'<span class="btnTest" onclick="searchContent('+m+')">查看更多</span>'+ ' <\/div>';
				
				if(more==""){
					$("#"+index+"maildiv").append(results);
				}else{
					$('#btntestBox'+more).remove();
					$("#"+more+"maildiv").append(results);
				}
				 
				
				hiddenemailMG();
    }

   
 
 	/*提取*/
 	function filtermail(id){
 		++index;
 		var m=index;
 		 var results='<div W style="padding:10px 0px"> <div class="indextabCOn emailtabCon"> <div class="tabUlBox" >'+'<ul class="tabulTit clearfix">'+
		//new add
//		'<li class="li1"><input type="checkbox" \/></li>'+
 		' <li class="li4"><a class="qxBtn" href="javascript:void(0);" onclick="checkSelEmail('+m+')">全选</a><\/li> '+
		' <li class="li4"><a class="fxBtn" href="javascript:void(0);" onclick="nocheckSelEmail('+m+')">反选</a><\/li> '+
       ' <li class="li2"><a class="fsBtn" href="javascript:void(0);" onclick="sendmails('+m+')">关系网络</a><\/li> '+
       ' <li class="li3"><a href="javascript:void(0);" class="tqBtn" onclick="filtermail('+m+')">热词提取</a><\/li>'+
	'<\/ul>'+ ' <div class="innerEmailtabCon" id='+index+'maildiv > <div class="tabulListLeft">' ;
 		
   		var str=document.getElementsByName("mailcheckbox"+id);
     		var objarray=str.length;
     		var oblist=new Array();
     		var oblistcount=0;
     		for (var i=0;i<objarray;i++){
     		  if(str[i].checked == true){
     			oblist[oblistcount]=str[i].value;
     			++oblistcount;
     		  }
     		}
     		
     		var contentList=new Array();
     		for (var i=0;i<oblist.length;i++){
     			contentList[i]=emailmap.get(oblist[i]).content+"  "+emailmap.get(oblist[i]).attachfileContents;
     		}
     		
   		 for(var i =0; i<oblist.length;i++){
   				 results+= '<div class="tabulList clearfix">'+
				 		'<div class="checkP"><input type="checkbox" name="mailcheckbox'+m+'" value="'+emailmap.get(oblist[i]).id+'"\/><\/div>'+
				    '<div class="emailDiv">'+
				       ' <dl class="clearfix">'+
				           ' <dt><img src="images/img/e_mail.png" width="24" height="17" \/></dt>'+
				           ' <dd>'+
				             '   <h1>'+emailmap.get(oblist[i]).title+'<\/h1>'+
				              '  <p>发送时间：'+emailmap.get(oblist[i]).sendtime+'<\/p>'+
				              '  <p>发件人：<span>'+emailmap.get(oblist[i]).from+'<\/span><\/p>'+
				              '  <p>收件人：<span>'+emailmap.get(oblist[i]).tos+'<\/span><\/p>'+
				              '  <p>抄送：<span>'+emailmap.get(oblist[i]).copyTos+'<\/span><\/p>';
   				
   				results+= '<\/dd>'+
				       ' <\/dl>'+' <div class="btnXx">'+
				        '<a href=javascript:toShowEmailDetails("'+emailmap.get(oblist[i]).vertexID+'") >查看详情<\/a>'+
					       ' <\/div>'+
				   ' <\/div>'+
				   ' <\/div>' ; 
   		} 
   		$.ajax({
   			 type : "post",  
	   	         async : false, //同步执行 
	   	         url : pageVariate.base+"bar/getFilterkeywords.action", 
	   	      	 data : {"contentList":contentList.toString(),
		        	 date:new Date().getTime() 
		        	},  
	   	         dataType : "json", //返回数据形式为json  
		         success:function(data){
		        	 results+='<\/div><div class="rightdiv">';
		        	 if(data){
		        		
		        		 for(var i=0;i<data.split('##').length-1;i++){
			        		 results+='<span>'+data.split("##")[i]+'<\/span>';
			        	 }
		        	 }
		           },
		         error:function(){
		        	 results='';
		        	 hintManager.showHint("未查询到邮件数据.");
		         }
		     });
   		results+='<\/div><\/div><\/div><\/div>'; 
   		if(oblistcount == 0){
  			 hintManager.showHint("请至少选择一个mail！");
  		}else{
  			$('#tt').tabs('add',{
			title: '邮件 '+index,
			closable: true,
			content: results
		});
  			}
			 
 	}
 	/*隐藏/显示DIV*/
 	function hiddenemailMG(){
 		var $hoverDiv = null;
 		$(".emailDiv").hover(function(){
 			if($hoverDiv != null) {
 				$hoverDiv.hide();
 			}
 			$hoverDiv = $(this).next(".hoverDiv");
 			$hoverDiv.show();
 		});
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
 			});
 		/*var i=true;
 		$(".tabulTit .li1 input").click(function(){
 			var temp = 'input[name="mailcheckbox"'+id+'"]';
 			if(i==true){
// 				$('.tabulList .checkP input').prop('checked',true);
 				$(temp).prop('checked',true);
 				i=false;
 			}else{
// 				$('.tabulList .checkP input').prop('checked',false);
 				$(temp).prop('checked',false);
 				i=true;
 			}
 		});*/
 	}
 	
 	//全选
 	function checkSelEmail(id){
 		var temp = 'input[name="mailcheckbox'+id+'"]';
 		var str=$(temp);
		var len=str.length;
		for (var i=0;i<len;i++){
			str[i].checked = true;
		}
 	}
 	
 	//反选
 	function nocheckSelEmail(id){
 		var temp = 'input[name="mailcheckbox'+id+'"]';
 		var str=$(temp);
		var len=str.length;
		for (var i=0;i<len;i++){
			var em = str[i];
			if(em.checked == true){
//				em.prop('checked',false);
				em.checked = false;
			}else{
//				em.prop('checked',true);
				em.checked = true;
			}
		}
 	}
 	
 	
 	