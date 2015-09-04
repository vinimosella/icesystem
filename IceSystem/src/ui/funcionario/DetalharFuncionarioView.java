package ui.funcionario;

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
import vo.FuncionarioVO;
import vo.TelefoneVO;
import bo.FuncionarioBO;

public class DetalharFuncionarioView extends JFrame{

	private static final long serialVersionUID = 1L;
	private FuncionarioVO funcionario;
	private JPanel contentPane;
	private JLabel lblNomeFuncionario;
	private List<EmailVO> listaEmails;
	private List<TelefoneVO> listaTelefones;
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
	private JLabel lblNumero;
	private JLabel lblComplemento;

	
	private FuncionarioBO bo;
	
	{
		bo = new FuncionarioBO();
	}

	public DetalharFuncionarioView(FuncionarioVO funcionario) {
		
		this.funcionario = funcionario;
		
		this.setTitle("Detalhe");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(600, 100, 460, 460);
		this.setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		this.setContentPane(contentPane);
		contentPane.setBackground(Color.decode("#F0F8FF"));
		contentPane.setLayout(null);
		
		lblNomeFuncionario = new JLabel(funcionario.getNome());
		lblNomeFuncionario.setBounds(10, 11, 430, 14);
		contentPane.add(lblNomeFuncionario);		
		
		detalheEmail();
		detalheTelefone();
		detalheEndereco();
	}

	private void detalheEmail(){
		
		listaEmails = bo.detalharEmail(funcionario.getIdFuncionario());
		
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
		);
		
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
		
		listaTelefones = bo.detalharTelefone(funcionario.getIdFuncionario());
		
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
		);
		
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
		
		lblEstado = new JLabel("Estado: " + funcionario.getEndereco().getCidade().getEstado().getNome());
		lblEstado.setBounds(40, 200, 150, 60);
		contentPane.add(lblEstado);
			
		lblCidade = new JLabel("Cidade: "+ funcionario.getEndereco().getCidade().getNome());
		lblCidade.setBounds(230,200,150,60);
		contentPane.add(lblCidade);
			
		lblCep = new JLabel("CEP: " + funcionario.getEndereco().getCep());
		lblCep.setBounds(40, 240, 300, 60);
		contentPane.add(lblCep);
			
		lblBairro = new JLabel("Bairro: " + funcionario.getEndereco().getBairro());
		lblBairro.setBounds(40, 280, 300, 60);
		contentPane.add(lblBairro);
			
		lblLogradouro = new JLabel("Logradouro: " + funcionario.getEndereco().getLogradouro());
		lblLogradouro.setBounds(40, 320, 300, 60);
		contentPane.add(lblLogradouro);
			
		lblComplemento = new JLabel("Complemento: " + funcionario.getEndereco().getComplemento());
		lblComplemento.setBounds(40, 360, 150, 60);
		contentPane.add(lblComplemento);
		
		lblNumero = new JLabel("Número: " + funcionario.getEndereco().getNumero());
		lblNumero.setBounds(230, 360, 150, 60);
		contentPane.add(lblNumero);
		
	}
	
}
