package bo;

import java.util.List;

import teste.BancoEstatico;
import vo.MateriaPrimaVO;

public class MateriaPrimaBO {

	public List<MateriaPrimaVO> consultarMateriasPrimas(){
		
		return BancoEstatico.listaMateriasPrimas;
	}
	
}
