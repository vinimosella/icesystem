package vo;

public class IngredienteReceitaProdutoVO {
	
	public IngredienteReceitaProdutoVO(){
		
	}

	private MateriaPrimaVO materiaPrima;
	private ProdutoVO produto;
	private Double quantidadeMateria;
	
	public MateriaPrimaVO getMateriaPrima() {
		return materiaPrima;
	}
	public void setMateriaPrima(MateriaPrimaVO materiaPrima) {
		this.materiaPrima = materiaPrima;
	}
	public ProdutoVO getProduto() {
		return produto;
	}
	public void setProduto(ProdutoVO produto) {
		this.produto = produto;
	}
	public Double getQuantidadeMateria() {
		return quantidadeMateria;
	}
	public void setQuantidadeMateria(Double quantidadeMateria) {
		this.quantidadeMateria = quantidadeMateria;
	}
	
}
