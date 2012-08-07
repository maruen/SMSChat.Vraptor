package com.mehana.smschat.jwebsocket;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.jwebsocket.factory.JWebSocketFactory;
import org.jwebsocket.server.TokenServer;

import com.mehana.smschat.dao.ActiveOperatorDAO;

/**
 * @author aschulze
 * @author maruen email: maruen@gmail.com
 * 
 */

public class ContextListener implements ServletContextListener {
	
	private ActiveOperatorDAO activeOperatorDAO;

	public void contextInitialized(ServletContextEvent sce) {

		JWebSocketFactory.start();
		TokenServer lTS = (TokenServer) JWebSocketFactory.getServer("tsServer1");
		SMSChatPlugin smsChatPlugin = new SMSChatPlugin();
		lTS.getPlugInChain().addPlugIn(smsChatPlugin);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		JWebSocketFactory.stop();

	}
}
