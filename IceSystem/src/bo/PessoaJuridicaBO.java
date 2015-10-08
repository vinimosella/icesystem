package bo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import teste.BancoEstatico;
import vo.CidadeVO;
import vo.EmailVO;
import vo.EstadoVO;
import vo.TelefoneVO;

public class PessoaJuridicaBO {

	public List<EstadoVO> buscaEstados(){
		
		return BancoEstatico.listaEstados;
	}
	
	public List<CidadeVO> buscaCidadesPorEstado(Integer idEstado){
		
		Iterator<CidadeVO> it = BancoEstatico.listaCidades.iterator();		
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
	
	public boolean isTelefoneExistenteLista(String txtTelefone, List<TelefoneVO> listaTelefones){
		
		Iterator<TelefoneVO> it = listaTelefones.iterator();
		
		while(it.hasNext()){
			
			TelefoneVO telefone = it.next();
			
			if(txtTelefone.equals(telefone.getDdd()+telefone.getNumero())){
				
				return true;				
			}
			
		}
		
		return false;
	}
	
	public boolean isEmailExistenteLista(String txtEmail, List<EmailVO> listaEmails){
				
		Iterator<EmailVO> it = listaEmails.iterator();
		
		while(it.hasNext()){
			
			EmailVO email = it.next();
						
			if(txtEmail.equals(email.getEmail())){
				
				return true;				
			}
			
		}
		
		return false;
	}
	
	public CidadeVO buscaCidadePorNomeNaLista(String nomeCidade, List<CidadeVO> listaCidades){
		
		Iterator<CidadeVO> it = listaCidades.iterator();
		
		CidadeVO cidade;
		
		while(it.hasNext()){
			
			cidade = it.next();
			
			if(cidade.getNome().equals(nomeCidade)){
				
				return cidade;
			}
			
		}
		
		return null;
	}
	
	public EstadoVO buscaEstadoPorNomeNaLista(String nomeEstado, List<EstadoVO> listaEstados){
		
		Iterator<EstadoVO> it = listaEstados.iterator();
		
		EstadoVO estado;
		
		while(it.hasNext()){
			
			estado = it.next();
			
			if(estado.getNome().equals(nomeEstado)){
				
				return estado;
			}
			
		}
		
		return null;
	}
	
}
