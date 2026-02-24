package dao;

import modelo.Lancamento;
import modelo.TipoTransacao;
import modelo.Usuario;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public List<Lancamento> listarTodos(){
        List<Lancamento> lista = new ArrayList<>();

        String sql = "SELECT * FROM lancamentos";

        try{
            PreparedStatement pstm = conexao.prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()){
                String descricao = rs.getString("descricao");
                BigDecimal valor = rs.getBigDecimal("valor");
                LocalDate data = rs.getDate("data").toLocalDate();
                TipoTransacao tipo = TipoTransacao.valueOf(rs.getString("tipo"));
                String categoria = rs.getString("categoria");
                int idUsuario = rs.getInt("usuario_id");
                Usuario dono = new Usuario(idUsuario, null, null, null);

                Lancamento lancamento = new Lancamento(descricao, valor, data, tipo,categoria, dono);
                lista.add(lancamento);
            }

            rs.close();
            pstm.close();

            }catch (Exception erro) {
                throw new RuntimeException("Erro ao listar lancamento!", erro);
        }

        return lista;
    }
}
