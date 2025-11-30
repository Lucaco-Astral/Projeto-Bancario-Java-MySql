package projetoBancario.telas;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;

import projetoBancario.excecoes.ExcecoesBanco;
import projetoBancario.informacoes.Cadastro;
import projetoBancario.utilitarios.FiltraSenha;
import projetoBancario.utilitarios.FiltroEmail;
import projetoBancario.utilitarios.FiltroNome;
import projetoBancario.utilitarios.botoes.Botao;
import projetoBancario.utilitarios.interfaces.Cores;
import projetoBancario.utilitarios.labels.Titulos;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.border.LineBorder;

import java.io.File;

public class CadastroTela extends JFrame implements DocumentListener {

	private static final long serialVersionUID = 1L;

	private final Cadastro cadastro;

	private final JTextField txtEmail, txtNome;
	private final JPasswordField passSenha, passRptSenha;

	private JLabel lbl_img_user;
	private ImageIcon iconPerfil;

	private final Container conte;

	private final int MAX_EMAIL = 100;
	private final int MAX_NOME = 15;
	private final int MAX_SENHA = 20;

	public CadastroTela() {
		cadastro = new Cadastro();

		setResizable(false);
		conte = getContentPane();
		setSize(680, 428);
		setBackground(Cores.fundo());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		conte.setLayout(new BorderLayout());

		JLabel lblTitulo = new Titulos("Cadastro Bank");

		JPanel pnl_img = new JPanel();
		pnl_img.setBorder(new EmptyBorder(15, 5, 15, 5));
		pnl_img.setBackground(Cores.painel());
		pnl_img.setPreferredSize(new Dimension(250, getHeight()));

		JPanel pnlPrincipal = new JPanel();
		pnlPrincipal.setBackground(Cores.fundo());
		pnlPrincipal.setLayout(new BorderLayout());

		JPanel pnlInfo = new JPanel();
		pnlInfo.setBorder(new EmptyBorder(10, 5, 0, 5));
		pnlInfo.setBackground(Cores.fundo());
		GridBagLayout gbl_pnlInfo = new GridBagLayout();
		gbl_pnlInfo.columnWeights = new double[] { 1.0 };
		pnlInfo.setLayout(gbl_pnlInfo);

		JPanel pnlBotoes = new JPanel();
		pnlBotoes.setBackground(Cores.fundo());
		pnlBotoes.setLayout(new BorderLayout(0, 0));

		JPanel pnl_logarVoltar = new JPanel();
		pnl_logarVoltar.setBackground(null);
		pnlBotoes.add(pnl_logarVoltar, BorderLayout.SOUTH);
		pnl_logarVoltar.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		Botao btnLogin = new Botao("Login");
		pnl_logarVoltar.add(btnLogin);
		btnLogin.addActionListener(this::login);

		Botao btnVoltar = new Botao("Voltar");
		pnl_logarVoltar.add(btnVoltar);
		btnVoltar.addActionListener(this::voltar);

		pnlPrincipal.add(lblTitulo, BorderLayout.NORTH);
		pnlPrincipal.add(pnlInfo, BorderLayout.CENTER);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setForeground(new Color(37, 161, 142));
		lblEmail.setFont(new Font("Arial", Font.BOLD, 17));
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.WEST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 0);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 0;
		pnlInfo.add(lblEmail, gbc_lblEmail);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Arial", Font.PLAIN, 15));
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		pnlInfo.add(txtEmail, gbc_textField);
		txtEmail.setColumns(10);

		AbstractDocument doc = (AbstractDocument) txtEmail.getDocument();
		doc.setDocumentFilter(new FiltroEmail());
		txtEmail.getDocument().addDocumentListener(this);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblNome.setForeground(new Color(37, 161, 142));
		lblNome.setFont(new Font("Arial", Font.BOLD, 17));
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.anchor = GridBagConstraints.WEST;
		gbc_lblNome.insets = new Insets(8, 0, 5, 0);
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 2;
		pnlInfo.add(lblNome, gbc_lblNome);

		txtNome = new JTextField();
		txtNome.setFont(new Font("Arial", Font.PLAIN, 15));
		txtNome.setColumns(10);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 0;
		gbc_textField_1.gridy = 3;
		pnlInfo.add(txtNome, gbc_textField_1);

		doc = (AbstractDocument) txtNome.getDocument();
		doc.setDocumentFilter(new FiltroNome());
		txtNome.getDocument().addDocumentListener(this);

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setHorizontalAlignment(SwingConstants.CENTER);
		lblSenha.setForeground(new Color(37, 161, 142));
		lblSenha.setFont(new Font("Arial", Font.BOLD, 17));
		GridBagConstraints gbc_lblSenha = new GridBagConstraints();
		gbc_lblSenha.anchor = GridBagConstraints.WEST;
		gbc_lblSenha.insets = new Insets(8, 0, 5, 0);
		gbc_lblSenha.gridx = 0;
		gbc_lblSenha.gridy = 4;
		pnlInfo.add(lblSenha, gbc_lblSenha);

		passSenha = new JPasswordField();
		passSenha.setFont(new Font("Arial", Font.PLAIN, 15));
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 0;
		gbc_passwordField.gridy = 5;
		pnlInfo.add(passSenha, gbc_passwordField);

		doc = (AbstractDocument) passSenha.getDocument();
		doc.setDocumentFilter(new FiltraSenha());
		passSenha.getDocument().addDocumentListener(this);

		JLabel lblRepitaASenha = new JLabel("Repita a senha");
		lblRepitaASenha.setHorizontalAlignment(SwingConstants.CENTER);
		lblRepitaASenha.setForeground(new Color(37, 161, 142));
		lblRepitaASenha.setFont(new Font("Arial", Font.BOLD, 17));
		GridBagConstraints gbc_lblRepitaASenha = new GridBagConstraints();
		gbc_lblRepitaASenha.anchor = GridBagConstraints.WEST;
		gbc_lblRepitaASenha.insets = new Insets(8, 0, 5, 0);
		gbc_lblRepitaASenha.gridx = 0;
		gbc_lblRepitaASenha.gridy = 6;
		pnlInfo.add(lblRepitaASenha, gbc_lblRepitaASenha);

		passRptSenha = new JPasswordField();
		passRptSenha.setFont(new Font("Arial", Font.PLAIN, 15));
		GridBagConstraints gbc_passwordField_1 = new GridBagConstraints();
		gbc_passwordField_1.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField_1.gridx = 0;
		gbc_passwordField_1.gridy = 7;
		pnlInfo.add(passRptSenha, gbc_passwordField_1);

		Botao btnConfirmar = new Botao("Cadastrar");
		GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
		gbc_btnConfirmar.insets = new Insets(10, 0, 0, 0);
		gbc_btnConfirmar.gridx = 0;
		gbc_btnConfirmar.gridy = 8;
		pnlInfo.add(btnConfirmar, gbc_btnConfirmar);
		btnConfirmar.setText("Confirmar");
		btnConfirmar.setForeground(new Color(37, 161, 142));
		btnConfirmar.setFont(new Font("Arial", Font.BOLD, 17));

		btnConfirmar.addActionListener(this::confirmar);

		doc = (AbstractDocument) passRptSenha.getDocument();
		doc.setDocumentFilter(new FiltraSenha());
		passRptSenha.getDocument().addDocumentListener(this);
		pnlPrincipal.add(pnlBotoes, BorderLayout.SOUTH);

		conte.add(pnlPrincipal, BorderLayout.CENTER);
		conte.add(pnl_img, BorderLayout.WEST);
		pnl_img.setLayout(new BorderLayout(0, 15));

		lbl_img_user = new JLabel();
		lbl_img_user.setBorder(new LineBorder(Cores.azulFraco(), 2, true));
		pnl_img.add(lbl_img_user, BorderLayout.CENTER);

		JPanel pnl_opcoes_img = new JPanel();
		pnl_opcoes_img.setBackground(null);
		pnl_img.add(pnl_opcoes_img, BorderLayout.SOUTH);
		GridLayout gl_pnl_opcoes_img = new GridLayout(2, 1);
		gl_pnl_opcoes_img.setVgap(10);
		pnl_opcoes_img.setLayout(gl_pnl_opcoes_img);

		Botao btn_img_user = new Botao("Colocar Imagem");
		pnl_opcoes_img.add(btn_img_user);

		Botao btn_img_sistema = new Botao("Usar imagem do sistema");
		pnl_opcoes_img.add(btn_img_sistema);

		btn_img_user.addActionListener(this::escolherImg);
		btn_img_sistema.addActionListener(this::sistemaImg);

		iconPerfil = new ImageIcon();
	}

	public void escolherImg(ActionEvent e) {
		JFileChooser carregarImg = new JFileChooser();
		carregarImg.setDialogTitle("Escolher imagem do sistema");

		carregarImg.setFileFilter(new FileNameExtensionFilter("Arquivos de imagem permitidos (*.PNG, *.JPG, *.JPEG)",
				"png", "jpg", "jpeg"));

		int resultado = carregarImg.showOpenDialog(this);

		if (resultado == JFileChooser.APPROVE_OPTION) {
			File imgEscolhida = carregarImg.getSelectedFile();

			try {
				Image img = ImageIO.read(imgEscolhida);

				Image imgLbl = ImageIO.read(carregarImg.getSelectedFile()).getScaledInstance(lbl_img_user.getWidth(),
						lbl_img_user.getHeight(), Image.SCALE_SMOOTH);

				iconPerfil.setImage(img);
				lbl_img_user.setIcon(new ImageIcon(imgLbl));

				JOptionPane.showMessageDialog(this, "Imagem de perfil adicionada com sucesso", "Cadastro Banco",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "Erro: " + e2);
			}
		}
	}

	public void sistemaImg(ActionEvent e) {
		ImageIcon iconUser = new ImageIcon(getClass().getResource("/projetoBancario/img/user.png"));

		Image imgUser = iconUser.getImage().getScaledInstance(lbl_img_user.getWidth(), lbl_img_user.getHeight(),
				Image.SCALE_SMOOTH);

		iconPerfil.setImage(imgUser);
		lbl_img_user.setIcon(iconPerfil);

		JOptionPane.showMessageDialog(this,
				"Você escolheu utilizar a imagem do sistema!!\n Caso queira troca basta apenas ir no perfil e mudar a imagem",
				"Cadastro Banco", JOptionPane.INFORMATION_MESSAGE);
	}

	public void confirmar(ActionEvent event) {
		try {
			cadastro.validarNomeEmail(txtNome.getText(), txtEmail.getText());
			cadastro.validarSenha(passSenha.getPassword(), passRptSenha.getPassword());
			cadastro.salvarImagem(iconPerfil);

			cadastro.cadastrarUsuario();

			JOptionPane.showMessageDialog(this, "Cadastro feito com sucesso:");

			txtEmail.setText("");
			txtNome.setText("");
			passSenha.setText(null);
			passRptSenha.setText(null);
			lbl_img_user.setIcon(null);

			int opicao = JOptionPane.showConfirmDialog(this, "Deseja fazer Login?", "Aviso Login",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (opicao == 0) {
				login(event);
			}
		} catch (ExcecoesBanco cadastroAviso) {
			JOptionPane.showMessageDialog(this, cadastroAviso.getMessage(), "Aviso Cadastro",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	public void login(ActionEvent evento) {
		LoginTela loginTela = new LoginTela();
		loginTela.setVisible(true);
		dispose();
	}

	public void voltar(ActionEvent evento) {
		txtEmail.setText("");
		txtNome.setText("");
		passSenha.setText(null);
		passRptSenha.setText(null);

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
			mensagem = "Máximo de caracteres para email digitado atingido " + limite;
		} else if (e.getDocument() == txtNome.getDocument()) {
			limite = MAX_NOME;
			mensagem = "Máximo de caracteres para nome digitado atingido " + limite;
		} else if (e.getDocument() == passSenha.getDocument() || e.getDocument() == passRptSenha.getDocument()) {
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