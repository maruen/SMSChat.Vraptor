<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<link type="image/x-icon" rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico"/>

		<meta name="description" content="Chat by SMS" />
		<meta name="author" content="Maruen Mehana"/>
		<meta name="keywords" content="SMSChat"/>

		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesheet.css"/>

		<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/jquery.livequery.js"></script>
		<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
		<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/jquery.colorbox-1.3.16.min.js"></script>
		<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/javascript.js"></script>
		
		
		<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/jWebSocket.js"></script>
		<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/SMSChatPlugin.js"></script>

		<script type="text/javascript" charset="utf-8">
	
			var debug = false;
			var lJWSID = "SMSChat";
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
				
				var lURL = jws.JWS_SERVER_URL + ":" + ${userSession.config.value} + "/;prot=json,timeout=360000";
						
				if (debug) {
					alert("Openning web socket connection at the following URL: " + lURL);
				}
				
				lWSC.logon( lURL, "${userSession.user.username}", "", {
					
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
										
										//login suscessfull
										
									} else if ( aToken.code != 0 ) {
						
										//login failed
									}
									
									
								} else if (aToken.reqType == "sendSMS") { 
									displayMessage( aToken.username, SENDSMS, aToken.message, "tab"+aToken.msisdn,aToken.time); 
							    } else if( aToken.reqType == "getClients" ) {
									//display the clients that are online
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
								//client was disconected by aToken.reason
								
							} else if( aToken.type == "broadcast" ) {
								
								if (debug) {
									alert("entering in aToken.type == broadcast");
								}
								
								if( aToken.data ) {
									//displayMessage( aToken.sender, IN, aToken.data );
								}
							}
						}
					},
		
					OnClose: function( aEvent ) {
						lWSC.stopKeepAlive();
						//client disconected message
					}
					
				});
			}
			
			function doClose() {
				lWSC.stopKeepAlive(); // maybe remove it cause OnClose method already has it
				var lRes = lWSC.close({timeout: 3000}); // wait a maximum of 3 seconds for server good bye message
				//displayMessage( SYS, OUT, "logout: " + lRes.msg );
			}
		
	// 		function broadcast() {
	// 			var lMsg = eMessage.value;
	// 			if( lMsg.length > 0 ) {
	// 				displayMessage( USR, OUT, lMsg );
	// 				var lRes = lWSC.broadcastText("",lMsg); 	//the first field would be the user to send the message.
	// 				if( lRes.code != 0 ) { 						// log error only, on success don't confuse the user
	// 					displayMessage( SYS, OUT, "broadcast: " + lRes.msg );
	// 				}
	// 				eMessage.value = "";
	// 			}
	// 			doFocus( eMessage );
	// 		}
			
			
			function sendSMS() {
				
				var aToken = {
						ns: jws.SMSChatPlugIn.NS,
						type: "sendSMS",
						mobileMsisdn: activeMsisdn,
						modem3gMsisdn: "${userSession.user.modem3gMsisdn}",
						username: "${userSession.user.username}",
						message: $("#SMSMessage").val()
				};
				var aOptions = aOptions = {};
				lWSC.sendSMS(aToken,aOptions);
				$("#SMSMessage").val("");
			}
			
			
	// 		function sendText() {  
	//             var lMsg = eMessage.value;  
	//             if( lMsg.length > 0 ) {  
	<%--                 var lRes = lWSC.sendText("<%=username%>",lMsg); --%>
	//                 if( lRes.code != 0 ) {  
	//                	Error Message!
	//                 	displayMessage( SYS, OUT, "SendMessage: " + lRes.msg );
	//                 }  
	  
	//             }  
	//             doFocus( eMessage );  
	//         }  
			
		
			
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
				
				window.open('${pageContext.request.contextPath}/SMSSimulator','mywin','left=(screen.width-500)/2,top=(screen.height-500)/2,width=500,height=500,toolbar=1,resizable=0');
			}
		

		</script>
		
		<title><decorator:title default="SMSChat"/></title>

	</head>

	<body>
		<div id="mensagem"></div>

		<div id="wrapper">
			<div id="topo"><%@ include file="/topo.jsp" %></div>
			<div id="menu"><%@ include file="/menu.jsp" %></div>
	
			<div id="content">
				<c:if test="${not empty errors}">
					<div id="errors" class="error">
						<c:forEach var="error" items="${errors}">
					  		${error.category} - ${error.message}<br/>
						</c:forEach>
					</div>
				</c:if>
		
				<c:if test="${not empty error}">
					<div id="error" class="error">${error}</div>
				</c:if>
		
				<c:if test="${not empty message}">
					<div id="notice" class="notice">${message}</div>
				</c:if>

				<decorator:body/>
			</div>
	
			<div id="rodape"><%@ include file="/rodape.jsp" %></div>
		</div>
		
	</body>
</html>
