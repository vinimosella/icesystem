package ui.estoque;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.Utilidades;
import vo.ProdutoVO;
import bo.ProdutoBO;

public class CadastrarProdutoView extends JDialog{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNome;
	private JTextField txtNome;
	private JLabel lblSabor;
	private JTextField txtSabor;
	private ProdutoBO bo;
	private ProdutoVO produto;
	private JButton btnSalvar;
	private JButton btnCancelar;
	
	{
		bo = new ProdutoBO();
	}

	public CadastrarProdutoView() {
		setTitle(Utilidades.CADASTRAR_MATERIA_PRIMA);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 259, 200);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setBackground(Color.decode("#F0F8FF"));
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);
				
		lblNome = new JLabel("Nome:");
		lblNome.setBounds(20, 20, 70, 25);
		contentPane.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(100,20,130,25);
		contentPane.add(txtNome);
		
		lblSabor = new JLabel("Sabor:");
		lblSabor.setBounds(20, 60, 70, 25);
		contentPane.add(lblSabor);
		
		txtSabor = new JTextField();
		txtSabor.setBounds(100,60,130,25);
		contentPane.add(txtSabor);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(30,120,90,30);
		btnSalvar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//se não estiver vazio
				if(!txtNome.getText().trim().equals("")){
					
					produto = new ProdutoVO();
					
					produto.setNome(txtNome.getText());
					produto.setSabor(txtSabor.getText());
					bo.cadastrarProduto(produto);
					
				}
				
				Utilidades.frmHome.getContentPane().removeAll();
				ConsultaProdutoView consulta = new ConsultaProdutoView();
				Utilidades.frmHome.add(consulta);
				Utilidades.frmHome.revalidate();
				CadastrarProdutoView.this.dispose();
				
			}
		});
		contentPane.add(btnSalvar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(130,120,90,30);
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				CadastrarProdutoView.this.dispose();
				
			}
		});
		contentPane.add(btnCancelar);
		
	}
	
}
