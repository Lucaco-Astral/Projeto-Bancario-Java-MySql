package projetoBancario.contaBanco;

import projetoBancario.excecoes.conta.*;
import projetoBancario.utilitarios.Moeda;
import projetoBancario.excecoes.ExcecoesBanco;

// Classe Conta
public abstract class Conta {
	private double saldo;

	// Getter e Setter da variável Saldo
	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	// Método Sacar com assinatura para saques negativos ou acima do disponível
	public void sacar(double valor) throws ExcecoesBanco {
		if (valor <= 0) {
			throw new SaqueExcedido("Valor de saque não pode ser abaixo negativo ou zero: " + valor);
		}

		if (getSaldo() < valor) {
			throw new SaqueInvalido("Valor de saque (" + Moeda.formatar(valor) + ") ultrapassa saldo disponível ("
					+ Moeda.formatar(getSaldo()) + ")");
		}

		double saque = getSaldo() - valor;
		setSaldo(saque);
	}

	// Método Depositar com assinatura para deposito igual ou abaixo de 0
	public void depositar(double valor) throws ExcecoesBanco {
		if (valor < 0) {
			throw new DepositoInvalido("Valor de deposito não pode ser abaixo negativo: " + valor);
		}

		if (valor == 0) {
			throw new DepositoInvalido("Valor de deposito não pode ser 0");
		}

		double deposito = getSaldo() + valor;
		setSaldo(deposito);
	}

	// Método que atualiza o saldo colocando juros caso o saldo esteja negativo
	public void atualizarSaldo() {
		if (getSaldo() < 0) {
			double juros = getSaldo() * 0.08;
			setSaldo(juros + getSaldo());
		}
	}
}
