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
		radioJuridico = new Radio("Jur�dico", radioTipoCliente);
		radioFisico = new Radio("F�sico", radioTipoCliente);
		
		radioTipoCliente.setSelectedIndex(1);
		
		System.out.println("O cliente � do tipo jur�dico? " + (radioJuridico.isChecked() ? "Sim" : "N�o"));
		System.out.println("O cliente � do tipo jur�dico? " + (radioFisico.isChecked() ? "Sim" : "N�o"));
		
		System.out.println("O primeiro radio � " + radioTipoCliente.getRadio(0).getText());
		
		System.out.println("O grupo que o radio " + radioTipoCliente.getRadio(0).getText() + " pertence � " + radioJuridico.getRadioGroup());
		
		System.out.println(radioTipoCliente.getSelectedItem() != null
				? "Cliente " + radioTipoCliente.getSelectedItem().getText() + " selecionado" : "Nenhum radio selecionado");
	}
	
	@Override
	public void initUI() {
		add(radioJuridico, LEFT + 10, TOP + 10);
		add(radioFisico, SAME, AFTER + 10);
	}
}
