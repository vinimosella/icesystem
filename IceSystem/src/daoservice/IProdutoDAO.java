package daoservice;

import java.util.List;

import vo.ProdutoVO;

public interface IProdutoDAO {

	public List<ProdutoVO> consultarProduto(ProdutoVO produto);
	
	public boolean cadastrarProduto(ProdutoVO produto);
	
	public boolean atualizarProduto(ProdutoVO produto);
	
	public boolean excluirProduto(ProdutoVO produto);
	
}
