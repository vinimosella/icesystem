package bo;

import java.util.List;

import teste.BancoEstatico;
import util.Utilidades;
import vo.CompraVO;
import vo.ItemCompraVO;
import vo.SituacaoVO;

public class CompraBO {
	
	//private CompraDAO dao = new CompraDAO();
	
	public List<CompraVO> consultarCompras() {

		return BancoEstatico.listaCompras;
	}

	public boolean atualizarCompra(CompraVO compra) {

		return true;
	}
	
	public boolean cadastrarCompra(List<ItemCompraVO> listaItensCompra){
		
		CompraVO compra = new CompraVO();
		
		compra.setFuncionario(Utilidades.funcionarioLogado);
		
		SituacaoVO situacao = new SituacaoVO();
		
		situacao.setIdSituacao(Utilidades.CODIGO_SITUACAO_COMPRA_DEFAULT);
		
		compra.setSituacao(situacao);
		
		//dao.cadastrarCompra(listaItensCompra, compra);
		
		return true;
	}

	public List<ItemCompraVO> consultarComprasPorId(CompraVO compra) {

		return BancoEstatico.listaItensCompra;
	}
	
}
