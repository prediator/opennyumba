<%@ include file="/WEB-INF/templates/include.jsp" %>

<%@ include file="/WEB-INF/templates/header.jsp" %>

<h1><spring:message code="locations.title"/></h1>

<div><b class="boxHeader">New Location</b>
	<div class="box">
		<table>
			<tr>
				<td width="50%">
					<table>
						<tr><th valign="top"><spring:message code="global.name"/>:</th><td><input type="text" name="locationName"/></td></tr>
						<tr><th valign="top"><spring:message code="global.description"/>:</th><td><textarea rows="5" cols="60"></textarea></td></tr>
						<tr><td><input type="button" name="saveLocation" value="Save Role" /></td></tr>
					</table>
				</td>
				<td width="50%">
				</td>
			</tr>
		</table>
	</div>
</div>

<%@ include file="/WEB-INF/templates/footer.jsp" %>