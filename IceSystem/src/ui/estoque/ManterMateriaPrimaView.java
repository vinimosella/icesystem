package ui.estoque;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import ui.ManterGenericoView;
import util.Utilidades;
import vo.MateriaPrimaVO;
import bo.MateriaPrimaBO;

public class ManterMateriaPrimaView extends ManterGenericoView{

	private static final long serialVersionUID = 1L;
	private DefaultTableModel dtm;
	private MateriaPrimaBO bo;
	private List<MateriaPrimaVO> listaMaterias;
	private MateriaPrimaVO materia;
	private Iterator<?> it;
	private JLabel lblNome;
	private JTextField txtNome;
	private JLabel lblSabor;
	private JTextField txtSabor;
	private JButton btnFiltrar;
	
	public ManterMateriaPrimaView() {
		super(Utilidades.CONSULTA_MATERIAS_PRIMAS);

		super.getBtnDetalhar().setVisible(false);
		super.getScrollPane().setBounds(20, 70, 550, 400);
		super.boundsBtn(500);
		
		lblNome = new JLabel("Nome:");
		lblNome.setBounds(20,40,50,20);
		this.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(70,40,130,20);
		this.add(txtNome);
		
		lblSabor = new JLabel("Sabor:");
		lblSabor.setBounds(230,40,70,20);
		this.add(lblSabor);
		
		txtSabor = new JTextField();
		txtSabor.setBounds(280,40,130,20);
		this.add(txtSabor);
		
		btnFiltrar = new JButton("Filtrar");
		btnFiltrar.setBounds(430,40,70,20);
		btnFiltrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				listaMaterias = bo.filtrarBusca(txtNome.getText(), txtSabor.getText());
				carregaDtm();
			}
		});
		this.add(btnFiltrar);
		
	}
	
	private void carregaDtm(){
		
		carregaDtm(super.getTable(), super.getDtm());
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
		
		if(bo == null){
			bo = new MateriaPrimaBO();
		}
		
		if(listaMaterias==null){
			listaMaterias = bo.consultarMateriasPrimas();
		}
		
		it = listaMaterias.iterator();
				
		while(it.hasNext()){
				
			materia = (MateriaPrimaVO) it.next();
				
			dtm.addRow(new Object[] {
				materia.getIdMateriaPrima(),
				materia.getNome(),
				(materia.getSabor()!=null && !materia.getSabor().trim().equals("")) ? materia.getSabor() : "-",
				materia.getQuantidadeDisponivel(),
				materia.getFornecedor().getRazaoSocial()
			});
			
		}			
		
		table.setModel(dtm);
		this.dtm = dtm;
		
	}

	/**
	 * Não será usado nessa tela;
	 * */
	@Override
	public void btnDetalhar(Integer linhaSelecionada) {

		//btn detalhar não é visível nessa tela.
		
	}

	@Override
	public void btnAtualizar(Integer linhaSelecionada) {

		AlterarMateriaPrimaView at = new AlterarMateriaPrimaView(listaMaterias.get(linhaSelecionada));
		at.setVisible(true);
		
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
			if(bo.excluirMateriaPrima(listaMaterias.get(linhaSelecionada))){
				
				//remove da lista e da tabela
				listaMaterias.remove(linhaSelecionada);						
				dtm.removeRow(linhaSelecionada);
				
			}
					
		}
		
	}

	@Override
	public void btnCadastrar() {

		CadastrarMateriaPrimaView cadastrarMateria = new CadastrarMateriaPrimaView();
		cadastrarMateria.setVisible(true);
		
	}

}
