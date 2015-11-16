package ui.estoque;

import java.awt.BorderLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ui.ManterGenericoView;
import util.Utilidades;
import vo.ProdutoVO;
import bo.ProdutoBO;

public class ManterProdutoView extends ManterGenericoView{

	private static final long serialVersionUID = 1L;
	private DefaultTableModel dtm;
	private ProdutoBO bo;
	private List<ProdutoVO> listaProdutos;
	private ProdutoVO produto;
	private Iterator<?> it;
	
	public ManterProdutoView() {
		
		super(Utilidades.CONSULTA_PRODUTOS);

		super.getBtnDetalhar().setVisible(false);
		super.boundsBtn();

	}

	@Override
	public void carregaDtm(JTable table, DefaultTableModel dtm) {
				
		dtm = new DefaultTableModel(
				
				new Object[][] {
						
				},
				new String[] {
					"ID", "Tipo", "Sabor", "Quantidade Estoque"
				}				
		){
		
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				
			   return false;
			}
			
		};
		
		bo = new ProdutoBO();
		
		listaProdutos = bo.consultarProdutos();

		it = listaProdutos.iterator();
				
		while(it.hasNext()){
				
			produto = (ProdutoVO) it.next();
				
			dtm.addRow(new Object[] {
				produto.getIdProduto(),
				produto.getTipo(),
				produto.getSabor(),
				produto.getQuantidadeEstoque()
			});
			
		}			
		
		table.setModel(dtm);
		this.dtm = dtm;
	}

	/**
	 * Não será usado nessa tela;
	 */
	@Override
	public void btnDetalhar(Integer linhaSelecionada) {

		//não será usado nessa tela.
	}

	@Override
	public void btnAtualizar(Integer linhaSelecionada) {

		Utilidades.frmHome.getContentPane().removeAll();
		AlterarProdutoView at = new AlterarProdutoView(listaProdutos.get(linhaSelecionada));
		Utilidades.frmHome.getContentPane().add(at, BorderLayout.CENTER);
		Utilidades.frmHome.getContentPane().revalidate();

	}

	@Override
	public void btnRemover(Integer linhaSelecionada) {
		
		String sabor= "";
		
		if(listaProdutos.get(linhaSelecionada).getSabor()!=null){
			sabor = " - " +listaProdutos.get(linhaSelecionada).getSabor();
		}
		
		// se clicar em sim, vai excluir
		if (JOptionPane.showConfirmDialog(null,"Deseja realmente excluir o produto "+ listaProdutos.get(linhaSelecionada).getTipo()+sabor+ " ?", "Exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

			// se excluir com sucesso...
			if (bo.excluirProduto(listaProdutos.get(linhaSelecionada))) {

				// remove da lista e da tabela
				listaProdutos.remove(linhaSelecionada);
				dtm.removeRow(linhaSelecionada);

			}

		}
		
	}

	@Override
	public void btnCadastrar() {

		Utilidades.frmHome.getContentPane().removeAll();
		CadastrarProdutoView cadastrarProduto = new CadastrarProdutoView();
		Utilidades.frmHome.getContentPane().add(cadastrarProduto, BorderLayout.CENTER);
		Utilidades.frmHome.getContentPane().revalidate();
		
	}


}
