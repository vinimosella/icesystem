package teste;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vo.CargoVO;
import vo.CidadeVO;
import vo.ClienteVO;
import vo.CompraVO;
import vo.EmailVO;
import vo.EnderecoVO;
import vo.EstadoVO;
import vo.FornecedorVO;
import vo.FuncionarioVO;
import vo.SituacaoVO;
import vo.TelefoneVO;
import vo.VendaVO;

public class BancoEstatico {
	
	public static FuncionarioVO funcionario;
	public static List<FuncionarioVO> listaFuncionarios;

	public static CargoVO cargo;
	public static List<CargoVO> listaCargos;
	
	public static EnderecoVO endereco;
	public static List<EnderecoVO> listaEnderecos;
	
	public static EstadoVO estado;
	public static List<EstadoVO> listaEstados;
	
	public static CidadeVO cidade;
	public static List<CidadeVO> listaCidades;
	
	public static EmailVO email;
	public static List<EmailVO> listaEmails;

	public static TelefoneVO telefone;
	public static List<TelefoneVO> listaTelefones;
	
	public static FornecedorVO fornecedor;
	public static List<FornecedorVO> listaFornecedores;
	
	public static ClienteVO cliente;
	public static List<ClienteVO> listaClientes;
	
	public static SituacaoVO situacao;
	public static List<SituacaoVO> listasituacoes;
	
	public static CompraVO compra;
	public static List<CompraVO> listaCompras;
	
	public static VendaVO venda;
	public static List<VendaVO> listaVendas;

	public static void carregaBanco(){
		
		//CARREGA ESTADOS
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
		
		//CARREGA CIDADES
		listaCidades = new ArrayList<CidadeVO>();
		
		cidade = new CidadeVO();
		cidade.setEstado(listaEstados.get(0));
		cidade.setNome("Pederneiras");
		cidade.setIdCidade(1);
		listaCidades.add(cidade);
		
		estado = new EstadoVO();
		estado.setIdEstado(2);
		
		cidade = new CidadeVO();
		cidade.setEstado(listaEstados.get(1));
		cidade.setNome("Rio de Janeiro");
		cidade.setIdCidade(2);
		listaCidades.add(cidade);
		
		estado = new EstadoVO();
		estado.setIdEstado(3);
		
		cidade = new CidadeVO();
		cidade.setEstado(listaEstados.get(2));
		cidade.setNome("Belo Horizonte");
		cidade.setIdCidade(3);
		listaCidades.add(cidade);
		
		//CARREGA ENDERECOS
		
		listaEnderecos = new ArrayList<EnderecoVO>();
		
		endereco = new EnderecoVO();
		endereco.setCidade(listaCidades.get(0));
		endereco.setCep("17.280-000");
		endereco.setBairro("Centro");
		endereco.setLogradouro("Rua Rio Branco");
		endereco.setNumero(268);
		endereco.setComplemento("Oeste");
		
		listaEnderecos.add(endereco);
		
		endereco = new EnderecoVO();
		endereco.setCidade(listaCidades.get(1));
		endereco.setCep("66.288-888");
		endereco.setBairro("Centro");
		endereco.setLogradouro("Rua Bombinhas");
		endereco.setNumero(666);
		endereco.setComplemento("Boooom!");
		
		listaEnderecos.add(endereco);
		
		endereco = new EnderecoVO();
		endereco.setCidade(listaCidades.get(2));
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
		
		email = new EmailVO();
		email.setEmail("porra@meu.com.br");
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
		funcionario.setIdFuncionario(1);
		funcionario.setNome("Vinicius");
		funcionario.setCpf("422.811.468-71");
		funcionario.setRg("38.345.776-02");		
		char[] senha = {'a','d','m','i','n'};
		funcionario.setCargo(listaCargos.get(0));
		funcionario.setLogin("admin");
		funcionario.setSenha(senha);
		
		funcionario.setListaEmails(listaEmails);
		funcionario.setListaTelefones(listaTelefones);
		funcionario.setEndereco(listaEnderecos.get(0));
		
		listaFuncionarios.add(funcionario);
		
		
		funcionario = new FuncionarioVO();
		funcionario.setIdFuncionario(2);
		funcionario.setNome("Thiago");
		funcionario.setCpf("566.671.448-65");
		funcionario.setRg("35.455.754-72");		
		char[] senha2 = {'u','s','e','r'};
		funcionario.setCargo(listaCargos.get(1));
		funcionario.setLogin("user");
		funcionario.setSenha(senha2);
		
		funcionario.setListaEmails(listaEmails);
		funcionario.setListaTelefones(listaTelefones);
		funcionario.setEndereco(listaEnderecos.get(1));
		
		listaFuncionarios.add(funcionario);
		
		funcionario = new FuncionarioVO();
		funcionario.setIdFuncionario(3);
		funcionario.setNome("Leandro");
		funcionario.setCpf("567.432.123-88");
		funcionario.setRg("88.777.345-55");
		char[] senha3 = {'1','2','3'};
		funcionario.setCargo(listaCargos.get(2));
		funcionario.setLogin("123");
		funcionario.setSenha(senha3);
		
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
		
		//CARREGA SITUAÇÃO
		listasituacoes = new ArrayList<SituacaoVO>();
		
		situacao = new SituacaoVO();
		situacao.setIdSituacao(1);
		situacao.setCodigo(1);
		situacao.setDescricao("Encaminhada");
		listasituacoes.add(situacao);
		
		situacao = new SituacaoVO();
		situacao.setIdSituacao(2);
		situacao.setCodigo(2);
		situacao.setDescricao("Entregue");
		listasituacoes.add(situacao);
		
		situacao = new SituacaoVO();
		situacao.setIdSituacao(3);
		situacao.setCodigo(3);
		situacao.setDescricao("Solicitada");
		listasituacoes.add(situacao);
		
		situacao = new SituacaoVO();
		situacao.setIdSituacao(4);
		situacao.setCodigo(4);
		situacao.setDescricao("Recebida");
		listasituacoes.add(situacao);
		
		//CARREGA COMPRAS
		listaCompras = new ArrayList<CompraVO>();
		
		compra = new CompraVO();
		compra.setIdCompra(1l);
		compra.setDataCompra(new Date());
		compra.setFuncionario(listaFuncionarios.get(0));
		compra.setSituacao(listasituacoes.get(0));
		listaCompras.add(compra);
		
		compra = new CompraVO();
		compra.setIdCompra(2l);
		compra.setDataCompra(new Date());
		compra.setFuncionario(listaFuncionarios.get(1));
		compra.setSituacao(listasituacoes.get(1));
		listaCompras.add(compra);
		
		//CARREGA VENDAS
		listaVendas = new ArrayList<VendaVO>();
		
		venda = new VendaVO();
		venda.setIdVenda(1l);
		venda.setDataVenda(new Date());
		venda.setSituacao(listasituacoes.get(2));
		venda.setCliente(listaClientes.get(0));
		listaVendas.add(venda);
		
		venda = new VendaVO();
		venda.setIdVenda(2l);
		venda.setDataVenda(new Date());
		venda.setSituacao(listasituacoes.get(3));
		venda.setCliente(listaClientes.get(1));
		listaVendas.add(venda);
		
	}
	
}
