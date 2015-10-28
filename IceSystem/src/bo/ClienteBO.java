package bo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import teste.BancoEstatico;
import util.Utilidades;
import vo.CidadeVO;
import vo.ClienteVO;
import vo.EstadoVO;

public class ClienteBO {
	
	public boolean cadastrarCliente(ClienteVO cliente){
		
		cliente.setStatus(Utilidades.STATUS_ATIVO);
		
		return true;
	}
	
	public List<ClienteVO> consultarClientes(){
		
		return BancoEstatico.listaClientes;
	}
	
	public boolean excluirCliente(ClienteVO cliente){
		
		return true;
	}
	
	public boolean atualizarCliente(ClienteVO cliente){
		
		return true;
	}
	
	public ClienteVO detalharCliente(ClienteVO cliente){
		
		cliente.setListaEmails(BancoEstatico.listaEmails);
		cliente.setListaTelefones(BancoEstatico.listaTelefones);
				
		return cliente;
		//return ClienteDAO.detalharCliente(cliente);
	}
	
	// ##################################################################

	public List<EstadoVO> buscaEstados(){
		
		return BancoEstatico.listaEstados;
	}
	
	public List<CidadeVO> buscaCidadesPorEstado(Integer idEstado){

		Iterator<CidadeVO> it = BancoEstatico.listaCidades.iterator();		
		List<CidadeVO> listaCidadesAux = new ArrayList<CidadeVO>();
		CidadeVO cidadeAux;
		
		while(it.hasNext()){
			
			cidadeAux = it.next();
			
			if(cidadeAux.getEstado().getIdEstado() == idEstado){
				listaCidadesAux.add(cidadeAux);
			}
		}
		
		return listaCidadesAux;
		
	}	
	
}
