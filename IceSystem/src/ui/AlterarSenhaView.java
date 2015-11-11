package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class AlterarSenhaView extends JDialog{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblSenhaAntiga;
	private JPasswordField txtSenhaAntiga;
	private JLabel lblNovaSenha;
	private JPasswordField txtNovaSenha;
	private JLabel lblConfNovaSenha;
	private JPasswordField txtConfNovaSenha;
	private JButton btnCancelar;
	private JButton btnSalvar;
	
	public AlterarSenhaView(){
		
		this.setTitle("Alterar Senha");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(600, 100, 460, 460);
		this.setLocationRelativeTo(null);
		this.setModal(true);
		
		contentPane = new JPanel();
		this.setContentPane(contentPane);
		contentPane.setBackground(Color.decode("#F0F8FF"));
		contentPane.setLayout(null);
		
		lblSenhaAntiga = new JLabel("Senha Antiga:");
		lblSenhaAntiga.setBounds(20,20,100,25);
		contentPane.add(lblSenhaAntiga);
		
		txtSenhaAntiga = new JPasswordField();
		txtSenhaAntiga.setBounds(150,20,100,25);
		contentPane.add(txtSenhaAntiga);
		
		lblNovaSenha = new JLabel("Nova Senha:");
		lblNovaSenha.setBounds(20,60,100,25);
		contentPane.add(lblNovaSenha);
		
		txtNovaSenha = new JPasswordField();
		txtNovaSenha.setBounds(150,60,100,25);
		contentPane.add(txtNovaSenha);
		
		lblConfNovaSenha = new JLabel("Confirmação Nova Senha:");
		lblConfNovaSenha.setBounds(20,100,100,25);
		contentPane.add(lblConfNovaSenha);
		
		txtConfNovaSenha = new JPasswordField();
		txtConfNovaSenha.setBounds(150,100,100,25);
		contentPane.add(txtConfNovaSenha);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(100,140,70,25);
		btnCancelar.addActionListener(new  ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				AlterarSenhaView.this.dispose();
			}
		});
		contentPane.add(btnCancelar);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(200,140,70,25);
		btnSalvar.addActionListener(new  ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		contentPane.add(btnSalvar);
		
	}	
	
}
