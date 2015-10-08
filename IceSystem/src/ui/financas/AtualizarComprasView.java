package ui.financas;

import javax.swing.JFrame;

import util.Utilidades;
import vo.CompraVO;
import vo.SituacaoVO;
import bo.CompraBO;

public class AtualizarComprasView extends AtualizarSituacaoView{
	
	private static final long serialVersionUID = 1L;
	private CompraBO bo;
	
	public AtualizarComprasView(CompraVO compra, JFrame frmHome){
		
		super(compra.getSituacao(),Utilidades.ATUALIZAR_COMPRAS, compra, frmHome);
	}

	@Override
	public void actionBtnSalvar(SituacaoVO situacao, Object o, JFrame frmHome) {
		
		bo = new CompraBO();
		
		CompraVO compra = (CompraVO) o;
		
		compra.setSituacao(situacao);
		
		bo.AtualizarCompra(compra);
		
		frmHome.getContentPane().removeAll();
		ConsultarComprasView consulta = new ConsultarComprasView(frmHome, Utilidades.CONSULTA_COMPRAS);
		frmHome.add(consulta);
		frmHome.revalidate();
		
	}

}
