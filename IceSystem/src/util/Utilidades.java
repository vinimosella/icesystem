package util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

import javax.swing.JFrame;

import vo.FuncionarioVO;
import vo.SituacaoVO;
import vo.StatusVO;
import daoimpl.SituacaoDAO;
import daoimpl.StatusDAO;

public class Utilidades {
	
	private static SituacaoDAO sitDao = new SituacaoDAO();
	private static StatusDAO staDao = new StatusDAO();
	
	public static JFrame frmHome = null;
	public static FuncionarioVO funcionarioLogado = null;
	
	public static final DecimalFormat FORMAT = new DecimalFormat("#,##0.00");

	public static final String CONSULTA_FUNCIONARIOS = "Funcionários";
	public static final String CONSULTA_CLIENTES = "Clientes";
	public static final String CONSULTA_FORNECEDORES = "Fornecedores";
	public static final String CONSULTA_COMPRAS = "Compras";
	public static final String CONSULTA_VENDAS = "Vendas";
	public static final String CONSULTA_PRODUTOS = "Produtos";
	public static final String CONSULTA_MATERIAS_PRIMAS = "Matérias Primas";
	
	public static final String DETALHE_FUNCIONARIOS = "Detalhar Funcionário";
	public static final String DETALHE_CLIENTES = "Detalhar Cliente";
	public static final String DETALHE_FORNECEDORES = "Detalhar Fornecedor";
	public static final String DETALHE_COMPRAS = "Detalhar Compra";
	public static final String DETALHE_VENDAS = "Detalhar Venda";
	
	public static final String ATUALIZAR_COMPRAS = "Atualizar Compra";
	public static final String ATUALIZAR_VENDAS = "Atualizar Venda";
	public static final String ATUALIZAR_ORDEM_PRODUCAO = "Atualizar Ordem de Produção";
	public static final String ATUALIZAR_PRODUTO = "Atualizar Produto";
	public static final String ATUALIZAR_MATERIA_PRIMA = "Atualizar Matéria Prima";
	
	public static final String CADASTRAR_FUNCIONARIO = "Cadastrar Funcionário";
	public static final String CADASTRAR_FORNECEDOR = "Cadastrar Fornecedor";
	public static final String CADASTRAR_CLIENTE = "Cadastrar Cliente";
	public static final String CADASTRAR_PRODUTO = "Cadastrar Produto";
	public static final String CADASTRAR_MATERIA_PRIMA = "Cadastrar Matéria Prima";
	
	public static final String FINALIZADO = "Finalizado";
	public static final String CANCELADO = "Cancelado";
	public static final String ATIVO = "Ativo";
	
	public static final String CARGO_ACESSO_ADMIN = "Administrador";
	public static final String CARGO_ACESSO_SECRETARIO = "Secretario";
	
	public static final SituacaoVO SITUACAO_VENDA_DEFAULT = sitDao.consultarSituacaoPorDesc("Solicitado");
	public static final SituacaoVO SITUACAO_COMPRA_DEFAULT = sitDao.consultarSituacaoPorDesc("Solicitado");
	public static final SituacaoVO SITUACAO_ORDEM_PRODUCAO_DEFAULT = sitDao.consultarSituacaoPorDesc("Solicitado");
	
	public static final StatusVO STATUS_ATIVO = staDao.consultarPorDescricao("Ativo");
	public static final StatusVO STATUS_INATIVO = staDao.consultarPorDescricao("Inativo");
	
	public static final String[] VET_TIPOS_PRODUTOS = {"Massa","Picolé"};
	
	public static final String PATH_LOG = "C:/Desenvolvimento/Logs/log.txt";
	
	public static String removePontoETraco(String texto){
		
		return texto.replaceAll("\\.|-","");
	}
	
	public static String trocaEspacoPorPonto(String texto){
		
		return texto.replaceAll("\\ ",".");
	}
	
	public static String criptografarMd5(String campo){
		
		String campoFormatado = null;
		
		try {
			
			//Biblioteca para indicar em qual tipo sera a criptografia ( no caso, md5)
			
			MessageDigest conversor = MessageDigest.getInstance("md5");
		
			//Converte um vetor de bytes que é passado pelo campo.getBytes
			
			conversor.update(campo.getBytes());
			
			//O conversor criptografa o vetor de bytes
			//O numero 1 serve para manter o padrao entre o BigInteger e o md5, sem ele, pode gerar valores diferentes entre os 2
			
			BigInteger numericoAux = new BigInteger(1,conversor.digest());
			
			//Indica de qual tipo o md5 ira criptografar ( 16 = Hexadecimal )
			
			campoFormatado = numericoAux.toString(16);
			
		} catch (NoSuchAlgorithmException e) {
			
			campoFormatado = null;
			
		}
		
		return campoFormatado;
	}

}
