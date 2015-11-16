package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.LogFactory;
import util.Utilidades;
import vo.FornecedorVO;
import vo.MateriaPrimaVO;
import vo.StatusVO;

public class MateriaPrimaDAO{

	private Connection conexao;
	private PreparedStatement pstm;
	private ConnectionFactory fabrica;
	private ResultSet rs;
		
	{
		fabrica = ConnectionFactory.getInstance();		
	}
	
	public List<MateriaPrimaVO> consultarMPFornecedor(FornecedorVO fornecedor){
		
		List<MateriaPrimaVO> listaMP = null;
		
		MateriaPrimaVO mp = null;
		StatusVO st = null;
		
		try {
			
			//Cria a conex�o com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select mp.id_materia_prima, mp.id_fornecedor_pj, mp.quantidade_disponivel, mp.nome, mp.sabor, st.id_status, st.descricao from Materia_Prima mp"
                    						+ " inner join Status st on st.id_status = mp.id_status"
                    						+ " where mp.id_fornecedor_pj = ?"
				                            + " and mp.id_status = ?");
			
			pstm.setLong(1, fornecedor.getIdPessoaJuridica());
			
			pstm.setInt(2, Utilidades.STATUS_ATIVO.getIdStatus());
			
			//Executa uma pesquisa no banco
			rs = pstm.executeQuery();
			
			listaMP = new ArrayList<MateriaPrimaVO>();
			
			//Carrega a listaMP 
			while(rs.next()){
				
				mp = new MateriaPrimaVO();
				st = new StatusVO();
				
				st.setDescricao(rs.getString("descricao"));
				st.setIdStatus(rs.getInt("id_status"));
				
				mp.setIdMateriaPrima(rs.getInt("id_materia_prima"));
				mp.setFornecedor(fornecedor);
				mp.getFornecedor().setIdPessoaJuridica(rs.getInt("id_fornecedor_pj"));
				mp.setNome(rs.getString("nome"));
				mp.setQuantidadeDisponivel(rs.getDouble("quantidade_disponivel"));
				mp.setSabor(rs.getString("sabor"));
				mp.setStatus(st);
				
				listaMP.add(mp);
			}
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			listaMP = null;
			
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
			listaMP = null;
			
		} finally {
			
			//Finalizando os recursos
			try {
				
				conexao.close();
				pstm.close();
				
				if(rs != null){
					
					rs.close();
				}
				
			} catch (SQLException sql) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
				
				sql.printStackTrace();
				
				listaMP = null;
				
			}			
		
		}
		
		return listaMP;
	}

	public List<MateriaPrimaVO> consultarTodasMP() {
		
		MateriaPrimaVO mp = null;
		StatusVO st = null;
		
		List<MateriaPrimaVO> listaMP = null;
		
		try {
			
			//Cria a conex�o com o banco
			conexao = fabrica.getConexao();
			
			//Cria o [select] que sera executado no banco
			pstm = conexao.prepareStatement("select mp.id_materia_prima, mp.quantidade_disponivel, mp.nome, mp.sabor, pj.razao_social, st.id_status, st.descricao from Materia_Prima mp"
					                       + " inner join Fornecedor f on mp.id_fornecedor_pj = f.id_fornecedor_pj"
					                       + " inner join Pessoa_Juridica pj on pj.id_pessoa_juridica = f.id_fornecedor_pj"
					                       + " inner join Status st on st.id_status = mp.id_status"
					                       + " where mp.id_status = ?");
			
			pstm.setInt(1, 1);
			
			//Executa uma pesquisa no banco
			rs = pstm.executeQuery();
			
			listaMP = new ArrayList<MateriaPrimaVO>();
			
			//Carregando a listaCompras
			while(rs.next()){
				
				mp = new MateriaPrimaVO();
				st = new StatusVO();
				
				st.setDescricao(rs.getString("descricao"));
				st.setIdStatus(rs.getInt("id_status"));
					
				mp.setIdMateriaPrima(rs.getInt("id_materia_prima"));
				mp.setQuantidadeDisponivel(rs.getDouble("quantidade_disponivel"));
				mp.setNome(rs.getString("nome"));
				mp.setSabor(rs.getString("sabor"));
				mp.setFornecedor(new FornecedorVO());
				mp.getFornecedor().setRazaoSocial(rs.getString("razao_social"));
				mp.setStatus(new StatusVO());
				mp.getStatus().setIdStatus(rs.getInt("id_status"));
				mp.setStatus(st);
				
				listaMP.add(mp);
				
			}
			
		} catch (SQLException sql) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			listaMP = null;
			
		} catch (ClassNotFoundException cnf) {
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
			listaMP = null;
			
		} finally {
			
			//Finalizando os recursos
			try {
				
				conexao.close();
				pstm.close();
				
				if(rs != null){
					
					rs.close();
				}
				
			} catch (SQLException sql) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());

				sql.printStackTrace();
				
				listaMP = null;
				
			}			
		
		}
		
		return listaMP;
	}

	public boolean alterarMP(MateriaPrimaVO mp) {
		
		try {
			
			//Cria a conexao com o banco
			conexao = fabrica.getConexao();
			conexao.setAutoCommit(false); //Inicia uma transa��o
			
			//Cria o [alter] que sera executado no banco
			pstm = conexao.prepareStatement("update materia_prima set id_fornecedor_pj=?, quantidade_disponivel=?, sabor=?, nome=?, id_status=?"
					                        +" where id_materia_prima=?");
			
			pstm.setInt(1, mp.getFornecedor().getIdPessoaJuridica());
			pstm.setDouble(2, mp.getQuantidadeDisponivel());
			pstm.setString(3, mp.getSabor());
			pstm.setString(4, mp.getNome());
			pstm.setInt(5, mp.getStatus().getIdStatus());
			pstm.setInt(6, mp.getIdMateriaPrima());
			
			//Executa uma atualiza��o no banco
			pstm.executeUpdate();
			
			//Em caso de sucesso, executa o commit do update no banco
			conexao.commit();
	
		} catch (ClassNotFoundException cnf) {
			
			//Caso ocorra algum erro, executa o rollback do update no banco
			try {
				
				conexao.rollback();
				
			} catch (SQLException sql) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(), sql.getMessage());
				
				sql.printStackTrace();
				
			}
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
			return false;
			
		} catch (SQLException sql) {
			
			//Caso ocorra algum erro, executa o rollback do update no banco
			try {
				
				conexao.rollback();
				
			} catch (SQLException sql2) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(), sql2.getMessage());
				
				sql2.printStackTrace();
				
				return false;
				
			}
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			return false;
			
		} finally {

			//Finalizando os recursos
			try {

				conexao.close();
				pstm.close();

			} catch (SQLException sql) {

				//Caso ocorra algum erro, executa o rollback do update no banco
				try {
					
					conexao.rollback();
					
				} catch (SQLException sql2) {
					
					LogFactory.getInstance().gerarLog(getClass().getName(), sql2.getMessage());
					
					sql2.printStackTrace();
					
					return false;
					
				}
				
				LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
				
				sql.printStackTrace();
				
				return false;
			}

		}
		
		return true;
	}

	public boolean excluirMP(MateriaPrimaVO mp) {
		
		try {
			
			//Cria a conex�o com o banco
			conexao = fabrica.getConexao();
			conexao.setAutoCommit(false); //Inicia uma transa��o
			
			//Cria o [delete] que sera executado no banco
			pstm = conexao.prepareStatement("update materia_prima set id_status = ? where id_materia_prima=?");
			
			pstm.setInt(1, mp.getStatus().getIdStatus());
			
			pstm.setInt(2, mp.getIdMateriaPrima());
			
			//Executa uma atualiza��o no banco
			pstm.executeUpdate();
			
			//Em caso de sucesso, executa o commit da exclus�o no banco
			conexao.commit();
						
		} catch (ClassNotFoundException cnf) {
			
			//Caso ocorra algum erro, executa o rollback da exclus�o no banco
			try {
				
				conexao.rollback();
				
			} catch (SQLException sql) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(), sql.getMessage());
				
				sql.printStackTrace();
				
				return false;
				
			}
			
			LogFactory.getInstance().gerarLog(getClass().getName(), cnf.getMessage());
			
			cnf.printStackTrace();
						
			return false;
			
		} catch (SQLException sql) {
			
			//Caso ocorra algum erro, executa o rollback da exclus�o no banco
			try {
				
				conexao.rollback();
				
			} catch (SQLException sql2) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(), sql2.getMessage());
				
				sql2.printStackTrace();
				
				return false;
				
			}
			
			LogFactory.getInstance().gerarLog(getClass().getName(), sql.getMessage());
			
			sql.printStackTrace();
			
			return false;
			
		} finally{
			
			//Finalizando os recursos
			try {
				
				conexao.close();
				pstm.close();
				
			} catch (SQLException sql) {
				
				//Caso ocorra algum erro, executa o rollback da exclus�o no banco
				try {
					
					conexao.rollback();
					
				} catch (SQLException sql2) {
					
					LogFactory.getInstance().gerarLog(getClass().getName(), sql2.getMessage());
					
					sql2.printStackTrace();
					
					return false;
				}
				
				LogFactory.getInstance().gerarLog(getClass().getName(), sql.getMessage());
				
				sql.printStackTrace();
				
				return false;
			}
			
		}
		
		return true;
	}

	public boolean cadastrarMP(MateriaPrimaVO mp) {
		
		try {
			
			//Cria a conex�o com o banco
			conexao = fabrica.getConexao(); 				
			conexao.setAutoCommit(false); //Inicia uma transa��o
			
			//Cria o [insert] que sera executado no banco
			pstm = conexao.prepareStatement("insert into materia_prima (nome, sabor, id_fornecedor_pj, quantidade_disponivel, id_status) values (?, ?, ?, ?, ?)");
			
			pstm.setString(1, mp.getNome());
			
			pstm.setString(2, mp.getSabor());
		
			pstm.setInt(3, mp.getFornecedor().getIdPessoaJuridica());
			
			pstm.setDouble(4, mp.getQuantidadeDisponivel());
			
			pstm.setInt(5, mp.getStatus().getIdStatus());
			
			//Executa uma atualiza��o no banco
			pstm.executeUpdate();
			
			//Em caso de sucesso, executa o commit do cadastro no banco
			conexao.commit();		
			
		} catch (ClassNotFoundException cnf) {
			
			//Caso ocorra algum erro, executa o rollback do cadastro no banco
			try {
				
				conexao.rollback();
				
			} catch (SQLException sql) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
				
				sql.printStackTrace();
				
				return false;
				
			}
			
			LogFactory.getInstance().gerarLog(getClass().getName(), cnf.getMessage());
			
			cnf.printStackTrace();
			
			return false;
			
		} catch (SQLException sql) {
			
			//Caso ocorra algum erro, executa o rollback do cadastro no banco
			try {
				
				conexao.rollback();
				
			} catch (SQLException sql2) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(),sql2.getMessage());
				
				sql2.printStackTrace();
				
				return false;
				
			}	
			
			LogFactory.getInstance().gerarLog(getClass().getName(), sql.getMessage());
			
			sql.printStackTrace();
			
			return false;
			
		} finally{
			
			//Finalizando os recursos
			try {
				
				conexao.close();
				pstm.close();					
				
			} catch (SQLException sql) {
				
				//Caso ocorra algum erro, executa o rollback do cadastro no banco
				try {
					
					conexao.rollback();
					
				} catch (SQLException sql2) {
					
					LogFactory.getInstance().gerarLog(getClass().getName(), sql2.getMessage());
					
					sql2.printStackTrace();
					
					return false;
					
				}
				
				LogFactory.getInstance().gerarLog(getClass().getName(), sql.getMessage());
				
				sql.printStackTrace();
				
				return false;
			}
		}
	
	return true;
	
	}

	public boolean alterarEstoqueMaterias(List<MateriaPrimaVO> listaMaterias) {
		
		try {
			
			//Cria a conexao com o banco
			conexao = fabrica.getConexao();
			conexao.setAutoCommit(false); //Inicia uma transa��o
			
			//Cria o [alter] que sera executado no banco
			pstm = conexao.prepareStatement("update materia_prima set quantidade_disponivel=? where id_materia_prima=?");
			
			//Atuliza cada item da lista
			for (MateriaPrimaVO mp : listaMaterias) {
				
				pstm.setDouble(1, mp.getQuantidadeDisponivel());
				pstm.setInt(2, mp.getIdMateriaPrima());
				

				//Executa uma atualiza��o no banco
				pstm.executeUpdate();
				
			}			
			
			//Em caso de sucesso, executa o commit do update no banco
			conexao.commit();
	
		} catch (ClassNotFoundException cnf) {
			
			//Caso ocorra algum erro, executa o rollback do update no banco
			try {
				
				conexao.rollback();
				
			} catch (SQLException sql) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(), sql.getMessage());
				
				sql.printStackTrace();
				
			}
			
			LogFactory.getInstance().gerarLog(getClass().getName(),cnf.getMessage());
			
			cnf.printStackTrace();
			
			return false;
			
		} catch (SQLException sql) {
			
			//Caso ocorra algum erro, executa o rollback do update no banco
			try {
				
				conexao.rollback();
				
			} catch (SQLException sql2) {
				
				LogFactory.getInstance().gerarLog(getClass().getName(), sql2.getMessage());
				
				sql2.printStackTrace();
				
				return false;
				
			}
			
			LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
			
			sql.printStackTrace();
			
			return false;
			
		} finally {

			//Finalizando os recursos
			try {

				conexao.close();
				pstm.close();

			} catch (SQLException sql) {

				//Caso ocorra algum erro, executa o rollback do update no banco
				try {
					
					conexao.rollback();
					
				} catch (SQLException sql2) {
					
					LogFactory.getInstance().gerarLog(getClass().getName(), sql2.getMessage());
					
					sql2.printStackTrace();
					
					return false;
					
				}
				
				LogFactory.getInstance().gerarLog(getClass().getName(),sql.getMessage());
				
				sql.printStackTrace();
				
				return false;
			}

		}
		
		return true;
	}
		
}
