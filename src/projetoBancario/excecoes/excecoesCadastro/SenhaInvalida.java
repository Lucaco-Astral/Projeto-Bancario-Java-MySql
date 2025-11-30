package projetoBancario.excecoes.excecoesCadastro;

import projetoBancario.excecoes.ExcecoesBanco;

public class SenhaInvalida extends ExcecoesBanco {
    public SenhaInvalida(String mensagem){
        super(mensagem);
    }
}