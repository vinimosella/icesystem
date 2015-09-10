package ui.funcionario;

import java.util.List;

import bo.FuncionarioBO;
import ui.DetalheGenericoView;
import util.Utilidades;
import vo.EmailVO;
import vo.EnderecoVO;
import vo.FuncionarioVO;
import vo.TelefoneVO;

public class DetalharFuncionarioView extends DetalheGenericoView{

	private static final long serialVersionUID = 1L;
	
	private FuncionarioVO funcionario;
	private FuncionarioBO bo;

	public DetalharFuncionarioView(FuncionarioVO funcionario) {
		super(Utilidades.DETALHE_FUNCIONARIOS, funcionario.getNome(), funcionario);

	}

	@Override
	public List<EmailVO> setEmails(Object objeto) {

		bo = new FuncionarioBO();
		funcionario = (FuncionarioVO) objeto;
		funcionario = bo.detalharFuncionario(funcionario);
				
		return funcionario.getListaEmails();
	}

	@Override
	public List<TelefoneVO> setTelefones() {

		return funcionario.getListaTelefones();
	}

	@Override
	public EnderecoVO setEndereco() {

		return funcionario.getEndereco();
	}

	
}
