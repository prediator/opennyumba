<%@ include file="/WEB-INF/templates/include.jsp" %>

<%@ include file="/WEB-INF/templates/header.jsp" %>

<h1><spring:message code="menu.userManagement"/></h1>


<div><b class="boxHeader">Users</b>
	<div class="box">
		<c:set var="userSize" value="${fn:length(users)}" />
		<c:choose>
			<c:when test="${userSize < 1}">
				<br/>
				<i>(There are no registered users in the database. "I wonder how you are here")</i>
				<br/>
			</c:when>
			<c:otherwise>
				<table cellpadding="4" cellspacing="0">
					<tr>
						<th>Username</th>
						<th>Full Name</th>
						<th>Gender</th>
					</tr>
					<c:forEach items="${users}" var="user" varStatus="status">
						<tr class="<c:choose><c:when test="${status.index % 2 == 0}">evenRow</c:when><c:otherwise>oddRow</c:otherwise></c:choose>">
							<td>${user.username}</td>
							<td>${user.firstName} ${user.middleName} ${user.lastName}</td>
							<td>
								<c:choose>
									<c:when test="${user.gender == 'M'}">
										<img src="${pageContext.request.contextPath}/images/male.gif"/>
									</c:when>
									<c:otherwise>
										<img src="${pageContext.request.contextPath}/images/female.gif"/>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<br/><br/>
<div><b class="boxHeader">Roles</b>
	<div class="box">
		<table>
			<tr>
				<td width="50%">
					<div style="color: #627588;"><h3>Create New Role</h3></div>
					<table>
						<tr><th valign="top">Role:</th><td><input type="text" name="roleName"/></td></tr>
						<tr><th valign="top">Description:</th><td><textarea rows="5" cols="60"></textarea></td></tr>
						<tr><td><input type="button" name="saveRole" value="Save Role" /></td></tr>
					</table>
				</td>
				<td width="50%">
				</td>
			</tr>
		</table>
	</div>
</div>
<br/><br/>
<div><b class="boxHeader">Privileges</b>
	<div class="box">
		<table>
			<tr>
				<td width="50%">
					<div style="color: #627588;"><h3>Create New Privilege</h3></div>
					<table>
						<tr><th valign="top">Privilege:</th><td><input type="text" name="privilegeName"/></td></tr>
						<tr><th valign="top">Description:</th><td><textarea rows="5" cols="60"></textarea></td></tr>
						<tr><td><input type="button" name="savePrivilege" value="Save Privilege" /></td></tr>
					</table>
				</td>
				<td width="50%">
				</td>
			</tr>
		</table>
	</div>
</div>

<%@ include file="/WEB-INF/templates/footer.jsp" %>