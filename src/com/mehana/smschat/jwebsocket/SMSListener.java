package com.mehana.smschat.jwebsocket;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.factory.JWebSocketFactory;
import org.jwebsocket.server.TokenServer;
import org.jwebsocket.token.Token;

import com.mehana.smschat.object.SMSMessage;
import com.mehana.smschat.util.EscapeChars;

/**
 * 
 * @author maruen email: maruen@gmail.com
 * 
 */

public class SMSListener {

	private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

	public void process(SMSMessage message) {

		if (message != null && (message.getText() == null || message.getText().trim().equals(""))) {
			return;
		}

		Token aToken = new Token();
		aToken.put("msisdn", message.getMobileMsisdn());
		aToken.put("type", "event");
		aToken.put("name", "sms");
		aToken.put("time", sdf.format(new Date()));
		aToken.put("data", EscapeChars.escapeCharsToSMS(message.getText()));

		TokenServer lServer = (TokenServer) JWebSocketFactory.getServer("tsServer1");

		Map<String, WebSocketConnector> lConnectorMap = lServer.getAllConnectors();
		Collection<WebSocketConnector> lConnectors = lConnectorMap.values();

		for (WebSocketConnector lConnector : lConnectors) {
			lServer.sendToken(lConnector, aToken);
		}

	}
}
