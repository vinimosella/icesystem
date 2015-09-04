package bo;

import java.util.List;

import teste.BancoEstatico;
import vo.CargoVO;
import vo.CidadeVO;
import vo.EmailVO;
import vo.EstadoVO;
import vo.FuncionarioVO;
import vo.TelefoneVO;

public class FuncionarioBO {

	public List<EstadoVO> buscaEstados(){
		
		return BancoEstatico.consultaEstados();
	}
	
	public List<CidadeVO> buscaCidadesPorEstado(Integer idEstado){
		
		return BancoEstatico.consultaCidadesPorEstado(idEstado);
	}
	
	public List<CargoVO> buscaCargos(){
		
		return BancoEstatico.consultaCargo();
	}
	
	public List<FuncionarioVO> consultarFuncionarios(){
		
		return BancoEstatico.consultaFuncionarios();
	}
	
	public List<EmailVO> detalharEmail(Integer idFuncionario){
				
		return BancoEstatico.consultaEmails();
	}
	
	public List<TelefoneVO> detalharTelefone(Integer idFuncionario){
				
		return BancoEstatico.consultaTelefones();
	}
	
	public boolean excluirFuncionario(Integer idFuncionario){
		
		return true;
	}
	
}
