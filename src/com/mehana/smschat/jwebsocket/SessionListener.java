/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mehana.smschat.jwebsocket;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.jwebsocket.appserver.WebSocketHttpSessionMerger;

/**
 * Web application lifecycle listener. Here the http session is added or removed
 * respectively from the global WebSocketHttpSessionMerger.
 * 
 * @author aschulze
 * @author maruen email: maruen@gmail.com
 */
public class SessionListener implements HttpSessionListener {

   
    private void checkLogs() {
    }

    public void sessionCreated(HttpSessionEvent hse) {
        WebSocketHttpSessionMerger.addHttpSession(hse.getSession());
        checkLogs();
    }

    public void sessionDestroyed(HttpSessionEvent hse) {
        WebSocketHttpSessionMerger.removeHttpSession(hse.getSession());
        checkLogs();
    }
}
