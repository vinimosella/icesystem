package ui.cliente;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ui.pessoaJuridica.AtualizarPessoaJuridicaView;
import util.Utilidades;
import vo.ClienteVO;
import vo.PessoaJuridicaVO;
import bo.ClienteBO;

public class AtualizarClienteView extends AtualizarPessoaJuridicaView{

	private static final long serialVersionUID = 1L;

	private JFrame frmHome;
	private Byte codUser;
	private ClienteBO bo;
	private ClienteVO cliente;
	
	public AtualizarClienteView(JFrame frmHome, PessoaJuridicaVO pj, Byte codUser) {
		super(frmHome, pj, codUser);

		this.frmHome = frmHome;
		this.codUser = codUser;
	}

	@Override
	public void btnCancelar() {

		AtualizarClienteView.this.frmHome.getContentPane().removeAll();
		ConsultarClienteView consulta = new ConsultarClienteView(AtualizarClienteView.this.frmHome, AtualizarClienteView.this.codUser, Utilidades.CONSULTA_CLIENTES);
		AtualizarClienteView.this.frmHome.getContentPane().add(consulta, BorderLayout.CENTER);
		AtualizarClienteView.this.frmHome.getContentPane().revalidate();
		
	}

	@Override
	public void btnAtualizar(PessoaJuridicaVO pj) {
		
		bo = new ClienteBO();
		
		cliente = new ClienteVO();
		
		cliente.setRazaoSocial(pj.getRazaoSocial());
		cliente.setCnpj(pj.getCnpj());
		cliente.setListaEmails(pj.getListaEmails());
		cliente.setListaTelefones(pj.getListaTelefones());
		cliente.setEndereco(pj.getEndereco());
		
		if(bo.atualizarCliente(cliente)){
			
			JOptionPane.showMessageDialog(AtualizarClienteView.this.frmHome, "   Cliente atualizado!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
			
		}
		else{
			
			JOptionPane.showMessageDialog(AtualizarClienteView.this.frmHome, "   Não foi possível atualizar!", "Alerta!", JOptionPane.ERROR_MESSAGE);

		}
		
	}

}
