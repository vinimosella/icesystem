package daoservice;

import java.util.List;

import vo.CompraVO;
import vo.ItemCompraVO;

public interface ICompraDAO {

	public List<CompraVO> consultarCompras();
	
	public List<ItemCompraVO> detalharCompra(CompraVO compra);
	
	public boolean cadastrarCompra(CompraVO compra);

	public boolean atualizarCompra(CompraVO compra);
	
}
