<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SMS Simulator</title>
</head>
<body>
<form id="myForm" method="post" action="${pageContext.request.contextPath}/JWebSocket" accept-charset="ISO-8859-1" >
	
	<table>
		<tr>
			<th>Celular</th>
			<td><input type="text" name="telefone" maxlength="11" size="11"/></td>
		</tr>
		<tr>
			<th valign="middle">Mensagem</th>
			<td><textarea name="texto" style="width: 310px; height: 336px;"></textarea></td>
		</tr>
	</table>
	
	<input type="submit" name="SimulateSMS" value="Simular SMS">
	<input type="hidden" name="fromSimulator" value="true">

</form>

</body>
</html>