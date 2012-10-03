package com.mehana.smschat.jwebsocket;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jwebsocket.api.WebSocketPacket;
import org.jwebsocket.appserver.WebSocketHttpSessionMerger;
import org.jwebsocket.factory.JWebSocketFactory;
import org.jwebsocket.kit.WebSocketServerEvent;
import org.jwebsocket.listener.WebSocketServerTokenEvent;
import org.jwebsocket.listener.WebSocketServerTokenListener;
import org.jwebsocket.server.TokenServer;
import org.jwebsocket.token.Token;

import com.mehana.smschat.dao.ActiveOperatorDAO;
import com.mehana.smschat.dao.GenericDAO;
import com.mehana.smschat.model.ActiveOperator;
import com.mehana.smschat.model.MsgHist;
import com.mehana.smschat.object.SMSMessage;

/**
 * 
 * 
 * @author Maruen Mehana
 */


public class JWebSocket extends HttpServlet implements WebSocketServerTokenListener {
    private static final long   serialVersionUID = 1L;
    private GenericDAO<MsgHist> msgHistDAO;
    private ActiveOperatorDAO   activeOperatorDAO;
    private Logger logger = Logger.getLogger(this.getClass());
    

    

	protected void processRequest(HttpServletRequest request,HttpServletResponse response) throws ServletException,
            IOException {

		boolean fromSimulator = (request.getParameter("fromSimulator") == null) ? false	: true;
		
		logger.info("Inserting into table MsgHist: [MSISDN: " + request.getParameter("telefone") + 
				", MESSAGE: " + request.getParameter("texto") + "]");
		
        SMSMessage smsMessage = new SMSMessage();
        smsMessage.setMobileMsisdn(request.getParameter("telefone"));
        smsMessage.setText(request.getParameter("texto"));
      

        MsgHist msgHist = new MsgHist();
        msgHist.setDt_sys(Calendar.getInstance().getTime());
        msgHist.setSession_id(1L);
        msgHist.setMsisdn(request.getParameter("telefone"));
        msgHist.setOperator("operator");
        msgHist.setMsg_orig(request.getParameter("texto"));
        msgHist.setOriginator(request.getParameter("telefone"));
        msgHist.setMsg_norm(request.getParameter("texto").toUpperCase());
        msgHist.setDt_plat(Calendar.getInstance().getTime());
        //msgHistDAO.save(msgHist);
        new SMSListener().process(smsMessage);
        if (fromSimulator) {
			response.sendRedirect(request.getContextPath() + "/SMSSimulator");
        	
        }

    }

    public void init() {
    	logger.info("Adding servlet '" + getClass().getSimpleName() + "' to WebSocket listeners...");
        //TokenServer lServer = (TokenServer) JWebSocketFactory.getServer("tsServer1");
    	TokenServer lServer = (TokenServer) JWebSocketFactory.getServer("tsServer1");

    	
        if (lServer != null) {
            lServer.addListener(this);
        }
        
        logger.info("Method init: lServer.getAllConnectors() "	+ lServer.getAllConnectors());
   
    }

    public void processOpened(WebSocketServerEvent aEvent) {
    	
    	WebSocketHttpSessionMerger.addWebSocketSession(aEvent.getSession());
    	logger.info("Method processOpened: Opened WebSocket with connector: "	+ aEvent.getConnector());
    }

    public void processPacket(WebSocketServerEvent aEvent, WebSocketPacket aPacket) {
    	logger.info("Received WebSocket packet: " + aPacket.getASCII());
    }

    public void processToken(WebSocketServerTokenEvent aEvent, Token aToken) {
    	
    	logger.info("processToken: [aEvent ->" + aEvent + ", aToken ->" +aToken );
    	
        String lType = aToken.getType();
        if (lType != null && !"ping".equals(lType)) {
        	logger.info("Method processToken: Client '" + aEvent.getUsername(aEvent.getConnector()) + "' sent Token: '" + aToken.toString() + "'.");
        }

        if (lType != null && "login".equals(lType)) {

        	logger.info("Inserting into table ACTIVE_OPERATOR: [username: " + aEvent.getUsername(aEvent.getConnector()) + "]");
        	
            ActiveOperator activeOperator = new ActiveOperator(aEvent.getUsername(aEvent.getConnector()), aEvent
                    .getConnector().getId(), Calendar.getInstance().getTime(), "tsServer1");
            activeOperatorDAO.save(activeOperator);

        } else if (lType != null && ("close".equals(lType) || "logout".equals(lType))) {

        }

    }

    public void processClosed(WebSocketServerEvent aEvent) {
    	
    	logger.info("Method processClosed: Closed WebSocket with connector: "	+ aEvent.getConnector());
		logger.info("Removing from table Active Operator the operator: " + aEvent.getConnector().getId());

        ActiveOperator activeOperator = new ActiveOperator();
        activeOperator.setConnector((aEvent.getConnector().getId()));
        activeOperator.setServername("tsServer1");

        activeOperatorDAO.remove(activeOperator);

        WebSocketHttpSessionMerger.removeWebSocketSession(aEvent.getSession());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        processRequest(request, response);
    }

    public String getServletInfo() {
        return "Short description";
    }
}
