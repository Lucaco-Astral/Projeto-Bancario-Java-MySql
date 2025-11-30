package projetoBancario.excecoes.excessoesLogin;

import projetoBancario.excecoes.ExcecoesBanco;

public class UsuarioInexistente extends ExcecoesBanco {
    public UsuarioInexistente(String mensagem){
        super(mensagem);
    }
}
