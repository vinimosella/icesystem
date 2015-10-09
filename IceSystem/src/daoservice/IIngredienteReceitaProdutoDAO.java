package daoservice;

import java.util.List;

import vo.IngredienteReceitaProdutoVO;
import vo.ProdutoVO;

public interface IIngredienteReceitaProdutoDAO {

	public List<IngredienteReceitaProdutoVO> consultarIngredientes(ProdutoVO produto);
	
}
