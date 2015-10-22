package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.LogFactory;
import vo.ProdutoVO;
import daoservice.IProdutoDAO;

public class  ProdutoDAO implements IProdutoDAO{

	private Connection conexao;
	private PreparedStatement pstm;
	private ConnectionFactory fabrica;
	private ResultSet rs;
		
	{
		fabrica = ConnectionFactory.getInstance();
		
	}
	
	@Override
	public List<ProdutoVO> consultarTodosProdutos() {
		
		ProdutoVO p = null;
		
		List<ProdutoVO> listaProdutos = null;
		
		try {
			
			//Cria a conex�o com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select id_produto, quantidade_estoque, nome, sabor from Produto");
			
			//Executa a pesquisa no banco
			rs = pstm.executeQuery();
			
			listaProdutos = new ArrayList<ProdutoVO>();
			
			//Carrega a listaProdutos
			while(rs.next()){
				
				p = new ProdutoVO();
					
				p.setIdProduto(rs.getInt("id_produto"));
				p.setNome(rs.getString("nome"));
				p.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
				p.setSabor(rs.getString("sabor"));
				
				listaProdutos.add(p);
				
			}
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			listaProdutos = null;
			
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			listaProdutos = null;
			
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

				listaProdutos = null;
				
			}			
		
		}
		
		return listaProdutos;
	}
	
	@Override
	public ProdutoVO consultarProduto(ProdutoVO produto){
				
		try {
			
			//Cria a conex�o com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select p.id_produto, p.quantidade_estoque, p.nome, p.sabor from Produto p where p.id_produto = ?");
			
			pstm.setLong(1, produto.getIdProduto());
			
			//Executa a pesquisa no banco
			rs = pstm.executeQuery();
			
			//Carrega o produto				
			produto.setIdProduto(rs.getInt("id_produto"));
			produto.setNome(rs.getString("nome"));
			produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
			produto.setSabor(rs.getString("sabor"));
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			produto = null;
			
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			produto = null;
			
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

				produto = null;
				
			}			
		
		}
		
		return produto;
	}

	@Override
	public boolean alterarProduto(ProdutoVO produto) {
		
		try {
			
			//Cria a conex�o com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [alter] que sera executado no banco
			pstm = conexao.prepareStatement("alter table Produto set quantidade_estoque=?, nome=?, sabor=? where id_produto=?");
			
			pstm.setInt(1, produto.getQuantidadeEstoque());
			pstm.setString(2, produto.getNome());
			pstm.setString(3, produto.getSabor());
			pstm.setInt(4, produto.getIdProduto());
			
			//Executa uma atualiza��o no banco
			pstm.executeUpdate();
	
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			return false;
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			return false;
			
		} finally {

			//Finalizando os recursos
			try {

				conexao.close();
				pstm.close();

			} catch (SQLException sql) {

				LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			}

		}
		
		return true;
	}

	@Override
	public boolean cadastrarProduto(ProdutoVO produto) {
					
			try {
				
				//Cria a conex�o com o banco
				conexao = fabrica.getConexao();
				
				//Cria o [insert] que sera executado no banco
				pstm = conexao.prepareStatement("insert into Produto (quantidade_estoque, nome, sabor) values (?, ?, ?)");
				
				pstm.setInt(1, produto.getQuantidadeEstoque());
				pstm.setString(2, produto.getNome());
				pstm.setString(3, produto.getSabor());
				
				//Executa uma atualiza��o no banco
				pstm.executeUpdate();
				
			} catch (ClassNotFoundException cnf) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
				
			} catch (SQLException sql) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
				
			} finally{
				
				//Finalizando os recursos
				try {
					
					conexao.close();
					pstm.close();					
					
				} catch (SQLException sql) {
					
					LogFactory.getInstance().gerarLog(getClass().getName(), sql.getMessage());
				}
			}
		
		return true;
	}

	@Override
	public boolean excluirProduto(ProdutoVO produto) {
		
		try {
			
			//Cria a conex�o com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [delete] que sera executado no banco
			pstm = conexao.prepareStatement("delete from Produto where id_produto = ?");
			
			pstm.setInt(1, produto.getIdProduto());
			
			//Executa uma atualiza��o no banco
			pstm.executeQuery();
						
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(), cnf.getMessage());
			
			return false;
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(), sql.getMessage());
			
			return false;
			
		} finally{
			
			//Finalizando os recursos
			try {
				
				conexao.close();
				pstm.close();
				
			} catch (SQLException sql) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(), sql.getMessage());
				
				return false;
			}
			
		}
		
		return true;
	}
	
}
