package vo;

import java.util.Date;

public class NotaFiscalVO {
	
	public NotaFiscalVO(){
		
	}
	
	private FuncionarioVO funcionario;
	private VendaVO venda;
	private String numeroNota;
	private Date dataEmissao;
	
	public FuncionarioVO getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(FuncionarioVO funcionario) {
		this.funcionario = funcionario;
	}
	public VendaVO getVenda() {
		return venda;
	}
	public void setVenda(VendaVO venda) {
		this.venda = venda;
	}
	public String getNumeroNota() {
		return numeroNota;
	}
	public void setNumeroNota(String numeroNota) {
		this.numeroNota = numeroNota;
	}
	public Date getDataEmissao() {
		return dataEmissao;
	}
	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

}
