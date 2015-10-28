package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.LogFactory;
import vo.CompraVO;
import vo.FornecedorVO;
import vo.FuncionarioVO;
import vo.ItemCompraVO;
import vo.MateriaPrimaVO;
import vo.SituacaoVO;
import daoservice.ICompraDAO;

public class CompraDAO implements ICompraDAO{

	private Connection conexao;
	private PreparedStatement pstm;
	private ConnectionFactory fabrica;
	private ResultSet rs;
		
	{
		fabrica = ConnectionFactory.getInstance();	
	}
	
	@Override
	public List<CompraVO> consultarCompras() {
		
		CompraVO c = null;
		
		List<CompraVO> listaCompras = null;
		
		try {
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select c.id_compra, c.data_compra, c.id_funcionario, c.id_situacao, s.descricao, f.nome from Compra c"
					                       + " inner join Funcionario f on c.id_funcionario = f.id_funcionario"
										   + " inner join Situacao s on s.id_situacao = c.id_situacao");
			
			//Executa uma pesquisa no banco
			rs = pstm.executeQuery();
			
			listaCompras = new ArrayList<CompraVO>();
			
			//Carregando a listaCompras
			while(rs.next()){
				
				c = new CompraVO();
					
				c.setIdCompra(rs.getLong("id_compra"));
				c.setDataCompra(rs.getDate("data_compra"));
				c.setFuncionario(new FuncionarioVO());
				c.getFuncionario().setIdFuncionario(rs.getInt("id_funcionario"));
				c.getFuncionario().setNome(rs.getString("nome"));
				c.setSituacao(new SituacaoVO());
				c.getSituacao().setIdSituacao(rs.getInt("id_situacao"));
				c.getSituacao().setDescricao(rs.getString("descricao"));
				
				listaCompras.add(c);
				
			}
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			listaCompras = null;
			
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
			listaCompras = null;
			
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
				
				listaCompras = null;
				
			}			
		
		}
		
		return listaCompras;
	}

	@Override
	public List<ItemCompraVO> detalharCompra(CompraVO compra) {
		
		ItemCompraVO item = null;
		
		List<ItemCompraVO> listaItens = null;
		
		try {
				
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select mp.id_materia_prima, mp.id_fornecedor_pj, mp.nome, mp.quantidade_disponivel, mp.sabor, i.quantidade, i.valor from Item_Compra i"
					                       + " inner join Materia_Prima mp on mp.id_materia_prima = i.id_materia_prima where i.id_compra=?");
			
			pstm.setLong(1, compra.getIdCompra());
			
			//Executa o select no banco
			rs = pstm.executeQuery();
			
			listaItens = new ArrayList<ItemCompraVO>();
			
			MateriaPrimaVO mp = null;
			
			//Carregando a listaItens
			while(rs.next()){
				
				mp = new MateriaPrimaVO();
				
				item = new ItemCompraVO();
					
				mp.setFornecedor(new FornecedorVO());
				mp.getFornecedor().setIdPessoaJuridica(rs.getInt("id_fornecedor_pj"));
				mp.setNome(rs.getString("nome"));
				mp.setQuantidadeDisponivel(rs.getDouble("quantidade_disponivel"));
				mp.setSabor(rs.getString("sabor"));
				mp.setIdMateriaPrima(rs.getInt("id_materia_prima"));
				
				item.setMateriaPrima(mp);
				item.setCompra(compra);
				item.setQuantidade(rs.getDouble("quantidade"));
				item.setValor(rs.getDouble("valor"));
				
				listaItens.add(item);
				
			}
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			listaItens = null;
			
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			listaItens = null;
			
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

				listaItens = null;
				
			}			
		
		}
		
		return listaItens;
	}
	
	@Override
	public boolean cadastrarCompra(CompraVO compra, List<ItemCompraVO> listaItensCompra){
				
		try {
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			conexao.setAutoCommit(false); //Inicia uma transação
			
			//Cria o [insert] que sera executado no banco
			pstm = conexao.prepareStatement("insert into Compra (id_funcionario, id_situacao) values (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
						
			pstm.setInt(1, compra.getFuncionario().getIdFuncionario());
			pstm.setInt(2, compra.getSituacao().getIdSituacao());
			
			//Executa uma atualização no banco
			pstm.executeUpdate();
			
			//Recebe o id gerado automaticamente no insert anterior
			rs = pstm.getGeneratedKeys();
			
			
			if(rs != null && rs.next()){
				
				int idCompra = rs.getInt(1);
				
				//Cria o [insert] que sera executado no banco
				pstm = conexao.prepareStatement("insert into Item_Compra (id_compra, id_materia_prima, quantidade, valor) values (?, ?, ?, ?)");
				
				for (ItemCompraVO itemCompra : listaItensCompra) {

					pstm.setInt(1, idCompra);
					pstm.setInt(2, itemCompra.getMateriaPrima().getIdMateriaPrima());
					pstm.setDouble(3, itemCompra.getQuantidade());
					pstm.setDouble(4, itemCompra.getValor());
					
					//Executa uma atualização no banco
					pstm.executeUpdate();
					
				}
				
				//Em caso de sucesso, executa o commit do cadastro no banco
				conexao.commit();
				
			}
			else{
				
				conexao.rollback();
				
				return false;
			}
			
		} catch (ClassNotFoundException cnf) {
			
			//Caso ocorra algum erro, executa o rollback do cadastro no banco
			try {
				
				conexao.rollback();
				
			} catch (SQLException sql) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
				
				sql.printStackTrace();
				
				return false;
			}
						
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
			return false;
			
		} catch (SQLException sql) {
						
			//Caso ocorra algum erro, executa o rollback do cadastro no banco
			try {
				
				conexao.rollback();
				
			} catch (SQLException sql2) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),sql2.getMessage());
				
				sql2.printStackTrace();
				
				return false;
			}
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			return false;

		} finally{
			
			//Finalizando recursos
			try {
				
				conexao.close();
				pstm.close();
				
				if(rs != null){
					
					rs.close();
				}
				
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
	public boolean atualizarCompra(CompraVO compra) {

		try {
			
			//Cria a conexao com o banco
			conexao = fabrica.getConexao();
			conexao.setAutoCommit(false); //Inicia uma transação
			
			//Cria o [alter] que sera executado no banco
			pstm = conexao.prepareStatement("update Compra set id_situacao=? where id_compra=?");
			
			pstm.setInt(1, compra.getSituacao().getIdSituacao());
			pstm.setLong(2, compra.getIdCompra());
			
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
	
}
