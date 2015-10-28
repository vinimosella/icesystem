package daoservice;

import vo.StatusVO;

public interface IStatusDAO {

	public StatusVO consultarPorDescricao(String descricao);
	
}
