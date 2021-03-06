package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import util.Utilidades;

public class HomeView {
	
	private JMenuBar menuBar;
	private JMenu mnArquivo;
	private JMenuItem mntmSobre;
	private JMenuItem mntmSair;
	private ImageIcon img;
	private JLabel labelImg;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new HomeView();
					Utilidades.frmHome.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public HomeView(){
		initialize();
	}
	
	private void initialize(){
		
		Utilidades.frmHome = new JFrame();
		Utilidades.frmHome.setTitle("IceSystem");
		Utilidades.frmHome.setBounds(400, 100, 600, 600);
		Utilidades.frmHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Utilidades.frmHome.setResizable(false);
		Utilidades.frmHome.getContentPane().setBackground(Color.decode("#F0F8FF"));
		Utilidades.frmHome.setLocationRelativeTo(null);
		
		img = new ImageIcon(getClass().getResource("/img/img.jpg"));
		labelImg = new JLabel(img);
		labelImg.setSize(200, 200);
		Utilidades.frmHome.add(labelImg);
		
		menuBar = new JMenuBar();
		Utilidades.frmHome.setJMenuBar(menuBar);
		
		mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);
		
		mntmSobre = new JMenuItem("Sobre");
		mntmSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		mnArquivo.add(mntmSobre);
		
		mnArquivo.addSeparator();
		
		//ITEM SAIR
		mntmSair = new JMenuItem("Sair do Sistema");
		mntmSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		mnArquivo.add(mntmSair);
		
		Utilidades.frmHome.getContentPane().removeAll();
		LoginView loginView = new LoginView();
		Utilidades.frmHome.getContentPane().add(loginView,BorderLayout.CENTER);
		Utilidades.frmHome.getContentPane().revalidate();
		
	}
	
}
