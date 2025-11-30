package projetoBancario.utilitarios.botoes;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import projetoBancario.utilitarios.interfaces.Cores;
import projetoBancario.utilitarios.interfaces.Fontes;

public class BotaoSaldo extends JButton {
	public BotaoSaldo(ImageIcon nome) {
		super(nome);

		this.setBorder(null);
		this.setFont(Fontes.botoes());
		this.setBackground(null);
		this.setForeground(Cores.fonte());
		this.setBorder(null);
	}
}