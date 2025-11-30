package projetoBancario.excecoes.conta;

import projetoBancario.excecoes.ExcecoesBanco;

public class DepositoInvalido extends ExcecoesBanco {
	public DepositoInvalido(String mensagem) {
		super(mensagem);
	}
}
