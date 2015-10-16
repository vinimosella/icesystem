package ui.financas;

import java.util.Iterator;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import util.Utilidades;
import vo.ItemVendaVO;
import vo.VendaVO;
import bo.VendaBO;

public class DetalharVendaView extends DetalharFinancasGenericaView{
	
	private static final long serialVersionUID = 1L;
	private Double valorTotal;
	private DefaultTableModel dtm;
	private VendaVO venda;
	private ItemVendaVO itemVenda;
	private List<ItemVendaVO> listaItensVenda;
	private VendaBO bo;
	private Iterator<?> it;

	public DetalharVendaView(VendaVO venda) {
		super(venda, Utilidades.DETALHE_VENDAS);

	}

	@Override
	public DefaultTableModel montaDtm(Object o) {
		
		valorTotal = 0.0;
		venda = (VendaVO) o;
		bo = new VendaBO();
		listaItensVenda = bo.consultarVendasPorId(venda);
		
		dtm = new DefaultTableModel(
				
				new Object[][] {
						
				},
				new String[] {
					"ID Venda", "ID Produto", "Nome-Produto", "Sabor", "Quantidade", "Valor", "Total"
				}				
		){
		
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				
			   return false;
			}
			
		};

		it = listaItensVenda.iterator();
				
		while(it.hasNext()){
				
			itemVenda = (ItemVendaVO) it.next();
			//	"ID Venda", "ID Produto", "Nome-Produto", "Sabor", "Quantidade", "Valor", "Total"
			dtm.addRow(new Object[] {
					venda.getIdVenda(),
					itemVenda.getProduto().getIdProduto(),
					itemVenda.getProduto().getNome(),
					(itemVenda.getProduto().getSabor()!=null) ? itemVenda.getProduto().getSabor() : "-",
					itemVenda.getQuantidade(),
					itemVenda.getValor(),
					Utilidades.FORMAT.format(itemVenda.getQuantidade()*itemVenda.getValor())
			});
			
			valorTotal += itemVenda.getQuantidade() * itemVenda.getValor();
		}
		
		return dtm;
		
	}

	@Override
	public Double valorTotal() {

		return valorTotal;
	}

}
