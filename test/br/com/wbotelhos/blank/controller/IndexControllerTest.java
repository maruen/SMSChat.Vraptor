package br.com.wbotelhos.blank.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;

public class IndexControllerTest {

	private IndexController controller;

	@Spy
	private Result result = new MockResult();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		controller = new IndexController(result);
	}

	@Test
	public void deveriaIndexTest() throws Exception {
		// given

		// when
		controller.index();

		// then

	}

}