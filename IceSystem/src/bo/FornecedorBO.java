package bo;

import java.util.List;

import util.Utilidades;
import vo.CidadeVO;
import vo.EmailVO;
import vo.EstadoVO;
import vo.FornecedorVO;
import vo.TelefoneVO;
import dao.CidadeDAO;
import dao.EstadoDAO;
import dao.FornecedorDAO;

public class FornecedorBO {
	
	private FornecedorDAO dao;
	private EstadoDAO estDao;
	private CidadeDAO cidDao;
	
	{
		estDao = new EstadoDAO();
		cidDao = new CidadeDAO();
		dao = new FornecedorDAO();
	}
	
	public List<FornecedorVO> filtrar(String razao, String cnpj){
		
		return dao.consultarFornecedoresFiltro(razao, cnpj);
	}
	
	public List<List<EmailVO>> gerenciaMudancasEmails(List<EmailVO>listaOriginal, List<EmailVO> listaMudada){
		
		return Utilidades.gerenciaMudancasEmails(listaOriginal, listaMudada);
	}
	
	public List<List<TelefoneVO>> gerenciaMudancasTelefones(List<TelefoneVO>listaOriginal, List<TelefoneVO> listaMudada){
		
		return Utilidades.gerenciaMudancasTelefones(listaOriginal, listaMudada);
	}
	
	public boolean cadastrarFornecedor(FornecedorVO fornecedor) {

		fornecedor.setStatus(Utilidades.STATUS_ATIVO);
		
		return dao.cadastrarFornecedor(fornecedor);
	}
	
	public boolean excluirFornecedor(FornecedorVO fornecedor){
		
		fornecedor.setStatus(Utilidades.STATUS_INATIVO);
		
		return dao.excluirFornecedor(fornecedor);
	}
	
	public boolean atualizarFornecedor(FornecedorVO fornecedor, List<List<EmailVO>> listaListaEmail, List<List<TelefoneVO>> listaListaTelefone){
		
		return dao.alterarFornecedor(fornecedor, listaListaEmail, listaListaTelefone);
	}
	
	public List<FornecedorVO> consultarFornecedores(){
		
		return dao.consultarFornecedores();
	}
	
	public FornecedorVO detalharFornecedor(FornecedorVO fornecedor){

		return dao.detalharFornecedor(fornecedor);
	}
	
	//########################################################################
	
	public List<EstadoVO> buscaEstados(){
		
		return estDao.consultaEstados();
	}
	
	public List<CidadeVO> buscaCidadesPorEstado(EstadoVO estado){
				
		return cidDao.consultaCidadesPorEstado(estado);
	}
	
}
