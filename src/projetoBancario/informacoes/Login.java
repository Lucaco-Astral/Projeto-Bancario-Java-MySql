package projetoBancario.informacoes;

import java.util.Arrays;

import projetoBancario.DAO.UsuarioDAO;
import projetoBancario.excecoes.*;
import projetoBancario.excecoes.excecoesCadastro.SenhaInvalida;
import projetoBancario.excecoes.excessoesLogin.SenhaIncorreta;
import projetoBancario.excecoes.excessoesLogin.UsuarioInexistente;
import projetoBancario.excecoes.informacoesVazias.*;
import projetoBancario.sessao.Sessao;

public class Login {
	private UsuarioDAO usuarioDAO;

	private Usuario user;
	private boolean cadastro = true;

	public Login() {
		usuarioDAO = new UsuarioDAO();
	}

	private void confirmarEmail(String email) throws ExcecoesBanco {
		if (email.isEmpty()) {
			throw new NomeVazio("Preencha os campos necessários");
		}

		if (!usuarioDAO.verificarEmail(email)) {
			cadastro = false;
			throw new UsuarioInexistente("Esse usuário não existe");
		}

		user = usuarioDAO.procurarUserEmail(email);
	}

	private void confirmaSenha(char[] senha) throws ExcecoesBanco {
		if (senha.length == 0) {
			throw new SenhaVazia("Digite a senha do usuário");
		}

		if (senha.length < 4) {
			throw new SenhaInvalida("Senha deve ter no minímo 4 números");
		}

		if (!Arrays.equals(user.getSenha(), senha)) {
			throw new SenhaIncorreta("Senha de usuário incorreta");
		}
	}

	public void validarLogin(String email, char[] senha) throws ExcecoesBanco {
		confirmarEmail(email);
		confirmaSenha(senha);

		Sessao.setLogado(user);
	}

	public boolean isCadastro() {
		return cadastro;
	}

	public void setCadastro(boolean cadastro) {
		this.cadastro = cadastro;
	}
}