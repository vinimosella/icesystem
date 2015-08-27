package vo;

public class SituacaoVO {
	
	public SituacaoVO(){
		
	}
	
	private Integer idSituacao;
	private String descricao;
	private Integer codigo;
	
	public Integer getIdSituacao() {
		return idSituacao;
	}
	public void setIdSituacao(Integer idSituacao) {
		this.idSituacao = idSituacao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
}
