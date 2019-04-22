package trab_bd2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//classe responsável por fazer a conexão com banco
public class Conexao {
    String servername = "localhost";
    String mydb = "trab_bd2";
    String usuario = "root";
    String senha = "";
    
    Connection conexao; 
    Conexao() throws SQLException{
        conexao = DriverManager.getConnection("jdbc:mysql://localhost/trab_bd2", usuario, senha);
    }
}


