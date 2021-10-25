package componentes;

import domains.Carro;
import totalcross.ui.ComboBox;
import totalcross.ui.MainWindow;
import totalcross.ui.event.Event;
import totalcross.ui.event.KeyEvent;

public class ComponenteComboBox extends MainWindow {

	private ComboBox cbEstadoCivil;
	private ComboBox comboDomain;
	private Carro[] carrosArray;
	private Carro[] carrosArrayDeNovo;
	
	public ComponenteComboBox() {
		cbEstadoCivil = new ComboBox(
				new String[] {
						"", "Solteiro(a)", "Casado(a)", "Divorciado(a)", "União Estável"
				});
		
		cbEstadoCivil.setSelectedItem("Casado(a)");
		
		carrosArray = new Carro[] {
				new Carro("Ford", "Focus", "Prata"),
				new Carro("Chevrolet", "Cruze", "Preto"),
				new Carro("Hyundai", "i30", "Branco")
		};
		
		carrosArrayDeNovo = new Carro[] {
				new Carro("Renault", "Logan", "Preto"),
				new Carro("Fiat", "Punto", "Branco"),
				new Carro("VW", "Gol", "Prata")
		};
		
		comboDomain = new ComboBox(carrosArray);
		
		comboDomain.setSelectedItem(new Carro("Chevrolet", "Cruze", "Preto"));
		
		int selectedEstadoCivilIndex = cbEstadoCivil.getSelectedIndex();
		String selectedEstadoCivil = (String) cbEstadoCivil.getSelectedItem();
		
		int selectedCarroIndex = comboDomain.getSelectedIndex();
		Carro selectedCarro = (Carro) comboDomain.getSelectedItem();
		
		System.out.println(String.format("Estado Civil: %s na posição %s", selectedEstadoCivil, selectedEstadoCivilIndex));
		System.out.println(String.format("Carro: %s na posição %s", selectedCarro, selectedCarroIndex));
		
		cbEstadoCivil.add("Viúvo");
		comboDomain.add(carrosArrayDeNovo);
	}
	
	@Override
	public void initUI() {
		add(cbEstadoCivil, LEFT + 10, TOP + 10);
		add(comboDomain, SAME, AFTER + 10);
	}
	
	@Override
	public void onEvent(Event event) {
		if(event instanceof KeyEvent) {
			KeyEvent ke = (KeyEvent) event;
			if(ke.isDownKey()) {
				int index = comboDomain.getSelectedIndex() + 1 == comboDomain.size() ? 0 : comboDomain.getSelectedIndex() + 1;
				comboDomain.setSelectedIndex(index);
			}
		}
		super.onEvent(event);
	}
}
