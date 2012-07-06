package com.mehana.smschat.controller;

import static br.com.caelum.vraptor.view.Results.referer;
import static com.mehana.smschat.util.Utils.i18n;

import com.mehana.smschat.annotation.Public;
import com.mehana.smschat.component.UserSession;
import com.mehana.smschat.model.Usuario;
import com.mehana.smschat.repository.LoginRepository;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class LoginController {

	private final Result result;
	private final LoginRepository repository;
	private final UserSession userSession;

	public LoginController(Result result, LoginRepository repository, UserSession userSession) {
		this.result = result;
		this.repository = repository;
		this.userSession = userSession;
	}

	@Public
	@Post("/autenticar")
	public void autenticar(Usuario entity) {
		Usuario user = repository.autenticar(entity.getEmail(), entity.getSenha());

		if (user != null) {
			userSession.setUser(user);

			try {
				result.use(referer()).redirect();
			} catch (IllegalStateException e) {
				result.redirectTo(IndexController.class).index();
			}
		} else {
			result.include("error", i18n("email.senha.incorreta")).redirectTo(IndexController.class).index();
		}
	}

	@Get("/logout")
	public void logout() {
		userSession.logout();
		result.redirectTo(IndexController.class).index();
	}

}
