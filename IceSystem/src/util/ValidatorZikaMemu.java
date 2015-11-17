package util;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

public class ValidatorZikaMemu extends JFormattedTextField{

	private static final long serialVersionUID = 1L;

	public ValidatorZikaMemu(int comprimento){
		
		validaTamanhoCampo(comprimento);
		
	}
	
	public ValidatorZikaMemu(String mascara) throws ParseException{
		
		super(new MaskFormatter(mascara));
		
		this.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusGained(FocusEvent e) {

				if(ValidatorZikaMemu.this.getText().equals("Usuário")){
					
					ValidatorZikaMemu.this.setCaretPosition(0);
				}
			}
			
		});
	}
	
	private void validaTamanhoCampo(final int comprimento){
		
		this.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
				if(ValidatorZikaMemu.this.getText().length() > comprimento){
					
					e.consume();
				}
				
			}
			
		});
		
	}
	
}
