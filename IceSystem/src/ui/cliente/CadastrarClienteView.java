package ui.cliente;

import javax.swing.JOptionPane;

import ui.pessoaJuridica.CadastrarPessoaJuridicaView;
import util.Utilidades;
import vo.ClienteVO;
import vo.PessoaJuridicaVO;
import bo.ClienteBO;

public class CadastrarClienteView extends CadastrarPessoaJuridicaView{

	private static final long serialVersionUID = 1L;
	private ClienteVO cliente;
	private ClienteBO bo;
	
	public CadastrarClienteView() {
		
		super(Utilidades.CADASTRAR_CLIENTE);		
	}

	@Override
	public void btnCadastrar(PessoaJuridicaVO pj) {

		bo = new ClienteBO();
		
		cliente = new ClienteVO();
		
		cliente.setRazaoSocial(pj.getRazaoSocial());
		cliente.setCnpj(pj.getCnpj());
		cliente.setListaEmails(pj.getListaEmails());
		cliente.setListaTelefones(pj.getListaTelefones());
		cliente.setEndereco(pj.getEndereco());
				
		if(bo.cadastrarCliente(cliente)){
			
			JOptionPane.showMessageDialog(Utilidades.frmHome, "   Cliente cadastrado!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
			
		}
		else{
			
			JOptionPane.showMessageDialog(Utilidades.frmHome, "   Não foi possível cadastrar!", "Alerta!", JOptionPane.ERROR_MESSAGE);

		}
		
	}

	
}
