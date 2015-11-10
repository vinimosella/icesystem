package daoservice;

import java.util.List;

import vo.IngredienteReceitaProdutoVO;
import vo.ProdutoVO;

public interface IIngredienteReceitaProdutoDAO {

	public List<IngredienteReceitaProdutoVO> consultarIngredientesReceita(ProdutoVO produto);
	
	public boolean excluirIngredientesReceita(IngredienteReceitaProdutoVO irp);
	
	public boolean cadastrarIngredientesReceita(List<IngredienteReceitaProdutoVO> listaIRP);
	
}
