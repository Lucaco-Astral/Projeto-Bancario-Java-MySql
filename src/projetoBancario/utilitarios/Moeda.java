package projetoBancario.utilitarios;

import java.text.NumberFormat;
import java.util.Locale;

public class Moeda {
	private static final NumberFormat MOEDA = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));

	public static String formatar(double valor) {
		return MOEDA.format(valor);
	}
}