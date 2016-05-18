$(function() {
	$('#secAddButton').click(
			function() {
				$("#targetTableselect").parent().find(".formtips").remove();
				$("#connectionTableselect").parent().find(".formtips").remove();
				$("#sqlTextid").parent().find(".formtips").remove();
				if ($('#targetTableselect').val() == "") {
					var errorMsg = '目标数据源不能为空';
					$("#targetTableselect").parent().append('<span class="formtips onError">' + errorMsg
							+ '</span>');
					//window.parent.window.showTargetInfo(this.value);
				}
				var sqlFlag = $('#sqlFlagBox').attr("checked");
				if ($('#connectionTableselect').val() == "") {
					if (sqlFlag != "checked") {
						var errorMsg = '源数据库表不能为空';
						$('#connectionTableselect').parent().append('<span class="formtips onError">'
								+ errorMsg + '</span>');
					}
					//window.parent.window.showSourceInfo(this.value);
				}
				if ($('#sqlTextid').val() == "") {
					if (sqlFlag == "checked") {
						var errorMsg = 'SQL语句不能为空';
						$('#sqlTextid').parent().append(
								'<br/><span class="formtips onError">'
										+ errorMsg + '</span>');
					}
				}

				var numError = $('form .onError').length;
				if (numError) {
					return false;
				} else {
					addDIIForm.submit();
				}
			});
});