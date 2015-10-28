package daoservice;

import java.util.List;

import vo.ClienteVO;

public interface IClienteDAO {

	public List<ClienteVO> consultarTodosClientes();
	
}
