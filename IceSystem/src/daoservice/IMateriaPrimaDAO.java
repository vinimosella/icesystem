package daoservice;

import java.util.List;

import vo.FornecedorVO;
import vo.IngredienteReceitaProdutoVO;
import vo.MateriaPrimaVO;

public interface IMateriaPrimaDAO {

	public List<MateriaPrimaVO> consultarMateriaPrimaFornecedor(FornecedorVO fornecedor);
	
	public boolean alterarMateriasPrimas(List<MateriaPrimaVO> materiaPrima);
	
}
