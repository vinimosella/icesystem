package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.LogFactory;
import util.Utilidades;
import vo.CidadeVO;
import vo.ClienteVO;
import vo.EmailVO;
import vo.EnderecoVO;
import vo.EstadoVO;
import vo.StatusVO;
import vo.TelefoneVO;
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
			
			//Cria a conex�o com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select c.id_cliente_pj, pj.cnpj, pj.id_endereco, en.bairro, en.cep, en.complemento, en.logradouro, en.numero, cd.id_cidade, cd.nome as nome_cidade, es.id_estado, es.nome, es.sigla, pj.razao_social, st.id_status, st.descricao from Cliente c"
					                       + " inner join Pessoa_Juridica pj on cli.id_cliente_pj = pj.id_pessoa_juridica"
										   + " inner join Status st on cli.id_status = st.id_status"
					                       + " inner join Endereco en on en.id_endereco = pj.id_endereco"
					                       + " inner join Cidade cd on cd.id_cidade = en.id_cidade"
					                       + " inner join Estado es on es.id_estado = cd.id_estado");
			
			pstm.setInt(1, Utilidades.STATUS_ATIVO.getIdStatus());
			
			//Executa uma pesquisa no banco
			rs = pstm.executeQuery();
			
			listaClientes = new ArrayList<ClienteVO>();
			
			//Carregando a listaClientes
			for (ClienteVO cli : listaClientes) {
				
				Integer idCliente = rs.getInt("id_cliente_pj");
				
				cli = new ClienteVO();
				
				cli.setIdPessoaJuridica(idCliente);
				cli.setRazaoSocial(rs.getString("razao_social"));
				cli.setStatus(new StatusVO());
				cli.getStatus().setIdStatus(rs.getInt("id_status"));
				cli.setCnpj(rs.getString("cnpj"));
				cli.setEndereco(new EnderecoVO());
				cli.getEndereco().setIdEndereco(rs.getInt("id_endereco"));
				cli.getEndereco().setBairro(rs.getString("bairro"));
				cli.getEndereco().setCep(rs.getString("cep"));
				cli.getEndereco().setComplemento(rs.getString("complemento"));
				cli.getEndereco().setLogradouro(rs.getString("logradouro"));
				cli.getEndereco().setNumero(rs.getInt("numero"));
				cli.getEndereco().setCidade(new CidadeVO());
				cli.getEndereco().getCidade().setIdCidade(rs.getInt("id_cidade"));
				cli.getEndereco().getCidade().setNome(rs.getString("nome_cidade"));
				cli.getEndereco().getCidade().setEstado(new EstadoVO());
				cli.getEndereco().getCidade().getEstado().setIdEstado(rs.getInt("id_estado"));
				cli.getEndereco().getCidade().getEstado().setNome(rs.getString("es.nome"));
				cli.getEndereco().getCidade().getEstado().setSigla(rs.getString("sigla"));
				cli.setListaEmails(consultarEmailCliente(idCliente, conexao));
				cli.setListaTelefones(consultarTelefoneCliente(idCliente, conexao));
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
	
	public List<EmailVO> consultarEmailCliente(Integer idCliente, Connection conexao) throws SQLException {
		
		EmailVO email = null;
		
		List<EmailVO> listaEmails = null;
		
		ResultSet rs = null;
		
		PreparedStatement pstm = null;
		
		try {
			
			Connection conexaoLocal = conexao;
			
			//Cria o [select] que sera executado no banco
			pstm = conexaoLocal.prepareStatement("select id_email, email, id_pessoa_juridica from Email where id_pessoa_juridica = ?");
			
			pstm.setInt(1, idCliente);
			
			//Executa uma pesquisa no banco
			rs = pstm.executeQuery();
			
			listaEmails = new ArrayList<EmailVO>();
			
			//Carrega a listaEmails que sera utilizada no consultarFornecedores
			while(rs.next()){
				
				email = new EmailVO();
					
				email.setIdEmail(rs.getInt("id_email"));
				email.setEmail(rs.getString("email"));
				email.setPessoaJuridica(new ClienteVO());
				email.getPessoaJuridica().setIdPessoaJuridica(idCliente);
				
				listaEmails.add(email);
				
			}
			
		} finally {
			
			//Finalizando os recursos 
			//Obs: A conex�o n�o � finalizada pois faz parte da mesma transa��o do Fornecedor, ent�o � Finalizada apenas uma vez
			pstm.close();
			
			if(rs != null){
				
				rs.close();
			}
			
		}
		
		return listaEmails;
	}
	
	public List<TelefoneVO> consultarTelefoneCliente(Integer idCliente, Connection conexao) throws SQLException{
		
		TelefoneVO telefone = null;
		
		List<TelefoneVO> listaTelefones = null;
		
		ResultSet rs = null;
		
		PreparedStatement pstm = null;
		
		try {
			
			Connection conexaoLocal = conexao;
			
			//Cria o [select] que sera executado no banco
			pstm = conexaoLocal.prepareStatement("select id_telefone, ddd, numero, id_pessoa_juridica from Telefone where id_pessoa_juridica = ?");
			
			//Executa uma pesquisa no banco
			pstm.setInt(1, idCliente);
			
			rs = pstm.executeQuery();
			
			listaTelefones = new ArrayList<TelefoneVO>();
			
			//Carrega a listaTelefones que sera utilizada no consultarFornecedores
			while(rs.next()){
				
				telefone = new TelefoneVO();
					
				telefone.setIdTelefone(rs.getInt("id_telefone"));
				telefone.setDdd(rs.getString("ddd"));
				telefone.setNumero(rs.getString("numero"));
				telefone.setPessoaJuridica(new ClienteVO());
				telefone.getPessoaJuridica().setIdPessoaJuridica(idCliente);
				
				listaTelefones.add(telefone);
				
			}
			
		} finally {
			
			//Finalizando os recursos
			//Obs: A conex�o n�o � finalizada pois faz parte da mesma transa��o do Fornecedor, ent�o � Finalizada apenas uma vez
			pstm.close();
			
			if(rs != null){
				
				rs.close();
			}
			
		}
		
		return listaTelefones;
	}

}
