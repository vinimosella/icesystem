package bo;

import java.util.Iterator;
import java.util.List;

import teste.BancoEstatico;
import vo.CargoVO;
import vo.CidadeVO;
import vo.EmailVO;
import vo.EstadoVO;
import vo.FuncionarioVO;
import vo.TelefoneVO;
import dao.FuncionarioDAO;

public class FuncionarioBO {
	
	public boolean cadastrarFuncionario(FuncionarioVO funcionario){
		
		return FuncionarioDAO.cadastrarFuncionario(funcionario);
	}
	
	public boolean atualizarFuncionario(FuncionarioVO funcionario){
		
		return FuncionarioDAO.atualizarFuncionario(funcionario);
	}
	
	public FuncionarioVO detalharFuncionario(FuncionarioVO funcionario){
		
		funcionario.setListaEmails(BancoEstatico.consultaEmails());
		funcionario.setListaTelefones(BancoEstatico.consultaTelefones());
				
		return funcionario;
		//return FuncionarioDAO.detalharFuncionario(funcionario);
	}
	
	// ##########################################################################################################

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
	
	public boolean excluirFuncionario(Integer idFuncionario){
		
		return true;
	}
	
	public boolean isTelefoneExistenteLista(String txtTelefone, List<TelefoneVO> listaTelefones){
		
		Iterator<TelefoneVO> it = listaTelefones.iterator();
		
		while(it.hasNext()){
			
			TelefoneVO telefone = it.next();
			
			if(txtTelefone.equals(telefone.getDdd()+telefone.getNumero())){
				
				return true;				
			}
			
		}
		
		return false;
	}
	
	public boolean isEmailExistenteLista(String txtEmail, List<EmailVO> listaEmails){
				
		Iterator<EmailVO> it = listaEmails.iterator();
		
		while(it.hasNext()){
			
			EmailVO email = it.next();
						
			if(txtEmail.equals(email.getEmail())){
				
				return true;				
			}
			
		}
		
		return false;
	}
	
	public CidadeVO buscaCidadePorNomeNaLista(String nomeCidade, List<CidadeVO> listaCidades){
		
		Iterator<CidadeVO> it = listaCidades.iterator();
		
		CidadeVO cidade;
		
		while(it.hasNext()){
			
			cidade = it.next();
			
			if(cidade.getNome().equals(nomeCidade)){
				
				return cidade;
			}
			
		}
		
		return null;
	}
	
	public EstadoVO buscaEstadoPorNomeNaLista(String nomeEstado, List<EstadoVO> listaEstados){
		
		Iterator<EstadoVO> it = listaEstados.iterator();
		
		EstadoVO estado;
		
		while(it.hasNext()){
			
			estado = it.next();
			
			if(estado.getNome().equals(nomeEstado)){
				
				return estado;
			}
			
		}
		
		return null;
	}
	
	public CargoVO buscaCargoPorFuncaoNaLista(String funcaoCargo, List<CargoVO> listaCargos){
		
		Iterator<CargoVO> it = listaCargos.iterator();
		
		CargoVO cargo;
		
		while(it.hasNext()){
			
			cargo = it.next();
			
			if(cargo.getFuncao().equals(funcaoCargo)){
				
				return cargo;
			}
			
		}
		
		return null;
	}
	
}
