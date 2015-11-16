package bo;

import java.util.ArrayList;
import java.util.List;

import util.Utilidades;
import vo.ItemVendaVO;
import vo.ProdutoVO;
import vo.VendaVO;
import daoimpl.ProdutoDAO;
import daoimpl.VendaDAO;

public class VendaBO {
	
	private VendaDAO dao;
	private ProdutoDAO prodDao;
	
	{
		dao = new VendaDAO();
		prodDao = new ProdutoDAO();
	}

	public List<VendaVO> consultarVendas() {

		return dao.consultarVendas();
	}
	
	public List<VendaVO> consultarFinalizadas() {

		return dao.consultarVendasFinalizadas();
	}
	
	public List<VendaVO> consultarCanceladas() {

		return dao.consultarVendasCanceladas();
	}
	
	public List<VendaVO> consultarSolicitadas() {

		return dao.consultarVendasSolicitadas();
	}

	public boolean AtualizarVenda(VendaVO venda) {
		
		if(venda.getSituacao().equals(Utilidades.CANCELADO)){
			
			List<ItemVendaVO> itensVenda = dao.detalharVenda(venda);
			List<ProdutoVO> listaProdutosAtualizados = new ArrayList<ProdutoVO>();
			
			ProdutoVO produto;
			
			for (ItemVendaVO itemVenda : itensVenda) {
				
				produto = itemVenda.getProduto();
				
				//devolve o estoque
				produto.setQuantidadeEstoque(produto.getQuantidadeEstoque()+itemVenda.getQuantidade());
				
				listaProdutosAtualizados.add(produto);
				
			}
			
			//se der certo alterar o estoque
			if(prodDao.alterarEstoqueProdutos(listaProdutosAtualizados)){
				
				//atualiza a venda
				return dao.atualizarVenda(venda);
			}
			
		}
		
		return false;
	}

	public boolean cadastrarVenda(VendaVO venda, List<ItemVendaVO> listaItensVenda) {
			
		List<ProdutoVO> listaProdutosAtualizados = new ArrayList<ProdutoVO>();
		
		for (ItemVendaVO itemVenda : listaItensVenda) {
						
			listaProdutosAtualizados.add(itemVenda.getProduto());			
		}
		
		if(prodDao.alterarEstoqueProdutos(listaProdutosAtualizados)){
			
			venda.setFuncionario(Utilidades.funcionarioLogado);
			venda.setSituacao(Utilidades.SITUACAO_VENDA_DEFAULT);
			return dao.cadastrarVenda(venda, listaItensVenda);
		}
		
		return false;
	}

	public List<ItemVendaVO> consultarVendasPorId(VendaVO venda) {

		return dao.detalharVenda(venda);
	}

}
