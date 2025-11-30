package projetoBancario.utilitarios.interfaces;

import java.awt.Font;

public class Fontes {
	public static Font titulos() {
		return new Font("Arial", Font.BOLD, 30);
	}

	public static Font texto() {
		return new Font("Arial", Font.PLAIN, 15);
	}

	public static Font textoBold() {
		return new Font("Arial", Font.BOLD, 15);
	}

	public static Font botoes() {
		return new Font("Arial", Font.BOLD, 16);
	}
}