package daoservice;

import java.util.List;

import vo.CompraVO;
import vo.FuncionarioVO;
import vo.ItemCompraVO;

public interface ICompraDAO {

	public List<CompraVO> consultarCompras();
	
	public List<ItemCompraVO> detalharCompra(CompraVO compra);
	
	public boolean cadastrarCompra(FuncionarioVO funcionario, List<ItemCompraVO> listaItensCompra);

	public boolean atualizarCompra(CompraVO compra);
	
}
