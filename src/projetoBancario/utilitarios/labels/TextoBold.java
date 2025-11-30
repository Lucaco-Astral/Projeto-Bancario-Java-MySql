package projetoBancario.utilitarios.labels;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import projetoBancario.utilitarios.interfaces.Cores;
import projetoBancario.utilitarios.interfaces.Fontes;

public class TextoBold extends JLabel {
	public TextoBold(String texto) {
		super(texto);

		this.setFont(Fontes.textoBold());
		this.setForeground(Cores.fonte());
	}
}