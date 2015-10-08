package bo;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import teste.BancoEstatico;
import vo.FuncionarioVO;

public class LoginBO {
	
	public Byte verificaLogin(String user, char[] password){		
		
		List<FuncionarioVO> listaFuncionarios = BancoEstatico.listaFuncionarios;
		
		Iterator<FuncionarioVO> it = (Iterator<FuncionarioVO>) listaFuncionarios.iterator();
		FuncionarioVO funcionario;
		while(it.hasNext()){
			
			funcionario = (FuncionarioVO) it.next();

			if(funcionario.getLogin().equals(user) && Arrays.equals(funcionario.getSenha(), password)){
				
				return funcionario.getCargo().getIdCargo();
			}
			
		}
		
		return -1;
	}

}
