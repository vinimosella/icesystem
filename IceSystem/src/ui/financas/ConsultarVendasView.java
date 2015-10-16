package ui.financas;

import java.util.Iterator;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import vo.VendaVO;
import bo.VendaBO;

public class ConsultarVendasView extends ConsultaFinancasGenericaView{

	public ConsultarVendasView(String lblConsulta) {
		super(lblConsulta);
	}

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private DefaultTableModel dtm;
	private VendaBO bo;
	private List<VendaVO> listaVendas;
	private VendaVO venda;
	
	@Override
	public void montaTabela(JTable table) {
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 50, 550, 400);
		this.add(scrollPane);
				
		dtm = new DefaultTableModel(
				
				new Object[][] {
						
				},
				new String[] {
					"ID", "Descrição", "Data", "Cliente"
				}				
		){
		
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				
			   return false;
			}
			
		};
		
		bo = new VendaBO();
		
		listaVendas = bo.consultarVendas();

		Iterator<VendaVO> it = listaVendas.iterator();
				
		while(it.hasNext()){
				
			venda = it.next();
				
			dtm.addRow(new Object[] {
					venda.getIdVenda(),
					venda.getSituacao().getDescricao(),
					venda.getDataVenda().toString(),
					venda.getCliente().getRazaoSocial()
			});
			
		}			
		
		table.setModel(dtm);
		scrollPane.setViewportView(table);
		
	}

	@Override
	public void btnAtualizar(Integer linhaSelecionada) {
		
		AtualizarVendasView atualizarVendas = new AtualizarVendasView(listaVendas.get(linhaSelecionada));
		atualizarVendas.setVisible(true);
		
	}

	@Override
	public void btnDetalhar(Integer linhaSelecionada) {
		// TODO Auto-generated method stub
		
	}
	
	
}
