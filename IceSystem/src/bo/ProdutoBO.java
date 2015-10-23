package bo;

import java.util.List;

import vo.ProdutoVO;
import daoimpl.ProdutoDAO;

public class ProdutoBO {
	
	private ProdutoDAO dao;
	
	{
		dao = new ProdutoDAO();
	}

	public List<ProdutoVO> consultarProdutos(){
		
		return dao.consultarTodosProdutos();
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
