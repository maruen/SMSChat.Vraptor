package br.com.wbotelhos.starting.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.mehana.smschat.annotation.Public;
import com.mehana.smschat.component.UserSession;
import com.mehana.smschat.controller.LoginController;
import com.mehana.smschat.model.Usuario;
import com.mehana.smschat.repository.LoginRepository;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;

public class LoginControllerTest {

	private LoginController controller;
	private UserSession userSession = new UserSession();

	@Spy private Result result = new MockResult();

	@Mock private LoginRepository repository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		controller = new LoginController(result, repository, userSession);
	}

	@Test
	public void deveriaAutenticar() {
		// given
		Usuario entity = new Usuario();
		entity.setEmail("email@email.com");
		entity.setSenha("senha");

		Mockito.when(repository.autenticar(entity.getEmail(), entity.getSenha())).thenReturn(entity);

		// when
		controller.autenticar(entity);

		// then
		verify(repository).autenticar(entity.getEmail(), entity.getSenha());

		assertTrue("deve haver usuario na sessao", userSession.isLogged());
		assertEquals("deve ser o usuario correto", entity.getEmail(), userSession.getUser().getEmail());
		assertFalse("nao deve haver mensagem de error", result.included().containsKey("error"));
	}

	@Test
	public void deveriaNaoAutenticar() {
		// given
		Usuario entity = new Usuario();
		entity.setEmail("email@email.com");
		entity.setSenha("senha");

		Mockito.when(repository.autenticar(entity.getEmail(), entity.getSenha())).thenReturn(null);

		// when
		controller.autenticar(entity);

		// then
		verify(repository).autenticar(entity.getEmail(), entity.getSenha());

		assertFalse("nao deve haver usuario na sessao", userSession.isLogged());
		assertTrue("deve haver mensagem de error", result.included().containsKey("error"));
	}

	@Test
	public void deveriaLogout() {
		// given
		userSession.setUser(new Usuario());

		// when
		controller.logout();

		// then
		assertFalse("nao deve haver usuario na sessao", userSession.isLogged());
	}

	@Test
	public void deveriaEstarAnotadoComPermissaoPublicOMetodoAutenticar() throws SecurityException, NoSuchMethodException {
		// given
		Class<? extends LoginController> clazz = controller.getClass();
		Method method = clazz.getMethod("autenticar", Usuario.class);

		// when
		Public annotation = method.getAnnotation(Public.class);

		// then
		assertNotNull(annotation);
		assertTrue(method.isAnnotationPresent(Public.class));
	}

}
