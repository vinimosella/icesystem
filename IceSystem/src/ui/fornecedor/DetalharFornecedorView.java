package ui.fornecedor;

import java.util.List;

import ui.DetalheGenericoView;
import util.Utilidades;
import vo.EmailVO;
import vo.EnderecoVO;
import vo.FornecedorVO;
import vo.TelefoneVO;
import bo.FornecedorBO;

public class DetalharFornecedorView extends DetalheGenericoView{

	private static final long serialVersionUID = 1L;
	private FornecedorBO bo;
	private FornecedorVO fornecedor;

	public DetalharFornecedorView(FornecedorVO fornecedor) {
		super(Utilidades.DETALHE_FORNECEDORES, fornecedor.getRazaoSocial(), fornecedor);

	}

	@Override
	public List<EmailVO> setEmails(Object objeto) {
		
		bo = new FornecedorBO();
		fornecedor = (FornecedorVO) objeto;
		fornecedor = bo.detalharFornecedor(fornecedor);
		
		return fornecedor.getListaEmails();
	}

	@Override
	public List<TelefoneVO> setTelefones() {

		return fornecedor.getListaTelefones();
	}

	@Override
	public EnderecoVO setEndereco() {

		return fornecedor.getEndereco();
	}

}
