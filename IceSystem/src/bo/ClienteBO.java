package bo;

import java.util.List;

import util.Utilidades;
import vo.CidadeVO;
import vo.ClienteVO;
import vo.EmailVO;
import vo.EstadoVO;
import vo.TelefoneVO;
import daoimpl.CidadeDAO;
import daoimpl.ClienteDAO;
import daoimpl.EstadoDAO;

public class ClienteBO {
	
	private ClienteDAO dao;
	private EstadoDAO estDao;
	private CidadeDAO cidDao;
	
	{
		estDao = new EstadoDAO();
		cidDao = new CidadeDAO();
		dao = new ClienteDAO();
	}
	
	public List<List<EmailVO>> gerenciaMudancasEmails(List<EmailVO>listaOriginal, List<EmailVO> listaMudada){
		
		return Utilidades.gerenciaMudancasEmails(listaOriginal, listaMudada);
	}
	
	public List<List<TelefoneVO>> gerenciaMudancasTelefones(List<TelefoneVO>listaOriginal, List<TelefoneVO> listaMudada){
		
		return Utilidades.gerenciaMudancasTelefones(listaOriginal, listaMudada);
	}
	
	public boolean cadastrarCliente(ClienteVO cliente){
		
		cliente.setStatus(Utilidades.STATUS_ATIVO);
		return dao.cadastrarCliente(cliente);
	}
	
	public List<ClienteVO> consultarClientes(){
		
		return dao.consultarTodosClientes();
	}
	
	public boolean excluirCliente(ClienteVO cliente){
		
		cliente.setStatus(Utilidades.STATUS_INATIVO);
		
		return dao.excluirCliente(cliente);
	}
	
	public boolean atualizarCliente(ClienteVO cliente, List<List<EmailVO>> listaListaEmail, List<List<TelefoneVO>> listaListaTelefone){
		
		return dao.alterarCliente(cliente, listaListaEmail, listaListaTelefone);
	}
	
	public ClienteVO detalharCliente(ClienteVO cliente){
		
		return dao.detalharCliente(cliente);
	}
	
	// ##################################################################

	public List<EstadoVO> buscaEstados(){
		
		return estDao.consultaEstados();
	}
	
	public List<CidadeVO> buscaCidadesPorEstado(EstadoVO estado){

		return cidDao.consultaCidadesPorEstado(estado);
		
	}	
	
}
