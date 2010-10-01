<%@ include file="/WEB-INF/templates/include.jsp" %>

<%@ include file="/WEB-INF/templates/header.jsp" %>

<h1><spring:message code="locations.title"/></h1>

<div><b class="boxHeader"><spring:message code="locations.header"/></b>
	<div class="box">
		<c:set var="locationsSize" value="${fn:length(locations)}" />
		<c:choose>
			<c:when test="${locationsSize < 1}">
				<br/><i>There are no locations in the database.</i><br/>
			</c:when>
			<c:otherwise>
				<form method="post">
					<table cellpadding="4" cellspacing="0">
						<tr>
							<th></th>
							<th><spring:message code="global.name"/></th>
							<th><spring:message code="global.description"/></th>
							<th></th>
						</tr>
						<c:forEach items="${locations}" var="location" varStatus="status">
							<tr class="<c:choose><c:when test="${status.index % 2 == 0}">evenRow</c:when><c:otherwise>oddRow</c:otherwise></c:choose>">
								<td> <input type="checkbox" name="locationId" value="${location.id}"/> </td>
								<td>${location.name}</td>
								<td>${location.description}</td>
								<td>
									<a href="/"><img src="${pageContext.request.contextPath}/images/edit.gif" border="0"
									title="<spring:message code="locations.edit"/>" alt="<spring:message code="locations.edit"/>"/></a>
								</td>
							</tr>
						</c:forEach>
					</table>
					<input type="submit" name="action" value="<spring:message code="locations.delete"/>"/>
				</form>			
			</c:otherwise>
		</c:choose>
	</div>
</div>
<%@ include file="/WEB-INF/templates/footer.jsp" %>