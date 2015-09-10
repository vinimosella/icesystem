package bo;

import java.util.List;

import teste.BancoEstatico;
import vo.CidadeVO;
import vo.ClienteVO;
import vo.EstadoVO;
import dao.ClienteDAO;

public class ClienteBO {
	
	public boolean cadastrarCliente(ClienteVO cliente){
		
		return ClienteDAO.cadastrarCliente(cliente);
	}
	
	public List<ClienteVO> consultarCliente(){
		
		return BancoEstatico.consultaClientes();
	}
	
	public boolean excluirCliente(ClienteVO cliente){
		
		return ClienteDAO.excluirCliente(cliente);
	}
	
	public boolean atualizarCliente(ClienteVO cliente){
		
		return ClienteDAO.atualizarCliente(cliente);
	}
	
	public ClienteVO detalharCliente(ClienteVO cliente){
		
		cliente.setListaEmails(BancoEstatico.consultaEmails());
		cliente.setListaTelefones(BancoEstatico.consultaTelefones());
				
		return cliente;
		//return ClienteDAO.detalharCliente(cliente);
	}
	
	// ##################################################################

	public List<EstadoVO> buscaEstados(){
		
		return BancoEstatico.consultaEstados();
	}
	
	public List<CidadeVO> buscaCidadesPorEstado(Integer idEstado){
		
		return BancoEstatico.consultaCidadesPorEstado(idEstado);
	}	
	
}
