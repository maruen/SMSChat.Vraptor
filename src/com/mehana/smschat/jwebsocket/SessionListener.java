/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mehana.chat.server;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.jwebsocket.appserver.WebSocketHttpSessionMerger;
import org.jwebsocket.logging.Logging;

/**
 * Web application lifecycle listener.
 * Here the http session is added or removed respectively from the
 * global WebSocketHttpSessionMerger.
 * @author aschulze
 * @author maruen
 * email: maruen@gmail.com
 */
public class SessionListener implements HttpSessionListener {

	private static Logger log = null;

	private void checkLogs() {
		if (log == null) {
			log = Logging.getLogger(SessionListener.class);
		}
	}

	
	public void sessionCreated(HttpSessionEvent hse) {
		WebSocketHttpSessionMerger.addHttpSession(hse.getSession());
		checkLogs();
		log.info("Created Http session: " + hse.getSession().getId());
	}

	
	public void sessionDestroyed(HttpSessionEvent hse) {
		WebSocketHttpSessionMerger.removeHttpSession(hse.getSession());
		checkLogs();
		log.info("Destroyed Http session for user: " + hse.getSession().getId());
	}
}
