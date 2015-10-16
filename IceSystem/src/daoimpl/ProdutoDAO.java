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
				
			conexao = fabrica.getConexao();
			
			pstm = conexao.prepareStatement("select id_produto, quantidade_estoque, nome, sabor from Produto");
			
			rs = pstm.executeQuery();
			
			listaProdutos = new ArrayList<ProdutoVO>();
			
			while(rs.next()){
				
				p = new ProdutoVO();
					
				p.setIdProduto(rs.getInt("id_produto"));
				p.setNome(rs.getString("nome"));
				p.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
				p.setSabor(rs.getString("sabor"));
				
				listaProdutos.add(p);
				
			}
			
		} catch (SQLException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			listaProdutos = null;
			
		} catch (ClassNotFoundException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			listaProdutos = null;
			
		} finally {
			
			try {
				
				conexao.close();
				pstm.close();
				
				if(rs != null){
					
					rs.close();
				}
				
			} catch (SQLException e) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());

				listaProdutos = null;
				
			}			
		
		}
		
		return listaProdutos;
	}
	
	@Override
	public ProdutoVO consultarProduto(ProdutoVO produto){
				
		ProdutoVO p = null;
		
		try {
				
			conexao = fabrica.getConexao();
			
			pstm = conexao.prepareStatement("select p.id_produto, p.quantidade_estoque, p.nome, p.sabor from Produto p where p.id_produto = ?");
			
			pstm.setLong(1, produto.getIdProduto());
		
			rs = pstm.executeQuery();
				
			p = new ProdutoVO();
				
			p.setIdProduto(rs.getInt("id_produto"));
			p.setNome(rs.getString("nome"));
			p.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
			p.setSabor(rs.getString("sabor"));
			
		} catch (SQLException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			p = null;
			
		} catch (ClassNotFoundException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			p = null;
			
		} finally {
			
			try {
				
				conexao.close();
				pstm.close();
				
				if(rs != null){
					
					rs.close();
				}
				
			} catch (SQLException e) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());

				p = null;
				
			}			
		
		}
		
		return p;
	}

	@Override
	public boolean alterarProduto(ProdutoVO produto) {
		
		try {
			
			conexao = fabrica.getConexao();
			
			pstm = conexao.prepareStatement("alter table Produto set quantidade_estoque=?, nome=?, sabor=? where id_produto=?");
			
			pstm.setInt(1, produto.getQuantidadeEstoque());
			pstm.setString(2, produto.getNome());
			pstm.setString(3, produto.getSabor());
			pstm.setInt(4, produto.getIdProduto());
	
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
	
}
