package daoservice;

import vo.CompraVO;

public interface ICompraDAO {

	public boolean consultarCompra(CompraVO compra);
	
	public boolean cadastrarCompra(CompraVO compra);

	public boolean atualizarCompra(CompraVO compra);
	
}
