<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.mehana.smschat.model.User"%>

<html>
<head>
<title>Usuário: ${userSession.user.username}, Server: ${userSession.config.server}</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
</head>

<body>
	<c:if test="${userSession.user != null}">
		<br />
		<div class="box">Não existem usuários no chat.</div>
		 <a href="javascript:openSMSSimulatorPage();" title="Simular SMS">Simular SMS</a>
		
		<div class="chat">

			<ul class="tabs" id="tabsButtons"></ul>
			<div class="tab_container" id="tabsContents"></div>

			<div class="textInput">
				<table>
					<tr>
						<td valign="middle" class="msg1" width="5">Mensagem</td>
						<td valign="top" style="padding-right: 12pt">
							<textarea id="SMSMessage" cols="255 " rows="2" style="width: 100%"></textarea>
						</td>
						<td valign="middle" width="5">
							<input class="sbtnDlg" id="sbtnSend" type="button" value="Enviar"
							onclick="javascript:sendSMS();" style="margin-left: 6pt">
						</td>
					</tr>
				</table>
			</div>
		</div>

	</c:if>

</body>