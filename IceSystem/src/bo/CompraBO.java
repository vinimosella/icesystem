package bo;

import java.util.List;

import util.Utilidades;
import vo.CompraVO;
import vo.ItemCompraVO;
import dao.CompraDAO;

public class CompraBO {
	
	private CompraDAO dao;
	
	{
		dao = new CompraDAO();
	}
	
	public List<CompraVO> consultarCompras() {

		return dao.consultarCompras();
	}
	
	public List<CompraVO> consultarFinalizadas() {

		return dao.consultarComprasFinalizadas();
	}
	
	public List<CompraVO> consultarCanceladas() {

		return dao.consultarComprasCanceladas();
	}
	
	public List<CompraVO> consultarSolicitadas() {

		return dao.consultarComprasSolicitadas();
	}

	public boolean atualizarCompra(CompraVO compra) {

		return dao.atualizarCompra(compra);
	}
	
	public boolean cadastrarCompra(List<ItemCompraVO> listaItensCompra){
		
		CompraVO compra = new CompraVO();
		
		compra.setFuncionario(Utilidades.funcionarioLogado);
						
		compra.setSituacao(Utilidades.SITUACAO_COMPRA_DEFAULT);
		
		dao.cadastrarCompra(compra,listaItensCompra);
		
		return true;
	}

	public List<ItemCompraVO> consultarComprasPorId(CompraVO compra) {

		return dao.detalharCompra(compra);
	}
	
}
