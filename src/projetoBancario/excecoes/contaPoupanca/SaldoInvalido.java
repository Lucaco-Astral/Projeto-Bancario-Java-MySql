package projetoBancario.excecoes.contaPoupanca;

import projetoBancario.excecoes.ExcecoesBanco;

public class SaldoInvalido extends ExcecoesBanco {
    public SaldoInvalido(String mensagem){
        super(mensagem);
    }
}