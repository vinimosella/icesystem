package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

import ui.cliente.ConsultarClienteView;
import ui.estoque.ConsultaMateriaPrimaView;
import ui.estoque.ConsultaProdutoView;
import ui.estoque.OrdemDeProducaoView;
import ui.financas.ConsultarComprasView;
import ui.financas.ConsultarVendasView;
import ui.fornecedor.ConsultarFornecedorView;
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
	private JMenu mnPessoas;
	private JMenu mnEstoque;
	private JMenu mnFinancas;
	private LoginBO bo;
	private JMenuItem mntmLogout;
	private JMenuItem mntmSobre;
	private JMenuItem mntmSair;
	private JMenuItem mntmFuncionario;
	private JMenuItem mntmCliente;
	private JMenuItem mntmFornecedor;
	private JMenuItem mntmCompra;
	private JMenuItem mntmVenda;
	private JMenuItem mntmMateriaPrima;
	private JMenuItem mntmProduto;
	private JMenuItem mntmOrdensProducao;
	private ImageIcon fundo = new ImageIcon(getClass().getResource("/img/biometria.jpg"));  

	{
		bo = new LoginBO();
	}

	LoginView(){
		this.setLayout(null);
		this.setBackground(Color.decode("#F0F8FF"));
		
		//criação dos campos da tela de login
		labelUser = new JLabel();
		labelUser.setText("Usuário:");
		labelUser.setForeground(Color.BLACK);
		labelUser.setBackground(Color.GRAY);
		labelUser.setFont(new Font("Arial", Font.BOLD, 20));  
		labelUser.setBounds(120,160,120,50);
		
		txtUser = new JTextField();
		txtUser.setBounds(220,170,200,30);
		
		//ficar com foco no txtUser ao abrir a janela
		javax.swing.SwingUtilities.invokeLater(new Runnable() {  
		    public void run() {  
		    	txtUser.requestFocusInWindow();  
		    }  
		});  
		
		labelPassword = new JLabel();
		labelPassword.setText("Senha:");
		labelPassword.setForeground(Color.BLACK);
		labelPassword.setFont(new Font("Dialog", Font.BOLD, 20)); 
		labelPassword.setBounds(120,220,120,50);
		
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
				
				//USUARIO REALIZOU LOGIN - adiciona menus
				criarMenu();
				
				Utilidades.frmHome.getContentPane().removeAll();
				LogadoView logado = new LogadoView();
				Utilidades.frmHome.getContentPane().add(logado, BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();
				
				//atualiza o frame
				Utilidades.frmHome.revalidate();				
			}
			
		});
		
		this.add(labelUser);
		this.add(txtUser);
		this.add(labelPassword);
		this.add(txtPassword);
		this.add(btnLogin);
		Utilidades.frmHome.getRootPane().setDefaultButton(btnLogin); //logar quando apertar enter

	}
	
	private void criarMenu(){
		
		//a barra de menu criada na HomeView é armazenada no atributo
		menuBar = Utilidades.frmHome.getJMenuBar();
		
		//o menu arquivo criado na HomeView é armazenada no atributo
		mnArquivo = Utilidades.frmHome.getJMenuBar().getMenu(0);
				
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
				Utilidades.frmHome.revalidate();
			}
		});
		mnArquivo.add(mntmLogout,2);//coloca entre o sobre/sair e depois do separator
		
		// * -- MENU PESSOAS
		mnPessoas = new JMenu("Pessoas");
		menuBar.add(mnPessoas);
		
		//ITEM FUNCIONARIO
		mntmFuncionario = new JMenuItem("Funcionário");
		mntmFuncionario.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Utilidades.frmHome.getContentPane().removeAll();
				ConsultarFuncionarioView consultarFuncionario = new ConsultarFuncionarioView();
				Utilidades.frmHome.getContentPane().add(consultarFuncionario, BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();				
			}
		});
		mnPessoas.add(mntmFuncionario);
		
		//ITEM CLIENTE
		mntmCliente = new JMenuItem("Cliente");
		mntmCliente.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Utilidades.frmHome.getContentPane().removeAll();
				ConsultarClienteView consultarCliente = new ConsultarClienteView();
				Utilidades.frmHome.getContentPane().add(consultarCliente, BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();
			}
		});
		mnPessoas.add(mntmCliente);
		
		//ITEM FORNECEDOR
		mntmFornecedor = new JMenuItem("Fornecedor");
		mntmFornecedor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Utilidades.frmHome.getContentPane().removeAll();
				ConsultarFornecedorView consultarFornecedor = new ConsultarFornecedorView();
				Utilidades.frmHome.getContentPane().add(consultarFornecedor, BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();
			}
		});
		mnPessoas.add(mntmFornecedor);
		
		// * -- MENU PRODUÇÃO
		mnEstoque = new JMenu("Estoque");
		menuBar.add(mnEstoque);
		
		// ITEM MATERIA PRIMA
		mntmMateriaPrima = new JMenuItem("Matérias Primas");
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
		mntmProduto = new JMenuItem("Produtos");
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
		
		// ITEM CONSULTAR ORDEM PRODUCAO
		mntmOrdensProducao = new JMenuItem("Ordem de Produção");
		mntmOrdensProducao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Utilidades.frmHome.getContentPane().removeAll();
				OrdemDeProducaoView op = new OrdemDeProducaoView();
				Utilidades.frmHome.getContentPane().add(op,BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();
			}
		});

		mnEstoque.add(mntmOrdensProducao);
		
		// * -- MENU FINANÇAS
		mnFinancas = new JMenu("Finanças");
		menuBar.add(mnFinancas);
		
		// ITEM CONSULTAR COMPRA
		mntmCompra = new JMenuItem("Compras");
		mntmCompra.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Utilidades.frmHome.getContentPane().removeAll();
				ConsultarComprasView consCompras = new ConsultarComprasView();
				Utilidades.frmHome.getContentPane().add(consCompras, BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();
			}
		});
		mnFinancas.add(mntmCompra);
		
		//SEPARADOR
		mnFinancas.addSeparator();
		
		// ITEM CONSULTAR VENDA
		mntmVenda = new JMenuItem("Vendas");
		mntmVenda.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Utilidades.frmHome.getContentPane().removeAll();
				ConsultarVendasView consVendas = new ConsultarVendasView();
				Utilidades.frmHome.getContentPane().add(consVendas, BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();
			}
		});
		mnFinancas.add(mntmVenda);		
		
	}
	
	private void AdicionarMenuDeslogado(){
				
		menuBar = new JMenuBar();
		Utilidades.frmHome.setJMenuBar(menuBar);
		
		mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);
		
		mntmSobre = new JMenuItem("Sobre");
		mntmSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		mnArquivo.add(mntmSobre);
		
		mnArquivo.addSeparator();
		
		//ITEM SAIR
		mntmSair = new JMenuItem("Sair do Sistema");
		mntmSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		mnArquivo.add(mntmSair);
		
	}
		  
	@Override  
	protected void paintComponent(Graphics g) {  
	    super.paintComponent(g);  
	    Graphics2D g2d = (Graphics2D) g.create();  
	    g2d.drawImage(fundo.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);  
	    g2d.dispose();  
	}

}
