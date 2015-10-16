package vo;

public class OrdemProducaoVO {
	
	private Long idOrdemProducao;
	private SituacaoVO situacao;
	private ProdutoVO produto;
	private Integer quantidade;
	
	public Long getIdOrdemProducao() {
		return idOrdemProducao;
	}
	public void setIdOrdemProducao(Long idOrdemProducao) {
		this.idOrdemProducao = idOrdemProducao;
	}
	public SituacaoVO getSituacao() {
		return situacao;
	}
	public void setSituacao(SituacaoVO situacao) {
		this.situacao = situacao;
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

}
