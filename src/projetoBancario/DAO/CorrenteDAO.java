package projetoBancario.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import projetoBancario.conexao.Conexao;
import projetoBancario.contaBanco.ContaCorrente;
import projetoBancario.excecoes.ExcecoesSQL;
import projetoBancario.excecoes.excecoesSQL.ExcessaoConexao;

public class CorrenteDAO {
	private String sql;
	private PreparedStatement sta;
	private ResultSet res;

	private ContaCorrente CC;

	public ContaCorrente buscarCorrenteID(int id) throws ExcecoesSQL {
		try {
			sql = "SELECT saldo, limiteEspecial FROM contaCorrente WHERE id_user = ?;";
			sta = Conexao.conectar().prepareStatement(sql);

			sta.setInt(1, id);
			res = sta.executeQuery();

			if (res.next()) {
				CC = new ContaCorrente(res.getInt("limiteEspecial"), res.getInt("saldo"));
				return CC;
			}

			sta.close();
			res.close();
			return null;
		} catch (SQLException e) {
			throw new ExcessaoConexao("Erro ao achar Conta Corrente");
		}
	}

	public void criarCorrenteID(int id) throws ExcecoesSQL {
		try {
			sql = "INSERT INTO contaCorrente(id_user, limiteEspecial, saldo) VALUES (?, ?, ?);";
			sta = Conexao.conectar().prepareStatement(sql);

			sta.setInt(1, id);
			sta.setDouble(2, 1000);
			sta.setDouble(3, 0);
			sta.execute();

			sta.close();
		} catch (SQLException e) {
			throw new ExcessaoConexao("Erro ao criar Conta Corrente");
		}
	}

	public void excluirCorrenteID(int id) throws ExcecoesSQL {
		try {
			sql = "DELETE FROM contaCorrente WHERE id_user = ?";
			sta = Conexao.conectar().prepareStatement(sql);

			sta.setInt(1, id);
			sta.execute();

			sta.close();
		} catch (Exception e) {
			throw new ExcessaoConexao("Erro ao excluir Conta Corrente");
		}
	}

	public void saldoCorrente(ContaCorrente cc, int id) throws ExcecoesSQL {
		try {
			sql = "UPDATE contaCorrente SET saldo = ? WHERE id_user = ?;";
			sta = Conexao.conectar().prepareStatement(sql);

			sta.setDouble(1, cc.getSaldo());
			sta.setInt(2, id);
			sta.execute();

			sta.close();
		} catch (SQLException e) {
			throw new ExcessaoConexao("Erro ao atualizaro saldo da Conta Corrente");
		}
	}
}