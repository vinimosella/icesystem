package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.LogFactory;
import vo.OrdemProducaoVO;
import vo.ProdutoVO;
import vo.SituacaoVO;
import daoservice.IOrdemDeProducaoDAO;

public class OrdemDeProducaoDAO implements IOrdemDeProducaoDAO{

	private Connection conexao;
	private PreparedStatement pstm;
	private ConnectionFactory fabrica;
	private ResultSet rs;
		
	{
		fabrica = ConnectionFactory.getInstance();	
	}
	
	@Override
	public List<OrdemProducaoVO> consultarTodasOP() {
		
		OrdemProducaoVO op = null;
		
		List<OrdemProducaoVO> listaOP = null;
		
		try {
			
			//Cria a conex�o com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select op.id_produto, op.id_situacao, op.quantidade, op.data_solicitacao, p.nome from Ordem_Producao op"
					                       + " inner join Produto p on op.id_produto = p.id_produto");
			
			//Executa uma pesquisa no banco
			rs = pstm.executeQuery();
			
			listaOP = new ArrayList<OrdemProducaoVO>();
			
			//Carrega a listaOP
			while(rs.next()){
				
				op = new OrdemProducaoVO();
				
				op.setProduto(new ProdutoVO());
				op.getProduto().setIdProduto(rs.getInt("id_produto"));
				op.getProduto().setNome(rs.getString("nome"));
				op.setSituacao(new SituacaoVO());
				op.getSituacao().setIdSituacao(rs.getInt("id_situacao"));
				op.setQuantidade(rs.getInt("quantidade"));
				op.setDataSolicitacao(rs.getDate("data_solicitacao"));
				
				listaOP.add(op);
				
			}
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			listaOP = null;
			
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
			listaOP = null;
			
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

				listaOP = null;
				
			}			
		
		}
		
		return listaOP;
	}

	@Override
	public List<OrdemProducaoVO> consultarOPSolicitado() {
		
		OrdemProducaoVO op = null;
		
		List<OrdemProducaoVO> listaOP = null;
		
		try {
			
			//Cria a conex�o com o banco
			conexao = fabrica.getConexao();
			
			//Cria um [select] que sera executado no banco
			pstm = conexao.prepareStatement("select op.id_produto, op.id_situacao, op.quantidade, op.data_solicitacao, p.nome from Ordem_Producao op"
					                       + " inner join Produto p on op.id_produto = p.id_produto"
					                       + " inner join Situacao s on op.id_situacao = s.id_situacao where descricao = 'Solicitado'");
			
			//Executa uma pesquisa no banco
			rs = pstm.executeQuery();
			
			listaOP = new ArrayList<OrdemProducaoVO>();
			
			//Carrega a listaOP
			while(rs.next()){
				
				op = new OrdemProducaoVO();
				
				op.setProduto(new ProdutoVO());
				op.getProduto().setIdProduto(rs.getInt("id_produto"));
				op.getProduto().setNome(rs.getString("nome"));
				op.setSituacao(new SituacaoVO());
				op.getSituacao().setIdSituacao(rs.getInt("id_situacao"));
				op.setQuantidade(rs.getInt("quantidade"));
				op.setDataSolicitacao(rs.getDate("data_solicitacao"));
				
				listaOP.add(op);
				
			}
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			listaOP = null;
			
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
			listaOP = null;
			
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

				listaOP = null;
				
			}			
		
		}
		
		return listaOP;
	}

	@Override
	public List<OrdemProducaoVO> consultarOPFinalizado() {
		
		OrdemProducaoVO op = null;
		
		List<OrdemProducaoVO> listaOP = null;
		
		try {
			
			//Cria a conex�o com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select op.id_produto, op.id_situacao, op.quantidade, op.data_solicitacao, p.nome from Ordem_Producao op"
					                       + " inner join Produto p on op.id_produto = p.id_produto"
					                       + " inner join Situacao s on op.id_situacao = s.id_situacao where descricao = Finalizado");
			
			//Executa uma pesquisa no banco
			rs = pstm.executeQuery();
			
			listaOP = new ArrayList<OrdemProducaoVO>();
			
			//Carrega a listaOP
			while(rs.next()){
				
				op = new OrdemProducaoVO();
				
				op.setProduto(new ProdutoVO());
				op.getProduto().setIdProduto(rs.getInt("id_produto"));
				op.getProduto().setNome(rs.getString("nome"));
				op.setSituacao(new SituacaoVO());
				op.getSituacao().setIdSituacao(rs.getInt("id_situacao"));
				op.setQuantidade(rs.getInt("quantidade"));
				op.setDataSolicitacao(rs.getDate("data_solicitacao"));
				
				listaOP.add(op);
				
			}
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			listaOP = null;
			
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
			listaOP = null;
			
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

				listaOP = null;
				
			}			
		
		}
		
		return listaOP;
	}

	@Override
	public List<OrdemProducaoVO> consultarOPCancelado() {
		
		OrdemProducaoVO op = null;
		
		List<OrdemProducaoVO> listaOP = null;
		
		try {
			
			//Cria a conexao com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select op.id_produto, op.id_situacao, op.quantidade, op.data_solicitacao, p.nome from Ordem_Producao op"
					                       + " inner join Produto p on op.id_produto = p.id_produto"
					                       + " inner join Situacao s on op.id_situacao = s.id_situacao where descricao = Cancelado");
			
			//Executa uma pesquisa no banco
			rs = pstm.executeQuery();
			
			listaOP = new ArrayList<OrdemProducaoVO>();
			
			//Carrega a listaOP
			while(rs.next()){
				
				op = new OrdemProducaoVO();
				
				op.setProduto(new ProdutoVO());
				op.getProduto().setIdProduto(rs.getInt("id_produto"));
				op.getProduto().setNome(rs.getString("nome"));
				op.setSituacao(new SituacaoVO());
				op.getSituacao().setIdSituacao(rs.getInt("id_situacao"));
				op.setQuantidade(rs.getInt("quantidade"));
				op.setDataSolicitacao(rs.getDate("data_solicitacao"));
				
				listaOP.add(op);
				
			}
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			listaOP = null;
			
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
			listaOP = null;
			
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

				listaOP = null;
				
			}			
		
		}
		
		return listaOP;
	}

	@Override
	public boolean alterarOrdemProducao(OrdemProducaoVO op) {
		
		try {
			
			//Cria a conex�o com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [alter] que sera executado no banco
			pstm = conexao.prepareStatement("alter table Ordem_Producao set id_situacao=?, id_produto=?, quantidade=? where id_ordem_producao=?");
			
			pstm.setInt(1, op.getSituacao().getIdSituacao());
			pstm.setInt(2, op.getProduto().getIdProduto());
			pstm.setInt(3, op.getQuantidade());
			pstm.setLong(4, op.getIdOrdemProducao());
			
			//Executa uma atualiza��o no banco
			pstm.executeUpdate();
	
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
			return false;
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			return false;
			
		} finally {

			//Finalizando os processos
			try {

				conexao.close();
				pstm.close();

			} catch (SQLException sql) {

				LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
				
				sql.printStackTrace();
				
				return false;
			}

		}
		
		return true;
	}

	@Override
	public boolean cadastrarOP(OrdemProducaoVO op) {
		
		try {
			
			//Cria a conexao com o banco
			conexao = fabrica.getConexao();
			conexao.setAutoCommit(false); //Inicia uma transa��o
			
			//Cria o [insert] que sera executado no banco
			pstm = conexao.prepareStatement("insert into Ordem_Producao (id_produto, id_situacao, quantidade) values (?, ?, ?)");
						
			pstm.setInt(1, op.getProduto().getIdProduto());
			pstm.setInt(2, op.getSituacao().getIdSituacao());
			pstm.setInt(3, op.getQuantidade());
			
			//Executa uma atualiza��o no banco
			pstm.executeUpdate();
			
			//Em caso de sucesso, executa o commit do cadastro no banco
			conexao.commit();
			
		} catch (ClassNotFoundException cnf) {
			
			//Log do ClassNotFoundException
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
			//Caso ocorra algum erro, executa o rollback do cadastro no banco
			try {
				
				conexao.rollback();
				
				return false;
				
			} catch (SQLException sql) {
				
				//Log do rollback do ClassNotFoundException
				LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
				
				sql.printStackTrace();
				
				return false;
			}
			
		} catch (SQLException sql) {
			
			//Log do SQLException
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			//Caso ocorra algum erro, executa o rollback do cadastro no banco
			try {
				
				conexao.rollback();
				
				return false;
				
			} catch (SQLException sql2) {
				
				//Log do rollback do SQLException
				LogFactory.getInstance().gerarLog(getClass().getName(),sql2.getMessage());
				
				sql2.printStackTrace();
				
				return false;
			}

		}
		
		return true;
	}

	@Override
	public boolean excluirOP(OrdemProducaoVO op) {

		try {
			
			//Cria a conex�o com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [delete] que sera executado no banco
			pstm = conexao.prepareStatement("delete from Ordem_Producao where id_ordem_producao = ?");
			
			pstm.setLong(1, op.getIdOrdemProducao());
			
			//Executa uma atualiza��o no banco
			pstm.executeQuery();
						
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(), cnf.getMessage());
			
			cnf.printStackTrace();
			
			return false;
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(), sql.getMessage());
			
			sql.printStackTrace();
			
			return false;
			
		} finally{
			
			//Finalizando os recursos
			try {
				
				conexao.close();
				pstm.close();
				
			} catch (SQLException sql) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(), sql.getMessage());
				
				sql.printStackTrace();
				
				return false;
			}
			
		}
		
		return true;
	}

}
