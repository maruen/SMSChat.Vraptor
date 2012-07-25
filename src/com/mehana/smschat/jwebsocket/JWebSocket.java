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
import org.jwebsocket.logging.Logging;
import org.jwebsocket.server.TokenServer;
import org.jwebsocket.token.Token;

import br.com.caelum.vraptor.Resource;

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

@Resource
public class JWebSocket extends HttpServlet implements	WebSocketServerTokenListener {
	private static final long serialVersionUID = 1L;
	private static Logger log = null;
	private GenericDAO<MsgHist> msgHistDAO;
	private ActiveOperatorDAO activeOperatorDAO;
	
	protected void processRequest(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

		boolean fromSimulator = (request.getParameter("fromSimulator") == null) ? false	: true;
		SMSMessage smsMessage = new SMSMessage();
		smsMessage.setMobileMsisdn(request.getParameter("telefone"));
		smsMessage.setText(request.getParameter("texto"));
		
		log.info("Inserting into table MSG_HIST: [MSISDN: " + request.getParameter("telefone") + 
				", MESSAGE: " + request.getParameter("texto") + "]");
		
		MsgHist msgHist = new MsgHist();
		msgHist.setDt_sys(Calendar.getInstance().getTime());
		msgHist.setSession_id(1L);
		msgHist.setMsisdn(request.getParameter("telefone"));
		msgHist.setOperator("operator");
		msgHist.setMsg_orig(request.getParameter("texto"));
		msgHist.setOriginator(request.getParameter("telefone"));
		msgHist.setMsg_norm(request.getParameter("texto").toUpperCase());
		msgHist.setDt_plat(Calendar.getInstance().getTime());
		msgHistDAO.save(msgHist);
		
		

	}

	public void init() {
		log = Logging.getLogger(JWebSocket.class);
		log.info("Adding servlet '" + getClass().getSimpleName() + "' to WebSocket listeners...");
		log.info("Weblogic server:" + System.getProperty("weblogic.Name"));
		TokenServer lServer = (TokenServer) JWebSocketFactory.getServer("ts" + System.getProperty("weblogic.Name"));
		
		if (lServer != null) {
			lServer.addListener(this);
		}
		log.info("Method init: lServer.getAllConnectors() "	+ lServer.getAllConnectors());

	}

	public void processOpened(WebSocketServerEvent aEvent) {
		WebSocketHttpSessionMerger.addWebSocketSession(aEvent.getSession());
		log.info("Method processOpened: Opened WebSocket with connector: "	+ aEvent.getConnector());
		
	}

	public void processPacket(WebSocketServerEvent aEvent, WebSocketPacket aPacket) {
		log.info("Received WebSocket packet: " + aPacket.getASCII());
	}

	public void processToken(WebSocketServerTokenEvent aEvent, Token aToken) {
		
		String lType = aToken.getType();
		if (lType != null && !"ping".equals(lType)) {
			log.info("Method processToken: Client '" + aEvent.getUsername(aEvent.getConnector()) + "' sent Token: '" + aToken.toString() + "'.");
		}

		if (lType != null && "login".equals(lType)) {
			
			log.info("Inserting into table ACTIVE_OPERATOR: [username: " + aEvent.getUsername(aEvent.getConnector()) + "]");
			
			ActiveOperator activeOperator = new ActiveOperator(aEvent.getUsername(aEvent.getConnector()),
															   aEvent.getConnector().getId(),
															   Calendar.getInstance().getTime(),
															   System.getProperty("weblogic.Name"));
			activeOperatorDAO.save(activeOperator);
			
			
		} else if (lType != null && ("close".equals(lType) || "logout".equals(lType) ) ) {
			
		
			
		} 
	
	}

	public void processClosed(WebSocketServerEvent aEvent) {
		log.info("Method processClosed: Closed WebSocket with connector: "	+ aEvent.getConnector());
		log.info("Removing from table Active Operator the operator: " + aEvent.getConnector().getId());
		
		ActiveOperator cssActiveOperator = new ActiveOperator();
		cssActiveOperator.setConnector((aEvent.getConnector().getId()));
		cssActiveOperator.setServer_name(System.getProperty("weblogic.Name"));
		
		cssChatDAO.removeFromActiveOperator(cssActiveOperator);
		
		WebSocketHttpSessionMerger.removeWebSocketSession(aEvent.getSession());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	public String getServletInfo() {
		return "Short description";
	}
}
