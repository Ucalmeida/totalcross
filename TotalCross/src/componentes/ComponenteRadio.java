package componentes;

import totalcross.ui.MainWindow;
import totalcross.ui.Radio;
import totalcross.ui.RadioGroupController;

public class ComponenteRadio extends MainWindow {

	private Radio radioJuridico;
	private Radio radioFisico;
	private RadioGroupController radioTipoCliente;
	
	public ComponenteRadio() {
		radioTipoCliente = new RadioGroupController();
		radioJuridico = new Radio("Jurídico", radioTipoCliente);
		radioFisico = new Radio("Físico", radioTipoCliente);
		
		radioTipoCliente.setSelectedIndex(1);
		
		System.out.println("O cliente é do tipo jurídico? " + (radioJuridico.isChecked() ? "Sim" : "Não"));
		System.out.println("O cliente é do tipo jurídico? " + (radioFisico.isChecked() ? "Sim" : "Não"));
		
		System.out.println("O primeiro radio é " + radioTipoCliente.getRadio(0).getText());
		
		System.out.println("O grupo que o radio " + radioTipoCliente.getRadio(0).getText() + " pertence é " + radioJuridico.getRadioGroup());
		
		System.out.println(radioTipoCliente.getSelectedItem() != null
				? "Cliente " + radioTipoCliente.getSelectedItem().getText() + " selecionado" : "Nenhum radio selecionado");
	}
	
	@Override
	public void initUI() {
		add(radioJuridico, LEFT + 10, TOP + 10);
		add(radioFisico, SAME, AFTER + 10);
	}
}
