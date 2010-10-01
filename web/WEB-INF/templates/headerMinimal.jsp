<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<openhouse:htmlInclude file="/openhouse.css" />

		<script type="text/javascript">
			/* variable used in js to know the context path */
			var openhouseContextPath = '${pageContext.request.contextPath}';
		</script>
		<c:choose>
			<c:when test="${!empty pageTitle}">
				<title>${pageTitle}</title>
			</c:when>
			<c:otherwise>
				<title><spring:message code="openhouse.title"/></title>
			</c:otherwise>
		</c:choose>

	</head>

<body>
	<div id="pageBody">
		<div id="contentMinimal">
			<c:if test="${msg != null}">
				<div id="openhouse_msg"><spring:message code="${msg}" text="${msg}" arguments="${msgArgs}" /></div>
			</c:if>
			<c:if test="${err != null}">
				<div id="openhouse_error"><spring:message code="${err}" text="${err}" arguments="${errArgs}"/></div>
			</c:if>

