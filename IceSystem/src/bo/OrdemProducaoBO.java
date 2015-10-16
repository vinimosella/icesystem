package bo;

import util.Utilidades;
import vo.OrdemProducaoVO;

public class OrdemProducaoBO {
	
	public boolean incluirOrdemProducao(OrdemProducaoVO ordemProducao){
		
		return true;
	}
	
	public boolean alterarOrdemProducaoEControlaEstoque(OrdemProducaoVO ordemProducao){
		
		//dao.alterarOrdemProducao(ordemProducao);
		
		if(ordemProducao.getSituacao().getDescricao().equals(Utilidades.PRODUZIDO)){
			
			ordemProducao.getProduto().setQuantidadeEstoque(ordemProducao.getProduto().getQuantidadeEstoque()+ordemProducao.getQuantidade());			
			//dao.alterarProduto(ordemProducao.getProduto());
		}
		
		return true;
	}
	
	private void ordemCancelada(OrdemProducaoVO ordemProducao){
		
		/*List<MateriaPrimaVO> listaMateriasAtualizadas = new ArrayList<MateriaPrimaVO>();
		
		Iterator<IngredienteReceitaProdutoVO> it = listaReceitas.iterator();
		
		IngredienteReceitaProdutoVO receita;
		
		while(it.hasNext()){
			
			receita = it.next();
			
			//se a receita for do produto passado
			if(receita.getProduto().getIdProduto() == produto.getIdProduto()){
				
				//diminui a quantidade do estoque
				receita.getMateriaPrima().setQuantidadeDisponivel(receita.getMateriaPrima().getQuantidadeDisponivel()-receita.getQuantidadeMateria()*qtdProduto);
				
				//adiciona na lista que será atualiza no banco
				listaMateriasAtualizadas.add(receita.getMateriaPrima());
				
			}
			
		}
		
		//dao.AtualizarMateriasPrimas(listaMateriasAtualizadas);*/
	}
	
	

}
