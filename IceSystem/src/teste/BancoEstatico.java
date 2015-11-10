package teste;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vo.CargoVO;
import vo.ClienteVO;
import vo.CompraVO;
import vo.EmailVO;
import vo.EnderecoVO;
import vo.FornecedorVO;
import vo.FuncionarioVO;
import vo.IngredienteReceitaProdutoVO;
import vo.ItemCompraVO;
import vo.ItemVendaVO;
import vo.MateriaPrimaVO;
import vo.OrdemProducaoVO;
import vo.ProdutoVO;
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
	
	public static ProdutoVO produto;
	public static List<ProdutoVO> listaProdutos;
	
	public static MateriaPrimaVO materiasPrima;
	public static List<MateriaPrimaVO> listaMateriasPrimas;
	
	public static IngredienteReceitaProdutoVO receita;
	public static List<IngredienteReceitaProdutoVO> listaReceitas;
	
	public static ItemCompraVO itemCompra;
	public static List<ItemCompraVO> listaItensCompra;
	
	public static ItemVendaVO itemVenda;
	public static List<ItemVendaVO> listaItensVenda;
	
	public static OrdemProducaoVO op;
	public static List<OrdemProducaoVO> listaOrdensProducao;
	public static List<OrdemProducaoVO> listaOrdensProducaoSolicitada;
	public static List<OrdemProducaoVO> listaOrdensProducaoCancelada;
	public static List<OrdemProducaoVO> listaOrdensProducaoFinzalizada;

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
		situacao.setIdSituacao(3);
		situacao.setDescricao("Solicitado");
		listasituacoes.add(situacao);
		
		situacao = new SituacaoVO();
		situacao.setIdSituacao(2);
		situacao.setDescricao("Cancelado");
		listasituacoes.add(situacao);
		
		situacao = new SituacaoVO();
		situacao.setIdSituacao(1);
		situacao.setDescricao("Finalizado");
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
		venda.setSituacao(listasituacoes.get(2));
		venda.setCliente(listaClientes.get(1));
		listaVendas.add(venda);
		
		//CARREGA PRODUTOS
		listaProdutos = new ArrayList<ProdutoVO>();
		
		produto = new ProdutoVO();
		produto.setIdProduto(1);
		produto.setNome("Sorvete de Massa");
		produto.setSabor("Morango");
		produto.setQuantidadeEstoque(600);
		listaProdutos.add(produto);
		
		produto = new ProdutoVO();
		produto.setIdProduto(2);
		produto.setNome("Sorvete de Palito");
		produto.setSabor("Abacaxi");
		produto.setQuantidadeEstoque(88);
		listaProdutos.add(produto);
		
		produto = new ProdutoVO();
		produto.setIdProduto(3);
		produto.setNome("Sorvete de Massa");
		produto.setSabor("Chocolate");
		produto.setQuantidadeEstoque(22);
		listaProdutos.add(produto);
		
		produto = new ProdutoVO();
		produto.setIdProduto(4);
		produto.setNome("Sorvete de Palito");
		produto.setSabor("Limão");
		produto.setQuantidadeEstoque(666);
		listaProdutos.add(produto);
		
		//CARREGA MATERIAS PRIMAS
		listaMateriasPrimas = new ArrayList<MateriaPrimaVO>();
		
		materiasPrima = new MateriaPrimaVO();
		materiasPrima.setIdMateriaPrima(1);
		materiasPrima.setNome("Pó");
		materiasPrima.setSabor("Morango");
		materiasPrima.setQuantidadeDisponivel(44.5);
		materiasPrima.setFornecedor(listaFornecedores.get(0));
		listaMateriasPrimas.add(materiasPrima);
		
		materiasPrima = new MateriaPrimaVO();
		materiasPrima.setIdMateriaPrima(2);
		materiasPrima.setNome("Leite");
		materiasPrima.setQuantidadeDisponivel(100.7);
		materiasPrima.setFornecedor(listaFornecedores.get(1));
		listaMateriasPrimas.add(materiasPrima);
		
		materiasPrima = new MateriaPrimaVO();
		materiasPrima.setIdMateriaPrima(3);
		materiasPrima.setNome("Fruta");
		materiasPrima.setSabor("Banana");
		materiasPrima.setQuantidadeDisponivel(100.5);
		materiasPrima.setFornecedor(listaFornecedores.get(1));
		listaMateriasPrimas.add(materiasPrima);
		
		materiasPrima = new MateriaPrimaVO();
		materiasPrima.setIdMateriaPrima(4);
		materiasPrima.setNome("Açucar");
		materiasPrima.setQuantidadeDisponivel(12.5);
		materiasPrima.setFornecedor(listaFornecedores.get(0));
		listaMateriasPrimas.add(materiasPrima);
		
		//CARREGA RECEITAS
		listaReceitas = new ArrayList<IngredienteReceitaProdutoVO>();
		
		receita = new IngredienteReceitaProdutoVO();
		receita.setProduto(listaProdutos.get(0));
		receita.setMateriaPrima(listaMateriasPrimas.get(0));
		receita.setQuantidadeMateria(11.2);
		listaReceitas.add(receita);
		
		receita = new IngredienteReceitaProdutoVO();
		receita.setProduto(listaProdutos.get(0));
		receita.setMateriaPrima(listaMateriasPrimas.get(1));
		receita.setQuantidadeMateria(1.2);
		listaReceitas.add(receita);
		
		receita = new IngredienteReceitaProdutoVO();
		receita.setProduto(listaProdutos.get(0));
		receita.setMateriaPrima(listaMateriasPrimas.get(3));
		receita.setQuantidadeMateria(3.2);
		listaReceitas.add(receita);
		
		receita = new IngredienteReceitaProdutoVO();
		receita.setProduto(listaProdutos.get(1));
		receita.setMateriaPrima(listaMateriasPrimas.get(0));
		receita.setQuantidadeMateria(4.2);
		listaReceitas.add(receita);
		
		receita = new IngredienteReceitaProdutoVO();
		receita.setProduto(listaProdutos.get(1));
		receita.setMateriaPrima(listaMateriasPrimas.get(2));
		receita.setQuantidadeMateria(1.1);
		listaReceitas.add(receita);
		
		receita = new IngredienteReceitaProdutoVO();
		receita.setProduto(listaProdutos.get(2));
		receita.setMateriaPrima(listaMateriasPrimas.get(0));
		receita.setQuantidadeMateria(1.4);
		listaReceitas.add(receita);
		
		receita = new IngredienteReceitaProdutoVO();
		receita.setProduto(listaProdutos.get(1));
		receita.setMateriaPrima(listaMateriasPrimas.get(3));
		receita.setQuantidadeMateria(1.7);
		listaReceitas.add(receita);
		
		receita = new IngredienteReceitaProdutoVO();
		receita.setProduto(listaProdutos.get(3));
		receita.setMateriaPrima(listaMateriasPrimas.get(1));
		receita.setQuantidadeMateria(7.5);
		listaReceitas.add(receita);
		
		receita = new IngredienteReceitaProdutoVO();
		receita.setProduto(listaProdutos.get(3));
		receita.setMateriaPrima(listaMateriasPrimas.get(2));
		receita.setQuantidadeMateria(8.3);
		listaReceitas.add(receita);
		
		//LISTA ITENS COMPRA
		listaItensCompra = new ArrayList<ItemCompraVO>();
		
		itemCompra = new ItemCompraVO();
		itemCompra.setMateriaPrima(listaMateriasPrimas.get(0));
		itemCompra.setQuantidade(20.00);
		itemCompra.setValor(20.00);
		listaItensCompra.add(itemCompra);
		
		itemCompra = new ItemCompraVO();
		itemCompra.setMateriaPrima(listaMateriasPrimas.get(1));
		itemCompra.setQuantidade(40.00);
		itemCompra.setValor(30.00);
		listaItensCompra.add(itemCompra);
		
		itemCompra = new ItemCompraVO();
		itemCompra.setMateriaPrima(listaMateriasPrimas.get(2));
		itemCompra.setQuantidade(70.00);
		itemCompra.setValor(10.00);
		listaItensCompra.add(itemCompra);
		
		//LISTA ITENS VENDA
		listaItensVenda = new ArrayList<ItemVendaVO>();
		
		itemVenda = new ItemVendaVO();
		itemVenda.setProduto(listaProdutos.get(0));
		itemVenda.setQuantidade(30);
		itemVenda.setValor(32.50);
		listaItensVenda.add(itemVenda);
		
		itemVenda = new ItemVendaVO();
		itemVenda.setProduto(listaProdutos.get(1));
		itemVenda.setQuantidade(10);
		itemVenda.setValor(22.50);
		listaItensVenda.add(itemVenda);
		
		
		itemVenda = new ItemVendaVO();
		itemVenda.setProduto(listaProdutos.get(2));
		itemVenda.setQuantidade(40);
		itemVenda.setValor(10.10);
		listaItensVenda.add(itemVenda);
		
		//CARREGA ORDENS PRODUCAO
		listaOrdensProducao = new ArrayList<OrdemProducaoVO>();
		listaOrdensProducaoSolicitada = new ArrayList<OrdemProducaoVO>();
		listaOrdensProducaoCancelada = new ArrayList<OrdemProducaoVO>();
		listaOrdensProducaoFinzalizada = new ArrayList<OrdemProducaoVO>();
		
		op = new OrdemProducaoVO();
		op.setDataSolicitacao(new Date());
		op.setIdOrdemProducao(1l);
		op.setProduto(listaProdutos.get(0));
		op.setQuantidade(20);
		op.setSituacao(listasituacoes.get(0));
		listaOrdensProducao.add(op);
		listaOrdensProducaoSolicitada.add(op);
		
		op = new OrdemProducaoVO();
		op.setDataSolicitacao(new Date());
		op.setIdOrdemProducao(2l);
		op.setProduto(listaProdutos.get(1));
		op.setQuantidade(30);
		op.setSituacao(listasituacoes.get(1));
		listaOrdensProducaoCancelada.add(op);
		listaOrdensProducao.add(op);
		
		op = new OrdemProducaoVO();
		op.setDataSolicitacao(new Date());
		op.setIdOrdemProducao(3l);
		op.setProduto(listaProdutos.get(2));
		op.setQuantidade(40);
		op.setSituacao(listasituacoes.get(2));
		listaOrdensProducao.add(op);
		listaOrdensProducaoFinzalizada.add(op);
		
		op = new OrdemProducaoVO();
		op.setDataSolicitacao(new Date());
		op.setIdOrdemProducao(4l);
		op.setProduto(listaProdutos.get(3));
		op.setQuantidade(50);
		op.setSituacao(listasituacoes.get(0));
		listaOrdensProducaoSolicitada.add(op);
		listaOrdensProducao.add(op);
	}
	
}
