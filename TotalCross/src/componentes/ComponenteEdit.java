package componentes;

import totalcross.ui.Edit;
import totalcross.ui.MainWindow;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.KeyEvent;

public class ComponenteEdit extends MainWindow {
	
	private Edit editNormal;
	private Edit editCep;
	private Edit editFone;
	private Edit editCpf;
	private Edit editTotal;
	
	public ComponenteEdit() {
		editNormal = new Edit();
		editNormal.alignment = Edit.CENTER;
		editNormal.setText("Descrição");
		editNormal.setEditable(false);
		editCep = new Edit("99999-999");
		editCep.setMode(Edit.NORMAL, true);
		editCep.setValidChars("0123456789");
		editCep.setText("00000000");
		
		editFone = new Edit("(99)99999-9999");
		editFone.setMode(Edit.NORMAL, true);
		editFone.setValidChars("0123456789");
		editFone.setText("99999999999");
		
		editCpf = new Edit("999.999.999-99");
		editCpf.setMode(Edit.NORMAL, true);
		editCpf.setValidChars("0123456789");
		editCpf.setText("12345678923");
		
		editTotal = new Edit("999.999.999,99");
		editTotal.setMode(Edit.CURRENCY, true);
		editTotal.setValidChars("0123456789");
		editTotal.setText("1200,30");
		
		System.out.println("CEP: " + editCep.getTextWithoutMask());
		System.out.println("CPF: " + editCpf.getText());
		System.out.println("Telefone: " + editFone.getTextWithoutMask());
		System.out.println("Total: R$" + editTotal.getText());
	}
	
	@Override
	public void initUI() {
		add(editNormal, LEFT + 5, TOP + 5);
		add(editCep, SAME, AFTER + 5);
		add(editFone, SAME, AFTER + 5);
		add(editCpf, SAME, AFTER + 5);
		add(editTotal, SAME, AFTER + 5);
	}
	
	@Override
	public void onEvent(Event event) {
		switch(event.type) {
		case ControlEvent.FOCUS_IN:
			if(event.target == editCep) {
				System.out.println("Aguardando informações...");
				break;
			}
		case ControlEvent.FOCUS_OUT:
			if(event.target == editCep) {
				System.out.println(String.format("CEP digitado: %s", editCep.getText()));
				break;
			}
		case ControlEvent.HIGHLIGHT_IN:
			if(event.target == editTotal) {
				System.out.println("Mesmo comportamento do FOCUS_IN");
				break;
			}
		case KeyEvent.KEY_PRESS:
			if(event.target == editFone) {
				System.out.println(editFone.getText());
			}
			break;
		}
		super.onEvent(event);
	}
}
