package ui.financas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ui.AlterarSituacaoView;
import util.Utilidades;
import vo.CompraVO;
import vo.ItemCompraVO;
import vo.MateriaPrimaVO;
import vo.SituacaoVO;
import bo.CompraBO;
import bo.MateriaPrimaBO;

public class AlterarComprasView extends AlterarSituacaoView{
	
	private static final long serialVersionUID = 1L;
	private CompraBO bo;
	
	public AlterarComprasView(CompraVO compra){
		
		super(compra.getSituacao(),Utilidades.ATUALIZAR_COMPRAS, compra);
	}

	@Override
	public void actionBtnSalvar(SituacaoVO situacao, Object o) {
		
		bo = new CompraBO();
		
		CompraVO compra = (CompraVO) o;
		
		compra.setSituacao(situacao);
		
		bo.atualizarCompra(compra);
		
		//se for mudado pra finalizado, o estoque de matéria é aumentado
		if(situacao.getDescricao().trim().equals(Utilidades.FINALIZADO)){
			
			MateriaPrimaBO matBo = new MateriaPrimaBO();
			List<ItemCompraVO> listaItensCompra = bo.consultarComprasPorId(compra);
			List<MateriaPrimaVO> listaMaterias = new ArrayList<MateriaPrimaVO>();
			Iterator<ItemCompraVO> it = listaItensCompra.iterator();
			ItemCompraVO item;
			
			while(it.hasNext()){
				
				item = it.next();
				
				//soma a quantidade que estava disponivel com a comprada
				item.getMateriaPrima().setQuantidadeDisponivel(item.getMateriaPrima().getQuantidadeDisponivel()+item.getQuantidade());
				
				listaMaterias.add(item.getMateriaPrima());
				
			}
			
			matBo.alteraEstoqueMaterias(listaMaterias);
		}
		
		Utilidades.frmHome.getContentPane().removeAll();
		ManterComprasView consulta = new ManterComprasView();
		Utilidades.frmHome.add(consulta);
		Utilidades.frmHome.revalidate();
		
	}

}
