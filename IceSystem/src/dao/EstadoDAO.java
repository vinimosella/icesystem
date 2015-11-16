package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.LogFactory;
import vo.EstadoVO;

public class EstadoDAO {

	private Connection conexao;
	private PreparedStatement pstm;
	private ConnectionFactory fabrica;
	private ResultSet rs;
	
	{
		fabrica = ConnectionFactory.getInstance();		
	}
	
	public List<EstadoVO> consultaEstados(){
		
		EstadoVO estado;
		List<EstadoVO> listaEstados;
		
		try{
			
			conexao = fabrica.getConexao();
			
			pstm = conexao.prepareStatement("select id_estado, sigla, nome from Estado");
			
			rs = pstm.executeQuery();
			
			listaEstados= new ArrayList<EstadoVO>();
			
			//Caregga a listaEstados
			while(rs.next()){
				
				estado = new EstadoVO();
				estado.setIdEstado(rs.getInt("id_estado"));
				estado.setSigla(rs.getString("sigla"));
				estado.setNome(rs.getString("nome"));
				listaEstados.add(estado);
			}
			
		}catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			listaEstados = null;
			
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
			listaEstados = null;
			
		} finally {
			
			//Finalizando os recursos
			try {
				
				conexao.close();
				pstm.close();
				
				if(rs != null){
					
					rs.close();
				}
				
			} catch (SQLException sql) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
				
				sql.printStackTrace();

				listaEstados = null;
				
			}			
		
		}
		
		return listaEstados;
	}
	
}
