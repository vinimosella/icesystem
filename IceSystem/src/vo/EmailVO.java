package vo;

public class EmailVO {

	public EmailVO(){
		
	}
	
	private Integer idEmail;
	private String email;
	private PessoaJuridicaVO pessoaJuridica;
	private FuncionarioVO funcionario;
	
	public Integer getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(Integer idEmail) {
		this.idEmail = idEmail;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public PessoaJuridicaVO getPessoaJuridica() {
		return pessoaJuridica;
	}
	public void setPessoaJuridica(PessoaJuridicaVO pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}
	public FuncionarioVO getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(FuncionarioVO funcionario) {
		this.funcionario = funcionario;
	}
	
}
