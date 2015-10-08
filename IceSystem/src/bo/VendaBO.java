package bo;

import java.util.List;

import teste.BancoEstatico;
import vo.VendaVO;
import dao.VendaDAO;

public class VendaBO {

	public List<VendaVO> consultarVendas() {

		return BancoEstatico.listaVendas;
	}

	public boolean AtualizarVenda(VendaVO venda) {
		
		return VendaDAO.atualizarVenda(venda);
	}

}
