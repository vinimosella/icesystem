package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.jdesktop.swingx.prompt.PromptSupport;

import ui.cliente.ManterClienteView;
import ui.estoque.ManterMateriaPrimaView;
import ui.estoque.ManterOrdemDeProducaoView;
import ui.estoque.ManterProdutoView;
import ui.financas.ManterComprasView;
import ui.financas.ManterVendasView;
import ui.fornecedor.ManterFornecedorView;
import ui.funcionario.ManterFuncionarioView;
import util.Utilidades;
import bo.LoginBO;

public class LoginView extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JTextField txtUser;
	private JPasswordField txtPassword;
	private JButton btnLogin;
	private JMenuBar menuBar;
	private JMenu mnArquivo;
	private JMenu mnPessoas;
	private JMenu mnEstoque;
	private LoginBO bo;
	private JMenuItem mntmAlterarSenha;
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
		txtUser = new JTextField();
		txtUser.setBounds(200,170,200,30);
		txtUser.setText("Usuário");
		txtUser.setForeground(Color.gray);
		txtUser.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(txtUser.getText().equals("Usuário")){
					
					txtUser.setText("");
					txtUser.setForeground(Color.BLACK);
				}
				
			}
			
		});
		txtUser.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {

				if(txtUser.getText().trim().equals("")){
					
					txtUser.setText("Usuário");
					txtUser.setForeground(Color.gray);
				}
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {

				if(txtUser.getText().equals("Usuário")){
					
					txtUser.setCaretPosition(0);
				}
			}
		});
		txtUser.addCaretListener(new CaretListener() {
			
			@Override
			public void caretUpdate(CaretEvent e) {

				if(e.getDot()!=0 && txtUser.getText().equals("Usuário")){
					
					txtUser.setCaretPosition(0);
				}
			}
		});
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(200,230,200,30);
		PromptSupport.setBackground(txtUser.getBackground(), txtPassword);
		PromptSupport.setForeground(Color.gray, txtPassword);
		PromptSupport.setPrompt("Senha", txtPassword);
		
		btnLogin = new JButton(new ImageIcon(getClass().getResource("/img/login.png")));
		btnLogin.setText("Login");
		btnLogin.setBounds(250, 300, 100, 30);
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
								
				Utilidades.funcionarioLogado = bo.verificaLogin(txtUser.getText(), new String(txtPassword.getPassword()));
				
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
		
		this.add(txtUser);
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
		//ITEM ALTERAR SENHA
		mntmAlterarSenha = new JMenuItem("Alterar Senha");
		mntmAlterarSenha.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				AlterarSenhaView ats = new AlterarSenhaView();
				ats.setVisible(true);
				
			}
		});
		mnArquivo.add(mntmAlterarSenha,2);
		
		mnArquivo.insertSeparator(3);
		
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
		mnArquivo.add(mntmLogout,4);//coloca entre o sobre/sair e depois do separator
		
		// * -- MENU PESSOAS
		mnPessoas = new JMenu("Pessoas");
		menuBar.add(mnPessoas);
		
		if(Utilidades.funcionarioLogado.getCargo().getFuncao().trim().equals(Utilidades.CARGO_ACESSO_GERENTE)){
			//ITEM FUNCIONARIO
			mntmFuncionario = new JMenuItem("Funcionário");
			mntmFuncionario.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Utilidades.frmHome.getContentPane().removeAll();
					ManterFuncionarioView consultarFuncionario = new ManterFuncionarioView();
					Utilidades.frmHome.getContentPane().add(consultarFuncionario, BorderLayout.CENTER);
					Utilidades.frmHome.getContentPane().revalidate();				
				}
			});
			mnPessoas.add(mntmFuncionario);
		}
		
		//ITEM CLIENTE
		mntmCliente = new JMenuItem("Cliente");
		mntmCliente.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Utilidades.frmHome.getContentPane().removeAll();
				ManterClienteView consultarCliente = new ManterClienteView();
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
				ManterFornecedorView consultarFornecedor = new ManterFornecedorView();
				Utilidades.frmHome.getContentPane().add(consultarFornecedor, BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();
			}
		});
		mnPessoas.add(mntmFornecedor);
		
		// * -- MENU PRODUÇÃO
		mnEstoque = new JMenu("Estoque");
		menuBar.add(mnEstoque);
		
		// ITEM CONSULTAR ORDEM PRODUCAO
		mntmOrdensProducao = new JMenuItem("Ordem de Produção");
		mntmOrdensProducao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Utilidades.frmHome.getContentPane().removeAll();
				ManterOrdemDeProducaoView op = new ManterOrdemDeProducaoView();
				Utilidades.frmHome.getContentPane().add(op,BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();
			}
		});

		mnEstoque.add(mntmOrdensProducao);
		
		mnEstoque.addSeparator();
		
		// ITEM MATERIA PRIMA
		mntmMateriaPrima = new JMenuItem("Matérias Primas");
		mntmMateriaPrima.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Utilidades.frmHome.getContentPane().removeAll();
				ManterMateriaPrimaView materiaPrima = new ManterMateriaPrimaView();
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
				ManterProdutoView produto = new ManterProdutoView();
				Utilidades.frmHome.getContentPane().add(produto,BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();
			}
		});

		mnEstoque.add(mntmProduto);
		
		mnEstoque.addSeparator();
				
		// ITEM CONSULTAR COMPRA
		mntmCompra = new JMenuItem("Compras");
		mntmCompra.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Utilidades.frmHome.getContentPane().removeAll();
				ManterComprasView consCompras = new ManterComprasView();
				Utilidades.frmHome.getContentPane().add(consCompras, BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();
			}
		});
		mnEstoque.add(mntmCompra);
		
		// ITEM CONSULTAR VENDA
		mntmVenda = new JMenuItem("Vendas");
		mntmVenda.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Utilidades.frmHome.getContentPane().removeAll();
				ManterVendasView consVendas = new ManterVendasView();
				Utilidades.frmHome.getContentPane().add(consVendas, BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();
			}
		});
		mnEstoque.add(mntmVenda);		
		
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
