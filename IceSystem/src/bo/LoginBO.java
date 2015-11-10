package bo;

import util.Utilidades;
import vo.FuncionarioVO;
import daoimpl.FuncionarioDAO;

public class LoginBO {
	
	private FuncionarioDAO dao;
	
	{
		dao = new FuncionarioDAO();
	}
	
	public FuncionarioVO verificaLogin(String user, String password){
		
		//se algum estiver vazio
		if(user == null ||user.trim().equals("") || password == null || password.equals("")){
			
			return null;
		}
				
		return dao.realizarLogin(user, Utilidades.criptografarMd5(password.trim()));
	}

}
