package projetoBancario.excecoes.informacoesVazias;

import projetoBancario.excecoes.ExcecoesBanco;

public class SenhaVazia extends ExcecoesBanco {
    public SenhaVazia(String mensagem){
        super(mensagem);
    }
}
