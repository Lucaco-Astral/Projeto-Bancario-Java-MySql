package projetoBancario.contaBanco;

import projetoBancario.excecoes.ExcecoesBanco;
import projetoBancario.excecoes.conta.SaqueExcedido;
import projetoBancario.excecoes.conta.SaqueInvalido;
import projetoBancario.excecoes.contaCorrente.*;
import projetoBancario.utilitarios.Moeda;

// Classe ContaCorrente
public class ContaCorrente extends Conta {
	private double limiteEspecial;

	// Getter da variável limiteEspecial
	public double getLimiteEspecial() {
		return limiteEspecial;
	}

	// Setter da variável LimiteEspecial com assinatura para caso digite números
	// negativos
	public void setLimiteEspecial(double limiteEspecial) throws ExcecoesBanco {
		if (limiteEspecial < 0) {
			throw new EspecialInvalido("Limite especial não pode ser negativo");
		}

		this.limiteEspecial = limiteEspecial;
	}

	@Override
	public void sacar(double valor) throws ExcecoesBanco {
		if (valor <= 0) {
			throw new SaqueExcedido("Valor de saque não pode ser negativo ou zero: " + Moeda.formatar(valor));
		}

		double novoValor = getSaldo() - valor;

		if (novoValor < -getLimiteEspecial()) {
			throw new SaqueInvalido("Valor de saque ultrapassa o saldo com limite especial");
		}

		setSaldo(novoValor);
	}

	// Construtor da classe ContaCorrente com assinatura para caso digite números
	// negativos
	public ContaCorrente(double limiteEspecial, double saldoInicial) throws ExcecoesBanco {
		if (limiteEspecial <= 0) {
			throw new EspecialInvalido("Limite especial não pode ser igual ou abaixo de 0");
		}

		if (saldoInicial < 0) {
			throw new SaldoInvalido("Saldo inicial não pode ser abaixo de 0");
		}

		setSaldo(saldoInicial);
		this.limiteEspecial = limiteEspecial;
	}

	// Método que sobre-escreve atualizarSaldo colocando juros de 8%
	@Override
	public void atualizarSaldo() {
		if (getSaldo() < 0) {
			double juros = getSaldo() * 0.08;
			setSaldo(juros + getSaldo());
		}
	}

	@Override
	public String toString() {
		return "Conta corrente:" + "\nSaldo: " + Moeda.formatar(getSaldo()) + "\nLimite especial: "
				+ Moeda.formatar(getLimiteEspecial());
	}
}
