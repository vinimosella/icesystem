package ui.cliente;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ui.pessoaJuridica.CadastrarPessoaJuridicaView;
import vo.ClienteVO;
import vo.PessoaJuridicaVO;
import bo.ClienteBO;

public class CadastrarClienteView extends CadastrarPessoaJuridicaView{

	private static final long serialVersionUID = 1L;
	private ClienteVO cliente;
	private ClienteBO bo;
	private JFrame frmHome;
	
	public CadastrarClienteView(JFrame frmHome) {
		
		super(frmHome);
		this.frmHome = frmHome;
		
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
			
			JOptionPane.showMessageDialog(CadastrarClienteView.this.frmHome, "   Cliente cadastrado!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
			
		}
		else{
			
			JOptionPane.showMessageDialog(CadastrarClienteView.this.frmHome, "   Não foi possível cadastrar!", "Alerta!", JOptionPane.ERROR_MESSAGE);

		}
		
	}

	
}
