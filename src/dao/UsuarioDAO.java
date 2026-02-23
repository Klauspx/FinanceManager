package dao;

import modelo.Usuario;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection conexao;

    public UsuarioDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void salvar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";

    try {
        PreparedStatement pstm = conexao.prepareStatement(sql);

        pstm.setString(1, usuario.getNome());
        pstm.setString(2, usuario.getEmail());
        pstm.setString(3, usuario.getSenha());

        pstm.executeUpdate();

        pstm.close();

        System.out.println("Usuário " + usuario.getNome() + " salvo com sucesso!");

    }catch (Exception erro){
        throw new RuntimeException("Erro ao salvar o usuário.", erro);
    }
    }

    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT * FROM usuarios";

        try {
            PreparedStatement pstm = conexao.prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String senha = rs.getString("senha");

                Usuario usuario = new Usuario(id, nome, email, senha);
                lista.add(usuario);
            }

            rs.close();
            pstm.close();

        }catch (Exception erro){
            throw new RuntimeException("Erro ao listar usuários!", erro);
        }

        return lista;
    }
}
