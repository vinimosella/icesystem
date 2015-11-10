package ui.cliente;

import java.awt.BorderLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ui.ConsultaGenericaView;
import util.Utilidades;
import vo.ClienteVO;
import bo.ClienteBO;

public class ConsultarClienteView extends ConsultaGenericaView{
	
	private static final long serialVersionUID = 1L;

	private DefaultTableModel dtm;
	private ClienteVO cliente;
	private List<ClienteVO> listaClientes;
	
	private ClienteBO bo;
	
	public ConsultarClienteView() {
		super(Utilidades.CONSULTA_CLIENTES);

	}

	@Override
	public void carregaDtm(JTable table, DefaultTableModel dtm) {
						
		dtm = new DefaultTableModel(
				
				new Object[][] {
						
				},
				new String[] {
					"ID","Razão Social", "CNPJ",
				}				
		){
		
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				
			   return false;
			}
			
		};
		
		bo = new ClienteBO();
		
		listaClientes = bo.consultarClientes();
		
		Iterator<ClienteVO> it = listaClientes.iterator();
			
		while(it.hasNext()){
				
			cliente = it.next();
				
			dtm.addRow(new Object[] {
					cliente.getIdPessoaJuridica(),
					cliente.getRazaoSocial(),
					cliente.getCnpj(),
			});
			
		}			
		
		this.dtm = dtm;
		table.setModel(dtm);
		
	}

	@Override
	public void btnDetalhar(Integer linhaSelecionada) {
		
		DetalharClienteView detalhe = new DetalharClienteView(listaClientes.get(linhaSelecionada));
		detalhe.setVisible(true);
		
	}

	@Override
	public void btnAtualizar(Integer linhaSelecionada) {
		
		Utilidades.frmHome.getContentPane().removeAll();
		AtualizarClienteView atualizar = new AtualizarClienteView(listaClientes.get(linhaSelecionada));
		Utilidades.frmHome.getContentPane().add(atualizar,BorderLayout.CENTER);
		Utilidades.frmHome.getContentPane().revalidate();
		
	}

	@Override
	public void btnRemover(Integer linhaSelecionada) {
		
		//se clicar em sim, vai excluir
		if(JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o cliente "+listaClientes.get(linhaSelecionada).getRazaoSocial()+" ?", "Exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
							
			//se excluir com sucesso...
			if(bo.excluirCliente(listaClientes.get(linhaSelecionada))){
								
				//remove da lista e da tabela
				listaClientes.remove(linhaSelecionada);
				dtm.removeRow(linhaSelecionada);
								
			}
							
		}
		
	}

	@Override
	public void btnCadastrar() {

		Utilidades.frmHome.getContentPane().removeAll();
		CadastrarClienteView cadastrarCliente = new CadastrarClienteView();
		Utilidades.frmHome.getContentPane().add(cadastrarCliente, BorderLayout.CENTER);
		Utilidades.frmHome.getContentPane().revalidate();
		
	}
	
}
