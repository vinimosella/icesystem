package ui.financas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import vo.CompraVO;
import vo.FornecedorVO;
import vo.ItemCompraVO;
import vo.MateriaPrimaVO;
import vo.ProdutoVO;
import bo.FornecedorBO;
import bo.MateriaPrimaBO;

public class CompraMateriaPrimaView extends JPanel{

	private static final long serialVersionUID = 1L;
	private JFrame frmHome;
	private FornecedorBO fornecedorBo;
	private MateriaPrimaBO materiaPrimaBo;
	private JComboBox<String> comboFornecedor;
	private List<FornecedorVO> listaFornecedores;
	private JComboBox<String> comboMateria;
	private List<MateriaPrimaVO> listaMaterias;
	private FornecedorVO fornecedor;
	private MateriaPrimaVO materia;
	private JLabel lblQuantidade;
	private JTextField txtQuantidade;
	private JLabel lblValor;
	private JTextField txtValor;
	private JButton btnAdicionar;
	private List<ItemCompraVO> listaItensCompra;
	private ItemCompraVO itemCompra;
	private CompraVO compra;
	private Iterator<?> it;
	private StringBuilder msg;
	private JTable tabela; //TODO montar a tabela carregando a lista de itensVenda
	private JScrollPane scrollPane;
	private DefaultTableModel dtm;
	
	{
		fornecedorBo = new FornecedorBO();
		materiaPrimaBo = new MateriaPrimaBO();
		compra = new CompraVO();
	}
	
	public CompraMateriaPrimaView(JFrame frmHome, MateriaPrimaVO materiaPrima){
		
		this.frmHome=frmHome;
		this.setLayout(null);
		this.setBackground(Color.decode("#F0F8FF"));
		
		//COMBO FORNECEDOR
		comboFornecedor = new JComboBox<String>();
		comboFornecedor.setBounds(30,30,300,25);
		comboFornecedor.addItem("Selecione um fornecedor");		
		
		listaFornecedores = fornecedorBo.consultarFornecedores();
		it = listaFornecedores.iterator();
		
		while(it.hasNext()){
			
			fornecedor = (FornecedorVO) it.next();
			comboFornecedor.addItem(fornecedor.getRazaoSocial());
			
		}
		
		//CARREGA MATERIAS DO FORNECEDOR SELECIONADO
		comboFornecedor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//se selecionar o item selecione, o combo de matéria é resetado
				if(comboFornecedor.getSelectedIndex()==0){
					
					comboMateria.removeAllItems();
					comboMateria.addItem("Primeiro selecione um Fornecedor");
					comboMateria.setEnabled(false);
					
				}
				//se selecionar um fornecedor, o combo de matéria é carregado.
				else{
					
					comboMateria.removeAllItems();
					comboMateria.addItem("Selecione a matéria prima");
					comboMateria.setEnabled(true);
					
					//Pego o index -1 por causa do item selecione no combo
					listaMaterias = materiaPrimaBo.consultarMateriasPrimas(listaFornecedores.get(comboFornecedor.getSelectedIndex()-1));
					
					it = listaMaterias.iterator();
					
					while(it.hasNext()){
						
						materia = (MateriaPrimaVO) it.next();
						
						if(materia.getSabor()!=null){
							
							comboMateria.addItem(materia.getNome()+" - "+materia.getSabor());
							
						}
						else{
							
							comboMateria.addItem(materia.getNome());

						}
						
					}
				}
			}
		});
		this.add(comboFornecedor);
		
		//COMBO MATERIAS
		comboMateria = new JComboBox<String>();
		comboMateria.setBounds(30,75,300,25);
		comboMateria.addItem("Primeiro selecione um Fornecedor");
		comboMateria.setEnabled(false);
		this.add(comboMateria);
		
		//CAMPO QUANTIDADE
		lblQuantidade = new JLabel("Quantidade");
		lblQuantidade.setBounds(30,110,100,25);
		this.add(lblQuantidade);
		
		txtQuantidade = new JTextField();
		txtQuantidade.setBounds(140,110,60,25);
		this.add(txtQuantidade);
		
		//CAMPO VALOR
		lblValor = new JLabel("Valor Unitário");
		lblValor.setBounds(220,110,100,25);
		this.add(lblValor);
		
		txtValor = new JTextField();
		txtValor.setBounds(310,110,60,25);
		this.add(txtValor);
		
		//BOTAO ADICIONAR
		btnAdicionar = new JButton(new ImageIcon(getClass().getResource("/img/note.png")));
		btnAdicionar.setBounds(410,110,25,25);
		btnAdicionar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				adicionarItemCompra();
				
			}
		});
		this.add(btnAdicionar);
		
		//TABELA ITENS COMPRA
		tabela = new JTable();
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 50, 550, 400);
		this.add(scrollPane);
		carregaDtm();
		scrollPane.setViewportView(tabela);

	}

	private void carregaDtm() {

		dtm = new DefaultTableModel(
				
				new Object[][] {
						
				},
				new String[] {
					"Fornecedor", "Nome-Materia", "Sabor", "Quantidade", "Valor", "Valor Total"
				}				
		){
		
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				
			   return false;
			}
			
		};
				
		if(null != listaItensCompra | listaItensCompra.size()!=0){ //TODO

			it = listaItensCompra.iterator();
				
			while(it.hasNext()){
					
				itemCompra = (ItemCompraVO) it.next();
					
				dtm.addRow(new Object[] {
						itemCompra.getMateriaPrima().getFornecedor().getRazaoSocial(),
						itemCompra.getMateriaPrima().getNome(),
						(itemCompra.getMateriaPrima().getSabor()!=null) ? itemCompra.getMateriaPrima().getSabor() : "-",
						itemCompra.getQuantidade(),
						itemCompra.getValor(),
						itemCompra.getQuantidade()*itemCompra.getValor()
				});
				
			}
		}
		
		tabela.setModel(dtm);
		
	}

	private void adicionarItemCompra() {

		msg = new StringBuilder();
		
		//CARREGA MENSAGEM SE ALGO ESTÁ INVÁLIDO
		if(comboFornecedor.getSelectedIndex()==0){
			
			msg.append("Selecione o Fornecedor\n");
		}
		
		if(comboMateria.getSelectedIndex()==0){
			
			msg.append("Selecione a Matéria Prima\n");
		}
		
		if(txtQuantidade.getText().equals("")){
			
			msg.append("Indique a quantidade\n");
		}
		
		if(txtValor.getText().equals("")){
			
			msg.append("Indique o valor unitário\n");
		}
		
		//VERIFICA SE A MENSAGEM NÃO ESTÁ VAZIA PARA EXIBI-LA
		if(!msg.toString().trim().equals("")){
			
			JOptionPane.showMessageDialog(frmHome, msg.toString(), "Não foi possível adicionar item!", JOptionPane.ERROR_MESSAGE);

		}
		else{ //SE CAIU AQUI ESTÁ CERTO E PODE SER ADICIONADO Á COMPRA
			
			itemCompra = new ItemCompraVO();
			
			itemCompra.setCompra(compra);
			itemCompra.setMateriaPrima(listaMaterias.get(comboMateria.getSelectedIndex()-1));
			itemCompra.setQuantidade(Double.parseDouble(txtQuantidade.getText()));
			itemCompra.setValor(Double.parseDouble(txtValor.getText()));
			
			listaItensCompra.add(itemCompra);
			
		}
	}
	
}
