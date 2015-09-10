package ui.fornecedor;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ui.pessoaJuridica.AtualizarPessoaJuridicaView;
import util.Utilidades;
import vo.FornecedorVO;
import vo.PessoaJuridicaVO;
import bo.FornecedorBO;

public class AtualizarFornecedorView extends AtualizarPessoaJuridicaView{

	private static final long serialVersionUID = 1L;
	
	private JFrame frmHome;
	private Byte codUser;
	private FornecedorBO bo;
	private FornecedorVO fornecedor;

	public AtualizarFornecedorView(JFrame frmHome, PessoaJuridicaVO pj, Byte codUser) {
		super(frmHome, pj, codUser);
		
		this.frmHome = frmHome;
		this.codUser = codUser;
	}

	@Override
	public void btnCancelar() {
		
		AtualizarFornecedorView.this.frmHome.getContentPane().removeAll();
		ConsultarFornecedorView consulta = new ConsultarFornecedorView(AtualizarFornecedorView.this.frmHome, AtualizarFornecedorView.this.codUser, Utilidades.CONSULTA_FORNECEDORES);
		AtualizarFornecedorView.this.frmHome.getContentPane().add(consulta, BorderLayout.CENTER);
		AtualizarFornecedorView.this.frmHome.getContentPane().revalidate();
		
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
			
			JOptionPane.showMessageDialog(AtualizarFornecedorView.this.frmHome, "   Fornecedor atualizado!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
			
		}
		else{
			
			JOptionPane.showMessageDialog(AtualizarFornecedorView.this.frmHome, "   Não foi possível atualizar!", "Alerta!", JOptionPane.ERROR_MESSAGE);

		}
		
	}

}
