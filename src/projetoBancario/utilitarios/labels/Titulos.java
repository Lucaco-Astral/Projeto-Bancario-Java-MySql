package projetoBancario.utilitarios.labels;

import java.awt.Font;

import javax.swing.JLabel;

import projetoBancario.utilitarios.interfaces.Cores;

public class Titulos extends JLabel {
	public Titulos(String texto) {
		super(texto);
		this.setHorizontalAlignment(Titulos.CENTER);

		this.setFont(new Font("Arial", Font.BOLD, 30));
		this.setForeground(Cores.fonte());
	}
}
