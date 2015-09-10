package bo;

import java.util.List;

import teste.BancoEstatico;
import vo.CidadeVO;
import vo.EstadoVO;
import vo.FornecedorVO;
import dao.FornecedorDAO;

public class FornecedorBO {
	
	public boolean cadastrarFornecedor(FornecedorVO fornecedor) {

		return FornecedorDAO.cadastrarFornecedor(fornecedor);
	}
	
	public boolean excluirFornecedor(FornecedorVO fornecedor){
		
		return FornecedorDAO.excluirFornecedor(fornecedor);
	}
	
	public boolean atualizarFornecedor(FornecedorVO fornecedor){
		
		return FornecedorDAO.atualizarFornecedor(fornecedor);
	}
	
	public List<FornecedorVO> consultarFornecedores(){
		
		return BancoEstatico.consultaFornecedores();
	}
	
	public FornecedorVO detalharFornecedor(FornecedorVO fornecedor){
		
		fornecedor.setListaEmails(BancoEstatico.consultaEmails());
		fornecedor.setListaTelefones(BancoEstatico.consultaTelefones());
				
		return fornecedor;
		//return FornecedorDAO.detalharFornecedor(fornecedor);
	}
	
	//########################################################################
	
	public List<EstadoVO> buscaEstados(){
		
		return BancoEstatico.consultaEstados();
	}
	
	public List<CidadeVO> buscaCidadesPorEstado(Integer idEstado){
		
		return BancoEstatico.consultaCidadesPorEstado(idEstado);
	}
	
}
