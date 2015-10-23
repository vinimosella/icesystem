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
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select v.id_venda, v.data_venda, s.descricao, pj.razao_social from Venda v"
					                       + " inner join Situacao s on v.id_situacao = s.id_situacao"
					                       + " inner join Cliente c on v.id_cliente = c.id_cliente"
					                       + " inner join Pessoa_Juridica pj on pj.id_pessoa_juridica = c.id_cliente_pj");
			
			//Executa uma pesquisa no banco
			rs = pstm.executeQuery();
			
			listaVendas = new ArrayList<VendaVO>();
			
			//Carrega a listaVendas
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
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			listaVendas = null;
			
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			listaVendas = null;
			
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
				
				listaVendas = null;
				
			}			
		
		}
		
		return listaVendas;
	}
	
	@Override
	public boolean cadastrarVenda(VendaVO venda, List<ItemVendaVO> listaItensVenda){
		
		try {
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			conexao.setAutoCommit(false); //Inicia uma transação
			
			//Cria o [insert] que sera executado no  banco
			pstm = conexao.prepareStatement("insert into Venda (id_cliente, id_funcionario, id_situacao) "
					                       + "values (?, ?, ?)");
			
			pstm.setInt(1, venda.getCliente().getIdPessoaJuridica());
			pstm.setInt(2, venda.getFuncionario().getIdFuncionario());
			pstm.setInt(3, venda.getSituacao().getIdSituacao());
			
			//Executa uma atualização no banco
			pstm.executeUpdate();
			
			//Recebe o id gerado automaticamente no insert anterior
			rs = pstm.getGeneratedKeys();
			int idVenda = rs.getInt("id_venda");
			
			//Cria o [insert] que sera executado no banco
			pstm = conexao.prepareStatement("insert into Item_Venda (id_venda, id_produto, quantidade, valor) values (?, ?, ?, ?)");
			
			//Foreach para inserir a listaItensVenda na Venda
			for (ItemVendaVO itemVenda : listaItensVenda) {

				pstm.setInt(1, idVenda);
				pstm.setInt(2, itemVenda.getProduto().getIdProduto());
				pstm.setDouble(3, itemVenda.getQuantidade());
				pstm.setDouble(4, itemVenda.getValor());			
				
				//Executa uma atualização no banco
				pstm.executeUpdate();
				
			}
			
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
				
				LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
				
				sql.printStackTrace();
				
				return false;
			}
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			//Caso ocorra algum erro, executa o rollback do cadastro no banco
			try {
				
				conexao.rollback();
				
				return false;
				
			} catch (SQLException sql2) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),sql2.getMessage());
				
				sql2.printStackTrace();
				
				return false;
				
			} finally{
				
				//Finalizando os recursos
				try {
					
					conexao.close();
					
					pstm.close();
					
					if(rs!= null){
						
						rs.close();
					}
					
				} catch (SQLException sql2) {
					
					LogFactory.getInstance().gerarLog(getClass().getName(), sql2.getMessage());
					
					sql2.printStackTrace();
					
					return false;
					
				}
			}

		}
		
		return true;
	}

	@Override
	public boolean atualizarVenda(VendaVO venda) {

		try {
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			conexao.setAutoCommit(false); //Inicia uma tranção
			
			//Cria o [alter] que sera executado no banco
			pstm = conexao.prepareStatement("alter table Venda set id_situacao=? where id_venda=?");
			
			pstm.setInt(1, venda.getSituacao().getIdSituacao());
			pstm.setLong(2, venda.getIdVenda());
			
			//Executa uma atualização no banco
			pstm.executeUpdate();
			
			//Em caso de sucesso, executa o commit do cadastro no banco
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
			}

		}
		
		return true;
	}
	
}
