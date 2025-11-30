package projetoBancario.excecoes.conta;

import projetoBancario.excecoes.ExcecoesBanco;

public class SaldoNegativado extends ExcecoesBanco {
    public SaldoNegativado(String mensagem){
        super(mensagem);
    }
}
