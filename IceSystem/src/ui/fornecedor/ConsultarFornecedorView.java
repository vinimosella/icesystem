package ui.fornecedor;

import java.awt.BorderLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ui.ConsultaGenericaView;
import util.Utilidades;
import vo.FornecedorVO;
import bo.FornecedorBO;

public class ConsultarFornecedorView extends ConsultaGenericaView{

	private DefaultTableModel dtm;
	private FornecedorVO fornecedor;
	private List<FornecedorVO> listaFornecedores;
	
	private FornecedorBO bo;
	
	public ConsultarFornecedorView(String lblConsulta) {
		super(lblConsulta);

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
		
		Utilidades.frmHome.getContentPane().removeAll();
		AtualizarFornecedorView atualizar = new AtualizarFornecedorView(listaFornecedores.get(linhaSelecionada));
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
	

	
}
