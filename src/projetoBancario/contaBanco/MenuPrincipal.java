package projetoBancario.contaBanco;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import projetoBancario.DAO.CorrenteDAO;
import projetoBancario.DAO.PoupancaDAO;
import projetoBancario.DAO.UsuarioDAO;
import projetoBancario.informacoes.Usuario;
import projetoBancario.sessao.Sessao;
import projetoBancario.telas.DepositoTela;
import projetoBancario.telas.SaqueTela;
import projetoBancario.telas.TelaAcesso;
import projetoBancario.telas.TelaUsuario;
import projetoBancario.utilitarios.Moeda;
import projetoBancario.utilitarios.botoes.Botao;
import projetoBancario.utilitarios.botoes.BotaoSair;
import projetoBancario.utilitarios.botoes.BotaoSaldo;
import projetoBancario.utilitarios.interfaces.Cores;
import projetoBancario.utilitarios.interfaces.Fontes;
import projetoBancario.utilitarios.labels.TextoBold;
import projetoBancario.utilitarios.labels.Titulos;

public class MenuPrincipal extends Menu {
	private JFrame frmMenu;

	private JPanel pnlMenu;
	private JPanel pnlOpcoes;

	private JButton btnCorrente, btnPoupanca;
	private JPanel pnlTopo, pnlBaixo, pnlContas;

	private JPanel pnlCorrente, pnlPoupanca;

	private JButton btnDeposito;

	private JButton btnSaqueCC;
	private JButton btnSaqueCP;

	private JLabel lblSaldo;

	private JButton btnUser;

	private JButton btnSaldo, btnVoltar, btnSair;

	private Usuario userLogado;
	private ContaCorrente contaCC;
	private ContaPoupanca contaCP;

	private ImageIcon senha;
	private ImageIcon iconSenha;

	private Border borda;

	private Image ajuste;

	private final CardLayout carde;

	private JLabel lblConta;

	private boolean visivel = true;

	CorrenteDAO correnteDAO;
	PoupancaDAO poupancaDAO;
	UsuarioDAO usuarioDAO;

	// Construtor do MenuContas
	public MenuPrincipal() {
		borda = BorderFactory.createLineBorder(new Color(37, 161, 142), 2);

		frmMenu = new JFrame("Banker Menu principal");
		carde = new CardLayout();
		frmMenu.setLayout(carde);
		frmMenu.setSize(550, 350);
		frmMenu.setResizable(false);
		frmMenu.setLocationRelativeTo(null);
		frmMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pnlMenu = new JPanel(new BorderLayout());
		pnlCorrente = new JPanel(new BorderLayout());
		pnlPoupanca = new JPanel(new BorderLayout());

		frmMenu.add(pnlMenu, "menu");
		frmMenu.add(pnlCorrente, "contaCC");
		frmMenu.add(pnlPoupanca, "contaCP");

		lblConta = new JLabel();
		lblConta.setHorizontalAlignment(SwingConstants.CENTER);
		lblConta.setFont(new Font("Arial", Font.BOLD, 25));
		lblConta.setForeground(Cores.fonte());

		correnteDAO = new CorrenteDAO();
		poupancaDAO = new PoupancaDAO();
		usuarioDAO = new UsuarioDAO();
	}

	@Override
	public void trocarTela(String tela) {
		carde.show(frmMenu.getContentPane(), tela);
	}

	@Override
	protected void executarMenu() {
		userLogado = Sessao.getLogado();
		contaCC = userLogado.getCC();
		contaCP = userLogado.getCP();

		pnlTopo = criaPnlTopo(userLogado.getNome(), true);

		pnlContas = new JPanel(new GridBagLayout());
		pnlContas.setBackground(Cores.fundo());

		GridBagConstraints gbr = new GridBagConstraints();
		gbr.fill = GridBagConstraints.HORIZONTAL;
		gbr.weightx = 0.5;

		gbr.insets = new Insets(0, 15, 0, 15);

		gbr.gridx = 0;
		gbr.gridy = 0;
		btnCorrente = new Botao("Conta Corrente");
		btnCorrente.setActionCommand("1");
		pnlContas.add(btnCorrente, gbr);

		gbr.gridx = 1;
		btnPoupanca = new Botao("Conta Poupança");
		btnPoupanca.setActionCommand("2");
		pnlContas.add(btnPoupanca, gbr);

		pnlBaixo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
		pnlBaixo.setBackground(Cores.fundo());
		btnSair = new BotaoSair("Sair");
		btnSair.setActionCommand("0");
		pnlBaixo.add(btnSair);

		pnlMenu.add(pnlTopo, BorderLayout.NORTH);
		pnlMenu.add(pnlContas, BorderLayout.CENTER);
		pnlMenu.add(pnlBaixo, BorderLayout.SOUTH);

		frmMenu.setVisible(true);

		btnCorrente.addActionListener(this::avaliarOpcaoEscolhida);
		btnPoupanca.addActionListener(this::avaliarOpcaoEscolhida);
		btnSair.addActionListener(this::avaliarOpcaoEscolhida);
	}

	@Override
	protected void avaliarOpcaoEscolhida(ActionEvent e) {
		setOpcoes(Integer.parseInt(e.getActionCommand()));

		switch (getOpcoes()) {
		case 1:
			lblConta.setText("Conta Corrente");
			operarContaCC();
			trocarTela("contaCC");
			break;
		case 2:
			lblConta.setText("Conta Poupança");
			operarContaCP();
			trocarTela("contaCP");
			break;
		default:
			JOptionPane.showMessageDialog(this.frmMenu, "Voltando para a tela inicial", "Aviso Cadastro",
					JOptionPane.INFORMATION_MESSAGE);

			Sessao.setLogado(null);
			TelaAcesso telaAcesso = new TelaAcesso();
			telaAcesso.setVisible(true);
			frmMenu.dispose();
			break;
		}
	}

	public void operarContaCC() {
		pnlCorrente.removeAll();
		pnlOpcoes = new JPanel(new GridLayout(1, 2));
		pnlOpcoes.setBackground(Cores.fundo());

		pnlTopo = criaPnlTopo("Saldo: ***", false);

		JPanel pnlDir = new JPanel();
		pnlDir.setBackground(null);
		pnlDir.setLayout(new GridBagLayout());
		GridBagConstraints grid_Dir = new GridBagConstraints();
		grid_Dir.fill = GridBagConstraints.HORIZONTAL;
		grid_Dir.insets = new Insets(10, 0, 0, 0);

		grid_Dir.gridx = 0;
		grid_Dir.gridy = 0;
		btnDeposito = new Botao("Depósito");
		pnlDir.add(btnDeposito, grid_Dir);

		grid_Dir.gridx = 0;
		grid_Dir.gridy = 1;
		btnSaqueCC = new Botao("Saque");
		pnlDir.add(btnSaqueCC, grid_Dir);

		JPanel pnlEsq = new JPanel();
		pnlEsq.setLayout(new GridBagLayout());
		pnlEsq.setBackground(null);
		GridBagConstraints grid_Esq = new GridBagConstraints();
		grid_Esq.fill = GridBagConstraints.HORIZONTAL;

		grid_Esq.gridx = 0;
		grid_Esq.gridy = 0;
		JLabel lblInfo = new JLabel("Informações Corrente");
		lblInfo.setFont(new Font("Arial", Font.BOLD, 23));
		lblInfo.setForeground(Cores.fonte());
		pnlEsq.add(lblInfo, grid_Esq);

		grid_Esq.gridy = 1;
		grid_Esq.insets = new Insets(10, 0, 0, 0);
		JLabel lblInfoLM = new JLabel("Limite especial: " + Moeda.formatar(contaCC.getLimiteEspecial()));
		lblInfoLM.setFont(Fontes.texto());
		lblInfoLM.setForeground(Cores.fonte());
		pnlEsq.add(lblInfoLM, grid_Esq);

		pnlOpcoes.add(pnlDir);
		pnlOpcoes.add(pnlEsq);

		btnVoltar = new BotaoSair("Voltar");
		btnVoltar.setPreferredSize(new Dimension(80, 25));

		JPanel pnlBaixoCC = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlBaixoCC.setBackground(Cores.fundo());
		pnlBaixoCC.add(btnVoltar);

		pnlCorrente.add(pnlTopo, BorderLayout.NORTH);
		pnlCorrente.add(pnlOpcoes, BorderLayout.CENTER);
		pnlCorrente.add(pnlBaixoCC, BorderLayout.SOUTH);

		btnDeposito.addActionListener(e -> deposito(true));
		btnSaqueCC.addActionListener(e -> saque(true));

		btnVoltar.addActionListener(this::voltar);
		btnSaldo.addActionListener(e -> visibilidadeSaldo(true));

		frmMenu.setVisible(true);
	}

	private void operarContaCP() {
		pnlPoupanca.removeAll();
		pnlOpcoes = new JPanel(new GridLayout(1, 2));
		pnlOpcoes.setBackground(Cores.fundo());

		pnlTopo = criaPnlTopo("Saldo: ***", false);

		JPanel pnlDir = new JPanel();
		pnlDir.setBackground(null);
		pnlDir.setLayout(new GridBagLayout());
		GridBagConstraints grd = new GridBagConstraints();
		grd.fill = GridBagConstraints.HORIZONTAL;

		grd.gridx = 0;
		grd.gridy = 0;
		btnDeposito = new Botao("Depósito");
		pnlDir.add(btnDeposito, grd);

		grd.gridy = 1;
		grd.insets = new Insets(20, 0, 0, 0);
		btnSaqueCP = new Botao("Saque");
		pnlDir.add(btnSaqueCP, grd);

		JPanel pnlEsq = new JPanel();
		pnlEsq.setBackground(null);
		pnlEsq.setLayout(new GridBagLayout());
		GridBagConstraints grid_Esq = new GridBagConstraints();
		grid_Esq.fill = GridBagConstraints.HORIZONTAL;

		grid_Esq.gridx = 0;
		grid_Esq.gridy = 0;
		JLabel lblInfo = new JLabel("Informações Poupança");
		lblInfo.setFont(new Font("Arial", Font.BOLD, 23));
		lblInfo.setForeground(Cores.fonte());
		pnlEsq.add(lblInfo, grid_Esq);

		grid_Esq.gridy = 1;
		grid_Esq.insets = new Insets(10, 0, 0, 0);
		JLabel lblInfoRJM = new JLabel("Reajuste mensal: " + contaCP.getReajusteMensal());
		lblInfoRJM.setFont(Fontes.texto());
		lblInfoRJM.setForeground(Cores.fonte());
		pnlEsq.add(lblInfoRJM, grid_Esq);

		pnlOpcoes.add(pnlDir, BorderLayout.WEST);
		pnlOpcoes.add(pnlEsq, BorderLayout.EAST);

		btnVoltar = new BotaoSair("Voltar");

		JPanel pnlBaixoCP = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlBaixoCP.setBackground(Cores.fundo());
		pnlBaixoCP.add(btnVoltar);

		pnlPoupanca.add(pnlTopo, BorderLayout.NORTH);
		pnlPoupanca.add(pnlOpcoes, BorderLayout.CENTER);
		pnlPoupanca.add(pnlBaixoCP, BorderLayout.SOUTH);

		btnDeposito.addActionListener(e -> deposito(false));
		btnSaqueCP.addActionListener(e -> saque(false));

		btnVoltar.addActionListener(this::voltar);
		btnSaldo.addActionListener(e -> visibilidadeSaldo(false));

		frmMenu.setVisible(true);
	}

	private void visibilidadeSaldo(boolean isCorrente) {
		if (visivel) {
			iconSenha = new ImageIcon(getClass().getResource("/projetoBancario/img/mostrar.png"));
			ajuste = iconSenha.getImage().getScaledInstance(30, 25, Image.SCALE_DEFAULT);
			senha = new ImageIcon(ajuste);

			btnSaldo.setIcon(senha);
			if (isCorrente) {
				lblSaldo.setText(
						"Saldo: " + Moeda.formatar(correnteDAO.buscarCorrenteID(userLogado.getId()).getSaldo()));
			} else {
				lblSaldo.setText(
						"Saldo: " + Moeda.formatar(poupancaDAO.buscarPoupancaID(userLogado.getId()).getSaldo()));
			}

			visivel = false;
		} else {
			iconSenha = new ImageIcon(getClass().getResource("/projetoBancario/img/esconder.png"));
			ajuste = iconSenha.getImage().getScaledInstance(30, 25, Image.SCALE_DEFAULT);
			senha = new ImageIcon(ajuste);

			lblSaldo.setText("Saldo: ***");
			btnSaldo.setIcon(senha);
			visivel = true;
		}
	}

	private void voltar(ActionEvent e) {
		esconderSaldo();
		executarMenu();
		trocarTela("menu");
	}

	private void esconderSaldo() {
		iconSenha = new ImageIcon(getClass().getResource("/projetoBancario/img/esconder.png"));
		ajuste = iconSenha.getImage().getScaledInstance(30, 25, Image.SCALE_DEFAULT);
		senha = new ImageIcon(ajuste);

		btnSaldo.setIcon(senha);
		lblSaldo.setText("Saldo: ***");
		visivel = true;
	}

	public void deposito(boolean isConta) {
		esconderSaldo();

		frmMenu.add(new DepositoTela(this, isConta), "deposito");
		trocarTela("deposito");
	}

	public void saque(boolean isConta) {
		esconderSaldo();

		frmMenu.add(new SaqueTela(this, isConta), "saque");
		trocarTela("saque");
	}

	private JPanel criaPnlTopo(String titulo, boolean mostraUser) {
		JPanel pnl = new JPanel();
		pnl.setBackground(Cores.painel());
		pnl.setBorder(borda);
		pnl.setLayout(new BorderLayout());

		if (mostraUser) {
			Image imgUser = userLogado.getImgUser().getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
			btnUser = new JButton(new ImageIcon(imgUser));

			borda = BorderFactory.createLineBorder(Cores.fundo(), 2);
			btnUser.setBorder(borda);

			btnUser.setBackground(null);
			btnUser.setPreferredSize(new Dimension(40, 40));

			JLabel lblNomeUser = new Titulos(titulo);

			JPanel pnlUser = new JPanel(new FlowLayout(FlowLayout.LEFT));
			pnlUser.setBackground(null);
			pnlUser.add(btnUser);
			pnlUser.add(lblNomeUser);
			pnl.add(pnlUser, BorderLayout.WEST);

			btnUser.addActionListener(this::user);
		} else {
			Border padding = new EmptyBorder(5, 5, 5, 5);
			pnl.setBorder(padding);

			JPanel pnlSaldo = new JPanel(new FlowLayout(FlowLayout.LEFT));
			pnlSaldo.setBackground(null);

			pnl.add(lblConta, BorderLayout.NORTH);

			lblSaldo = new TextoBold(titulo);
			lblSaldo.setFont(new Font("Arial", Font.BOLD, 18));
			pnlSaldo.add(lblSaldo);
			pnl.add(pnlSaldo, BorderLayout.WEST);

			iconSenha = new ImageIcon(getClass().getResource("/projetoBancario/img/esconder.png"));
			ajuste = iconSenha.getImage().getScaledInstance(30, 25, Image.SCALE_DEFAULT);
			senha = new ImageIcon(ajuste);

			btnSaldo = new BotaoSaldo(senha);
			pnl.add(btnSaldo, BorderLayout.EAST);
		}

		return pnl;
	}

	private void user(ActionEvent e) {
		new TelaUsuario();
		frmMenu.dispose();
	}
}