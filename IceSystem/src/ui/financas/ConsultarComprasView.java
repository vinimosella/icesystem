package ui.financas;

import java.awt.BorderLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ui.ConsultaGenericaView;
import util.Utilidades;
import vo.CompraVO;
import bo.CompraBO;

public class ConsultarComprasView extends ConsultaGenericaView{

	private static final long serialVersionUID = 1L;
	private CompraBO bo;
	private List<CompraVO> listaCompras;
	private CompraVO compra;
	
	public ConsultarComprasView() {
		super(Utilidades.CONSULTA_COMPRAS);
		
		super.getBtnRemover().setVisible(false);
		super.boundsBtn();
	}
	
	@Override
	public void btnAtualizar(Integer linhaSelecionada) {
		
		//se ja estiver cancelada ou finalizada...
		if(listaCompras.get(linhaSelecionada).getSituacao().getDescricao().equals(Utilidades.CANCELADO) || listaCompras.get(linhaSelecionada).getSituacao().getDescricao().equals(Utilidades.FINALIZADO)){
			
			JOptionPane.showMessageDialog(Utilidades.frmHome, "Não é possível alterar itens finalizados ou cancelados!", "Alerta!", JOptionPane.ERROR_MESSAGE);
			
		}
		else{
			
			AtualizarComprasView atualizarCompras = new AtualizarComprasView(listaCompras.get(linhaSelecionada));
			atualizarCompras.setVisible(true);
			
		}
		
	}

	@Override
	public void btnDetalhar(Integer linhaSelecionada) {

		DetalharCompraView detalharCompra = new DetalharCompraView(listaCompras.get(linhaSelecionada));
		detalharCompra.setVisible(true);
		
	}

	@Override
	public void btnCadastrar() {

		Utilidades.frmHome.getContentPane().removeAll();
		CompraMateriaPrimaView efetCompras = new CompraMateriaPrimaView(null);
		Utilidades.frmHome.getContentPane().add(efetCompras, BorderLayout.CENTER);
		Utilidades.frmHome.getContentPane().revalidate();
		
	}

	@Override
	public void carregaDtm(JTable table, DefaultTableModel dtm) {
				
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
		
	}

	//NAO TERÁ ESSE BOTÃO NESSA TELA
	@Override
	public void btnRemover(Integer linhaSelecionada) {
		
	}


}
