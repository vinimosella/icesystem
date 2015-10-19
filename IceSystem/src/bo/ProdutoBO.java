package bo;

import java.util.List;

import teste.BancoEstatico;
import vo.ProdutoVO;

public class ProdutoBO {

	public List<ProdutoVO> consultarProdutos(){
		
		return BancoEstatico.listaProdutos;
	}

	public boolean atualizarProduto(ProdutoVO produto) {

		return true;
	}

	public boolean excluirProduto(Integer idProduto) {

		return true;
	}
	
}
