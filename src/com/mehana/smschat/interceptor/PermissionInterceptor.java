package com.mehana.smschat.interceptor;

import static br.com.caelum.vraptor.view.Results.http;
import static com.mehana.smschat.util.Utils.i18n;

import java.util.Arrays;
import java.util.Collection;

import com.mehana.smschat.annotation.Permission;
import com.mehana.smschat.annotation.Public;
import com.mehana.smschat.component.UserSession;
import com.mehana.smschat.controller.IndexController;
import com.mehana.smschat.model.common.Perfil;

import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;

@Intercepts
public class PermissionInterceptor implements Interceptor {

	private final Result result;
	private final UserSession userSession;
	
	public PermissionInterceptor(Result result, UserSession userSession) {
		this.result = result;
		this.userSession = userSession;
	}

	public boolean accepts(ResourceMethod method) {
		return !(method.getMethod().isAnnotationPresent(Public.class) || method.getResource().getType().isAnnotationPresent(Public.class));
	}

	public void intercept(InterceptorStack stack, ResourceMethod method, Object resource) {
		if (userSession.isLogged()) {
			Permission methodPermission = method.getMethod().getAnnotation(Permission.class);
			Permission controllerPermission = method.getResource().getType().getAnnotation(Permission.class);
			
			if (this.hasAccess(methodPermission) && this.hasAccess(controllerPermission)) {
				stack.next(method, resource);
			} else {
				result.use(http()).sendError(500, i18n("voce.nao.tem.permissao.para.tal.acao"));
			}
		} else {
			result.redirectTo(IndexController.class).index();
		}
	}

	private boolean hasAccess(Permission permission) {
		if (permission == null) {
			return true;
		}

		Collection<Perfil> perfilList = Arrays.asList(permission.value());

		return perfilList.contains(userSession.getUser().getPerfil());
	}

}
