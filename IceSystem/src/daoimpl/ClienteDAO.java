package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.LogFactory;
import vo.ClienteVO;
import daoservice.IClienteDAO;

public class ClienteDAO implements IClienteDAO{

	private Connection conexao;
	private PreparedStatement pstm;
	private ConnectionFactory fabrica;
	private ResultSet rs;
		
	{
		fabrica = ConnectionFactory.getInstance();	
	}
	
	@Override
	public List<ClienteVO> consultarTodosClientes() {
		
		List<ClienteVO> listaClientes = null;
		
		try {
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select cli.id.cliente_pj, pj.razao_social"
					                       + " inner join Pessoa_Juridica pj on cli.id_cliente_pj = pj.id_pessoa_juridica"
										   + " inner join Status st on cli.id_status = st.id_status where cli.descricao = ?");
			
			//Executa uma pesquisa no banco
			rs = pstm.executeQuery();
			
			listaClientes = new ArrayList<ClienteVO>();
			
			//Carregando a listaClientes
			for (ClienteVO cli : listaClientes) {
				
				cli.setIdPessoaJuridica(rs.getInt("id_cliente_pj"));
				cli.setRazaoSocial(rs.getString("razao_social"));
				//cli.setStatus(new StatusVO());
				//cli.getStatus.setDescricao(rs.getString(Utilidades.ATIVO));
				listaClientes.add(cli);
				
			}				
			
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			listaClientes = null;
			
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
			listaClientes = null;
			
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
				
				listaClientes = null;
				
			}			
		
		}
		
		return listaClientes;
	}

}
