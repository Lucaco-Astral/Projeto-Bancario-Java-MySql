package projetoBancario.informacoes;

import javax.swing.ImageIcon;

import projetoBancario.contaBanco.ContaCorrente;
import projetoBancario.contaBanco.ContaPoupanca;

public class Usuario {
	private ContaCorrente CC;
	private ContaPoupanca CP;

	private final int ID;
	private String nome;
	private String email;

	private final char[] SENHA;

	private ImageIcon imgUser;

	public ImageIcon getImgUser() {
		return imgUser;
	}

	public void setImgUser(ImageIcon img) {
		this.imgUser = img;
	}

	int tamanho;

	public Usuario(String email, String nome, char[] senha, ImageIcon iconUser, int contador) {
		CC = new ContaCorrente(1000, 0);
		CP = new ContaPoupanca(0, 1);

		imgUser = iconUser;

		this.nome = nome;
		this.email = email;
		this.SENHA = senha;

		this.ID = contador;
	}

	public int getId() {
		return ID;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public char[] getSenha() {
		return SENHA;
	}

	public ContaCorrente getCC() {
		return CC;
	}

	public void setCC(ContaCorrente cC) {
		CC = cC;
	}

	public ContaPoupanca getCP() {
		return CP;
	}

	public void setCP(ContaPoupanca cP) {
		CP = cP;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}