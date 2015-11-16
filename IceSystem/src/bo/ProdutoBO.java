package bo;

import java.util.List;

import util.Utilidades;
import vo.ProdutoVO;
import dao.ProdutoDAO;

public class ProdutoBO {
	
	private ProdutoDAO dao;
	
	{
		dao = new ProdutoDAO();
	}

	public List<ProdutoVO> consultarProdutos(){
		
		return dao.consultarTodosProdutos();
	}

	public boolean atualizarProduto(ProdutoVO produto) {
		
		return dao.alterarProduto(produto);
	}

	public boolean excluirProduto(ProdutoVO produto) {
		
		produto.setStatus(Utilidades.STATUS_INATIVO);

		return dao.excluirProduto(produto);
	}

	public ProdutoVO cadastrarProduto(ProdutoVO produto) {

		produto.setQuantidadeEstoque(0);
		produto.setStatus(Utilidades.STATUS_ATIVO);
		
		return dao.cadastrarProduto(produto);
	}
	
}
