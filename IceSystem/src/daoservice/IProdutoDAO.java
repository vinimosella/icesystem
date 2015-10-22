package daoservice;

import java.util.List;

import vo.ProdutoVO;

public interface IProdutoDAO {

	public List<ProdutoVO> consultarTodosProdutos();
	
	public ProdutoVO consultarProduto(ProdutoVO produto);
	
	public boolean alterarProduto(ProdutoVO produto);
	
	public boolean cadastrarProduto(ProdutoVO produto);
	
}
