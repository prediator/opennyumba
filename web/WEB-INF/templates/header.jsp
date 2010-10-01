<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="org.openhouse.web.WebConstants" %>
<%
	pageContext.setAttribute("msg", session.getAttribute(WebConstants.OPENHOUSE_MSG_ATTR));
	pageContext.setAttribute("msgArgs", session.getAttribute(WebConstants.OPENHOUSE_MSG_ARGS));
	pageContext.setAttribute("err", session.getAttribute(WebConstants.OPENHOUSE_ERROR_ATTR));
	pageContext.setAttribute("errArgs", session.getAttribute(WebConstants.OPENHOUSE_ERROR_ARGS));
	session.removeAttribute(WebConstants.OPENHOUSE_MSG_ATTR);
	session.removeAttribute(WebConstants.OPENHOUSE_MSG_ARGS);
	session.removeAttribute(WebConstants.OPENHOUSE_ERROR_ATTR);
	session.removeAttribute(WebConstants.OPENHOUSE_ERROR_ARGS);
%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<openhouse:htmlInclude file="/openhouse.js" />
		<openhouse:htmlInclude file="/openhouse.css" />
		<openhouse:htmlInclude file="/navigation.css" />

		<c:choose>
			<c:when test="${!empty pageTitle}">
				<title>${pageTitle}</title>
			</c:when>
			<c:otherwise>
				<title><spring:message code="openhouse.title"/></title>
			</c:otherwise>
		</c:choose>


		<script type="text/javascript">
			/* variable used in js to know the context path */
			var openhouseContextPath = '${pageContext.request.contextPath}';
		</script>

	</head>

<body>
	<div id="pageBody">
		<div id="userBar">
			<openhouse:authentication>
				<span id="userLoggedInAs" class="firstChild">
					<spring:message code="auth.logged.in"/> ${authenticatedUser.username}
				</span>
				<span id="userLogout">
					<a href='${pageContext.request.contextPath}/logout'><spring:message code="auth.logout" /></a>
				</span>
				<span>
					<a href="${pageContext.request.contextPath}/"><spring:message code="user.options"/></a>
				</span>
			</openhouse:authentication>
		</div>

		<div id="nav_bar">
			<div>
				<ul class="coolMenu">
					<li><a href='${pageContext.request.contextPath}/'><spring:message code="menu.home"/></a></li>
					<li><a href='${pageContext.request.contextPath}/user/users.list'><spring:message code="menu.userManagement"/></a></li>
					<li><a href='${pageContext.request.contextPath}/server/server.list'><spring:message code="menu.serverManagement"/></a></li>
					<li><a href='${pageContext.request.contextPath}/persons/persons.list'><spring:message code="menu.persons"/></a></li>
					<li><a href='${pageContext.request.contextPath}/groups/groups.list'><spring:message code="menu.groups"/></a></li>
					<li><a href='${pageContext.request.contextPath}/locations/locations.list'><spring:message code="menu.locations"/></a></li>
					<li><a href='${pageContext.request.contextPath}/encounter/encounter.list'><spring:message code="menu.encounters"/></a></li>
					<li><a href='${pageContext.request.contextPath}/concept/concept.list'><spring:message code="menu.concepts"/></a></li>
					<li><a href='${pageContext.request.contextPath}/obs/obs.list'><spring:message code="menu.observations"/></a></li>
				</ul>
			</div>
		</div>
		<div id="wrapper">
			<div id="content_bar">
				<c:if test="${msg != null}">
					<div id="openhouse_msg"><spring:message code="${msg}" text="${msg}" arguments="${msgArgs}" /></div>
				</c:if>
				<c:if test="${err != null}">
					<div id="openhouse_error"><spring:message code="${err}" text="${err}" arguments="${errArgs}"/></div>
				</c:if>
			
