package bo;

import java.util.List;

import teste.BancoEstatico;
import util.Utilidades;
import vo.ItemVendaVO;
import vo.SituacaoVO;
import vo.VendaVO;

public class VendaBO {

	public List<VendaVO> consultarVendas() {

		return BancoEstatico.listaVendas;
	}

	public boolean AtualizarVenda(VendaVO venda) {
		
		return true;
	}

	public boolean cadastrarVenda(VendaVO venda, List<ItemVendaVO> listaItensVenda) {

		venda.setFuncionario(Utilidades.funcionarioLogado);
		
		SituacaoVO situacao = new SituacaoVO();
		situacao.setIdSituacao(Utilidades.CODIGO_SITUACAO_VENDA_DEFAULT);
		venda.setSituacao(situacao);
		
		//dao.cadastrarVenda(venda, listaItensVenda);
		
		return true;
	}

	public List<ItemVendaVO> consultarVendasPorId(VendaVO venda) {

		return BancoEstatico.listaItensVenda;
	}

}
