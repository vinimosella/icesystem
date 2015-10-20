package ui.estoque;

import java.awt.BorderLayout;
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
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import util.Utilidades;
import vo.IngredienteReceitaProdutoVO;
import vo.OrdemProducaoVO;
import vo.ProdutoVO;
import bo.IngredienteReceitaProdutoBO;
import bo.MateriaPrimaBO;
import bo.OrdemProducaoBO;
import bo.ProdutoBO;

public class GerarOrdemProducao extends JPanel{

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
	private OrdemProducaoBO oPBo;
	private List<ProdutoVO> listaProdutos;
	private ProdutoVO produto;
	private List<IngredienteReceitaProdutoVO> listaReceitas;
	private OrdemProducaoVO op;
	
	{
		produtoBo = new ProdutoBO();
		oPBo = new OrdemProducaoBO();
		receitaBo = new IngredienteReceitaProdutoBO();
		materiaBo = new MateriaPrimaBO();
	}
	
	public GerarOrdemProducao(){
				
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
		
		lblQuantidade = new JLabel("Quantidade: ");
		lblQuantidade.setBounds(40, 490, 100, 25);
		this.add(lblQuantidade);
		
		txtQuantidade = new JTextField();
		txtQuantidade.setBounds(110, 490, 100, 25);
		this.add(txtQuantidade);
				
		btnVerReceita = new JButton("Visualizar Receita");
		btnVerReceita.setBounds(380, 490, 140, 23);
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
		btnProduzir.setBounds(255, 490, 90, 23);
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
					(produto.getSabor()!=null) ? produto.getSabor() : "-",
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
				
				//se a quantidade total de materia prima necess�ria n�o estiver dispon�vel no estoque
				if(!(receita.getQuantidadeMateria()*qtdProduto <= receita.getMateriaPrima().getQuantidadeDisponivel())){
										
					msgErro.append("Quantidade total necess�ria de ");
					msgErro.append(receita.getMateriaPrima().getNome());
					
					//se tiver sabor, informo o sabor da materia tamb�m
					if(receita.getMateriaPrima().getSabor()!= null){
						msgErro.append(" - ");
						msgErro.append(receita.getMateriaPrima().getSabor());
					}
					msgErro.append(": ");
					msgErro.append(receita.getQuantidadeMateria()*qtdProduto);
					msgErro.append(" / Quantidade dispon�vel: ");
					msgErro.append(receita.getMateriaPrima().getQuantidadeDisponivel());
					msgErro.append("\n");
				}
				
			}
			
			if(txtQuantidade.getText().trim().equals("")){
				msgErro.append("Selecione a quantidade de produto");
			}
			
			//se possuir todas materias primas necessarias
			if(msgErro.toString().trim().equals("")){
				
				//gera a nova ordem de produ��o
				op = new OrdemProducaoVO();
				op.setProduto(produto);
				op.setQuantidade(qtdProduto);
				
				oPBo.incluirOrdemProducao(op);
				
				//diminui a quantidade de estoque de materia prima
				materiaBo.alterarMateriaPrimaReceita(listaReceitas, qtdProduto, produto);
				
				//limpa campo de quantidade
				txtQuantidade.setText("");
				
				//mudar texto dos bot�es "yes" e "no"
				UIManager.put("OptionPane.noButtonText", "N�o");  
				UIManager.put("OptionPane.yesButtonText", "Sim");
				
				//se clicar em sim, vai excluir
				if(JOptionPane.showConfirmDialog(null, "Ordem de produ��o gerada!\nDeseja abrir a tela de ordens?", "Sucesso", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
					
					Utilidades.frmHome.getContentPane().removeAll();
					OrdemDeProducaoView opv = new OrdemDeProducaoView();
					Utilidades.frmHome.getContentPane().add(opv, BorderLayout.CENTER);
					Utilidades.frmHome.getContentPane().revalidate();	
									
				}
				
			}
			else{
				
				JOptionPane.showMessageDialog(Utilidades.frmHome, msgErro.toString(), "N�o foi poss�vel produzir!", JOptionPane.ERROR_MESSAGE);
				
			}
			
		}
		else{
			
			JOptionPane.showMessageDialog(Utilidades.frmHome, "Selecione um produto e indique a quantidade a ser produzida!", "N�o foi poss�vel produzir!", JOptionPane.ERROR_MESSAGE);
		}

	}
	
}