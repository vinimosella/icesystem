package ui.financas;

import ui.AtualizarSituacaoView;
import util.Utilidades;
import vo.CompraVO;
import vo.SituacaoVO;
import bo.CompraBO;

public class AtualizarComprasView extends AtualizarSituacaoView{
	
	private static final long serialVersionUID = 1L;
	private CompraBO bo;
	
	public AtualizarComprasView(CompraVO compra){
		
		super(compra.getSituacao(),Utilidades.ATUALIZAR_COMPRAS, compra);
	}

	@Override
	public void actionBtnSalvar(SituacaoVO situacao, Object o) {
		
		bo = new CompraBO();
		
		CompraVO compra = (CompraVO) o;
		
		compra.setSituacao(situacao);
		
		bo.atualizarCompra(compra);
		
		Utilidades.frmHome.getContentPane().removeAll();
		ConsultarComprasView consulta = new ConsultarComprasView(Utilidades.CONSULTA_COMPRAS);
		Utilidades.frmHome.add(consulta);
		Utilidades.frmHome.revalidate();
		
	}

}
