package ui.estoque;

import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ui.ConsultaGenericaView;
import util.Utilidades;
import vo.MateriaPrimaVO;
import bo.MateriaPrimaBO;

public class ConsultaMateriaPrimaView extends ConsultaGenericaView{

	private static final long serialVersionUID = 1L;
	private DefaultTableModel dtm;
	private MateriaPrimaBO bo;
	private List<MateriaPrimaVO> listaMaterias;
	private MateriaPrimaVO materia;
	private Iterator<?> it;
	
	public ConsultaMateriaPrimaView() {
		super(Utilidades.CONSULTA_MATERIAS_PRIMAS);

	}

	@Override
	public void carregaDtm(JTable table, DefaultTableModel dtm) {
		
		dtm = new DefaultTableModel(
				
				new Object[][] {
						
				},
				new String[] {
					"ID", "Nome", "Sabor", "Quantidade Disponível", "Fornecedor"
				}				
		){
		
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				
			   return false;
			}
			
		};
		
		bo = new MateriaPrimaBO();
		
		listaMaterias = bo.consultarMateriasPrimas();

		it = listaMaterias.iterator();
				
		while(it.hasNext()){
				
			materia = (MateriaPrimaVO) it.next();
				
			dtm.addRow(new Object[] {
				materia.getIdMateriaPrima(),
				materia.getNome(),
				(materia.getSabor()!=null) ? materia.getSabor() : "-",
				materia.getQuantidadeDisponivel(),
				materia.getFornecedor().getRazaoSocial()
			});
			
		}			
		
		table.setModel(dtm);
		this.dtm = dtm;
		
	}

	@Override
	public void btnDetalhar(Integer linhaSelecionada) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void btnAtualizar(Integer linhaSelecionada) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void btnRemover(Integer linhaSelecionada) {

		String sabor = "";
		
		if(listaMaterias.get(linhaSelecionada).getSabor()!=null){
			
			sabor = " - "+listaMaterias.get(linhaSelecionada).getSabor();
		}
		
		//se clicar em sim, vai excluir
		if(JOptionPane.showConfirmDialog(null, "Deseja realmente excluir a Matéria Prima "+listaMaterias.get(linhaSelecionada).getNome()+sabor+" ?", "Exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
					
			//se excluir com sucesso...
			if(bo.excluirMateriaPrima(listaMaterias.get(linhaSelecionada).getIdMateriaPrima())){
				
				//remove da lista e da tabela
				listaMaterias.remove(linhaSelecionada);						
				dtm.removeRow(linhaSelecionada);
				
			}
					
		}
		
	}

}
