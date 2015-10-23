package ui.estoque;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
import vo.MateriaPrimaVO;
import vo.ProdutoVO;
import bo.MateriaPrimaBO;
import bo.ProdutoBO;

public class CadastrarProdutoView extends JPanel{

	private static final long serialVersionUID = 1L;
	private JLabel lblNomePagina;
	private JLabel lblNome;
	private JTextField txtNome;
	private JLabel lblSabor;
	private JTextField txtSabor;
	private ProdutoBO bo;
	private MateriaPrimaBO matBo;
	private List<MateriaPrimaVO> listaMaterias;
	private MateriaPrimaVO materia;
	private ProdutoVO produto;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private StringBuilder msgErro;
	private JTable tabelaMaterias;
	private JTable tabelaReceita;
	private List<IngredienteReceitaProdutoVO> listaReceita;
	private IngredienteReceitaProdutoVO itemReceita;
	private JScrollPane scrollPane;
	private DefaultTableModel dtm;
	private Iterator<?> it;
	private JLabel lblQtd;
	private JTextField txtQtd;
	private JButton btnAdicionarItemMateria;
	
	{
		bo = new ProdutoBO();
		matBo = new MateriaPrimaBO();
		listaMaterias = matBo.consultarMateriasPrimas();
		listaReceita = new ArrayList<IngredienteReceitaProdutoVO>();
	}

	public CadastrarProdutoView() {
		
		this.setLayout(null);
		this.setBackground(Color.decode("#F0F8FF"));
		
		lblNomePagina = new JLabel(Utilidades.CADASTRAR_MATERIA_PRIMA);
		lblNomePagina.setBounds(10,20,170,25);
				
		lblNome = new JLabel("Nome:");
		lblNome.setBounds(20, 20, 70, 20);
		this.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(100,20,130,20);
		this.add(txtNome);
		
		lblSabor = new JLabel("Sabor:");
		lblSabor.setBounds(20, 60, 70, 20);
		this.add(lblSabor);
		
		txtSabor = new JTextField();
		txtSabor.setBounds(100,60,130,20);
		this.add(txtSabor);
		
		tabelaMaterias = new JTable();
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 90, 550, 150);
		this.add(scrollPane);
		carregaDtmMateria();
		
		lblQtd = new JLabel("Quantidade Necess�ria");
		lblQtd.setBounds(20,250,130,20);
		this.add(lblQtd);
		
		txtQtd = new JTextField();
		txtQtd.setBounds(160, 250, 70, 20);
		this.add(txtQtd);
		
		btnAdicionarItemMateria = new JButton("\\/");
		btnAdicionarItemMateria.setBounds(365, 250, 70, 30);
		btnAdicionarItemMateria.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				msgErro = new StringBuilder();
				
				if(tabelaMaterias.getSelectedRow()==-1){
					
					msgErro.append("Selecione uma mat�ria prima\n");
				}
				if(txtQtd.getText().trim().equals("")){
					
					msgErro.append("Indique a quantia de mat�ria necess�ria\n");
				}
				
				//se estiver tudo certo
				if(msgErro.toString().trim().equals("")){
					
					itemReceita = new IngredienteReceitaProdutoVO();
					
					itemReceita.setMateriaPrima(listaMaterias.get(tabelaMaterias.getSelectedRow()));
					itemReceita.setQuantidadeMateria(Double.parseDouble(txtQtd.getText()));
					
					listaReceita.add(itemReceita);
					
					carregaDtmReceita();
				}
				else{
					
					JOptionPane.showMessageDialog(Utilidades.frmHome, msgErro.toString(), "Aten��o!", JOptionPane.ERROR_MESSAGE);

				}
				
			}
		});
		this.add(btnAdicionarItemMateria);
		
		tabelaReceita = new JTable();
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 290, 550, 150);
		this.add(scrollPane);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(30,520,90,30);
		btnSalvar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				msgErro = new StringBuilder();
				
				//valida campos
				if(txtNome.getText().trim().equals("")){
					
					msgErro.append("O campo 'nome' deve estar preenchido\n");
				}
				
				if(txtSabor.getText().trim().equals("")){
					
					msgErro.append("O campo 'sabor' deve estar preenchido\n");
				}
				
				//se n�o tiver erros
				if(msgErro.toString().trim().equals("")){
					
					produto = new ProdutoVO();
					
					produto.setNome(txtNome.getText());
					produto.setSabor(txtSabor.getText());
					bo.cadastrarProduto(produto);
					
					Utilidades.frmHome.getContentPane().removeAll();
					ConsultaProdutoView consulta = new ConsultaProdutoView();
					Utilidades.frmHome.add(consulta);
					Utilidades.frmHome.revalidate();
					
				}
				else{
					
					JOptionPane.showMessageDialog(Utilidades.frmHome, msgErro.toString(), "Alerta!", JOptionPane.ERROR_MESSAGE);

				}
				
			}
		});
		this.add(btnSalvar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(130,120,90,30);
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				Utilidades.frmHome.getContentPane().removeAll();
				ConsultaProdutoView consulta = new ConsultaProdutoView();
				Utilidades.frmHome.add(consulta);
				Utilidades.frmHome.revalidate();
				
			}
		});
		this.add(btnCancelar);
		
	}
	
	private void carregaDtmMateria(){
		
		dtm = new DefaultTableModel(
				
				new Object[][] {
						
				},
				new String[] {
					"ID", "Nome", "Sabor", "Fornecedor"
				}				
		){
		
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				
			   return false;
			}
			
		};
				
		it = listaMaterias.iterator();
				
		while(it.hasNext()){
				
			materia = (MateriaPrimaVO) it.next();
				
			dtm.addRow(new Object[] {
				materia.getIdMateriaPrima(),
				materia.getNome(),
				(materia.getSabor()!=null && !materia.getSabor().trim().equals("")) ? materia.getSabor() : "-",
				materia.getFornecedor().getRazaoSocial()
			});
			
		}			
		
		tabelaMaterias.setModel(dtm);
		scrollPane.setViewportView(tabelaMaterias);
		
	}
	
	private void carregaDtmReceita(){
		
		if(listaReceita == null){
			
			return;
		}
		
		dtm = new DefaultTableModel(
				
				new Object[][] {
						
				},
				new String[] {
					"ID", "Nome", "Sabor", "Fornecedor", "Quantidade Necess�ria"
				}				
		){
		
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				
			   return false;
			}
			
		};
		
		it = listaReceita.iterator();
		
		while(it.hasNext()){
				
			itemReceita = (IngredienteReceitaProdutoVO) it.next();
				
			dtm.addRow(new Object[] {
				itemReceita.getMateriaPrima().getIdMateriaPrima(),
				itemReceita.getMateriaPrima().getNome(),
				(itemReceita.getMateriaPrima().getSabor()!=null && !itemReceita.getMateriaPrima().getSabor().trim().equals("")) ? itemReceita.getMateriaPrima().getSabor() : "-",
				itemReceita.getMateriaPrima().getFornecedor().getRazaoSocial(),
				itemReceita.getQuantidadeMateria()
			});
			
		}			
		
		tabelaReceita.setModel(dtm);
		scrollPane.setViewportView(tabelaReceita);
		
	}
	
}
