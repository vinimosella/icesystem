package vo;

import java.util.List;

public abstract class PessoaJuridicaVO {
	
	private Integer idPessoaJuridica;
	private EnderecoVO endereco;
	private String cnpj;
	private String razaoSocial;
	private List<EmailVO> listaEmails;
	private List<TelefoneVO> listaTelefones;
	
	public List<EmailVO> getListaEmails() {
		return listaEmails;
	}
	public void setListaEmails(List<EmailVO> listaEmails) {
		this.listaEmails = listaEmails;
	}
	public List<TelefoneVO> getListaTelefones() {
		return listaTelefones;
	}
	public void setListaTelefones(List<TelefoneVO> listaTelefones) {
		this.listaTelefones = listaTelefones;
	}
	public Integer getIdPessoaJuridica() {
		return idPessoaJuridica;
	}
	public void setIdPessoaJuridica(Integer idPessoaJuridica) {
		this.idPessoaJuridica = idPessoaJuridica;
	}
	public EnderecoVO getEndereco() {
		return endereco;
	}
	public void setEndereco(EnderecoVO endereco) {
		this.endereco = endereco;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

}
