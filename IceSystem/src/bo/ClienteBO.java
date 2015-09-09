package bo;

import java.util.List;

import dao.ClienteDAO;
import teste.BancoEstatico;
import vo.CidadeVO;
import vo.ClienteVO;
import vo.EstadoVO;

public class ClienteBO {
	
	public boolean cadastrarCliente(ClienteVO cliente){
		
		return ClienteDAO.cadastrarCliente(cliente);
	}

	public List<EstadoVO> buscaEstados(){
		
		return BancoEstatico.consultaEstados();
	}
	
	public List<CidadeVO> buscaCidadesPorEstado(Integer idEstado){
		
		return BancoEstatico.consultaCidadesPorEstado(idEstado);
	}	
	
}
