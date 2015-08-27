package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ui.cliente.CadastrarClienteView;
import ui.cliente.ConsultarClienteView;
import ui.financas.ComprasView;
import ui.financas.MateriaPrimaView;
import ui.financas.ProdutoView;
import ui.financas.VendasView;
import ui.fornecedor.CadastrarFornecedorView;
import ui.fornecedor.ConsultarFornecedorView;
import ui.funcionario.CadastrarFuncionarioView;
import ui.funcionario.ConsultarFuncionarioView;
import bo.LoginBO;

public class LoginView extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JFrame frmHome;
	private JLabel labelUser;
	private JTextField txtUser;
	private JLabel labelPassword;
	private JPasswordField txtPassword;
	private JButton btnLogin;
	private Byte codUser;
	private JMenuBar menuBar;
	private JMenu mnArquivo;
	private JMenu mnFuncionario;
	private JMenu mnCliente;
	private JMenu mnFornecedor;
	private JMenu mnFinancas;
	private LoginBO bo;
	private JMenuItem mntmLogout;
	private JMenuItem mntmSair;
	private JMenuItem mntmLogin;
	private JMenuItem mntmSobre;
	private JMenuItem mntmCadastrarFuncionario;
	private JMenuItem mntmConsultarFuncionario;
	private JMenuItem mntmCadastrarCliente;
	private JMenuItem mntmConsultarCliente;
	private JMenuItem mntmCadastrarFornecedor;
	private JMenuItem mntmConsultarFornecedor;
	private JMenuItem mntmCompra;
	private JMenuItem mntmVenda;
	private JMenuItem mntmMateriaPrima;
	private JMenuItem mntmProduto;
	
	{
		bo = new LoginBO();
	}

	LoginView(JFrame frmHome){
		this.setBackground(Color.decode("#F0F8FF"));
		this.frmHome = frmHome;
		
		//criação dos campos da tela de login
		labelUser = new JLabel();
		labelUser.setText("Usuário:  ");
		labelUser.setBounds(50,50,60,50);
		
		txtUser = new JTextField();
		txtUser.setBounds(120,60,200,30);
		
		labelPassword = new JLabel();
		labelPassword.setText("Senha:  ");
		labelPassword.setBounds(50,110,60,50);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(120,120,200,30);
		
		btnLogin = new JButton();
		btnLogin.setText("Login");
		btnLogin.setBounds(170, 200, 100, 30);
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				txtUser.setText("admin");
				txtPassword.setText("admin");
				
				codUser = bo.verificaLogin(txtUser.getText(), txtPassword.getPassword());
				
				if(codUser ==  -1){ //se for inválido
					JOptionPane.showMessageDialog(LoginView.this.frmHome, "   Usuário Não Cadastrado!", "Alerta!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				//adiciona menus que todos users podem usar
				criarMenu();
				
				LoginView.this.frmHome.getContentPane().removeAll();
				LogadoView logado = new LogadoView(LoginView.this.frmHome);
				LoginView.this.frmHome.getContentPane().add(logado, BorderLayout.CENTER);
				LoginView.this.frmHome.getContentPane().revalidate();
				
				//atualiza o frame
				LoginView.this.frmHome.revalidate();				
			}
			
		});
		
		this.frmHome.add(labelUser);
		this.frmHome.add(txtUser);
		this.frmHome.add(labelPassword);
		this.frmHome.add(txtPassword);
		this.frmHome.add(btnLogin);
	}
	
	private void criarMenu(){
		
		//a barra de menu criada na HomeView é armazenada no atributo menuBar
		menuBar = LoginView.this.frmHome.getJMenuBar();
		
		//armazena o menu arquivo e remove o primeiro item dele (o item login)
		mnArquivo = this.frmHome.getJMenuBar().getMenu(0);
		mnArquivo.remove(0);
		
		//o sobre ficou no menu como primeiro item, após ele é adicionado um separador
		mnArquivo.addSeparator();
		
		// * -- MENU ARQUIVO
		//ITEM LOGOUT
		mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginView.this.frmHome.setJMenuBar(null); //remover o menubar
				LoginView.this.frmHome.getContentPane().removeAll(); //remove o conteudo do painel aberto
				AdicionarMenuDeslogado();
				LoginView loginView = new LoginView(frmHome); //abre a tela de login com o novo menu
				LoginView.this.frmHome.getContentPane().add(loginView,BorderLayout.CENTER);
				LoginView.this.frmHome.getContentPane().revalidate();
			}
		});
		mnArquivo.add(mntmLogout);
		
		//ITEM SAIR
		mntmSair = new JMenuItem("Sair do Sistema");
		mntmSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		mnArquivo.add(mntmSair);
		
		// * -- MENU FUNCIONARIO
		mnFuncionario = new JMenu("Funcionario");
		menuBar.add(mnFuncionario);
		
		//ITEM CONSULTAR FUNCIONARIO
		mntmConsultarFuncionario = new JMenuItem("Consultar");
		mntmConsultarFuncionario.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				LoginView.this.frmHome.getContentPane().removeAll();
				ConsultarFuncionarioView consultarFuncionario = new ConsultarFuncionarioView(frmHome,codUser);
				LoginView.this.frmHome.getContentPane().add(consultarFuncionario, BorderLayout.CENTER);
				LoginView.this.frmHome.getContentPane().revalidate();				
			}
		});
		mnFuncionario.add(mntmConsultarFuncionario);
		
		// * -- MENU CLIENTE
		mnCliente = new JMenu("Cliente");
		menuBar.add(mnCliente);
		
		//ITEM CADASTRAR CLIENTE
		mntmCadastrarCliente = new JMenuItem("Cadastrar");
		mntmCadastrarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginView.this.frmHome.getContentPane().removeAll();
				CadastrarClienteView cadastrarCliente = new CadastrarClienteView(frmHome);
				LoginView.this.frmHome.getContentPane().add(cadastrarCliente, BorderLayout.CENTER);
				LoginView.this.frmHome.getContentPane().revalidate();
			}
		});
		mnCliente.add(mntmCadastrarCliente);
		
		//ITEM CONSULTAR CLIENTE
		mntmConsultarCliente = new JMenuItem("Consultar");
		mntmConsultarCliente.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				LoginView.this.frmHome.getContentPane().removeAll();
				ConsultarClienteView consultarCliente = new ConsultarClienteView(frmHome);
				LoginView.this.frmHome.getContentPane().add(consultarCliente, BorderLayout.CENTER);
				LoginView.this.frmHome.getContentPane().revalidate();
			}
		});
		mnCliente.add(mntmConsultarCliente);
		
		// * -- MENU FORNECEDOR
		mnFornecedor = new JMenu("Fornecedor");
		menuBar.add(mnFornecedor);
		
		//ITEM CADASTRAR FORNECEDOR
		mntmCadastrarFornecedor = new JMenuItem("Cadastrar");
		mntmCadastrarFornecedor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				LoginView.this.frmHome.getContentPane().removeAll();
				CadastrarFornecedorView cadastrarFornecedor = new CadastrarFornecedorView(frmHome);
				LoginView.this.frmHome.getContentPane().add(cadastrarFornecedor, BorderLayout.CENTER);
				LoginView.this.frmHome.getContentPane().revalidate();				
			}
		});
		mnFornecedor.add(mntmCadastrarFornecedor);
		
		//ITEM CONSULTAR FORNECEDOR
		mntmConsultarFornecedor = new JMenuItem("Consultar");
		mntmConsultarFornecedor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginView.this.frmHome.getContentPane().removeAll();
				ConsultarFornecedorView consultarFornecedor = new ConsultarFornecedorView(frmHome);
				LoginView.this.frmHome.getContentPane().add(consultarFornecedor, BorderLayout.CENTER);
				LoginView.this.frmHome.getContentPane().revalidate();
			}
		});
		mnFornecedor.add(mntmConsultarFornecedor);
		
		// * -- MENU FINANÇAS
		mnFinancas = new JMenu("Finanças");
		menuBar.add(mnFinancas);
		
		// ITEM COMPRA
		mntmCompra = new JMenuItem("Compras");
		mntmCompra.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginView.this.frmHome.getContentPane().removeAll();
				ComprasView compras = new ComprasView(frmHome);
				LoginView.this.frmHome.getContentPane().add(compras, BorderLayout.CENTER);
				LoginView.this.frmHome.getContentPane().revalidate();
			}
		});
		mnFinancas.add(mntmCompra);
		
		// ITEM VENDA
		mntmVenda = new JMenuItem("Vendas");
		mntmVenda.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginView.this.frmHome.getContentPane().removeAll();
				VendasView vendas = new VendasView(frmHome);
				LoginView.this.frmHome.getContentPane().add(vendas, BorderLayout.CENTER);
				LoginView.this.frmHome.getContentPane().revalidate();
			}
		});
		mnFinancas.add(mntmVenda);
		mnFinancas.addSeparator();

		// ITEM MATERIA PRIMA
		mntmMateriaPrima = new JMenuItem("Materia Prima");
		mntmMateriaPrima.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginView.this.frmHome.getContentPane().removeAll();
				MateriaPrimaView materiaPrima = new MateriaPrimaView(frmHome);
				LoginView.this.frmHome.getContentPane().add(materiaPrima, BorderLayout.CENTER);
				LoginView.this.frmHome.getContentPane().revalidate();
			}
		});
		mnFinancas.add(mntmMateriaPrima);

		// ITEM PRODUTO
		mntmProduto = new JMenuItem("Produto");
		mntmProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginView.this.frmHome.getContentPane().removeAll();
				ProdutoView produto = new ProdutoView(frmHome);
				LoginView.this.frmHome.getContentPane().add(produto, BorderLayout.CENTER);
				LoginView.this.frmHome.getContentPane().revalidate();
			}
		});

		mnFinancas.add(mntmProduto);
		
		//adiciona menus do administrador
		if(codUser == 1){
			criarMenuAdministrador();
		}
		
	}
	
	private void criarMenuAdministrador(){
		
		// * - MENU ARQUIVO
		//ITEM CADASTRAR FUNCIONARIO		
		mntmCadastrarFuncionario = new JMenuItem("Cadastrar");
		mntmCadastrarFuncionario.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				LoginView.this.frmHome.getContentPane().removeAll();
				CadastrarFuncionarioView cadastrarFuncionario = new CadastrarFuncionarioView(frmHome);
				LoginView.this.frmHome.getContentPane().add(cadastrarFuncionario, BorderLayout.CENTER);
				LoginView.this.frmHome.getContentPane().revalidate();
			}
		});
		mnFuncionario.add(mntmCadastrarFuncionario,0);//adiciona na primeira posição
				
	}
	
	private void AdicionarMenuDeslogado(){
		
		menuBar = new JMenuBar();
		frmHome.setJMenuBar(menuBar);
		
		mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);
		
		mntmLogin = new JMenuItem("Login");
		mnArquivo.add(mntmLogin);
		
		mntmSobre = new JMenuItem("Sobre");
		mntmSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		mnArquivo.add(mntmSobre);
		
	}

}
