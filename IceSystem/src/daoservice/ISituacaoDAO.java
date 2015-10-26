package daoservice;

import java.util.List;

import vo.SituacaoVO;

public interface ISituacaoDAO {

	
	public List<SituacaoVO> consultarSituacoes();
	
	public SituacaoVO consultarSituacaoPorDesc(String descricao);
	
}
