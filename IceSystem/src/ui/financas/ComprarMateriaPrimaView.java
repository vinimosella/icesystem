package ui.financas;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import vo.FornecedorVO;
import vo.MateriaPrimaVO;
import bo.FornecedorBO;

public class ComprarMateriaPrimaView extends JPanel{

	private static final long serialVersionUID = 1L;
	//private JFrame frmHome;
	private FornecedorBO fornecedorBo;
	//private MateriaPrimaBO materiaPrimaBo;
	private JComboBox<String> comboFornecedor;
	private List<FornecedorVO> listaFornecedores;
	private FornecedorVO fornecedor;
	private Iterator<?> it;
	
	{
		fornecedorBo = new FornecedorBO();
		//materiaPrimaBo = new MateriaPrimaBO();
	}
	
	public ComprarMateriaPrimaView(JFrame frmHome, MateriaPrimaVO materiaPrima){
		
		//this.frmHome=frmHome;
		this.setLayout(null);
		this.setBackground(Color.decode("#F0F8FF"));
		
		//COMBO FORNECEDOR
		comboFornecedor = new JComboBox<String>();
		comboFornecedor.setBounds(30,30,150,25);
		comboFornecedor.addItem("Selecione");		
		
		listaFornecedores = fornecedorBo.consultarFornecedores();
		it = listaFornecedores.iterator();
		
		while(it.hasNext()){
			
			fornecedor = (FornecedorVO) it.next();
			comboFornecedor.addItem(fornecedor.getRazaoSocial());
			
		}
		this.add(comboFornecedor);
		
		//COMBO PRODUTOS
	}
	
}
