jws.SMSChatPlugIn = {

		NS: "com.mehana.smschat.jwebsocket.SMSChatPlugin",

		processToken: function( aToken ) {

			if( aToken.ns == jws.SMSChatPlugIn.NS ) {

//				if( aToken.reqType == "requestServerTime" ) {
//					alert( "requestServerTime: SMSChatPlugin: " + aToken );
//				}
//				
//				if (aToken.reqType == "sendSMS") {
//					var output = "";
//					for (property in aToken) {
//					  output += property + ": " + aToken[property] + "; ";
//					}
//					alert( "sendSMS: SMSChatPlugin: " + output);
//				}
			}
		},

		requestServerTime: function( aOptions ) {
			var lRes = this.createDefaultResult();
			if( this.isConnected() ) {
				var lToken = {
						ns: jws.SMSChatPlugIn.NS,
						type: "requestServerTime"
				};
				this.sendToken( lToken,  aOptions );
			} else {
				lRes.code = -1;
				lRes.localeKey = "jws.jsc.res.notConnected";
				lRes.msg = "Not connected.";
			}
			return lRes;
		},

		sendSMS: function(aToken, aOptions ) {
			var lRes = this.createDefaultResult();
			if( this.isConnected() ) {
				this.sendToken( aToken,  aOptions );
			} else {
				lRes.code = -1;
				lRes.localeKey = "jws.jsc.res.notConnected";
				lRes.msg = "Not connected.";
			}
			return lRes;
		}

}

jws.oop.addPlugIn( jws.jWebSocketTokenClient, jws.SMSChatPlugIn );