package ui.financas;

import java.awt.BorderLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ui.ConsultaGenericaView;
import util.Utilidades;
import vo.VendaVO;
import bo.VendaBO;

public class ConsultarVendasView extends ConsultaGenericaView{

	private static final long serialVersionUID = 1L;
	private VendaBO bo;
	private List<VendaVO> listaVendas;
	private VendaVO venda;
	
	public ConsultarVendasView() {
		super(Utilidades.CONSULTA_VENDAS);
		
		super.getBtnRemover().setVisible(false);
		super.boundsBtn();
	}

	@Override
	public void btnAtualizar(Integer linhaSelecionada) {
		
		//se ja estiver cancelada ou finalizada...
		if(listaVendas.get(linhaSelecionada).getSituacao().getDescricao().equals(Utilidades.CANCELADO) || listaVendas.get(linhaSelecionada).getSituacao().getDescricao().equals(Utilidades.FINALIZADO)){
			
			JOptionPane.showMessageDialog(Utilidades.frmHome, "Não é possível alterar itens finalizados ou cancelados!", "Alerta!", JOptionPane.ERROR_MESSAGE);
			
		}
		else{
			
			AtualizarVendasView atualizarVendas = new AtualizarVendasView(listaVendas.get(linhaSelecionada));
			atualizarVendas.setVisible(true);
			
		}
		
	}

	@Override
	public void btnDetalhar(Integer linhaSelecionada) {

		DetalharVendaView detalharVenda = new DetalharVendaView(listaVendas.get(linhaSelecionada));
		detalharVenda.setVisible(true);
		
	}

	@Override
	public void btnCadastrar() {
		
		Utilidades.frmHome.getContentPane().removeAll();
		VendaProdutoView efetVendas = new VendaProdutoView();
		Utilidades.frmHome.getContentPane().add(efetVendas, BorderLayout.CENTER);
		Utilidades.frmHome.getContentPane().revalidate();
		
	}

	@Override
	public void carregaDtm(JTable table, DefaultTableModel dtm) {
				
		dtm = new DefaultTableModel(
				
				new Object[][] {
						
				},
				new String[] {
					"ID", "Situação", "Data", "Cliente"
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
		
	}

	//ESSA TELA NÃO POSSUI ESSE BOTAO
	@Override
	public void btnRemover(Integer linhaSelecionada) {
		
	}
	
	
}
