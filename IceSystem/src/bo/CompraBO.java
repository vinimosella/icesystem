package bo;

import java.util.List;

import teste.BancoEstatico;
import vo.CompraVO;

public class CompraBO {
	
	public List<CompraVO> consultarCompras() {

		return BancoEstatico.listaCompras;
	}

	public boolean AtualizarCompra(CompraVO compra) {

		return true;
	}
	
}
