package ui;

import java.sql.SQLException;
import java.util.List;

import dao.ItemDAO;
import domain.Item;
import totalcross.sys.Settings;
import totalcross.sys.Vm;
import totalcross.ui.Button;
import totalcross.ui.Grid;
import totalcross.ui.Label;
import totalcross.ui.Window;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.GridEvent;

public class ListarSorvetesVendidosWindow extends Window {
	
private Grid gridSorvetesVendidos;
	
	private Button btVoltar;
	
	private int indice = 0;
	
	List<Item> itemList;
	
	private ItemDAO itemDAO;
	
	public ListarSorvetesVendidosWindow() throws SQLException {
		
		btVoltar = new Button("Voltar");
		
		itemDAO = new ItemDAO();
	}
	
	public ListarSorvetesVendidosWindow(int cdvenda) throws SQLException {
		
		btVoltar = new Button("Voltar");
		
		itemDAO = new ItemDAO();
		itemList = itemDAO.findItensByVenda(cdvenda);
		indice = cdvenda;
		
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
		
	@Override
	public void initUI() {
		montaTela();
	}
	
	private void reloadList() throws SQLException {
		itemList = itemDAO.findItensByVenda(indice);
		loadGridData();

		removeAll();
		montaTela();
		reposition();
	}
	
	private void montaTela() {
		add(new Label("Lista Sorvetes Vendidos"), CENTER, TOP + 10);
		add(gridSorvetesVendidos, CENTER, CENTER, 450, getGridSize());
		
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
				try {
					reloadList();
				} catch (SQLException e) {
					Vm.debug(e.getMessage());
				}
				unpop();
			}
			break;
		case GridEvent.SELECTED_EVENT:
			try {
				GridEvent geSeleciona = (GridEvent) event;
				Item item = itemList.get(geSeleciona.row);
				AlterarSorvetesWindow sorvetesVendidos = new AlterarSorvetesWindow(item);
				sorvetesVendidos.popup();
				reloadList();
			} catch (SQLException e) {
				Vm.debug(e.getMessage());
			}
			break;
		default:
			break;
		}
		super.onEvent(event);
	}
}
