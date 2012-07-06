package com.mehana.smschat.integration;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import com.jintegrity.core.JIntegrity;
import com.jintegrity.helper.JPAHelper;
import com.mehana.smschat.business.LoginBusiness;
import com.mehana.smschat.helper.Given;
import com.mehana.smschat.model.Usuario;
import com.mehana.smschat.repository.LoginRepository;

public class LoginBusinessTest {

	private final JIntegrity helper = new JIntegrity();

	private LoginRepository repository;

	@BeforeClass
	public static void beforeClass() {
		JPAHelper.entityManagerFactory("default");
	}

	@Before
	public void setup() {
		helper.cleanAndInsert();
		repository = new LoginBusiness(JPAHelper.currentEntityManager());
	}

	@After
	public void tearDown() {
		JPAHelper.close();
		helper.clean();
	}

	@Test
	public void deveriaAutenticar() {
		// given
		Usuario entity = Given.usuario(1l);

		// when
		Usuario actual = repository.autenticar(entity.getEmail(), entity.getSenha());

		// then
		assertNotNull("deveria encontrar o usuario", actual);
	}

	@Test
	public void deveriaNaoAutenticar() {
		// given
		Usuario entity = Given.usuario(4l);

		// when
		Usuario actual = repository.autenticar(entity.getEmail(), entity.getSenha());

		// then
		assertNull("nao deveria encontrar o usuario", actual);
	}

}
