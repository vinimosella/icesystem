package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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

	LoginView(){
		this.setBackground(Color.decode("#F0F8FF"));
		
		//criação dos campos da tela de login
		labelUser = new JLabel();
		labelUser.setText("Usuário:  ");
		labelUser.setBounds(150,160,60,50);
		
		txtUser = new JTextField();
		txtUser.setBounds(220,170,200,30);
		
		//ficar com foco no txtUser ao abrir a janela
		javax.swing.SwingUtilities.invokeLater(new Runnable() {  
		    public void run() {  
		    	txtUser.requestFocusInWindow();  
		    }  
		});  
		
		labelPassword = new JLabel();
		labelPassword.setText("Senha:  ");
		labelPassword.setBounds(150,220,60,50);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(220,230,200,30);
		
		btnLogin = new JButton(new ImageIcon(getClass().getResource("/img/login.png")));
		btnLogin.setText("Login");
		btnLogin.setBounds(250, 300, 100, 30);
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				txtUser.setText("admin");
				txtPassword.setText("admin");
				
				Utilidades.funcionarioLogado = bo.verificaLogin(txtUser.getText(), txtPassword.getPassword());
				
				if(Utilidades.funcionarioLogado==null){ //se for inválido
					JOptionPane.showMessageDialog(Utilidades.frmHome, "   Usuário Não Cadastrado!", "Alerta!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				//adiciona menus que todos users podem usar
				criarMenu();
				
				Utilidades.frmHome.getContentPane().removeAll();
				LogadoView logado = new LogadoView(Utilidades.frmHome);
				Utilidades.frmHome.getContentPane().add(logado, BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();
				
				//atualiza o frame
				Utilidades.frmHome.revalidate();				
			}
			
		});
		
		Utilidades.frmHome.add(labelUser);
		Utilidades.frmHome.add(txtUser);
		Utilidades.frmHome.add(labelPassword);
		Utilidades.frmHome.add(txtPassword);
		Utilidades.frmHome.add(btnLogin);
		Utilidades.frmHome.getRootPane().setDefaultButton(btnLogin); //logar quando apertar enter

	}
	
	private void criarMenu(){
		
		//a barra de menu criada na HomeView é armazenada no atributo menuBar
		menuBar = Utilidades.frmHome.getJMenuBar();
		
		//armazena o menu arquivo e remove o primeiro item dele (o item login)
		mnArquivo = Utilidades.frmHome.getJMenuBar().getMenu(0);
		mnArquivo.remove(0);
		
		//o sobre ficou no menu como primeiro item, após ele é adicionado um separador
		mnArquivo.addSeparator();
		
		// * -- MENU ARQUIVO
		//ITEM LOGOUT
		mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Utilidades.funcionarioLogado=null; //remove o funcionario logado
				Utilidades.frmHome.setJMenuBar(null); //remover o menubar
				Utilidades.frmHome.getContentPane().removeAll(); //remove o conteudo do painel aberto
				AdicionarMenuDeslogado();
				LoginView loginView = new LoginView(); //abre a tela de login com o novo menu
				Utilidades.frmHome.getContentPane().add(loginView,BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();
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
				Utilidades.frmHome.getContentPane().removeAll();
				ConsultarFuncionarioView consultarFuncionario = new ConsultarFuncionarioView(Utilidades.CONSULTA_FUNCIONARIOS);
				Utilidades.frmHome.getContentPane().add(consultarFuncionario, BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();				
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
				Utilidades.frmHome.getContentPane().removeAll();
				CadastrarClienteView cadastrarCliente = new CadastrarClienteView();
				Utilidades.frmHome.getContentPane().add(cadastrarCliente, BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();
			}
		});
		mnCliente.add(mntmCadastrarCliente);
		
		//ITEM CONSULTAR CLIENTE
		mntmConsultarCliente = new JMenuItem("Consultar");
		mntmConsultarCliente.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Utilidades.frmHome.getContentPane().removeAll();
				ConsultarClienteView consultarCliente = new ConsultarClienteView(Utilidades.CONSULTA_CLIENTES);
				Utilidades.frmHome.getContentPane().add(consultarCliente, BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();
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
				Utilidades.frmHome.getContentPane().removeAll();
				CadastrarFornecedorView cadastrarFornecedor = new CadastrarFornecedorView();
				Utilidades.frmHome.getContentPane().add(cadastrarFornecedor, BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();				
			}
		});
		mnFornecedor.add(mntmCadastrarFornecedor);
		
		//ITEM CONSULTAR FORNECEDOR
		mntmConsultarFornecedor = new JMenuItem("Consultar");
		mntmConsultarFornecedor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Utilidades.frmHome.getContentPane().removeAll();
				ConsultarFornecedorView consultarFornecedor = new ConsultarFornecedorView(Utilidades.CONSULTA_FORNECEDORES);
				Utilidades.frmHome.getContentPane().add(consultarFornecedor, BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();
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
				Utilidades.frmHome.getContentPane().removeAll();
				ConsultaMateriaPrimaView materiaPrima = new ConsultaMateriaPrimaView();
				Utilidades.frmHome.getContentPane().add(materiaPrima,BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();
			}
		});
		mnEstoque.add(mntmMateriaPrima);

		// ITEM PRODUTO
		mntmProduto = new JMenuItem("Consulta Produtos");
		mntmProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Utilidades.frmHome.getContentPane().removeAll();
				ConsultaProdutoView produto = new ConsultaProdutoView();
				Utilidades.frmHome.getContentPane().add(produto,BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();
			}
		});

		mnEstoque.add(mntmProduto);

		mnEstoque.addSeparator();

		// ITEM PRODUZIR
		mntmProduzir = new JMenuItem("Produzir");
		mntmProduzir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Utilidades.frmHome.getContentPane().removeAll();
				ProduzirView produzir = new ProduzirView();
				Utilidades.frmHome.getContentPane().add(produzir,BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();
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
				Utilidades.frmHome.getContentPane().removeAll();
				ConsultarComprasView consCompras = new ConsultarComprasView(Utilidades.CONSULTA_COMPRAS);
				Utilidades.frmHome.getContentPane().add(consCompras, BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();
			}
		});
		mnFinancas.add(mntmConsultarCompra);
		
		// ITEM EFETUAR COMPRA
		mntmEfetuarCompra = new JMenuItem("Adicionar Compras");
		mntmEfetuarCompra.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Utilidades.frmHome.getContentPane().removeAll();
				CompraMateriaPrimaView efetCompras = new CompraMateriaPrimaView(null);
				Utilidades.frmHome.getContentPane().add(efetCompras, BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();
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
				Utilidades.frmHome.getContentPane().removeAll();
				ConsultarVendasView consVendas = new ConsultarVendasView(Utilidades.CONSULTA_VENDAS);
				Utilidades.frmHome.getContentPane().add(consVendas, BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();
			}
		});
		mnFinancas.add(mntmConsultarVenda);
		
		// ITEM EFETUAR VENDA
		mntmEfetuarVenda = new JMenuItem("Adicionar Vendas");
		mntmEfetuarVenda.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Utilidades.frmHome.getContentPane().removeAll();
				VendaProdutoView efetVendas = new VendaProdutoView();
				Utilidades.frmHome.getContentPane().add(efetVendas, BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();
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
				Utilidades.frmHome.getContentPane().removeAll();
				CadastrarFuncionarioView cadastrarFuncionario = new CadastrarFuncionarioView();
				Utilidades.frmHome.getContentPane().add(cadastrarFuncionario, BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();
			}
		});
		mnFuncionario.add(mntmCadastrarFuncionario,0);//adiciona na primeira posição
				
	}
	
	private void AdicionarMenuDeslogado(){
		
		menuBar = new JMenuBar();
		Utilidades.frmHome.setJMenuBar(menuBar);
		
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
