package ui.funcionario;

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
import vo.CargoVO;
import vo.CidadeVO;
import vo.EmailVO;
import vo.EnderecoVO;
import vo.EstadoVO;
import vo.FuncionarioVO;
import vo.TelefoneVO;
import bo.FuncionarioBO;

public class CadastrarFuncionarioView extends JPanel{

	private static final long serialVersionUID = 1L;
	private JLabel labelNome;
	private JTextField txtNome;
	private JLabel labelCpf;
	private JTextField txtCpf;
	private JLabel labelRg;
	private JTextField txtRg;
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
	private JLabel labelCargo;
	private JComboBox<String> comboCargo;
	private List<TelefoneVO> listaTelefones;
	private List<EmailVO> listaEmails;
	private Integer contadorTelefones;
	private Integer contadorEmails;
	private JButton btnCadastrar;
	
	private FuncionarioVO funcionario;
	private TelefoneVO telefone;
	private EmailVO email;
	private EnderecoVO endereco;
	private CidadeVO cidade;
	private EstadoVO estado;
	private CargoVO cargo;
	private FuncionarioBO bo;
	private Iterator<?> it;
	
	private List<EstadoVO> listaEstados;
	private List<CidadeVO> listaCidades;
	private List<CargoVO> listaCargos;
	
	{
		bo = new FuncionarioBO();
		contadorTelefones = 0;
		contadorEmails = 0;
		listaTelefones = new ArrayList<TelefoneVO>();
		listaEmails = new ArrayList<EmailVO>();
	}
	
	public CadastrarFuncionarioView(){
		
		this.setLayout(null);
		this.setBackground(Color.decode("#F0F8FF"));
		
		labelNome = new JLabel();
		labelNome.setText("Nome:");
		labelNome.setBounds(20,30,120,20);
		this.add(labelNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(300,30,200,20);
		this.add(txtNome);
		
		labelCpf = new JLabel();
		labelCpf.setText("CPF:");
		labelCpf.setBounds(20,60,120,20);
		this.add(labelCpf);
		
		txtCpf = new JTextField();
		txtCpf.setBounds(300,60,200,20);
		this.add(txtCpf);
		
		labelRg = new JLabel();
		labelRg.setText("RG:");
		labelRg.setBounds(20,90,120,20);
		this.add(labelRg);
		
		txtRg = new JTextField();
		txtRg.setBounds(300,90,200,20);
		this.add(txtRg);
		
		labelTelefone = new JLabel();
		labelTelefone.setText("Telefone:");
		labelTelefone.setBounds(20,120,120,20);
		this.add(labelTelefone);
		
		//armazena a quantidade de telefones que tem, alterar� o valor do label de telefone para o numero corrente
		comboTelefone = new JComboBox<Integer>();
				
		comboTelefone.setBounds(250,120,40,20);
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
		txtTelefone.setBounds(300,120,200,20);
		this.add(txtTelefone);
		
		btnAtualizarTelefone = new JButton(new ImageIcon(getClass().getResource("/img/update.png")));
		btnAtualizarTelefone.setBounds(502,120,17,17);
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
					
					JOptionPane.showMessageDialog(Utilidades.frmHome, "   Telefone n�o modificado ou j� cadastrado!", "Alerta!", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		this.add(btnAtualizarTelefone);
		btnAtualizarTelefone.setEnabled(false);

		btnAdicionarTelefone = new JButton(new ImageIcon(getClass().getResource("/img/confirm.png")));
		btnAdicionarTelefone.setBounds(521,120,17,17);
		btnAdicionarTelefone.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//apenas um deles � verificado pois a inativa��o � feita pra todos ao mesmo tempo
				if(!comboTelefone.isEnabled()){
					
					comboTelefone.setEnabled(true);
					btnRemoverTelefone.setEnabled(true);
					btnAtualizarTelefone.setEnabled(true);
					
				}
				
				//verifica se ja � existente, se n�o, adiciona
				if(!bo.isTelefoneExistenteLista(txtTelefone.getText(), listaTelefones)){
					
					telefone = new TelefoneVO();
					telefone.setDdd(txtTelefone.getText().substring(0, 2));
					telefone.setNumero(txtTelefone.getText().substring(2));
					listaTelefones.add(telefone);
					comboTelefone.addItem(++contadorTelefones);
					comboTelefone.setSelectedItem(contadorTelefones);
					
					//limpa o txt
					txtTelefone.setText("");
					
					//bloqueia pra n�o passar de 5
					if(listaTelefones.size()==5){
						
						btnAdicionarTelefone.setEnabled(false);
					}
					
				}
				else{
					
					JOptionPane.showMessageDialog(Utilidades.frmHome, "   Telefone j� cadastrado!", "Alerta!", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		this.add(btnAdicionarTelefone);
		
		btnRemoverTelefone = new JButton(new ImageIcon(getClass().getResource("/img/delete.png")));
		btnRemoverTelefone.setBounds(540,120,17,17);
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
		
		labelEmail = new JLabel();
		labelEmail.setText("Email:");
		labelEmail.setBounds(20,150,120,20);
		this.add(labelEmail);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(300,150,200,20);
		this.add(txtEmail);
		
		//armazena a quantidade de emails que tem, alterar� o valor do label de email para o numero corrente
		comboEmail = new JComboBox<Integer>();
				
		comboEmail.setBounds(250,150,40,20);
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
		btnAtualizarEmail.setBounds(502,150,17,17);
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
					
					JOptionPane.showMessageDialog(Utilidades.frmHome, "   Email n�o modificado ou j� cadastrado!", "Alerta!", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		this.add(btnAtualizarEmail);
		btnAtualizarEmail.setEnabled(false);
		
		btnAdicionarEmail = new JButton(new ImageIcon(getClass().getResource("/img/confirm.png")));
		btnAdicionarEmail.setBounds(521,150,17,17);
		btnAdicionarEmail.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//apenas um deles � verificado pois a inativa��o � feita pra todos ao mesmo tempo
				if(!comboEmail.isEnabled()){
					
					comboEmail.setEnabled(true);
					btnRemoverEmail.setEnabled(true);
					btnAtualizarEmail.setEnabled(true);
					
				}
				
				//verifica se ja � existente, se n�o, adiciona
				if(!bo.isEmailExistenteLista(txtEmail.getText(), listaEmails)){
					
					email = new EmailVO();
					email.setEmail(txtEmail.getText());
					listaEmails.add(email);
					comboEmail.addItem(++contadorEmails);
					comboEmail.setSelectedItem(contadorEmails);
					
					//limpa o txt
					txtEmail.setText("");
					
					//bloqueia pra n�o passar de 5
					if(listaEmails.size()==5){
						btnAdicionarEmail.setEnabled(false);
					}
					
				}
				else{
					
					JOptionPane.showMessageDialog(Utilidades.frmHome, "   Email j� cadastrado!", "Alerta!", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		this.add(btnAdicionarEmail);
		
		btnRemoverEmail = new JButton(new ImageIcon(getClass().getResource("/img/delete.png")));
		btnRemoverEmail.setBounds(540,150,17,17);
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
		
		labelEstado = new JLabel();
		labelEstado.setText("Estado:");
		labelEstado.setBounds(20,180,120,20);
		this.add(labelEstado);
		
		comboEstado = new JComboBox<String>();
		comboEstado.setBounds(300,180,200,20);
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
		
		labelCidade = new JLabel();
		labelCidade.setText("Cidade:");
		labelCidade.setBounds(20,210,120,20);
		this.add(labelCidade);
		
		comboCidade = new JComboBox<String>();
		comboCidade.setBounds(300,210,200,20);
		comboCidade.addItem("Selecione");
		comboCidade.setEnabled(false);
		
		this.add(comboCidade);
		
		labelLogradouro = new JLabel();
		labelLogradouro.setText("Logradouro:");
		labelLogradouro.setBounds(20,240,120,20);
		this.add(labelLogradouro);
		
		txtLogradouro = new JTextField();
		txtLogradouro.setBounds(300,240,200,20);
		this.add(txtLogradouro);
		
		labelNumero = new JLabel();
		labelNumero.setText("N�mero:");
		labelNumero.setBounds(20,270,120,20);
		this.add(labelNumero);
		
		txtNumero = new JTextField();
		txtNumero.setBounds(300,270,200,20);
		this.add(txtNumero);
		
		labelComplemento = new JLabel();
		labelComplemento.setText("Complemento:");
		labelComplemento.setBounds(20,300,120,20);
		this.add(labelComplemento);
		
		txtComplemento = new JTextField();
		txtComplemento.setBounds(300,300,200,20);
		this.add(txtComplemento);
		
		labelBairro = new JLabel();
		labelBairro.setText("Bairro:");
		labelBairro.setBounds(20,330,120,20);
		this.add(labelBairro);
		
		txtBairro = new JTextField();
		txtBairro.setBounds(300,330,200,20);
		this.add(txtBairro);
		
		labelCep = new JLabel();
		labelCep.setText("CEP:");
		labelCep.setBounds(20,360,120,20);
		this.add(labelCep);
		
		txtCep = new JTextField();
		txtCep.setBounds(300,360,200,20);
		this.add(txtCep);
		
		labelCargo = new JLabel();
		labelCargo.setText("Cargo:");
		labelCargo.setBounds(20,390,120,20);
		this.add(labelCargo);
		
		comboCargo = new JComboBox<String>();
		comboCargo.setBounds(300,390,200,20);
		listaCargos = bo.buscaCargos();
		
		Iterator<CargoVO> iterador = listaCargos.iterator();
		CargoVO cargo;
		
		while(iterador.hasNext()){
			
			cargo = (CargoVO) iterador.next();
			comboCargo.addItem(cargo.getFuncao());
			
		}
		
		this.add(comboCargo);
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(280,450,120,20);
		btnCadastrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if(bo.cadastrarFuncionario(validaFuncionario())){
					
					JOptionPane.showMessageDialog(Utilidades.frmHome, "   Funcion�rio cadastrado!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
					
				}
				else{
					
					JOptionPane.showMessageDialog(Utilidades.frmHome, "   N�o foi poss�vel cadastrar!", "Alerta!", JOptionPane.ERROR_MESSAGE);

				}
			}
		});
		this.add(btnCadastrar);
		
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
	
	private FuncionarioVO validaFuncionario(){
		
		funcionario = new FuncionarioVO();
		
		funcionario.setNome(txtNome.getText());
		funcionario.setCpf(txtCpf.getText());
		funcionario.setRg(txtRg.getText());
		funcionario.setListaTelefones(listaTelefones);
		funcionario.setListaEmails(listaEmails);
		
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
		
		funcionario.setEndereco(endereco);
		
		cargo = bo.buscaCargoPorFuncaoNaLista(comboCargo.getSelectedItem().toString(), listaCargos);
				
		funcionario.setCargo(cargo);
		
		return funcionario;
	}
	
}
