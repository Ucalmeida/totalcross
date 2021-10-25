package componentes;

import java.util.List;

import service.CarroService;
import totalcross.io.IOException;
import totalcross.ui.Grid;
import totalcross.ui.MainWindow;
import totalcross.ui.event.Event;
import totalcross.ui.event.GridEvent;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

public class ComponenteGrid extends MainWindow {

	private Grid gridCarros;
	
	private CarroService carroService;
	
	public ComponenteGrid() throws ImageException, IOException {
		
		carroService = new CarroService();
		loadGridData();
		gridCarros.del(3);
		
		String[] row = gridCarros.getItem(5);
		StringBuilder sb = new StringBuilder();
		sb.append("O carro na posição 5 é o ");
		for(int i = 0; i < row.length; i++) {
			sb.append(row[i] + (i == row.length - 1 ? "" : " - "));
		}
		
		System.out.println(sb.toString());
		
		gridCarros.setSelectedIndex(2);
		System.out.println("Você selecionou a posição:" + gridCarros.getSelectedIndex());
		sb.setLength(0);
		sb.append("Nesta posição está o carro: ");
		row = gridCarros.getSelectedItem();
		for(int i = 0; i < row.length; i++) {
			sb.append(row[i] + (i == row.length - 1 ? "" : " - "));
		}
		
		System.out.println(sb.toString());
		
		gridCarros.qsort(2, true);
		
		gridCarros.setColumnChoices(3, new String[] {"2019", "2018", "2017", "2016", "2015"});
		
		gridCarros.setColumnEditable(4, true);
		
		gridCarros.setImage("@foto1", new Image("/resources/camera.png"));
	}
	
	private void loadGridData() {
		gridCarros = new Grid(
				new String[] {"Marca", "Modelo", "Cor", "Ano", "Estoque", "Foto"},
				new int[] {-24, -24, -17, -17, -11, -5},
				new int[] {CENTER, CENTER, CENTER, CENTER, RIGHT, CENTER},
				true
		);
		
		List<String[]> carrosList = carroService.instantiateCarros();
		if(carrosList != null && carrosList.size() > 0) {
			for(int i = 0; i < carrosList.size(); i++) {
				gridCarros.add(carrosList.get(i));
			}
		}
	}
	
	@Override
	public void initUI() {
		add(gridCarros, LEFT, TOP, FILL, FILL);
	}
	
	@Override
	public void onEvent(Event event) {
		switch(event.type) {
		case GridEvent.SELECTED_EVENT:
			GridEvent ge = (GridEvent) event;
			System.out.println(gridCarros.getCellText(ge.row, ge.col));
			break;
		case GridEvent.CHECK_CHANGED_EVENT:
			GridEvent geCheck = (GridEvent) event;
			System.out.println(String.format("A linha %s foi selecionada", geCheck.row));
			
			gridCarros.setSelectedIndex(geCheck.row);
			String[] linha = gridCarros.getSelectedItem();
			StringBuilder sbuild = new StringBuilder();
			sbuild.append("Nesta posição está o carro: ");
			for(int i = 0; i < linha.length; i++) {
				sbuild.append(linha[i] + (i == linha.length - 1 ? "" : " - "));
			}
			
			System.out.println(sbuild.toString());
			
			break;
		case GridEvent.TEXT_CHANGED_EVENT:
			GridEvent geText = (GridEvent) event;
			System.out.println(gridCarros.getCellText(geText.row, geText.col));
			break;
		}
		super.onEvent(event);
	}
}
