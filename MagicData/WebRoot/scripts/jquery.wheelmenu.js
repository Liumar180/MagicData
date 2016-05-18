/* ===========================================================
 * jquery-wheelmenu.js v1
 * ===========================================================
 * Copyright 2013 Pete Rojwongsuriya.
 * http://www.thepetedesign.com
 *
 * A small jQuery plugin that adds a beautiful
 * Path-like menu button to your website
 * https://github.com/peachananr/wheel-menu
 *
 * angle[开始角度，结束角度]
 * ========================================================== */

!function($){
  var left = {};
  var top = {};
  var defaults = {
		trigger: "click",//点击时触发
		animation: "fade",//设置动画效果
		angle: [0,360],//环形角度
		animationSpeed: "fast" //动画速度
	};
	
	$.fn.centerAround = function (button) {
		var offset = button.offset(),
        width = button.outerWidth(),//按钮外框宽度
        height = button.outerHeight(),//按钮外框高度
        buttonX = (offset.left - $(document).scrollLeft() ) + width / 2,
        buttonY = (offset.top -  $(document).scrollTop() ) + height / 2;
        //objectOffset = this.offset();
		//buttonX,buttonY为按钮的圆心=按钮外框-滚轮宽高+按钮宽高的一半
		this.css("position","fixed");
	    this.css("top", buttonY  - (this.outerHeight() / 2)  + "px");
	    this.css("left", buttonX - (this.outerWidth() / 2)   + "px");
	    //以上为设定环形层的位置top=圆形XY-外框的一半	     
	    return this;
   };//定位起点圆心的信息
  
  
  $.fn.fadeInIcon = function (el, button, width, height, angle, step, radius, settings) {
	  
    var d = 0;
    this.stop(true,true);
/*    this.each(function(index) {
    	var pi = 3.14;
        angle = (settings.angle[0] + (step * index)) * (pi/180); 
        var x = Math.round(width/2 + radius * Math.cos(angle) - $(this).find("a").outerWidth()/2),
            y = Math.round(height/2 + radius * Math.sin(angle) - $(this).find("a").outerHeight()/2);//定位环形按钮的起点坐标
        $(this).css({
            position: 'absolute',
            left: x + 'px',
            top: y + 'px',
            opacity: 0
        }).delay(d).animate({opacity:1}, settings.animationSpeed[1]);
        d += settings.animationSpeed[0];
        
        var part= 360/settings.liSize;
        $("#subWheelLi"+index).css("z-index", "6");//设定层级
        if(index>0){
      	  $("#subWheelLi"+index).css("transform", " rotate("+part*index+"deg) scale(1)");//设定层旋转变形角度
      	  //$("#subWheelLi"+index).css("transition-duration", "0s");//动画执行时间
        }
        
      });*/
    this.each(function(index) {//width按钮内宽
      var pi = 3.14;
      angle = (settings.angle[0] + (step * index)) * (pi/180); //Math.PI
      if(index==0) angle = 0;
      var x = Math.round(width/2 + radius * Math.cos(angle) - $(this).find("a").outerWidth()/2),
          y = Math.round(height/2 + radius * Math.sin(angle) - $(this).find("a").outerHeight()/2);//定位环形按钮的起点坐标
      left[index] = x;
      top[index] = y;
      if(index ==2) y = top[1];
      if(index ==3) y = top[0];
      if(index ==5) y = top[4];
      if(index ==4) x = left[2];
      if(index ==5) x = left[1];
      
      if(index==0){
    	  x = x-1;
      }
      $(this).css({
          position: 'absolute',
          left: x + 'px',
          top: y + 'px',
          opacity: 0
      }).delay(d).animate({opacity:1}, settings.animationSpeed[1]);
      d += settings.animationSpeed[0];
      
      var part= 360/settings.liSize;
      $("#subWheelLi"+index).css("z-index", "6");//设定层级
      
      
      /*if(index>0){
    	  //$("#subWheelLi"+index).css("transform", " rotate("+part*index+"deg) scale(1)");//设定层旋转变形角度
    	  //$("#subWheelLi"+index).css("transition-duration", "0s");//动画执行时间
    	  var parentLi =  ($("#subWheelLi"+index).parent()).parent();
    	  parentLi.css("transform", " rotate("+(part*index)+"deg) scale(1)");
      }else{
    	  var parentLi =  ($("#subWheelLi"+index).parent()).parent();
    	  parentLi.css("transform", " rotate(0deg) scale(1)");
      }*/
      if(index>0){
      	  //$("#subWheelLi"+index).css("transform", " rotate("+part*index+"deg) scale(1)");//设定层旋转变形角度
      	  //$("#subWheelLi"+index).css("transition-duration", "0s");//动画执行时间
    	  var parentLi =  ($("#subWheelLi"+index).parent()).parent();
    	  parentLi.css("transform", " rotate("+(part*index)+"deg) scale(1)");
        }
    });
  };
  
  $.fn.fadeOutIcon = function (el, button) {
    var d = 0;
    this.stop(true,true);
    
    $(this.get().reverse()).each(function() {
	    $(this).delay(d).animate({opacity:0}, 6);
      d += 3;
	  }).promise().done( function() {
      el.removeClass("active").css("visibility", "hidden").hide();
      button.removeClass("active");
    });
  };
	
	$.fn.hideIcon = function (button, settings) {
	  var fields = this.find(".item"),
	      el = this;
	  try{
		  //同时隐藏2，3级菜单
		  var lv2Ul = this.find(".ulLv2");
		  lv2Ul.removeClass("active").css("visibility", "hidden").hide();
		  var lv3Ul = this.find(".ulLv3");
		  lv3Ul.removeClass("active").css("visibility", "hidden").hide();
	  }catch(e){}
	  switch (settings.animation) { 
      case 'fade': 
        fields.fadeOutIcon(el, button);
        break; 
    
      case 'fly': 
        fields.flyOut(el, button);
        break; 
    }
	  
	};
	
	$.fn.showIcon = function (button, settings,rootWheelR) {
	  var el = this,
	      zindex = '6';
	  if (settings.trigger == "hover") {
	      zindex = '3';
      }
	  button.addClass("active").css({
          'z-index': zindex
      });
	  el.show().css({
        position: 'absolute',
        'opacity':1,
        'z-index': '5',
        'padding': '30px' // add safe zone for mouseover
      }).centerAround(button); 
      el.addClass("wheel active").css("visibility", "visible").show();
      
	  if (el.attr('data-angle')) {
        settings.angle = el.attr('data-angle');
      }
    
      settings = predefineAngle(settings);
	 // var radius = el.width() / 2,//半径
      //fields = el.find(".item"),
	  var radius = rootWheelR,
      fields = el.children(".item"),
      container = el,
      width = container.innerWidth()-30,//决定了环的位置
      height = container.innerHeight()-66,
      /*width = container.innerWidth()-25,//决定了环的位置
      height = container.innerHeight()-60,*/
      angle =  0,
      step = (settings.angle[1] - settings.angle[0]) / settings.liSize;
	  //step = 360 / settings.liSize;
      switch (settings.animation) { 
        case 'fade': 
          fields.fadeInIcon(el, button, width, height, angle, step, radius, settings);
          break; 
          
        case 'fly': 
          fields.flyIn(el, button, width, height, angle, step, radius, settings);
          break; 
      }
	};
	
	$.fn.animateRotate = function(angle, duration, easing, complete) {
      return this.each(function() {
          var $elem = $(this);

          $({deg: 0}).animate({deg: angle}, {
              duration: duration,
              easing: easing,
              step: function(now) {
                  $elem.css({
                      transform: 'rotate(' + now + 'deg)'
                  });
              },
              complete: complete || $.noop
          });
      });
  };
  
  
	
	function predefineAngle (settings) {//设置角度
	  var convert = false;
	  if ($.type(settings.angle) == "string") {
	    try {
        if (eval(settings.angle).length > 1) convert = true;
      }
      catch(err) {
        convert = false;
      }
	    if (convert == true) {
	      settings.angle = JSON.parse(settings.angle);
	    } else {
	      switch (settings.angle) { 
          case 'N':
            settings.angle = [180,380];
            break;
          case 'NE':
            settings.angle = [270,380];
            break;
          case 'E':
            settings.angle = [270,470];
            break;
          case 'SE':
            settings.angle = [360,470];
            break;
          case 'S':
            settings.angle = [360,560];
            break;
          case 'SW':
            settings.angle = [90,200];
            break;
          case 'W':
            settings.angle = [90,290];
            break;
          case 'NW':
            settings.angle = [180,290];
            break;
          case 'all':
            settings.angle = [0,360];
            break;
        }
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
	          settings.animationSpeed = [3,6];//[25,250]
	          break;
	        case 'instant':
	          settings.animationSpeed = [0,0];
	          break;
	      }
      }
      return settings;
	}//设置动画 速度的方法
	
  $.fn.wheelmenu = function(options,rootWheelR){
	  
    var settings = $.extend({}, defaults, options);
    
    settings = predefineSpeed(settings);
    
    return this.each(function(){
      var button = $(this);
      var el = $($(this).attr("id"));//取得wheel-button类中属性id里所对应的值
      //var el = $($(this).attr("href"));
      el.addClass("wheel");
      
      button.css("opacity", 0).animate({
        opacity: 1
      });
      if (settings.trigger == "hover") {

        button.bind({
          mouseenter: function() {
            el.showIcon(button, settings,rootWheelR);
          }
        });
        
        el.bind({
          mouseleave: function() {
            el.hideIcon(button, settings);
          }
        });
        
      } else {
        button.click( function() {
          if (el.css('visibility') == "visible") {
        	  graphOperation.resetCurrentNode();//还原节点状态
            el.hideIcon(button, settings);
            $("#menuDiv").css("visibility","hidden");
          } else {
            el.showIcon(button, settings,rootWheelR);
          }
        });
      }
    });
  };
  
}(window.jQuery);