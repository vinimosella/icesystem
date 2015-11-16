package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.LogFactory;
import vo.CidadeVO;
import vo.EstadoVO;

public class CidadeDAO {

	private Connection conexao;
	private PreparedStatement pstm;
	private ConnectionFactory fabrica;
	private ResultSet rs;
	
	{
		fabrica = ConnectionFactory.getInstance();		
	}
	
	public List<CidadeVO> consultaCidadesPorEstado(EstadoVO estado){
		
		List<CidadeVO> listaCidades;
		CidadeVO cidade;
		
		try{
			
			conexao = fabrica.getConexao();
			
			pstm = conexao.prepareStatement("select c.id_cidade, c.id_estado, c.nome from Cidade c "
					+ "inner join Estado e on c.id_estado = e.id_estado "
					+ "where c.id_estado=?");
			
			pstm.setInt(1, estado.getIdEstado());
			
			rs = pstm.executeQuery();
			
			listaCidades= new ArrayList<CidadeVO>();
			
			//Caregga a listaCidades
			while(rs.next()){
				
				cidade = new CidadeVO();
				cidade.setIdCidade(rs.getInt("id_cidade"));
				cidade.setNome(rs.getString("nome"));
				cidade.setEstado(estado);
				listaCidades.add(cidade);
			}
			
		}catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			listaCidades = null;
			
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
			listaCidades = null;
			
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

				listaCidades = null;
				
			}			
		
		}		
		
		return listaCidades;
	}
	
}
