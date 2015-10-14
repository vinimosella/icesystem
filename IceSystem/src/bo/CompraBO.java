package bo;

import java.util.List;

import teste.BancoEstatico;
import vo.CompraVO;
import vo.ItemCompraVO;

public class CompraBO {
	
	//private CompraDAO dao = new CompraDAO();
	
	public List<CompraVO> consultarCompras() {

		return BancoEstatico.listaCompras;
	}

	public boolean atualizarCompra(CompraVO compra) {

		return true;
	}
	
	public boolean cadastrarCompra(List<ItemCompraVO> listaItensCompra){
		
		//dao.cadastrarCompra(listaItensCompra, Utilidades.funcionarioLogado);
		return true;
	}
	
}
