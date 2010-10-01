		<br/>
		</div>
		</div>
	</div>

	<div id="footer">
		<div id="footerInner">
			<%
				pageContext.setAttribute("openhouseVersion", org.openhouse.util.OpenhouseConstants.OPENHOUSE_VERSION);
			%>
			
			<span id="buildDate">Last Build: <%= org.openhouse.web.WebConstants.BUILD_TIMESTAMP %></span>
				
			<span id="codeVersion">Version: <%= org.openhouse.util.OpenhouseConstants.OPENHOUSE_VERSION %> </span>
			
		</div>
	</div>

</body>
</html>