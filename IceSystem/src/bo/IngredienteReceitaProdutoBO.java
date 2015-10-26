package bo;

import java.util.List;

import daoimpl.IngredienteProdutoReceitaDAO;
import vo.IngredienteReceitaProdutoVO;
import vo.ProdutoVO;

public class IngredienteReceitaProdutoBO {
	
	private IngredienteProdutoReceitaDAO dao;
	
	{
		dao = new IngredienteProdutoReceitaDAO();
	}
	
	public List<IngredienteReceitaProdutoVO> consultaReceitasPorProduto(ProdutoVO produto){
		
		return dao.consultarIngredientesReceita(produto);
	}

	public boolean cadastrarReceitas(List<IngredienteReceitaProdutoVO> listaReceita) {

		return dao.cadastrarIngredientesReceita(listaReceita);
	}

}
