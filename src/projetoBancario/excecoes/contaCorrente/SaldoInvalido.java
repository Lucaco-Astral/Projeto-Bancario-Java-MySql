package projetoBancario.excecoes.contaCorrente;

import projetoBancario.excecoes.ExcecoesBanco;

public class SaldoInvalido extends ExcecoesBanco {
    public SaldoInvalido(String mensagem){
        super(mensagem);
    }
}