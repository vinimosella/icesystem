package ui.estoque;

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
import vo.IngredienteReceitaProdutoVO;
import vo.MateriaPrimaVO;
import vo.ProdutoVO;
import bo.IngredienteReceitaProdutoBO;
import bo.MateriaPrimaBO;
import bo.ProdutoBO;

public class AlterarProdutoView extends JPanel{

	private static final long serialVersionUID = 1L;
	private JLabel lblTipo;
	private JComboBox<String> comboTipo;
	private JLabel lblSabor;
	private JTextField txtSabor;
	private ProdutoBO bo;
	private ProdutoVO produto;
	private MateriaPrimaBO matBo;
	private List<MateriaPrimaVO> listaMaterias;
	private MateriaPrimaVO materia;
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
	private JButton btnRemoverItemMateria;
	private JLabel lblMaterias;
	private JLabel lblReceita;
	private IngredienteReceitaProdutoBO recBo;
	private List<IngredienteReceitaProdutoVO> listaIncluidos;
	private List<IngredienteReceitaProdutoVO> listaExcluidos;
	private List<IngredienteReceitaProdutoVO> listaAlterados;
	
	{
		bo = new ProdutoBO();
		matBo = new MateriaPrimaBO();
		recBo = new IngredienteReceitaProdutoBO();
		listaMaterias = matBo.consultarMateriasPrimas();
		listaIncluidos = new ArrayList<IngredienteReceitaProdutoVO>();
		listaExcluidos = new ArrayList<IngredienteReceitaProdutoVO>();
		listaAlterados = new ArrayList<IngredienteReceitaProdutoVO>();
	}

	public AlterarProdutoView(ProdutoVO produto) {
		
		this.produto = produto;
		
		this.setLayout(null);
		this.setBackground(Color.decode("#F0F8FF"));
		
		listaReceita = recBo.consultaReceitasPorProduto(produto);
				
		lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(20, 20, 70, 25);
		this.add(lblTipo);
		
		comboTipo = new JComboBox<String>();
		comboTipo.setBounds(100,20,130,25);
		for(int x = 0; x<Utilidades.VET_TIPOS_PRODUTOS.length;x++){
			
			comboTipo.addItem(Utilidades.VET_TIPOS_PRODUTOS[x]);
			if(Utilidades.VET_TIPOS_PRODUTOS[x].equals(produto.getTipo())){
				comboTipo.setSelectedItem(Utilidades.VET_TIPOS_PRODUTOS[x]);
			}
		}
		this.add(comboTipo);
		
		lblSabor = new JLabel("Sabor:");
		lblSabor.setBounds(20, 60, 70, 25);
		this.add(lblSabor);
		
		txtSabor = new JTextField();
		txtSabor.setBounds(100,60,130,25);
		if(produto.getSabor()!=null){
			txtSabor.setText(produto.getSabor());
		}
		this.add(txtSabor);
		
		lblMaterias = new JLabel("Matérias Primas");
		lblMaterias.setBounds(20,90, 120, 20);
		this.add(lblMaterias);
		
		tabelaMaterias = new JTable();
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 120, 550, 150);
		this.add(scrollPane);
		carregaDtmMateria();
		
		lblQtd = new JLabel("Quantidade Necessária:");
		lblQtd.setBounds(20,270,170,20);
		this.add(lblQtd);
		
		txtQtd = new JTextField();
		txtQtd.setBounds(180, 270, 70, 20);
		this.add(txtQtd);
		
		btnAdicionarItemMateria = new JButton(new ImageIcon(getClass().getResource("/img/down.png")));
		btnAdicionarItemMateria.setBounds(365, 270, 25, 25);
		btnAdicionarItemMateria.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				msgErro = new StringBuilder();
				
				if(tabelaMaterias.getSelectedRow()==-1){
					
					msgErro.append("Selecione uma matéria prima\n");
				}
				if(txtQtd.getText().trim().equals("")){
					
					msgErro.append("Indique a quantia de matéria necessária\n");
				}
				
				//se estiver tudo certo
				if(msgErro.toString().trim().equals("")){
					
					it = listaReceita.iterator();
					
					boolean flagEstaNaLista = false;
					
					while (it.hasNext()){
						
						itemReceita = (IngredienteReceitaProdutoVO) it.next();
						
						//verifica se ja está na lista de receitas
						if(itemReceita.getMateriaPrima().getIdMateriaPrima() == listaMaterias.get(tabelaMaterias.getSelectedRow()).getIdMateriaPrima()){
							flagEstaNaLista = true;
							
							//soma a qtdd que ele estava tentando add novamente no item da receita que ja esta na lista
							itemReceita.setQuantidadeMateria(itemReceita.getQuantidadeMateria()+Double.parseDouble(txtQtd.getText()));
							listaAlterados.add(itemReceita);
						}
						
					}
					
					for (IngredienteReceitaProdutoVO exc : listaExcluidos) {
						
						//se estava na lista de excluidos.... 
						if(exc.getMateriaPrima().getIdMateriaPrima() == listaMaterias.get(tabelaMaterias.getSelectedRow()).getIdMateriaPrima()){
							
							exc.setQuantidadeMateria(listaMaterias.get(tabelaMaterias.getSelectedRow()).getQuantidadeDisponivel());
							listaAlterados.add(exc);
							listaReceita.add(exc);
							
							flagEstaNaLista = true;
							
							//remove da lista, pois está adicionando novamente
							listaExcluidos.remove(exc);
						}
						
					}
					
					if(!flagEstaNaLista){
						
						itemReceita = new IngredienteReceitaProdutoVO();
						
						itemReceita.setMateriaPrima(listaMaterias.get(tabelaMaterias.getSelectedRow()));
						itemReceita.setProduto(AlterarProdutoView.this.produto);
						itemReceita.setQuantidadeMateria(Double.parseDouble(txtQtd.getText()));
						
						listaReceita.add(itemReceita);
						listaIncluidos.add(itemReceita);
					}
					
					carregaDtmReceita();
				}
				else{
					
					JOptionPane.showMessageDialog(Utilidades.frmHome, msgErro.toString(), "Atenção!", JOptionPane.ERROR_MESSAGE);

				}
				
			}
		});
		this.add(btnAdicionarItemMateria);
		
		lblReceita = new JLabel("Receita do Produto");
		lblReceita.setBounds(20,290,120, 20);
		this.add(lblReceita);
		
		tabelaReceita = new JTable();
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 320, 550, 150);
		carregaDtmReceita();
		this.add(scrollPane);
		
		btnRemoverItemMateria = new JButton(new ImageIcon(getClass().getResource("/img/delete.png")));
		btnRemoverItemMateria.setBounds(570, 350, 25, 25);
		btnRemoverItemMateria.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if(tabelaReceita.getSelectedRow()!=-1){
					
					listaExcluidos.add(listaReceita.get(tabelaReceita.getSelectedRow()));
					listaReceita.remove(tabelaReceita.getSelectedRow());
					carregaDtmReceita();
					
				}
				
			}

		});
		this.add(btnRemoverItemMateria);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(170,500,90,30);
		btnSalvar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				msgErro = new StringBuilder();
				
				if(txtSabor.getText().trim().equals("")){
					
					msgErro.append("O campo 'sabor' deve estar preenchido\n");
				}
				
				//se não tiver erros
				if(msgErro.toString().trim().equals("")){
										
					AlterarProdutoView.this.produto.setTipo((String)comboTipo.getSelectedItem());
					AlterarProdutoView.this.produto.setSabor(txtSabor.getText());
					bo.atualizarProduto(AlterarProdutoView.this.produto, listaIncluidos, listaExcluidos, listaAlterados);
					
					Utilidades.frmHome.getContentPane().removeAll();
					ManterProdutoView consulta = new ManterProdutoView();
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
		btnCancelar.setBounds(300,500,90,30);
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				Utilidades.frmHome.getContentPane().removeAll();
				ManterProdutoView consulta = new ManterProdutoView();
				Utilidades.frmHome.add(consulta);
				Utilidades.frmHome.revalidate();
				
			}
		});
		this.add(btnCancelar);
		
	}

	private void carregaDtmMateria() {

		dtm = new DefaultTableModel(
				
				new Object[][] {
						
				},
				new String[] {
					"ID", "Tipo", "Sabor", "Fornecedor"
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
	
	private void carregaDtmReceita() {
		
		if(listaReceita == null){
			
			return;
		}
		
		dtm = new DefaultTableModel(
				
				new Object[][] {
						
				},
				new String[] {
					"ID", "Nome", "Sabor", "Fornecedor", "Quantidade Necessária"
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
