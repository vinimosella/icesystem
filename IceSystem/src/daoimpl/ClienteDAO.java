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

public class ClienteDAO{

	private Connection conexao;
	private PreparedStatement pstm;
	private ConnectionFactory fabrica;
	private ResultSet rs;
		
	{
		fabrica = ConnectionFactory.getInstance();	
	}
	
	//[] = {listaInclusao,listaAlteracao,listaExclusao}
	public boolean alterarCliente(ClienteVO cliente, List<List<EmailVO>> listaListaEmail, List<List<TelefoneVO>> listaListaTelefone){
		
		try {
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			conexao.setAutoCommit(false); //Inicia uma transação
			
			//Cria o [update] que sera executado no banco
			pstm = conexao.prepareStatement("update Pessoa_Juridica set cnpj=?, razao_social=? where id_pessoa_juridica=?");
			
			pstm.setString(1, cliente.getCnpj());
			pstm.setString(2, cliente.getRazaoSocial());
			pstm.setInt(3, cliente.getIdPessoaJuridica());

			//Executa uma atualização no banco
			pstm.executeUpdate();
			
			alterarEnderecoCliente(cliente.getEndereco());
			
			if(listaListaEmail.get(0).size() > 0){
				
				incluirEmailCliente(cliente, listaListaEmail.get(0), conexao);
			}
			
			if(listaListaEmail.get(1).size() > 0){
				
				alterarEmailCliente(listaListaEmail.get(1), conexao);
			}
			
			if(listaListaEmail.get(2).size() > 0){
				
				excluirEmailCliente(listaListaEmail.get(2), conexao);
			}
			
			if(listaListaTelefone.get(0).size() > 0){
				
				incluirTelefoneCliente(cliente, listaListaTelefone.get(0), conexao);
			}
			
			if(listaListaTelefone.get(1).size() > 0){
				
				alterarTelefoneCliente(listaListaTelefone.get(1), conexao);
			}
			
			if(listaListaTelefone.get(2).size() > 0){
				
				excluirTelefoneCliente(listaListaTelefone.get(2), conexao);
			}
			
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
	
	public boolean cadastrarCliente(ClienteVO cliente) {

		try {

			// Cria a conexão com o banco
			conexao = fabrica.getConexao();
			conexao.setAutoCommit(false);
			
			//INCLUIR ENDERECO
			pstm = conexao.prepareStatement("insert into Endereco (id_cidade, logradouro, bairro, cep, numero, complemento) values (?, ?, ?, ?, ?, ?)",PreparedStatement.RETURN_GENERATED_KEYS);

			pstm.setInt(1, cliente.getEndereco().getCidade().getIdCidade());
			pstm.setString(2, cliente.getEndereco().getLogradouro());
			pstm.setString(3, cliente.getEndereco().getBairro());
			pstm.setString(4, cliente.getEndereco().getCep());
			pstm.setInt(5, cliente.getEndereco().getNumero());
			pstm.setString(6, cliente.getEndereco().getComplemento());
			
			pstm.executeUpdate();

			// Recebe o id gerado automaticamente no insert anterior
			rs = pstm.getGeneratedKeys();

			if (rs != null && rs.next()) {

				Integer idEndereco = rs.getInt(1);
			
				//INCLUIR PESSOA JURIDICA
				pstm = conexao.prepareStatement("insert into Pessoa_Juridica (cnpj, razao_social, id_endereco) values (?, ?, ?)",PreparedStatement.RETURN_GENERATED_KEYS);
				
				pstm.setString(1, cliente.getCnpj());
				pstm.setString(2, cliente.getRazaoSocial());
				pstm.setInt(3, idEndereco);
				
				pstm.executeUpdate();
				
				// Recebe o id gerado automaticamente no insert anterior
				rs = pstm.getGeneratedKeys();

				if (rs != null && rs.next()) {
					
					Integer idPJ = rs.getInt(1);
					
					//INCLUIR FORNECEDOR
					pstm = conexao.prepareStatement("insert into Cliente (id_cliente_pj, id_status) values (?, ?)");
					
					pstm.setInt(1, idPJ);
					pstm.setInt(2, cliente.getStatus().getIdStatus());
					
					pstm.executeUpdate();
					
					//INCLUIR EMAIL
					for (EmailVO email : cliente.getListaEmails()) {
						
						pstm = conexao.prepareStatement("insert into Email (id_pessoa_juridica, email, id_status) values (?, ?, ?)");
						
						pstm.setInt(1, idPJ);
						pstm.setString(2, email.getEmail());
						pstm.setInt(3, cliente.getStatus().getIdStatus());
						
						pstm.executeUpdate();
					}
					
					//INCLUIR TELEFONE
					for (TelefoneVO telefone : cliente.getListaTelefones()) {
						
						pstm = conexao.prepareStatement("insert into Telefone (id_pessoa_juridica, ddd, numero, id_status) values (?, ?, ?, ?)");
						
						pstm.setInt(1, idPJ);
						pstm.setString(2, telefone.getDdd());
						pstm.setString(3, telefone.getNumero());
						pstm.setInt(4, cliente.getStatus().getIdStatus());
						
						pstm.executeUpdate();
					}
					
					//TERMINOU \O/ 
					conexao.commit();
					
				}else{ //SE NÃO INCLUIU PJ
					
					conexao.rollback();
				}

			}
			else{ //SE NÃO INCLUIU ENDERECO
				
				conexao.rollback();
			}

		} catch (ClassNotFoundException cnf) {

			cnf.printStackTrace();

			// Caso ocorra algum erro, executa o rollback do cadastro no banco
			try {

				conexao.rollback();

			} catch (SQLException sql) {

				LogFactory.getInstance().gerarLog(getClass().getName(),
						sql.getMessage());

				sql.printStackTrace();

				return false;

			}

		} catch (SQLException sql) {

			sql.printStackTrace();

			// Caso ocorra algum erro, executa o rollback do cadastro no banco
			try {

				conexao.rollback();

			} catch (SQLException sql2) {

				LogFactory.getInstance().gerarLog(getClass().getName(),
						sql2.getMessage());

				sql2.printStackTrace();

				return false;

			}

		} finally {

			// Finalizando os recursos
			try {

				conexao.close();
				pstm.close();

			} catch (SQLException sql) {

				// Caso ocorra algum erro, executa o rollback do cadastro no
				// banco
				try {

					conexao.rollback();

				} catch (SQLException sql2) {

					LogFactory.getInstance().gerarLog(getClass().getName(),
							sql2.getMessage());

					sql2.printStackTrace();

					return false;

				}

				LogFactory.getInstance().gerarLog(getClass().getName(),
						sql.getMessage());

				sql.printStackTrace();

				return false;
			}
		}

		return true;
	}
	
	public List<ClienteVO> consultarTodosClientes() {
		
		ClienteVO cli = null;
		
		List<ClienteVO> listaClientes = null;
		
		try {
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select c.id_cliente_pj, pj.cnpj, pj.id_endereco, pj.razao_social, st.id_status, st.descricao from Cliente c"
					                       + " inner join Pessoa_Juridica pj on c.id_cliente_pj = pj.id_pessoa_juridica"
										   + " inner join Status st on c.id_status = st.id_status"
					                       + " where c.id_status = ?");
			
			pstm.setInt(1, Utilidades.STATUS_ATIVO.getIdStatus());
			
			//Executa uma pesquisa no banco
			rs = pstm.executeQuery();
			
			listaClientes = new ArrayList<ClienteVO>();
			
			//Carregando a listaClientes
			while(rs.next()) {
				
				Integer idCliente = rs.getInt("id_cliente_pj");
				
				cli = new ClienteVO();
				
				cli.setIdPessoaJuridica(idCliente);
				cli.setRazaoSocial(rs.getString("razao_social"));
				cli.setStatus(new StatusVO());
				cli.getStatus().setIdStatus(rs.getInt("id_status"));
				cli.setCnpj(rs.getString("cnpj"));
				cli.setEndereco(new EnderecoVO());
				cli.getEndereco().setIdEndereco(rs.getInt("id_endereco"));
				cli.setStatus(new StatusVO());
				cli.getStatus().setIdStatus(rs.getInt("id_status"));
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
	
	private List<EmailVO> consultarEmailCliente(Integer idCliente, Connection conexao) throws SQLException {
		
		EmailVO email = null;
		
		List<EmailVO> listaEmails = null;
		
		ResultSet rs = null;
		
		PreparedStatement pstm = null;
		
		try {
			
			Connection conexaoLocal = conexao;
			
			//Cria o [select] que sera executado no banco
			pstm = conexaoLocal.prepareStatement("select id_email, email, id_pessoa_juridica from Email where id_pessoa_juridica = ? and id_status=?");
			
			pstm.setInt(1, idCliente);
			pstm.setInt(2, Utilidades.STATUS_ATIVO.getIdStatus());
			
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
			//Obs: A conexão não é finalizada pois faz parte da mesma transação do Fornecedor, então é Finalizada apenas uma vez
			pstm.close();
			
			if(rs != null){
				
				rs.close();
			}
			
		}
		
		return listaEmails;
	}
	
	private void incluirEmailCliente(ClienteVO cliente, List<EmailVO> listaEmails, Connection conexao) throws SQLException {
						
		rs = null;
		
		pstm = null;
		Connection conexaoLocal = conexao;
			
			for(EmailVO email : listaEmails){
				
				//Cria o [insert] que sera executado no banco
				pstm = conexaoLocal.prepareStatement("insert into Email (email, id_pessoa_juridica, id_status) values (?,?,?)");
				
				pstm.setString(1, email.getEmail());
				pstm.setInt(2, cliente.getIdPessoaJuridica());
				pstm.setInt(3, cliente.getStatus().getIdStatus());
				
				//Executa o insert
				pstm.executeUpdate();
			}
			
	}
	
	private void alterarEmailCliente(List<EmailVO> listaEmails, Connection conexao) throws SQLException {
		
		rs = null;
		
		pstm = null;
		Connection conexaoLocal = conexao;
			
			for(EmailVO email : listaEmails){
				
				//Cria o [update] que sera executado no banco
				pstm = conexaoLocal.prepareStatement("update Email set email = ? where id_email = ?");
				
				pstm.setString(1, email.getEmail());
				pstm.setInt(2, email.getIdEmail());
				
				//Executa o update
				pstm.executeUpdate();
			}
			
	}
	
	private void excluirEmailCliente(List<EmailVO> listaEmails, Connection conexao) throws SQLException {
		
		rs = null;
		
		pstm = null;
		Connection conexaoLocal = conexao;
			
			for(EmailVO email : listaEmails){
				
				//Cria o [update] que sera executado no banco
				pstm = conexaoLocal.prepareStatement("update Email set id_status = ? where id_email = ?");
				
				pstm.setInt(1, Utilidades.STATUS_INATIVO.getIdStatus());
				pstm.setInt(2, email.getIdEmail());
				
				//Executa o update
				pstm.executeUpdate();
			}
			
	}
	
	private List<TelefoneVO> consultarTelefoneCliente(Integer idCliente, Connection conexao) throws SQLException{
		
		TelefoneVO telefone = null;
		
		List<TelefoneVO> listaTelefones = null;
		
		ResultSet rs = null;
		
		PreparedStatement pstm = null;
		
		try {
			
			Connection conexaoLocal = conexao;
			
			//Cria o [select] que sera executado no banco
			pstm = conexaoLocal.prepareStatement("select id_telefone, ddd, numero, id_pessoa_juridica from Telefone where id_pessoa_juridica = ? and id_status=?");
			
			//Executa uma pesquisa no banco
			pstm.setInt(1, idCliente);
			pstm.setInt(2, Utilidades.STATUS_ATIVO.getIdStatus());
			
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
			//Obs: A conexão não é finalizada pois faz parte da mesma transação do Fornecedor, então é Finalizada apenas uma vez
			pstm.close();
			
			if(rs != null){
				
				rs.close();
			}
			
		}
		
		return listaTelefones;
	}
	
	private void incluirTelefoneCliente(ClienteVO cliente, List<TelefoneVO> listaTelefones, Connection conexao) throws SQLException {
		
		rs = null;
		
		pstm = null;
		Connection conexaoLocal = conexao;
			
			for(TelefoneVO telefone : listaTelefones){
				
				//Cria o [insert] que sera executado no banco
				pstm = conexaoLocal.prepareStatement("insert into Telefone (ddd, numero, id_pessoa_juridica, id_status) values (?,?,?,?)");
				
				pstm.setString(1, telefone.getDdd());
				pstm.setString(2, telefone.getNumero());
				pstm.setInt(3, cliente.getIdPessoaJuridica());
				pstm.setInt(4, cliente.getStatus().getIdStatus());
				
				//Executa o insert
				pstm.executeUpdate();
			}
			
	}
	
	private void alterarTelefoneCliente(List<TelefoneVO> listaTelefones, Connection conexao) throws SQLException {
		
		rs = null;
		
		pstm = null;
		Connection conexaoLocal = conexao;
			
			for(TelefoneVO telefone : listaTelefones){
				
				//Cria o [update] que sera executado no banco
				pstm = conexaoLocal.prepareStatement("update Telefone set ddd = ?, numero = ? where id_telefone = ?");
				
				pstm.setString(1, telefone.getDdd());
				pstm.setString(2, telefone.getNumero());
				pstm.setInt(3, telefone.getIdTelefone());
				
				//Executa o insert
				pstm.executeUpdate();
			}
			
	}

	private void excluirTelefoneCliente(List<TelefoneVO> listaTelefones, Connection conexao) throws SQLException {
		
		rs = null;
		
		pstm = null;
		Connection conexaoLocal = conexao;
			
			for(TelefoneVO telefone : listaTelefones){
				
				//Cria o [update] que sera executado no banco
				pstm = conexaoLocal.prepareStatement("update Telefone set id_status = ? where id_telefone = ?");
				
				pstm.setInt(1, Utilidades.STATUS_INATIVO.getIdStatus());
				pstm.setInt(2, telefone.getIdTelefone());
				
				//Executa o insert
				pstm.executeUpdate();
			}
			
	}
	
	private boolean alterarEnderecoCliente(EnderecoVO endereco) throws SQLException{
		
		rs = null;
		
		pstm = null;
		Connection conexaoLocal = conexao;
			
		//Cria o [update] que sera executado no banco
		pstm = conexaoLocal.prepareStatement("update Endereco set id_cidade=?, logradouro=?, bairro=?, cep=?, numero=?, complemento=? where id_endereco = ?");
		
		pstm.setInt(1, endereco.getCidade().getIdCidade());
		pstm.setString(2, endereco.getLogradouro());
		pstm.setString(3, endereco.getBairro());
		pstm.setString(4, endereco.getCep());
		pstm.setInt(5, endereco.getNumero());
		pstm.setString(6, endereco.getComplemento());
		pstm.setInt(7, endereco.getIdEndereco());
		
		//Executa o update
		pstm.executeUpdate();
		
		return true;	
	}
	
	public boolean excluirCliente(ClienteVO cliente) {
		
		try {
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			conexao.setAutoCommit(false); //Inicia uma transação
			
			//Cria o [delete] que sera executado no banco
			pstm = conexao.prepareStatement("update Cliente set id_status = ? where id_cliente_pj = ?");
			
			pstm.setInt(1, cliente.getStatus().getIdStatus());
			
			pstm.setInt(2, cliente.getIdPessoaJuridica());
			
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
	
	public ClienteVO detalharCliente(ClienteVO cliente){
		
		try {
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select en.bairro, en.cep, en.complemento, en.logradouro, en.numero, cd.id_cidade, cd.nome as nome_cidade, es.id_estado, es.nome, es.sigla from Endereco en"
					                       + " inner join Cidade cd on cd.id_cidade = en.id_cidade"
					                       + " inner join Estado es on es.id_estado = cd.id_estado"
					                       + " where en.id_endereco = ?");
			
			pstm.setLong(1, cliente.getEndereco().getIdEndereco());
			
			//Executa o select no banco
			rs = pstm.executeQuery();			
			
			//Carregando a listaItens
			if(rs.next()){
				
				cliente.getEndereco().setBairro(rs.getString("bairro"));
				cliente.getEndereco().setCep(rs.getString("cep"));
				cliente.getEndereco().setComplemento(rs.getString("complemento"));
				cliente.getEndereco().setLogradouro(rs.getString("logradouro"));
				cliente.getEndereco().setNumero(rs.getInt("numero"));
				cliente.getEndereco().setCidade(new CidadeVO());
				cliente.getEndereco().getCidade().setIdCidade(rs.getInt("id_cidade"));
				cliente.getEndereco().getCidade().setNome(rs.getString("nome_cidade"));
				cliente.getEndereco().getCidade().setEstado(new EstadoVO());
				cliente.getEndereco().getCidade().getEstado().setIdEstado(rs.getInt("id_estado"));
				cliente.getEndereco().getCidade().getEstado().setNome(rs.getString("nome"));
				cliente.getEndereco().getCidade().getEstado().setSigla(rs.getString("sigla"));
				cliente.setListaEmails(consultarEmailCliente(cliente.getIdPessoaJuridica(), conexao));
				cliente.setListaTelefones(consultarTelefoneCliente(cliente.getIdPessoaJuridica(), conexao));
			}
			
		} catch (SQLException sql) {
			
			sql.printStackTrace();
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
		} catch (ClassNotFoundException cnf) {
			
			cnf.printStackTrace();
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
		} finally {
			
			//Finalizando os recursos
			try {
				
				conexao.close();
				pstm.close();
				
				if(rs != null){
					
					rs.close();
				}
				
			} catch (SQLException sql) {
				
				sql.printStackTrace();
				LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
				
			}			
		
		}
		
		return cliente;
	}
	
}
