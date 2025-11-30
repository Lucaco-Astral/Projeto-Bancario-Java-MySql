package projetoBancario.utilitarios.labels;

import java.awt.Font;

import javax.swing.JLabel;

import projetoBancario.utilitarios.interfaces.Cores;

public class Texto extends JLabel {
	public Texto(String texto) {
		super(texto);

		this.setFont(new Font("Arial", Font.PLAIN, 15));
		this.setForeground(Cores.fonte());
	}
}