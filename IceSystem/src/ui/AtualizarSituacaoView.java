package ui;

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

import vo.SituacaoVO;
import bo.SituacaoBO;

public abstract class AtualizarSituacaoView extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblSituacao;
	private JComboBox<String> comboSituacao;
	private List<SituacaoVO> listaSituacoes;
	private SituacaoBO bo;
	private SituacaoVO situacao;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private Object o;
	
	{
		bo = new SituacaoBO();
	}

	public AtualizarSituacaoView(SituacaoVO situacaoEntrada, String titulo, Object o) {
		this.o = o;
		
		setTitle(titulo);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 259, 146);
		setModal(true);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setBackground(Color.decode("#F0F8FF"));
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		lblSituacao = new JLabel("Situação");
		lblSituacao.setBounds(20, 20, 70, 14);
		contentPane.add(lblSituacao);
		
		comboSituacao = new JComboBox<String>();
		comboSituacao.setBounds(100, 20, 120, 25);
		listaSituacoes = bo.consultarSituacoes();
		Iterator<SituacaoVO> it = listaSituacoes.iterator();
		
		while(it.hasNext()){
			
			situacao= (SituacaoVO) it.next();
			
			comboSituacao.addItem(situacao.getDescricao());
		
		}
		
		comboSituacao.setSelectedItem(situacaoEntrada.getDescricao());
		contentPane.add(comboSituacao);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(30,60,90,30);
		btnSalvar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				actionBtnSalvar(listaSituacoes.get(comboSituacao.getSelectedIndex()), AtualizarSituacaoView.this.o);
				
				AtualizarSituacaoView.this.dispose();
				
			}
		});
		contentPane.add(btnSalvar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(130,60,90,30);
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				AtualizarSituacaoView.this.dispose();
			}
		});
		contentPane.add(btnCancelar);
		
	}
	
	public abstract void actionBtnSalvar(SituacaoVO situacao, Object o);
	
}
