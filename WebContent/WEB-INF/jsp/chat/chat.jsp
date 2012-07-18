<script type="text/javascript">

	var debug = false;
	var lJWSID = "CSS Chat";
	var lWSC = null;
	var eUsername = null, eMessage = null, eLog = null;
	var RECEIVESMS = 0, SENDSMS = 1, EVT = 2, SYS = "SYS",USR = null;
	var activeTab = null, activeMsisdn = null;
	var serverTime = null;
	
	
	function printJSObject(object) {
		var output = "";
		for (property in object) {
		  output += property + ": " + object[property] + "; ";
		}
		alert(output);	
	}
	
	function initJWebSocketClient() {
		if( jws.browserSupportsWebSockets() ) {
			lWSC = new jws.jWebSocketJSONClient();
		} else {
			var lMsg = jws.MSG_WS_NOT_SUPPORTED;
			alert( lMsg );
		}
		
	}
	
	function exitPage() {
		if( lWSC ) {
			lWSC.close({
				timeout: 0
			});
		}
	}
	
	function initHtmlFieldListeners() {
		eMessage  = jws.$("SMSMessage");
		jws.events.addEventListener( eMessage, "focus",  elemFocusLsnr );
		jws.events.addEventListener( eMessage, "keydown", msgKeyDnLsnr );
	}
	
	function elemFocusLsnr( aEvent ) {
		jws.events.getTarget( aEvent ).select();
	}
	
	function msgKeyDnLsnr( aEvent ) {
		if( !aEvent.shiftKey && aEvent.keyCode == 13 ) {
			sendSMS();
		}
	}
	
	
	function displayMessage( aUsername, aEvent, aMessage, aTabId, aTime ) {
		
		if( !aUsername ) { aUsername = lWSC.getUsername();}
		if( !aUsername ) { aUsername = "USR"; }
	
		tabContent = jws.$(aTabId);
		
		switch( aEvent ) {
		
			case RECEIVESMS:
				tabContent.innerHTML += "<span style=\"color:#995776;font-style: italic; font-weight: bold;\">" + aUsername +
				 " escreveu &agraves " + aTime + " &lt; </span><span style=\"color:#995776;font-weight: bold;\">" + aMessage + "</span><br/>";
				break;
			case SENDSMS:
				tabContent.innerHTML += "<span style=\"color:#617798;font-style: italic; font-weight: bold;\">" + aUsername + 
				 " escreveu &agraves " + aTime + " &gt; </span><span style=\"color:#617798;font-weight: bold;\">" + aMessage + "</span><br/>";
				break;
		}
		
		 divToScroll = jws.$(aTabId);
	     if( divToScroll.scrollHeight > divToScroll.clientHeight ) {
	    	 divToScroll.scrollTop = divToScroll.scrollHeight - divToScroll.clientHeight;
	     }
	}
	
	function doFocus( aElement ) {
		aElement.focus();
		aElement.select();
	}
	
	function setKeepAlive( aOptions ) {
		if( !aOptions ) {
			aOptions = {};
		}
		aOptions.interval = 30000; 
		lWSC.startKeepAlive( aOptions );
	}
	
	function doOpen() {
		
<%-- 		var lURL = jws.JWS_SERVER_URL + ":<%=tcpEnginePort%>" + "/;prot=json,timeout=360000"; --%>
		
		var lURL = jws.JWS_SERVER_URL + ":8787" + "/;prot=json,timeout=360000";
		
		
		
		if (debug) {
			alert("Openning web socket connection at the following URL: " + lURL);
		}
		
<%-- 		lWSC.logon( lURL, "<%=security.getUsername()%>", "", { --%>
		lWSC.logon( lURL, "Mehana", "", {	
			
			OnOpen: function( aEvent ) {
				
				if (aEvent && debug) {
					printJSObject(aEvent);
				}
				
				$("#statusImage").attr("src","/SMSChat/images/chatPage/greenLight.jpg");
				$("#statusMsg").html("<font color='green'>online</font>");
				
				setKeepAlive({ immediate: false });
			},
	
			OnMessage: function( aEvent, aToken ) {
				
				if (aToken && aToken.reqType != 'ping' && debug) {
					printJSObject(aToken);
				}
				
				if( aToken ) {
					if( aToken.type == "response" ) {
						
						if( aToken.reqType == "login" ) {
							
							if( aToken.code == 0) {
								
								// login suscessfull
								
							} else if ( aToken.code != 0 ) {
				
								// login failed
							}
							
							
						} else if (aToken.reqType == "sendSMS") { 
							displayMessage( aToken.username, SENDSMS, aToken.message, "tab"+aToken.msisdn,aToken.time); 
					    } else if( aToken.reqType == "getClients" ) {
							// display the clients that are online
						} 
						
					} else if( aToken.type == "event" ) {
						
						if (aToken.name == "sms") {
							
							if(debug) {
								alert("SMS Event Received");
							}
							
							if ( $("#tab"+aToken.msisdn).length == 0 ) {
								createTab(aToken.msisdn,aToken.msisdn);
							}									    
							
							displayMessage( aToken.msisdn, RECEIVESMS, aToken.data,"tab"+aToken.msisdn,aToken.time);
						} 
						
					} else if( aToken.type == "goodBye" ) {
						// client was disconected by aToken.reason
						
					} else if( aToken.type == "broadcast" ) {
						
						if (debug) {
							alert("entering in aToken.type == broadcast");
						}
						
						if( aToken.data ) {
							// displayMessage( aToken.sender, IN,
							// aToken.data );
						}
					}
				}
			},
	
			OnClose: function( aEvent ) {
				lWSC.stopKeepAlive();
				// client disconected message
			}
			
		});
	}
	
	function doClose() {
		lWSC.stopKeepAlive(); // maybe remove it cause OnClose method
								// already has it
		var lRes = lWSC.close({timeout: 3000}); // wait a maximum of 3
												// seconds for server good
												// bye message
		// displayMessage( SYS, OUT, "logout: " + lRes.msg );
	}
	
	
	function sendSMS() {
		
		var aToken = {
				ns: jws.SMSChatPlugIn.NS,
				type: "sendSMS",
				mobileMsisdn: activeMsisdn,
<%-- 				modem3gMsisdn: "<%=security.getModem3gMsisdn().toString()%>", --%>
<%-- 				username: "<%=security.getUsername()%>", --%>
				message: $("#SMSMessage").val()
		};
		var aOptions = aOptions = {};
		lWSC.sendSMS(aToken,aOptions);
		$("#SMSMessage").val("");
	}
	
	
	function createTab(tabId,tabLabel){
		
		$(".box").hide();
		$(".chatSection").show();
		
		li = document.createElement("li");
		ahref = document.createElement("a");
		$(ahref).attr("href","#tab"+tabId);
		$(ahref).html(tabLabel);
		
		$(li).attr("class","active");
		$(li).append(ahref);
		$("ul.tabs li").removeClass("active"); 
		$("#tabsButtons").append(li);
					
		div = document.createElement("div");
		$(div).attr("class", "tab_content");
		$(div).attr("id","tab" + tabId);
		 
		$(".tab_content").hide();
		$("#tabsContents").append(div);
		
		activeTab = "#tab" + tabId;
		activeMsisdn = tabId; 
		$(activeTab).fadeIn();
		
	}
	
	
	$(window).load(	function() {
		initJWebSocketClient();
		initHtmlFieldListeners();
		doOpen();
	});
	
	$(window).unload(function() {
		exitPage(); 
	});
	
	
	$(document).ready(function() {
		
		$("ul.tabs li").livequery('click',function(event) {
			$("ul.tabs li").removeClass("active"); 
			$(this).addClass("active"); 
			$(".tab_content").hide(); 
			activeTab = $(this).find("a").attr("href");
			activeMsisdn = activeTab.substring(4, activeTab.length); 
			$(activeTab).fadeIn();
			return false;
		});
	
	
	});
	
	function openSMSSimulatorPage() {
		window.open('./SMSSimulator.jsp','mywin','left=(screen.width-500)/2,top=(screen.height-500)/2,width=500,height=500,toolbar=1,resizable=0');
	}
	
	function alertTest() {
		alert("teste");
	}
	
	
</script>

<body>
	<c:if test="${userSession.user != null}">
		<br />
		<div class="box">Não existem usuários no chat.</div>
		 <a href="javascript:alertTest();" title="Simular SMS">Simular SMS</a>
		
		<div class="chat">

			<ul class="tabs" id="tabsButtons"></ul>
			<div class="tab_container" id="tabsContents"></div>

			<div class="textInput">
				<table>
					<tr>
						<td valign="middle" class="msg1" width="5">Mensagem</td>
						<td valign="top" style="padding-right: 12pt">
							<textarea id="SMSMessage" cols="255 " rows="2" style="width: 100%"></textarea>
						</td>
						<td valign="middle" width="5">
							<input class="sbtnDlg" id="sbtnSend" type="button" value="Enviar"
							onclick="javascript:sendSMS();" style="margin-left: 6pt">
						</td>
					</tr>
				</table>
			</div>
		</div>

	</c:if>

</body>