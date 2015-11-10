package bo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import daoimpl.OrdemDeProducaoDAO;
import daoimpl.ProdutoDAO;
import teste.BancoEstatico;
import util.Utilidades;
import vo.IngredienteReceitaProdutoVO;
import vo.MateriaPrimaVO;
import vo.OrdemProducaoVO;
import vo.SituacaoVO;

public class OrdemProducaoBO {
	
	private OrdemDeProducaoDAO dao;
	private ProdutoDAO daoP;
	
	{
		dao = new OrdemDeProducaoDAO();
		daoP = new ProdutoDAO();
	}
	
	public boolean incluirOrdemProducao(OrdemProducaoVO ordemProducao){
		
		SituacaoVO situação = new SituacaoVO();
		situação.setIdSituacao(3);
		
		ordemProducao.setSituacao(situação);
		
		return dao.cadastrarOP(ordemProducao);
	}
	
	public boolean alterarOrdemProducaoEControlaEstoque(OrdemProducaoVO ordemProducao){
		
		if(ordemProducao.getSituacao().getDescricao().equals(Utilidades.FINALIZADO)){
						
			ordemProducao.getProduto().setQuantidadeEstoque(ordemProducao.getProduto().getQuantidadeEstoque()+ordemProducao.getQuantidade());			
			daoP.alterarProduto(ordemProducao.getProduto());
		}
		
		else if(ordemProducao.getSituacao().getDescricao().equals(Utilidades.CANCELADO)){
			
			ordemCancelada(ordemProducao);
		}
		
		return dao.alterarOrdemProducao(ordemProducao);
	}
	
	private void ordemCancelada(OrdemProducaoVO ordemProducao){
		
		//List<IngredienteReceitaProdutoVO> listaIngredientes = dao.consultarIngredienteReceitaProduto(ordemProducao.getProduto());
		
		//TODO
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

		return dao.consultarTodasOP();
	}
	
	public List<OrdemProducaoVO> consultarFinalizadas() {

		return dao.consultarOPFinalizado();
	}
	
	public List<OrdemProducaoVO> consultarCanceladas() {

		return dao.consultarOPCancelado();
	}
	
	public List<OrdemProducaoVO> consultarSolicitadas() {

		return dao.consultarOPSolicitado();
	}
	

}
