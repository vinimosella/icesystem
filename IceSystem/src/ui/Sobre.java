package ui;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Sobre extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public Sobre() {
		setTitle("Sobre");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(600, 100, 460, 100);
		this.setBackground(Color.decode("#F0F8FF"));
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setLocationRelativeTo(null);
		
		JLabel lblSobre = new JLabel("Projeto desenvolvido por: Leandro, Thiago e Vinicius. Data: XX/XX/2015");
		lblSobre.setBounds(20, 20, 500, 14);
		contentPane.add(lblSobre);
	}
}
