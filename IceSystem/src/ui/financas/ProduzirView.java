package ui.financas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import ui.cliente.AtualizarClienteView;
import util.Utilidades;
import vo.IngredienteReceitaProdutoVO;
import vo.ProdutoVO;
import bo.IngredienteReceitaProdutoBO;
import bo.ProdutoBO;

public class ProduzirView extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JTable table;
	private JLabel lblProduzir;
	private JLabel lblQuantidade;
	private JTextField txtQuantidade;
	private JButton btnDetalhar;
	private JButton btnProduzir;
	private JScrollPane scrollPane;
	private DefaultTableModel dtm;
	private ProdutoBO produtoBo;
	private IngredienteReceitaProdutoBO receitaBo;
	private List<ProdutoVO> listaProdutos;
	private ProdutoVO produto;
	private List<IngredienteReceitaProdutoVO> listaReceitas;
	private JFrame frmHome;
	
	{
		produtoBo = new ProdutoBO();
		receitaBo = new IngredienteReceitaProdutoBO();
	}
	
	public ProduzirView(JFrame frmHome){
		
		this.frmHome=frmHome;
		this.setLayout(null);
		this.setBackground(Color.decode("#F0F8FF"));
		
		lblProduzir = new JLabel("Produzir");
		lblProduzir.setBounds(10, 11, 430, 14);
		this.add(lblProduzir);
		
		table = new JTable();
		
		montaTabela();
		
		lblQuantidade = new JLabel("Quantidade");
		lblQuantidade.setBounds(40, 460, 100, 25);
		this.add(lblQuantidade);
		
		txtQuantidade = new JTextField();
		txtQuantidade.setBounds(130, 460, 100, 25);
		this.add(txtQuantidade);
				
		btnDetalhar = new JButton("Detalhar");
		btnDetalhar.setBounds(130, 490, 91, 23);
		btnDetalhar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(table.getSelectedRow() != -1){ // se tiver algo selecionado
					
					//TODO btnDetalhar(table.getSelectedRow());
					
				}		
				
			}
		});
		this.add(btnDetalhar);
		
		btnProduzir = new JButton("Produzir");
		btnProduzir.setBounds(231, 490, 91, 23);
		btnProduzir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
									
				produzir();
				
			}
			
		});
		this.add(btnProduzir);
		
	}
	
	private void montaTabela(){
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 50, 550, 400);
		this.add(scrollPane);
				
		dtm = new DefaultTableModel(
				
				new Object[][] {
						
				},
				new String[] {
					"ID Produto", "Nome", "Sabor", "Estoque"
				}				
		){
		
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				
			   return false;
			}
			
		};
				
		listaProdutos = produtoBo.consultarProdutos(produto);

		Iterator<ProdutoVO> it = listaProdutos.iterator();
			
		while(it.hasNext()){
				
			produto = (ProdutoVO) it.next();
				
			dtm.addRow(new Object[] {
					produto.getIdProduto(),
					produto.getNome(),
					produto.getSabor(),
					produto.getQuantidadeEstoque()
			});
			
		}			
		
		table.setModel(dtm);
		scrollPane.setViewportView(table);
		
	}
	
	private void produzir() {
		
		if(table.getSelectedRow() != -1){
			
			produto = listaProdutos.get(table.getSelectedRow());
			
			Integer qtdProduto = new Integer(txtQuantidade.getText());
			
			//consulta todos os itens da receita do produto
			listaReceitas = receitaBo.consultaReceitasPorProduto(listaProdutos.get(table.getSelectedRow()).getIdProduto());
			
			Iterator<IngredienteReceitaProdutoVO> it = listaReceitas.iterator();
			
			IngredienteReceitaProdutoVO receita;
			
			StringBuilder msgErro = new StringBuilder();
			
			while(it.hasNext()){
				
				receita = (IngredienteReceitaProdutoVO) it.next();
				
				//se a quantidade total de materia prima necessária não estiver disponível no estoque
				if(!(receita.getQuantidadeMateria()*qtdProduto <= receita.getMateriaPrima().getQuantidadeDisponivel())){
					
					msgErro.append("Quantidade necessária de ");
					msgErro.append(receita.getMateriaPrima().getNome());
					msgErro.append(" - ");
					msgErro.append(receita.getMateriaPrima().getSabor());
					msgErro.append(" :");
				}
				
			}
			
			//se possuir todas materias primas necessarias
			if(msgErro.equals("")){
				
				//soma a quantidade que possuia antes com o valor produzido
				produto.setQuantidadeEstoque(produto.getQuantidadeEstoque()+qtdProduto);
				produtoBo.alterarProduto(produto);
				
				//atualiza a tela
				frmHome.getContentPane().removeAll();
				ProduzirView produzir = new ProduzirView(frmHome);
				frmHome.add(produzir);
				frmHome.revalidate();
				
			}
			
		}

	}
	
}
