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
import vo.CompraVO;
import vo.FornecedorVO;
import vo.ItemCompraVO;
import vo.MateriaPrimaVO;
import bo.CompraBO;
import bo.FornecedorBO;
import bo.MateriaPrimaBO;

public class ComprarMateriaPrimaView extends JPanel{

	private static final long serialVersionUID = 1L;
	private JLabel lblCadastrarCompra;
	private FornecedorBO fornecedorBo;
	private MateriaPrimaBO materiaPrimaBo;
	private CompraBO compraBo;
	private JComboBox<String> comboFornecedor;
	private List<FornecedorVO> listaFornecedores;
	private JComboBox<String> comboMateria;
	private List<MateriaPrimaVO> listaMaterias;
	private FornecedorVO fornecedor;
	private MateriaPrimaVO materia;
	private JLabel lblQuantidade;
	private JTextField txtQuantidade;
	private JLabel lblValor;
	private JTextField txtValor;
	private JButton btnAdicionar;
	private List<ItemCompraVO> listaItensCompra;
	private ItemCompraVO itemCompra;
	private CompraVO compra;
	private Iterator<?> it;
	private StringBuilder msg;
	private JLabel lblItensCompra;
	private JTable tabela;
	private JScrollPane scrollPane;
	private DefaultTableModel dtm;
	private JLabel lblTotal;
	private Double total;
	private JButton btnRemover;
	private JButton btnCadastrar;
	
	{
		fornecedorBo = new FornecedorBO();
		materiaPrimaBo = new MateriaPrimaBO();
		compraBo = new CompraBO();
		compra = new CompraVO();
		listaItensCompra = new ArrayList<ItemCompraVO>();
	}
	
	public ComprarMateriaPrimaView(MateriaPrimaVO materiaPrima){
		
		this.setLayout(null);
		this.setBackground(Color.decode("#F0F8FF"));
		
		lblCadastrarCompra = new JLabel("Cadastrar Compra");
		this.add(lblCadastrarCompra);
		
		//COMBO FORNECEDOR
		comboFornecedor = new JComboBox<String>();
		comboFornecedor.addItem("Selecione um fornecedor");		
		
		listaFornecedores = fornecedorBo.consultarFornecedores();
		it = listaFornecedores.iterator();
		
		while(it.hasNext()){
			
			fornecedor = (FornecedorVO) it.next();
			comboFornecedor.addItem(fornecedor.getRazaoSocial());
			
		}
		
		//CARREGA MATERIAS DO FORNECEDOR SELECIONADO
		comboFornecedor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//se selecionar o item selecione, o combo de matéria é resetado
				if(comboFornecedor.getSelectedIndex()==0){
					
					comboMateria.removeAllItems();
					comboMateria.addItem("Primeiro selecione um Fornecedor");
					comboMateria.setEnabled(false);
					
				}
				//se selecionar um fornecedor, o combo de matéria é carregado.
				else{
					
					comboMateria.removeAllItems();
					comboMateria.addItem("Selecione a matéria prima");
					comboMateria.setEnabled(true);
					
					//Pego o index -1 por causa do item selecione no combo
					listaMaterias = materiaPrimaBo.consultarMateriasPrimas(listaFornecedores.get(comboFornecedor.getSelectedIndex()-1));
					
					it = listaMaterias.iterator();
					
					while(it.hasNext()){
						
						materia = (MateriaPrimaVO) it.next();
						
						if(materia.getSabor()!=null){
							
							comboMateria.addItem(materia.getNome()+" - "+materia.getSabor());
							
						}
						else{
							
							comboMateria.addItem(materia.getNome());

						}
						
					}
				}
			}
		});
		this.add(comboFornecedor);
		
		//COMBO MATERIAS
		comboMateria = new JComboBox<String>();
		comboMateria.addItem("Primeiro selecione um Fornecedor");
		comboMateria.setEnabled(false);
		this.add(comboMateria);
		
		//CAMPO QUANTIDADE
		lblQuantidade = new JLabel("Quantidade: ");
		this.add(lblQuantidade);
		
		txtQuantidade = new JTextField();
		this.add(txtQuantidade);
		
		//CAMPO VALOR
		lblValor = new JLabel("Valor Unitário: ");
		this.add(lblValor);
		
		txtValor = new JTextField();
		this.add(txtValor);
		
		//BOTAO ADICIONAR
		btnAdicionar = new JButton(new ImageIcon(getClass().getResource("/img/addLista.png")));
		btnAdicionar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//o combo de fornecedor é desabilitado para nao ter itens de fornecedores diferentes na mesma compra
				comboFornecedor.setEnabled(false);
				//agora que tem item da compra, é possivel cadastrar compra
				btnCadastrar.setEnabled(true);
				
				adicionarItemCompra();
				carregaDtm();
				somaTotal();
				
			}
		});
		this.add(btnAdicionar);
		
		//LABEL TABELA ITENS COMPRA
		lblItensCompra = new JLabel("Itens da compra:");
		this.add(lblItensCompra);
		
		//BOTÃO REMOVER ITEM DA COMPRA
		btnRemover = new JButton("Remover item selecionado", new ImageIcon(getClass().getResource("/img/remover.png")));
		btnRemover.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				//se tiver algo selecionado
				if(tabela.getSelectedRow()!=-1){
					listaItensCompra.remove(tabela.getSelectedRow());
					carregaDtm();
					somaTotal();
				}
				//se a lista de itens estiver vazia, a troca de fornecedores é habilitada e nao e possivel cadastrar
				if(listaItensCompra.size()==0){
					comboFornecedor.setEnabled(true);
					btnCadastrar.setEnabled(false);
				}
			}
			
		});
		this.add(btnRemover);
		
		//TABELA ITENS COMPRA
		tabela = new JTable();
		
		scrollPane = new JScrollPane();
		this.add(scrollPane);
		carregaDtm();
		scrollPane.setViewportView(tabela);
		
		//LABEL TOTAL DA COMPRA
		lblTotal = new JLabel("Total: 0");
		this.add(lblTotal);
		
		//BOTÃO CADASTRAR COMPRA
		btnCadastrar = new JButton("Cadastrar Compra");
		btnCadastrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if(compraBo.cadastrarCompra(listaItensCompra)){
					
					Utilidades.frmHome.getContentPane().removeAll();
					ManterComprasView consCompras = new ManterComprasView();
					Utilidades.frmHome.getContentPane().add(consCompras, BorderLayout.CENTER);
					Utilidades.frmHome.getContentPane().revalidate();
					
				}
			}
		});
		btnCadastrar.setEnabled(false);
		this.add(btnCadastrar);

		gerarBounds();
	}
	
	private void gerarBounds(){
		
		int altura = 5;
		
		lblCadastrarCompra.setBounds(10,altura,300,20);
		
		comboFornecedor.setBounds(30,altura+=30,300,25);
		
		comboMateria.setBounds(30,altura+=45,300,25);
		
		lblQuantidade.setBounds(30,altura+=40,100,25);
		txtQuantidade.setBounds(120,altura,60,25);
		lblValor.setBounds(220,altura,100,25);
		txtValor.setBounds(310,altura,60,25);
		btnAdicionar.setBounds(400,altura,26,27);
		
		lblItensCompra.setBounds(20,altura+=45,100,25);
		btnRemover.setBounds(359,altura,210,25);
		
		scrollPane.setBounds(20, altura+=35, 550, 300);
		
		lblTotal.setBounds(20,altura+=310, 100, 25);
		btnCadastrar.setBounds(200,altura,160,25);
		
	}
	
	private void carregaDtm() {

		dtm = new DefaultTableModel(
				
				new Object[][] {
						
				},
				new String[] {
					"Fornecedor", "Nome-Materia", "Sabor", "Quantidade", "Valor", "Valor Total"
				}				
		){
		
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				
			   return false;
			}
			
		};
				
		if(listaItensCompra.size()==0){
			tabela.setModel(dtm);
			return;
		}

			it = listaItensCompra.iterator();
				
			while(it.hasNext()){
					
				itemCompra = (ItemCompraVO) it.next();
					
				dtm.addRow(new Object[] {
						itemCompra.getMateriaPrima().getFornecedor().getRazaoSocial(),
						itemCompra.getMateriaPrima().getNome(),
						(itemCompra.getMateriaPrima().getSabor()!=null) ? itemCompra.getMateriaPrima().getSabor() : "-",
						itemCompra.getQuantidade(),
						Utilidades.FORMAT.format(itemCompra.getValor()),
						Utilidades.FORMAT.format(itemCompra.getQuantidade()*itemCompra.getValor())
				});
				
			}
		
		tabela.setModel(dtm);
		
	}

	private void adicionarItemCompra() {

		msg = new StringBuilder();
		
		//CARREGA MENSAGEM SE ALGO ESTÁ INVÁLIDO
		if(comboFornecedor.getSelectedIndex()==0){
			
			msg.append("Selecione o Fornecedor\n");
		}
		
		if(comboMateria.getSelectedIndex()==0){
			
			msg.append("Selecione a Matéria Prima\n");
		}
		
		if(txtQuantidade.getText().equals("")){
			
			msg.append("Indique a quantidade\n");
		}
		
		if(txtValor.getText().equals("")){
			
			msg.append("Indique o valor unitário\n");
		}
		
		//VERIFICA SE A MENSAGEM NÃO ESTÁ VAZIA PARA EXIBI-LA
		if(!msg.toString().trim().equals("")){
			
			JOptionPane.showMessageDialog(Utilidades.frmHome, msg.toString(), "Não foi possível adicionar item!", JOptionPane.ERROR_MESSAGE);

		}
		else{ //SE CAIU AQUI ESTÁ CERTO E PODE SER ADICIONADO Á COMPRA
			
			it = listaItensCompra.iterator();
			
			boolean itemEstaNaTabela = false;
			//verifica se o item ja esta na lista, se estiver, não será adicionado novamente, apenas somado
			while(it.hasNext()){
				
				itemCompra = (ItemCompraVO) it.next();
				
				if(itemCompra.getMateriaPrima().getIdMateriaPrima() == listaMaterias.get(comboMateria.getSelectedIndex()-1).getIdMateriaPrima()){
					
					itemCompra.setQuantidade(itemCompra.getQuantidade() + Double.parseDouble(txtQuantidade.getText()));
					itemCompra.setValor(Double.parseDouble(txtValor.getText()));
					itemEstaNaTabela = true;
					
				}
				
			}
			
			//se o item não foi somado no while acima, ele é adicionado na lista aqui
			if(!itemEstaNaTabela){
				itemCompra = new ItemCompraVO();
				
				itemCompra.setCompra(compra);
				itemCompra.setMateriaPrima(listaMaterias.get(comboMateria.getSelectedIndex()-1));
				itemCompra.setQuantidade(Double.parseDouble(txtQuantidade.getText()));
				itemCompra.setValor(Double.parseDouble(txtValor.getText()));
				
				listaItensCompra.add(itemCompra);
			}
			
		}
	}
	
	private void somaTotal(){
		
		if(listaItensCompra.size()==0){
			
			lblTotal.setText("Total: 0");
			return;
		}
		
		it = listaItensCompra.iterator();
		
		total = 0.0;
		
		while(it.hasNext()){
			
			itemCompra = (ItemCompraVO) it.next();
			total += itemCompra.getValor()*itemCompra.getQuantidade();
			
		}
		
		lblTotal.setText("Total: "+ Utilidades.FORMAT.format(total));
		
	}
}
