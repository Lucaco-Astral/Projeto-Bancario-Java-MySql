package projetoBancario.excecoes.excecoesCadastro;

import projetoBancario.excecoes.ExcecoesBanco;

public class EmailInvalido extends ExcecoesBanco {
	public EmailInvalido(String mensagem) {
		super(mensagem);
	}
}