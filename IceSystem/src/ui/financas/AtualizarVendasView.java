package ui.financas;

import ui.AtualizarSituacaoView;
import util.Utilidades;
import vo.SituacaoVO;
import vo.VendaVO;
import bo.VendaBO;

public class AtualizarVendasView extends AtualizarSituacaoView {

	private static final long serialVersionUID = 1L;
	VendaBO bo = new VendaBO();
	
	public AtualizarVendasView(VendaVO venda) {
		super(venda.getSituacao(), Utilidades.ATUALIZAR_VENDAS, venda);
	}

	@Override
	public void actionBtnSalvar(SituacaoVO situacao, Object o) {

		bo = new VendaBO();
		
		VendaVO venda = (VendaVO) o;
		
		venda.setSituacao(situacao);
		
		bo.AtualizarVenda(venda);
		
		Utilidades.frmHome.getContentPane().removeAll();
		ConsultarVendasView consulta = new ConsultarVendasView();
		Utilidades.frmHome.add(consulta);
		Utilidades.frmHome.revalidate();
		
	}



}
