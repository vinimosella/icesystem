package ui.cliente;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JOptionPane;

import ui.pessoaJuridica.AtualizarPessoaJuridicaView;
import util.Utilidades;
import vo.ClienteVO;
import vo.EmailVO;
import vo.PessoaJuridicaVO;
import vo.TelefoneVO;
import bo.ClienteBO;

public class AtualizarClienteView extends AtualizarPessoaJuridicaView{

	private static final long serialVersionUID = 1L;

	private ClienteBO bo;
	private ClienteVO cliente;
	
	public AtualizarClienteView(PessoaJuridicaVO pj) {
		super(pj,"Atualizar Cliente");

	}

	@Override
	public void btnCancelar() {

		Utilidades.frmHome.getContentPane().removeAll();
		ConsultarClienteView consulta = new ConsultarClienteView();
		Utilidades.frmHome.getContentPane().add(consulta, BorderLayout.CENTER);
		Utilidades.frmHome.getContentPane().revalidate();
		
	}

	@Override
	public void btnAtualizar(PessoaJuridicaVO pj, List<TelefoneVO> listaTelAlt, List<EmailVO> listaEmailAlt) {
		
		bo = new ClienteBO();
		
		cliente = new ClienteVO();
		
		cliente.setRazaoSocial(pj.getRazaoSocial());
		cliente.setCnpj(pj.getCnpj());
		cliente.setListaEmails(pj.getListaEmails());
		cliente.setListaTelefones(pj.getListaTelefones());
		cliente.setEndereco(pj.getEndereco());
		
		List<List<EmailVO>> listaListaEmail = bo.gerenciaMudancasEmails(cliente.getListaEmails(), listaEmailAlt);
		List<List<TelefoneVO>> listaListaTelefone = bo.gerenciaMudancasTelefones(cliente.getListaTelefones(), listaTelAlt);
		
		if(bo.atualizarCliente(cliente, listaListaEmail, listaListaTelefone)){
			
			JOptionPane.showMessageDialog(Utilidades.frmHome, "   Cliente atualizado!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
			Utilidades.frmHome.getContentPane().removeAll();
			ConsultarClienteView consulta = new ConsultarClienteView();
			Utilidades.frmHome.getContentPane().add(consulta, BorderLayout.CENTER);
			Utilidades.frmHome.getContentPane().revalidate();		
		}
		else{
			
			JOptionPane.showMessageDialog(Utilidades.frmHome, "   Não foi possível atualizar!", "Alerta!", JOptionPane.ERROR_MESSAGE);

		}
		
	}

}
