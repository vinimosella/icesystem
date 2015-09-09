package ui.fornecedor;

import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ui.ConsultaGenericaView;
import vo.FornecedorVO;
import bo.FornecedorBO;

public class ConsultarFornecedorView extends ConsultaGenericaView{

	private JFrame frmHome;
	private Byte codUser;
	private JScrollPane scrollPane;
	private DefaultTableModel dtm;
	private FornecedorVO fornecedor;
	private List<FornecedorVO> listaFornecedores;
	
	private FornecedorBO bo;
	
	public ConsultarFornecedorView(JFrame frmHome, Byte codUser, String lblConsulta) {
		super(frmHome, codUser, lblConsulta);

		this.frmHome = frmHome;
		this.codUser = codUser;
	}

	private static final long serialVersionUID = 1L;

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
		
		bo = new FornecedorBO();
		
		listaFornecedores = bo.consultarFornecedores();

		Iterator<FornecedorVO> it = listaFornecedores.iterator();
			
		while(it.hasNext()){
				
			fornecedor = it.next();
				
			dtm.addRow(new Object[] {
					fornecedor.getIdPessoaJuridica(),
					fornecedor.getRazaoSocial(),
					fornecedor.getCnpj(),
			});
			
		}			
		
		table.setModel(dtm);
		scrollPane.setViewportView(table);
		
	}

	@Override
	public void btnDetalhar(Integer linhaSelecionada) {
		
		/*DetalharFornecedorView detalhe = new DetalharFornecedorView(listaFornecedores.get(linhaSelecionada));
		detalhe.setVisible(true);*/
		
	}

	@Override
	public void btnAtualizar(Integer linhaSelecionada) {
		
		/*ConsultarFornecedorView.this.frmHome.getContentPane().removeAll();
		AtualizarFornecedorView atualizar = new AtualizarFornecedorView(ConsultarFornecedorView.this.frmHome, listaFornecedores.get(linhaSelecionada),ConsultarFuncionarioView.this.codUser);
		ConsultarFornecedorView.this.frmHome.getContentPane().add(atualizar,BorderLayout.CENTER);
		ConsultarFornecedorView.this.frmHome.getContentPane().revalidate();*/
		
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
	

	
}
