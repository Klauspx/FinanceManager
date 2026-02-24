import dao.UsuarioDAO;
import factory.ConnectionFactory;
import modelo.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public static void main(String[] args) throws SQLException {
    ConnectionFactory factory = new  ConnectionFactory();
    Connection conexao = factory.recuperarConexao();
    UsuarioDAO usuarioDAO = new UsuarioDAO(conexao);
//SALVAR USUARIOS
//    Usuario novousuario = new Usuario(null, "Klaus", "klaus@gmail.com", "klaus123");
//
//    usuarioDAO.salvar(novousuario);


//LISTAR USUARIOS
//    List<Usuario> meusUsuarios = usuarioDAO.listarTodos();
//
//    for (Usuario usuario : meusUsuarios) {
//        System.out.println(usuario.getEmail());

//    usuarioDAO.deletar(1);
//ATUALIZAR USUARIOS
    Usuario usuarioEditado = new Usuario(2, "Nadson Klaus", "klausplima@gmail.com", "klaus321");

    usuarioDAO.atualizar(usuarioEditado);

    List<Usuario> meusUsuarios = usuarioDAO.listarTodos();

    System.out.println("--- Lista de Usuários Atualizada ---");
    for (Usuario usuario : meusUsuarios) {
        System.out.println("ID: " + usuario.getIdUsuario() + " | Nome: " + usuario.getNome());
    }

    conexao.close();
}