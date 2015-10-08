package bo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import teste.BancoEstatico;
import vo.CidadeVO;
import vo.EstadoVO;
import vo.FornecedorVO;
import dao.FornecedorDAO;

public class FornecedorBO {
	
	public boolean cadastrarFornecedor(FornecedorVO fornecedor) {

		return FornecedorDAO.cadastrarFornecedor(fornecedor);
	}
	
	public boolean excluirFornecedor(FornecedorVO fornecedor){
		
		return FornecedorDAO.excluirFornecedor(fornecedor);
	}
	
	public boolean atualizarFornecedor(FornecedorVO fornecedor){
		
		return FornecedorDAO.atualizarFornecedor(fornecedor);
	}
	
	public List<FornecedorVO> consultarFornecedores(){
		
		return BancoEstatico.listaFornecedores;
	}
	
	public FornecedorVO detalharFornecedor(FornecedorVO fornecedor){
		
		fornecedor.setListaEmails(BancoEstatico.listaEmails);
		fornecedor.setListaTelefones(BancoEstatico.listaTelefones);
				
		return fornecedor;
		//return FornecedorDAO.detalharFornecedor(fornecedor);
	}
	
	//########################################################################
	
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
	
}
