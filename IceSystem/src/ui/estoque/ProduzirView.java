package ui.estoque;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import util.Utilidades;
import vo.IngredienteReceitaProdutoVO;
import vo.ProdutoVO;
import bo.IngredienteReceitaProdutoBO;
import bo.MateriaPrimaBO;
import bo.ProdutoBO;

public class ProduzirView extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JTable table;
	private JLabel lblProduzir;
	private JLabel lblQuantidade;
	private JTextField txtQuantidade;
	private JButton btnVerReceita;
	private JButton btnProduzir;
	private JScrollPane scrollPane;
	private DefaultTableModel dtm;
	private ProdutoBO produtoBo;
	private IngredienteReceitaProdutoBO receitaBo;
	private MateriaPrimaBO materiaBo;
	private List<ProdutoVO> listaProdutos;
	private ProdutoVO produto;
	private List<IngredienteReceitaProdutoVO> listaReceitas;
	
	{
		produtoBo = new ProdutoBO();
		receitaBo = new IngredienteReceitaProdutoBO();
		materiaBo = new MateriaPrimaBO();
	}
	
	public ProduzirView(){
				
		this.setLayout(null);
		this.setBackground(Color.decode("#F0F8FF"));
		
		lblProduzir = new JLabel("Produzir");
		lblProduzir.setBounds(10, 11, 430, 14);
		this.add(lblProduzir);
		
		table = new JTable();
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 50, 550, 400);
		this.add(scrollPane);
		carregaDtm();
		scrollPane.setViewportView(table);
		
		lblQuantidade = new JLabel("Quantidade");
		lblQuantidade.setBounds(40, 460, 100, 25);
		this.add(lblQuantidade);
		
		txtQuantidade = new JTextField();
		txtQuantidade.setBounds(130, 460, 100, 25);
		this.add(txtQuantidade);
				
		btnVerReceita = new JButton("Visualizar Receita");
		btnVerReceita.setBounds(130, 490, 111, 23);
		btnVerReceita.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(table.getSelectedRow() != -1){ // se tiver algo selecionado
					
					ReceitaProdutoView receita = new ReceitaProdutoView(listaProdutos.get(table.getSelectedRow()));
					receita.setVisible(true);
					
				}		
				
			}
		});
		this.add(btnVerReceita);
		
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
	
	private void carregaDtm(){
		
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
				
		listaProdutos = produtoBo.consultarProdutos();

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
		
	}
	
	private void produzir() {
		
		if(table.getSelectedRow() != -1 && !txtQuantidade.getText().trim().equals("")){
			
			produto = listaProdutos.get(table.getSelectedRow());
			
			Integer qtdProduto = new Integer(txtQuantidade.getText());
			
			//consulta todos os itens da receita do produto
			listaReceitas = receitaBo.consultaReceitasPorProduto(listaProdutos.get(table.getSelectedRow()));
			
			Iterator<IngredienteReceitaProdutoVO> it = listaReceitas.iterator();
			
			IngredienteReceitaProdutoVO receita;
			
			StringBuilder msgErro = new StringBuilder();
			
			while(it.hasNext()){
				
				receita = (IngredienteReceitaProdutoVO) it.next();
				
				//se a quantidade total de materia prima necessária não estiver disponível no estoque
				if(!(receita.getQuantidadeMateria()*qtdProduto <= receita.getMateriaPrima().getQuantidadeDisponivel())){
										
					msgErro.append("Quantidade total necessária de ");
					msgErro.append(receita.getMateriaPrima().getNome());
					
					//se tiver sabor, informo o sabor da materia também
					if(receita.getMateriaPrima().getSabor()!= null){
						msgErro.append(" - ");
						msgErro.append(receita.getMateriaPrima().getSabor());
					}
					msgErro.append(": ");
					msgErro.append(receita.getQuantidadeMateria()*qtdProduto);
					msgErro.append(" / Quantidade disponível: ");
					msgErro.append(receita.getMateriaPrima().getQuantidadeDisponivel());
					msgErro.append("\n");
				}
				
			}
			
			if(txtQuantidade.getText().trim().equals("")){
				msgErro.append("Selecione a quantidade de produto");
			}
			
			//se possuir todas materias primas necessarias
			if(msgErro.toString().trim().equals("")){
				
				//soma a quantidade que possuia antes com o valor produzido
				produto.setQuantidadeEstoque(produto.getQuantidadeEstoque()+qtdProduto);
				produtoBo.atualizarProduto(produto);
				
				//diminui a quantidade de estoque de materia prima
				materiaBo.alterarMateriaPrimaReceita(listaReceitas, qtdProduto, produto);
				
				//atualiza conteudo da tabela
				carregaDtm();
				//limpa campo de quantidade
				txtQuantidade.setText("");
				
			}
			else{
				
				JOptionPane.showMessageDialog(Utilidades.frmHome, msgErro.toString(), "Não foi possível produzir!", JOptionPane.ERROR_MESSAGE);
				
			}
			
		}
		else{
			
			JOptionPane.showMessageDialog(Utilidades.frmHome, "Selecione um produto e indique a quantidade a ser produzida!", "Não foi possível produzir!", JOptionPane.ERROR_MESSAGE);
		}

	}
	
}
