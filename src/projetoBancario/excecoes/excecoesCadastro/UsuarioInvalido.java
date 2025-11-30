package projetoBancario.excecoes.excecoesCadastro;

import projetoBancario.excecoes.ExcecoesBanco;

public class UsuarioInvalido extends ExcecoesBanco {
    public UsuarioInvalido(String mensagem){
        super(mensagem);
    }
}
