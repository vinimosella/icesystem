package daoservice;

import java.util.List;

import vo.OrdemProducaoVO;

public interface IOrdemDeProducaoDAO {

	public List<OrdemProducaoVO> consultarTodasOP();
	
	public List<OrdemProducaoVO> consultarOPSolicitado();
	
	public List<OrdemProducaoVO> consultarOPFinalizado();
	
	public List<OrdemProducaoVO> consultarOPCancelado();
	
	public boolean alterarOrdemProducao(OrdemProducaoVO op);
	
	public boolean cadastrarOP(OrdemProducaoVO ordemProducao);
	
}
