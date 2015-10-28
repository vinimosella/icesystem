package ui.estoque;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
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
import bo.IngredienteReceitaProdutoBO;
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
		
		lblMaterias = new JLabel("Matérias Primas");
		lblMaterias.setBounds(20,90, 120, 20);
		this.add(lblMaterias);
		
		tabelaMaterias = new JTable();
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 120, 550, 150);
		this.add(scrollPane);
		carregaDtmMateria();
		
		lblQtd = new JLabel("Quantidade Necessária");
		lblQtd.setBounds(20,270,130,20);
		this.add(lblQtd);
		
		txtQtd = new JTextField();
		txtQtd.setBounds(160, 270, 70, 20);
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
		lblReceita.setBounds(20,290,120, 20);
		this.add(lblReceita);
		
		tabelaReceita = new JTable();
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 320, 550, 150);
		this.add(scrollPane);
		
		btnRemoverItemMateria = new JButton(new ImageIcon(getClass().getResource("/img/delete.png")));
		btnRemoverItemMateria.setBounds(570, 350, 25, 25);
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
				
				if(listaReceita.size() == 0){
					
					msgErro.append("O produto precisa de pelo menos 1 item na receita\n");
				}
				
				//se não tiver erros
				if(msgErro.toString().trim().equals("")){
					
					produto = new ProdutoVO();
					
					produto.setNome(txtNome.getText());
					produto.setSabor(txtSabor.getText());
					produto = bo.cadastrarProduto(produto);
					recBo.cadastrarReceitas(listaReceita);
					
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
