package ui.fornecedor;

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
import vo.FornecedorVO;
import bo.FornecedorBO;

public class ManterFornecedorView extends ManterGenericoView{

	private DefaultTableModel dtm;
	private FornecedorVO fornecedor;
	private List<FornecedorVO> listaFornecedores;
	private FornecedorBO bo;
	private JLabel lblRazao;
	private JTextField txtRazao;
	private JLabel lblCnpj;
	private JTextField txtCnpj;
	private JButton btnFiltrar;
	
	public ManterFornecedorView() {
		super(Utilidades.CONSULTA_FORNECEDORES);
		super.getScrollPane().setBounds(20, 70, 550, 400);
		super.boundsBtn(500);
		
		if(!Utilidades.funcionarioLogado.getCargo().getFuncao().trim().equals(Utilidades.CARGO_ACESSO_GERENTE)){
			super.getBtnAtualizar().setEnabled(false);
			super.getBtnRemover().setEnabled(false);
			super.getBtnCadastrar().setEnabled(false);
		}
		
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

				listaFornecedores = bo.filtrar(txtRazao.getText(), txtCnpj.getText());
				carregaDtm();
			}
		});
		this.add(btnFiltrar);

	}
	
	private void carregaDtm(){
		
		carregaDtm(super.getTable(), super.getDtm());
	}


	private static final long serialVersionUID = 1L;

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
		
		if(bo==null){
			bo = new FornecedorBO();
		}
		
		if(listaFornecedores==null){
			listaFornecedores = bo.consultarFornecedores();
		}
		
		Iterator<FornecedorVO> it = listaFornecedores.iterator();
			
		while(it.hasNext()){
				
			fornecedor = it.next();
				
			dtm.addRow(new Object[] {
					fornecedor.getIdPessoaJuridica(),
					fornecedor.getRazaoSocial(),
					fornecedor.getCnpj(),
			});
			
		}			
		
		this.dtm = dtm;
		table.setModel(dtm);		
	}

	@Override
	public void btnDetalhar(Integer linhaSelecionada) {
		
		DetalharFornecedorView detalhe = new DetalharFornecedorView(listaFornecedores.get(linhaSelecionada));
		detalhe.setVisible(true);
		
	}

	@Override
	public void btnAtualizar(Integer linhaSelecionada) {
		
		bo = new FornecedorBO();
		
		Utilidades.frmHome.getContentPane().removeAll();
		AlterarFornecedorView atualizar = new AlterarFornecedorView(bo.detalharFornecedor(listaFornecedores.get(linhaSelecionada)));
		Utilidades.frmHome.getContentPane().add(atualizar,BorderLayout.CENTER);
		Utilidades.frmHome.getContentPane().revalidate();
		
	}

	@Override
	public void btnRemover(Integer linhaSelecionada) {
		
		//se clicar em sim, vai excluir
		if(JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o fornecedor "+listaFornecedores.get(linhaSelecionada).getRazaoSocial()+" ?", "Exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
					
			//se excluir com sucesso...
			if(bo.excluirFornecedor(listaFornecedores.get(linhaSelecionada))){
						
				//remove da lista e da tabela
				listaFornecedores.remove(linhaSelecionada);
				dtm.removeRow(linhaSelecionada);
						
			}
					
		}
		
	}

	@Override
	public void btnCadastrar() {

		Utilidades.frmHome.getContentPane().removeAll();
		CadastrarFornecedorView cadastrarFornecedor = new CadastrarFornecedorView();
		Utilidades.frmHome.getContentPane().add(cadastrarFornecedor, BorderLayout.CENTER);
		Utilidades.frmHome.getContentPane().revalidate();	
		
	}
	

	
}
