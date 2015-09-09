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
	
	//########################################################################
	
	public List<EstadoVO> buscaEstados(){
		
		return BancoEstatico.consultaEstados();
	}
	
	public List<CidadeVO> buscaCidadesPorEstado(Integer idEstado){
		
		return BancoEstatico.consultaCidadesPorEstado(idEstado);
	}

	public List<FornecedorVO> consultarFornecedores(){
		
		return BancoEstatico.consultaFornecedores();
	}
	
}
