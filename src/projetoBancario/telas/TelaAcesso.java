package projetoBancario.telas;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import projetoBancario.utilitarios.botoes.Botao;
import projetoBancario.utilitarios.botoes.BotaoSair;
import projetoBancario.utilitarios.interfaces.Cores;
import projetoBancario.utilitarios.labels.Titulos;

public class TelaAcesso extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private final JButton btnLogin, btnCadastrar, btnSair;
	private final Container conte;

	public TelaAcesso() {
		setTitle("Banker Acesso");
		conte = getContentPane();
		conte.setBackground(Cores.fundo());
		setSize(550, 350);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		conte.setLayout(new BorderLayout());

		JLabel lblTitulo = new Titulos("Bem-vindo ao Sistema Bancário");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 25));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel pnlTopo = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlTopo.setBackground(Cores.fundo());
		pnlTopo.setBorder(new EmptyBorder(30, 0, 30, 0));
		pnlTopo.add(lblTitulo);

		JPanel pnlCentro = new JPanel(new GridBagLayout());
		pnlCentro.setBackground(Cores.fundo());

		GridBagConstraints grdCadastro = new GridBagConstraints();
		grdCadastro.insets = new Insets(10, 0, 10, 0);
		grdCadastro.fill = GridBagConstraints.HORIZONTAL;

		grdCadastro.gridx = 0;
		grdCadastro.gridy = 0;
		btnCadastrar = new Botao("Cadastrar");
		btnCadastrar.setPreferredSize(new Dimension(150, 40));
		pnlCentro.add(btnCadastrar, grdCadastro);

		GridBagConstraints grdLogin = new GridBagConstraints();
		grdLogin.insets = new Insets(10, 0, 10, 0);
		grdLogin.fill = GridBagConstraints.HORIZONTAL;

		grdLogin.insets = new Insets(10, 30, 10, 0);
		btnLogin = new Botao("Login");
		btnLogin.setPreferredSize(new Dimension(150, 40));
		pnlCentro.add(btnLogin, grdLogin);

		JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		pnlBotoes.setBackground(Cores.fundo());
		btnSair = new BotaoSair("Sair");
		btnSair.setText("Fechar");
		pnlBotoes.add(btnSair);

		conte.add(pnlTopo, BorderLayout.NORTH);
		conte.add(pnlCentro, BorderLayout.CENTER);
		conte.add(pnlBotoes, BorderLayout.SOUTH);
		setVisible(true);

		btnCadastrar.addActionListener(this);
		btnLogin.addActionListener(this);
		btnSair.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCadastrar) {
			CadastroTela cadastroTela = new CadastroTela();
			cadastroTela.setVisible(true);
			dispose();
		} else if (e.getSource() == btnLogin) {
			LoginTela loginTela = new LoginTela();
			loginTela.setVisible(true);
			dispose();
		} else if (e.getSource() == btnSair) {
			System.exit(0);
		}
	}
}