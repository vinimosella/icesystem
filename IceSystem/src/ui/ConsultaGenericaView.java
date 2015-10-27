package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public abstract class ConsultaGenericaView extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JTable table;
	private JLabel lblConsultar;
	private JButton btnCadastrar;
	private JButton btnDetalhar;
	private JButton btnAtualizar;
	private JButton btnRemover;
	private JScrollPane scrollPane;
	private DefaultTableModel dtm;

	//é necessario o codUser pra criar o botao da tela de alterar/delete apenas se for userAdmin
	public ConsultaGenericaView(String lblConsulta){
		
		this.setLayout(null);
		this.setBackground(Color.decode("#F0F8FF"));
		
		lblConsultar = new JLabel(lblConsulta);
		lblConsultar.setBounds(10, 11, 430, 14);
		this.add(lblConsultar);
		
		table = new JTable();
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 50, 550, 400);
		this.add(scrollPane);
		carregaDtm(table, dtm);
		scrollPane.setViewportView(table);
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(130, 480, 91, 23);
		btnCadastrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				btnCadastrar();
				
			}
		});
		this.add(btnCadastrar);
		
		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setBounds(231, 480, 91, 23);
		btnAtualizar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(table.getSelectedRow() != -1){
					
					btnAtualizar(table.getSelectedRow());
					
				}
				
			}
			
		});
		this.add(btnAtualizar);
		
		btnRemover = new JButton("Remover");
		btnRemover.setBounds(332, 480, 91, 23);
		btnRemover.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(table.getSelectedRow() != -1){
						
					//mudar texto dos botões "yes" e "no"
					UIManager.put("OptionPane.noButtonText", "Não");  
					UIManager.put("OptionPane.yesButtonText", "Sim"); 
					
					btnRemover(table.getSelectedRow());
					
				}
				
			}
		});
		this.add(btnRemover);
		
		btnDetalhar = new JButton("Detalhar");
		btnDetalhar.setBounds(250, 510, 91, 23);
		btnDetalhar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(table.getSelectedRow() != -1){ // se tiver algo selecionado
					
					btnDetalhar(table.getSelectedRow());
					
				}		
				
			}
		});
		this.add(btnDetalhar);
		
	}
	
	public abstract void btnCadastrar();

	public abstract void carregaDtm(JTable table, DefaultTableModel dtm);
	
	public abstract void btnDetalhar(Integer linhaSelecionada);
	
	public abstract void btnAtualizar(Integer linhaSelecionada);
	
	public abstract void btnRemover(Integer linhaSelecionada);
	
	public JButton getBtnDetalhar() {
		return btnDetalhar;
	}

	public void setBtnDetalhar(JButton btnDetalhar) {
		this.btnDetalhar = btnDetalhar;
	}
	public JButton getBtnAtualizar() {
		return btnAtualizar;
	}

	public void setBtnAtualizar(JButton btnAtualizar) {
		this.btnAtualizar = btnAtualizar;
	}

	public JButton getBtnRemover() {
		return btnRemover;
	}

	public void setBtnRemover(JButton btnRemover) {
		this.btnRemover = btnRemover;
	}

}
