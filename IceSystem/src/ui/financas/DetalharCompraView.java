package ui.financas;

import java.util.Iterator;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import util.Utilidades;
import vo.CompraVO;
import vo.ItemCompraVO;
import bo.CompraBO;

public class DetalharCompraView extends DetalharFinancasGenericaView{
	
	private static final long serialVersionUID = 1L;
	private Double valorTotal;
	private DefaultTableModel dtm;
	private CompraVO compra;
	private ItemCompraVO itemCompra;
	private List<ItemCompraVO> listaItensCompra;
	private CompraBO bo;
	private Iterator<?> it;

	public DetalharCompraView(CompraVO compra) {
		super(compra, Utilidades.DETALHE_COMPRAS);
	}

	@Override
	public DefaultTableModel montaDtm(Object o) {
		
		valorTotal = 0.0;
		compra = (CompraVO) o;
		bo = new CompraBO();
		listaItensCompra = bo.consultarComprasPorId(compra);
		
		dtm = new DefaultTableModel(
				
				new Object[][] {
						
				},
				new String[] {
					"ID Compra", "ID Materia", "Nome-Materia", "Sabor", "Quantidade", "Valor", "Total"
				}				
		){
		
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				
			   return false;
			}
			
		};

		it = listaItensCompra.iterator();
				
		while(it.hasNext()){
				
			itemCompra = (ItemCompraVO) it.next();
			
			dtm.addRow(new Object[] {
					compra.getIdCompra(),
					itemCompra.getMateriaPrima().getIdMateriaPrima(),
					itemCompra.getMateriaPrima().getNome(),
					(itemCompra.getMateriaPrima().getSabor()!=null) ? itemCompra.getMateriaPrima().getSabor() : "-",
					itemCompra.getQuantidade(),
					itemCompra.getValor(),
					Utilidades.FORMAT.format(itemCompra.getQuantidade()*itemCompra.getValor())
			});
			
			valorTotal += itemCompra.getQuantidade() * itemCompra.getValor();
		}
		
		return dtm;
	}

	@Override
	public Double valorTotal() {

		return valorTotal;
	}

}
