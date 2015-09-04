package ui.funcionario;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import ui.LoginView;
import vo.FuncionarioVO;
import bo.FuncionarioBO;

public class ConsultarFuncionarioView extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private JFrame frmHome;
	private JTable table;
	private JLabel lblConsultar;
	private JScrollPane scrollPane;
	private FuncionarioVO funcionario;
	private List<FuncionarioVO> listaFuncionarios;
	private DefaultTableModel dtm;
	private JButton btnDetalhar;
	private JButton btnAtualizar;
	private JButton btnRemover;
	
	private FuncionarioBO bo;
	
	{
		bo = new FuncionarioBO();
	}
	
	//é necessario o codUser pra criar o botao da tela de alterar/delete apenas se for userAdmin
	public ConsultarFuncionarioView(final JFrame frmHome, final Byte codUser){
		
		this.frmHome = frmHome;
		
		this.setLayout(null);
		this.setBackground(Color.decode("#F0F8FF"));
		
		lblConsultar = new JLabel("Consultar");
		lblConsultar.setBounds(10, 11, 430, 14);
		this.add(lblConsultar);
		
		tabelaFuncionarios();
				
		btnDetalhar = new JButton("Detalhar");
		btnDetalhar.setBounds(130, 211, 91, 23);
		btnDetalhar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(table.getSelectedRow() != -1){
					DetalharFuncionarioView detalhe = new DetalharFuncionarioView(listaFuncionarios.get(table.getSelectedRow()));
					detalhe.setVisible(true);
				}		
				
			}
		});
		this.add(btnDetalhar);
		
		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setBounds(231, 211, 91, 23);
		btnAtualizar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(table.getSelectedRow() != -1){
					
				}
				
			}
			
		});
		this.add(btnAtualizar);
		
		btnRemover = new JButton("Remover");
		btnRemover.setBounds(332, 211, 91, 23);
		btnRemover.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(table.getSelectedRow() != -1){ // se tiver algo selecionado
						
					//mudar texto dos botões "yes" e "no"
					UIManager.put("OptionPane.noButtonText", "Não");  
					UIManager.put("OptionPane.yesButtonText", "Sim"); 
					
					//se clicar em sim, vai excluir
					if(JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o funcionário "+listaFuncionarios.get(table.getSelectedRow()).getNome()+" ?", "Exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
						
						//se excluir com sucesso...
						if(bo.excluirFuncionario(listaFuncionarios.get(table.getSelectedRow()).getIdFuncionario())){
							
							//remove da lista e da tabela
							listaFuncionarios.remove(table.getSelectedRow());						
							dtm.removeRow(table.getSelectedRow());
							
						}
						
					}
					
				}
				
			}
		});
		this.add(btnRemover);
		
	}
	
	public void tabelaFuncionarios(){
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 31, 550, 169);
		this.add(scrollPane);
		
		table = new JTable();
		
		dtm = new DefaultTableModel(
				new Object[][] {
						
				},
				new String[] {
					"ID", "Nome", "CPF", "RG", "Cargo",
				}				
		);
		
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
		
		table.setModel(dtm);
		scrollPane.setViewportView(table);
		
	}
	
}
