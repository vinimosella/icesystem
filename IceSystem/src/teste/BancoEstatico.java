package teste;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import vo.CargoVO;
import vo.CidadeVO;
import vo.EmailVO;
import vo.EnderecoVO;
import vo.EstadoVO;
import vo.FuncionarioVO;
import vo.TelefoneVO;

public class BancoEstatico {
	
	static List<FuncionarioVO> listaFuncionarios;
	static FuncionarioVO funcionario;
	static CargoVO cargo;
	static EnderecoVO endereco;
	
	static List<EstadoVO> listaEstados;
	static EstadoVO estado;
	
	static List<CidadeVO> listaCidades;
	static CidadeVO cidade;
	
	static List<CargoVO> listaCargos;
	
	static List<EmailVO> listaEmails;
	static EmailVO email;
	
	static List<TelefoneVO> listaTelefones;
	static TelefoneVO telefone;

	public static List<FuncionarioVO> consultaFuncionarios(){
		
		listaFuncionarios = new ArrayList<FuncionarioVO>();
		
		funcionario = new FuncionarioVO();
		funcionario.setIdFuncionario(1);
		funcionario.setNome("Vinicius");
		funcionario.setCpf("422.811.468-71");
		funcionario.setRg("38.345.776-02");
		cargo = new CargoVO();
		
		char[] senha = {'a','d','m','i','n'};
		
		cargo.setIdCargo((byte) 1);
		cargo.setFuncao("ADM");
		funcionario.setCargo(cargo);
		funcionario.setLogin("admin");
		funcionario.setSenha(senha);
		
		endereco = new EnderecoVO();
		cidade = new CidadeVO();
		cidade.setNome("Pederneiras");
		estado= new EstadoVO();
		estado.setNome("São Paulo");
		cidade.setEstado(estado);
		endereco.setCidade(cidade);
		endereco.setCep("17.280-000");
		endereco.setBairro("Centro");
		endereco.setLogradouro("Rua Rio Branco");
		endereco.setNumero(268);
		endereco.setComplemento("Oeste");
		funcionario.setEndereco(endereco);
		
		listaFuncionarios.add(funcionario);
		
		
		funcionario = new FuncionarioVO();
		funcionario.setIdFuncionario(2);
		funcionario.setNome("Thiago");
		funcionario.setCpf("566.671.448-65");
		funcionario.setRg("35.455.754-72");
		cargo = new CargoVO();
		
		char[] senha2 = {'u','s','e','r'};
		
		cargo.setIdCargo((byte) 2);
		cargo.setFuncao("Gerente");
		funcionario.setCargo(cargo);
		funcionario.setLogin("user");
		funcionario.setSenha(senha2);
		
		endereco = new EnderecoVO();
		cidade = new CidadeVO();
		cidade.setNome("Onde tem bomba");
		estado= new EstadoVO();
		estado.setNome("Islâmico");
		cidade.setEstado(estado);
		endereco.setCidade(cidade);
		endereco.setCep("66.288-888");
		endereco.setBairro("Centro");
		endereco.setLogradouro("Rua Bombinhas");
		endereco.setNumero(666);
		endereco.setComplemento("Boooom!");
		funcionario.setEndereco(endereco);
		
		listaFuncionarios.add(funcionario);
		
		funcionario = new FuncionarioVO();
		funcionario.setIdFuncionario(3);
		funcionario.setNome("Leandro");
		funcionario.setCpf("567.432.123-88");
		funcionario.setRg("88.777.345-55");
		cargo = new CargoVO();
		
		char[] senha3 = {'1','2','3'};
		
		cargo.setIdCargo((byte) 3);
		cargo.setFuncao("Cozinheiro");
		funcionario.setCargo(cargo);
		funcionario.setLogin("123");
		funcionario.setSenha(senha3);
		
		endereco = new EnderecoVO();
		cidade = new CidadeVO();
		cidade.setNome("Bariri");
		estado= new EstadoVO();
		estado.setNome("São Paulo");
		cidade.setEstado(estado);
		endereco.setCidade(cidade);
		endereco.setCep("24.111-111");
		endereco.setBairro("Bairro dos corinthianos");
		endereco.setLogradouro("Rua Dos Viados");
		endereco.setNumero(111);
		endereco.setComplemento("Bixa");
		funcionario.setEndereco(endereco);
		
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
	
	public static List<CargoVO> consultaCargo(){
		
		listaCargos = new ArrayList<CargoVO>();
		
		cargo = new CargoVO();
		cargo.setIdCargo((byte) 1);
		cargo.setFuncao("ADM");
		listaCargos.add(cargo);
		
		cargo = new CargoVO();
		cargo.setIdCargo((byte) 2);
		cargo.setFuncao("Gerente");
		listaCargos.add(cargo);
		
		cargo = new CargoVO();
		cargo.setIdCargo((byte) 3);
		cargo.setFuncao("Cozinheiro");
		listaCargos.add(cargo);	
		
		return listaCargos;
	}
	
	public static List<EmailVO> consultaEmails(){
		
		listaEmails = new ArrayList<EmailVO>();
		
		email = new EmailVO();
		email.setEmail("teste@lalala.com.br");
		listaEmails.add(email);
		
		email = new EmailVO();
		email.setEmail("fausto@silva.com.br");
		listaEmails.add(email);
		
		email = new EmailVO();
		email.setEmail("oloco@bixo.com.br");
		listaEmails.add(email);
		
		email = new EmailVO();
		email.setEmail("porra@meu.com.br");
		listaEmails.add(email);
		
		return listaEmails;
	}
	
	public static List<TelefoneVO> consultaTelefones(){
		
		listaTelefones = new ArrayList<TelefoneVO>();
		
		telefone = new TelefoneVO();
		telefone.setDdd("14");
		telefone.setNumero("3284-4758");
		listaTelefones.add(telefone);
		
		telefone = new TelefoneVO();
		telefone.setDdd("67");
		telefone.setNumero("67866-4768");
		listaTelefones.add(telefone);
		
		telefone = new TelefoneVO();
		telefone.setDdd("55");
		telefone.setNumero("1111-2222");
		listaTelefones.add(telefone);
		
		telefone = new TelefoneVO();
		telefone.setDdd("99");
		telefone.setNumero("9090-8080");
		listaTelefones.add(telefone);
		
		return listaTelefones;
	}
	
}
