package ui;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import vo.EmailVO;
import vo.EnderecoVO;
import vo.TelefoneVO;

public abstract class DetalheGenericoView extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblIdentificador;
	private List<EmailVO> listaEmails;
	private List<TelefoneVO> listaTelefones;
	private EnderecoVO endereco;
	private JScrollPane scrollPaneEmail;
	private JTable tableEmail;
	private JScrollPane scrollPaneTelefone;
	private JTable tableTelefone;
	private DefaultTableModel dtm;
	private JLabel lblEstado;
	private JLabel lblCidade;
	private JLabel lblCep;
	private JLabel lblBairro;
	private JLabel lblLogradouro;
	
	public DetalheGenericoView(String title, String identificador, Object objeto){
		
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(600, 100, 460, 460);
		this.setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		this.setContentPane(contentPane);
		contentPane.setBackground(Color.decode("#F0F8FF"));
		contentPane.setLayout(null);
		
		lblIdentificador = new JLabel(identificador);
		lblIdentificador.setBounds(10, 11, 430, 14);
		contentPane.add(lblIdentificador);	
		
		listaEmails = setEmails(objeto);
		listaTelefones = setTelefones();
		endereco = setEndereco();
		
		detalheEmail();
		detalheTelefone();
		detalheEndereco();
		
	}

	public abstract List<EmailVO> setEmails(Object objeto);
	
	public abstract List<TelefoneVO> setTelefones();
	
	public abstract EnderecoVO setEndereco();
	
	private void detalheEmail(){
				
		scrollPaneEmail = new JScrollPane();
		scrollPaneEmail.setBounds(20, 31, 150, 169);
		contentPane.add(scrollPaneEmail);
		
		tableEmail = new JTable();
		dtm = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"E-mail"
				}
		){
			
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
			
			   return false;
			}
			
		};
		
		Iterator<EmailVO> it = listaEmails.iterator();
		EmailVO email;
		
		while(it.hasNext()){
				
			email = it.next();
				
			dtm.addRow(new Object[] {
					email.getEmail()
			});
			
		}
		
		tableEmail.setModel(dtm);
		scrollPaneEmail.setViewportView(tableEmail);
		
	}
	
	private void detalheTelefone(){
				
		scrollPaneTelefone = new JScrollPane();
		scrollPaneTelefone.setBounds(190, 31, 150, 169);
		contentPane.add(scrollPaneTelefone);
		
		tableTelefone = new JTable();
		dtm = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Telefone"
				}
		){
			
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
			
			   return false;
			}
			
		};
		
		Iterator<TelefoneVO> it = listaTelefones.iterator();
		TelefoneVO telefone;
		
		while(it.hasNext()){
				
			telefone = it.next();
				
			dtm.addRow(new Object[] {
					"(" + telefone.getDdd() + ") " + telefone.getNumero()
			});
			
		}
		
		tableTelefone.setModel(dtm);
		scrollPaneTelefone.setViewportView(tableTelefone);
		
	}
	
	private void detalheEndereco() {
		
		lblEstado = new JLabel("Estado: " + endereco.getCidade().getEstado().getNome());
		lblEstado.setBounds(40, 200, 150, 60);
		contentPane.add(lblEstado);
			
		lblCidade = new JLabel("Cidade: "+ endereco.getCidade().getNome());
		lblCidade.setBounds(40,240,150,60);
		contentPane.add(lblCidade);
			
		lblCep = new JLabel("CEP: " + endereco.getCep());
		lblCep.setBounds(40, 280, 300, 60);
		contentPane.add(lblCep);
			
		lblBairro = new JLabel("Bairro: " + endereco.getBairro());
		lblBairro.setBounds(40, 320, 300, 60);
		contentPane.add(lblBairro);
			
		lblLogradouro = new JLabel("Logradouro: " + endereco.getLogradouro() + ", " + endereco.getComplemento() + " - " + endereco.getNumero());
		lblLogradouro.setBounds(40, 360, 300, 60);
		contentPane.add(lblLogradouro);			
		
	}
	
}
