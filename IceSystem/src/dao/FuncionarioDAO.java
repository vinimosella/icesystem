package dao;

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
import vo.StatusVO;
import vo.TelefoneVO;

public class FuncionarioDAO {

	private Connection conexao;
	private PreparedStatement pstm;
	private ConnectionFactory fabrica;
	private ResultSet rs;
		
	{
		fabrica = ConnectionFactory.getInstance();		
	}
	
	//{listaInclusao,listaAlteracao,listaExclusao}
	public boolean alterarFuncionario(FuncionarioVO funcionario, List<List<EmailVO>> listaListaEmail, List<List<TelefoneVO>> listaListaTelefone){
		
		try {
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			conexao.setAutoCommit(false); //Inicia uma transação
			
			//Cria o [update] que sera executado no banco
			pstm = conexao.prepareStatement("update Funcionario set id_cargo=?, nome=?, rg=?, cpf=?, usuario=?, senha=? where id_funcionario=?");
			
			pstm.setInt(1, funcionario.getCargo().getIdCargo());
			pstm.setString(2, funcionario.getNome());
			pstm.setString(3, funcionario.getRg());
			pstm.setString(4, funcionario.getCpf());
			pstm.setString(5, funcionario.getLogin());
			pstm.setString(6, funcionario.getSenha());
			pstm.setInt(7, funcionario.getIdFuncionario());
			
			//Executa uma atualização no banco
			pstm.executeUpdate();
			
			alterarEnderecoFuncionario(funcionario.getEndereco());
			
			if(listaListaEmail.get(0).size() > 0){
				
				incluirEmailFuncionario(funcionario, listaListaEmail.get(0), conexao);
			}
			
			if(listaListaEmail.get(1).size() > 0){
				
				alterarEmailFuncionario(listaListaEmail.get(1), conexao);
			}
			
			if(listaListaEmail.get(2).size() > 0){
				
				excluirEmailFuncionario(listaListaEmail.get(2), conexao);
			}
			
			if(listaListaTelefone.get(0).size() > 0){
				
				incluirTelefoneFuncionario(funcionario, listaListaTelefone.get(0), conexao);
			}
			
			if(listaListaTelefone.get(1).size() > 0){
				
				alterarTelefoneFuncionario(listaListaTelefone.get(1), conexao);
			}
			
			if(listaListaTelefone.get(2).size() > 0){
				
				excluirTelefoneFuncionario(listaListaTelefone.get(2), conexao);
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
	
	public boolean alterarSenhaFuncLogado(FuncionarioVO funcionarioLogado){
	
		try {
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			conexao.setAutoCommit(false); //Inicia uma tranção
			
			//Cria o [update] que sera executado no banco
			pstm = conexao.prepareStatement("update Funcionario set senha=? where id_funcionario=?");
			
			pstm.setString(1, funcionarioLogado.getSenha());
			pstm.setInt(2, funcionarioLogado.getIdFuncionario());
			
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
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao(); 				
			conexao.setAutoCommit(false); //Inicia uma transação
			
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
				
				//Executa uma atualização no banco
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
			
			//Cria a conexão com o banco
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
				funcionario.setListaEmails(consultarEmailFuncionario(funcionario.getIdFuncionario(), conexao));
				funcionario.setListaTelefones(consultarTelefoneFuncionario(funcionario.getIdFuncionario(), conexao));
				
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
			
			//Cria a conexão com o banco
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
				funcionario.setStatus(new StatusVO());
				funcionario.getStatus().setIdStatus(rs.getInt("id_status"));
				
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
	
	public List<FuncionarioVO> consultarFuncionariosFiltro(String nome, String cargo){
		
		FuncionarioVO funcionario;
		List<FuncionarioVO> listaFuncionarios = new ArrayList<FuncionarioVO>();
		
		try {
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select f.id_funcionario, f.nome, f.rg, f.cpf, f.usuario, f.senha, f.id_cargo, f.id_endereco, f.id_status,"
										   + " st.id_status, st.descricao,"
										   + " c.id_cargo, c.funcao"
										   + " from Funcionario f"
										   + " inner join Cargo c on f.id_cargo = c.id_cargo"
					                       + " inner join Status st on f.id_status = st.id_status"
					                       + " where f.id_status = ? and upper(f.nome) like upper(?) and upper(c.funcao) like upper(?)");			

			pstm.setInt(1, Utilidades.STATUS_ATIVO.getIdStatus());
			pstm.setString(2, '%'+nome+'%');
			pstm.setString(3, '%'+cargo+'%');
			
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
				funcionario.setStatus(new StatusVO());
				funcionario.getStatus().setIdStatus(rs.getInt("id_status"));
				
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
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			conexao.setAutoCommit(false); //Inicia uma transação
			
			//Cria o [delete] que sera executado no banco
			pstm = conexao.prepareStatement("update Funcionario set id_status = ? where id_funcionario = ?");
			
			pstm.setInt(1, funcionario.getStatus().getIdStatus());
			
			pstm.setInt(2, funcionario.getIdFuncionario());
			
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
	
	public FuncionarioVO detalharFuncionario(FuncionarioVO funcionario){
		
		try {
			
			//Cria a conexão com o banco
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
	
	private List<EmailVO> consultarEmailFuncionario(Integer idFuncionario, Connection conexao) throws SQLException {
		
		EmailVO email = null;
		
		List<EmailVO> listaEmails = null;
		
		ResultSet rs = null;
		
		PreparedStatement pstm = null;
		
		try {
			
			Connection conexaoLocal = conexao;
			
			//Cria o [select] que sera executado no banco
			pstm = conexaoLocal.prepareStatement("select id_email, email, id_funcionario from Email where id_funcionario = ? and id_status = ?");
			
			pstm.setInt(1, idFuncionario);
			pstm.setInt(2, Utilidades.STATUS_ATIVO.getIdStatus());
			
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
			//Obs: A conexão não é finalizada pois faz parte da mesma transação do Fornecedor, então é Finalizada apenas uma vez
			pstm.close();
			
			if(rs != null){
				
				rs.close();
			}
			
		}
		
		return listaEmails;
	}
	
	private List<TelefoneVO> consultarTelefoneFuncionario(Integer idFuncionario, Connection conexao) throws SQLException{
		
		TelefoneVO telefone = null;
		
		List<TelefoneVO> listaTelefones = null;
		
		ResultSet rs = null;
		
		PreparedStatement pstm = null;
		
		try {
			
			Connection conexaoLocal = conexao;
			
			//Cria o [select] que sera executado no banco
			pstm = conexaoLocal.prepareStatement("select id_telefone, ddd, numero, id_funcionario from Telefone where id_funcionario = ? and id_status = ?");
			
			//Executa uma pesquisa no banco
			pstm.setInt(1, idFuncionario);
			pstm.setInt(2, Utilidades.STATUS_ATIVO.getIdStatus());
			
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
			//Obs: A conexão não é finalizada pois faz parte da mesma transação do Fornecedor, então é Finalizada apenas uma vez
			pstm.close();
			
			if(rs != null){
				
				rs.close();
			}
			
		}
		
		return listaTelefones;
	}
	
	private void incluirEmailFuncionario(FuncionarioVO funcionario, List<EmailVO> listaEmails, Connection conexao) throws SQLException {
		
		rs = null;
		
		pstm = null;
		Connection conexaoLocal = conexao;
			
			for(EmailVO email : listaEmails){
				
				//Cria o [insert] que sera executado no banco
				pstm = conexaoLocal.prepareStatement("insert into Email (email, id_funcionario, id_status) values (?,?,?)");
				
				pstm.setString(1, email.getEmail());
				pstm.setInt(2, funcionario.getIdFuncionario());
				pstm.setInt(3, funcionario.getStatus().getIdStatus());
				
				//Executa o insert
				pstm.executeUpdate();
			}
			
	}
	
	private void alterarEmailFuncionario(List<EmailVO> listaEmails, Connection conexao) throws SQLException {
		
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
	
	private void excluirEmailFuncionario(List<EmailVO> listaEmails, Connection conexao) throws SQLException {
		
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
	
	private void incluirTelefoneFuncionario(FuncionarioVO funcionario, List<TelefoneVO> listaTelefones, Connection conexao) throws SQLException {
		
		rs = null;
		
		pstm = null;
		Connection conexaoLocal = conexao;
			
			for(TelefoneVO telefone : listaTelefones){
				
				//Cria o [insert] que sera executado no banco
				pstm = conexaoLocal.prepareStatement("insert into Telefone (ddd, numero, id_funcionario, id_status) values (?,?,?,?)");
				
				pstm.setString(1, telefone.getDdd());
				pstm.setString(2, telefone.getNumero());
				pstm.setInt(3, funcionario.getIdFuncionario());
				pstm.setInt(4, funcionario.getStatus().getIdStatus());
				
				//Executa o insert
				pstm.executeUpdate();
			}
			
	}
	
	private void alterarTelefoneFuncionario(List<TelefoneVO> listaTelefones, Connection conexao) throws SQLException {
		
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

	private void excluirTelefoneFuncionario(List<TelefoneVO> listaTelefones, Connection conexao) throws SQLException {
		
		rs = null;
		
		pstm = null;
		Connection conexaoLocal = conexao;
			
			for(TelefoneVO telefone : listaTelefones){
				
				//Cria o [update] que sera executado no banco
				pstm = conexaoLocal.prepareStatement("update Telefone set id_status = ? where id_telefone = ?");
				
				pstm.setInt(1, Utilidades.STATUS_INATIVO.getIdStatus());
				pstm.setInt(2, telefone.getIdTelefone());
				
				pstm.executeUpdate();
			}
			
	}
	
	private boolean alterarEnderecoFuncionario(EnderecoVO endereco) throws SQLException{
				
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
		
		pstm.executeUpdate();
		
		return true;	
	}
	
}
