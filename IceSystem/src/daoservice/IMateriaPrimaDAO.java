package daoservice;

import java.util.List;

import vo.FornecedorVO;
import vo.MateriaPrimaVO;

public interface IMateriaPrimaDAO {

	public List<MateriaPrimaVO> consultarMateriaPrimaFornecedor(FornecedorVO fornecedor);
	
}
