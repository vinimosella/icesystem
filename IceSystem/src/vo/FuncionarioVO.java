package vo;

public class FuncionarioVO extends PessoaFisicaVO{
	
	public FuncionarioVO(){
		
	}

	private CargoVO cargo;
	private char[] senha;
	private String login;
	
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
