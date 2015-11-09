package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LogadoView extends JPanel{

	private static final long serialVersionUID = 1L;
	private JLabel labelWelcome;
	private ImageIcon fundo = new ImageIcon(getClass().getResource("/img/img.jpg"));  
	
	public LogadoView(){
		
		this.setLayout(null);
		this.setBackground(Color.decode("#F0F8FF"));
		
		labelWelcome = new JLabel();
		labelWelcome.setText("Bem-Vindo!");
		labelWelcome.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
		labelWelcome.setForeground(Color.BLACK);
		labelWelcome.setBounds(180,30,250,50);
		this.add(labelWelcome);
		
	}
	
	@Override  
	protected void paintComponent(Graphics g) {  
	    super.paintComponent(g);  
	    Graphics2D g2d = (Graphics2D) g.create();  
	    g2d.drawImage(fundo.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);  
	    g2d.dispose();  
	}
	
}
