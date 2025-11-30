package projetoBancario.excecoes.excessoesLogin;

import projetoBancario.excecoes.ExcecoesBanco;

public class EmailInvalido extends ExcecoesBanco {
	public EmailInvalido(String mensagem) {
		super(mensagem);
	}
}