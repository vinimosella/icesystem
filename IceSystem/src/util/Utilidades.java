package util;
import javax.swing.text.MaskFormatter;

public class Utilidades {

	public static MaskFormatter Mascara(String Mascara){  
        
	       MaskFormatter F_Mascara = new MaskFormatter();  
	       try{  
	           F_Mascara.setMask(Mascara); //Atribui a mascara  
	           F_Mascara.setPlaceholderCharacter(' '); //Caracter para preencimento   
	       }  
	       catch (Exception excecao) {  
	    	   excecao.printStackTrace();  
	       }   
	       return F_Mascara;  
	}  
	
}
