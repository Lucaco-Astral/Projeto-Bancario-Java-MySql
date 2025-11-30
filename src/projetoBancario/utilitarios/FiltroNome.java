package projetoBancario.utilitarios;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class FiltroNome extends DocumentFilter {
	private final String PERMITIDO = "[^a-zA-Z\\s]";
	private int LIMITE = 15;

	@Override
	public void insertString(FilterBypass fb, int offset, String nome, AttributeSet attr) throws BadLocationException {
		if (nome == null) {
			return;
		}

		String nomeFiltrado = nome.replaceAll(PERMITIDO, "");

		if ((fb.getDocument().getLength() + nomeFiltrado.length()) <= LIMITE) {
			super.insertString(fb, offset, nomeFiltrado, attr);
		}
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String nome, AttributeSet attrs)
			throws BadLocationException {

		if (nome == null) {
			super.replace(fb, offset, length, nome, attrs);
			return;
		}

		String nomeFiltrado = nome.replaceAll(PERMITIDO, "");

		int comprimento = fb.getDocument().getLength() - length + nomeFiltrado.length();

		if (comprimento <= LIMITE) {
			super.replace(fb, offset, length, nomeFiltrado, attrs);
		}
	}
}