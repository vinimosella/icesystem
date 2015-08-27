package ui.cliente;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import vo.CidadeVO;
import vo.EstadoVO;
import bo.CadastarClienteBO;

public class CadastrarClienteView extends JPanel{

	private static final long serialVersionUID = 1L;
	private JLabel labelCnpj;
	private JTextField txtCnpj;
	private JLabel labelRazaoSocial;
	private JTextField txtRazaoSocial;
	private JLabel labelTelefone;
	private JTextField txtTelefone;
	private JLabel labelEmail;
	private JTextField txtEmail;
	private JLabel labelEstado;
	private JComboBox<String> comboEstado;
	private JLabel labelCidade;
	private JComboBox<String> comboCidade;
	private JLabel labelLogradouro;
	private JTextField txtLogradouro;
	private JLabel labelNumero;
	private JTextField txtNumero;
	private JLabel labelComplemento;
	private JTextField txtComplemento;
	private JLabel labelBairro;
	private JTextField txtBairro;
	private JLabel labelCep;
	private JTextField txtCep;

	
	private CadastarClienteBO bo;
	
	private List<EstadoVO> listaEstados;
	private List<CidadeVO> listaCidades;
	
	{
		bo = new CadastarClienteBO();
	}
	
	public CadastrarClienteView(JFrame frame){
				
		this.setLayout(null);
		this.setBackground(Color.decode("#F0F8FF"));
		
		labelCnpj = new JLabel();
		labelCnpj.setText("CNPJ:");
		labelCnpj.setBounds(20,5,120,20);
		this.add(labelCnpj);
		
		txtCnpj = new JTextField();
		txtCnpj.setBounds(300,7,200,20);
		this.add(txtCnpj);
		
		labelRazaoSocial = new JLabel();
		labelRazaoSocial.setText("Razão Social:");
		labelRazaoSocial.setBounds(20,30,120,20);
		this.add(labelRazaoSocial);
		
		txtRazaoSocial = new JTextField();
		txtRazaoSocial.setBounds(300,32,200,20);
		this.add(txtRazaoSocial);
		
		labelTelefone = new JLabel();
		labelTelefone.setText("Telefone:");
		labelTelefone.setBounds(20,55,120,20);
		this.add(labelTelefone);
		
		txtTelefone = new JTextField();
		txtTelefone.setBounds(300,57,200,20);
		this.add(txtTelefone);
		
		labelEmail = new JLabel();
		labelEmail.setText("Email:");
		labelEmail.setBounds(20,80,120,20);
		this.add(labelEmail);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(300,82,200,20);
		this.add(txtEmail);
		
		labelEstado = new JLabel();
		labelEstado.setText("Estado:");
		labelEstado.setBounds(20,105,120,20);
		this.add(labelEstado);
		
		comboEstado = new JComboBox<String>();
		comboEstado.setBounds(300,107,200,20);
		comboEstado.addItem("Selecione");
		listaEstados = bo.buscaEstados();
		Iterator<EstadoVO> it = (Iterator<EstadoVO>) listaEstados.iterator();
		EstadoVO estado;
		
		while(it.hasNext()){
			estado = (EstadoVO) it.next();
			comboEstado.addItem(estado.getNome());
		}
		
		comboEstado.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				comboCidade.removeAllItems();
				comboCidade.addItem("Selecione");
				
				if(comboEstado.getSelectedIndex()!= 0){
					
					listaCidades = bo.buscaCidadesPorEstado(listaEstados.get(comboEstado.getSelectedIndex()-1).getIdEstado());
					comboCidade.setEnabled(true);
					
					Iterator<CidadeVO> it = listaCidades.iterator();
					CidadeVO cidade;
					
					while(it.hasNext()){
						
						cidade = (CidadeVO) it.next();
						comboCidade.addItem(cidade.getNome());
					}
				}
				else{
					comboCidade.setEnabled(false);
				}
				
			}
		});
		this.add(comboEstado);
		
		labelCidade = new JLabel();
		labelCidade.setText("Cidade:");
		labelCidade.setBounds(20,130,120,20);
		this.add(labelCidade);
		
		comboCidade = new JComboBox<String>();
		comboCidade.setBounds(300,132,200,20);
		comboCidade.addItem("Selecione");
		comboCidade.setEnabled(false);
		
		this.add(comboCidade);
		
		labelLogradouro = new JLabel();
		labelLogradouro.setText("Logradouro:");
		labelLogradouro.setBounds(20,155,120,20);
		this.add(labelLogradouro);
		
		txtLogradouro = new JTextField();
		txtLogradouro.setBounds(300,157,200,20);
		this.add(txtLogradouro);
		
		labelNumero = new JLabel();
		labelNumero.setText("Número:");
		labelNumero.setBounds(20,180,120,20);
		this.add(labelNumero);
		
		txtNumero = new JTextField();
		txtNumero.setBounds(300,182,200,20);
		this.add(txtNumero);
		
		labelComplemento = new JLabel();
		labelComplemento.setText("Complemento:");
		labelComplemento.setBounds(20,205,120,20);
		this.add(labelComplemento);
		
		txtComplemento = new JTextField();
		txtComplemento.setBounds(300,207,200,20);
		this.add(txtComplemento);
		
		labelBairro = new JLabel();
		labelBairro.setText("Bairro:");
		labelBairro.setBounds(20,230,120,20);
		this.add(labelBairro);
		
		txtBairro = new JTextField();
		txtBairro.setBounds(300,232,200,20);
		this.add(txtBairro);
		
		labelCep = new JLabel();
		labelCep.setText("CEP:");
		labelCep.setBounds(20,255,120,20);
		this.add(labelCep);
		
		txtCep = new JTextField();
		txtCep.setBounds(300,257,200,20);
		this.add(txtCep);
		
	}
	
}
