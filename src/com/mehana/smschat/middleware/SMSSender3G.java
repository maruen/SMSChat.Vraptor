package com.mehana.smschat.middleware;

import java.io.IOException;

import com.mehana.smschat.object.SMSMessage;

/**
 * 
 * @author maruen
 * email: maruen@gmail.com
 *
 */

public class SMSSender3G extends Thread {
	private SMSMessage smsMessage;
	
	public SMSSender3G(SMSMessage smsMessage) {
		setDaemon(true);
		this.smsMessage = smsMessage;
	}
	
	public void run() {
	
		try {
			
			StringBuffer configFile = new StringBuffer();
			configFile.append("/home/maruen/Local/gammu/");
			configFile.append(smsMessage.getModem3gMsisdn());
			configFile.append("/config/gammu-smsdrc");

			StringBuffer cmmd = new StringBuffer();	
			cmmd.append("echo \"").append(smsMessage.getText()).append("\" | ");
			cmmd.append("gammu-smsd-inject -c ").append(configFile);
			cmmd.append( " TEXT ").append(smsMessage.getMobileMsisdn());
			
			execute(cmmd.toString());
	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void execute(String cmmd) throws IOException {
		
		System.out.println("\nRunning the command: " + cmmd +"\n");
		
		ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmmd.toString());
		pb.redirectErrorStream(true); 
		pb.start();
		
	}

}
