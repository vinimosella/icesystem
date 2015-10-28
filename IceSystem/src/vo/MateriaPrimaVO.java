package vo;

public class MateriaPrimaVO {
	
	public MateriaPrimaVO(){
		
	}
	
	private Integer idMateriaPrima;
	private FornecedorVO fornecedor;
	private Double quantidadeDisponivel;
	private String nome;
	private String sabor;
	private StatusVO status;
	
	public FornecedorVO getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(FornecedorVO fornecedor) {
		this.fornecedor = fornecedor;
	}
	public Integer getIdMateriaPrima() {
		return idMateriaPrima;
	}
	public void setIdMateriaPrima(Integer idMateriaPrima) {
		this.idMateriaPrima = idMateriaPrima;
	}
	public Double getQuantidadeDisponivel() {
		return quantidadeDisponivel;
	}
	public void setQuantidadeDisponivel(Double quantidadeDisponivel) {
		this.quantidadeDisponivel = quantidadeDisponivel;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSabor() {
		return sabor;
	}
	public void setSabor(String sabor) {
		this.sabor = sabor;
	}
	public StatusVO getStatus() {
		return status;
	}
	public void setStatus(StatusVO status) {
		this.status = status;
	}

}
