package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.LogFactory;
import util.Utilidades;
import vo.CidadeVO;
import vo.EmailVO;
import vo.EnderecoVO;
import vo.EstadoVO;
import vo.FornecedorVO;
import vo.StatusVO;
import vo.TelefoneVO;

public class FornecedorDAO{
	
	private Connection conexao;
	private PreparedStatement pstm;
	private ConnectionFactory fabrica;
	private ResultSet rs;
		
	{
		fabrica = ConnectionFactory.getInstance();		
	}
	
	//[] = {listaInclusao,listaAlteracao,listaExclusao}
	public boolean alterarFornecedor(FornecedorVO fornecedor, List<List<EmailVO>> listaListaEmail, List<List<TelefoneVO>> listaListaTelefone){
		
		try {
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			conexao.setAutoCommit(false); //Inicia uma transação
			
			//Cria o [update] que sera executado no banco
			pstm = conexao.prepareStatement("update Pessoa_Juridica set cnpj=?, razao_social=? where id_pessoa_juridica=?");
			
			pstm.setString(1, fornecedor.getCnpj());
			pstm.setString(2, fornecedor.getRazaoSocial());
			pstm.setInt(3, fornecedor.getIdPessoaJuridica());

			//Executa uma atualização no banco
			pstm.executeUpdate();
			
			alterarEnderecoFornecedor(fornecedor.getEndereco());
			
			if(listaListaEmail.get(0).size() > 0){
				
				incluirEmailFornecedor(fornecedor, listaListaEmail.get(0), conexao);
			}
			
			if(listaListaEmail.get(1).size() > 0){
				
				alterarEmailFornecedor(listaListaEmail.get(1), conexao);
			}
			
			if(listaListaEmail.get(2).size() > 0){
				
				excluirEmailFornecedor(listaListaEmail.get(2), conexao);
			}
			
			if(listaListaTelefone.get(0).size() > 0){
				
				incluirTelefoneFornecedor(fornecedor, listaListaTelefone.get(0), conexao);
			}
			
			if(listaListaTelefone.get(1).size() > 0){
				
				alterarTelefoneFornecedor(listaListaTelefone.get(1), conexao);
			}
			
			if(listaListaTelefone.get(2).size() > 0){
				
				excluirTelefoneFornecedor(listaListaTelefone.get(2), conexao);
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
	
	public boolean cadastrarFornecedor(FornecedorVO fornecedor) {

		try {

			// Cria a conexão com o banco
			conexao = fabrica.getConexao();
			conexao.setAutoCommit(false);
			
			//INCLUIR ENDERECO
			pstm = conexao.prepareStatement("insert into Endereco (id_cidade, logradouro, bairro, cep, numero, complemento) values (?, ?, ?, ?, ?, ?)",PreparedStatement.RETURN_GENERATED_KEYS);

			pstm.setInt(1, fornecedor.getEndereco().getCidade().getIdCidade());
			pstm.setString(2, fornecedor.getEndereco().getLogradouro());
			pstm.setString(3, fornecedor.getEndereco().getBairro());
			pstm.setString(4, fornecedor.getEndereco().getCep());
			pstm.setInt(5, fornecedor.getEndereco().getNumero());
			pstm.setString(6, fornecedor.getEndereco().getComplemento());
			
			pstm.executeUpdate();

			// Recebe o id gerado automaticamente no insert anterior
			rs = pstm.getGeneratedKeys();

			if (rs != null && rs.next()) {

				Integer idEndereco = rs.getInt(1);
			
				//INCLUIR PESSOA JURIDICA
				pstm = conexao.prepareStatement("insert into Pessoa_Juridica (cnpj, razao_social, id_endereco) values (?, ?, ?)",PreparedStatement.RETURN_GENERATED_KEYS);
				
				pstm.setString(1, fornecedor.getCnpj());
				pstm.setString(2, fornecedor.getRazaoSocial());
				pstm.setInt(3, idEndereco);
				
				pstm.executeUpdate();
				
				// Recebe o id gerado automaticamente no insert anterior
				rs = pstm.getGeneratedKeys();

				if (rs != null && rs.next()) {
					
					Integer idPJ = rs.getInt(1);
					
					//INCLUIR FORNECEDOR
					pstm = conexao.prepareStatement("insert into Fornecedor (id_fornecedor_pj, id_status) values (?, ?)");
					
					pstm.setInt(1, idPJ);
					pstm.setInt(2, fornecedor.getStatus().getIdStatus());
					
					pstm.executeUpdate();
					
					//INCLUIR EMAIL
					for (EmailVO email : fornecedor.getListaEmails()) {
						
						pstm = conexao.prepareStatement("insert into Email (id_pessoa_juridica, email, id_status) values (?, ?, ?)");
						
						pstm.setInt(1, idPJ);
						pstm.setString(2, email.getEmail());
						pstm.setInt(3, fornecedor.getStatus().getIdStatus());
						
						pstm.executeUpdate();
					}
					
					//INCLUIR TELEFONE
					for (TelefoneVO telefone : fornecedor.getListaTelefones()) {
						
						pstm = conexao.prepareStatement("insert into Telefone (id_pessoa_juridica, ddd, numero, id_status) values (?, ?, ?, ?)");
						
						pstm.setInt(1, idPJ);
						pstm.setString(2, telefone.getDdd());
						pstm.setString(3, telefone.getNumero());
						pstm.setInt(4, fornecedor.getStatus().getIdStatus());
						
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
	
	public List<FornecedorVO> consultarFornecedores() {
		
		FornecedorVO fornecedor = null;
		
		List<FornecedorVO> listaFornecedores = null;
		
		try {
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select f.id_fornecedor_pj, pj.cnpj, pj.id_endereco, pj.razao_social, st.id_status, st.descricao from Fornecedor f"
					                       + " inner join Pessoa_Juridica pj on f.id_fornecedor_pj = pj.id_pessoa_juridica"
					                       + " inner join Status st on f.id_status = st.id_status"
					                       + " where f.id_status = ?");
			

			pstm.setInt(1, Utilidades.STATUS_ATIVO.getIdStatus());
			
			//Executa uma pesquisa no banco
			rs = pstm.executeQuery();
			
			listaFornecedores = new ArrayList<FornecedorVO>();
			
			//Caregga a listaFornecedores
			while(rs.next()){
			
				Integer idFornecedor = rs.getInt("id_fornecedor_pj");
				
				fornecedor = new FornecedorVO();
					
				fornecedor.setIdPessoaJuridica(idFornecedor);
				fornecedor.setCnpj(rs.getString("cnpj"));
				fornecedor.setEndereco(new EnderecoVO());
				fornecedor.getEndereco().setIdEndereco(rs.getInt("id_endereco"));
				fornecedor.setRazaoSocial(rs.getString("razao_social"));
				fornecedor.setStatus(new StatusVO());
				fornecedor.getStatus().setIdStatus(rs.getInt("id_status"));
				fornecedor.setListaEmails(consultarEmailFornecedor(idFornecedor, conexao));
				fornecedor.setListaTelefones(consultarTelefoneFornecedor(idFornecedor, conexao));
				
				listaFornecedores.add(fornecedor);
				
			}
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			listaFornecedores = null;
			
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
			listaFornecedores = null;
			
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

				listaFornecedores = null;
				
			}			
		
		}
		
		return listaFornecedores;
	}
	
	public List<FornecedorVO> consultarFornecedoresFiltro(String razao, String cnpj) {
		
		FornecedorVO fornecedor = null;
		
		List<FornecedorVO> listaFornecedores = null;
		
		try {
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select f.id_fornecedor_pj, pj.cnpj, pj.id_endereco, pj.razao_social, st.id_status, st.descricao from Fornecedor f"
					                       + " inner join Pessoa_Juridica pj on f.id_fornecedor_pj = pj.id_pessoa_juridica"
					                       + " inner join Status st on f.id_status = st.id_status"
					                       + " where f.id_status = ? and pj.cnpj like ? and upper(pj.razao_social) like upper(?)");
			

			pstm.setInt(1, Utilidades.STATUS_ATIVO.getIdStatus());
			pstm.setString(2, '%'+cnpj+'%');
			pstm.setString(3, '%'+razao+'%');
			
			//Executa uma pesquisa no banco
			rs = pstm.executeQuery();
			
			listaFornecedores = new ArrayList<FornecedorVO>();
			
			//Caregga a listaFornecedores
			while(rs.next()){
			
				Integer idFornecedor = rs.getInt("id_fornecedor_pj");
				
				fornecedor = new FornecedorVO();
					
				fornecedor.setIdPessoaJuridica(idFornecedor);
				fornecedor.setCnpj(rs.getString("cnpj"));
				fornecedor.setEndereco(new EnderecoVO());
				fornecedor.getEndereco().setIdEndereco(rs.getInt("id_endereco"));
				fornecedor.setRazaoSocial(rs.getString("razao_social"));
				fornecedor.setStatus(new StatusVO());
				fornecedor.getStatus().setIdStatus(rs.getInt("id_status"));
				fornecedor.setListaEmails(consultarEmailFornecedor(idFornecedor, conexao));
				fornecedor.setListaTelefones(consultarTelefoneFornecedor(idFornecedor, conexao));
				
				listaFornecedores.add(fornecedor);
				
			}
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			listaFornecedores = null;
			
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
			listaFornecedores = null;
			
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

				listaFornecedores = null;
				
			}			
		
		}
		
		return listaFornecedores;
	}
	
	private List<EmailVO> consultarEmailFornecedor(Integer idFornecedor, Connection conexao) throws SQLException {
		
		EmailVO email = null;
		
		List<EmailVO> listaEmails = null;
		
		ResultSet rs = null;
		
		PreparedStatement pstm = null;
		
		try {
			
			Connection conexaoLocal = conexao;
			
			//Cria o [select] que sera executado no banco
			pstm = conexaoLocal.prepareStatement("select id_email, email, id_pessoa_juridica from Email where id_pessoa_juridica = ? and id_status = ?");
			
			pstm.setInt(1, idFornecedor);
			pstm.setInt(2, Utilidades.STATUS_ATIVO.getIdStatus());
			
			//Executa uma pesquisa no banco
			rs = pstm.executeQuery();
			
			listaEmails = new ArrayList<EmailVO>();
			
			//Carrega a listaEmails que sera utilizada no consultarFornecedores
			while(rs.next()){
				
				email = new EmailVO();
					
				email.setIdEmail(rs.getInt("id_email"));
				email.setEmail(rs.getString("email"));
				email.setPessoaJuridica(new FornecedorVO());
				email.getPessoaJuridica().setIdPessoaJuridica(idFornecedor);
				
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
	
	private List<TelefoneVO> consultarTelefoneFornecedor(Integer idFornecedor, Connection conexao) throws SQLException{
		
		TelefoneVO telefone = null;
		
		List<TelefoneVO> listaTelefones = null;
		
		ResultSet rs = null;
		
		PreparedStatement pstm = null;
		
		try {
			
			Connection conexaoLocal = conexao;
			
			//Cria o [select] que sera executado no banco
			pstm = conexaoLocal.prepareStatement("select id_telefone, ddd, numero, id_pessoa_juridica from Telefone where id_pessoa_juridica = ? and id_status=?");
			
			//Executa uma pesquisa no banco
			pstm.setInt(1, idFornecedor);
			pstm.setInt(2, Utilidades.STATUS_ATIVO.getIdStatus());
			
			rs = pstm.executeQuery();
			
			listaTelefones = new ArrayList<TelefoneVO>();
			
			//Carrega a listaTelefones que sera utilizada no consultarFornecedores
			while(rs.next()){
				
				telefone = new TelefoneVO();
					
				telefone.setIdTelefone(rs.getInt("id_telefone"));
				telefone.setDdd(rs.getString("ddd"));
				telefone.setNumero(rs.getString("numero"));
				telefone.setPessoaJuridica(new FornecedorVO());
				telefone.getPessoaJuridica().setIdPessoaJuridica(idFornecedor);
				
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

	public boolean excluirFornecedor(FornecedorVO fornecedor) {
		
		try {
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			conexao.setAutoCommit(false); //Inicia uma transação
			
			//Cria o [delete] que sera executado no banco
			pstm = conexao.prepareStatement("update Fornecedor set id_status = ? where id_fornecedor_pj = ?");
			
			pstm.setInt(1, fornecedor.getStatus().getIdStatus());
			
			pstm.setInt(2, fornecedor.getIdPessoaJuridica());
			
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
	
	public FornecedorVO detalharFornecedor(FornecedorVO fornecedor){
		
		try {
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select en.bairro, en.cep, en.complemento, en.logradouro, en.numero, cd.id_cidade, cd.nome as nome_cidade, es.id_estado, es.nome, es.sigla from Endereco en"
					                       + " inner join Cidade cd on cd.id_cidade = en.id_cidade"
					                       + " inner join Estado es on es.id_estado = cd.id_estado"
					                       + " where en.id_endereco = ?");
			
			pstm.setLong(1, fornecedor.getEndereco().getIdEndereco());
			
			//Executa o select no banco
			rs = pstm.executeQuery();			
			
			//Carregando a listaItens
			if(rs.next()){
				
				fornecedor.getEndereco().setBairro(rs.getString("bairro"));
				fornecedor.getEndereco().setCep(rs.getString("cep"));
				fornecedor.getEndereco().setComplemento(rs.getString("complemento"));
				fornecedor.getEndereco().setLogradouro(rs.getString("logradouro"));
				fornecedor.getEndereco().setNumero(rs.getInt("numero"));
				fornecedor.getEndereco().setCidade(new CidadeVO());
				fornecedor.getEndereco().getCidade().setIdCidade(rs.getInt("id_cidade"));
				fornecedor.getEndereco().getCidade().setNome(rs.getString("nome_cidade"));
				fornecedor.getEndereco().getCidade().setEstado(new EstadoVO());
				fornecedor.getEndereco().getCidade().getEstado().setIdEstado(rs.getInt("id_estado"));
				fornecedor.getEndereco().getCidade().getEstado().setNome(rs.getString("nome"));
				fornecedor.getEndereco().getCidade().getEstado().setSigla(rs.getString("sigla"));
				fornecedor.setListaEmails(consultarEmailFornecedor(fornecedor.getIdPessoaJuridica(), conexao));
				fornecedor.setListaTelefones(consultarTelefoneFornecedor(fornecedor.getIdPessoaJuridica(), conexao));
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
		
		return fornecedor;
	}
	
	private void incluirTelefoneFornecedor(FornecedorVO fornecedor, List<TelefoneVO> listaTelefones, Connection conexao) throws SQLException {
		
		rs = null;
		
		pstm = null;
		Connection conexaoLocal = conexao;
			
			for(TelefoneVO telefone : listaTelefones){
				
				//Cria o [insert] que sera executado no banco
				pstm = conexaoLocal.prepareStatement("insert into Telefone (ddd, numero, id_pessoa_juridica, id_status) values (?,?,?,?)");
				
				pstm.setString(1, telefone.getDdd());
				pstm.setString(2, telefone.getNumero());
				pstm.setInt(3, fornecedor.getIdPessoaJuridica());
				pstm.setInt(4, fornecedor.getStatus().getIdStatus());
				
				//Executa o insert
				pstm.executeUpdate();
			}
			
	}
	
	private void alterarTelefoneFornecedor(List<TelefoneVO> listaTelefones, Connection conexao) throws SQLException {
		
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

	private void excluirTelefoneFornecedor(List<TelefoneVO> listaTelefones, Connection conexao) throws SQLException {
		
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
	
	private void incluirEmailFornecedor(FornecedorVO fornecedor, List<EmailVO> listaEmails, Connection conexao) throws SQLException {
		
		rs = null;
		
		pstm = null;
		Connection conexaoLocal = conexao;
			
			for(EmailVO email : listaEmails){
				
				//Cria o [insert] que sera executado no banco
				pstm = conexaoLocal.prepareStatement("insert into Email (email, id_pessoa_juridica, id_status) values (?,?,?)");
				
				pstm.setString(1, email.getEmail());
				pstm.setInt(2, fornecedor.getIdPessoaJuridica());
				pstm.setInt(3, fornecedor.getStatus().getIdStatus());
				
				//Executa o insert
				pstm.executeUpdate();
			}
			
	}
	
	private void alterarEmailFornecedor(List<EmailVO> listaEmails, Connection conexao) throws SQLException {
		
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
	
	private void excluirEmailFornecedor(List<EmailVO> listaEmails, Connection conexao) throws SQLException {
		
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
	
	private boolean alterarEnderecoFornecedor(EnderecoVO endereco) throws SQLException{
		
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
	
}
