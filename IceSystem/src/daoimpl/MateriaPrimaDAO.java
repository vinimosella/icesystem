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
	public List<MateriaPrimaVO> consultarMateriaPrima(MateriaPrimaVO materiaPrima){
		
		List<MateriaPrimaVO> listaMP = null;
		
		MateriaPrimaVO mp = null;
		
		try {
			
			conexao = fabrica.getConexao();
			
			pstm = conexao.prepareStatement("select id_materia_prima, id_fornecedor, quantidade_disponivel, nome, sabor"
				                            + " from MateriaPrima mp where mp.id_materia_prima = ?");
			
			pstm.setLong(1, materiaPrima.getIdMateriaPrima());
		
			rs = pstm.executeQuery();
			
			listaMP = new ArrayList<MateriaPrimaVO>();
			
			while(rs.next()){
				
				mp = new MateriaPrimaVO();
				
				materiaPrima.setIdMateriaPrima(rs.getInt("id_materia_prima"));
				materiaPrima.setFornecedor(new FornecedorVO());
				materiaPrima.getFornecedor().setIdPessoaJuridica(rs.getInt("id_fornecedor"));
				materiaPrima.setNome(rs.getString("nome"));
				materiaPrima.setQuantidadeDisponivel(rs.getDouble("quantidade"));
				materiaPrima.setSabor(rs.getString("sabor"));
				
				listaMP.add(mp);
			}
			
		} catch (SQLException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			listaMP = null;
			
		} catch (ClassNotFoundException e) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),e.getMessage());
			listaMP = null;
		}
		
		return listaMP;
	}
	
	@Override
	public boolean alterarMateriasPrimas(List<MateriaPrimaVO> materiaPrima) {
		
		return false;
	}
	
	
}
