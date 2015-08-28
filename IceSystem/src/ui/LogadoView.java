package ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LogadoView extends JPanel{

	private static final long serialVersionUID = 1L;
	private JLabel labelWelcome;
	
	public LogadoView(JFrame frmHome){
		
		this.setLayout(null);
		this.setBackground(Color.decode("#F0F8FF"));
		
		labelWelcome = new JLabel();
		labelWelcome.setText("Bem-Vindo!");
		labelWelcome.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
		labelWelcome.setForeground(Color.black);
		labelWelcome.setBounds(175,250,250,50);
		this.add(labelWelcome);
		
	}
	
}
