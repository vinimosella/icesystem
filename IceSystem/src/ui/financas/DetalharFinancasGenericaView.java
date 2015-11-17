package ui.financas;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import util.Utilidades;

public abstract class DetalharFinancasGenericaView extends JDialog{
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel lblTotal;
	private JLabel lblIdFinanca;

	public DetalharFinancasGenericaView(Object o, String titulo, Long id) {
		setTitle(titulo);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 600, 500);
		setModal(true);
		setResizable(false);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setBackground(Color.decode("#F0F8FF"));
		contentPane.setLayout(null);
		this.setLocationRelativeTo(null);
		
		lblIdFinanca = new JLabel("Id "+titulo.substring(9)+": "+id);
		lblIdFinanca.setBounds(20,20,500,20);
		contentPane.add(lblIdFinanca);
		
		table = new JTable();
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 50, 555, 370);
		contentPane.add(scrollPane);
		
		table.setModel(montaDtm(o));
		scrollPane.setViewportView(table);
		
		lblTotal = new JLabel("Total da "+titulo.substring(9)+": "+Utilidades.FORMAT.format((valorTotal())));
		lblTotal.setBounds(20, 440, 200, 14);
		contentPane.add(lblTotal);
	}
	
	public abstract DefaultTableModel montaDtm(Object o);

	public abstract Double valorTotal();

}
