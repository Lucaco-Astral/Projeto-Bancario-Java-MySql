package projetoBancario.DAO;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import projetoBancario.conexao.Conexao;
import projetoBancario.excecoes.ExcecoesSQL;
import projetoBancario.excecoes.excecoesSQL.ExcessaoAlterar;
import projetoBancario.excecoes.excecoesSQL.ExcessaoCadastar;
import projetoBancario.excecoes.excecoesSQL.ExcessaoExcluir;
import projetoBancario.excecoes.excecoesSQL.ExcessaoImagem;
import projetoBancario.excecoes.excecoesSQL.ExcessaoUsuario;
import projetoBancario.informacoes.Usuario;

public class UsuarioDAO {
	private PreparedStatement sta;
	private ResultSet res;

	private final CorrenteDAO correnteDAO;
	private final PoupancaDAO poupancaDAO;
	private String sql;

	public UsuarioDAO() {
		correnteDAO = new CorrenteDAO();
		poupancaDAO = new PoupancaDAO();
	}

	public void cadastrarUser(Usuario user) throws ExcecoesSQL {
		try {
			sql = "INSERT INTO usuario (email_user, nome_user, senha_user, img_user, adm) VALUES ( ?, ?, ?, ?, ?);";
			sta = Conexao.conectar().prepareStatement(sql);

			sta.setString(1, user.getEmail());
			sta.setString(2, user.getNome());

			String senha = new String(user.getSenha());
			sta.setString(3, senha);

			sta.setBlob(4, imageByte(user.getImgUser()));
			sta.setBoolean(5, false);
			sta.execute();

			user = procurarUserEmail(user.getEmail());
			correnteDAO.criarCorrenteID(2);
			poupancaDAO.criarPoupancaID(2);

			sta.close();
		} catch (SQLException e) {
			throw new ExcessaoCadastar("Erro ao cadastrar usuário no banco de dados " + e.getMessage());
		}
	}

	private ImageIcon byteImage(Blob blob) throws ExcecoesSQL {
		try {
			byte[] img = blob.getBytes(1, (int) blob.length());
			ByteArrayInputStream byteInputSteam = new ByteArrayInputStream(img);
			BufferedImage bufferImage = ImageIO.read(byteInputSteam);

			ImageIcon imgIcon = new ImageIcon(bufferImage);
			return imgIcon;
		} catch (Exception e) {
			throw new ExcessaoImagem("Erro ao trazer a Imagemm do banco de dados");
		}
	}

	private ByteArrayInputStream imageByte(ImageIcon imgIcon) throws ExcecoesSQL {
		Image img = imgIcon.getImage();

		BufferedImage bufferImage = new BufferedImage(img.getWidth(null), img.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);

		bufferImage.getGraphics().drawImage(img, 0, 0, null);

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			ImageIO.write(bufferImage, "png", baos);
			return new ByteArrayInputStream(baos.toByteArray());
		} catch (Exception e) {
			throw new ExcessaoImagem("Erro ao salvar Imagem");
		}
	}

	public void excluirUser(Usuario user) throws ExcecoesSQL {
		try {
			sql = "DELETE FROM usuario WHERE cod_user = ?;";

			sta = Conexao.conectar().prepareStatement(sql);
			sta.setInt(1, user.getId());
			sta.execute();

			correnteDAO.excluirCorrenteID(user.getId());
			poupancaDAO.excluirPoupancaID(user.getId());

			sta.close();
		} catch (SQLException e) {
			throw new ExcessaoExcluir("Erro ao excluir as informações no banco de dados");
		}
	}

	public void atualizarUser(Usuario user) throws ExcecoesSQL {
		try {
			sql = "UPDATE usuario SET nome_user = ?, email_user = ?, senha_user = ?, img_user = ? WHERE id_user = ?";
			sta = Conexao.conectar().prepareStatement(sql);

			sta.setString(1, user.getNome());
			sta.setString(2, user.getEmail());

			String senha = new String(user.getSenha());
			sta.setString(3, senha);

			sta.setBlob(4, imageByte(user.getImgUser()));
			sta.setInt(5, user.getId());

			sta.execute();

			sta.close();
		} catch (SQLException e) {
			throw new ExcessaoAlterar("Erro ao alterar informação do usuário no banco de dados");
		}
	}

	public boolean verificarEmail(String email) {
		if (procurarUserEmail(email) != null) {
			return true;
		} else {
			return false;
		}
	}

	public Usuario procurarUserEmail(String email) throws ExcecoesSQL {
		try {
			sql = "SELECT * FROM usuario WHERE email_user = ?";
			sta = Conexao.conectar().prepareStatement(sql);
			sta.setString(1, email);

			res = sta.executeQuery();

			if (res.next()) {
				String emailUser = res.getString("email_user");
				String nomeUser = res.getString("nome_user");
				String senha = res.getString("senha_user");
				ImageIcon imgUser = byteImage(res.getBlob("img_user"));
				int id = res.getInt("id_user");

				Usuario usuario = new Usuario(emailUser, nomeUser, senha.toCharArray(), imgUser, id);
				usuario.setCC(correnteDAO.buscarCorrenteID(id));
				usuario.setCP(poupancaDAO.buscarPoupancaID(id));

				sta.close();
				res.close();
				return usuario;
			}

			sta.close();
			res.close();
			return null;
		} catch (SQLException e) {
			throw new ExcessaoUsuario("Usuario não cadastrado");
		}
	}
}