package util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogFactory {

	private Logger logger;
	
	private LogFactory(){

	}
	
	public static LogFactory getInstance(){
		
		return new LogFactory();
	}
	
	public boolean gerarLog(String nomeClasse, String mensagemErro){
		
		try {
			logger = Logger.getLogger(nomeClasse);
			
			logger.addHandler(new FileHandler(Utilidades.PATH_LOG,true));

			logger.log(Level.SEVERE,mensagemErro);
		
		} catch (SecurityException e) {
	
			return false;
			
		} catch (IOException e) {
			
			return false;
		}
		
		return true;
	}
	
	
}
