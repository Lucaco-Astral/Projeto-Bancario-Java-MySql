package projetoBancario.excecoes.contaPoupanca;

import projetoBancario.excecoes.ExcecoesBanco;

public class ReajusteInvalido extends ExcecoesBanco {
    public ReajusteInvalido(String mensagem){
        super(mensagem);
    }
}
