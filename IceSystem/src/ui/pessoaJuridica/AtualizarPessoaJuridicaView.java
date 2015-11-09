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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import vo.CidadeVO;
import vo.EmailVO;
import vo.EnderecoVO;
import vo.EstadoVO;
import vo.PessoaJuridicaVO;
import vo.TelefoneVO;
import bo.PessoaJuridicaBO;

public abstract class AtualizarPessoaJuridicaView extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JLabel labelNomePagina;
	private JLabel labelRazaoSocial;
	private JTextField txtRazaoSocial;
	private JLabel labelCnpj;
	private JTextField txtCnpj;
	private JLabel labelTelefone;
	private JComboBox<Integer> comboTelefone;
	private JTextField txtTelefone;
	private JButton btnAtualizarTelefone;
	private JButton btnAdicionarTelefone;
	private JButton btnRemoverTelefone;
	private JLabel labelEmail;
	private JComboBox<Integer> comboEmail; 
	private JTextField txtEmail;
	private JButton btnAtualizarEmail;
	private JButton btnAdicionarEmail;
	private JButton btnRemoverEmail;
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
	private List<TelefoneVO> listaTelefones;
	private List<EmailVO> listaEmails;
	private Integer contadorTelefones;
	private Integer contadorEmails;
	private JButton btnAtualizar;
	private JButton btnCancelar;
	
	private PessoaJuridicaVO pj;
	private TelefoneVO telefone;
	private EmailVO email;
	private EnderecoVO endereco;
	private CidadeVO cidade;
	private EstadoVO estado;
	private JFrame frmHome;
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
	
	public AtualizarPessoaJuridicaView(PessoaJuridicaVO pj, String titulo) {

		this.pj = pj;

		this.setLayout(null);
		this.setBackground(Color.decode("#F0F8FF"));
		
		labelNomePagina = new JLabel();
		labelNomePagina.setText(titulo);
		this.add(labelNomePagina);
		
		labelRazaoSocial = new JLabel();
		labelRazaoSocial.setText("Razão Social:");
		this.add(labelRazaoSocial);
		
		txtRazaoSocial = new JTextField();
		txtRazaoSocial.setText(pj.getRazaoSocial());
		this.add(txtRazaoSocial);
		
		labelCnpj = new JLabel();
		labelCnpj.setText("CNPJ:");
		this.add(labelCnpj);
		
		txtCnpj = new JTextField();
		txtCnpj.setText(pj.getCnpj());
		this.add(txtCnpj);
		
		labelTelefone = new JLabel();
		labelTelefone.setText("Telefone:");
		this.add(labelTelefone);
		
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
				}				
				else{
					
					JOptionPane.showMessageDialog(AtualizarPessoaJuridicaView.this.frmHome, "   Telefone não modificado ou já cadastrado!", "Alerta!", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		this.add(btnAtualizarTelefone);

		btnAdicionarTelefone = new JButton(new ImageIcon(getClass().getResource("/img/confirm.png")));
		btnAdicionarTelefone.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//verifica se ja é existente, se não, adiciona
				if(!bo.isTelefoneExistenteLista(txtTelefone.getText(), listaTelefones)){
					
					telefone = new TelefoneVO();
					telefone.setDdd(txtTelefone.getText().substring(0, 2));
					telefone.setNumero(txtTelefone.getText().substring(2));
					listaTelefones.add(telefone);
					comboTelefone.addItem(++contadorTelefones);
					comboTelefone.setSelectedItem(contadorTelefones);
					
					//bloqueia pra não passar de 5
					if(listaTelefones.size()==5){
						
						btnAdicionarTelefone.setEnabled(false);
					}
					
				}
				else{
					
					JOptionPane.showMessageDialog(AtualizarPessoaJuridicaView.this.frmHome, "   Telefone já cadastrado!", "Alerta!", JOptionPane.ERROR_MESSAGE);
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

			}
		});
		this.add(btnRemoverTelefone);
		
		labelEmail = new JLabel();
		labelEmail.setText("Email:");
		this.add(labelEmail);
		
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
				}				
				else{
					
					JOptionPane.showMessageDialog(AtualizarPessoaJuridicaView.this.frmHome, "   Email não modificado ou já cadastrado!", "Alerta!", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		this.add(btnAtualizarEmail);
		
		btnAdicionarEmail = new JButton(new ImageIcon(getClass().getResource("/img/confirm.png")));
		btnAdicionarEmail.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//verifica se ja é existente, se não, adiciona
				if(!bo.isEmailExistenteLista(txtEmail.getText(), listaEmails)){
					
					email = new EmailVO();
					email.setEmail(txtEmail.getText());
					listaEmails.add(email);
					comboEmail.addItem(++contadorEmails);
					comboEmail.setSelectedItem(contadorEmails);
					
					//bloqueia pra não passar de 5
					if(listaEmails.size()==5){
						btnAdicionarEmail.setEnabled(false);
					}
					
				}
				else{
					
					JOptionPane.showMessageDialog(AtualizarPessoaJuridicaView.this.frmHome, "   Email já cadastrado!", "Alerta!", JOptionPane.ERROR_MESSAGE);
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

			}
		});
		this.add(btnRemoverEmail);
		
		labelEstado = new JLabel();
		labelEstado.setText("Estado:");
		this.add(labelEstado);
		
		comboEstado = new JComboBox<String>();
		comboEstado.addItem("Selecione");
		listaEstados = bo.buscaEstados();
		it = listaEstados.iterator();
		
		while(it.hasNext()){
			estado = (EstadoVO) it.next();
			comboEstado.addItem(estado.getNome());
		}
		
		comboEstado.setSelectedItem(pj.getEndereco().getCidade().getEstado().getNome());
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
		this.add(labelCidade);
		
		comboCidade = new JComboBox<String>();
		comboCidade.addItem("Selecione");
		listaCidades = bo.buscaCidadesPorEstado(listaEstados.get(comboEstado.getSelectedIndex()-1).getIdEstado());
		it = listaCidades.iterator();
		CidadeVO cidade;
		
		while(it.hasNext()){
			
			cidade = (CidadeVO) it.next();
			comboCidade.addItem(cidade.getNome());
		}
		comboCidade.setSelectedItem(pj.getEndereco().getCidade().getNome());
		this.add(comboCidade);
		
		labelLogradouro = new JLabel();
		labelLogradouro.setText("Logradouro:");
		this.add(labelLogradouro);
		
		txtLogradouro = new JTextField();
		txtLogradouro.setText(pj.getEndereco().getLogradouro());
		this.add(txtLogradouro);
		
		labelNumero = new JLabel();
		labelNumero.setText("Número:");
		this.add(labelNumero);
		
		txtNumero = new JTextField();
		txtNumero.setText(pj.getEndereco().getNumero().toString());
		this.add(txtNumero);
		
		labelComplemento = new JLabel();
		labelComplemento.setText("Complemento:");
		this.add(labelComplemento);
		
		txtComplemento = new JTextField();
		txtComplemento.setText(pj.getEndereco().getComplemento());
		this.add(txtComplemento);
		
		labelBairro = new JLabel();
		labelBairro.setText("Bairro:");
		this.add(labelBairro);
		
		txtBairro = new JTextField();
		txtBairro.setText(pj.getEndereco().getBairro());
		this.add(txtBairro);
		
		labelCep = new JLabel();
		labelCep.setText("CEP:");
		this.add(labelCep);
		
		txtCep = new JTextField();
		txtCep.setText(pj.getEndereco().getCep());
		this.add(txtCep);
		
		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				btnAtualizar(validaPessoaJuridica());
				
			}

		});
		this.add(btnAtualizar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				btnCancelar();
				
			}

		});
		this.add(btnCancelar);
		
		geraBoundsCamposTela();
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
	
	private void geraBoundsCamposTela(){
		
		int distY = 10;
		int camposEsqX = 20;
		int camposDirX = 300;
		int distEntre = 40;
		int alturaCampo = 20;
		
		labelNomePagina.setBounds(camposEsqX,distY,180,alturaCampo);
		labelRazaoSocial.setBounds(camposEsqX,distY+distEntre,120,alturaCampo);
		labelCnpj.setBounds(camposEsqX,distY+distEntre*2,120,alturaCampo);
		labelTelefone.setBounds(camposEsqX,distY+distEntre*3,120,alturaCampo);		
		labelEmail.setBounds(camposEsqX,distY+distEntre*4,120,alturaCampo);
		labelEstado.setBounds(camposEsqX,distY+distEntre*5,120,alturaCampo);
		labelCidade.setBounds(camposEsqX,distY+distEntre*6,120,alturaCampo);
		labelLogradouro.setBounds(camposEsqX,distY+distEntre*7,120,alturaCampo);
		labelNumero.setBounds(camposEsqX,distY+distEntre*8,120,alturaCampo);
		labelComplemento.setBounds(camposEsqX,distY+distEntre*9,120,alturaCampo);
		labelBairro.setBounds(camposEsqX,distY+distEntre*10,120,alturaCampo);
		labelCep.setBounds(camposEsqX,distY+distEntre*11,120,alturaCampo);

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
		btnAtualizar.setBounds(245, (distY+distEntre*12)+5, 120, 20);
		
	}
	
	private PessoaJuridicaVO validaPessoaJuridica(){
				
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
	
	public abstract void btnCancelar();
	
	public abstract void btnAtualizar(PessoaJuridicaVO pj);

}
