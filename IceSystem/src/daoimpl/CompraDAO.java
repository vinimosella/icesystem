package daoimpl;

import vo.CompraVO;
import daoservice.ICompraDAO;

public class CompraDAO implements ICompraDAO{
	
	@Override
	public boolean consultarCompra(CompraVO compra){
		
		return true;
	}
	
	@Override
	public boolean cadastrarCompra(CompraVO compra){
		
		return true;
	}
	
	@Override
	public boolean atualizarCompra(CompraVO compra) {

		return true;
	}
	
}
