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
import vo.EmailVO;
import vo.EnderecoVO;
import vo.EstadoVO;
import vo.FornecedorVO;
import vo.TelefoneVO;
import daoservice.IFornecedorDAO;

public class FornecedorDAO implements IFornecedorDAO{
	
	private Connection conexao;
	private PreparedStatement pstm;
	private ConnectionFactory fabrica;
	private ResultSet rs;
		
	{
		fabrica = ConnectionFactory.getInstance();		
	}
	
	@Override
	public List<FornecedorVO> consultarFornecedores() {
		
		FornecedorVO fornecedor = null;
		
		List<FornecedorVO> listaFornecedores = null;
		
		try {
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select f.id_fornecedor_pj, pj.cnpj, pj.id_endereco, en.bairro, en.cep, en.complemento, en.logradouro, en.numero, cd.id_cidade, cd.nome as nome_cidade, es.id_estado, es.nome, es.sigla, pj.razao_social, st.id_status, st.descricao from Fornecedor f"
					                       + " inner join Pessoa_Juridica pj on f.id_fornecedor_pj = pj.id_pessoa_juridica"
					                       + " inner join Status st on f.id_status = st.id_status"
					                       + " inner join Endereco en on en.id_endereco = pj.id_endereco"
					                       + " inner join Cidade cd on cd.id_cidade = en.id_cidade"
					                       + " inner join Estado es on es.id_estado = cd.id_estado where f.id_status = ?");
			

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
				fornecedor.setRazaoSocial(rs.getString("razao_social"));
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
	
	@Override
	public List<EmailVO> consultarEmailFornecedor(Integer idFornecedor, Connection conexao) throws SQLException {
		
		EmailVO email = null;
		
		List<EmailVO> listaEmails = null;
		
		ResultSet rs = null;
		
		PreparedStatement pstm = null;
		
		try {
			
			Connection conexaoLocal = conexao;
			
			//Cria o [select] que sera executado no banco
			pstm = conexaoLocal.prepareStatement("select id_email, email, id_pessoa_juridica from Email where id_pessoa_juridica = ?");
			
			pstm.setInt(1, idFornecedor);
			
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
	
	@Override
	public List<TelefoneVO> consultarTelefoneFornecedor(Integer idFornecedor, Connection conexao) throws SQLException{
		
		TelefoneVO telefone = null;
		
		List<TelefoneVO> listaTelefones = null;
		
		ResultSet rs = null;
		
		PreparedStatement pstm = null;
		
		try {
			
			Connection conexaoLocal = conexao;
			
			//Cria o [select] que sera executado no banco
			pstm = conexaoLocal.prepareStatement("select id_telefone, ddd, numero, id_pessoa_juridica from Telefone where id_pessoa_juridica = ?");
			
			//Executa uma pesquisa no banco
			pstm.setInt(1, idFornecedor);
			
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

}
