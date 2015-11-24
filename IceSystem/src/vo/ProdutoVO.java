package vo;

public class ProdutoVO {

	public ProdutoVO(){
		
		setQuantidadeEstoque(0);
	}
	
	private Integer idProduto;
	private Integer quantidadeEstoque;
	private String tipo;
	private String sabor;
	private StatusVO status;
	
	public Integer getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}
	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}
	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
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
