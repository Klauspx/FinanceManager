package dao;

import modelo.Lancamento;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class LancamentoDAO {
    private Connection conexao;

    public LancamentoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void salvar(Lancamento lancamento){
        String sql = "INSERT INTO lancamentos (descricao, valor, data, tipo, categoria, usuario_id) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstm = conexao.prepareStatement(sql);

            pstm.setString(1, lancamento.getDescricao());
            pstm.setBigDecimal(2, lancamento.getValor());
            pstm.setDate(3, java.sql.Date.valueOf(lancamento.getData()));
            pstm.setString(4, lancamento.getTipo().name());
            pstm.setString(5, lancamento.getCategoria());
            pstm.setInt(6, lancamento.getUsuario().getIdUsuario());

            pstm.executeUpdate();

            pstm.close();

            System.out.println("Lançamento salvo com sucesso!");
        }catch (Exception erro){
            throw new RuntimeException("Erro ao salvar lançamento!");
        }
    }
}
