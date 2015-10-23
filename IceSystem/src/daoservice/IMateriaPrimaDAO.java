package daoservice;

import java.util.List;

import vo.FornecedorVO;
import vo.MateriaPrimaVO;

public interface IMateriaPrimaDAO {

	public List<MateriaPrimaVO> consultarMPFornecedor(FornecedorVO fornecedor);
	
	public List<MateriaPrimaVO> consultarTodasMP();
	
	public boolean alterarMP(MateriaPrimaVO mp);
	
	public boolean excluirMP(MateriaPrimaVO mp);
	
	public boolean cadastrarMP(MateriaPrimaVO mp);
	
}
