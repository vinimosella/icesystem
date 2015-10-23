package bo;

import java.util.List;

import daoimpl.ProdutoDAO;
import teste.BancoEstatico;
import vo.ProdutoVO;

public class ProdutoBO {
	
	private ProdutoDAO dao;
	
	{
		dao = new ProdutoDAO();
	}

	public List<ProdutoVO> consultarProdutos(){
		
		return BancoEstatico.listaProdutos;
	}

	public boolean atualizarProduto(ProdutoVO produto) {

		return true;
	}

	public boolean excluirProduto(Integer idProduto) {

		return true;
	}

	public boolean cadastrarProduto(ProdutoVO produto) {

		produto.setQuantidadeEstoque(0);
		
		return dao.cadastrarProduto(produto);
	}

	public boolean alterarProduto(ProdutoVO produto) {

		return true;
	}
	
}
