package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public Connection recuperarConexao(){
        try {
           return DriverManager.getConnection(
                   "jdbc:postgresql://localhost:5432/financemanager",
                   "postgres",
                   "1234"
           );
        } catch (SQLException erro){
            throw new RuntimeException("Erro ao conectar ao banco de dados.", erro);
        }
    }
}
