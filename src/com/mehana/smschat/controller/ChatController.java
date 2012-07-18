package com.mehana.smschat.controller;

import static com.mehana.smschat.util.Utils.i18n;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import com.mehana.smschat.annotation.Public;
import com.mehana.smschat.component.UserSession;
import com.mehana.smschat.dao.SMSChatDAO;
import com.mehana.smschat.model.User;

@Resource
public class ChatController {

	private final Result result;
	private final SMSChatDAO smschatDAO;
	private final UserSession userSession;

	public ChatController(Result result, SMSChatDAO smschatDAO, UserSession userSession) {
		this.result = result;
		this.smschatDAO = smschatDAO;
		this.userSession = userSession;
	}

	@Public
	@Post("/autenticate")
	public void autenticar(User entity) {
		User user = smschatDAO.autenticate(entity.getEmail(), entity.getPassword());

		if (user != null) {
			userSession.setUser(user);

			try {
				result.redirectTo(ChatController.class).chat();
			} catch (IllegalStateException e) {
				result.redirectTo(IndexController.class).index();
			}
		} else {
			result.include("error", i18n("email.senha.incorreta")).redirectTo(IndexController.class).index();
		}
	}
	
	@Path("/chat")
	public void chat() {

	}
	

	@Get("/logout")
	public void logout() {
		userSession.logout();
		result.redirectTo(IndexController.class).index();
	}

}
