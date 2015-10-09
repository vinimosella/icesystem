package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.LogFactory;
import vo.FornecedorVO;
import vo.IngredienteReceitaProdutoVO;
import vo.MateriaPrimaVO;
import vo.ProdutoVO;
import daoservice.IIngredienteReceitaProdutoDAO;

public class IngredienteReceitaProdutoDAO implements IIngredienteReceitaProdutoDAO{
	
	private Connection conexao;
	private PreparedStatement pstm;
	private ConnectionFactory fabrica;
	private ResultSet rs;
		
	{
		fabrica = ConnectionFactory.getInstance();
		
	}
	
	@Override
	public List<IngredienteReceitaProdutoVO> consultarIngredientes(ProdutoVO produto) {
		
		List<IngredienteReceitaProdutoVO> listaIngredientes = null;
		
		IngredienteReceitaProdutoVO ingredientes = null;
		
		try {
			
			conexao = fabrica.getConexao();
			
			pstm = conexao.prepareStatement("select id_materia_prima, id_produto, quantidade_materia, fornecedor, quantidade_disponivel, nome, sabor"
				                            + " from ingrediente_receita_produto irp inner join materia_prima mp"
				                            + " where irp.id_materia_prima = mp.id_materia_prima and irp.id_produto = ?");
			
			pstm.setLong(1, produto.getIdProduto());
		
			rs = pstm.executeQuery();
			
			listaIngredientes = new ArrayList<IngredienteReceitaProdutoVO>();
			
			while(rs.next()){
				
				ingredientes = new IngredienteReceitaProdutoVO();
				
				MateriaPrimaVO mp = new MateriaPrimaVO();
				
				//Setando a MateriaPrimaVO
				mp.setFornecedor(new FornecedorVO());
				mp.getFornecedor().setIdPessoaJuridica(rs.getInt("id_fornecedor"));
				mp.setIdMateriaPrima(rs.getInt("id_materia_prima"));
				mp.setNome(rs.getString("nome"));
				mp.setQuantidadeDisponivel(rs.getDouble("quantidade_disponivel"));
				mp.setSabor(rs.getString("sabor"));
				
				//Setando o IngredienteReceitaProdutoVO
				ingredientes.setMateriaPrima(mp);
				ingredientes.setProduto(new ProdutoVO());
				ingredientes.getProduto().setIdProduto(rs.getInt("id_produto"));
				ingredientes.setQuantidadeMateria(rs.getDouble("quantidade_materia"));				
				
				listaIngredientes.add(ingredientes);
			}
			
		} catch (SQLException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			listaIngredientes = null;
			
		} catch (ClassNotFoundException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			listaIngredientes = null;
			
		} finally {
			
			try {
				
				conexao.close();
				pstm.close();
				
				if(rs != null){
					
					rs.close();
				}
				
			} catch (SQLException e) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());

				listaIngredientes = null;
				
			}			
		
		}
		
		return listaIngredientes;
	}

}
