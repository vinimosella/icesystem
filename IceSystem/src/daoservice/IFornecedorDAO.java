package daoservice;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import vo.EmailVO;
import vo.FornecedorVO;
import vo.TelefoneVO;

public interface IFornecedorDAO {

	public List<FornecedorVO> consultarFornecedores();
	
	public List<EmailVO> consultarEmailFornecedor(Integer idFornecedor, Connection conexao) throws SQLException ;
	
	public List<TelefoneVO> consultarTelefoneFornecedor(Integer idFornecedor, Connection conexao) throws SQLException ;
	
}
