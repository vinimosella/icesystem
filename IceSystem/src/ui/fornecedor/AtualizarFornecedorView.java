package ui.fornecedor;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JOptionPane;

import ui.pessoaJuridica.AtualizarPessoaJuridicaView;
import util.Utilidades;
import vo.EmailVO;
import vo.FornecedorVO;
import vo.PessoaJuridicaVO;
import vo.TelefoneVO;
import bo.FornecedorBO;

public class AtualizarFornecedorView extends AtualizarPessoaJuridicaView{

	private static final long serialVersionUID = 1L;
	
	private FornecedorBO bo;
	private FornecedorVO fornecedor;

	public AtualizarFornecedorView(PessoaJuridicaVO pj) {
		super(pj, "Atualizar Fornecedor");
	}

	@Override
	public void btnCancelar() {
		
		Utilidades.frmHome.getContentPane().removeAll();
		ConsultarFornecedorView consulta = new ConsultarFornecedorView();
		Utilidades.frmHome.getContentPane().add(consulta, BorderLayout.CENTER);
		Utilidades.frmHome.getContentPane().revalidate();
		
	}

	@Override
	public void btnAtualizar(PessoaJuridicaVO pj, List<TelefoneVO> listaTelAlt, List<EmailVO> listaEmailAlt) {
		
		bo = new FornecedorBO();
		
		fornecedor = new FornecedorVO();
		
		fornecedor.setStatus(Utilidades.STATUS_ATIVO);
		fornecedor.setIdPessoaJuridica(pj.getIdPessoaJuridica());
		fornecedor.setRazaoSocial(pj.getRazaoSocial());
		fornecedor.setCnpj(pj.getCnpj());
		fornecedor.setListaEmails(pj.getListaEmails());
		fornecedor.setListaTelefones(pj.getListaTelefones());
		fornecedor.setEndereco(pj.getEndereco());
		
		List<List<EmailVO>> listaListaEmail = bo.gerenciaMudancasEmails(fornecedor.getListaEmails(), listaEmailAlt);
		List<List<TelefoneVO>> listaListaTelefone = bo.gerenciaMudancasTelefones(fornecedor.getListaTelefones(), listaTelAlt);
		
		if(bo.atualizarFornecedor(fornecedor, listaListaEmail, listaListaTelefone)){
			
			JOptionPane.showMessageDialog(Utilidades.frmHome, "   Fornecedor atualizado!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
			Utilidades.frmHome.getContentPane().removeAll();
			ConsultarFornecedorView consulta = new ConsultarFornecedorView();
			Utilidades.frmHome.getContentPane().add(consulta, BorderLayout.CENTER);
			Utilidades.frmHome.getContentPane().revalidate();	
		}
		else{
			
			JOptionPane.showMessageDialog(Utilidades.frmHome, "   Não foi possível atualizar!", "Alerta!", JOptionPane.ERROR_MESSAGE);

		}
		
	}

}
