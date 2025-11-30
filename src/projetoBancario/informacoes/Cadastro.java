package projetoBancario.informacoes;

import projetoBancario.DAO.UsuarioDAO;
import projetoBancario.excecoes.*;
import projetoBancario.excecoes.excecoesCadastro.*;
import projetoBancario.excecoes.informacoesVazias.*;

import java.util.Arrays;

import javax.swing.ImageIcon;

public class Cadastro {
	private final UsuarioDAO dao;
	private Usuario user;

	private String nome;
	private String email;
	private char[] senha;
	private ImageIcon imgUser;

	public Cadastro() {
		dao = new UsuarioDAO();
	}

	private void salvarNome(String nome) throws ExcecoesBanco {
		if (nome.isEmpty()) {
			throw new NomeVazio("Preencha os campos obrigatorios");
		}

		this.nome = nome;
	}

	private void salvarEmail(String email) throws ExcecoesBanco {
		if (email.isEmpty()) {
			throw new EmailInvalido("Preencha os campos obrigatorios");
		}

		if (dao.verificarEmail(email)) {
			throw new EmailInvalido("Esse email de usuário já está cadastrado");
		}

		this.email = email;
	}

	private void salvarSenha(char[] senha) throws ExcecoesBanco {
		boolean letra = false;
		boolean num = false;

		if (senha.length == 0) {
			throw new SenhaVazia("Preencha os campos obrigatorios");
		}

		if (senha.length < 3) {
			throw new SenhaInvalida("A senha deve ter no minímo 3 caracteres");
		}

		for (char c : senha) {
			if (Character.isLetter(c)) {
				letra = true;
			}

			if (Character.isDigit(c)) {
				num = true;
			}
		}

		if (!letra) {
			throw new SenhaInvalida("A senha deve ter ao menos uma letra");
		}

		if (!num) {
			throw new SenhaInvalida("A senha deve ter ao menos um numero");
		}

		this.senha = senha;
	}

	private void confirmarSenha(char[] senha) throws ExcecoesBanco {
		if (senha == null) {
			throw new SenhaVazia("Preencha os campos obrigatorios");
		}

		if (!Arrays.equals(senha, this.senha)) {
			throw new SenhaInvalida("Digite a mesma senha nos dois campos");
		}
	}

	public void salvarImagem(ImageIcon imgUser) throws ExcecoesBanco {
		if (imgUser.getImage() == null) {
			throw new ImagemVazia("Escolha uma imagem para o perfil do usuário ");
		}

		this.imgUser = imgUser;
	}

	public void validarNomeEmail(String nome, String Email) throws ExcecoesBanco {
		salvarNome(nome);
		salvarEmail(Email);
	}

	public void validarSenha(char[] senha, char[] rtpSenha) throws ExcecoesBanco {
		salvarSenha(senha);
		confirmarSenha(rtpSenha);
	}

	public void cadastrarUsuario() throws ExcecoesBanco {
		user = new Usuario(email, nome, senha, imgUser, 0);
		dao.cadastrarUser(user);
		user = null;
	}
}