package ui.estoque;

import ui.AtualizarSituacaoView;
import util.Utilidades;
import vo.OrdemProducaoVO;
import vo.SituacaoVO;
import bo.OrdemProducaoBO;

public class AtualizarOrdemProducaoView extends AtualizarSituacaoView{

	private static final long serialVersionUID = 1L;
	
	private OrdemProducaoBO bo;

	public AtualizarOrdemProducaoView(OrdemProducaoVO op) {
		super(op.getSituacao(), Utilidades.ATUALIZAR_ORDEM_PRODUCAO, op);

	}

	@Override
	public void actionBtnSalvar(SituacaoVO situacao, Object o) {

		bo = new OrdemProducaoBO();
		
		OrdemProducaoVO op = (OrdemProducaoVO) o;
		
		op.setSituacao(situacao);
		
		bo.alterarOrdemProducaoEControlaEstoque(op);
		
		Utilidades.frmHome.getContentPane().removeAll();
		OrdemDeProducaoView consultaOp = new OrdemDeProducaoView();
		Utilidades.frmHome.add(consultaOp);
		Utilidades.frmHome.revalidate();
		
	}

}
