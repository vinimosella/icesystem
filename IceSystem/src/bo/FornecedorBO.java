package bo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import daoimpl.FornecedorDAO;
import teste.BancoEstatico;
import vo.CidadeVO;
import vo.EstadoVO;
import vo.FornecedorVO;

public class FornecedorBO {
	
	private FornecedorDAO dao;
	
	{
		dao = new FornecedorDAO();
	}
	
	public boolean cadastrarFornecedor(FornecedorVO fornecedor) {

		return true;
	}
	
	public boolean excluirFornecedor(FornecedorVO fornecedor){
		
		return true;
	}
	
	public boolean atualizarFornecedor(FornecedorVO fornecedor){
		
		return true;
	}
	
	public List<FornecedorVO> consultarFornecedores(){
		
		return dao.consultarFornecedores();
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
