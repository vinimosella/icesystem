package ui.cliente;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JOptionPane;

import ui.pessoaJuridica.AlterarPessoaJuridicaView;
import util.Utilidades;
import vo.ClienteVO;
import vo.EmailVO;
import vo.PessoaJuridicaVO;
import vo.TelefoneVO;
import bo.ClienteBO;

public class AlterarClienteView extends AlterarPessoaJuridicaView{

	private static final long serialVersionUID = 1L;

	private ClienteBO bo;
	private ClienteVO cliente;
	
	public AlterarClienteView(PessoaJuridicaVO pj) {
		super(pj,"Atualizar Cliente");

	}

	@Override
	public void btnCancelar() {

		Utilidades.frmHome.getContentPane().removeAll();
		ManterClienteView consulta = new ManterClienteView();
		Utilidades.frmHome.getContentPane().add(consulta, BorderLayout.CENTER);
		Utilidades.frmHome.getContentPane().revalidate();
		
	}

	@Override
	public void btnAtualizar(PessoaJuridicaVO pj, List<TelefoneVO> listaTelAlt, List<EmailVO> listaEmailAlt) {
		
		bo = new ClienteBO();
		
		cliente = new ClienteVO();
		
		cliente.setStatus(Utilidades.STATUS_ATIVO);
		cliente.setIdPessoaJuridica(pj.getIdPessoaJuridica());
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
			ManterClienteView consulta = new ManterClienteView();
			Utilidades.frmHome.getContentPane().add(consulta, BorderLayout.CENTER);
			Utilidades.frmHome.getContentPane().revalidate();		
		}
		else{
			
			JOptionPane.showMessageDialog(Utilidades.frmHome, "   N�o foi poss�vel atualizar!", "Alerta!", JOptionPane.ERROR_MESSAGE);

		}
		
	}

}
