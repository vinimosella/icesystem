package vo;

public class ItemVendaVO {

	public ItemVendaVO(){
		
	}
	
	private VendaVO venda;
	private ProdutoVO produto;
	private Integer quantidade;
	private Double valor;
	
	public VendaVO getVenda() {
		return venda;
	}
	public void setVenda(VendaVO venda) {
		this.venda = venda;
	}
	public ProdutoVO getProduto() {
		return produto;
	}
	public void setProduto(ProdutoVO produto) {
		this.produto = produto;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	
}
