package daoimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private static ConnectionFactory fabrica;	
	private Connection conexao;
	
	
	private ConnectionFactory(){
				
	}
	
	public static ConnectionFactory getInstance(){
		
		if(fabrica == null){
			
			fabrica = new ConnectionFactory();
		}
		
		return fabrica;		
	}
	
    public Connection getConexao() throws ClassNotFoundException, SQLException{
		
  	  Class.forName("net.sourceforge.jtds.jdbc.Driver");
  	  //TODO ALTERAR O SCHEMA - ID/SENHA
  	  conexao = DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1433/NOME_SCHEMA","sa","sql");
		
		return conexao;
	}	
		
	}
