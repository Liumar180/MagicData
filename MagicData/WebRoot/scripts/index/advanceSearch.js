
  /*#################高级搜索开始#########################*/
  var advanceSearch = new Map();
  advanceSearch.put("titleOfSearch","");
  advanceSearch.put("mailAddressOfSearch","");
  advanceSearch.put("contectOfSearch","");
  advanceSearch.put("filenameOfSearch","");
  advanceSearch.put("filecontectOfSearch","");
  advanceSearch.put("hotwordOfSearch","");
  advanceSearch.put("tagOfSearch","");
  advanceSearch.put("folderOfSearch","");
  advanceSearch.put("datefromSearch","");
  advanceSearch.put("datetoSearch","");
  var setting = {
			check: {
				enable: true,
				chkboxType:{
					"Y" : "", "N" : "" 
				},
			},
			
			data: {
				simpleData: {
					enable: true
				}
			}
		};
                 
  var zNodes ="";
      
  function advancedSearch(){
	 
	 
	  $.fn.zTree.init($("#treeDemo"), setting, zNodes); 
	  
	  document.getElementById("content").disabled="true";
	  $("#emailsearch").show();
	  $("#titleOfSearch").val(advanceSearch.get("titleOfSearch"));
	  $("#mailAddressOfSearch").val(advanceSearch.get("mailAddressOfSearch"));
	  $("#contectOfSearch").val(advanceSearch.get("contectOfSearch"));
	  $("#filenameOfSearch").val(advanceSearch.get("filenameOfSearch"));
	  $("#filecontectOfSearch").val(advanceSearch.get("filecontectOfSearch"));
	  $("#hotwordOfSearch").val(advanceSearch.get("hotwordOfSearch"));
	  $("#tagOfSearch").val(advanceSearch.get("tagOfSearch"));
	 // $("#folderOfSearch").val(advanceSearch.get("folderOfSearch"));
	  $("#datefromSearch").val(advanceSearch.get("datefromSearch"));
	  $("#datetoSearch").val(advanceSearch.get("datetoSearch"));
	 
  }
  var from = {
		    elem: '#datefromSearch',
		    format: 'YYYY-MM-DD hh:MM:ss',
		    min: '1099-01-01', //设定最小日期为当前日期
		    max: '2099-01-01', //最大日期
		    festival: true, //显示节日
		    istime: true,
		    istoday: true,
		    choose: function(datas){
		    	to.min = datas; //开始日选好后，重置结束日的最小日期
		    	to.start = datas; //将结束日的初始值设定为开始日
		    }
		};
var to = {
		    elem: '#datetoSearch',
		    format: 'YYYY-MM-DD  hh:MM:ss',
		    min: '1099-01-01',
		    max: '2099-01-01',
		    festival: true, //显示节日
		    istime: true,
		    istoday: true,
		    choose: function(datas){
		    	from.max = datas; //结束日选好后，重置开始日的最大日期
		    }
		};
  function submitAdvanceSearch(){
	  advanceSearch.put("allTextOfSearch",document.getElementById("allTextOfSearch").value);
	  advanceSearch.put("titleOfSearch",document.getElementById("titleOfSearch").value);
	  advanceSearch.put("mailAddressOfSearch",document.getElementById("mailAddressOfSearch").value);
	  advanceSearch.put("contectOfSearch",document.getElementById("contectOfSearch").value);
	  advanceSearch.put("filenameOfSearch",document.getElementById("filenameOfSearch").value);
	  advanceSearch.put("filecontectOfSearch",document.getElementById("filecontectOfSearch").value);
	  advanceSearch.put("hotwordOfSearch",document.getElementById("hotwordOfSearch").value);
	  advanceSearch.put("tagOfSearch",document.getElementById("tagOfSearch").value);
	  advanceSearch.put("datefromSearch",document.getElementById("datefromSearch").value);
	  advanceSearch.put("datetoSearch",document.getElementById("datetoSearch").value);
	  var treeObj=$.fn.zTree.getZTreeObj("treeDemo"),
	  truenodes=treeObj.getCheckedNodes(true),
	  falsenodes=treeObj.getCheckedNodes(false),
       v="";
      for(var i=0;i<truenodes.length;i++){
      v+=truenodes[i].name + ",";
		  for (var k = 0; k < zNodes.length; k++) {
			  if (zNodes[k].id == truenodes[i].id) {
				  zNodes[k].checked=true;
				  zNodes[k].open=true;
	          }
		  } 
      }
      for(var i=0;i<falsenodes.length;i++){
          
    		  for (var k = 0; k < zNodes.length; k++) {
    			  if (zNodes[k].id == falsenodes[i].id) {
    				  zNodes[k].checked=false;
    				  zNodes[k].open=false;
    	          }
    		  } 
          }
	  advanceSearch.put("folderOfSearch",v);
	  var folder=advanceSearch.get("folderOfSearchid");
	  if(folder!=null){
		  for(var i = 0; i <= folder.length; i++){
			  
	         
	      }
	  }
	  document.getElementById('barBox').innerhtml='';
	  
	  $('.searchtccBox').hide();
	  var str="";
	  if(advanceSearch.get("allTextOfSearch")!="" ){
		  str+="全文:"+advanceSearch.get("allTextOfSearch")+"#";
	  }
	  if(advanceSearch.get("titleOfSearch")!="" ){
		  str+="主题:"+advanceSearch.get("titleOfSearch")+"#";
	  }
	  if(advanceSearch.get("mailAddressOfSearch")!="" ){
		  str+="发件人/收件人:"+advanceSearch.get("mailAddressOfSearch")+"#";
	  }
	  if(advanceSearch.get("contectOfSearch")!="" ){
		  str+="邮件正文:"+advanceSearch.get("contectOfSearch")+"#";
	  }
	  if(advanceSearch.get("filenameOfSearch")!="" ){
		  str+="附件文件名:"+advanceSearch.get("filenameOfSearch")+"#";
	  }
	  if(advanceSearch.get("filecontectOfSearch")!="" ){
		  str+="附件内容:"+advanceSearch.get("filecontectOfSearch")+"#";
	  }
	  if(advanceSearch.get("hotwordOfSearch")!="" ){
		  str+="热词:"+advanceSearch.get("hotwordOfSearch")+"#";
	  }
	  if(advanceSearch.get("tagOfSearch")!="" ){
		  str+="标签:"+advanceSearch.get("tagOfSearch")+"#";
	  }
	  if(advanceSearch.get("folderOfSearch")!="" ){
		  str+="文件夹:"+advanceSearch.get("folderOfSearch")+"#";
	  }
	  if(advanceSearch.get("datefromSearch")!="" ){
		  str+="日期-开始:"+advanceSearch.get("datefromSearch")+"#";
	  }
	  if(advanceSearch.get("datetoSearch")!="" ){
		  str+="日期-结束:"+advanceSearch.get("datetoSearch")+"#";
	  }
	  document.getElementById('content').value=str;
	  searchContent("");
  }
  function clearSearchText(){
	  document.getElementById("allTextOfSearch").value ="";
	  document.getElementById("titleOfSearch").value ="";
	  document.getElementById("mailAddressOfSearch").value ="";
	  document.getElementById("contectOfSearch").value ="";
	  document.getElementById("filenameOfSearch").value ="";
	  document.getElementById("filecontectOfSearch").value ="";
	  document.getElementById("hotwordOfSearch").value ="";
	  document.getElementById("tagOfSearch").value ="";
	  document.getElementById("datefromSearch").value ="";
	  document.getElementById("datetoSearch").value ="";
	  var treeObj=$.fn.zTree.getZTreeObj("treeDemo"),
	  truenodes=treeObj.getCheckedNodes(true),
	  falsenodes=treeObj.getCheckedNodes(false),
	  v = "";
      for(var i=0;i<truenodes.length;i++){
          v+=truenodes[i].name + ",";
    		  for (var k = 0; k < zNodes.length; k++) {
    			  if (zNodes[k].id == truenodes[i].id) {
    				  zNodes[k].checked=false;
    				  zNodes[k].open=false;
    	          }
    		  } 
         }
      for(var i=0;i<falsenodes.length;i++){
          
		  for (var k = 0; k < zNodes.length; k++) {
			  if (zNodes[k].id == falsenodes[i].id) {
				  zNodes[k].checked=false;
				  zNodes[k].open=false;
	          }
		  } 
      }
	  $.fn.zTree.init($("#treeDemo"), setting, zNodes);
  }
  function checkboxInsearch(data){
	  var id =data.id.substring(0,data.id.length-2);
	 if(document.getElementById(id).disabled==true){
		 document.getElementById(id).disabled=false;
	 }else{
		 document.getElementById(id).value="";
		 document.getElementById(id).disabled=true;
	 }
  }
  
  /*#################高级搜索结束#########################*/