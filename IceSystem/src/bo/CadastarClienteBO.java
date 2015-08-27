package bo;

import java.util.List;

import teste.BancoEstatico;
import vo.CidadeVO;
import vo.EstadoVO;

public class CadastarClienteBO {

	public List<EstadoVO> buscaEstados(){
		
		return BancoEstatico.consultaEstados();
	}
	
	public List<CidadeVO> buscaCidadesPorEstado(Integer idEstado){
		
		return BancoEstatico.consultaCidadesPorEstado(idEstado);
	}
	
}
