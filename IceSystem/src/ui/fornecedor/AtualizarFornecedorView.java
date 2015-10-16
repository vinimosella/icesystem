package ui.fornecedor;

import java.awt.BorderLayout;

import javax.swing.JOptionPane;

import ui.pessoaJuridica.AtualizarPessoaJuridicaView;
import util.Utilidades;
import vo.FornecedorVO;
import vo.PessoaJuridicaVO;
import bo.FornecedorBO;

public class AtualizarFornecedorView extends AtualizarPessoaJuridicaView{

	private static final long serialVersionUID = 1L;
	
	private FornecedorBO bo;
	private FornecedorVO fornecedor;

	public AtualizarFornecedorView(PessoaJuridicaVO pj) {
		super(pj);
	}

	@Override
	public void btnCancelar() {
		
		Utilidades.frmHome.getContentPane().removeAll();
		ConsultarFornecedorView consulta = new ConsultarFornecedorView(Utilidades.CONSULTA_FORNECEDORES);
		Utilidades.frmHome.getContentPane().add(consulta, BorderLayout.CENTER);
		Utilidades.frmHome.getContentPane().revalidate();
		
	}

	@Override
	public void btnAtualizar(PessoaJuridicaVO pj) {
		
		bo = new FornecedorBO();
		
		fornecedor = new FornecedorVO();
		
		fornecedor.setRazaoSocial(pj.getRazaoSocial());
		fornecedor.setCnpj(pj.getCnpj());
		fornecedor.setListaEmails(pj.getListaEmails());
		fornecedor.setListaTelefones(pj.getListaTelefones());
		fornecedor.setEndereco(pj.getEndereco());
		
		if(bo.atualizarFornecedor(fornecedor)){
			
			JOptionPane.showMessageDialog(Utilidades.frmHome, "   Fornecedor atualizado!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
			
		}
		else{
			
			JOptionPane.showMessageDialog(Utilidades.frmHome, "   Não foi possível atualizar!", "Alerta!", JOptionPane.ERROR_MESSAGE);

		}
		
	}

}
