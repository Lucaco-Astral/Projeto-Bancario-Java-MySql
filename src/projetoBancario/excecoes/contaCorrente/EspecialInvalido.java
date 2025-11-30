package projetoBancario.excecoes.contaCorrente;

import projetoBancario.excecoes.ExcecoesBanco;

public class EspecialInvalido extends ExcecoesBanco {
    public EspecialInvalido(String mensagem){
        super(mensagem);
    }
}