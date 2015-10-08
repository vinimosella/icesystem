package daoservice;

import vo.VendaVO;

public interface IVendaDAO {

	public boolean consultarVenda(VendaVO venda);
	
	public boolean cadastrarVenda(VendaVO venda);
	
	public boolean excluirVenda(VendaVO venda);

	public boolean atualizarVenda(VendaVO venda);
	
}
