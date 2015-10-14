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
import ui.estoque.ConsultaMateriaPrimaView;
import ui.estoque.ConsultaProdutoView;
import ui.estoque.ProduzirView;
import ui.financas.CompraMateriaPrimaView;
import ui.financas.ConsultarComprasView;
import ui.financas.ConsultarVendasView;
import ui.financas.VendaProdutoView;
import ui.fornecedor.CadastrarFornecedorView;
import ui.fornecedor.ConsultarFornecedorView;
import ui.funcionario.CadastrarFuncionarioView;
import ui.funcionario.ConsultarFuncionarioView;
import util.Utilidades;
import bo.LoginBO;

public class LoginView extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JFrame frmHome;
	private JLabel labelUser;
	private JTextField txtUser;
	private JLabel labelPassword;
	private JPasswordField txtPassword;
	private JButton btnLogin;
	private JMenuBar menuBar;
	private JMenu mnArquivo;
	private JMenu mnFuncionario;
	private JMenu mnCliente;
	private JMenu mnFornecedor;
	private JMenu mnEstoque;
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
	private JMenuItem mntmConsultarCompra;
	private JMenuItem mntmEfetuarCompra;
	private JMenuItem mntmConsultarVenda;
	private JMenuItem mntmEfetuarVenda;
	private JMenuItem mntmMateriaPrima;
	private JMenuItem mntmProduto;
	private JMenuItem mntmProduzir;
	
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
		
		//ficar com foco no txtUser ao abrir a janela
		javax.swing.SwingUtilities.invokeLater(new Runnable() {  
		    public void run() {  
		    	txtUser.requestFocusInWindow();  
		    }  
		});  
		
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
				
				Utilidades.funcionarioLogado = bo.verificaLogin(txtUser.getText(), txtPassword.getPassword());
				
				if(Utilidades.funcionarioLogado==null){ //se for inválido
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
		this.frmHome.getRootPane().setDefaultButton(btnLogin); //logar quando apertar enter

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
				Utilidades.funcionarioLogado=null; //remove o funcionario logado
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
				ConsultarFuncionarioView consultarFuncionario = new ConsultarFuncionarioView(frmHome,Utilidades.CONSULTA_FUNCIONARIOS);
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
				ConsultarClienteView consultarCliente = new ConsultarClienteView(frmHome, Utilidades.CONSULTA_CLIENTES);
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
				ConsultarFornecedorView consultarFornecedor = new ConsultarFornecedorView(frmHome, Utilidades.CONSULTA_FORNECEDORES);
				LoginView.this.frmHome.getContentPane().add(consultarFornecedor, BorderLayout.CENTER);
				LoginView.this.frmHome.getContentPane().revalidate();
			}
		});
		mnFornecedor.add(mntmConsultarFornecedor);
		
		// * -- MENU PRODUÇÃO
		mnEstoque = new JMenu("Estoque");
		menuBar.add(mnEstoque);
		
		// ITEM MATERIA PRIMA
		mntmMateriaPrima = new JMenuItem("Consulta Materias Primas");
		mntmMateriaPrima.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginView.this.frmHome.getContentPane().removeAll();
				ConsultaMateriaPrimaView materiaPrima = new ConsultaMateriaPrimaView(frmHome);
				LoginView.this.frmHome.getContentPane().add(materiaPrima,BorderLayout.CENTER);
				LoginView.this.frmHome.getContentPane().revalidate();
			}
		});
		mnEstoque.add(mntmMateriaPrima);

		// ITEM PRODUTO
		mntmProduto = new JMenuItem("Consulta Produtos");
		mntmProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginView.this.frmHome.getContentPane().removeAll();
				ConsultaProdutoView produto = new ConsultaProdutoView(frmHome);
				LoginView.this.frmHome.getContentPane().add(produto,BorderLayout.CENTER);
				LoginView.this.frmHome.getContentPane().revalidate();
			}
		});

		mnEstoque.add(mntmProduto);

		mnEstoque.addSeparator();

		// ITEM PRODUZIR
		mntmProduzir = new JMenuItem("Produzir");
		mntmProduzir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginView.this.frmHome.getContentPane().removeAll();
				ProduzirView produzir = new ProduzirView(frmHome);
				LoginView.this.frmHome.getContentPane().add(produzir,BorderLayout.CENTER);
				LoginView.this.frmHome.getContentPane().revalidate();
			}
		});

		mnEstoque.add(mntmProduzir);
		
		// * -- MENU FINANÇAS
		mnFinancas = new JMenu("Finanças");
		menuBar.add(mnFinancas);
		
		// ITEM CONSULTAR COMPRA
		mntmConsultarCompra = new JMenuItem("Consultar Compras");
		mntmConsultarCompra.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginView.this.frmHome.getContentPane().removeAll();
				ConsultarComprasView consCompras = new ConsultarComprasView(frmHome,Utilidades.CONSULTA_COMPRAS);
				LoginView.this.frmHome.getContentPane().add(consCompras, BorderLayout.CENTER);
				LoginView.this.frmHome.getContentPane().revalidate();
			}
		});
		mnFinancas.add(mntmConsultarCompra);
		
		// ITEM EFETUAR COMPRA
		mntmEfetuarCompra = new JMenuItem("Adicionar Compras");
		mntmEfetuarCompra.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginView.this.frmHome.getContentPane().removeAll();
				CompraMateriaPrimaView efetCompras = new CompraMateriaPrimaView(frmHome,null);
				LoginView.this.frmHome.getContentPane().add(efetCompras, BorderLayout.CENTER);
				LoginView.this.frmHome.getContentPane().revalidate();
			}
		});
		mnFinancas.add(mntmEfetuarCompra);
		
		//SEPARADOR
		mnFinancas.addSeparator();
		
		// ITEM CONSULTAR VENDA
		mntmConsultarVenda = new JMenuItem("Consultar Vendas");
		mntmConsultarVenda.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginView.this.frmHome.getContentPane().removeAll();
				ConsultarVendasView consVendas = new ConsultarVendasView(frmHome, Utilidades.CONSULTA_VENDAS);
				LoginView.this.frmHome.getContentPane().add(consVendas, BorderLayout.CENTER);
				LoginView.this.frmHome.getContentPane().revalidate();
			}
		});
		mnFinancas.add(mntmConsultarVenda);
		
		// ITEM EFETUAR VENDA
		mntmEfetuarVenda = new JMenuItem("Adicionar Vendas");
		mntmEfetuarVenda.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginView.this.frmHome.getContentPane().removeAll();
				VendaProdutoView efetVendas = new VendaProdutoView(frmHome);
				LoginView.this.frmHome.getContentPane().add(efetVendas, BorderLayout.CENTER);
				LoginView.this.frmHome.getContentPane().revalidate();
			}
		});
		mnFinancas.add(mntmEfetuarVenda);		
		
		// adiciona menus do administrador
		if (Utilidades.funcionarioLogado.getCargo().getIdCargo() == 1) {
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
