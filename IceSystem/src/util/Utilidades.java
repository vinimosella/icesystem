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
	public static final String CONSULTA_PRODUTOS = "Consultar Produtos";
	public static final String CONSULTA_MATERIAS_PRIMAS = "Consultar Matérias Primas";
	
	public static final String DETALHE_FUNCIONARIOS = "Detalhar Funcionário";
	public static final String DETALHE_CLIENTES = "Detalhar Cliente";
	public static final String DETALHE_FORNECEDORES = "Detalhar Fornecedor";
	public static final String DETALHE_COMPRAS = "Detalhar Compra";
	public static final String DETALHE_VENDAS = "Detalhar Venda";
	
	public static final String ATUALIZAR_COMPRAS = "Atualizar Compras";
	public static final String ATUALIZAR_VENDAS = "Atualizar Vendas";
	public static final String ATUALIZAR_ORDEM_PRODUCAO = "Atualizar Ordem de Produção";
	
	public static final String FINALIZADO = "Finalizado";
	public static final String CANCELADO = "Cancelado";
	
	public static final int CODIGO_SITUACAO_VENDA_DEFAULT = 0;
	public static final int CODIGO_SITUACAO_COMPRA_DEFAULT = 0;
	public static final int CODIGO_SITUACAO_ORDEM_PRODUCAO_DEFAULT = 0;
	
	public static final String PATH_LOG = "C:/Desenvolvimento/Logs/log.txt";
}
