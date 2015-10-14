package ui.funcionario;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import util.Utilidades;
import vo.CargoVO;
import vo.CidadeVO;
import vo.EmailVO;
import vo.EnderecoVO;
import vo.EstadoVO;
import vo.FuncionarioVO;
import vo.TelefoneVO;
import bo.FuncionarioBO;

public class AtualizarFuncionarioView extends JPanel{

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
	private JButton btnCancelar;
	private JButton btnAtualizar;
	
	private JFrame frmHome;
	private FuncionarioBO bo;
	private Iterator<?> it;
	
	private FuncionarioVO funcionario;
	private TelefoneVO telefone;
	private EmailVO email;
	private EnderecoVO endereco;
	private CidadeVO cidade;
	private EstadoVO estado;
	private CargoVO cargo;
	
	private List<EstadoVO> listaEstados;
	private List<CidadeVO> listaCidades;
	private List<CargoVO> listaCargos;
		
	{
		bo = new FuncionarioBO();
		comboCidade = new JComboBox<String>();
	}
	
	public AtualizarFuncionarioView(JFrame frmHome, FuncionarioVO funcionario, Byte codUser){
		
		this.frmHome = frmHome;
				
		listaEmails = funcionario.getListaEmails();
		listaTelefones = funcionario.getListaTelefones();
		
		this.setLayout(null);
		this.setBackground(Color.decode("#F0F8FF"));
		
		labelNome = new JLabel();
		labelNome.setText("Nome:");
		labelNome.setBounds(20,5,120,20);
		this.add(labelNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(300,7,200,20);
		txtNome.setText(funcionario.getNome());
		this.add(txtNome);
		
		labelCpf = new JLabel();
		labelCpf.setText("CPF:");
		labelCpf.setBounds(20,30,120,20);
		this.add(labelCpf);
		
		txtCpf = new JTextField();
		txtCpf.setBounds(300,32,200,20);
		txtCpf.setText(funcionario.getCpf());
		this.add(txtCpf);
		
		labelRg = new JLabel();
		labelRg.setText("RG:");
		labelRg.setBounds(20,55,120,20);
		this.add(labelRg);
		
		txtRg = new JTextField();
		txtRg.setBounds(300,57,200,20);
		txtRg.setText(funcionario.getRg());
		this.add(txtRg);
		
		labelTelefone = new JLabel();
		labelTelefone.setText("Telefone:");
		labelTelefone.setBounds(20,80,120,20);
		this.add(labelTelefone);
		
		//armazena a quantidade de telefones que tem, alterará o valor do label de telefone para o numero corrente
		comboTelefone = new JComboBox<Integer>();
		
		carregaTelefone();
		
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
		txtTelefone.setText(listaTelefones.get(0).getDdd()+listaTelefones.get(0).getNumero());
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
					
					JOptionPane.showMessageDialog(AtualizarFuncionarioView.this.frmHome, "   Telefone não modificado ou já cadastrado!", "Alerta!", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		this.add(btnAtualizarTelefone);

		btnAdicionarTelefone = new JButton(new ImageIcon(getClass().getResource("/img/confirm.png")));
		btnAdicionarTelefone.setBounds(521,83,17,17);
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
					
					JOptionPane.showMessageDialog(AtualizarFuncionarioView.this.frmHome, "   Telefone já cadastrado!", "Alerta!", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		this.add(btnAdicionarTelefone);
		
		btnRemoverTelefone = new JButton(new ImageIcon(getClass().getResource("/img/delete.png")));
		btnRemoverTelefone.setBounds(540,83,17,17);
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
		labelEmail.setBounds(20,105,120,20);
		this.add(labelEmail);
		
		//armazena a quantidade de emails que tem, alterará o valor do label de email para o numero corrente
		comboEmail = new JComboBox<Integer>();
		
		carregaEmail();
		
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
		
		txtEmail = new JTextField();
		txtEmail.setBounds(300,107,200,20);
		txtEmail.setText(listaEmails.get(0).getEmail());
		this.add(txtEmail);
		
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
					
					JOptionPane.showMessageDialog(AtualizarFuncionarioView.this.frmHome, "   Email não modificado ou já cadastrado!", "Alerta!", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		this.add(btnAtualizarEmail);
		
		btnAdicionarEmail = new JButton(new ImageIcon(getClass().getResource("/img/confirm.png")));
		btnAdicionarEmail.setBounds(521,108,17,17);
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
					
					JOptionPane.showMessageDialog(AtualizarFuncionarioView.this.frmHome, "   Email já cadastrado!", "Alerta!", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		this.add(btnAdicionarEmail);
		
		btnRemoverEmail = new JButton(new ImageIcon(getClass().getResource("/img/delete.png")));
		btnRemoverEmail.setBounds(540,108,17,17);
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
		labelEstado.setBounds(20,130,120,20);
		this.add(labelEstado);
		
		comboEstado = new JComboBox<String>();
		comboEstado.setBounds(300,132,200,20);
		comboEstado.addItem("Selecione");
		listaEstados = bo.buscaEstados();
		
		it = (Iterator<EstadoVO>) listaEstados.iterator();
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
		comboEstado.setSelectedItem(funcionario.getEndereco().getCidade().getEstado().getNome());
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
		
		comboCidade.setSelectedItem(funcionario.getEndereco().getCidade().getNome());
		
		this.add(comboCidade);
		
		labelLogradouro = new JLabel();
		labelLogradouro.setText("Logradouro:");
		labelLogradouro.setBounds(20,180,120,20);
		this.add(labelLogradouro);
		
		txtLogradouro = new JTextField();
		txtLogradouro.setBounds(300,182,200,20);
		txtLogradouro.setText(funcionario.getEndereco().getLogradouro());
		this.add(txtLogradouro);
		
		labelNumero = new JLabel();
		labelNumero.setText("Número:");
		labelNumero.setBounds(20,205,120,20);
		this.add(labelNumero);
		
		txtNumero = new JTextField();
		txtNumero.setBounds(300,207,200,20);
		txtNumero.setText(funcionario.getEndereco().getNumero().toString());
		this.add(txtNumero);
		
		labelComplemento = new JLabel();
		labelComplemento.setText("Complemento:");
		labelComplemento.setBounds(20,230,120,20);
		this.add(labelComplemento);
		
		txtComplemento = new JTextField();
		txtComplemento.setBounds(300,232,200,20);
		txtComplemento.setText(funcionario.getEndereco().getComplemento());
		this.add(txtComplemento);
		
		labelBairro = new JLabel();
		labelBairro.setText("Bairro:");
		labelBairro.setBounds(20,255,120,20);
		this.add(labelBairro);
		
		txtBairro = new JTextField();
		txtBairro.setBounds(300,257,200,20);
		txtBairro.setText(funcionario.getEndereco().getBairro());
		this.add(txtBairro);
		
		labelCep = new JLabel();
		labelCep.setText("CEP:");
		labelCep.setBounds(20,280,120,20);
		this.add(labelCep);
		
		txtCep = new JTextField();
		txtCep.setBounds(300,282,200,20);
		txtCep.setText(funcionario.getEndereco().getCep());
		this.add(txtCep);
		
		labelCargo = new JLabel();
		labelCargo.setText("Cargo:");
		labelCargo.setBounds(20,305,120,20);
		this.add(labelCargo);
		
		comboCargo = new JComboBox<String>();
		comboCargo.setBounds(300, 307, 200, 20);
		listaCargos = bo.buscaCargos();
		
		it = listaCargos.iterator();
		CargoVO cargo;
		
		while(it.hasNext()){
			
			cargo = (CargoVO) it.next();
			comboCargo.addItem(cargo.getFuncao());
			
		}
		comboCargo.setSelectedItem(funcionario.getCargo().getFuncao());
		this.add(comboCargo);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(100, 450, 120, 20);
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				AtualizarFuncionarioView.this.frmHome.getContentPane().removeAll();
				ConsultarFuncionarioView consulta = new ConsultarFuncionarioView(AtualizarFuncionarioView.this.frmHome, Utilidades.CONSULTA_FUNCIONARIOS);
				AtualizarFuncionarioView.this.frmHome.getContentPane().add(consulta, BorderLayout.CENTER);
				AtualizarFuncionarioView.this.frmHome.getContentPane().revalidate();
				
			}
		});
		this.add(btnCancelar);
		
		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setBounds(300, 450, 120, 20);
		btnAtualizar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if(bo.atualizarFuncionario(validaFuncionario())){
					
					JOptionPane.showMessageDialog(AtualizarFuncionarioView.this.frmHome, "   Funcionário atualizado!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
					
				}
				else{
					
					JOptionPane.showMessageDialog(AtualizarFuncionarioView.this.frmHome, "   Não foi possível atualizar!", "Alerta!", JOptionPane.ERROR_MESSAGE);

				}
				
			}
		});
		this.add(btnAtualizar);
		
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
