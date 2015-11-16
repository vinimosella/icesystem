package ui.funcionario;

import java.awt.BorderLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ui.ManterGenericoView;
import util.Utilidades;
import vo.FuncionarioVO;
import bo.FuncionarioBO;

public class ManterFuncionarioView extends ManterGenericoView{
	
	private static final long serialVersionUID = 1L;
	private DefaultTableModel dtm;
	private FuncionarioVO funcionario;
	private List<FuncionarioVO> listaFuncionarios;
	
	private FuncionarioBO bo;

	public ManterFuncionarioView() {
		super(Utilidades.CONSULTA_FUNCIONARIOS);

	}

	@Override
	public void carregaDtm(JTable table, DefaultTableModel dtm) {
				
		dtm = new DefaultTableModel(
				
				new Object[][] {
						
				},
				new String[] {
					"ID", "Nome", "CPF", "RG", "Cargo",
				}				
		){
		
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				
			   return false;
			}
			
		};
		
		bo = new FuncionarioBO();
		
		listaFuncionarios = bo.consultarFuncionarios();

		Iterator<FuncionarioVO> it = listaFuncionarios.iterator();
			
		while(it.hasNext()){
				
			funcionario = it.next();
				
			dtm.addRow(new Object[] {
					funcionario.getIdFuncionario(),
					funcionario.getNome(),
					funcionario.getCpf(),
					funcionario.getRg(),
					funcionario.getCargo().getFuncao()
			});
			
		}			
		
		this.dtm = dtm;
		table.setModel(dtm);		
	}

	@Override
	public void btnDetalhar(Integer linhaSelecionada) {
		
		bo = new FuncionarioBO();
		
		DetalharFuncionarioView detalhe = new DetalharFuncionarioView(bo.detalharFuncionario(listaFuncionarios.get(linhaSelecionada)));
		detalhe.setVisible(true);
		
	}

	@Override
	public void btnAtualizar(Integer linhaSelecionada) {

		bo = new FuncionarioBO();
		Utilidades.frmHome.getContentPane().removeAll();
		AlterarFuncionarioView atualizar = new AlterarFuncionarioView(bo.detalharFuncionario(listaFuncionarios.get(linhaSelecionada)));
		Utilidades.frmHome.getContentPane().add(atualizar,BorderLayout.CENTER);
		Utilidades.frmHome.getContentPane().revalidate();
		
	}

	@Override
	public void btnRemover(Integer linhaSelecionada) {

		//se clicar em sim, vai excluir
		if(JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o funcion�rio "+listaFuncionarios.get(linhaSelecionada).getNome()+" ?", "Exclus�o", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
			
			//se excluir com sucesso...
			if(bo.excluirFuncionario(listaFuncionarios.get(linhaSelecionada))){
				
				//remove da lista e da tabela
				listaFuncionarios.remove(linhaSelecionada);						
				dtm.removeRow(linhaSelecionada);
				
			}
			
		}
		
	}

	@Override
	public void btnCadastrar() {

		Utilidades.frmHome.getContentPane().removeAll();
		CadastrarFuncionarioView cadastrarFuncionario = new CadastrarFuncionarioView();
		Utilidades.frmHome.getContentPane().add(cadastrarFuncionario, BorderLayout.CENTER);
		Utilidades.frmHome.getContentPane().revalidate();
		
	}
	
}