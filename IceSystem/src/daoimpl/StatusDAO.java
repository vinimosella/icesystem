package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.LogFactory;
import vo.StatusVO;
import daoservice.IStatusDAO;

public class StatusDAO implements IStatusDAO{
	
	private Connection conexao;
	private PreparedStatement pstm;
	private ConnectionFactory fabrica;
	private ResultSet rs;
		
	{
		fabrica = ConnectionFactory.getInstance();		
	}
	
	@Override
	public StatusVO consultarPorDescricao(String descricao) {
		
		StatusVO status = null;
		
		try {
			
			status = new StatusVO();
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select id_status, descricao from Status where descricao = ?");
			
			pstm.setString(1, descricao);
			
			//Executa a pesquisa no banco
			rs = pstm.executeQuery();
			
			if(rs.next()){
				
				//Carrega a situacao				
				//status.setIdStatus(rs.getInt("id_status"));
				//status.setDescricao(rs.getString("descricao"));
				
			}
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			status = null;
			
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
			status = null;
			
		} finally {
			
			//Finalizando os processos
			try {
				
				conexao.close();
				pstm.close();
				
				if(rs != null){
					
					rs.close();
				}
				
			} catch (SQLException sql) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
				
				sql.printStackTrace();

				status = null;
				
			}			
		
		}
		
		return status;
	}

}
