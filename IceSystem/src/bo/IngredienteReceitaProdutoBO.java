package bo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import teste.BancoEstatico;
import vo.IngredienteReceitaProdutoVO;
import vo.ProdutoVO;

public class IngredienteReceitaProdutoBO {
	
	public List<IngredienteReceitaProdutoVO> consultaReceitasPorProduto(ProdutoVO produto){
		
		List<IngredienteReceitaProdutoVO> aux = new ArrayList<IngredienteReceitaProdutoVO>();
		
		Iterator<IngredienteReceitaProdutoVO> it = BancoEstatico.listaReceitas.iterator();
		
		IngredienteReceitaProdutoVO receita;
		
		while(it.hasNext()){
			
			receita = (IngredienteReceitaProdutoVO) it.next();
			
			if(receita.getProduto().getIdProduto()==produto.getIdProduto()){
				aux.add(receita);
			}			
			
		}
		
		return aux;
	}

}
