package ui.financas;

import java.awt.BorderLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import util.Utilidades;
import vo.ClienteVO;
import vo.ItemVendaVO;
import vo.ProdutoVO;
import vo.VendaVO;
import bo.ClienteBO;
import bo.ProdutoBO;
import bo.VendaBO;

public class VendaProdutoView extends JPanel{

	private static final long serialVersionUID = 1L;
	private ClienteBO clienteBo;
	private ProdutoBO produtoBo;
	private VendaBO vendaBo;
	private JComboBox<String> comboCliente;
	private List<ClienteVO> listaClientes;
	private JComboBox<String> comboProduto;
	private List<ProdutoVO> listaProdutos;
	private ClienteVO cliente;
	private ProdutoVO produto;
	private JLabel lblQuantidade;
	private JTextField txtQuantidade;
	private JLabel lblValor;
	private JTextField txtValor;
	private JButton btnAdicionar;
	private List<ItemVendaVO> listaItensVenda;
	private ItemVendaVO itemVenda;
	private VendaVO venda;
	private Iterator<?> it;
	private StringBuilder msg;
	private JLabel lblItensVenda;
	private JTable tabela;
	private JScrollPane scrollPane;
	private DefaultTableModel dtm;
	private JLabel lblTotal;
	private Double total;
	private JButton btnRemover;
	private JButton btnCadastrar;
	
	{
		clienteBo = new ClienteBO();
		produtoBo = new ProdutoBO();
		vendaBo = new VendaBO();
		venda = new VendaVO();
		listaItensVenda = new ArrayList<ItemVendaVO>();		
	}
	
	public VendaProdutoView(){
		
		this.setLayout(null);
		this.setBackground(Color.decode("#F0F8FF"));

		// COMBO CLIENTE
		comboCliente = new JComboBox<String>();
		comboCliente.setBounds(30, 30, 300, 25);
		comboCliente.addItem("Selecione um cliente");

		listaClientes = clienteBo.consultarClientes();
		it = listaClientes.iterator();

		while (it.hasNext()) {

			cliente = (ClienteVO) it.next();
			comboCliente.addItem(cliente.getRazaoSocial());

		}
		this.add(comboCliente);

		// COMBO PRODUTOS
		comboProduto = new JComboBox<String>();
		comboProduto.setBounds(30, 75, 300, 25);
		comboProduto.addItem("Selecione o Produto");
		
		listaProdutos = produtoBo.consultarProdutos();
		it = listaProdutos.iterator();
		
		
		while(it.hasNext()){
			
			produto = (ProdutoVO) it.next();
			
			if(produto.getSabor() == null){
				comboProduto.addItem(produto.getNome());
			}
			else{
				comboProduto.addItem(produto.getNome() + " - " + produto.getSabor());
			}
			
		}
		
		this.add(comboProduto);

		// CAMPO QUANTIDADE
		lblQuantidade = new JLabel("Quantidade: ");
		lblQuantidade.setBounds(30,120,100,25);
		this.add(lblQuantidade);

		txtQuantidade = new JTextField();
		txtQuantidade.setBounds(120,120,60,25);
		this.add(txtQuantidade);

		// CAMPO VALOR
		lblValor = new JLabel("Valor Unit�rio: ");
		lblValor.setBounds(220,120,100,25);
		this.add(lblValor);

		txtValor = new JTextField();
		txtValor.setBounds(310,120,60,25);
		this.add(txtValor);

		// BOTAO ADICIONAR
		btnAdicionar = new JButton(new ImageIcon(getClass().getResource("/img/addLista.png")));
		btnAdicionar.setBounds(400,120,26,27);
		btnAdicionar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(validaCamposPraAdicionarItem()){
				
					//se for o primeiro item � preciso informar o cliente da venda e desabilitar o combo de clientes
					if(listaItensVenda.size() == 0){
						
						venda.setCliente(listaClientes.get(comboCliente.getSelectedIndex()-1));
						comboCliente.setEnabled(false);				
						btnCadastrar.setEnabled(true);
						
					}
					
					adicionarItemVenda();
					carregaDtm();
					somaTotal();					
				}
				
			}
		});
		this.add(btnAdicionar);

		// LABEL TABELA ITENS VENDA
		lblItensVenda = new JLabel("Itens da venda: ");
		lblItensVenda.setBounds(20,165,100,25);
		this.add(lblItensVenda);

		// BOT�O REMOVER ITEM DA VENDA
		btnRemover = new JButton("Remover item selecionado", new ImageIcon(getClass().getResource("/img/remover.png")));
		btnRemover.setBounds(359,165,210,25);
		btnRemover.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// se tiver algo selecionado
				if (tabela.getSelectedRow() != -1) {
					devolveEstoque();
					listaItensVenda.remove(tabela.getSelectedRow());
					carregaDtm();
					somaTotal();
				}
				// se a lista de itens estiver vazia, a troca de cliente � habilitada e nao e possivel cadastrar
				if (listaItensVenda.size() == 0) {
					comboCliente.setEnabled(true);
					btnCadastrar.setEnabled(false);
				}
			}

		});
		this.add(btnRemover);

		// TABELA ITENS venda
		tabela = new JTable();

		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 200, 550, 300);
		this.add(scrollPane);
		carregaDtm();
		scrollPane.setViewportView(tabela);

		// LABEL TOTAL DA VENDA
		lblTotal = new JLabel("Total: 0");
		lblTotal.setBounds(20,510, 100, 25);
		this.add(lblTotal);

		// BOT�O CADASTRAR VENDA
		btnCadastrar = new JButton("Cadastrar Venda");
		btnCadastrar.setBounds(200,510,160,25);
		btnCadastrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(vendaBo.cadastrarVenda(venda,listaItensVenda)){
					
					Utilidades.frmHome.getContentPane().removeAll();
					ConsultarVendasView consVendas = new ConsultarVendasView();
					Utilidades.frmHome.getContentPane().add(consVendas, BorderLayout.CENTER);
					Utilidades.frmHome.getContentPane().revalidate();
				}
			}
		});
		btnCadastrar.setEnabled(false);
		this.add(btnCadastrar);

	}

	protected void devolveEstoque() {

		ProdutoVO produto = listaItensVenda.get(tabela.getSelectedRow()).getProduto();
		
		for(ProdutoVO produtoLista : listaProdutos){
			
			if(produto.getIdProduto() == produtoLista.getIdProduto()){
				
				produtoLista.setQuantidadeEstoque(produtoLista.getQuantidadeEstoque()+listaItensVenda.get(tabela.getSelectedRow()).getQuantidade());
				break;
			}
			
		}
		
	}

	private void carregaDtm() {

		dtm = new DefaultTableModel(

		new Object[][] {

		}, new String[] { "Cliente", "Nome-Produto", "Sabor", "Quantidade",
				"Valor", "Valor Total" }) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {

				return false;
			}

		};

		if (listaItensVenda.size() == 0) {
			tabela.setModel(dtm);
			return;
		}

		it = listaItensVenda.iterator();

		while (it.hasNext()) {

			itemVenda = (ItemVendaVO) it.next();

			dtm.addRow(new Object[] {
					
					venda.getCliente().getRazaoSocial(),
					itemVenda.getProduto().getNome(),
					(itemVenda.getProduto().getSabor() != null) ? itemVenda.getProduto().getSabor() : "-",
					itemVenda.getQuantidade(),
					Utilidades.FORMAT.format(itemVenda.getValor()),
					Utilidades.FORMAT.format(itemVenda.getQuantidade()* itemVenda.getValor()) });

		}

		tabela.setModel(dtm);

	}
	
	private boolean validaCamposPraAdicionarItem(){
		
		msg = new StringBuilder();

		// CARREGA MENSAGEM SE ALGO EST� INV�LIDO
		if (comboCliente.getSelectedIndex() == 0) {

			msg.append("Selecione o Cliente\n");
		}

		if (comboProduto.getSelectedIndex() == 0) {

			msg.append("Selecione o Produto\n");
		}		

		if (txtQuantidade.getText().equals("")) {

			msg.append("Indique a quantidade\n");
		}

		if (txtValor.getText().equals("")) {

			msg.append("Indique o valor unit�rio\n");
		}
		
		//verifica se tudo foi preenchido corretamente pra poder validar o estoque
		if(msg.toString().trim().equals("")){
			
			//valida se a quantidade est� dispon�vel no estoque
			if(listaProdutos.get(comboProduto.getSelectedIndex()-1).getQuantidadeEstoque() < Integer.parseInt(txtQuantidade.getText())){
				
				msg.append("Quantidade dispon�vel insuficiente! (dispon�vel: "+listaProdutos.get(comboProduto.getSelectedIndex()-1).getQuantidadeEstoque()+")\n");
			}
			
		}

		// VERIFICA SE A MENSAGEM N�O EST� VAZIA PARA EXIBI-LA
		if (!msg.toString().trim().equals("")) {

			JOptionPane.showMessageDialog(Utilidades.frmHome, msg.toString(),"N�o foi poss�vel adicionar item!",JOptionPane.ERROR_MESSAGE);
			
			return false;
		} 
		
		return true;
	}

	private void adicionarItemVenda() {
				
		it = listaItensVenda.iterator();
		
		ProdutoVO produtoHelper = listaProdutos.get(comboProduto.getSelectedIndex() - 1);

		boolean itemEstaNaTabela = false;
		// verifica se o item ja esta na lista, se estiver, n�o ser�
		// adicionado novamente, apenas somado
		while (it.hasNext()) {
						
			itemVenda = (ItemVendaVO) it.next();
			
			if (itemVenda.getProduto().getIdProduto() == produtoHelper.getIdProduto()) {
								
				itemVenda.setQuantidade(itemVenda.getQuantidade()+ Integer.parseInt(txtQuantidade.getText()));
				itemVenda.setValor(Double.parseDouble(txtValor.getText()));
				produtoHelper.setQuantidadeEstoque(produtoHelper.getQuantidadeEstoque()-Integer.parseInt(txtQuantidade.getText()));
				itemVenda.setProduto(produtoHelper);
				itemEstaNaTabela = true;
				break;
			}

		}

		// se o item n�o foi somado no while acima, ele � adicionado na
		// lista aqui
		if (!itemEstaNaTabela) {
			itemVenda = new ItemVendaVO();
			
			//diminui o estoque do produto antes de adiciona-lo ao itemVenda
			produtoHelper.setQuantidadeEstoque(produtoHelper.getQuantidadeEstoque()-Integer.parseInt(txtQuantidade.getText()));
			
			itemVenda.setVenda(venda);
			itemVenda.setProduto(produto);
			itemVenda.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
			itemVenda.setValor(Double.parseDouble(txtValor.getText()));

			listaItensVenda.add(itemVenda);
		}

		listaProdutos.get(comboProduto.getSelectedIndex() - 1).setQuantidadeEstoque(produtoHelper.getQuantidadeEstoque());
	}

	private void somaTotal() {

		if (listaItensVenda.size() == 0) {

			lblTotal.setText("Total: 0");
			return;
		}

		it = listaItensVenda.iterator();

		total = 0.0;

		while (it.hasNext()) {

			itemVenda = (ItemVendaVO) it.next();
			total += itemVenda.getValor() * itemVenda.getQuantidade();

		}

		lblTotal.setText("Total: " + Utilidades.FORMAT.format(total));

	}

}
