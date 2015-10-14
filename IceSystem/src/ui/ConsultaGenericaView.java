package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;

public abstract class ConsultaGenericaView extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JTable table;
	private JLabel lblConsultar;
	private JButton btnDetalhar;
	private JButton btnAtualizar;
	private JButton btnRemover;
	
	//é necessario o codUser pra criar o botao da tela de alterar/delete apenas se for userAdmin
	public ConsultaGenericaView(JFrame frmHome, String lblConsulta){
		
		this.setLayout(null);
		this.setBackground(Color.decode("#F0F8FF"));
		
		lblConsultar = new JLabel(lblConsulta);
		lblConsultar.setBounds(10, 11, 430, 14);
		this.add(lblConsultar);
		
		table = new JTable();
		
		montaTabela(table);
				
		btnDetalhar = new JButton("Detalhar");
		btnDetalhar.setBounds(130, 480, 91, 23);
		btnDetalhar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(table.getSelectedRow() != -1){ // se tiver algo selecionado
					
					btnDetalhar(table.getSelectedRow());
					
				}		
				
			}
		});
		this.add(btnDetalhar);
		
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
		
	}
	
	public abstract void montaTabela(JTable table);
	
	public abstract void btnDetalhar(Integer linhaSelecionada);
	
	public abstract void btnAtualizar(Integer linhaSelecionada);
	
	public abstract void btnRemover(Integer linhaSelecionada);

}
