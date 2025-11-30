package projetoBancario.excecoes.informacoesVazias;

import projetoBancario.excecoes.ExcecoesBanco;

public class NomeVazio extends ExcecoesBanco {
    public NomeVazio(String mensagem){
        super(mensagem);
    }
}