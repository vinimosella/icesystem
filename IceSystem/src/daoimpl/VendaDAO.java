package daoimpl;

import vo.VendaVO;
import daoservice.IVendaDAO;

public class VendaDAO implements IVendaDAO{

	@Override
	public boolean consultarVenda(VendaVO venda){
		
		return true;
	}
	
	@Override
	public boolean cadastrarVenda(VendaVO venda){
		
		return true;
	}
	
	@Override
	public boolean excluirVenda(VendaVO venda){
		
		return true;
	}

	@Override
	public boolean atualizarVenda(VendaVO venda) {

		return false;
	}
	
}
