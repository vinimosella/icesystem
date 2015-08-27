package vo;

public abstract class PessoaJuridicaVO {
	
	private Integer idPessoaJuridica;
	private TelefoneVO telefone;
	private EmailVO email;
	private EnderecoVO endereco;
	private String cnpj;
	private String razaoSocial;
	
	public Integer getIdPessoaJuridica() {
		return idPessoaJuridica;
	}
	public void setIdPessoaJuridica(Integer idPessoaJuridica) {
		this.idPessoaJuridica = idPessoaJuridica;
	}
	public TelefoneVO getTelefone() {
		return telefone;
	}
	public void setTelefone(TelefoneVO telefone) {
		this.telefone = telefone;
	}
	public EmailVO getEmail() {
		return email;
	}
	public void setEmail(EmailVO email) {
		this.email = email;
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
