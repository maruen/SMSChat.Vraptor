<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.mehana.smschat.model.User"%>

<head>
	<title>SMSChat</title>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<link type="image/x-icon" rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico"/>

		<meta name="description" content="Chat by SMS" />
		<meta name="author" content="Maruen Mehana"/>
		<meta name="keywords" content="SMSChat"/>

		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesheet.css"/>

		<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/jquery.livequery.js"></script>
		<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/jquery.colorbox-1.3.16.min.js"></script>
		<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/javascript.js"></script>
	</head>
	<body>

		<div id="mensagem"></div>
	
		<div id="wrapper">
			<div id="topo"><%@ include file="/topo.jsp" %></div>
			<div id="menu"><%@ include file="/menu.jsp" %></div>
	
			<div id="content">
				<c:if test="${not empty errors}">
					<div id="errors" class="error">
						<c:forEach var="error" items="${errors}">
					  		${error.category} - ${error.message}<br/>
						</c:forEach>
					</div>
				</c:if>
	
				<c:if test="${userSession.user == null}">
					<form action="${pageContext.request.contextPath}/autenticate" method="post">
						
						<table border="0">
						<tr>
							<td>Usuário: </td>
							<td><input type="text" name="entity.username"/></td>
						</tr>
						<tr>
						    <td>Senha:  </td>
						    <td><input  type="password" name="entity.password"/></td>
						</tr>
						</table>
						<input type="submit" value="Autenticar" class="btn" />
					</form>
				</c:if>
			
			</div>
			
			<div id="rodape"><%@ include file="/rodape.jsp" %></div>
		</div>
	</body>
</html>