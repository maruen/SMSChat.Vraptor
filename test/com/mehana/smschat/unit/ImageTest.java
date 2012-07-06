package com.mehana.smschat.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mehana.smschat.util.Image;


public class ImageTest {

	private String fileName;

	@Test
	public void deveriaRetornarEstensao() throws Exception {
		// given
		dadoQueTenhoOArquivo("image.jpg");

		// when
		String extensao = Image.getExtension(fileName);

		// then
		assertEquals(".jpg", extensao);
	}

	@Test
	public void deveriaRetornarEstensaoMinuscula() throws Exception {
		// given
		dadoQueTenhoOArquivo("image.PNG");

		// when
		String extensao = Image.getExtension(fileName);

		// then
		assertEquals(".png", extensao);
	}

	@Test
	public void deveriaRetornarEstensaoMinusculaResumida() throws Exception {
		// given
		dadoQueTenhoOArquivo("image.JpEg");

		// when
		String extensao = Image.getExtension(fileName);

		// then
		assertEquals(".jpg", extensao);
	}

	@Test
	public void deveriaSerUmaEstensaoValida() throws Exception {
		// given
		dadoQueTenhoOArquivo("image.JpEg");

		// when
		boolean result = Image.isValidFile(fileName);

		// then
		assertTrue(result);
	}

	@Test
	public void deveriaSerUmaEstensaoInvalida() throws Exception {
		// given
		dadoQueTenhoOArquivo("image.txt");

		// when
		boolean result = Image.isValidFile(fileName);

		// then
		assertFalse(result);
	}

	private void dadoQueTenhoOArquivo(String fileName) {
		this.fileName = fileName;
	}

}
