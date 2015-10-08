package ui.financas;

import javax.swing.JFrame;

import util.Utilidades;
import vo.SituacaoVO;
import vo.VendaVO;
import bo.VendaBO;

public class AtualizarVendasView extends AtualizarSituacaoView {

	private static final long serialVersionUID = 1L;
	VendaBO bo = new VendaBO();
	
	public AtualizarVendasView(VendaVO venda, JFrame frmHome) {
		super(venda.getSituacao(), Utilidades.ATUALIZAR_VENDAS, venda, frmHome);
	}

	@Override
	public void actionBtnSalvar(SituacaoVO situacao, Object o, JFrame frmHome) {

		bo = new VendaBO();
		
		VendaVO venda = (VendaVO) o;
		
		venda.setSituacao(situacao);
		
		bo.AtualizarVenda(venda);
		
		frmHome.getContentPane().removeAll();
		ConsultarVendasView consulta = new ConsultarVendasView(frmHome, Utilidades.CONSULTA_VENDAS);
		frmHome.add(consulta);
		frmHome.revalidate();
		
	}



}
