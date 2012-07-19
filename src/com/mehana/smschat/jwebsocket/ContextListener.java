//	---------------------------------------------------------------------------
//	jWebSocket - Context Listener for Web Applications
//	Copyright (c) 2010 jWebSocket.org, Alexander Schulze, Innotrade GmbH
//	---------------------------------------------------------------------------
//	This program is free software; you can redistribute it and/or modify it
//	under the terms of the GNU Lesser General Public License as published by the
//	Free Software Foundation; either version 3 of the License, or (at your
//	option) any later version.
//	This program is distributed in the hope that it will be useful, but WITHOUT
//	ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
//	FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for
//	more details.
//	You should have received a copy of the GNU Lesser General Public License along
//	with this program; if not, see <http://www.gnu.org/licenses/lgpl.html>.
//	---------------------------------------------------------------------------
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

    private static Logger log        = Logging.getLogger(ContextListener.class);
    private ActiveOperatorDAO    activeOperatorDAO; 

    public void contextInitialized(ServletContextEvent sce) {

        JWebSocketFactory.start();

        log.info("Removing all previous operators from table ACTIVE_OPERATOR from server: "
                + System.getProperty("weblogic.Name"));

        //remove all active operators

        TokenServer lTS = (TokenServer) JWebSocketFactory.getServer("ts" + System.getProperty("weblogic.Name"));
        SMSChatPlugin cssChatPlugin = new SMSChatPlugin();
        lTS.getPlugInChain().addPlugIn(cssChatPlugin);

    }

    public void contextDestroyed(ServletContextEvent sce) {
        JWebSocketFactory.stop();

    }
}
