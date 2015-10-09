package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.LogFactory;
import vo.EmailVO;
import vo.EnderecoVO;
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
				
			conexao = fabrica.getConexao();
			
			pstm = conexao.prepareStatement("select id_produto, quantidade_estoque, nome, sabor from Produto");
			
			rs = pstm.executeQuery();
			
			listaFornecedores = new ArrayList<FornecedorVO>();
						
			while(rs.next()){
			
				int idFornecedor = rs.getInt("id_pessoa_juridica");
								
				fornecedor = new FornecedorVO();
					
				fornecedor.setIdPessoaJuridica(idFornecedor);
				fornecedor.setCnpj(rs.getString("cnpj"));
				fornecedor.setEndereco(new EnderecoVO());
				fornecedor.getEndereco().setIdEndereco(rs.getInt("id_endereco"));
				fornecedor.setRazaoSocial(rs.getString("razao_social"));
				fornecedor.setListaEmails(consultarEmailFornecedor(idFornecedor, conexao));
				fornecedor.setListaTelefones(consultarTelefoneFornecedor(idFornecedor, conexao));
				
				listaFornecedores.add(fornecedor);
				
			}
			
		} catch (SQLException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			listaFornecedores = null;
			
		} catch (ClassNotFoundException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			listaFornecedores = null;
			
		} finally {
			
			try {
				
				conexao.close();
				pstm.close();
				
				if(rs != null){
					
					rs.close();
				}
				
			} catch (SQLException e) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());

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
			
			pstm = conexaoLocal.prepareStatement("select id_email, email, id_fornecedor from Email where id_fornecedor = ?");
			
			pstm.setInt(1, idFornecedor);
			
			rs = pstm.executeQuery();
			
			listaEmails = new ArrayList<EmailVO>();
			
			while(rs.next()){
				
				email = new EmailVO();
					
				email.setIdEmail(rs.getInt("id_email"));
				email.setEmail(rs.getString("email"));
				email.setPessoaJuridica(new FornecedorVO());
				email.getPessoaJuridica().setIdPessoaJuridica(idFornecedor);
				
				listaEmails.add(email);
				
			}
			
		} finally {
			
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
			
			pstm = conexaoLocal.prepareStatement("select id_telefone, ddd, numero, id_fornecedor from Telefone where id_fornecedor = ?");
			
			pstm.setInt(1, idFornecedor);
			
			rs = pstm.executeQuery();
			
			listaTelefones = new ArrayList<TelefoneVO>();
			
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
			
			pstm.close();
			
			if(rs != null){
				
				rs.close();
			}
			
		}
		
		return listaTelefones;
	}

}
