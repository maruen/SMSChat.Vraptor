<%@page import="com.mehana.smschat.model.User"%>

<head>
	<title>SMSChat</title>
</head>
<body>
	<c:if test="${userSession.user == null}">
		<form action="${pageContext.request.contextPath}/autenticate" method="post">
			E-mail: <input type="text" name="entity.email"/><br/>
			Senha:  <input  type="password" name="entity.password"/><br/>
	
			<input type="submit" value="Autenticar" class="btn" />
		</form>
	</c:if>
		
</body>