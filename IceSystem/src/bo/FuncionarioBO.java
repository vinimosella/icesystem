package bo;

import java.util.Iterator;
import java.util.List;

import util.Utilidades;
import vo.CargoVO;
import vo.CidadeVO;
import vo.EmailVO;
import vo.EstadoVO;
import vo.FuncionarioVO;
import vo.TelefoneVO;
import dao.CargoDAO;
import dao.CidadeDAO;
import dao.EstadoDAO;
import dao.FuncionarioDAO;

public class FuncionarioBO {
	
	private EstadoDAO estDao;
	private CidadeDAO cidDao;
	private FuncionarioDAO dao;
	private CargoDAO cargDao;
	
	{
		estDao = new EstadoDAO();
		cidDao = new CidadeDAO();
		dao = new FuncionarioDAO();
		cargDao = new CargoDAO();
	}
	
	public List<FuncionarioVO> filtrar(String nome, String cargo){
		
		return dao.consultarFuncionariosFiltro(nome, cargo);
	}
	
	public List<List<EmailVO>> gerenciaMudancasEmails(List<EmailVO>listaOriginal, List<EmailVO> listaMudada){
		
		return Utilidades.gerenciaMudancasEmails(listaOriginal, listaMudada);
	}
	
	public List<List<TelefoneVO>> gerenciaMudancasTelefones(List<TelefoneVO>listaOriginal, List<TelefoneVO> listaMudada){
		
		return Utilidades.gerenciaMudancasTelefones(listaOriginal, listaMudada);
	}
	
	public boolean cadastrarFuncionario(FuncionarioVO funcionario){
		
		funcionario.setStatus(Utilidades.STATUS_ATIVO);
		
		//se for administrador ou secretario gera usuario/senha
		if(funcionario.getCargo().getFuncao().equals(Utilidades.CARGO_ACESSO_ADMIN) || funcionario.getCargo().getFuncao().equals(Utilidades.CARGO_ACESSO_SECRETARIO)){
			
			String userAndPass = Utilidades.trocaEspacoPorPonto(funcionario.getNome().trim().toLowerCase());
			
			funcionario.setLogin(userAndPass);
			funcionario.setSenha(Utilidades.criptografarMd5(userAndPass));
		}
		
		return dao.cadastrarFuncionario(funcionario);
	}
	
	public boolean alterarSenhaFuncLogado(){
		
		Utilidades.funcionarioLogado.setSenha(Utilidades.criptografarMd5(Utilidades.funcionarioLogado.getSenha()));
		
		return dao.alterarSenhaFuncLogado(Utilidades.funcionarioLogado);
	}
	
	public boolean atualizarFuncionario(FuncionarioVO funcionario, List<List<EmailVO>> listaListaEmail, List<List<TelefoneVO>> listaListaTelefone){
		
		//se for administrador ou secretario gera usuario/senha
		if(funcionario.getCargo().getFuncao().equals(Utilidades.CARGO_ACESSO_ADMIN) || funcionario.getCargo().getFuncao().equals(Utilidades.CARGO_ACESSO_SECRETARIO)
				&& funcionario.getSenha() == null){
		
			String userAndPass = Utilidades.trocaEspacoPorPonto(funcionario.getNome().trim().toLowerCase());
			
			funcionario.setLogin(userAndPass);
			funcionario.setSenha(Utilidades.criptografarMd5(userAndPass));
		}
		
		return dao.alterarFuncionario(funcionario, listaListaEmail, listaListaTelefone);
	}
	
	public FuncionarioVO detalharFuncionario(FuncionarioVO funcionario){
		
		return dao.detalharFuncionario(funcionario);
	}
	
	// ##########################################################################################################

	public List<EstadoVO> buscaEstados(){
		
		return estDao.consultaEstados();
	}
	
	public List<CidadeVO> buscaCidadesPorEstado(EstadoVO estado){
		
		return cidDao.consultaCidadesPorEstado(estado);
	}
	
	public List<CargoVO> buscaCargos(){
		
		return cargDao.consultaCargos();
	}
	
	public List<FuncionarioVO> consultarFuncionarios(){
		
		return dao.consultarFuncionarios();
	}
	
	public boolean excluirFuncionario(FuncionarioVO funcionario){
		
		funcionario.setStatus(Utilidades.STATUS_INATIVO);
		
		return dao.excluirFuncionario(funcionario);
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
