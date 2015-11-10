package bo;

import java.util.List;

import util.Utilidades;
import vo.ItemVendaVO;
import vo.VendaVO;
import daoimpl.VendaDAO;

public class VendaBO {
	
	private VendaDAO dao;
	
	{
		dao = new VendaDAO();
	}

	public List<VendaVO> consultarVendas() {

		return dao.consultarVendas();
	}

	public boolean AtualizarVenda(VendaVO venda) {
		
		return dao.atualizarVenda(venda);
	}

	public boolean cadastrarVenda(VendaVO venda, List<ItemVendaVO> listaItensVenda) {

		venda.setFuncionario(Utilidades.funcionarioLogado);
		
		venda.setSituacao(Utilidades.SITUACAO_VENDA_DEFAULT);
		
		return dao.cadastrarVenda(venda, listaItensVenda);
	}

	public List<ItemVendaVO> consultarVendasPorId(VendaVO venda) {

		return dao.detalharVenda(venda);
	}

}
