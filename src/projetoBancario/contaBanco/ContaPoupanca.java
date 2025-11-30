package projetoBancario.contaBanco;

import projetoBancario.excecoes.ExcecoesBanco;
import projetoBancario.excecoes.contaPoupanca.*;
import projetoBancario.utilitarios.Moeda;

//Classe ContaPoupanca
public class ContaPoupanca extends Conta {
	private double reajusteMensal;

	// Getter do ReajusteMensal
	public double getReajusteMensal() {
		return reajusteMensal;
	}

	// Setter do ReajusteMensal assinatura para caso o valor digitado seja negativo
	public void setReajusteMensal(double reajusteMensal) throws ExcecoesBanco {
		if (reajusteMensal < 0) {
			throw new ReajusteInvalido("Valor de reajuste não pode ser abaixo de 0");
		}
		this.reajusteMensal = reajusteMensal;
	}

	// Construtor da classe ContaPoupanca com assinatura para o reajuste e saldo
	// inicial não seja abaixo ou igual a 0
	public ContaPoupanca(double saldoInicial, double reajuste) throws ExcecoesBanco {
		if (saldoInicial < 0) {
			throw new SaldoInvalido("Valor de saldo inicial abaixo de 0");
		}

		if (reajuste < 0) {
			throw new ReajusteInvalido("Reajuste mensal inválido");
		}

		setSaldo(saldoInicial);
		this.reajusteMensal = reajuste;
	}

	// Método atualizarSaldo: Da bonús caso a conta esteja positiva
	public void atualizarSaldo(double reajusteMensal) throws ExcecoesBanco {
		if (reajusteMensal < 0) {
			throw new ReajusteInvalido("Reajuste mensal não pode ser abaixo de 0");
		}

		setReajusteMensal(reajusteMensal);

		if (getSaldo() > 0) {
			double bonus = (getSaldo() * reajusteMensal) / 100;
			setSaldo(getSaldo() + bonus);
		}
	}

	@Override
	public String toString() {
		return "Conta corrente:" + "\nSaldo: " + Moeda.formatar(getSaldo()) + "\nReajuste mensal: "
				+ getReajusteMensal() + "%";
	}
}