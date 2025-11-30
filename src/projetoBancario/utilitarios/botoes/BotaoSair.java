package projetoBancario.utilitarios.botoes;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

import projetoBancario.utilitarios.interfaces.Cores;
import projetoBancario.utilitarios.interfaces.Dimensao;
import projetoBancario.utilitarios.interfaces.Fontes;

public class BotaoSair extends JButton {
	private Border borda = BorderFactory.createLineBorder(new Color(37, 161, 142), 2);

	public BotaoSair(String nome) {
		super(nome);

		this.setPreferredSize(Dimensao.botaoSair());
		this.setFont(Fontes.botoes());
		this.setBackground(Cores.fundoBotoes());
		this.setForeground(Cores.fonte());
		this.setBorder(borda);
	}
}