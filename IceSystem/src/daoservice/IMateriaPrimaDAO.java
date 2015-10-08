package daoservice;

import java.util.List;

import vo.MateriaPrimaVO;

public interface IMateriaPrimaDAO {

	public List<MateriaPrimaVO> consultarMateriaPrima(MateriaPrimaVO materiaPrima);
	
	public boolean alterarMateriasPrimas(List<MateriaPrimaVO> materiaPrima);
	
}
