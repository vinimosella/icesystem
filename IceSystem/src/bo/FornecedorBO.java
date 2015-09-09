package bo;

import java.util.List;

import dao.FornecedorDAO;
import teste.BancoEstatico;
import vo.CidadeVO;
import vo.EstadoVO;
import vo.FornecedorVO;

public class FornecedorBO {

	public List<EstadoVO> buscaEstados(){
		
		return BancoEstatico.consultaEstados();
	}
	
	public List<CidadeVO> buscaCidadesPorEstado(Integer idEstado){
		
		return BancoEstatico.consultaCidadesPorEstado(idEstado);
	}

	public boolean cadastrarFornecedor(FornecedorVO fornecedor) {

		return FornecedorDAO.cadastrarFornecedor(fornecedor);
	}
	
}
