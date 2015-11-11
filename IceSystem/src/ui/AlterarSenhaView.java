package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import util.Utilidades;
import bo.FuncionarioBO;

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
	private StringBuilder msgErro;
	private FuncionarioBO bo;
	
	{
		bo = new FuncionarioBO();
	}
	
	public AlterarSenhaView(){
		
		this.setTitle("Alterar Senha");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(600, 100, 420, 220);
		this.setLocationRelativeTo(null);
		this.setModal(true);
		
		contentPane = new JPanel();
		this.setContentPane(contentPane);
		contentPane.setBackground(Color.decode("#F0F8FF"));
		contentPane.setLayout(null);
		
		lblSenhaAntiga = new JLabel("Senha Antiga:");
		lblSenhaAntiga.setBounds(20,20,180,25);
		contentPane.add(lblSenhaAntiga);
		
		txtSenhaAntiga = new JPasswordField();
		txtSenhaAntiga.setBounds(190,20,180,25);
		contentPane.add(txtSenhaAntiga);
		
		lblNovaSenha = new JLabel("Nova Senha:");
		lblNovaSenha.setBounds(20,60,120,25);
		contentPane.add(lblNovaSenha);
		
		txtNovaSenha = new JPasswordField();
		txtNovaSenha.setBounds(190,60,180,25);
		contentPane.add(txtNovaSenha);
		
		lblConfNovaSenha = new JLabel("Confirmação Nova Senha:");
		lblConfNovaSenha.setBounds(20,100,160,25);
		contentPane.add(lblConfNovaSenha);
		
		txtConfNovaSenha = new JPasswordField();
		txtConfNovaSenha.setBounds(190,100,180,25);
		contentPane.add(txtConfNovaSenha);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(100,140,90,25);
		btnCancelar.addActionListener(new  ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				AlterarSenhaView.this.dispose();
			}
		});
		contentPane.add(btnCancelar);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(230,140,90,25);
		btnSalvar.addActionListener(new  ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				msgErro = new StringBuilder();
				
				//se a senha estiver errada
				if(!Utilidades.criptografarMd5(new String(txtSenhaAntiga.getPassword())).equals(Utilidades.funcionarioLogado.getSenha())){
					
					msgErro.append("Senha antiga incorreta!");
				}
				//se as senhas novas não baterem
				if(!new String(txtNovaSenha.getPassword()).equals(new String(txtConfNovaSenha.getPassword()))){
					
					msgErro.append("A nova senha e a confirmação da nova senha não são iguais!");
				}
				
				if(msgErro.toString().trim().equals("")){
					
					Utilidades.funcionarioLogado.setSenha(new String(txtNovaSenha.getPassword()));
					
					if(bo.alterarSenhaFuncLogado()){
						
						JOptionPane.showMessageDialog(Utilidades.frmHome, "Senha alterada com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
						AlterarSenhaView.this.dispose();
					}
				}
				else{
					
					JOptionPane.showMessageDialog(Utilidades.frmHome, msgErro.toString(), "Alerta!", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		contentPane.add(btnSalvar);
		
	}	
	
}
