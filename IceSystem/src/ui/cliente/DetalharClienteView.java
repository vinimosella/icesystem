package ui.cliente;

import java.util.List;

import bo.ClienteBO;
import ui.DetalheGenericoView;
import util.Utilidades;
import vo.ClienteVO;
import vo.EmailVO;
import vo.EnderecoVO;
import vo.TelefoneVO;

public class DetalharClienteView extends DetalheGenericoView{

	private static final long serialVersionUID = 1L;
	
	private ClienteVO cliente;
	private ClienteBO bo;

	public DetalharClienteView(ClienteVO cliente) {
		super(Utilidades.DETALHE_CLIENTES, cliente.getRazaoSocial(), cliente);
	}

	@Override
	public List<EmailVO> setEmails(Object objeto) {
		
		bo = new ClienteBO();
		cliente = (ClienteVO) objeto;
		cliente = bo.detalharCliente(cliente);
		
		return cliente.getListaEmails();
	}

	@Override
	public List<TelefoneVO> setTelefones() {

		return cliente.getListaTelefones();
	}

	@Override
	public EnderecoVO setEndereco() {

		return cliente.getEndereco();
	}

}
