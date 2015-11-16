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

public class CadastrarProdutoView extends JPanel{

	private static final long serialVersionUID = 1L;
	private JLabel lblNomePagina;
	private JLabel lblTipo;
	private JComboBox<String> comboTipo;
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
	private JScrollPane scrollPaneMat;
	private JScrollPane scrollPaneRec;
	private DefaultTableModel dtm;
	private Iterator<?> it;
	private JLabel lblQtd;
	private JTextField txtQtd;
	private JButton btnAdicionarItemMateria;
	private JButton btnRemoverItemMateria;
	private JLabel lblMaterias;
	private JLabel lblReceita;
	private IngredienteReceitaProdutoBO recBo;
	
	{
		bo = new ProdutoBO();
		matBo = new MateriaPrimaBO();
		recBo = new IngredienteReceitaProdutoBO();
		listaMaterias = matBo.consultarMateriasPrimas();
		listaReceita = new ArrayList<IngredienteReceitaProdutoVO>();
	}

	public CadastrarProdutoView() {
		
		this.setLayout(null);
		this.setBackground(Color.decode("#F0F8FF"));
		
		lblNomePagina = new JLabel(Utilidades.CADASTRAR_PRODUTO);
		this.add(lblNomePagina);
		
		lblTipo = new JLabel("Tipo:");
		this.add(lblTipo);
		
		comboTipo = new JComboBox<String>();
		for(int x = 0; x<Utilidades.VET_TIPOS_PRODUTOS.length;x++){
			
			comboTipo.addItem(Utilidades.VET_TIPOS_PRODUTOS[x]);
		}
		this.add(comboTipo);
		
		lblSabor = new JLabel("Sabor:");
		this.add(lblSabor);
		
		txtSabor = new JTextField();
		this.add(txtSabor);
		
		lblMaterias = new JLabel("Matérias Primas");
		this.add(lblMaterias);
		
		tabelaMaterias = new JTable();
		scrollPaneMat = new JScrollPane();
		this.add(scrollPaneMat);
		carregaDtmMateria();
		
		lblQtd = new JLabel("Quantidade Necessária:");
		this.add(lblQtd);
		
		txtQtd = new JTextField();
		this.add(txtQtd);
		
		btnAdicionarItemMateria = new JButton(new ImageIcon(getClass().getResource("/img/down.png")));
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
						}
						
					}
					
					if(!flagEstaNaLista){
						
						itemReceita = new IngredienteReceitaProdutoVO();
						
						itemReceita.setMateriaPrima(listaMaterias.get(tabelaMaterias.getSelectedRow()));
						itemReceita.setQuantidadeMateria(Double.parseDouble(txtQtd.getText()));
						
						listaReceita.add(itemReceita);
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
		this.add(lblReceita);
		
		tabelaReceita = new JTable();
		scrollPaneRec = new JScrollPane();
		this.add(scrollPaneRec);
		
		btnRemoverItemMateria = new JButton("Remover item da receita", new ImageIcon(getClass().getResource("/img/remover.png")));
		btnRemoverItemMateria.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if(tabelaReceita.getSelectedRow()!=-1){
					
					listaReceita.remove(tabelaReceita.getSelectedRow());
					carregaDtmReceita();
					
				}
				
			}
		});
		this.add(btnRemoverItemMateria);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				msgErro = new StringBuilder();
				
				if(txtSabor.getText().trim().equals("")){
					
					msgErro.append("O campo 'sabor' deve estar preenchido\n");
				}
				
				if(listaReceita.size() == 0){
					
					msgErro.append("O produto precisa de pelo menos 1 item na receita\n");
				}
				
				//se não tiver erros
				if(msgErro.toString().trim().equals("")){
					
					produto = new ProdutoVO();
					
					produto.setTipo((String)comboTipo.getSelectedItem());
					produto.setSabor(txtSabor.getText());
					produto = bo.cadastrarProduto(produto);
					
					it = listaReceita.iterator();	
					while(it.hasNext()){
						
						itemReceita = (IngredienteReceitaProdutoVO) it.next();
						itemReceita.setProduto(produto);
					}
					
					recBo.cadastrarReceitas(listaReceita, produto);
					
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
		
		geraBounds();
	}
	
	private void geraBounds(){
		
		int altura = 10;
		
		lblNomePagina.setBounds(10,altura,170,25);
		
		lblTipo.setBounds(20, altura+=40, 70, 20);
		comboTipo.setBounds(100,altura,130,20);
		
		lblSabor.setBounds(20, altura+=30, 70, 20);
		txtSabor.setBounds(100,altura,130,20);
		
		lblMaterias.setBounds(20,altura+=30, 120, 20);
		
		scrollPaneMat.setBounds(20, altura+=20, 550, 150);
		
		lblQtd.setBounds(20,altura+=160,150,20);
		txtQtd.setBounds(165, altura, 70, 20);
		btnAdicionarItemMateria.setBounds(240, altura, 25, 25);
		
		lblReceita.setBounds(20,altura+=40,120, 20);
		btnRemoverItemMateria.setBounds(350, altura-10, 200, 25);
		
		scrollPaneRec.setBounds(20, altura+=20, 550, 150);
		
		btnSalvar.setBounds(210,altura+=160,90,30);
		btnCancelar.setBounds(320,altura,90,30);
		
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
		scrollPaneMat.setViewportView(tabelaMaterias);
		
	}
	
	private void carregaDtmReceita(){
		
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
		scrollPaneRec.setViewportView(tabelaReceita);
		
	}
	
}
