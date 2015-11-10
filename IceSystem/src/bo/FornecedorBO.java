package bo;

import java.util.List;

import teste.BancoEstatico;
import util.Utilidades;
import vo.CidadeVO;
import vo.EstadoVO;
import vo.FornecedorVO;
import daoimpl.CidadeDAO;
import daoimpl.EstadoDAO;
import daoimpl.FornecedorDAO;

public class FornecedorBO {
	
	private FornecedorDAO dao;
	private EstadoDAO estDao;
	private CidadeDAO cidDao;
	
	{
		estDao = new EstadoDAO();
		cidDao = new CidadeDAO();
		dao = new FornecedorDAO();
	}
	
	public boolean cadastrarFornecedor(FornecedorVO fornecedor) {

		fornecedor.setStatus(Utilidades.STATUS_ATIVO);
		
		return dao.cadastrarFornecedor(fornecedor);
	}
	
	public boolean excluirFornecedor(FornecedorVO fornecedor){
		
		fornecedor.setStatus(Utilidades.STATUS_INATIVO);
		
		return dao.excluirFornecedor(fornecedor);
	}
	
	public boolean atualizarFornecedor(FornecedorVO fornecedor){
		
		return true;
	}
	
	public List<FornecedorVO> consultarFornecedores(){
		
		return dao.consultarFornecedores();
	}
	
	public FornecedorVO detalharFornecedor(FornecedorVO fornecedor){
		
		fornecedor.setListaEmails(BancoEstatico.listaEmails);
		fornecedor.setListaTelefones(BancoEstatico.listaTelefones);
				
		return fornecedor;
		//return FornecedorDAO.detalharFornecedor(fornecedor);
	}
	
	//########################################################################
	
	public List<EstadoVO> buscaEstados(){
		
		return estDao.consultaEstados();
	}
	
	public List<CidadeVO> buscaCidadesPorEstado(EstadoVO estado){
				
		return cidDao.consultaCidadesPorEstado(estado);
	}
	
}
