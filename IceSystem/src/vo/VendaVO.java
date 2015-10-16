package vo;

import java.util.Date;

public class VendaVO {
	
	public VendaVO(){
		
	}
	
	private Long idVenda;
	private SituacaoVO situacao;
	private ClienteVO cliente;
	private Date dataVenda;
	private FuncionarioVO funcionario;
	
	public Long getIdVenda() {
		return idVenda;
	}
	public void setIdVenda(Long idVenda) {
		this.idVenda = idVenda;
	}
	public SituacaoVO getSituacao() {
		return situacao;
	}
	public void setSituacao(SituacaoVO situacao) {
		this.situacao = situacao;
	}
	public ClienteVO getCliente() {
		return cliente;
	}
	public void setCliente(ClienteVO cliente) {
		this.cliente = cliente;
	}
	public Date getDataVenda() {
		return dataVenda;
	}
	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}
	public FuncionarioVO getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(FuncionarioVO funcionario) {
		this.funcionario = funcionario;
	}

}
