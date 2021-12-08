package util;

import java.sql.SQLException;

import dao.ItemDAO;
import domain.Item;
import domain.Sorvete;
import domain.Venda;
import totalcross.sys.Vm;

public class ScreenToDomain {
	
	ValidateFields valida = new ValidateFields();
	CalculaCodigoVenda calcula = new CalculaCodigoVenda();
	Substituicao substituir = new Substituicao();
	ItemDAO itemDAO = new ItemDAO();
	
	// ScreenToDomain *** Item ***
	public Item screenToDomain(String[] itensCadastrados) throws Exception {
		int cditem = !itemDAO.findAllItens().isEmpty() ? ItemDAO.ultimoCodigoItem() + 1 : 1;
		int cdvenda = calcula.calculaCodigoVenda();
		String sabor = itensCadastrados[1];
		String totalItem = itensCadastrados[4];
		String qtdItem = itensCadastrados[3];
		
		// Caso o método validateFields retorne false, entra no if e dá esse return, que não termina a execução
		if(!valida.validateFields(sabor, qtdItem, totalItem)) throw new IllegalArgumentException("Campos inválidos");
		
		return createDomain(cditem, cdvenda, sabor, totalItem, qtdItem);
	}
	
	private Item createDomain(int cditem, int cdvenda, String sabor, String totalItem, String qtdItem) {
		double qtdItemAsDouble = 0;
		double totalItemAsDouble = 0;
		try {
			qtdItem = qtdItem.replace(",", ".");
			qtdItemAsDouble = Double.parseDouble(qtdItem);
			totalItem = totalItem.replace(",", ".");
			totalItemAsDouble = Double.parseDouble(totalItem);
		} catch(Exception e) {
			Vm.debug(e.getMessage());
			return null;
		}
		
		// Chama o objeto Item e faz as atribuições de acordo com os valores passados e seus atributos
		Item item = new Item();
		item.cditem = cditem;
		item.cdvenda = cdvenda;
		item.sabor = sabor;
		item.totalVendido = totalItemAsDouble;
		item.qtdTotalVendida = qtdItemAsDouble;
		return item;
	}
	
	public Item itemScreenToDomain(int cditem) throws Exception {
		int indice = cditem;
		// Caso o método validateFields retorne false, entra no if e dá esse return, que não termina a execução
		if(!valida.validateFields(cditem)) throw new Exception("Campos inválidos");
		
		return createItemDomain(indice);
	}
	
	private Item createItemDomain(int indice) {
		// Chama o objeto Item e faz as exclusões de acordo com os valores passados e seus atributos
		Item item = new Item();
		item.cditem = indice;
		return item;
	}

	public Item itemScreenToDomain(int cditem, String saborItem, String valorUnid, String qTotalVendida) throws Exception {
		int indice = cditem;
		String sabor = saborItem;
		String valor = valorUnid;
		String qtdItem = qTotalVendida;
		// Caso o método validateFields retorne false, entra no if e dá esse return, que não termina a execução
		if(!valida.validateFields(indice, sabor, valor, qtdItem)) throw new Exception("Campos inválidos");
		
		return createItemDomain(indice, sabor, valor, qtdItem);
	}
	
	private Item createItemDomain(int cditem, String sabor, String valor, String qtdItem) {
		double qtdItemAsDouble = 0;
		double valorUnidAsDouble = Double.parseDouble(substituir.substituiPorPonto(valor));
		double totalItemAsDouble = 0;
		try {
			qtdItemAsDouble = Double.parseDouble(substituir.substituiPorPonto(qtdItem));
			totalItemAsDouble = qtdItemAsDouble * valorUnidAsDouble;
		} catch(Exception e) {
			Vm.debug(e.getMessage());
			return null;
		}
		
		// Chama o objeto Item e faz as atribuições de acordo com os valores passados e seus atributos
		Item item = new Item();
		item.cditem = cditem;
		item.sabor = sabor;
		item.totalVendido = totalItemAsDouble;
		item.qtdTotalVendida = qtdItemAsDouble;
		return item;
	}
	
	// ScreenToDomain *** Venda ***
	public Venda vendaScreenToDomain(String valor1, String valor2) throws ArithmeticException, SQLException {
		int cdvenda = calcula.calculaCodigoVenda();
		String valortotal = valor1;
		String qtotalvendida = valor2;
		if(!valida.validateFields(valortotal, qtotalvendida)) throw new IllegalArgumentException("Campos inválidos");
		return createVendaDomain(cdvenda, valortotal, qtotalvendida);
	}
	
	private Venda createVendaDomain(int cdvenda, String valortotal, String qtotalvendida) {
		double valortotalAsDouble = 0;
		double qtotalvendidaAsDouble = 0;
		try {
			valortotalAsDouble = Double.parseDouble(substituir.substituiPorPonto(valortotal));
			qtotalvendidaAsDouble = Double.parseDouble(substituir.substituiPorPonto(qtotalvendida));
		} catch(Exception e) {
			Vm.debug(e.getMessage());
			return null;
		}
		
		// Chama o objeto Venda e faz as atribuições de acordo com os valores passados e seus atributos
		Venda venda = new Venda();
		venda.cdvenda = cdvenda;
		venda.qtdVendida = qtotalvendidaAsDouble;
		venda.valorTotal = valortotalAsDouble;
		return venda;
	}
	
	public Venda vendaScreenToDomain(int codvenda, String valor1, String valor2) throws ArithmeticException, SQLException {
		int cdvenda = codvenda;
		String valorTotal = valor1;
		String qtdTotal = valor2;
		if(!valida.validateFields(valorTotal, qtdTotal)) throw new IllegalArgumentException("Campos inválidos");
		return createExcluirVendaDomain(cdvenda, valorTotal, qtdTotal);
	}
	
	private Venda createExcluirVendaDomain(int cdvenda, String valorTotal, String qtdTotal) {
		double valorTotalAsDouble = 0;
		double qtdTotalAsDouble = 0;
		try {
			valorTotalAsDouble = Double.parseDouble(substituir.substituiPorPonto(valorTotal));
			qtdTotalAsDouble = Double.parseDouble(substituir.substituiPorPonto(qtdTotal));
		} catch(Exception e) {
			Vm.debug(e.getMessage());
			return null;
		}
		
		// Chama o objeto Venda e faz as atribuições de acordo com os valores passados e seus atributos
		Venda venda = new Venda();
		venda.cdvenda = cdvenda;
		venda.qtdVendida = qtdTotalAsDouble;
		venda.valorTotal = valorTotalAsDouble;
		return venda;
	}
	
	// ScreenToDomain *** Sorvete ***
	public Sorvete sorveteScreenToDomain(String valor1, String valor2, String valor3) throws Exception {
		String sabor = valor1;
		String valor = valor2;
		String estoque = valor3;
		// Caso o método validateFields retorne false, entra no if e dá esse return, que não termina a execução
		if(!valida.validateFields(valor1, valor2, valor3)) throw new Exception("Campos inválidos");
		
		return createSorveteDomain(sabor, valor, estoque);	
	}

	private Sorvete createSorveteDomain(String sabor, String valor, String estoque) {
		// Declara as variáveis double e depois substitui as vírgulas das strings por ponto,
		// para fazer o parse para double, que só aceita ponto em seus valores
		double valorAsDouble = 0;
		double estoqueAsDouble = 0;
		try {
			valorAsDouble = Double.parseDouble(substituir.substituiPorPonto(valor));
			estoqueAsDouble = Double.parseDouble(substituir.substituiPorPonto(estoque));
		} catch(Exception e) {
			Vm.debug(e.getMessage());
			return null;
		}
		
		// Chama o objeto Sorvete e faz as atribuições de acordo com os valores passados e seus atributos
		Sorvete sorvete = new Sorvete();
		sorvete.sabor = sabor;
		sorvete.valor = valorAsDouble;
		sorvete.estoque = estoqueAsDouble;
		return sorvete;
	}	
}
