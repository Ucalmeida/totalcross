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
import totalcross.ui.Edit;
import totalcross.ui.Grid;
import totalcross.ui.Label;
import totalcross.ui.Window;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.GridEvent;

public class ListarVendaSorvetesWindow extends Window {
	
	private Grid gridVendas;
	
	private Edit editQuantidade;
	private Edit editTotal;
	
	private Button btVender;
	private Button btVoltar;
	
	List<Venda> vendaList;
	List<Item> itemList;
	
	private VendaDAO vendaDAO;
	private ItemDAO itemDAO;
	
	public ListarVendaSorvetesWindow() throws SQLException {
		
		String valor = "9999999,99";
		String validChars = "0123456789";
		
		editQuantidade = new Edit(valor);
		editQuantidade.setMode(Edit.CURRENCY, true);
		editQuantidade.setValidChars(validChars);
		editQuantidade.setEnabled(false);
		
		editTotal = new Edit(valor);
		editTotal.setMode(Edit.CURRENCY, true);
		editTotal.setValidChars(validChars);
		editTotal.setEnabled(false);
		
		btVender = new Button("Vender Sorvete");
		btVoltar = new Button("Voltar");
		
		vendaDAO = new VendaDAO();
		itemDAO = new ItemDAO();
		vendaList = vendaDAO.findAllVendas();

		loadGridData();
	}
	
	private void loadGridData() {
		gridVendas = new Grid(
				new String[] {"CDVENDA", "VALORTOTAL", "QTDVENDIDA"},
				new int[] {-24, -24, -17},
				new int[] {CENTER, CENTER, CENTER},
				false
		);
		
		if(vendaList != null && !vendaList.isEmpty()) {
			for( Venda venda : vendaList) {
				gridVendas.add(new String[] {String.valueOf(venda.cdvenda), String.valueOf(venda.valorTotal), String.valueOf(venda.qtdVendida)});
			}
		}
	}
	
	@Override
	public void initUI() {
		montaTela();
	}
	
	private void reloadList() throws SQLException {
		vendaList = vendaDAO.findAllVendas();
		itemList = itemDAO.findItensByVenda(gridVendas.getSelectedIndex());
		loadGridData();
		
		removeAll();
		montaTela();
		reposition();
	}

	private void montaTela() {
		add(new Label("Lista de Vendas"), CENTER, TOP + 10);
		add(gridVendas, CENTER, CENTER, 450, getGridSize());
		
		add(btVender, CENTER - 40, BOTTOM - 10);
		add(btVoltar, CENTER + 40, BOTTOM - 10);
	}

	private int getGridSize() {
		int size = (vendaList.size() * 400) + (vendaList.size() * 24) + 10;
		size = size > Settings.screenHeight ? Settings.screenHeight - 240 : size;
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
			if(event.target == btVender) {
				try {
					VenderSorvetesWindow venderSorvetes = new VenderSorvetesWindow();
					venderSorvetes.popup();
					reloadList();
				} catch (SQLException e) {
					Vm.debug(e.getMessage());
				}
			} else if(event.target == btVoltar) {
				unpop();				
			}
			break;
		case GridEvent.SELECTED_EVENT:
			try {
				GridEvent geSeleciona = (GridEvent) event;
				Venda venda = vendaList.get(geSeleciona.row);
				ListarSorvetesVendidosWindow sorvetesVendidos = new ListarSorvetesVendidosWindow(venda.cdvenda);
				sorvetesVendidos.popup();
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
