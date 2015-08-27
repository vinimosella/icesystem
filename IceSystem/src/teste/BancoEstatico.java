package teste;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import vo.CargoVO;
import vo.CidadeVO;
import vo.EstadoVO;
import vo.FuncionarioVO;

public class BancoEstatico {
	
	static List<FuncionarioVO> listaFuncionarios;
	static FuncionarioVO funcionario;
	static CargoVO cargo;
	
	static List<EstadoVO> listaEstados;
	static EstadoVO estado;
	
	static List<CidadeVO> listaCidades;
	static CidadeVO cidade;

	public static List<FuncionarioVO> consultaFuncionarios(){
		
		listaFuncionarios = new ArrayList<FuncionarioVO>();
		
		funcionario = new FuncionarioVO();
		cargo = new CargoVO();
		
		char[] senha = {'a','d','m','i','n'};
		
		cargo.setIdCargo((byte) 1);
		funcionario.setCargo(cargo);
		funcionario.setLogin("admin");
		funcionario.setSenha(senha);
		listaFuncionarios.add(funcionario);
		
		funcionario = new FuncionarioVO();
		cargo = new CargoVO();
		
		char[] senha2 = {'u','s','e','r'};
		
		cargo.setIdCargo((byte) 2);
		funcionario.setCargo(cargo);
		funcionario.setLogin("user");
		funcionario.setSenha(senha2);
		listaFuncionarios.add(funcionario);
		
		funcionario = new FuncionarioVO();
		cargo = new CargoVO();
		
		char[] senha3 = {'1','2','3'};
		
		cargo.setIdCargo((byte) 3);
		funcionario.setCargo(cargo);
		funcionario.setLogin("123");
		funcionario.setSenha(senha3);
		listaFuncionarios.add(funcionario);
		
		return listaFuncionarios;
		
	}
	
	public static List<EstadoVO> consultaEstados(){
		
		listaEstados = new ArrayList<EstadoVO>();
		
		estado = new EstadoVO();
		estado.setIdEstado(1);
		estado.setNome("São Paulo");
		estado.setSigla("SP");
		listaEstados.add(estado);
		
		estado = new EstadoVO();
		estado.setIdEstado(2);
		estado.setNome("Rio de Janeiro");
		estado.setSigla("RJ");
		listaEstados.add(estado);
		
		estado = new EstadoVO();
		estado.setIdEstado(3);
		estado.setNome("Minas Gerais");
		estado.setSigla("MG");
		listaEstados.add(estado);
		
		return listaEstados;
	}
	
	public static List<CidadeVO> consultaCidadesPorEstado(Integer idEstado){
		
		listaCidades = new ArrayList<CidadeVO>();
		
		estado = new EstadoVO();
		estado.setIdEstado(1);
		
		cidade = new CidadeVO();
		cidade.setEstado(estado);
		cidade.setNome("Pederneiras");
		cidade.setIdCidade(1);
		listaCidades.add(cidade);
		
		estado = new EstadoVO();
		estado.setIdEstado(2);
		
		cidade = new CidadeVO();
		cidade.setEstado(estado);
		cidade.setNome("Rio de Janeiro");
		cidade.setIdCidade(2);
		listaCidades.add(cidade);
		
		estado = new EstadoVO();
		estado.setIdEstado(3);
		
		cidade = new CidadeVO();
		cidade.setEstado(estado);
		cidade.setNome("Belo Horizonte");
		cidade.setIdCidade(3);
		listaCidades.add(cidade);
		
		Iterator<CidadeVO> it = listaCidades.iterator();		
		List<CidadeVO> listaCidadesAux = new ArrayList<CidadeVO>();
		CidadeVO cidadeAux;
		
		while(it.hasNext()){
			
			cidadeAux = it.next();
			
			if(cidadeAux.getEstado().getIdEstado() == idEstado){
				listaCidadesAux.add(cidadeAux);
			}
		}
		
		return listaCidadesAux;
	}
}
