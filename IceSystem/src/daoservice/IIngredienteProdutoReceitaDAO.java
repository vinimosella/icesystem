package daoservice;

import java.util.List;

import vo.IngredienteReceitaProdutoVO;
import vo.ProdutoVO;

public interface IIngredienteProdutoReceitaDAO {

	public List<IngredienteReceitaProdutoVO> consultarIngredientesReceita(ProdutoVO produto);
	
}
