package projetoBancario.utilitarios;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import projetoBancario.informacoes.Usuario;

public class TabelaModelo extends AbstractTableModel {
	private final String[] colunas = { "Email", "Nome", "Corrente", "Poupança", "Limite Especial", "Reajuste Mensal" };
	private final ArrayList<Usuario> listaUsuarios;

	public TabelaModelo(ArrayList<Usuario> lista) {
		this.listaUsuarios = lista;
	}

	@Override
	public int getRowCount() {
		return listaUsuarios.size();
	}

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public String getColumnName(int column) {
		return colunas[column];
	}

	// Método que pega os valores na tabela
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Usuario user = listaUsuarios.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return user.getEmail();
		case 1:
			return user.getNome();
		case 2:
			return user.getCC() != null ? Moeda.formatar(user.getCC().getSaldo()) : Moeda.formatar(0.00);
		case 3:
			return user.getCP() != null ? Moeda.formatar(user.getCP().getSaldo()) : Moeda.formatar(0.00);
		case 4:
			return user.getCC().getLimiteEspecial();
		case 5:
			return user.getCP().getReajusteMensal();
		default:
			return null;
		}
	}

	// Método que permite apenas a coluna 4 e 5 serem alteradas
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 4 || columnIndex == 5) {
			return true;
		}

		return false;
	}

	// Método que verifica qual linha foi escolhida para alterar
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Usuario user = listaUsuarios.get(rowIndex);

		try {
			String valorStr = ((String) aValue).replaceAll("[^0-9,-]", "").replace(',', '.');
			double novoValor = Double.parseDouble(valorStr);

			if (columnIndex == 4) {
				user.getCC().setLimiteEspecial(novoValor);
			} else if (columnIndex == 5) {
				user.getCP().setReajusteMensal(novoValor);
			}

			fireTableRowsInserted(rowIndex, columnIndex);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Valor inválido: " + aValue, "Tabela de Usuários",
					JOptionPane.WARNING_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, "Erro ao salvar o valor: " + e.getMessage(), "Tabela Usuários",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}