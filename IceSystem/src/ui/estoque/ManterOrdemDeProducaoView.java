package ui.estoque;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import util.Utilidades;
import vo.OrdemProducaoVO;
import bo.OrdemProducaoBO;

public class ManterOrdemDeProducaoView extends JPanel{

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JLabel lblOrdemProducao;
	private JButton btnGerarOrdem;
	private JButton btnAtualizar;
	private DefaultTableModel dtm;
	private OrdemProducaoVO op;
	private List<OrdemProducaoVO> listaOps;
	private OrdemProducaoBO bo;
	private Iterator<?> it;
	private JScrollPane scrollPane;
	private JLabel lblTodas;
	private JRadioButton radioTodas;
	private JLabel lblSolicitado;
	private JRadioButton radioSolicitado;
	private JLabel lblCancelado;
	private JRadioButton radioCancelado;
	private JLabel lblFinalizado;
	private JRadioButton radioFinalizado;
	private ButtonGroup grupoRadio;
	
	{
		
		bo = new OrdemProducaoBO();
		listaOps = bo.consultarSolicitadas();
		
	}
	
	//é necessario o codUser pra criar o botao da tela de alterar/delete apenas se for userAdmin
	public ManterOrdemDeProducaoView(){
		
		this.setLayout(null);
		this.setBackground(Color.decode("#F0F8FF"));
		
		lblOrdemProducao = new JLabel("Ordens De Produção");
		lblOrdemProducao.setBounds(10, 11, 430, 14);
		this.add(lblOrdemProducao);
		
		//RADIOS
		grupoRadio = new ButtonGroup();
		
		lblTodas = new JLabel("Todas");
		this.add(lblTodas);
		
		radioTodas = new JRadioButton();
		radioTodas.setBackground(Color.decode("#F0F8FF"));
		radioTodas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				listaOps = bo.consultarTodas();
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

				listaOps = bo.consultarSolicitadas();
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

				listaOps = bo.consultarFinalizadas();
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

				listaOps = bo.consultarCanceladas();
				carregaDtm();

			}
		});
		this.add(radioCancelado);
		grupoRadio.add(radioCancelado);
		
		gerarBoundsRadios();
		
		//TABELA
		table = new JTable();
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 60, 550, 400);
		this.add(scrollPane);
		carregaDtm();
		scrollPane.setViewportView(table);
		
		btnGerarOrdem = new JButton("Gerar Ordem");
		btnGerarOrdem.setBounds(151, 480, 111, 23);
		btnGerarOrdem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Utilidades.frmHome.getContentPane().removeAll();
				GerarOrdemProducao gerarOrdem = new GerarOrdemProducao();
				Utilidades.frmHome.getContentPane().add(gerarOrdem, BorderLayout.CENTER);
				Utilidades.frmHome.getContentPane().revalidate();
				
			}
			
		});
		this.add(btnGerarOrdem);
				
		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setBounds(341, 480, 111, 23);
		btnAtualizar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(table.getSelectedRow() != -1){
					
					btnAtualizar();
					
				}
				
			}
			
		});
		this.add(btnAtualizar);
	
		
	}

	private void carregaDtm() {

		dtm = new DefaultTableModel(
				
				new Object[][] {
						
				},
				new String[] {
					"ID Ordem", "Tipo", "Sabor", "Quantidade", "Situação"
				}				
		){
		
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				
			   return false;
			}
			
		};
				
		it = listaOps.iterator();
			
		while(it.hasNext()){
				
			op = (OrdemProducaoVO) it.next();

			dtm.addRow(new Object[] {
					op.getIdOrdemProducao(),
					op.getProduto().getTipo(),
					(op.getProduto().getSabor()!=null) ? op.getProduto().getSabor() : "-",
					op.getQuantidade(),
					op.getSituacao().getDescricao()
			});
			
		}
		
		table.setModel(dtm);
		
	}
	
	private void btnAtualizar() {
		
		//se ja estiver cancelada ou finalizada...
		if(listaOps.get(table.getSelectedRow()).getSituacao().getDescricao().equals(Utilidades.CANCELADO) || listaOps.get(table.getSelectedRow()).getSituacao().getDescricao().equals(Utilidades.FINALIZADO)){
			
			JOptionPane.showMessageDialog(Utilidades.frmHome, "Não é possível alterar itens finalizados ou cancelados!", "Alerta!", JOptionPane.ERROR_MESSAGE);
			
		}
		else{
			
			AlterarOrdemProducaoView atualizarOp = new AlterarOrdemProducaoView(listaOps.get(table.getSelectedRow()));
			atualizarOp.setVisible(true);	
			
		}
		
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
	
}
