package ui.funcionario;

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
import vo.FuncionarioVO;
import bo.FuncionarioBO;

public class ManterFuncionarioView extends ManterGenericoView{
	
	private static final long serialVersionUID = 1L;
	private DefaultTableModel dtm;
	private FuncionarioVO funcionario;
	private List<FuncionarioVO> listaFuncionarios;
	private FuncionarioBO bo;
	private JLabel lblNome;
	private JTextField txtNome;
	private JLabel lblCargo;
	private JTextField txtCargo;
	private JButton btnFiltrar;

	public ManterFuncionarioView() {
		super(Utilidades.CONSULTA_FUNCIONARIOS);
		super.getScrollPane().setBounds(20, 70, 550, 400);
		super.boundsBtn(500);
		
		lblNome = new JLabel("Nome:");
		lblNome.setBounds(20,40,50,20);
		this.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(70,40,130,20);
		this.add(txtNome);
		
		lblCargo = new JLabel("Cargo:");
		lblCargo.setBounds(230,40,70,20);
		this.add(lblCargo);
		
		txtCargo = new JTextField();
		txtCargo.setBounds(280,40,130,20);
		this.add(txtCargo);
		
		btnFiltrar = new JButton("Filtrar");
		btnFiltrar.setBounds(430,40,70,20);
		btnFiltrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				listaFuncionarios = bo.filtrar(txtNome.getText(), txtCargo.getText());
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
					"ID", "Nome", "CPF", "RG", "Cargo",
				}				
		){
		
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				
			   return false;
			}
			
		};
		
		if(bo==null){
			bo = new FuncionarioBO();
		}
		
		if(listaFuncionarios==null){
			listaFuncionarios = bo.consultarFuncionarios();
		}
		
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
		if(JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o funcionário "+listaFuncionarios.get(linhaSelecionada).getNome()+" ?", "Exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
			
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
