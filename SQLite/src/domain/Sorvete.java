package domain;

public class Sorvete {

	// No TotalCross, toda chamada a método tem um custo,
	// portanto, os atributos serão públicos, para que possam
	// ser acessadas e manipuladas sem a necessidade de métodos,
	// resultando em uma performance melhor
	public String sabor;
	public double valor;
	public double estoque;
}
