package ui.financas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public abstract class ConsultaFinancasGenericaView extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JTable table;
	private JLabel lblConsultar;
	private JButton btnDetalhar;
	private JButton btnAtualizar;
	private JButton btnCadastrar;
	
	//� necessario o codUser pra criar o botao da tela de alterar/delete apenas se for userAdmin
	public ConsultaFinancasGenericaView(String lblConsulta){
				
		this.setLayout(null);
		this.setBackground(Color.decode("#F0F8FF"));
		
		lblConsultar = new JLabel(lblConsulta);
		lblConsultar.setBounds(10, 11, 430, 14);
		this.add(lblConsultar);
		
		table = new JTable();
		
		montaTabela(table);
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(100, 480, 91, 23);
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
		
		btnDetalhar = new JButton("Detalhar");
		btnDetalhar.setBounds(150, 510, 91, 23);
		btnDetalhar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(table.getSelectedRow() != -1){
					
					btnDetalhar(table.getSelectedRow());
					
				}
				
			}
		});
		this.add(btnDetalhar);
		
	}
	
	public abstract void btnDetalhar(Integer linhaSelecionada);

	public abstract void montaTabela(JTable table);
		
	public abstract void btnAtualizar(Integer linhaSelecionada);
	
	public abstract void btnCadastrar();
		
}
