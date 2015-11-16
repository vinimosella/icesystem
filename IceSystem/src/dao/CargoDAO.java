package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.LogFactory;
import vo.CargoVO;

public class CargoDAO {

	private Connection conexao;
	private PreparedStatement pstm;
	private ConnectionFactory fabrica;
	private ResultSet rs;
		
	{
		fabrica = ConnectionFactory.getInstance();	
	}
	
	public List<CargoVO> consultaCargos(){
		
		List<CargoVO> listaCargos;
		CargoVO cargo;
		
		try {
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select id_cargo, funcao from Cargo");
			
			//Executa uma pesquisa no banco
			rs = pstm.executeQuery();
			
			listaCargos = new ArrayList<CargoVO>();
			
			//Carregando a listaClientes
			while(rs.next()) {
				
				cargo = new CargoVO();
				cargo.setIdCargo(rs.getByte("id_cargo"));
				cargo.setFuncao(rs.getString("funcao"));
				
				listaCargos.add(cargo);

			}				
			
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			listaCargos = null;
			
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
			listaCargos = null;
			
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
				
				listaCargos = null;
				
			}			
		
		}
		
		return listaCargos;
	}
	
}
