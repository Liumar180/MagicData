var currentX;
var currentY;
var currentR;
var currentStep;

!function($){
  
  var subDefaults = {
		trigger: "click",//点击时触发
		animation: "fast",//设置动画效果
		angle: [0,360],//角度
		animationSpeed: "medium", //动画速度 
  };
	
  $.fn.centerSubAround = function (rootButton,settings) {
	  var offset = rootButton.offset(),
      width = rootButton.outerWidth(),//按钮外框宽度
      height = rootButton.outerHeight(),//按钮外框高度
      buttonX = (offset.left - $(document).scrollLeft() ) + width / 2,
      buttonY = (offset.top -  $(document).scrollTop() ) + height / 2;
	  this.css("position","fixed");
	  //if(settings.level==2){
		  this.css("top", buttonY  - (this.outerHeight() / 2)  + 30+"px");
		  this.css("left",buttonX - (this.outerWidth() / 2)  +15+ "px");
	 // }
	//此处的+30,+15同menuStyle.css中levelOneA中所设置的left和top值
	 
	  /*this.css("width",600);
	  this.css("height",600);*/
	  return this;
   };//定位起点按钮的信息
  
  
  $.fn.fadeInSubIcon = function (el, button, width, height, angle, step, radius, settings) {
    var d = 0;
    this.stop(true,true);
    this.each(function(index) {
    	var pi = 3.14;
        angle = (settings.angle[0] + (step * index)) * (pi/180); 
        var x;
        var y;
        //定位环形按钮的起点坐标start
        if(settings.level==2){
        	x = Math.round(width/2 + radius * Math.cos(angle) - $(this).find("div").outerWidth()/2);
            y = Math.round(height/2 + radius * Math.sin(angle) - $(this).find("div").outerHeight()/2);
        }else{
        	 x = Math.round(width/2 + radius * Math.cos(angle) - $(this).find("a").outerWidth()/2);
             y = Math.round(height/2 + radius * Math.sin(angle) - $(this).find("a").outerHeight()/2);
        }
      //定位环形按钮的起点坐标end
      $(this).css({
          position: 'absolute',
          left: x + 'px',
          top: y + 'px',
          opacity: 0
      }).delay(d).animate({opacity:1}, settings.animationSpeed[1]);
      
      d += settings.animationSpeed[0];
      var rotateDeg = settings.angle[0]; 
      if(index>0){
    	  if(settings.level>2){
    		  rotateDeg = 19*index+settings.angle[0]; 
    	  }else{
    		  rotateDeg = 43*index+settings.angle[0]; 
    	  }
      }
      $("#subWheelLi"+settings.level+settings.subIds+index).css("transform", "rotate("+rotateDeg+"deg) scale(1)");
      $("#subWheelLi"+settings.level+settings.subIds+index).css("transition-duration", "0s");//动画执行时间

      /*var parentLi =  ($("#subWheelLi"+settings.level+settings.subIds+index).parent()).parent();
      parentLi.css("transform", "rotate("+rotateDeg+"deg) scale(1)");
      parentLi.css("transition-duration", "0s");*/
      
      $("#subWheelLi"+settings.level+settings.subIds+index).css("z-index", "6");//设定层级
    });
  };
  
  $.fn.fadeOutSubIcon = function (el, button) {
    var d = 0;
    this.stop(true,true);
    
    $(this.get().reverse()).each(function() {
	    $(this).delay(d).animate({opacity:0}, 150);
      d += 15;
	  }).promise().done( function() {
      el.removeClass("active").css("visibility", "hidden").hide();
      button.removeClass("active");
    });
  };
	
	$.fn.hideSubIcon = function (button, settings) {
	  var fields = this.find(".item"),
	      el = this;
	  //还原父按钮图
	  var parentButton = $("#subWheelLi"+settings.subIds);
	  if(settings.level>2){
		  parentButton = $("#subWheelLi"+(settings.level-1)+settings.subIds);
	  }
	  
	  var parentBClass = parentButton.attr("class");
	  if(parentBClass.indexOf("-click")>0){
		  var tempClassStr = parentBClass.substring(0,parentBClass.indexOf("-click"));
		  parentButton.removeClass(parentBClass);
		  parentButton.attr("class",tempClassStr);
	  }
	  fields.fadeOutSubIcon(el, button);
    };
	
	$.fn.showSubIcon = function (button, settings,rootButton,rootWheelR) {
	  var el = this,
	      zindex = '7';//6
	  if (settings.trigger == "hover") {
	     zindex = '3';
      }
	  button.addClass("active").css({
          'z-index': zindex
      });
	  el.show().css({
        position: 'absolute',
        'z-index': '0',//5
        'padding': '30px' // add safe zone for mouseover
      }).centerSubAround(rootButton,settings); 
      el.addClass("wheel active").css("visibility", "visible").show();
	  
	  if (el.attr('data-angle')) {
        settings.angle = el.attr('data-angle');
      }
    
      settings = predefineSubAngle(settings);
	  var radius = rootWheelR*settings.level*settings.rParam,//rootButton.width() + el.width(),//半径
	  //fields = el.find(".item")
      fields = el.children(".item"),
      container = el,
      width = container.innerWidth()-30,
      height = container.innerHeight()-66,
      angle =  0,
      step = (settings.angle[1] - settings.angle[0]) / fields.length;
	  //设置父按钮点击图
	  var parentButton = $("#subWheelLi"+settings.subIds);
	  if(settings.level>2){
		  parentButton = $("#subWheelLi"+(settings.level-1)+settings.subIds);
	  } 
	  
	 if(settings.hasSub=="true"){//如果有子菜单，加入变换
		  var parentBClass = parentButton.attr("class");
		  var tempClassStr = parentBClass.trim()+"-click";
		  parentButton.removeClass(parentBClass);
		  parentButton.attr("class",tempClassStr);
	  }
	  
	  fields.fadeInSubIcon(el, button, width, height, angle, step, radius, settings);
	};
	
	function predefineSubAngle (settings) {//设置角度
	  var convert = false;
	  if ($.type(settings.angle) == "string") {
	    try {
          if (eval(settings.angle).length > 1) convert = true;
        } catch(err) {
          convert = false;
        }
	    if (convert == true) {
	      settings.angle = JSON.parse(settings.angle);
	    } else {
	    	settings.angle = [90,290];
	    } 
      }
      return settings;
	}

	function predefineSpeed(settings) {
		  if ($.type(settings.animationSpeed) == "string") { 
			  switch (settings.animationSpeed) { 
		        case 'slow':
		          settings.animationSpeed = [75,700];
		          break;
		        case 'medium':
		          settings.animationSpeed = [50,500];
		          break;
		        case 'fast':
		          settings.animationSpeed = [25,250];
		          break;
		        case 'instant':
		          settings.animationSpeed = [0,0];
		          break;
		      }
	      }
	      return settings;
	};//设置动画 速度的方法
	
  $.fn.subWheelmenu = function(options,rootButton){
	    var settings = $.extend({}, subDefaults, options);
	    
	    settings = predefineSpeed(settings);//设置动画显示速度
	    
	    return this.each(function(){
	    	var button = null;
	    	/*if(settings.level==2){
	    		button = $(this).find("a");
	    	}else{
	    		button = $(this);
	    	}*/
	    	button = $(this).find("a");
	    	
	      var el = $($(this).attr("id"));//取得wheel-button类中属性id里所对应的值
	      el.addClass("wheel");
	      
	      button.css("opacity", 0).animate({
	        opacity: 1
	      });
	      if (settings.trigger == "hover") {//子类型只有hover
	        /*button.bind({
	          mouseenter: function() {
	            el.showSubIcon(button, settings,rootButton,rootWheelR);
	          }
	        });
	        
	        el.bind({
	          mouseleave: function() {
	            el.hideSubIcon(button, settings);
	          }
	        });*/
	        
	      } else {
	    	  button.click( function() {
	        	  if (el.css('visibility') == "visible") {
	                  el.hideSubIcon(button, settings);//如果下级菜单为显示状态 隐藏下级菜单
	                } else {
	                	var i = 0;
	                	var currentIndex = settings.subIds;
	                	var length = settings.length;
	                	while(i<length){
	                		  var indexEStr ="";
	                		  var indexBStr ="";
              			      if(settings.level>2){
              			    	  indexEStr = (currentIndex+"").substring(0,1)+i;
              			    	  indexBStr = (settings.level-1)+(currentIndex+"").substring(0,1)+i;
              			      }else{
              			    	  indexBStr = i+"";
              			    	  indexEStr = i+"";
              			      }
	                		  var tempEl = $("#subWheel"+indexEStr);
	                		  /*var tempFields = tempEl.find(".item");*/
	                		  var tempUl = tempEl.find("ul");
	                		  /*if(i!=currentIndex){*/
	                			  //隐藏当前已经显示的层
	                			  tempEl.removeClass("active").css("visibility", "hidden").hide();
	                			  tempUl.removeClass("active").css("visibility", "hidden").hide();
	                			  
	                			  //还原按钮
	                			  var parentButton = $("#subWheelLi"+indexBStr);
	                			  var parentBClass = parentButton.attr("class");
	                			  if(parentBClass.indexOf("-click")>0){
	                				  var tempClassStr = parentBClass.substring(0,parentBClass.indexOf("-click"));
	                				  parentButton.removeClass(parentBClass);
	                				  parentButton.attr("class",tempClassStr);
	                			  }
	                		  /*}*/
	                		  i++;
	                	}
	                	clearClickIco("wheel");
	                	if(settings.level==3){
	                		var parentButton = $("#subWheelLi"+currentIndex.substring(0,1));
	          			    var parentBClass = parentButton.attr("class");
	          			    var tmp = parentBClass + "-click";
	          			    parentButton.removeClass(parentBClass);
        				    parentButton.attr("class",tmp);
	                	}
	                	//changeOpenList();//modify by hanxue 展开/收起
	                	el.showSubIcon(button, settings,rootButton,rootWheelR);
	                }
	            });
	      }
	    });
	  };
  
}(window.jQuery);


