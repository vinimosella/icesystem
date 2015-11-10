package teste;

import java.util.ArrayList;
import java.util.List;

import vo.CargoVO;
import vo.ClienteVO;
import vo.EmailVO;
import vo.EnderecoVO;
import vo.FornecedorVO;
import vo.FuncionarioVO;
import vo.TelefoneVO;

public class BancoEstatico {
	
	public static FuncionarioVO funcionario;
	public static List<FuncionarioVO> listaFuncionarios;

	public static CargoVO cargo;
	public static List<CargoVO> listaCargos;
	
	public static EnderecoVO endereco;
	public static List<EnderecoVO> listaEnderecos;
	
	public static EmailVO email;
	public static List<EmailVO> listaEmails;

	public static TelefoneVO telefone;
	public static List<TelefoneVO> listaTelefones;
	
	public static FornecedorVO fornecedor;
	public static List<FornecedorVO> listaFornecedores;
	
	public static ClienteVO cliente;
	public static List<ClienteVO> listaClientes;

	public static void carregaBanco(){
		
		//CARREGA ENDERECOS
		
		listaEnderecos = new ArrayList<EnderecoVO>();
		
		endereco = new EnderecoVO();
		endereco.setCep("17.280-000");
		endereco.setBairro("Centro");
		endereco.setLogradouro("Rua Rio Branco");
		endereco.setNumero(268);
		endereco.setComplemento("Oeste");
		
		listaEnderecos.add(endereco);
		
		endereco = new EnderecoVO();
		endereco.setCep("66.288-888");
		endereco.setBairro("Centro");
		endereco.setLogradouro("Rua Bombinhas");
		endereco.setNumero(666);
		endereco.setComplemento("Boooom!");
		
		listaEnderecos.add(endereco);
		
		endereco = new EnderecoVO();
		endereco.setCep("24.111-111");
		endereco.setBairro("Bairro dos corinthianos");
		endereco.setLogradouro("Rua Dos Viados");
		endereco.setNumero(111);
		endereco.setComplemento("Bixa");
		
		listaEnderecos.add(endereco);
		
		//CARREGA EMAILS
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
		
		//CARREGA TELEFONES
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
		
		//CARREGA CARGOS
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
		
		//CARREGA FUNCIONARIOS
		listaFuncionarios = new ArrayList<FuncionarioVO>();
		
		funcionario = new FuncionarioVO();
		funcionario.setIdFuncionario(8);
		funcionario.setNome("Vinicius");
		funcionario.setCpf("422.811.468-71");
		funcionario.setRg("38.345.776-02");		
		funcionario.setCargo(listaCargos.get(0));
		funcionario.setLogin("admin");
		funcionario.setSenha("admin");
		
		funcionario.setListaEmails(listaEmails);
		funcionario.setListaTelefones(listaTelefones);
		funcionario.setEndereco(listaEnderecos.get(0));
		
		listaFuncionarios.add(funcionario);
		
		
		funcionario = new FuncionarioVO();
		funcionario.setIdFuncionario(2);
		funcionario.setNome("Thiago");
		funcionario.setCpf("566.671.448-65");
		funcionario.setRg("35.455.754-72");		
		funcionario.setCargo(listaCargos.get(1));
		funcionario.setLogin("user");
		funcionario.setSenha("user");
		
		funcionario.setListaEmails(listaEmails);
		funcionario.setListaTelefones(listaTelefones);
		funcionario.setEndereco(listaEnderecos.get(1));
		
		listaFuncionarios.add(funcionario);
		
		funcionario = new FuncionarioVO();
		funcionario.setIdFuncionario(3);
		funcionario.setNome("Leandro");
		funcionario.setCpf("567.432.123-88");
		funcionario.setRg("88.777.345-55");
		funcionario.setCargo(listaCargos.get(2));
		funcionario.setLogin("123");
		funcionario.setSenha("123");
		
		funcionario.setListaEmails(listaEmails);
		funcionario.setListaTelefones(listaTelefones);
		funcionario.setEndereco(listaEnderecos.get(2));
		
		listaFuncionarios.add(funcionario);
		
		//CARREGA FORNECEDORES
		listaFornecedores = new ArrayList<FornecedorVO>();
		
		fornecedor = new FornecedorVO();
		fornecedor.setRazaoSocial("Fabrica de pó");
		fornecedor.setCnpj("566.5667.708-7776");
		fornecedor.setListaEmails(listaEmails);
		fornecedor.setListaTelefones(listaTelefones);
		fornecedor.setEndereco(listaEnderecos.get(0));
		
		listaFornecedores.add(fornecedor);
		
		fornecedor = new FornecedorVO();
		fornecedor.setRazaoSocial("Pó de Fabrica");
		fornecedor.setCnpj("1234321567");
		fornecedor.setListaEmails(listaEmails);
		fornecedor.setListaTelefones(listaTelefones);
		fornecedor.setEndereco(listaEnderecos.get(1));
		
		listaFornecedores.add(fornecedor);
		
		//CARREGA CLIENTES
		listaClientes = new ArrayList<ClienteVO>();
		
		cliente = new ClienteVO();
		cliente.setRazaoSocial("Sorveteria Ki-Gelado");
		cliente.setCnpj("566.5667.708-7776");
		cliente.setListaEmails(listaEmails);
		cliente.setListaTelefones(listaTelefones);
		cliente.setEndereco(listaEnderecos.get(0));
		
		listaClientes.add(cliente);
		
		cliente = new ClienteVO();
		cliente.setRazaoSocial("Sorveteria IceDream");
		cliente.setCnpj("1234321567");
		cliente.setListaEmails(listaEmails);
		cliente.setListaTelefones(listaTelefones);
		cliente.setEndereco(listaEnderecos.get(1));
		
		listaClientes.add(cliente);
	}
	
}
