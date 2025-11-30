package projetoBancario.utilitarios;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class FiltroEmail extends DocumentFilter {
	private final String PERMITIDO = "[^0-9-a-zA-Z@._-]";
	private final int LIMITE = 100;

	@Override
	public void insertString(FilterBypass fb, int offset, String email, AttributeSet attr) throws BadLocationException {

		if (email == null) {
			return;
		}

		String emailFiltrado = email.replaceAll(PERMITIDO, "");

		if ((fb.getDocument().getLength() + emailFiltrado.length()) <= LIMITE) {
			super.insertString(fb, offset, emailFiltrado, attr);
		}
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String email, AttributeSet attrs)
			throws BadLocationException {

		if (email == null) {
			super.replace(fb, offset, length, email, attrs);
			return;
		}

		String emailFiltrado = email.replaceAll(PERMITIDO, "");

		int tamanho = fb.getDocument().getLength() - length + emailFiltrado.length();

		if (tamanho <= LIMITE) {
			super.replace(fb, offset, length, emailFiltrado, attrs);
		}
	}
}