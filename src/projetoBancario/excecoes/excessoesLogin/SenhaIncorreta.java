package projetoBancario.excecoes.excessoesLogin;

import projetoBancario.excecoes.ExcecoesBanco;

public class SenhaIncorreta extends ExcecoesBanco {
    public SenhaIncorreta(String mensagem){
        super(mensagem);
    }
}
