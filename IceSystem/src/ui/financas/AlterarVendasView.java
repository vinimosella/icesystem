package ui.financas;

import ui.AlterarSituacaoView;
import util.Utilidades;
import vo.SituacaoVO;
import vo.VendaVO;
import bo.VendaBO;

public class AlterarVendasView extends AlterarSituacaoView {

	private static final long serialVersionUID = 1L;
	VendaBO bo = new VendaBO();
	
	public AlterarVendasView(VendaVO venda) {
		super(venda.getSituacao(), Utilidades.ATUALIZAR_VENDAS, venda);
	}

	@Override
	public void actionBtnSalvar(SituacaoVO situacao, Object o) {

		bo = new VendaBO();
		
		VendaVO venda = (VendaVO) o;
		
		venda.setSituacao(situacao);
		
		bo.AtualizarVenda(venda);
		
		Utilidades.frmHome.getContentPane().removeAll();
		ManterVendasView consulta = new ManterVendasView();
		Utilidades.frmHome.add(consulta);
		Utilidades.frmHome.revalidate();
		
	}



}
