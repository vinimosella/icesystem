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
	
	public AtualizarPessoaJuridicaView(PessoaJuridicaVO pj) {

		this.pj = pj;

		this.setLayout(null);
		this.setBackground(Color.decode("#F0F8FF"));
		
		labelRazaoSocial = new JLabel();
		labelRazaoSocial.setText("Raz�o Social:");
		labelRazaoSocial.setBounds(20,5,120,20);
		this.add(labelRazaoSocial);
		
		txtRazaoSocial = new JTextField();
		txtRazaoSocial.setText(pj.getRazaoSocial());
		txtRazaoSocial.setBounds(300,7,200,20);
		this.add(txtRazaoSocial);
		
		labelCnpj = new JLabel();
		labelCnpj.setText("CNPJ:");
		labelCnpj.setBounds(20,30,120,20);
		this.add(labelCnpj);
		
		txtCnpj = new JTextField();
		txtCnpj.setText(pj.getCnpj());
		txtCnpj.setBounds(300,32,200,20);
		this.add(txtCnpj);
		
		labelTelefone = new JLabel();
		labelTelefone.setText("Telefone:");
		labelTelefone.setBounds(20,80,120,20);
		this.add(labelTelefone);
		
		//armazena a quantidade de telefones que tem, alterar� o valor do label de telefone para o numero corrente
		comboTelefone = new JComboBox<Integer>();
				
		comboTelefone.setBounds(250,82,40,20);
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
		txtTelefone.setBounds(300,82,200,20);
		this.add(txtTelefone);
		
		btnAtualizarTelefone = new JButton(new ImageIcon(getClass().getResource("/img/update.png")));
		btnAtualizarTelefone.setBounds(502,83,17,17);
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
					
					JOptionPane.showMessageDialog(AtualizarPessoaJuridicaView.this.frmHome, "   Telefone n�o modificado ou j� cadastrado!", "Alerta!", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		this.add(btnAtualizarTelefone);

		btnAdicionarTelefone = new JButton(new ImageIcon(getClass().getResource("/img/confirm.png")));
		btnAdicionarTelefone.setBounds(521,83,17,17);
		btnAdicionarTelefone.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//verifica se ja � existente, se n�o, adiciona
				if(!bo.isTelefoneExistenteLista(txtTelefone.getText(), listaTelefones)){
					
					telefone = new TelefoneVO();
					telefone.setDdd(txtTelefone.getText().substring(0, 2));
					telefone.setNumero(txtTelefone.getText().substring(2));
					listaTelefones.add(telefone);
					comboTelefone.addItem(++contadorTelefones);
					comboTelefone.setSelectedItem(contadorTelefones);
					
					//bloqueia pra n�o passar de 5
					if(listaTelefones.size()==5){
						
						btnAdicionarTelefone.setEnabled(false);
					}
					
				}
				else{
					
					JOptionPane.showMessageDialog(AtualizarPessoaJuridicaView.this.frmHome, "   Telefone j� cadastrado!", "Alerta!", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		this.add(btnAdicionarTelefone);
		
		btnRemoverTelefone = new JButton(new ImageIcon(getClass().getResource("/img/delete.png")));
		btnRemoverTelefone.setBounds(540,83,17,17);
		btnRemoverTelefone.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
								
				//se ja n�o houver telefones pra remover...
				if(listaTelefones.size() == 0){
					
					return;
				}
				
				listaTelefones.remove(comboTelefone.getSelectedIndex());
				comboTelefone.removeAllItems();
				carregaTelefone();
				
				//se o botao estava bloqueado e agora � possivel adicionar mais, o bot�o � habilitado
				if(!btnAdicionarTelefone.isEnabled() && listaTelefones.size()<5){
					
					btnAdicionarTelefone.setEnabled(true);
				}
								
				//se n�o for o ultimo a ser removido
				if(listaTelefones.size() != 0){
					
					comboTelefone.setSelectedIndex(0);
					
				}

			}
		});
		this.add(btnRemoverTelefone);
		
		labelEmail = new JLabel();
		labelEmail.setText("Email:");
		labelEmail.setBounds(20,105,120,20);
		this.add(labelEmail);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(300,107,200,20);
		this.add(txtEmail);
		
		//armazena a quantidade de emails que tem, alterar� o valor do label de email para o numero corrente
		comboEmail = new JComboBox<Integer>();
				
		comboEmail.setBounds(250,107,40,20);
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
		btnAtualizarEmail.setBounds(502,108,17,17);
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
					
					JOptionPane.showMessageDialog(AtualizarPessoaJuridicaView.this.frmHome, "   Email n�o modificado ou j� cadastrado!", "Alerta!", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		this.add(btnAtualizarEmail);
		
		btnAdicionarEmail = new JButton(new ImageIcon(getClass().getResource("/img/confirm.png")));
		btnAdicionarEmail.setBounds(521,108,17,17);
		btnAdicionarEmail.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//verifica se ja � existente, se n�o, adiciona
				if(!bo.isEmailExistenteLista(txtEmail.getText(), listaEmails)){
					
					email = new EmailVO();
					email.setEmail(txtEmail.getText());
					listaEmails.add(email);
					comboEmail.addItem(++contadorEmails);
					comboEmail.setSelectedItem(contadorEmails);
					
					//bloqueia pra n�o passar de 5
					if(listaEmails.size()==5){
						btnAdicionarEmail.setEnabled(false);
					}
					
				}
				else{
					
					JOptionPane.showMessageDialog(AtualizarPessoaJuridicaView.this.frmHome, "   Email j� cadastrado!", "Alerta!", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		this.add(btnAdicionarEmail);
		
		btnRemoverEmail = new JButton(new ImageIcon(getClass().getResource("/img/delete.png")));
		btnRemoverEmail.setBounds(540,108,17,17);
		btnRemoverEmail.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
								
				//se ja n�o houver emails pra remover...
				if(listaEmails.size()==0){
					
					return;
				}
				
				listaEmails.remove(comboEmail.getSelectedIndex());
				comboEmail.removeAllItems();
				carregaEmail();
				
				//se o botao estava bloqueado e agora � possivel adicionar mais, o bot�o � habilitado
				if(!btnAdicionarEmail.isEnabled() && listaEmails.size()<5){
					
					btnAdicionarEmail.setEnabled(true);
				}
				
				//se n�o for o ultimo a ser removido
				if(listaEmails.size() != 0){
									
					comboEmail.setSelectedIndex(0);
					
				}

			}
		});
		this.add(btnRemoverEmail);
		
		labelEstado = new JLabel();
		labelEstado.setText("Estado:");
		labelEstado.setBounds(20,130,120,20);
		this.add(labelEstado);
		
		comboEstado = new JComboBox<String>();
		comboEstado.setBounds(300,132,200,20);
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
		labelCidade.setBounds(20,155,120,20);
		this.add(labelCidade);
		
		comboCidade = new JComboBox<String>();
		comboCidade.setBounds(300,157,200,20);
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
		labelLogradouro.setBounds(20,180,120,20);
		this.add(labelLogradouro);
		
		txtLogradouro = new JTextField();
		txtLogradouro.setText(pj.getEndereco().getLogradouro());
		txtLogradouro.setBounds(300,182,200,20);
		this.add(txtLogradouro);
		
		labelNumero = new JLabel();
		labelNumero.setText("N�mero:");
		labelNumero.setBounds(20,205,120,20);
		this.add(labelNumero);
		
		txtNumero = new JTextField();
		txtNumero.setText(pj.getEndereco().getNumero().toString());
		txtNumero.setBounds(300,207,200,20);
		this.add(txtNumero);
		
		labelComplemento = new JLabel();
		labelComplemento.setText("Complemento:");
		labelComplemento.setBounds(20,230,120,20);
		this.add(labelComplemento);
		
		txtComplemento = new JTextField();
		txtComplemento.setText(pj.getEndereco().getComplemento());
		txtComplemento.setBounds(300,232,200,20);
		this.add(txtComplemento);
		
		labelBairro = new JLabel();
		labelBairro.setText("Bairro:");
		labelBairro.setBounds(20,255,120,20);
		this.add(labelBairro);
		
		txtBairro = new JTextField();
		txtBairro.setText(pj.getEndereco().getBairro());
		txtBairro.setBounds(300,257,200,20);
		this.add(txtBairro);
		
		labelCep = new JLabel();
		labelCep.setText("CEP:");
		labelCep.setBounds(20,280,120,20);
		this.add(labelCep);
		
		txtCep = new JTextField();
		txtCep.setText(pj.getEndereco().getCep());
		txtCep.setBounds(300,282,200,20);
		this.add(txtCep);
		
		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setBounds(280, 380, 120, 20);
		btnAtualizar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				btnAtualizar(validaPessoaJuridica());
				
			}

		});
		this.add(btnAtualizar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(350, 380, 120, 20);
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				btnCancelar();
				
			}

		});
		this.add(btnCancelar);
		
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
