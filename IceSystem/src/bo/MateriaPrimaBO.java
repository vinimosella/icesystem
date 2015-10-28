package bo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.Utilidades;
import vo.FornecedorVO;
import vo.IngredienteReceitaProdutoVO;
import vo.MateriaPrimaVO;
import vo.ProdutoVO;
import daoimpl.MateriaPrimaDAO;

public class MateriaPrimaBO {
	
	private MateriaPrimaDAO dao;
	
	{
		dao = new MateriaPrimaDAO();
	}

	public List<MateriaPrimaVO> consultarMateriasPrimas(FornecedorVO fornecedor){
		
		return dao.consultarMPFornecedor(fornecedor);
	}
	
	public List<MateriaPrimaVO> consultarMateriasPrimas(){
		
		return dao.consultarTodasMP();
	}
	
	public void alterarMateriaPrimaReceita(List<IngredienteReceitaProdutoVO> listaReceitas, Integer qtdProduto, ProdutoVO produto){
		
		List<MateriaPrimaVO> listaMateriasAtualizadas = new ArrayList<MateriaPrimaVO>();
		
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
		
		//dao.AtualizarMateriasPrimas(listaMateriasAtualizadas);
	}

	public boolean excluirMateriaPrima(MateriaPrimaVO materiaPrima) {

		return dao.excluirMP(materiaPrima);
	}

	public boolean cadastrarMateriaPrima(MateriaPrimaVO materiaPrima) {
		
		materiaPrima.setQuantidadeDisponivel(0.0);
		materiaPrima.setStatus(Utilidades.STATUS_ATIVO);

		return dao.cadastrarMP(materiaPrima);
	}

	public boolean alterarMateriaPrima(MateriaPrimaVO materiaPrima) {

		return dao.alterarMP(materiaPrima);
	}
	
	public boolean alteraEstoqueMaterias(List<MateriaPrimaVO> listaMaterias) {

		return dao.alterarEstoqueMaterias(listaMaterias);
	}
	
}
