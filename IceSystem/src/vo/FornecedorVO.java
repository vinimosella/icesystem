package vo;

public class FornecedorVO extends PessoaJuridicaVO{
	
	public FornecedorVO(){
		
	}
	
	public StatusVO getStatus() {
		return status;
	}

	public void setStatus(StatusVO status) {
		this.status = status;
	}

	private StatusVO status;
	
}
