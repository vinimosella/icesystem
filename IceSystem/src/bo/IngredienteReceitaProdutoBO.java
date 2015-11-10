package bo;

import java.util.List;

import daoimpl.IngredienteReceitaProdutoDAO;
import vo.IngredienteReceitaProdutoVO;
import vo.ProdutoVO;

public class IngredienteReceitaProdutoBO {
	
	private IngredienteReceitaProdutoDAO dao;
	
	{
		dao = new IngredienteReceitaProdutoDAO();
	}
	
	public List<IngredienteReceitaProdutoVO> consultaReceitasPorProduto(ProdutoVO produto){
		
		return dao.consultarIngredientesReceita(produto);
	}

	public boolean cadastrarReceitas(List<IngredienteReceitaProdutoVO> listaReceita) {

		return dao.cadastrarIngredientesReceita(listaReceita);
	}

}
