package projetoBancario.telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import projetoBancario.DAO.UsuarioDAO;
import projetoBancario.contaBanco.ContaCorrente;
import projetoBancario.contaBanco.ContaPoupanca;
import projetoBancario.contaBanco.MenuPrincipal;
import projetoBancario.informacoes.EditarUser;
import projetoBancario.informacoes.Usuario;
import projetoBancario.sessao.Sessao;
import projetoBancario.utilitarios.Moeda;
import projetoBancario.utilitarios.botoes.Botao;
import projetoBancario.utilitarios.botoes.BotaoSair;
import projetoBancario.utilitarios.botoes.BotaoSaldo;
import projetoBancario.utilitarios.interfaces.Cores;
import projetoBancario.utilitarios.interfaces.Dimensao;
import projetoBancario.utilitarios.interfaces.Fontes;
import projetoBancario.utilitarios.labels.TextoBold;
import projetoBancario.utilitarios.labels.Titulos;

public class TelaUsuario extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel pnlTopo, pnlCentro, pnlBaixo;
	private JButton btnUser, btnVoltar;

	private JLabel lblInfoCC, lblInfoCP;
	private JLabel lblImg, lblNomeUser;

	private JButton btnSaldoCC, btnSaldoCP;

	private final Border borda;

	private final ContaCorrente CC;
	private final ContaPoupanca CP;

	private final EditarUser editarUser;

	private MenuPrincipal MN;
	private Container conte;

	private boolean visivelCC = false;
	private boolean visivelCP = false;

	private final ImageIcon iconMostrar;
	private final ImageIcon iconEsconder;

	private JPanel panel;
	private JLabel lblInfoEmail;
	private JLabel lblInfoNome;

	private JTextField txtEmail, txtNome;

	private final UsuarioDAO usuarioDAO;
	private final Usuario user;

	public TelaUsuario() {
		setTitle("Bank Usuário");
		conte = getContentPane();
		conte.setBackground(Cores.fundo());
		setSize(650, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		editarUser = new EditarUser();
		usuarioDAO = new UsuarioDAO();
		user = Sessao.getLogado();

		CC = user.getCC();
		CP = user.getCP();

		borda = BorderFactory.createLineBorder(Cores.azulFraco(), 2);

		ImageIcon iconVisivel = new ImageIcon(getClass().getResource("/projetoBancario/img/mostrar.png"));
		ImageIcon iconInvisivel = new ImageIcon(getClass().getResource("/projetoBancario/img/esconder.png"));

		Image imgInvisivel = iconInvisivel.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		Image imgVisivel = iconVisivel.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

		this.iconMostrar = new ImageIcon(imgVisivel);
		this.iconEsconder = new ImageIcon(imgInvisivel);

		pnlTopo = new JPanel();
		pnlTopo.setBackground(Cores.painel());
		pnlTopo.setBorder(borda);
		pnlTopo.setLayout(new BorderLayout());

		ImageIcon iconUser;
		iconUser = user.getImgUser();
		Image imgUser = iconUser.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);

		btnUser = new JButton(new ImageIcon(imgUser));
		btnUser.setBackground(null);
		btnUser.setBorder(null);
		btnUser.setPreferredSize(new Dimension(40, 40));

		lblNomeUser = new Titulos(user.getNome());

		JPanel pnlUser = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlUser.setBackground(null);
		pnlUser.add(btnUser);
		pnlUser.add(lblNomeUser);
		pnlTopo.add(pnlUser, BorderLayout.WEST);

		pnlCentro = new JPanel();
		pnlCentro.setLayout(new BorderLayout());
		pnlCentro.setBackground(Cores.fundo());

		JPanel pnlImg = new JPanel();
		pnlImg.setLayout(new BorderLayout(0, 10));
		pnlImg.setBackground(null);
		pnlImg.setBorder(new EmptyBorder(5, 10, 5, 10));

		imgUser = iconUser.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);

		lblImg = new JLabel(new ImageIcon(imgUser), SwingConstants.CENTER);
		lblImg.setBorder(borda);

		JButton btnMudarImg = new Botao("Mudar Imagem");
		pnlImg.add(lblImg, BorderLayout.CENTER);
		pnlImg.add(btnMudarImg, BorderLayout.SOUTH);

		JLabel lblInfo = new JLabel("Informações da conta", SwingConstants.CENTER);
		lblInfo.setFont(new Font("Arial", Font.BOLD, 22));
		lblInfo.setForeground(Cores.fonte());

		lblInfoCC = new TextoBold("Corrente: ***");
		lblInfoCC.setHorizontalAlignment(SwingConstants.LEFT);
		lblInfoCC.setFont(new Font("Arial", Font.BOLD, 16));

		lblInfoCP = new TextoBold("Poupança: ***");
		lblInfoCP.setHorizontalAlignment(SwingConstants.LEFT);
		lblInfoCP.setFont(new Font("Arial", Font.BOLD, 16));

		JPanel pnlAreaConta = new JPanel();
		pnlAreaConta.setLayout(new BorderLayout());
		pnlAreaConta.setBackground(null);

		JPanel pnlInfo = new JPanel();
		pnlInfo.setLayout(new GridBagLayout());
		pnlInfo.setBackground(null);
		pnlInfo.setBorder(new EmptyBorder(5, 10, 5, 10));

		GridBagConstraints gridCC = new GridBagConstraints();
		gridCC.fill = GridBagConstraints.HORIZONTAL;
		gridCC.gridx = 1;
		gridCC.gridy = 1;
		gridCC.insets = new Insets(10, 0, 5, 0);
		pnlInfo.add(lblInfoCC, gridCC);

		GridBagConstraints gridCP = new GridBagConstraints();
		gridCP.fill = GridBagConstraints.HORIZONTAL;
		gridCP.gridx = 1;
		gridCP.gridy = 2;
		gridCP.insets = new Insets(15, 0, 0, 0);
		pnlInfo.add(lblInfoCP, gridCP);

		btnSaldoCC = new BotaoSaldo(iconEsconder);
		btnSaldoCC.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagConstraints gridSaldoCC = new GridBagConstraints();
		gridSaldoCC.anchor = GridBagConstraints.CENTER;
		gridSaldoCC.insets = new Insets(10, 0, 0, 5);
		gridSaldoCC.gridx = 0;
		gridSaldoCC.gridy = 1;
		pnlInfo.add(btnSaldoCC, gridSaldoCC);

		btnSaldoCP = new BotaoSaldo(iconEsconder);
		btnSaldoCP.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagConstraints gridSaldoCP = new GridBagConstraints();
		gridSaldoCP.anchor = GridBagConstraints.CENTER;
		gridSaldoCP.gridx = 0;
		gridSaldoCP.gridy = 2;
		gridSaldoCP.insets = new Insets(15, 0, 0, 5);
		pnlInfo.add(btnSaldoCP, gridSaldoCP);

		JPanel pnlStatusConta = new JPanel();
		pnlStatusConta.setBackground(null);
		pnlStatusConta.setLayout(new GridBagLayout());
		pnlStatusConta.setBorder(new EmptyBorder(5, 10, 5, 10));

		JLabel lblInfoLimite = new TextoBold("Limite Especial: " + user.getCC().getLimiteEspecial());
		lblInfoLimite.setHorizontalAlignment(SwingConstants.LEFT);
		lblInfoLimite.setFont(new Font("Arial", Font.BOLD, 16));
		GridBagConstraints grc_infocontas = new GridBagConstraints();
		grc_infocontas.fill = GridBagConstraints.HORIZONTAL;
		grc_infocontas.gridx = 0;
		grc_infocontas.gridy = 1;
		grc_infocontas.insets = new Insets(10, 0, 5, 0);
		pnlStatusConta.add(lblInfoLimite, grc_infocontas);

		JLabel lblInfoReajuste = new TextoBold("Reajuste Mensal: " + user.getCP().getReajusteMensal());
		lblInfoReajuste.setHorizontalAlignment(SwingConstants.LEFT);
		lblInfoReajuste.setFont(new Font("Arial", Font.BOLD, 16));
		grc_infocontas.fill = GridBagConstraints.HORIZONTAL;
		grc_infocontas.gridx = 0;
		grc_infocontas.gridy = 2;
		grc_infocontas.insets = new Insets(10, 0, 5, 0);
		pnlStatusConta.add(lblInfoReajuste, grc_infocontas);

		JLabel lblInfoSaldo = new JLabel("Status das Contas", SwingConstants.CENTER);
		lblInfoSaldo.setForeground(new Color(37, 161, 142));
		lblInfoSaldo.setFont(new Font("Arial", Font.BOLD, 20));

		pnlAreaConta.add(lblInfoSaldo, BorderLayout.NORTH);
		pnlAreaConta.add(pnlInfo, BorderLayout.WEST);
		pnlAreaConta.add(pnlStatusConta, BorderLayout.EAST);

		pnlBaixo = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlBaixo.setBackground(Cores.fundo());

		btnVoltar = new BotaoSair("Voltar");
		pnlBaixo.add(btnVoltar);

		panel = new JPanel();
		panel.setBackground(null);
		panel.setBorder(new EmptyBorder(5, 10, 5, 10));
		pnlCentro.add(panel, BorderLayout.WEST);
		GridBagLayout gbl_panel = new GridBagLayout();

		panel.setLayout(gbl_panel);

		lblInfoEmail = new JLabel("Email:");
		lblInfoEmail.setFont(new Font("Arial", Font.BOLD, 16));
		lblInfoEmail.setForeground(Cores.fonte());
		GridBagConstraints gbc_info_email = new GridBagConstraints();
		gbc_info_email.insets = new Insets(0, 0, 5, 0);
		gbc_info_email.anchor = GridBagConstraints.WEST;
		gbc_info_email.gridx = 0;
		gbc_info_email.gridy = 0;
		panel.add(lblInfoEmail, gbc_info_email);

		txtEmail = new JTextField(user.getEmail());
		txtEmail.setPreferredSize(Dimensao.caixasTexto());
		gbc_info_email.insets = new Insets(0, 0, 5, 0);
		gbc_info_email.anchor = GridBagConstraints.WEST;
		gbc_info_email.gridx = 0;
		gbc_info_email.gridy = 1;
		panel.add(txtEmail, gbc_info_email);

		lblInfoNome = new JLabel("Nome:");
		lblInfoNome.setFont(new Font("Arial", Font.BOLD, 16));
		lblInfoNome.setForeground(Cores.fonte());
		GridBagConstraints gbc_info_nome = new GridBagConstraints();
		gbc_info_nome.anchor = GridBagConstraints.WEST;
		gbc_info_nome.gridx = 0;
		gbc_info_nome.gridy = 2;
		gbc_info_nome.insets = new Insets(15, 0, 0, 0);
		panel.add(lblInfoNome, gbc_info_nome);

		txtNome = new JTextField(user.getNome());
		txtNome.setPreferredSize(Dimensao.caixasTexto());
		gbc_info_email.insets = new Insets(5, 0, 0, 0);
		gbc_info_email.anchor = GridBagConstraints.WEST;
		gbc_info_email.gridx = 0;
		gbc_info_email.gridy = 3;
		panel.add(txtNome, gbc_info_email);

		Border bordaLinha = BorderFactory.createLineBorder(Cores.fonte(), 2);
		Border padding = BorderFactory.createEmptyBorder(2, 5, 2, 5);
		Border bordaBotao = BorderFactory.createCompoundBorder(bordaLinha, padding);

		JButton btnAlterarNome = new JButton("Alterar nome");
		btnAlterarNome.setFont(Fontes.botoes());
		btnAlterarNome.setBackground(Cores.fundoBotoes());
		btnAlterarNome.setForeground(Cores.fonte());
		btnAlterarNome.setBorder(bordaBotao);
		gbc_info_email.insets = new Insets(2, 10, 0, 0);
		gbc_info_email.anchor = GridBagConstraints.WEST;
		gbc_info_email.gridx = 2;
		gbc_info_email.gridy = 3;
		panel.add(btnAlterarNome, gbc_info_email);

		JButton btnAlterarEmail = new JButton("Alterar email");
		btnAlterarEmail.setFont(Fontes.botoes());
		btnAlterarEmail.setBackground(Cores.fundoBotoes());
		btnAlterarEmail.setForeground(Cores.fonte());
		btnAlterarEmail.setBorder(bordaBotao);
		gbc_info_email.insets = new Insets(2, 10, 0, 0);
		gbc_info_email.anchor = GridBagConstraints.WEST;
		gbc_info_email.gridx = 2;
		gbc_info_email.gridy = 1;
		panel.add(btnAlterarEmail, gbc_info_email);

		JButton btnCancelarAlt = new JButton("Cancelar alteração");
		btnCancelarAlt.setFont(Fontes.botoes());
		btnCancelarAlt.setBackground(Cores.fundoBotoes());
		btnCancelarAlt.setForeground(Cores.fonte());
		btnCancelarAlt.setBorder(bordaBotao);
		gbc_info_email.insets = new Insets(15, 0, 0, 0);
		gbc_info_email.anchor = GridBagConstraints.WEST;
		gbc_info_email.gridx = 0;
		gbc_info_email.gridy = 4;
		panel.add(btnCancelarAlt, gbc_info_email);

		pnlCentro.add(lblInfo, BorderLayout.NORTH);
		pnlCentro.add(pnlAreaConta, BorderLayout.SOUTH);
		pnlCentro.add(pnlImg, BorderLayout.EAST);

		conte.add(pnlTopo, BorderLayout.NORTH);
		conte.add(pnlCentro, BorderLayout.CENTER);
		conte.add(pnlBaixo, BorderLayout.SOUTH);

		setVisible(true);

		btnAlterarNome.addActionListener(this::alterarNome);
		btnAlterarEmail.addActionListener(this::alterarEmail);
		btnCancelarAlt.addActionListener(this::cancelarAlt);

		btnSaldoCC.addActionListener(this::visibilidadeSaldo);
		btnSaldoCP.addActionListener(this::visibilidadeSaldo);

		btnVoltar.addActionListener(this::voltar);
		btnMudarImg.addActionListener(this::carregarImg);
	}

	private void cancelarAlt(ActionEvent e) {
		txtNome.setText(user.getNome());
		txtEmail.setText(user.getEmail());

		JOptionPane.showMessageDialog(this, "Alteração permanecerão as mesma do usuário", "Aviso Banco",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void alterarNome(ActionEvent e) {
		try {
			editarUser.editarNome(txtNome.getText());

			JOptionPane.showMessageDialog(this, "Informações alteradas com sucesso!!!", "Aviso Banco",
					JOptionPane.INFORMATION_MESSAGE);

			txtNome.setText(user.getNome());
		} catch (Exception e2) {
			txtNome.setText(user.getNome());

			JOptionPane.showMessageDialog(this, e2.getMessage(), "Aviso Banco", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void alterarEmail(ActionEvent e) {
		try {
			editarUser.editarEmail(txtEmail.getText());

			JOptionPane.showMessageDialog(this, "Informações alteradas com sucesso!!!", "Aviso Banco",
					JOptionPane.INFORMATION_MESSAGE);

			txtEmail.setText(user.getEmail());
		} catch (Exception e2) {
			txtEmail.setText(user.getEmail());

			JOptionPane.showMessageDialog(this, e2.getMessage(), "Aviso Banco", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void visibilidadeSaldo(ActionEvent e) {

		if (e.getSource() == btnSaldoCC) {
			if (!visivelCC) {
				btnSaldoCC.setIcon(iconMostrar);
				lblInfoCC.setText("Corrente: " + (CC != null ? Moeda.formatar(CC.getSaldo()) : Moeda.formatar(0.00)));
				visivelCC = true;
			} else {
				btnSaldoCC.setIcon(iconEsconder);
				lblInfoCC.setText("Corrente: ***");

				visivelCC = false;
			}
		} else if (e.getSource() == btnSaldoCP) {
			if (!visivelCP) {
				btnSaldoCP.setIcon(iconMostrar);
				lblInfoCP.setText("Poupança: " + (CP != null ? Moeda.formatar(CP.getSaldo()) : Moeda.formatar(0.00)));

				visivelCP = true;
			} else {
				btnSaldoCP.setIcon(iconEsconder);
				lblInfoCP.setText("Poupança: ***");

				visivelCP = false;
			}
		}
	}

	private void esconderSaldo() {
		ImageIcon imgVisivelCP = new ImageIcon(getClass().getResource("/projetoBancario/img/esconder.png"));
		Image img = imgVisivelCP.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

		btnSaldoCC.setIcon(new ImageIcon(img));
		btnSaldoCP.setIcon(new ImageIcon(img));

		lblInfoCC.setText("Corrente: ***");
		lblInfoCP.setText("Poupança: ***");
		visivelCC = false;
		visivelCP = false;
	}

	private void voltar(ActionEvent e) {
		esconderSaldo();

		MN = new MenuPrincipal();
		MN.executar();

		dispose();
	}

	private void carregarImg(ActionEvent e) {
		JFileChooser carregarImg = new JFileChooser();
		carregarImg.setDialogTitle("Mudar Imagem do Usuário");

		carregarImg.setFileFilter(new FileNameExtensionFilter("Arquivos de imagem permitidos (*.PNG, *.JPG, *.JPEG)",
				"png", "jpg", "jpeg"));

		int resultado = carregarImg.showOpenDialog(this);

		if (resultado == JFileChooser.APPROVE_OPTION) {
			File imgEscolhida = carregarImg.getSelectedFile();

			try {
				Image img = ImageIO.read(imgEscolhida);

				Image imgLbl = ImageIO.read(carregarImg.getSelectedFile()).getScaledInstance(lblImg.getWidth(),
						lblImg.getHeight(), Image.SCALE_SMOOTH);
				lblImg.setIcon(new ImageIcon(imgLbl));

				Image imgBtn = ImageIO.read(carregarImg.getSelectedFile()).getScaledInstance(btnUser.getWidth(),
						btnUser.getHeight(), Image.SCALE_SMOOTH);
				btnUser.setIcon(new ImageIcon(imgBtn));

				ImageIcon imgPerfil = new ImageIcon(img);
				user.setImgUser(imgPerfil);

				lblNomeUser.setText(user.getNome());
				usuarioDAO.atualizarUser(user);
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "Erro: " + e2);
			}
		}
	}
}