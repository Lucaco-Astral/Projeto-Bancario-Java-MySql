package projetoBancario.conexao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import projetoBancario.excecoes.ExcecoesSQL;
import projetoBancario.excecoes.excecoesSQL.ExcessaoConexao;

public class Conexao {
	private static Connection conexao;

	private static Properties props = new Properties();

	static {
		try {
			props.load(new FileInputStream("config.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = props.getProperty("db.url");
	private static final String USER = props.getProperty("db.user");
	private static final String SENHA = props.getProperty("db.pass");

	public static Connection conectar() throws ExcecoesSQL {
		try {
			Class.forName(DRIVER);

			if (conexao == null || conexao.isClosed()) {

				conexao = DriverManager.getConnection(URL, USER, SENHA);

				return conexao;
			} else {
				return conexao;
			}
		} catch (SQLException erro) {
			throw new ExcessaoConexao("Erro ao conectar com o servidor");
		} catch (ClassNotFoundException e) {
			throw new ExcessaoConexao("Erro ao achar o DRIVER do Servidor");
		}
	}
}