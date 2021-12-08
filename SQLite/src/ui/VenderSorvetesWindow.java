package ui;

import java.util.ArrayList;
import java.util.List;

import dao.ItemDAO;
import dao.VendaDAO;
import domain.Item;
import domain.Sorvete;
import domain.Venda;
import totalcross.sys.Settings;
import totalcross.sys.Vm;
import totalcross.ui.Button;
import totalcross.ui.ComboBox;
import totalcross.ui.Edit;
import totalcross.ui.Grid;
import totalcross.ui.Label;
import totalcross.ui.Window;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.GridEvent;
import util.CarregaSabores;
import util.ScreenToDomain;
import util.Substituicao;

public class VenderSorvetesWindow extends Window {

	private Grid gridSorvetesVendidos;
	
	private ComboBox comboSabor;
	private Edit editValor;
	private Edit editItemQuantidade;
	private Edit editItemTotal;
	private Edit editQuantidade;
	private Edit editTotal;
	
	private Button btInserir;
	private Button btConcluir;
	private Button btVoltar;
	
	int index = 1;
	double totalQtdItemAsDouble = 0.0;
	double totalVendidoAsDouble = 0.0;
	
	List<Sorvete> sorveteList;
	List<Item> itemList;
	List<String[]> vendaList;
	List<String[]> sorvetesVendidosList = new ArrayList<>();
	private CarregaSabores carrega;
	private Substituicao substituir;
	private ScreenToDomain screen;
	
	private VendaDAO vendaDAO;
	private ItemDAO itemDAO;
	
	public VenderSorvetesWindow() {
		
		carrega = new CarregaSabores();
		substituir = new Substituicao();
		screen = new ScreenToDomain();
		
		String valor = "9999999,99";
		String validChars = "0123456789";
		
		carrega.loadSabores();
		comboSabor = new ComboBox(carrega.getSorvetesArray());
		
		editValor = new Edit(valor);
		editValor.setMode(Edit.CURRENCY, true);
		editValor.setValidChars(validChars);
		
		editItemQuantidade = new Edit("9999999,999");
		editItemQuantidade.setMode(Edit.CURRENCY, true);
		editItemQuantidade.setValidChars(validChars);
		
		editItemTotal = new Edit(valor);
		editItemTotal.setMode(Edit.CURRENCY, true);
		editItemTotal.setValidChars(validChars);
		editItemTotal.setEnabled(false);
		
		editQuantidade = new Edit(valor);
		editQuantidade.setMode(Edit.CURRENCY, true);
		editQuantidade.setValidChars(validChars);
		editQuantidade.setEnabled(false);
		
		editTotal = new Edit(valor);
		editTotal.setMode(Edit.CURRENCY, true);
		editTotal.setValidChars(validChars);
		editTotal.setEnabled(false);
		
		btInserir = new Button("Inserir");
		btConcluir = new Button("Concluir Venda");
		btVoltar = new Button("Voltar");
		
		vendaDAO = new VendaDAO();
		itemDAO = new ItemDAO();
		
		sorvetesVendidosList.add(new String[] {"", "", "", "", ""});
		
		loadGridSorvetes();
	}
	
	private void loadGridSorvetes() {
		gridSorvetesVendidos = new Grid(
				new String[] {"ITEM", "SABOR", "VALOR UNIT", "QTD KG", "TOTAL"},
				new int[] {-12, -24, -12, -12, -24},
				new int[] {CENTER, CENTER, CENTER, CENTER, CENTER},
				true
		);
		
		vendaList = sorvetesVendidosList;
		if(vendaList != null && !vendaList.isEmpty()) {
			for(int i = 0; i < vendaList.size(); i++) {
				gridSorvetesVendidos.add(vendaList.get(i));
			}
		}
	}
	
	@Override
	public void initUI() {
		montaTela();
	}
	
	private void reloadList() {
		loadGridSorvetes();
		
		removeAll();
		montaTela();
		reposition();
	}
	
	private void montaTela() {
		add(new Label("Venda de Sorvetes"), CENTER, TOP + 10);
		add(new Label("Sabor"), LEFT + 15, AFTER + 20);
		add(comboSabor, LEFT + 15, AFTER + 5);
		
		add(new Label("Valor"), AFTER + 5, TOP + 50);
		add(editValor, SAME, AFTER + 5);

		add(new Label("Quantidade"), AFTER + 5, TOP + 50);
		add(editItemQuantidade, SAME, AFTER + 5);

		add(new Label("Total"), AFTER + 5, TOP + 50);
		add(editItemTotal, SAME, AFTER + 5);
		
		add(btInserir, AFTER + 5, TOP + 70);
		
		add(gridSorvetesVendidos, CENTER, CENTER, 450, getGridSize());
		
		add(btConcluir, LEFT + 20, BOTTOM - 20);
		
		add(new Label("Total KG"), AFTER + 60, SAME - 20);
		add(editQuantidade, SAME, AFTER + 5);
		
		add(new Label("Total Venda"), AFTER + 5, SAME - 25);
		add(editTotal, SAME, AFTER + 5);
		
		add(btVoltar, RIGHT - 20, BOTTOM - 20);		
	}
	
	private int getGridSize() {
		int size = 480;
		if(!vendaList.isEmpty()) {
			size = (vendaList.size() * 400) + (vendaList.size() * 24) + 10;
			size = size > Settings.screenHeight ? Settings.screenHeight - 240 : size;
		}
		return size;
	}

	@Override
	public void popup() {
		setRect(0, 0, Settings.screenWidth, Settings.screenHeight);
		super.popup();
	}
	
	@Override
	public void onEvent(Event event) {

		switch(event.type) {
		case ControlEvent.PRESSED:
			if(event.target == btConcluir ) {
				insertItem();
				unpop();
			} else if(event.target == btVoltar) {
				unpop();
			} else if(event.target == btInserir) {
				gridInserirItem();
				reloadList();
			}
			break;
		case GridEvent.CHECK_CHANGED_EVENT:
			GridEvent geCheck = (GridEvent) event;
			gridRemoveElemento(sorvetesVendidosList, geCheck.row);
			gridReordenaItens(sorvetesVendidosList);
			gridSorvetesVendidos.del(geCheck.row);
			break;
		case ControlEvent.FOCUS_OUT:
			if(event.target == editValor) {
				editarValor();
			} else if(event.target == editItemQuantidade) {
				editarItemQuantidade();
			}
			break;
		default:
			break;
		}
		super.onEvent(event);
	}
	
	public void gridInserirItem() {
		if(gridSorvetesVendidos.size() >= 1) {
			String indS = String.valueOf(index);
			String valorUnid = editValor.getText();
			String qtdItem = editItemQuantidade.getText();
			String total = editItemTotal.getText();
			
			double qtdItemAsDouble = Double.parseDouble(substituir.substituiPorPonto(qtdItem));
			totalQtdItemAsDouble += qtdItemAsDouble;
			String totalQtdItem = String.valueOf(totalQtdItemAsDouble);
			editQuantidade.setText(substituir.substituiPorVirgula(totalQtdItem));
			
			// Cálculo total vendido
			double totalAsDouble = Double.parseDouble(substituir.substituiPorPonto(total));
			totalVendidoAsDouble += totalAsDouble;
			String totalVendido = String.valueOf(totalVendidoAsDouble);
			editTotal.setText(substituir.substituiPorVirgula(totalVendido));
			
			if(!sorvetesVendidosList.isEmpty()) {
				for(String[] listaSorvete : sorvetesVendidosList) {
					if(listaSorvete[1].equals("")) {
						sorvetesVendidosList.remove(listaSorvete);
						break;
					}
				}
			}
			
			sorvetesVendidosList.add(new String[] {indS, (String) comboSabor.getSelectedItem(), valorUnid, qtdItem, total});
			index++;
		}
	}
	
	public void gridReordenaItens (List<String[]> listaDeItens) {
		int count = 0;
		if(listaDeItens != null && !listaDeItens.isEmpty()) {
			for(String[] listaItens : listaDeItens) {
				if(!"".equals(listaItens[1])) {
					listaItens[0] = String.valueOf(index);
					listaDeItens.set(count, listaItens);
					count++;
					index++;
				} else {
					gridSorvetesVendidos.clear();
				}
			}
		}
	}
	
	public void editarValor() {
		String valor = editValor.getText();
		String qtdItem = editItemQuantidade.getText();
		
		double valorAsDouble = Double.parseDouble(substituir.substituiPorPonto(valor));
		double qtdItemAsDouble;
		if(!qtdItem.isEmpty()) {
			qtdItemAsDouble = Double.parseDouble(substituir.substituiPorPonto(qtdItem));
		} else {
			qtdItemAsDouble = 1.0;
		}
		
		double totalAsDouble = valorAsDouble * qtdItemAsDouble;
		String total = String.valueOf(totalAsDouble);
		editItemTotal.setText(substituir.substituiPorVirgula(total));
	}
	
	public void editarItemQuantidade() {
		String valor = editValor.getText();
		String qtdItem = editItemQuantidade.getText();
		
		double valorAsDouble;
		if(!qtdItem.isEmpty()) {
			valorAsDouble = Double.parseDouble(substituir.substituiPorPonto(valor));
		} else {
			valorAsDouble = 0.0;
		}
		double qtdItemAsDouble = Double.parseDouble(substituir.substituiPorPonto(qtdItem));
		
		double totalAsDouble = valorAsDouble * qtdItemAsDouble;
		String total = String.valueOf(totalAsDouble);
		editItemTotal.setText(substituir.substituiPorVirgula(total));
	}
	
	public void gridRemoveElemento(List<String[]> itens, int indice) {
		if(itens != null && !itens.isEmpty()) {
			index = 1;
			String[] itensRemover = itens.get(indice);
			itens.remove(itensRemover);
			String qtdItem = itensRemover[3];
			String total = itensRemover[4];
			
			// Quantidade vendida
			if(!qtdItem.isEmpty()) {
				double qtdItemAsDouble = Double.parseDouble(substituir.substituiPorPonto(qtdItem));
				totalQtdItemAsDouble -= qtdItemAsDouble;
				String totalQtdItem = String.valueOf(totalQtdItemAsDouble);
				editQuantidade.setText(substituir.substituiPorVirgula(totalQtdItem));
			}				
			
			// Cálculo total vendido
			if(!total.isEmpty()) {
				double totalAsDouble = Double.parseDouble(substituir.substituiPorPonto(total));
				totalVendidoAsDouble -= totalAsDouble;
				String totalVendido = String.valueOf(totalVendidoAsDouble);
				editTotal.setText(substituir.substituiPorVirgula(totalVendido));
			}
		}		
		
		if(vendaList == null || vendaList.isEmpty()) {
			sorvetesVendidosList.add(new String[] {"", "", "", "", ""});
			vendaList = sorvetesVendidosList;
			gridSorvetesVendidos.add(vendaList.get(0));
		}		
	}
	
	public void insertItem() {
		try {
			for(String[] itensCadastrados : sorvetesVendidosList) {
				Item item = screen.screenToDomain(itensCadastrados);
				if(item == null) return;
				// Faz uso do DAO
				itemDAO.insertItem(item);
			}
			
			Venda venda = screen.vendaScreenToDomain(editTotal.getText(), editQuantidade.getText());
			if(venda == null) return;
			// Faz uso do DAO
			if(vendaDAO.insertVenda(venda)) {
				new MessageBox("Info", "Itens Inseridos").popup();
			}
		} catch (Exception e) {
			Vm.debug(e.getMessage());
		}
	}	
}