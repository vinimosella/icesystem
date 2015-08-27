package vo;

import java.util.Date;

public class CompraVO {
	
	public CompraVO(){
		
	}
	
	private Long idCompra;
	private FuncionarioVO funcionario;
	private SituacaoVO situacao;
	private Date dataCompra;
	
	public Long getIdCompra() {
		return idCompra;
	}
	public void setIdCompra(Long idCompra) {
		this.idCompra = idCompra;
	}
	public FuncionarioVO getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(FuncionarioVO funcionario) {
		this.funcionario = funcionario;
	}
	public SituacaoVO getSituacao() {
		return situacao;
	}
	public void setSituacao(SituacaoVO situacao) {
		this.situacao = situacao;
	}
	public Date getDataCompra() {
		return dataCompra;
	}
	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}
	
}
