package vo;

public abstract class PessoaFisicaVO {
	
	private Integer idPessoaFisica;
	private TelefoneVO telefone;
	private EmailVO email;
	private EnderecoVO endereco;
	private String nome;
	private String cpf;
	private String rg;
	
	public Integer getIdPessoaFisica() {
		return idPessoaFisica;
	}
	public void setIdPessoaFisica(Integer idPessoaFisica) {
		this.idPessoaFisica = idPessoaFisica;
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
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}

}
