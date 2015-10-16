package util;

import java.text.DecimalFormat;

import javax.swing.JFrame;

import vo.FuncionarioVO;

public class Utilidades {
	
	public static JFrame frmHome = null;
	public static FuncionarioVO funcionarioLogado = null;
	
	public static final DecimalFormat FORMAT = new DecimalFormat("#,##0.00");

	public static final String CONSULTA_FUNCIONARIOS = "Consultar Funcionários";
	public static final String CONSULTA_CLIENTES = "Consultar Clientes";
	public static final String CONSULTA_FORNECEDORES = "Consultar Fornecedores";
	public static final String CONSULTA_COMPRAS = "Consultar Compras";
	public static final String CONSULTA_VENDAS = "Consultar Vendas";
	
	public static final String DETALHE_FUNCIONARIOS = "Detalhar Funcionários";
	public static final String DETALHE_CLIENTES = "Detalhar Clientes";
	public static final String DETALHE_FORNECEDORES = "Detalhar Fornecedores";
	
	public static final String ATUALIZAR_COMPRAS = "Atualizar Compras";
	public static final String ATUALIZAR_VENDAS = "Atualizar Vendas";
	
	//TODO
	public static final int[] CODIGOS_SITUACAO_VENDA = {1,2,3};
	public static final int CODIGO_SITUACAO_VENDA_DEFAULT = 1;
	
	//TODO
	public static final int[] CODIGOS_SITUACAO_COMPRA = {1,2,4};
	public static final int CODIGO_SITUACAO_COMPRA_DEFAULT = 1;
	
	public static final String PATH_LOG = "C:/Desenvolvimento/Logs/log.txt";
}
