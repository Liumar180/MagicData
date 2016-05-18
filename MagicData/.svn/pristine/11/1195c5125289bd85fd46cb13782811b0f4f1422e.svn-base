(function($) {

	$.extend($.fn.fmatter, {
		dictable: function(cellvalue, options, rowObject) {
			if (options.colModel && options.colModel.formatoptions) {
				
				var result = options.colModel.formatoptions[cellvalue];
				if (!result)
					result = options.colModel.formatoptions._;
				return result ? result : cellvalue;
			}
			
			return cellvalue;
		}
	});
	
	$.extend($.fn.fmatter.dictable, {
		unformat: function(cellvalue, options) {
			if (options.colModel.options) {
				for (var key in options.colModel.options) {
					if (cellvalue == options.colModel.options[key])
						return key;
				}
			}
			
			return cellvalue;
		}
	});

})(jQuery);
