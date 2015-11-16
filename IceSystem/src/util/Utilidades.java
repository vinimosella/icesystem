package util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import vo.EmailVO;
import vo.FuncionarioVO;
import vo.SituacaoVO;
import vo.StatusVO;
import vo.TelefoneVO;
import dao.SituacaoDAO;
import dao.StatusDAO;

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
	public static final String CARGO_ACESSO_SECRETARIO = "Secretário";
	
	public static final SituacaoVO SITUACAO_VENDA_DEFAULT = sitDao.consultarSituacaoPorDesc("Solicitado");
	public static final SituacaoVO SITUACAO_COMPRA_DEFAULT = sitDao.consultarSituacaoPorDesc("Solicitado");
	public static final SituacaoVO SITUACAO_ORDEM_PRODUCAO_DEFAULT = sitDao.consultarSituacaoPorDesc("Solicitado");
	
	public static final StatusVO STATUS_ATIVO = staDao.consultarPorDescricao("Ativo");
	public static final StatusVO STATUS_INATIVO = staDao.consultarPorDescricao("Inativo");
	
	public static final String[] VET_TIPOS_PRODUTOS = {"Massa","Picolé"};
	
	public static final String PATH_LOG = "C:/Desenvolvimento/Logs/log.txt";
	
	public static String removePontoETraco(String texto){
		
		return texto.replaceAll("\\.|-|(|)","");
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
	
	public static List<List<EmailVO>> gerenciaMudancasEmails(List<EmailVO>listaOriginal, List<EmailVO> listaMudada){
				
		List<EmailVO> listaExcluidos = new ArrayList<EmailVO>();
		List<EmailVO> listaIncluidos = new ArrayList<EmailVO>();
		List<EmailVO> listaAlterados = new ArrayList<EmailVO>();
		boolean flgEstaNaOriginal = false;
		boolean flgEstaNaExcluida = false;
		boolean contem = false;
		
		for(EmailVO emailOri : listaOriginal){
				
			contem = false;
			
			for(EmailVO  emailMud: listaMudada){
				
				//se o id do email for null, ele n veio do banco, portanto deve ser incluido, porém se for incluido na lista aqui
				//será incluido repetidamente, pois este for esta dentro de outro.
				if(emailMud.getIdEmail() == null){
					
					continue; //pula pro proximo laço desse for
				}
				
				//se o email da original estiver na mudada
				if(emailMud.getIdEmail() == emailOri.getIdEmail()){
					
					contem = true;
					
					if(!emailMud.getEmail().trim().equals(emailOri.getEmail().trim())){
						
						listaAlterados.add(emailMud);
					}
					
				}
								
			}
						
			//se o email da original não estiver na mudada
			if(!contem){
				
				listaExcluidos.add(emailOri);
			}
			
		}
		
		//adiciona se foi incluido REALMENTE
		for(EmailVO  emailMud: listaMudada){
			
			if(emailMud.getIdEmail()!=null){
				
				continue;
			}
			
			flgEstaNaOriginal = false;
			flgEstaNaExcluida = false;
			
			for(EmailVO emailOrig : listaOriginal){
				
				if(emailMud.getIdEmail()==null && (emailOrig.getEmail().trim().equals(emailMud.getEmail().trim()))){
					
					flgEstaNaOriginal = true;
				}
				
			}
			
			for(int x=0; x<listaExcluidos.size(); x++){
				
				if(listaExcluidos.get(x).getEmail().trim().equals(emailMud.getEmail().trim())){
					
					flgEstaNaExcluida = true;
					listaExcluidos.remove(x);
				}
				
			}
			
			if(!flgEstaNaOriginal && !flgEstaNaExcluida){

				listaIncluidos.add(emailMud);
			}
		}
		
		List<List<EmailVO>> listaListaEmails = new ArrayList<List<EmailVO>>();
		listaListaEmails.add(listaIncluidos);
		listaListaEmails.add(listaAlterados);
		listaListaEmails.add(listaExcluidos);
		
		return listaListaEmails;
	}
	
	public static List<List<TelefoneVO>> gerenciaMudancasTelefones(List<TelefoneVO>listaOriginal, List<TelefoneVO> listaMudada){
				
		List<TelefoneVO> listaExcluidos = new ArrayList<TelefoneVO>();
		List<TelefoneVO> listaIncluidos = new ArrayList<TelefoneVO>();
		List<TelefoneVO> listaAlterados = new ArrayList<TelefoneVO>();
		boolean flgEstaNaOriginal = false;
		boolean flgEstaNaExcluida = true;
		
		for(TelefoneVO telefoneOri : listaOriginal){
				
			boolean contem = false;
			for(TelefoneVO  telefoneMud: listaMudada){
				
				//se o id do telefone for null, ele n veio do banco, portanto deve ser incluido, porém se for incluido na lista aqui
				//será incluido repetidamente, pois este for esta dentro de outro.
				if(telefoneMud.getIdTelefone() == null){
					
					continue; //pula pro proximo laço desse for
				}
				
				//se o telefone da original estiver na mudada
				if(telefoneMud.getIdTelefone() == telefoneOri.getIdTelefone()){
					
					contem = true;
					
					//se o ddd for diferente, mas for do mesmo id, foi alterado
					if(!telefoneMud.getDdd().trim().equals(telefoneOri.getDdd().trim())){
						
						listaAlterados.add(telefoneMud);
					}
					//se o numero for diferente, mas for do mesmo id, foi alterado
					else if(!telefoneMud.getNumero().trim().equals(telefoneOri.getNumero().trim())){
						
						listaAlterados.add(telefoneMud);
					}
					
				}
				
			}
			
			//se o email da original não estiver na mudada
			if(!contem){
				
				listaExcluidos.add(telefoneOri);
			}
			
		}
		
		//adiciona se foi incluido REALMENTE
		for(TelefoneVO  telefoneMud: listaMudada){
			
			if(telefoneMud.getIdTelefone()!=null){
				
				continue;
			}
			
			flgEstaNaOriginal = false;
			flgEstaNaExcluida = false;
			
			for(TelefoneVO telefoneOrig : listaOriginal){
				
				if(telefoneOrig.getNumero().trim().equals(telefoneMud.getNumero().trim()) && 
						telefoneOrig.getDdd().trim().equals(telefoneMud.getDdd().trim())){
					
					flgEstaNaOriginal = true;
				}
				
			}
			
			for(int x = 0; x < listaExcluidos.size(); x++){
				
				if(listaExcluidos.get(x).getDdd().trim().equals(telefoneMud.getDdd().trim())&& listaExcluidos.get(x).getNumero().trim().equals(telefoneMud.getNumero().trim())){
					
					flgEstaNaExcluida = true;
					listaExcluidos.remove(x);
				}
				
			}
			
			if(!flgEstaNaOriginal && !flgEstaNaExcluida){
				listaIncluidos.add(telefoneMud);
			}
			
		}
		
		List<List<TelefoneVO>> listaListaTelefones = new ArrayList<List<TelefoneVO>>();
		listaListaTelefones.add(listaIncluidos);
		listaListaTelefones.add(listaAlterados);
		listaListaTelefones.add(listaExcluidos);
		
		return listaListaTelefones;
	}

}
