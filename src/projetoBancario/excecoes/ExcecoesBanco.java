package projetoBancario.excecoes;

public class ExcecoesBanco extends RuntimeException{
    public ExcecoesBanco(String mensagem){
        super(mensagem);
    }
}