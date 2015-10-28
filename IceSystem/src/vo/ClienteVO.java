package vo;

public class ClienteVO extends PessoaJuridicaVO{

	public ClienteVO(){
		
	}
	
	public StatusVO getStatus() {
		return status;
	}

	public void setStatus(StatusVO status) {
		this.status = status;
	}

	private StatusVO status;
	
}
