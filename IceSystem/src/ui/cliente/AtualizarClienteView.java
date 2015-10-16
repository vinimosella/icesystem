package ui.cliente;

import java.awt.BorderLayout;

import javax.swing.JOptionPane;

import ui.pessoaJuridica.AtualizarPessoaJuridicaView;
import util.Utilidades;
import vo.ClienteVO;
import vo.PessoaJuridicaVO;
import bo.ClienteBO;

public class AtualizarClienteView extends AtualizarPessoaJuridicaView{

	private static final long serialVersionUID = 1L;

	private ClienteBO bo;
	private ClienteVO cliente;
	
	public AtualizarClienteView(PessoaJuridicaVO pj) {
		super(pj);

	}

	@Override
	public void btnCancelar() {

		Utilidades.frmHome.getContentPane().removeAll();
		ConsultarClienteView consulta = new ConsultarClienteView(Utilidades.CONSULTA_CLIENTES);
		Utilidades.frmHome.getContentPane().add(consulta, BorderLayout.CENTER);
		Utilidades.frmHome.getContentPane().revalidate();
		
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
			
			JOptionPane.showMessageDialog(Utilidades.frmHome, "   Cliente atualizado!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
			
		}
		else{
			
			JOptionPane.showMessageDialog(Utilidades.frmHome, "   Não foi possível atualizar!", "Alerta!", JOptionPane.ERROR_MESSAGE);

		}
		
	}

}
