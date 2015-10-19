package bo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import teste.BancoEstatico;
import util.Utilidades;
import vo.IngredienteReceitaProdutoVO;
import vo.MateriaPrimaVO;
import vo.OrdemProducaoVO;

public class OrdemProducaoBO {
	
	public boolean incluirOrdemProducao(OrdemProducaoVO ordemProducao){
		
		//return dao.incluirOrdemProducao(ordemProducao);
		return true;
	}
	
	public boolean alterarOrdemProducaoEControlaEstoque(OrdemProducaoVO ordemProducao){
		
		//dao.alterarOrdemProducao(ordemProducao);
		
		if(ordemProducao.getSituacao().getDescricao().equals(Utilidades.FINALIZADO)){
			
			ordemProducao.getProduto().setQuantidadeEstoque(ordemProducao.getProduto().getQuantidadeEstoque()+ordemProducao.getQuantidade());			
			//dao.alterarProduto(ordemProducao.getProduto());
		}
		
		else if(ordemProducao.getSituacao().getDescricao().equals(Utilidades.CANCELADO)){
			
			ordemCancelada(ordemProducao);
		}
		
		return true;
	}
	
	private void ordemCancelada(OrdemProducaoVO ordemProducao){
		
		//List<IngredienteReceitaProdutoVO> listaIngredientes = dao.consultarIngredienteReceitaProduto(ordemProducao.getProduto());
		
		List<IngredienteReceitaProdutoVO> listaIngredientes = BancoEstatico.listaReceitas;
		
		List<MateriaPrimaVO> listaMateriasAtualizadas = new ArrayList<MateriaPrimaVO>();
		
		Iterator<IngredienteReceitaProdutoVO> it = listaIngredientes.iterator();
		
		IngredienteReceitaProdutoVO receita;
		
		while(it.hasNext()){
			
			receita = it.next();
			
			//se a receita for do produto passado
			if(receita.getProduto().getIdProduto() == ordemProducao.getProduto().getIdProduto()){
				
				//diminui a quantidade do estoque
				receita.getMateriaPrima().setQuantidadeDisponivel(receita.getMateriaPrima().getQuantidadeDisponivel()-receita.getQuantidadeMateria()*ordemProducao.getQuantidade());
				
				//adiciona na lista que será atualiza no banco
				listaMateriasAtualizadas.add(receita.getMateriaPrima());
				
			}
			
		}
		
		//dao.AtualizarMateriasPrimas(listaMateriasAtualizadas);*/
	}

	public List<OrdemProducaoVO> consultarTodas() {

		return BancoEstatico.listaOrdensProducao;
	}
	
	public List<OrdemProducaoVO> consultarFinalizadas() {

		return BancoEstatico.listaOrdensProducaoFinzalizada;
	}
	
	public List<OrdemProducaoVO> consultarCanceladas() {

		return BancoEstatico.listaOrdensProducaoCancelada;
	}
	
	public List<OrdemProducaoVO> consultarSolicitadas() {

		return BancoEstatico.listaOrdensProducaoSolicitada;
	}
	

}
