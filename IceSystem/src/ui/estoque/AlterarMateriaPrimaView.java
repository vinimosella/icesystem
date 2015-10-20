package ui.estoque;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.Utilidades;
import vo.FornecedorVO;
import vo.MateriaPrimaVO;
import bo.FornecedorBO;
import bo.MateriaPrimaBO;

public class AlterarMateriaPrimaView extends JDialog{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNome;
	private JTextField txtNome;
	private JLabel lblSabor;
	private JTextField txtSabor;
	private JComboBox<String> comboFornecedor;
	private MateriaPrimaBO bo;
	private MateriaPrimaVO materiaPrima;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private FornecedorBO fornBo;
	private List<FornecedorVO> listaFornecedores;
	private FornecedorVO fornecedor;
	
	{
		bo = new MateriaPrimaBO();
		fornBo = new FornecedorBO();
		listaFornecedores = fornBo.consultarFornecedores();
	}

	public AlterarMateriaPrimaView(MateriaPrimaVO materiaPrima) {
		this.materiaPrima = materiaPrima;
		setTitle(Utilidades.CADASTRAR_MATERIA_PRIMA);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 259, 200);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setBackground(Color.decode("#F0F8FF"));
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);
				
		lblNome = new JLabel("Nome:");
		lblNome.setBounds(20, 20, 70, 25);
		contentPane.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(100,20,130,25);
		if(materiaPrima.getNome()!=null){
			txtNome.setText(materiaPrima.getNome());
		}
		contentPane.add(txtNome);
		
		lblSabor = new JLabel("Sabor:");
		lblSabor.setBounds(20, 60, 70, 25);
		contentPane.add(lblSabor);
		
		txtSabor = new JTextField();
		txtSabor.setBounds(100,60,130,25);
		if(materiaPrima.getSabor()!=null){
			txtSabor.setText(materiaPrima.getSabor());
		}
		contentPane.add(txtSabor);
		
		comboFornecedor = new JComboBox<String>();
		comboFornecedor.setBounds(20,95,180,25);
		comboFornecedor.addItem("Selecione um Fornecedor");
		
		Iterator<FornecedorVO> it = listaFornecedores.iterator();
		
		while(it.hasNext()){
			
			fornecedor = it.next();
			comboFornecedor.addItem(fornecedor.getRazaoSocial());
			if(materiaPrima.getFornecedor().getRazaoSocial().equals(fornecedor.getRazaoSocial())){
				
				comboFornecedor.setSelectedItem(fornecedor.getRazaoSocial());
			}
			
		}
		contentPane.add(comboFornecedor);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(30,130,90,30);
		btnSalvar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//TODO VALIDAR TAMBEM O FORNECEDOR E EXIBIR MSG INDIVIDUAL PRA CADA UM... TBM ALTERAR NA TELA DE CADASTRO
				//se n�o estiver vazio
				if(!txtNome.getText().trim().equals("")){
					
					AlterarMateriaPrimaView.this.materiaPrima = new MateriaPrimaVO();
					
					AlterarMateriaPrimaView.this.materiaPrima.setNome(txtNome.getText());
					AlterarMateriaPrimaView.this.materiaPrima.setSabor(txtSabor.getText());
					AlterarMateriaPrimaView.this.materiaPrima.setFornecedor(listaFornecedores.get(comboFornecedor.getSelectedIndex()-1));
					bo.alterarMateriaPrima(AlterarMateriaPrimaView.this.materiaPrima);
					
				}
								
				Utilidades.frmHome.getContentPane().removeAll();
				ConsultaMateriaPrimaView consulta = new ConsultaMateriaPrimaView();
				Utilidades.frmHome.add(consulta);
				Utilidades.frmHome.revalidate();
				AlterarMateriaPrimaView.this.dispose();
				
			}
		});
		contentPane.add(btnSalvar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(130,130,90,30);
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				AlterarMateriaPrimaView.this.dispose();
			}
		});
		contentPane.add(btnCancelar);
		
	}
	
}