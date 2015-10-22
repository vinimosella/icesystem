package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.LogFactory;
import vo.FornecedorVO;
import vo.MateriaPrimaVO;
import daoservice.IMateriaPrimaDAO;

public class MateriaPrimaDAO implements IMateriaPrimaDAO{

	private Connection conexao;
	private PreparedStatement pstm;
	private ConnectionFactory fabrica;
	private ResultSet rs;
		
	{
		fabrica = ConnectionFactory.getInstance();
		
	}
	
	@Override
	public List<MateriaPrimaVO> consultarMateriaPrimaFornecedor(FornecedorVO fornecedor){
		
		List<MateriaPrimaVO> listaMP = null;
		
		MateriaPrimaVO mp = null;
		
		try {
			
			//Cria a conexão com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select mp.id_materia_prima, mp.id_fornecedor, mp.quantidade_disponivel, mp.nome, mp.sabor"
				                            + " from Materia_Prima mp where mp.id_fornecedor = ?");
			
			pstm.setLong(1, fornecedor.getIdPessoaJuridica());
			
			//Executa uma pesquisa no banco
			rs = pstm.executeQuery();
			
			listaMP = new ArrayList<MateriaPrimaVO>();
			
			//Carrega a listaMP 
			while(rs.next()){
				
				mp = new MateriaPrimaVO();
				
				mp.setIdMateriaPrima(rs.getInt("id_materia_prima"));
				mp.setFornecedor(new FornecedorVO());
				mp.getFornecedor().setIdPessoaJuridica(rs.getInt("id_fornecedor"));
				mp.setNome(rs.getString("nome"));
				mp.setQuantidadeDisponivel(rs.getDouble("quantidade"));
				mp.setSabor(rs.getString("sabor"));
				
				listaMP.add(mp);
			}
			
		} catch (SQLException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			listaMP = null;
			
		} catch (ClassNotFoundException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			listaMP = null;
			
		} finally {
			
			//Finalizando os recursos
			try {
				
				conexao.close();
				pstm.close();
				
				if(rs != null){
					
					rs.close();
				}
				
			} catch (SQLException e) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());

				listaMP = null;
				
			}			
		
		}
		
		return listaMP;
	}
		
}
