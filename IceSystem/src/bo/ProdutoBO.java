package bo;

import java.util.List;

import util.Utilidades;
import vo.IngredienteReceitaProdutoVO;
import vo.ProdutoVO;
import dao.IngredienteReceitaProdutoDAO;
import dao.ProdutoDAO;

public class ProdutoBO {
	
	private ProdutoDAO dao;
	private IngredienteReceitaProdutoDAO ingDao;
	
	{
		dao = new ProdutoDAO();
		ingDao = new IngredienteReceitaProdutoDAO();
	}
	
	public List<ProdutoVO> filtrarBusca(String tipo, String sabor){
		
		return dao.consultarTodosProdutosPorTipoESabor(tipo, sabor);
	}

	public List<ProdutoVO> consultarProdutos(){
		
		return dao.consultarTodosProdutos();
	}

	public boolean atualizarProduto(ProdutoVO produto, List<IngredienteReceitaProdutoVO> listaIncluidos, List<IngredienteReceitaProdutoVO> listaExcluidos, List<IngredienteReceitaProdutoVO> listaAlterados){
		
		//adicionar o produto nas receitas antes
		
		ingDao.cadastrarIngredientesReceita(listaIncluidos, produto);
		ingDao.alterarIngredientesReceita(listaAlterados);
		ingDao.excluirIngredientesReceita(listaExcluidos);
		
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
