package projetoBancario.sessao;

import projetoBancario.informacoes.Usuario;

public class Sessao {
	private static Usuario logado;

	public static Usuario getLogado() {
		return logado;
	}

	public static void setLogado(Usuario logado) {
		Sessao.logado = logado;
	}
}