package bo;

import java.util.List;

import teste.BancoEstatico;
import vo.VendaVO;

public class VendaBO {

	public List<VendaVO> consultarVendas() {

		return BancoEstatico.listaVendas;
	}

	public boolean AtualizarVenda(VendaVO venda) {
		
		return true;
	}

}
