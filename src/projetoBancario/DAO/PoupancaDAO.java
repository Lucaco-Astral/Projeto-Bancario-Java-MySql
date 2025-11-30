package projetoBancario.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import projetoBancario.conexao.Conexao;
import projetoBancario.contaBanco.ContaPoupanca;
import projetoBancario.excecoes.ExcecoesSQL;
import projetoBancario.excecoes.excecoesSQL.ExcessaoConexao;

public class PoupancaDAO {
	private String sql;
	private ResultSet res;
	private ContaPoupanca CP;

	private PreparedStatement sta;

	public ContaPoupanca buscarPoupancaID(int id) throws ExcecoesSQL {
		try {
			sql = "SELECT saldo, reajusteMensal FROM contaPoupanca WHERE id_user = ?;";
			sta = Conexao.conectar().prepareStatement(sql);
			sta.setInt(1, id);

			res = sta.executeQuery();

			if (res.next()) {
				CP = new ContaPoupanca(res.getDouble("saldo"), res.getDouble("reajusteMensal"));
				return CP;
			}

			sta.close();
			res.close();
			return null;
		} catch (Exception e) {
			throw new ExcessaoConexao("Erro ao achar Conta Poupança");
		}
	}

	public void criarPoupancaID(int id) {
		try {
			sql = "INSERT INTO contaPoupanca (id_user, saldo, reajusteMensal) VALUES (?, ?, ?);";
			sta = Conexao.conectar().prepareStatement(sql);
			sta.setInt(1, id);
			sta.setDouble(2, 0);
			sta.setDouble(3, 0.2);
			sta.execute();
			sta.close();
		} catch (SQLException e) {
			throw new ExcessaoConexao("Erro ao criar Conta Poupanca");
		}
	}

	public void excluirPoupancaID(int id) throws ExcecoesSQL {
		try {
			sql = "DELETE FROM contaPoupanca WHERE id_user = ?";
			sta = Conexao.conectar().prepareStatement(sql);
			sta.setInt(1, id);
			sta.execute();
			sta.close();
		} catch (SQLException e) {
			throw new ExcessaoConexao("Erro ao excluir Conta Poupança");
		}
	}

	public void saldoPoupanca(ContaPoupanca cp, int id) throws ExcecoesSQL {
		try {
			sql = "UPDATE contaPoupanca SET saldo = ? WHERE id_user = ?;";
			sta = Conexao.conectar().prepareStatement(sql);
			sta.setDouble(1, cp.getSaldo());
			sta.setInt(2, id);
			sta.execute();
			sta.close();
		} catch (SQLException e) {
			throw new ExcessaoConexao("Erro ao atualiza o saldo da Conta Poupança");
		}
	}
}