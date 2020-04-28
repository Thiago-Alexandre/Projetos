package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {
    
    private final String URL_CONEXAO = "jdbc:mariadb://localhost:3306/agenda";
    private final String USUARIO = "root";
    private final String SENHA = "123";
    
    public Connection recuperarConexao(){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            return DriverManager.getConnection(URL_CONEXAO,USUARIO,SENHA);
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex.getMessage());
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}