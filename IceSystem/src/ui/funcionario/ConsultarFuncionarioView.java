package ui.funcionario;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ConsultarFuncionarioView extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	//é necessario o codUser pra criar o link pra tela de alterar/delete apenas se for userAdmin
	public ConsultarFuncionarioView(JFrame frmHome, Byte codUser){
		
		this.setLayout(null);
		this.setBackground(Color.decode("#F0F8FF"));
		
	}
	
}
