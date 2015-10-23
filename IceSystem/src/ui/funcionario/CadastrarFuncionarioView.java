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
	private JLabel lblNomeTela;
	private JLabel lblNome;
	private JTextField txtNome;
	private JLabel lblCpf;
	private JTextField txtCpf;
	private JLabel lblRg;
	private JTextField txtRg;
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
	private JLabel lblCargo;
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

		lblNomeTela = new JLabel();
		lblNomeTela.setText(Utilidades.CADASTRAR_FUNCIONARIO);
		this.add(lblNomeTela);
		
		lblNome = new JLabel();
		lblNome.setText("Nome:");
		this.add(lblNome);
		
		txtNome = new JTextField();
		this.add(txtNome);
		
		lblCpf = new JLabel();
		lblCpf.setText("CPF:");
		this.add(lblCpf);
		
		txtCpf = new JTextField();
		this.add(txtCpf);
		
		lblRg = new JLabel();
		lblRg.setText("RG:");
		this.add(lblRg);
		
		txtRg = new JTextField();
		this.add(txtRg);
		
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
		
		txtEmail = new JTextField();
		this.add(txtEmail);
		
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
		
		lblCargo = new JLabel();
		lblCargo.setText("Cargo:");
		this.add(lblCargo);
		
		comboCargo = new JComboBox<String>();
		listaCargos = bo.buscaCargos();
		
		Iterator<CargoVO> iterador = listaCargos.iterator();
		CargoVO cargo;
		
		while(iterador.hasNext()){
			
			cargo = (CargoVO) iterador.next();
			comboCargo.addItem(cargo.getFuncao());
			
		}
		
		this.add(comboCargo);
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if(bo.cadastrarFuncionario(validaFuncionario())){
					
					JOptionPane.showMessageDialog(Utilidades.frmHome, "   Funcionário cadastrado!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
					
				}
				else{
					
					JOptionPane.showMessageDialog(Utilidades.frmHome, "   Não foi possível cadastrar!", "Alerta!", JOptionPane.ERROR_MESSAGE);

				}
			}
		});
		this.add(btnCadastrar);
		
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
	
	private void geraBoundsCamposTela(){
		
		int distY = 10;
		int lblEsqX = 20;
		int lblDirX = 300;
		int distEntre = 35;
		int alturaCampo = 20;
		
		//campos esquerda
		lblNomeTela.setBounds(lblEsqX,distY,180,alturaCampo);
		lblNome.setBounds(lblEsqX,distY+distEntre,120,alturaCampo);
		lblCpf.setBounds(lblEsqX,distY+distEntre*2,120,alturaCampo);
		lblRg.setBounds(lblEsqX,distY+distEntre*3,120,alturaCampo);
		lblTelefone.setBounds(lblEsqX,distY+distEntre*4,120,alturaCampo);
		lblEmail.setBounds(lblEsqX,distY+distEntre*5,120,alturaCampo);
		lblEstado.setBounds(lblEsqX,distY+distEntre*6,120,alturaCampo);
		lblCidade.setBounds(lblEsqX,distY+distEntre*7,120,alturaCampo);
		lblLogradouro.setBounds(lblEsqX,distY+distEntre*8,120,alturaCampo);
		lblNumero.setBounds(lblEsqX,distY+distEntre*9,120,alturaCampo);
		lblComplemento.setBounds(lblEsqX,distY+distEntre*10,120,alturaCampo);
		lblBairro.setBounds(lblEsqX,distY+distEntre*11,120,alturaCampo);
		lblCep.setBounds(lblEsqX,distY+distEntre*12,120,alturaCampo);
		lblCargo.setBounds(lblEsqX,distY+distEntre*13,120,alturaCampo);
		
		
		//campos direita
		txtNome.setBounds(lblDirX,distY+distEntre,200,alturaCampo);
		txtCpf.setBounds(lblDirX,distY+distEntre*2,200,alturaCampo);
		txtRg.setBounds(lblDirX,distY+distEntre*3,200,alturaCampo);
		
		comboTelefone.setBounds(250,distY+distEntre*4,40,alturaCampo);
		txtTelefone.setBounds(lblDirX,distY+distEntre*4,200,alturaCampo);
		btnAtualizarTelefone.setBounds(502,distY+distEntre*4,17,alturaCampo-3);
		btnAdicionarTelefone.setBounds(521,distY+distEntre*4,17,alturaCampo-3);
		btnRemoverTelefone.setBounds(540,distY+distEntre*4,17,alturaCampo-3);
		
		comboEmail.setBounds(250,distY+distEntre*5,40,alturaCampo);
		txtEmail.setBounds(lblDirX,distY+distEntre*5,200,alturaCampo);
		btnAtualizarEmail.setBounds(502,distY+distEntre*5,17,alturaCampo-3);
		btnAdicionarEmail.setBounds(521,distY+distEntre*5,17,alturaCampo-3);
		btnRemoverEmail.setBounds(540,distY+distEntre*5,17,alturaCampo-3);
		
		comboEstado.setBounds(lblDirX,distY+distEntre*6,200,alturaCampo);
		comboCidade.setBounds(lblDirX,distY+distEntre*7,200,alturaCampo);	
		txtLogradouro.setBounds(lblDirX,distY+distEntre*8,200,alturaCampo);
		txtNumero.setBounds(lblDirX,distY+distEntre*9,200,alturaCampo);
		txtComplemento.setBounds(lblDirX,distY+distEntre*10,200,alturaCampo);
		txtBairro.setBounds(lblDirX,distY+distEntre*11,200,alturaCampo);
		txtCep.setBounds(lblDirX,distY+distEntre*12,200,alturaCampo);
		comboCargo.setBounds(lblDirX,distY+distEntre*13,200,alturaCampo);
		btnCadastrar.setBounds(245,(distY+distEntre*14)+5,120,alturaCampo);
		
	}
	
}
