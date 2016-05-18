
(function ($){
   
   $.fn.extend({
        MSDL: function (options){/*MultiSelectDropList*/
		 //各种属性参数
		 
		 var defaults = {
			width: '150',//下拉列表宽 
			maxheight: '180',//下拉列表最大高度
            data: ['item1','item2','item3','item4','item5','item6'],//下拉列表中的数据
            value_selected:[false, false, false, false, false, false],
			selectallTxt: 'All',//全选文本
			selectokTxt: '&nbsp;&nbsp;X&nbsp;&nbsp;',//确认文本
		 };
		 var options = $.extend(defaults, options);
		 var $multiSelectList = $(this);
		 var selectData;
		 
		 return this.each(function (){
		 
		 //插件实现代码
			//创建 input输入框
			//readonly:锁住键盘，不能向文本框输入内容  
			var $ipt = $('<input type="text" readonly value="" class="select_rel form-control" />');
			$ipt.width(options.width - 8);//设定文本框宽度
			
			var $this = $(this);
			$this.width(options.width);
			$ipt.appendTo($this);
		    
			//创建 下拉选项 
			
			//1.下拉选项包裹
			var $container = $('<div class="container1"></div>');
		
			
			//2.创建 全选和确认按钮  top层 
			var $top = $('<div class="top"></div>');//外层div包裹
			var $all = $('<input type="checkbox" class="select_all"/><label>'+options.selectallTxt+'</label>');//全选
            var $btn = $('<span class="ok">'+options.selectokTxt+'</span>');
            $all.appendTo($top);
            $btn.appendTo($top);
			
			//3.下拉中的内容 content层
			var $content = $('<div class="content"></div>');//外层div包裹
			var count = options.data.length;
			var h = ( (count * 22) > parseInt(options.maxheight) ) ? options.maxheight : "'" + count * 22 + "'";
			$content.height(h);
			for(var i = count-1; i >= 0; i--){			  
			  /* var $list = $('<div><input type="checkbox" value='+options.value[i]+' text_data=' + options.data[i] + ' /><label>'+options.data[i]+'</label><br></div>');
			   $list.appendTo($content);*/
				var $list;
				// 回写
				if (options.value_selected[i]) {
					$list = $('<div><input type="checkbox" checked="checked" value='+options.value[i]+' text_data=' + options.data[i] + ' /><label>'+options.data[i]+'</label><br></div>');
				} else {
					$list = $('<div><input type="checkbox" value='+options.value[i]+' text_data=' + options.data[i] + ' /><label>'+options.data[i]+'</label><br></div>');
				}
			   $list.appendTo($content);
			}
           
			//4把top层和content层加到$container下
			$top.appendTo($container);
            $content.appendTo($container);	

            //把$container加到$(this)下
			$container.appendTo($this);	
			
			// 回写
			var default_text_arr = [];
			 for(var i = 0; i < options.value_selected.length; i++) {
				 if (options.value_selected[i]) {
					 default_text_arr.push(options.data[i]);
				 }
			 }
			$ipt.val(default_text_arr.join(';'));
			
            //js Effect
			var $dropList = $content.children().children('input');
			
			$all.change(function (index, element){//点击all								 
				  var opt_value_arr = [];
				  var opt_text_arr = [];
				  $dropList.each(function (){
					  if($all.is(':checked')){
						  $(this)[0].checked = 'checked';
						  options.value_selected[index] = true;
						  opt_value_arr.push($(this).val());
					   	  opt_text_arr.push($(this).attr("text_data"));
					  }else{
						  $(this)[0].checked = '';
						   	opt_value_arr = [];
				  			opt_text_arr = [];
				  			options.value_selected[index] = false;
					  }
				  }); 
								  
				  $ipt.val(opt_text_arr.join(';'));
				  
				  selectData = {
						type:$multiSelectList.attr("type"),
						value_selected:options.value_selected,
						value_arr:opt_value_arr,
						text_arr:opt_text_arr
				};				  
				  //勾选联动
				  changeSelect(selectData);
			});
			
			$container.addClass('hidden');//开始隐藏
			
			$ipt.focus(function (){//文本框处于编辑
				$container.removeClass('hidden');
				$this.addClass('multi_select_focus');
			});
			
			$btn.click(function (){//点击 ok按钮 
			    $container.addClass('hidden');
				$this.removeClass('multi_select_focus');
				 
			});
			
			
			$dropList.change(function (index, element){//勾选选项
				 var opt_value_arr = [];
				 var opt_text_arr = [];
				 $dropList.each(function (){
				   if ($(this).is(':checked')) {
					   opt_value_arr.push($(this).val());
					   opt_text_arr.push($(this).attr("text_data"));
					   options.value_selected[index] = true;
				   } else {
					   options.value_selected[index] = false;
				   }
				 });
				 var $dropList_selected = $content.children().children('input:checked');
				 $ipt.val(opt_text_arr.join(';'));
				 var o = $all[0];
				 var n1 = $dropList_selected.length;
				 var n2 = $dropList.length;
				 o.checked = (n1 === n2) ? 'checked' : '';
				 
				 selectData = {
					type:$multiSelectList.attr("type"),
					value_selected:options.value_selected,
					value_arr:opt_value_arr,
					text_arr:opt_text_arr
				};
				 
				 //勾选联动
				 changeSelect(selectData);
				
			});
		 });
	 },
   });
})(jQuery);

//勾选联动
function changeSelect(selectData){
	//selectData.value_arr 已勾选的值数组
	//selectData.text_arr 已勾选的显示值数组
	//selectData.type 标识（div中的type）
	
	if(typeof(selectHandler) != "undefined"){
		//各自实现
		selectHandler(selectData);
	}
}