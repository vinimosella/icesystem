package daoservice;

import vo.ProdutoVO;

public interface IProdutoDAO {

	public boolean consultarProduto(ProdutoVO produto);
	
	public boolean cadastrarProduto(ProdutoVO produto);
	
	public boolean atualizarProduto(ProdutoVO produto);
	
	public boolean excluirProduto(ProdutoVO produto);
	
}
