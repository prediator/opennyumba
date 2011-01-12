<%@ include file="/WEB-INF/templates/include.jsp" %>

<%@ include file="/WEB-INF/templates/header.jsp" %>

<h3>Persons Place</h3>


<div><b class="boxHeader"><spring:message code="persons.header"/></b>
	<div class="box">
		<c:set var="personsSize" value="${fn:length(persons)}" />
		<c:choose>
			<c:when test="${personsSize < 1}">
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
						<c:forEach items="${persons}" var="person" varStatus="status">
							<tr class="<c:choose><c:when test="${status.index % 2 == 0}">evenRow</c:when><c:otherwise>oddRow</c:otherwise></c:choose>">
								<td> <input type="checkbox" name="personId" value="${person.personId}"/> </td>
								<td>${person.firstName}</td>
								<td>${person.lastName}</td>
								<td>
									<a href="/locationEdit.form?personId=${person.personId}"><img src="${pageContext.request.contextPath}/images/edit.gif" border="0"
									title="<spring:message code="persons.edit"/>" alt="<spring:message code="persons.edit"/>"/></a>
								</td>
							</tr>
						</c:forEach>
					</table>
					<input type="submit" name="action" value="<spring:message code="persons.delete"/>"/>
				</form>			
			</c:otherwise>
		</c:choose>
	</div>
</div>

<%@ include file="/WEB-INF/templates/footer.jsp" %>