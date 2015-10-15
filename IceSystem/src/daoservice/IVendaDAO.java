package daoservice;

import java.util.List;

import vo.VendaVO;

public interface IVendaDAO {

	public List<VendaVO> consultarVendas();
	
	public boolean cadastrarVenda(VendaVO venda);
	
	public boolean excluirVenda(VendaVO venda);

	public boolean atualizarVenda(VendaVO venda);
	
}
