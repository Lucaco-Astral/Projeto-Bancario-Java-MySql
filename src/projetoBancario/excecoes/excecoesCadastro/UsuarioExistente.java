package projetoBancario.excecoes.excecoesCadastro;

import projetoBancario.excecoes.ExcecoesBanco;

public class UsuarioExistente extends ExcecoesBanco {
    public UsuarioExistente(String mensagem) {
        super(mensagem);
    }
}