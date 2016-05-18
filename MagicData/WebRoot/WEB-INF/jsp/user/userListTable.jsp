<%@ page pageEncoding="UTF-8" %>

<table id="tableList" class="tableList"  style="">
  		<tHead>
  			<tr height="25">
  				<td>&nbsp;</td>
				<td align="center">用户序号</td>
				<td align="center">用户名</td>
				<td align="center">用户类型</td>
				<td align="center">操作</td>
			</tr>
		</tHead>
		<tBody>
			<c:forEach items="${userlist}" var="result" varStatus="status">
			  		<tr height="22">
			  			<td>&nbsp;</td>
			  			<td align="center"><c:out value="<%=i++ %>"/></td>
			  			<td align="center"><c:out value="${result.userName}"/></td>
			  			<td align="center">
							<c:if test="${result.roleType==1}">超级管理员</c:if>
							<c:if test="${result.roleType==2}">普通管理员</c:if>
						</td>
						<td align="center">
						   <c:if test="${result.roleType==2 }">
						   		<a href="deleteUser.action?id=${result.id}">删除用户</a>
								<c:choose>  
								   <c:when test="${result.status==0}">
								             <a href="modifyStatus.action?status=1&id=${result.id}">禁用 </a> 
								   </c:when> 
								   <c:otherwise>
								            <a href="modifyStatus.action?status=0&id=${result.id}">启用 </a> 
								   </c:otherwise>  
								</c:choose>
						   </c:if>
						</td>
			  		</tr>
	  		</c:forEach>
	    </tBody>
	  	<tFoot>
			<tr height="auto">
				<td>&nbsp;</td>
				<td colspan="4">&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr height="22">
				<td>&nbsp;</td>
				<td colspan="4">
					<%@ include file="pagerInc.jsp" %>
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr height="22">
				<td>&nbsp;</td>
				<td colspan="4"></td>
			</tr>
		</tFoot>
	</table>