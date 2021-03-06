package ui.estoque;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ui.financas.ComprarMateriaPrimaView;
import util.Utilidades;
import vo.IngredienteReceitaProdutoVO;
import vo.ProdutoVO;
import bo.IngredienteReceitaProdutoBO;

public class ReceitaProdutoView extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private IngredienteReceitaProdutoBO bo;
	private List<IngredienteReceitaProdutoVO> listaIngredientes;
	private IngredienteReceitaProdutoVO ingrediente;
	private JLabel lblReceita;
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel dtm;
	private ProdutoVO produto;
	private JButton btnComprar;
	
	{
		bo = new IngredienteReceitaProdutoBO();
	}
	

	public ReceitaProdutoView(ProdutoVO produto) {
		
		this.produto = produto;
		
		setTitle("Receita do Produto");		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 700, 500);
		setModal(true);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setBackground(Color.decode("#F0F8FF"));
		contentPane.setLayout(null);
		this.setLocationRelativeTo(null);
				
		lblReceita = new JLabel("Receita - "+produto.getTipo()+" - "+produto.getSabor());		
		lblReceita.setBounds(10,10,600,25);
		contentPane.add(lblReceita);
		
		table = new JTable();
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 50, 650, 350);
		contentPane.add(scrollPane);
		carregaDtm();
		scrollPane.setViewportView(table);
		
		btnComprar = new JButton("Comprar");
		btnComprar.setBounds(315,400,90,25);
		btnComprar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if(table.getSelectedRow()!=-1){
					ComprarMateriaPrimaView comprar = new ComprarMateriaPrimaView(listaIngredientes.get(table.getSelectedRow()).getMateriaPrima());
					Utilidades.frmHome.getContentPane().removeAll();
					Utilidades.frmHome.add(comprar);
					Utilidades.frmHome.revalidate();
					
					ReceitaProdutoView.this.dispose();
				}
			}
		});
		contentPane.add(btnComprar);
		
	}


	private void carregaDtm() {

		dtm = new DefaultTableModel(
				
				new Object[][] {
						
				},
				new String[] {
					"ID Materia", "Nome", "Sabor", "Quantidade Necessária", "Quantidade Disponível", "Fornecedor"
				}				
		){
		
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				
			   return false;
			}
			
		};
				
		listaIngredientes = bo.consultaReceitasPorProduto(produto);

		Iterator<IngredienteReceitaProdutoVO> it = listaIngredientes.iterator();
			
		while(it.hasNext()){
				
			ingrediente = (IngredienteReceitaProdutoVO) it.next();
							
				dtm.addRow(new Object[] {
						
					ingrediente.getMateriaPrima().getIdMateriaPrima(),
					ingrediente.getMateriaPrima().getNome(),
					(ingrediente.getMateriaPrima().getSabor()!=null) ? ingrediente.getMateriaPrima().getSabor() : "-",
					Utilidades.FORMAT.format(ingrediente.getQuantidadeMateria()),
					Utilidades.FORMAT.format(ingrediente.getMateriaPrima().getQuantidadeDisponivel()),
					ingrediente.getMateriaPrima().getFornecedor().getRazaoSocial()
						
				});
							
		}			
		
		table.setModel(dtm);
		
	}
	
}
