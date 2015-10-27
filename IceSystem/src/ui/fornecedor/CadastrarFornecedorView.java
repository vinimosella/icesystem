package ui.fornecedor;

import java.awt.BorderLayout;

import javax.swing.JOptionPane;

import ui.pessoaJuridica.CadastrarPessoaJuridicaView;
import util.Utilidades;
import vo.FornecedorVO;
import vo.PessoaJuridicaVO;
import bo.FornecedorBO;

public class CadastrarFornecedorView extends CadastrarPessoaJuridicaView{

	private static final long serialVersionUID = 1L;
	private FornecedorVO fornecedor;
	private FornecedorBO bo;
	
	public CadastrarFornecedorView() {
		super(Utilidades.CADASTRAR_FORNECEDOR);
		
	}

	@Override
	public void btnCadastrar(PessoaJuridicaVO pj) {

		bo = new FornecedorBO();
		
		fornecedor = new FornecedorVO();
		
		fornecedor.setRazaoSocial(pj.getRazaoSocial());
		fornecedor.setCnpj(pj.getCnpj());
		fornecedor.setListaEmails(pj.getListaEmails());
		fornecedor.setListaTelefones(pj.getListaTelefones());
		fornecedor.setEndereco(pj.getEndereco());
		
		if(bo.cadastrarFornecedor(fornecedor)){
			
			Utilidades.frmHome.getContentPane().removeAll();
			ConsultarFornecedorView consultarFornecedor = new ConsultarFornecedorView();
			Utilidades.frmHome.getContentPane().add(consultarFornecedor, BorderLayout.CENTER);
			Utilidades.frmHome.getContentPane().revalidate();
			
		}
		else{
			
			JOptionPane.showMessageDialog(Utilidades.frmHome, "   Não foi possível cadastrar!", "Alerta!", JOptionPane.ERROR_MESSAGE);

		}
		
	}

}
