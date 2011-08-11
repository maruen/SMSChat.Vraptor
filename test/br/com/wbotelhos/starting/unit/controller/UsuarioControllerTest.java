package br.com.wbotelhos.starting.unit.controller;

import static br.com.wbotelhos.starting.util.Utils.i18n;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.core.Localization;
import br.com.caelum.vraptor.util.test.MockLocalization;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.wbotelhos.starting.controller.UsuarioController;
import br.com.wbotelhos.starting.helper.Given;
import br.com.wbotelhos.starting.model.Usuario;
import br.com.wbotelhos.starting.model.common.TipoPerfil;
import br.com.wbotelhos.starting.repository.UsuarioRepository;

public class UsuarioControllerTest {

	private static final Long ID_VALIDO = 1L;

	private UsuarioController controller;

	@Spy private Result result = new MockResult();
	@Spy private Validator validator = new MockValidator();
	@Spy private Localization localization = new MockLocalization();

	@Mock private UsuarioRepository repository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		controller = new UsuarioController(result, repository, validator, localization);
	}

	@Test
	public void deveriaAtualizar() throws Exception {
		// given
		Usuario entity = Given.usuario(ID_VALIDO, "email_1@email.com", "imagem-1.jpg", "nome-1", TipoPerfil.ADMINISTRADOR, "senha-1");

		// when
		controller.atualizar(entity);

		// then
		verify(repository).save(entity);
		verify(result).include("message", i18n("usuario.atualizado.sucesso"));
	}

	@Test
	public void deveriaCriar() {
		// given
		Usuario entity = Given.usuario(ID_VALIDO, "email_1@email.com", "imagem-1.jpg", "nome-1", TipoPerfil.ADMINISTRADOR, "senha-1");

		// when
		controller.criar(entity);

		// then
		verify(result).include("perfilList", TipoPerfil.values());
		verify(result).include("entity", entity);
	}

	@Test
	public void deveriaChamarFormularioDeEdicaoSemBuscarNoBanco() {
		// given
		Usuario entity = Given.usuario(ID_VALIDO, "email_1@email.com", "imagem-1.jpg", "nome-1", TipoPerfil.ADMINISTRADOR, "senha-1");

		// when
		controller.editar(entity);

		// then
		verify(result).include("perfilList", TipoPerfil.values());
		verify(result).include("entity", entity);
	}

	@Test
	public void deveriaChamarFormularioDeEdicaoBuscandoNoBanco() {
		// given
		Usuario entity = dadoQueReceboUmUsuarioSomenteComIDParaEditar();

		// when
		controller.editar(entity);

		// then
		verify(result).include("perfilList", TipoPerfil.values());

		Usuario resulted = verify(repository).loadById(entity.getId());
		verify(result).include("entity", resulted);
	}

	@Test
	public void deveriaExibir() {
		// given
		Usuario entity = Given.usuario(ID_VALIDO, "email_1@email.com", "imagem-1.jpg", "nome-1", TipoPerfil.ADMINISTRADOR, "senha-1");

		// when
		controller.exibir(entity);

		// then
		entity = verify(repository).loadById(entity.getId());
		verify(result).include("entity", entity);
	}

	@Test
	public void deveriaListar() {
		// given

		// when
		controller.listagem();

		// then
		verify(repository).loadAll();
		verify(result).include("entityList", new ArrayList<Usuario>());
	}

	@Test
	public void deveriaRemover() {
		// given
		Usuario entity = Given.usuario(ID_VALIDO, "email_1@email.com", "imagem-1.jpg", "nome-1", TipoPerfil.ADMINISTRADOR, "senha-1");

		// when
		controller.remover(entity);

		// then
		verify(repository).remove(entity);
		verify(result).include("message", i18n("usuario.removido.sucesso"));
	}
	
	@Test
	public void deveriaSalvar() throws Exception {
		// given
		Usuario entity = Given.usuario(ID_VALIDO, "email_1@email.com", "imagem-1.jpg", "nome-1", TipoPerfil.ADMINISTRADOR, "senha-1");

		// when
		controller.salvar(entity);

		// then
		Assert.assertEquals("default.jpg", entity.getImagem());

		verify(repository).save(entity);
		verify(result).include("message", i18n("usuario.salvo.sucesso"));
	}

	private Usuario dadoQueReceboUmUsuarioSomenteComIDParaEditar() {
		return Given.usuario(ID_VALIDO, null, null, null, null, null);
	}

}