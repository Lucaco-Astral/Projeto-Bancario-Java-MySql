package projetoBancario.telas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
import projetoBancario.utilitarios.botoes.Botao;
import projetoBancario.utilitarios.botoes.BotaoSair;
import projetoBancario.utilitarios.interfaces.Cores;
import projetoBancario.utilitarios.labels.Texto;
import projetoBancario.utilitarios.labels.Titulos;
import java.awt.Font;
import java.awt.Dimension;

public class SaqueTela extends JPanel {

	private static final long serialVersionUID = 1L;
	private MenuPrincipal MP;

	private ContaPoupanca CP;
	private ContaCorrente CC;

	private JPanel pnlSaque, pnlBaixo;

	private JLabel lblSaque;
	private JTextField txtSaque;

	private JButton btnVoltar, btnSaque;

	private boolean isConta;

	private final Usuario usuario;

	private final CorrenteDAO correnteDAO;
	private final PoupancaDAO poupancaDAO;

	public SaqueTela(MenuPrincipal mn, boolean isConta) {
		this.MP = mn;
		this.isConta = isConta;

		correnteDAO = new CorrenteDAO();
		poupancaDAO = new PoupancaDAO();

		usuario = Sessao.getLogado();

		setLayout(new BorderLayout(0, 0));
		setBackground(Cores.fundo());

		// Título da Tela
		String tituloConta;
		if (isConta) {
			tituloConta = "Conta Corrente";
		} else {
			tituloConta = "Conta Poupança";
		}
		JLabel lblTitulo = new Titulos("SAQUE: " + tituloConta);
		lblTitulo.setHorizontalAlignment(JLabel.CENTER);
		add(lblTitulo, BorderLayout.NORTH);

		// Painel Central
		pnlSaque = new JPanel();
		pnlSaque.setBackground(Cores.fundo());
		pnlSaque.setLayout(new GridBagLayout());

		GridBagConstraints grdLblSaque = new GridBagConstraints();
		grdLblSaque.fill = GridBagConstraints.HORIZONTAL;
		grdLblSaque.insets = new Insets(5, 10, 5, 10);

		grdLblSaque.gridx = 0;
		grdLblSaque.gridy = 0;
		lblSaque = new Texto("Valor do Saque (R$)");
		lblSaque.setFont(new Font("Arial", Font.PLAIN, 17));
		pnlSaque.add(lblSaque, grdLblSaque);

		GridBagConstraints grdTxtSaque = new GridBagConstraints();
		grdLblSaque.fill = GridBagConstraints.HORIZONTAL;
		grdTxtSaque.gridx = 0;
		grdTxtSaque.gridy = 1;

		txtSaque = new JTextField();
		txtSaque.setFont(new Font("Arial", Font.PLAIN, 12));
		txtSaque.setPreferredSize(new Dimension(280, 25));
		pnlSaque.add(txtSaque, grdTxtSaque);

		GridBagConstraints grdBtnSaque = new GridBagConstraints();
		grdBtnSaque.fill = GridBagConstraints.NONE;
		grdBtnSaque.insets = new Insets(15, 10, 5, 10);
		grdBtnSaque.gridy = 2;

		btnSaque = new Botao("Sacar");
		pnlSaque.add(btnSaque, grdBtnSaque);

		JPanel pnlPrincipal = new JPanel(new GridBagLayout());
		pnlPrincipal.setBackground(Cores.fundo());
		GridBagConstraints gbc_pnlSaque = new GridBagConstraints();
		pnlPrincipal.add(pnlSaque, gbc_pnlSaque);

		// Painel Inferior
		pnlBaixo = new JPanel();
		pnlBaixo.setBackground(Cores.fundo());
		pnlBaixo.setLayout(new FlowLayout(FlowLayout.RIGHT));

		btnVoltar = new BotaoSair("Voltar");
		pnlBaixo.add(btnVoltar);

		add(pnlPrincipal, BorderLayout.CENTER);
		add(pnlBaixo, BorderLayout.SOUTH);

		btnSaque.addActionListener(this::sacar);
		btnVoltar.addActionListener(this::voltar);
	}

	private void sacar(ActionEvent e) {
		try {
			double valorSaque = Double.parseDouble(txtSaque.getText());

			if (isConta) {
				CC = usuario.getCC();
				CC.sacar(valorSaque);

				JOptionPane.showMessageDialog(this,
						"Saque feito com sucesso!\nValor: R$" + String.format("%.2f", valorSaque) + "\nNovo Saldo: R$"
								+ String.format("%.2f", CC.getSaldo()));

				correnteDAO.saldoCorrente(CC, usuario.getId());
			} else {
				CP = usuario.getCP();
				CP.sacar(valorSaque);

				JOptionPane.showMessageDialog(this,
						"Saque feito com sucesso!\nValor: R$" + String.format("%.2f", valorSaque) + "\nNovo Saldo: R$"
								+ String.format("%.2f", CP.getSaldo()));

				poupancaDAO.saldoPoupanca(CP, usuario.getId());
			}

			txtSaque.setText("");
		} catch (NumberFormatException e2) {
			JOptionPane.showMessageDialog(this, "Valor inválido. Por favor, digite apenas números.", "Erro de Formato",
					JOptionPane.ERROR_MESSAGE);
			txtSaque.setText("");
		} catch (ExcecoesBanco e2) {
			JOptionPane.showMessageDialog(this, e2.getMessage(), "Erro de Saque", JOptionPane.WARNING_MESSAGE);
			txtSaque.setText("");
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