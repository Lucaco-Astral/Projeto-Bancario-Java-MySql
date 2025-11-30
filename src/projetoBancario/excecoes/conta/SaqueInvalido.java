package projetoBancario.excecoes.conta;

import projetoBancario.excecoes.ExcecoesBanco;

public class SaqueInvalido extends ExcecoesBanco {
    public SaqueInvalido(String mensagem){
        super(mensagem);
    }
}