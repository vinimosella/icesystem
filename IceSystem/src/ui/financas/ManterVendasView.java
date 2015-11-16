package ui.financas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ui.ManterGenericoView;
import util.Utilidades;
import vo.VendaVO;
import bo.VendaBO;

public class ManterVendasView extends ManterGenericoView{

	private static final long serialVersionUID = 1L;
	private VendaBO bo;
	private List<VendaVO> listaVendas;
	private VendaVO venda;
	private JLabel lblTodas;
	private JRadioButton radioTodas;
	private JLabel lblSolicitado;
	private JRadioButton radioSolicitado;
	private JLabel lblCancelado;
	private JRadioButton radioCancelado;
	private JLabel lblFinalizado;
	private JRadioButton radioFinalizado;
	private ButtonGroup grupoRadio;
	
	public ManterVendasView() {
		super(Utilidades.CONSULTA_VENDAS);
		
		super.getBtnRemover().setVisible(false);
		super.boundsBtn();
		
		super.getScrollPane().setBounds(20, 70, 550, 400);
		
		//RADIOS
		grupoRadio = new ButtonGroup();
		
		lblTodas = new JLabel("Todas");
		this.add(lblTodas);
		
		radioTodas = new JRadioButton();
		radioTodas.setBackground(Color.decode("#F0F8FF"));
		radioTodas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				listaVendas = bo.consultarVendas();
				carregaDtm();
			}
		});
		this.add(radioTodas);
		grupoRadio.add(radioTodas);
		
		lblSolicitado = new JLabel("Solicitadas");
		this.add(lblSolicitado);
		
		radioSolicitado = new JRadioButton();
		radioSolicitado.setBackground(Color.decode("#F0F8FF"));
		radioSolicitado.setSelected(true);
		radioSolicitado.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				listaVendas = bo.consultarSolicitadas();
				carregaDtm();
			}
		});
		this.add(radioSolicitado);
		grupoRadio.add(radioSolicitado);
		
		lblFinalizado = new JLabel("Finalizadas");
		this.add(lblFinalizado);
		
		radioFinalizado = new JRadioButton();
		radioFinalizado.setBackground(Color.decode("#F0F8FF"));
		radioFinalizado.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				listaVendas = bo.consultarFinalizadas();
				carregaDtm();
			}
		});
		this.add(radioFinalizado);
		grupoRadio.add(radioFinalizado);
		
		lblCancelado = new JLabel("Canceladas");
		this.add(lblCancelado);
				
		radioCancelado = new JRadioButton();
		radioCancelado.setBackground(Color.decode("#F0F8FF"));
		radioCancelado.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				listaVendas = bo.consultarCanceladas();
				carregaDtm();
			}
		});
		this.add(radioCancelado);
		grupoRadio.add(radioCancelado);
		
		gerarBoundsRadios();
	}
	
	private void carregaDtm(){
		
		carregaDtm(super.getTable(), super.getDtm());
	}
	
	private void gerarBoundsRadios(){
		
		int x = 60;
		
		lblTodas.setBounds(x, 37, 40,17);
		radioTodas.setBounds(x+=40, 37, 20, 20);
		
		lblSolicitado.setBounds(x+=60,37, 70,17);
		radioSolicitado.setBounds(x+=70,37, 20, 20);
		
		lblFinalizado.setBounds(x+=60,37, 70,17);
		radioFinalizado.setBounds(x+=70,37, 20, 20);
		
		lblCancelado.setBounds(x+=60,37, 70,17);
		radioCancelado.setBounds(x+=70,37, 20, 20);
		
	}

	@Override
	public void btnAtualizar(Integer linhaSelecionada) {
		
		//se ja estiver cancelada ou finalizada...
		if(listaVendas.get(linhaSelecionada).getSituacao().getDescricao().equals(Utilidades.CANCELADO) || listaVendas.get(linhaSelecionada).getSituacao().getDescricao().equals(Utilidades.FINALIZADO)){
			
			JOptionPane.showMessageDialog(Utilidades.frmHome, "Não é possível alterar itens finalizados ou cancelados!", "Alerta!", JOptionPane.ERROR_MESSAGE);
			
		}
		else{
			
			AlterarVendasView atualizarVendas = new AlterarVendasView(listaVendas.get(linhaSelecionada));
			atualizarVendas.setVisible(true);
			
		}
		
	}

	@Override
	public void btnDetalhar(Integer linhaSelecionada) {

		DetalharVendaView detalharVenda = new DetalharVendaView(listaVendas.get(linhaSelecionada));
		detalharVenda.setVisible(true);
		
	}

	@Override
	public void btnCadastrar() {
		
		Utilidades.frmHome.getContentPane().removeAll();
		VenderProdutoView efetVendas = new VenderProdutoView();
		Utilidades.frmHome.getContentPane().add(efetVendas, BorderLayout.CENTER);
		Utilidades.frmHome.getContentPane().revalidate();
		
	}

	@Override
	public void carregaDtm(JTable table, DefaultTableModel dtm) {
				
		dtm = new DefaultTableModel(
				
				new Object[][] {
						
				},
				new String[] {
					"ID", "Situação", "Data", "Cliente"
				}				
		){
		
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				
			   return false;
			}
			
		};
		
		if(bo==null){
			bo = new VendaBO();
		}
		if(listaVendas==null){
			listaVendas = bo.consultarSolicitadas();
		}
		
		Iterator<VendaVO> it = listaVendas.iterator();
				
		while(it.hasNext()){
				
			venda = it.next();
				
			dtm.addRow(new Object[] {
					venda.getIdVenda(),
					venda.getSituacao().getDescricao(),
					venda.getDataVenda().toString(),
					venda.getCliente().getRazaoSocial()
			});
			
		}			
		
		table.setModel(dtm);
		
	}

	//ESSA TELA NÃO POSSUI ESSE BOTAO
	@Override
	public void btnRemover(Integer linhaSelecionada) {
		
	}
	
	
}
