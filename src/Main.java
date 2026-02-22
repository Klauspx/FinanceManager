import dao.UsuarioDAO;
import factory.ConnectionFactory;
import modelo.Usuario;

import java.sql.Connection;
import java.sql.SQLException;

public static void main(String[] args) throws SQLException {
    ConnectionFactory factory = new  ConnectionFactory();
    Connection conexao = factory.recuperarConexao();

    UsuarioDAO usuarioDAO = new UsuarioDAO(conexao);

    Usuario novousuario = new Usuario(null, "Klaus", "klaus@gmail.com", "klaus123");

    usuarioDAO.salvar(novousuario);

    conexao.close();
}