package projetoBancario.utilitarios;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class FiltraSenha extends DocumentFilter {
	private final String PERMITIDO = "[^0-9-a-z-A-Z]";
	private final int LIMITE = 20;

	@Override
	public void insertString(FilterBypass fb, int offset, String senha, AttributeSet attr) throws BadLocationException {

		if (senha == null) {
			return;
		}

		if ((fb.getDocument().getLength() + senha.length()) <= LIMITE) {
			String senhaFiltrada = senha.replaceAll(PERMITIDO, "");
			super.insertString(fb, offset, senhaFiltrada, attr);
		}
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String senha, AttributeSet attrs)
			throws BadLocationException {

		if (senha == null) {
			super.replace(fb, offset, length, senha, attrs);
			return;
		}

		String senhaFiltrada = senha.replaceAll(PERMITIDO, "");

		int tamanho = fb.getDocument().getLength() - length + senhaFiltrada.length();

		if (tamanho <= LIMITE) {
			super.replace(fb, offset, length, senhaFiltrada, attrs);
		}
	}
}