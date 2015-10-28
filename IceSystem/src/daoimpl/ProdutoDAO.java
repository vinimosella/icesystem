package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.LogFactory;
import util.Utilidades;
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
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select id_produto, quantidade_estoque, nome, sabor from Produto"
										  + " where id_status = ?");
			
			pstm.setInt(1, 1);
			
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
			
			sql.printStackTrace();
			
			listaProdutos = null;
			
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
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
				
				sql.printStackTrace();

				listaProdutos = null;
				
			}			
		
		}
		
		return listaProdutos;
	}
	
	@Override
	public ProdutoVO consultarProduto(ProdutoVO produto){
				
		try {
			
			//Cria a conexão com o banco
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
			
			sql.printStackTrace();
			
			produto = null;
			
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
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
				
				sql.printStackTrace();

				produto = null;
				
			}			
		
		}
		
		return produto;
	}

	@Override
	public boolean alterarProduto(ProdutoVO produto) {
		
		try {
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			conexao.setAutoCommit(false); //Inicia uma transação
			
			//Cria o [alter] que sera executado no banco
			pstm = conexao.prepareStatement("update Produto set quantidade_estoque=?, nome=?, sabor=? where id_produto=?");
			
			pstm.setInt(1, produto.getQuantidadeEstoque());
			pstm.setString(2, produto.getNome());
			pstm.setString(3, produto.getSabor());
			pstm.setInt(4, produto.getIdProduto());
			
			//Executa uma atualização no banco
			pstm.executeUpdate();
			
			//Em caso de sucesso, executa o commit do update no banco
			conexao.commit(); 
	
		} catch (ClassNotFoundException cnf) {
			
			//Caso ocorra algum erro, executa o rollback do update no banco
			try {
				
				conexao.rollback();
				
			} catch (SQLException sql) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(), sql.getMessage());
				
				sql.printStackTrace();
				
				return false;
				
			}
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
			return false;
			
		} catch (SQLException sql) {
			
			//Caso ocorra algum erro, executa o rollback do update no banco
			try {
				
				conexao.rollback();
				
			} catch (SQLException sql2) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(), sql2.getMessage());
				
				sql2.printStackTrace();
				
				return false;
				
			}
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			return false;
			
		} finally {

			//Finalizando os recursos
			try {

				conexao.close();
				pstm.close();

			} catch (SQLException sql) {

				//Caso ocorra algum erro, executa o rollback do update no banco
				try {
					
					conexao.rollback();
					
				} catch (SQLException sql2) {
					
					LogFactory.getInstance().gerarLog(getClass().getName(), sql2.getMessage());
					
					sql2.printStackTrace();
					
					return false;
					
				}
				
				LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
				
				sql.printStackTrace();
				
				return false;
			}

		}
		
		return true;
	}

	@Override
	public ProdutoVO cadastrarProduto(ProdutoVO produto) {
		
			try {
				
				//Cria a conexão com o banco
				conexao = fabrica.getConexao(); 				
				conexao.setAutoCommit(false); //Inicia uma transação
				
				//Cria o [insert] que sera executado no banco
				pstm = conexao.prepareStatement("insert into Produto (nome, sabor, quantidade_estoque, id_status) values (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
				
				pstm.setString(1, produto.getNome());
				
				pstm.setString(2, produto.getSabor());
			
				pstm.setInt(3, produto.getQuantidadeEstoque());
				
				pstm.setInt(4, Utilidades.STATUS_ATIVO.getIdStatus());
				
				//Executa uma atualização no banco
				pstm.executeUpdate();
				
				//Recebe o id gerado automaticamente no insert anterior
				rs = pstm.getGeneratedKeys();
				
				if(rs != null && rs.next()){
					
					Integer idProduto = rs.getInt(1);
					
					//Carrega o produto com o id gerado pelo banco
					produto.setIdProduto(idProduto);
					
					System.out.println(idProduto);
					
					//Em caso de sucesso, executa o commit do cadastro no banco
					conexao.commit();
					
				}
				else{
					
					conexao.rollback();
	
				}
				
			} catch (ClassNotFoundException cnf) {
				
				cnf.printStackTrace();
				
				//Caso ocorra algum erro, executa o rollback do cadastro no banco
				try {
					
					conexao.rollback();
					
				} catch (SQLException sql) {
					
					LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
					
					sql.printStackTrace();
					
					return null;
					
				}
				
			} catch (SQLException sql) {
				
				sql.printStackTrace();
				
				//Caso ocorra algum erro, executa o rollback do cadastro no banco
				try {
					
					conexao.rollback();
					
				} catch (SQLException sql2) {
					
					LogFactory.getInstance().gerarLog(getClass().getName(),sql2.getMessage());
					
					sql2.printStackTrace();
					
					return null;
					
				}			
				
			} finally{
				
				//Finalizando os recursos
				try {
					
					conexao.close();
					pstm.close();					
					
				} catch (SQLException sql) {
					
					//Caso ocorra algum erro, executa o rollback do cadastro no banco
					try {
						
						conexao.rollback();
						
					} catch (SQLException sql2) {
						
						LogFactory.getInstance().gerarLog(getClass().getName(), sql2.getMessage());
						
						sql2.printStackTrace();
						
						return null;
						
					}
					
					LogFactory.getInstance().gerarLog(getClass().getName(), sql.getMessage());
					
					sql.printStackTrace();
					
					return null;
				}
			}
		
		return produto;
	}

	@Override
	public boolean excluirProduto(ProdutoVO produto) {
		
		try {
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			conexao.setAutoCommit(false); //Inicia uma transação
			
			//Cria o [delete] que sera executado no banco
			pstm = conexao.prepareStatement("update Produto set id_status = ? where id_produto = ?");
			
			pstm.setInt(1, 2);
			
			pstm.setInt(2, produto.getIdProduto());
			
			//Executa uma atualização no banco
			pstm.executeUpdate();
			
			//Em caso de sucesso, executa o commit da exclusão no banco
			conexao.commit();
						
		} catch (ClassNotFoundException cnf) {
			
			//Caso ocorra algum erro, executa o rollback da exclusão no banco
			try {
				
				conexao.rollback();
				
			} catch (SQLException sql) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(), sql.getMessage());
				
				sql.printStackTrace();
				
				return false;
				
			}
			
			LogFactory.getInstance().gerarLog(getClass().getName(), cnf.getMessage());
			
			cnf.printStackTrace();
						
			return false;
			
		} catch (SQLException sql) {
			
			//Caso ocorra algum erro, executa o rollback da exclusão no banco
			try {
				
				conexao.rollback();
				
			} catch (SQLException sql2) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(), sql2.getMessage());
				
				sql2.printStackTrace();
				
				return false;
				
			}
			
			LogFactory.getInstance().gerarLog(getClass().getName(), sql.getMessage());
			
			sql.printStackTrace();
			
			return false;
			
		} finally{
			
			//Finalizando os recursos
			try {
				
				conexao.close();
				pstm.close();
				
			} catch (SQLException sql) {
				
				//Caso ocorra algum erro, executa o rollback da exclusão no banco
				try {
					
					conexao.rollback();
					
				} catch (SQLException sql2) {
					
					LogFactory.getInstance().gerarLog(getClass().getName(), sql2.getMessage());
					
					sql2.printStackTrace();
					
					return false;
				}
				
				LogFactory.getInstance().gerarLog(getClass().getName(), sql.getMessage());
				
				sql.printStackTrace();
				
				return false;
			}
			
		}
		
		return true;
	}
	
}
