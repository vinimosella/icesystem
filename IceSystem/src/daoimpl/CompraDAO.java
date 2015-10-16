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
				
			conexao = fabrica.getConexao();
			
			pstm = conexao.prepareStatement("select c.id_compra, c.data_compra, c.id_funcionario, c.id_situacao, f.nome from Compra c"
					                       + " inner join Funcionario f on c.id_funcionario = f.id_funcionario");
			
			rs = pstm.executeQuery();
			
			listaCompras = new ArrayList<CompraVO>();
			
			while(rs.next()){
				
				c = new CompraVO();
					
				c.setIdCompra(rs.getLong("id_compra"));
				c.setDataCompra(rs.getDate("data_compra"));
				c.setFuncionario(new FuncionarioVO());
				c.getFuncionario().setIdFuncionario(rs.getInt("id_funcionario"));
				c.getFuncionario().setNome(rs.getString("nome"));
				c.setSituacao(new SituacaoVO());
				c.getSituacao().setIdSituacao(rs.getInt("id_situacao"));
				
				listaCompras.add(c);
				
			}
			
		} catch (SQLException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			listaCompras = null;
			
		} catch (ClassNotFoundException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			listaCompras = null;
			
		} finally {
			
			try {
				
				conexao.close();
				pstm.close();
				
				if(rs != null){
					
					rs.close();
				}
				
			} catch (SQLException e) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());

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
				
			conexao = fabrica.getConexao();
			
			pstm = conexao.prepareStatement("select mp.id_pessoa_juridica, mp.nome, mp.quantidade_disponivel, mp.sabor, i.quantidade, i.valor from Item_Compra i"
					                       + " inner join Materia_Prima mp on mp.id_materia_prima = i.id_materia_prima where i.id_compra = ?");
			
			pstm.setLong(1, compra.getIdCompra());
			
			rs = pstm.executeQuery();
			
			listaItens = new ArrayList<ItemCompraVO>();
			
			MateriaPrimaVO mp = null;
			
			while(rs.next()){
				
				mp = new MateriaPrimaVO();
				
				item = new ItemCompraVO();
					
				mp.setFornecedor(new FornecedorVO());
				mp.getFornecedor().setIdPessoaJuridica(rs.getInt("id_pessoa_juridica"));
				mp.setNome(rs.getString("nome"));
				mp.setQuantidadeDisponivel(rs.getDouble("quantidade_disponivel"));
				mp.setSabor(rs.getString("sabor"));
				
				item.setMateriaPrima(mp);
				item.setCompra(compra);
				item.setQuantidade(rs.getDouble("quantidade"));
				item.setValor(rs.getDouble("valor"));
				
				listaItens.add(item);
				
			}
			
		} catch (SQLException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			listaItens = null;
			
		} catch (ClassNotFoundException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			listaItens = null;
			
		} finally {
			
			try {
				
				conexao.close();
				pstm.close();
				
				if(rs != null){
					
					rs.close();
				}
				
			} catch (SQLException e) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());

				listaItens = null;
				
			}			
		
		}
		
		return listaItens;
	}
	
	@Override
	public boolean cadastrarCompra(CompraVO compra, List<ItemCompraVO> listaItensCompra){
				
		try {
			
			conexao = fabrica.getConexao();
			conexao.setAutoCommit(false); //Inicia uma transação
			
			pstm = conexao.prepareStatement("insert into Compra (id_funcionario, id_situacao) values (?, ?)");
						
			pstm.setInt(1, compra.getFuncionario().getIdFuncionario());
			pstm.setInt(2, compra.getSituacao().getIdSituacao());
			
			pstm.executeUpdate();
			
			//Recebe o id gerado automaticamente no insert anterior
			rs = pstm.getGeneratedKeys();
			int idCompra = rs.getInt("id_compra");
			
			pstm = conexao.prepareStatement("insert into Item_Compra (id_compra, id_materia_prima, quantidade, valor) values (?, ?, ?, ?)");
						
			for (ItemCompraVO itemCompra : listaItensCompra) {

				pstm.setInt(1, idCompra);
				pstm.setInt(2, itemCompra.getMateriaPrima().getIdMateriaPrima());
				pstm.setDouble(3, itemCompra.getQuantidade());
				pstm.setDouble(4, itemCompra.getValor());
				
				pstm.executeUpdate();
				
			}
			
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
	
	@Override
	public boolean atualizarCompra(CompraVO compra) {

		try {
			
			conexao = fabrica.getConexao();
			
			pstm = conexao.prepareStatement("alter table Compra set id_situacao=? where id_venda=?");
			
			pstm.setInt(1, compra.getSituacao().getIdSituacao());
			pstm.setLong(2, compra.getIdCompra());
	
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
