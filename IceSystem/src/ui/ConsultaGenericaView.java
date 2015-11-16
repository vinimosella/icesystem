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
		btnCadastrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				btnCadastrar();
				
			}
		});
		this.add(btnCadastrar);
		
		btnAtualizar = new JButton("Atualizar");
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
		btnDetalhar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(table.getSelectedRow() != -1){ // se tiver algo selecionado
					
					btnDetalhar(table.getSelectedRow());
					
				}		
				
			}
		});
		this.add(btnDetalhar);
		
		boundsBtn();
		
	}
	
	public void boundsBtn(){
		
		int altura, distanciaInicial, distanciaEntre;
		
		if(btnDetalhar.isVisible() && btnRemover.isVisible()){
			
			altura = 490; distanciaInicial = 100; distanciaEntre = 100;
			
			btnCadastrar.setBounds(distanciaInicial, altura, 91, 23);
			btnDetalhar.setBounds(distanciaInicial+distanciaEntre, altura, 91, 23);
			btnAtualizar.setBounds(distanciaInicial+distanciaEntre*2, altura, 91, 23);
			btnRemover.setBounds(distanciaInicial+distanciaEntre*3, altura, 91, 23);
			
		}
		else if(!btnDetalhar.isVisible()){
			
			altura = 490; distanciaInicial = 150; distanciaEntre = 100;
			
			btnCadastrar.setBounds(distanciaInicial, altura, 91, 23);
			btnAtualizar.setBounds(distanciaInicial+distanciaEntre, altura, 91, 23);
			btnRemover.setBounds(distanciaInicial+distanciaEntre*2, altura, 91, 23);
			
		}
		else if(!btnRemover.isVisible()){
			
			altura = 490; distanciaInicial = 150; distanciaEntre = 100;
			
			btnCadastrar.setBounds(distanciaInicial, altura, 91, 23);
			btnDetalhar.setBounds(distanciaInicial+distanciaEntre, altura, 91, 23);
			btnAtualizar.setBounds(distanciaInicial+distanciaEntre*2, altura, 91, 23);
			
		}
		
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

	public JButton getBtnCadastrar() {
		return btnCadastrar;
	}

	public void setBtnCadastrar(JButton btnCadastrar) {
		this.btnCadastrar = btnCadastrar;
	}
	
	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}
	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public DefaultTableModel getDtm() {
		return dtm;
	}

	public void setDtm(DefaultTableModel dtm) {
		this.dtm = dtm;
	}

}
