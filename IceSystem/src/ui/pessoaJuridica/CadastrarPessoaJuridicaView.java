package ui.pessoaJuridica;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.Utilidades;
import vo.CidadeVO;
import vo.EmailVO;
import vo.EnderecoVO;
import vo.EstadoVO;
import vo.PessoaJuridicaVO;
import vo.TelefoneVO;
import bo.PessoaJuridicaBO;

public abstract class CadastrarPessoaJuridicaView extends JPanel{

	private static final long serialVersionUID = 1L;
	private JLabel lblNomePagina;
	private JLabel lblRazaoSocial;
	private JTextField txtRazaoSocial;
	private JLabel lblCnpj;
	private JTextField txtCnpj;
	private JLabel lblTelefone;
	private JComboBox<Integer> comboTelefone;
	private JTextField txtTelefone;
	private JButton btnAtualizarTelefone;
	private JButton btnAdicionarTelefone;
	private JButton btnRemoverTelefone;
	private JLabel lblEmail;
	private JComboBox<Integer> comboEmail; 
	private JTextField txtEmail;
	private JButton btnAtualizarEmail;
	private JButton btnAdicionarEmail;
	private JButton btnRemoverEmail;
	private JLabel lblEstado;
	private JComboBox<String> comboEstado;
	private JLabel lblCidade;
	private JComboBox<String> comboCidade;
	private JLabel lblLogradouro;
	private JTextField txtLogradouro;
	private JLabel lblNumero;
	private JTextField txtNumero;
	private JLabel lblComplemento;
	private JTextField txtComplemento;
	private JLabel lblBairro;
	private JTextField txtBairro;
	private JLabel lblCep;
	private JTextField txtCep;
	private List<TelefoneVO> listaTelefones;
	private List<EmailVO> listaEmails;
	private Integer contadorTelefones;
	private Integer contadorEmails;
	private JButton btnCadastrar;
	
	private PessoaJuridicaVO pj;
	private TelefoneVO telefone;
	private EmailVO email;
	private EnderecoVO endereco;
	private CidadeVO cidade;
	private EstadoVO estado;
	private PessoaJuridicaBO bo;
	private Iterator<?> it;
	
	private List<EstadoVO> listaEstados;
	private List<CidadeVO> listaCidades;
	
	{
		bo = new PessoaJuridicaBO();
		contadorTelefones = 0;
		contadorEmails = 0;
		listaTelefones = new ArrayList<TelefoneVO>();
		listaEmails = new ArrayList<EmailVO>();
	}
	
	public CadastrarPessoaJuridicaView(String nomePag){
		
		this.setLayout(null);
		this.setBackground(Color.decode("#F0F8FF"));
		
		lblNomePagina = new JLabel();
		lblNomePagina.setText(nomePag);
		this.add(lblNomePagina);
		
		lblRazaoSocial = new JLabel();
		lblRazaoSocial.setText("Razão Social:");
		this.add(lblRazaoSocial);
		
		txtRazaoSocial = new JTextField();
		this.add(txtRazaoSocial);
		
		lblCnpj = new JLabel();
		lblCnpj.setText("CNPJ:");
		this.add(lblCnpj);
		
		txtCnpj = new JTextField();
		this.add(txtCnpj);
		
		lblTelefone = new JLabel();
		lblTelefone.setText("Telefone:");
		this.add(lblTelefone);
		
		//armazena a quantidade de telefones que tem, alterará o valor do label de telefone para o numero corrente
		comboTelefone = new JComboBox<Integer>();
				
		//Muda o valor do textField quando muda o 'id' do telefone do combobox
		comboTelefone.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {

				txtTelefone.setText("");
				
				if(comboTelefone.getSelectedIndex()!=-1){
					txtTelefone.setText(listaTelefones.get(comboTelefone.getSelectedIndex()).getDdd()
						+ listaTelefones.get(comboTelefone.getSelectedIndex()).getNumero());
				}
				
			}
		});
		this.add(comboTelefone);
		comboTelefone.setEnabled(false);
		
		txtTelefone = new JTextField();
		this.add(txtTelefone);
		
		btnAtualizarTelefone = new JButton(new ImageIcon(getClass().getResource("/img/update.png")));
		btnAtualizarTelefone.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if(listaTelefones.size()==0){
					return;
				}
				
				if(!bo.isTelefoneExistenteLista(txtTelefone.getText(), listaTelefones)){
				
					listaTelefones.get(comboTelefone.getSelectedIndex()).setDdd(txtTelefone.getText().substring(0,2));
					listaTelefones.get(comboTelefone.getSelectedIndex()).setNumero(txtTelefone.getText().substring(2));
					
					comboTelefone.removeAllItems();
					carregaTelefone();
					comboTelefone.setSelectedIndex(listaTelefones.size()-1);
					txtTelefone.setText("");
				}				
				else{
					
					JOptionPane.showMessageDialog(Utilidades.frmHome, "   Telefone não modificado ou já cadastrado!", "Alerta!", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		this.add(btnAtualizarTelefone);
		btnAtualizarTelefone.setEnabled(false);

		btnAdicionarTelefone = new JButton(new ImageIcon(getClass().getResource("/img/confirm.png")));
		btnAdicionarTelefone.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//apenas um deles é verificado pois a inativação é feita pra todos ao mesmo tempo
				if(!comboTelefone.isEnabled()){
					
					comboTelefone.setEnabled(true);
					btnRemoverTelefone.setEnabled(true);
					btnAtualizarTelefone.setEnabled(true);
					
				}
				
				//verifica se ja é existente, se não, adiciona
				if(!bo.isTelefoneExistenteLista(txtTelefone.getText(), listaTelefones)){
					
					telefone = new TelefoneVO();
					telefone.setDdd(txtTelefone.getText().substring(0, 2));
					telefone.setNumero(txtTelefone.getText().substring(2));
					listaTelefones.add(telefone);
					comboTelefone.addItem(++contadorTelefones);
					comboTelefone.setSelectedItem(contadorTelefones);
					
					//limpa o txt
					txtTelefone.setText("");
					
					//bloqueia pra não passar de 5
					if(listaTelefones.size()==5){
						
						btnAdicionarTelefone.setEnabled(false);
					}
					
				}
				else{
					
					JOptionPane.showMessageDialog(Utilidades.frmHome, "   Telefone já cadastrado!", "Alerta!", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		this.add(btnAdicionarTelefone);
		
		btnRemoverTelefone = new JButton(new ImageIcon(getClass().getResource("/img/delete.png")));
		btnRemoverTelefone.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
								
				//se ja não houver telefones pra remover...
				if(listaTelefones.size() == 0){
					
					return;
				}
				
				listaTelefones.remove(comboTelefone.getSelectedIndex());
				comboTelefone.removeAllItems();
				carregaTelefone();
				
				//se o botao estava bloqueado e agora é possivel adicionar mais, o botão é habilitado
				if(!btnAdicionarTelefone.isEnabled() && listaTelefones.size()<5){
					
					btnAdicionarTelefone.setEnabled(true);
				}
								
				//se não for o ultimo a ser removido
				if(listaTelefones.size() != 0){
					
					comboTelefone.setSelectedIndex(0);
					
				}
				else{
					
					comboTelefone.setEnabled(false);
					btnRemoverTelefone.setEnabled(false);
					btnAtualizarTelefone.setEnabled(false);
					txtTelefone.requestFocus();
					
				}
				
				txtTelefone.setText("");

			}
		});
		this.add(btnRemoverTelefone);
		btnRemoverTelefone.setEnabled(false);
		
		lblEmail = new JLabel();
		lblEmail.setText("Email:");
		this.add(lblEmail);
		
		txtEmail = new JTextField();
		this.add(txtEmail);
		
		//armazena a quantidade de emails que tem, alterará o valor do label de email para o numero corrente
		comboEmail = new JComboBox<Integer>();
				
		//Muda o valor do textField quando muda o 'id' do email do combobox
		comboEmail.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				txtEmail.setText("");
				
				if(comboEmail.getSelectedIndex()!=-1){
					txtEmail.setText(listaEmails.get(comboEmail.getSelectedIndex()).getEmail());
				}
				
			}
		});
		this.add(comboEmail);
		comboEmail.setEnabled(false);
		
		btnAtualizarEmail = new JButton(new ImageIcon(getClass().getResource("/img/update.png")));
		btnAtualizarEmail.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if(listaEmails.size()==0){
					return;
				}
				
				if(!bo.isEmailExistenteLista(txtEmail.getText(), listaEmails)){
				
					listaEmails.get(comboEmail.getSelectedIndex()).setEmail(txtEmail.getText());;
					
					comboEmail.removeAllItems();
					carregaEmail();
					comboEmail.setSelectedIndex(listaEmails.size()-1);
					txtEmail.setText("");
				}				
				else{
					
					JOptionPane.showMessageDialog(Utilidades.frmHome, "   Email não modificado ou já cadastrado!", "Alerta!", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		this.add(btnAtualizarEmail);
		btnAtualizarEmail.setEnabled(false);
		
		btnAdicionarEmail = new JButton(new ImageIcon(getClass().getResource("/img/confirm.png")));
		btnAdicionarEmail.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//apenas um deles é verificado pois a inativação é feita pra todos ao mesmo tempo
				if(!comboEmail.isEnabled()){
					
					comboEmail.setEnabled(true);
					btnRemoverEmail.setEnabled(true);
					btnAtualizarEmail.setEnabled(true);
					
				}
				
				//verifica se ja é existente, se não, adiciona
				if(!bo.isEmailExistenteLista(txtEmail.getText(), listaEmails)){
					
					email = new EmailVO();
					email.setEmail(txtEmail.getText());
					listaEmails.add(email);
					comboEmail.addItem(++contadorEmails);
					comboEmail.setSelectedItem(contadorEmails);
					
					//limpa o txt
					txtEmail.setText("");
					
					//bloqueia pra não passar de 5
					if(listaEmails.size()==5){
						btnAdicionarEmail.setEnabled(false);
					}
					
				}
				else{
					
					JOptionPane.showMessageDialog(Utilidades.frmHome, "   Email já cadastrado!", "Alerta!", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		this.add(btnAdicionarEmail);
		
		btnRemoverEmail = new JButton(new ImageIcon(getClass().getResource("/img/delete.png")));
		btnRemoverEmail.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
								
				//se ja não houver emails pra remover...
				if(listaEmails.size()==0){
					
					return;
				}
				
				listaEmails.remove(comboEmail.getSelectedIndex());
				comboEmail.removeAllItems();
				carregaEmail();
				
				//se o botao estava bloqueado e agora é possivel adicionar mais, o botão é habilitado
				if(!btnAdicionarEmail.isEnabled() && listaEmails.size()<5){
					
					btnAdicionarEmail.setEnabled(true);
				}
				
				//se não for o ultimo a ser removido
				if(listaEmails.size() != 0){
									
					comboEmail.setSelectedIndex(0);
					
				}
				else{
					
					comboEmail.setEnabled(false);
					btnRemoverEmail.setEnabled(false);
					btnAtualizarEmail.setEnabled(false);
					txtEmail.requestFocus();
					
				}
				
				txtEmail.setText("");

			}
		});
		this.add(btnRemoverEmail);
		btnRemoverEmail.setEnabled(false);
		
		lblEstado = new JLabel();
		lblEstado.setText("Estado:");
		this.add(lblEstado);
		
		comboEstado = new JComboBox<String>();
		comboEstado.addItem("Selecione");
		listaEstados = bo.buscaEstados();
		Iterator<EstadoVO> it = (Iterator<EstadoVO>) listaEstados.iterator();
		
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
		
		lblCidade = new JLabel();
		lblCidade.setText("Cidade:");
		this.add(lblCidade);
		
		comboCidade = new JComboBox<String>();
		comboCidade.addItem("Selecione");
		comboCidade.setEnabled(false);
		
		this.add(comboCidade);
		
		lblLogradouro = new JLabel();
		lblLogradouro.setText("Logradouro:");
		this.add(lblLogradouro);
		
		txtLogradouro = new JTextField();
		this.add(txtLogradouro);
		
		lblNumero = new JLabel();
		lblNumero.setText("Número:");
		this.add(lblNumero);
		
		txtNumero = new JTextField();
		this.add(txtNumero);
		
		lblComplemento = new JLabel();
		lblComplemento.setText("Complemento:");
		this.add(lblComplemento);
		
		txtComplemento = new JTextField();
		this.add(txtComplemento);
		
		lblBairro = new JLabel();
		lblBairro.setText("Bairro:");
		this.add(lblBairro);
		
		txtBairro = new JTextField();
		this.add(txtBairro);
		
		lblCep = new JLabel();
		lblCep.setText("CEP:");
		this.add(lblCep);
		
		txtCep = new JTextField();
		this.add(txtCep);
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				btnCadastrar(validaPessoaJuridica());
				
			}

		});
		this.add(btnCadastrar);
		
		geraBoundsCamposTela();
		
	}
	
	//TODO
	private void geraBoundsCamposTela(){
		
		int distY = 10;
		int camposEsqX = 20;
		int camposDirX = 300;
		int distEntre = 40;
		int alturaCampo = 20;
		
		lblNomePagina.setBounds(camposEsqX,distY,180,alturaCampo);
		lblRazaoSocial.setBounds(camposEsqX,distY+distEntre,120,alturaCampo);
		lblCnpj.setBounds(camposEsqX,distY+distEntre*2,120,alturaCampo);
		lblTelefone.setBounds(camposEsqX,distY+distEntre*3,120,alturaCampo);		
		lblEmail.setBounds(camposEsqX,distY+distEntre*4,120,alturaCampo);
		lblEstado.setBounds(camposEsqX,distY+distEntre*5,120,alturaCampo);
		lblCidade.setBounds(camposEsqX,distY+distEntre*6,120,alturaCampo);
		lblLogradouro.setBounds(camposEsqX,distY+distEntre*7,120,alturaCampo);
		lblNumero.setBounds(camposEsqX,distY+distEntre*8,120,alturaCampo);
		lblComplemento.setBounds(camposEsqX,distY+distEntre*9,120,alturaCampo);
		lblBairro.setBounds(camposEsqX,distY+distEntre*10,120,alturaCampo);
		lblCep.setBounds(camposEsqX,distY+distEntre*11,120,alturaCampo);

		txtRazaoSocial.setBounds(camposDirX,distY+distEntre,200,20);
		txtCnpj.setBounds(camposDirX,distY+distEntre*2,200,20);

		comboTelefone.setBounds(250,distY+distEntre*3,40,20);
		txtTelefone.setBounds(camposDirX,distY+distEntre*3,200,20);
		btnAtualizarTelefone.setBounds(502,distY+distEntre*3,17,17);
		btnAdicionarTelefone.setBounds(521,distY+distEntre*3,17,17);
		btnRemoverTelefone.setBounds(540,distY+distEntre*3,17,17);
		
		comboEmail.setBounds(250,distY+distEntre*4,40,20);
		txtEmail.setBounds(camposDirX,distY+distEntre*4,200,20);
		btnAtualizarEmail.setBounds(502,distY+distEntre*4,17,17);
		btnAdicionarEmail.setBounds(521,distY+distEntre*4,17,17);
		btnRemoverEmail.setBounds(540,distY+distEntre*4,17,17);
		
		comboEstado.setBounds(camposDirX,distY+distEntre*5,200,20);
		comboCidade.setBounds(camposDirX,distY+distEntre*6,200,20);
		txtLogradouro.setBounds(camposDirX,distY+distEntre*7,200,20);
		txtNumero.setBounds(camposDirX,distY+distEntre*8,200,20);
		txtComplemento.setBounds(camposDirX,distY+distEntre*9,200,20);
		txtBairro.setBounds(camposDirX,distY+distEntre*10,200,20);
		txtCep.setBounds(camposDirX,distY+distEntre*11,200,20);
		btnCadastrar.setBounds(245, (distY+distEntre*12)+5, 120, 20);
		
	}
	
	private void carregaTelefone(){
		
		it = listaTelefones.iterator();
		contadorTelefones = 0;

		while (it.hasNext()) {
			comboTelefone.addItem(++contadorTelefones);
			it.next();
		}
		
	}
	
	private void carregaEmail(){
		
		it = listaEmails.iterator();
		contadorEmails = 0;
		
		while(it.hasNext()){
			comboEmail.addItem(++contadorEmails);
			it.next();
		}
		
	}
	
	private PessoaJuridicaVO validaPessoaJuridica(){
		
		pj = new PessoaJuridicaVO() {};
		
		pj.setRazaoSocial(txtRazaoSocial.getText());
		pj.setCnpj(txtCnpj.getText());

		pj.setListaTelefones(listaTelefones);
		pj.setListaEmails(listaEmails);
		
		endereco = new EnderecoVO();
		
		endereco.setLogradouro(txtLogradouro.getText());
		endereco.setNumero(Integer.parseInt(txtNumero.getText()));
		endereco.setComplemento(txtComplemento.getText());
		endereco.setBairro(txtBairro.getText());
		endereco.setCep(txtCep.getText());
		
		cidade = bo.buscaCidadePorNomeNaLista(comboCidade.getSelectedItem().toString(), listaCidades);	
		
		estado = bo.buscaEstadoPorNomeNaLista(comboEstado.getSelectedItem().toString(), listaEstados);
		
		cidade.setEstado(estado);
		endereco.setCidade(cidade);
		
		pj.setEndereco(endereco);
		
		return pj;
	}
	
	public abstract void btnCadastrar(PessoaJuridicaVO pj);
	
}
