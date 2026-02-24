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

    public void deletar (int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try {
            PreparedStatement pstm = conexao.prepareStatement(sql);

            pstm.setInt(1, id);

            pstm.executeUpdate();

            pstm.close();

            System.out.println("Usuário deletado com sucesso!");

        }catch (Exception erro){
            throw new RuntimeException("Erro ao deletar usuário.", erro);
        }
    }

    public void atualizar (Usuario usuario) {
        String sql = "UPDATE usuarios SET nome = ?, email = ?, senha = ? WHERE id = ?";

        try{
            PreparedStatement pstm = conexao.prepareStatement(sql);

            pstm.setString(1, usuario.getNome());
            pstm.setString(2, usuario.getEmail());
            pstm.setString(3, usuario.getSenha());
            pstm.setInt(4, usuario.getIdUsuario());

            pstm.executeUpdate();
            pstm.close();

            System.out.println("Atualizado com sucesso!");
        }catch (Exception erro){
            throw new RuntimeException("Erro ao atualizar!");
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
