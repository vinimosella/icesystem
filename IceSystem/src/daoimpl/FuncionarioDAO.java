package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.LogFactory;
import util.Utilidades;
import vo.CargoVO;
import vo.CidadeVO;
import vo.EmailVO;
import vo.EnderecoVO;
import vo.EstadoVO;
import vo.FuncionarioVO;
import vo.TelefoneVO;

public class FuncionarioDAO {

	private Connection conexao;
	private PreparedStatement pstm;
	private ConnectionFactory fabrica;
	private ResultSet rs;
		
	{
		fabrica = ConnectionFactory.getInstance();		
	}
	
	public boolean alterarSenhaFuncLogado(FuncionarioVO funcionarioLogado){
	
		try {
			
			//Cria a conex�o com o banco
			conexao = fabrica.getConexao();
			conexao.setAutoCommit(false); //Inicia uma tran��o
			
			//Cria o [update] que sera executado no banco
			pstm = conexao.prepareStatement("update Funcionario set senha=? where id_funcionario=?");
			
			pstm.setString(1, funcionarioLogado.getSenha());
			pstm.setInt(2, funcionarioLogado.getIdFuncionario());
			
			//Executa uma atualiza��o no banco
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
	
	public boolean cadastrarFuncionario(FuncionarioVO funcionario){
		
		try {
			
			//Cria a conex�o com o banco
			conexao = fabrica.getConexao(); 				
			conexao.setAutoCommit(false); //Inicia uma transa��o
			
			//INCLUIR ENDERECO
			pstm = conexao.prepareStatement("insert into Endereco (id_cidade, logradouro, bairro, cep, numero, complemento) values (?, ?, ?, ?, ?, ?)",PreparedStatement.RETURN_GENERATED_KEYS);

			pstm.setInt(1, funcionario.getEndereco().getCidade().getIdCidade());
			pstm.setString(2, funcionario.getEndereco().getLogradouro());
			pstm.setString(3, funcionario.getEndereco().getBairro());
			pstm.setString(4, funcionario.getEndereco().getCep());
			pstm.setInt(5, funcionario.getEndereco().getNumero());
			pstm.setString(6, funcionario.getEndereco().getComplemento());

			pstm.executeUpdate();

			// Recebe o id gerado automaticamente no insert anterior
			rs = pstm.getGeneratedKeys();
			
			if (rs != null && rs.next()) {
				
				Integer idEndereco = rs.getInt(1);
				
				//Cria o [insert] que sera executado no banco
				pstm = conexao.prepareStatement("insert into Funcionario (nome, rg, cpf, usuario, senha, id_status, id_cargo, id_endereco) values (?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
				
				pstm.setString(1, funcionario.getNome());
				pstm.setString(2, funcionario.getRg());
				pstm.setString(3, funcionario.getCpf());
				pstm.setString(4, funcionario.getLogin());
				pstm.setString(5, funcionario.getSenha());
				pstm.setInt(6, funcionario.getStatus().getIdStatus());
				pstm.setInt(7, funcionario.getCargo().getIdCargo());
				pstm.setInt(8, idEndereco);
				
				//Executa uma atualiza��o no banco
				pstm.executeUpdate();
				
				//Recebe o id gerado automaticamente no insert anterior
				rs = pstm.getGeneratedKeys();
				
				if(rs != null && rs.next()){
					
					Integer idFuncionario = rs.getInt(1);
					
					//INCLUIR EMAIL
					for (EmailVO email : funcionario.getListaEmails()) {
						
						pstm = conexao.prepareStatement("insert into Email (id_funcionario, email, id_status) values (?, ?, ?)");
						
						pstm.setInt(1, idFuncionario);
						pstm.setString(2, email.getEmail());
						pstm.setInt(3, funcionario.getStatus().getIdStatus());
						
						pstm.executeUpdate();
					}
					
					//INCLUIR TELEFONE
					for (TelefoneVO telefone : funcionario.getListaTelefones()) {
						
						pstm = conexao.prepareStatement("insert into Telefone (id_funcionario, ddd, numero, id_status) values (?, ?, ?, ?)");
						
						pstm.setInt(1, idFuncionario);
						pstm.setString(2, telefone.getDdd());
						pstm.setString(3, telefone.getNumero());
						pstm.setInt(4, funcionario.getStatus().getIdStatus());
						
						pstm.executeUpdate();
					}

					conexao.commit();
				}
				else{
					conexao.rollback();
				}
			}
			else{
				
				conexao.rollback();
			}
			
		} catch (ClassNotFoundException cnf) {
			
			cnf.printStackTrace();
			
			//Caso ocorra algum erro, executa o rollback do cadastro no banco
			try {
				
				conexao.rollback();
				
			} catch (SQLException sql) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
				
				sql.printStackTrace();
				
				return false;
				
			}
			
		} catch (SQLException sql) {
			
			sql.printStackTrace();
			
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
	
	public FuncionarioVO realizarLogin(String user, String password){
		
		FuncionarioVO funcionario = new FuncionarioVO();
		
		try {
			
			//Cria a conex�o com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select f.id_funcionario, f.nome, f.rg, f.cpf, f.usuario, f.senha, f.id_cargo, f.id_endereco, f.id_status,"
										   + " en.bairro, en.cep, en.complemento, en.logradouro, en.numero,"
										   + " cd.id_cidade, cd.nome as nome_cidade,"
										   + " es.id_estado, es.nome, es.sigla,"
										   + " st.id_status, st.descricao,"
										   + " c.id_cargo, c.funcao"
										   + " from Funcionario f"
										   + " inner join Cargo c on f.id_cargo = c.id_cargo"
					                       + " inner join Status st on f.id_status = st.id_status"
					                       + " inner join Endereco en on en.id_endereco = f.id_endereco"
					                       + " inner join Cidade cd on cd.id_cidade = en.id_cidade"
					                       + " inner join Estado es on es.id_estado = cd.id_estado"
					                       + " where f.id_status = ? and f.usuario = ? and f.senha = ?");			

			pstm.setInt(1, Utilidades.STATUS_ATIVO.getIdStatus());
			pstm.setString(2, user);
			pstm.setString(3, password);
			
			//Executa uma pesquisa no banco
			rs = pstm.executeQuery();
						
			if(rs.next()){
		
				funcionario.setIdFuncionario(rs.getInt("id_funcionario"));
				funcionario.setNome(rs.getString("nome"));
				funcionario.setRg(rs.getString("rg"));
				funcionario.setCpf(rs.getString("cpf"));
				funcionario.setLogin(rs.getString("usuario"));
				funcionario.setSenha(rs.getString("senha"));
				funcionario.setEndereco(new EnderecoVO());
				funcionario.getEndereco().setIdEndereco(rs.getInt("id_endereco"));
				funcionario.getEndereco().setBairro(rs.getString("bairro"));
				funcionario.getEndereco().setCep(rs.getString("cep"));
				funcionario.getEndereco().setComplemento(rs.getString("complemento"));
				funcionario.getEndereco().setLogradouro(rs.getString("logradouro"));
				funcionario.getEndereco().setNumero(rs.getInt("numero"));
				funcionario.getEndereco().setCidade(new CidadeVO());
				funcionario.getEndereco().getCidade().setIdCidade(rs.getInt("id_cidade"));
				funcionario.getEndereco().getCidade().setNome(rs.getString("nome_cidade"));
				funcionario.getEndereco().getCidade().setEstado(new EstadoVO());
				funcionario.getEndereco().getCidade().getEstado().setIdEstado(rs.getInt("id_estado"));
				funcionario.getEndereco().getCidade().getEstado().setNome(rs.getString("nome"));
				funcionario.getEndereco().getCidade().getEstado().setSigla(rs.getString("sigla"));
				funcionario.setCargo(new CargoVO());
				funcionario.getCargo().setIdCargo(rs.getByte("id_cargo"));
				funcionario.getCargo().setFuncao(rs.getString("funcao"));
				
			}
			else{
				
				funcionario = null;
			}
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			funcionario = null;
			
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
			funcionario = null;
			
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

				funcionario = null;
				
			}			
		
		}
		
		return funcionario;
	}
	
	public List<FuncionarioVO> consultarFuncionarios(){
		
		FuncionarioVO funcionario;
		List<FuncionarioVO> listaFuncionarios = new ArrayList<FuncionarioVO>();
		
		try {
			
			//Cria a conex�o com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select f.id_funcionario, f.nome, f.rg, f.cpf, f.usuario, f.senha, f.id_cargo, f.id_endereco, f.id_status,"
										   + " st.id_status, st.descricao,"
										   + " c.id_cargo, c.funcao"
										   + " from Funcionario f"
										   + " inner join Cargo c on f.id_cargo = c.id_cargo"
					                       + " inner join Status st on f.id_status = st.id_status"
					                       + " where f.id_status = ?");			

			pstm.setInt(1, Utilidades.STATUS_ATIVO.getIdStatus());
			
			//Executa uma pesquisa no banco
			rs = pstm.executeQuery();
						
			while(rs.next()){
		
				funcionario = new FuncionarioVO();
				
				funcionario.setIdFuncionario(rs.getInt("id_funcionario"));
				funcionario.setNome(rs.getString("nome"));
				funcionario.setRg(rs.getString("rg"));
				funcionario.setCpf(rs.getString("cpf"));
				funcionario.setLogin(rs.getString("usuario"));
				funcionario.setSenha(rs.getString("senha"));
				funcionario.setEndereco(new EnderecoVO());
				funcionario.getEndereco().setIdEndereco(rs.getInt("id_endereco"));
				funcionario.setCargo(new CargoVO());
				funcionario.getCargo().setIdCargo(rs.getByte("id_cargo"));
				funcionario.getCargo().setFuncao(rs.getString("funcao"));
				
				listaFuncionarios.add(funcionario);
			}
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			listaFuncionarios = null;
			
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
			listaFuncionarios = null;
			
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

				listaFuncionarios = null;
				
			}			
		
		}
		
		return listaFuncionarios;
	}
	
	public boolean excluirFuncionario(FuncionarioVO funcionario) {
		
		try {
			
			//Cria a conex�o com o banco
			conexao = fabrica.getConexao();
			conexao.setAutoCommit(false); //Inicia uma transa��o
			
			//Cria o [delete] que sera executado no banco
			pstm = conexao.prepareStatement("update Funcionario set id_status = ? where id_funcionario = ?");
			
			pstm.setInt(1, funcionario.getStatus().getIdStatus());
			
			pstm.setInt(2, funcionario.getIdFuncionario());
			
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
	
	public FuncionarioVO detalharFuncionario(FuncionarioVO funcionario){
		
		try {
			
			//Cria a conex�o com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select en.bairro, en.cep, en.complemento, en.logradouro, en.numero, cd.id_cidade, cd.nome as nome_cidade, es.id_estado, es.nome, es.sigla from Endereco en"
					                       + " inner join Cidade cd on cd.id_cidade = en.id_cidade"
					                       + " inner join Estado es on es.id_estado = cd.id_estado"
					                       + " where en.id_endereco = ?");
			
			pstm.setLong(1, funcionario.getEndereco().getIdEndereco());
			
			//Executa o select no banco
			rs = pstm.executeQuery();			
			
			//Carregando a listaItens
			if(rs.next()){
				
				funcionario.getEndereco().setBairro(rs.getString("bairro"));
				funcionario.getEndereco().setCep(rs.getString("cep"));
				funcionario.getEndereco().setComplemento(rs.getString("complemento"));
				funcionario.getEndereco().setLogradouro(rs.getString("logradouro"));
				funcionario.getEndereco().setNumero(rs.getInt("numero"));
				funcionario.getEndereco().setCidade(new CidadeVO());
				funcionario.getEndereco().getCidade().setIdCidade(rs.getInt("id_cidade"));
				funcionario.getEndereco().getCidade().setNome(rs.getString("nome_cidade"));
				funcionario.getEndereco().getCidade().setEstado(new EstadoVO());
				funcionario.getEndereco().getCidade().getEstado().setIdEstado(rs.getInt("id_estado"));
				funcionario.getEndereco().getCidade().getEstado().setNome(rs.getString("nome"));
				funcionario.getEndereco().getCidade().getEstado().setSigla(rs.getString("sigla"));
				funcionario.setListaEmails(consultarEmailFuncionario(funcionario.getIdFuncionario(), conexao));
				funcionario.setListaTelefones(consultarTelefoneFuncionario(funcionario.getIdFuncionario(), conexao));
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
		
		return funcionario;
	}
	
	public List<EmailVO> consultarEmailFuncionario(Integer idFuncionario, Connection conexao) throws SQLException {
		
		EmailVO email = null;
		
		List<EmailVO> listaEmails = null;
		
		ResultSet rs = null;
		
		PreparedStatement pstm = null;
		
		try {
			
			Connection conexaoLocal = conexao;
			
			//Cria o [select] que sera executado no banco
			pstm = conexaoLocal.prepareStatement("select id_email, email, id_funcionario from Email where id_funcionario = ?");
			
			pstm.setInt(1, idFuncionario);
			
			//Executa uma pesquisa no banco
			rs = pstm.executeQuery();
			
			listaEmails = new ArrayList<EmailVO>();
			
			//Carrega a listaEmails que sera utilizada no consultarFornecedores
			while(rs.next()){
				
				email = new EmailVO();
					
				email.setIdEmail(rs.getInt("id_email"));
				email.setEmail(rs.getString("email"));
				email.setFuncionario(new FuncionarioVO());
				email.getFuncionario().setIdFuncionario(idFuncionario);
				
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
	
	public List<TelefoneVO> consultarTelefoneFuncionario(Integer idFuncionario, Connection conexao) throws SQLException{
		
		TelefoneVO telefone = null;
		
		List<TelefoneVO> listaTelefones = null;
		
		ResultSet rs = null;
		
		PreparedStatement pstm = null;
		
		try {
			
			Connection conexaoLocal = conexao;
			
			//Cria o [select] que sera executado no banco
			pstm = conexaoLocal.prepareStatement("select id_telefone, ddd, numero, id_funcionario from Telefone where id_funcionario = ?");
			
			//Executa uma pesquisa no banco
			pstm.setInt(1, idFuncionario);
			
			rs = pstm.executeQuery();
			
			listaTelefones = new ArrayList<TelefoneVO>();
			
			//Carrega a listaTelefones que sera utilizada no consultarFornecedores
			while(rs.next()){
				
				telefone = new TelefoneVO();
					
				telefone.setIdTelefone(rs.getInt("id_telefone"));
				telefone.setDdd(rs.getString("ddd"));
				telefone.setNumero(rs.getString("numero"));
				telefone.setFuncionario(new FuncionarioVO());
				telefone.getFuncionario().setIdFuncionario(idFuncionario);
				
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
