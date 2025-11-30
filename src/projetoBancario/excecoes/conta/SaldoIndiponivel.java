package projetoBancario.excecoes.conta;

import projetoBancario.excecoes.ExcecoesBanco;

public class SaldoIndiponivel extends ExcecoesBanco {
    public SaldoIndiponivel(String mensagem){
        super(mensagem);
    }
}
