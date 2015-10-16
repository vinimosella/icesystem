package daoservice;

import java.util.List;

import vo.ItemVendaVO;
import vo.VendaVO;

public interface IVendaDAO {

	public List<VendaVO> consultarVendas();
	
	public boolean cadastrarVenda(VendaVO venda, List<ItemVendaVO> listaItensVenda);

	public boolean atualizarVenda(VendaVO venda);
	
}
