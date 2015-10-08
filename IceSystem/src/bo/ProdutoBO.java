package bo;

import java.util.List;

import teste.BancoEstatico;
import vo.ProdutoVO;

public class ProdutoBO {

	public List<ProdutoVO> consultarProdutos(ProdutoVO produto){
		
		return BancoEstatico.listaProdutos;
		//return ProdutoDAO.consultar(produto);
	}

	public boolean alterarProduto(ProdutoVO produto) {

		return true;
	}
	
}
