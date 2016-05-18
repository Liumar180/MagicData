<%@ page contentType="text/html;charset=UTF-8" %>
<div>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" id="PageCtrl_Table" style="font-size:12pt;">
	<tr>
		<td id="PageCtrl_pageInfo" align="left" valign="bottom">&nbsp;第 <c:out value="${pageInfo.pageNo}"/>/<c:out value="${pageInfo.totalPages}"/> 页</td>
		<td id="PageCtrl_recordInfo" align="center">&nbsp;共 <c:out value="${pageInfo.totalRecords}"/> 条&nbsp;
		每页<input type="text" style="text-align:center; border-left-style:none; border-right-style:none; border-top-style:none; border-bottom: solid 1px #333333; font-family:Verdana; font-size: 9pt; background-color:transparent;" size="3" id="PageCtrl_pageSize" value="<c:out value="${pageInfo.pageSize}"/>">条</td>
		<td id="PageCtrl_pageJump" align="center">前往第<input type="text" style="text-align:center; border-left-style:none; border-right-style:none; border-top-style:none; border-bottom: solid 1px #333333; font-family:Verdana; font-size: 9pt; background-color:transparent;" size="2" id="PageCtrl_pageGoto">页</td>
		<td id="PageCtrl_pageChange"align="right" style="color:black; font-size-12px;">
			<span title="首页" id="PageCtrl_pageFirst" style="cursor:hand" onclick="gotoPage(1)">首页</span>
			<span title="前一页" id="PageCtrl_pagePrev" style="cursor:hand" onclick="gotoPrevPage()">前一页</span>
			<span title="后一页" id="PageCtrl_pageNext" style="cursor:hand" onclick="gotoNextPage()">后一页</span>
			<span title="末页" id="PageCtrl_pageLast" style="cursor:hand" onclick="gotoPage(<c:out value="${pageInfo.totalPages}"/>)">末页</span>&nbsp;&nbsp;
		</td>
	</tr>
</table>
</div>
<script>
	var vPageNo = <c:out value="${pageInfo.pageNo}" default="0"/>;
	var vPageSize = <c:out value="${pageInfo.pageSize}" default="0"/>;
	var vTotalPages = <c:out value="${pageInfo.totalPages}" default="0"/>;
	var vTotalRecords = <c:out value="${pageInfo.totalRecords}" default="0"/>;
	function gotoPrevPage(){
		if(vPageNo>1)
			return gotoPage(vPageNo - 1);
		else 
			alert("已到第一页！")
	}
	
	function gotoNextPage(){
		if(vPageNo < vTotalPages)    <%--小于总页数--%>
			return gotoPage(vPageNo + 1);
		else 
			alert("已到最后一页！")
	}
	
	function gotoPage(toPage){
		if(toPage == vPageNo){
			alert("无页可翻！");
			return;
		}
		if(frm){
			if(frm.pageNo){
				frm.pageNo.value = toPage;
				frm.submit();
			} else
				alert("没找到pageNo文本框，请联系开发人员！");
		}	else
			alert("没找到frm，请联系开发人员！");
	}
	
	function changePageSize(pageSize){
		if(frm){
			if(frm.pageSize){
				frm.pageSize.value = pageSize;
				frm.pageNo.value = 1;
				frm.submit();
			} else
				alert("没找到pageSize文本框，请联系开发人员！");
		}	else
			alert("没找到frm，请联系开发人员！");
	}
	$("#PageCtrl_Table").bind("keydown",function(event){
        if ( event.keyCode == 13 )		// 回车键
		{
			var tagEvent = event.srcElement ? event.srcElement : event.target;
			if(tagEvent.id == "PageCtrl_pageGoto"){
				var pn = tagEvent.value;
				if(isNaN(pn) || pn < 1 || pn > vTotalPages)
					alert("请输入 1 -- " + vTotalPages + " 之间的数字！");
				else
					gotoPage(pn);
			} else if(tagEvent.id == "PageCtrl_pageSize") {
				var ps = tagEvent.value;
				if(isNaN(ps) || ps < 1 || ps > vTotalRecords)
					alert("请输入 1 -- " + vTotalRecords + " 之间的数字！");
				else 
					changePageSize(ps);
			}
		}
    });
</script>