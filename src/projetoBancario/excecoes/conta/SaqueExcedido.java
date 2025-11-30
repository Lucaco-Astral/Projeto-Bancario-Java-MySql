package projetoBancario.excecoes.conta;

import projetoBancario.excecoes.ExcecoesBanco;

public class SaqueExcedido extends ExcecoesBanco {
    public SaqueExcedido(String mensagem){
        super(mensagem);
    }
}
