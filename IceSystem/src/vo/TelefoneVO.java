package vo;

public class TelefoneVO {
	
	public TelefoneVO(){
		
	}
	
	private Integer idTelefone;
	private String ddd;
	private String numero;
	private PessoaJuridicaVO pessoaJuridica;
	private FuncionarioVO funcionario;
	
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
	public Integer getIdTelefone() {
		return idTelefone;
	}
	public void setIdTelefone(Integer idTelefone) {
		this.idTelefone = idTelefone;
	}
	public String getDdd() {
		return ddd;
	}
	public void setDdd(String ddd) {
		this.ddd = ddd;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}	

}
