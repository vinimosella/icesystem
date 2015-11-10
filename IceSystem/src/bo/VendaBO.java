package bo;

import java.util.List;

import daoimpl.VendaDAO;
import teste.BancoEstatico;
import util.Utilidades;
import vo.ItemVendaVO;
import vo.VendaVO;

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

		//TODO
		return BancoEstatico.listaItensVenda;
	}

}
