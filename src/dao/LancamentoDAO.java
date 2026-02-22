package dao;

import java.sql.Connection;

public class LancamentoDAO {
    private Connection conexao;

    public LancamentoDAO(Connection conexao) {
        this.conexao = conexao;
    }


}
