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
import daoservice.IIngredienteProdutoReceitaDAO;

public class IngredienteProdutoReceitaDAO implements IIngredienteProdutoReceitaDAO{

	private Connection conexao;
	private PreparedStatement pstm;
	private ConnectionFactory fabrica;
	private ResultSet rs;
		
	{
		fabrica = ConnectionFactory.getInstance();		
	}
	
	@Override
	public List<IngredienteReceitaProdutoVO> consultarIngredientesReceita(ProdutoVO produto) {
		
		IngredienteReceitaProdutoVO irp = null;
		
		List<IngredienteReceitaProdutoVO> listaIRP = null;
		
		try {
			
			//Cria a conex�o com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select irp.quantidade_materia, irp.id_produto, irp.id_materia_prima, mp.nome, mp.sabor, mp.quantidade_disponivel, pj.razao_social from Ingrediente_Receita_Produto irp"
					                        + " inner join Materia_Prima mp on irp.id_materia_prima = mp.id_materia_prima"
					                        + " inner join Fornecedor f on mp.id_fornecedor_pj = f.id_fornecedor_pj"
					                        + " inner join Pessoa_Juridica pj on f.id_fornecedor_pj = pj.id_pessoa_juridica"
					                        + " where irp.id_produto = ?");
			
			pstm.setLong(1, produto.getIdProduto());
			
			//Executa a pesquisa no banco
			rs = pstm.executeQuery();
			
			listaIRP = new ArrayList<IngredienteReceitaProdutoVO>();
			
			//Carrega a listaProdutos
			while(rs.next()){
				
				irp = new IngredienteReceitaProdutoVO();
				
				irp.setMateriaPrima(new MateriaPrimaVO());
				irp.getMateriaPrima().setIdMateriaPrima(rs.getInt("id_materia_prima"));
				irp.getMateriaPrima().setNome(rs.getString("nome"));
				irp.getMateriaPrima().setQuantidadeDisponivel(rs.getDouble("quantidade_disponivel"));
				irp.getMateriaPrima().setSabor(rs.getString("sabor"));
				irp.getMateriaPrima().setFornecedor(new FornecedorVO());
				irp.getMateriaPrima().getFornecedor().setRazaoSocial(rs.getString("razao_social"));
				irp.setProduto(produto);
				irp.getProduto().setIdProduto(rs.getInt("id_produto"));
				irp.setQuantidadeMateria(rs.getDouble("quantidade_materia"));
				
				listaIRP.add(irp);
				
			}
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			listaIRP = null;
			
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
			listaIRP = null;
			
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

				listaIRP = null;
				
			}			
		
		}
		
		return listaIRP;
	}

	@Override
	public boolean cadastrarIngredientesReceita(IngredienteReceitaProdutoVO irp) {
		
		try {
			
			//Cria a conex�o com o banco
			conexao = fabrica.getConexao(); 				
			conexao.setAutoCommit(false); //Inicia uma transa��o
			
			//Cria o [insert] que sera executado no banco
			pstm = conexao.prepareStatement("insert into Ingrediente_Produto_Receita (id_produto, id_materia_prima, quantidade_materia) values (?, ?, ?)");
			
			pstm.setInt(1, irp.getProduto().getIdProduto());
			pstm.setInt(2, irp.getMateriaPrima().getIdMateriaPrima());
			pstm.setDouble(3, irp.getQuantidadeMateria());
			
			//Executa uma atualiza��o no banco
			pstm.executeUpdate();
			
			//Em caso de sucesso, executa o commit do cadastro no banco
			conexao.commit();		
			
		} catch (ClassNotFoundException cnf) {
			
			//Caso ocorra algum erro, executa o rollback do cadastro no banco
			try {
				
				conexao.rollback();
				
			} catch (SQLException sql) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
				
				sql.printStackTrace();
				
				return false;
				
			}
			
		} catch (SQLException sql) {
			
			//Caso ocorra algum erro, executa o rollback do cadastro no banco
			try {
				
				conexao.rollback();
				
			} catch (SQLException sql2) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),sql2.getMessage());
				
				sql2.printStackTrace();
				
				return false;
				
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
					
					return false;
					
				}
				
				LogFactory.getInstance().gerarLog(getClass().getName(), sql.getMessage());
				
				sql.printStackTrace();
				
				return false;
			}
		}
	
	return true;
}

	@Override
	public boolean excluirIngredientesReceita(IngredienteReceitaProdutoVO irp) {
		
		try {
			
			//Cria a conex�o com o banco
			conexao = fabrica.getConexao();
			conexao.setAutoCommit(false); //Inicia uma transa��o
			
			//Cria o [delete] que sera executado no banco
			pstm = conexao.prepareStatement("delete from Ingrediente_Produto_Receita where id_produto = ? and id_materia_prima = ?");
			
			pstm.setInt(1, irp.getProduto().getIdProduto());
			
			pstm.setInt(2, irp.getMateriaPrima().getIdMateriaPrima());
			
			
			//Executa uma atualiza��o no banco
			pstm.executeUpdate();
			
			//Em caso de sucesso, executa o commit da exclus�o no banco
			conexao.commit();
						
		} catch (ClassNotFoundException cnf) {
			
			//Caso ocorra algum erro, executa o rollback da exclus�o no banco
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
			
			//Caso ocorra algum erro, executa o rollback da exclus�o no banco
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
				
				//Caso ocorra algum erro, executa o rollback da exclus�o no banco
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
