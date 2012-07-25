package com.mehana.smschat.jwebsocket;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.jwebsocket.factory.JWebSocketFactory;
import org.jwebsocket.logging.Logging;
import org.jwebsocket.server.TokenServer;

import com.mehana.smschat.dao.ActiveOperatorDAO;

/**
 * @author aschulze
 * @author maruen email: maruen@gmail.com
 * 
 */

public class ContextListener implements ServletContextListener {

	private static Logger log = Logging.getLogger(ContextListener.class);
	private ActiveOperatorDAO activeOperatorDAO;

	public void contextInitialized(ServletContextEvent sce) {

		JWebSocketFactory.start();

		TokenServer lTS = (TokenServer) JWebSocketFactory.getServer("servername");
		
		SMSChatPlugin smsChatPlugin = new SMSChatPlugin();
		lTS.getPlugInChain().addPlugIn(smsChatPlugin);

	}

	public void contextDestroyed(ServletContextEvent sce) {
		JWebSocketFactory.stop();

	}
}
