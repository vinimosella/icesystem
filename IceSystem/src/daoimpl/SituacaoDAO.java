package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.LogFactory;
import vo.SituacaoVO;
import daoservice.ISituacaoDAO;

public class SituacaoDAO implements ISituacaoDAO{
	
	private Connection conexao;
	private PreparedStatement pstm;
	private ConnectionFactory fabrica;
	private ResultSet rs;
		
	{
		fabrica = ConnectionFactory.getInstance();		
	}
	
	@Override
	public List<SituacaoVO> consultarSituacoes() {
		
		SituacaoVO situacao = null;
		
		List<SituacaoVO> listaSituacoes = null;
		
		try {
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select id_situacao, descricao from Situacao");
			
			//Executa uma pesquisa no banco
			rs = pstm.executeQuery();
			
			listaSituacoes = new ArrayList<SituacaoVO>();
			
			//Carregando a listaCompras
			while(rs.next()){
				
				situacao = new SituacaoVO();
					
				situacao.setIdSituacao(rs.getInt("id_situacao"));
				situacao.setDescricao(rs.getString("descricao"));
				
				listaSituacoes.add(situacao);
				
			}
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			listaSituacoes = null;
			
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
			listaSituacoes = null;
			
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
				
				listaSituacoes = null;
				
			}			
		
		}
		
		return listaSituacoes;
	}

	public SituacaoVO consultarSituacaoPorDesc(String descricao) {
		
		SituacaoVO situacao = null;
		
		try {
			
			situacao = new SituacaoVO();
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select id_situacao, descricao from Situacao where descricao = ?");
			
			pstm.setString(1, descricao);
			
			//Executa a pesquisa no banco
			rs = pstm.executeQuery();
						
			//Carrega a situacao				
			situacao.setIdSituacao(rs.getInt("id_situacao"));
			situacao.setDescricao(rs.getString("descricao"));
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			situacao = null;
			
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
			situacao = null;
			
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

				situacao = null;
				
			}			
		
		}
		
		return situacao;
	}
	
}
