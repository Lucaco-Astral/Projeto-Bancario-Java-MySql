package projetoBancario.contaBanco;

import java.awt.event.ActionEvent;

public abstract class Menu {
	private int opcoes;
	private String mensagemMenu;

	public int getOpcoes() {
		return opcoes;
	}

	public void setOpcoes(int opcoes) {
		this.opcoes = opcoes;
	}

	public String getMensagemMenu() {
		return mensagemMenu;
	}

	public void setMensagemMenu(String mensagemMenu) {
		this.mensagemMenu = mensagemMenu;
	}

	public void executar() {
		executarMenu();
	}

	protected abstract void executarMenu();

	protected abstract void avaliarOpcaoEscolhida(ActionEvent e);

	public abstract void trocarTela(String tela);
}