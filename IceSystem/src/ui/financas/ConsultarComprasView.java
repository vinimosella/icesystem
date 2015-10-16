package ui.financas;

import java.util.Iterator;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import vo.CompraVO;
import bo.CompraBO;

public class ConsultarComprasView extends ConsultaFinancasGenericaView{

	public ConsultarComprasView(String lblConsulta) {
		super(lblConsulta);
	}
	
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private DefaultTableModel dtm;
	private CompraBO bo;
	private List<CompraVO> listaCompras;
	private CompraVO compra;

	@Override
	public void montaTabela(JTable table) {
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 50, 550, 400);
		this.add(scrollPane);
				
		dtm = new DefaultTableModel(
				
				new Object[][] {
						
				},
				new String[] {
					"ID", "Descrição", "Data", "Funcionario"
				}				
		){
		
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				
			   return false;
			}
			
		};
		
		bo = new CompraBO();
		
		listaCompras = bo.consultarCompras();

		Iterator<CompraVO> it = listaCompras.iterator();
				
		while(it.hasNext()){
				
			compra = it.next();
				
			dtm.addRow(new Object[] {
					compra.getIdCompra(),
					compra.getSituacao().getDescricao(),
					compra.getDataCompra().toString(),
					compra.getFuncionario().getNome()
			});
			
		}			
		
		table.setModel(dtm);
		scrollPane.setViewportView(table);
		
	}

	@Override
	public void btnAtualizar(Integer linhaSelecionada) {
		
		AtualizarComprasView atualizarCompras = new AtualizarComprasView(listaCompras.get(linhaSelecionada));
		atualizarCompras.setVisible(true);
		
	}

	@Override
	public void btnDetalhar(Integer linhaSelecionada) {

		DetalharCompraView detalharCompra = new DetalharCompraView(listaCompras.get(linhaSelecionada));
		detalharCompra.setVisible(true);
		
	}


}
