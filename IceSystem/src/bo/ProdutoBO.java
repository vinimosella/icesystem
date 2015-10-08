package bo;

import java.util.List;

import teste.BancoEstatico;
import vo.ProdutoVO;

public class ProdutoBO {

	public List<ProdutoVO> consultarProdutos(){
		
		return BancoEstatico.listaProdutos;
	}

	public boolean alterarProduto(ProdutoVO produto) {

		return true;
	}
	
}
