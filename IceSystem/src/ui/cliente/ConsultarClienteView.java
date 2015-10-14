package ui.cliente;

import java.awt.BorderLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ui.ConsultaGenericaView;
import vo.ClienteVO;
import bo.ClienteBO;

public class ConsultarClienteView extends ConsultaGenericaView{
	
	private static final long serialVersionUID = 1L;

	private JFrame frmHome;
	private JScrollPane scrollPane;
	private DefaultTableModel dtm;
	private ClienteVO cliente;
	private List<ClienteVO> listaClientes;
	
	private ClienteBO bo;
	
	public ConsultarClienteView(JFrame frmHome, String lblConsulta) {
		super(frmHome,lblConsulta);

	}

	@Override
	public void montaTabela(JTable table) {
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 50, 550, 400);
		this.add(scrollPane);
				
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
		
		listaClientes = bo.consultarCliente();

		Iterator<ClienteVO> it = listaClientes.iterator();
			
		while(it.hasNext()){
				
			cliente = it.next();
				
			dtm.addRow(new Object[] {
					cliente.getIdPessoaJuridica(),
					cliente.getRazaoSocial(),
					cliente.getCnpj(),
			});
			
		}			
		
		table.setModel(dtm);
		scrollPane.setViewportView(table);
		
	}

	@Override
	public void btnDetalhar(Integer linhaSelecionada) {
		
		DetalharClienteView detalhe = new DetalharClienteView(listaClientes.get(linhaSelecionada));
		detalhe.setVisible(true);
		
	}

	@Override
	public void btnAtualizar(Integer linhaSelecionada) {
		
		ConsultarClienteView.this.frmHome.getContentPane().removeAll();
		AtualizarClienteView atualizar = new AtualizarClienteView(ConsultarClienteView.this.frmHome, listaClientes.get(linhaSelecionada));
		ConsultarClienteView.this.frmHome.getContentPane().add(atualizar,BorderLayout.CENTER);
		ConsultarClienteView.this.frmHome.getContentPane().revalidate();
		
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
	
}
