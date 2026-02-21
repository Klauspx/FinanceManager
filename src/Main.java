import factory.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public static void main(String[] args) throws SQLException {
    ConnectionFactory factory = new  ConnectionFactory();

    Connection conexao = factory.recuperarConexao();

    System.out.println("Conexão aberta com sucesso!");

    conexao.close();

}