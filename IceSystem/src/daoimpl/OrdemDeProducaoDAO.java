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
				
			conexao = fabrica.getConexao();
			
			pstm = conexao.prepareStatement("select op.id_produto, op.id_situacao, op.quantidade, op.data_solicitacao, p.nome from Ordem_Producao op"
					                       + " inner join Produto p on op.id_produto = p.id_produto");
			
			rs = pstm.executeQuery();
			
			listaOP = new ArrayList<OrdemProducaoVO>();
			
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
			
		} catch (SQLException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			listaOP = null;
			
		} catch (ClassNotFoundException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			listaOP = null;
			
		} finally {
			
			try {
				
				conexao.close();
				pstm.close();
				
				if(rs != null){
					
					rs.close();
				}
				
			} catch (SQLException e) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());

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
				
			conexao = fabrica.getConexao();
			
			pstm = conexao.prepareStatement("select op.id_produto, op.id_situacao, op.quantidade, op.data_solicitacao, p.nome from Ordem_Producao op"
					                       + " inner join Produto p on op.id_produto = p.id_produto"
					                       + " inner join Situacao s on op.id_situacao = s.id_situacao where descricao = Solicitado");
			
			rs = pstm.executeQuery();
			
			listaOP = new ArrayList<OrdemProducaoVO>();
			
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
			
		} catch (SQLException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			listaOP = null;
			
		} catch (ClassNotFoundException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			listaOP = null;
			
		} finally {
			
			try {
				
				conexao.close();
				pstm.close();
				
				if(rs != null){
					
					rs.close();
				}
				
			} catch (SQLException e) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());

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
				
			conexao = fabrica.getConexao();
			
			pstm = conexao.prepareStatement("select op.id_produto, op.id_situacao, op.quantidade, op.data_solicitacao, p.nome from Ordem_Producao op"
					                       + " inner join Produto p on op.id_produto = p.id_produto"
					                       + " inner join Situacao s on op.id_situacao = s.id_situacao where descricao = Finalizado");
			
			rs = pstm.executeQuery();
			
			listaOP = new ArrayList<OrdemProducaoVO>();
			
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
			
		} catch (SQLException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			listaOP = null;
			
		} catch (ClassNotFoundException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			listaOP = null;
			
		} finally {
			
			try {
				
				conexao.close();
				pstm.close();
				
				if(rs != null){
					
					rs.close();
				}
				
			} catch (SQLException e) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());

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
				
			conexao = fabrica.getConexao();
			
			pstm = conexao.prepareStatement("select op.id_produto, op.id_situacao, op.quantidade, op.data_solicitacao, p.nome from Ordem_Producao op"
					                       + " inner join Produto p on op.id_produto = p.id_produto"
					                       + " inner join Situacao s on op.id_situacao = s.id_situacao where descricao = Cancelado");
			
			rs = pstm.executeQuery();
			
			listaOP = new ArrayList<OrdemProducaoVO>();
			
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
			
		} catch (SQLException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			listaOP = null;
			
		} catch (ClassNotFoundException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			listaOP = null;
			
		} finally {
			
			try {
				
				conexao.close();
				pstm.close();
				
				if(rs != null){
					
					rs.close();
				}
				
			} catch (SQLException e) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());

				listaOP = null;
				
			}			
		
		}
		
		return listaOP;
	}

	@Override
	public boolean alterarOrdemProducao(OrdemProducaoVO op) {
		
		try {
			
			conexao = fabrica.getConexao();
			
			pstm = conexao.prepareStatement("alter table Ordem_Producao set id_situacao=?, id_produto=?, quantidade=? where id_ordem_producao=?");
			
			pstm.setInt(1, op.getSituacao().getIdSituacao());
			pstm.setInt(2, op.getProduto().getIdProduto());
			pstm.setInt(3, op.getQuantidade());
			pstm.setLong(4, op.getIdOrdemProducao());
	
		} catch (ClassNotFoundException c) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),c.getMessage());
			
			return false;
			
		} catch (SQLException s) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),s.getMessage());
			
			return false;
			
		} finally {

			try {

				conexao.close();
				pstm.close();

			} catch (SQLException s) {

				LogFactory.getInstance().gerarLog(getClass().getName(),s.getMessage());
			}

		}
		
		return true;
	}

	@Override
	public boolean cadastrarOP(OrdemProducaoVO op) {
		
		try {
			
			conexao = fabrica.getConexao();
			conexao.setAutoCommit(false); //Inicia uma transação
			
			pstm = conexao.prepareStatement("insert into Ordem_Producao (id_produto, id_situacao, quantidade) values (?, ?, ?)");
						
			pstm.setInt(1, op.getProduto().getIdProduto());
			pstm.setInt(2, op.getSituacao().getIdSituacao());
			pstm.setInt(3, op.getQuantidade());
			
			pstm.executeUpdate();
			
			conexao.commit();
			
		} catch (ClassNotFoundException c) {
			
			//Log do ClassNotFoundException
			LogFactory.getInstance().gerarLog(getClass().getName(),c.getMessage());
			
			try {
				
				conexao.rollback();
				
				return false;
				
			} catch (SQLException s) {
				
				//Log do rollback do ClassNotFoundException
				LogFactory.getInstance().gerarLog(getClass().getName(),s.getMessage());
			}
			
		} catch (SQLException s) {
			
			//Log do SQLException
			LogFactory.getInstance().gerarLog(getClass().getName(),s.getMessage());
			
			try {
				
				conexao.rollback();
				
				return false;
				
			} catch (SQLException s1) {
				
				//Log do rollback do SQLException
				LogFactory.getInstance().gerarLog(getClass().getName(),s1.getMessage());
				
				return false;
			}

		}
		
		return true;
	}

}
