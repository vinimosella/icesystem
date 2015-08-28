package bo;

import java.util.List;

import teste.BancoEstatico;
import vo.CargoVO;
import vo.CidadeVO;
import vo.EstadoVO;

public class CadastrarFuncionarioBO {

	public List<EstadoVO> buscaEstados(){
		
		return BancoEstatico.consultaEstados();
	}
	
	public List<CidadeVO> buscaCidadesPorEstado(Integer idEstado){
		
		return BancoEstatico.consultaCidadesPorEstado(idEstado);
	}
	
	public List<CargoVO> buscaCargos(){
		
		return BancoEstatico.consultaCargo();
	}
	
}
