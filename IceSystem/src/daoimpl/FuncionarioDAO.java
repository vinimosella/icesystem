package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.LogFactory;
import util.Utilidades;
import vo.CargoVO;
import vo.CidadeVO;
import vo.EnderecoVO;
import vo.EstadoVO;
import vo.FuncionarioVO;

public class FuncionarioDAO {

	private Connection conexao;
	private PreparedStatement pstm;
	private ConnectionFactory fabrica;
	private ResultSet rs;
		
	{
		fabrica = ConnectionFactory.getInstance();		
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
	
}
