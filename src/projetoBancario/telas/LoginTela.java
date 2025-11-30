package projetoBancario.telas;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;

import projetoBancario.contaBanco.MenuPrincipal;
import projetoBancario.excecoes.ExcecoesBanco;
import projetoBancario.informacoes.Login;
import projetoBancario.utilitarios.FiltraSenha;
import projetoBancario.utilitarios.FiltroEmail;
import projetoBancario.utilitarios.botoes.Botao;
import projetoBancario.utilitarios.interfaces.Cores;
import projetoBancario.utilitarios.interfaces.Dimensao;
import projetoBancario.utilitarios.interfaces.Fontes;
import projetoBancario.utilitarios.labels.Texto;
import java.awt.Font;

public class LoginTela extends JFrame implements DocumentListener {

	private static final long serialVersionUID = 1L;

	private final Login login = new Login();

	private final JTextField txtEmail;
	private final JPasswordField passSenha;

	private final Container conte;
	private final MenuPrincipal MP;

	private final int MAX_EMAIL = 100;
	private final int MAX_SENHA = 20;

	public LoginTela() {
		setTitle("Bank Login");
		MP = new MenuPrincipal();
		conte = getContentPane();
		setSize(550, 350);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		conte.setLayout(new BorderLayout());

		JPanel pnlPrincipal = new JPanel();
		pnlPrincipal.setBackground(Cores.fundo());
		pnlPrincipal.setLayout(new BorderLayout());

		JLabel lblTitulo = new JLabel("Login Bank", SwingConstants.CENTER);
		lblTitulo.setFont(Fontes.titulos());
		lblTitulo.setForeground(Cores.fonte());

		JPanel pnlCor = new JPanel();
		pnlCor.setBackground(Cores.painel());
		pnlCor.setPreferredSize(new Dimension(270, getHeight()));

		JPanel pnlInfo = new JPanel();
		pnlInfo.setLayout(new GridBagLayout());
		pnlInfo.setBackground(Cores.fundo());

		GridBagConstraints grdLblEmail = new GridBagConstraints();
		grdLblEmail.fill = GridBagConstraints.HORIZONTAL;
		grdLblEmail.gridx = 0;
		grdLblEmail.gridy = 0;

		JLabel lblEmail = new Texto("Email");
		lblEmail.setFont(new Font("Arial", Font.PLAIN, 17));
		pnlInfo.add(lblEmail, grdLblEmail);

		GridBagConstraints grdTxtEmail = new GridBagConstraints();
		grdTxtEmail.fill = GridBagConstraints.HORIZONTAL;
		grdTxtEmail.gridy = 1;

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Arial", Font.PLAIN, 17));
		txtEmail.setPreferredSize(Dimensao.caixasTexto());
		pnlInfo.add(txtEmail, grdTxtEmail);

		AbstractDocument doc = (AbstractDocument) txtEmail.getDocument();
		doc.setDocumentFilter(new FiltroEmail());
		txtEmail.getDocument().addDocumentListener(this);

		GridBagConstraints grdLblSenha = new GridBagConstraints();
		grdLblSenha.fill = GridBagConstraints.HORIZONTAL;
		grdLblSenha.insets = new Insets(15, 0, 0, 0);
		grdLblSenha.gridy = 2;

		JLabel lblSenha = new Texto("Senha");
		lblSenha.setFont(new Font("Arial", Font.PLAIN, 17));
		pnlInfo.add(lblSenha, grdLblSenha);

		GridBagConstraints grdPassSenha = new GridBagConstraints();
		grdPassSenha.fill = GridBagConstraints.HORIZONTAL;
		grdPassSenha.gridy = 3;

		passSenha = new JPasswordField();
		passSenha.setFont(new Font("Arial", Font.PLAIN, 17));
		passSenha.setPreferredSize(Dimensao.caixasTexto());
		pnlInfo.add(passSenha, grdPassSenha);

		doc = (AbstractDocument) passSenha.getDocument();
		doc.setDocumentFilter(new FiltraSenha());
		passSenha.getDocument().addDocumentListener(this);

		GridBagConstraints grdBtnEntrar = new GridBagConstraints();
		grdBtnEntrar.fill = GridBagConstraints.HORIZONTAL;
		grdBtnEntrar.insets = new Insets(20, 0, 0, 0);
		grdBtnEntrar.gridy = 4;

		JButton btnEntrar = new Botao("Entrar");
		pnlInfo.add(btnEntrar, grdBtnEntrar);

		JPanel pnlBotao = new JPanel();
		pnlBotao.setBackground(Cores.fundo());
		pnlBotao.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

		JButton btnCadastro = new Botao("Cadastro");
		pnlBotao.add(btnCadastro);

		JButton btnVoltar = new Botao("Voltar");
		pnlBotao.add(btnVoltar);

		pnlPrincipal.add(lblTitulo, BorderLayout.NORTH);
		pnlPrincipal.add(pnlInfo, BorderLayout.CENTER);
		pnlPrincipal.add(pnlBotao, BorderLayout.SOUTH);

		conte.add(pnlCor, BorderLayout.WEST);
		conte.add(pnlPrincipal, BorderLayout.CENTER);

		btnEntrar.addActionListener(this::entrar);
		btnCadastro.addActionListener(this::cadastro);
		btnVoltar.addActionListener(this::voltar);
	}

	public void entrar(ActionEvent evento) {
		try {
			login.validarLogin(txtEmail.getText(), passSenha.getPassword());

			JOptionPane.showMessageDialog(this, "Login feito com sucesso", "Login", JOptionPane.INFORMATION_MESSAGE);

			txtEmail.setText("");
			passSenha.setText(null);

			MP.executar();
			dispose();
		} catch (ExcecoesBanco loginAviso) {
			JOptionPane.showMessageDialog(null, loginAviso.getMessage(), "Aviso Login", JOptionPane.WARNING_MESSAGE);

			if (!login.isCadastro()) {
				int opicao = JOptionPane.showConfirmDialog(this, "Deseja se cadastrar?", "Aviso Login",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (opicao == 0) {
					cadastro(evento);
				} else {
					JOptionPane.showMessageDialog(this, "Digite outro Usuário");
				}

				login.setCadastro(true);
			}

			txtEmail.setText("");
			passSenha.setText(null);
		}
	}

	public void cadastro(ActionEvent evento) {
		CadastroTela cadastroTela = new CadastroTela();
		cadastroTela.setVisible(true);
		dispose();
	}

	public void voltar(ActionEvent evento) {
		txtEmail.setText("");
		passSenha.setText(null);

		TelaAcesso telaAcesso = new TelaAcesso();
		telaAcesso.setVisible(true);
		dispose();
	}

	private void verificarAviso(DocumentEvent e) {
		int tamanho = e.getDocument().getLength();
		int limite = 0;
		String mensagem = "";

		if (e.getDocument() == txtEmail.getDocument()) {
			limite = MAX_EMAIL;
			mensagem = "Máximo de caracteres para nome digitado atingido " + limite;
		} else if (e.getDocument() == passSenha.getDocument()) {
			limite = MAX_SENHA;
			mensagem = "Máximo de caracteres para senha digitado atingido " + limite;
		} else {
			return;
		}

		if (tamanho == limite) {
			JOptionPane.showMessageDialog(this, mensagem, "Aviso", JOptionPane.WARNING_MESSAGE);
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		verificarAviso(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		verificarAviso(e);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	}
}