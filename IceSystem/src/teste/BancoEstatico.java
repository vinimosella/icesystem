package teste;

import java.util.ArrayList;
import java.util.List;

import vo.EmailVO;
import vo.EnderecoVO;
import vo.TelefoneVO;

public class BancoEstatico {
	
	public static EnderecoVO endereco;
	public static List<EnderecoVO> listaEnderecos;
	
	public static EmailVO email;
	public static List<EmailVO> listaEmails;

	public static TelefoneVO telefone;
	public static List<TelefoneVO> listaTelefones;

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
		
	}
	
}
