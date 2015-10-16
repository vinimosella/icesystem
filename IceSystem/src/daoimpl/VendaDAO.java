package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.LogFactory;
import vo.ClienteVO;
import vo.ItemVendaVO;
import vo.SituacaoVO;
import vo.VendaVO;
import daoservice.IVendaDAO;

public class VendaDAO implements IVendaDAO{

	private Connection conexao;
	private PreparedStatement pstm;
	private ConnectionFactory fabrica;
	private ResultSet rs;
		
	{
		fabrica = ConnectionFactory.getInstance();	
	}
	
	@Override
	public List<VendaVO> consultarVendas(){
		
		VendaVO v = null;
		
		List<VendaVO> listaVendas = null;
		
		try {
				
			conexao = fabrica.getConexao();
			
			pstm = conexao.prepareStatement("select v.id_venda, v.data_venda, s.descricao, pj.razao_social from Venda v"
					                       + " inner join Situacao s on v.id_situacao = s.id_situacao"
					                       + " inner join Cliente c on v.id_cliente = c.id_cliente"
					                       + " inner join Pessoa_Juridica pj on pj.id_pessoa_juridica = c.id_cliente_pj");
			
			rs = pstm.executeQuery();
			
			listaVendas = new ArrayList<VendaVO>();
			
			while(rs.next()){
				
				v = new VendaVO();
					
				v.setIdVenda(rs.getLong("id_venda"));
				v.setDataVenda(rs.getDate("data_venda"));
				v.setSituacao(new SituacaoVO());
				v.getSituacao().setDescricao(rs.getString("descricao"));
				v.setCliente(new ClienteVO());
				v.getCliente().setRazaoSocial(rs.getString("razao_social"));				
				
				listaVendas.add(v);
				
			}
			
		} catch (SQLException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			listaVendas = null;
			
		} catch (ClassNotFoundException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			listaVendas = null;
			
		} finally {
			
			try {
				
				conexao.close();
				pstm.close();
				
				if(rs != null){
					
					rs.close();
				}
				
			} catch (SQLException e) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());

				listaVendas = null;
				
			}			
		
		}
		
		return listaVendas;
	}
	
	@Override
	public boolean cadastrarVenda(VendaVO venda, List<ItemVendaVO> listaItensVenda){
		
try {
			
			conexao = fabrica.getConexao();
			conexao.setAutoCommit(false); //Inicia uma transação
			
			pstm = conexao.prepareStatement("insert into Venda (id_cliente, id_funcionario, id_situacao) "
					                       + "values (?, ?, ?)");
			
			pstm.setInt(1, venda.getCliente().getIdPessoaJuridica());
			pstm.setInt(2, venda.getFuncionario().getIdFuncionario());
			pstm.setInt(3, venda.getSituacao().getIdSituacao());
			
			pstm.executeUpdate();
			
			//Recebe o id gerado automaticamente no insert anterior
			rs = pstm.getGeneratedKeys();
			int idVenda = rs.getInt("id_venda");
			
			pstm = conexao.prepareStatement("insert into Item_Venda (id_venda, id_produto, quantidade, valor) values (?, ?, ?, ?)");
						
			for (ItemVendaVO itemVenda : listaItensVenda) {

				pstm.setInt(1, idVenda);
				pstm.setInt(2, itemVenda.getProduto().getIdProduto());
				pstm.setDouble(3, itemVenda.getQuantidade());
				pstm.setDouble(4, itemVenda.getValor());			
				
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
	public boolean atualizarVenda(VendaVO venda) {

		try {
			
			conexao = fabrica.getConexao();
			
			pstm = conexao.prepareStatement("alter table Venda set id_situacao=? where id_venda=?");
			
			pstm.setInt(1, venda.getSituacao().getIdSituacao());
			pstm.setLong(2, venda.getIdVenda());
	
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
