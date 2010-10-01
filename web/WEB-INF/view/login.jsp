<%@ include file="/WEB-INF/templates/include.jsp" %>

<%@ include file="/WEB-INF/templates/headerMinimal.jsp" %>

<form name="loginform" method="post"><br>
	<br>
	<table align="center">
		<tr>
			<td><h4><spring:message code="auth.welcome"/></h4></td>
		</tr>
	</table>
	<table width="300px" align="center" style="border: 1px solid #f90; background-color: #efefef;">
		<tr><td colspan=2></td></tr>
		<tr><td colspan=2>&nbsp;</td></tr>
		<tr>
			<td><b>&nbsp;&nbsp;&nbsp;<spring:message code="user.username"/></b></td>
			<td><input type="text" name="username" id="username" value=""></td>
		</tr>
		<tr>
			<td><b>&nbsp;&nbsp;&nbsp;<spring:message code="user.password"/></b></td>
			<td><input type="password" name="password" value=""></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" name="action" value="<spring:message code="auth.login"/>"></td>
		</tr>
		<tr><td colspan=2>&nbsp;</td></tr>
	</table>
</form>
<script type="text/javascript">
 document.getElementById('username').focus();
</script>

<%@ include file="/WEB-INF/templates/footer.jsp" %>