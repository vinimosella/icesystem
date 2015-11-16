package bo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.Utilidades;
import vo.IngredienteReceitaProdutoVO;
import vo.MateriaPrimaVO;
import vo.OrdemProducaoVO;
import dao.IngredienteReceitaProdutoDAO;
import dao.MateriaPrimaDAO;
import dao.OrdemDeProducaoDAO;
import dao.ProdutoDAO;

public class OrdemProducaoBO {
	
	private OrdemDeProducaoDAO dao;
	private ProdutoDAO daoP;
	private IngredienteReceitaProdutoDAO ingRPDao;
	private MateriaPrimaDAO matDao;
	
	{
		dao = new OrdemDeProducaoDAO();
		daoP = new ProdutoDAO();
		ingRPDao = new IngredienteReceitaProdutoDAO();
		matDao = new MateriaPrimaDAO();
	}
	
	public boolean incluirOrdemProducao(OrdemProducaoVO ordemProducao){
		
		ordemProducao.setSituacao(Utilidades.SITUACAO_ORDEM_PRODUCAO_DEFAULT);
		
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
		
		//consulta os ingredientes pra depois voltar o estoque
		List<IngredienteReceitaProdutoVO> listaIngredientes = ingRPDao.consultarIngredientesReceita(ordemProducao.getProduto());
		
		List<MateriaPrimaVO> listaMateriasAtualizadas = new ArrayList<MateriaPrimaVO>();
		
		IngredienteReceitaProdutoVO ingredienteReceita;
		
		Iterator<IngredienteReceitaProdutoVO> it = listaIngredientes.iterator();		
		while(it.hasNext()){
			
			ingredienteReceita = it.next();
			
			//retorna a quantidade ao estoque
			ingredienteReceita.getMateriaPrima().setQuantidadeDisponivel(ingredienteReceita.getMateriaPrima().getQuantidadeDisponivel()+ingredienteReceita.getQuantidadeMateria()*ordemProducao.getQuantidade());
				
			//adiciona na lista que será atualiza no banco
			listaMateriasAtualizadas.add(ingredienteReceita.getMateriaPrima());
			
		}
		
		matDao.alterarEstoqueMaterias(listaMateriasAtualizadas);
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
