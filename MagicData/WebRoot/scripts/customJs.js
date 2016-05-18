/*
 * 自定义数组操作
 */
Array.prototype.indexOf = function(val) {
	for (var i = 0; i < this.length; i++) {
		if (this[i] == val) return i;
	}
	return -1;
};
Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
		this.splice(index, 1);
	}
};
var isArray = function(obj) { 
	return Object.prototype.toString.call(obj) === '[object Array]'; 
} 
/*
 * 自定义map操作
 */
function Map() {    
 var struct = function(key, value) {    
  this.key = key;    
  this.value = value;    
 }    
     
 var put = function(key, value){    
  for (var i = 0; i < this.arr.length; i++) {    
   if ( this.arr[i].key === key ) {    
    this.arr[i].value = value;    
    return;    
   }    
  }    
   this.arr[this.arr.length] = new struct(key, value);    
 }    
     
 var get = function(key) {    
  for (var i = 0; i < this.arr.length; i++) {    
   if ( this.arr[i].key === key ) {    
     return this.arr[i].value;    
   }    
  }    
  return null;    
 }    
     
 var remove = function(key) {   
  var v;    
  for (var i = 0; i < this.arr.length; i++) {    
   v = this.arr.pop();    
   if ( v.key === key ) {    
    continue;    
   }    
   this.arr.unshift(v);    
  }    
 } 
 
 var removeAll = function(arr) {
 		arr.length = 0;
 }
     
 var size = function() {    
  return this.arr.length;    
 }    
     
 var isEmpty = function() {    
  return this.arr.length <= 0;    
 }    
   
 this.arr = new Array();    
 this.get = get;    
 this.put = put;    
 this.remove = remove;    
 this.size = size;    
 this.isEmpty = isEmpty; 
 this.removeAll = removeAll;
}  

/*
 * 自定义字符串操作
 */
String.prototype.endWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substring(this.length-str.length)==str)
	  return true;
	else
	  return false;
	return true;
}
String.prototype.startWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substr(0,str.length)==str)
	  return true;
	else
	  return false;
	return true;
}

String.prototype.trim=function(){
	if(!this) return "";
	return this.replace(/(^\s*)|(\s*$)/g, "");
}

/******************************消息提示 start**************************************/
var hintManager = {
	showHint:function (msg){//错误提示
		var item = $(".hintMsg");
		item.css("background-color","#e53232");
		item.css("color","#fff");
		item.html(msg).show();
		window.setTimeout(hintManager.hideHint,3000); 
	},
	showWarnHint:function (msg){//警告
		var item = $(".hintMsg");
		item.css("background-color","#fe6c6c");
		item.css("color","#fff");
		item.html(msg).show();
		window.setTimeout(hintManager.hideHint,3000); 
	},
	showInfoHint:function (msg){//消息
		var item = $(".hintMsg");
		item.css("background-color","#afe3fd");
		item.css("color","#245269");
		item.html(msg).show();
		window.setTimeout(hintManager.hideHint,3000); 
	},
	showSuccessHint:function (msg){//通过
		var item = $(".hintMsg");
		item.css("background-color","#77d461");
		item.css("color","#2b542c");
		item.html(msg).show();
		window.setTimeout(hintManager.hideHint,3000); 
	},
	hideHint:function (){
		$(".hintMsg").html("").hide();
	}
}
/******************************消息提示 end**************************************/

/** ****************************mask end************************************* */
var maskManager = {
		show : function(message){
			if(!message){
				$(".maskDetail").html("正在处理中，请稍后。。。");
			}else{
				$(".maskDetail").html(message);
			}
			$("#mask").css("display", "block");
		},
		hide : function(){
			$("#mask").css("display", "none");
			$(".maskDetail").html("正在处理中，请稍后。。。");
		},
}
/** ****************************mask end************************************* */