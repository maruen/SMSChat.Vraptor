package com.mehana.smschat.jwebsocket;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jwebsocket.api.WebSocketPacket;
import org.jwebsocket.appserver.WebSocketHttpSessionMerger;
import org.jwebsocket.factory.JWebSocketFactory;
import org.jwebsocket.kit.WebSocketServerEvent;
import org.jwebsocket.listener.WebSocketServerTokenEvent;
import org.jwebsocket.listener.WebSocketServerTokenListener;
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
public class JWebSocket extends HttpServlet implements WebSocketServerTokenListener {
    private static final long   serialVersionUID = 1L;
    private GenericDAO<MsgHist> msgHistDAO;
    private ActiveOperatorDAO   activeOperatorDAO;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

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
        msgHistDAO.save(msgHist);

    }

    public void init() {
        TokenServer lServer = (TokenServer) JWebSocketFactory.getServer("tsServer1");

        if (lServer != null) {
            lServer.addListener(this);
        }
   
    }

    public void processOpened(WebSocketServerEvent aEvent) {
        WebSocketHttpSessionMerger.addWebSocketSession(aEvent.getSession());
    }

    public void processPacket(WebSocketServerEvent aEvent, WebSocketPacket aPacket) {
    }

    public void processToken(WebSocketServerTokenEvent aEvent, Token aToken) {

        String lType = aToken.getType();
        if (lType != null && !"ping".equals(lType)) {
                  }

        if (lType != null && "login".equals(lType)) {

            ActiveOperator activeOperator = new ActiveOperator(aEvent.getUsername(aEvent.getConnector()), aEvent
                    .getConnector().getId(), Calendar.getInstance().getTime(), "tsServer1");
            activeOperatorDAO.save(activeOperator);

        } else if (lType != null && ("close".equals(lType) || "logout".equals(lType))) {

        }

    }

    public void processClosed(WebSocketServerEvent aEvent) {

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
