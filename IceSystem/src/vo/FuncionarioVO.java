package vo;

public class FuncionarioVO{
	
	public FuncionarioVO(){
		
	}
	
	private Integer idFuncionario;
	private String nome;
	private String cpf;
	private String rg;
	private CargoVO cargo;
	private char[] senha;
	private String login;
	private EnderecoVO endereco;
	
	public Integer getIdFuncionario() {
		return idFuncionario;
	}
	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
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
	public CargoVO getCargo() {
		return cargo;
	}
	public void setCargo(CargoVO cargo) {
		this.cargo = cargo;
	}
	public char[] getSenha() {
		return senha;
	}
	public void setSenha(char[] senha) {
		this.senha = senha;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
}
