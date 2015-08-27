package vo;

public class ItemCompraVO {
	
	public ItemCompraVO(){
		
	}
	
	private CompraVO compra;
	private MateriaPrimaVO materiaPrima;
	private Double quantidade;
	private Double valor;
	
	public CompraVO getCompra() {
		return compra;
	}
	public void setCompra(CompraVO compra) {
		this.compra = compra;
	}
	public MateriaPrimaVO getMateriaPrima() {
		return materiaPrima;
	}
	public void setMateriaPrima(MateriaPrimaVO materiaPrima) {
		this.materiaPrima = materiaPrima;
	}
	public Double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}

}
