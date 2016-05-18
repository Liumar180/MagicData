$(function() {
	// 左侧
	$(".leftCon ul li").click(function() {
		$(this).addClass("setOn").siblings("li").removeClass("setOn");
	});
	// 右侧tab
	$(".userTab span").click(
			function() {
				$(this).addClass("spanSeton").siblings("span").removeClass(
						"spanSeton");
				var tabIndex = $(".userTab span").index(this);
				$(".userCon").eq(tabIndex).show().siblings(".userCon").hide();
			});

	// 右侧条件筛选
	$(".commodity-screening .selected-list span")
			.click(
					function() {
						var $spanText = $(this).text();
						var $spanValue = $(this).attr("value");
						var id = $(this).parent().parent().attr("id");
						if ($(".apend div[id='append_" + id + "']").length) {
							$(".apend div[id='append_" + id + "'] strong")
									.text($spanText);
							$(".apend div[id='append_" + id + "']").attr(
									"value", $spanValue);
						} else {
							$(".apend")
									.append(
											"<div class='selected-div' "
													+ "id='append_"
													+ id
													+ "'"
													+ "value='"
													+ $spanValue
													+ "'"
													+ ">"
													+ "<strong>"
													+ $spanText
													+ "</strong>"
													+ "<i class='delet-btn' onclick=commondel('"
													+ id + "')></i>" + "</div>"

									);

							$(".delet-btn").on("click", function() {
								$(this).closest(".selected-div").remove();
							});
						}

					});

	// 右侧图片的浮层
	$(".imglistBox .imgList").each(function(index, element) {
		var tli = $(this);
		tli.hover(function() {
			$(this).find(".hoverbox").show();
		}, function() {
			$(this).find(".hoverbox").hide();
		});
	});
	//导出下拉框
	$(".select_box_export").click(function(event) {
		event.stopPropagation();
		$(this).find(".option1").toggle();
		$(this).parent().siblings().find(".option1").hide();
	});
	$(document).click(function(event) {
		var eo = $(event.target);
		if ($(".select_box_export").is(":visible")
				&& eo.attr("class") != "option1"
				&& !eo.parent(".option1").length)
			$('.option1').hide();
	});
	/* 赋值给文本框 */
	$(".option1 span").click(function() {
		var value = $(this).text();
		$(this).parent().siblings(".select_txt1").text(value);
		// <!--15年9月8日加-->
		var classAttr = $(this).attr('class');
		$(".select_txt1").attr("name", classAttr);
		// <!--15年9月8日加--结束-->
		exportFile(classAttr);
	})
	// 搜索
	$(".select_box").click(function(event) {
		event.stopPropagation();
		$(this).find(".option").toggle();
		$(this).parent().siblings().find(".option").hide();
	});
	$(document).click(function(event) {
		var eo = $(event.target);
		if ($(".select_box").is(":visible")
				&& eo.attr("class") != "option"
				&& !eo.parent(".option").length)
			$('.option').hide();
	});
	/* 赋值给文本框 */
	$(".option span").click(function() {
		var value = $(this).text();
		$(this).parent().siblings(".select_txt").text(value);
		// <!--15年9月8日加-->
		var classAttr = $(this).attr('class');
		$(".select_txt").attr("name", classAttr);
		// <!--15年9月8日加--结束-->
		$("#select_value").val(value);

		if (typeof (conditionLinkage) != "undefined") {
			// 列表条件实现
			conditionLinkage(classAttr);

		}

	})

	// 隐藏结果div
	$(document).click(function(e) {
		$(".hideDiv").hide();
	});
	
	//4月12日加案件导出
	$(".dc a").click(function(){
		$(this).next(".hideselectDiv").css("display","block");
	})
	//点击其他地方该下拉内容隐藏
	$(document).bind("click",function(e){ 	
		var target = $(e.target); 	
		if(target.parents(".dc").length == 0){ 	
			$(".hideselectDiv").hide(); 			
		} 	
	})
	
	
	
	
	
	
	
	

})
