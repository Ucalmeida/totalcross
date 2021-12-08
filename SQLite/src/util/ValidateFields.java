package util;

import totalcross.ui.dialog.MessageBox;

public class ValidateFields {
	
	String aviso = "Aten��o";
	String sabor = "Escolha um sabor!";
	String valor = "Algum valor faltou ser digitado!";
	String quantidade = "Digite uma quantidade!";
	// Criar evento do bot�o inserir. Nele, vamos obter os valores digitados pelos usu�rios,
	// mas vamos evitar a inser��o de valores vazios. Passamos uma mensagem atrav�s de um MessageBox, 
	// caso o valor seja vazio, contendo o T�tulo e a Mensagem. Al�m disso, dessa forma, o m�todo retorna,
	// sem terminar a execu��o
	public boolean validateFields(int indice, String valor1, String valor2, String valor3) {
		
		if(indice == 0) {
			new MessageBox(aviso, "Item n�o existe!").popup();
			return false;
		}
		
		if(valor1.isEmpty()) {
			new MessageBox(aviso, sabor).popup();
			return false;
		}
		
		if(valor2.isEmpty()) {
			new MessageBox(aviso, valor).popup();
			return false;
		}
		
		if(valor3.isEmpty()) {
			new MessageBox(aviso, quantidade).popup();
			return false;
		}
		
		return true;
	}
	public boolean validateFields(int cditem) {
		if(cditem == 0) {
			new MessageBox(aviso, "Item n�o existe!").popup();
			return false;
		}
		
		return true;
	}
	
	public boolean validateFields(String valor1, String valor2, String valor3) {
		
		if(valor1.isEmpty()) {
			new MessageBox(aviso, sabor).popup();
			return false;
		}
		
		if(valor2.isEmpty()) {
			new MessageBox(aviso, valor).popup();
			return false;
		}
		
		if(valor3.isEmpty()) {
			new MessageBox(aviso, quantidade).popup();
			return false;
		}
		
		return true;
	}

	public boolean validateFields(String valor1, String valor2) {
		
		if(valor1.isEmpty()) {
			new MessageBox(aviso, sabor).popup();
			return false;
		}
		
		if(valor2.isEmpty()) {
			new MessageBox(aviso, valor).popup();
			return false;
		}
		
		return true;
	}
}
