package ui.estoque;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ui.ConsultaGenericaView;
import util.Utilidades;
import vo.ProdutoVO;
import bo.ProdutoBO;

public class ConsultaProdutoView extends ConsultaGenericaView{

	private static final long serialVersionUID = 1L;
	private DefaultTableModel dtm;
	private ProdutoBO bo;
	private List<ProdutoVO> listaProdutos;
	private ProdutoVO produto;
	private Iterator<?> it;
	private JButton btnCadastrar;

	
	public ConsultaProdutoView() {
		super(Utilidades.CONSULTA_PRODUTOS);

		super.getBtnDetalhar().setVisible(false);
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(130, 480, 94, 23);
		btnCadastrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				CadastrarProdutoView cadProduto = new CadastrarProdutoView();
				cadProduto.setVisible(true);
				
			}
		});
		this.add(btnCadastrar);
	}

	@Override
	public void carregaDtm(JTable table, DefaultTableModel dtm) {
				
		dtm = new DefaultTableModel(
				
				new Object[][] {
						
				},
				new String[] {
					"ID", "Nome", "Sabor", "Quantidade Estoque"
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
				produto.getNome(),
				(produto.getSabor()!=null) ? produto.getSabor() : "-",
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

		AlterarProdutoView at = new AlterarProdutoView(listaProdutos.get(linhaSelecionada));
		at.setVisible(true);
		
	}

	@Override
	public void btnRemover(Integer linhaSelecionada) {
		
		String sabor= "";
		
		if(listaProdutos.get(linhaSelecionada).getSabor()!=null){
			sabor = " - " +listaProdutos.get(linhaSelecionada).getSabor();
		}
		
		// se clicar em sim, vai excluir
		if (JOptionPane.showConfirmDialog(null,"Deseja realmente excluir o produto "+ listaProdutos.get(linhaSelecionada).getNome()+sabor+ " ?", "Exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

			// se excluir com sucesso...
			if (bo.excluirProduto(listaProdutos.get(linhaSelecionada).getIdProduto())) {

				// remove da lista e da tabela
				listaProdutos.remove(linhaSelecionada);
				dtm.removeRow(linhaSelecionada);

			}

		}
		
	}


}
