package util;

public class Substituicao {

	public String substituiPorPonto(String valorASerSubstituido) {
		valorASerSubstituido = valorASerSubstituido.replace(",", ".");
		return valorASerSubstituido;
	}
	
	public String substituiPorVirgula(String valorASerSubstituido) {
		valorASerSubstituido = valorASerSubstituido.replace(".", ",");
		return valorASerSubstituido;
	}
}
