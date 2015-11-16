package bo;

import java.util.List;

import vo.SituacaoVO;
import dao.SituacaoDAO;

public class SituacaoBO {
	
	private SituacaoDAO dao;
	
	{
		dao = new SituacaoDAO();
	}

	public List<SituacaoVO> consultarSituacoes() {

		return dao.consultarSituacoes();
	}
	
}
