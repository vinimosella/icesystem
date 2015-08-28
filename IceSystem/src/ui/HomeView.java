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

public class HomeView {
	
	private JFrame frmHome;
	private JMenuBar menuBar;
	private JMenu mnArquivo;
	private JMenuItem mntmLogin;
	private JMenuItem mntmSobre;
	private ImageIcon img;
	private JLabel labelImg;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeView window = new HomeView();
					window.frmHome.setVisible(true);
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
		
		frmHome = new JFrame();
		frmHome.setTitle("IceSystem");
		frmHome.setBounds(400, 100, 600, 600);
		frmHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHome.setResizable(false);
		frmHome.getContentPane().setBackground(Color.decode("#F0F8FF"));
		frmHome.setLocationRelativeTo(null);
		
		img = new ImageIcon(getClass().getResource("/img/img.jpg"));
		labelImg = new JLabel(img);
		labelImg.setSize(200, 200);
		frmHome.add(labelImg);
		
		menuBar = new JMenuBar();
		frmHome.setJMenuBar(menuBar);
		
		mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);
		
		mntmLogin = new JMenuItem("Login");
		mntmLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frmHome.getContentPane().removeAll();
				LoginView loginView = new LoginView(frmHome);
				frmHome.getContentPane().add(loginView,BorderLayout.CENTER);
				frmHome.getContentPane().revalidate();
			}
		});
		mnArquivo.add(mntmLogin);
		
		mntmSobre = new JMenuItem("Sobre");
		mntmSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		mnArquivo.add(mntmSobre);
		
		
		
	}
	
}
