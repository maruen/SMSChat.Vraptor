package br.com.mehana.chat.server;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.kit.PlugInResponse;
import org.jwebsocket.logging.Logging;
import org.jwebsocket.plugins.TokenPlugIn;
import org.jwebsocket.token.Token;

import br.com.mehana.chat.dao.SMSChatDAO;
import br.com.mehana.chat.middleware.SMSSender3G;
import br.com.mehana.chat.model.MsgHist;
import br.com.mehana.chat.object.SMSMessage;
import br.com.mehana.chat.utils.EscapeChars;

/**
 * 
 * @author maruen
 * email: maruen@gmail.com
 *
 */

public class SMSChatPlugin extends TokenPlugIn {


	private static Logger log = Logging.getLogger(SMSChatPlugin.class);
	private static String NS_CSS_CHAT_PLUGIN = "br.com.mehana.chat.server.plugins.SMSChatPlugin";
	private static String CSS_CHAT_PLUGIN_VAR = NS_CSS_CHAT_PLUGIN + ".started";
	private SMSChatDAO cssChatDAO = new SMSChatDAO();
	private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

	public SMSChatPlugin() {
		
		if (log.isDebugEnabled()) {
			log.debug("Instantiating SMSChatPlugin...");
		}

		this.setNamespace(NS_CSS_CHAT_PLUGIN);
	}
	
	
	public void connectorStarted(WebSocketConnector aConnector) {
		 String dateAsString = new Date().toString();
		 log.info("The connector " + aConnector.getUsername() + " started at " + dateAsString);   
		 aConnector.setVar(CSS_CHAT_PLUGIN_VAR, dateAsString);
	 }

	public void processToken(PlugInResponse aResponse, WebSocketConnector aConnector, Token aToken) {

		String lType = aToken.getType();
		String lNS = aToken.getNS();
		
		log.info("Processing token: " + aToken + " from connector: " + aConnector.getUsername());
		
		if (lType != null && lNS != null && lNS.equals(getNamespace())) {
			
			if (lType.equals("requestServerTime")) {

				Token lResponse = createResponse(aToken);
				lResponse.put("time", sdf.format(new Date()));
				sendToken( aConnector,aConnector, lResponse);
			
			} else if (lType.equals("sendSMS")) {
				//JMSMessage jmsMessage = new JMSMessage();
				SMSMessage smsMessage = new SMSMessage();
				
				String escapedMsg = EscapeChars.escapeCharsToSMS(aToken.getString("message"));
				smsMessage.setMobileMsisdn(aToken.getString("mobileMsisdn"));
				smsMessage.setModem3gMsisdn(aToken.getString("modem3gMsisdn"));
				smsMessage.setText(escapedMsg);
				
				log.info("Inserting into table CSS_MSG_HIST: [MSISDN: " + aToken.getString("msisdn") + 
						", MESSAGE: " + aToken.getString("message") + "]");
				
				MsgHist cssMsgHist = new MsgHist();
				cssMsgHist.setDt_sys(Calendar.getInstance().getTime());
				cssMsgHist.setSession_id(1L);
				cssMsgHist.setMsisdn(aToken.getString("mobileMsisdn"));
				cssMsgHist.setOperator(aToken.getString("username"));
				cssMsgHist.setMsg_orig(aToken.getString("message"));
				cssMsgHist.setOriginator(aToken.getString("username"));
				cssMsgHist.setMsg_norm(escapedMsg);
				cssMsgHist.setDt_plat(Calendar.getInstance().getTime());
				cssChatDAO.insertToMsgHist(cssMsgHist);
				
				Token lResponse = createResponse(aToken);
				lResponse.put("username",aConnector.getUsername());
				lResponse.put("message",aToken.getString("message"));
				lResponse.put("msisdn",aToken.getString("mobileMsisdn"));
				lResponse.put("time", sdf.format(new Date()));
				sendToken( aConnector,aConnector, lResponse);
				
				new SMSSender3G(smsMessage).start();
				
//				jmsMessage.setMessage(smsMessage);
//				try {
//					JMS.postRequest(jmsMessage);
//				} catch (NamingException e) {
//					e.printStackTrace();
//				} catch (JMSException e) {
//					e.printStackTrace();
//				}
			}
		}
	}

}
