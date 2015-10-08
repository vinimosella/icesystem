package bo;

import java.util.List;

import teste.BancoEstatico;
import vo.SituacaoVO;

public class SituacaoBO {

	public List<SituacaoVO> consultarSituacoes() {

		return BancoEstatico.listasituacoes;
	}
	
}
