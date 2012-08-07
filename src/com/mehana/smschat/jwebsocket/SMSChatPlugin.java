package com.mehana.smschat.jwebsocket;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.kit.PlugInResponse;
import org.jwebsocket.plugins.TokenPlugIn;
import org.jwebsocket.token.Token;

import br.com.caelum.vraptor.Resource;

import com.mehana.smschat.dao.GenericDAO;
import com.mehana.smschat.middleware.SMSSender3G;
import com.mehana.smschat.model.MsgHist;
import com.mehana.smschat.object.SMSMessage;
import com.mehana.smschat.util.EscapeChars;

/**
 * 
 * @author maruen email: maruen@gmail.com
 * 
 */

@Resource
public class SMSChatPlugin extends TokenPlugIn {
   
    private static String           NS_CSS_CHAT_PLUGIN  = "br.com.mehana.chat.server.plugins.SMSChatPlugin";
    private static String           CSS_CHAT_PLUGIN_VAR = NS_CSS_CHAT_PLUGIN + ".started";
    private GenericDAO<MsgHist>     msgHistDAO;
    private static SimpleDateFormat sdf                 = new SimpleDateFormat("HH:mm:ss");

    public SMSChatPlugin() {

       

        this.setNamespace(NS_CSS_CHAT_PLUGIN);
    }

    public void connectorStarted(WebSocketConnector aConnector) {
        String dateAsString = new Date().toString();
        aConnector.setVar(CSS_CHAT_PLUGIN_VAR, dateAsString);
    }

    public void processToken(PlugInResponse aResponse, WebSocketConnector aConnector, Token aToken) {

        String lType = aToken.getType();
        String lNS = aToken.getNS();

        
        if (lType != null && lNS != null && lNS.equals(getNamespace())) {

            if (lType.equals("requestServerTime")) {

                Token lResponse = createResponse(aToken);
                lResponse.put("time", sdf.format(new Date()));
                sendToken(aConnector, aConnector, lResponse);

            } else if (lType.equals("sendSMS")) {
                SMSMessage smsMessage = new SMSMessage();

                String escapedMsg = EscapeChars.escapeCharsToSMS(aToken.getString("message"));
                smsMessage.setMobileMsisdn(aToken.getString("mobileMsisdn"));
                smsMessage.setModem3gMsisdn(aToken.getString("modem3gMsisdn"));
                smsMessage.setText(escapedMsg);
          
                MsgHist msgHist = new MsgHist();
                msgHist.setDt_sys(Calendar.getInstance().getTime());
                msgHist.setSession_id(1L);
                msgHist.setMsisdn(aToken.getString("mobileMsisdn"));
                msgHist.setOperator(aToken.getString("username"));
                msgHist.setMsg_orig(aToken.getString("message"));
                msgHist.setOriginator(aToken.getString("username"));
                msgHist.setMsg_norm(escapedMsg);
                msgHist.setDt_plat(Calendar.getInstance().getTime());
                msgHistDAO.save(msgHist);

                Token lResponse = createResponse(aToken);
                lResponse.put("username", aConnector.getUsername());
                lResponse.put("message", aToken.getString("message"));
                lResponse.put("msisdn", aToken.getString("mobileMsisdn"));
                lResponse.put("time", sdf.format(new Date()));
                sendToken(aConnector, aConnector, lResponse);

                new SMSSender3G(smsMessage).start();

            }
        }
    }

}
