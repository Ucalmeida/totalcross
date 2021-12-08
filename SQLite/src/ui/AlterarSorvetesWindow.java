package ui;

import java.sql.SQLException;
import java.util.List;

import dao.ItemDAO;
import dao.VendaDAO;
import domain.Item;
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

public class AlterarSorvetesWindow extends Window {
	
	private Grid gridSorvetesVendidos;
	
	private ComboBox comboSabor;
	private Edit editValor;
	private Edit editItemQuantidade;
	private Edit editItemTotal;
	
	private Button btAtualizar;
	private Button btExcluir;
	private Button btVoltar;
	
	private int cditem = 0;
	private int codvenda = 0;
	private double totalVendido = 0;
	private double quantidadeVendida = 0;
	
	List<Item> itemList;
	List<Venda> vendaList;
	private CarregaSabores carrega;
	private ScreenToDomain screen;
	private Substituicao substituir;
	
	private ItemDAO itemDAO;
	private VendaDAO vendaDAO;
	
	private boolean atualizando;
	
	public AlterarSorvetesWindow() throws SQLException {
		
		String valor = "9999999,99";
		String validChars = "0123456789";
		carrega = new CarregaSabores();
		screen = new ScreenToDomain();
		substituir = new Substituicao();
		
		this.atualizando = false;
		
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
		
		btAtualizar = new Button("Atualizar");
		btExcluir = new Button("Excluir");
		btVoltar = new Button("Voltar");
		
		itemDAO = new ItemDAO();
		vendaDAO = new VendaDAO();
	}
	
	public AlterarSorvetesWindow(int cdvenda) throws SQLException {
		
		this.atualizando = false;
		
		btAtualizar = new Button("Atualizar");
		btVoltar = new Button("Voltar");
		
		itemList = itemDAO.findItensByVenda(cdvenda);
		
		loadGridData();
	}
	
	private void loadGridData() {
		gridSorvetesVendidos = new Grid(
				new String[] {"ITEM", "SABOR", "VALOR UNIT", "QTD KG", "TOTAL"},
				new int[] {-12, -24, -12, -12, -24},
				new int[] {CENTER, CENTER, CENTER, CENTER, CENTER},
				false
		);
		
		if(itemList != null && !itemList.isEmpty()) {
			for( Item item : itemList) {
				
				gridSorvetesVendidos.add(
						new String[] {
								String.valueOf(item.cditem), 
								String.valueOf(item.sabor), 
								String.valueOf(item.totalVendido / item.qtdTotalVendida),
								String.valueOf(item.qtdTotalVendida),
								String.valueOf(item.totalVendido)						
						});
			}
		}
	}
	
	// Novo construtor setando o valor nos campos edit
	public AlterarSorvetesWindow(Item item) throws SQLException {
		// Primeiro chamamos o construtor vazio aqui
		this();
		
		this.atualizando = true;
		
		// Aqui setamos os valores do sorvete escolhido anteriormente
		cditem = item.cditem;
		codvenda = item.cdvenda;
		comboSabor.setSelectedItem(item.sabor);
		editValor.setText(String.valueOf(item.totalVendido / item.qtdTotalVendida));
		editItemQuantidade.setText(String.valueOf(item.qtdTotalVendida));
		editItemTotal.setText(String.valueOf(item.totalVendido));
	}
	
	@Override
	public void initUI() {
		montaTela();
	}
	
	private void montaTela() {
		add(new Label("Atualizar Sorvetes Vendidos"), CENTER, TOP + 10);
		if(!atualizando) {
			add(gridSorvetesVendidos, CENTER, CENTER, 450, getGridSize());
		}
		
		if(atualizando) {
			add(new Label("Sabor"), LEFT + 10, TOP + 10);
			add(comboSabor, LEFT + 10, AFTER + 5, FILL - 10, PREFERRED);
			
			add(new Label("Valor"), LEFT + 10, AFTER + 20);
			add(editValor, LEFT + 10, AFTER + 5);
			
			add(new Label("Quantidade"), AFTER + 10, SAME - 25);
			add(editItemQuantidade, SAME, AFTER + 5);

			add(new Label("Total"), AFTER + 10, SAME - 25);
			add(editItemTotal, SAME, AFTER + 5);
			
			add(btAtualizar, CENTER - 35, TOP + 150);
			add(btExcluir, CENTER + 35, SAME);
		}
		add(btVoltar, CENTER, BOTTOM - 25);
	}
	
	private int getGridSize() {
		int size = 480;
		if(!itemList.isEmpty()) {
			size = (itemList.size() * 400) + (itemList.size() * 24) + 10;
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
			if(event.target == btVoltar) {
				unpop();
			} else if(event.target == btExcluir) {
				excluirItem();
				unpop();
			} else if(event.target == btAtualizar) {
				atualizarItem();
				unpop();
			}
			break;
		case GridEvent.SELECTED_EVENT:
			try {
				GridEvent geSeleciona = (GridEvent) event;
				Item item = itemList.get(geSeleciona.row);
				AlterarSorvetesWindow sorvetesVendidos = new AlterarSorvetesWindow(item);
				sorvetesVendidos.popup();
			} catch (SQLException e) {
				Vm.debug(e.getMessage());
			}
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
	
	public void editarValor() {
		String valor = editValor.getText();
		String qtdItem = editItemQuantidade.getText();
		
		double valorAsDouble;
		if(!valor.isEmpty()) {
			valorAsDouble = Double.parseDouble(substituir.substituiPorPonto(valor));
		} else {
			valorAsDouble = 0.0;
		}
		
		double qtdItemAsDouble = Double.parseDouble(substituir.substituiPorPonto(qtdItem));
		
		double totalAsDouble = valorAsDouble * qtdItemAsDouble;
		String total = String.valueOf(totalAsDouble);
		editItemTotal.setText(substituir.substituiPorVirgula(total));
	}
	
	public void editarItemQuantidade() {
		String valor = editValor.getText();
		String qtdItem = editItemQuantidade.getText();
		
		double qtdItemAsDouble;
		if(!qtdItem.isEmpty()) {
			qtdItemAsDouble = Double.parseDouble(substituir.substituiPorPonto(qtdItem));
		} else {
			qtdItemAsDouble = 0.0;
		}
		
		double valorAsDouble = Double.parseDouble(substituir.substituiPorPonto(valor));
		
		double totalAsDouble = valorAsDouble * qtdItemAsDouble;
		String total = String.valueOf(totalAsDouble);
		editItemTotal.setText(substituir.substituiPorVirgula(total));
	}
	
	public void atualizarItem() {
		try {
			Item item = screen.itemScreenToDomain(cditem, comboSabor.getSelectedItem().toString(), editValor.getText(), editItemQuantidade.getText());
			if(item == null) return;
			// Faz uso do DAO
			itemDAO.atualizarItem(item);
			
			carregaLista();
			
			Venda venda = screen.vendaScreenToDomain(codvenda, String.valueOf(totalVendido), String.valueOf(quantidadeVendida));
			if(venda == null) return;
			// Faz uso do DAO
			if(vendaDAO.atualizarVenda(venda)) {
				new MessageBox("Info", "Venda Atualizada!").popup();
			}
		} catch (SQLException e) {
			Vm.debug(e.getMessage());
		} catch (Exception e) {
			Vm.debug(e.getMessage());
		}
	}

	public void excluirItem() {
		try {
			Item item = screen.itemScreenToDomain(cditem);
			if(item == null) return;
			// Faz uso do DAO
			itemDAO.excluirItem(item);
			
			carregaLista();
			
			Venda venda = screen.vendaScreenToDomain(codvenda, String.valueOf(totalVendido), String.valueOf(quantidadeVendida));
			if(venda == null) return;
			// Faz uso do DAO
			if(!itemList.isEmpty()) {
				vendaDAO.atualizarVenda(venda);
				new MessageBox("Info", "Venda Excluída!").popup();
			} else {
				vendaDAO.excluirVenda(venda);
				new MessageBox("Info", "Venda Excluída!").popup();
			}
		} catch (SQLException e) {
			Vm.debug(e.getMessage());
		} catch (Exception e) {
			Vm.debug(e.getMessage());
		}
	}
	
	private void carregaLista() throws SQLException {
		itemList = itemDAO.findItensByVenda(codvenda);
		for(Item somaItem : itemList) {
			totalVendido += somaItem.totalVendido;
			quantidadeVendida += somaItem.qtdTotalVendida;
		}
	}
}
