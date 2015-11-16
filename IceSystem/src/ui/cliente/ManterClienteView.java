package ui.cliente;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import ui.ManterGenericoView;
import util.Utilidades;
import vo.ClienteVO;
import bo.ClienteBO;

public class ManterClienteView extends ManterGenericoView{
	
	private static final long serialVersionUID = 1L;

	private DefaultTableModel dtm;
	private ClienteVO cliente;
	private List<ClienteVO> listaClientes;
	private ClienteBO bo;
	private JLabel lblRazao;
	private JTextField txtRazao;
	private JLabel lblCnpj;
	private JTextField txtCnpj;
	private JButton btnFiltrar;
	
	public ManterClienteView() {
		super(Utilidades.CONSULTA_CLIENTES);
		super.getScrollPane().setBounds(20, 70, 550, 400);
		super.boundsBtn(500);
		
		lblRazao = new JLabel("Razão Social:");
		lblRazao.setBounds(20,40,100,20);
		this.add(lblRazao);
		
		txtRazao = new JTextField();
		txtRazao.setBounds(120,40,130,20);
		this.add(txtRazao);
		
		lblCnpj = new JLabel("CNPJ:");
		lblCnpj.setBounds(280,40,70,20);
		this.add(lblCnpj);
		
		txtCnpj = new JTextField();
		txtCnpj.setBounds(330,40,130,20);
		this.add(txtCnpj);
		
		btnFiltrar = new JButton("Filtrar");
		btnFiltrar.setBounds(480,40,70,20);
		btnFiltrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				listaClientes = bo.filtrar(txtRazao.getText(), txtCnpj.getText());
				carregaDtm();
			}
		});
		this.add(btnFiltrar);

	}
	
	private void carregaDtm(){
		
		carregaDtm(super.getTable(), super.getDtm());
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
		
		if(bo == null){
			bo = new ClienteBO();
		}
		
		if(listaClientes == null){
			listaClientes = bo.consultarClientes();
		}
		
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
		
		bo = new ClienteBO();
		
		Utilidades.frmHome.getContentPane().removeAll();
		AlterarClienteView atualizar = new AlterarClienteView(bo.detalharCliente(listaClientes.get(linhaSelecionada)));
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
