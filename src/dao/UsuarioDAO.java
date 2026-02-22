package dao;

import modelo.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

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

        conexao.close();

        System.out.println("Usuário " + usuario.getNome() + " salvo com sucesso!");

    }catch (Exception erro){
        throw new RuntimeException("Erro ao salvar o usuário.", erro);
    }
    }
}
