package projetoBancario.telas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import projetoBancario.DAO.CorrenteDAO;
import projetoBancario.DAO.PoupancaDAO;
import projetoBancario.contaBanco.ContaCorrente;
import projetoBancario.contaBanco.ContaPoupanca;
import projetoBancario.contaBanco.MenuPrincipal;
import projetoBancario.excecoes.ExcecoesBanco;
import projetoBancario.informacoes.Usuario;
import projetoBancario.sessao.Sessao;
import projetoBancario.utilitarios.Moeda;
import projetoBancario.utilitarios.botoes.Botao;
import projetoBancario.utilitarios.botoes.BotaoSair;
import projetoBancario.utilitarios.interfaces.Cores;
import projetoBancario.utilitarios.labels.Texto;
import projetoBancario.utilitarios.labels.Titulos;

public class DepositoTela extends JPanel {

	private static final long serialVersionUID = 1L;

	private MenuPrincipal MP;

	private ContaPoupanca CP;
	private ContaCorrente CC;

	private JPanel pnlDeposito, pnlBaixo;

	private JLabel lblDeposito;
	private JTextField txtDeposito;

	private JButton btnVoltar, btnDeposito;

	private final Usuario user;

	private boolean isConta;

	private final CorrenteDAO correnteDAO;
	private final PoupancaDAO poupancaDAO;

	public DepositoTela(MenuPrincipal mn, boolean isConta) {
		this.MP = mn;
		this.isConta = isConta;

		correnteDAO = new CorrenteDAO();
		poupancaDAO = new PoupancaDAO();

		user = Sessao.getLogado();

		setLayout(new BorderLayout(5, 0));
		setBackground(Cores.fundo());

		// Título da Tela
		String tituloConta;
		if (isConta) {
			tituloConta = "Conta Corrente";
		} else {
			tituloConta = "Conta Poupança";
		}
		JLabel lblTitulo = new Titulos("DEPÓSITO: " + tituloConta);
		lblTitulo.setHorizontalAlignment(JLabel.CENTER);
		add(lblTitulo, BorderLayout.NORTH);

		// Painel Central
		pnlDeposito = new JPanel();
		pnlDeposito.setBackground(Cores.fundo());
		pnlDeposito.setLayout(new GridBagLayout());

		GridBagConstraints grdLblDeposito = new GridBagConstraints();
		grdLblDeposito.fill = GridBagConstraints.HORIZONTAL;
		grdLblDeposito.insets = new Insets(5, 10, 5, 10);
		grdLblDeposito.gridx = 0;
		grdLblDeposito.gridy = 0;

		lblDeposito = new Texto("Valor do Depósito (R$)");
		lblDeposito.setFont(new Font("Arial", Font.PLAIN, 17));
		pnlDeposito.add(lblDeposito, grdLblDeposito);

		GridBagConstraints grdTxtDeposito = new GridBagConstraints();
		grdTxtDeposito.fill = GridBagConstraints.HORIZONTAL;
		grdTxtDeposito.gridx = 0;
		grdTxtDeposito.gridy = 1;

		txtDeposito = new JTextField();
		txtDeposito.setFont(new Font("Arial", Font.PLAIN, 12));
		txtDeposito.setPreferredSize(new Dimension(280, 25));
		pnlDeposito.add(txtDeposito, grdTxtDeposito);

		GridBagConstraints grdBtnDeposito = new GridBagConstraints();
		grdBtnDeposito.insets = new Insets(15, 10, 5, 10);
		grdBtnDeposito.anchor = GridBagConstraints.CENTER;
		grdBtnDeposito.gridy = 2;

		btnDeposito = new Botao("Depositar");
		pnlDeposito.add(btnDeposito, grdBtnDeposito);

		JPanel pnlPrincipal = new JPanel(new GridBagLayout());
		pnlPrincipal.setBackground(Cores.fundo());
		pnlPrincipal.add(pnlDeposito);

		// Painel Inferior
		pnlBaixo = new JPanel();
		pnlBaixo.setBackground(Cores.fundo());
		pnlBaixo.setLayout(new FlowLayout(FlowLayout.RIGHT));

		btnVoltar = new BotaoSair("Voltar");
		pnlBaixo.add(btnVoltar);

		add(pnlPrincipal, BorderLayout.CENTER);
		add(pnlBaixo, BorderLayout.SOUTH);

		btnDeposito.addActionListener(this::depositar);
		btnVoltar.addActionListener(this::voltar);
	}

	private void depositar(ActionEvent e) {
		try {
			double valorDeposito = Double.parseDouble(txtDeposito.getText());

			if (isConta) {
				CC = user.getCC();

				CC.depositar(valorDeposito);

				JOptionPane.showMessageDialog(this, "Depósito feito com sucesso!\nValor: "
						+ Moeda.formatar(valorDeposito) + "\nNovo Saldo: " + Moeda.formatar(CC.getSaldo()));

				correnteDAO.saldoCorrente(CC, user.getId());
			} else {
				CP = user.getCP();

				CP.depositar(valorDeposito);

				JOptionPane.showMessageDialog(this, "Depósito feito com sucesso!\nValor: "
						+ Moeda.formatar(valorDeposito) + "\nNovo Saldo: " + Moeda.formatar(CP.getSaldo()));

				poupancaDAO.saldoPoupanca(CP, user.getId());
			}

			txtDeposito.setText("");
		} catch (NumberFormatException e3) {
			JOptionPane.showMessageDialog(this, "Valor inválido. Por favor, digite apenas números.", "Erro de Formato",
					JOptionPane.ERROR_MESSAGE);
			txtDeposito.setText("");
		} catch (ExcecoesBanco e2) {
			JOptionPane.showMessageDialog(this, e2.getMessage(), "Erro de Depósito", JOptionPane.WARNING_MESSAGE);
			txtDeposito.setText("");
		}
	}

	private void voltar(ActionEvent e) {
		if (isConta) {
			MP.trocarTela("contaCC");
		} else {
			MP.trocarTela("contaCP");
		}
	}
}